/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.services.companyInfo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;

/**
 * 
 * @projectName   : hsxt-access-web-scs
 * @package       : com.gy.hsxt.access.web.scs.services.companyinfo
 * @className     : IUCBankfoService.java
 * @description   : 银行账户接口
 * @author        : maocy
 * @createDate    : 2016-01-14
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public interface IUCBankfoService extends IBaseService{
    
    /**
     * 
     * 方法名称：添加银行卡
     * 方法描述：添加银行卡
     * @param acctInfo 银行账户
     * @param userType 用户类型（1：非持卡人 2：持卡人  4：企业）
     * @throws HsException
     */
	public void addBank(AsBankAcctInfo acctInfo, String  userType) throws HsException;
    
    /**
     * 
     * 方法名称：删除银行卡
     * 方法描述：删除银行卡
     * @param accId 银行卡编号
     * @param userType 用户类型（1：非持卡人 2：持卡人  4：企业）
     * @throws HsException
     */
	public void delBank(Long accId, String userType) throws HsException;
    
    /**
     * 
     * 方法名称：查询银行卡
     * 方法描述：查询银行卡
     * @param custId 客户号
     * @param userType 用户类型（1：非持卡人 2：持卡人  4：企业）
     * @return
     */
	public List<AsBankAcctInfo>	findBanksByCustId(String custId,  String userType) throws HsException;
    
}