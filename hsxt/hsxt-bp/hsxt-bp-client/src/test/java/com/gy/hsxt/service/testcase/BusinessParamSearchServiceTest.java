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

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.bp.bean.BusinessCustParamItemsRedis;
import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.common.exception.HsException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/spring-global.xml"})
public class BusinessParamSearchServiceTest {

    @Autowired
    public BusinessParamSearch businessParamSearch;
    
    @Resource()
    private RedisTemplate redisTemplate;
    
    @SuppressWarnings("unused")
	@Test
    public void test() throws java.text.ParseException{
        try{
        	String custId = "0601011000020160109";
        	
//        	BusinessCustParamItemsRedis custParamItemsRedis  = businessParamSearch.searchCustParamItemsByIdKey(custId, BusinessParam.SINGLE_DAILY_HAD_TRANSFER.getCode());
        	
//        	businessParamSearch.setBusinessCustParamItemsRed(custParamItemsRedis);
//        	BusinessCustParamItemsRedis custParamItemsRedis  = businessParamSearch.searchCustParamItemsByIdKey(custId, BusinessParam.HSB_PAYMENT.getCode(), BusinessParam.CONSUMER_PAYMENT_MAX.getCode());
        	
//            String sysGroupCode = "PUB_PLAT_PARA";
//            String sysItemsKey = "CORP_CODE";
//            String agreeCode = "12";
//            String agreeFeeCode = "12";
//            redisTemplate.opsForHash().delete(BPConstants.SYS_NAME, sysGroupCode);
//            Map<String, BusinessSysParamItems> sysParamItemsMap = businessParamSearchService.searchSysParamItemsByGroup(sysGroupCode);
//            BusinessSysParamItemsRedis bSysParamItems = businessParamSearch.searchSysParamItemsByCodeKey(sysGroupCode, sysItemsKey);
//            businessParamSearchService.searchCustParamItemsByGroup(custId);
//            businessParamSearchService.searchBusinessAgreeFee(agreeCode);
//            businessParamSearchService.searchSysParamItemsByCodeKey(sysGroupCode, sysItemsKey);
//            businessParamSearchService.searchCustParamItemsByIdKey(custId, sysItemsKey);
//            businessParamSearchService.searchBusinessAgreeFeeByCode(agreeCode, agreeFeeCode);
//         
//            System.out.println(sysParamItemsMap.get(sysItemsKey).getSysGroupCode());
//            System.out.println(sysParamItemsMap.get(sysItemsKey).getSysItemsKey());
//            System.out.println(sysParamItemsMap.get(sysItemsKey).getSysItemsName());
//            System.out.println(sysParamItemsMap.get(sysItemsKey).getSysItemsValue());
            
//            System.out.println(custParamItemsRedis.getSysItemsName());
//            System.out.println(custParamItemsRedis.getSysItemsValue());
//        		SystemLog.showSysName();
//        		BizLog.showBizName();
        	
//        	BusinessCustParamItemsRedis business = businessParamSearch.searchCustParamItemsByIdKey(custId, "SINGLE_DAY_HAD_BUY_HSB");
//        	
//            BusinessCustParamItemsRedis custParamItemsRedis = new BusinessCustParamItemsRedis();
//            custParamItemsRedis.setCustId(custId);
//            
//            custParamItemsRedis.setDueDate("20160130");
//            custParamItemsRedis.setSysItemsKey("SINGLE_DAY_HAD_BUY_HSB");
//            custParamItemsRedis.setSysItemsValue("1000");
//            businessParamSearch.setBusinessCustParamItemsRed(custParamItemsRedis);
//            custParamItemsRedis.setDueDate("20160130");
//            custParamItemsRedis.setSysItemsKey("SINGLE_DAY_HAD_BUY_MIN1");
//            custParamItemsRedis.setSysItemsValue("2000");
////            businessParamSearch.setBusinessCustParamItemsRed(custParamItemsRedis);
//            BusinessCustParamItemsRedis business2 = businessParamSearch.searchCustParamItemsByIdKey(custId, "SINGLE_DAY_HAD_BUY_HSB");
//            BusinessCustParamItemsRedis business3 = businessParamSearch.searchCustParamItemsByIdKey(custId, "SINGLE_DAY_HAD_BUY_MIN1");
//            business2 = businessParamSearch.searchCustParamItemsByIdKey(custId, "SINGLE_DAY_HAD_BUY_HSB");
//            business2.getSysItemsValue();
//            business3.getSysItemsValue();
        	
//        	Map<String, BusinessCustParamItemsRedis> custMap = businessParamSearch.searchCustParamItemsByGroup(custId);
        	BusinessCustParamItemsRedis businessCustParamItemsRedis = businessParamSearch.searchCustParamItemsByIdKey(custId, "SINGLE_DAY_HAD_BUY_HSB");
        	businessCustParamItemsRedis.getSysItemsValue();
        	
        }catch(HsException ex){
            fail("ErrorCode:" + ex.getErrorCode() + " ErrorMessage:" + ex.getMessage() + "\n" + ex.getStackTrace());
        }
    
    }
    
    
}
