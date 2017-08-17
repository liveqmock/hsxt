/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;

import com.alibaba.dubbo.rpc.RpcException;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gp.api.IGPTransCashService;
import com.gy.hsxt.gp.bean.TransCash;
import com.gy.hsxt.gp.bean.TransCashOrderState;
import com.gy.hsxt.gp.common.constant.Constant.AddrFlag;
import com.gy.hsxt.gp.common.constant.Constant.BSysFlag;
import com.gy.hsxt.gp.common.constant.Constant.UnionFlag;
import com.gy.hsxt.gp.common.spring.transaction.TransactionHandler;
import com.gy.hsxt.gp.common.utils.B2ePABankTransFeeUtil;
import com.gy.hsxt.gp.common.utils.DateUtils;
import com.gy.hsxt.gp.common.utils.MerIdUtils;
import com.gy.hsxt.gp.common.utils.MoneyAmountHelper;
import com.gy.hsxt.gp.common.utils.StringUtils;
import com.gy.hsxt.gp.common.utils.TimeSecondsSeqWorker;
import com.gy.hsxt.gp.constant.GPConstant;
import com.gy.hsxt.gp.constant.GPConstant.GPErrorCode;
import com.gy.hsxt.gp.constant.GPConstant.GPTransSysFlag;
import com.gy.hsxt.gp.constant.GPConstant.MerId;
import com.gy.hsxt.gp.constant.GPConstant.TransStateCode;
import com.gy.hsxt.gp.mapper.TGpBankAccountMapper;
import com.gy.hsxt.gp.mapper.TGpTransOrderMapper;
import com.gy.hsxt.gp.mapper.vo.TGpBankAccount;
import com.gy.hsxt.gp.mapper.vo.TGpTransOrder;
import com.gy.hsxt.gp.service.impl.check.BasicPropertyCheck;
import com.gy.hsxt.gp.service.impl.check.BeanCheck;
import com.gy.hsxt.pg.api.IPGTransCashService;
import com.gy.hsxt.pg.bean.PGTransCash;
import com.gy.hsxt.pg.bean.PGTransCashOrderState;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.service
 * 
 *  File Name       : GPTransCashService.java
 * 
 *  Creation Date   : 2015-9-28
 * 
 *  Author          : zhangysh, 由zhangysh优化
 * 
 *  Purpose         : 平安银行转账业务：单笔转账、批量转账、转账结果查询
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service("gpTransCashService")
public class GPTransCashService implements IGPTransCashService {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private TGpTransOrderMapper transOrderMapper;

	@Autowired
	private TGpBankAccountMapper bankAccountMapper;

	@Autowired
	private IPGTransCashService pgTransCashService;

	@Autowired
	private static TGpBankAccount bankAccount = null;

	@Autowired
	private DataSourceTransactionManager transactionMgr;

	private int outBankAccType = GPConstant.COMMON_OUT_BANK_ACC_TYPE;
	
	@PostConstruct
	private void init() {
		try {
			bankAccount = bankAccountMapper.selectByBankAccType(outBankAccType);

			if (null == bankAccount) {
				String errMsg = "严重错误：未初始化数据库表T_GP_BANK_ACCOUNT !!! "
						+ "该表是地区平台银行账户表(存储银行账户数据,该表数据由脚本导入), 请优先初始化, 再启动.";

				logger.error(errMsg);
			}
		} catch (Exception e) {
			logger.error("初始化严重错误：", e);
		}
	}

