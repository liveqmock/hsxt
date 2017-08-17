/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.common;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.RandomCodeUtils;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * TM系统字符串工具类型
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

	private StringUtil() {
		super();
	}

	/**
	 * 生成积分刷卡器KSN码
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午6:41:52
	 * @param : @param longs
	 * @param : @return
	 * @return : String
	 * @version V3.0.0
	 */
	public static String createKsnCode(Integer longs) {
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
	 * @param : @param value
	 * @param : @param length
	 * @param : @return
	 * @return : String
	 * @version V3.0.0
	 */
	public static String frontCompWithZore(Integer value, int length) {
		return String.format("%0" + length + "d", value);
	}

	/**
	 * 获取从1970 到现在的秒数
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月8日 下午4:39:42
	 * @param : @return
	 * @return : String
	 * @version V3.0.0
	 */
	public static String getDateTime() {
		return String.valueOf(new Date().getTime());
	}

	/**
	 * 创建BS服务系统的GUID
	 * 
	 * @Desc: TODO
	 * @author: likui
	 * @created: 2015年10月10日 下午3:59:08
	 * @param : @return
	 * @return : String
	 * @version V3.0.0
	 */
	public static String getAoGuid(String appNode) {
		String guid = GuidAgent.getStringGuid(BizGroup.AO + appNode);
		return guid;
	}

	/**
	 * 生成设备客户号
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月23日 上午11:25:39
	 * @param prefix
	 * @param appNode
	 * @return
	 * @return : String
	 * @version V3.0.0
	 */
	public static String createDeviceCustId(String prefix, String appNode) {
		return prefix + appNode + RandomCodeUtils.getNumberCode(15);
	}

	/**
	 * 获取类所有属性名称
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月14日 下午2:14:27
	 * @param : @param clazz
	 * @param : @return
	 * @return : String
	 * @version V3.0.0
	 */
	public static String getClassAttribute(
			@SuppressWarnings("rawtypes") Class clazz) {
		String ret = "";
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (!field.getName().equals("serialVersionUID")) {
				ret += field.getName() + "|";
			}
		}
		return ret.substring(0, ret.length() - 1);
	}

	/**
	 * 数组转换成字符串
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月3日 下午4:12:00
	 * @param param
	 * @param decollator
	 * @return
	 * @return : String
	 * @version V3.0.0
	 */
	public static String arrayToString(String[] param, String decollator) {
		String value = "";
		if (null == param) {
			return value;
		}
		for (int i = 0; i < param.length; i++) {
			if (StringUtils.isNotBlank(param[i])) {
				value += (param[i] + decollator);
			}
		}
		if (StringUtils.isNotBlank(value)) {
			value = value.substring(0, value.length() - 1);
		}
		return value;
	}

	/**
	 * 获取本月最后一天和下个月第一天
	 * 
	 * @return
	 */
	public Map<String, String> getFirstAndLastDate() {
		Map<String, String> map = new HashMap<String, String>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		Date theDate = calendar.getTime();

		// 下个月第一天
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String firstDay = df.format(gcLast.getTime());
		map.put("nextFirstDay", firstDay);

		// 本月最后一天
		calendar.add(Calendar.MONTH, 0); // 加一个月
		calendar.set(Calendar.DATE, 1); // 设置为该月第一天
		calendar.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天
		String lastDay = df.format(calendar.getTime());
		map.put("lastDay", lastDay);
		return map;
	}

	public static void main(String[] args) {
		// String deviceSeqNo = "ec000000001541";
		// String nextSeqNo = "";
		// int seqNo = Integer.parseInt(deviceSeqNo.substring(2,
		// deviceSeqNo.length()));
		// for (int i = 1; i <= 10; i++)
		// {
		// nextSeqNo = "ec"
		// + StringUtil.frontCompWithZore((seqNo + i),
		// (deviceSeqNo.length() - 2));
		// System.out.println(nextSeqNo);
		// }

	}
}
