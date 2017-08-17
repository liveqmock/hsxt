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

package com.gy.hsxt.rp.common.bean;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;

/** 
 * 
 * @Package: com.gy.hsxt.rp.common.bean  
 * @ClassName: RpTools 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2016-5-11 上午10:16:44 
 * @version V1.0 
 

 */
public class RpTools {
    
    
    /**
     * 获取当前日期的第一天
     * @param date
     * @return
     */
    public static Date getMonthStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int index = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, (1 - index));
        return calendar.getTime();
    }
 
    /**
     * 获取当前日期的最后一天
     * @param date
     * @return
     */
    public static Date getMonthEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        int index = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, (-index));
        return calendar.getTime();
    }
 
    /**
     * 获取当前日期的第一天
     * @param date
     * @return
     */
    public static Timestamp getMonthStartForTim(Date date){
        return Timestamp.valueOf(DateUtil.DateToString(getMonthStart(date))+" 00:00:00");
    }
    
    /**
     * 获取当前日期的最后一天
     * @param date
     * @return
     */
    public static Timestamp getMonthEndForTim(Date date){
        return Timestamp.valueOf(DateUtil.DateToString(getMonthEnd(date))+" 23:59:59");
    }
    
    //获取上月最后一日日期
    public static Date preMonthLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }
    

    //获取上个月第一天的日期
    public static Timestamp preMonthFirstDayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, -1);
        String monthStart = String.valueOf(sdf.format(calendar.getTime()));
        Timestamp monthStartDate = Timestamp.valueOf(monthStart + " 00:00:00");
        return monthStartDate;
    }
    //获取上个月最后一天的日期
    public static Timestamp preMonthLastDayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
        String monthEnd = String.valueOf(sdf.format(calendar.getTime()));
        Timestamp monthEndDate = Timestamp.valueOf(monthEnd + " 23:59:59");
        return monthEndDate;
    }
    
    /**
     * 验证开始、结束时间日期格式，开始时间时分秒为00:00:00,结束时间时分秒为23:59:59
     * 
     * @param beginDate
     *            开始时间
     * @param endDate
     *            结束时间
     * @return Map<String,String> 封装时间格式后的开始和结束时间
     * @throws HsException
     *             异常处理
     */
    public static Map<String, Timestamp> validateDateFormat(String beginDate, String endDate) throws HsException {
        Map<String, Timestamp> dateMap = new HashMap<String, Timestamp>();

        // 格式化日期并转换日期格式String-->TimeStamp
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 开始时间
        if (beginDate != null && !"".equals(beginDate))
        {
            try
            {
                Date date = sdf.parse(beginDate);
                beginDate = sdf.format(date) + " 00:00:00";
                dateMap.put("beginDate", Timestamp.valueOf(beginDate));
            }
            catch (ParseException e)
            {
                SystemLog.debug("HSXT_RP", "方法：validateDateFormat", RespCode.RP_PARAMETER_FORMAT_ERROR.getCode() + ","
                        + beginDate + " = " + beginDate + " ,日期格式错误，正确格式 yyyy-MM-dd");
                throw new HsException(RespCode.RP_PARAMETER_FORMAT_ERROR.getCode(), beginDate + " = " + beginDate
                        + " ,日期格式错误，正确格式 yyyy-MM-dd");
            }
        }

        // 结束时间
        if (endDate != null && !"".equals(endDate))
        {
            try
            {
                Date date = sdf.parse(endDate);
                endDate = sdf.format(date) + " 23:59:59";
                dateMap.put("endDate", Timestamp.valueOf(endDate));
            }
            catch (ParseException e)
            {
                SystemLog.debug("HSXT_RP", "方法：validateDateFormat", RespCode.RP_PARAMETER_FORMAT_ERROR.getCode() + ","
                        + endDate + " = " + endDate + " ,日期格式错误，正确格式 yyyy-MM-dd");
                throw new HsException(RespCode.RP_PARAMETER_FORMAT_ERROR.getCode(), endDate + " = " + endDate
                        + " ,日期格式错误，正确格式 yyyy-MM-dd");
            }
        }
        return dateMap;
    }
    
 /*   public static void main(String[] agrs){
        String str = "20160302";
        String startDate = RpTools.getMonthStartForStr(DateUtil.StringToDate(str));
        String endDate = RpTools.getMonthEndForStr(DateUtil.StringToDate(str));
        System.out.println("date========"+ startDate);
        System.out.println("date========"+ endDate);
    }*/

}