	/**
	 * 单笔转账
	 * 
	 * @param merId
	 *            商户号
	 * @param transCash
	 * @return 提交成功 or 失败，如果失败会抛出异常
	 * @throws HsException
	 */
	@Override
	public boolean transCash(String merId, TransCash transCash,
			String srcSubsysId) throws HsException {
		try {
			// 参数校验
			this.checkBeforeSingleTrans(merId, transCash, srcSubsysId);

			return this.doTransCashInTransaction(merId, transCash, srcSubsysId);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(GPErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 单笔转账结果查询
	 * 
	 * @param merId
	 *            商户号
	 * @param orderNo
	 *            订单号
	 * @return
	 * @throws HsException
	 */
	@Override
	public TransCashOrderState getTransCashOrderState(String merId,
			String orderNo) throws HsException {

		// 判断商户号merId是否有效
		BasicPropertyCheck.checkMerId(merId);

		// 校验订单号
		BasicPropertyCheck.checkOrderNo(orderNo);

		return getBatchTransCashOrderStates(merId,
				Arrays.asList(new String[] { orderNo })).get(0);
	}

	/**
	 * 批量转账
	 * 
	 * @param merId
	 *            商户号
	 * @param batchNo
	 *            转账批次号
	 * @param transCashList
	 *            平安银行转账bean集合
	 * @param srcSubsysId
	 *            来源子系统id
	 * @return
	 * @throws HsException
	 */
	@Override
	public boolean batchTransCash(final String merId, final String batchNo,
			final List<TransCash> transCashList, final String srcSubsysId)
			throws HsException {
		try {
			// 参数校验
			this.checkBeforeBatchTrans(merId, batchNo, transCashList,
					srcSubsysId);

			// 交给事务进行处理
			TransactionHandler<Boolean> transaction = new TransactionHandler<Boolean>(
					transactionMgr) {
				@Override
				protected Boolean doInTransaction() {
					return doBatchTransCashInTransaction(merId, batchNo,
							transCashList, srcSubsysId);
				}
			};

			// 避免生产环境报“SQL state [HY000]; error code [1665];”错误, 不可以设置为其他隔离级别
			transaction.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);

			return transaction.getResult();
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(GPErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 根据批次号批量转账结果查询
	 * 
	 * @param merId
	 *            商户号
	 * @param batchNo
	 *            转账批次号
	 * @return
	 * @throws HsException
	 */
	@Override
	public List<TransCashOrderState> getBatchTransCashOrderState(String merId,
			String batchNo) throws HsException {
		// 判断批次号batchNo和商户号merId是否有效
		BasicPropertyCheck.checkMerId(merId);
		BasicPropertyCheck.checkBatchNo(batchNo);

		try {
			// 查询数据库看批次号的数据是否存在
			List<TGpTransOrder> transOrders = transOrderMapper
					.selectbyBatchNoMerId(merId, batchNo);

			// 批次号不存在
			if ((null == transOrders) || (0 >= transOrders.size())) {
				throw new HsException(GPErrorCode.DATA_NOT_EXIST, "输入的批次号不存在!");
			}

			// 从支付网关获取最新转账状态
			return syncTransOrderStateFromPG(merId, transOrders, true);
		} catch (HsException e) {
			logger.error("批量查询转账单状态失败: " + e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error("批量查询转账单状态失败: " + e.getMessage(), e);
			throw new HsException(GPErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 根据订单号批量转账结果查询
	 * 
	 * @param merId
	 *            商户号
	 * @param orderNoList
	 *            业务订单号列表，最大只能传递50个业务订单号，主要是基于性能角度考虑
	 * @return
	 * @throws HsException
	 */
	@Override
	public List<TransCashOrderState> getBatchTransCashOrderStates(String merId,
			List<String> orderNoList) throws HsException {
		// 校验商户号
		BasicPropertyCheck.checkMerId(merId);

		// 校验业务订单号
		BasicPropertyCheck.checkOrderNoList(orderNoList);

		try {
			// 查询数据库
			Map<String, Object> params = new HashMap<String, Object>(2);
			params.put("merId", merId);
			params.put("orderNoList", orderNoList);

			List<TGpTransOrder> transOrders = transOrderMapper
					.selectbyOrderNoMerId(params);

			// 不存在
			if ((null == transOrders) || (0 >= transOrders.size())) {
				String orderNoListStr = orderNoList.toString().replaceAll(
						"\\[|\\]", "");

				throw new HsException(GPErrorCode.DATA_NOT_EXIST,
						StringUtils.joinString("要查询的记录不存在! ", " merId=", merId,
								", orderNo=", orderNoListStr));
			}

			// 从支付网关获取最新转账状态
			return syncTransOrderStateFromPG(merId, transOrders, false);
		} catch (HsException e) {
			logger.error("查询转账单状态失败: " + e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error("查询转账单状态失败: ", e);

			throw new HsException(GPErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 查询转账手续费
	 * 
	 * @param transAmount
	 *            :交易金额
	 * @param inAccBankNode
	 *            :收款人开户银行代码
	 * @param inAccCityCode
	 *            :收款账户开户城市代码
	 * @param sysFlag
	 *            :转账加急标志
	 * @return 转账手续费(精度为6，传递值的精度小于6的自动补足)
	 * @throws HsException
	 */
	@Override
	public String getBankTransFee(String transAmount, String inAccBankNode,
			String inAccCityCode, String sysFlag) throws HsException {
		// 参数校验
		BasicPropertyCheck.checkTransAmount(transAmount);
		BasicPropertyCheck.checkInAccBankNode(inAccBankNode);
		BasicPropertyCheck.checkInAccCityCode(inAccCityCode);
		BasicPropertyCheck.checkSysFlag(sysFlag);

		UnionFlag unionFlag = UnionFlag.OTHER_BANK;

		if (inAccBankNode.equals(bankAccount.getBankCode())) {
			unionFlag = UnionFlag.SAME_BANK;
		}

		// 同城/异地标志 1—同城（默认）2—异地
		AddrFlag addrFlag = AddrFlag.SAME_CITY;

		if (!inAccCityCode.equals(bankAccount.getBankAccCityCode())) {
			addrFlag = AddrFlag.OTHER_CITY;
		}

		// 是否加急
		BSysFlag bSysFlag = BSysFlag.NO;

		if (BSysFlag.SPECITAL.valueEquals(sysFlag)) {
			bSysFlag = BSysFlag.SPECITAL;
		} else if (BSysFlag.YES.valueEquals(sysFlag)) {
			bSysFlag = BSysFlag.YES;
		}

		double bankfee = 0.000000;

		try {
			bankfee = B2ePABankTransFeeUtil.getPABankFee(
					Double.parseDouble(transAmount), unionFlag, addrFlag,
					bSysFlag);
		} catch (Exception e) {
			logger.error("", e);

			throw new HsException(GPErrorCode.INNER_ERROR, "GP支付系统内部处理错误!");
		}

		return MoneyAmountHelper.formatAmount(bankfee);
	}

	/**
	 * 处理单笔转账
	 * 
	 * @param merId
	 *            商户号
	 * @param transCash
	 *            转账对象
	 * @param srcSubsysId
	 *            来源子系统
	 * @return 提交成功 or 失败，如果失败会抛出异常
	 * @throws HsException
	 */
	private boolean doTransCashInTransaction(final String merId,
			final TransCash transCash, final String srcSubsysId)
			throws HsException {

		// 交给事务进行处理
		TransactionHandler<Boolean> transaction = new TransactionHandler<Boolean>(
				transactionMgr) {
			@Override
			protected Boolean doInTransaction() {

				try {
					// 如果GP数据库中不存在, 则新插入一条
					TGpTransOrder transOrder = insertTransOrder2DB(transCash,
							srcSubsysId);

					// 组装数据调用支付网关
					PGTransCash pgTransCash = new PGTransCash();

					BeanUtils.copyProperties(transCash, pgTransCash);

					pgTransCash.setOutBankAccNo(bankAccount.getBankAccNo());
					pgTransCash.setOutBankAccName(bankAccount.getBankAccName());
			
					pgTransCash.setBankOrderNo(transOrder.getBankOrderNo());
					pgTransCash.setTransDate(transOrder.getTransDate());
					
					pgTransCash.setUnionFlag(transOrder.getUnionFlag()); // 同行or跨行
					pgTransCash.setAddrFlag(transOrder.getAddrFlag()); // 同城or异地
					pgTransCash.setSysFlag(transOrder.getSysFlag()); // 加急标志

					return pgTransCashService.transCash(
							MerIdUtils.merId2Mertype(merId), pgTransCash);
				} catch (RpcException e) {
					throw new HsException(GPErrorCode.NET_ERROR,
							"GP支付系统与PG支付网关间通信错误!");
				}
			}

			@Override
			protected void doWhenException() {
			}
		};

		// 避免生产环境报“SQL state [HY000]; error code [1665];”错误, 不可以设置为其他隔离级别
		transaction.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);

		return transaction.getResult();
	}

	/**
	 * 批量转账
	 * 
	 * @param merId
	 *            商户号
	 * @param batchNo
	 *            转账批次号
	 * @param transCashList
	 *            平安银行转账bean集合
	 * @param srcSubsysId
	 *            来源子系统id
	 * @return
	 * @throws HsException
	 */
	private boolean doBatchTransCashInTransaction(String merId, String batchNo,
			List<TransCash> transCashList, String srcSubsysId)
			throws HsException {

		List<PGTransCash> pgTransCashs = new ArrayList<PGTransCash>(
				transCashList.size());

		// 生成用于平安银行使用的批次号
		String bankBatchNo = TimeSecondsSeqWorker.timeNextId20(srcSubsysId);

		// 校验日期, 支付系统和支付网关必须一致!!
		Date transDate = DateUtils.getCurrentDate();

		for (TransCash transCash : transCashList) {
			// 生成银行交易订单号
			String bankOrderNo = TimeSecondsSeqWorker.timeNextId20(srcSubsysId);

			String transAmount = MoneyAmountHelper.formatAmount(transCash
					.getTransAmount());

			// 组装数据保存转账单数据至数据库
			TGpTransOrder transOrder = compateData();

			BeanUtils.copyProperties(transCash, transOrder);

			transOrder.setBankOrderNo(bankOrderNo);
			transOrder.setTransAmount(new BigDecimal(transAmount));
			transOrder.setSrcSubsysId(srcSubsysId);
			transOrder.setTransDate(transDate);			
			transOrder.setNotifyMobile(StringUtils.listToString(transCash
					.getNotifyMobile()));
			transOrder.setBatchNo(batchNo);
			transOrder.setBankBatchNo(bankBatchNo);
			transOrder.setTransDesc(transCash.getUseDesc());
			
			// 设置同城、跨行标识、加急标识
			this.setUnion$Addr$SysFlag(transCash, transOrder);

			transOrderMapper.insert(transOrder);

			PGTransCash pgTransCash = new PGTransCash();

			BeanUtils.copyProperties(transCash, pgTransCash);

			pgTransCash.setOutBankAccNo(bankAccount.getBankAccNo());
			pgTransCash.setOutBankAccName(bankAccount.getBankAccName());
			
			pgTransCash.setBankOrderNo(bankOrderNo);
			pgTransCash.setTransDate(transDate);
			
			pgTransCash.setUnionFlag(transOrder.getUnionFlag()); // 行内跨行标志
			pgTransCash.setAddrFlag(transOrder.getAddrFlag()); // 同城异地标志
			pgTransCash.setSysFlag(transOrder.getSysFlag()); // 加急标志

			pgTransCashs.add(pgTransCash);
		}

		// 调用支付网关接口, 进行批量转账操作处理
		try {
			return pgTransCashService.batchTransCash(
					MerIdUtils.merId2Mertype(merId), bankBatchNo, pgTransCashs);
		} catch (HsException e) {
			throw e;
		} catch (RpcException e) {
			throw new HsException(GPErrorCode.NET_ERROR, "GP支付系统与PG支付网关间通信错误!");
		}
	}

	/**
	 * 判断是否已经转账成功或者正在转账
	 * 
	 * @param orderNo
	 * @param merId
	 * @throws HsException
	 */
	private void checkOrderSuccess(String orderNo, String merId)
			throws HsException {

		TGpTransOrder transOrder = transOrderMapper.selectOneBySelective(merId,
				orderNo);

		if (null != transOrder) {
			checkOrderSuccess(transOrder.getOrderNo(),
					transOrder.getTransStatus());

			throw new HsException(GPErrorCode.REPEAT_SUBMIT,
					transOrder.getOrderNo() + "订单号已经存在, 不能重复提交!!");
		}
	}

	/**
	 * 判断是否已经转账成功或者正在转账
	 * 
	 * @param orderNo
	 * @param transStatus
	 * @throws HsException
	 */
	private void checkOrderSuccess(String orderNo, Integer transStatus)
			throws HsException {

		// 不存在
		if (null == transStatus) {
			return;
		}

		// 判断是否已经转账
		if (transStatus == TransStateCode.TRANS_SUCCESS) {
			throw new HsException(GPErrorCode.HAS_SUCCESS, orderNo
					+ "已经处理成功，请不要重复提交!!");
		}

		// 判断是否正在转账
		if (transStatus == TransStateCode.BANK_HANDLING) {
			throw new HsException(GPErrorCode.BE_HANDLING, orderNo
					+ "正在处理，请不要重复提交!!");
		}
	}

	/**
	 * 向数据库插入转账单
	 * 
	 * @param transCash
	 * @param srcSubsysId
	 * @return
	 */
	private TGpTransOrder insertTransOrder2DB(TransCash transCash,
			String srcSubsysId) {

		String bankOrderNo = TimeSecondsSeqWorker.timeNextId20(srcSubsysId);

		// 交易日期, 重要：支付系统和支付网关必须一致
		Date transDate = DateUtils.getCurrentDate();

		TGpTransOrder transOrder = this.compateData();

		BeanUtils.copyProperties(transCash, transOrder);

		transOrder.setBankOrderNo(bankOrderNo);
		transOrder.setTransAmount(new BigDecimal(transCash.getTransAmount()));
		transOrder.setSrcSubsysId(srcSubsysId);
		transOrder.setTransDate(transDate);		
		transOrder.setNotifyMobile(StringUtils.listToString(transCash
				.getNotifyMobile()));
		transOrder.setTransDesc(transCash.getUseDesc());
		
		// 设置同城、跨行标识、加急标识
		this.setUnion$Addr$SysFlag(transCash, transOrder);

		transOrderMapper.insert(transOrder);

		return transOrder;
	}

	/**
	 * 组装转账单数据对象
	 * 
	 * @return
	 */
	private TGpTransOrder compateData() {
		// 生成支付交易流水号
		String transSeqId = TimeSecondsSeqWorker.timeNextId32();

		// 保存支付单数据至数据库
		TGpTransOrder transOrder = new TGpTransOrder();
		transOrder.setTransSeqId(transSeqId);
		transOrder.setTransStatus(TransStateCode.READY);
		transOrder.setMerId(MerId.HS);
		transOrder.setCurrencyCode("CNY");
		transOrder.setCreatedDate(DateUtils.getCurrentDate());
		transOrder.setOutBankAccType(outBankAccType);

		transOrder.setOutAccNo(bankAccount.getBankAccNo());

		return transOrder;
	}

	/**
	 * 判断批次号在数据库中是否有重复
	 * 
	 * @param batchNo
	 * @throws HsException
	 */
	private void checkIfBatchNoExist(String batchNo) throws HsException {

		int count = transOrderMapper.countBatchNo(batchNo);

		if (count > 0) {
			throw new HsException(GPErrorCode.HAS_SUCCESS, batchNo
					+ "已经处理成功，请不要重复提交!!");
		}
	}

	/**
	 * 批量转账校验
	 * 
	 * @param merId
	 * @param batchNo
	 * @param transCashList
	 * @param srcSubsysId
	 */
	private void checkBeforeBatchTrans(String merId, String batchNo,
			List<TransCash> transCashList, String srcSubsysId) {

		if ((null == transCashList) || (0 >= transCashList.size())) {
			throw new HsException(GPErrorCode.INVALID_PARAM,
					"传递的List<TransCash>参数不能为空!!");
		} else if (50 < transCashList.size()) {
			throw new HsException(GPErrorCode.INVALID_PARAM,
					"传递的List<TransCash>列表大小不能超过50条!!");
		}

		BasicPropertyCheck.checkMerId(merId);
		BasicPropertyCheck.checkBatchNo(batchNo);
		BasicPropertyCheck.checkSrcSubSystemId(srcSubsysId);

		// 判断批次号在数据库中是否有重复
		this.checkIfBatchNoExist(batchNo);

		// 参数校验
		for (TransCash transCash : transCashList) {
			// 校验请求参数是否有效
			BeanCheck.isvalid(transCash);

			// 判断是否已经转账成功或者正在转账
			this.checkOrderSuccess(transCash.getOrderNo(), merId);
		}
	}

	/**
	 * 单笔转账校验
	 * 
	 * @param merId
	 * @param transCash
	 * @param srcSubsysId
	 */
	private void checkBeforeSingleTrans(String merId, TransCash transCash,
			String srcSubsysId) {

		// 校验请求参数是否有效
		BasicPropertyCheck.checkMerId(merId);
		BeanCheck.isvalid(transCash);
		BasicPropertyCheck.checkSrcSubSystemId(srcSubsysId);

		// 判断是否已经转账成功或者正在转账
		this.checkOrderSuccess(transCash.getOrderNo(), merId);
	}

	/**
	 * 从支付网关查询支付单交易状态
	 * 
	 * @param merId
	 * @param transOrderList
	 * @param isByBankBatchNo
	 * @return
	 */
	private List<TransCashOrderState> syncTransOrderStateFromPG(String merId,
			List<TGpTransOrder> transOrderList, boolean isByBankBatchNo) {
		String bankOrderNo;
		String failReason;

		// 银行支付单号列表
		List<String> bankOrderNoList = new ArrayList<String>(
				transOrderList.size());

		// Map<bankOrderNo, TGpPaymentOrder>
		Map<String, TGpTransOrder> gpTransOrdersMap = new HashMap<String, TGpTransOrder>(
				transOrderList.size());

		for (TGpTransOrder transOrder : transOrderList) {
			bankOrderNo = transOrder.getBankOrderNo();
			bankOrderNoList.add(bankOrderNo);
			gpTransOrdersMap.put(bankOrderNo, transOrder);
		}

		int merType = MerIdUtils.merId2Mertype(merId);

		// 从PG支付网关查询转账状态
		List<PGTransCashOrderState> transOrderStatesFromPG;

		// 根据银行批次号查询
		if (isByBankBatchNo) {
			String bankBatchNo = transOrderList.get(0).getBankBatchNo(); // 银行的批次号

			transOrderStatesFromPG = pgTransCashService
					.getBatchTransCashOrderState(merType, bankBatchNo);
		}
		// 根据银行转账单号查询
		else {
			transOrderStatesFromPG = pgTransCashService
					.getBatchTransCashOrderState(merType, bankOrderNoList);
		}

		if (null != transOrderStatesFromPG) {
			TGpTransOrder gpTransOrder;
			BigDecimal bankFee;

			for (PGTransCashOrderState pgOrderState : transOrderStatesFromPG) {
				bankOrderNo = pgOrderState.getBankOrderNo();
				failReason = pgOrderState.getFailReason();

				int transStatusInPg = pgOrderState.getTransStatus();

				gpTransOrder = gpTransOrdersMap.get(bankOrderNo);

				if (null == gpTransOrder) {
					continue;
				}

				// 判断是否GP、PG状态一致, 如果不一致, 以支付网关为准
				if (!isOrderDataSame(pgOrderState, gpTransOrder)) {
					bankFee = StringUtils.isEmpty(pgOrderState.getBankFee()) ? null
							: new BigDecimal(pgOrderState.getBankFee());
					
					gpTransOrder.setTransStatus(transStatusInPg);
					gpTransOrder.setFailReason(failReason);
					gpTransOrder.setBankOrderSeqId(pgOrderState
							.getBankOrderSeqId());
					gpTransOrder.setBankSubmitDate(pgOrderState
							.getBankSubmitDate());
					gpTransOrder.setBankFee(bankFee);

					transOrderMapper.updateStatusByBankOrderNo(gpTransOrder); // 更新支付系统转账状态
				}
			}
		}

		return this.convert2TransOrderState(transOrderList);
	}

	/**
	 * 转换为TransCashOrderState
	 * 
	 * @param transOrderList
	 * @return
	 */
	private List<TransCashOrderState> convert2TransOrderState(
			List<TGpTransOrder> transOrderList) {
		List<TransCashOrderState> transOrderStateList = new ArrayList<TransCashOrderState>(
				transOrderList.size());

		TransCashOrderState transOrderState;

		for (TGpTransOrder transOrder : transOrderList) {
			transOrderState = new TransCashOrderState();

			BeanUtils.copyProperties(transOrder, transOrderState);

			transOrderState.setTransAmount(MoneyAmountHelper
					.formatAmount(transOrder.getTransAmount()));
			transOrderState.setBankFee(MoneyAmountHelper
					.formatAmount(transOrder.getBankFee()));
			transOrderState.setBankOrderSeqId(transOrder.getBankOrderSeqId());
			transOrderState.setBankSubmitDate(transOrder.getBankSubmitDate());
			transOrderState.setBankOrderNo(transOrder.getBankOrderNo());

			transOrderStateList.add(transOrderState);
		}

		return transOrderStateList;
	}
	
	/**
	 * 对比银行的数据跟网关的是否一致
	 * 
	 * @param dataInPg
	 * @param dataFromBank
	 * @return
	 */
	private boolean isOrderDataSame(PGTransCashOrderState dataInPg,
			TGpTransOrder dataFromBank) {

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

	/**
	 * 设置同城、跨行标识、加急标识
	 * 
	 * @param transCash
	 * @param transOrder
	 */
	private void setUnion$Addr$SysFlag(TransCash transCash,
			TGpTransOrder transOrder) {

		// 行内、跨行标志
		{
			String inAccBankNode = StringUtils.avoidNullTrim(transCash
					.getInAccBankNode());

			// 判断行内跨行标志 0：跨行转账（默认） 1：行内转账
			int unionFlag = UnionFlag.OTHER_BANK.getValue();

			if (inAccBankNode.equals(bankAccount.getBankCode())) {
				unionFlag = UnionFlag.SAME_BANK.getValue();
			}

			transOrder.setUnionFlag(unionFlag);
		}

		// 同城、异地标志
		{
			String inAccCityCode = StringUtils.avoidNullTrim(transCash
					.getInAccCityCode());

			// 同城/异地标志 1—同城（默认）2—异地
			int addrFlag = AddrFlag.OTHER_CITY.getValue();

			if (inAccCityCode.equals(bankAccount.getBankAccCityCode())) {
				addrFlag = AddrFlag.SAME_CITY.getValue();
			}

			transOrder.setAddrFlag(addrFlag);
		}

		// 加急标志
		{
			String sysFlag = transCash.getSysFlag();

			// 默认为: N-普通
			if (StringUtils.isEmpty(sysFlag)) {
				sysFlag = GPTransSysFlag.URGENT;
			}

			transOrder.setSysFlag(sysFlag);
		}
	}
}
