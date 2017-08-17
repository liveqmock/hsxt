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

package com.gy.hsxt.bs.batch;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.apply.interfaces.IResFeeResolveService;

/**
 * @Description: 资源费日终分配
 * 
 * @Package: com.gy.hsxt.bs.batch
 * @ClassName: BatchResFeeAlloc
 * @author: yangjianguo
 * @date: 2016-3-30 下午5:46:24
 * @version V1.0
 */
@Service
public class BatchResFeeAlloc extends AbstractBatchService {

    @Autowired
    IResFeeResolveService resFeeResolveService;

    /**
     * @param executeId
     * @param scanDate
     * @throws Exception
     * @see com.gy.hsxt.bs.batch.AbstractBatchService#execute(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void execute(String executeId, String scanDate) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        // 日终批记账会计日期为昨天的最后一秒
        String fiscalDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
        
        List<String> applyIds = resFeeResolveService.queryUnAllocApplyIds();
        for(String applyId : applyIds){
            try{
                resFeeResolveService.accoutingForApply(applyId, fiscalDate);
            }catch(Exception e){
             // 日终批处理资源费分配的时候异常不抛出
                SystemLog.error(this.getClass().getName(), "批量执行系统资源费分配", "分配申报编号["+applyId+"]会计日期[" + fiscalDate + "]资源费分配失败",
                        e);
            }
        }
        this.getBatchServiceListener().reportStatus(executeId, 0, "执行成功");
    }
}
