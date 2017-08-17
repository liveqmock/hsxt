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
import com.gy.hsxt.rp.common.bean.ReportAccountEntGiveIntegrel;
import com.gy.hsxt.rp.mapper.ReportAccountEntryBatchMapper;

/** 
 * 企业月送积分资源列表多线程类
 * @Package: com.gy.hsxt.ac.service.runnable  
 * @ClassName: AccountIntegralMonthFileRunnable 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2015-10-12 下午3:08:49 
 * @version V1.0 
 

 */
public class ReportAccountIntegralMonthFileRunnable implements Runnable{

    //记账任务记录
    private ReportAccountBatchJob accountBatchJob;
    
    //交易系统记录
    private Map<String, ReportAccountEntGiveIntegrel> accTransSystemMap;
    
    //企业月送积分资源列表数据时间段条件
    private Map<String, Object> accountEntryMap;
    
    //开始页
    private int beginPage;
    
    //
    private int remainder;
    
    /** 账务分录的接口服务 */
    private ReportAccountEntryBatchMapper accountEntryMapper;
    

    public ReportAccountIntegralMonthFileRunnable(ReportAccountBatchJob accountBatchJob, Map<String, ReportAccountEntGiveIntegrel> accTransSystemMap, ReportAccountEntryBatchMapper accountEntryMapper, Map<String, Object> accountEntryMap, int beginPage,int remainder){
        this.accountBatchJob = accountBatchJob;
        this.accTransSystemMap = accTransSystemMap;
        this.accountEntryMapper = accountEntryMapper;
        this.accountEntryMap = accountEntryMap;
        this.beginPage = beginPage;
        this.remainder = remainder;
    }
    
    public void run() {
        
        int row = Integer.valueOf(PropertyConfigurer.getProperty("rp.accountCheck.row"));
        int count = remainder/row;
        int num = remainder % row;
        if( num > 0){
            count++;
        }
        for(int x = 1; x < count + 1; x++){
            int startPage = beginPage*10 + x;
            Page page = new Page(startPage,row);
            PageContext.setPage(page); 
            try {
                List<ReportsAccountEntry> accountEntryList = accountEntryMapper.seachAccEntryListPageByTransType(accountEntryMap);
                if(!accountEntryList.isEmpty()){
                    StringBuilder writeFileData = new StringBuilder();
                    for(ReportsAccountEntry accountEntry : accountEntryList){
                        // 客户全局编号
                        writeFileData.append(accountEntry.getCustID() + "|");
                        // 根据客户全局编号和互生号计算的金额
                        writeFileData.append(accountEntry.getAmount());
                        writeFileData.append("\r\n");
                    }
                    ReportAccountEntGiveIntegrel accountEntGiveIntegrel = accTransSystemMap.get("EGIM");
                    accountEntGiveIntegrel.setTransSys("EGIM");
                    accountEntGiveIntegrel.write(writeFileData);
                }
            } catch (SQLException e) {
                e.getMessage();
            }
        }
    }
}
