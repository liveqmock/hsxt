/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.service.impl.handlers;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.ChinapayFacade;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.params.ChinapayPayParam;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.ChinapayUpopFacade;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.common.ChinapayUpopConst.UpopSubTransType;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.params.UpopBindingNoResult;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.params.UpopCardBindingParam;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.params.UpopPayFirstParam;
import com.gy.hsxt.pg.bankadapter.chinapay.upop.params.UpopSmsCodeParam;
import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;
import com.gy.hsxt.pg.bankadapter.common.utils.MoneyAmountHelper;
import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;
import com.gy.hsxt.pg.bean.PGFirstQuickPayBean;
import com.gy.hsxt.pg.bean.PGMobilePayTnBean;
import com.gy.hsxt.pg.bean.PGNetPayBean;
import com.gy.hsxt.pg.bean.PGQuickPayBean;
import com.gy.hsxt.pg.bean.PGQuickPayBindingNo;
import com.gy.hsxt.pg.bean.PGQuickPayCardBindingBean;
import com.gy.hsxt.pg.bean.PGQuickPaySmsCodeBean;
import com.gy.hsxt.pg.common.constant.ConfigConst;
import com.gy.hsxt.pg.common.constant.Constant;
import com.gy.hsxt.pg.common.constant.Constant.PGCallbackBizType;
import com.gy.hsxt.pg.common.spring.PropertyConfigurer;
import com.gy.hsxt.pg.common.utils.CallbackUrlHelper;
import com.gy.hsxt.pg.constant.PGConstant.PGChannelProvider;
import com.gy.hsxt.pg.constant.PGConstant.PGErrorCode;
import com.gy.hsxt.pg.constant.PGConstant.PGPayChannel;
import com.gy.hsxt.pg.constant.PGConstant.SecondQuickPayReqKey;
import com.gy.hsxt.pg.mapper.TPgBankPaymentOrderMapper;
import com.gy.hsxt.pg.mapper.vo.TPgBankPaymentOrder;
import com.gy.hsxt.pg.service.IRefundPost;
import com.gy.hsxt.pg.service.impl.ChinapayMobilePayAdapter;
import com.gy.hsxt.pg.service.impl.check.BasicPropertyCheck;
import com.gy.hsxt.pg.service.impl.check.BeanCheck;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service.impl.handlers
 * 
 *  File Name       : ChinapayPaymentHandler.java
 * 
 *  Creation Date   : 2015-10-12
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : PG支付接口服务
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service("chinapayPaymentHandler")
public class ChinapayPaymentHandler extends AbstractPaymentHandler {

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private ChinapayUpopFacade chinapayUpopFacade;

	@Autowired
	private ChinapayFacade chinapayB2cFacade;

	@Autowired
	private ChinapayMobilePayAdapter mobilePayAdapter;

	@Autowired
	private TPgBankPaymentOrderMapper chinapayOrderMapper;

	@Autowired
	private IRefundPost refundPost;

	@Resource(name = "getSmsCodeExecutor")
	private ThreadPoolTaskExecutor getSmsCodeExecutor;

	@Resource(name = "refundExecutor")
	private ThreadPoolTaskExecutor refundExecutor;

	public ChinapayPaymentHandler() {
	}

	@Override
	protected String getChannelProvider() {
		return PGChannelProvider.CHINAPAY;
	}

	/**
	 * 获取网银支付url地址
	 * 
	 * @param netPayBean
	 * @return
	 * @throws Exception
	 */
	public String doGetNetPayUrl(PGNetPayBean netPayBean) throws Exception {
		// 校验请求参数是否有效
		BeanCheck.isvalid(netPayBean);

		// 判断订单号是否存在
		if (checkOrderExist(netPayBean.getBankOrderNo())) {
			throw new HsException(PGErrorCode.DATA_EXIST, "支付单已存在，请不要重复提交");
		}

		// 组装支付单数据保存至数据库
		{
			TPgBankPaymentOrder chinapayOrder = compateData(netPayBean);
			chinapayOrder.setPayChannel(PGPayChannel.B2C);

			chinapayOrderMapper.insert(chinapayOrder);
		}

		// 组装查询所用数据调用银行接口，获取URL
		{
			// 组装查询所用数据
			int payChannel = PGPayChannel.B2C;
			int merType = netPayBean.getMerType();
			int bizType = PGCallbackBizType.CALLBACK_PAY;

			ChinapayPayParam params = new ChinapayPayParam();
			params.setJumpURL(CallbackUrlHelper.getJumpUrl(channelProvider,
					payChannel, merType, bizType));
			params.setNotifyURL(CallbackUrlHelper.getNotifyUrl(channelProvider,
					payChannel, merType, bizType));
			params.setOrderDate(DateUtils.dateToString(
					netPayBean.getTransDate(), "yyyyMMdd"));
			params.setOrderNo(netPayBean.getBankOrderNo());
			params.setPayAmount(MoneyAmountHelper.fromYuanToFen(netPayBean
					.getTransAmount()));

			// 调用银行接口获取URL
			return chinapayB2cFacade.getPayWorker().doAssemblePayUrl(params);
		}
	}

