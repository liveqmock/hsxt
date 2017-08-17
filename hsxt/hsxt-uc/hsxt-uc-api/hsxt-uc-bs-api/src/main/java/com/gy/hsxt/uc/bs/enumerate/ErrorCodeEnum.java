/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.bs.enumerate;

public enum ErrorCodeEnum {

	UNKNOW_ERROR(160000, "未知错误，请查看日志"),

	QUERY_DATABASE_ERROR(160001, "查询数据异常"),

	PROCESS_TIMEOUT(160002, "处理超时"),

	IO_ERROR(160003, "未成功加载配置文件(IO异常)"),

	CONNECT_ERROR(160004, "连接数据库超时"),

	UPDATE_DATEBASE_ERROR(160005, "更新数据异常"),

	SAVE_DATEBASE_ERROR(160006, "保存数据异常"),

	DELETE_DATEBASE_ERROR(160007, "删除数据异常"),

	/**
	 * 用户不存在
	 */
	USER_NOT_FOUND(160102, "用户不存在"),
	/**
	 * 用户已锁定
	 */
	USER_LOCKED(160104, "用户已锁定"),
	/**
	 * 用户已锁定
	 */
	USER_EXIST(160103, "用户已存在"),
	/**
	 * 渠道错误
	 */
	CHANNEL_IS_WRONG(160110, "渠道类型错误"),
	/**
	 * 用户密码错误
	 */
	USER_PWD_IS_WRONG(160108, "用户登录密码错误"),
	/**
	 * 用户已登录
	 */
	USER_IS_LOGIN(160144, "用户已登录"),
	/**
	 * 用户名为空
	 */
	USER_NAME_IS_EMPTY(160148, "用户名为空"),
	/**
	 * 数据解密错误
	 */
	DATA_DECRYPT(160146, "数据解密错误"),
	/**
	 * 查询互生卡信息异常
	 */
	HSCARD_QUERY_ERROR(160222, "查询互生卡信息异常"),
	/**
	 * 互生卡信息不存在
	 */
	HSCARD_NOT_FOUND(160223, "互生卡信息不存在"),
	/**
	 * 持卡人信息不存在
	 */
	CARDHOLDER_NOT_FOUND(160224, "持卡人信息不存在"),
	/**
	 * 查询客户号异常
	 */
	CUST_ID_QUERY_ERROR(160225, "查询客户号异常"),
	/**
	 * 非沉淀状态不能注销
	 */
	CARDSTATUS_NOT_SUBSIDE(160226, "非沉淀状态不能注销"),
	/**
	 * 当前操作员不是企业管理员
	 */
	OPER_IS_NOT_ADMIN(160301, "当前操作员不是企业管理员"),
	/**
	 * 更新实名状态异常
	 */
	REALNAMEAUTH_UPDATE_FAIL(160227, "更新实名状态异常"),
	/**
	 * 检查证件是否注册异常
	 */
	CHECK_ID_REGISTERED_FAIL(160228, "检查证件是否注册异常"),
	/**
	 * 保存实名注册信息异常
	 */
	REALNAME_REGINFO_SAVE_FAIL(160229, "保存实名注册信息异常"),
	
	/**
	 * 更新互生卡信息异常
	 */
	HSCARD_UPDATE_FAIL(160231, "更新互生卡信息异常"),
	

	/**
	 * 互生卡的卡状态为空
	 */
	HSCARD_NO_CARDSTATUS(160235, "互生卡的卡状态为空"),
	/**
	 * 互生卡已启用
	 */
	HSCARD_ENABLED(160236, "互生卡已启用"),
	/**
	 * 保存非持卡人信息异常
	 */
	NOCARDHOLDER_SAVE_FAIL(160237, "保存非持卡人信息异常"),

	/**
	 * 企业未找到
	 */
	ENT_IS_NOT_FOUND(160147, "企业不存在"),

	/**
	 * 互生号不存在
	 */
	RES_NO_IS_NOT_FOUND(160121, "互生号不存在"),

	/**
	 * 互生卡已绑定
	 */
	RES_NO_IS_BIND(160125, "互生卡已绑定"),

	/**
	 * 互生号不能为空
	 */
	RES_NO_IS_NULL(160126, "互生号不能为空"),

	/**
	 * 互生号已存在被使用
	 */
	RES_NO_EXIST(160127, "互生号已存在被使用"),

	/**
	 * 互生卡已停用
	 */
	HSCARD_DISABLED(160122, "互生卡已停用"),

