/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.pg.api.IPGTransCashService;
import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;
import com.gy.hsxt.pg.bean.PGTransCash;
import com.gy.hsxt.pg.bean.PGTransCashOrderState;
import com.gy.hsxt.pg.common.constant.Constant.RetryBussinessType;
import com.gy.hsxt.pg.common.utils.StringUtils;
import com.gy.hsxt.pg.common.utils.TimeSecondsSeqWorker;
import com.gy.hsxt.pg.constant.PGConstant.PGErrorCode;
import com.gy.hsxt.pg.constant.PGConstant.PGTransStateCode;
import com.gy.hsxt.pg.mapper.TPgPinganOrderMapper;
import com.gy.hsxt.pg.mapper.vo.TPgPinganOrder;
import com.gy.hsxt.pg.service.ITransCashPost;
import com.gy.hsxt.pg.service.impl.check.BasicPropertyCheck;
import com.gy.hsxt.pg.service.impl.check.BeanCheck;
import com.gy.hsxt.pg.task.RetryManager;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service
 * 
 *  File Name       : PGTransCashService.java
 * 
 *  Creation Date   : 2015-10-10
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 平安银行转账处理服务类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service("pgTransCashService")
public class PGTransCashService implements IPGTransCashService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private TPgPinganOrderMapper pinganOrderMapper;

	@Autowired
	private ITransCashPost transCashPost;

	@Autowired
	private RetryManager retryManager;

	@Resource(name = "tranCashExecutor")
	private ThreadPoolTaskExecutor tranCashExecutor;

	/**
	 * 单笔转账
	 * 
	 * @param merType
	 * @param transCash
	 * @return
	 * @throws HsException
	 */
	@Override
	public boolean transCash(int merType, PGTransCash transCash)
			throws HsException {
		try {
			return this.doTransCash(merType, transCash);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(PGErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 单笔转账结果查询
	 * 
	 * @param merType
	 * @param bankOrderNo
	 * @return
	 * @throws HsException
	 */
	@Override
	public PGTransCashOrderState getTransCashOrderState(int merType,
			String bankOrderNo) throws HsException {
		try {
			return getBatchTransCashOrderState(merType,
					Arrays.asList(new String[] { bankOrderNo })).get(0);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(PGErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 批量转账
	 * 
	 * @param merType
	 * @param bankBatchNo
	 * @param transCashList
	 * @return
	 * @throws HsException
	 */
	@Override
	public boolean batchTransCash(int merType, String bankBatchNo,
			List<PGTransCash> transCashList) throws HsException {

		try {
			return this.doBatchTransCash(merType, bankBatchNo, transCashList);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(PGErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 批量转账结果查询
	 * 
	 * @param merType
	 * @param bankBatchNo
	 * @return
	 * @throws HsException
	 */
	@Override
	public List<PGTransCashOrderState> getBatchTransCashOrderState(int merType,
			String bankBatchNo) throws HsException {

		BasicPropertyCheck.checkMerType(merType);
		BasicPropertyCheck.checkBatchNo(bankBatchNo);

		try {
			List<PGTransCashOrderState> orderStates = pinganOrderMapper
					.selectStateByBatchNo(bankBatchNo);

			// 批次号不存在
			if ((null == orderStates) || (0 >= orderStates.size())) {
				throw new HsException(PGErrorCode.DATA_NOT_EXIST,
						"输入的银行批次号不存在!");
			}

			// 从平安银行查询转账状态
			synTransCashOrderStateFromBank(orderStates, bankBatchNo);

			return orderStates;
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(PGErrorCode.INNER_ERROR, e.getMessage());
		}
	}
	
	@Override
	public List<PGTransCashOrderState> getBatchTransCashOrderState(int merType,
			List<String> bankOrderNoList) throws HsException {

		BasicPropertyCheck.checkMerType(merType);
		BasicPropertyCheck.checkBankOrderNoList(bankOrderNoList);

		PGTransCashOrderState transOrderState;
		List<PGTransCashOrderState> orderStates = new ArrayList<PGTransCashOrderState>(
				10);

		try {
			for (String bankOrderNo : bankOrderNoList) {
				transOrderState = pinganOrderMapper
						.selectStateByBankOrderNo(bankOrderNo);

				if (null != transOrderState) {
					orderStates.add(transOrderState);
				}
			}

			// PG不存在该转账单
			if (0 >= orderStates.size()) {
				String orderNoListStr = bankOrderNoList.toString().replaceAll(
						"\\[|\\]", "");

				throw new HsException(PGErrorCode.DATA_NOT_EXIST,
						StringUtils.joinString("要查询的记录不存在! ",
								" bankOrderNoList=", orderNoListStr));
			}

			// 从平安银行查询转账状态
			synTransCashOrderStateFromBank(orderStates, null);

			return orderStates;
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(PGErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 单笔转账
	 * 
	 * @param merType
	 * @param transCash
	 * @return
	 */
	private boolean doTransCash(int merType, PGTransCash transCash) {
		// 校验请求参数是否有效
		BasicPropertyCheck.checkMerType(merType);
		BeanCheck.isvalid(transCash);

		String bankOrderNo = transCash.getBankOrderNo();

		// 判断订单号是否存在
		boolean exist = checkOrderExist(bankOrderNo);

		if (exist) {
			throw new HsException(PGErrorCode.DATA_EXIST, "转账单" + bankOrderNo
					+ "已经存在，请不要重复提交!");
		}

		// 组装数据保存支付单数据至数据库
		final TPgPinganOrder pinganOrder = assemblePgPinganOrder(merType,
				transCash);

		// 插入转账单数据
		pinganOrderMapper.insert(pinganOrder);

		// 在线程池中调用银行接口转账
		try {
			// =====================插入重试记录：false======================
			retryManager.insertOrUpdateRetry(pinganOrder.getBankOrderNo(),
					RetryBussinessType.TRANS_CASH_REQ, false);
			
			tranCashExecutor.execute(new Runnable() {
				@Override
				public void run() {
					boolean success = transCashPost.postTranCash(pinganOrder);

					if (success) {
						// ===================更新重试记录：true====================
						retryManager.insertOrUpdateRetry(
								pinganOrder.getBankOrderNo(),
								RetryBussinessType.TRANS_CASH_REQ, true);
					}
				}
			});
		} catch (TaskRejectedException e) {
		}

		return true;
	}

	/**
	 * 批量转账
	 * 
	 * @param merType
	 * @param bankBatchNo
	 * @param transCashList
	 * @return
	 */
	private boolean doBatchTransCash(int merType, final String bankBatchNo,
			List<PGTransCash> transCashList) {

		BasicPropertyCheck.checkMerType(merType);
		BasicPropertyCheck.checkBatchNo(bankBatchNo);

		if ((null == transCashList) || (0 >= transCashList.size())) {
			throw new HsException(PGErrorCode.INVALID_PARAM,
					"传入的List<PGTransCash>对象不能为空!");
		}

		// 参数校验
		for (PGTransCash transCash : transCashList) {
			// 校验请求参数是否有效
			BeanCheck.isvalid(transCash);

			String bankOrderNo = transCash.getBankOrderNo();

			// 判断订单号是否存在
			boolean exist = checkOrderExist(bankOrderNo);

			if (exist) {
				throw new HsException(PGErrorCode.DATA_EXIST, "转账单"
						+ bankOrderNo + "已经存在，请不要重复提交!");
			}
		}

		final List<TPgPinganOrder> pinganOrderList = new ArrayList<TPgPinganOrder>();

		for (PGTransCash transCash : transCashList) {
			// 组装数据保存支付单数据至数据库
			TPgPinganOrder pinganOrder = assemblePgPinganOrder(merType,
					transCash);
			pinganOrder.setBankBatchNo(bankBatchNo);

			pinganOrderMapper.insert(pinganOrder);

			pinganOrderList.add(pinganOrder);
		}

		// 在线程池中提交批量转账
		try {
			// =====================插入重试记录：false======================
			retryManager.insertOrUpdateRetry(bankBatchNo,
					RetryBussinessType.TRANS_CASH_BATCH_REQ, false);

			tranCashExecutor.execute(new Runnable() {
				@Override
				public void run() {
					boolean success = transCashPost.postBatchTransCash(
							bankBatchNo, pinganOrderList);

					if (success) {
						// ===================更新重试记录：true====================
						retryManager.insertOrUpdateRetry(bankBatchNo,
								RetryBussinessType.TRANS_CASH_BATCH_REQ, true);
					}
				}
			});
		} catch (TaskRejectedException e) {
		}

		return true;
	}

	/**
	 * 组装平安银行转账单对象
	 * 
	 * @param merType
	 * @param transCash
	 * @return
	 */
	private TPgPinganOrder assemblePgPinganOrder(int merType,
			PGTransCash transCash) {
		// 当前时间
		Date currentDate = DateUtils.getCurrentDate();

		// 组装数据保存支付单数据至数据库
		TPgPinganOrder pinganOrder = new TPgPinganOrder();

		BeanUtils.copyProperties(transCash, pinganOrder);

		pinganOrder.setCreatedDate(currentDate);
		pinganOrder.setTransStatus(PGTransStateCode.READY);

		// 生成转账交易流水号
		String transSeqId = TimeSecondsSeqWorker.timeNextId32();
		String mobileList = StringUtils.listToString(transCash
				.getNotifyMobile());

		pinganOrder.setOrderSeqId(transSeqId);
		pinganOrder.setTransAmount(new BigDecimal(transCash.getTransAmount()));
		pinganOrder.setOutAccName(transCash.getOutBankAccName());
		pinganOrder.setOutAccNo(transCash.getOutBankAccNo());
		pinganOrder.setNotifyMobile(mobileList);
		pinganOrder.setTransDesc(transCash.getUseDesc());
		pinganOrder.setMerType(merType);

		return pinganOrder;
	}

	/**
	 * 判断订单是否已经存在
	 * 
	 * @param bankOrderNo
	 * @return
	 * @throws HsException
	 */
	private boolean checkOrderExist(String bankOrderNo) throws HsException {
		int count = pinganOrderMapper.isExistBankOrderNo(bankOrderNo);

		return (0 < count);
	}
	
	/**
	 * 从平安银行获取最新状态
	 * 
	 * @param transOrderStates
	 * @param bankBatchNo
	 */
	private void synTransCashOrderStateFromBank(
			List<PGTransCashOrderState> transOrderStates, String bankBatchNo) {

		// 如果为空直接返回不处理
		if ((null == transOrderStates) || (0 >= transOrderStates.size())) {
			return;
		}

		TPgPinganOrder pinganOrder;
		List<TPgPinganOrder> resultsFromPA = new ArrayList<TPgPinganOrder>(10);

		// 逐个查询
		if (StringUtils.isEmpty(bankBatchNo)) {
			for (PGTransCashOrderState orderState : transOrderStates) {
				pinganOrder = transCashPost.qrySingleTransferFromPA(orderState
						.getBankOrderNo());

				if (null != pinganOrder) {
					resultsFromPA.add(pinganOrder);
				}
			}
		}
		// 根据银行批次号进行查询
		else {
			resultsFromPA = transCashPost.qryBatchTransferFromPA(bankBatchNo);
		}

		// ======================== 解析并同步银行查询结果 ======================
		if ((null != resultsFromPA) && (0 < resultsFromPA.size())) {
			Map<String, TPgPinganOrder> pinganOrderMap = new HashMap<String, TPgPinganOrder>(
					10);

			for (TPgPinganOrder $pinganOrder : resultsFromPA) {
				pinganOrderMap.put($pinganOrder.getBankOrderNo(), $pinganOrder);
			}

			for (PGTransCashOrderState pgTransOrderState : transOrderStates) {
				pinganOrder = pinganOrderMap.get(pgTransOrderState
						.getBankOrderNo());

				if (null == pinganOrder) {
					continue;
				}

				// 判断PG支付网关的交易状态是否跟银行一致, 不一致则以银行为准
				if (!isOrderDataSame(pgTransOrderState, pinganOrder)) {
					pgTransOrderState.setTransStatus(pinganOrder
							.getTransStatus());
					pgTransOrderState
							.setFailReason(pinganOrder.getFailReason());
					pgTransOrderState.setBankOrderSeqId(pinganOrder
							.getBankOrderSeqId());
					pgTransOrderState.setBankSubmitDate(pinganOrder
							.getBankSubmitDate());

					if (null != pinganOrder.getBankFee()) {
						pgTransOrderState.setBankFee(pinganOrder.getBankFee()
								.toString());
					}

					// =================== 更新支付网关数据库 ===================
					pinganOrderMapper.updateStatusByBankOrderNo(pinganOrder);
				}
			}
		}
	}
	
	/**
	 * 对比银行的数据跟网关的是否一致
	 * 
	 * @param dataInPg
	 * @param dataFromBank
	 * @return
	 */
	private boolean isOrderDataSame(PGTransCashOrderState dataInPg,
			TPgPinganOrder dataFromBank) {

		String feeFromBank = (null == dataFromBank.getBankFee()) ? ""
				: dataFromBank.getBankFee().toString();
		
		boolean isOrderDataSame = true;

		// 比较交易状态
		isOrderDataSame &= (dataInPg.getTransStatus() != dataFromBank
				.getTransStatus());

		// 比较银行手续费
		isOrderDataSame &= StringUtils.compareTrim(dataInPg.getBankFee(),
				feeFromBank);

		// 比较银行流水号
		isOrderDataSame &= StringUtils.compareTrim(
				dataInPg.getBankOrderSeqId(), dataFromBank.getBankOrderSeqId());

		return isOrderDataSame;
	}
}
