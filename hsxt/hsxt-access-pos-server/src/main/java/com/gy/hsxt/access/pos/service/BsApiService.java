/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.service;

import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.common.exception.HsException;
		
/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.service  
 * @ClassName: BsApiService 
 * @Description: 业务类接口封装
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:14:02 
 * @version V1.0
 */
public interface BsApiService {
	
	
	
	/**
	 * 适用于兑换互生币，实物订单由所负责该模块的人提供接口
	 * @author   wucl	
	 * 2015-11-9 下午5:10:19
	 * @param order
	 * @param jumpUrl
	 * @return
	 * @throws HsException
	 */
	public String saveOrder(Order order, String jumpUrl) throws HsException;

}

	