package com.gy.hsi.nt.server.util.enumtype;

/**
 * 
 * @className:RoutingKey
 * @author:likui
 * @date:2015年8月6日
 * @desc:交换机路由key
 * @company:gysit
 */
public enum RoutingKey
{

	/**
	 * 短信路由key--高优先级
	 */
	RK_NOTE_HIGH("rk.note.high"),
	/**
	 * 短信路由key--中优先级
	 */
	RK_NOTE_MIDDLE("rk.note.middle"),
	/**
	 * 短信路由key--低优先级
	 */
	RK_NOTE_LOW("rk.note.low"),
	/**
	 * 邮件路由key--高优先级
	 */
	RK_EMAIL_HIGH("rk.email.high"),
	/**
	 * 邮件路由key--中优先级
	 */
	RK_EMAIL_MIDDLE("rk.email.middle"),
	/**
	 * 邮件路由key--低优先级
	 */
	RK_EMAIL_LOW("rk.email.low"),
	/**
	 * 系统互动信息路由key--高优先级
	 */
	RK_DYNAMIC_SYS_HIGH("rk.dynamic.sys.high"),
	/**
	 * 系统互动信息路由key--中优先级
	 */
	RK_DYNAMIC_SYS_MIDDLE("rk.dynamic.sys.middle"),
	/**
	 * 系统互动信息路由key--低优先级
	 */
	RK_DYNAMIC_SYS_LOW("rk.dynamic.sys.low"),
	/**
	 * 业务互动信息路由key--高优先级
	 */
	RK_DYNAMIC_BIZ_HIGH("rk.dynamic.biz.high"),
	/**
	 * 业务互动信息路由key--中优先级
	 */
	RK_DYNAMIC_BIZ_MIDDLE("rk.dynamic.biz.middle"),
	/**
	 * 业务互动信息路由key--低优先级
	 */
	RK_DYNAMIC_BIZ_LOW("rk.dynamic.biz.low");

	private String key;

	RoutingKey(String key)
	{
		this.key = key;
	}

	public String getKey()
	{
		return key;
	}
}
