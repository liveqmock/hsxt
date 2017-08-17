/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.service.impl.handlers;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.rpc.RpcException;
import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gp.bean.pinganpay.PinganNetPayBean;
import com.gy.hsxt.gp.bean.pinganpay.PinganQuickPayBean;
import com.gy.hsxt.gp.bean.pinganpay.PinganQuickPayBindingBean;
import com.gy.hsxt.gp.bean.pinganpay.PinganQuickPaySmsCodeBean;
import com.gy.hsxt.gp.common.constant.ConfigConst.TcConfigConsts;
import com.gy.hsxt.gp.common.utils.DateUtils;
import com.gy.hsxt.gp.common.utils.MerIdUtils;
import com.gy.hsxt.gp.common.utils.MoneyAmountHelper;
import com.gy.hsxt.gp.common.utils.TimeSecondsSeqWorker;
import com.gy.hsxt.gp.constant.GPConstant.GPErrorCode;
import com.gy.hsxt.gp.constant.GPConstant.PayChannel;
import com.gy.hsxt.gp.mapper.TGpCustidBankcardMapMapper;
import com.gy.hsxt.gp.mapper.TGpPaymentOrderMapper;
import com.gy.hsxt.gp.mapper.vo.TGpCustidBankcardMap;
import com.gy.hsxt.gp.mapper.vo.TGpPaymentOrder;
import com.gy.hsxt.gp.service.ISequenceService;
import com.gy.hsxt.gp.service.impl.check.BasicPropertyCheck;
import com.gy.hsxt.gp.service.impl.check.BeanCheck;
import com.gy.hsxt.gp.service.impl.parent.ParentPaymentService;
import com.gy.hsxt.pg.api.IPGPaymentService;
import com.gy.hsxt.pg.bean.pinganpay.PGPinganNetPayBean;
import com.gy.hsxt.pg.bean.pinganpay.PGPinganQuickPayBean;
import com.gy.hsxt.pg.bean.pinganpay.PGPinganQuickPayBindingBean;
import com.gy.hsxt.pg.bean.pinganpay.PGPinganQuickPaySmsCodeBean;
import com.gy.hsxt.pg.constant.PGConstant.PGChannelProvider;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.service
 * 
 *  File Name       : PinganPaymentHandler.java
 * 
 *  Creation Date   : 2015-9-28
 * 
 *  Author          : zhangysh, 由zhangysh优化
 * 
 *  Purpose         : 平安银行网银接口逻辑处理
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Component("pinganPaymentHandler")
public class PinganPaymentHandler extends ParentPaymentService {
	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private TGpPaymentOrderMapper tGpPaymentOrderMapper;

	@Autowired
	private TGpCustidBankcardMapMapper custidBankcardMapper;

	@Autowired
	private IPGPaymentService pgPaymentService;

	@Autowired
	private ISequenceService sequenceService;

	/**
	 * 获取网银支付url逻辑(平安银行)
	 * 
	 * @param netPayBean
	 * @param srcSubsysId
	 * @return
	 * @throws HsException
	 */
	public String doGetPinganNetPayUrl(PinganNetPayBean netPayBean,
			String srcSubsysId) throws HsException {
		// 校验请求参数、来源系统id是否有效
		BeanCheck.isvalid((PinganNetPayBean) netPayBean);
		BasicPropertyCheck.checkSrcSubSystemId(srcSubsysId);

		// 判断是否已经支付成功或者正在支付
		this.checkOrderSuccess(netPayBean.getOrderNo(), netPayBean.getMerId());

		// 生成银行交易订单号(26位, 按平安银行接口规则生成)
		String bankOrderNo = this.getPinganOrderId();

		// 交易日期, 重要：支付系统和支付网关必须一致
		Date transDate = new Date();

		// 组装支付单数据, 并插入数据库
		this.buildAndInsertOrder2DB(netPayBean, bankOrderNo, transDate,
				srcSubsysId);

		// 调用支付网关接口，获取URL
		try {
			// 组装网关接口所用数据(平安银行)
			PGPinganNetPayBean pgNetPayBean = new PGPinganNetPayBean();
			pgNetPayBean.setBankOrderNo(bankOrderNo);
			pgNetPayBean.setTransDate(transDate);
			pgNetPayBean.setMerType(MerIdUtils.merId2Mertype(netPayBean
					.getMerId()));

			BeanUtils.copyProperties(netPayBean, pgNetPayBean);

			// 组装并设置内部处理私有域
			this.assembleAndSetPrivObligate2(netPayBean.getOrderNo(),
					netPayBean.getOrderDate(), pgNetPayBean);

			return pgPaymentService.getPinganNetPayUrl(pgNetPayBean);
		} catch (HsException e) {
			throw e;
		} catch (RpcException e) {
			logger.error("", e);
			throw new HsException(GPErrorCode.NET_ERROR, "GP支付系统与PG支付网关间通信错误!");
		}
	}

