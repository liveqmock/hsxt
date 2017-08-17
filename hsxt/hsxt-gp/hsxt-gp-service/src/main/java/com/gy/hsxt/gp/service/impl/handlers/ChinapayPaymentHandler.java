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
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gp.bean.FirstQuickPayBean;
import com.gy.hsxt.gp.bean.MobilePayBean;
import com.gy.hsxt.gp.bean.NetPayBean;
import com.gy.hsxt.gp.bean.QuickPayBean;
import com.gy.hsxt.gp.bean.QuickPayCardBindingBean;
import com.gy.hsxt.gp.bean.QuickPaySmsCodeBean;
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
import com.gy.hsxt.pg.bean.PGFirstQuickPayBean;
import com.gy.hsxt.pg.bean.PGMobilePayTnBean;
import com.gy.hsxt.pg.bean.PGNetPayBean;
import com.gy.hsxt.pg.bean.PGQuickPayBean;
import com.gy.hsxt.pg.bean.PGQuickPayCardBindingBean;
import com.gy.hsxt.pg.bean.PGQuickPaySmsCodeBean;
import com.gy.hsxt.pg.constant.PGConstant.PGChannelProvider;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.service
 * 
 *  File Name       : ChinapayPaymentHandler.java
 * 
 *  Creation Date   : 2015-9-28
 * 
 *  Author          : zhangysh, 由zhangysh优化
 * 
 *  Purpose         : 中国银联业务接口，包括网银支付，快捷支付，手机支付，全渠道
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Component("chinapayPaymentHandler")
public class ChinapayPaymentHandler extends ParentPaymentService {
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
	 * 获取网银支付url逻辑
	 * 
	 * @param netPayBean
	 * @param srcSubsysId
	 * @return
	 * @throws HsException
	 */
	public String doGetNetPayUrl(NetPayBean netPayBean, String srcSubsysId)
			throws HsException {
		// 校验请求参数、来源系统id是否有效
		BeanCheck.isvalid(netPayBean);
		BasicPropertyCheck.checkSrcSubSystemId(srcSubsysId);

		// 判断是否已经支付成功或者正在支付
		this.checkOrderSuccess(netPayBean.getOrderNo(), netPayBean.getMerId());

		// 生成银行交易订单号
		String bankOrderNo = TimeSecondsSeqWorker.timeNextId16(srcSubsysId);

		// 交易日期, 重要：支付系统和支付网关必须一致
		Date transDate = new Date();

		// 组装支付单数据
		try {
			BigDecimal transAmount = new BigDecimal(netPayBean.getTransAmount());

			TGpPaymentOrder tGpPaymentOrder = compateOrderData(PGChannelProvider.CHINAPAY);
			tGpPaymentOrder.setTransDate(transDate);
			tGpPaymentOrder.setTransAmount(transAmount);
			tGpPaymentOrder.setBankOrderNo(bankOrderNo);
			tGpPaymentOrder.setSrcSubsysId(srcSubsysId);
			tGpPaymentOrder.setPayChannel(PayChannel.B2C);

			BeanUtils.copyProperties(netPayBean, tGpPaymentOrder);

			// 保存支付单数据至数据库
			tGpPaymentOrderMapper.insert(tGpPaymentOrder);
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(GPErrorCode.INNER_ERROR, "GP支付系统内部处理错误!");
		}

		// 调用支付网关接口，获取URL
		try {
			// 组装网关接口所用数据
			PGNetPayBean pgNetPayBean = new PGNetPayBean();
			pgNetPayBean.setBankOrderNo(bankOrderNo);
			pgNetPayBean.setTransDate(transDate);
			pgNetPayBean.setMerType(MerIdUtils.merId2Mertype(netPayBean
					.getMerId()));

			BeanUtils.copyProperties(netPayBean, pgNetPayBean);

			// 组装并设置内部处理私有域
			this.assembleAndSetPrivObligate2(netPayBean.getOrderNo(),
					netPayBean.getOrderDate(), pgNetPayBean);

			return pgPaymentService.getNetPayUrl(pgNetPayBean);
		} catch (HsException e) {
			throw e;
		} catch (RpcException e) {
			logger.error("", e);
			throw new HsException(GPErrorCode.NET_ERROR, "GP支付系统与PG支付网关间通信错误!");
		}
	}

