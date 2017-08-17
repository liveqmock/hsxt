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
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.RpcException;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gp.api.IGPNotifyBingNoService;
import com.gy.hsxt.gp.api.IGPPaymentService;
import com.gy.hsxt.gp.bean.FirstQuickPayBean;
import com.gy.hsxt.gp.bean.MobilePayBean;
import com.gy.hsxt.gp.bean.NetPayBean;
import com.gy.hsxt.gp.bean.PaymentOrderState;
import com.gy.hsxt.gp.bean.QuickPayBean;
import com.gy.hsxt.gp.bean.QuickPayCardBindingBean;
import com.gy.hsxt.gp.bean.QuickPaySmsCodeBean;
import com.gy.hsxt.gp.common.utils.DateUtils;
import com.gy.hsxt.gp.common.utils.MerIdUtils;
import com.gy.hsxt.gp.common.utils.MoneyAmountHelper;
import com.gy.hsxt.gp.common.utils.StringUtils;
import com.gy.hsxt.gp.common.utils.TimeSecondsSeqWorker;
import com.gy.hsxt.gp.constant.GPConstant.GPErrorCode;
import com.gy.hsxt.gp.constant.GPConstant.PayChannel;
import com.gy.hsxt.gp.mapper.TGpCustidBankcardnoMappingMapper;
import com.gy.hsxt.gp.mapper.TGpPaymentOrderMapper;
import com.gy.hsxt.gp.mapper.vo.TGpCustidBankcardnoMapping;
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

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.service
 * 
 *  File Name       : GPPaymentService.java
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
@Service("gpPaymentService")
public class GPPaymentService extends ParentPaymentService implements
		IGPPaymentService {
	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private TGpPaymentOrderMapper tGpPaymentOrderMapper;

	@Autowired
	private TGpCustidBankcardnoMappingMapper custidBankcardnoMapper;
	
	@Autowired
	private IPGPaymentService pgPaymentService;

	@Autowired
	private IGPNotifyBingNoService gpNotifyBingNoService;
	
	@Autowired
	private ISequenceService sequenceService;

	/**
	 * 获取网银支付URL
	 * 
	 * @param netPayBean
	 * @param srcSubsysId
	 * @return url地址
	 * @throws HsException
	 */
	@Override
	public String getNetPayUrl(NetPayBean netPayBean, String srcSubsysId)
			throws HsException {
		try {
			return this.doGetNetPayUrl(netPayBean, srcSubsysId);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(GPErrorCode.INNER_ERROR, e.getMessage());
		}
	}
	
	/**
	 * 获取快捷支付银行卡签约的URL
	 * 
	 * @param cardBindingBean
	 * @return
	 */
	@Override
	public String getQuickPayCardBindingUrl(
			QuickPayCardBindingBean cardBindingBean) {
		try {
			return this.doGetQuickPayCardBindingUrl(cardBindingBean);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(GPErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 获取快捷支付URL(首次)
	 * 
	 * @param quickPayBean
	 * @param srcSubsysId
	 * @return 首次快捷支付URL地址
	 * @throws HsException
	 */
	@Override
	public String getFirstQuickPayUrl(FirstQuickPayBean firstQuickPayBean,
			String srcSubsysId) throws HsException {
		try {
			return this.doGetFirstQuickPayUrl(firstQuickPayBean, srcSubsysId);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(GPErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 获取非首次快捷支付URL地址
	 * 
	 * @param quickPayBean
	 * @param srcSubsysId
	 * @return 非首次快捷支付URL地址
	 * @throws HsException
	 */
	@Override
	public String getQuickPayUrl(QuickPayBean quickPayBean, String srcSubsysId)
			throws HsException {
		try {
			return this.doGetQuickPayUrl(quickPayBean, srcSubsysId);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(GPErrorCode.INNER_ERROR, e.getMessage());
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
	@Override
	public boolean getQuickPaySmsCode(QuickPaySmsCodeBean quickPaySmsCodeBean,
			String srcSubsysId) throws HsException {
		try {
			return this.doGetQuickPaySmsCode(quickPaySmsCodeBean, srcSubsysId);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(GPErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 获取手机移动支付TN码
	 * 
	 * @param mobilePayBean
	 * @param srcSubsysId
	 * @return TN交易流水号
	 * @throws HsException
	 */
	@Override
	public String getMobilePayTn(MobilePayBean mobilePayBean, String srcSubsysId)
			throws HsException {
		try {
			return this.doGetMobilePayTn(mobilePayBean, srcSubsysId);
		} catch (HsException e) {
			logger.error("", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new HsException(GPErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 支付结果查询
	 * 
	 * @param merId
	 *            商户号
	 * @param orderNo
	 *            业务订单号
	 * @return
	 * @throws HsException
	 */
	@Override
	public PaymentOrderState getPaymentOrderState(String merId, String orderNo)
			throws HsException {
		// 校验商户号
		BasicPropertyCheck.checkMerId(merId);

		// 校验订单号
		BasicPropertyCheck.checkOrderNo(orderNo);

		return getPaymentOrderState(merId,
				Arrays.asList(new String[] { orderNo })).get(0);
	}

	/**
	 * 支付结果查询[批量]
	 * 
	 * @param merId
	 *            商户号
	 * @param orderNoList
	 *            业务订单号列表
	 * @return
	 * @throws HsException
	 */
	@Override
	public List<PaymentOrderState> getPaymentOrderState(String merId,
			List<String> orderNoList) throws HsException {

		// 校验商户号
		BasicPropertyCheck.checkMerId(merId);

		// 校验业务订单号
		BasicPropertyCheck.checkOrderNoList(orderNoList);

		TGpPaymentOrder paymentOrder;

		List<TGpPaymentOrder> validOrderList = new ArrayList<TGpPaymentOrder>(
				10);

		try {
			for (String orderNo : orderNoList) {
				paymentOrder = tGpPaymentOrderMapper.selectOneBySelective(
						merId, orderNo);

				if (null != paymentOrder) {
					validOrderList.add(paymentOrder);
				}
			}

			// GP不存在该支付单
			if (0 >= validOrderList.size()) {
				String orderNoListStr = orderNoList.toString().replaceAll(
						"\\[|\\]", "");

				throw new HsException(GPErrorCode.DATA_NOT_EXIST,
						StringUtils.joinString("要查询的记录不存在! ", " merId=", merId,
								", orderNo=", orderNoListStr));
			}

			// 从支付网关同步支付状态
			return syncPaymentOrderStateFromPG(merId, validOrderList);
		} catch (HsException e) {
			logger.error("", e);

			throw e;
		} catch (Exception e) {
			logger.error("查询支付单状态失败: ", e);

			throw new HsException(GPErrorCode.INNER_ERROR, e.getMessage());
		}
	}

	/**
	 * 获取网银支付url逻辑
	 * 
	 * @param netPayBean
	 * @param srcSubsysId
	 * @return
	 * @throws HsException
	 */
	private String doGetNetPayUrl(NetPayBean netPayBean, String srcSubsysId)
			throws HsException {
		// 校验请求参数是否有效
		BeanCheck.isvalid(netPayBean);

		// 判断来源系统id是否有效
		BasicPropertyCheck.checkSrcSubSystemId(srcSubsysId);

		// 判断是否已经支付成功或者正在支付
		this.checkOrderSuccess(netPayBean.getOrderNo(), netPayBean.getMerId());

		// 生成银行交易订单号
		String bankOrderNo = TimeSecondsSeqWorker.timeNextId16(srcSubsysId);

		// 交易日期, 重要：支付系统和支付网关必须一致
		Date transDate = new Date();

		// 组装支付单数据
		try {
			TGpPaymentOrder tGpPaymentOrder = compateOrderData();

			BeanUtils.copyProperties(netPayBean, tGpPaymentOrder);

			tGpPaymentOrder.setBankOrderNo(bankOrderNo);
			tGpPaymentOrder.setTransAmount(new BigDecimal(netPayBean
					.getTransAmount()));
			tGpPaymentOrder.setPayChannel(PayChannel.B2C);
			tGpPaymentOrder.setSrcSubsysId(srcSubsysId);
			tGpPaymentOrder.setTransDate(transDate);

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

			BeanUtils.copyProperties(netPayBean, pgNetPayBean);

			pgNetPayBean.setBankOrderNo(bankOrderNo);
			pgNetPayBean.setMerType(MerIdUtils.merId2Mertype(netPayBean
					.getMerId()));
			pgNetPayBean.setTransDate(transDate);

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
	private String doGetQuickPayCardBindingUrl(
			QuickPayCardBindingBean cardBindingBean) throws HsException {
		// 校验请求参数是否有效
		BeanCheck.isvalid(cardBindingBean);

		// 生成银行交易订单号
		String requestId = sequenceService.getNextSeq4UpopBinding();
		
		// 保存custId与bankcardno的对应关系，以备后续签约号的通知
		{
			TGpCustidBankcardnoMapping bankCardMap = new TGpCustidBankcardnoMapping();

			BeanUtils.copyProperties(cardBindingBean, bankCardMap);

			bankCardMap.setBankOrderNo(requestId);
			bankCardMap.setCreatedDate(new Date());

			custidBankcardnoMapper.insert(bankCardMap);
		}
				
		// ========================== 调用支付网关组装url =========================
		try {
			// 调用支付网关接口，获取银行卡签约 URL
			PGQuickPayCardBindingBean pgCardBindingBean = new PGQuickPayCardBindingBean();

			{
				BeanUtils.copyProperties(cardBindingBean, pgCardBindingBean);

				pgCardBindingBean.setMerType(MerIdUtils
						.merId2Mertype(cardBindingBean.getMerId()));
				pgCardBindingBean.setRequestId(requestId);
			}

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
	private String doGetFirstQuickPayUrl(FirstQuickPayBean firstQuickPayBean,
			String srcSubsysId) throws HsException {
		// 校验请求参数是否有效
		BeanCheck.isvalid(firstQuickPayBean);

		// 判断来源系统id是否有效
		BasicPropertyCheck.checkSrcSubSystemId(srcSubsysId);

		// 判断是否已经支付成功或者正在支付
		this.checkOrderSuccess(firstQuickPayBean.getOrderNo(),
				firstQuickPayBean.getMerId());

		// 废弃! 调用网关接口判断银行卡号是否已经签约过, marked by:zhangysh
		// this.checkIfBindingAndNotify(firstQuickPayBean);

		// 生成银行交易订单号
		String bankOrderNo = TimeSecondsSeqWorker.timeNextId16(srcSubsysId);

		// 交易日期, 重要：支付系统和支付网关必须一致
		Date transDate = new Date();

		// 保存支付单数据至数据库
		{
			TGpPaymentOrder tGpPaymentOrder = compateOrderData();

			BeanUtils.copyProperties(firstQuickPayBean, tGpPaymentOrder);

			tGpPaymentOrder.setBankOrderNo(bankOrderNo);
			tGpPaymentOrder.setTransAmount(new BigDecimal(firstQuickPayBean
					.getTransAmount()));
			tGpPaymentOrder.setPayChannel(PayChannel.UPOP);
			tGpPaymentOrder.setSrcSubsysId(srcSubsysId);
			tGpPaymentOrder.setTransDate(transDate);

			tGpPaymentOrderMapper.insert(tGpPaymentOrder);
		}

		// 保存custId与bankcardno的对应关系，以备后续签约号的通知
		{
			TGpCustidBankcardnoMapping bankCardMap = new TGpCustidBankcardnoMapping();
			
			BeanUtils.copyProperties(firstQuickPayBean, bankCardMap);

			bankCardMap.setBankOrderNo(bankOrderNo);
			bankCardMap.setCreatedDate(new Date());

			custidBankcardnoMapper.insert(bankCardMap);
		}

		// ========================== 调用支付网关组装url =========================
		try {
			// 调用支付网关接口，获取签约并支付 URL
			PGFirstQuickPayBean pgFirstQuickPayBean = new PGFirstQuickPayBean();

			{
				BeanUtils
						.copyProperties(firstQuickPayBean, pgFirstQuickPayBean);

				pgFirstQuickPayBean.setMerType(MerIdUtils
						.merId2Mertype(firstQuickPayBean.getMerId()));
				pgFirstQuickPayBean.setBankOrderNo(bankOrderNo);
				pgFirstQuickPayBean.setTransDate(transDate);

				// 组装并设置内部处理私有域
				this.assembleAndSetPrivObligate2(
						firstQuickPayBean.getOrderNo(),
						firstQuickPayBean.getOrderDate(), pgFirstQuickPayBean);
			}

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
	private String doGetQuickPayUrl(QuickPayBean quickPayBean,
			String srcSubsysId) throws HsException {
		// 校验请求参数是否有效
		BeanCheck.isvalid(quickPayBean);

		// 判断来源系统id是否有效
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
			pgQuickPayBean.setMerType(merType);
			pgQuickPayBean.setSmsCode(quickPayBean.getSmsCode());

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
	private boolean doGetQuickPaySmsCode(
			QuickPaySmsCodeBean quickPaySmsCodeBean, String srcSubsysId)
			throws HsException {
		// 校验请求参数是否有效
		BeanCheck.isvalid(quickPaySmsCodeBean);

		// 判断来源系统id是否有效
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

			tGpPaymentOrder = compateOrderData();

			BeanUtils.copyProperties(quickPaySmsCodeBean, tGpPaymentOrder);

			tGpPaymentOrder.setBankOrderNo(bankOrderNo);
			tGpPaymentOrder.setTransAmount(new BigDecimal(transAmount));
			tGpPaymentOrder.setTransDate(transDate);
			tGpPaymentOrder.setPayChannel(PayChannel.UPOP);
			tGpPaymentOrder.setSrcSubsysId(srcSubsysId);

			// 保存支付单数据至数据库
			tGpPaymentOrderMapper.insert(tGpPaymentOrder);
		} else {
			bankOrderNo = tGpPaymentOrder.getBankOrderNo();
			transAmount = tGpPaymentOrder.getTransAmount().toString();
			transDate = tGpPaymentOrder.getTransDate();
		}

		PGQuickPaySmsCodeBean smsCodeBean = new PGQuickPaySmsCodeBean();

		{
			BeanUtils.copyProperties(quickPaySmsCodeBean, smsCodeBean);

			smsCodeBean.setMerType(MerIdUtils.merId2Mertype(quickPaySmsCodeBean
					.getMerId()));
			smsCodeBean.setBankOrderNo(bankOrderNo);
			smsCodeBean.setTransAmount(MoneyAmountHelper
					.formatAmount(transAmount));
			smsCodeBean.setTransDate(transDate);

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
	private String doGetMobilePayTn(MobilePayBean mobilePayBean,
			String srcSubsysId) throws HsException {
		// 校验请求参数是否有效
		BeanCheck.isvalid(mobilePayBean);

		// 判断来源系统id是否有效
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
			TGpPaymentOrder tGpPaymentOrder = compateOrderData();

			BeanUtils.copyProperties(mobilePayBean, tGpPaymentOrder);

			tGpPaymentOrder.setBankOrderNo(bankOrderNo);
			tGpPaymentOrder.setTransAmount(new BigDecimal(mobilePayBean
					.getTransAmount()));
			tGpPaymentOrder.setPayChannel(PayChannel.UPMP);
			tGpPaymentOrder.setSrcSubsysId(srcSubsysId);
			tGpPaymentOrder.setTransDate(transDate);

			tGpPaymentOrderMapper.insert(tGpPaymentOrder);
		}

		try {
			// 调用支付网关接口，获取交易流水号
			PGMobilePayTnBean mobilePayTnBean = new PGMobilePayTnBean();

			{
				BeanUtils.copyProperties(mobilePayBean, mobilePayTnBean);

				mobilePayTnBean.setBankOrderNo(bankOrderNo);
				mobilePayTnBean.setMerType(MerIdUtils
						.merId2Mertype(mobilePayBean.getMerId()));
				mobilePayTnBean.setTransDate(transDate);
			}

			return pgPaymentService.getMobilePayTn(mobilePayTnBean);
		} catch (HsException e) {
			throw e;
		} catch (RpcException e) {			
			logger.error("", e);
			
			throw new HsException(GPErrorCode.NET_ERROR, "GP支付系统与PG支付网关间通信错误!");
		}
	}

}
