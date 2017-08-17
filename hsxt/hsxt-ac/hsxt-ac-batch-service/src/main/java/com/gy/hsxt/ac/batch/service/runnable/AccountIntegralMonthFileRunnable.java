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
import com.gy.hsxt.ac.common.bean.AccountEntGiveIntegrel;
import com.gy.hsxt.ac.common.bean.PageContext;
import com.gy.hsxt.common.bean.Page;

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
public class AccountIntegralMonthFileRunnable implements Runnable{

    //记账任务记录
    private AccountBatchJob accountBatchJob;
    
    //交易系统记录
    private Map<String, AccountEntGiveIntegrel> accTransSystemMap;
    
    //企业月送积分资源列表数据时间段条件
    private Map<String, Object> accountEntryMap;
    
    //开始行
    private int beginRow;
    
    /** 账务分录的接口服务 */
    private AccountEntryBatchMapper accountEntryMapper;
    

    public AccountIntegralMonthFileRunnable(AccountBatchJob accountBatchJob, Map<String, AccountEntGiveIntegrel> accTransSystemMap, AccountEntryBatchMapper accountEntryMapper, Map<String, Object> accountEntryMap, int beginRow){
        this.accountBatchJob = accountBatchJob;
        this.accTransSystemMap = accTransSystemMap;
        this.accountEntryMapper = accountEntryMapper;
        this.accountEntryMap = accountEntryMap;
        this.beginRow = beginRow;
    }
    
    public void run() {
        
        int sumRow = 100000;
        int row = 10000;
        int count = sumRow/row;
        for(int x = 0; x < count; x++){
            Page page = new Page(beginRow + 1 + (x * row), beginRow + (x + 1) * row);
            PageContext.setPage(page); 
            try {
                List<AccountEntry> accountEntryList = accountEntryMapper.seachAccEntryListPageByTransType(accountEntryMap);
                if(!accountEntryList.isEmpty()){
                    StringBuilder writeFileData = new StringBuilder();
                    for(AccountEntry accountEntry : accountEntryList){
                        // 客户全局编号
                        writeFileData.append(accountEntry.getCustID() + "|");
                        // 互生号
                        writeFileData.append(accountEntry.getHsResNo() + "|");
                        // 根据客户全局编号和互生号计算的金额
                        writeFileData.append(accountEntry.getAmount());
                        writeFileData.append("\r\n");
                    }
                    AccountEntGiveIntegrel accountEntGiveIntegrel = accTransSystemMap.get("EGIM");
                    accountEntGiveIntegrel.setTransSys("EGIM");
                    accountEntGiveIntegrel.write(writeFileData);
                }
            } catch (SQLException e) {
                e.getMessage();
            }
        }
    }
}