	/**
	 * 互生卡已停用
	 */
	HSCARD_IS_LOSS(160131, "互生卡已挂失"),

	/**
	 * 实名认证信息不存在
	 */
	REALNAMEAUTH_NOT_FOUND(160206, "实名认证信息不存在"),

	/**
	 * 客户号不存在
	 */
	CUST_ID_IS_NOT_FOUND(160101, "客户号不存在"),

	/**
	 * 企业非激活状态
	 */
	ENT_WAS_INACTIVITY(160350, "企业非激活状态"),

	/**
	 * 会话非法
	 */
	SESSION_IS_ILLEGAL(160106, "会话非法"),

	/**
	 * 会话
	 */
	SESSION_IS_EXPIRED(160107, "会话过期"),

	/**
	 * 短信发送失败
	 */
	SM_SEND_FAILED(160351, "短信发送失败"),

	/**
	 * 没有设置邮箱
	 */
	EMAIL_NOT_SET(160352, "邮箱未设置"),

	/**
	 * 邮箱未验证
	 */
	EMAIL_NOT_CHECK(160141, "邮箱未验证"),

	/**
	 * 用户邮箱错误
	 */
	EMAIL_NOT_MATCH(160353, "用户邮箱错误"),

	/**
	 * 手机未验证
	 */
	MOBILE_NOT_CHECK(160129, "手机未验证"),

	/**
	 * 短信验证码不正确
	 */
	SMS_NOT_MATCH(160133, "短信验证码不正确"),

	/**
	 * 验证邮件不正确
	 */
	MAIL_NOT_MATCH(160136, "验证邮件不正确"),

	/**
	 * 短信验证码已过期
	 */
	SMS_IS_EXPIRED(160134, "短信验证码已过期"),

	/**
	 * 验证邮件已过期
	 */
	MAIL_IS_EXPIRED(160137, "验证邮件已过期"),

	/**
	 * EMAIL发送失败
	 */
	EMAIL_SEND_FAILED(160354, "EMAIL发送失败"),

	/**
	 * 邮件验证失败
	 */
	EMAIL_CHECK_FAILED(160143, "邮件验证失败,邮件验证码错误或已失效"),

	/**
	 * 必传参数值为空
	 */
	PARAM_IS_REQUIRED(160355, "必传参数 %s为空"),

	/**
	 * 系统用户信息不存在
	 */
	SYSOPER_NOT_FOUND(160202, "系统用户信息不存在"),

	/**
	 * 更新系统用户信息失败
	 */
	SYSOPER_UPDATE_ERROR(160203, "更新系统用户信息失败"),

	/**
	 * 保存系统用户信息失败
	 */
	SYSOPER_SAVE_ERROR(160204, "保存系统用户信息失败"),

	/**
	 * 秘钥长度不是6位
	 */
	SECRETKEY_LENGTH_SIXTEEN(160205, "秘钥长度不是16位"),

	/**
	 * 实名状态为[已实名注册]方能保存实名认证信息
	 */
	REALNAME_STATUS_NOT_REGISTERED(160221, "实名状态为[已实名注册]方能保存实名认证信息"),

	/**
	 * 证件已实名注册或实名认证
	 */
	CHECK_ID_IS_REGISTERED(160219, "证件已实名注册或实名认证"),

	/**
	 * 证件已实名注册
	 */
	ID_IS_REGISTERED(160220, "证件已实名注册"),

	/**
	 * 查询持卡人信息异常
	 */
	CARDHOLDER_QUERY_ERROR(160214, "查询持卡人信息异常"),

	/**
	 * 保存持卡人信息异常
	 */
	CARDHOLDER_SAVE_ERROR(160200, "保存持卡人信息异常"),

	/**
	 * 保存网络信息异常
	 */
	NETWORK_SAVE_ERROR(160211, "保存网络信息异常"),

	/**
	 * 更新非持卡人信息异常
	 */
	NOCARDHOLDER_UPDATE_FAIL(160251, "更新非持卡人信息异常"),

	/**
	 * 通过客户号查询非持卡人信息异常
	 */
	NOCARDHOLDER_QUERY_ERROR(160256, "通过客户号查询非持卡人信息异常"),

	/**
	 * 通过手机号码查询客户号异常
	 */
	CUST_ID_IS_NOT_FOUND_MOBILE(160254, "通过手机号码查询客户号异常"),

	/**
	 * 手机号码不正确
	 */
	MOBILE_NOT_CORRECT(160140, "手机号码不正确"),

