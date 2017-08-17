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

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.api.IReportsAccountEntryService;
import com.gy.hsxt.rp.api.IReportsPointDeductionSearchService;
import com.gy.hsxt.rp.bean.ReportsAccountEntry;
import com.gy.hsxt.rp.bean.ReportsAccountEntryInfo;
import com.gy.hsxt.rp.bean.ReportsPointDeduction;

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
public class ReportsAccountEntryServiceTest {

    @Autowired
    IReportsAccountEntryService reportsAccountEntryService;
    
    
    @Test
    public void test() {
        
    	ReportsAccountEntryInfo reportsAccountEntryInfo = new ReportsAccountEntryInfo();
    	reportsAccountEntryInfo.setBeginDate("2015-01-01");
    	reportsAccountEntryInfo.setEndDate("2016-12-31");
//    	reportsAccountEntryInfo.setTransType(TransType.P_INVEST_BONUS.getCode());
//    	reportsAccountEntryInfo.setTransType(TransType.C_INVEST_BONUS.getCode());
    	reportsAccountEntryInfo.setAccTypes(new String[]{"10110"});
    	 Page page = new Page(1, 10);
        try {
//        	PageData<ReportsPointDeduction> reportsPointDeductionPageDate = reportsPointDeductionSearchService.searchReportsPointDeduction(reportsPointDeduction, page);
//        	PageData<ReportsPointDeduction> reportsPointDeductionPageDate = reportsPointDeductionSearchService.searchReportsPointDeductionByChannel(reportsPointDeduction, page);
//        	PageData<ReportsAccountEntry> reportsAccountEntryPageDate = reportsAccountEntryService.searchCarAccountEntryDividend(reportsAccountEntryInfo, page);
        	PageData<ReportsAccountEntry> reportsAccountEntryPageDate = reportsAccountEntryService.searchCarAccountEntry(reportsAccountEntryInfo, page);
//        	PageData<ReportsAccountEntry> accountEntryPageDate = reportsAccountEntryService.searchEntAccountEntry(reportsAccountEntryInfo, page);
        	List<ReportsAccountEntry> reportsAccountEntryList = reportsAccountEntryPageDate.getResult();
        	
        	for(int i=0;i<reportsAccountEntryList.size();i++){
        		ReportsAccountEntry reportsAccountEntry = reportsAccountEntryList.get(i);
        		System.out.println("=========");
        		System.out.println(reportsAccountEntry.getHsResNo());
        		System.out.println(reportsAccountEntry.getAmount());
        		System.out.println(reportsAccountEntry.getFiscalDate());
        		System.out.println(reportsAccountEntry.getTransNo());
        		System.out.println(reportsAccountEntry.getSourceTransAmount());
        		System.out.println(reportsAccountEntry.getPointRate());
        		System.out.println("=========");
        	}
        } catch (HsException e) {
            
            System.out.println("HsException ========="+e.getMessage());
            e.printStackTrace();
        }
        
        
        
    }
    
}
