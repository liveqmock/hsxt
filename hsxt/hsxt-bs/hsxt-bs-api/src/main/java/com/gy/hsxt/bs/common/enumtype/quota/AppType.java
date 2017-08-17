package com.gy.hsxt.bs.common.enumtype.quota;

/**
 * 
 * @className:AppType
 * @author:likui
 * @date:2015年9月2日
 * @desc:申请类型枚举类
 * @company:gyist
 */
public enum AppType {

	/**
	 * 首次配置
	 */
	FIRST(1),
	/**
	 * 增加
	 */
	ADD(2),
	/**
	 * 减少
	 */
	LESSEN(3);

	private int code;

	public int getCode()
	{
		return code;
	}

	AppType(int code)
	{
		this.code = code;
	}
}
