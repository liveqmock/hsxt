package com.gy.hsxt.ps.common;

import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PsTools {

    /**
     * 生成GUID 流水号
     *
     * @return
     */
/*
    public static synchronized String GUID(String serial) {
        String prefix = serial + PropertyConfigurer.getProperty("system.instance.no");
        return GuidAgent.getStringGuid(prefix);
    }
*/

    /**
     * 生获得机器节点
     *
     * @return
     */
    public static String getInstanceNo() {
        return PropertyConfigurer.getProperty("system.instance.no");
    }

    /**
     * 获取业务编码
     *
     * @param serial
     * @return
     */
    public static String getBusinessNo(String serial) {
        return serial.substring(0, 2);
    }

    /**
     * 通过比较找出管理公司、服务公司、托管企业
     *
     * @param custId
     * @param start
     * @return
     */
    public static boolean isCustType(String custId, int start) {
        if (start == 2) {
            // 管理公司互生号
            return custId.substring(start, custId.length()).equals("000000000");
        } else if (start == 5) {
            // 服务公司互生号
            return custId.substring(start, custId.length()).equals("000000");
        } else if (start == 7) {
            // 托管企业互生号
            return custId.substring(start, custId.length()).equals("0000");
        } else {
            return false;
        }
    }

    /**
     * 返回互生号所属(管理公司、服务公司、托管企业、成员企业)
     *
     * @param number
     * @return
     */
    public static int parsHsNo(String number) {
        int hsNo = 0;
        // 管理公司
        String manageResNo = number.substring(0, 2) + "000000000";
        // 服务公司
        String serviceResNo = number.substring(0, 5) + "000000";
        // 托管企业
        String trusteeResNo = number.substring(0, 7) + "0000";
        // 成员企业
        String entResNo = number.substring(0, 5) + "00" + number.substring(7, 11);

        if (manageResNo.equals(number)) {
            hsNo = Constants.HS_MANAGE;

        } else if (serviceResNo.equals(number)) {
            hsNo = Constants.HS_SERVICE;

        } else if (trusteeResNo.equals(number)) {
            hsNo = Constants.HS_TRUSTEE;
        } else if (entResNo.equals(number)) {
            hsNo = Constants.HS_ENTERPRISE;
        } else {
            hsNo = Constants.HS_PAAS;
        }
        return hsNo;
    }


    /**
     * 返回互生号所属(管理公司、服务公司、托管企业、成员企业)互生号
     *
     * @param fromResNo 原互生号
     * @param hsNo      企业类型
     * @return 企业互生号
     */
    public static String getEntResNo(String fromResNo, int hsNo) {
        // 管理公司
        String manageResNo = fromResNo.substring(0, 2) + "000000000";
        // 服务公司
        String serviceResNo = fromResNo.substring(0, 5) + "000000";
        // 托管企业
        String trusteeResNo = fromResNo.substring(0, 7) + "0000";
        // 成员企业
        String entResNo = fromResNo.substring(0, 5) + "00" + fromResNo.substring(7, 11);

        if (hsNo == Constants.HS_MANAGE) {
            return manageResNo;
        }
        if (hsNo == Constants.HS_SERVICE) {
            return serviceResNo;
        }
        if (hsNo == Constants.HS_TRUSTEE) {
            return trusteeResNo;
        }
        if (hsNo == Constants.HS_ENTERPRISE) {
            return entResNo;
        }
        return null;
    }


    /**
     * 获取前一天的日期
     *
     * @return
     */
    public static String getBeforeDay() {
        return DateUtil.DateToString(DateUtil.getAfterDay(DateUtil.today(), -1), DateUtil.DEFAULT_DATE_FORMAT);
    }

    /**
     * 获取前一天的日期名称
     *
     * @return
     */
    public static String getBeforeDayDirectory() {
        return DateUtil.DateToString(DateUtil.getAfterDay(DateUtil.today(), -1), DateUtil.DATE_FORMAT);
    }

    /**
     * 获取当前的日期时间
     *
     * @return
     */
    public static String getDateTime() {
        return DateUtil.DateToString(DateUtil.getCurrentDate(), DateUtil.DEFAULT_DATE_TIME_FORMAT);
    }

    /**
     * 日期格式化
     *
     * @param dateTimeStr
     * @return
     */
    public static String setDateFormat(String dateTimeStr) {
        String formattedTime = "";
        SimpleDateFormat simpleDateFormat;
        try {
            String format = "yyyy-MM-dd";
            String format1 = "yyyyMMdd";
            if (dateTimeStr.contains("-")) {
                simpleDateFormat = new SimpleDateFormat(format);
            } else {
                simpleDateFormat = new SimpleDateFormat(format1);
            }

            Date date = simpleDateFormat.parse(dateTimeStr);
            String temDate = DateUtil.DateToString(date);
            date = DateUtil.StringToDate(temDate);
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
            formattedTime = simpleDateFormat2.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedTime;
    }

    /**
     * 金额格式化返回字符串(保留6位小数)
     *
     * @param bd
     * @return
     */
    public static String amountDecimalFormat(BigDecimal bd) {
        DecimalFormat myformat = new DecimalFormat("0.000000");
        return myformat.format(bd);
    }

    /**
     * 金额格式化返回BigDecimal(保留6位小数)
     *
     * @param bd
     * @return
     */
    public static BigDecimal formatBigDec(BigDecimal bd) {
        String adf = amountDecimalFormat(bd);
        BigDecimal bigDec = new BigDecimal(adf);
        return bigDec;
    }

    /**
     * 日期 String 转 long
     *
     * @param dateStr
     * @return
     */
    public static long getDateLong(String dateStr) {
        long dateLong = 0;
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        try {
            if (!isEmpty(dateStr)) {
                Date d = dFormat.parse(dateStr);
                dateLong = getDateLong(d);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateLong;
    }

    /**
     * 日期 date 转 long
     *
     * @param date
     * @return
     */
    public static long getDateLong(Date date) {
        Date d;
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String date_string = sdfDate.format(date);
        try {
            d = sdfDate.parse(date_string);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
        return d.getTime();
    }

    /**
     * 判断为空(String)
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return (null == str || "".equals(str));
    }

    /**
     * 判断为空(Object)
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        return (null == obj || "".equals(obj));
    }

    /**
     * 判断为空(BigDecimal)
     *
     * @param bd
     * @return
     */
    public static boolean isEmpty(BigDecimal bd) {
        return (null == bd);
    }

    /**
     * 判断是否相等
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean isEqual(String str1, String str2) {
        return (str1.equals(str2));
    }


    /**
     * 验证资源号
     *
     * @param resNo 验证资源号
     * @return 如果是符合格式的字符串, 返回 客户类型
     */
    public static String verifyHsResNo(String resNo) {
        // 平台F^[0]{11}$
        // 地区平台:^([0]{8})(([1-9]\\d\\d)|(\\d[1-9]\\d)|(\\d\\d[1-9]))$
        // 管理公司M:^(([1-9]\\d){1}|(\\d[1-9]){1})([0]{9})$
        // 服务公司S:^(([1-9]\\d){1}|(\\d[1-9]){1})([1-9]\\d{2}|\\d{2}[1-9]|\\d[1-9]\\d)([0]{6})$
        // 托管企业T:^(([1-9]\\d){1}|(\\d[1-9]){1})([1-9]\\d{2}|\\d{2}[1-9]|(\\d[1-9]\\d))([1-9]\\d|\\d[1-9])([0]{4})$
        // 成员企业B:^(([1-9]\\d){1}|(\\d[1-9]){1})([1-9]\\d{2}|\\d{2}[1-9]|(\\d[1-9]\\d))([0]{2})([1-9]{1}\\d{3}|\\d{3}[1-9]{1}|\\d{1}[1-9]{1}\\d{2}|\\d{2}[1-9]{1}\\d{1})$
        // 消息者P:^(([1-9]\\d){1}|(\\d[1-9]){1})([1-9]\\d{2}|\\d{2}[1-9]|(\\d[1-9]\\d))([1-9]\\d|\\d[1-9])([1-9]{1}\\d{3}|\\d{3}[1-9]{1}|\\d{1}[1-9]{1}\\d{2}|\\d{2}[1-9]{1}\\d{1})$
        Map<String, String> regex = new HashMap<>();
        regex.put(String.valueOf(CustType.AREA_PLAT.getCode()),
                "^([0]{8})(([1-9]\\d\\d)|(\\d[1-9]\\d)|(\\d\\d[1-9]))$");
        regex.put(String.valueOf(CustType.MANAGE_CORP.getCode()),
                "^(([1-9]\\d){1}|(\\d[1-9]){1})([0]{9})$");
        regex.put(String.valueOf(CustType.SERVICE_CORP.getCode()),
                "^(([1-9]\\d){1}|(\\d[1-9]){1})([1-9]\\d{2}|\\d{2}[1-9]|\\d[1-9]\\d)([0]{6})$");
        regex.put(String.valueOf(CustType.TRUSTEESHIP_ENT.getCode()),
                "^(([1-9]\\d){1}|(\\d[1-9]){1})([1-9]\\d{2}|\\d{2}[1-9]|(\\d[1-9]\\d))([1-9]\\d|\\d[1-9])([0]{4})$");
        regex.put(
                String.valueOf(CustType.MEMBER_ENT.getCode()),
                "^(([1-9]\\d){1}|(\\d[1-9]){1})([1-9]\\d{2}|\\d{2}[1-9]|(\\d[1-9]\\d))([0]{2})([1-9]{1}\\d{3}|\\d{3}[1-9]{1}|\\d{1}[1-9]{1}\\d{2}|\\d{2}[1-9]{1}\\d{1})$");
        regex.put(
                String.valueOf(CustType.PERSON.getCode()),
                "^(([1-9]\\d){1}|(\\d[1-9]){1})([1-9]\\d{2}|\\d{2}[1-9]|(\\d[1-9]\\d))([1-9]\\d|\\d[1-9])([1-9]{1}\\d{3}|\\d{3}[1-9]{1}|\\d{1}[1-9]{1}\\d{2}|\\d{2}[1-9]{1}\\d{1})$");
        for (Map.Entry<String, String> entry : regex.entrySet()) {
            Boolean b = match(entry.getValue(), resNo);
            if (b == true)
                return entry.getKey();
        }

        return null;
    }

    /**
     * 遍历list中的map,获取KEY
     *
     * @param dataList
     * @return
     */
    public static Set<String> getSetKey(List<Map<String, String>> dataList) {
        Set<String> colName = new HashSet<String>();
        for (int i = 0; i < dataList.size(); i++) {
            Map<String, String> map = dataList.get(i);
            Iterator<Entry<String, String>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                String keys = entry.getKey();
                colName.add(keys);
            }
        }
        return colName;
    }

    /**
     * 遍历list中的map,获取value
     *
     * @param dataList
     * @return
     */
    public static List<String> getListValue(List<Map<String, String>> dataList) {
        List<String> colLength = new ArrayList<String>();
        for (int i = 0; i < dataList.size(); i++) {
            Map<String, String> map = dataList.get(i);
            Iterator<Entry<String, String>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                String values = entry.getValue();
                colLength.add(values);
            }
        }
        return colLength;
    }

    /**
     * @param regex 正则表达式字符串
     * @param str   要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 遍历获取list中的map值
     *
     * @param list
     * @return
     */
    public static List<String> getKeyValue(List<Map<String, String>> list) {
        List<String> dataList = new ArrayList<String>();
        for (Map<String, String> m : list) {
            for (Map.Entry<String, String> entry : m.entrySet()) {
                dataList.add(entry.getValue());
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            }

        }
        return dataList;
    }

    /**
     * 关闭线程池
     *
     * @param pool
     */
    public static void shutdown(ExecutorService pool) {
        pool.shutdown();
        while (true) {
            if (pool.isTerminated()) {
                System.out.println(new Timestamp(System.currentTimeMillis()).toString());
                break;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("[结束执行时间] " + DateUtil.getCurrentDateTime());
    }

    /**
     * 中判断字符串是否日期格式
     *
     * @param stringDate 时间字符串
     * @return
     */
    public static boolean checkValidPattern(String stringDate, String pa) {
        Pattern pattern = Pattern.compile(pa);
        Matcher matcher = pattern.matcher(stringDate);
        return matcher.find();
    }

    /**
     * 转换sting成两位小数点
     *
     * @param bigDecimal bigDecima
     * @return
     */
    public static String bigDecimal2String(BigDecimal bigDecimal) {
        return String.format("%1$.2f", bigDecimal);
    }


    /**
     * <b>function:</b> 处理oracle sql 语句in子句中（where id in (1, 2, ..., 1000, 1001)），
     * 如果子句中超过1000项就会报错。
     * 这主要是oracle考虑性能问题做的限制。
     * 如果要解决次问题，可以用 where id (1, 2, ..., 1000) or id (1001, ...)
     *
     * @param ids   in语句中的集合对象
     * @param count in语句中出现的条件个数
     * @param field in语句对应的数据库查询字段
     * @return 返回 field in (...) or field in (...) 字符串
     * @author martin.cubbon
     * @createDate 2016-05-03 下午02:36:03
     */
    public static String getOracleSQLIn(List<?> ids, int count, String field) {
        count = Math.min(count, 1000);
        int len = ids.size();
        int size = len % count;
        if (size == 0) {
            size = len / count;
        } else {
            size = (len / count) + 1;
        }
        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (i < size) {
            int fromIndex = i * count;
            int toIndex = Math.min(fromIndex + count, len);
            String productId = StringUtils.defaultIfEmpty(StringUtils.join(ids.subList(fromIndex, toIndex), "','"), "");
            if (i != 0) {
                builder.append(" or ");
            }
            builder.append(field).append(" in ('").append(productId).append("')");
            i++;
        }
       // return StringUtils.defaultIfEmpty(builder.toString(), field + " in ('')");
        return StringUtils.defaultIfEmpty(builder.toString(),null);
    }

}

