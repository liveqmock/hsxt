package com.gy.hsi.nt.server.util.enumtype;

/**
 * 
 * @className:PushType
 * @author:likui
 * @date:2015年7月29日
 * @desc:互动消息推送类型
 * @company:gyist
 */
public enum PushType
{
	/**
	 * 个人
	 */
	SINGLE("1"),
	/**
	 * 多人
	 */
	MULTIPLE("2"),
	/**
	 * 群组
	 */
	GROUP("3");

	private String pushType;

	PushType(String pushType)
	{
		this.pushType = pushType;
	}

	public String getPushType()
	{
		return pushType;
	}
}
