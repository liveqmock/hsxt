/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.service.testcase;

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

import com.gy.hsxt.gpf.gcs.bean.Plat;
import com.gy.hsxt.gpf.gcs.bean.ResNoRoute;
import com.gy.hsxt.gpf.gcs.bean.Version;
import com.gy.hsxt.gpf.gcs.common.Constant;
import com.gy.hsxt.gpf.gcs.interfaces.IPlatService;
import com.gy.hsxt.gpf.gcs.interfaces.IResNoRouteService;
import com.gy.hsxt.gpf.gcs.interfaces.IVersionService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.service.testcase
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
        platService.insert(plat);

        Plat plat02 = new Plat();
        plat02.setPlatNo(platNo02);
        plat02.setPlatEntryIP("16.54.45.4");
        plat02.setPlatEntryPort(15555);
        plat02.setTimeOffset("20");
        platService.insert(plat02);
    }

    @Test
    public void addResNoRoute() {
        int row = resNoRouteService.addResNoRoute(resNoRoute);
        Assert.isTrue(1 == row);
        ResNoRoute resNoRouteDB = resNoRouteService.queryResNoRouteWithPK(resNo);
        Assert.isTrue(resNo.equals(resNoRouteDB.getResNo()));
        Assert.isTrue(platNo.equals(platNo));

        Version version = versionService.queryVersion(Constant.GL_RESNO_ROUTE);
        Assert.isTrue(version.getVersion() == resNoRouteDB.getVersion());

    }

    @Test
    public void deleteResNoRoute() {
        resNoRouteService.addResNoRoute(resNoRoute);
        Assert.isTrue(resNoRouteService.deleteResNoRoute(resNo));
        ResNoRoute resNoRouteDB = resNoRouteService.queryResNoRouteWithPK(resNo);
        Assert.isTrue(resNoRouteDB.isDelFlag());

        Version version = versionService.queryVersion(Constant.GL_RESNO_ROUTE);
        Assert.isTrue(version.getVersion() == resNoRouteDB.getVersion());
    }

    @Test
    public void updateResNoRoute() {
        resNoRouteService.addResNoRoute(resNoRoute);
        ResNoRoute resNoRouteNew = new ResNoRoute(resNo);
        resNoRouteNew.setPlatNo(platNo02);
        Assert.isTrue(resNoRouteService.updateResNoRoute(resNoRouteNew));
        ResNoRoute resNoRouteDB = resNoRouteService.queryResNoRouteWithPK(resNo);
        Assert.isTrue(platNo02.equals(resNoRouteDB.getPlatNo()));

        Version version = versionService.queryVersion(Constant.GL_RESNO_ROUTE);
        Assert.isTrue(version.getVersion() == resNoRouteDB.getVersion());
    }

    @Test
    public void queryResNoRoute() {
        resNoRouteService.addResNoRoute(resNoRoute);
        List<ResNoRoute> resNoRouteList = resNoRouteService.queryResNoRoute(resNoRoute);
        Assert.isTrue(1 == resNoRouteList.size());
        Assert.isTrue(resNo.equals(resNoRouteList.get(0).getResNo()));

        ResNoRoute resNoRoute02 = new ResNoRoute(resNo02);
        resNoRoute02.setPlatNo(platNo);
        List<ResNoRoute> resNoRouteList02 = resNoRouteService.queryResNoRoute(resNoRoute02);
        Assert.isTrue(0 == resNoRouteList02.size());

        resNoRouteService.addResNoRoute(resNoRoute02);

        ResNoRoute resNoRoute03 = new ResNoRoute();
        resNoRoute03.setPlatNo(platNo);
        List<ResNoRoute> resNoRouteList03 = resNoRouteService.queryResNoRoute(resNoRoute03);
        Assert.isTrue(2 == resNoRouteList03.size());
    }

    @Test
    public void existResNoRoute() {
        Assert.isTrue(!resNoRouteService.existResNoRoute(resNo));
        resNoRouteService.addResNoRoute(resNoRoute);
        Assert.isTrue(resNoRouteService.existResNoRoute(resNo));
    }

    @Test
    public void queryResNoRouteWithPK() {
        resNoRouteService.addResNoRoute(resNoRoute);
        ResNoRoute resNoRouteDB = resNoRouteService.queryResNoRouteWithPK(resNo);
        Assert.isTrue(resNo.equals(resNoRouteDB.getResNo()));
        Assert.isTrue(platNo.equals(resNoRouteDB.getPlatNo()));
    }

    @Test
    public void queryResNoRoute4SyncData() {
        List<ResNoRoute> resNoRouteList = resNoRouteService.queryResNoRoute4SyncData(new Long(50));
        System.out.println(resNoRouteList.size());
        int count = 0;
        for (ResNoRoute resNoRoute : resNoRouteList)
        {
            if (resNoRoute.getVersion() <= 15)
            {
                count++;
            }
        }
        int bigThan15Count = resNoRouteList.size() - count;

        Assert.isTrue(bigThan15Count == resNoRouteService.queryResNoRoute4SyncData(new Long(15)).size());
    }

    @Test
    public void batchDelResNoRoute() {
        List<ResNoRoute> resNoRouteList = resNoRouteService.getResNoRouteList();
        int beforeCnt = resNoRouteList.size();
        List<String> resNoList = new ArrayList<String>();
        for (int i = 0; i < beforeCnt / 2; i++)
        {
            resNoList.add(resNoRouteList.get(i).getResNo() + "000000");
        }
        resNoRouteService.batchDelResNoRoute(resNoList);
        int afterCnt = resNoRouteService.getResNoRouteList().size();
        System.out.println("batchDelResNoRoute: beforeCnt=" + beforeCnt + "\tafterCnt=" + afterCnt);
    }

    @Test
    public void syncAddRouteRule() {
        List<String> resNoList = new ArrayList<String>();
        resNoList.add("09030000000");
        resNoList.add("09031000000");
        resNoList.add("09032000000");
        resNoList.add("09033000000");
        resNoList.add("09034000000");
        resNoRouteService.syncAddRouteRule(resNoList, "156");
    }
}
