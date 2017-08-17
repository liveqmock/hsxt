/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.companyInfo;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.companyinfo
 * @className     : ICompanyInfoService.java
 * @description   : 企业系统信息接口
 * @author        : maocy
 * @createDate    : 2016-01-13
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public interface ICompanyInfoService extends IBaseService {
    
    /**
     * 
     * 方法名称：查询企业信息
     * 方法描述：查询企业信息
     * @param entCustId 企业客户号
     * @throws HsException
     */
	public AsEntExtendInfo findEntAllInfo(String entCustId) throws HsException;
	
	 /**
     * 
     * 方法名称：更新企业信息
     * 方法描述：更新企业信息
     * @param entExtInfo 企业信息
     * @param operator 操作用户id
     * @throws HsException
     */
	public void updateEntExtInfo(AsEntExtendInfo entExtInfo, String operator) throws HsException;
    
}