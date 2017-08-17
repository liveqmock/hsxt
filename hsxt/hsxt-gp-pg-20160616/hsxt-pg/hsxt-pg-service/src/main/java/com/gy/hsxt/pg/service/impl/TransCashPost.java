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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.pg.api.IPGNotifyService;
import com.gy.hsxt.pg.bankadapter.common.constants.BanksConstants.ErrorCode;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.gy.hsxt.pg.bankadapter.common.utils.MoneyAmountHelper;
import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.PinganB2eFacade;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.orm.Iserializer;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.params.B2eBatchTransDetailParam;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.params.B2eBatchTransParam;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.params.B2eSingleTransParam;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.HeaderDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.BSysFlag;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.PinganRespCode;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean.QrySingleTransferResDTO;
import com.gy.hsxt.pg.bean.PGTransCashOrderState;
import com.gy.hsxt.pg.common.bean.TransferRespData;
import com.gy.hsxt.pg.common.constant.Constant.PGAmountScale;
import com.gy.hsxt.pg.common.constant.Constant.RetryBussinessType;
import com.gy.hsxt.pg.common.utils.StringUtils;
import com.gy.hsxt.pg.constant.PGConstant.PGTransStateCode;
import com.gy.hsxt.pg.mapper.TPgPinganOrderMapper;
import com.gy.hsxt.pg.mapper.vo.TPgPinganOrder;
import com.gy.hsxt.pg.service.impl.parent.ParentTransCashPost;
import com.gy.hsxt.pg.task.RetryManager;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service.impl
 * 
 *  File Name       : TransCashPost.java
 * 
 *  Creation Date   : 2015年12月22日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国平安转账处理
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service("transCashPost")
public class TransCashPost extends ParentTransCashPost {

	@Autowired
	private PinganB2eFacade pinganB2eFacade;

	@Autowired
	private TPgPinganOrderMapper pinganOrderMapper;

	@Autowired
	private RetryManager retryManager;

