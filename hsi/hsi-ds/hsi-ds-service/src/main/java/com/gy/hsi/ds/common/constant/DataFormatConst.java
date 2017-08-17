package com.gy.hsi.ds.common.constant;
/**
 * 时间格式常量类
 * 
 * @Package: com.gy.hsi.ds.common.constant  
 * @ClassName: DataFormatConstants 
 * @Description: TODO
 *
 * @author: yangyp 
 * @date: 2015年10月16日 下午3:23:07 
 * @version V3.0
 */
public class DataFormatConst {

    // 后端通用的时间格式
    public static String COMMON_TIME_FORMAT = "yyyyMMddHHmmss";
    
    public static String MILLIS_TIME_FORMAT = "yyyyMMddHHmmssSSS";
    
    // 前端显示通用的时间格式
    public static String DISPLAY_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    // 前端显示通用的日期格式
    public static String DISPLAY_DATE_FORMAT = "yyyy-MM-dd";

    // 后端通用的时间格式
    public static String COMMON_DATE_FORMAT = "yyyyMMdd";

    /**
     * 日期开始时间后缀
     */
    public static final String DATE_DAY_START_SUFFIX = "000000";
    /**
     * 日期结束时间后缀
     */
    public static final String DATE_DAY_END_SUFFIX = "235959";

}
