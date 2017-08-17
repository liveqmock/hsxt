package com.gy.hsxt.common.constant;

/**
 * Simple to Introduction
 * 
 * @category Simple to Introduction
 * @projectName hsxt-common
 * @package com.gy.hsxt.common.constant;
 * @className BusinessParam
 * @description 业务参数枚举
 * @author maocan
 * @createDate 2015-12-8 下午2:58:55
 * @updateUser maocan
 * @updateDate 2015-12-8 下午2:58:55
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
public enum BusinessParam {
	
	/** ----系统参数项 begin----**/
	
    /** 地区平台参数 （组）*/
	PUB_PLAT_PARA("PUB_PLAT_PARA"),

    /** 初始系统状态 */
	CURRENT_SYSTEM_STATE("CURRENT_SYSTEM_STATE"),

    /** 未付款订单自动关闭期限 */
	NOT_PAY_ORDER_CANCEL_LIMIT("NOT_PAY_ORDER_CANCEL_LIMIT"),

	
	
    /** 货币转银行 （组）*/
	HB_CHANGE_BANK("HB_CHANGE_BANK"),

    /** 个人单笔货币转银行最小限额 */
	PERSON_SINGLE_TRANSFER_MIN("PERSON_SINGLE_TRANSFER_MIN"),

	/** 个人单笔货币转银行最大限额 */
	PERSON_SINGLE_TRANSFER_MAX("PERSON_SINGLE_TRANSFER_MAX"),
	
	/** 个人单日货币转银行最大限额 */
	PERSON_SINGLE_DAILY_TRANSFER_MAX("PERSON_SINGLE_DAILY_TRANSFER_MAX"),
	
	/** 个人单笔货币转银行审核最小限额 */
	PERSON_SINGLE_TRANSFER_CHECK_LIMIT("PERSON_SINGLE_TRANSFER_CHECK_LIMIT"),
    
	/** 个人货币转银行单日有效交易次数 */
	PERSON_SINGLE_TRANSFER_CHECK_NUMBER("PERSON_SINGLE_TRANSFER_CHECK_NUMBER"),
	
	/** 企业单笔货币转银行最小限额 */
	COMAPNY_SINGLE_TRANSFER_MIN("COMAPNY_SINGLE_TRANSFER_MIN"),
    
	/** 企业单笔货币转银行最大限额 */
	COMAPNY_SINGLE_TRANSFER_MAX("COMAPNY_SINGLE_TRANSFER_MAX"),
    
	/** 企业单日货币转银行最大限额 */
	COMAPANY_SINGLE_DAILY_TRANSFER_MAX("COMAPANY_SINGLE_DAILY_TRANSFER_MAX"),
    
	/** 企业单笔货币转银行审核最小限额 */
	COMPANY_SINGLE_TRANSFER_CHECK_LIMIT("COMPANY_SINGLE_TRANSFER_CHECK_LIMIT"),
    
	/** 企业货币转银行单日有效交易次数 */
	COMPANY_SINGLE_TRANSFER_CHECK_NUMBER("COMPANY_SINGLE_TRANSFER_CHECK_NUMBER"),
	
	
    
	
	/** 积分转互生币（组）*/
	JF_CHANGE_HSB("JF_CHANGE_HSB"),
    
	/** 个人单笔积分汇兑最小限额*/
	P_SINGLE_EXCHANGE_POINT_MIN("P_SINGLE_EXCHANGE_POINT_MIN"),
    
	/** 企业单笔积分汇兑最小限额*/
	C_SINGLE_EXCHANGE_POINT_MIN("C_SINGLE_EXCHANGE_POINT_MIN"),
    
	/** 个人积分保底*/
	P_SAVING_POINT("P_SAVING_POINT"),
    
	/** 企业积分保底*/
	C_SAVING_POINT("C_SAVING_POINT"),
    
	
	
	
	/** 兑换互生币（组）*/
	EXCHANGE_HSB("EXCHANGE_HSB"),
	
	/** 代兑互生币手续费 */
	BUY_HSB_FEE("BUY_HSB_FEE"),
    
	/** 已实名注册个人单笔兑换互生币最大限额*/
	P_REGISTERED_SINGLE_BUY_HSB_MAX("P_REGISTERED_SINGLE_BUY_HSB_MAX"),
    
	/** 已实名注册个人单笔兑换生币最小限额*/
	P_REGISTERED_SINGLE_BUY_HSB_MIN("P_REGISTERED_SINGLE_BUY_HSB_MIN"),
	
	/** 已实名注册个人单日限额*/
	P_REGISTERED_SINGLE_DAY_BUY_HSB_MAX("P_REGISTERED_SINGLE_DAY_BUY_HSB_MAX"),
	
	/** 已实名注册个人单日互生币有效兑换次数*/
	P_REGISTERED_SINGLE_BUY_HSB_NUMBER("P_REGISTERED_SINGLE_BUY_HSB_NUMBER"),
	
	/** 未实名注册个人单笔兑换互生币最大限额*/
	P_NOT_REGISTERED_SINGLE_BUY_HSB_MAX("P_NOT_REGISTERED_SINGLE_BUY_HSB_MAX"),
    
	/** 未实名注册个人单笔兑换互生币最小限额*/
	P_NOT_REGISTERED_SINGLE_BUY_HSB_MIN("P_NOT_REGISTERED_SINGLE_BUY_HSB_MIN"),
    
	/** 未实名注册个人单日限额*/
	P_NOT_REGISTERED_SINGLE_DAY_BUY_HSB_MAX("P_NOT_REGISTERED_SINGLE_DAY_BUY_HSB_MAX"),
    
	/** 未实名注册个人单日有效兑换次数*/
	P_NOT_REGISTERED_SINGLE_BUY_HSB_NUMBER("P_NOT_REGISTERED_SINGLE_BUY_HSB_NUMBER"),
	
	/** 非持卡人已填写真实姓名个人单笔兑换互生币最大限额*/
    P_REAL_NAME_SINGLE_BUY_HSB_MAX("P_REAL_NAME_SINGLE_BUY_HSB_MAX"),
    
    /** 非持卡人已填写真实姓名个人单笔兑换生币最小限额*/
    P_REAL_NAME_SINGLE_BUY_HSB_MIN("P_REAL_NAME_SINGLE_BUY_HSB_MIN"),
    
    /** 非持卡人已填写真实姓名个人单日限额*/
    P_REAL_NAME_SINGLE_DAY_BUY_HSB_MAX("P_REAL_NAME_SINGLE_DAY_BUY_HSB_MAX"),
    
    /** 非持卡人未填写真实姓名个人单笔兑换互生币最大限额*/
    P_UNREAL_NAME_SINGLE_BUY_HSB_MAX("P_UNREAL_NAME_SINGLE_BUY_HSB_MAX"),
    
    /** 非持卡人未填写真实姓名个人单笔兑换生币最小限额*/
    P_UNREAL_NAME_SINGLE_BUY_HSB_MIN("P_UNREAL_NAME_SINGLE_BUY_HSB_MIN"),
    
    /** 非持卡人未填写真实姓名个人单日限额*/
    P_UNREAL_NAME_SINGLE_DAY_BUY_HSB_MAX("P_UNREAL_NAME_SINGLE_DAY_BUY_HSB_MAX"),
    
	/** 托管企业单笔互生币汇兑最小限额*/
	T_SINGLE_BUY_HSB_MIN("T_SINGLE_BUY_HSB_MIN"),
    
	/** 托管企业单笔兑换互生币最大限额*/
	T_SINGLE_BUY_HSB_MAX("T_SINGLE_BUY_HSB_MAX"),
    
	/** 托管企业兑换互生币单日限额*/
	T_SINGLE_DAY_BUY_HSB_MAX("T_SINGLE_DAY_BUY_HSB_MAX"),
    
	/** 托管企业兑换互生币单日有效兑换次数*/
	T_SINGLE_BUY_HSB_NUMBER("T_SINGLE_BUY_HSB_NUMBER"),
	
	/** 成员企业单笔互生币汇兑最小限额*/
	B_SINGLE_BUY_HSB_MIN("B_SINGLE_BUY_HSB_MIN"),
    
	/** 成员企业单笔兑换互生币最大限额*/
	B_SINGLE_BUY_HSB_MAX("B_SINGLE_BUY_HSB_MAX"),
    
	/** 成员企业兑换互生币单日限额*/
	B_SINGLE_DAY_BUY_HSB_MAX("B_SINGLE_DAY_BUY_HSB_MAX"),
    
	/** 成员企业兑换互生币单日有效兑换次数*/
	B_SINGLE_BUY_HSB_NUMBER("B_SINGLE_BUY_HSB_NUMBER"),
	
	/** 服务公司单笔互生币汇兑最小限额*/
	S_SINGLE_BUY_HSB_MIN("S_SINGLE_BUY_HSB_MIN"),
    
	/** 服务公司单笔兑换互生币最大限额*/
	S_SINGLE_BUY_HSB_MAX("S_SINGLE_BUY_HSB_MAX"),
    
	/** 服务公司兑换互生币单日限额*/
	S_SINGLE_DAY_BUY_HSB_MAX("S_SINGLE_DAY_BUY_HSB_MAX"),

	/** 服务公司兑换互生币单日有效兑换次数*/
	S_SINGLE_BUY_HSB_NUMBER("S_SINGLE_BUY_HSB_NUMBER"),
    
	
	
    
	/** 积分投资（组）*/
	JF_INVEST("JF_INVEST"),
    
	/** 个人单笔积分投资最小限额*/
	P_SINGLE_INVEST_POINT_MIN("P_SINGLE_INVEST_POINT_MIN"),
    
	/** 企业单笔积分投资最小限额*/
	C_SINGLE_INVEST_POINT_MIN("C_SINGLE_INVEST_POINT_MIN"),
    
	/** 个人投资分红流通币比率*/
	P_EXCHENGEABLE_HSB_SCALE("P_EXCHENGEABLE_HSB_SCALE"),
    
	/** 个人投资分红定向消费币比率*/
	P_CONSUMABLE_HSB_SCALE("P_CONSUMABLE_HSB_SCALE"),
    
	/** 企业投资分红流通币比率*/
	C_EXCHENGEABLE_HSB_SCALE("C_EXCHENGEABLE_HSB_SCALE"),
    
	/** 企业投资分红慈善救助基金比率*/
	C_DIRECTIONAL_SUPPORT_HSB_SCALE("C_DIRECTIONAL_SUPPORT_HSB_SCALE"),
    
    
    
	/** 互生币转货币（组）*/
	HSB_CHANGE_HB("HSB_CHANGE_HB"),
    
	/** 互生币转货币单次最低限额*/
	HSB_CHANGE_HB_SINGAL_MIN("HSB_CHANGE_HB_SINGAL_MIN"),
    
	/** 互生币转货币手续费比率*/
	HSB_CHANGE_HB_RATIO("HSB_CHANGE_HB_RATIO"),
    
    
    
	/** 互生币支付（组）*/
	HSB_PAYMENT("HSB_PAYMENT"),
    
	/** 消费者互生币支付单笔限额*/
	CONSUMER_PAYMENT_MAX("CONSUMER_PAYMENT_MAX"),
    
	/** 消费者互生币支付当日限额*/
	CONSUMER_PAYMENT_DAY_MAX("CONSUMER_PAYMENT_DAY_MAX"),
	
	/** 消费者互生币支付单日交易次数*/
	CONSUMER_PAYMENT_DAY_NUMBER("CONSUMER_PAYMENT_DAY_NUMBER"),
	
	/** 企业互生币支付单笔限额*/
	ENT_PAYMENT_MAX("ENT_PAYMENT_MAX"),
    
	/** 企业互生币支付当日限额*/
	ENT_PAYMENT_DAY_MAX("ENT_PAYMENT_DAY_MAX"),
	
	/** 企业互生币支付单日交易次数*/
	ENT_PAYMENT_DAY_NUMBER("ENT_PAYMENT_DAY_NUMBER"),
    
	/** 服务公司互生币支付单笔限额*/
	COMPANY_PAYMENT_MAX("COMPANY_PAYMENT_MAX"),
    
	/** 服务公司互生币支付当日限额*/
	COMPANY_PAYMENT_DAY_MAX("COMPANY_PAYMENT_DAY_MAX"),
    
	/** 服务公司互生币支付单日交易次数*/
	COMPANY_PAYMENT_DAY_NUMBER("COMPANY_PAYMENT_DAY_NUMBER"),
	
    
    
	/** 积分福利（组）*/
	JF_WELFARE("JF_WELFARE"),
    
	/** 互生意外伤害补贴最大限额（每年）*/
	P_ACCIDENT_INSURANCE_SUBSIDIES_YEAR_MAX("P_ACCIDENT_INSURANCE_SUBSIDIES_YEAR_MAX"),
    
	/** 互生意外伤害补贴最大限额（每次）*/
	P_ACCIDENT_INSURANCE_SUBSIDIES_TIMES_MAX("P_ACCIDENT_INSURANCE_SUBSIDIES_TIMES_MAX"),
    
	/** 意外身故补贴最大限额*/
	P_DEATH_SUBSIDIES_YEAR_MAX("P_DEATH_SUBSIDIES_YEAR_MAX"),
    
	/** 意外身故补贴有效期*/
	P_DEATH_SUBSIDIES_EXPIRY("P_DEATH_SUBSIDIES_EXPIRY"),
    
	/** 免费医疗补贴年度赔付上限*/
	P_FREE_MEDICAL_MAX("P_FREE_MEDICAL_MAX"),
	
	/** 享受意外保障福利需累计消费积分的阀值*/
	P_CONSUME_THRESHOLD_POINT("P_CONSUME_THRESHOLD_POINT"),
	
	/** 享受住院补贴福利需累计投资积分的阀值*/
	P_INVEST_THRESHOLD_POINT("P_INVEST_THRESHOLD_POINT"),
	
	/** 连续失效的天数 致使意外保障失效*/
	P_DUR_INVALID_DAYS("P_DUR_INVALID_DAYS"), 
	
    
	
	
	/** 账号安全（组）*/
	ACCOUNT_SAFETY("ACCOUNT_SAFETY"),
    
	/** 当日交易密码连续错误最大次数*/
	ERROE_PD_TIEMS("ERROE_PD_TIEMS"),
    
	/** 当日交易密码连续错误已达次数*/
	ERROE_PD_HAD_TIEMS("ERROE_PD_HAD_TIEMS"),
	
	/** 当日登录密码连续错误最大次数*/
	ERROE_LD_TIEMS("ERROE_LD_TIEMS"),
	
	/** 密保验证次数*/
	ERROE_VALIDATE_TIEMS("ERROE_VALIDATE_TIEMS"),
	
	/** 密保已验证次数*/
	ERROE_HAD_VALIDATE_TIEMS("ERROE_HAD_VALIDATE_TIEMS"),
    
	/** 卡挂失天数*/
	CARD_LOST_STATE("CARD_LOST_STATE"),
    
	/** 短信验证码发送频率*/
	MESSAGE_SEND_RATE("MESSAGE_SEND_RATE"),
    
	/** 短信验证码有效时间*/
	MESSAGE_SEND_EXPIRY("MESSAGE_SEND_EXPIRY"),
    
	/** 邮箱验证码发送频率*/
	EMAIL_SEND_RATE("EMAIL_SEND_RATE"),
    
	/** 邮箱验证码有效时间*/
	EMAIL_SEND_EXPIRY("EMAIL_SEND_EXPIRY"),
   
    
    
    
	/** 预警参数（组）*/
	PUB_ALERT_PARA("PUB_ALERT_PARA"),
   
	/** 企业积分预付款充值最小额*/
	C_DISPOSE_PRE_POINT_MIN("C_DISPOSE_PRE_POINT_MIN"),
    
	/** 企业积分预付款不足预警额*/
	C_LESS_PRE_POINT("C_LESS_PRE_POINT"),
	
    /** 企业积分预付款偏少预警额*/
	C_MORE_LESS_PRE_POINT("C_MORE_LESS_PRE_POINT"),
	
    /** 企业积分预付款暂停预警额*/
	C_SUSPENSIVE_PRE_POINT("C_SUSPENSIVE_PRE_POINT"),
	
    /** Pos机库存预警*/
	POS_STOCK_LESS("POS_STOCK_LESS"),
	
    /** 刷卡器库存预警*/
	MIS_POS_STOCK_POINT("MIS_POS_STOCK_POINT"),
    
    /** 互生卡库存预警*/
	HS_CARD_STOCK_POINT("HS_CARD_STOCK_POINT"),
    
    /** 企业停止积分预警*/
	ENT_STOP_GIVE_POINT("ENT_STOP_GIVE_POINT"),
    
    /** 企业连续不送积分预警期限*/
	COMPANY_NOT_SEND_POINT_WARNING_LIMIT("COMPANY_NOT_SEND_POINT_WARNING_LIMIT"),
    
    /** 企业连续不送积分休眠期限*/
	COMPANY_NOT_SEND_POINT_RESTING_LIMIT("COMPANY_NOT_SEND_POINT_RESTING_LIMIT"),
    
    /** 企业连续不送积分报停期限*/
	COMPANY_NOT_SEND_POINT_TERMINATING_LIMIT("COMPANY_NOT_SEND_POINT_TERMINATING_LIMIT"),
    
	
	
	
    /** 配置参数（组）*/
	CONFIG_PARA("CONFIG_PARA"),
    
    /** 银行卡绑定数量*/
	BANK_CARD_BIND_NUMBER("BANK_CARD_BIND_NUMBER"),
    
    /** 保底积分设置*/
	HS_POIT_MIN("HS_POIT_MIN"),
    
	/** 免费企业积分比例设置最小值*/
	HS_FREE_ENT_POIT_RATE_MIN("HS_FREE_ENT_POIT_RATE_MIN"),
	
    /** 积分比例设置最小值*/
	HS_POIT_RATE_MIN("HS_POIT_RATE_MIN"),
    
    /** 积分比例设置最大值*/
	HS_POIT_RATE_MAX("HS_POIT_RATE_MAX"),
    
    /** 积分比例设置数量*/
	HS_POIT_RATE_NUMBER("HS_POIT_RATE_NUMBER"),
    
    /** 消费积分分配方案-消费者*/
	HS_CONSUMER_ALLOT_RATE("HS_CONSUMER_ALLOT_RATE"),
    
    /** 消费积分分配方案-托管企业*/
	HS_TG_ENT_ALLOT_RATE("HS_TG_ENT_ALLOT_RATE"),
    
    /** 消费积分分配方案-服务公司 */
	HS_SERVER_COMPANY_ALLOT_RATE("HS_SERVER_COMPANY_ALLOT_RATE"),
    
    /** 消费积分分配方案-管理公司*/
	HS_MANAGER_COMPANY_ALLOT_RATE("HS_MANAGER_COMPANY_ALLOT_RATE"),
    
    /** 消费积分分配方案-地区平台*/
	HS_PLATFORM__ALLOT_RATE("HS_PLATFORM__ALLOT_RATE"),
	
	/** 个性卡样定制费用 **/
	HS_CARD_STYLE_BUY_FEE("HS_CARD_STYLE_BUY_FEE"),
	
	/** 商业服务费比例 */
    HS_BUSINESS_SERVICE_RATE("HS_BUSINESS_SERVICE_RATE"),
    
	
	
    /** 内部地区平台参数（组）*/
	ACCT_PLAT_PARA("ACCT_PLAT_PARA"),
    
    /** 昨日交易日期*/
	LAST_TRADING_DATE("LAST_TRADING_DATE"),
    
    /** 当日自然日日期*/
	CURRENT_NATURE_DATE("CURRENT_NATURE_DATE"),
    
    /** 昨日自然日日期*/
	LAST_NATURE_DATE("LAST_NATURE_DATE"),
    
	
	
	
    /** 账户阀值（组）*/
	PUB_ACCOUNT_THRESHOLD("PUB_ACCOUNT_THRESHOLD"),
    
    /** 个人互生币保底*/
	P_SAVING_HSB("P_SAVING_HSB"),
    
    /** 企业互生币保底*/
	C_SAVING_HSB("C_SAVING_HSB"),
    
	/** 积分预付款扣除最小金额*/
	ACCU_POINT_MIN("ACCU_POINT_MIN"),
    
	/** 跨行银行转账确认时限*/
	DIFF_BANK_TRANS_CONFIRM_LIMIT("DIFF_BANK_TRANS_CONFIRM_LIMIT"),
    
	/** 申报积分分配*/
	ACCT_APPLE_POINT_ALLOCATION("ACCT_APPLE_POINT_ALLOCATION"),
    
    
	
	
	
	/** 系统状态（组）*/
	SYSTEM_STATE("SYSTEM_STATE"),
    
	/** 系统状态正常*/
	SYSTEM_STATE_NORMAL("SYSTEM_STATE_NORMAL"),
    
	/** 系统状态锁定*/
	SYSTEM_STATE_LOCK("SYSTEM_STATE_LOCK"),

	
	/** 同步参数（组）*/
	ACCT_SYNC_PARA("ACCT_SYNC_PARA"),
	
	/** 同步数据最大次数*/
	SEND_MAX_TIMES("SEND_MAX_TIMES"),
    
	/** 同步数据间隔时间（秒）*/
	RESEND_INTERVAL_TIME("RESEND_INTERVAL_TIME"),
	
	/** ----系统参数项 end----**/
	
	
	/** ----客户参数固定项 begin----**/
	
	/** 互生币支付当日已经支付金额*/
	HAD_PAYMENT_DAY("HAD_PAYMENT_DAY"),
	
	/** 互生币支付单日已经交易次数*/
	HAD_PAYMENT_DAY_NUMBER("HAD_PAYMENT_DAY_NUMBER"),
	
	/** 单日互生币兑换已经发生额*/
	SINGLE_DAY_HAD_BUY_HSB("SINGLE_DAY_HAD_BUY_HSB"),
	
	/** 单日互生币已经兑换次数*/
	SINGLE_HAD_BUY_HSB_NUMBER("SINGLE_HAD_BUY_HSB_NUMBER"),
	
	/** 单日货币转银行已经转换金额 */
	SINGLE_DAILY_HAD_TRANSFER("SINGLE_DAILY_HAD_TRANSFER"),
	
	/** 单日货币转银行已经发生交易次数 */
	SINGLE_TRANSFER_HAD_CHECK_NUMBER("SINGLE_TRANSFER_HAD_CHECK_NUMBER"),
	
	/** 当日登录密码连续错误已达次数*/
	ERROE_LD_HAD_TIEMS("ERROE_LD_HAD_TIEMS"),
	
	/** 兑换互生币 **/
	BUY_HSB("BUY_HSB"),
	
	/** 互生币转货币 **/
	HSB_TO_CASH("HSB_TO_CASH"),
	
	/** 货币转银行 **/
	CASH_TO_BANK("CASH_TO_BANK"),
	
	/** 积分转互生币 **/
	PV_TO_HSB("PV_TO_HSB"),
	
	/** 积分投资 **/
	PV_INVEST("PV_INVEST"),
	
    /** 开启互生币支付单笔限额*/
    S_OPEN_HSB_PAY_SINGAL_MAX("S_OPEN_HSB_PAY_SINGAL_MAX"),
    
    /** 开启互生币支付每日限额*/
    S_OPEN_HSB_PAY_DAY_MAX("S_OPEN_HSB_PAY_DAY_MAX"),
    
    /** ----客户参数固定项 end----**/
    
    /** 抵扣券组 **/
    DEDUCTION_VOUCHER("DEDUCTION_VOUCHER"),
    
    /** 抵扣券最大张数 **/
    DEDUCTION_VOUCHER_MAX_NUM("DEDUCTION_VOUCHER_MAX_NUM"),
    
    /** 抵扣券每张面额 **/
    DEDUCTION_VOUCHER_PER_AMOUNT("DEDUCTION_VOUCHER_PER_AMOUNT"),
    
    /** 抵扣券金额占消费金额比率 **/
    DEDUCTION_VOUCHER_RATE("DEDUCTION_VOUCHER_RATE"),
    
    
    
	;

    private String code;

    BusinessParam(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