	/**
	 * 获取平安快捷支付银行卡签约的URL[平安银行]
	 * 
	 * @param cardBindingBean
	 *            封装的参数对象
	 * @return
	 */
	public String doGetPinganQuickPayBindingUrl(
			PinganQuickPayBindingBean cardBindingBean) {
		// 校验请求参数是否有效
		BeanCheck.isvalid(cardBindingBean);

		// 生成银行交易订单号
		String requestId = sequenceService.getNextSeq4UpopBinding();

		// 保存custId与bankcardno的对应关系，以备后续签约号的通知
		{
			TGpCustidBankcardMap bankCardMap = new TGpCustidBankcardMap();
			bankCardMap.setChannelProvider(PGChannelProvider.PINGAN_PAY);
			bankCardMap.setCustId(cardBindingBean.getCustId());
			bankCardMap.setBankOrderNo(requestId);
			bankCardMap.setCreatedDate(new Date());

			custidBankcardMapper.insert(bankCardMap);
		}

		// ========================== 调用支付网关组装url =========================
		try {
			// 调用支付网关接口，获取银行卡签约 URL
			PGPinganQuickPayBindingBean pgCardBindingBean = new PGPinganQuickPayBindingBean();
			pgCardBindingBean.setRequestId(requestId);
			pgCardBindingBean.setMerType(MerIdUtils
					.merId2Mertype(cardBindingBean.getMerId()));

			BeanUtils.copyProperties(cardBindingBean, pgCardBindingBean);

			return pgPaymentService
					.getPinganQuickPayBindingUrl(pgCardBindingBean);
		} catch (HsException e) {
			throw e;
		} catch (RpcException e) {
			logger.error("", e);
			throw new HsException(GPErrorCode.NET_ERROR, "GP支付系统与PG支付网关间通信错误!");
		}
	}

	/**
	 * 获取平安快捷支付短信验证码[平安银行]
	 * 
	 * @param quickPaySmsCodeBean
	 *            封装的参数对象
	 * @param srcSubsysId
	 *            子系统标识
	 * @return 成功 or 失败
	 * @throws HsException
	 */
	public boolean doGetPinganQuickPaySmsCode(
			PinganQuickPaySmsCodeBean quickPaySmsCodeBean, String srcSubsysId)
			throws HsException {
		// 校验请求参数、来源系统id是否有效
		BeanCheck.isvalid(quickPaySmsCodeBean);
		BasicPropertyCheck.checkSrcSubSystemId(srcSubsysId);

		TGpPaymentOrder tGpPaymentOrder = tGpPaymentOrderMapper
				.selectOneBySelective(quickPaySmsCodeBean.getMerId(),
						quickPaySmsCodeBean.getOrderNo());

		// 判断是否已经支付成功或者正在支付
		this.checkOrderSuccess(tGpPaymentOrder);

		String bankOrderNo; // 银行支付单号(26位, 按平安银行接口规则生成)
		String transAmount; // 交易金额
		Date transDate; // 交易日期

		if (null == tGpPaymentOrder) {
			bankOrderNo = this.getPinganOrderId();
			transAmount = quickPaySmsCodeBean.getTransAmount();
			transDate = DateUtils.getCurrentDate();

			tGpPaymentOrder = compateOrderData(PGChannelProvider.PINGAN_PAY);
			tGpPaymentOrder.setBankOrderNo(bankOrderNo);
			tGpPaymentOrder.setTransAmount(new BigDecimal(transAmount));
			tGpPaymentOrder.setTransDate(transDate);
			tGpPaymentOrder.setPayChannel(PayChannel.UPOP);
			tGpPaymentOrder.setSrcSubsysId(srcSubsysId);

			BeanUtils.copyProperties(quickPaySmsCodeBean, tGpPaymentOrder);

			// 保存支付单数据至数据库
			tGpPaymentOrderMapper.insert(tGpPaymentOrder);
		} else {
			bankOrderNo = tGpPaymentOrder.getBankOrderNo();
			transAmount = tGpPaymentOrder.getTransAmount().toString();
			transDate = tGpPaymentOrder.getTransDate();
		}

		PGPinganQuickPaySmsCodeBean smsCodeBean = new PGPinganQuickPaySmsCodeBean();
		{
			smsCodeBean.setMerType(MerIdUtils.merId2Mertype(quickPaySmsCodeBean
					.getMerId()));
			smsCodeBean.setBankOrderNo(bankOrderNo);
			smsCodeBean.setTransAmount(MoneyAmountHelper
					.formatAmount(transAmount));
			smsCodeBean.setTransDate(transDate);

			BeanUtils.copyProperties(quickPaySmsCodeBean, smsCodeBean);

			// 组装并设置内部处理私有域
			this.assembleAndSetPrivObligate2(quickPaySmsCodeBean.getOrderNo(),
					quickPaySmsCodeBean.getOrderDate(), smsCodeBean);
		}

		try {
			// 调用支付网关dubbo接口，发起发短信请求
			return pgPaymentService.getPinganQuickPaySmsCode(smsCodeBean);
		} catch (HsException e) {
			logger.error("调用支付网关获取快捷支付手机验证码失败: ", e);
			throw e;
		} catch (RpcException e) {
			logger.error("调用支付网关获取快捷支付手机验证码失败: ", e);
			throw new HsException(GPErrorCode.NET_ERROR, "GP支付系统与PG支付网关间通信错误!");
		}
	}

