/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.toolorder;

import java.util.Map;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tool.Shipping;
import com.gy.hsxt.bs.bean.tool.ShippingData;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.toolorder
 * @className     : IToolDispatchingService.java
 * @description   : 售后工具配送
 * @author        : maocy
 * @createDate    : 2016-03-09
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
public interface IToolDispatchingService extends IBaseService {
	
	/**
     * 
     * 方法名称：添加售后服务发货单
     * 方法描述：售后订单管理-添加售后服务发货单
     * @param bean 发货单
     * @return
     */
	public void addToolShippingAfter(Shipping bean) throws HsException;

	/**
     * 
     * 方法名称：查询发货单的数据
     * 方法描述：售后订单管理-查询发货单的数据
     * @param confNo 配置单编号列表
     * @return
     */
	public ShippingData findAfterShipingData(String[] confNo) throws HsException;
	
	/**
     * 
     * 方法名称：分页查询售后工具配送配送单
     * 方法描述：售后订单管理-分页查询售后工具配送配送单
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    public PageData<?> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException;
    
}
