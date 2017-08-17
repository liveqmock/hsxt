package com.gy.hsi.nt.server.util.enumtype;

/**
 * 
 * @className:ResType
 * @author:likui
 * @date:2015年7月30日
 * @desc:资源类型枚举类
 * @company:gysit
 */
public enum ResType
{
	/**
	 * 总平台
	 */
	A,
	/**
	 * 地区平台
	 */
	F,
	/**
	 * 管理公司
	 */
	M,
	/**
	 * 服务公司
	 */
	S,
	/**
	 * 托管企业
	 */
	T,
	/**
	 * 成员企业
	 */
	B,
	/**
	 * 持卡人
	 */
	P;
	public static ResType getType(String type)
	{
		return valueOf(type.toUpperCase());
	}
}
