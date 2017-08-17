/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 文件同步系统-日期工具
 *
 * @Package :com.gy.hsxt.gpf.fss.utils
 * @ClassName : FssDateUtil
 * @Description : 日期工具
 * @Author : chenm
 * @Date : 2015/10/12 16:33
 * @Version V3.0.0.0
 */
public abstract class FssDateUtil extends DateFormatUtils {

    /**
     * 默认的日期格式：yyyy-MM-dd
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 日期格式：yyyyMMdd
     */
    public static final String SHORT_DATE_FORMAT = "yyyyMMdd";

    /**
     * 时分秒的日期格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时分秒的日期格式：yyyyMMddHHmmss
     */
    public static final String SHORT_DATE_TIME_FORMAT = "yyyyMMddHHmmss";

    /**
     * 上周
     */
    public static final int PREVIOUS_WEEK = -7;

    /**
     * 本周
     */
    public static final int THIS_WEEK = 0;

    /**
     * 下周
     */
    public static final int NEXT_WEEK = 7;

    /**
     * 上一个月
     */
    public static final int PREVIOUS_MONTH = -1;

    /**
     * 本月
     */
    public static final int THIS_MONTH = 0;

    /**
     * 下一个月
     */
    public static final int NEXT_MONTH = 1;


    /**
     * 获取某周的某一天
     * 格式：yyyy-MM-dd
     *
     * @param weekField 星期几
     * @param offset    日期的偏移量
     * @return String
     */
    public static String obtainWeekDay(int weekField, int offset) {
        return obtainWeekDay(weekField, offset, DEFAULT_DATE_FORMAT);
    }

    /**
     * 获取某周的某一天
     *
     * @param weekField 星期几
     * @param offset    日期的偏移量
     * @param pattern   日期的格式
     * @return String
     */
    public static String obtainWeekDay(int weekField, int offset, String pattern) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, weekField);
        c.add(Calendar.DAY_OF_YEAR, offset);
        return format(c.getTime(), pattern);
    }

    /**
     * 默认本周
     *
     * @param weekField 周几
     * @return string
     */
    public static String obtainThisWeekDay(int weekField) {
        return obtainWeekDay(weekField, THIS_WEEK);
    }

    /**
     * 默认本周
     *
     * @param weekField 星期几
     * @param pattern   格式
     * @return string
     */
    public static String obtainThisWeekDay(int weekField, String pattern) {
        return obtainWeekDay(weekField, THIS_WEEK, pattern);
    }

    /**
     * 获取某月某日
     *
     * @param moffset 月偏移量
     * @param doffset 日偏移量
     * @param isLast  是否是月末
     * @param pattern  格式
     * @return string
     */
    private static String obtainMonthDay(int moffset, int doffset, boolean isLast,String pattern) {
        Calendar c = Calendar.getInstance();
        //默认从第一天开始
        c.set(Calendar.DAY_OF_MONTH, 1);
        //月份偏移量 月末的算法：加一个月 减一天
        if (isLast) {
            c.add(Calendar.MONTH, moffset + 1);
        } else {
            c.add(Calendar.MONTH, moffset);
        }
        //日期偏移量
        c.add(Calendar.DAY_OF_MONTH, doffset);
        return format(c.getTime(), pattern);
    }

    /**
     * 获取某月某日
     *
     * @param moffset 月偏移量
     * @param doffset 日偏移量 以每个月1日为基准
     * @param pattern 日期格式
     * @return string
     */
    public static String obtainMonthDay(int moffset, int doffset,String pattern) {
        return obtainMonthDay(moffset, doffset, false,pattern);
    }

    /**
     * 某月的最后一天
     *
     * @param moffset 月偏移量
     * @param pattern 格式
     * @return string
     */
    public static String obtainMonthLastDay(int moffset,String pattern) {
        return obtainMonthDay(moffset, -1, true,pattern);
    }
    /**
     * 某月的最后一天
     *
     * @param moffset 月偏移量
     * @return string
     */
    public static String obtainMonthLastDay(int moffset) {
        return obtainMonthDay(moffset, -1, true,DEFAULT_DATE_FORMAT);
    }

    /**
     * 某月的第一天
     *
     * @param moffset 月偏移量
     * @return string
     */
    public static String obtainMonthFirstDay(int moffset,String pattern) {
        return obtainMonthDay(moffset, 0,pattern);
    }

    /**
     * 某月的第一天
     *
     * @param moffset 月偏移量
     * @return string
     */
    public static String obtainMonthFirstDay(int moffset) {
        return obtainMonthDay(moffset, 0,DEFAULT_DATE_FORMAT);
    }


    /**
     * 获取今天日期
     *
     * @param pattern 格式
     * @return string
     */
    public static String obtainToday(String pattern) {
        return format(new Date(), pattern);
    }

}
