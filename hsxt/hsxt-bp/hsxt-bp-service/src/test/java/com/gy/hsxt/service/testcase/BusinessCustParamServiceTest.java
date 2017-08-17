/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

package com.gy.hsxt.service.testcase;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.bp.api.IBusinessCustParamService;
import com.gy.hsxt.bp.bean.BusinessCustParamItems;
import com.gy.hsxt.bp.bean.BusinessCustParamItemsRedis;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.exception.HsException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring/spring-global.xml"})
public class BusinessCustParamServiceTest {

    @Autowired
    public IBusinessCustParamService ibusinessCustParamItemsService;
    
    @Test
    public void test() throws java.text.ParseException{
        try{
            
            String custId = "0618601000620151130";
            
            List<BusinessCustParamItemsRedis> list = new ArrayList<BusinessCustParamItemsRedis>();
            
            BusinessCustParamItemsRedis businessCustParamItems1 = new BusinessCustParamItemsRedis();
            businessCustParamItems1.setCustId("0618601000620151130");
            businessCustParamItems1.setHsResNo("06186010006");
            businessCustParamItems1.setCustName("呵呵");
            businessCustParamItems1.setSysGroupCode("HSB_PAYMENT");
            businessCustParamItems1.setSysItemsKey("CONSUMER_PAYMENT_MAX");
            businessCustParamItems1.setSysItemsName("消费者互生币支付单笔限额");
            businessCustParamItems1.setSysItemsValue("1000");
            
            BusinessCustParamItemsRedis businessCustParamItems2 = new BusinessCustParamItemsRedis();
            businessCustParamItems2.setCustId("0618601000620151130");
            businessCustParamItems2.setHsResNo("06186010006");
            businessCustParamItems2.setCustName("呵呵");
            businessCustParamItems2.setSysGroupCode("HSB_PAYMENT");
            businessCustParamItems2.setSysItemsKey("CONSUMER_PAYMENT_DAY_MAX");
            businessCustParamItems2.setSysItemsName("消费者互生币支付当日限额");
            businessCustParamItems2.setSysItemsValue("50000");
            
            BusinessCustParamItemsRedis businessCustParamItems3 = new BusinessCustParamItemsRedis();
            businessCustParamItems3.setCustId("0618601000620151130");
            businessCustParamItems3.setHsResNo("06186010006");
            businessCustParamItems3.setCustName("呵呵");
            businessCustParamItems3.setSysGroupCode("HSB_PAYMENT");
            businessCustParamItems3.setSysItemsKey("CONSUMER_PAYMENT_DAY_NUMBER");
            businessCustParamItems3.setSysItemsName("消费者互生币支付单日交易次数");
            businessCustParamItems3.setSysItemsValue("2");
            
            list.add(businessCustParamItems1);
            list.add(businessCustParamItems2);
            list.add(businessCustParamItems3);
            
//            Page page = new Page(1,3);
//            
//            String custItemsId = "4";
//            String custId = "wxz01";
//            String sysGroupCode = "PUB_PLAT_PARA";
//            String sysItemsKey = "CORP_CODE";
            
//            ibusinessCustParamItemsService.addCustParamItems(businessCustParamItems);
//            ibusinessCustParamItemsService.updateCustParamItems(businessCustParamItems);
//            ibusinessCustParamItemsService.deleteCustParamItems(custItemsId,custId,sysItemsKey);
//            ibusinessCustParamItemsService.getCustParamItemsById("1");
//            ibusinessCustParamItemsService.searchCustParamItemsPage(businessCustParamItems, page);
            ibusinessCustParamItemsService.addOrUpdateCustParamItemsList(custId, list);
            
        }catch(HsException ex){
            fail("ErrorCode:" + ex.getErrorCode() + " ErrorMessage:" + ex.getMessage() + "\n" + ex.getStackTrace());
        }
    
    }
    
    
}
