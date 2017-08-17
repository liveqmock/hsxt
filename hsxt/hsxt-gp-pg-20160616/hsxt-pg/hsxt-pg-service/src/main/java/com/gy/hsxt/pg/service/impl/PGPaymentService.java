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
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.pg.api.IPGPaymentService;
import com.gy.hsxt.pg.bankadapter.chinapay.ChinapayOrderResultDTO;
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
import com.gy.hsxt.pg.bean.PGPaymentOrderState;
import com.gy.hsxt.pg.bean.PGQuickPayBean;
import com.gy.hsxt.pg.bean.PGQuickPayBindingNo;
import com.gy.hsxt.pg.bean.PGQuickPayCardBindingBean;
import com.gy.hsxt.pg.bean.PGQuickPaySmsCodeBean;
import com.gy.hsxt.pg.bean.parent.PGParentBean;
import com.gy.hsxt.pg.common.bean.CallbackRouterInfo;
import com.gy.hsxt.pg.common.constant.ConfigConst;
import com.gy.hsxt.pg.common.constant.Constant;
import com.gy.hsxt.pg.common.constant.Constant.PGCallbackBizType;
import com.gy.hsxt.pg.common.spring.PropertyConfigurer;
import com.gy.hsxt.pg.common.utils.CallbackUrlHelper;
import com.gy.hsxt.pg.common.utils.StringUtils;
import com.gy.hsxt.pg.common.utils.TimeSecondsSeqWorker;
import com.gy.hsxt.pg.constant.PGConstant.PGErrorCode;
import com.gy.hsxt.pg.constant.PGConstant.PGPayChannel;
import com.gy.hsxt.pg.constant.PGConstant.PGPaymentStateCode;
import com.gy.hsxt.pg.constant.PGConstant.PGTransStateCode;
import com.gy.hsxt.pg.constant.PGConstant.SecondQuickPayReqKey;
import com.gy.hsxt.pg.constant.PGConstant.TransType;
import com.gy.hsxt.pg.mapper.TPgChinapayOrderMapper;
import com.gy.hsxt.pg.mapper.vo.TPgChinapayOrder;
import com.gy.hsxt.pg.service.IRefundPost;
import com.gy.hsxt.pg.service.impl.check.BasicPropertyCheck;
import com.gy.hsxt.pg.service.impl.check.BeanCheck;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service.impl
 * 
 *  File Name       : PGPaymentService.java
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
@Service("pgPaymentService")
public class PGPaymentService implements IPGPaymentService {

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private ChinapayUpopFacade chinapayUpopFacade;

	@Autowired
	private ChinapayFacade chinapayB2cFacade;

	@Autowired
	private ChinapayMobilePayAdapter mobilePayAdapter;

	@Autowired
	private TPgChinapayOrderMapper chinapayOrderMapper;

	@Autowired
	private IRefundPost refundPost;
	
	@Resource(name = "getSmsCodeExecutor")  
	private ThreadPoolTaskExecutor getSmsCodeExecutor;
	
	@Resource(name = "refundExecutor")
	private ThreadPoolTaskExecutor refundExecutor;

