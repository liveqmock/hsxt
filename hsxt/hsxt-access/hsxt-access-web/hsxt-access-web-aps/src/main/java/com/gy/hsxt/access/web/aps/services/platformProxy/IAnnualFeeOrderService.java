package com.gy.hsxt.access.web.aps.services.platformProxy;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeInfo;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeOrder;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.common.exception.HsException;

public interface IAnnualFeeOrderService extends IBaseService{
	/**
	 * 查询某一个企业年费信息
	 * 
	 * @param entCustId
	 *            :企业客户号
	 * @return
	 * @throws HsException
	 */
	public AnnualFeeInfo queryAnnualFeeInfo(String entCustId) throws HsException;
	/**
	 * 提交年费业务订单接口
	 * 
	 * @param AnnualFeeOrder
	 *            :年费订单实体
	 * @return
	 * @throws HsException
	 */
	public AnnualFeeOrder submitAnnualFeeOrder(AnnualFeeOrder annualFeeOrder)
			throws HsException;
	/**
	 * 兑换互生币
	 * @param order
	 * @return
	 * @throws HsException
	 */
	public void saveBuyHsb(Order order) throws HsException;

}
