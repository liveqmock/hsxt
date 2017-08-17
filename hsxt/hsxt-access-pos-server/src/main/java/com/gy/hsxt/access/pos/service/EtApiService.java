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

package com.gy.hsxt.access.pos.service;

import com.gy.hsec.external.bean.QueryParam;
import com.gy.hsec.external.bean.QueryResult;

/** 
 * 
 * @Package: com.gy.hsxt.access.pos.service  
 * @ClassName: EtApiService 
 * @Description: TODO
 *
 * @author: weiyq 
 * @date: 2015-12-15 下午3:55:57 
 * @version V1.0 
 

 */
public interface EtApiService {
	/**
	 * 按订单号查询单笔记录
	 * @param qp
	 * @return
	 */
	public QueryResult queryOrderInfoByOrderId(QueryParam qp) throws Exception;
	
	/**
	 * 按卡号查询订单列表
	 * @param qp
	 * @return
	 */
	public QueryResult queryOrderListByResNo(QueryParam qp) throws Exception;
	
	/**
	 * 订单支付
	 * @param qp
	 * @return
	 */
	public QueryResult netOrderPay(QueryParam qp) throws Exception;
	
	/**
	 * 订单冲正
	 * @param qp
	 * @return
	 */
	public QueryResult correctOrder(QueryParam qp) throws Exception;

}