	/**
	 * 获取网银支付URL
	 * 
	 * @param netPayBean
	 * @return url地址
	 * @throws HsException
	 */
	@Override
	public String getNetPayUrl(PGNetPayBean netPayBean) throws HsException {

		try {
			return this.doGetNetPayUrl(netPayBean);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(PGErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 查询快捷支付签约号接口
	 * 
	 * @param bankCardNo
	 *            银行账户
	 * @param bankCardType
	 *            借贷记标识
	 * @param bankId
	 *            银行简码
	 * @return 签约号相关信息
	 * @throws HsException
	 */
	@Override
	public PGQuickPayBindingNo getQuickPayBindingNo(String bankCardNo,
			int bankCardType, String bankId) throws HsException {

		try {
			return this.doGetQuickPayBingNo(bankCardNo, bankCardType, bankId);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(PGErrorCode.INNER_ERROR, e.getMessage());
		}
	}
	
	/**
	 * 查询快捷支付签约号接口[根据是否测试环境, 返回相应值]
	 * 
	 * @param bankCardNo
	 * @param bankCardType
	 * @param bankId
	 * @return
	 * @throws HsException
	 */
	@Override
	public PGQuickPayBindingNo getQuickPayBindingNoExt(String bankCardNo,
			int bankCardType, String bankId) throws HsException {
		
		// 如果当前是测试环境
		if(!chinapayUpopFacade.getBindingNoQuery().isProductionEnv()) {
			return null;
		}
		
		return this.getQuickPayBindingNo(bankCardNo, bankCardType, bankId);
	}
	
	/**
	 * 获取银行卡签约快捷支付URL接口
	 * 
	 * @param cardBindingBean
	 * @return
	 * @throws HsException
	 */
	@Override
	public String getQuickPayCardBindingUrl(
			PGQuickPayCardBindingBean cardBindingBean) throws HsException {
		try {
			return this.doGetQuickPayCardBindingUrl(cardBindingBean);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(PGErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 获取快捷支付URL(首次)
	 * 
	 * @param firstQuickPayBean
	 * @return 首次快捷支付URL地址
	 * @throws HsException
	 */
	@Override
	public String getFirstQuickPayUrl(PGFirstQuickPayBean firstQuickPayBean)
			throws HsException {
		try {
			return this.doGetFirstQuickPayUrl(firstQuickPayBean);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(PGErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 获取非首次快捷支付URL地址
	 * 
	 * @param quickPayBean
	 * @return 非首次快捷支付URL地址
	 * @throws HsException
	 */
	@Override
	public String getQuickPayUrl(PGQuickPayBean quickPayBean)
			throws HsException {
		try {
			return this.doGetQuickPayUrl(quickPayBean);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(PGErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 获取快捷支付短信验证码
	 * 
	 * @param quickPaySmsCodeBean
	 * @return 成功 or 失败
	 * @throws HsException
	 */
	@Override
	public boolean getQuickPaySmsCode(PGQuickPaySmsCodeBean quickPaySmsCodeBean)
			throws HsException {
		try {
			return this.doGetQuickPaySmsCode(quickPaySmsCodeBean);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(PGErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 获取手机移动支付TN码
	 * 
	 * @param mobilePayTnBean
	 * @return TN交易流水号
	 * @throws HsException
	 */
	@Override
	public String getMobilePayTn(PGMobilePayTnBean mobilePayTnBean)
			throws HsException {
		try {
			return this.doGetMobilePayTn(mobilePayTnBean);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(PGErrorCode.INNER_ERROR, e.getMessage());
		}
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
	@Override
	public boolean refund(String bankOrderNo, String origBankOrderNo,
			int merType) throws HsException {
		try {
			return this.doRefund(bankOrderNo, origBankOrderNo, merType);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(PGErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	@Override
	public List<PGPaymentOrderState> getPaymentOrderState(int merType,
			List<String> bankOrderNoList) throws HsException {

		BasicPropertyCheck.checkMerType(merType);
		BasicPropertyCheck.checkBankOrderNoList(bankOrderNoList);

		List<PGPaymentOrderState> orderStates = new ArrayList<PGPaymentOrderState>(
				10);
		PGPaymentOrderState paymentOrderState;

		try {
			for (String bankOrderNo : bankOrderNoList) {
				paymentOrderState = chinapayOrderMapper
						.selectStateByBankOrderNo(bankOrderNo);

				if (null != paymentOrderState) {
					orderStates.add(paymentOrderState);

					// 手机支付由于无法从银行进行自动对账,所以只能逐个查询
					synUpmpOrderStateFromBank(paymentOrderState);
				}
			}

			// PG不存在该支付单
			if (0 >= orderStates.size()) {
				String orderNoListStr = bankOrderNoList.toString().replaceAll(
						"\\[|\\]", "");

				throw new HsException(PGErrorCode.DATA_NOT_EXIST,
						StringUtils.joinString("要查询的记录不存在! ",
								" bankOrderNoList=", orderNoListStr));
			}

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
	 * 获取网银支付url地址
	 * 
	 * @param netPayBean
	 * @return
	 * @throws Exception
	 */
	private String doGetNetPayUrl(PGNetPayBean netPayBean) throws Exception {
		// 校验请求参数是否有效
		BeanCheck.isvalid(netPayBean);
	
		// 判断订单号是否存在
		boolean isExist = checkOrderExist(netPayBean.getBankOrderNo());
	
		if (isExist) {
			throw new HsException(PGErrorCode.DATA_EXIST, "支付单已存在，请不要重复提交");
		}
	
		// 组装支付单数据保存至数据库
		{
			TPgChinapayOrder chinapayOrder = compateData(netPayBean);
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
			params.setJumpURL(getJumpUrl(payChannel, merType, bizType));
			params.setNotifyURL(getNotifyUrl(payChannel, merType, bizType));
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
	private String doGetQuickPayCardBindingUrl(
			PGQuickPayCardBindingBean cardBindingBean) throws Exception {
		// 校验请求参数是否有效
		BeanCheck.isvalid(cardBindingBean);

		// 组装查询所用数据调用银行接口，获取URL
		{
			int merType = cardBindingBean.getMerType();
			int payChannel = PGPayChannel.UPOP;
			int bizType = PGCallbackBizType.CALLBACK_BINDING;

			// 后台通知url
			String notifyUrl = getNotifyUrl(payChannel, merType, bizType,
					cardBindingBean.getRequestId());

			UpopCardBindingParam params = new UpopCardBindingParam();
			params.setBankId(cardBindingBean.getBankId());
			params.setCardNo(cardBindingBean.getBankCardNo());
			params.setCardType(cardBindingBean.getBankCardType());
			params.setOrderDate(DateUtils.dateToString(new Date(), "yyyyMMdd"));
			params.setNotifyURL(notifyUrl);

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
	private String doGetFirstQuickPayUrl(PGFirstQuickPayBean firstQuickPayBean)
			throws Exception {
		// 校验请求参数是否有效
		BeanCheck.isvalid(firstQuickPayBean);
	
		// 判断订单号是否存在
		boolean exist = checkOrderExist(firstQuickPayBean.getBankOrderNo());
	
		if (exist) {
			throw new HsException(PGErrorCode.DATA_EXIST, "支付单已存在，请不要重复提交!");
		}
	
		// 组装数据保存支付单数据至数据库
		{
			TPgChinapayOrder chinapayOrder = compateData(firstQuickPayBean);
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
			params.setJumpURL(getJumpUrl(payChannel, merType, bizType,
					UpopSubTransType.PAY_FIRST));
			params.setNotifyURL(getNotifyUrl(payChannel, merType, bizType,
					UpopSubTransType.PAY_FIRST));
	
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
	// http://61.144.241.233:8888/hsxt-pg/paygateway/inner/upop/paysecond?merType=&bankOrderNo=&bindingNo=&smsCode=
	private String doGetQuickPayUrl(PGQuickPayBean quickPayBean) {
		// 校验请求参数是否有效
		BeanCheck.isvalid(quickPayBean);

		// 判断订单号是否存在
		boolean exist = checkOrderExist(quickPayBean.getBankOrderNo());

		if (!exist) {
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
	private boolean doGetQuickPaySmsCode(
			PGQuickPaySmsCodeBean quickPaySmsCodeBean) {
		// 校验请求参数是否有效
		BeanCheck.isvalid(quickPaySmsCodeBean);
	
		// 组装数据保存支付单数据至数据库
		TPgChinapayOrder chinapayOrder = this.compateData(quickPaySmsCodeBean);
		chinapayOrder.setPayChannel(PGPayChannel.UPOP);
		
		// 如果不存在, 则插入
		if(!checkOrderExist(quickPaySmsCodeBean.getBankOrderNo())) {
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
	private String doGetMobilePayTn(PGMobilePayTnBean mobilePayTnBean) {

		// 校验请求参数是否有效
		BeanCheck.isvalid(mobilePayTnBean);

		// 判断订单号是否存在
		boolean exist = checkOrderExist(mobilePayTnBean.getBankOrderNo());

		if (exist) {
			throw new HsException(PGErrorCode.DATA_EXIST, "订单已存在，请不要重复提交");
		}

		// 组装数据保存支付单数据至数据库
		{
			TPgChinapayOrder chinapayOrder = compateData(mobilePayTnBean);
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
	private PGQuickPayBindingNo doGetQuickPayBingNo(String bankCardNo,
			int bankCardType, String bankId) {
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

			if (bindingNoResult.getSumLimitStrValue() != null) {
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
	private boolean doRefund(String bankOrderNo, String origBankOrderNo,
			int merType) throws HsException {
		// 判断参数是否有效
		BasicPropertyCheck.checkBankOrderNo(origBankOrderNo);
		BasicPropertyCheck.checkBankOrderNo(bankOrderNo);
		BasicPropertyCheck.checkMerType(merType);

		// 判断原订单号是否存在
		boolean exist = checkOrderExist(origBankOrderNo);

		if (!exist) {
			throw new HsException(PGErrorCode.DATA_NOT_EXIST, origBankOrderNo
					+ "不存在，不能退款");
		}

		// 根据原订单信息生成退款订单信息，并保存至数据库
		final TPgChinapayOrder refundOrder = compateRefundByOrign(origBankOrderNo);
		refundOrder.setBankOrderNo(bankOrderNo);

		chinapayOrderMapper.insert(refundOrder);

		// 调用银联接口，进行退款(在线程中执行)
		refundExecutor.execute(new Runnable() {
			@Override
			public void run() {
				refundPost.refund(refundOrder);
			}
		});		

		return true;
	}

	/**
	 * 根据PGMobilePayTnBean 组装数据
	 * 
	 * @param mobilePayTnBean
	 * @return
	 */
	private TPgChinapayOrder compateData(PGParentBean mobilePayTnBean) {
		// 当前时间
		Date currentDate = DateUtils.getCurrentDate();
	
		// 组装数据保存支付单数据至数据库
		TPgChinapayOrder chinapayOrder = new TPgChinapayOrder();
	
		chinapayOrder.setCreatedDate(currentDate);
		chinapayOrder.setTransStatus(PGPaymentStateCode.READY);
		
		// 生成支付交易流水号
		String transSeqId = TimeSecondsSeqWorker.timeNextId32();
		chinapayOrder.setOrderSeqId(transSeqId);
		chinapayOrder.setTransType(TransType.PAYMENT);
	
		BeanUtils.copyProperties(mobilePayTnBean, chinapayOrder);
	
		chinapayOrder.setTransAmount(new BigDecimal(mobilePayTnBean
				.getTransAmount()));
	
		return chinapayOrder;
	}

	/**
	 * 根据原订单号生成退款订单信息
	 * 
	 * @param origBankOrderNo
	 * @return
	 */
	private TPgChinapayOrder compateRefundByOrign(String origBankOrderNo) {
		// 当前时间
		Date currentDate = DateUtils.getCurrentDate();

		// 根据原单号查询数据库
		TPgChinapayOrder chinapayOrder = chinapayOrderMapper
				.selectByBankOrderNo(origBankOrderNo);	
	
		// 组装退款订单
		TPgChinapayOrder refundOrder = new TPgChinapayOrder();
	
		BeanUtils.copyProperties(chinapayOrder, refundOrder);
	
		refundOrder.setOrigBankOrderNo(chinapayOrder.getBankOrderNo());
		refundOrder.setTransStatus(PGTransStateCode.READY);
		refundOrder.setTransType(TransType.REFUND);
		refundOrder.setCreatedDate(currentDate);
		refundOrder.setOrderSeqId(TimeSecondsSeqWorker.timeNextId32());
	
		return refundOrder;
	}

	/**
	 * 判断订单是否已经存在
	 * 
	 * @param bankOrderNo
	 * @return
	 */
	private boolean checkOrderExist(String bankOrderNo) {	
		int count = chinapayOrderMapper.isExistBankOrderNo(bankOrderNo);
	
		return (0 < count);
	}

	/**
	 * 组装notifyUrl
	 * 
	 * @param payChannel
	 * @param merType
	 * @param bizType
	 * @return
	 */
	private String getNotifyUrl(Integer payChannel, Integer merType,
			Integer bizType) {
		return getNotifyUrl(payChannel, merType, bizType, "");
	}

	/**
	 * 组装notifyUrl
	 * 
	 * @param payChannel
	 * @param merType
	 * @param bizType
	 * @param customizeType
	 * @return
	 */
	private String getNotifyUrl(Integer payChannel, Integer merType,
			Integer bizType, String customizeType) {
		return CallbackUrlHelper
				.assembleCallbackNotifyUrl(new CallbackRouterInfo(payChannel,
						merType, bizType, customizeType));
	}

	/**
	 * 组装jumpUrl
	 * 
	 * @param payChannel
	 * @param merType
	 * @param bizType
	 * @return
	 */
	private String getJumpUrl(Integer payChannel, Integer merType,
			Integer bizType) {
		return getJumpUrl(payChannel, merType, bizType, "");
	}
	
	/**
	 * 组装jumpUrl
	 * 
	 * @param payChannel
	 * @param merType
	 * @param bizType
	 * @param customizeType
	 * @return
	 */
	private String getJumpUrl(Integer payChannel, Integer merType,
			Integer bizType, String customizeType) {
		return CallbackUrlHelper
				.assembleCallbackJumpUrl(new CallbackRouterInfo(payChannel,
						merType, bizType, customizeType));
	}
	
	/**
	 * 从银联获取手机支付状态
	 * 
	 * @param paymentOrderState
	 */
	private void synUpmpOrderStateFromBank(PGPaymentOrderState paymentOrderState) {
		// 手机支付由于无法从银行进行自动对账,所以只能逐个查询
		if (PGPayChannel.UPMP != paymentOrderState.getPayChannel()) {
			return;
		}

		// 支付单号
		String bankOrderNo = paymentOrderState.getBankOrderNo();

		try {
			ChinapayOrderResultDTO dto = mobilePayAdapter
					.getOrderStateFromBank(paymentOrderState);

			int orderStatusInBank = dto.getOrderStatus().getIntValue();

			// 判断支付网关的状态跟银联是否一致, 如果不一致以银行为准
			if (paymentOrderState.getTransStatus() != orderStatusInBank) {
				TPgChinapayOrder record = new TPgChinapayOrder();
				record.setBankOrderNo(bankOrderNo);
				record.setBankOrderStatus(String.valueOf(orderStatusInBank));

				chinapayOrderMapper.updateStatusByBankOrderNo(record);

				paymentOrderState.setTransStatus(orderStatusInBank);
			}
		} catch (Exception e) {
			logger.info("", e);
		}
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
				params.setJumpURL(getJumpUrl(PGPayChannel.UPOP, merType,
						PGCallbackBizType.CALLBACK_PAY,
						UpopSubTransType.PAY_SECOND));
			}

			params.setNotifyURL(getNotifyUrl(PGPayChannel.UPOP, merType,
					PGCallbackBizType.CALLBACK_PAY, UpopSubTransType.PAY_SECOND));
		}

		@Override
		public void run() {
			try {
				chinapayUpopFacade.getSmsCodeRequest().doSendSmsCode(params);
			} catch (Exception e) {
				logger.error("获取快捷支付手机短信验证码SMS Code发生异常:", e);
			}
		}
	}
}