	/**
	 * 调用银行接口获取银行卡签约URL
	 * 
	 * @param cardBindingBean
	 * @return
	 * @throws Exception
	 */
	public String doGetQuickPayCardBindingUrl(
			PGQuickPayCardBindingBean cardBindingBean) throws Exception {
		// 校验请求参数是否有效
		BeanCheck.isvalid(cardBindingBean);

		// 组装查询所用数据调用银行接口，获取URL
		{
			int merType = cardBindingBean.getMerType();
			int payChannel = PGPayChannel.UPOP;
			int bizType = PGCallbackBizType.CALLBACK_BINDING;

			int bankCardType = cardBindingBean.getBankCardType();

			String bankCardNo = cardBindingBean.getBankCardNo();
			String bankId = cardBindingBean.getBankId();

			String notifyUrl = CallbackUrlHelper.getNotifyUrl(channelProvider,
					payChannel, merType, bizType,
					cardBindingBean.getRequestId()); // 后台通知url

			UpopCardBindingParam params = new UpopCardBindingParam();
			params.setBankId(bankId);
			params.setCardNo(bankCardNo);
			params.setCardType(bankCardType);
			params.setNotifyURL(notifyUrl);
			params.setOrderDate(DateUtils.dateToString(new Date(), "yyyyMMdd"));

			// 组装银行请求URL
			return chinapayUpopFacade.getCardBinding()
					.doAssembleCardBindingUrl(params);
		}
	}

	/**
	 * 调用银行接口获取快捷支付URL(首次)URL
	 * 
	 * @param firstQuickPayBean
	 * @return
	 * @throws Exception
	 */
	public String doGetFirstQuickPayUrl(PGFirstQuickPayBean firstQuickPayBean)
			throws Exception {
		// 校验请求参数是否有效
		BeanCheck.isvalid(firstQuickPayBean);

		// 判断订单号是否存在
		if (checkOrderExist(firstQuickPayBean.getBankOrderNo())) {
			throw new HsException(PGErrorCode.DATA_EXIST, "支付单已存在，请不要重复提交!");
		}

		// 组装数据保存支付单数据至数据库
		{
			TPgBankPaymentOrder chinapayOrder = compateData(firstQuickPayBean);
			chinapayOrder.setPayChannel(PGPayChannel.UPOP);

			chinapayOrderMapper.insert(chinapayOrder);
		}

		// 组装查询所用数据调用银行接口，获取URL
		{
			int merType = firstQuickPayBean.getMerType();
			int payChannel = PGPayChannel.UPOP;
			int bizType = PGCallbackBizType.CALLBACK_PAY;

			UpopPayFirstParam params = new UpopPayFirstParam();
			params.setCardNo(firstQuickPayBean.getBankCardNo());
			params.setCardType(firstQuickPayBean.getBankCardType());
			params.setOrderNo(firstQuickPayBean.getBankOrderNo());
			params.setOrderDate(DateUtils.dateToString(
					firstQuickPayBean.getTransDate(), "yyyyMMdd"));
			params.setPayAmount(MoneyAmountHelper
					.fromYuanToFen(firstQuickPayBean.getTransAmount()));
			params.setBankId(firstQuickPayBean.getBankId());
			params.setJumpURL(CallbackUrlHelper.getJumpUrl(channelProvider,
					payChannel, merType, bizType, UpopSubTransType.PAY_FIRST));
			params.setNotifyURL(CallbackUrlHelper.getNotifyUrl(channelProvider,
					payChannel, merType, bizType, UpopSubTransType.PAY_FIRST));

			// 私有域(银行卡号,借贷记标识,银行id), 使用逗号分隔
			String priv1 = StringHelper.joinString(
					firstQuickPayBean.getBankCardNo(), ",",
					firstQuickPayBean.getBankCardType() + ",",
					firstQuickPayBean.getBankId());

			params.setPriv1(priv1);

			return chinapayUpopFacade.getQuickPayFirst().doAssemblePayFirstUrl(
					params);
		}
	}

