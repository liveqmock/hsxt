package com.gy.hsxt.bs.common.enumtype.quota;

/**
 * 
 * @className: AppReason
 * @author:likui
 * @date:2015年9月2日
 * @desc:申请理由枚举类
 * @company:gyist
 */
public enum AppReason {

	/**
	 * 配额售罄
	 */
	NOHAVE(1),
	/**
	 * 配额不足
	 */
	LITTLE(2),
	/**
	 * 人口增加
	 */
	PADD(3),
	/**
	 * 人口减少
	 */
	PLESSEN(4),
	/**
	 * 其他
	 */
	OTHER(5);

	private int code;

	public int getCode()
	{
		return code;
	}

	AppReason(int code)
	{
		this.code = code;
	}
}
