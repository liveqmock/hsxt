package com.gy.hsxt.common.utils;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 字符串处理工具类，提供了一些对字符串进行处理的静态方法
 */
public final class StringUtils {

	/* 私有的构造方法，保证此类不能外部被实例化 */
	private StringUtils()
	{
	}

	/**
	 * 如果字符串等于null、空白字符(“”)、空格(“ ”)则返回true,否则返回false
	 * 
	 * @param str
	 *            String 要比较的字符串
	 * @return boolean
	 */
	public static boolean isBlank(String str)
	{
		if (null == str)
		{
			return true;
		}
		return ("").equals(str.trim()) || ("null").equals(str.trim());
	}

	/**
	 * 如果字符串等于null、空白字符(“”)、空格(“ ”)则返回true,否则返回false
	 * 
	 * @param obj
	 *            Object 要比较的字符串
	 * @return boolean
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isBlank(Object obj)
	{
		if (null == obj)
		{
			return true;
		}
		if (obj instanceof String)
		{
			return ("").equals(obj.toString().trim()) || ("null").equals(obj.toString().trim());
		}
		if (obj instanceof Object[])
		{
			return ((Object[]) obj).length == 0;
		}
		if (obj instanceof Collection)
		{
			return ((Collection) obj).isEmpty();
		}
		return false;
	}

	/**
	 * 如果字符串不等于null、空白字符(“”)、空格(“ ”)则返回true,否则返回false
	 * 
	 * @param str
	 *            String 要比较的字符串
	 * @return boolean
	 */
	public static boolean isNotBlank(String str)
	{
		return (!isBlank(str));
	}

	/**
	 * 如果字符串等于null、空白字符(“”)、空格(“ ”)则返回false,否则返回true
	 * 
	 * @param obj
	 *            Object 要比较的字符串
	 * @return boolean
	 */
	public static boolean isNotBlank(Object obj)
	{
		return (!isBlank(obj));
	}

	/**
	 * 如果字符串等于null、空白字符("")、空格(" ")则返回空白字符(""), 否则返回一个将字符串的前后空格去掉的字符串
	 * 
	 * @param str
	 *            String 要处理的字符串
	 * @return String
	 */
	public static String trimToBlank(String str)
	{
		if (isBlank(str))
		{
			return "";
		}
		return str.trim();
	}

	/**
	 * 如果字符串等于null、空白字符("")、空格(" ")则返回null, 否则返回一个将字符串的前后空格去掉的字符串
	 * 
	 * @param str
	 *            String 要处理的字符串
	 * @return String
	 */
	public static String trimToNull(String str)
	{
		if (isBlank(str))
		{
			return null;
		}
		return str.trim();
	}

	/**
	 * 验证数据是否是正整数字
	 * 
	 * @param str
	 */
	public static boolean isNumber(String str)
	{
		// 非空验证
		if (StringUtils.isBlank(str))
		{
			return false;
		}
		// 定义正则表达式验证规则变量
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("[0-9]*");
		java.util.regex.Matcher match = pattern.matcher(str);

		// 执行
		if (match.matches() == false)
		{
			return false;
		} else
		{
			return true;
		}
	}

	/**
	 * 将对象使用指定的分隔符转换成一个字符串，
	 * 
	 * @param delimiter
	 *            分隔符
	 * @param ignore
	 *            为true忽略null值
	 * @param objs
	 *            分隔对象
	 * @return
	 */
	public static String join(String delimiter, boolean ignore, Object... objs)
	{
		if (objs == null || 0 == objs.length)
		{
			return "";
		}
		StringBuffer bf = new StringBuffer();
		Object obj = null;
		int ind = 0;
		for (int i = 0; i < objs.length; i++)
		{
			obj = objs[i];
			if (null == obj && true == ignore)
			{
				continue;
			} else
			{
				if (0 == ind)
				{
					bf.append(obj);
				} else
				{
					bf.append(delimiter).append(obj);
				}
				ind++;
			}
		}
		return bf.toString();
	}

