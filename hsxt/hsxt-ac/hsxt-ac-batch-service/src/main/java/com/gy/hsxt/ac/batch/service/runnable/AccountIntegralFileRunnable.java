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

package com.gy.hsxt.ac.batch.service.runnable;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.gy.hsxt.ac.batch.mapper.AccountEntryBatchMapper;
import com.gy.hsxt.ac.bean.AccountBatchJob;
import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.ac.common.bean.AccountIntegralActive;
import com.gy.hsxt.ac.common.bean.PageContext;
import com.gy.hsxt.common.bean.Page;

/** 
 * 
 * @Package: com.gy.hsxt.ac.service.runnable  
 * @ClassName: AccountIntegralFileRunnable 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2015-9-22 下午3:59:35 
 * @version V1.0 
 

 */
public class AccountIntegralFileRunnable implements Runnable{

	
		//记账任务记录
		private AccountBatchJob accountBatchJob;
		
		private Map<String, AccountIntegralActive> accIntegralActiveMap;
		
		private AccountEntryBatchMapper accountEntryMapper;
		
		private Map<String, Object> accountEntryMap;
		
		private int beginRow;

		public AccountIntegralFileRunnable(AccountBatchJob accountBatchJob, Map<String, AccountIntegralActive> accIntegralActiveMap, AccountEntryBatchMapper accountEntryMapper, Map<String, Object> accountEntryMap, int beginRow){
			this.accountBatchJob = accountBatchJob;
			this.accIntegralActiveMap = accIntegralActiveMap;
			this.accountEntryMapper = accountEntryMapper;
			this.accountEntryMap = accountEntryMap;
			this.beginRow = beginRow;
		}
		
		public void run() {
			int sumRow = 100000;
			int row = 10000;
			int count = sumRow/row;
			for(int x=0;x<count;x++){
				Page page = new Page(beginRow+1+(x*row),beginRow+(x+1)*row);
				PageContext.setPage(page); 
				try {
					List<AccountEntry> accountEntryList = accountEntryMapper.seachAccEntryListPageByFisDate(accountEntryMap);
					if(!accountEntryList.isEmpty()){
						StringBuilder writeFileData = new StringBuilder();
						for(AccountEntry accountEntry : accountEntryList){
							//客户全局编号
							writeFileData.append(accountEntry.getCustID() + "|");
							writeFileData.append(accountEntry.getCustType());
							writeFileData.append("\r\n");
						}
						AccountIntegralActive accountIntegralActive = accIntegralActiveMap.get("AC");
						accountIntegralActive.write(writeFileData);
					}
				} catch (SQLException e) {
					e.getMessage();
				}
			}
		}
}
