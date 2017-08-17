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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gp.api.IGPNotifyBingNoService;
import com.gy.hsxt.gp.bean.PaymentOrderState;
import com.gy.hsxt.gp.bean.QuickPayBindingNo;
import com.gy.hsxt.gp.bean.TransCashOrderState;
import com.gy.hsxt.gp.common.utils.MoneyAmountHelper;
import com.gy.hsxt.gp.common.utils.NumbericUtils;
import com.gy.hsxt.gp.common.utils.StringUtils;
import com.gy.hsxt.gp.constant.GPConstant.GPTransType;
import com.gy.hsxt.gp.constant.GPConstant.PaymentStateCode;
import com.gy.hsxt.gp.constant.GPConstant.TransStateCode;
import com.gy.hsxt.gp.mapper.TGpCustidBankcardnoMappingMapper;
import com.gy.hsxt.gp.mapper.TGpPaymentOrderMapper;
import com.gy.hsxt.gp.mapper.TGpTransOrderMapper;
import com.gy.hsxt.gp.mapper.vo.TGpCustidBankcardnoMapping;
import com.gy.hsxt.gp.mapper.vo.TGpPaymentOrder;
import com.gy.hsxt.gp.mapper.vo.TGpTransOrder;
import com.gy.hsxt.gp.service.impl.parent.AbstractAppContextAwareServcie;
import com.gy.hsxt.gp.task.RetryManager;
import com.gy.hsxt.lcs.api.ILCSBaseDataService;
import com.gy.hsxt.lcs.bean.PayBank;
import com.gy.hsxt.pg.api.IPGNotifyService;
import com.gy.hsxt.pg.bean.PGPaymentOrderState;
import com.gy.hsxt.pg.bean.PGQuickPayBindingNo;
import com.gy.hsxt.pg.bean.PGTransCashOrderState;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.service
 * 
 *  File Name       : PGNotifyService.java
 * 
 *  Creation Date   : 2015-10-12
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 接收PG支付网关的状态通知, 然后将其递传给BS、AO、UC, 包括通知支付、转账状态, 通知UPOP签约号
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service("pgNotifyService")
public class PGNotifyService extends AbstractAppContextAwareServcie implements
		IPGNotifyService {
	private Logger logger = Logger.getLogger(this.getClass());

	/** 缓存银行id和银行名称映射关系(数据量很小) **/
	private static final Map<String, String> BANK_ID_NAME_MAP = new ConcurrentHashMap<String, String>(
			50);

	@Autowired
	private TGpPaymentOrderMapper paymentOrderMapper;

	@Autowired
	private TGpTransOrderMapper transOrderMapper;

	@Autowired
	private TGpCustidBankcardnoMappingMapper custidBankcardnoMapper;

	@Autowired
	private IGPNotifyBingNoService notifyBingNoService;

	@Autowired
	private ILCSBaseDataService lcsBaseDataService;

	@Autowired
	private RetryManager retryManager;

	@Resource(name = "notifyOrderStateExecutor")
	private ThreadPoolTaskExecutor notifyExecutor;

	/**
	 * 支付单状态异步通知, 该接口由支付网关定义, 然后由支付系统实现。
	 * 
	 * @param pgPaymentState
	 * @return
	 * @throws HsException
	 */
	@Override
	public boolean notifyPaymentOrderState(PGPaymentOrderState pgPaymentState)
			throws HsException {
		logger.info("接收到支付网关支付状态通知: pgPaymentState="
				+ StringUtils.change2Json(pgPaymentState));

		try {
			// 根据银行支付单号查询
			TGpPaymentOrder paymentOrder = paymentOrderMapper
					.selectByBankOrderNo(pgPaymentState.getBankOrderNo());

			if (null != paymentOrder) {
				// 通知的交易状态
				int transStatus = pgPaymentState.getTransStatus();

				// 组装相关的数据更新数据库
				paymentOrder.setBankOrderSeqId(pgPaymentState
						.getBankOrderSeqId());
				paymentOrder.setTransStatus(transStatus);
				paymentOrder.setFailReason(pgPaymentState.getFailReason());

				// 根据银行支付单号更新更新GP本地数据库
				paymentOrderMapper.updateStatusByBankOrderNo(paymentOrder);

				// 通知支付结果状态
				this.notifyPaymentOrderStateInthread(paymentOrder);
			}

			return true;
		} catch (Exception e) {
			logger.error("接收到支付网关payment结果状态通知, 但是GP支付系统处理失败:", e);

			return false;
		}
	}

	/**
	 * PG-GP转账结果异步通知接口
	 * 
	 * @param pgTransCashOrderState
	 * @return
	 * @throws HsException
	 */
	@Override
	public boolean notifyTransCashOrderState(
			PGTransCashOrderState pgTransCashOrderState) throws HsException {
		logger.info("接收到支付网关转账状态通知: pgTransCashOrderState="
				+ StringUtils.change2Json(pgTransCashOrderState));

		// 银行订单流水号 （银联不一定返回），最大20位字符
		String bankOrderSeqId = pgTransCashOrderState.getBankOrderSeqId();

		// 精度为6，转账成功状态才有手续费
		String bankFee = pgTransCashOrderState.getBankFee();

		// 失败原因
		String failReason = StringUtils.cut2SpecialLength(
				pgTransCashOrderState.getFailReason(), 240);

		// 银行受理时间
		Date bankSubmitDate = pgTransCashOrderState.getBankSubmitDate();

		// 银行主机记账时间
		Date bankAccountDate = pgTransCashOrderState.getBankAccountDate();

		// 交易状态 详见“支付状态枚举值定义”
		int transStatus = pgTransCashOrderState.getTransStatus();

		try {
			// 根据银行支付单号查询
			TGpTransOrder transOrder = transOrderMapper
					.selectByBankOrderNo(pgTransCashOrderState.getBankOrderNo());

			if (null != transOrder) {
				// 更新GP转账表相关状态
				transOrder.setBankOrderSeqId(bankOrderSeqId);
				transOrder.setBankSubmitDate(bankSubmitDate);
				transOrder.setBankAccountDate(bankAccountDate);
				transOrder.setTransStatus(transStatus);
				transOrder.setFailReason(failReason);

				if (NumbericUtils.isNumeric(bankFee)) {
					transOrder.setBankFee(new BigDecimal(bankFee));
				}

				// 根据银行支付单号更新GP本地数据库的转账表
				transOrderMapper.updateStatusByBankOrderNo(transOrder);

				// 通知转账结果状态
				this.notifyTransOrderStateInthread(transOrder);
			}

			return true;
		} catch (Exception e) {
			logger.error("接收到支付网关转账状态通知, 但是GP支付系统处理失败:", e);

			return false;
		}
	}

	/**
	 * 快捷支付签约号同步，该接口由支付网关定义，然后由支付系统实现。
	 * 
	 * @param pgQuickPayBindingNo
	 * @return
	 * @throws HsException
	 */
	@Override
	public boolean notifyQuickPayBindingNo(
			PGQuickPayBindingNo pgQuickPayBindingNo) throws HsException {

		logger.info("接收到支付网关签约号同步通知: pgQuickPayBindingNo="
				+ StringUtils.change2Json(pgQuickPayBindingNo));

		String bankOrderNo = pgQuickPayBindingNo.getBankOrderNo();
		String bankCardNo = pgQuickPayBindingNo.getBankCardNo();

		if (!StringUtils.isEmpty(bankOrderNo)) {
			// 查询数据库
			TGpCustidBankcardnoMapping custidBankcard = custidBankcardnoMapper
					.selectByBankcardnoBankorderno(bankOrderNo, bankCardNo);

			// 通知UC
			return doNotifyQuickPayBindingNo(pgQuickPayBindingNo,
					custidBankcard);
		}

		boolean success = true;

		{
			// 查询出卡对应关系列表
			List<TGpCustidBankcardnoMapping> list = custidBankcardnoMapper
					.selectByBankcardno(bankCardNo);

			// 通知UC
			if (null != list) {
				for (TGpCustidBankcardnoMapping record : list) {
					success &= doNotifyQuickPayBindingNo(pgQuickPayBindingNo,
							record);
				}
			}

			return success;
		}
	}

	/**
	 * 处理通知签约号
	 * 
	 * @param pgQuickPayBindingNo
	 * @param custidBankcard
	 * @return
	 */
	private boolean doNotifyQuickPayBindingNo(
			PGQuickPayBindingNo pgQuickPayBindingNo,
			TGpCustidBankcardnoMapping custidBankcard) {
		try {
			// 根据 从数据库查询的数据与网关通知的数据组装通知用户中心的数据
			QuickPayBindingNo quickPayBindingNo = new QuickPayBindingNo();

			BeanUtils.copyProperties(pgQuickPayBindingNo, quickPayBindingNo);

			String bankId = custidBankcard.getBankId();
			String bankName = queryBankNameFromLCS(bankId);

			quickPayBindingNo.setBankId(bankId);
			quickPayBindingNo.setBankName(bankName);
			quickPayBindingNo.setBankCardType(custidBankcard.getBankCardType());
			quickPayBindingNo.setCustId(custidBankcard.getCustId());

			// --------------通知用户中心--------------------
			return notifyBingNoService
					.notifyQuickPayBindingNo(quickPayBindingNo);
		} catch (Exception e) {
			logger.info("向子系统UC通知快捷支付签约号失败:", e);
		}

		return false;
	}

	/**
	 * 将支付状态在线程池中通知BS、AO
	 * 
	 * @param paymentOrder
	 */
	private void notifyPaymentOrderStateInthread(TGpPaymentOrder paymentOrder) {

		// 通知的交易状态
		int transStatus = paymentOrder.getTransStatus();

		// 判断支付状态, 只通知'支付成功'、'支付失败'、'处理中'
		if (!isValidPaymentOrderState(transStatus)) {
			logger.info("只有'支付成功'、'支付失败'、'处理中'的结果状态才进行通知,"
					+ " 其他状态忽略, 当前支付状态忽略: transStatus=" + transStatus);

			return;
		}

		try {
			notifyExecutor.execute(new PaymentOrderStateNotifyThread(
					paymentOrder));
		} catch (TaskRejectedException e) {
			String businessId = paymentOrder.getTransSeqId();
			int retryBussinessType = paymentOrder.getTransType();

			// 根据notify状态更新重试表
			retryManager.insertOrUpdateRetry(businessId, retryBussinessType,
					false);
		}
	}

	/**
	 * 将转账结果状态在线程池中通知BS、AO
	 * 
	 * @param transOrder
	 */
	private void notifyTransOrderStateInthread(final TGpTransOrder transOrder) {
		// 通知的交易状态
		int transStatus = transOrder.getTransStatus();

		// 判断支付状态, 只通知'转账成功'、'转账失败'、'处理中'、'交易错误'
		if (!isValidTransOrderState(transStatus)) {
			logger.info("只通知'转账成功'、'转账失败'、'处理中'、'交易错误', "
					+ "其他状态忽略, 当前转账状态忽略: transStatus=" + transStatus);

			return;
		}

		try {
			notifyExecutor.execute(new TransOrderStateNotifyThread(transOrder));
		} catch (TaskRejectedException e) {
			String businessId = transOrder.getTransSeqId();

			// 根据notify状态更新重试表
			retryManager.insertOrUpdateRetry(businessId,
					GPTransType.TRANS_CASH, false);
		}
	}

	/**
	 * 从LCS查询银行名称
	 * 
	 * @param bankId
	 * @return
	 */
	private String queryBankNameFromLCS(String bankId) {

		// 从缓存中取
		String bankName = BANK_ID_NAME_MAP.get(bankId);

		if (!StringUtils.isEmpty(bankName)) {
			return bankName;
		}

		try {
			PayBank payBank = lcsBaseDataService.queryPayBankByCode(bankId);

			if (null != payBank) {
				bankName = payBank.getBankName();

				// 放入缓存中...
				BANK_ID_NAME_MAP.put(bankId, bankName);
			}
		} catch (Exception e) {
			logger.error("根据快捷支付银行id查询银行名称失败！！ bankId=" + bankId
					+ ", 请检查LCS服务是否正常！", e);
		}

		return bankName;
	}

	/**
	 * 有效的支付结果状态才通知给BS、AO
	 * 
	 * @param transStatus
	 * @return
	 */
	private boolean isValidPaymentOrderState(int transStatus) {
		switch (transStatus) {
			case PaymentStateCode.PAY_SUCCESS:
			case PaymentStateCode.PAY_FAILED:
			case PaymentStateCode.PAY_HANDLING: {
				return true;
			}
			default: {
			}
		}

		return false;
	}

	/**
	 * 有效的转账结果状态才通知给BS、AO
	 * 
	 * @param transStatus
	 * @return
	 */
	private boolean isValidTransOrderState(int transStatus) {
		// 不是待提交银行的状态
		return (TransStateCode.READY != transStatus);
	}

	/** 支付状态通知线程 **/
	private class PaymentOrderStateNotifyThread implements Runnable {
		private TGpPaymentOrder paymentOrder;

		public PaymentOrderStateNotifyThread(TGpPaymentOrder paymentOrder) {
			this.paymentOrder = paymentOrder;
		}

		@Override
		public void run() {
			// 交易序列id
			String businessId = paymentOrder.getTransSeqId();

			// 交易类型
			int retryBussinessType = paymentOrder.getTransType();

			// 调用BS/AO系统, 通知支付结果
			boolean notifySuccess = doNotifyPaymentOrderState();

			// 根据notify状态更新重试表
			retryManager.insertOrUpdateRetry(businessId, retryBussinessType,
					notifySuccess);
		}

		/**
		 * 执行支付结果状态通知
		 * 
		 * @return
		 */
		private boolean doNotifyPaymentOrderState() {
			// 来源子系统id
			String srcSubsysId = paymentOrder.getSrcSubsysId();

			// 交易金额
			String transAmount = MoneyAmountHelper.formatAmount(paymentOrder
					.getTransAmount());

			try {
				// 调用BS系统，通知支付结果
				PaymentOrderState paymentState = new PaymentOrderState();

				BeanUtils.copyProperties(paymentOrder, paymentState);

				paymentState.setTransAmount(transAmount);

				return getNotifyService(srcSubsysId).notifyPaymentOrderState(
						paymentState);
			} catch (Exception e) {
				logger.info("向子系统" + srcSubsysId + "通知支付单状态失败:", e);
			}

			return false;
		}
	}

	/** 转账结果状态通知线程 **/
	private class TransOrderStateNotifyThread implements Runnable {
		private TGpTransOrder transOrder;

		public TransOrderStateNotifyThread(TGpTransOrder transOrder) {
			this.transOrder = transOrder;
		}

		@Override
		public void run() {
			// 交易序列id
			String businessId = transOrder.getTransSeqId();

			// 调用BS/AO系统, 通知支付结果
			boolean notifySuccess = this.doNotifyTransCashOrderState();

			// 根据notify状态更新重试表
			retryManager.insertOrUpdateRetry(businessId,
					GPTransType.TRANS_CASH, notifySuccess);
		}

		/**
		 * 执行转账结果状态通知
		 * 
		 * @return
		 */
		private boolean doNotifyTransCashOrderState() {

			// 来源子系统
			String srcSubsysId = transOrder.getSrcSubsysId();

			// 银行手续费
			String bankFee = MoneyAmountHelper.formatAmount(transOrder
					.getBankFee());

			// 交易金额
			String transAmount = MoneyAmountHelper.formatAmount(transOrder
					.getTransAmount());

			// 银行交易流水号, 由平安银行返回
			String bankOrderSeqId = transOrder.getBankOrderSeqId();

			try {
				// 调用BS系统，通知转账结果
				TransCashOrderState transCashOrderState = new TransCashOrderState();

				BeanUtils.copyProperties(transOrder, transCashOrderState);

				transCashOrderState.setTransAmount(transAmount);
				transCashOrderState.setBankFee(bankFee);
				transCashOrderState.setBankOrderSeqId(bankOrderSeqId);

				logger.info(this.assembleNotifyLogString(transCashOrderState,
						srcSubsysId));

				return getNotifyService(srcSubsysId).notifyTransCashOrderState(
						transCashOrderState);
			} catch (Exception e) {
				logger.info("向子系统" + srcSubsysId + "通知转账状态失败:", e);
			}

			return false;
		}

		/**
		 * 组装日志
		 * 
		 * @param orderState
		 * @param srcSubsysId
		 * @return
		 */
		private String assembleNotifyLogString(TransCashOrderState orderState,
				String srcSubsysId) {

			StringBuilder buffer = new StringBuilder();

			buffer.append("开始向子系统").append(srcSubsysId);
			buffer.append("通知转账结果状态: orderNo=").append(orderState.getOrderNo());
			buffer.append(", bankOrderSeqId=").append(
					orderState.getBankOrderSeqId());
			buffer.append(", transStatus=").append(orderState.getTransStatus());

			return buffer.toString();
		}
	}
}