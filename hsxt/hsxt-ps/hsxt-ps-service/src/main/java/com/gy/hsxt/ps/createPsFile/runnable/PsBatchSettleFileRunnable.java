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

package com.gy.hsxt.ps.createPsFile.runnable;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.common.PageContext;
import com.gy.hsxt.ps.common.PropertyConfigurer;
import com.gy.hsxt.ps.common.PsException;
import com.gy.hsxt.ps.createPsFile.bean.BatSettleInfo;
import com.gy.hsxt.ps.createPsFile.bean.PsEntrySettle;
import com.gy.hsxt.ps.createPsFile.mapper.PsBatchSettleFileMapper;

/**
 * 企业月送积分资源列表多线程类
 * 
 * @Package: com.gy.hsxt.ps.createPsFile.runnable  
 * @ClassName: PsBatchSettleFileRunnable 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2016-3-22 上午9:07:51 
 * @version V1.0
 */
public class PsBatchSettleFileRunnable implements Runnable {

    // 交易系统记录
    private Map<String, PsEntrySettle> psTransSystemMap;

    // 企业月送积分资源列表数据时间段条件
    private Map<String, Object> psEntryMap;

    // 最后一行剩余数
    private int remainder;
    
    // 开始页
    private int beginPage;
    
    // 总记录数
    private int totalNum;

    /** 账务分录的接口服务 */
    private PsBatchSettleFileMapper psBatchSettleFileMapper;

    public PsBatchSettleFileRunnable(Map<String, PsEntrySettle> psTransSystemMap,
            PsBatchSettleFileMapper psBatchSettleFileMapper, Map<String, Object> psEntryMap, int beginPage,int remainder,int totalNum) {
        this.psTransSystemMap = psTransSystemMap;
        this.psBatchSettleFileMapper = psBatchSettleFileMapper;
        this.psEntryMap = psEntryMap;
        this.beginPage = beginPage;
        this.remainder = remainder;
        this.totalNum = totalNum;
    }

    public void run() throws HsException {

        int row = Integer.parseInt(PropertyConfigurer.getProperty("ps.accountCheck.row"));
        int count = remainder / row;
        int num = remainder % row;
        if(num > 0){
            count++;
        }
        for (int x = 1; x < count+1; x++)
        {
            int startPage = beginPage*10 + x;
            Page page = new Page(startPage,row);
            PageContext.setPage(page);
            try
            {
                List<BatSettleInfo> allocList = psBatchSettleFileMapper.searchPsEntryPage(psEntryMap);
                if (!allocList.isEmpty())
                {
                    StringBuilder writeFileData = new StringBuilder();
                    if(beginPage == 0){
                        writeFileData.append(totalNum);
                        writeFileData.append("\r\n");
                    }
                    for (BatSettleInfo info : allocList)
                    {
                        // 订单号
                        writeFileData.append(info.getOrderNo() + ",");
                        // 企业互生号
                        writeFileData.append(info.getEntHsResNo() + ",");
                        // 消费者互生号
                        writeFileData.append(info.getPerCustId() + ",");
                        // 结算状态
                        writeFileData.append(info.getSettleStatus() + ",");
                        // 业务类型
                        writeFileData.append(info.getBussnessType());
                        writeFileData.append("\r\n");
                    }
                    PsEntrySettle psEntrySettle = psTransSystemMap.get("PS");
                    psEntrySettle.write(writeFileData);
                }
            }
            catch (SQLException e)
            {
                // 判断原积分单非正常状态, 则抛出互生异常
                PsException.psThrowException(new Throwable().getStackTrace()[0],
                        e.getErrorCode(), "企业月送积分资源列表多线程类报错",e);
            }
        }
    }
}
