/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.services.companyInfo.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.scs.services.companyInfo.IBankAccountInfoService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.JsonUtil;
import com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;

@Service
public class BankAccountInfoServiceImpl extends BaseServiceImpl implements IBankAccountInfoService {
    @Autowired
    private IUCAsBankAcctInfoService baService;

    @Override
    public void addBankCard(AsBankAcctInfo bcInfo,String custType) throws HsException {
        System.out.println("添加银行帐户:"+JsonUtil.createJsonString(bcInfo));
        baService.bindBank(bcInfo, custType);
    }

    @Override
    public void delBankCard(String bankCardNo, String custType) throws HsException {
        System.out.println("删除银行帐户:" + bankCardNo);
        baService.unBindBank(Long.parseLong(bankCardNo), custType);
    }


    @Override
    public List<AsBankAcctInfo> findBankAccountList(String custId,String type) throws HsException {
        
        List<AsBankAcctInfo> list =  baService.listBanksByCustId(custId, type);
        
        return list;
    }

}