	/**
	 * 获取快捷支付URL(首次)
	 * 
	 * @param cardBindingBean
	 * @return
	 * @throws HsException
	 */
	public String doGetQuickPayCardBindingUrl(
			QuickPayCardBindingBean cardBindingBean) throws HsException {
		// 校验请求参数是否有效
		BeanCheck.isvalid(cardBindingBean);

		// 生成银行交易订单号
		String requestId = sequenceService.getNextSeq4UpopBinding();

		// 保存custId与bankcardno的对应关系，以备后续签约号的通知
		{
			TGpCustidBankcardMap bankCardMap = new TGpCustidBankcardMap();
			bankCardMap.setBankOrderNo(requestId);
			bankCardMap.setChannelProvider(PGChannelProvider.CHINAPAY);
			bankCardMap.setCreatedDate(new Date());

			BeanUtils.copyProperties(cardBindingBean, bankCardMap);

			custidBankcardMapper.insert(bankCardMap);
		}

		// ========================== 调用支付网关组装url =========================
		try {
			// 调用支付网关接口，获取银行卡签约 URL
			PGQuickPayCardBindingBean pgCardBindingBean = new PGQuickPayCardBindingBean();
			pgCardBindingBean.setRequestId(requestId);
			pgCardBindingBean.setMerType(MerIdUtils
					.merId2Mertype(cardBindingBean.getMerId()));

			BeanUtils.copyProperties(cardBindingBean, pgCardBindingBean);

			return pgPaymentService
					.getQuickPayCardBindingUrl(pgCardBindingBean);
		} catch (HsException e) {
			throw e;
		} catch (RpcException e) {
			logger.error("", e);
			throw new HsException(GPErrorCode.NET_ERROR, "GP支付系统与PG支付网关间通信错误!");
		}
	}

	/**
	 * 获取快捷支付URL(首次)
	 * 
	 * @param firstQuickPayBean
	 * @param srcSubsysId
	 * @return
	 * @throws HsException
	 */
	public String doGetFirstQuickPayUrl(FirstQuickPayBean firstQuickPayBean,
			String srcSubsysId) throws HsException {
		// 校验请求参数、来源系统id是否有效
		BeanCheck.isvalid(firstQuickPayBean);
		BasicPropertyCheck.checkSrcSubSystemId(srcSubsysId);

		// 判断是否已经支付成功或者正在支付
		this.checkOrderSuccess(firstQuickPayBean.getOrderNo(),
				firstQuickPayBean.getMerId());

		// 生成银行交易订单号
		String bankOrderNo = TimeSecondsSeqWorker.timeNextId16(srcSubsysId);

		// 交易日期, 重要：支付系统和支付网关必须一致
		Date transDate = new Date();

		// 保存支付单数据至数据库
		{
			BigDecimal transAmount = new BigDecimal(
					firstQuickPayBean.getTransAmount());

			TGpPaymentOrder tGpPaymentOrder = compateOrderData(PGChannelProvider.CHINAPAY);
			tGpPaymentOrder.setBankOrderNo(bankOrderNo);
			tGpPaymentOrder.setTransAmount(transAmount);
			tGpPaymentOrder.setTransDate(transDate);
			tGpPaymentOrder.setPayChannel(PayChannel.UPOP);
			tGpPaymentOrder.setSrcSubsysId(srcSubsysId);

			BeanUtils.copyProperties(firstQuickPayBean, tGpPaymentOrder);

			tGpPaymentOrderMapper.insert(tGpPaymentOrder);
		}

		// 保存custId与bankcardno的对应关系，以备后续签约号的通知
		{
			TGpCustidBankcardMap bankCardMap = new TGpCustidBankcardMap();

			bankCardMap.setBankOrderNo(bankOrderNo);
			bankCardMap.setCreatedDate(new Date());

			BeanUtils.copyProperties(firstQuickPayBean, bankCardMap);

			custidBankcardMapper.insert(bankCardMap);
		}

		// ========================== 调用支付网关组装url =========================
		try {
			// 调用支付网关接口，获取签约并支付 URL
			PGFirstQuickPayBean pgFirstQuickPayBean = new PGFirstQuickPayBean();
			pgFirstQuickPayBean.setMerType(MerIdUtils
					.merId2Mertype(firstQuickPayBean.getMerId()));
			pgFirstQuickPayBean.setBankOrderNo(bankOrderNo);
			pgFirstQuickPayBean.setTransDate(transDate);

			BeanUtils.copyProperties(firstQuickPayBean, pgFirstQuickPayBean);

			// 组装并设置内部处理私有域
			this.assembleAndSetPrivObligate2(firstQuickPayBean.getOrderNo(),
					firstQuickPayBean.getOrderDate(), pgFirstQuickPayBean);

			return pgPaymentService.getFirstQuickPayUrl(pgFirstQuickPayBean);
		} catch (HsException e) {
			throw e;
		} catch (RpcException e) {
			logger.error("", e);
			throw new HsException(GPErrorCode.NET_ERROR, "GP支付系统与PG支付网关间通信错误!");
		}
	}

