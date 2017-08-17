package com.gy.hsxt.uc.as.api.util;

import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;

/**
 * 缓存key生成类
 * 
 * @Package: com.gy.hsxt.uc.as.api.util  
 * @ClassName: UcCacheKey 
 * @Description: TODO
 *
 * @author: lvyan 
 * @date: 2016-2-1 下午6:31:40 
 * @version V1.0
 */
public class UcCacheKey {
	/**
	 * 分隔符
	 */
	private final static String SEQ_UNDER_LINE = "_";
//	/**
//	 * 用户状态前缀
//	 */
//	private final static String USER_STATUS_KEY = "user_status";
//
//	/** 客服使用的key */
//	private final static String CUSTOMER_KEY = "customer";
//
//	/**
//	 * role-url cacheKey 前缀(cacheKey=前缀+roleId)
//	 */
//	private static final String ROLE_URL_KEY_PRE = "UC.role_url_";
//	/**
//	 * custId-role cacheKey 前缀(cacheKey=前缀+custId)
//	 */
//	private static final String CUST_ROLE_KEY_PRE = "UC.cust_role_";

	/**
	 * 前缀
	 */
	private static final String PREFIX_UC = "UC.";

	/**
	 * CSRF的前缀
	 */
	private static final String PREFIX_CSRF = "UC.CSRF_";
	/**
	 * 消费者银行卡实体的前缀
	 */
	private static final String PREFIX_CONSUMER_BANK = "UC.P_BANK_INFO";
	
	/**
	 * 企业银行卡实体的前缀
	 */
	private static final String PREFIX_ENT_BANK = "UC.E_BANK_INFO";
	
	/**
	 * 网络信息
	 */
	private static final String PREFIX_NETWROK = "UC.NETWORK_INFO";
	
	
	/**
	 * 企业用户资源号前缀
	 */
	private final static String PREFIX_ENT_USER = "UC.E_RES_USER_";
	/**
	 * 持卡人资源号前缀
	 */
	private final static String PREFIX_CARD_HOLDER_RES = "UC.C_RES_CUS_";
	
	/**
	 * 企业实体信息前缀
	 */
	private final static String PREFIX_EXT_ENT_INFO = "UC.EXT_ENT_INFO_";
	
	/**
	 * 持卡人实体信息前缀
	 */
	private final static String PREFIX_EXT_CARDER_INFO = "UC.EXT_CARDER_INFO_";
	
	/**
	 * 非持卡人实体信息前缀
	 */
	private final static String PREFIX_EXT_NOCARDER_INFO = "UC.EXT_NOCARDER_INFO_";
	
	/**
	 * 操作员实体信息前缀
	 */
	private final static String PREFIX_EXT_OPER_INFO = "UC.EXT_OPER_INFO_";
	
	/**
	 * 系统用户实体信息前缀
	 */
	private final static String PREFIX_EXT_SYS_INFO = "UC.EXT_SYS_INFO_";
	
	/**
	 * 双签员实体信息前缀
	 */
	private final static String PREFIX_EXT_CHECKER_INFO = "UC.EXT_CHECKER_INFO_";
	
	/**
	 * 企业状态信息前缀
	 */
	private final static String PREFIX_STATUS_INFO = "UC.E_STATUS_INFO_";
	
	/**
	 * 实名认证信息
	 */
	private final static String PREFIX_RA_INFO = "UC.C_RA_INFO_";
	
	/**
	 * 互生卡信息前缀
	 */
	private final static String PREFIX_C_INFO_ = "UC.C_INFO_";
	
	
	/**
	 * 短信缓存前缀
	 */
	private static final String PREFIX_SMS_CODE = "UC.SMS_CODE_";
	
	/**
	 * 短信重置登录密码缓存前缀
	 */
	private static final String PREFIX_SMS_RESET = "UC.SMS_RESET_";
	
	/**
	 * 短信发送授权码缓存前缀
	 */
	private static final String PREFIX_SMS_AUTH_CODE = "UC.SMS_AUTH_CODE_";
	
	
	/**
	 * 随机数验证码缓存前缀
	 */
	private static final String PERFIX_RANDOM = "UC.RANDOM_";
	
	/**
	 * 随机数验证码缓存前缀
	 */
	private static final String PERFIX_RESET_TPASS_VALID = "UC.RESET_TPASS_VALID_";
	/**
	 * 手机号前缀
	 */
	private final static String PREFIX_CUSTID_BY_MOBILE = "UC.C_PH_CUS_";
	/**
	 * 企业用户前缀
	 */
	private final static String PREFIX_PERFIX_ENT_RES = "UC.E_RES_CUS_";
	/**
	 * 企业税率前缀
	 */
	private final static String PREFIX_PERFIX_ENT_RES_TAXRATE = "UC.E_RES_TAXRATE";

