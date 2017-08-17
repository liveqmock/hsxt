package com.gy.hsi.nt.server.util.enumtype;

/**
 * 
 * @className:ParamsKey
 * @author:likui
 * @date:2015年7月29日
 * @desc:参数
 * @company:
 */
public enum ParamsKey
{

	/**
	 * 所有参数key
	 */
	NT_PARAMS("nt.params"),

	/**
	 * 短信参数
	 */
	SMS_ADDRESS("sms.address"), SMS_CUSTOMERID("sms.customerid"), SMS_USERID("sms.userid"), SMS_PASSWORD("sms.password"), SMS_APPID("sms.appid"), SMS_SIGN("sms.sign"),

	/**
	 * 邮件参数
	 */
	EMAIL_SERVER("email.server"), EMAIL_SMTP("email.smtp"), EMAIL_USERNAME("email.username"), EMAIL_PASSWORD("email.password"),

	/**
	 * 互动参数
	 */
	DYNAMIC_URL("dynamic.url"), DYNAMIC_ACCESS_ID("dynamic.access.id"), DYNAMIC_ACCESS_PWD("dynamic.access.pwd"), DYNAMIC_APP_KEY("dynamic.app.key"), DYNAMIC_SIGN("dynamic.sign"),

	/**
	 * 重发时间间隔参数
	 */
	RESEND_TIME("resend.time"), RESEND_COUNT("resend.count"),

	/**
	 * 是否重复扫描
	 */
	HIGH_RESEND_ORDER("high.resend.order"), MIDDLE_RESEND_ORDER("middle.resend.order"), LOW_RESEND_ORDER("low.resend.order"),
	/**
	 * 重发时查询每页多少行
	 */
	QUERY_PAGE_SIZE("query.page.size"),
	/**
	 * 手机号码、邮箱的正则
	 */
	PHONE_PATTERN("phone.pattern"), EMAIL_PATTERN("email.pattern"),

	/**
	 * 模板参数key
	 */
	MSG_TEMPLATE("MSG_TEMPLATE");

	private String key;

	ParamsKey(String key)
	{
		this.key = key;
	}

	public String getKey()
	{
		return key;
	}
}
