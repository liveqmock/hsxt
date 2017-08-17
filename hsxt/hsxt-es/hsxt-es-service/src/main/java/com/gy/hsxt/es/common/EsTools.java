package com.gy.hsxt.es.common;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.dubbo.common.json.JSON;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.es.common.PropertyConfigurer;

public class EsTools
{

	/**
	 * 生成积分的GUID
	 * 
	 * @return
	 */
	public static String GUID(String number)
	{
	    String prefix = number + PropertyConfigurer.getProperty("system.instance.no");
		return GuidAgent.getStringGuid(prefix);
	}
	
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
	 * @param serial
	 * @return
	 */
	public static String getBusinessNo(String serial){
		return serial.substring(0,2);
	}
	
	/**
	 * 关闭线程池
	 * @param pool
	 */
	public static void shutdown(ExecutorService pool){
		pool.shutdown();
		while (true)
		{
			if (pool.isTerminated())
			{
				System.out.println(new Timestamp(System.currentTimeMillis()).toString());
				break;
			}
			try
			{
				Thread.sleep(200);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		System.out.println("[结束执行时间] " + DateUtil.getCurrentDateTime());
	}

	/**
	 * 通过比较找出管理公司、服务公司、托管企业
	 * 
	 * @param custId
	 * @param start
	 * @return
	 */
	public static boolean isEquals(String custId, int start)
	{
		if (start == 2)
		{
			return custId.substring(start, custId.length()).equals("000000000");
		} else if (start == 5)
		{
			return custId.substring(start, custId.length()).equals("000000");
		} else if (start == 7)
		{
			return custId.substring(start, custId.length()).equals("0000");
		} else
		{
			return false;
		}
	}

	/**
	 * 返回互生号所属(管理公司、服务公司、托管企业、成员企业)
	 * 
	 * @param number
	 * @return
	 */
	public static String parsHsNo(String number)
	{
		String hsNo = "";
		// 管理公司
		String manageResNo = number.substring(0, 2) + "000000000";
		// 服务公司
		String serviceResNo = number.substring(0, 5) + "000000";
		// 托管企业
		String trusteeResNo = number.substring(0, 7) + "0000";
		// 成员企业
		String entResNo = number.substring(0, 5) + "00" + number.substring(7, 11);
		if (manageResNo.equals(number))
		{
			hsNo = manageResNo;
		} else if (serviceResNo.equals(number))
		{
			hsNo = serviceResNo;
		} else if (trusteeResNo.equals(number))
		{
			hsNo = trusteeResNo;
		} else if (trusteeResNo.equals(number))
		{
			hsNo = manageResNo;
		} else if (entResNo.equals(number))
		{
			hsNo = entResNo;
		}
		return hsNo;
	}

	/**
	 * 获取前一天的日期
	 * 
	 * @return
	 */
	public static String getBeforeDay()
	{
		return DateUtil.DateToString(DateUtil.getAfterDay(DateUtil.today(), -1));
	}
	
	/**
	 * 获取前一天的日期
	 * 
	 * @return
	 */
	public static String getAfterDay()
	{
		return DateUtil.DateToString(DateUtil.getAfterDay(DateUtil.today(), 1));
	}

	/**
	 * 金额格式化返回字符串(保留6位小数)
	 * 
	 * @param bd
	 * @return
	 */
	public static String amountDecimalFormat(BigDecimal bd)
	{
		DecimalFormat myformat = new DecimalFormat("0.000000");
		return myformat.format(bd);
	}

	/**
	 * 金额格式化返回BigDecimal(保留6位小数)
	 * 
	 * @param bd
	 * @return
	 */
	public static BigDecimal formatBigDec(BigDecimal bd)
	{
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
	public static long getDateLong(String dateStr)
	{
		long dateLong = 0;
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		try
		{
			if (!isEmpty(dateStr))
			{
				Date d = dFormat.parse(dateStr);
				dateLong = getDateLong(d);
			}
		} catch (ParseException e)
		{
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
	public static long getDateLong(Date date)
	{
		Date d;
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String date_string = sdfDate.format(date);
		try
		{
			d = sdfDate.parse(date_string);
		} catch (ParseException e)
		{
			e.printStackTrace();
			return -1;
		}
		return d.getTime();
	}

	/**
	 * 判断为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str)
	{
		return (null == str || "".equals(str));
	}
	
	/**
	 * 判断为空(Object)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(Object obj)
	{
		return (null == obj || "".equals(obj));
	}


	/**
	 * 判断是否相等
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isEqual(String str1, String str2)
	{
		return (str1.equals(str2));
	}

	/**
	 * 转JSON格式
	 * 
	 * @param list
	 * @return
	 */
	public static String jsonString(List<?> list)
	{
		String info = "";
		for (int i = 0; i < list.size(); i++)
		{
			try
			{
				info += JSON.json(list.get(i)).toString();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		System.out.println("[ JSON String ]" + info);
		return info;
	}

	/**
	 * 验证资源号
	 * 
	 * @param resNo
	 *            验证资源号
	 * @return 如果是符合格式的字符串, 返回 客户类型
	 */
	public static String verifyHsResNo(String resNo)
	{
		// 平台F^[0]{11}$
		// 地区平台:^([0]{8})(([1-9]\\d\\d)|(\\d[1-9]\\d)|(\\d\\d[1-9]))$
		// 管理公司M:^(([1-9]\\d){1}|(\\d[1-9]){1})([0]{9})$
		// 服务公司S:^(([1-9]\\d){1}|(\\d[1-9]){1})([1-9]\\d{2}|\\d{2}[1-9]|\\d[1-9]\\d)([0]{6})$
		// 托管企业T:^(([1-9]\\d){1}|(\\d[1-9]){1})([1-9]\\d{2}|\\d{2}[1-9]|(\\d[1-9]\\d))([1-9]\\d|\\d[1-9])([0]{4})$
		// 成员企业B:^(([1-9]\\d){1}|(\\d[1-9]){1})([1-9]\\d{2}|\\d{2}[1-9]|(\\d[1-9]\\d))([0]{2})([1-9]{1}\\d{3}|\\d{3}[1-9]{1}|\\d{1}[1-9]{1}\\d{2}|\\d{2}[1-9]{1}\\d{1})$
		// 消息者P:^(([1-9]\\d){1}|(\\d[1-9]){1})([1-9]\\d{2}|\\d{2}[1-9]|(\\d[1-9]\\d))([1-9]\\d|\\d[1-9])([1-9]{1}\\d{3}|\\d{3}[1-9]{1}|\\d{1}[1-9]{1}\\d{2}|\\d{2}[1-9]{1}\\d{1})$
		Map<String, String> regex = new HashMap<>();
		regex.put(String.valueOf(CustType.AREA_PLAT.getCode()), "^([0]{8})(([1-9]\\d\\d)|(\\d[1-9]\\d)|(\\d\\d[1-9]))$");
		regex.put(String.valueOf(CustType.MANAGE_CORP.getCode()), "^(([1-9]\\d){1}|(\\d[1-9]){1})([0]{9})$");
		regex.put(String.valueOf(CustType.SERVICE_CORP.getCode()), "^(([1-9]\\d){1}|(\\d[1-9]){1})([1-9]\\d{2}|\\d{2}[1-9]|\\d[1-9]\\d)([0]{6})$");
		regex.put(String.valueOf(CustType.TRUSTEESHIP_ENT.getCode()),
				"^(([1-9]\\d){1}|(\\d[1-9]){1})([1-9]\\d{2}|\\d{2}[1-9]|(\\d[1-9]\\d))([1-9]\\d|\\d[1-9])([0]{4})$");
		regex.put(
				String.valueOf(CustType.MEMBER_ENT.getCode()),
				"^(([1-9]\\d){1}|(\\d[1-9]){1})([1-9]\\d{2}|\\d{2}[1-9]|(\\d[1-9]\\d))([0]{2})([1-9]{1}\\d{3}|\\d{3}[1-9]{1}|\\d{1}[1-9]{1}\\d{2}|\\d{2}[1-9]{1}\\d{1})$");
		regex.put(
				String.valueOf(CustType.PERSON.getCode()),
				"^(([1-9]\\d){1}|(\\d[1-9]){1})([1-9]\\d{2}|\\d{2}[1-9]|(\\d[1-9]\\d))([1-9]\\d|\\d[1-9])([1-9]{1}\\d{3}|\\d{3}[1-9]{1}|\\d{1}[1-9]{1}\\d{2}|\\d{2}[1-9]{1}\\d{1})$");
		for (Map.Entry<String, String> entry : regex.entrySet())
		{
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
	public static Set<String> getSetKey(List<Map<String, String>> dataList)
	{
		Set<String> colName = new HashSet<String>();
		for (int i = 0; i < dataList.size(); i++)
		{
			Map<String, String> map = (Map<String, String>) dataList.get(i);
			Iterator<Entry<String, String>> it = map.entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
				String keys = (String) entry.getKey();
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
	public static List<String> getListValue(List<Map<String, String>> dataList)
	{
		List<String> colLength = new ArrayList<String>();
		for (int i = 0; i < dataList.size(); i++)
		{
			Map<String, String> map = (Map<String, String>) dataList.get(i);
			Iterator<Entry<String, String>> it = map.entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
				String values = (String) entry.getValue();
				colLength.add(values);
			}
		}
		return colLength;
	}

	/**
	 * @param regex
	 *            正则表达式字符串
	 * @param str
	 *            要匹配的字符串
	 * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	 */
	private static boolean match(String regex, String str)
	{
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
	public static List<String> getKeyValue(List<Map<String, String>> list)
	{
		List<String> dataList = new ArrayList<String>();
		for (Map<String, String> m : list)
		{
			for (Map.Entry<String, String> entry : m.entrySet())
			{
				dataList.add(entry.getValue());
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

		}
		return dataList;
	}

}
