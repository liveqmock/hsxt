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

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.rp.api.IReportsInvoiceValueStatisticsBatchService;

/** 
 * 
 * @Package: com.gy.hsxt.rp.service  
 * @ClassName: ReportsInvoiceValueStatisticsBatchService 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2015-10-13 下午2:21:59 
 * @version V1.0 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring/spring-global.xml"})
public class ReportsInvoiceValueStatisticsBatchServiceTest {
    
    @Autowired
    IReportsInvoiceValueStatisticsBatchService reportsInvoiceValueStatisticsBatchService;

    @Test
    public void testBatchChargeAccount() {
    	Map<String, String> args = new HashMap<String, String>();
    	String batchJobName = "发票金额统计每日统计文件";
        String fileAddress = "D://text//";
        args.put("fileAddress", fileAddress);
        args.put("batchJobName", batchJobName);
        try {
        	reportsInvoiceValueStatisticsBatchService.invoiceValueStatistics(batchJobName, null, "20160522");
        } catch (Exception e) {
            e.getMessage();
        }
        
    }

}

    