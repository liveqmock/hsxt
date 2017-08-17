/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.common.utils.RandomCodeUtils;
import com.gy.hsxt.common.utils.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * BS系统字符串工具类型
 *
 * @Package: com.hsxt.bs.common
 * @ClassName: StringUtil
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月30日 下午6:41:15
 * @company: gyist
 * @version V3.0.0
 */
public class StringUtil implements Serializable {

	private static final long serialVersionUID = 2518232251421073393L;

	private StringUtil()
	{
		super();
	}

	/**
	 * 生成积分刷卡器KSN码
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午6:41:52
	 * @param longs
	 *            长度
	 * @param
	 * @return : String
	 * @version V3.0.0
	 */
	public static String createKsnCode(Integer longs)
	{
		// 生成UUId
		UUID uuid = UUID.randomUUID();
		String guid = uuid.toString().replace("-", "").toUpperCase();
		// 生成随机数
		Random random = new Random(System.currentTimeMillis());
		int k = random.nextInt();
		int start = Math.abs(k % (32 - longs));
		// 加密
		guid = DigestUtils.md5Hex(guid);
		guid = guid.substring(start, start + longs);
		return "ec" + guid;
	}

	/**
	 * 将原数据前补零，补后的总长度为指定的长度，以字符串的形式返回
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月8日 上午11:52:38
	 * @param value
	 *            值
	 * @param length
	 *            长度
	 * @return : String
	 * @version V3.0.0
	 */
	public static String frontCompWithZore(Integer value, int length)
	{
		return String.format("%0" + length + "d", value);
	}

	/**
	 * 获取从1970 到现在的秒数
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月8日 下午4:39:42
	 * @return : String
	 * @version V3.0.0
	 */
	public static String getDateTime()
	{
		return String.valueOf(new Date().getTime());
	}

	/**
	 * 生成设备客户号
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月23日 上午11:25:39
	 * @param prefix
	 *            前缀
	 * @param appNode
	 *            节点编号
	 * @return
	 * @return : String
	 * @version V3.0.0
	 */
	public static String createDeviceCustId(String prefix, String appNode)
	{
		return prefix + appNode + RandomCodeUtils.getNumberCode(15);
	}

	/**
	 * 数组转换成字符串
	 *
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月3日 下午4:12:00
	 * @param param
	 *            集合(list or array)
	 * @param decollator
	 *            切割符
	 * @return
	 * @return : String
	 * @version V3.0.0
	 */
	@SuppressWarnings("rawtypes")
	public static String arrayToString(Object param, String decollator)
	{
		String value = "";
		if (null == param)
		{
			return value;
		}
		if (param instanceof String[])
		{
			return org.apache.commons.lang3.StringUtils.join((String[]) param, decollator);
		}
		if (param instanceof Collection)
		{
			return org.apache.commons.lang3.StringUtils.join((Collection) param, decollator);
		}
		return null;
	}

	/**
	 * 获取本月最后一天和下个月第一天
	 *
	 * @return
	 */
	public static Map<String, String> getFirstAndLastDate()
	{
		Map<String, String> map = new HashMap<String, String>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		Date theDate = calendar.getTime();
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);

		// 下个月第一天
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String nextFirstDay = df.format(gcLast.getTime());
		map.put("nextFirstDay", nextFirstDay);

		// 本月最后一天
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DATE, 1); // 设置为该月第一天
		calendar.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天
		String lastDay = df.format(calendar.getTime());
		map.put("lastDay", lastDay);

		// 本月第一天
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		String firstDay = df.format(calendar.getTime());
		map.put("firstDay", firstDay);
		return map;
	}

	/**
	 * 组装查询的In查询条件
	 *
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月15日 上午10:02:45
	 * @param value
	 *            参数值
	 * @return
	 * @return : String
	 * @version V3.0.0
	 */
	@SuppressWarnings("rawtypes")
	public static String assembleQueryInIf(Object value)
	{
		if (StringUtils.isBlank(value))
		{
			return null;
		}
		if (value instanceof String[])
		{
			return "IN ('" + org.apache.commons.lang3.StringUtils.join((String[]) value, "','") + "')";
		}
		if (value instanceof Collection)
		{
			return "IN ('" + org.apache.commons.lang3.StringUtils.join((Collection) value, "','") + "')";
		}
		return null;
	}

	/**
	 * @Desc:  获取资源段的起始互生号
	 * @author: likui
	 * @created: 2016/6/12 11:44
	 * @param: entResNo 互生号
	 * @eturn: Map<String, String[]>
	 * @copany: gyist
	 * @version V3.0.0
	 */
	public static Map<String, String[]> getResSegmentStartAndEndResNo(String entResNo)
	{
		if (StringUtils.isBlank(entResNo) || !HsResNoUtils.isTrustResNo(entResNo))
		{
			return null;
		}
		String resNo = entResNo.substring(0, 7);
		Map<String, String[]> result = new HashMap<String, String[]>();
		int startcount = 1;
		int endcount = 1000;
		for (int i = 1; i <= 10; i++)
		{
			result.put(String.valueOf(i), new String[]
			{ resNo + StringUtil.frontCompWithZore(startcount, 4), resNo + StringUtil.frontCompWithZore(endcount, 4) });
			startcount += 1000;
			endcount = i == 9 ? endcount + 999 : endcount + 1000;
		}
		return result;
	}
}
