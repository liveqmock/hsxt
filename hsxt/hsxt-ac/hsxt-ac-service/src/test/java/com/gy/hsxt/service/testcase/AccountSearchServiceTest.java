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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.ac.api.IAccountSearchService;
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ac.bean.AccountCustType;
import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.ac.bean.AccountEntryInfo;
import com.gy.hsxt.ac.bean.AccountStatusChange;
import com.gy.hsxt.ac.bean.AccountType;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/** 
 * 查询接口测试类
 * @Package: com.gy.hsxt.service.testcase  
 * @ClassName: AccountAdminServiceTest 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2015-10-12 下午5:40:28 
 * @version V1.0 
 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring/spring-global.xml"})
public class AccountSearchServiceTest {

    @Autowired
    public IAccountSearchService iAccountSearchService;
    
    @Test
    public void test() throws java.text.ParseException{
        
        try{
        	String custID = "0601016000020160128";
//        	String[] accTypes = new String[]{"10510","20610","30310"};
        	String accType = "20110";
//        	String beginDate = "2007-12-01";
//        	String endDate = "2016-02-29";
        	Integer businessType = 0;
//        	String transNo = "1020160225174039";
        	String sourceTransNo = "309102442133";
        	AccountEntryInfo accountEntryInfo = new AccountEntryInfo();
        	accountEntryInfo.setCustID(custID);
//        	accountEntryInfo.setAccTypes(accTypes);
        	accountEntryInfo.setAccType(accType);
//        	accountEntryInfo.setBeginDate(beginDate);
//        	accountEntryInfo.setEndDate(endDate);
        	accountEntryInfo.setBusinessType(businessType);
//        	accountEntryInfo.setTransNo(transNo);
        	accountEntryInfo.setSourceTransNo(sourceTransNo);
        	
        	Page page = new Page(1, 10);
        	PageData<AccountEntry> pageData = iAccountSearchService.searchAccEntryList(accountEntryInfo, page);
        	List<AccountEntry> list = pageData.getResult();
            for(int i=0;i<list.size();i++)
            {
            	AccountEntry accountEntry = list.get(i);
            	System.out.println(accountEntry.getAccType());
            	System.out.println(accountEntry.getAmount());
            	System.out.println(accountEntry.getTransType());
            	System.out.println(accountEntry.getAccBalanceNew());
            	System.out.println(accountEntry.getAccName());
            	System.out.println(accountEntry.getSubAmount());
            	System.out.println(accountEntry.getFiscalDate());
            }
            
        }catch(HsException ex){
            fail("ErrorCode:" + ex.getErrorCode() + " ErrorMessage:" + ex.getMessage() + "\n" + ex.getStackTrace());
        }
    
    }
    
    
}