	/**
	 * 系统用户前缀
	 */
	private final static String PREFIX_SYS_CUS = "UC.C_SYS_CUS_";

	/**
	 * 系统用户前缀
	 */
	private final static String PREFIX_SYS_CUS_SECOND = "UC.C_SYS_CUS_SECOND";
	/**
	 * 绑定邮箱前缀
	 */
	private final static String PREFIX_MAIL_LPASS = "UC.MAIL_LPASS_";
	
	/**
	 * 邮箱重置登录密码的前缀
	 */
	private final static String PREFIX_MAIL_RESET = "UC.MAIL_RESET_";

	/**
	 * PAD密钥的前缀
	 */
	private final static String PREFIX_PAD_TOKEN = "UC.PAD_TOKEN_";
	
	
	/**
	 * POS密钥的前缀
	 */
	private final static String PREFIX_POS_TOKEN = "UC.POS_TOKEN_";
	
	/**
	 * 刷卡器密钥的前缀
	 */
	private final static String PREFIX_CARDREADER_TOKEN = "UC.CARDREADER_TOKEN_";
	
	
	/**
	 * POS机比对mac前缀
	 */
	private final static String PREFIX_POS_CHK_MAC = "UC.POS_CHK_MAC_";
	
	/**
	 * 角色拥有url权限前缀
	 */
	private final static String PREFIX_ROLE_PERM = "UC.ROLE_PERM_";
	
	
	/**
	 * 用户拥有角色权限前缀
	 */
	private final static String PREFIX_USER_ROLE = "UC.USER_ROLE_";
	
	/**
	 * 生成POS企业key前缀
	 */
	private final static String PREFIX_POS_ENT = "UC.POS_ENT_";
	
	/**
	 * 生成POS积分比例版本号前缀
	 */
	private final static String PREFIX_POS_PV_RATE = "UC.POS_PV_RATE_";
	
	/**
	 * 生成POS公用基础信息key前缀
	 */
	private final static String PREFIX_POS_BASE_INFO = "UC.POS_BASE_INFO";
	
	
	/**
	 * BO限制配置前缀
	 */
	private final static String PREFIX_BO_SET = "UC.BO_SET_";
	
	
	
	/**
	 * 非持卡人登录密码失败次数
	 */
	private final static String PREFIX_NC_LOGIN_FAILTIMES = "UC.NC_LOGIN_FAILTIMES";
	/**
	 * 非持卡人交易密码失败次数
	 */
	private final static String PREFIX_NC_TRADE_FAILTIMES = "UC.NC_TRADE_FAILTIMES";
	/**
	 * 非持卡人密保失败次数
	 */
	private final static String PREFIX_NC_CYPT_FAILTIMES = "UC.NC_CYPT_FAILTIMES";

	/**
	 * 持卡人登录密码失败次数
	 */
	private final static String PREFIX_C_LOGIN_FAILTIMES = "UC.C_LOGIN_FAILTIMES";
	/**
	 * 持卡人交易密码失败次数
	 */
	private final static String PREFIX_C_TRADE_FAILTIMES = "UC.C_TRADE_FAILTIMES";
	/**
	 * 持卡人密保失败次数
	 */
	private final static String PREFIX_C_CYPT_FAILTIMES = "UC.C_CYPT_FAILTIMES";
	
	
	/**
	 * 操作员登录失败次数
	 */
	private final static String PREFIX_OPER_LOGIN_FAILTIMES = "UC.OPER_LOGIN_FAILTIMES";
			
	/**
	 * 企业交易密码失败次数
	 */
	private final static String PREFIX_ENT_TRADE_FAILTIMES = "UC.ENT_TRADE_FAILTIMES";
	/**
	 * 持卡人密保失败次数
	 */
	private final static String PREFIX_ENT_CYPT_FAILTIMES = "UC.ENT_CYPT_FAILTIMES";
	
	/**
	 * 双签员登录失败次数
	 */
	private final static String PREFIX_CHECK_LOGIN_FAILTIMES = "UC.CHECK_LOGIN_FAILTIMES";
	
	/**
	 * 系统用户登录密码失败次数
	 */
	private final static String PREFIX_SYS_LOGIN_FAILTIMES = "UC.SYS_LOGIN_FAILTIMES";
	
