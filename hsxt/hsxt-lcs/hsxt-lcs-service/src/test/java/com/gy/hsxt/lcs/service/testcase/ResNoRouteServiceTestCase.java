/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.service.testcase;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.gy.hsxt.lcs.bean.Plat;
import com.gy.hsxt.lcs.bean.ResNoRoute;
import com.gy.hsxt.lcs.interfaces.IPlatService;
import com.gy.hsxt.lcs.interfaces.IResNoRouteService;
import com.gy.hsxt.lcs.interfaces.IVersionService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.service.testcase
 * 
 *  File Name       : ResNoRouteServiceTestCase.java
 * 
 *  Creation Date   : 2015-7-20
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 测试ResNoRouteService
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/

@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-global.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class ResNoRouteServiceTestCase {

    @Autowired
    IVersionService versionService;

    @Autowired
    IResNoRouteService resNoRouteService;

    @Autowired
    IPlatService platService;

    ResNoRoute resNoRoute;

    Plat plat;

    String resNo = "TEST1";

    String platNo = "TS1";

    String resNo02 = "TEST2";

    String platNo02 = "TS2";

    @Before
    public void setUp() throws Exception {
        resNoRoute = new ResNoRoute(resNo);
        resNoRoute.setPlatNo(platNo);
        resNoRoute.setDelFlag(false);

        plat = new Plat();
        plat.setPlatNo(platNo);
        plat.setPlatEntryIP("16.54.45.4");
        plat.setPlatEntryPort(15555);
        plat.setTimeOffset("20");

        Plat plat02 = new Plat();
        plat02.setPlatNo(platNo02);
        plat02.setPlatEntryIP("16.54.45.4");
        plat02.setPlatEntryPort(15555);
        plat02.setTimeOffset("20");

        List<Plat> platList = new ArrayList<Plat>();
        platList.add(plat);
        platList.add(plat02);
        platService.addOrUpdatePlat(platList, new Long(99));
    }

    @Test
    public void queryResNoRouteWithPK() {
        List<ResNoRoute> resNoRouteList = new ArrayList<ResNoRoute>();
        resNoRouteList.add(resNoRoute);
        resNoRouteService.addOrUpdateResNoRoute(resNoRouteList, new Long(99));
        ResNoRoute resNoRouteDB = resNoRouteService.queryResNoRouteWithPK(resNo);
        Assert.isTrue(resNo.equals(resNoRouteDB.getResNo()));
        Assert.isTrue(platNo.equals(resNoRouteDB.getPlatNo()));
    }

    @Test
    public void addOrUpdateResNoRoute() {
        List<ResNoRoute> resNoRouteList = new ArrayList<ResNoRoute>();
        resNoRouteList.add(resNoRoute);

        ResNoRoute resNoRoute02 = new ResNoRoute(resNo02);
        resNoRoute02.setPlatNo(platNo02);

        resNoRouteList.add(resNoRoute02);

        int flag = resNoRouteService.addOrUpdateResNoRoute(resNoRouteList, new Long(99));
        Assert.isTrue(1 == flag);
        Assert.notNull(resNoRouteService.queryResNoRouteWithPK(resNo));
        Assert.notNull(resNoRouteService.queryResNoRouteWithPK(resNo02));
    }
}
