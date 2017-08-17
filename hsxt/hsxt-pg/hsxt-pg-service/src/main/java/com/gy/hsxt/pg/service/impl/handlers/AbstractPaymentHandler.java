/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.service.impl.handlers;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;
import com.gy.hsxt.pg.bean.parent.PGParentBean;
import com.gy.hsxt.pg.common.utils.TimeSecondsSeqWorker;
import com.gy.hsxt.pg.constant.PGConstant.PGPaymentStateCode;
import com.gy.hsxt.pg.constant.PGConstant.PGTransStateCode;
import com.gy.hsxt.pg.constant.PGConstant.TransType;
import com.gy.hsxt.pg.mapper.TPgBankPaymentOrderMapper;
import com.gy.hsxt.pg.mapper.vo.TPgBankPaymentOrder;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service.impl.handlers
 * 
 *  File Name       : AbstractPaymentHandler.java
 * 
 *  Creation Date   : 2016年6月29日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 抽象父类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public abstract class AbstractPaymentHandler {

	protected final Logger logger = Logger.getLogger(getClass());

	/** 渠道提供商 */
	protected String channelProvider;

	@Autowired
	private TPgBankPaymentOrderMapper chinapayOrderMapper;

	public AbstractPaymentHandler() {
		channelProvider = getChannelProvider();
	}

	/**
	 * 子类必须实现: 获得渠道提供商名称(即：银行名称)
	 * 
	 * @return
	 */
	protected abstract String getChannelProvider();

	/**
	 * 根据PGMobilePayTnBean 组装数据
	 * 
	 * @param mobilePayTnBean
	 * @return
	 */
	protected TPgBankPaymentOrder compateData(PGParentBean mobilePayTnBean) {
		// 当前时间
		Date currentDate = DateUtils.getCurrentDate();

		// 生成支付交易流水号
		String transSeqId = TimeSecondsSeqWorker.timeNextId32();

		// 交易金额
		BigDecimal transAmount = new BigDecimal(
				mobilePayTnBean.getTransAmount());

		// 组装数据保存支付单数据至数据库
		TPgBankPaymentOrder chinapayOrder = new TPgBankPaymentOrder();
		chinapayOrder.setTransType(TransType.PAYMENT);
		chinapayOrder.setTransStatus(PGPaymentStateCode.READY);
		chinapayOrder.setTransAmount(transAmount);
		chinapayOrder.setOrderSeqId(transSeqId);
		chinapayOrder.setChannelProvider(channelProvider);
		chinapayOrder.setCreatedDate(currentDate);

		BeanUtils.copyProperties(mobilePayTnBean, chinapayOrder);

		return chinapayOrder;
	}

	/**
	 * 根据原订单号生成退款订单信息
	 * 
	 * @param origBankOrderNo
	 * @return
	 */
	protected TPgBankPaymentOrder compateRefundByOrign(String origBankOrderNo) {
		// 当前时间
		Date currentDate = DateUtils.getCurrentDate();

		// 根据原单号查询数据库
		TPgBankPaymentOrder chinapayOrder = chinapayOrderMapper
				.selectByBankOrderNo(origBankOrderNo);

		// 组装退款订单
		TPgBankPaymentOrder refundOrder = new TPgBankPaymentOrder();
		refundOrder.setOrigBankOrderNo(chinapayOrder.getBankOrderNo());
		refundOrder.setTransStatus(PGTransStateCode.READY);
		refundOrder.setTransType(TransType.REFUND);
		refundOrder.setOrderSeqId(TimeSecondsSeqWorker.timeNextId32());
		refundOrder.setChannelProvider(channelProvider);
		refundOrder.setCreatedDate(currentDate);
		
		BeanUtils.copyProperties(chinapayOrder, refundOrder);

		return refundOrder;
	}

	/**
	 * 判断订单是否已经存在
	 * 
	 * @param bankOrderNo
	 * @return
	 */
	protected boolean checkOrderExist(String bankOrderNo) {
		int count = chinapayOrderMapper.isExistBankOrderNo(bankOrderNo);

		return (0 < count);
	}
}
