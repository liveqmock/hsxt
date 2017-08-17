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
import com.gy.hsxt.rp.bean.ReportsWelfareScore;
import com.gy.hsxt.rp.common.bean.PageContext;
import com.gy.hsxt.rp.common.bean.PropertyConfigurer;
import com.gy.hsxt.rp.common.bean.ReportsWelfareScoreExportFile;
import com.gy.hsxt.rp.mapper.ReportsSystemResourceMapper;

/** 
 * 
 * @Package: com.gy.hsxt.ac.service.runnable  
 * @ClassName: ReportsWelfareScoreRunnable 
 * @Description: TODO
 *
 * @author: maocan 
 * @date: 2016-1-16 下午3:59:35 
 * @version V1.0 
 

 */
public class ReportsWelfareScoreRunnable implements Runnable{

	
		
		private Map<String, ReportsWelfareScoreExportFile> reportsExportFileMap;
		
		private ReportsSystemResourceMapper reportsSystemResourceMapper;
		
		private Map<String, Object> welfareScoreMap;
		
		private int beginPage;
        
        private int remainder;

		public ReportsWelfareScoreRunnable(Map<String, ReportsWelfareScoreExportFile> reportsExportFileMap, ReportsSystemResourceMapper reportsSystemResourceMapper, Map<String, Object> welfareScoreMap, int beginPage, int remainder){
			this.reportsExportFileMap = reportsExportFileMap;
			this.reportsSystemResourceMapper = reportsSystemResourceMapper;
			this.welfareScoreMap = welfareScoreMap;
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
			for(int x=1;x<=count;x++){
			    int startPage = beginPage*10 + x;
                Page page = new Page(startPage,row);
                PageContext.setPage(page);
				try {
					List<ReportsWelfareScore> reportsWelfareScoreList = reportsSystemResourceMapper.searchWelfareScoreListPage(welfareScoreMap);
					if(!reportsWelfareScoreList.isEmpty()){
						StringBuilder writeFileData = new StringBuilder();
						for(ReportsWelfareScore reportsWelfareScore : reportsWelfareScoreList){
							//客户全局编号
							writeFileData.append(reportsWelfareScore.getCustId() + "|");
							writeFileData.append(reportsWelfareScore.getHsResNo() + "|");
							writeFileData.append(reportsWelfareScore.getPerName() + "|");
							writeFileData.append(reportsWelfareScore.getConsumeScore() + "|");
							writeFileData.append(reportsWelfareScore.getInvestScore());
							writeFileData.append("\r\n");
						}
						ReportsWelfareScoreExportFile reportsExportFile = reportsExportFileMap.get("WS_PS");
						reportsExportFile.write(writeFileData);
					}
				} catch (Exception e) {
					e.getMessage();
				}
			}
		}
}