	/**
	 * 使用指定的分隔符将字符串分割成一个字符串数组
	 * 
	 * @param input
	 *            字符串
	 * @param delimiter
	 *            分隔符
	 * @return
	 */
	public static String[] split(String input, String delimiter)
	{
		String[] values = new String[] { input };
		if (null != input && null != delimiter && -1 != input.indexOf(delimiter))
		{
			values = input.split(delimiter);
		}
		return values;
	}

	/**
	 * 将特殊字符(<、>、"、'等)转换成对应的实体
	 * 
	 * @param s
	 *            需要转换的字符串
	 * @return
	 */
	public static final String htmlEncode(String s)
	{
		return htmlEncode(s, true);
	}

	/**
	 * 将特殊字符(<、>、"、'等)转换成对应的实体
	 * 
	 * @param s
	 *            需要转换的字符串
	 * @param encodeSpecialChars
	 *            对特殊字符进行编码
	 * @return
	 */
	public static final String htmlEncode(String s, boolean encodeSpecialChars)
	{
		s = trimToBlank(s);
		StringBuffer str = new StringBuffer();
		for (int j = 0; j < s.length(); j++)
		{
			char c = s.charAt(j);
			if (c < '\200')
			{
				switch (c)
				{
				case 34: // '"'
					str.append("\"");
					break;

				case 38: // '&'
					str.append("&");
					break;

				case 60: // '<'
					str.append("<");
					break;

				case 62: // '>'
					str.append(">");
					break;

				default:
					str.append(c);
					break;
				}
				continue;
			}
			if (encodeSpecialChars && c < '\377')
			{
				String hexChars = "0123456789ABCDEF";
				int a = c % 16;
				int b = (c - a) / 16;
				String hex = (new StringBuilder()).append("").append(hexChars.charAt(b)).append(hexChars.charAt(a))
						.toString();
				str.append((new StringBuilder()).append("&#x").append(hex).append(";").toString());
			} else
			{
				str.append(c);
			}
		}

		return str.toString();
	}

	/**
	 * 处理url 是否/结尾，如果没有加上/
	 * 
	 * @param url
	 * @return
	 */
	public static String handlerUrl(String url)
	{
		if (null != url && !"".equals(url))
		{
			if (!url.trim().endsWith("/"))
			{
				return url.trim() + "/";
			}
		}
		return url;
	}

