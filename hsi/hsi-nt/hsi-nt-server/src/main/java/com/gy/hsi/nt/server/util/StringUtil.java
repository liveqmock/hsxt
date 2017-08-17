package com.gy.hsi.nt.server.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.gy.hsi.nt.api.common.TempNo;
import com.gy.hsi.nt.server.util.enumtype.TempType;

import net.sf.json.JSONObject;

/**
 * 
 * @className:StringUtil
 * @author:likui
 * @date:2015年7月28日
 * @desc:字符串工具类
 * @company:gyist
 */
public class StringUtil {

	private static final Logger logger = Logger.getLogger(StringUtil.class);

	private StringUtil()
	{
		super();
	}

	/**
	 * 替换模板中的占位符
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月29日 下午7:16:00
	 * @param tempContent
	 * @param params
	 * @return
	 * @return : String
	 * @version V3.0.0
	 */
	public static String replacePlaceholder(String tempContent, String[] params)
	{
		try
		{
			if (StringUtils.isBlank(tempContent))
			{
				return "";
			}
			if (null == params)
			{
				return "";
			}
			Matcher matcher = Pattern.compile("\\{(\\d)\\}").matcher(tempContent);
			while (matcher.find())
			{
				tempContent = tempContent.replace(matcher.group(), params[Integer.parseInt(matcher.group(1))]);
			}
		} catch (Exception e)
		{
			logger.error("替换占位符异常,参数少于占位符个数");
			return "";
		}
		return tempContent;
	}

	/**
	 * 获取模板类型
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月29日 下午7:15:52
	 * @param templateId
	 * @return
	 * @return : String
	 * @version V3.0.0
	 */
	public static String getTempType(String templateId)
	{
		if (templateId.equals(TempNo.BINDED_EMAIL_VERIFICATION.getTemoNo())
				|| templateId.equals(TempNo.LOGIN_PWD_RETRIVE_EMAIL.getTemoNo()))
		{
			return TempType.EMAIL.name();
		}
		return TempType.NOTE.name();
	}

	/**
	 * 数组转换成字符串
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月29日 下午7:15:41
	 * @param param
	 * @param decollator
	 * @return
	 * @return : String
	 * @version V3.0.0
	 */
	public static String arrayToString(String[] param, String decollator)
	{
		String value = "";
		if (null == param)
		{
			return value;
		}
		for (int i = 0; i < param.length; i++)
		{
			if (StringUtils.isNotBlank(param[i]))
			{
				value += (param[i] + decollator);
			}
		}
		if (StringUtils.isNotBlank(value))
		{
			value = value.substring(0, value.length() - 1);
		}
		return value;
	}

	/**
	 * 字符串转换成数组
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月30日 上午10:26:19
	 * @param param
	 * @param decollator
	 * @return
	 * @return : String[]
	 * @version V3.0.0
	 */
	public static String[] stringToArray(String param, String decollator)
	{
		if (StringUtils.isBlank(param))
		{
			return null;
		}
		if (param.indexOf(decollator) < 0)
		{
			return new String[] { param };
		}
		return param.split(decollator);
	}

	/**
	 * 替换字符串 null
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月29日 下午7:16:18
	 * @param value
	 * @return
	 * @return : String
	 * @version V3.0.0
	 */
	public static String navNull(String value)
	{
		if (value == null || "null".equals(value))
		{
			return "";
		}
		return value.trim();
	}

	/**
	 * 获取字符串的编码格式
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月29日 上午10:09:07
	 * @param value
	 * @return
	 * @return : String
	 * @version V3.0.0
	 */
	public static String getEncoding(String value)
	{
		try
		{
			if (value.equals(new String(value.getBytes("UTF-8"), "UTF-8")))
			{
				return "UTF-8";
			}
			if (value.equals(new String(value.getBytes("ISO-8859-1"), "ISO-8859-1")))
			{
				return "ISO-8859-1";
			}
			if (value.equals(new String(value.getBytes("GB2312"), "GB2312")))
			{
				return "GB2312";
			}
			if (value.equals(new String(value.getBytes("GBK"), "GBK")))
			{
				return "GBK";
			}
		} catch (UnsupportedEncodingException e)
		{
			logger.error("获取字符串的编码格式异常");
		}
		return "";
	}

	/**
	 * 计算起始行
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月29日 下午3:14:15
	 * @param pages
	 * @param pageSize
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public static int countPageStaticCount(int pages, int pageSize)
	{
		if (pages > 1)
		{
			return (pages - 1) * pageSize;
		}
		return 0;
	}

	/**
	 * 删除数组中的空值
	 * 
	 * @Description:
	 * @author: likui
	 * @date: 2016/5/6 17:32
	 * @param param
	 * @return
	 * @company: gyist
	 * @version V3.0.0
	 */
	public static String[] deleteArrayEmptyElement(String[] param)
	{
		if (null == param)
		{
			return null;
		}
		return stringToArray(arrayToString(param, ","), ",");
	}

	/**
	 * json字符串增加转译符
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年5月18日 上午10:22:07
	 * @param value
	 * @return
	 * @return : String
	 * @version V3.0.0
	 */
	public static String jsonToTranslation(String value)
	{
		if (StringUtils.isBlank(value))
		{
			return "";
		}
		return new JSONObject().element("key", "\"" + value + "\"").getString("key");
	}
}
