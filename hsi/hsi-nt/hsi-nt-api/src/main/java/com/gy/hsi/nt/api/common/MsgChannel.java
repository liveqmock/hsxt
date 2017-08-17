package com.gy.hsi.nt.api.common;

/**
 * 
 * @className:MsgChannel
 * @author:likui
 * @date:2015年7月31日
 * @desc:消息渠道枚举类
 * @company:gysit
 */
public enum MsgChannel
{

	/**
	 * 短信
	 */
	NOTE(1),
	/**
	 * 互动消息(业务消息)
	 */
	DYNAMIC_BIZ(2),
	/**
	 * 邮件
	 */
	EMAIIL(3),
	/**
	 * 互动消息(系统消息)
	 */
	DYNAMIC_SYS(4);

	private int channel;

	MsgChannel(int channel)
	{
		this.channel = channel;
	}

	public int getChannel()
	{
		return channel;
	}
}
