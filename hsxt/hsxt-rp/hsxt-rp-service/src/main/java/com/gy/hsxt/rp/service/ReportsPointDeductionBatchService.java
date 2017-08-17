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

package com.gy.hsxt.rp.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.api.IDSBatchService;
import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.mapper.ReportsPointDeductionMapper;

/** 
 * 消费积分扣除每日统计
 * @Package: com.gy.hsxt.rp.service  
 * @ClassName: ReportsSystemResourceService 
 * @Description: TODO
 *
 * @author: maocan 
 * @date: 2016-1-6 上午10:51:26 
 * @version V1.0 
 */

@Service
public class ReportsPointDeductionBatchService implements IDSBatchService {

    
    @Autowired
    private ReportsPointDeductionMapper reportsPointDeductionMapper;
    
    protected static final Logger LOG = LoggerFactory.getLogger(ReportsPointDeductionBatchService.class);
    /**
     * 回调监听类
     */
    @Autowired
    private IDSBatchServiceListener listener;
    
    @Override
    public boolean excuteBatch(String executeId, Map<String, String> args) 
    {
        boolean result=true;
        if(listener!=null){
            LOG.info("执行中!!!!");
            // 发送进度通知
            listener.reportStatus(executeId, 2, "执行中");
            
            try
            {
            	String beginDate = args.get("beginDate");
				String endDate = args.get("endDate");
				Integer poReturnvalue = generatePointDeduction(beginDate,endDate);
                if(poReturnvalue == 0)
                {
					LOG.info("执行成功!!!!");
	                // 发送进度通知
	                listener.reportStatus(executeId, 0,"执行成功");
                }
                else
                {
                	LOG.info("异常，执行失败!!!!");
                    // 发送进度通知
                    listener.reportStatus(executeId, 1,"执行失败");
                    result=false;
                }
            }
            catch (Exception e)
            {
                LOG.info("异常，执行失败!!!!");
                // 发送进度通知
                listener.reportStatus(executeId, 1,"执行失败");
                result=false;
            }
        }
        return result;
    }
    
    /**
     * 生成消费积分扣除每日统计数据
     * @throws HsException
     */
    public Integer generatePointDeduction(String beginDate, String endDate) throws HsException{
    	
    	Map<String, Object> welfareScoreMap = new HashMap<String, Object>();
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String yesterdayDate = sdf.format(preYesterday());
    	
    	// 昨天开始时间
		if(beginDate == null || beginDate.length() == 0)
		{
			beginDate = yesterdayDate + " 00:00:00";
		}
		else
		{
			beginDate = beginDate + " 00:00:00";
		}
		
		Timestamp beginDateTim = Timestamp.valueOf(beginDate);

		// 昨天结束时间
		if(endDate == null || endDate.length() == 0)
		{
			endDate = yesterdayDate + " 23:59:59";
		}
		else
		{
			endDate = endDate + " 23:59:59";
		}
		
		Timestamp endDateTim = Timestamp.valueOf(endDate);
		
		Integer poReturnvalue = 0;
		
		// 开始时间
		welfareScoreMap.put("beginDate", beginDateTim);
		// 结束时间
		welfareScoreMap.put("endDate", endDateTim);
		
		welfareScoreMap.put("poReturnvalue", poReturnvalue);
    	
    	//生成消费积分扣除每日统计数据
    	try {
			reportsPointDeductionMapper.generatePointDeduction(welfareScoreMap);
		} catch (Exception e) {
			SystemLog.error("HSXT_RP", "方法：generatePointDeduction", RespCode.RP_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
		}
    	return (Integer)welfareScoreMap.get("poReturnvalue");
    }
    
	// 获取昨日日期
	public Date preYesterday() {
		Calendar calendar = Calendar.getInstance();// 获取日历
		calendar.setTime(new Date());// 把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1); // 设置为前一天
		return calendar.getTime();
	}
}
