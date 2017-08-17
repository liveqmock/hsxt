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

import com.gy.hsxt.uc.as.api.common.IUCAsReserveInfoService;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-global.xml" })
public class UCAsReserveInfoServiceTest {
    @Autowired
    IUCAsReserveInfoService reserveInfoService;
    
    @Test
    public void findReserveInfoByCustId(){
        String custId = "07186630000163969843354624";
        String userType = "";
      String sss =   reserveInfoService.findReserveInfoByCustId(custId, UserTypeEnum.OPERATOR.getType());
      System.out.println("ReserveInfo["+sss+"]");
    }
 //   @Test
    public void setReserveInfo(){
        String custId = "07186630000163969843354624";
        String userType = "";
        String reserveInfo = "首次设置预留信息";
        reserveInfoService.setReserveInfo(custId, reserveInfo, UserTypeEnum.OPERATOR.getType());
    }
}
