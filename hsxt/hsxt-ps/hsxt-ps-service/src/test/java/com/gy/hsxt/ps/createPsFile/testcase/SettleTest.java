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

package com.gy.hsxt.ps.createPsFile.testcase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.api.IPsBatchSettleFileService;

/** 
 * 
 * 
 * @Package: com.gy.hsxt.ps.createPsFile.testcase  
 * @ClassName: SettleTest 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2016-3-21 下午4:31:11 
 * @version V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring/spring-global.xml"})
public class SettleTest {
    
    @Autowired
    IPsBatchSettleFileService batSettleFileService;
    

    @Test
    public void testBatchChargeAccount() {
        String fileAddress = "D://text//";
        String batchDate = null;
        try {
            batSettleFileService.createBatSettleFile("查询批消费积分结算信息并生成文件", null,batchDate);
        } catch (HsException e) {
            e.printStackTrace();
        }
     }
}
