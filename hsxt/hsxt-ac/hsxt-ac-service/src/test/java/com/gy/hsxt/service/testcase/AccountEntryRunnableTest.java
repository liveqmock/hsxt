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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.gy.hsxt.ac.api.IAccountEntryService;
import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.common.exception.HsException;

/** 
 * 
 * @Package: com.gy.hsxt.ac.service.runnable  
 * @ClassName: AccountEntryRunnableTest 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2015-9-14 上午11:17:50 
 * @version V1.0 
 

 */
public class AccountEntryRunnableTest implements Runnable{

	private IAccountEntryService iAccountEntryService;
	
	private Integer number;


	public AccountEntryRunnableTest(IAccountEntryService iAccountEntryService,Integer number) {
		this.number  = number;
		this.iAccountEntryService = iAccountEntryService;
	}
	
	public void run() {
		addAccountEntryInfos();
	}
	
	
	public void addAccountEntryInfos() {
		try {
			System.out.println("number====="+number);
			long lastTime = System.currentTimeMillis();
			String custId = number + "wxz";
			BigDecimal addAmount = BigDecimal.valueOf(10.00);
			BigDecimal subAmount = BigDecimal.valueOf(0);
			Timestamp tt = new Timestamp(System.currentTimeMillis()); 
			List<AccountEntry> accountEntryList = new ArrayList<AccountEntry>();
			for(int i=0;i<100000;i++){
				if(accountEntryList.size()<9){
					AccountEntry accountEntry = new AccountEntry();
					accountEntry.setBatchNo("00001");
					accountEntry.setAccType("11100");
					accountEntry.setWriteBack(0);
					accountEntry.setCustType(1);
					accountEntry.setAddAmount(String.valueOf(addAmount));
					accountEntry.setSubAmount(String.valueOf(subAmount));
					accountEntry.setTransType("2");
					accountEntry.setTransNo("2");
					StringBuffer strBuffer = new StringBuffer(custId);
					strBuffer.append(i);
					String newCustID = String.valueOf(strBuffer);
					accountEntry.setCustID(newCustID);
					accountEntryList.add(accountEntry);
				}else{
					AccountEntry accountEntry = new AccountEntry();
					accountEntry.setBatchNo("00001");
					accountEntry.setAccType("11100");
					accountEntry.setWriteBack(0);
					accountEntry.setCustType(1);
					accountEntry.setAddAmount(String.valueOf(addAmount));
					accountEntry.setSubAmount(String.valueOf(subAmount));
					accountEntry.setTransType("2");
					accountEntry.setTransNo("2");
					StringBuffer strBuffer = new StringBuffer(custId);
					strBuffer.append(i);
					String newCustID = String.valueOf(strBuffer);
					accountEntry.setCustID(newCustID);
					accountEntryList.add(accountEntry);
					iAccountEntryService.actualAccount(accountEntryList);
					accountEntryList = new ArrayList<AccountEntry>();
				}
			}
			long endTime = System.currentTimeMillis();
			System.out.println("datatime=" + (endTime - lastTime));
		} catch (HsException e) {
			e.printStackTrace();
		}
	}

}