	/**
	 * 操作员登录失败次数
	 * @param entResNo
	 * @param operNo
	 * @return
	 */
	public static String genOperLoginFailTimesKey(String entResNo,String operNo) {
		return PREFIX_OPER_LOGIN_FAILTIMES + SEQ_UNDER_LINE + entResNo +SEQ_UNDER_LINE + operNo;
	}
	
	/**
	 * 企业交易密码失败次数
	 * @param entResNo
	 * @return
	 */
	public static String genEntTradeFailTimesKey(String entResNo) {
		return PREFIX_ENT_TRADE_FAILTIMES + SEQ_UNDER_LINE + entResNo;
	}
	
	/**
	 * 企业密保失败次数
	 * @param entResNo
	 * @return
	 */
	public static String genEntPwdQuestionFailTimesKey(String entResNo) {
		return PREFIX_ENT_CYPT_FAILTIMES + SEQ_UNDER_LINE + entResNo;
	}
	
	
	/**
	 * 生成非持卡人登录密码失败次数缓存key
	 * 
	 * @param mobile
	 * @return
	 */
	public static String genNoCardLoginFailTimesKey(String mobile) {
		return PREFIX_NC_LOGIN_FAILTIMES + SEQ_UNDER_LINE + mobile;
	}
	
	/**
	 * 生成系统用户登录密码失败次数缓存key
	 * 
	 * @param mobile
	 * @return
	 */
	public static String genSystemLoginFailTimesKey(String userName) {
		return PREFIX_SYS_LOGIN_FAILTIMES + SEQ_UNDER_LINE + userName;
	}
	
	/**
	 * 生成系双签员登录密码失败次数缓存key
	 * 
	 * @param mobile
	 * @return
	 */
	public static String genCheckerLoginFailTimesKey(String userName) {
		return PREFIX_CHECK_LOGIN_FAILTIMES + SEQ_UNDER_LINE + userName;
	}
	
	/**
	 * 生成非持卡人交易密码失败次数缓存key
	 * 
	 * @param mobile
	 * @return
	 */
	public static String genNoCardTradeFailTimesKey(String mobile) {
		return PREFIX_NC_TRADE_FAILTIMES + SEQ_UNDER_LINE + mobile;
	}
	
	/**
	 * 生成持卡人密保失败次数缓存key
	 * 
	 * @param mobile
	 * @return
	 */
	public static String genNoCardPwdQuestionFailTimesKey(String mobile) {
		return PREFIX_NC_CYPT_FAILTIMES + SEQ_UNDER_LINE + mobile;
	}
	
	/**
	 * 生成持卡人密保失败次数缓存key
	 * 
	 * @param perResNo
	 * @return
	 */
	public static String genCarderLoginFailTimesKey(String perResNo) {
		return PREFIX_C_LOGIN_FAILTIMES + SEQ_UNDER_LINE + perResNo;
	}
	
	/**
	 * 生成持卡人密保失败次数缓存key
	 * 
	 * @param perResNo
	 * @return
	 */
	public static String genCarderTradeFailTimesKey(String perResNo) {
		return PREFIX_C_TRADE_FAILTIMES + SEQ_UNDER_LINE + perResNo;
	}
	
	/**
	 * 生成持卡人密保失败次数缓存key
	 * 
	 * @param mobile
	 * @return
	 */
	public static String genCarderPwdQuestionFailTimesKey(String perResNo) {
		return PREFIX_C_CYPT_FAILTIMES + SEQ_UNDER_LINE + perResNo;
	}
	
	
	
	
	/**
	 * 生成POS企业key
	 * 
	 * @param entResNo
	 * @return
	 */
	public static String genPosEntKey(String entResNo) {
		return PREFIX_POS_ENT + entResNo;
	}

	/**
	 * 生成POS积分比例版本号
	 * 
	 * @param deviceNo
	 * @return
	 */
	public static String genPosPointRateVersion(String entResNo, String deviceNo) {
		return PREFIX_POS_PV_RATE + entResNo + "_" + deviceNo;
	}
	
	/**
	 * 生成POS公用基础信息key
	 * 
	 * @return
	 */
	public static String genPosBaseInfoKey() {
		return PREFIX_POS_BASE_INFO;
	}
	
	public static String genBoSettingKey(String custId){
		return PREFIX_BO_SET + custId;
	}
	
	
	
	/*************************** 持卡人 begin*************************/
	
	/**
	 * 根据持卡人互生号查找客户号的key
	 * 
	 * @param resNo
	 *            互生号
	 * @return
	 */
	public static String genPerCustId(String resNo) {
		return PREFIX_CARD_HOLDER_RES + resNo;
	}
	
