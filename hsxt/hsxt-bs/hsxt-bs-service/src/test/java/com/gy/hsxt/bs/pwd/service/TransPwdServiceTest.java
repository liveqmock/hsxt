package com.gy.hsxt.bs.pwd.service;/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

import com.gy.hsxt.bs.bean.pwd.ResetTransPwd;
import com.gy.hsxt.bs.bean.pwd.TransPwdQuery;
import com.gy.hsxt.bs.common.enumtype.ApprStatus;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.CustType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @Package : com.gy.hsxt.bs.pwd.service
 * @ClassName : TransPwdServiceTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2016/1/21 20:12
 * @Version V3.0.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
public class TransPwdServiceTest {

    @Resource
    private TransPwdService transPwdService;

    @Test
    public void testCreateResetTransPwdApply() throws Exception {
        ResetTransPwd pwd = new ResetTransPwd();
        pwd.setApplyPath("applyPath1");
        pwd.setApplyReason("applyReason1");
        pwd.setCreatedby("00000000156163438271051776");
        pwd.setLinkman("李杨浩");
        pwd.setMobile("158888888");
        pwd.setEntResNo("06001020000");
        pwd.setEntCustId("0600102000020151215");
        pwd.setEntCustName("托管企业-创业");
        pwd.setCustType(CustType.TRUSTEESHIP_ENT.getCode());
        try {
            transPwdService.createResetTransPwdApply(pwd);
            System.out.println("操作成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testQueryResetTransPwdApplyListPage() throws Exception {

    }

    @Test
    public void testQueryTaskListPage() throws Exception {
        //00000000156000220160105
        Page page = new Page(1, 10);
        TransPwdQuery transPwdQuery = new TransPwdQuery();
        transPwdQuery.setOptCustId("00000000156000220160105");

        PageData<ResetTransPwd> pageList = transPwdService.queryTaskListPage(page, transPwdQuery);
        for (ResetTransPwd ent : pageList.getResult()) {
            System.out.println(ent.getApplyId());
            System.out.println(ent.getMobile());
            System.out.println(ent.getLinkman());
        }
    }

    @Test
    public void testGetResetTransPwdApplyBean() throws Exception {

    }

    @Test
    public void testApprResetTranPwdApply() throws Exception {
        ResetTransPwd resetTransPwd = new ResetTransPwd();
        resetTransPwd.setApplyId("110120160121202909000000");
        resetTransPwd.setUpdatedby("0000000015600022016010511");
        resetTransPwd.setStatus(ApprStatus.PASS.getCode());
        resetTransPwd.setEntCustId("0600102000020151215");

        try
        {
            transPwdService.apprResetTranPwdApply(resetTransPwd);
            System.out.println("操作成功！");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testQueryLastApplyBean() throws Exception {

        ResetTransPwd transPwd = transPwdService.queryLastApplyBean("0600102000020151215");

        System.out.println(transPwd);

    }
}