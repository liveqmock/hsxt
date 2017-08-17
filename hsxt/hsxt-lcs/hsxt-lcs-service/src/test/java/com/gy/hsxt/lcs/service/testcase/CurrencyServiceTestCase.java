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

import com.gy.hsxt.lcs.bean.Currency;
import com.gy.hsxt.lcs.interfaces.ICurrencyService;

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
     * 批量更新数据
     */
    @Test
    public void addOrUpdateCurrency() {
        List<Currency> list = new ArrayList<Currency>();
        Currency currency = new Currency();
        currency.setCurrencyNo("TEST_N");
        currency.setCurrencyCode("TEST_C");
        currency.setCurrencyNameCn("TECT_NAME_CN");
        currency.setCurrencySymbol("1");
        currency.setDelFlag(0);
        currency.setExchangeRate("0.1");
        currency.setPrecisionNum(2);
        currency.setUnitCode("ee");
        currency.setVersion(0);
        list.add(currency);

        int row = currencyService.addOrUpdateCurrency(list, Long.parseLong("5"));
        Assert.isTrue(row == 1);
    }
}
