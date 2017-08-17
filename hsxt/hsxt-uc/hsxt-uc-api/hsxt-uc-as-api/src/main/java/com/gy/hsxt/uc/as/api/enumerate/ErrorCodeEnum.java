/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.api.enumerate;

import java.util.HashMap;

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
	 * 用户已存在
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
	 * 互生卡已挂失
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
	 * 邮箱验证码不正确
	 */
	MAIL_NOT_MATCH(160136, "邮箱验证码不正确"),

	/**
	 * 短信验证码已过期
	 */
	SMS_IS_EXPIRED(160134, "短信验证码已过期"),

	/**
	 * 邮箱验证码已过期
	 */
	MAIL_IS_EXPIRED(160137, "邮箱验证码已过期"),

	/**
	 * EMAIL发送失败
	 */
	EMAIL_SEND_FAILED(160354, "EMAIL发送失败"),

	

	/**
	 * 必传参数值为空
	 */
	PARAM_IS_REQUIRED(160355, "必传参数为空"),

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
	NOCARDHOLDER_UPDATE_ERROR(160251, "更新非持卡人信息异常"),

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
	 * 证件号码或者证件类型不正确
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
	 * 密保问题未设置
	 */
	PWD_QUESTION_IS_EMPTY(160120, "密保问题未设置"),

	/**
	 * 保存密保失败
	 */
	PWD_QUESTION_SAVE_ERROR(160210, "保存密保失败"),
	
	
	/**
	 * 密保答案错误
	 */
	PWD_INFO_IS_WRONG(160119, "密保信息不正确"),
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

	ENT_IS_EXIST(160384, "企业已存在"), 
	
	AUTHCODE_NOT_MATCH(160385, "授权码不正确"),
	
	/**
     * 更新密保失败
     */
    PWD_QUESTION_UPDATE_ERROR(160386, "更新密保失败"),
    
    /**
     * 查询密保失败
     */
    PWD_QUESTION_QUERY_ERROR(160387, "查询密保失败"),
    /**
     * 密保问题不正确或未设置
     */
    PWD_QUESTION_NOT_FOUND(160207, "密保问题不正确或未设置"),
    /**
     * 注销失败
     */
    CLOSE_ACCOUT_ERROR(160388, "注销失败"),
    /**
     * 互生code非法
     */
    HSRESNO_CODE_IS_ILLEGAL(160389, "互生code非法"),
    /**
     * 查询域名信息失败
     */
   DOMAIN_QUERY_ERROR(160390, "查询域名信息失败"),
    
    /**
     * 保存域名信息失败
     */
   DOMAIN_SAVE_ERROR(160391, "保存域名信息失败"),
    /**
     * 更新域名信息失败
     */
   DOMAIN_UPDATE_ERROR(160392, "更新域名信息失败"),
   
   POS_PIK_MAC_GEN_FAILED(160393, "POS生成pikmac失败，详见日志"),
    /**
     *授权码已过期
     */
   AUTHCODE_IS_EXPIRED(160394, "授权码已过期"),
   /**
    *非持卡人没有互生号
    */
  NOCARDHOLDER_NOT_HAVE_RESNO(160208, "非持卡人没有互生号"),
  /**
   *操作员信息更新失败
   */
  OPERATOR_UPDATE_ERROR(160395, "操作员信息更新失败"),
 /**
  *企业扩展信息更新失败
  */
  ENTEXTENDINFO_UPDATE_ERROR(160215, "企业扩展信息更新失败"),
  
  /**
   *更新收货地址异常
   */
  RECEIVEADDR_UPDATE_ERROR(160396, "更新收货地址异常"),
  /**
   *保存收货地址异常
   */
  RECEIVEADDR_SAVE_ERROR(160397, "保存收货地址异常"),
  /**
	 * 是否默认银行账户字段值非法
	 */
  IS_DEFAULT_ACCOUNT_ILLEGAL(160398, " 是否默认银行账户字段值非法"),
  /**
  *企业状态信息更新失败
  */
  ENTSTATUSINFO_UPDATE_ERROR(160216, "企业状态信息更新失败"),
  /**
   *密保问题转换中文失败
   */
  PWDQUESTION_CONVERT_ERROR(160217, "密保问题转换中文失败"),
  /**
   *批量查询持卡人的实名状态失败
   */
  BATCH_QUERY_AUTHINFO_ERROR(160218, "批量查询持卡人的实名状态失败"),
   /**
   *通过企业的客户类型查询企业的信息失败
   */
  QUERY_CUSTTYPE_ENTINFO_ERROR(160105, "通过企业的客户类型查询企业的信息失败"),
  /**
   *邮件标题转换中文失败
   */
  MAILTITLE_CONVERT_ERROR(160111, "邮件标题转换中文失败"),
  /**
   *更新企业状态信息出错
   */
  UPDATE_ENTSTATUSINFO_ERROR(160399, "更新企业状态信息出错"),
  /**
   *删除企业快捷银行账户出错
   */
  DELETE_ENTBANK_ERROR(160400, "删除企业快捷银行账户出错"),
  /**
   *删除消费者快捷银行账户出错
   */
  DELETE_COSUMERBANK_ERROR(160401, "删除消费者快捷银行账户出错"),
  /**
   *未通过消费者交易密码身份验证
   */
  NOT_PASS_CONSUMER_AUTHENTICATION(160402, "未通过消费者交易密码身份验证"),
  /**
   *消费者交易密码身份验证token已过期
   */
  CONSUMER_AUTHENTICATION_IS_EXPIRED(160403, "消费者交易密码身份验证token已过期"),
  
  /**
   *重置登录密码的短信验证token已过期
   */
  RESET_LOGINPWD_MOBILE_TOKEN_IS_EXPIRED(160404, "重置登录密码的短信验证token已过期"),
  
  
  /**
   *重置登录密码的短信验证token不正确
   */
  RESET_LOGINPWD_MOBILE_TOKEN_NOT_MATCH(160405, "重置登录密码的短信验证token不正确"),
  
  
  /**
   *重置登录密码的邮件验证token已过期
   */
  RESET_LOGINPWD_EMAIL_TOKEN_IS_EXPIRED(160406, "重置登录密码的邮件验证token已过期"),
  
  
  /**
   *重置登录密码的邮件验证token不正确
   */
  RESET_LOGINPWD_EMAIL_TOKEN_NOT_MATCH(160407, "重置登录密码的邮件验证token不正确"),
  
  
  /**
   *重置登录密码的密保验证token已过期
   */
  RESET_LOGINPWD_CRYPT_TOKEN_IS_EXPIRED(160408, "重置登录密码的密保验证token已过期"),
  
  
  /**
   *重置登录密码的密保验证token不正确
   */
  RESET_LOGINPWD_CRYPT_TOKEN_NOT_MATCH(160409, "重置登录密码的密保验证token不正确"),
  
  SOLR_SEARCH_FAILED(160410, "搜索引擎搜索失败"),
 
  /**
   *交易密码连续出错导致用户锁定
   */
  TRADEPWD_USER_LOCKED(160411, "交易密码连续出错导致用户锁定"),
  
  LOGIN_CODE_NOT_FOUND(160412, "登录验证码不存在"),
 
  LOGIN_CODE_WRONG(160413, "登录验证码不正确"),
  /**
   *删除收货地址异常
   */
  RECEIVEADDR_DELETE_ERROR(160414, "删除收货地址异常"),
  
  TRANS_PWD_NOT_SET(160415,"交易密码未设置"),
  /**
	 * 证件类型非法
	 */
	CERTYPE_IS_ILLEGAL(160416, "证件类型非法"),
	/**
	 * 管理员不能重置自己的登录密码
	 */
	CANOT_RESET_ADMIN_LOGPWD(160417, "管理员不能重置自己的登录密码"),
	/**
	 * 保存用户操作员记录日志表失败
	 */
	USER_OPERATELOG_SAVE_ERROR(160418, "保存用户操作员记录日志表失败"),
	
	CARD_NOT_BIND(160419, "互生卡未绑定"),

	/**
	 * 当前的复核员隶属企业不是地区平台
	 */
	RECHERCKER_IS_NOT_APS(160420, "当前的复核员隶属企业不是地区平台"),
	/**
	 * 批量查询企业的客户号失败
	 */
	BATCH_QUERY_ENTCUSTID_ERROR(160421, "批量查询企业的客户号失败"),
	/**
	 * 操作员与复核员不能是同一人
	 */
	DUBLE_CHECK_MUST_TWO_PERSON(160422, "操作员与复核员不能是同一人"),
	/**
	 * 当前复核员不具备双签复核员角色权限
	 */
	OPERATOR_NOT_DOUBLE_CHECKER(160423, "当前复核员不具备双签复核员角色权限"),
	/**
	 * 批量查询企业税率失败
	 */
	BATCH_QUERY_ENTTAXRATE_ERROR(160424, "批量查询企业税率失败"),
	/**
	 * 该企业无客户类型
	 */
	ENT_HAS_NO_CUSTTYPE(160425, "该企业未设置客户类型"),
	/**
	 * 企业客户类型不正确
	 */
	ENT_CUSTTYPE_NOT_MATCH(160426, "企业客户类型不正确"),
	/**
	 * 传入的客户号与当前银行卡隶属的客户号不一致
	 */
	CUSTID_NOT_MATCH(160436, "传入的客户号与当前银行卡隶属的客户号不一致"),
	/** 角色已存在*/
	ROLE_EXIST(160427,"角色已存在"),
	/** 权限已存在*/
	PERM_EXIST(160428,"权限已存在"),
	/** 不能删除有组员的用户组 */
	CANOT_DELETE_GROUP(160429,"不能删除有组员的用户组"),
	/** 保存银行账户信息异常 */
	SAVE_ACCOUT_ERROR(160430,"保存银行账户信息异常"),
	/** 设置默认账户为非默认异常 */
	SET_ACCOUT_NOT_DEFAULT_ERROR(160431,"设置默认账户为非默认异常"),
	/** 银行账户已存在 */
	BANK_ACCOUT_NO_IS_EXIST(160432,"银行账户已存在"),
	/** 查询企业银行账户数量异常 */
	QUERY_ENT_BANK_ACCOUT_COUNT_ERROR(160433,"查询企业银行账户数量异常"),
	/** 查询消费者银行账户数量异常 */
	QUERY_CUST_BANK_ACCOUT_COUNT_ERROR(160434,"查询消费者银行账户数量异常"),
	/** 对象类型非法 */
	TARGET_IS_ILLEGAL(160435,"对象类型非法"),
	/** 角色使用中*/
	ROLE_USING(160437,"角色使用中"),
	/** 查询未生效的企业税率信息异常*/
	QUERY_NOEFFECT_ENTTAXRATE_ERROR(160438,"查询未生效的企业税率信息异常"),
	/** 更新未生效的企业税率信息异常*/
	UPDATE_NOEFFECT_ENTTAXRATE_ERROR(160439,"更新未生效的企业税率信息异常"),
	/** 删除未生效的企业税率信息异常*/
	DELETE_NOEFFECT_ENTTAXRATE_ERROR(160440,"删除未生效的企业税率信息异常"),
	/** 保存未生效的企业税率信息异常*/
	SAVE_NOEFFECT_ENTTAXRATE_ERROR(160441,"保存未生效的企业税率信息异常"),
	/** 查询企业税率记录数异常*/
	QUERY_NOEFFECT_ENTTAXRATE_COUNT_ERROR(160442,"查询企业税率记录数异常"),
	/** 未通过实名认证*/
	NOT_THROUGH_REALNAME_AUTHENTICATION(160443,"未通过实名认证"),
	
	USER_SECOND_PWD_WRONG(160444,"第二个密码错误"),
	
	USER_SECOND_PWD_EXISTS(160445, "第二个密码已经设置"),
	
	/**
	 * 当前的复核员隶属企业不是管理平台
	 */
	RECHERCKER_IS_NOT_MCS(160446, "当前的复核员隶属企业不是管理平台"),
	/**
	 * 管理平台复核员与当前操作员不属于同一企业
	 */
	IS_NOT_ENT_MCS(160447, "管理平台复核员与当前操作员不属于同一企业"),
	/**
	 * 地区平台复核员与当前操作员不属于同一企业
	 */
	IS_NOT_ENT_APS(160448, "地区平台复核员与当前操作员不属于同一企业"),
	/**
	 * 地区平台复核员不具备双签复核员角色权限
	 */
	APS_CHECKER_NOT_HAVE_AUTHORITY(160449, "地区平台复核员不具备双签复核员角色权限"),
	/**
	 * 管理平台复核员不具备双签复核员角色权限
	 */
	MCS_CHECKER_NOT_HAVE_AUTHORITY(160450, "管理平台复核员不具备双签复核员角色权限"),
	/**
	 * 企业操作员数量不能超过10000
	 */
	OPERRATOR_COUNT_NOT_OVER_TEN_THOUSAND(160451, "企业操作员数量不能超过10000"),
	
	/** 角色已分配用户*/
	ROLE_USE_BY_USER(160452,"角色使用中,已分配用户"),
	
	/** 角色已分配权限*/
	ROLE_USE_BY_PERM(160453,"角色使用中,已分配权限"),
	/**
	 * 不支持管理员修改登录密码
	 */
	ADMIN_NOI_ALLOWED_CHANGEPWD(160454, "不支持管理员修改登录密码"),
	/**
	 * 用户未设置手机号码
	 */
	USER_NOT_SET_MOBILE(160455, "用户未设置手机号码"),
	
	/**
	 * 邮箱验证失败，验证邮箱链接已失效！
	 */
	EMAIL_VALID_FAIL(160456, "邮箱验证失败，验证邮箱链接已失效！"),
	
	ENT_IS_CLOSED(160457, "企业系统已关闭"),
	/**
	 * 修改企业名称或者联系方式（电话）,调用电商服务异常
	 */
	CHG_ENTPHONE_ENTNAME_ECSERVICE_ERROR(160458, "修改企业名称或者联系方式（电话）,调用电商服务异常：\r\n"),
	/**
	 * 企业已注销
	 */
	ENT_IS_CANCELED(160459, "企业已注销"),
	/**
	 * 您的邮箱已经绑定且验证成功，请不需要重复绑定。
	 */
	EMAIL_BIND_FAIL(160460, "您的邮箱已经绑定且验证成功，请不要重复绑定。"),
	/**
	 * 您的手机号已经绑定且验证成功，请不需要重复绑定。
	 */
	MOBILE_BIND_FAIL(160461, "您的手机号已经绑定且验证成功，请不要重复绑定。"),
	
	/**
	 * 操作员绑定互生号，发送互动消息失败
	 */
	HSCARD_BIND_FAIL(160462, "操作员绑定互生号，发送互动消息失败。"),
	
	HSCARD_IS_STOP(160463,"互生卡已停用"),
	
	ENT_IS_SLEEP(160464,"企业长眠"),
	/**
	 * 非绑定中的互生卡不能做绑定互生卡的业务
	 */
	RES_NO_IS_NOT_BINDING(160465, "非绑定中的互生卡不能做绑定互生卡的业务"),
	/**
	 * 验证密保业务被锁
	 */
	CPYT_IS_LOCK(160466, "验证密保业务被锁"),
	/**
	 *登录密码连续出错导致用户锁定
	 */
	LOGINPWD_USER_LOCKED(160467, "登录密码连续出错导致用户锁定"),
    ;
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
	
	public static void printErrorCode(){
		ErrorCodeEnum[] enums= ErrorCodeEnum.values();
		Integer code=null;
		String msg=null;
		for(ErrorCodeEnum e:enums){
			code=e.getValue();
			msg=e.getDesc();
			msg=msg.replaceAll(",", "，");
			msg=msg.replaceAll("\r\n", " ");			
			System.out.println(code+","+msg+",UC");
		}
	}
	
	public static void main (String[] args){
		//检查错误码是否重复定义
		HashMap<Integer,Object> m=new HashMap<Integer,Object>();
		
		ErrorCodeEnum[] enums= ErrorCodeEnum.values();
		Integer code=null;
		System.out.println("BBBBBBBBBBBB");
		for(ErrorCodeEnum e:enums){
			code=e.getValue();
			if(m.containsKey(code)){
				System.err.println(""+e+" "+code+" is used by "+m.get(code));
			}
			m.put(code, e);
		}
		System.out.println("EEEEEEEEEEE");
		System.out.println();
		printErrorCode();
	}
}