	/**
	 * 获取登录缓存key
	 * 
	 * 
	 * @param channelType
	 * @param custId
	 * @return
	 */
	public static String genLoginKey(ChannelTypeEnum channelType, String custId) {
		return PREFIX_UC + channelType.getPerfix() + custId;
	}
	
	/**
	 * 互生卡的key
	 * 
	 * @param mobile
	 * @return
	 */
	public static String genCardKey(String custId) {
		return PREFIX_C_INFO_ + custId;
	}
	
	/**
	 * 企业的扩展信息的缓存key
	 * @param custId
	 * @return
	 */
	public static String genExtendEntInfoKey(String custId){
		return PREFIX_EXT_ENT_INFO + custId;
	}
	
	/**
	 * 持卡人的扩展信息的缓存key
	 * @param custId
	 * @return
	 */
	public static String genExtendCarderInfoKey(String custId){
		return PREFIX_EXT_CARDER_INFO + custId;
	}
	
	/**
	 * 非持卡人的扩展信息的缓存key
	 * @param custId
	 * @return
	 */
	public static String genExtendNoCarderInfoKey(String custId){
		return PREFIX_EXT_NOCARDER_INFO + custId;
	}
	
	/**
	 * 操作员的扩展信息的缓存key
	 * @param custId
	 * @return
	 */
	public static String genExtendOperKey(String custId){
		return PREFIX_EXT_OPER_INFO + custId;
	}
	
	
	/**
	 * 系统用户
	 * @param custId
	 * @return
	 */
	public static String genExtendSysInfoKey(String custId){
		return PREFIX_EXT_SYS_INFO + custId;
	}
	
	/**
	 * 系统用户
	 * @param custId
	 * @return
	 */
	public static String genExtendCheckerInfoKey(String custId){
		return PREFIX_EXT_CHECKER_INFO + custId;
	}
	
	/**
	 * 生成实名认证信息key
	 * @return
	 */
	public static String genRealAuthKey(String custId) {
		return PREFIX_RA_INFO + custId;
	}
	
	
	
	
	
	/*************************** 持卡人 end*************************/
	
	
	
	
	/**
	 * 企业状态信息的缓存key
	 * @param custId
	 * @return
	 */
	public static String genStatusInfoKey(String custId){
		return PREFIX_STATUS_INFO + custId;
	}
	
	/**
	 * 系统用户的custId的key
	 * 
	 * @param mobile
	 * @return
	 */
	public static String genSysCustId(String userName) {
		return PREFIX_SYS_CUS + userName;
	}
	/**
	 * 系统用户2的custId的key
	 * 
	 * @param mobile
	 * @return
	 */
	public static String genSysCustIdSecond(String userName) {
		return PREFIX_SYS_CUS_SECOND + userName;
	}
	
	
	
	/**
	 * 生成非持卡人客户号 key 根据非持卡人手机号查询非持卡人的客户号
	 * 
	 * @param mobile
	 * @return
	 */
	public static String genPerCustIdByMobile(String mobile) {
		return PREFIX_CUSTID_BY_MOBILE + mobile;
	}
	
	
	
	/**
	 * 生成操作员客户号 key 通过企业互生号和操作员的账户
	 * 
	 * @param operResNo
	 * @return
	 */
	public static String genOperCustId(String entResNo, String userName) {
		return new StringBuilder().append(PREFIX_ENT_USER)
				.append(entResNo).append(SEQ_UNDER_LINE).append(userName)
				.toString();
	}
	
	/**
	 * 生成企业客户号 key 通过企业互生号
	 * @return
	 */
	public static String genOperCustId(String operResNo) {
		return PREFIX_PERFIX_ENT_RES + operResNo;
	}
	
	/**
	 * 生成企业客户号 key 通过企业互生号
	 * @return
	 */
	public static String genEntCustId() {
		return PREFIX_PERFIX_ENT_RES;
	}

	
	
	/**
	 * 生成企业税率前缀key
	 * @return
	 */
	public static String genEntTaxRate() {
		return PREFIX_PERFIX_ENT_RES_TAXRATE;
	}
	

	
	/**
	 * 生成随机token
	 * 
	 * @param custId
	 *            客户号
	 * @return
	 */
	public static String genRandomTokenKey(String custId, String randomToken) {
		custId = custId == null?"":custId;
		return  PREFIX_CSRF + custId + randomToken;
	}
	
	
	
