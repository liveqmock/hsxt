/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.access.pos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsec.external.api.OrderService;
import com.gy.hsec.external.bean.QueryParam;
import com.gy.hsec.external.bean.QueryResult;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.service.EtApiService;

/**
 * 
 * @Package: com.gy.hsxt.access.pos.service.impl
 * @ClassName: EtApiServiceImpl
 * @Description: TODO
 * 
 * @author: weiyq
 * @date: 2015-12-15 下午3:56:34
 * @version V1.0
 */
@Service("etApiService")
public class EtApiServiceImpl implements EtApiService {
	@Autowired
	@Qualifier("orderService")
	private OrderService orderService;

	public QueryResult queryOrderInfoByOrderId(QueryParam qp) throws Exception {
	    SystemLog.debug("EtApiServiceImpl","queryOrderInfoByOrderId()", "电商接口 请求参数 = " + JSON.toJSONString(qp));
	    QueryResult qr = orderService.queryOrderInfoByOrderId(qp);
	    SystemLog.debug("EtApiServiceImpl","queryOrderInfoByOrderId()", "电商接口 返回结果 = " + JSON.toJSONString(qr));
		return qr;
	}
	
	public QueryResult queryOrderListByResNo(QueryParam qp)throws Exception{
	    SystemLog.debug("EtApiServiceImpl","queryOrderListByResNo()", "电商接口 请求参数 = " + JSON.toJSONString(qp));
        QueryResult qr = orderService.queryOrderListByResNo(qp);
        SystemLog.debug("EtApiServiceImpl","queryOrderListByResNo()", "电商接口 返回结果 = " + JSON.toJSONString(qr));
		return qr;
	}
	
	public QueryResult netOrderPay(QueryParam qp)throws Exception{
	    SystemLog.debug("EtApiServiceImpl","netOrderPay()", "电商接口 请求参数 = " + JSON.toJSONString(qp));
        QueryResult qr = orderService.netOrderPay(qp);
        SystemLog.debug("EtApiServiceImpl","netOrderPay()", "电商接口 返回结果 = " + JSON.toJSONString(qr));
		return qr;
	}
	
	public QueryResult correctOrder(QueryParam qp)throws Exception{
	    SystemLog.debug("EtApiServiceImpl","correctOrder()", "电商接口 请求参数 = " + JSON.toJSONString(qp));
        QueryResult qr = orderService.correctOrder(qp);
        SystemLog.debug("EtApiServiceImpl","correctOrder()", "电商接口 返回结果 = " + JSON.toJSONString(qr));
		return qr;
	}
}
