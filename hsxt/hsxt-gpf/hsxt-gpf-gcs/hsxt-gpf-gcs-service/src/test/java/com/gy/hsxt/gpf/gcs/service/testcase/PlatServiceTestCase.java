/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.service.testcase;

import java.util.List;

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
import com.gy.hsxt.gpf.gcs.interfaces.IPlatService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gl-global
 * 
 *  Package Name    : com.gy.hsxt.gl.service
 * 
 *  File Name       : PlatServiceTest.java
 * 
 *  Creation Date   : 2015-7-17
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : TODO
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-global.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class PlatServiceTestCase {
    @Autowired
    private IPlatService platService;

    /**
     * 判断是否已存在相同
     */
    @Test
    public void existPlat() {
        boolean bo = platService.existPlat("29");
        Assert.isTrue(!bo);
    }

    /**
     * 插入记录
     */
    @Test
    public void insert() {
        Plat plat = new Plat();
        plat.setCountryNo("100");
        plat.setCurrencyNo("01");
        plat.setPlatNameCn("NameCn");
        plat.setPlatNo("29");
        plat.setLanguageCode("222");
        plat.setPlatEntryIP("168.33.56.66");
        plat.setPlatEntryPort(2556);
        plat.setVersion(20);
        plat.setDelFlag(false);
        plat.setTimeOffset("2");
        int row = platService.insert(plat);
        Assert.isTrue(row == 1);
    }

    /**
     * 读取某条记录
     */
    public void getPlat() {
        Plat plat = platService.getPlat("01");
        Assert.isTrue(!"".equals(plat.getPlatNo()));
    }

    /**
     * 更新某条记录
     */
    @Test
    public void update() {
        Plat plat = new Plat();
        plat.setCountryNo("100");
        plat.setCurrencyNo("01");
        plat.setPlatNameCn("NameCn");
        plat.setPlatNo("12");
        plat.setLanguageCode("222");
        plat.setPlatEntryIP("168.33.56.66");
        plat.setPlatEntryPort(2556);
        plat.setVersion(20);
        plat.setDelFlag(true);
        plat.setTimeOffset("2");
        int row = platService.update(plat);
        Assert.isTrue(row == 1);
    }

    /**
     * 获取列表
     */
    @Test
    public void getPlatListPage() {
        Plat plat = new Plat();
        List<Plat> list = platService.getPlatListPage(plat);
        Assert.isTrue(list.size() >= 1);
    }

    /**
     * 删除某条记录
     */
    @Test
    public void delete() {
        int row = platService.delete("21");
        Assert.isTrue(row == 1);
    }

    /**
     * 读取大于某个版本号的数据
     */
    @Test
    public void queryPlatSyncData() {
        List<Plat> list = platService.queryPlatSyncData(Long.parseLong("5"));
        Assert.isTrue(list.size() >= 1);
    }

}
