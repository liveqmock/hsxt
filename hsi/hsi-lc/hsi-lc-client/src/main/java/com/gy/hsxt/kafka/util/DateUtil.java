package com.gy.hsxt.kafka.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @ClassName: DateUtil 
 * @Description: 时间工具类 
 * @author Lee
 * @date 2015-7-8 下午2:31:16
 */
public class DateUtil {
	 
	/**
	 * 
	* @Title: getCurrentDate2String 
	* @Description: 获取当前系统时间转换为string
	* @param  无
	* @return 返回当前string类型时间    
	* @throws
	 */
	public static String getCurrentDate2String() {
	
		Date date=new Date();  
	    
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    
	    return formatter.format(date);
	}
}
