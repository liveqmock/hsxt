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

import com.gy.hsxt.bp.api.IBusinessSysBoSettingService;
import com.gy.hsxt.bp.bean.BusinessSysBoSetting;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.exception.HsException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring/spring-global.xml"})
public class BusinessSysBoSettingServiceTest {

    @Autowired
    public IBusinessSysBoSettingService businessSysBoSettingService;
    
    @Test
    public void test() throws java.text.ParseException{
        try{
            
            String custId = "0618601000620151130";
            String operCustId = "06186010006000020151130";
            
            List<BusinessSysBoSetting> sysBoSettingList = new ArrayList<BusinessSysBoSetting>();
            
            BusinessSysBoSetting sysBoSetting1 = new BusinessSysBoSetting();
            sysBoSetting1.setCustId(custId);
            sysBoSetting1.setSettingName(BusinessParam.BUY_HSB.getCode());
            sysBoSetting1.setSettingValue("0");
            sysBoSetting1.setCreatedby(operCustId);
            BusinessSysBoSetting sysBoSetting2 = new BusinessSysBoSetting();
            sysBoSetting2.setCustId(custId);
            sysBoSetting2.setSettingName(BusinessParam.HSB_TO_CASH.getCode());
            sysBoSetting2.setSettingValue("0");
            sysBoSetting2.setCreatedby(operCustId);
            BusinessSysBoSetting sysBoSetting3 = new BusinessSysBoSetting();
            sysBoSetting3.setCustId(custId);
            sysBoSetting3.setSettingName(BusinessParam.CASH_TO_BANK.getCode());
            sysBoSetting3.setSettingValue("1");
            sysBoSetting3.setCreatedby(operCustId);
            
            sysBoSettingList.add(sysBoSetting1);
            sysBoSettingList.add(sysBoSetting2);
            sysBoSettingList.add(sysBoSetting3);
            
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
            businessSysBoSettingService.setSysBoSettingList(custId, operCustId, sysBoSettingList);
            
        }catch(HsException ex){
            fail("ErrorCode:" + ex.getErrorCode() + " ErrorMessage:" + ex.getMessage() + "\n" + ex.getStackTrace());
        }
    
    }
    
    
}
