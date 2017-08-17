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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.bp.api.IBusinessParamSearchService;
import com.gy.hsxt.bp.bean.BusinessSysParamItemsRedis;
import com.gy.hsxt.common.exception.HsException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring/spring-global.xml"})
public class BusinessParamSearchServiceTest {

    @Autowired
    public IBusinessParamSearchService businessParamSearchService ;
    
    @Test
    public void test() throws java.text.ParseException{
        try{
//
            String sysGroupCode = "HSB_CHANGE_HB";
            String sysItemsKey = "HSB_CHANGE_HB_RATIO";
//            String custId = "wxz01";
            String agreeCode = "PUB_PLAT_PARA";
            String agreeFeeCode = "B_ANNUAL_FEE";
//            Map<String,BusinessSysParamItems> map = businessParamSearchService.searchSysParamItemsByGroup(sysGroupCode);
//            businessParamSearchService.searchCustParamItemsByGroup(custId);
//            Map<String,BusinessAgreeFee> map =  businessParamSearchService.searchBusinessAgreeFee(agreeCode);
           
            businessParamSearchService.searchSysParamItemsByCodeKey(sysGroupCode, sysItemsKey);
//            System.out.println("=============="+values);
//            System.out.println(businessSysParamItems.getSysItemsKey()+"======"+businessSysParamItems.getSysGroupCode());
//            BusinessCustParamItems businessCustParamItems =  businessParamSearchService.searchCustParamItemsByIdKey(custId, sysItemsKey);
//            System.out.println(businessCustParamItems.getSysItemsKey()+"======"+businessCustParamItems.getSysGroupCode());
//            businessParamSearchService.searchBusinessAgreeFeeByCode(agreeCode, agreeFeeCode);
//            
//            System.out.println("集合數量："+map.size());
//            businessParamSearchService.initDataToRedis();
//            SystemLog.debug("HSXT_BP", "test", "BP日志测试");
//            BizLog.biz("HSXT_BP", "test", "test", "BP日志测试");
//            businessParamSearchService.initDataToRedis();
        }catch(HsException ex){
            fail("ErrorCode:" + ex.getErrorCode() + " ErrorMessage:" + ex.getMessage() + "\n" + ex.getStackTrace());
        }
    
    }
    
    
}