	/**
	 * 组装非首次快捷支付URL地址
	 * 
	 * @param quickPayBean
	 * @return
	 */
	// http://61.144.241.233:8888/hsxt-pg/p/inner/upop/paysecond?merType=&bankOrderNo=&bindingNo=&smsCode=
	public String doGetQuickPayUrl(PGQuickPayBean quickPayBean) {
		// 校验请求参数是否有效
		BeanCheck.isvalid(quickPayBean);

		// 判断订单号是否存在
		if (!checkOrderExist(quickPayBean.getBankOrderNo())) {
			throw new HsException(PGErrorCode.DATA_NOT_EXIST, "支付单不存在，请先发短信");
		}

		// 商户类型
		int merType = quickPayBean.getMerType();

		String bankOrderNo = quickPayBean.getBankOrderNo();
		String bindingNo = quickPayBean.getBindingNo();
		String smsCode = quickPayBean.getSmsCode();

		String accessAddress = PropertyConfigurer.getProperty(
				ConfigConst.SYSTEM_EXTERNAL_ACCESS_ADDRESS).replaceAll(
				"/{1,}$", "");

		String upopPaysecond = Constant.URL_UPOP_PAY_SECOND;

		StringBuilder sb = new StringBuilder(accessAddress);
		sb.append(upopPaysecond).append("?");
		sb.append(SecondQuickPayReqKey.MER_TYPE).append("=").append(merType);
		sb.append("&");
		sb.append(SecondQuickPayReqKey.BANK_ORDERNO).append("=")
				.append(bankOrderNo);
		sb.append("&");
		sb.append(SecondQuickPayReqKey.BINDING_NO).append("=")
				.append(bindingNo);
		sb.append("&");
		sb.append(SecondQuickPayReqKey.SMS_CODE).append("=").append(smsCode);

		return sb.toString();
	}

	/**
	 * 获取快捷支付短信验证码
	 * 
	 * @param quickPaySmsCodeBean
	 * @return 成功 or 失败
	 */
	public boolean doGetQuickPaySmsCode(
			PGQuickPaySmsCodeBean quickPaySmsCodeBean) {
		// 校验请求参数是否有效
		BeanCheck.isvalid(quickPaySmsCodeBean);

		// 组装数据保存支付单数据至数据库
		TPgBankPaymentOrder chinapayOrder = this
				.compateData(quickPaySmsCodeBean);
		chinapayOrder.setPayChannel(PGPayChannel.UPOP);

		// 如果不存在, 则插入
		if (!checkOrderExist(quickPaySmsCodeBean.getBankOrderNo())) {
			chinapayOrderMapper.insert(chinapayOrder);
		}

		// 异步调用银联发送短信验证码请求
		getSmsCodeExecutor.execute(new GetSmsCodeRunnable(quickPaySmsCodeBean));

		return true;
	}

	/**
	 * 调用银行接口获取手机移动支付TN码
	 * 
	 * @param mobilePayTnBean
	 * @return
	 */
	public String doGetMobilePayTn(PGMobilePayTnBean mobilePayTnBean) {

		// 校验请求参数是否有效
		BeanCheck.isvalid(mobilePayTnBean);

		// 判断订单号是否存在
		if (checkOrderExist(mobilePayTnBean.getBankOrderNo())) {
			throw new HsException(PGErrorCode.DATA_EXIST, "订单已存在，请不要重复提交");
		}

		// 组装数据保存支付单数据至数据库
		{
			TPgBankPaymentOrder chinapayOrder = compateData(mobilePayTnBean);
			chinapayOrder.setPayChannel(PGPayChannel.UPMP);
			chinapayOrderMapper.insert(chinapayOrder);
		}

		return mobilePayAdapter.doGetMobilePayTn(mobilePayTnBean);
	}

