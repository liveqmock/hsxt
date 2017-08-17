package com.gy.hsxt.access.web.aps.services.platformProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.annualfee.IBSAnnualFeeService;
import com.gy.hsxt.bs.api.order.IBSOrderService;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeInfo;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeOrder;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.common.exception.HsException;

@Service("annualFeeOrderService")
public class AnnualFeeOrderService extends
		BaseServiceImpl<AnnualFeeOrderService> implements
		IAnnualFeeOrderService {
	@Autowired
	private IBSAnnualFeeService ibsAnnualFeeService;
	@Autowired
	private IBSOrderService ibsOrderService;

	/**
	 * 查询某一个企业年费信息
	 * 
	 * @param entCustId
	 *            :企业客户号
	 * @return
	 * @throws HsException
	 */
	@Override
	public AnnualFeeInfo queryAnnualFeeInfo(String entCustId)
			throws HsException {
		return ibsAnnualFeeService.queryAnnualFeeInfo(entCustId);
	}

	/**
	 * 提交年费业务订单接口
	 * 
	 * @param AnnualFeeOrder
	 *            :年费订单实体
	 * @return
	 * @throws HsException
	 */
	@Override
	public AnnualFeeOrder submitAnnualFeeOrder(AnnualFeeOrder annualFeeOrder)
			throws HsException {
		return ibsAnnualFeeService.submitAnnualFeeOrder(annualFeeOrder);
	}
	/**
	 * 兑换互生币
	 * @param order
	 * @return
	 * @throws HsException
	 */
	@Override
	public void saveBuyHsb(Order order) throws HsException {
		ibsOrderService.saveOrder(order);
	}
}