	/**
	 * 非持卡人信息不存在
	 */
	NOCARDHOLDER_NOT_FOUND(160253, "非持卡人信息不存在"),

	/**
	 * 更新持卡人信息异常
	 */
	CARDHOLDER_UPDATE_ERROR(160201, "更新持卡人信息异常"),

	/**
	 * 姓名不正确
	 */
	REALNAME_NOT_MATCH(160145, "姓名不正确"),

	/**
	 * 保存互生卡信息异常
	 */
	HSCARD_SAVE_ERROR(160130, "保存互生卡信息异常"),

	/**
	 * 证件号码不正确
	 */
	CERNO_NOT_MATCH(160109, "证件号码不正确"),

	/**
	 * 查询实名认证信息异常
	 */
	REALNAMEAUTH_QUERY_ERROR(160212, "查询实名认证信息异常"),

	/**
	 * 用户类型非法
	 */
	USERTYPE_IS_ILLEGAL(160150, "用户类型非法"),

	/**
	 * 非法参数值
	 */
	PARAM_IS_ILLEGAL(160149, "非法参数值"),

	/**
	 * 密保问题不正确或不存在
	 */
	PWD_QUESTION_NOT_FOUND(160120, "密保问题不正确或未设置"),

	/**
	 * 保存密保失败
	 */
	PWD_QUESTION_SAVE_ERROR(160210, "保存密保失败"),
	/**
	 * 密保答案错误
	 */
	PWD_ANSWER_IS_WRONG(160119, "密保答案错误"),
	/**
	 * 设备未找到
	 */
	DEVICE_IS_NOT_FOUND(160363, "设备未找到"),

	/**
	 * 问题修改失败
	 */
	QUESTION_MODIFY_FAILED(160356, "问题修改失败"),
	/** 操作员未绑定互生卡 */
	OPERATOR_NOT_BIND_RES_NO(160357, "操作员未绑定互生卡"),
	/** 短信验证码验证失败 */
	SM_CODE_NOT_MATCH(160358, "短信验证码验证失败"),
	/** 登录密码错误 */
	PWD_LOGIN_ERROR(160359, "登录密码错误"),
	/** 交易密码错误 */
	PWD_TRADE_ERROR(160360, "交易密码错误"),
	/** 密码修改失败 */

	PWD_MODIFY_FAILED(160361, "密码修改失败"),
	/** 收货地址修改失败 */
	RECEIVE_ADDR_MODIFY_FAILED(160362, "收货地址修改失败"),

	BANK_ACCT_NOT_FOUND(160364, "银行帐户未找到"),

	POS_POINT_RATE_NOT_SET(160365, "POS机积分比例未设置"),

	HS_CARD_CODE_IS_WRONG(160366, "互生卡暗码不正确"),

	/** 设备未签入 */
	DEVICE_NOT_SIGN(160367, "设备未签入"),

	POS_MATCH_MAC_FAILED_SIGNUP(160368, "POS机比对MAC失败次数超过最大限制，需重新登录"),

	POS_MATCH_MAC_FAILED(160369, "POS机比对MAC失败"),

	RANDOM_TOKEN_IS_EMPTY(160370, "随机token为空"),

	RANDOM_TOKEN_NOT_MATCH(160371, "随机token不正确"),

	/** 设备已存在 */
	DEVICE_EXIST(160372, "设备已存在"),

	/** 未登录 */
	NO_LOGIN(160373, "未登录或已被踢出"),

	SOLR_INDEX_ADD_FAILED(160374, "solr索引添加失败"),

	SOLR_INDEX_UPDATE_FAILED(160375, "solr索引更新失败"),

	SOLR_INDEX_DELETE_FAILED(160376, "solr索引删除失败"),

	SOLR_INDEX_SEARCH_FAILED(160377, "solr索引查询失败"),

	ENT_POINT_RATE_NOT_SET(160378, "企业机积分比例未设置"),

	PWD_QUESTION_USER_NOT_MATCH(160379, "密保问题不属于该用户"),

	RECEIVE_ADDR_NOT_MATCH(160380, "用户没有配置该收货地址"),

	RECEIVE_ADDR_ID_IS_NULL(160381, "用户收货地址ID为空"),

	ENT_GROUP_EXIST(160382, "此用户组已存在"),

	USER_NO_PERMISSION(160383, "用户没有权限访问该操作"),

	ENT_IS_EXIST(160384, "企业已存在"), ;
	int value;
	String desc;

	private ErrorCodeEnum(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
}
