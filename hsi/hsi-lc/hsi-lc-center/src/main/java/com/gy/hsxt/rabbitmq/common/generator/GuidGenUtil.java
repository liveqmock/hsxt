package com.gy.hsxt.rabbitmq.common.generator;



/**
 * 
 * @ClassName: GuidGenUtil 
 * @Description: GUID 生成工具类
 * @author Lee 
 * @date 2015-6-25 下午2:25:23
 */
public class GuidGenUtil {
	
	private static GuidAgent ga = new GuidAgent(SystemPropUtil.getIntValue("system.node"));
	
	/**
	 * 生成合 前缀的GUID
	 * 
	 * @param prefix
	 *            序列号前缀
	 * @return 返回字符串
	 */
	public static String getStringGuidWithPrefix(String prefix) {
		return prefix + ga.nextId();
	}

	/**
	 * 生成默认的GUID
	 * 
	 * @return 返回字符串
	 */
	public static String getStringGuid() {
		return getStringGuidWithPrefix("");
	}

	/**
	 * 生成合 前缀的GUID
	 * @param prefix
	 *            序列号前缀
	 * @return 返回长整型
	 */
	public static Long getLongGuidWithPrefix(String prefix) {
		return Long.parseLong(prefix + ga.nextId());
	}

	/**
	 * 生成合 前缀的GUID
	 * @param prefix
	 * @return 返回长整型
	 */
	public static Long getLongGuid() {
		return getLongGuidWithPrefix("");
	}
}
