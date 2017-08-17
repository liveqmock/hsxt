/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.services.companyInfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService;
import com.gy.hsxt.uc.as.bean.common.AsQkBank;

/**
 * 
 * @projectName   : hsxt-access-web-scs
 * @package       : com.gy.hsxt.access.web.scs.services.hsc
 * @className     : PayBankService.java
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
public class PayBankService extends BaseServiceImpl implements IPayBankService {
    
    @Autowired
    private IUCAsBankAcctInfoService ucService;//uc银行服务类
    
    private final static String USER_TYPE = "4";//1：非持卡人 2：持卡人  4：企业

    /**
     * 
     * 方法名称：查询绑定的快捷支付银行
     * 方法描述：依据客户号查询绑定的快捷支付银行
     * @param custId 客户编号
     * @return
     * @throws HsException
     */
	public List<AsQkBank> findPayBanksByCustId(String custId) {
		return this.ucService.listQkBanksByCustId(custId, USER_TYPE);
	}
    
    /**
     * 
     * 方法名称：添加快捷支付银行
     * 方法描述：添加快捷支付银行
     * @param bank 快捷支付银行
     * @throws HsException
     */
	public void addPayBank(AsQkBank bank) throws HsException {
		this.ucService.setQkBank(bank, USER_TYPE);
	}
	
	/**
     * 
     * 方法名称：删除快捷支付银行
     * 方法描述：删除快捷支付银行
     * @param accId 银行卡编号
     * @throws HsException
     */
	public void delPayBank(Long accId) throws HsException {
		this.ucService.unBindQkBank(accId, USER_TYPE);
	}
	
}
