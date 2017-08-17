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
import com.gy.hsxt.rp.api.IReportsSystemResourceExportService;
import com.gy.hsxt.rp.api.IReportsSystemResourceService;
import com.gy.hsxt.rp.bean.ReportsCardholderResource;
import com.gy.hsxt.rp.bean.ReportsEnterprisResource;
import com.gy.hsxt.rp.bean.ReportsResourceStatistics;

/** 
 * 
 * @Package: com.gy.hsxt.service.testcase  
 * @ClassName: ReportsSystemResourceServiceTest 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2016-1-6 下午3:23:54 
 * @version V1.0 
 

 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring/spring-global.xml"})
public class ReportsSystemResourceServiceTest {

    @Autowired
    private IReportsSystemResourceService iReportsSystemResourceService;
    
    @Autowired
    private IReportsSystemResourceExportService iReportsSystemResourceExportService;
    
    @Test
    public void test() {
        
        ReportsEnterprisResource  rpEnterprisResource = new ReportsEnterprisResource();
//        rpEnterprisResource.setHsResNo("06186630000");
//        rpEnterprisResource.setEntCustName("企业");
        
        ReportsCardholderResource  rpCardholderResource = new ReportsCardholderResource();
        rpCardholderResource.setEntResNo("06007010000");
        rpCardholderResource.setRealnameAuth(3);
        
        ReportsResourceStatistics  rpResourceStatistics = new ReportsResourceStatistics();
        rpResourceStatistics.setHsResNo("09186630000");
        
        try {
            
          List<ReportsEnterprisResource>  list = iReportsSystemResourceService.searchEnterprisResource(rpEnterprisResource);
          List<ReportsCardholderResource>  list1 = iReportsSystemResourceService.searchCardholderResource(rpCardholderResource);
          List<ReportsResourceStatistics>  list2 = iReportsSystemResourceService.searchResourceStatistics(rpResourceStatistics);
          
          
          System.out.println("list.size()==========="+list.size());
          System.out.println("list1.size()=="+list1.size());
          System.out.println("list2.size()==="+list2.size());
          
          Page page = new Page(1,10);
          
          PageData<ReportsCardholderResource> pageData = iReportsSystemResourceService.searchCardholderResourcePage(rpCardholderResource, page);
          PageData<ReportsEnterprisResource> pageData1 = iReportsSystemResourceService.searchEnterprisResourcePage(rpEnterprisResource, page);
          PageData<ReportsResourceStatistics> pageData2 = iReportsSystemResourceService.searchResourceStatisticsPage(rpResourceStatistics, page);
          
          System.out.println("pageData.Count()==========="+pageData.getCount()+",getResult==="+pageData.getResult().size());
          System.out.println("pageData1.Count()=="+pageData1.getCount()+",getResult==="+pageData1.getResult().size());
          System.out.println("pageData2.Count()==="+pageData2.getCount()+",getResult==="+pageData2.getResult().size());
          
        } catch (HsException e) {
            
            System.out.println("HsException ========="+e.getMessage());
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
