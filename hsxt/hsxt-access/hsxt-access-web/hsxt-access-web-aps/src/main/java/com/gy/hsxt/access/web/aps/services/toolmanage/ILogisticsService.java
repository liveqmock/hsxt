/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.toolmanage;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tool.ShippingMethod;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.toolmanage
 * @className     : ILogisticsService.java
 * @description   : 物流配送信息
 * @author        : maocy
 * @createDate    : 2016-01-18
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public interface ILogisticsService extends IBaseService {
	
    /**
     * 
     * 方法名称：添加配送方式
     * 方法描述：物流配送信息-添加配送方式
     * @param bean 配送方式信息
     * @throws HsException
     */
	public void addShipping(ShippingMethod bean)throws HsException;
	
    /**
     * 
     * 方法名称：修改配送方式
     * 方法描述：物流配送信息-修改配送方式
     * @param bean 配送方式信息
     * @throws HsException
     */
	public void modifyShipping(ShippingMethod bean)throws HsException;
	
    /**
     * 
     * 方法名称：删除配送方式
     * 方法描述：物流配送信息-删除配送方式
     * @param smId 配送方式编号
     * @throws HsException
     */
	public void removeShipping(String smId)throws HsException;

}
