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

import com.gy.hsxt.gpf.gcs.bean.Country;
import com.gy.hsxt.gpf.gcs.interfaces.ICountryService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gl-global
 * 
 *  Package Name    : com.gy.hsxt.gl.service
 * 
 *  File Name       : CountryServiceTest.java
 * 
 *  Creation Date   : 2015-7-16
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
public class CountryServiceTestCase {

    @Autowired
    private ICountryService countryService;

    String test = "java_test_";

    /**
     * 插入记录
     * 
     */
    @Test
    public void insert() {
        Country country = new Country();
        country.setCountryCode("Code");
        country.setCountryName(test + "Name");
        country.setCountryNameCn(test + "NameCn");
        country.setCountryNo("009");
        country.setPhonePrefix("0755");
        country.setPostCode("0256");
        country.setVersion(50);
        int row = countryService.insert(country);
        Assert.isTrue(row == 1);
    }

    /**
     * 读取某条记录
     */
    @Test
    public void getCountry() {
        Country city = countryService.getCountry("101");
        Assert.isTrue(!"".equals(city.getCountryNo()));
    }

    /**
     * 获取分页列表
     */
    @Test
    public void getCountryListPage() {
        Country city = new Country();
        List<Country> list = countryService.getCountryListPage(city);
        Assert.isTrue(list.size() >= 1);
    }

    /**
     * 获取全部有效国家
     */
    @Test
    public void getCountryAll() {
        List<Country> list = countryService.getCountryAll();
        Assert.isTrue(list.size() >= 1);
    }

    /**
     * 获取下拉菜单列表
     */
    @Test
    public void findContryAll() {
        List<Country> list = countryService.findContryAll();
        Assert.isTrue(list.size() >= 1);
    }

    /**
     * 更新某条记录
     */
    @Test
    public void update() {
        Country country = new Country();
        country.setCountryCode("Code");
        country.setCountryName(test + "Name");
        country.setCountryNameCn(test + "NameCn");
        country.setCountryNo("101");
        country.setPhonePrefix("0755");
        country.setPostCode("0256");
        country.setVersion(50);
        int row = countryService.update(country);
        Assert.isTrue(row == 1);
    }

    /**
     * 删除某条记录
     */
    @Test
    public void delete() {
        int row = countryService.delete("101");
        Assert.isTrue(row == 1);
    }

    /**
     * 判断是否已存在相同
     */
    @Test
    public void existCountry() {
        boolean bo = countryService.existCountry("101");
        Assert.isTrue(bo);
    }

    /**
     * 读取大于某个版本号的数据
     */
    @Test
    public void queryCountrySyncData() {
        List<Country> list = countryService.queryCountrySyncData(Long.parseLong("5"));
        Assert.isTrue(list.size() >= 1);
    }
}
