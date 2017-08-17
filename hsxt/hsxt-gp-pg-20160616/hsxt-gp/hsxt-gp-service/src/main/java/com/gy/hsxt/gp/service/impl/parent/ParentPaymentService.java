/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.service.impl.parent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gp.bean.PaymentOrderState;
import com.gy.hsxt.gp.common.constant.ConfigConst;
import com.gy.hsxt.gp.common.utils.DateUtils;
import com.gy.hsxt.gp.common.utils.MerIdUtils;
import com.gy.hsxt.gp.common.utils.TimeSecondsSeqWorker;
import com.gy.hsxt.gp.constant.GPConstant.GPErrorCode;
import com.gy.hsxt.gp.constant.GPConstant.GPTransType;
import com.gy.hsxt.gp.constant.GPConstant.PaymentStateCode;
import com.gy.hsxt.gp.mapper.TGpPaymentOrderMapper;
import com.gy.hsxt.gp.mapper.vo.TGpPaymentOrder;
import com.gy.hsxt.pg.api.IPGPaymentService;
import com.gy.hsxt.pg.bean.PGCallbackJumpRespData;
import com.gy.hsxt.pg.bean.PGPaymentOrderState;
import com.gy.hsxt.pg.bean.parent.PGParentBean;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.service.impl.parent
 * 
 *  File Name       : ParentPaymentService.java
 * 
 *  Creation Date   : 2016年6月2日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : none
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public abstract class ParentPaymentService {

	private int expiredtime = HsPropertiesConfigurer
			.getPropertyIntValue(ConfigConst.EXPIRED_DATE);

	@Autowired
	private TGpPaymentOrderMapper tGpPaymentOrderMapper;

	@Autowired
	private IPGPaymentService pgPaymentService;

	/**
	 * 组装数据
	 * 
	 * @return
	 */
	protected TGpPaymentOrder compateOrderData() {
		
		// 生成支付交易流水号
		String transSeqId = TimeSecondsSeqWorker.timeNextId32();

		// 当前时间
		Date currentDate = DateUtils.getCurrentDate();

		// 保存支付单数据至数据库
		TGpPaymentOrder tGpPaymentOrder = new TGpPaymentOrder();
		tGpPaymentOrder.setTransSeqId(transSeqId);
		tGpPaymentOrder.setTransStatus(PaymentStateCode.READY);
		tGpPaymentOrder.setTransType(GPTransType.PAYMENT);

		Date expiredDate = DateUtils.addMinutes(currentDate, expiredtime);

		tGpPaymentOrder.setCreatedDate(currentDate);
		tGpPaymentOrder.setExpiredDate(expiredDate);

		return tGpPaymentOrder;
	}

	/**
	 * 从支付网关查询支付单交易状态
	 * 
	 * @param merId
	 * @param paymentOrderList
	 * @return
	 */
	protected List<PaymentOrderState> syncPaymentOrderStateFromPG(String merId,
			List<TGpPaymentOrder> paymentOrderList) {

		String bankOrderNo;
		String failReason;

		// 银行支付单号列表
		List<String> bankOrderNoList = new ArrayList<String>(
				paymentOrderList.size());

		// Map<bankOrderNo, TGpPaymentOrder>
		Map<String, TGpPaymentOrder> gpPaymentOrdersMap = new HashMap<String, TGpPaymentOrder>(
				paymentOrderList.size());

		for (TGpPaymentOrder paymentOrder : paymentOrderList) {
			bankOrderNo = paymentOrder.getBankOrderNo();
			bankOrderNoList.add(bankOrderNo);
			gpPaymentOrdersMap.put(bankOrderNo, paymentOrder);
		}

		// 从PG支付网关查询支付单状态
		List<PGPaymentOrderState> paymentOrderStatesFromPG = pgPaymentService
				.getPaymentOrderState(MerIdUtils.merId2Mertype(merId),
						bankOrderNoList);

		if (null != paymentOrderStatesFromPG) {
			TGpPaymentOrder gpPaymentOrder;

			for (PGPaymentOrderState pgOrderState : paymentOrderStatesFromPG) {
				bankOrderNo = pgOrderState.getBankOrderNo();
				failReason = pgOrderState.getFailReason();

				int transStatusInPg = pgOrderState.getTransStatus();

				gpPaymentOrder = gpPaymentOrdersMap.get(bankOrderNo);

				// 判断是否GP、PG状态一致, 如果不一致, 以支付网关为准
				if ((null != gpPaymentOrder)
						&& (gpPaymentOrder.getTransStatus() != transStatusInPg)) {
					gpPaymentOrder.setTransStatus(transStatusInPg);
					gpPaymentOrder.setFailReason(failReason);

					tGpPaymentOrderMapper
							.updateStatusByBankOrderNo(gpPaymentOrder); // 更新支付系统支付单状态
				}
			}
		}

		return this.convert2PaymentOrderState(paymentOrderList);
	}

	/**
	 * 组装网银支付内部处理私有域
	 * 
	 * @param orderNo
	 * @param orderDate
	 * @param pgPayBean
	 */
	protected void assembleAndSetPrivObligate2(String orderNo, Date orderDate,
			PGParentBean pgPayBean) {
		// 订单日期
		String _orderDate = DateUtils.dateToString(orderDate,
				"yyyy-MM-dd HH:mm:ss");

		PGCallbackJumpRespData respData = new PGCallbackJumpRespData();
		respData.setOrderNo(orderNo);
		respData.setOrderDate(_orderDate);

		pgPayBean.setPrivObligate2(JSON.toJSONString(respData));
	}

	/**
	 * 判断是否已经支付成功或者正在支付
	 * 
	 * @param orderNo
	 * @param merId
	 * @throws HsException
	 */
	protected void checkOrderSuccess(String orderNo, String merId)
			throws HsException {

		// 交易状态
		Integer transStatus = tGpPaymentOrderMapper.selectOneBySucHanding(
				merId, orderNo);

		checkOrderSuccess(orderNo, merId, transStatus);
	}

	/**
	 * 判断是否已经支付成功或者正在支付
	 * 
	 * @param tGpPaymentOrder
	 */
	protected void checkOrderSuccess(TGpPaymentOrder tGpPaymentOrder) {
		
		if(null == tGpPaymentOrder) {
			return;
		}

		String orderNo = tGpPaymentOrder.getOrderNo();
		String merId = tGpPaymentOrder.getMerId();
		Integer transStatus = tGpPaymentOrder.getTransStatus();

		checkOrderSuccess(orderNo, merId, transStatus);
	}

	/**
	 * 判断是否已经支付成功或者正在支付
	 * 
	 * @param orderNo
	 * @param merId
	 * @param transStatus
	 * @throws HsException
	 */
	private void checkOrderSuccess(String orderNo, String merId,
			Integer transStatus) throws HsException {

		if (null == transStatus) {
			return;
		}

		// 判断是否已经支付
		if (PaymentStateCode.PAY_SUCCESS == transStatus) {
			throw new HsException(GPErrorCode.HAS_SUCCESS, "支付单" + orderNo
					+ "已经支付，请不要重复提交");
		}

		// 判断是否正在支付
		if (PaymentStateCode.PAY_HANDLING == transStatus) {
			throw new HsException(GPErrorCode.BE_HANDLING, "支付单" + orderNo
					+ "正在支付，请不要重复提交");
		}
	}

	/**
	 * 转换为PaymentOrderState
	 * 
	 * @param paymentOrderList
	 * @return
	 */
	private List<PaymentOrderState> convert2PaymentOrderState(
			List<TGpPaymentOrder> paymentOrderList) {

		List<PaymentOrderState> paymentOrderStateList = new ArrayList<PaymentOrderState>(
				paymentOrderList.size());

		PaymentOrderState paymentOrderState;

		for (TGpPaymentOrder paymentOrder : paymentOrderList) {
			paymentOrderState = new PaymentOrderState();
			paymentOrderState.setTransAmount(paymentOrder.getTransAmount()
					.toString());

			BeanUtils.copyProperties(paymentOrder, paymentOrderState);

			paymentOrderStateList.add(paymentOrderState);
		}

		return paymentOrderStateList;
	}
}
