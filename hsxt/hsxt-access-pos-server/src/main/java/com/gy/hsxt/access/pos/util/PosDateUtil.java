
package com.gy.hsxt.access.pos.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosConstant;

/** 
 * @Description: 时间工具类

 * @author: liuzh 
 * @createDate: 2016年6月23日 下午3:00:13
 * @version V1.0 
 */

public class PosDateUtil {
	
	/**
	 * 转换时间戳格式为:yyyyMMddHHmmss
	 * @param strDate
	 * @return
	 */
	public static String getYmdhms(String strDate) {
        String pattern = PosConstant.TIME_FORMAT_NUMBER_YMDHMS;       
        String strDateTime = null;
        Date date = stringToDate(strDate,PosConstant.TIME_FORMAT_DATE_YMDHMS);
        SimpleDateFormat formater = new SimpleDateFormat(pattern);
        strDateTime = date == null ? null : formater.format(date);
        
        return strDateTime;
	}	
	
	/**
	 * 字符串型时间转换为为日期型
	 * @param str
	 * @param pattern
	 * @return
	 */
    public static Date stringToDate(String str, String pattern) {
        Date dateTime = null;
        try
        {
            if (str != null && !str.equals(""))
            {
                SimpleDateFormat formater = new SimpleDateFormat(pattern);
                dateTime = formater.parse(str);
            }
        }
        catch (Exception ex)
        {
        }
        return dateTime;
    }
    
	/**
	 * 查询当天的开始时间和结束时间
	 * @return
	 */
	public static String[] getTodayStartEndDateHMS() {		
        SimpleDateFormat sdf = new SimpleDateFormat(PosConstant.TIME_FORMAT_DATE_YMDHMS);
        Calendar calendar = Calendar.getInstance();

        String startDate = sdf.format(calendar.getTime()) + " 00:00:00";
		String endDate = sdf.format(new Date()) + " 23:59:59";
		
		return new String[]{startDate,endDate};
	}
	
	/**
	 * 查询前n天的开始时间和结束时间
	 * @param days 前n天  填写正整数
	 * @return
	 */
	public static String[] getBeforeDaysStartEndDateHMS(int days) {
        SimpleDateFormat sdf = new SimpleDateFormat(PosConstant.TIME_FORMAT_DATE_YMDHMS);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -days);
        
		String startDate = sdf.format(calendar.getTime()) + " 00:00:00";
		String endDate = sdf.format(new Date()) + " 23:59:59";
		
		return new String[]{startDate,endDate};
	}
	
	/**
	 * 查询当天的开始时间和结束时间
	 * @return
	 */
	public static String[] getTodayStartEndDateYMD() {		
        SimpleDateFormat sdf = new SimpleDateFormat(PosConstant.DATE_FORMAT_YMD);
        Calendar calendar = Calendar.getInstance();
		
        String startDate = sdf.format(calendar.getTime());
		String endDate = sdf.format(new Date());
		
		return new String[]{startDate,endDate};
	}
	
	/**
	 * 查询前n天的开始时间和结束时间
	 * @param days 前n天  填写正整数
	 * @return
	 */
	public static String[] getBeforeDaysStartEndDateYMD(int days) {
        SimpleDateFormat sdf = new SimpleDateFormat(PosConstant.DATE_FORMAT_YMD);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -days);
        
		String startDate = sdf.format(calendar.getTime());
		String endDate = sdf.format(new Date());
    	
		return new String[]{startDate,endDate};
	}
}


