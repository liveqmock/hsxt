package com.gy.hsxt.common.constant;

/**
 * 
 * @className:StartResType
 * @author:likui
 * @date:2015年9月25日
 * @desc:托管企业启用资源类型
 * @company:gyist
 */
public enum StartResType {

	/**
	 * 首段资源
	 */
	FIRST_PARAGRAPH(1),
	/**
	 * 创业资源
	 */
	PIONEER(2),
	/**
	 * 全部资源
	 */
	ALL(3),

	/** 正常成员企业 **/
	NORMAL_MEMBER_ENT(4),

	/** 免费成员企业 **/
	FREE_MEMBER_ENT(5);

	private int code;

	StartResType(int code)
	{
		this.code = code;
	}

	public int getCode()
	{
		return code;
	}
}
