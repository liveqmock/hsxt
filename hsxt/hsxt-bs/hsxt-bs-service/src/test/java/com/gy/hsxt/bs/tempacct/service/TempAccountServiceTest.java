package com.gy.hsxt.bs.tempacct.service;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.tempacct.*;
import com.gy.hsxt.bs.common.enumtype.tempacct.ReceiveAccountType;
import com.gy.hsxt.bs.tempacct.interfaces.IAccountService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package :com.gy.hsxt.bs.tempacct.service
 * @ClassName : TempAccountServiceTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/19 18:10
 * @Version V3.0.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
public class TempAccountServiceTest {

    @Resource
    private IAccountService accountService;

    @Test
    public void testCreateAccountName() throws Exception {
        AccountName accountName = new AccountName();
//        accountName.setCreatedBy("system");
        accountName.setReceiveAccountName("nameThree");
        accountName.setReceiveAccountType(ReceiveAccountType.RECEIVE.getCode());
        accountName.setAbbreviation("noMove");

        accountService.createAccountName(accountName);

    }

    @Test
    public void testQueryAccountNameListForPage() throws Exception {
        Page page = new Page(0, 10);
        AccountNameQuery query = new AccountNameQuery();
        query.setReceiveAccountName("Two");
        PageData<AccountName> pageData = accountService.queryAccountNameListForPage(page, query);
        System.out.println(pageData);
    }

    @Test
    public void testQueryAccountNameDetail() throws Exception {

        AccountName accountName = accountService.queryAccountNameDetail("110120151221093507000000");
        System.out.println(accountName);
    }

    @Test
    public void testModifyAccountName() throws Exception {

        AccountName accountName = new AccountName();
        accountName.setReceiveAccountId("110120151221093507000000");
        accountName.setReceiveAccountName("归一科技");
        accountName.setReceiveAccountType(ReceiveAccountType.NOMOVE.getCode());
        accountName.setUpdatedBy("system");
        accountService.modifyAccountName(accountName);

    }

    @Test
    public void testQueryAccountNameList() throws Exception {

    }

    @Test
    public void testCreateAccountInfo() throws Exception {
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setBankBranchName("深圳中国银行分行");
        accountInfo.setBankName("中国银行");
        accountInfo.setCreatedBy("system");
        accountInfo.setReceiveAccountId("110120151221093507000000");
        accountInfo.setReceiveAccountNo("76656888521123585");
        accountInfo.setBankId("10201");

        accountService.createAccountInfo(accountInfo);

    }

    @Test
    public void testQueryAccountInfoListForPage() throws Exception {
        Page page = new Page(0, 10);
        AccountInfoQuery query = new AccountInfoQuery();
//        query.setReceiveAccountName("One");
        PageData<AccountInfo> pageData = accountService.queryAccountInfoListForPage(page, query);
        System.out.println(pageData);
    }

    @Test
    public void testQueryAccountInfoBean() throws Exception {

    }

    @Test
    public void testModifyAccountInfo() throws Exception {
        String json = "{\"bankBranchName\":\"中国工商银行桂园支行\",\"bankId\":\"004\",\"bankName\":\"中国工商银行\",\"receiveAccountId\":\"110120151221120710000000\",\"receiveAccountInfoId\":\"110120151222142039000000\",\"receiveAccountNo\":\"88888888888889\",\"updatedBy\":\"00000000156163438271051776\"}";

        AccountInfo accountInfo = JSON.parseObject(json, AccountInfo.class);

        accountService.modifyAccountInfo(accountInfo);
    }

    @Test
    public void testQueryAccountInfoDropdownMenu() throws Exception {

        List<AccountOption> options = accountService.queryAccountInfoDropdownMenu();
        System.out.println(options);
    }

    @Test
    public void testForbidAccountInfo() throws Exception {

        accountService.forbidAccountInfo("110120151221150617000000");
    }
}