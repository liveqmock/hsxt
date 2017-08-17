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
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.ac.api.IAccountAdminService;
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ac.bean.AccountCustType;
import com.gy.hsxt.ac.bean.AccountStatusChange;
import com.gy.hsxt.ac.bean.AccountType;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/** 
 * 账户管理测试类
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
public class AccountAdminServiceTest {

    @Autowired
    public IAccountAdminService iAccountAdminService;
    
    @Test
    public void test() throws java.text.ParseException{
        try{
            List<AccountType> accountTypeList = new ArrayList<AccountType>();
            AccountType accountType = new AccountType();
            accountType.setAccType("66688");
            accountType.setAccName("wxz0321HHH");
            accountType.setCurrencyCode("CNY");
            accountType.setAccTypeDescription("good test3221GGG");
            accountTypeList.add(accountType);
            AccountType accountType1 = new AccountType();
            accountType1.setAccType("66570");
            accountType1.setAccName("wxzbb1");
            accountType1.setCurrencyCode("JPY");
            accountType1.setAccTypeDescription("good test32333311");
            accountTypeList.add(accountType1);
            AccountStatusChange accountStatusChange = new AccountStatusChange();
            accountStatusChange.setCustID("11111");
            
//            iAccountAdminService.addAccType(accountType1);
//            iAccountAdminService.addAccTypes(accountTypeList);
//            iAccountAdminService.updateAccType(accountType1);
//            iAccountAdminService.updateAccTypes(accountTypeList);
//            iAccountAdminService.deleteAccountType("66570");
//            iAccountAdminService.deleteAccountTypes(accountTypeList);
//            
            List<AccountCustType> custAccTypeList = new ArrayList<AccountCustType>();
            AccountCustType custAccType = new AccountCustType();
            custAccType.setAccType("00531");
            custAccType.setCustType(4);
            BigDecimal balanceMin = BigDecimal.valueOf(0);
            custAccType.setBalanceMin("0");
            BigDecimal balanceMax = BigDecimal.valueOf(100000);
            custAccType.setBalanceMax("555636");
            custAccTypeList.add(custAccType);
            AccountCustType custAccType1 = new AccountCustType();
            custAccType1.setAccType("02265");
            custAccType1.setCustType(3);
            //custAccType1.setBalanceMin(String.valueOf(balanceMin));
            //custAccType1.setBalanceMax(String.valueOf(balanceMax));
            custAccTypeList.add(custAccType1);
            
//            iAccountAdminService.addCustAccType(custAccType);
//            iAccountAdminService.addCustAccTypes(custAccTypeList);
//            iAccountAdminService.updateCustAccType(custAccType);
//            iAccountAdminService.updateCustAccTypes(custAccTypeList);
//            iAccountAdminService.deleteCustAccType(4,"00531");
//            iAccountAdminService.deleteCustAccTypes(custAccTypeList);

            List<AccountBalance> accountBalanceList = new ArrayList<AccountBalance>();
            AccountBalance accountBalance = new AccountBalance();
            String accBalance = "0";
            accountBalance.setAccBalance(accBalance);
            accountBalance.setAccStatus(0);
            accountBalance.setCustID("212334556722");
            accountBalance.setAccType(com.gy.hsxt.common.constant.AccountType.ACC_TYPE_POINT10110.getCode());
            accountBalance.setCustType(1);
            accountBalanceList.add(accountBalance);
            AccountBalance accountBalance1 = new AccountBalance();
            BigDecimal accBalance1 = BigDecimal.valueOf(2000000000);
            accountBalance1.setAccBalance(String.valueOf(accBalance1));
            accountBalance1.setAccStatus(0);
            accountBalance1.setCustID("212334556722");
            accountBalance1.setAccType("11320");
            accountBalance1.setCustType(2);
            //accountBalance.setHsResNo("");
            accountBalanceList.add(accountBalance1);
            
          iAccountAdminService.openAccount(accountBalance);
//          iAccountAdminService.openAccounts(accountBalanceList);
//          iAccountAdminService.updateAccountBalance(accountBalance);
//          iAccountAdminService.updateAccountBalances(accountBalanceList);      
            
            List<AccountStatusChange> accStatusChangeList = new ArrayList<AccountStatusChange>();
            AccountStatusChange accStatusChange = new AccountStatusChange();
            accStatusChange.setCustID("11111");
            accStatusChange.setAccType("11");
            accStatusChange.setHsResNo("1111");
            accStatusChange.setAccStatusOld(0);
            accStatusChange.setAccStatusNew(1);
            accStatusChange.setRemarks("test");
            accStatusChange.setUpdatedby("wxz");
            accStatusChangeList.add(accStatusChange);
            
            AccountStatusChange accStatusChange1 = new AccountStatusChange();
            accStatusChange1.setCustID("11111");
            accStatusChange1.setAccType("11");
            accStatusChange1.setHsResNo("1111");
            accStatusChange1.setAccStatusOld(1);
            accStatusChange1.setAccStatusNew(2);
            accStatusChange1.setRemarks("test");
            accStatusChange1.setUpdatedby("wxz");
            accStatusChangeList.add(accStatusChange1);
            
//          iAccountAdminService.addAccStatusChange(accStatusChange);
//          iAccountAdminService.addAccStatusChanges(accStatusChangeList);
            
            String custID = "212334556722";
            String accType = "11";
            Page page = new Page(1,10);
            

            
        }catch(HsException ex){
            fail("ErrorCode:" + ex.getErrorCode() + " ErrorMessage:" + ex.getMessage() + "\n" + ex.getStackTrace());
        }
    
    }
    
    
}