	/**
	 * join ' 号
	 * 
	 * @param str
	 *            示例 "12443579533804544,12443588789224448"
	 * @param split
	 *            字符串分隔符号
	 * @return 示例 "'12443579533804544','12443588789224448'"
	 */
	public static String joinSemicolon(String str, String split)
	{
		if (isBlank(str))
			return str;
		String[] s = split(str, split);

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length; i++)
		{
			sb.append("'").append(s[i]).append("',");
		}
		return sb.length() > 0 ? sb.deleteCharAt(sb.length() - 1).toString() : sb.toString();
	}

	/**
	 * 根据指定字符在指定字符串截取
	 * 
	 * @param str
	 *            示例 "java.lang.Object"
	 * @param separator
	 *            "."
	 * @return Object
	 */
	public static String substringAfterLast(String str, String separator)
	{

		if (isBlank(str))
		{
			return str;
		}

		if (isBlank(separator))
		{
			return "";
		}

		int pos = str.lastIndexOf(separator);

		if ((pos == -1) || (pos == str.length() - separator.length()))
		{
			return "";
		}

		return str.substring(pos + separator.length());
	}

	/**
	 * 在每一个类里面写一个main方法,可以很方便的 对这个类进行测试
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args)
	{

		// String s = null;
		// System.out.println(isBlank(s));
		// System.out.println(isNotBlank(s));
		//
		// s = "";
		// System.out.println(isBlank(s));
		// System.out.println(isNotBlank(s));
		//
		// s = " ";
		// System.out.println(isBlank(s));
		// System.out.println(isNotBlank(s));
		//
		// s = " ";
		// System.out.println("[" + trimToNull(s) + "]");
		// System.out.println("[" + trimToBlank(s) + "]");

		// System.out.println(StringUtils.join(",", "ddd", null, "fff"));

		// String input = "aa , bb, ccc";
		// String[] arr = StringUtils.split(null, "z");
		// System.out.println(StringUtils.join("-", arr));
		// System.out.println(arr.length);
		// for (int i = 0; i < arr.length; i++) {
		// System.out.println(arr[i]);
		// }
		//
		// System.out.println(StringUtils.join("-", (Object[]) new Integer[] {
		// 11,
		// 22 }));

		// System.out.println(StringUtils.join(",", false, new Object[] { null,
		// "a", 2, 3, null }));

		// String str = "<br>？";
		// System.out.println(htmlEncode(str, true));
		// System.out.println('\377');

		// System.out.println(handlerUrl(" http://www.baidu.com/ "));

		// String a = "12443579533804544,12443588789224448";
		// System.out.println(isNumber("-123"));
		// System.out.println(joinSemicolon(a, ","));
		String input = "aa,bb,ccc";
		String[] arr = StringUtils.split(input, ",");
		List<String> list = new ArrayList<String>();
		list.add(input);
		System.out.println(isBlank(arr));
		System.out.println(isNotBlank(arr));
	}

	public final static String WHERE = "Where";

	/**
	 * 检查字符串是否为<code>null</code>或空字符串<code>""</code>。
	 * 
	 * <pre>
	 * StringUtil.isEmpty(null)      = true
	 * StringUtil.isEmpty("")        = true
	 * StringUtil.isEmpty(" ")       = false
	 * StringUtil.isEmpty("bob")     = false
	 * StringUtil.isEmpty("  bob  ") = false
	 * </pre>
	 * 
	 * @param str
	 *            要检查的字符串
	 * 
	 * @return 如果为空, 则返回<code>true</code>
	 */
	public static boolean isEmpty(final String str)
	{
		return str == null || str.length() == 0;
	}

	public static String replace(String inString, String oldPattern, String newPattern)
	{
		if (!hasLength(inString) || !hasLength(oldPattern) || newPattern == null)
		{
			return inString;
		}
		StringBuilder sb = new StringBuilder();
		int pos = 0; // our position in the old string
		int index = inString.indexOf(oldPattern);
		// the index of an occurrence we've found, or -1
		int patLen = oldPattern.length();
		while (index >= 0)
		{
			sb.append(inString.substring(pos, index));
			sb.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		sb.append(inString.substring(pos));
		// remember to append any characters to the right of a match
		return sb.toString();
	}

	/**
	 * Check that the given CharSequence is neither <code>null</code> nor of
	 * length 0. Note: Will return <code>true</code> for a CharSequence that
	 * purely consists of whitespace.
	 * <p>
	 * 
	 * <pre>
	 * StringUtils.hasLength(null) = false
	 * StringUtils.hasLength("") = false
	 * StringUtils.hasLength(" ") = true
	 * StringUtils.hasLength("Hello") = true
	 * </pre>
	 * 
	 * @param str
	 *            the CharSequence to check (may be <code>null</code>)
	 * @return <code>true</code> if the CharSequence is not null and has length
	 */
	public static boolean hasLength(CharSequence str)
	{
		return (str != null && str.length() > 0);
	}

	/**
	 * Check that the given String is neither <code>null</code> nor of length 0.
	 * Note: Will return <code>true</code> for a String that purely consists of
	 * whitespace.
	 * 
	 * @param str
	 *            the String to check (may be <code>null</code>)
	 * @return <code>true</code> if the String is not null and has length
	 */
	public static boolean hasLength(String str)
	{
		return hasLength((CharSequence) str);
	}

	public static byte[] getUtf8Bytes(String str)
	{
		if (hasLength(str))
		{
			try
			{
				return str.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e)
			{
				// ignore

			}
		}
		return null;
	}

	public static String getStringFromUtf8Bytes(byte[] tmpArray)
	{
		if (tmpArray != null && tmpArray.length > 0)
		{
			try
			{
				return new String(tmpArray, "UTF-8");
			} catch (UnsupportedEncodingException e)
			{
				// ignore

			}
		}
		return null;
	}

	public static boolean equals(String str1, String str2)
	{
		if (str1 == null && str2 == null)
		{
			return true;
		}
		if (str1 == null || str2 == null)
		{
			return false;
		}
		return str1.equals(str2);
	}

	/**
	 * 功能：检查字符串是否是由数字组成，不包含小数点。 tianxh
	 * 
	 * @param str
	 *            要判定字符串
	 * @return true 是数值类型,或为空 false 不空并且不是数字组成
	 */
	public static boolean isNumer(String s)
	{
		if (isBlank(s))
		{
			return false;
		}
		int i = s.trim().length();
		if (i == 0)
		{
			return false;
		}
		byte abyte0[] = new byte[i];
		abyte0 = s.getBytes();
		for (int j = 0; j < i; j++)
		{
			if ((abyte0[j] < 48 || abyte0[j] > 57))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * 说明：将null值转为空值
	 * 
	 * @param inputValue
	 * @return
	 */
	public static String nullToEmpty(String inputValue)
	{

		if (inputValue == null || "null".equals(inputValue))
		{
			return "";
		}
		return inputValue.trim();
	}

	/**
	 * 说明：将null或者""值转为字符0
	 * 
	 * @param inputValue
	 * @return
	 */
	public static String nullToZero(String inputValue)
	{

		if (inputValue == null || "".equals(inputValue))
		{
			return "0";
		}
		return inputValue;
	}

	/**
	 * 
	 * 方法描述 :是否英文字母 创建者：tianxh 版本： v1.0
	 * 
	 * @param c
	 * @return boolean
	 */
	public static boolean isWord(char c)
	{
		return String.valueOf(c).matches("^[a-zA-Z]*");
	}

	/**
	 * 获取当前时间的Timestamp对象
	 * 
	 * @return
	 */
	public static Timestamp getNowTimestamp()
	{
		return new Timestamp(new Date().getTime());
	}

	/**
	 * 
	 * @param date
	 *            日期
	 * @return 字符串日期 yyyymmdd
	 */
	public static String getSimpleDate(Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}

	/**
	 * 通过互生号获取用户类型
	 * 
	 * @param perResNo
	 *            互生号
	 * @return 用户类型 1非持卡人 2持卡人 3企业
	 */
	public static String getUserTypeByResNo(String resNo)
	{
		String userType = "";
		if (StringUtils.isBlank(resNo))
		{
			return userType;
		}
		String hsResNo = resNo.trim();
		if (hsResNo.length() != 11)
		{
			return userType;
		}
		String lastFour = hsResNo.substring(7, 11);
		return "0000".equals(lastFour) ? "3" : "2";
	}

	/**
	 * 通过客户号获取用户类型
	 * 
	 * @param custId
	 * @return
	 */
	public static String getUserTypeByCustId(String custId)
	{
		String userType = "";
		if (!StringUtils.isBlank(custId))
		{
			String cid = custId.trim();
			int length = cid.length();
			if (19 != length && 18 != length && 23 != length)
			{
				return userType;
			}
			String first = cid.substring(0, 1);
			switch (first)
			{
			case "1":
				userType = "pos";
				break;
			case "2":
				userType = "cardReader";
				break;
			case "3":
				userType = "pad";
				break;
			case "8":
				userType = "sys";
				break;
			case "9":
				userType = "1";
				break;
			default:
				String resNo = custId.substring(0, 11);
				userType = getUserTypeByResNo(resNo);
				break;
			}
		}
		return userType;
	}

	/**
	 * 校验银行卡卡号
	 * 
	 * @param cardId
	 * @author LiZhi Peter
	 * @return
	 */
	public static boolean checkBankCard(String cardId)
	{
		char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
		if (bit == 'N')
		{
			return false;
		}
		return cardId.charAt(cardId.length() - 1) == bit;
	}

	/**
	 * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
	 * 
	 * @param nonCheckCodeCardId
	 * @author LiZhi Peter
	 * @return
	 */
	public static char getBankCardCheckCode(String nonCheckCodeCardId)
	{
		if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
				|| !nonCheckCodeCardId.matches("\\d+"))
		{
			// 如果传的不是数据返回N
			return 'N';
		}
		char[] chs = nonCheckCodeCardId.trim().toCharArray();
		int luhmSum = 0;
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++)
		{
			int k = chs[i] - '0';
			if (j % 2 == 0)
			{
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
	}
}
