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

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.api.IReportsPointDeductionSearchService;
import com.gy.hsxt.rp.api.IReportsSystemResourceExportService;
import com.gy.hsxt.rp.api.IReportsSystemResourceService;
import com.gy.hsxt.rp.bean.ReportsCardholderResource;
import com.gy.hsxt.rp.bean.ReportsEnterprisResource;
import com.gy.hsxt.rp.bean.ReportsResourceStatistics;
import com.gy.hsxt.rp.mapper.ReportsSystemResourceMapper;
import com.gy.hsxt.rp.service.ReportsPointDeductionBatchService;

/** 
 * 
 * @Package: com.gy.hsxt.service.testcase  
 * @ClassName: ReportsPointDeductionSearchServiceTest 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2016-1-6 下午3:23:54 
 * @version V1.0 
 

 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring/spring-global.xml"})
public class ReportsPointDeductionSearchServiceTest {

    @Autowired
    private ReportsPointDeductionBatchService reportsPointDeductionSearchService;
    
    @Autowired
    private ReportsSystemResourceMapper reportsSystemResourceMapper;
    
    @Test
    public void test() {
        
    	String beginDate = "2016-02-22";
    	String endDate = "2016-02-22";
    	
    	reportsPointDeductionSearchService.generatePointDeduction(beginDate, endDate);
//        
        //生成系统资源数据
        try
        {
//            reportsSystemResourceMapper.generateResourceStatistics();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testExcel() throws Exception {


//        ReportsEnterprisResource rpEnterprisResource = new ReportsEnterprisResource();
//        iReportsSystemResourceExportService.exportTrusEntResource(rpEnterprisResource);
//        
    }
    
}
