/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.companyInfo.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.aps.services.companyInfo.IBankAccountInfoService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.JsonUtil;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;

@Service
public class BankAccountInfoServiceImpl extends BaseServiceImpl implements IBankAccountInfoService {


    @Override
    public void updateBankAccountInfo(AsBankAcctInfo info) throws HsException {
        System.out.println("修改银行帐户信息:" + JsonUtil.createJsonString(info));

    }

    @Override
    public void addBankCard(AsBankAcctInfo bcInfo) throws HsException {

        System.out.println(JsonUtil.createJsonString(bcInfo));
    }

    @Override
    public void delBankCard(String bankCardNo, String custNo) throws HsException {
        System.out.println("删除银行帐户:" + bankCardNo);
    }

    @Override
    public Object findBankAccountInfo(String cardId) throws HsException {

        Map<String, String> map = new HashMap<>();
        map.put("holderId", "121314");
        map.put("holderName", "小龙女");
        map.put("applyCategory", "支付");
        map.put("applyCurrency", "人民币");

        return map;
    }

    @Override
    public List<AsBankAcctInfo> findBankAccountList(String custId) throws HsException {
        List<AsBankAcctInfo> list = new ArrayList<>();

        for (int i = 0; i < 3; i++)
        {
            AsBankAcctInfo ba = new AsBankAcctInfo();
            ba.setBankAccName("杨过");
            ba.setCityNo("11");
            //ba.setCityName("深圳市");
            ba.setBankCode("1");
            ba.setBankName("中国银行");
            ba.setBankAccNo("4355616561654545" + i);
            ba.setBankBranch("");
            ba.setBankCardType("1");
            if (i == 0)
            {
                ba.setIsDefaultAccount("1");
            }
            else
            {
                ba.setIsDefaultAccount("0");
            }
            ba.setProvinceNo("1");
            //ba.setProvinceName("广东省");
            ba.setCountryNo("1");
            //ba.setCountryName("中国");
            list.add(ba);
        }

        return list;
    }

    @Override
    public Object findBankNameList() throws HsException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object findProvinceList() throws HsException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object findCityList(Object obj) throws HsException {
        // TODO Auto-generated method stub
        return null;
    }

}
