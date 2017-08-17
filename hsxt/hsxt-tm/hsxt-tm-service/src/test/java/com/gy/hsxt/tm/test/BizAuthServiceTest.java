/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.tm.api.ITMBizAuthService;
import com.gy.hsxt.tm.bean.BizType;
import com.gy.hsxt.tm.bean.BizTypeAuth;
import com.gy.hsxt.uc.as.api.auth.IUCAsPermissionService;
import com.gy.hsxt.uc.as.bean.auth.AsPermission;

public class BizAuthServiceTest extends BaseTest {

    @Autowired
    ITMBizAuthService authService;

    @Autowired
    IUCAsPermissionService ucPermissionService;

    @Test
    public void testGetUserPermission() {
        List<AsPermission> permissionList = ucPermissionService.listPermByCustId(null, null, null, null,
                "00000000156163438271051776", null);
        List<String> permIdList = new ArrayList<String>();
        for (AsPermission asPermission : permissionList)
        {
            permIdList.add(asPermission.getPermId());
            System.out.println("权限编号ID:" + asPermission.getPermId());
            System.out.println("父级权限:" + asPermission.getParentId());
            System.out.println("权限名称:" + asPermission.getPermName());
            System.out.println("权限代码:" + asPermission.getPermCode());
            System.out.println("权限URL:" + asPermission.getPermUrl());
            System.out.println("权限描述:" + asPermission.getPermDesc());
            System.out.println("权限类型:" + asPermission.getPermType());
            System.out.println("菜单放置区域:" + asPermission.getLayout());
            System.out.println("子系统代码:" + asPermission.getSubSystemCode());
            System.out.println("平台代码:" + asPermission.getPlatformCode());
            System.out.println("==============================================");
        }

        System.out.println("授权权限数：" + permIdList.size());
    }

    /*
     * 积分福利审批
     */
    // TASK_TYPE117("117"),

    /*
     * 积分福利发放
     */
    // TASK_TYPE118("118"), ;
    @Test
    public void testSaveBizType() {
        BizType bizType = new BizType();
        bizType.setBizType("117");
        bizType.setBizTypeName("积分福利审批");
        bizType.setBizEntCustType(CustType.AREA_PLAT.getCode());
        authService.saveBizType(bizType);
        bizType = new BizType();
        bizType.setBizType("118");
        bizType.setBizTypeName("积分福利发放");
        bizType.setBizEntCustType(CustType.AREA_PLAT.getCode());
        authService.saveBizType(bizType);
    }

    @Test
    public void testUpdateBizTypeName() {
        BizType bizType = new BizType();
        bizType.setBizType("888");
        bizType.setBizTypeName("现在开始有点明确");
        authService.updateBizTypeName(bizType);
    }

    @Test
    public void testDeleteBizType() {
        authService.deleteBizType("888");
    }

    @Test
    public void testGetBizTypeList() {
        List<BizType> bizTypeList = authService.getBizTypeList("00000000156163438271051776");
        for (BizType bizType : bizTypeList)
        {
            System.out.println(bizType);
        }
    }

    /**
     * 给操作员添加授权
     */
    @Test
    public void testSaveBizTypeAuth() {
        BizTypeAuth bizTypeAuth = null;
        List<BizType> bizTypeList = authService.getBizTypeList("");
        for (BizType bizType : bizTypeList)
        {
            bizTypeAuth = new BizTypeAuth();
            bizTypeAuth.setBizType(bizType.getBizType());
            bizTypeAuth.setOptCustId("06001010000000120160115");
            authService.saveBizTypeAuth(bizTypeAuth);
            // bizTypeAuth = new BizTypeAuth();
            // bizTypeAuth.setBizType(bizType.getBizType());
            // bizTypeAuth.setOptCustId("00000000156000320160105");
            // authService.saveBizTypeAuth(bizTypeAuth);
            // bizTypeAuth = new BizTypeAuth();
            // bizTypeAuth.setBizType(bizType.getBizType());
            // bizTypeAuth.setOptCustId("06000000000222320160113");
            // authService.saveBizTypeAuth(bizTypeAuth);
            // bizTypeAuth = new BizTypeAuth();
            // bizTypeAuth.setBizType(bizType.getBizType());
            // bizTypeAuth.setOptCustId("06000000000000120160107");
            // authService.saveBizTypeAuth(bizTypeAuth);
            // bizTypeAuth = new BizTypeAuth();
            // bizTypeAuth.setBizType(bizType.getBizType());
            // bizTypeAuth.setOptCustId("06000000000000220160107");
            // authService.saveBizTypeAuth(bizTypeAuth);
        }
    }

    @Test
    public void testDeleteOptCustAuth() {

        // authService.deleteOptCustAuth("331", "0353802264320151112");
    }

    @Test
    public void testGetOptCustAuthList() {
        List<BizTypeAuth> optCustAuthList = authService.getOptCustAuthList("0353802264320151112");
        for (BizTypeAuth bizTypeAuth : optCustAuthList)
        {
            System.out.println(bizTypeAuth);
        }
    }

    @Test
    public void testGetBizAuthList() {
        List<BizType> bizType = authService.getBizAuthList(CustType.AREA_PLAT.getCode());
        for (BizType bType : bizType)
        {
            System.out.println(bType);
        }
    }

    @Test
    public void testSyslog() {
        SystemLog.info("xxx", "me", "hhhee");
        BizLog.biz("xxx", "xx", "xx", "xx");
    }
}