	/**
	 * 获取非首次快捷支付URL地址
	 * 
	 * @param quickPayBean
	 * @param srcSubsysId
	 * @return
	 * @throws HsException
	 */
	public String doGetQuickPayUrl(QuickPayBean quickPayBean, String srcSubsysId)
			throws HsException {
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
			PGQuickPayBean pgQuickPayBean = new PGQuickPayBean();
			pgQuickPayBean.setBankOrderNo(tGpPaymentOrder.getBankOrderNo());
			pgQuickPayBean.setBindingNo(quickPayBean.getBindingNo());
			pgQuickPayBean.setSmsCode(quickPayBean.getSmsCode());
			pgQuickPayBean.setMerType(merType);

			return pgPaymentService.getQuickPayUrl(pgQuickPayBean);
		} catch (HsException e) {
			throw e;
		} catch (RpcException e) {
			logger.error("", e);
			throw new HsException(GPErrorCode.NET_ERROR, "GP支付系统与PG支付网关间通信错误!");
		}
	}

	/**
	 * 获取快捷支付短信验证码
	 * 
	 * @param quickPaySmsCodeBean
	 * @param srcSubsysId
	 * @return 成功 or 失败
	 * @throws HsException
	 */
	public boolean doGetQuickPaySmsCode(
			QuickPaySmsCodeBean quickPaySmsCodeBean, String srcSubsysId)
			throws HsException {
		// 校验请求参数、来源系统id是否有效
		BeanCheck.isvalid(quickPaySmsCodeBean);
		BasicPropertyCheck.checkSrcSubSystemId(srcSubsysId);

		TGpPaymentOrder tGpPaymentOrder = tGpPaymentOrderMapper
				.selectOneBySelective(quickPaySmsCodeBean.getMerId(),
						quickPaySmsCodeBean.getOrderNo());

		// 判断是否已经支付成功或者正在支付
		this.checkOrderSuccess(tGpPaymentOrder);

		String bankOrderNo; // 银行支付单号
		String transAmount; // 交易金额
		Date transDate; // 交易日期

		if (null == tGpPaymentOrder) {
			// 生成银行交易订单号
			bankOrderNo = TimeSecondsSeqWorker.timeNextId16(srcSubsysId);
			transAmount = quickPaySmsCodeBean.getTransAmount();
			transDate = DateUtils.getCurrentDate();

			tGpPaymentOrder = compateOrderData(PGChannelProvider.CHINAPAY);
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

		PGQuickPaySmsCodeBean smsCodeBean = new PGQuickPaySmsCodeBean();
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
			return pgPaymentService.getQuickPaySmsCode(smsCodeBean);
		} catch (HsException e) {
			logger.error("调用支付网关获取快捷支付手机验证码失败: ", e);
			throw e;
		} catch (RpcException e) {
			logger.error("调用支付网关获取快捷支付手机验证码失败: ", e);
			throw new HsException(GPErrorCode.NET_ERROR, "GP支付系统与PG支付网关间通信错误!");
		}
	}

	/**
	 * 获取手机移动支付TN码
	 * 
	 * @param mobilePayBean
	 * @return TN交易流水号
	 * @throws HsException
	 */
	public String doGetMobilePayTn(MobilePayBean mobilePayBean,
			String srcSubsysId) throws HsException {
		// 校验请求参数、来源系统id是否有效
		BeanCheck.isvalid(mobilePayBean);
		BasicPropertyCheck.checkSrcSubSystemId(srcSubsysId);

		// 判断是否已经支付成功或者正在支付
		this.checkOrderSuccess(mobilePayBean.getOrderNo(),
				mobilePayBean.getMerId());

		// 生成银行交易订单号
		String bankOrderNo = TimeSecondsSeqWorker.timeNextId16(srcSubsysId);

		// 交易日期, 重要：支付系统和支付网关必须一致
		Date transDate = new Date();

		// 保存支付单数据至数据库
		{
			BigDecimal transAmount = new BigDecimal(
					mobilePayBean.getTransAmount());

			TGpPaymentOrder tGpPaymentOrder = compateOrderData(PGChannelProvider.CHINAPAY);
			tGpPaymentOrder.setBankOrderNo(bankOrderNo);
			tGpPaymentOrder.setTransAmount(transAmount);
			tGpPaymentOrder.setPayChannel(PayChannel.UPMP);
			tGpPaymentOrder.setSrcSubsysId(srcSubsysId);
			tGpPaymentOrder.setTransDate(transDate);

			BeanUtils.copyProperties(mobilePayBean, tGpPaymentOrder);

			tGpPaymentOrderMapper.insert(tGpPaymentOrder);
		}

		try {
			// 调用支付网关接口，获取交易流水号
			PGMobilePayTnBean mobilePayTnBean = new PGMobilePayTnBean();
			mobilePayTnBean.setBankOrderNo(bankOrderNo);
			mobilePayTnBean.setMerType(MerIdUtils.merId2Mertype(mobilePayBean
					.getMerId()));
			mobilePayTnBean.setTransDate(transDate);

			BeanUtils.copyProperties(mobilePayBean, mobilePayTnBean);

			return pgPaymentService.getMobilePayTn(mobilePayTnBean);
		} catch (HsException e) {
			throw e;
		} catch (RpcException e) {
			logger.error("", e);
			throw new HsException(GPErrorCode.NET_ERROR, "GP支付系统与PG支付网关间通信错误!");
		}
	}

}