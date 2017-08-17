/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.toolorder;

import java.util.Map;

import com.gy.hsxt.access.web.bean.PrintDetailInfo;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tool.Shipping;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.toolorder
 * @className     : IToolListPrintService.java
 * @description   : 售后工具配送清单打印
 * @author        : maocy
 * @createDate    : 2016-03-09
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
public interface IToolListPrintService extends IBaseService {
	
    /**
     * 
     * 方法名称：查询发货清单
     * 方法描述：售后订单管理-消查询发货清单
     * @param shippingId 发货单编号
     * @return
     */
	public Shipping findShippingAfterById(String shippingId);
	
	/**
     * 
     * 方法名称：售后工具配送列表
     * 方法描述：售后订单管理-售后工具配送列表
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    public PageData<?> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException;
	
    /**
     * 
     * 方法名称：查询售后发货清单列表
     * 方法描述：售后订单管理-售后工具配送清单打印
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    public PageData<?> findPrintListResult(Map filterMap, Map sortMap, Page page) throws HsException;
    
    /**
     * 
     * 方法名称：查询售后发货清单打印信息
     * 方法描述：售后订单管理-售后工具配送清单打印-打印配送单
     * @param shippingId 发货单编号
     * @return
     */
    public PrintDetailInfo findPrintDetailAfterById(String shippingId) throws HsException;
}
