package com.gy.hsi.nt.api.common;

/**
 * 
 * @className:TempNo
 * @author:likui
 * @date:2015年7月27日
 * @desc:模板编号枚举类
 * @company:gyist
 */
public enum TempNo {

	/**
	 * 线下核款授权短信模板
	 */
	CHECK_MONEY("CHECK_MONEY"),

	/**
	 * 申报企业授权码短信发送模板
	 */
	APPLY_COMPANY_AUTH("APPLY_COMPANY_AUTH"),

	/**
	 * 申报企业成功短信发送模板
	 */
	APPLY_COMPANY_SUCCESS("APPLY_COMPANY_SUCCESS"),

	/**
	 * 申报服务公司付款通知短信发送模版
	 */
	APPLY_SERVICE_PAY_NOTICE("APPLY_SERVICE_PAY_NOTICE"),

	/**
	 * 登录密码找回短信发送模版
	 */
	LOGIN_PWD_RETRIVE_NOTE("LOGIN_PWD_RETRIVE_NOTE"),

	/**
	 * 登录密码重置短信发送模版
	 */
	LOGIN_PWD_RESET("LOGIN_PWD_RESET"),

	/**
	 * 交易密码重置短信发送模版
	 */
	TRADE_PWD_RESET("TRADE_PWD_RESET"),

	/**
	 * 交易密码重置授权码短信发送模板(申请成功)
	 */
	TRADE_PWD_RESET_APPLY_SUCCESS("TRADE_PWD_RESET_APPLY_SUCCESS"),

	/**
	 * 交易密码重置授权码短信发送模板（申请驳回）
	 */
	TRADE_PWD_RESET_APPLY_REJECTED("TRADE_PWD_RESET_APPLY_REJECTED"),

	/**
	 * 积分预付款账户告急短信发送模板
	 */
	PREPAID_ACCOUNT_EMERGENCY("PREPAID_ACCOUNT_EMERGENCY"),

	/**
	 * 积分预付款账户不足短信发送模板
	 */
	PREPAID_ACCOUNT_NOT_ENOUGH("PREPAID_ACCOUNT_NOT_ENOUGH"),

	/**
	 * 积分预付款账户偏少短信发送模板
	 */
	PREPAID_ACCOUNT_VERY_LITTLE("PREPAID_ACCOUNT_VERY_LITTLE"),

	/**
	 * 意外伤害保障生效
	 */
	ACCIDENT_INSURANCE_START("ACCIDENT_INSURANCE_START"),

	/**
	 * 意外伤害保障失效
	 */
	ACCIDENT_INSURANCE_END("ACCIDENT_INSURANCE_END"),

	/**
	 * 符合互生免费医疗补贴计划消息内容
	 */
	FREE_MEDICAL_PLAN("FREE_MEDICAL_PLAN"),

	/**
	 * 扣除年费成功短信模版
	 */
	DEDUCT_FEE_SUCCESS("DEDUCT_FEE_SUCCESS"),

	/**
	 * 扣除年费失败短信模版
	 */
	DEDUCT_FEE_FAILED("DEDUCT_FEE_FAILED"),

	/**
	 * 手机短信验证码发送模版
	 */
	MOBILE_VERIFY_CODE("MOBILE_VERIFY_CODE"),

	/**
	 * 互生系统邮箱绑定验证邮件模板（企业/消费者）
	 */
	BINDED_EMAIL_VERIFICATION("BINDED_EMAIL_VERIFICATION"),

	/**
	 * 互生系统密码找回验证邮箱邮件模板（企业/消费者
	 */
	LOGIN_PWD_RETRIVE_EMAIL("LOGIN_PWD_RETRIVE_EMAIL"),

	/**
	 * 订单自提码短信发送模版
	 */
	ORDER_SELF_PICK_CODE("ORDER_SELF_PICK_CODE");

	private String temoNo;

	TempNo(String temoNo) {
		this.temoNo = temoNo;
	}

	public String getTemoNo() {
		return temoNo;
	}
}
