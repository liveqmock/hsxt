/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.pos.service.BsApiService;
import com.gy.hsxt.bs.api.order.IBSOrderService;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.common.exception.HsException;
		
/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.service.impl  
 * @ClassName: BsApiServiceImpl 
 * @Description: 
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:13:33 
 * @version V1.0
 */
@Service("bsApiService")
public class BsApiServiceImpl implements BsApiService {
	
	@Autowired
	@Qualifier("bsOrderService")
	private IBSOrderService bsOrderService;

	@Override
	public String saveOrder(Order order, String jumpUrl) throws HsException {

//		return bsOrderService.saveOrder(order, jumpUrl);
	    return null;
	}

}

	