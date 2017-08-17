package com.gy.hsxt.common.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期工具类
 * 
 * @category 日期工具类
 * @className DateUtil
 * @description 日期工具类
 * @author zhucy
 * @createDate 2014-7-15 下午4:05:43
 * @updateUser zhucy
 * @updateDate 2014-7-15 下午4:05:43
 * @updateRemark 新增
 * @version v0.0.1
 */
public class DateUtil {
    /**
     * 默认的日期格式：yyyy-MM-dd
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 默认的是日期时间格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_TIME_FORMAT = "yyyyMMddHHmmss";

    public static final String DEFAULT_DATE_TIME_FORMAT_CN = "yyyy年MM月dd日 HH时mm分ss秒";

    public static final String DATE_FORMAT = "yyyyMMdd";

    /** 时分秒 **/
    public static final String DATE_FORMAT_HHmmss = "HHmmss";

    /** 月日 **/
    public static final String DATE_FORMAT_MMdd = "MMdd";

    public static final String START_MONTH_DAY = "START_MONTH_DAY";

    public static final String END_MONTH_DAY = "END_MONTH_DAY";

    public static final String START_WEEK_DAY = "START_WEEK_DAY";

    public static final String END_WEEK_DAY = "END_WEEK_DAY";

    /**
     * 当前日一个月的起始日期
     * 
     * @author leiyt
     * @createDate 2014-10-31 上午9:46:23
     * @return
     */
    public static Map<String, Object> getMonthDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(DateUtil.START_MONTH_DAY, sdf.format(calendar.getTime()) + " 00:00:00");
        map.put(DateUtil.END_MONTH_DAY, sdf.format(new Date()) + " 23:59:59");
        return map;
    }

    /**
     * 当前日一周内的起始日期
     * 
     * @author leiyt
     * @createDate 2014-10-31 上午10:07:32
     * @return
     */
    public static Map<String, Object> getWeekDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -6);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(DateUtil.START_WEEK_DAY, sdf.format(calendar.getTime()) + " 00:00:00");
        map.put(DateUtil.END_WEEK_DAY, sdf.format(new Date()) + " 23:59:59");
        return map;
    }

    /**
     * 
     * @author:likui
     * @created:2014年9月10日上午11:49:18
     * @desc:获取当前日期
     * @param:@return
     * @param:Date
     * @throws
     */
    public static Date getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        Date currDate = cal.getTime();

        return currDate;
    }

    /**
     * 
     * @author:likui
     * @created:2014年9月10日上午11:49:39
     * @desc:根据日期格式获取当前日期
     * @param:@param format
     * @param:@return
     * @param:Date
     * @throws
     */
    public static Date getCurrentDate(String format) {
        SimpleDateFormat dFormat = setFormat(format);
        try
        {
            return dFormat.parse(dFormat.format(getCurrentDate()));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private static SimpleDateFormat setFormat(String format) {
        if (StringUtils.isBlank(format))
        {
            format = DEFAULT_DATE_TIME_FORMAT;
        }
        return new SimpleDateFormat(format);
    }

    /**
     * 功能: 将日期对象按照某种格式进行转换，返回转换后的字符串
     * 
     * @param date
     *            日期对象
     * @param pattern
     *            转换格式 例：yyyy-MM-dd
     */
    public static String DateToString(Date date, String pattern) {
        String strDateTime = null;
        SimpleDateFormat formater = new SimpleDateFormat(pattern);
        strDateTime = date == null ? null : formater.format(date);
        return strDateTime;
    }

    /**
     * 功能: 将传入的日期对象按照yyyy-MM-dd格式转换成字符串返回
     * 
     * @param date
     *            日期对象
     * @return String
     */
    public static String DateToString(Date date) {
        return date == null ? null : DateToString(date, DEFAULT_DATE_FORMAT);
    }

    /**
     * 功能: 将传入的日期对象按照yyyy-MM-dd HH:mm:ss格式转换成字符串返回
     * 
     * @param date
     *            日期对象
     * @return String
     */
    public static String DateTimeToString(Date date) {
        return date == null ? null : DateToString(date, DEFAULT_DATE_TIME_FORMAT);
    }

    /**
     * 功能: 将传入的 时间戳对象按照yyyy-MM-dd HH:mm:ss格式转换成字符串返回
     * 
     * @param timestamp
     *            时间戳对象
     * @return String
     */
    public static String DateTimeToString(Timestamp timestamp) {
        return timestamp == null ? null : DateToString(new Date(timestamp.getTime()), DEFAULT_DATE_TIME_FORMAT);
    }

    /**
     * 功能: 将传入的日期对象按照yyyy-MM-dd HH:mm:ss格式转换成字符串返回
     * 
     * @param date
     *            日期对象
     * @return String
     */
    public static String DateTimeToStringNO(Date date) {
        return date == null ? null : DateToString(date, DATE_TIME_FORMAT);
    }

    /**
     * 功能: 将传入的日期对象按照yyyy-MM-dd HH:mm:ss格式转换成字符串返回
     * 
     * @param date
     *            日期对象
     * @return String
     */
    public static String DateTimeToStringCN(Date date) {
        return date == null ? null : DateToString(date, DEFAULT_DATE_TIME_FORMAT_CN);
    }

    public static String timestampToString(Timestamp datetime, String pattern) {

        if (null == datetime)
        {
            return null;
        }

        return setFormat(pattern).format(datetime);
    }

    /**
     * 功能: 将插入的字符串按格式转换成对应的日期对象
     * 
     * @param str
     *            字符串
     * @param pattern
     *            格式
     * @return Date
     */
    public static Date StringToDate(String str, String pattern) {
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
     * 功能: 将传入的字符串按yyyy-MM-dd格式转换成对应的日期对象
     * 
     * @param str
     *            需要转换的字符串
     * @return Date 返回值
     */
    public static Date StringToDate(String str) {
        return StringToDate(str, DEFAULT_DATE_FORMAT);
    }

    /**
     * 功能: 将传入的字符串按yyyy年MM月dd日 HH时mm分ss秒格式转换成对应的日期对象
     * 
     * @param str
     *            需要转换的字符串
     * @return Date
     */
    public static Date StringToDateTime(String str) {
        return StringToDate(str, DEFAULT_DATE_TIME_FORMAT);
    }
    
    /**
     * 功能: 将传入的字符串转换成对应的Timestamp对象
     * 
     * @param str
     *            待转换的字符串
     * @return Timestamp 转换之后的对象
     * @throws Exception
     *             Timestamp
     */
    public static Timestamp StringToDateHMS(String str) {
        Timestamp time = null;
        time = Timestamp.valueOf(str);
        return time;
    }

    /**
     * 功能: 根据传入的年月日返回相应的日期对象
     * 
     * @param year
     *            年份
     * @param month
     *            月份
     * @param day
     *            天
     * @return Date 日期对象
     */
    public static Date YmdToDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    /**
     * 功能: 将日期对象按照MM/dd HH:mm:ss的格式进行转换，返回转换后的字符串
     * 
     * @param date
     *            日期对象
     * @return String 返回值
     */
    public static String communityDateToString(Date date) {
        SimpleDateFormat formater = new SimpleDateFormat("MM/dd HH:mm:ss");
        String strDateTime = date == null ? null : formater.format(date);
        return strDateTime;
    }

    /**
     * <p>
     * 功能：获取某天时间的最大值 如：Thu May 22 23:59:59 CST 2014
     * </p>
     * 
     * @desc getMaxDateOfDay
     * @param date
     * @return
     */
    public static Date getMaxDateOfDay(Date date) {
        if (date == null)
        {
            return null;
        }
        else
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(11, calendar.getActualMaximum(11));
            calendar.set(12, calendar.getActualMaximum(12));
            calendar.set(13, calendar.getActualMaximum(13));
            calendar.set(14, calendar.getActualMaximum(14));
            return calendar.getTime();
        }
    }

    /**
     * 
     * @author:likui
     * @created:2015年9月25日下午3:21:14
     * @desc:获取某天时间的最大值 如：Thu May 22 23:59:59 CST 2014
     * @param:@param date
     * @param:@return
     * @param:Date
     * @throws
     */
    public static Date getMaxDateOfDay(String date) {
        if (StringUtils.isBlank(date))
        {
            return null;
        }
        return getMaxDateOfDay(StringToDate(date));
    }

    /**
     * 获取某天时间的最大值 如：2015-10-12 23:59:59
     * 
     * @Desc: TODO
     * @author: likui
     * @created: 2015年10月12日 下午4:40:48
     * @param : @param date
     * @param : @return
     * @return : String
     * @version V3.0.0
     */
    public static String getMaxDateOfDayStr(String date) {
        if (StringUtils.isBlank(date))
        {
            return null;
        }
        return DateTimeToString(getMaxDateOfDay(StringToDate(date)));
    }

    /**
     * <p>
     * 功能：获取某天时间的最小值 如：Thu May 22 00:00:00 CST 2014
     * </p>
     * 
     * @desc getMinDateOfDay
     * @param date
     * @return
     */
    public static Date getMinDateOfDay(Date date) {
        if (date == null)
        {
            return null;
        }
        else
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(11, calendar.getActualMinimum(11));
            calendar.set(12, calendar.getActualMinimum(12));
            calendar.set(13, calendar.getActualMinimum(13));
            calendar.set(14, calendar.getActualMinimum(14));
            return calendar.getTime();
        }
    }

    /**
     * 
     * @author:likui
     * @created:2015年9月25日下午3:23:08
     * @desc:获取某天时间的最小值 如：Thu May 22 00:00:00 CST 2014
     * @param:@param date
     * @param:@return
     * @param:Date
     * @throws
     */
    public static Date getMinDateOfDay(String date) {
        if (StringUtils.isBlank(date))
        {
            return null;
        }
        return getMinDateOfDay(StringToDate(date));
    }

    /**
     * 获取某天时间的最小值 如：2015-10-12 00:00:00
     * 
     * @Desc: TODO
     * @author: likui
     * @created: 2015年10月12日 下午4:41:34
     * @param : @param date
     * @param : @return
     * @return : String
     * @version V3.0.0
     */
    public static String getMinDateOfDayStr(String date) {
        if (StringUtils.isBlank(date))
        {
            return null;
        }
        return DateTimeToString(getMinDateOfDay(StringToDate(date)));
    }

    /**
     * 功能：返回传入日期对象（date）之后days天数的日期对象 若days大于0获得后几天 若days小于0获得前几天
     * 
     * @param date
     *            日期对象
     * @param days
     *            往后天数
     * @return java.util.Date 返回值
     */
    public static Date getAfterDay(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    /**
     * 功能：返回传入日期对象（date）之后年份数的日期对象 若year大于0获得后几年 若year小于0获得前几年
     * 
     * @param date
     *            日期对象
     * @param days
     *            往后天数
     * @return java.util.Date 返回值
     */
    public static Date getAfterYear(Date date, int year) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, year);
        return cal.getTime();
    }

    // day
    /**
     * 功能: 返回date1与date2相差的天数
     * 
     * @param date1
     * @param date2
     * @return int
     */
    public static int DateDiff(Date date1, Date date2) {
        int i = (int) ((date1.getTime() - date2.getTime()) / 3600 / 24 / 1000);
        return i;
    }

    // min
    /**
     * 功能: 返回date1与date2相差的分钟数
     * 
     * @param date1
     * @param date2
     * @return int
     */
    public static int MinDiff(Date date1, Date date2) {
        int i = (int) ((date1.getTime() - date2.getTime()) / 1000 / 60);
        return i;
    }

    // second
    /**
     * 功能: 返回date1与date2相差的秒数
     * 
     * @param date1
     * @param date2
     * @return int
     */
    public static int TimeDiff(Date date1, Date date2) {
        int i = (int) ((date1.getTime() - date2.getTime()) / 1000);
        return i;
    }

    /**
     * 给时间添加或减去多少分钟 date为null------当前时间 date不为null------指定时间
     * 
     * @Desc: TODO
     * @author: likui
     * @created: 2015年10月10日 下午4:08:57
     * @param : @param date
     * @param : @param minutes
     * @param : @return
     * @return : Date
     * @version V3.0.0
     */
    public static Date addMinutes(Date date, int minutes) {
        Calendar cal = Calendar.getInstance();
        if (null != date)
        {
            cal.setTime(date);
        }
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }

    /**
     * 给时间添加或减去多少分钟 date为null------当前时间 date不为null------指定时间
     * 
     * @Desc: TODO
     * @author: likui
     * @created: 2015年10月15日 下午2:35:56
     * @param : @param date
     * @param : @param minutes
     * @param : @return
     * @return : String
     * @version V3.0.0
     */
    public static String addMinutesStr(Date date, int minutes) {
        return DateTimeToString(addMinutes(date, minutes));
    }

    /**
     * 给时间添加或减去多少小时 date为null------当前时间 date不为null------指定时间
     * 
     * @Desc: TODO
     * @author: likui
     * @created: 2015年10月15日 下午2:25:05
     * @param : @param date
     * @param : @param hours
     * @param : @return
     * @return : Date
     * @version V3.0.0
     */
    public static Date addHour(Date date, int hours) {
        Calendar cal = Calendar.getInstance();
        if (null != date)
        {
            cal.setTime(date);
        }
        cal.add(Calendar.HOUR_OF_DAY, hours);
        return cal.getTime();
    }

    /**
     * 给时间添加或减去多少小时 date为null------当前时间 date不为null------指定时间
     * 
     * @Desc: TODO
     * @author: likui
     * @created: 2015年10月15日 下午2:35:48
     * @param : @param date
     * @param : @param hours
     * @param : @return
     * @return : String
     * @version V3.0.0
     */
    public static String addHourStr(Date date, int hours) {
        return DateTimeToString(addHour(date, hours));
    }

    /**
     * 给时间添加或减去多少天 date为null------当前时间 date不为null------指定时间
     * 
     * @Desc: TODO
     * @author: likui
     * @created: 2015年10月15日 下午2:25:29
     * @param : @param date
     * @param : @param days
     * @param : @return
     * @return : Date
     * @version V3.0.0
     */
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        if (null != date)
        {
            cal.setTime(date);
        }
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

    /**
     * 给时间添加或减去多少天 date为null------当前时间 date不为null------指定时间
     * 
     * @Desc: TODO
     * @author: likui
     * @created: 2015年10月15日 下午2:35:37
     * @param : @param date
     * @param : @param days
     * @param : @return
     * @return : String
     * @version V3.0.0
     */
    public static String addDaysStr(Date date, int days) {
        return DateTimeToString(addDays(date, days));
    }

    /**
     * 给时间添加或减去多少周 date为null------当前时间 date不为null------指定时间
     * 
     * @Desc: TODO
     * @author: likui
     * @created: 2015年10月10日 下午4:32:29
     * @param : @param date
     * @param : @param weeks
     * @param : @return
     * @return : Date
     * @version V3.0.0
     */
    public static Date addWeeks(Date date, int weeks) {
        Calendar cal = Calendar.getInstance();
        if (null != date)
        {
            cal.setTime(date);
        }
        cal.add(Calendar.WEEK_OF_MONTH, weeks);
        return cal.getTime();
    }

    /**
     * 给时间添加或减去多少周 date为null------当前时间 date不为null------指定时间
     * 
     * @Desc: TODO
     * @author: likui
     * @created: 2015年10月15日 下午2:35:28
     * @param : @param date
     * @param : @param weeks
     * @param : @return
     * @return : String
     * @version V3.0.0
     */
    public static String addWeeksStr(Date date, int weeks) {
        return DateTimeToString(addWeeks(date, weeks));
    }

    /**
     * 给时间添加或减去多少月 date为null------当前时间 date不为null------指定时间
     * 
     * @Desc: TODO
     * @author: likui
     * @created: 2015年10月10日 下午4:16:30
     * @param : @param date
     * @param : @param months
     * @param : @return
     * @return : Date
     * @version V3.0.0
     */
    public static Date addMonths(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        if (null != date)
        {
            cal.setTime(date);
        }
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    /**
     * 给时间添加或减去多少月 date为null------当前时间 date不为null------指定时间
     * 
     * @Desc: TODO
     * @author: likui
     * @created: 2015年10月15日 下午2:35:19
     * @param : @param date
     * @param : @param months
     * @param : @return
     * @return : String
     * @version V3.0.0
     */
    public static String addMonthsStr(Date date, int months) {
        return DateTimeToString(addMonths(date, months));
    }

    /**
     * 给指定时间添加或减去多少年 date为null------当前时间 date不为null------指定时间
     * 
     * @Desc: TODO
     * @author: likui
     * @created: 2015年10月10日 下午4:19:13
     * @param : @param date
     * @param : @param years
     * @param : @return
     * @return : Date
     * @version V3.0.0
     */
    public static Date addYears(Date date, int years) {
        Calendar cal = Calendar.getInstance();
        if (null != date)
        {
            cal.setTime(date);
        }
        cal.add(Calendar.YEAR, years);
        return cal.getTime();
    }

    /**
     * 给指定时间添加或减去多少年 date为null------当前时间 date不为null------指定时间
     * 
     * @Desc: TODO
     * @author: likui
     * @created: 2015年10月15日 下午2:35:06
     * @param : @param date
     * @param : @param years
     * @param : @return
     * @return : String
     * @version V3.0.0
     */
    public static String addYearsStr(Date date, int years) {
        return DateTimeToString(addYears(date, years));
    }

    /**
     * @return 当前日期
     */
    public static Date today() {
        return getCurrentDate(DEFAULT_DATE_FORMAT);
    }

    /**
     * @return 当前时间
     */
    public static Date now() {
        return getCurrentDate(DEFAULT_DATE_TIME_FORMAT);
    }

    /**
     * 获取当前不带符号的日期，如20150101
     * 
     * @return
     */
    public static String getCurrentDateNoSign() {
        return DateToString(getCurrentDate(), DATE_FORMAT);
    }

    /**
     * 获取当前不带符号的时间，如20150101231112
     * 
     * @return
     */
    public static String getCurrentDatetimeNoSign() {
        return DateToString(getCurrentDate(), DATE_TIME_FORMAT);
    }

    /**
     * 获取不带符号的日期，如20150101
     * 
     * @param date
     * @return
     */
    public static String getDateNoSign(Date date) {
        return DateToString(date, DATE_FORMAT);
    }

    /**
     * 获取不带符号的日期，如20150101
     * 
     * @param date
     * @return
     */
    public static Date getDateNoSign(String date) {
        return StringToDate(date, DATE_FORMAT);
    }

    /**
     * 获取当前日期和时间
     * 
     * @return
     */
    public static String getCurrentDateTime() {
        return DateToString(getCurrentDate(), DEFAULT_DATE_TIME_FORMAT);
    }

    /**
     * 比较两个日期相关的天数、月数、年数
     * 
     * @author tanglc
     * @version 1.1.0
     * @date:2014-10-22 下午5:56:17
     * @Description: compareDate(比较两个日期相关的天数、月数、年数)
     * @param date1
     *            需要比较的时间 不能为空(null),需要正确的日期格式
     * @param date2
     *            被比较的时间 为空(null)则为当前时间
     * @param stype
     *            返回值类型 0为多少天，1为多少个月，2为多少年
     * @return
     */
    public static int compareDate(String date1, String date2, int stype) {
        int n = 0;
        String formatStyle = stype == 1 ? "yyyy-MM" : "yyyy-MM-dd";
        date2 = date2 == null ? DateToString(new Date()) : date2;
        DateFormat df = new SimpleDateFormat(formatStyle);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try
        {
            c1.setTime(df.parse(date1));
            c2.setTime(df.parse(date2));
        }
        catch (Exception e3)
        {
            System.out.println("wrong occured");
        }
        while (!c1.after(c2))
        { // 循环对比，直到相等，n 就是所要的结果
            n++;
            if (stype == 1)
            {
                c1.add(Calendar.MONTH, 1); // 比较月份，月份+1
            }
            else
            {
                c1.add(Calendar.DATE, 1); // 比较天数，日期+1
            }
        }

        n = n - 1;
        if (stype == 2)
        {
            n = (int) n / 365;
        }
        return n;
    }

    /**
     * 比较两个日期大小
     * 
     * @author kongsl
     * @param date1
     * @param date2
     * @return 1: DATE1>DATE2 -1: DATE1<DATE2 0：相等
     */
    public static int compare_date(String date1, String date2) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime())
            {
                return 1;
            }
            else if (dt1.getTime() < dt2.getTime())
            {
                return -1;
            }
            else
            {
                return 0;
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return 0;
    }

    public static String getSimpleDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * 获取年份
     * 
     * @param date
     *            日期
     * @return
     */
    public static String getSimpleYear(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(date);
    }

    /**
     * 获取指定的年份
     * 
     * @author kongsl
     * @param rollNum
     *            为正为大于当前年，为负为小于当前年,例：当前为2016年，传-1则返回2015年,传1则返回2017年
     * @return 指定年份
     */
    public static String getAssignYear(int rollNum) {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Calendar lastDate = Calendar.getInstance();
        lastDate.roll(Calendar.YEAR, rollNum);// 加一个年
        str = sdf.format(lastDate.getTime());
        return str;
    }

    public static void main(String[] args) {
        Timestamp now = StringUtils.getNowTimestamp();
        System.out.println(timestampToString(now, "yyyy-MM-dd"));
        //
        // int i = compare_date("2995-11-12 15:21", "2995-11-12 15:21");
        // System.out.println("i==" + i);
        // // System.out.println(getCurrentDateTime());
        // // System.out.println(getMinDateOfDay("2015-09-25"));
        // System.out.println(addMinutesStr(null, 1));
        // System.out.println(addMinutesStr(getMinDateOfDay("2015-01-01"), 1));
        // System.out.println(addHourStr(null, 1));
        // System.out.println(addHourStr(getMinDateOfDay("2015-01-01"), 1));
        // System.out.println(addDaysStr(null, 1));
        // System.out.println(addDaysStr(getMinDateOfDay("2015-01-01"), 1));
        // System.out.println(addMonthsStr(null, 1));
        // System.out.println(addMonthsStr(getMinDateOfDay("2015-01-01"), 1));
        // System.out.println(addYearsStr(null, 1));
        // System.out.println(addYearsStr(getMinDateOfDay("2015-01-01"), 1));
        // System.out.println(addWeeksStr(null, 1));
        // System.out.println(addWeeksStr(getMinDateOfDay("2015-01-01"), 1));

        // System.out.println(getMinDateOfDayStr("2015-10-12"));

//        String endDate = DateUtil.getCurrentDateTime().substring(0, 10);
//        System.out.println("---------" + endDate);
//        int second = DateUtil.TimeDiff(DateUtil.getCurrentDate(), DateUtil.StringToDate(endDate + "23:59:59"));
//        System.out.println("--------" + second);
        System.out.println(StringToDateTime("2015-01-01 12:12:12.1.2"));
        Date d = Timestamp.valueOf("2015-01-01 12:12:12.1");
    }

    /**
     * Timestamp 转成 HHmmss
     * 
     * @param datetime
     * @return
     */
    public static String getFormatHms(Timestamp datetime) {
        return new SimpleDateFormat(DateUtil.DATE_FORMAT_HHmmss).format(datetime);
    }
    
    /**
     * 获取当前时间格式为（yyyy-MM-dd HH:mm:ss）
     * @return
     */
    public static String getCurrentDate2String() {
        Date date = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return formatter.format(date);
}
}
