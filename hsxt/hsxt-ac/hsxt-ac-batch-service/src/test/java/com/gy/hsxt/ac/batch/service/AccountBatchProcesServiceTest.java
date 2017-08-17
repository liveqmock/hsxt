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

package com.gy.hsxt.ac.batch.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.ac.api.IAccountCheckFileService;
import com.gy.hsxt.ac.api.IAccountEntGiveIntegralMonthFileService;
import com.gy.hsxt.ac.api.IAccountIntegralFileService;
import com.gy.hsxt.common.exception.HsException;

/** 
 * 
 * @Package: com.gy.hsxt.ac.batch.service  
 * @ClassName: AccountBatchProcesServiceTest 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2015-10-13 下午2:21:59 
 * @version V1.0 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring/spring-global.xml"})
public class AccountBatchProcesServiceTest {
    
    @Autowired
    IAccountCheckFileService iAccountCheckFileService;

    @Test
    public void testBatchChargeAccount() {
        String fileAddress = "D://text//";
        try {
        	iAccountCheckFileService.createAccountCheckFile("生成对账文件", null, null, "BS|PS|AO");
        } catch (HsException e) {
            e.printStackTrace();
        }
        
    }

}

    