	/**
	 * 短信验证码的key
	 * 
	 * @param mobile
	 * @return
	 */
	public static String genSmsCode(String mobile) {
		return PREFIX_SMS_CODE + mobile;
	}
	/**
	 * 短信重置登录密码验证码的key
	 * 
	 * @param mobile
	 * @return
	 */
	public static String genSmsReset(String mobile) {
		return PREFIX_SMS_RESET + mobile;
	}
	
	/**
	 * 短信授权码的key
	 * 
	 * @param mobile	手机号码
	 * @return
	 */
	public static String genSmsAuthCode(String mobile) {
		return PREFIX_SMS_AUTH_CODE + mobile;
	}
	
	/**
	 * 绑定邮箱的key
	 * 
	 * @param email	邮箱
	 * @return
	 */
	public static String genBindEmailCode(String email) {
		return PREFIX_MAIL_LPASS + email;
	}
	
	
	/**
	 * 邮箱重置登录密码的key
	 * 
	 * @param email	邮箱
	 * @return
	 */
	public static String genEmailResetCode(String email) {
		return PREFIX_MAIL_RESET + email;
	}
	
	/**
	 * 密保验证码验证的key
	 * 
	 * @param mobile
	 * @return
	 */
	public static String genCrypt(String custId) {
		return PERFIX_RANDOM + custId;
	}
	
	
	public static String genConsumerCertificate(String cerType, String cerNo) {
		return new StringBuilder()
				.append(PERFIX_RESET_TPASS_VALID)
				.append(cerType).append(SEQ_UNDER_LINE).append(cerNo)
				.toString();
	}
	
	/**
	 * 生成PAD密钥的key
	 * 
	 * @param entResNo
	 * @param deviceNo
	 * @return
	 */
	public static String genPadTokenKey(String entResNo, String deviceNo) {
		return PREFIX_PAD_TOKEN + entResNo + SEQ_UNDER_LINE + deviceNo;
	}
	
	/**
	 * 生成设备密钥的key
	 * 
	 * @param entResNo
	 * @param deviceNo
	 * @return
	 */
	public static String genPosTokenKey(String entResNo, String deviceNo) {
		return new StringBuilder().append(PREFIX_POS_TOKEN)
				.append(entResNo).append(SEQ_UNDER_LINE).append(deviceNo)
				.toString();
	}
	
	/**
	 * 生成刷卡器密钥的key
	 * 
	 * @param entResNo
	 * @param deviceNo
	 * @return
	 */
	public static String genCardReaderTokenKey(String entResNo, String deviceNo) {
		return new StringBuilder().append(PREFIX_CARDREADER_TOKEN)
				.append(entResNo).append(SEQ_UNDER_LINE).append(deviceNo)
				.toString();
	}
	

	
	/**
	 * 生成POS机比对mac的key
	 * 
	 * @param entResNo
	 * @param deviceNo
	 * @return
	 */
	public static String genPosMacMatch(String entResNo, String deviceNo) {
		return new StringBuilder().append(PREFIX_POS_CHK_MAC)
				.append(entResNo).append(SEQ_UNDER_LINE).append(deviceNo)
				.toString();
	}
	
	
	/**
	 * 生成角色拥有url权限缓存key
	 * 
	 * @param roleId
	 *            角色id
	 * @return
	 */
	public static String genRoleUrlCacheKey(String roleId) {
		return PREFIX_ROLE_PERM + roleId;
	}


	
	/**
	 * 生成用户拥有角色权限缓存key
	 * 
	 * @param custId
	 *            用户id
	 * @return
	 */
	public static String genCustRoleCacheKey(Object custId) {
		return PREFIX_USER_ROLE + custId;
	}
	
	
	/**
	 * 生成验证码的key
	 * @param randomToken
	 * @return
	 */
	public static String genCheckCodeKey(String randomToken){
		return "UC.checkCode_" + randomToken;
	}
	
	/**
	 * 生成消费者银行实体的key
	 * @param randomToken
	 * @return
	 */
	public static String genConsumerBankKey(String perCustId){
		return PREFIX_CONSUMER_BANK + perCustId;
	}
	
	/**
	 * 生成企业银行实体的key
	 * @param randomToken
	 * @return
	 */
	public static String genEntBankKey(String entCustId){
		return PREFIX_ENT_BANK + entCustId;
	}
	
	
	/**
	 * 生成网络信息的key
	 * @param randomToken
	 * @return
	 */
	public static String genNetworkKey(String custId){
		return PREFIX_NETWROK + custId;
	}
}
