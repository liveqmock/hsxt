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
import com.gy.hsxt.uc.as.bean.common.AsQkBank;

/**
 * 
 * @projectName   : hsxt-access-web-scs
 * @package       : com.gy.hsxt.access.web.scs.services.hsc
 * @className     : IPayBankService.java
 * @description   : 快捷支付接口
 * @author        : maocy
 * @createDate    : 2016-01-28
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public interface IPayBankService extends IBaseService {
    
	/**
     * 
     * 方法名称：查询绑定的快捷支付银行
     * 方法描述：依据客户号查询绑定的快捷支付银行
     * @param custId 客户编号
     * @return
     * @throws HsException
     */
	public List<AsQkBank> findPayBanksByCustId(String custId);
    
    /**
     * 
     * 方法名称：添加快捷支付银行
     * 方法描述：添加快捷支付银行
     * @param bank 快捷支付银行
     * @throws HsException
     */
	public void addPayBank(AsQkBank bank) throws HsException;
	
	/**
     * 
     * 方法名称：删除快捷支付银行
     * 方法描述：删除快捷支付银行
     * @param accId 银行卡编号
     * @throws HsException
     */
	public void delPayBank(Long accId) throws HsException;

}
