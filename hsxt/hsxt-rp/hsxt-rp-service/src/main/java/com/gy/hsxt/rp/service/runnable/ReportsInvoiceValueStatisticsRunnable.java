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

import java.util.List;
import java.util.Map;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.rp.bean.ReportsInvoiceValueStatistics;
import com.gy.hsxt.rp.common.bean.PageContext;
import com.gy.hsxt.rp.common.bean.PropertyConfigurer;
import com.gy.hsxt.rp.common.bean.ReportsInvoiceValueStatisticsExportFile;
import com.gy.hsxt.rp.mapper.ReportsInvoiceValueStatisticsMapper;

/** 
 * 
 * @Package: com.gy.hsxt.rp.service.runnable  
 * @ClassName: ReportsInvoiceValueStatisticsRunnable 
 * @Description: TODO
 *
 * @author: maocan 
 * @date: 2016-1-16 下午3:59:35 
 * @version V1.0 
 

 */
public class ReportsInvoiceValueStatisticsRunnable implements Runnable{

		private Map<String, ReportsInvoiceValueStatisticsExportFile> reportsExportFileMap;
		
		private ReportsInvoiceValueStatisticsMapper reportsInvoiceValueStatisticsMapper;
		
		private Map<String, Object> welfareScoreMap;
		
		private int beginPage;
		
		//当前记账数量
		private int remainder;

		public ReportsInvoiceValueStatisticsRunnable(Map<String, ReportsInvoiceValueStatisticsExportFile> reportsExportFileMap, ReportsInvoiceValueStatisticsMapper reportsInvoiceValueStatisticsMapper, Map<String, Object> welfareScoreMap, int beginPage, int remainder){
			this.reportsExportFileMap = reportsExportFileMap;
			this.reportsInvoiceValueStatisticsMapper = reportsInvoiceValueStatisticsMapper;
			this.welfareScoreMap = welfareScoreMap;
			this.beginPage = beginPage;
			this.remainder = remainder;
		}
		
		public void run() {
			
			// 每次查询条数
			int row = Integer.valueOf(PropertyConfigurer
					.getProperty("rp.reportsCheck.row"));
			
			int count = remainder/row;
			int num = remainder % row;
            if (num > 0) {
                count++;
            }
			for(int x=1;x<=count;x++){
			    int startPage = beginPage*10 + x;
                Page page = new Page(startPage,row);
				PageContext.setPage(page); 
				try {
					List<ReportsInvoiceValueStatistics> reportsInvoiceValueStatisticsList = reportsInvoiceValueStatisticsMapper.searchEntInvoiceValueStatisticsListPage(welfareScoreMap);
					if(!reportsInvoiceValueStatisticsList.isEmpty()){
						StringBuilder writeFileData = new StringBuilder();
						for(ReportsInvoiceValueStatistics reportsInvoiceValueStatistics : reportsInvoiceValueStatisticsList){
							//客户全局编号
							writeFileData.append(reportsInvoiceValueStatistics.getCustId() + "|");
							writeFileData.append(reportsInvoiceValueStatistics.getCustName() + "|");
							writeFileData.append(reportsInvoiceValueStatistics.getBizType() + "|");
							writeFileData.append(reportsInvoiceValueStatistics.getAmount());
							writeFileData.append("\r\n");
						}
						ReportsInvoiceValueStatisticsExportFile reportsExportFile = reportsExportFileMap.get("IVS_BS");
						reportsExportFile.write(writeFileData);
					}
				} catch (Exception e) {
					e.getMessage();
				}
			}
		}
}
