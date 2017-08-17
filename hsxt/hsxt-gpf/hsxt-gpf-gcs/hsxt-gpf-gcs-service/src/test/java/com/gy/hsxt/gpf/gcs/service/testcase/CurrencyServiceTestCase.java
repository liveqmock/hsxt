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

import com.gy.hsxt.gpf.gcs.bean.Currency;
import com.gy.hsxt.gpf.gcs.interfaces.ICurrencyService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gl-global
 * 
 *  Package Name    : com.gy.hsxt.gl.service
 * 
 *  File Name       : CurrencyServiceTest.java
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
public class CurrencyServiceTestCase {

    @Autowired
    private ICurrencyService currencyService;

    String test = "java_test_";

    /**
     * 插入记录
     * 
     */
    @Test
    public void insert() {
        Currency country = new Currency();
        country.setCurrencyCode("Code");
        country.setCurrencyNameCn(test + "NameCn");
        country.setCurrencyNo("009");
        country.setCurrencySymbol("2");
        country.setVersion(50);
        country.setUnitCode("2");
        country.setExchangeRate("1.0");
        int row = currencyService.insert(country);
        Assert.isTrue(row == 1);
    }

    /**
     * 读取某条记录
     */
    @Test
    public void getCurrency() {
        Currency city = currencyService.getCurrency("01");
        Assert.isTrue(!"".equals(city.getCurrencyNo()));
    }

    /**
     * 获取分页列表
     */
    @Test
    public void getCurrencyListPage() {
        Currency city = new Currency();
        List<Currency> list = currencyService.getCurrencyListPage(city);
        Assert.isTrue(list.size() >= 1);
    }

    /**
     * 获取全部有效货币
     */
    @Test
    public void getCurrencyAll() {
        List<Currency> list = currencyService.getCurrencyAll();
        Assert.isTrue(list.size() >= 1);
    }

    /**
     * 更新某条记录
     */
    @Test
    public void update() {
        Currency country = new Currency();
        country.setCurrencyCode("Code");
        country.setCurrencyNameCn(test + "NameCn");
        country.setCurrencyNo("01");
        country.setVersion(50);
        country.setCurrencySymbol("2");
        country.setUnitCode("2");
        country.setExchangeRate("1.0");
        int row = currencyService.update(country);
        Assert.isTrue(row == 1);
    }

    /**
     * 删除某条记录
     */
    @Test
    public void delete() {
        int row = currencyService.delete("01");
        Assert.isTrue(row == 1);
    }

    /**
     * 判断是否已存在相同
     */
    @Test
    public void existCurrency() {
        boolean bo = currencyService.existCurrency("01");
        Assert.isTrue(bo);
    }

    /**
     * 读取大于某个版本号的数据
     */
    @Test
    public void queryCurrencySyncData() {
        List<Currency> list = currencyService.queryCurrencySyncData(Long.parseLong("1"));
        Assert.isTrue(list.size() >= 1);
    }

}
