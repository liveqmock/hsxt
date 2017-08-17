/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.ps.reconciliation.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.ps.common.PsTools;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

/**
 * @version V3.0
 * @Package: com.gy.hsxt.ps.reconciliation.service
 * @ClassName: EcOrderTest
 * @Description: TODO
 * @author: chenhongzhi
 * @date: 2015-10-21 下午5:07:47
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-global.xml"})
public class CheckFileTest {
	@Autowired
    private ReconciliationService reconciliationService;

    /**
     * 批量生成对账文件
     */
    @Test
    public void checkFile() {
        try {
        	String runDate = DateUtil.DateToString(DateUtil.addDays(DateUtil.today(), -1));

    		Map<String, String> args = new HashMap<String, String>();
    		args.put("BATCH_DATE", "20160315");
    		// 获得前一天日期
    		if (!CollectionUtils.isEmpty(args))
    		{
    			String batchDate = args.get("BATCH_DATE");
    			if (!PsTools.isEmpty(batchDate))
    			{
    				Date date = DateUtil.StringToDate(batchDate, DateUtil.DATE_FORMAT);
    				System.out.println("date " + date);
    				runDate = DateUtil.DateToString(DateUtils.addDays(date, -1));
    			}
    		}
    		
            reconciliationService.balanceAccount(runDate);
        } catch (HsException e) {
            e.printStackTrace();
        }
    }

}
