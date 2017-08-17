/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.uc.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.gp.bean.QuickPayBindingNo;
import com.gy.hsxt.uc.common.service.GPNotifyBingNoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-uc.xml" })
public class GPNotifyBingNoServiceTest {
    @Autowired GPNotifyBingNoService gpNotifyBingNoService;
    
    @Test
    public void notifyQuickPayBindingNo(){
        QuickPayBindingNo pay = new QuickPayBindingNo();
        pay.setBankCardNo("6227000012");
        pay.setBankCardType(2);
        pay.setBankId("CYB");
        pay.setBindingNo("21452110");
//        pay.setCustId("0800101000020151222");
        pay.setCustId("0600211735020151207");
        gpNotifyBingNoService.notifyQuickPayBindingNo(pay);
        
       
    }
    
//    @Test
//    public void notifyQuickPayBindingNo(){
//        QuickPayBindingNo pay = new QuickPayBindingNo();
//        pay.setBankCardNo("622700001");
//        pay.setBankCardType(1);
//        pay.setBankId("CYB");
//        pay.setBindingNo("21452110");
//        pay.setCustId("12");
//        gpNotifyBingNoService.notifyQuickPayBindingNo(pay);
//        
//       
//    }
}