	/**
	 * 获取平安非首次快捷支付URL地址[平安银行]
	 * 
	 * @param quickPayBean
	 *            封装的参数对象
	 * @param srcSubsysId
	 *            子系统标识
	 * @return 非首次快捷支付URL地址
	 * @throws HsException
	 */
	public String doGetPinganQuickPayUrl(PinganQuickPayBean quickPayBean,
			String srcSubsysId) throws HsException {
		// 校验请求参数、来源系统id是否有效
		BeanCheck.isvalid(quickPayBean);
		BasicPropertyCheck.checkSrcSubSystemId(srcSubsysId);

		// 根据业务订单号从“支付表”中查询出发送短信时创建的预支付单信息
		TGpPaymentOrder tGpPaymentOrder = tGpPaymentOrderMapper
				.selectOneBySelective(quickPayBean.getMerId(),
						quickPayBean.getOrderNo());

		if (null == tGpPaymentOrder) {
			throw new HsException(GPErrorCode.DATA_NOT_EXIST, "请先提交发送短信操作!");
		}

		// 判断是否已经支付成功或者正在支付
		this.checkOrderSuccess(tGpPaymentOrder);

		// 调用支付网关接口，获取签约并支付 URL
		try {
			int merType = MerIdUtils.merId2Mertype(quickPayBean.getMerId());

			// 组装支付网关所需资料
			PGPinganQuickPayBean pgQuickPayBean = new PGPinganQuickPayBean();
			pgQuickPayBean.setBankOrderNo(tGpPaymentOrder.getBankOrderNo());
			pgQuickPayBean.setBindingNo(quickPayBean.getBindingNo());
			pgQuickPayBean.setSmsCode(quickPayBean.getSmsCode());
			pgQuickPayBean.setMerType(merType);			

			return pgPaymentService.getPinganQuickPayUrl(pgQuickPayBean);
		} catch (HsException e) {
			throw e;
		} catch (RpcException e) {
			logger.error("", e);
			throw new HsException(GPErrorCode.NET_ERROR, "GP支付系统与PG支付网关间通信错误!");
		}
	}

	/**
	 * 组装支付单数据, 并插入数据库
	 * 
	 * @param netPayBean
	 * @param bankOrderNo
	 * @param transDate
	 * @param srcSubsysId
	 */
	private void buildAndInsertOrder2DB(PinganNetPayBean netPayBean,
			String bankOrderNo, Date transDate, String srcSubsysId) {
		// 组装支付单数据
		try {
			BigDecimal transAmount = new BigDecimal(netPayBean.getTransAmount());

			TGpPaymentOrder tGpPaymentOrder = compateOrderData(PGChannelProvider.PINGAN_PAY);
			tGpPaymentOrder.setBankOrderNo(bankOrderNo);
			tGpPaymentOrder.setTransAmount(transAmount);
			tGpPaymentOrder.setTransDate(transDate);
			tGpPaymentOrder.setPayChannel(PayChannel.B2C);
			tGpPaymentOrder.setSrcSubsysId(srcSubsysId);

			BeanUtils.copyProperties(netPayBean, tGpPaymentOrder);

			// 保存支付单数据至数据库
			tGpPaymentOrderMapper.insert(tGpPaymentOrder);
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(GPErrorCode.INNER_ERROR, "GP支付系统内部处理错误!");
		}
	}

	/**
	 * 生成平安银行交易订单号
	 * 
	 * @return
	 */
	private String getPinganOrderId() {

		// 商号
		String prefix = HsPropertiesConfigurer
				.getProperty(TcConfigConsts.KEY_SYS_PA_PAY_ORDERID_PREFIX);

		// 生成银行交易订单号
		return TimeSecondsSeqWorker.timeNextId26ByPrefix(prefix);
	}
}