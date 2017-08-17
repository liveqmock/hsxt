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
package com.gy.hsxt.ao.testcase;

import static org.junit.Assert.fail;

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
@ContextConfiguration(locations = { "classpath:spring/spring-global.xml" })
public class BusinessParamSearchServiceTest {

    @Autowired
    public BusinessParamSearch businessParamSearch;

    @Resource()
    private RedisTemplate redisTemplate;

    @Test
    public void test() throws java.text.ParseException {
        try
        {
            String custId = "0618601000620151130";

            // BusinessCustParamItemsRedis custParamItemsRedis =
            // businessParamSearch.searchCustParamItemsByIdKey(custId,
            // BusinessParam.SINGLE_DAILY_HAD_TRANSFER.getCode());

            // businessParamSearch.setBusinessCustParamItemsRed(custParamItemsRedis);
            // BusinessCustParamItemsRedis custParamItemsRedis =
            // businessParamSearch.searchCustParamItemsByIdKey(custId,
            // BusinessParam.HSB_PAYMENT.getCode(),
            // BusinessParam.CONSUMER_PAYMENT_MAX.getCode());

            // String sysGroupCode = "PUB_PLAT_PARA";
            // String sysItemsKey = "CORP_CODE";
            // String agreeCode = "12";
            // String agreeFeeCode = "12";
            // redisTemplate.opsForHash().delete(BPConstants.SYS_NAME,
            // sysGroupCode);
            // Map<String, BusinessSysParamItems> sysParamItemsMap =
            // businessParamSearchService.searchSysParamItemsByGroup(sysGroupCode);
            // BusinessSysParamItemsRedis bSysParamItems =
            // businessParamSearch.searchSysParamItemsByCodeKey(sysGroupCode,
            // sysItemsKey);
            // businessParamSearchService.searchCustParamItemsByGroup(custId);
            // businessParamSearchService.searchBusinessAgreeFee(agreeCode);
            // businessParamSearchService.searchSysParamItemsByCodeKey(sysGroupCode,
            // sysItemsKey);
            // businessParamSearchService.searchCustParamItemsByIdKey(custId,
            // sysItemsKey);
            // businessParamSearchService.searchBusinessAgreeFeeByCode(agreeCode,
            // agreeFeeCode);
            //
            // System.out.println(sysParamItemsMap.get(sysItemsKey).getSysGroupCode());
            // System.out.println(sysParamItemsMap.get(sysItemsKey).getSysItemsKey());
            // System.out.println(sysParamItemsMap.get(sysItemsKey).getSysItemsName());
            // System.out.println(sysParamItemsMap.get(sysItemsKey).getSysItemsValue());

            // System.out.println(custParamItemsRedis.getSysItemsName());
            // System.out.println(custParamItemsRedis.getSysItemsValue());
            // SystemLog.showSysName();
            // BizLog.showBizName();

            BusinessCustParamItemsRedis business = businessParamSearch.searchCustParamItemsByIdKey(custId,
                    "SINGLE_DAY_HAD_BUY_HSB");

            if (business.getSysItemsValue() == null)
            {
                BusinessCustParamItemsRedis custParamItemsRedis = new BusinessCustParamItemsRedis();
                custParamItemsRedis.setCustId(custId);

                custParamItemsRedis.setDueDate("20160130");
                custParamItemsRedis.setSysItemsKey("SINGLE_DAY_HAD_BUY_HSB");
                custParamItemsRedis.setSysItemsValue("1000");
                businessParamSearch.setBusinessCustParamItemsRed(custParamItemsRedis);
            }
            else
            {
                Integer a1 = Integer.parseInt(business.getSysItemsValue());
                a1 = a1 + 1000;
                business.setSysItemsValue(a1.toString());
                businessParamSearch.setBusinessCustParamItemsRed(business);
            }

        }
        catch (HsException ex)
        {
            fail("ErrorCode:" + ex.getErrorCode() + " ErrorMessage:" + ex.getMessage() + "\n" + ex.getStackTrace());
        }

    }

}
