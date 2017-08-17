/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.services.systemBusiness.imp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.access.web.company.services.systemBusiness.ISysBusOrderService;
import com.gy.hsxt.bs.api.annualfee.IBSAnnualFeeService;
import com.gy.hsxt.bs.api.order.IBSOrderService;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeOrder;
import com.gy.hsxt.bs.bean.order.OrderCustom;
import com.gy.hsxt.bs.bean.order.OrderQueryParam;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 系统业务订单查询 service接口实现类
 * 
 * @Package: com.gy.hsxt.access.web.company.services.systemBusiness.imp
 * @ClassName: SysBusOrderServiceImpl
 * @Description: TODO
 * 
 * @author: zhangcy
 * @date: 2015-10-22 下午6:45:01
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
@Service
public class SysBusOrderServiceImpl extends BaseServiceImpl implements ISysBusOrderService {

	@Autowired
	private IBSOrderService ibSOrderService;

	@Autowired
	private IBSAnnualFeeService ibSAnnualFeeService;

	@Override
	public PageData<OrderCustom> getOrderList(OrderQueryParam orderQueryParam, Page page) throws HsException
	{
		try
		{
			return ibSOrderService.getOrderList(orderQueryParam, page);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "getOrderList", "调用BS查询系统订单失败", ex);
		}
		return null;
	}

	@Override
	public PageData<OrderCustom> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException
	{
		OrderQueryParam param = new OrderQueryParam();
		/** 客户号 **/
		param.setHsCustId((String) filterMap.get("entCustId"));
		/** 结束时间 **/
		param.setEndDate((String) filterMap.get("endDate"));
		/** 开始时间 **/
		param.setStartDate((String) filterMap.get("beginDate"));
		/** 支付方式 **/
		param.setPayChannel(CommonUtils.toInteger(filterMap.get("payChannel")));
		/** 订单类型 **/
		param.setOrderType(OrderType.ANNUAL_FEE.getCode());
		return getOrderList(param, page);
	}

	/**
	 * 获取年费订单详情
	 * 
	 * @Description:
	 * @param orderNo
	 * @return
	 */
	@Override
	public AnnualFeeOrder queryAnnualFeeOrderDetail(String orderNo)
	{
		try
		{
			return ibSAnnualFeeService.queryAnnualFeeOrder(orderNo);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryAnnualFeeOrderDetail", "调用BS获取年费订单详情失败", ex);
		}
		return null;
	}
}