	/**
	 * 组装数据，通过线程池异步调用单笔转账银行接口
	 * 
	 * @param pinganOrder
	 * @return
	 */
	@Override
	public boolean postTranCash(TPgPinganOrder pinganOrder) {

		// 执行单笔转账
		try {
			this.actionSingleTransRequest(pinganOrder);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/**
	 * 组装数据，通过线程池异步调用批量转账银行接口
	 * 
	 * @param bankBatchNo
	 * @param pinganOrderList
	 * @return
	 */
	@Override
	public boolean postBatchTransCash(String bankBatchNo,
			List<TPgPinganOrder> pinganOrderList) {

		// 执行批量转账
		try {
			this.actionBatchTransRequest(bankBatchNo, pinganOrderList);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/**
	 * 发起向银行查询单笔转账结果, 并进行通知GP
	 * 
	 * @param bankOrderNo
	 * @return
	 */
	@Override
	public boolean qryAndNotifySingleTransfer(String bankOrderNo)
			throws HsException {
		PackageDTO packageDTO = null;

		try {
			// 调用平安银行接口进行查询
			packageDTO = pinganB2eFacade.qrySingleTransfer(bankOrderNo, null,
					null);
		} catch (BankAdapterException e) {
			// 银行没有查到相应记录, 说明没有成功提交到银行
			if (ErrorCode.DATA_NOT_EXIST == e.getErrorCode()) {
				return doNotifySingleTransferState(bankOrderNo, null);
			}

			logger.warn("查询转账记录时出现异常：" + e.getMessage());
		} catch (Exception e) {
			logger.warn("查询转账记录时出现异常：" + e.getMessage());
		}

		if (null != packageDTO) {
			Iserializer resBody = packageDTO.getBody();

			if (null != resBody) {
				return doNotifySingleTransferState(bankOrderNo,
						(QrySingleTransferResDTO) resBody);
			}
		}

		return false;
	}

	/**
	 * 进行单笔转账处理
	 * 
	 * @param pinganOrder
	 * @throws Exception
	 */
	private void actionSingleTransRequest(TPgPinganOrder pinganOrder)
			throws Exception {

		boolean isSocketError = false;

		String failReason = null;
		String bankOrderNo = pinganOrder.getBankOrderNo();

		Integer transStatus = PGTransStateCode.READY;
		B2eSingleTransParam reqParam = assembleB2eSingleTransParam(pinganOrder);

		try {
			PackageDTO packageDto = pinganB2eFacade.doSingleTransfer(reqParam);
			HeaderDTO headerDto = packageDto.getHeader();

			// 银行响应码
			String returnedCode = headerDto.getReturndCode();

			// 银行受理成功
			if (PinganRespCode.isSuccess(returnedCode)) {
				transStatus = PGTransStateCode.BANK_HANDLING;

				logger.info("提交单笔转账请求成功, pinganOrder bankOrderNo="
						+ bankOrderNo);
			}
			// 银行受理失败
			else {
				transStatus = PGTransStateCode.TRANS_FAILED;
				failReason = StringHelper.joinString("[来自银行提示] ", returnedCode,
						" ", headerDto.getReturndDesc());

				logger.info("提交单笔转账请求失败, failReason=" + failReason);
			}
		} catch (BankAdapterException e) {
			ErrorCode errorCode = e.getErrorCode();

			// 出现通信异常或银行内部程序故障，则插入重试模块
			if ((ErrorCode.SOCKET_ERROR == errorCode)
					|| (ErrorCode.BANK_SYSTEM_BUSY == errorCode)) {
				isSocketError = true;

				transStatus = PGTransStateCode.TRANS_ERROR;
				failReason = "PG支付网关与银行通信故障或银行系统繁忙, 经多次重试仍然失败!";

				// 不可删除, 是否重要!!!!!!!!!!!!!!!
				throw e;
			}
			// 重复提交|银行返回空报文异常|银行内部程序故障|银行内部通信异常
			else if (isBelongSubmitedToBank(errorCode)) {
				transStatus = PGTransStateCode.BANK_HANDLING;
			}
			// 其他异常, 只打日志, 不重试
			else {
				transStatus = PGTransStateCode.TRANS_ERROR;
				failReason = "PG内部处理异常导致提交失败, 原因：" + e.getMessage();
			}

			logger.warn("异步提交平安银行转账请求发生异常--> " + e.getMessage());
		} catch (Exception e) {
			logger.warn("异步提交平安银行转账请求发生异常   -> " + e.getMessage());

			transStatus = PGTransStateCode.TRANS_ERROR;
			failReason = "PG内部处理异常导致提交失败, 原因：" + e.getMessage();
		} finally {
			// 是否为socket超时的最后一次重试
			boolean isLastSocketErrRetry = isSocketError
					&& retryManager.isLastTimeRetry(bankOrderNo,
							RetryBussinessType.TRANS_CASH_REQ);

			// 最后一次socket超时异常
			if (!isSocketError || isLastSocketErrRetry) {
				pinganOrder.setFailReason(failReason);
				pinganOrder.setTransStatus(transStatus);

				// =====更新转账单状态, 进行转账状态回调通知====
				this.updateAndNotify4SingleTransState(pinganOrder);
			}
		}
	}

	/**
	 * 进行批量转账处理
	 * 
	 * @param bankBatchNo
	 * @param pinganOrderList
	 * @throws Exception
	 */
	private void actionBatchTransRequest(String bankBatchNo,
			List<TPgPinganOrder> pinganOrderList) throws Exception {
		// 是否是socket通信异常
		boolean isSocketError = false;

		String failReason = null;
		Integer transStatus = PGTransStateCode.READY;

		B2eBatchTransParam params = this.assembleBatchTransCashParam(
				bankBatchNo, pinganOrderList);

		try {
			PackageDTO packageDto = pinganB2eFacade
					.maxBatchTransferFunds(params);

			HeaderDTO headerDto = packageDto.getHeader();

			// 根据转账银行反馈的结果更新数据库
			String returnedCode = headerDto.getReturndCode();

			// 银行受理成功
			if (PinganRespCode.isSuccess(returnedCode)) {
				// 交易状态：银行处理中
				transStatus = PGTransStateCode.BANK_HANDLING;

				logger.info(StringHelper.joinString("提交批量转账请求成功, 提交数据条数：",
						"pinganOrderList size=" + pinganOrderList.size(),
						", bankBatchNo=", bankBatchNo));
			}
			// 银行受理失败
			else {
				transStatus = PGTransStateCode.TRANS_FAILED;
				failReason = StringHelper.joinString("[来自银行提示] ", returnedCode,
						" ", headerDto.getReturndDesc());

				logger.info("提交批量转账请求失败, failReason=" + failReason);
			}
		} catch (BankAdapterException e) {
			ErrorCode errorCode = e.getErrorCode();

			// 出现通信异常或银行内部程序故障，则插入重试模块
			if ((ErrorCode.SOCKET_ERROR == errorCode)
					|| (ErrorCode.BANK_SYSTEM_BUSY == errorCode)) {
				isSocketError = true;

				transStatus = PGTransStateCode.TRANS_ERROR;
				failReason = "PG支付网关与银行之间通信故障, 经多次重试仍然失败!";

				// 不可删除, 是否重要!!!!!!!!!!!!!!!
				throw e;
			}
			// 重复提交|银行返回空报文异常|银行内部程序故障|银行内部通信异常
			else if (isBelongSubmitedToBank(errorCode)) {
				transStatus = PGTransStateCode.BANK_HANDLING;
			}
			// 其他异常, 只打日志, 不重试
			else {
				transStatus = PGTransStateCode.TRANS_ERROR;
				failReason = "PG内部处理异常导致提交失败, 原因：" + e.getMessage();
			}

			logger.warn("异步提交批量转账请求发生异常 --> " + e.getMessage());
		} catch (Exception e) {
			logger.warn("异步提交批量转账时发生异常 -> " + e.getMessage());

			transStatus = PGTransStateCode.TRANS_ERROR;
			failReason = "PG内部处理异常导致提交失败,原因：" + e.getMessage();
		} finally {
			// 是否为socket超时的最后一次重试
			boolean isLastSocketErrRetry = isSocketError
					&& retryManager.isLastTimeRetry(bankBatchNo,
							RetryBussinessType.TRANS_CASH_BATCH_REQ);

			// 最后一次socket超时异常
			if (!isSocketError || isLastSocketErrRetry) {
				// =====更新转账单状态, 进行转账状态回调通知====
				this.updateAndNotify4BatchTransState(bankBatchNo,
						params.getDetailList(), transStatus, failReason);
			}
		}
	}

	/**
	 * 单笔转账 --> 更新转账单状态, 进行转账状态回调通知(socket通信异常, 或者仍然有下一次重试的, 忽略处理)
	 * 
	 * @param pinganOrder
	 */
	private void updateAndNotify4SingleTransState(TPgPinganOrder pinganOrder) {

		Integer transStatus = pinganOrder.getTransStatus();

		String bankOrderNo = pinganOrder.getBankOrderNo();
		String failReason = StringUtils.cut2SpecialLength(
				pinganOrder.getFailReason(), 240);

		// 更新数据库状态
		{
			pinganOrder.setFailReason(failReason);

			pinganOrderMapper.updateStatusByBankOrderNo(pinganOrder);
		}

		// 成功提交到银行的、平安银行返回空报文异常或重复提交, 这种情况需要重新发起查询, 以确认是否已经提交到银行
		if ((PGTransStateCode.BANK_HANDLING == transStatus)
				|| (PGTransStateCode.READY == transStatus)) {
			// ======== 插入重试状态记录: 进行转账状态回调通知 =========
			this.refreshRetryData4SingleTrans(bankOrderNo);
		}

		// 将失败或错误信息通知GP系统
		if ((PGTransStateCode.TRANS_ERROR == transStatus)
				|| (PGTransStateCode.TRANS_FAILED == transStatus)) {
			// 将转账结果状态通知给GP支付系统
			if (!notifyGPSingleTransCash(pinganOrder)) {
				// =================入重试状态记录=================
				retryManager.insertOrUpdateRetry(bankOrderNo,
						RetryBussinessType.TRANS_CASH_NT, false);
			}
		}
		// 成功提交到银行, 处理中状态, 也通知BS/AO, 非重要状态,通知失败也不重试！！！
		else if (PGTransStateCode.BANK_HANDLING == transStatus) {
			this.notifyGPSingleTransCash(pinganOrder);
		}
	}

	/**
	 * 批量转账 --> 更新转账单状态, 进行转账状态回调通知(socket通信异常, 或者仍然有下一次重试的, 忽略处理)
	 * 
	 * @param bankBatchNo
	 * @param detailList
	 * @param transStatus
	 * @param failReason
	 */
	private void updateAndNotify4BatchTransState(String bankBatchNo,
			List<B2eBatchTransDetailParam> detailList, Integer transStatus,
			String failReason) {

		// 更新数据库状态
		pinganOrderMapper.updateByBatchNo(bankBatchNo, transStatus,
				StringUtils.cut2SpecialLength(failReason, 240));

		// 成功提交到银行的、平安银行返回空报文异常或重复提交, 这种情况需要重新发起查询, 以确认是否已经提交到银行
		if ((PGTransStateCode.BANK_HANDLING == transStatus)
				|| (PGTransStateCode.READY == transStatus)) {
			// ======== 插入重试状态记录: 进行批量转账状态回调通知 =========
			this.refreshRetryData4BatchTrans(bankBatchNo, detailList);
		}

		// 将失败或错误信息通知GP系统
		if ((PGTransStateCode.TRANS_ERROR == transStatus)
				|| (PGTransStateCode.TRANS_FAILED == transStatus)) {
			// 将转账结果状态通知给GP支付系统
			boolean isAllSuccess = this.notifyGPBatchTransCash(bankBatchNo);

			if (!isAllSuccess) {
				// 插入重试状态记录, 对错误状态进行通知
				retryManager.insertOrUpdateRetry(bankBatchNo,
						RetryBussinessType.TRANS_CASH_BATCH_NT, false);
			}
		}
		// 成功提交到银行, 处理中状态, 也通知BS/AO, 非重要状态,通知失败也不重试！！！
		else if (PGTransStateCode.BANK_HANDLING == transStatus) {
			this.notifyGPBatchTransCash(bankBatchNo);
		}
	}

	/**
	 * 根据查询单笔转账结果更新数据库
	 * 
	 * @param bankOrderNo
	 * @param resBody
	 * @return
	 */
	private boolean doNotifySingleTransferState(String bankOrderNo,
			QrySingleTransferResDTO resBody) {
		
		if (null == resBody) {
			return false;
		}

		TPgPinganOrder pinganOrder = convert2TPgPinganOrder(bankOrderNo,
				TransferRespData.build(resBody));

		// 同步更新PG本地数据库表状态
		pinganOrderMapper.updateStatusByBankOrderNo(pinganOrder);

		// 当前状态是否为'银行处理中'
		boolean isBankHandling = (PGTransStateCode.BANK_HANDLING == pinganOrder
				.getTransStatus());

		// 通知GP系统, 另：如果是银行处理中, 则需要下一轮重试, 重新查询
		return this.notifyGPSingleTransCash(pinganOrder) && !isBankHandling;
	}

	/**
	 * 转账结果通知GP系统
	 * 
	 * @param pinganOrder
	 * @return
	 */
	private boolean notifyGPSingleTransCash(TPgPinganOrder pinganOrder) {

		PGTransCashOrderState pgTransCashOrderState = new PGTransCashOrderState();
		BeanUtils.copyProperties(pinganOrder, pgTransCashOrderState);

		if (null != pinganOrder.getBankFee()) {
			String bankFee = MoneyAmountHelper.formatAmountScale(
					pinganOrder.getBankFee(), PGAmountScale.SIX);

			pgTransCashOrderState.setBankFee(bankFee);
		}

		// 交易状态
		int transStatus = pgTransCashOrderState.getTransStatus();

		if ((PGTransStateCode.TRANS_SUCCESS == transStatus)
				|| (PGTransStateCode.BANK_HANDLING == transStatus)) {
			pgTransCashOrderState.setFailReason(null);
		}

		// 银行订单号
		String bankOrderNo = pgTransCashOrderState.getBankOrderNo();

		boolean notifysuccess = false;

		try {
			notifysuccess = getNotifyServiceForTrans(bankOrderNo)
					.notifyTransCashOrderState(pgTransCashOrderState);
		} catch (Exception e) {
			logger.info("转账结果通知GP系统失败：" + e);
		}

		return notifysuccess;
	}

	/**
	 * 假批量转账结果通知
	 * 
	 * @param bankBatchNo
	 * @return 是否全部通知成功
	 */
	private boolean notifyGPBatchTransCash(String bankBatchNo) {

		List<PGTransCashOrderState> pgTransCashOrderStates = null;

		try {
			// 根据银行支付单号查询
			pgTransCashOrderStates = pinganOrderMapper
					.selectStateByBatchNo(bankBatchNo);
		} catch (Exception e) {
		}

		if (null != pgTransCashOrderStates) {

			IPGNotifyService pgNotifyService = null;

			// 调用GP系统，通知转账结果
			for (PGTransCashOrderState state : pgTransCashOrderStates) {

				if (null == pgNotifyService) {
					pgNotifyService = getNotifyServiceForTrans(state
							.getBankOrderNo());
				}

				if (!pgNotifyService.notifyTransCashOrderState(state)) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * 插入重试状态记录[单笔转账]
	 * 
	 * @param bankOrderNo
	 */
	private void refreshRetryData4SingleTrans(String bankOrderNo) {
		
		// 向银行提交单笔转账请求的重试模块设置为成功
		retryManager.insertOrUpdateRetry(bankOrderNo,
				RetryBussinessType.TRANS_CASH_REQ, true);

		// 向银行提交单笔转账查询结果请求进行重试
		retryManager.insertOrUpdateRetry(bankOrderNo,
				RetryBussinessType.TRANS_CASH_RESULT_REQ, false);
	}

	/**
	 * 插入重试状态记录[批量转账]
	 * 
	 * @param bankBatchNo
	 * @param detailList
	 */
	private void refreshRetryData4BatchTrans(String bankBatchNo,
			List<B2eBatchTransDetailParam> detailList) {

		// 将查询转账结果放入重试模块
		for (B2eBatchTransDetailParam detail : detailList) {
			retryManager.insertOrUpdateRetry(detail.getsThirdVoucher(),
					RetryBussinessType.TRANS_CASH_RESULT_REQ, false);
		}

		// 更新重试表，设置提交批量转账请求为成功
		retryManager.insertOrUpdateRetry(bankBatchNo,
				RetryBussinessType.TRANS_CASH_BATCH_REQ, true);
	}

	/**
	 * 组装单笔转账请求参数对象
	 * 
	 * @param pinganOrder
	 * @return
	 */
	private B2eSingleTransParam assembleB2eSingleTransParam(
			TPgPinganOrder pinganOrder) {
		// 交易金额
		BigInteger transAmount = MoneyAmountHelper.fromYuanToFen(pinganOrder
				.getTransAmount().setScale(2, BigDecimal.ROUND_HALF_UP)
				.toString());

		// 单笔转账请求参数
		B2eSingleTransParam reqParam = new B2eSingleTransParam();
		reqParam.setAddrFlag(pinganOrder.getAddrFlag());
		reqParam.setCcyCode(pinganOrder.getCurrencyCode());
		reqParam.setInAcctBankName(pinganOrder.getInAccBankName());
		// reqParam.setInAcctBankNode(pinganOrder.getInAccBankNode());
		reqParam.setInAcctCityName(pinganOrder.getInAccCityName());
		reqParam.setInAcctName(pinganOrder.getInAccName());
		reqParam.setInAcctNo(pinganOrder.getInAccNo());
		reqParam.setInAcctProvinceCode(pinganOrder.getInAccProvinceCode());
		reqParam.setMobile(pinganOrder.getNotifyMobile());
		reqParam.setOutAcctName(pinganOrder.getOutAccName());
		reqParam.setOutAcctNo(pinganOrder.getOutAccNo());
		reqParam.setSysFlag(pinganOrder.getSysFlag());
		reqParam.setThirdVoucher(pinganOrder.getBankOrderNo());
		reqParam.setUnionFlag(pinganOrder.getUnionFlag());
		reqParam.setTranAmount(transAmount);

		return reqParam;
	}

	/**
	 * 组装批量转账请求参数对象
	 * 
	 * @param bankBatchNo
	 * @param pinganOrderList
	 * @return
	 */
	private B2eBatchTransParam assembleBatchTransCashParam(String bankBatchNo,
			List<TPgPinganOrder> pinganOrderList) {
		BigInteger totalAmt = new BigInteger("0");
		B2eBatchTransDetailParam detailParam = null;

		List<B2eBatchTransDetailParam> detailList = new ArrayList<B2eBatchTransDetailParam>(
				10);

		// 组装批量请求参数对象
		for (TPgPinganOrder pinganOrder : pinganOrderList) {
			BigInteger tranAmount = MoneyAmountHelper.fromYuanToFen(pinganOrder
					.getTransAmount().setScale(2, BigDecimal.ROUND_HALF_UP)
					.toString());

			detailParam = new B2eBatchTransDetailParam();
			detailParam.setsThirdVoucher(pinganOrder.getBankOrderNo());
			detailParam.setAddrFlag(pinganOrder.getAddrFlag());
			detailParam.setInAcctBankName(pinganOrder.getInAccBankName());
			// reqParam.setInAcctBankNode(pinganOrder.getInAccBankNode());
			detailParam.setInAcctCityName(pinganOrder.getInAccCityName());
			detailParam.setInAcctName(pinganOrder.getInAccName());
			detailParam.setInAcctNo(pinganOrder.getInAccNo());
			// detailParam.setInAcctRecCode(inAcctRecCode);
			detailParam.setUseEx(pinganOrder.getTransDesc());
			detailParam.setUnionFlag(pinganOrder.getUnionFlag());
			detailParam.setInAcctProvinceCode(pinganOrder
					.getInAccProvinceCode());
			detailParam.setTranAmount(tranAmount);

			totalAmt = totalAmt.add(tranAmount);

			detailList.add(detailParam);
		}

		TPgPinganOrder pinganOrder$ = pinganOrderList.get(0);

		B2eBatchTransParam params = new B2eBatchTransParam();
		params.setbSysFlag(BSysFlag.NO.getValue());
		params.setCcycode(pinganOrder$.getCurrencyCode());
		params.setOutAcctName(pinganOrder$.getOutAccName());
		params.setOutAcctNo(pinganOrder$.getOutAccNo());
		params.setThirdVoucher(bankBatchNo);
		params.setTotalAmt(totalAmt);
		params.setDetailList(detailList);

		return params;
	}

	/**
	 * 是否属于已经提交成功(物理上或者逻辑上)
	 * 
	 * @param errorCode
	 * @return
	 */
	private boolean isBelongSubmitedToBank(ErrorCode errorCode) {
		// 重复提交|银行返回空报文异常|银行内部程序故障|银行内部通信异常|收款方户名输入错误及交易暂停等
		boolean flag = ((ErrorCode.DATA_EXIST == errorCode)
				|| (ErrorCode.RESP_PACKET_ABNORMAL == errorCode)
				|| (ErrorCode.BANK_PROGRAM_FAULT == errorCode) 
				|| (ErrorCode.BANK_COMM_ABNORMAL == errorCode)
				|| (ErrorCode.BANK_ABNORMAL_FEE == errorCode));

		return flag;
	}
}