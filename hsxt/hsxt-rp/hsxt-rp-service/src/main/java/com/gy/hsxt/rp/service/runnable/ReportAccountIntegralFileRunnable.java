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

package com.gy.hsxt.rp.service.runnable;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.rp.bean.ReportAccountBatchJob;
import com.gy.hsxt.rp.bean.ReportsAccountEntry;
import com.gy.hsxt.rp.common.bean.PageContext;
import com.gy.hsxt.rp.common.bean.PropertyConfigurer;
import com.gy.hsxt.rp.common.bean.ReportAccountIntegralActive;
import com.gy.hsxt.rp.mapper.ReportAccountEntryBatchMapper;

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
public class ReportAccountIntegralFileRunnable implements Runnable{

	
		//记账任务记录
		private ReportAccountBatchJob accountBatchJob;
		
		private Map<String, ReportAccountIntegralActive> accIntegralActiveMap;
		
		private ReportAccountEntryBatchMapper accountEntryMapper;
		
		private Map<String, Object> accountEntryMap;
		
		private int beginPage;
		
		private int remainder;

		public ReportAccountIntegralFileRunnable(ReportAccountBatchJob accountBatchJob, Map<String, ReportAccountIntegralActive> accIntegralActiveMap, ReportAccountEntryBatchMapper accountEntryMapper, Map<String, Object> accountEntryMap, int beginPage,int remainder){
			this.accountBatchJob = accountBatchJob;
			this.accIntegralActiveMap = accIntegralActiveMap;
			this.accountEntryMapper = accountEntryMapper;
			this.accountEntryMap = accountEntryMap;
			this.beginPage = beginPage;
			this.remainder = remainder;
		}
		
		public void run() {
			int row = Integer.valueOf(PropertyConfigurer.getProperty("rp.accountCheck.row"));
			int count = remainder/row;
			int num = remainder % row;
	        if(num > 0){
	            count++;
	        }
			for(int x = 1; x < count + 1; x++){
			    int startPage = beginPage*10 + x;
	            Page page = new Page(startPage,row);
				PageContext.setPage(page);
				try {
					List<ReportsAccountEntry> accountEntryList = accountEntryMapper.seachAccEntryListPageByFisDate(accountEntryMap);
					if(!accountEntryList.isEmpty()){
						StringBuilder writeFileData = new StringBuilder();
						for(ReportsAccountEntry accountEntry : accountEntryList){
							//客户全局编号
							writeFileData.append(accountEntry.getCustID() + "|");
							writeFileData.append(accountEntry.getCustType());
							writeFileData.append("\r\n");
						}
						ReportAccountIntegralActive accountIntegralActive = accIntegralActiveMap.get("RP");
						accountIntegralActive.write(writeFileData);
					}
				} catch (SQLException e) {
					e.getMessage();
				}
			}
		}
}
