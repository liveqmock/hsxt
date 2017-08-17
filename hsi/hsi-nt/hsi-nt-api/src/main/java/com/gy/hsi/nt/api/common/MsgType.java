package com.gy.hsi.nt.api.common;

/**
 * 互动消息类型枚举类
 * 
 * @Package: com.gy.hsi.nt.api.common
 * @ClassName: MsgType
 * @Description: TODO
 * @author: likui
 * @date: 2016年3月25日 下午5:54:39
 * @company: gyist
 * @version V3.0.0
 */
public enum MsgType
{

	/**
	 * 公开信
	 */
	PUBLIC_NOTICE(12),

	/**
	 * 私信
	 */
	PRIVATE_LETTER(20);

	private int msgType;

	MsgType(int msgType)
	{
		this.msgType = msgType;
	}

	public int getMsgType()
	{
		return msgType;
	}
}
