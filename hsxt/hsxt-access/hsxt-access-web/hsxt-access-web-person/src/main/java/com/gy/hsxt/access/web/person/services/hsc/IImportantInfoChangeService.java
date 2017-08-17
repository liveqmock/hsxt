/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.services.hsc;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.entstatus.PerChangeInfo;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-person
 * @package       : com.gy.hsxt.access.web.person.services.hsc
 * @className     : IImportantInfoChangeService.java
 * @description   : 重要信息变更接口
 * @author        : maocy
 * @createDate    : 2016-01-19
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public interface IImportantInfoChangeService extends IBaseService {
    
    /**
     * 
     * 方法名称：查询重要信息变更
     * 方法描述：依据客户号查询重要信息变更
     * @param custId 客户编号
     * @return
     * @throws HsException
     */
	public PerChangeInfo queryPerChangeByCustId(String custId);
    
    /**
     * 
     * 方法名称：创建重要信息变更
     * 方法描述：创建重要信息变更
     * @param perChangeInfo 重要信息变更对象
     * @throws HsException
     */
	public void createPerChange(PerChangeInfo info) throws HsException;
	
	/**
     * 
     * 方法名称：修改重要信息变更
     * 方法描述：修改重要信息变更
     * @param perChangeInfo 重要信息变更对象
     * @throws HsException
     */
	public void modifyPerChange(PerChangeInfo info) throws HsException;

}