	/**
	 * 调用银行接口查询快捷支付签约号
	 * 
	 * @param bankCardNo
	 * @param bankCardType
	 * @param bankId
	 * @return
	 */
	public PGQuickPayBindingNo doGetQuickPayBingNo(String bankCardNo,
			int bankCardType, String bankId) {

		// 如果当前是测试环境
		if (!chinapayUpopFacade.getBindingNoQuery().isProductionEnv()) {
			return null;
		}

		// 验证数据有效性
		BasicPropertyCheck.checkbankCardNo(bankCardNo);
		BasicPropertyCheck.checkbankCardType(bankCardType);
		BasicPropertyCheck.checkbankId(bankId);

		// 查询快捷支付签约号
		UpopBindingNoResult bindingNoResult = null;

		try {
			bindingNoResult = chinapayUpopFacade.getBindingNoQuery()
					.doQueryBindingNo(bankCardNo, bankId,
							String.valueOf(bankCardType));
		} catch (Exception e) {
			logger.error("查询UPOP快捷支付签约号失败：" + e.getMessage(), e);

			throw new HsException(PGErrorCode.NET_ERROR, "PG支付网关与银行之间通信失败!");
		}

		// 根据结果组装
		PGQuickPayBindingNo bindingNo = null;

		if (null != bindingNoResult) {
			bindingNo = new PGQuickPayBindingNo();
			bindingNo.setBankCardNo(bankCardNo);
			bindingNo.setBindingNo(bindingNoResult.getBindingNo());

			if (null != bindingNoResult.getSumLimitStrValue()) {
				bindingNo.setExpireDate(DateUtils.stringToDate(
						bindingNoResult.getExpiry(), "yyyyMMdd"));
				bindingNo.setSumLimit(bindingNoResult.getSumLimitStrValue());
				bindingNo
						.setTransLimit(bindingNoResult.getTransLimitStrValue());
			}
		}

		return bindingNo;
	}

	/**
	 * 支付退款接口
	 * 
	 * @param bankOrderNo
	 * @param origBankOrderNo
	 * @param merType
	 * @return
	 * @throws HsException
	 */
	public boolean doRefund(String bankOrderNo, String origBankOrderNo,
			int merType) throws HsException {
		// 判断参数是否有效
		BasicPropertyCheck.checkBankOrderNo(origBankOrderNo);
		BasicPropertyCheck.checkBankOrderNo(bankOrderNo);
		BasicPropertyCheck.checkMerType(merType);

		// 判断原订单号是否存在
		if (!checkOrderExist(origBankOrderNo)) {
			throw new HsException(PGErrorCode.DATA_NOT_EXIST, origBankOrderNo
					+ "不存在，不能退款");
		}

		// 根据原订单信息生成退款订单信息，并保存至数据库
		final TPgBankPaymentOrder refundOrder = compateRefundByOrign(origBankOrderNo);
		refundOrder.setBankOrderNo(bankOrderNo); // 新订单号

		chinapayOrderMapper.insert(refundOrder);

		// 调用银联接口，进行退款(在线程中执行)
		refundExecutor.execute(new Runnable() {

			public void run() {
				refundPost.refund(refundOrder);
			}
		});

		return true;
	}

	/** 对于中国银联快捷支付“获取短信验证码”请求以异步的方式，调用线程池进行银联相应接口的调用 */
	private class GetSmsCodeRunnable implements Runnable {
		private UpopSmsCodeParam params;

		public GetSmsCodeRunnable(PGQuickPaySmsCodeBean smsCodeBean) {
			int merType = smsCodeBean.getMerType();

			params = new UpopSmsCodeParam();
			params.setBindingNo(smsCodeBean.getBindingNo());
			params.setOrderNo(smsCodeBean.getBankOrderNo());
			params.setOrderDate(DateUtils.dateToString(
					smsCodeBean.getTransDate(), "yyyyMMdd"));
			params.setPayAmount(MoneyAmountHelper.fromYuanToFen(smsCodeBean
					.getTransAmount()));

			if (!StringHelper.isEmpty(smsCodeBean.getJumpUrl())) {
				params.setJumpURL(CallbackUrlHelper.getJumpUrl(channelProvider,
						PGPayChannel.UPOP, merType,
						PGCallbackBizType.CALLBACK_PAY,
						UpopSubTransType.PAY_SECOND));
			}

			params.setNotifyURL(CallbackUrlHelper.getNotifyUrl(channelProvider,
					PGPayChannel.UPOP, merType, PGCallbackBizType.CALLBACK_PAY,
					UpopSubTransType.PAY_SECOND));
		}

		public void run() {
			try {
				chinapayUpopFacade.getSmsCodeRequest().doSendSmsCode(params);
			} catch (Exception e) {
				logger.error("获取快捷支付手机短信验证码SMS Code发生异常:", e);
			}
		}
	}
}