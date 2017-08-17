/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.testcase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.junit.Test;

import com.gy.hsxt.ac.api.IAccountSearchService;
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ao.api.IAOCurrencyConvertService;
import com.gy.hsxt.ao.bean.HsbToCash;
import com.gy.hsxt.ao.bean.PvToHsb;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.Channel;
import com.gy.hsxt.common.constant.CustType;

/**
 * 货币转换单元测试类
 * 
 * @Package: com.gy.hsxt.ao.testcase
 * @ClassName: CurrencyConvertServiceTest
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-12-17 下午6:07:51
 * @version V3.0.0
 */
public class CurrencyConvertServiceTest extends BaseTest {

    @Resource
    IAOCurrencyConvertService currencyConvertService;

    @Resource
    IAccountSearchService accountSearchService;

    /**
     * 积分转互生币
     */
    @Test
    public void testSavePvToHsb() {
        // PvToHsb pvToHsb = null;
        // for (int i = 0; i < 1; i++)
        // {
        // pvToHsb = new PvToHsb();
        // pvToHsb.setCustId("06002110000164063559693312");
        // pvToHsb.setHsResNo("06002110000");
        // // pvToHsb.setCustId(getCustId());
        // // pvToHsb.setHsResNo(getHsResNo());
        // pvToHsb.setCustName("gyist");
        // // pvToHsb.setCustType(CustType.PERSON.getCode());
        // pvToHsb.setCustType(3);
        // pvToHsb.setAmount("100");
        // pvToHsb.setOptCustId(getOptCustId());
        // pvToHsb.setOptCustName(getOptCustName());
        // pvToHsb.setChannel(Channel.WEB.getCode());
        //
        // currencyConvertService.savePvToHsb(pvToHsb);
        // }
        int count = 100000;
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        for (int i = 0; i < count; i++)
            executorService.execute(new Thread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    PvToHsb pvToHsb = new PvToHsb();
                    pvToHsb.setCustId("06002110000164063559693312");
                    pvToHsb.setHsResNo("06002110000");
                    // pvToHsb.setCustId(getCustId());
                    // pvToHsb.setHsResNo(getHsResNo());
                    pvToHsb.setCustName("gyist");
                    // pvToHsb.setCustType(CustType.PERSON.getCode());
                    pvToHsb.setCustType(3);
                    pvToHsb.setAmount("100");
                    pvToHsb.setOptCustId(getOptCustId());
                    pvToHsb.setOptCustName(getOptCustName());
                    pvToHsb.setChannel(Channel.WEB.getCode());

                    currencyConvertService.savePvToHsb(pvToHsb);
                }
            }));

        executorService.shutdown();
        while (!executorService.isTerminated())
        {
            try
            {
                Thread.sleep(10);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 查询积分转互生币
     */
    @Test
    public void testGetPvToHsbInfo() {
        System.out.println(currencyConvertService.getPvToHsbInfo("130120151127040843000000"));
    }

    /**
     * 保存互生币转货币
     */
    @Test
    public void testSaveHsbToCash() {

        HsbToCash hsbToCash = new HsbToCash();
        // hsbToCash.setCustId(getCustId());
        // hsbToCash.setCustId("06002110000164063559693312");// 企业
        hsbToCash.setCustId("0601000000020151231");// 企业
        // hsbToCash.setHsResNo(getHsResNo());
        hsbToCash.setHsResNo("06010000000");
        // hsbToCash.setHsResNo("06002110000");
        hsbToCash.setCustName("gyist");
        // hsbToCash.setCustType(CustType.MANAGE_CORP.getCode());
        hsbToCash.setCustType(CustType.SERVICE_CORP.getCode());
        // hsbToCash.setCustType(CustType.PERSON.getCode());
        hsbToCash.setToAccType(AccountType.ACC_TYPE_POINT30110.getCode());
        hsbToCash.setFromHsbAmt("100");
        hsbToCash.setOptCustId("06002110000164063559693312");
        hsbToCash.setOptCustName("托管企业-110");
        hsbToCash.setChannel(Channel.MOBILE.getCode());

        // 获取货币帐户余额
        AccountBalance accountBalance = accountSearchService.searchAccBalance(hsbToCash.getCustId(),
                AccountType.ACC_TYPE_POINT20110.getCode());
        System.out.println("转账前：互生币帐户获取帐户余额：" + accountBalance.getAccBalance());
        accountBalance = accountSearchService.searchAccBalance(hsbToCash.getCustId(), AccountType.ACC_TYPE_POINT30110
                .getCode());
        System.out.println("转账前：货币帐户获取帐户余额：" + accountBalance.getAccBalance());
        currencyConvertService.saveHsbToCash(hsbToCash);
        accountBalance = accountSearchService.searchAccBalance(hsbToCash.getCustId(), AccountType.ACC_TYPE_POINT20110
                .getCode());
        System.out.println("转账后：互生币帐户获取帐户余额：" + accountBalance.getAccBalance());
        accountBalance = accountSearchService.searchAccBalance(hsbToCash.getCustId(), AccountType.ACC_TYPE_POINT30110
                .getCode());
        System.out.println("转账后：货币帐户获取帐户余额：" + accountBalance.getAccBalance());
    }

    /**
     * 查询互生币转货币
     */
    @Test
    public void testGetHsbToCashInfo() {
        System.out.println(currencyConvertService.getHsbToCashInfo("130120151127044816000000"));
    }

}
