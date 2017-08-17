/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.services.systemBusiness;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeOrder;
import com.gy.hsxt.bs.bean.order.OrderCustom;
import com.gy.hsxt.bs.bean.order.OrderQueryParam;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 系统业务订单查询Service
 * 
 * @Package: com.gy.hsxt.access.web.company.services.systemBusiness
 * @ClassName: ISysBusOrderService
 * @Description: TODO
 * 
 * @author: zhangcy
 * @date: 2015-10-22 下午6:43:35
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
@Service
public interface ISysBusOrderService extends IBaseService {

	/**
	 * 查询业务订单列表
	 * 
	 * @param orderQueryParam
	 * @param page
	 * @return
	 * @throws HsException
	 */
	public PageData<OrderCustom> getOrderList(OrderQueryParam orderQueryParam, Page page) throws HsException;

	/**
	 * 获取年费订单详情
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年2月2日 下午5:36:17
	 * @param orderNo
	 * @return
	 * @return : AnnualFeeOrder
	 * @version V3.0.0
	 */
	public AnnualFeeOrder queryAnnualFeeOrderDetail(String orderNo);
}
