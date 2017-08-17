/***************************************************************************
 *
 * This document contains confidential and proprietary information
 * subject to non-disclosure agreements with GUIYI Technology, Ltd.
 * This information shall not be distributed or copied without written
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.common.constant;

import java.util.HashSet;
import java.util.Set;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-common
 * 
 *  Package Name    : com.gy.hsxt.constant
 * 
 *  File Name       : RespCode.java
 * 
 *  Creation Date   : 2015-7-8
 * 
 *  Author          : yangjianguo
 * 
 *  Purpose         : 通用响应码定义
 * 
 * 
 *  History         :
 * 
 * </PRE>
 ***************************************************************************/
public enum RespCode implements IRespCode {
    /** 成功 **/
    SUCCESS(200,"成功"),
    /** 不需要知道具体原因的错误 **/
    FAIL(9999,"不需要知道具体原因的错误"),
    /** 未知错误 **/
    UNKNOWN(9998,"未知错误"),
    /** 验证码错误 **/
    VAILD_CODE_ERROR(9997,"验证码错误"),
    /** 参数错误 **/
    PARAM_ERROR(9996,"参数错误"),

    /************************* 全局配置 异常代码范围： 23000~23999 *********/

    /** 参数错误 **/
    GL_PARAM_ERROR(23901,"参数错误"),
    /** 连接失败 **/
    GL_CONNECT_ERROR(23601,"连接失败"),
    /** 处理超时 **/
    GL_TIMEOUT_ERROR(23602,"处理超时"),
    /** 无权限操作 **/
    GL_FORBIDDEN(23701,"无权限操作"),
    /** 数据没找到 **/
    GL_DATA_NOT_FOUND(23801,"数据没找到"),
    /** 数据重复 **/
    GL_DATA_DUPLICATE(23802,"数据重复"),
    /** 数据操作失败 **/
    GL_DATA_OPERATE_FAIL(23803,"数据操作失败"),

    /************************* 全局配置 end **************************/

    /************************* 消费积分 ***************************/

    /** 参数错误 **/
	PS_PARAM_ERROR(11001,"参数错误"),
	/** 找不到原订单 **/
	PS_ORDER_NOT_FOUND(11002,"找不到原订单"),
	/** 批结算账不平 **/
	PS_BATSETTLE_ERROR(11003,"批结算账不平"),
	/** 交易类型错误 **/
	PS_TRANSTYPE_ERROR(11004,"交易类型错误"),
	/** 积分小于最小值 **/
	PS_POINT_NO_MIN(11005,"积分小于最小值"),
	/** 找不到文件 **/
	PS_FILE_NOT_FOUND(11006,"找不到文件"),
	/** 编码格式 **/
	PS_CODE_FORMAT_ERROR(11007,"编码格式"),
	/** 文件读写错误 **/
	PS_FILE_READ_WRITE_ERROR(11008,"文件读写错误"),
	/** 数据库异常 **/
	PS_DB_SQL_ERROR(11009,"数据库异常"),
	/** 调用账务错误 **/
	PS_AC_ERROR(11010,"调用账务错误"),
	/** 数据不存在 **/
	PS_NO_DATA_EXIST(11011,"数据不存在"),
	/** 数据己存在 **/
	PS_DATA_EXIST(11012,"数据己存在"),
	/*** 原订单已撤单，无法撤单 */
	PS_HAS_THE_CANCELLATION(11013,"原订单已撤单，无法撤单"),
	/** 已经被冲正，无法重复冲正 */
	PS_HAS_BEEN_REVERSED(11014,"已经被冲正，无法重复冲正"),
	/** 线程异常 **/
	PS_THREAD_ERROR(11015,"线程异常"),
	/** 写文件异常 **/
	PS_WRITE_FILE_ERROR(11016,"写文件异常"),
	/** 日终积分分配批处理任务异常 **/
	PS_POINT_ALLOC_BATCH_JOB_ERROR(11017,"日终积分分配批处理任务异常"),
	/** 日终互生币结算批处理任务异常 **/
	PS_HSB_BALANCE_BATCH_JOB_ERROR(11018,"日终互生币结算批处理任务异常"),
	/** 日终积分结算批处理任务异常 **/
	PS_POINT_BALANCE_BATCH_JOB_ERROR(11019,"日终积分结算批处理任务异常"),
	/** 日终终批处理任务-日终批量退税 任务异常 **/
	PS_DAILY_BACK_TAX_JOB_ERROR(11020,"日终终批处理任务-日终批量退税 任务异常"),
	/**PS跨平台交易异常：秘钥长度错误**/
	CHECK_CARDORPASS_FAIL(11021,"PS跨平台交易异常：秘钥长度错误"),
	/**请求报文格式有误**/
	REQUEST_PACK_FORMAT(11022,"请求报文格式有误"),
	/** 调用账务非互生异常错误 **/
	PS_TO_AC_NOT_HSEXCEPTION_SRROR(11023,"调用账务非互生异常错误"),
	/** 调用用户中心系统读取客户号失败 **/        
	PS_UC_ERROR(11024,"调用用户中心系统读取客户号失败"),
	/*** 原订单已退货，无法退货 */
	PS_HAS_THE_BACKATION(11025,"原订单已退货，无法退货"),
	/*** 退货金额不能大于原订单金额,无法退货 */
	PS_BACK_AMOUNT_ERROR(11026,"退货金额不能大于原订单金额,无法退货"),
    /**互生币单笔支付超限**/
    PS_HSB_CONSUMER_PAYMENT_MAX(11027,"互生币单笔支付超限"),
    /**互生币支付每日超限**/
    PS_HSB_CONSUMER_PAYMENT_DAY_MAX(11028,"互生币支付每日超限"),
    /**缓存中找不到值**/
    PS_REDIS_NOT_VALUE(11029,"缓存中找不到值"),
    /**已冲正*/
    PS_ORDER_REVERSEED(11030,"已冲正"),
	/** 交易类型错误*/
    PS_ERROR_IN_TRANSTYPE(11031,"交易类型错误"),
    /** 定金结算失败*/
    PS_EARNEST_SETTLE_FAIL(11032, "定金结算失败"),
    /** 请不要重复请求！谢谢合作 */
    PS_OR_ES_REPEAT_SUBMIT(11033,"请不要重复请求！谢谢合作"),
    /** 调用支付系统获取手机支付TN码异常 */
    PS_INVOKE_GP_GET_MOBILE_PAY_TN_CODE_ERROR(11034,"调用支付系统获取手机支付TN码异常"),
    /** 获取快捷支付短信验证码异常 */
    PS_INVOKE_GP_GET_QUICK_PAY_SMS_CODE_ERROR(11035,"获取快捷支付短信验证码异常"),
    /** 调用支付系统获取快捷支付URL异常 */
    PS_INVOKE_GP_GET_PAY_URL_ERROR(11036, "调用支付系统获取快捷支付URL异常"),
    /** 您的订单已结算,无法撤单*/
    PS_HAS_ISSETTLE_CANCELLATION(11037,"您的订单已结算,无法撤单"),
    /** 定金交易流水号不支持退货操作 */
    PS_EARNEST_SETTLE_BACKATION(11038,"定金交易流水号不支持退货操作"),
    /** 使用抵扣券失败 */
    PS_USE_COUPON_FAIL(11039,"使用抵扣券失败"),
    /** 使用抵扣券失败 */
    PS_USE_COUPON_CANCEL_FAIL(11040,"撤销已使用的抵扣券失败"),
    //使用抵扣券
    PS_USER_RESOURCE_NULL_OR_PARAM_ERROR(11041,"用户资源号为空或格式错误"),
    PS_ENT_RESOURCE_NULL_OR_PARAM_ERROR(11042,"企业资源号为空或格式错误"),
    PS_TRANS_NO_NULL(11043,"流水号为空"),
    PS_NUMBER_MUST_GREATER_THAN_ZERO(11044,"数量错误，必须大于0"),
    PS_NO_DEDUCTION_VOUCHER(11045,"账户无抵扣券"),
    PS_DEDUCTION_VOUCHER_NOT_ENOUGH(11046,"账户抵扣券余数不足"),
    //撤销已使用的抵扣券
    PS_TRANS_NO_ERROR(11047,"流水号错误"),
    PS_USER_DEDUCTION_VOUCHER_TRANS_NOT_EXIT(11048,"用户抵扣券使用流水不存在"),

    PS_DEDUCTION_VOUCHER_MAX_NUM(11049,"抵扣券张数超过限制的数量"),
    PS_REBATE_AMOUNT_OVERSIZE(11050,"抵扣券金额过大"),

    
    /************************* 消费积分 end ***************************/

    /************************* 账务 ***************************/

    /** 参数为空 **/
    AC_PARAMETER_NULL(13000,"参数为空"),
    /** 重复数据 **/
    AC_REPEATED_DATA(13001,"重复数据"),
    /** 参数格式错误 **/
    AC_PARAMETER_FORMAT_ERROR(13002,"参数格式错误"),
    /** 客户无此账户 **/
    AC_NO_RELATION(13003,"客户无此账户"),
    /** 账户余额可变更状态异常 **/
    AC_ENTRY_STAUS_ERROR(13004,"账户余额可变更状态异常"),
    /** 正常记账金额为负 **/
    AC_AMOUNT_NEGATIVE(13005,"正常记账金额为负"),
    /** 冲正红冲金额为正 **/
    AC_AMOUNT_POSITIVE(13006,"冲正红冲金额为正"),
    /** 消费者账户余额不足 **/
    AC_BALANCE_DEFICIENCY(13007,"消费者账户余额不足"),
    /** 数据库异常 **/
    AC_SQL_ERROR(13008,"数据库异常"),
    /** 金额超出 **/
    AC_AMOUNT_BEYOND(13009,"金额超出"),
    /** 已冲正红冲 **/
    AC_CORRECTED(13010,"已冲正红冲"),
    /** IO流异常 **/
    AC_IO_ERROR(13011,"IO流异常"),
    /** 线程中断异常 **/
    AC_INTERRUPTED(13012,"线程中断异常"),
    /** 企业账户余额不足 **/
    AC_ENT_BALANCE_DEFICIENCY(13013,"企业账户余额不足"),
    /** 文件读写异常 **/
    AC_FILE(13014,"文件读写异常"),
    /** 已经退款完 **/
    AC_REFUND_OVER(13015,"已经完全退款"),
    /** 企业积分余额不足 **/
    AC_ENT_JF_DEFICIENCY(13016,"企业积分余额不足"),
    /** 企业互生币余额不足 **/
    AC_ENT_HSB_DEFICIENCY(13017,"企业互生币余额不足"),
    /** 企业货币余额不足 **/
    AC_ENT_HB_DEFICIENCY(13018,"企业货币余额不足"),
    /** 消费者积分余额不足 **/
    AC_PER_JF_DEFICIENCY(13019,"消费者积分余额不足"),
    /** 消费者互生币余额不足 **/
    AC_PER_HSB_DEFICIENCY(13020,"消费者互生币余额不足"),
    /** 消费者货币余额不足 **/
    AC_PER_HB_DEFICIENCY(13021,"消费者货币余额不足"),
    /** 当前退款金额大于原单可退款金额 **/    
    AC_REFUND_OVER_AVALIABLE(13022,"当前退款金额大于原单可退款金额"),

    /************************* 账务 end ***************************/

    BS_JFZHSB_POINTNUM(12701, "兑换积分数不能为空"),

    /****** AS接入服务 错误码范围：22000~22999 **********************/

    BS_JFZHSB_RATA(12702, "兑换率不能为空"),

    BS_JFZHSB_PASSWORD(12703, "交易密码不能为空"),

    BS_JFZHSB_MONEYNUM(12704, "可兑换的金额不能为空"),

    /** 操作成功 **/
    AS_OPT_SUCCESS(22000, "操作成功"),

    /** 操作失败 **/
    AS_OPT_FAILED(22001, "操作失败"),

    /** 参数错误 **/
    AS_PARAM_INVALID(22003, "参数错误 "),

    /** token令牌无效 **/
    AS_SECURE_TOKEN_INVALID(22004, "token令牌无效"),

    AS_POINTNO_INVALID(22005, "互生号不能为空"),

    AS_CUSTID_INVALID(22006, "客户号不能为空"),

    AS_TOKEN_INVALID(22007, "登录token不能为空"),

    AS_RANDOM_TOKEN_INVALID(22008, "randomToken不能为空"),

    AS_TRADEPWD_NUMBER_INVALID(22009, "交易密码必须是数字"),

    AS_SECURE_CUSTID_INVALID(22010, "互生号不能为空"),

    AS_QUERY_END_TIME_INVALID(22011, "查询结束时间不能为空"),

    AS_QUERY_STA_TIME_FORM_INVALID(22012, "查询开始时间格式错误"),

    AS_QUERY_END_TIME_FORM_INVALID(22013, "查询结束时间格式错误"),

    AS_QUERY_STA_END_INVALID(22014, "查询开始时间不能大于查询结束时间"),

    AS_VERIFY_USERNAME_INVALID(22015, "双签验证失败:用户名称不能为空"),

    AS_VERIFY_PASSWORD_INVALID(22016, "双签验证失败:登陆密码不能为空"),

    AS_VERIFY_CHECKTYPE_INVALID(22017, "双签验证失败:验证类型不能为空"),

    AS_GET_TOKEN_ERROR(22018, "双签验证失败:生成随机Token失败，请重试."),

    AS_VERIFY_LOGIN_INVALID(22019, "双签验证失败:账号或密码错误."),

    AS_VERIFY_TOKEN_INVALID(22020, "双签验证失败:随机Token不能为空."),

    AS_SECURE_TOKEN_NULL(22021, "随机令牌不能为空"),

    AS_GET_REGION_BY_CODE_FAILED(22022, "获取地区信息出现异常"),

    AS_GET_COUNTRY_LIST_FAILED(22023, "获取国家信息出现异常，请重试"),

    AS_GET_PROV_LIST_FAILED(22024, "获取省份列表出现异常，请重试"),

    AS_GET_BANK_LIST_FAILED(22025, "获取银行列表出现异常，请重试"),

    AS_GET_PALT_INFO_FAILED(22026, "获取平台信息出现异常，请重试"),

    AS_GET_CITY_LIST_FAILED(22027, "获取城市列表出现异常，请重试"),

    AS_DOULBE_USERID_INVALID(22028, "复核员ID不能为空"),

    AS_FILE_UPLOAD_TYPE_INVALID(22029, "上传失败，文件类型不支持"),

    AS_FILE_UPLOAD_MAXSIZE_INVALID(22030, "上传失败，文件大小超过最大限制"),

    AS_UPLOAD_FILE_INVALID(22031, "上传文件不能为空"),

    AS_UPLOAD_FILE_NOT_EXCEL(22032, "上传文件不是有效的Excel文件"),

    AS_PARSE_EXCEL_ERROR(22033, "解析Excel文件失败，请检查文件内容"),

    AS_UPLOAD_FILE_BLANK_CONTENT(22034, "上传文件内容不能为空"),

    AS_QUERY_STA_TIME_INVALID(22035, "查询开始时间不能为空 "),

    AS_ENT_CUSTID_INVALID(22036, "企业客户号不能为空"),

    AS_OPRATOR_OPTCUSTID(22037, "操作员客户号不能为空"),

    AS_LEGALNO_TYPE_INVALID(22038, "法人代表证件类型不能为空"),

    AS_BANKINFO_ACCID_INVALID(22039, "银行账户编号不能为空"),

    AS_CUSTNAME_INVALID(22040, "客户名称不能为空"),

    AS_CUSTTYPE_INVALID(22041, "客户类型不能为空"),

    AS_TRADEPWD_INVALID(22042, "交易密码不能为空"),

    AS_BANKCARDNO_INVALID(22043, "银行卡号不能为空"),

    AS_BANKCARDTYPE_INVALID(22044, "银行卡类型不能为空"),

    AS_BANKID_INVALID(22045, "银行编号不能为空"),

    AS_BANKCARDNO_ERROR(22046, "银行卡号错误"),

    /****** AS接入服务 end *****************************************/
	
	AS_APPLYID_INVALID(22047, "申请编号不能为空"),

    /****** 消费者Web Portal 错误码范围：30000~30999 begin **********************/
    /** 转出积分数不能为空 **/
    PW_INTEGRAL_INVALID(30001, " 转出积分数不能为空"),
    /** 交易密码不能为空 **/
    PW_TRADEPWD_INVALID(30002, "交易密码不能为空"),
    /** 转入互生币不能为空 **/
    PW_HSBNUM_INVALID(30003, "转入互生币不能为空"),
    /** 客户名不能为空 **/
    PW_CUSTNAME_INVALID(30004, "客户名称不能为空"),
    /** 转出积分数大于积分余额 **/
    PW_MAXINTEGRAL_INVALID(30005, "转出积分数大于积分余额"),
    /** 转出的积分数小于转出最小积分限额 **/
    PW_INTEGRATIONCONVERTIBLEMIN_INVALID(30006, "转出的积分数不满足转出最小积分限额"),

    PW_INTEGRATIONINVINTMULT_INVALID(30007, "积分投资金倍数注错误"),

    PW_HSBNUMBER_INVALID(30010, "转出互生币的数量大于账户余额"),

    PW_HSB_NUMBER_INVALID(30011, "转出互生币的数量错误"),

    PW_MAX_LENGTH_INVALID(30012, "长度校验失败"),

    PW_MIN_NUMBER_INVALID(30013, "互生币转出小于限制最小数"),

    PW_TARGETAMOUNT_NULL(30014, "转出货币金额不能为空"),

    PW_ACTUAL_AMOUNT_TRANSFERRED_INVALID(30015, "实际转入数量 不能为空"),

    PW_TARGETAMOUNT_INVALID(30016, "转出货币金额数字错误"),

    PW_OUT_HSBNUM_INVALID(30017, "转出互生币不能为空"),

    PW_HSRESNO_INVALID(30101, "互生号不能为空"),

    PW_TRANSFER_NUMBER_INVALID(30102, "兑换互生币数量 不准为空"),

    PW_AMOUNT_TO_BE_PAID_INVALID(30103, "应支付金额不准为空"),

    PW_ORDER_TYPE_INVALID(30104, "支付类型不准为空"),

    PW_TRANSFER_CSWEM_INVALID(30105, "实名或者未实名注册的兑换互生币限制错误"),

    PW_RESTRICTIONS_HSB_INVALID(30106, "兑换互生币每天限制额度错误"),

    PW_FEEAMOUNT_INVALID(30109, "手续费金额不能为空"),

    PW_BANK_NO_INVALID(30110, "银行账号不能为空"),

    PW_TARGET_AMOUNT_INVALID(30111, "银行到账金额不能为空 "),

    PW_AMOUNT_TRANSFERRED_INVALID(30112, "转出金额不能为空 "),

    PW_CURRENCY_BALANCE_INVALID(30113, "转出货币数量大于账户余额 "),

    PW_CURRENCY_MIN_INVALID(30114, "转出的货币金额不满足转出最小货币限额"),

    PW_RESTRICTIONS_INVALID(30115, "密保问题不能为空"),

    PW_RESTRICTIONS_ANSWER_INVALID(30116, "密保问题答案不能为空"),

    PW_OLD_PASSWORD_INVALID(30117, "原登录密码不能为空"),

    PW_NEW_PASSWORD_INVALID(30118, "新密码不能为空"),

    PW_NEW_PASSWORD_CONFIRM_INVALID(30119, "新密码确认不能为空"),

    PW_TRADEPWD_CONFIRM__INVALID(30120, "交易密码确认不能为空"),

    PW_LOGIN_PWD_NOT_EQUALS(30121, "两次登录密码不相等"),

    PW_TRADE_PWD_NOT_EQUALS(30122, "两次交易密码不相等"),

    PW_TRADEPWD_CONFIRM_INVALID(30023, "交易密码确认不能为空"),

    PW_VERIFICATION_CODE_ERROR(30024, "验证码错误"),

    PW_VERIFICATION_CODE_INVALID(30025, "验证码不能为空"),

    PW_LOGINPWD_INVALID(30026, "登录密码不能为空"),

    /** 系统安全设置 **/

    PW_LOGIN_PWD_LENGTH(30027, " 登录密码长度限制"),

    PW_TRADE_PWD_LENGTH(30028, "交易密码长度限制 "),

    PW_TRADE_PWD_EXIST(30029, "交易密码存在,不需要重新设置"),

    PW_TRADE_PWD_NO_EXIST(30030, "交易密码不存在,请先设置交易密码"),

    PW_NO_SET_RESERVE_INFO(30031, "未设置预留信息"),

    PW_RESERVE_MAX_INVALID(30032, "预留信息长度错误"),

    PW_ENT_FILING_SHAREHOLDER_IDNO_INVALID(30127, "证件号码不能为空"),

    PW_VERIFICATION_CODE_NULL(30128, "验证码已过期"),

    PW_REALNAME_NULL(30130, "真实姓名不能为空"),

    PW_CERTYPE_NULL(30131, "证件类型不能为空"),

    PW_CERNO_NULL(30132, "证件号码不能为空"),

    PW_COUNTRY_CODE_NULL(30133, "国籍不能为空"),

    PW_IDCARD_NO_INVALID(30134, "身份证号码无效"),

    PW_IDCARD_INVALID(30135, "证件证号码无效"),

    PW_USERNAME_LENGTH_INVALID(30136, "姓名的长度错误"),

    PW_BIND_BANK_MAX_INVALID(30137, "银行卡绑定已达到最大数量"),

    PW_BANK_NO_FORMAT_INVALID(30138, "银行卡号错误"),

    PW_BIND_NO_INCONSISTENT(30139, "两次账户输入不一致"),

    PW_DEPOSIT_BANK_INVALID(30140, "开户银行不能为空"),

    PW_CONFIRM_BANK_NO_INVALID(30141, "确认银行卡号不能为空"),

    PW_BANK_NAME_INVALID(30142, "银行名称不能为空"),

    PW_BANK_ACC_NAME_INVALID(30143, "银行编码不能为空"),

    PW_BANKINFO_COUNTRYNO_INVALID(30144, "开户地区所属国家不能为空"),

    PW_BANKINFO_PROVINCENO_INVALID(30145, "开户地区所属省份不能为空"),

    PW_BANKINFO_CITYNO_INVALID(30146, "开户地区所属城市不能为空"),

    PW_BANKINFO_ISDEFAUT_INVALID(30147, "是否默认账户不能为空"),

    PW_CONFIRM_BANK_INVALID(30148, "确认账户不能为空"),

    PW_BANK_ID_INVALID(30149, "银行卡编码参数错误"),

    PW_MOBILE_NULL(30150, "手机号码不能为空"),

    PW_MOBILE__INVALID(30151, "手机号码错误"),

    PW_SMS_CODE_INVALID(30152, "短信验证码不能为空"),

    PW_LOSS_REASON_NULL(30153, "挂失原因不能为空"),

    PW_INTEGRAL_NUMBER_INVALID(30154, "转出积分数量必须是整数"),

    PW_ENTERPRISE_NULL(30155, "企业名称不能为空"),

    PW_ENTERPRISE_ADDRESS_NULL(30156, "企业注册地址不能为空"),

    PW_ENTERPRISE_INVALID(30157, "企业名称长度格式错误"),

    PW_ENTERPRISE_ADDRESS_INVALID(30158, "企业注册地址长度格式错误"),

    PW_INFORMATION_CHANGES_NOT(30159, "重要信息变更期间不能进行银行转账操作"),

    PW_REALNAME_REGISTRATION(30160, "货币转银行账户操作必须先实名注册"),

    PW_SET_TRANSACTION_PASSWORD_INVALID(30161, "请至安全设置菜单设置交易密码"),

    PW_REGINFO_CITY_INVALID(30162, "所属地区城市不能为空"),

    PW_RESTRICTIONS_MAX_INVALID(30163, "密保问题答案只可以输入五十个汉字"),

    PW_EMAIL_INVALID(30164, "邮箱信息不能为空"),

    PW_CONFIRM_EMAIL_INVALID(30165, "确认邮箱信息不能为空"),

    PW_INCONSISTENT_EMAIL_INVALID(30166, "两次邮箱信息输入不一致"),

    PW_EMAIL_FORM_INVALID(30167, "邮箱格式不正确"),

    PW_SETDELPASSWORD_NUMBER_INVALID(30168, "交易密码必须是数字"),

    PW_NOT_RELNAME_AUTH_INVALID(30169, "未实名注册"),

    PW_COMPANY_TYPE_INVALID(30170, "公司类型不能为空"),

    PW_SMRZSP_SEX_NOT_NULL(30171, "性别不能为空"),

    PW_SMRZSP_LICENCEISSUING_NOT_NULL(30172, "发证机关不能为空"),

    PW_SMRZSP_CERPICA_NOT_NULL(30173, "证件正面照不能为空"),

    PW_SMRZSP_CERPICB_NOT_NULL(30174, "证件背面照不能为空"),

    PW_SMRZSP_CERPICH_NOT_NULL(30175, "手持证件照不能为空"),

    PW_SMRZSP_VALIDATE_NOT_NULL(30176, "证件有效期不能为空"),

    PW_SMRZSP_BIRTHADDR_NOT_NULL(30178, "户籍地址不能为空"),

    PW_POST_SCRIPT_NOT_NULL(30179, "认证附言长度过长请确认"),

    PW_SMRZSP_LOCATION_NOT_NULL(30180, "签发地点不能为空"),

    PW_BIRTH_PLACE_NOT_NULL(30181, "出生地点不能为空"),

    PW_BIZREG_ESTADATE_INVALID(30182, "成立日期不能为空"),

    PW_CUSTID_INVALID(30183, "客户号不能为空"),

    PW_CUSTTYPE_INVALID(30184, "客户类型不能为空"),

    PW_OPTCUSTID_INVALID(30185, " 操作员号不能为空"),

    PW_BUY_HSB_MAX_INVALID(30186, " 兑换互生币不能大于单笔限额最大值 "),

    PW_BUY_HSB_MIN_INVALID(30187, " 兑换互生币不能小于单笔限额最小值"),

    PW_BUY_HSB_LIMIT_INVALID(30188, " 兑换互生币不能大于单日限额"),

    PW_BIRTHADDR_LENGTH_INVALID(30189, "户籍地址长度应为2-128个字符"),

    PW_PROFESSION_LENGTH_INVALID(30190, "职业长度应为0-50个字符"),

    PW_LICENCE_ISSUING_LENGTH_INVALID(30191, "发证机关长度应为2-128个字"),

    PW_VALID_DATE_LENGTH_INVALID(30192, "证件有效期长度应为2-20个字"),

    PW_PASSPORT_BIRTHADDR_LENGTH_INVALID(30193, "出生地点长度应为2-128个字"),

    PW_PASSPORT_PROFESSION_LENGTH_INVALID(30194, "签发地点长度应为2-128个字"),

    PW_PASSPORT_LICENCE_ISSUING_LENGTH_INVALID(30195, "签发机关长度应为2-128个字"),

    PW_ENTTYPE_LENGTH_INVALID(30196, "公司类型长度应为2-20个字"),

    /** 系统安全设置 **/
	
	PW_BIZREG_ENTTYPE_INVALID(30197, "企业类型不能为空"),

    /****** 企业Web Portal 错误码范围：31000~31999 **********************/

    /** 系统安全设置 **/

    EW_RESTRICTIONS_INVALID(31000, "密保问题不能为空"),

    EW_RESTRICTIONS_ANSWER_INVALID(31001, "密保问题答案不能为空"),

    EW_OLD_PASSWORD_INVALID(31002, "旧密码不能为空"),

    EW_NEW_PASSWORD_INVALID(31003, "新密码不能为空"),

    EW_NEW_PASSWORD_CONFIRM_INVALID(31004, "新密码确认不能为空"),

    EW_TRADEPWD_CONFIRM__INVALID(31005, "交易密码确认不能为空"),

    EW_LOGIN_PWD_NOT_EQUALS(31006, "两次登录密码不相等"),

    EW_TRADE_PWD_NOT_EQUALS(31007, "两次交易密码不相等"),

    EW_TRADEPWD_CONFIRM_INVALID(31008, "交易密码确认不能为空"),

    EW_VERIFICATION_CODE_ERROR(31009, "验证码错误"),

    EW_VERIFICATION_CODE_INVALID(31010, "验证码不能为空"),

    EW_LOGINPWD_INVALID(31011, "登录密码能为空"),

    EW_VERIFICATION_CODE_NULL(31012, "验证码已过期"),

    EW_LOGIN_PWD_LENGTH(31013, " 登录密码长度限制"),

    EW_TRADE_PWD_LENGTH(31014, "交易密码长度限制 "),

    EW_TRADE_PWD_EXIST(31015, "交易密码存在,不需要重新设置"),

    EW_TRADE_PWD_NO_EXIST(31016, "交易密码不存在,请先设置交易密码"),

    EW_NO_SET_RESERVE_INFO(31017, "未设置预留信息"),

    EW_TOOL_SERVICE_TYPE(31018, "工具服务类型不能为空"),

    EW_TOOL_CONFIGS_INVALID(31019, "工具配置单不能为空"),

    EW_TOOL_ADDR_INVALID(31020, "工具收货地址不能为空"),

    EW_ANALYZE_TOOL_ORDER_PARAM_ERROR(31021, "操作非法,解析工具订单参数错误"),

    EW_TOOL_PAY_CHANNEL_INVALID(31022, "工具支付方式不能为空"),

    EW_TOOL_ORDERNO_INVALID(31023, "工具订单号不能为空"),

    EW_SMSCODE_INVALID(31024, "短信验证码不能为空"),

    EW_BINDINGNO_INVALID(31025, "快捷签约号不能为空"),

    EW_DEVICETYPE_INVALID(31026, "设备类型不能为空"),

    EW_DEVICENO_INVALID(31027, "设备号不能为空"),

    EW_DEVICESTATUS_INVALID(31028, "设备状态不能为空"),

    EW_CONFIRMFILE_INVALID(31029, "卡样确认文件不能为空"),

    EW_MICROPIC_INVALID(31030, "缩略图不能为空"),

    EW_SOURCEFILE_INVALID(31031, "源文件文件不能为空"),

    EW_TOOL_CONFNO_INVALID(31032, "配置单号不能为空"),

    /** 系统安全设置 **/

    /****** 企业Web Portal 错误码范围：31000~31999 end **********************/

    /****** SW服务公司 错误码范围：32000~32999 **********************/

    SW_POINTNO_INVALID(32500, "互生卡号不能为空"),

    SW_PHONE_INVALID(32506, "手机格式不正确 "),

    SW_LICENSENO_INVALID(32507, "营业执照号不正确"),

    SW_IDCARD_FORM_INVALID(32508, "身份证格式不正确"),

    SW_PASSPORT_FORM_INVALID(32509, "护照格式不正确"),

    SW_OFFICE_PHONE_INVALID(32510, "办公电话不能为空"),

    SW_OFFICE_PHONE_FORM_INVALID(32511, "办公电话格式不正确"),

    SW_ZIPCODE_INVALID(32512, "邮政编号不能为空"),

    SW_ZIPCODE_FORM_INVALID(32513, "邮政编码格式不正确"),

    SW_FAX_INVALID(32514, "传真号码不能为空"),

    SW_FAX_FORM_INVALID(32515, "传真号码格式不正确"),

    SW_BANK_INVALID(32516, "银行账号不能为空"),

    SW_BANK_FORM_INVALID(32517, "银行账号格式不正确"),

    SW_BANKNAME_INVALID(32518, "账户名称不能为空"),

    SW_BANKNAME_LENGTH_INVALID(32519, "账户名称应为2~100个字符"),

    SW_CUR_CODE_INVALID(32520, "结算币种不能为空"),

    SW_ORGNO_INVALID(32521, "组织机构代码证号不能为空"),

    SW_ORGNO_FORM_INVALID(32522, "组织机构代码证号格式不正确"),

    SW_TAXNO_INVALID(32523, "纳税人识别号不能为空"),

    SW_TAXNO_FORM_INVALID(32524, "纳税人识别号格式不正确"),

    SW_VIEW_MARK_LENGTH_INVALID(32599, "备注应为0~300个字符"),

    SW_INTENTION_CUST_APPLYID_INVALID(32600, "意向客户申请编号不能为空"),

    SW_INTENTION_CUST_STATUS_INVALID(32601, "意向客户处理状态不能为空"),

    SW_INTENTION_CUST_INVALID(32602, "未找到对应客户申请资料"),

    SW_INTENTION_CUST_APPR_REMARK_INVALID(32603, "处理意见应为0~300个字符"),

    SW_ENT_FILING_APPLYID_INVALID(32604, "企业申请编号不能为空"),

    SW_ENT_FILING_DETAIL_INVALID(32605, "未找到对应企业报备资料"),

    SW_ENT_FILING_RAISE_REMARK_INVALID(32606, "异议说明不能为空"),

    SW_ENT_FILING_RAISE_REMARK_LENGTH_INVALID(32607, "异议说明应为1~300个字符"),

    SW_ENT_FILING_SHAREHOLDER_INVALID(32608, "股东信息不能为空"),

    SW_ENT_FILING_OPTCUSTID_INVALID(32609, "操作员客户号不能为空"),

    SW_ENT_FILING_SHAREHOLDER_SHNAME_INVALID(32610, "股东名称不能为空"),

    SW_ENT_FILING_SHAREHOLDER_SHTYPE_INVALID(32611, "股东性质不能为空"),

    SW_ENT_FILING_SHAREHOLDER_IDTYPE_INVALID(32612, "证件类型不能为空"),

    SW_ENT_FILING_SHAREHOLDER_IDNO_INVALID(32613, "证件号码不能为空"),

    SW_ENT_FILING_SHAREHOLDER_PHONE_INVALID(32614, "联系电话不能为空"),

    SW_ENT_FILING_SHAREHOLDERID_INVALID(32615, "股东编号不能为空"),

    SW_ENT_FILING_OBJECT_INVALID(32616, "报备企业不能为空"),

    SW_ENT_FILING_ENTCUSTNAME_INVALID(32617, "企业名称不能为空"),

    SW_ENT_FILING_ENT_ADDRESS_INVALID(32618, "企业地址不能为空"),

    SW_ENT_FILING_LICENSENO_INVALID(32619, "营业执照号不能为空"),

    SW_ENT_FILING_LEGALNAME_INVALID(32620, "企业法人代表不能为空"),

    SW_ENT_FILING_LEGALTYPE_INVALID(32621, "证件类型不能为空"),

    SW_ENT_FILING_LINKEMAN_INVALID(32622, "联系人姓名不能为空"),

    SW_ENT_FILING_ENTTYPE_INVALID(32623, "企业性质不能为空"),

    SW_ENT_FILING_LEGALNO_INVALID(32624, "法人证件号码不能为空"),

    SW_ENT_FILING_PHONE_INVALID(32625, "联系人手机不能为空"),

    SW_ENT_FILING_DEALAREA_INVALID(32626, "企业经营范围不能为空"),

    SW_ENT_FILING_APPRREMARK_INVALID(32627, "报备说明不能为空"),

    SW_ENT_FILING_DEALAREA_LENGTH_INVALID(32628, "企业经营范围应为1~300个字符"),

    SW_ENT_FILING_APPRREMARK_LENGTH_INVALID(32629, "报备说明应为1~300个字符"),

    SW_ENT_FILING_ENTCUSTNAME_LENGTH_INVALID(32630, "企业名称应为2~128个字符"),

    SW_ENT_FILING_ENT_ADDRESS_LENGTH_INVALID(32631, "企业地址应为2~128个字符"),

    SW_ENT_FILING_LEGALNAME_LENGTH_INVALID(32632, "企业法人代表应为2~20个字符"),

    SW_ENT_FILING_LINKEMAN_LENGTH_INVALID(32633, "联系人姓名应为2~20个字符"),

    SW_ENT_FILING_ENTTYPE_LENGTH_INVALID(32634, "企业性质应为2~20个字符"),

    SW_ENT_FILING_SAVE_BASEINFO_INVALID(32635, "请先保存企业基本信息"),

    SW_ENT_FILING_SHAREHOLDER_SHNAME_LENGTHINVALID(32636, "股东名称应为2~20个字符"),

    SW_ENT_FILING_POSI_PIC_FILEID_INVALID(32637, "法人代表证件正面文件不能为空"),

    SW_ENT_FILING_BACK_PIC_FILEID_INVALID(32638, "法人代表证件反面文件不能为空"),

    SW_ENT_FILING_LICE_PIC_FILEID_INVALID(32639, "营业执照扫描件文件不能为空"),

    SW_ENT_FILING_BANK_PIC_FILEID_INVALID(32640, "银行资金证明文件不能为空"),

    SW_ENT_FILING_SHARE_PIC_FILEID_INVALID(32641, "合作股东证明文件文件不能为空"),

    SW_ENT_FILING_APT_INVALID(32642, "附件信息不完整"),

    SW_ENT_FILING_SHL_INVALID(32643, "股东信息不完整"),

    SW_ENT_FILING_BASEINFO_INVALID(32644, "未找到对应的企业基本信息"),

    SW_ENT_LINKINFO_LINKMAN_INVALID(32645, "联系人不能为空"),

    SW_ENT_LINKINFO_LINKMAN_LENGTH_INVALID(32646, "联系人应为2~20个字符"),

    SW_ENT_LINKINFO_ADDRESS_INVALID(32647, "联系地址不能为空"),

    SW_ENT_LINKINFO_ADDRESS_LENGTH_INVALID(32648, "联系地址应为2~128个字符"),

    SW_ENT_LINKINFO_WEBSITE_INVALID(32649, "企业网址不能为空"),

    SW_ENT_LINKINFO_WEBSITE_FORM_INVALID(32650, "企业网址格式不正确"),

    SW_ENT_LINKINFO_LINKMAN_JOB_INVALID(32651, "联系人职务不能为空"),

    SW_ENT_LINKINFO_LINKMAN_JOB_FORM_INVALID(32652, "联系人职务格式不正确"),

    SW_ENT_LINKINFO_MOBILE_INVALID(32653, "联系人手机不能为空"),

    SW_ENT_LINKINFO_MOBILE_FORM_INVALID(32654, "联系人手机格式不正确"),

    SW_ENT_LINKINFO_EMAIL_INVALID(32655, "企业安全邮箱不能为空"),

    SW_ENT_LINKINFO_EMAIL_FORM_INVALID(32656, "企业安全邮箱格式不正确"),

    SW_ENT_LINKINFO_QQ_INVALID(32657, "企业联系QQ不能为空"),

    SW_ENT_LINKINFO_QQ_FORM_INVALID(32658, "企业联系QQ格式不正确"),

    SW_ENT_LINKINFO_NOT_FOUND_INVALID(32659, "未找到对应的企业联系信息"),

    SW_ENT_LINKINFO_CERT_INVALID(32660, "联系人授权委托书不能为空"),

    SW_BANKINFO_NOT_FOUND_INVALID(32661, "未找到对应的银行账户信息"),

    SW_BANKINFO_INVALID(32662, "银行账户信息不能为空"),

    SW_BANKINFO_COUNTRYNO_INVALID(32663, "开户地区所属国家不能为空"),

    SW_BANKINFO_PROVINCENO_INVALID(32664, "开户地区所属省份不能为空"),

    SW_BANKINFO_CITYNO_INVALID(32665, "开户地区所属城市不能为空"),

    SW_BANKINFO_ISDEFAUT_INVALID(32666, "是否默认账户不能为空"),

    SW_BANKINFO_BANKCODE_INVALID(32667, "开户银行不能为空"),

    SW_CONFIRM_BANK_INVALID(32668, "确认账户不能为空"),

    SW_BANKINFO_TWO_TIMES_INVALID(32669, "两次账户输入不一致"),

    SW_ENT_LINKINFO_OBJ_INVALID(32670, "企业联系信息不能为空"),

    SW_BIZREG_MOBILE_INVALID(32671, "联系电话不能为空"),

    SW_BIZREG_MOBILE_FORM_INVALID(32672, "联系电话格式不正确"),

    SW_BIZREG_ENTTYPE_INVALID(32673, "企业类型不能为空"),

    SW_BIZREG_ENTTYPE_LENGTH_INVALID(32674, "企业类型应为2-20个字符"),

    SW_BIZREG_ESTADATE_INVALID(32675, "成立日期不能为空"),

    SW_BIZREG_ESTADATE_FORM_INVALID(32676, "成立日期格式不正确"),

    SW_BIZREG_LIMITDATE_INVALID(32677, "营业期限不能为空"),

    SW_BIZREG_LIMITDATE_LENGTH_INVALID(32678, "营业期限应为2~20个字符"),

    SW_BIZREG_INVALID(32679, "工商登记信息不能为空"),

    SW_DECLARE_ORG_PIC_FILEID_INVALID(32680, "组织机构代码证扫描件不能为空"),

    SW_DECLARE_TAX_PIC_FILEID_INVALID(32681, "税务登记证扫描件不能为空"),

    SW_DECLARE_PRO_PIC_FILEID_INVALID(32682, "创业帮扶协议不能为空"),

    SW_REGINFO_LOAD_FAILED_INVALID(32683, "数据初始化失败，请重试!"),

    SW_REGINFO_TOCUSTTYPE_INVALID(32684, "申报类别不能为空"),

    SW_REGINFO_ENTNAME_INVALID(32685, "企业中文名称不能为空"),

    SW_REGINFO_ENTNAME_LENGTH_INVALID(32686, "企业中文名称应为2~128个字符"),

    SW_REGINFO_COUNTRYNO_INVALID(32687, "所属地区国家不能为空"),

    SW_REGINFO_PROVINCENO_INVALID(32688, "所属地区省份不能为空"),

    SW_REGINFO_CITY_INVALID(32689, "所属地区城市不能为空"),

    SW_REGINFO_ENTENNAME_LENGTH_INVALID(32690, "企业英文名称应为2~128个字符"),

    SW_REGINFO_TG_AVAIL_QUOTA_INVALID(32691, "没有可用的托管企业可用配额"),

    SW_REGINFO_TOBUY_RESRANGE_INVALID(32692, "启用消费者资源类型不能为空"),

    SW_REGINFO_TOENTRESNO_INVALID(32693, "拟用企业管理号不能为空"),

    SW_REGINFO_CY_AVAIL_QUOTA_INVALID(32694, "没有可用的成员企业可用配额"),

    SW_REGINFO_FW_AVAIL_QUOTA_INVALID(32695, "没有可用的服务公司可用配额"),

    SW_REGINFO_NODE_INVALID(32696, "未找到对应的企业增值信息"),

    SW_REGINFO_OBJ_INVALID(32697, "企业注册信息不能为空"),

    SW_ENT_REVIEW_ISPASS_INVALID(32698, "审核结果不能为空"),

    SW_ENT_REVIEW_LENGTH_INVALID(32699, "审核意见应为0~300个字符"),

    SW_REGINFO_TOMRESNO_INVALID(32700, "所属管理公司互生号不能为空"),

    SW_ENT_MSG_TITIL(32701, "消息标题不能为空"),

    SW_ENT_MSG_CONTENT(32702, "消息内容不能为空"),

    SW_ENT_MSG_RECEIVE(32703, "没有指定接收消息的单位"),

    SW_ENT_MSG_ID(32704, "当前操作需要消息信息的ID编号"),

    SW_ENT_ANNUALFEE_TRANSPASS(32705, "交易密码不能为空"),

    SW_ENT_ANNUALFEE_ORDERNO(32706, "订单编号不能为空"),

    SW_ENT_CYQYZGZX_APPLYID_(32707, "申请编号不能为空"),

    SW_ENT_CYQYZGZX_ISPASS_(32708, "未指明审批结果"),

    // 银行帐户
    SW_NOT_EMPTY_CITY_CODE(33001, "所在城市不能不能空"),

    SW_NOT_EMPTY_PROVINCE_CODE(33002, "所在省份不能为空"),

    SW_NOT_EMPTY_BANK_CODE(33003, "开户银行不能为空"),

    SW_ILLEGAL_BANK_CARD_ID(33004, "银行卡号不正确"),

    // 联系人信息
    SW_ILLEGAL_OFFICE_PHONE(33005, "办公室电话不正确"),

    SW_ILLEGAL_MAIL(33006, "邮箱地址不正确"),

    SW_ILLEGAL_PORTCODE(33007, "邮政编码不正确"),

    SW_ILLEGAL_FIX(33008, "传真号码不正确"), SW_ILLEGAL_QQ(33009, "QQk号码不正确"),

    // 重要信息变更
    SW_ENT_CAHNGE_NOT_EMPTY_NAME(33010, "企业名称不能为空"),

    SW_ENT_CAHNGE_NOT_EMPTY_ADDRESS(33011, "企业地址不能为空"),

    SW_ENT_CAHNGE_NOT_EMPTY_LEGAL(33012, "法人代表不能为空"),

    SW_ENT_CAHNGE_NOT_EMPTY_LEGAL_CRE_NO(33013, "法人代表证件号码不能为空"),

    SW_ENT_CAHNGE_NOT_EMPTY_LEGAL_CRE_TYPE(33014, "法人代表证件不能为空"),

    SW_ENT_CAHNGE_NOT_EMPTY_LICENCE_NO(33015, "营业执照号码不能为空"),

    SW_ENT_CAHNGE_NOT_EMPTY_ORG_NO(33016, "组织机构号码不能为空"),

    SW_ENT_CAHNGE_NOT_EMPTY_TAXP_NO(33017, "纳税人识别号不能为空"),

    SW_ENT_CAHNGE_NOT_EMPTY_APPLY_PIC(33018, "申请书文件不能为空"),

    SW_ENT_CAHNGE_NOT_EMPTY_LEGAL_FRONT_PIC(33019, "法人代表证件正面图片不能为空"),

    SW_ENT_CAHNGE_NOT_EMPTY_LEGAL_BACK_PIC(33020, "法人代表证件背面图片不能为空"),

    SW_ENT_CAHNGE_NOT_EMPTY_LICENCE_PIC(33021, "营业执照图片不能为空"),

    SW_ENT_CAHNGE_NOT_EMPTY_OGR_PIC(33022, "组织机构代码证图片不能为空"),

    SW_ENT_CAHNGE_NOT_EMPTY_TAXPAYER_PIC(33023, "税务登记证图片不能为空"),

    SW_ENT_CAHNGE_NOT_EMPTY_PWE_PIC(33024, "授权委托书图片不能为空"),

    SW_ENT_CAHNGE_NOT_EMPTY_CONTACT(330125, "重要信息变更业务办理申请书不能为空"),

    SW_ILLEGAL_MOBILE(33026, "手机号码不正确"),

    SW_ILLEGAL_WEBSITE(33027, "网站地址不正确"),

    SW_ILLEGAL_POSTCODE(33028, "邮政编码不正确"),

    /** 系统安全设置 **/
    SW_RESTRICTIONS_INVALID(33029, "密保问题不能为空"),

    SW_RESTRICTIONS_ANSWER_INVALID(33030, "密保问题答案不能为空"),

    SW_OLD_PASSWORD_INVALID(33031, "旧密码不能为空"),

    SW_NEW_PASSWORD_INVALID(33032, "新密码不能为空"),

    SW_NEW_PASSWORD_CONFIRM_INVALID(33033, "新密码确认不能为空"),

    SW_TRADEPWD_CONFIRM__INVALID(33034, "交易密码确认不能为空"),

    SW_LOGIN_PWD_NOT_EQUALS(33035, "两次登录密码不相等"),

    SW_TRADE_PWD_NOT_EQUALS(33036, "两次交易密码不相等"),

    SW_TRADEPWD_CONFIRM_INVALID(33037, "交易密码确认不能为空"),

    SW_VERIFICATION_CODE_ERROR(33038, "验证码错误"),

    SW_VERIFICATION_CODE_INVALID(33039, "验证码不能为空"),

    SW_LOGINPWD_INVALID(33040, "登录密码能为空"),

    SW_VERIFICATION_CODE_NULL(33041, "验证码已过期"),

    SW_LOGIN_PWD_LENGTH(33042, " 登录密码长度限制"),

    SW_TRADE_PWD_LENGTH(33043, "交易密码长度限制 "),

    SW_TRADE_PWD_EXIST(33044, "交易密码存在,不需要重新设置"),

    SW_TRADE_PWD_NO_EXIST(33045, "交易密码不存在,请先设置交易密码"),

    SW_NO_SET_RESERVE_INFO(33046, "未设置预留信息"),

    /** 系统安全设置 **/

    /** 账户管理 **/

    /** 申请编号不能为空 */
    SW_APPLY_ID_NOT_NULL(33047, "申请编号不能为空"),
    /** 请填写正确的申请调整税率 */
    SW_APPLY_TAXRATE_ERROR(33048, "请填写正确的申请调整税率"),
    /** 企业纳税人类型不能为空 */
    SW_TAXPAYER_TYPE_ERROR(33049, "企业纳税人类型不能为空"),
    /** 请上传证明材料 */
    SW_PROOF_ERROR(33050, "请上传证明材料"),
    /** 请填写申请说明 */
    SW_APPLY_REASON_ERROR(33051, "请填写申请说明"),

    /** 账户管理 **/

    /****** SW服务公司 end *****************************************/

    /********** 33000~33999 MW 管理公司Web Portal **************/

    MW_APPLYID_INVALID(33000, "申请编号不能为空"),

    MW_REGINFO_ENTNAME_INVALID(33201, "企业中文名称不能为空"),

    MW_REGINFO_ENTNAME_LENGTH_INVALID(33202, "企业中文名称应为2~128个字符"),

    MW_REGINFO_ENTENNAME_LENGTH_INVALID(33203, "企业英文名称应为2~128个字符"),

    MW_VIEW_MARK_LENGTH_INVALID(33204, "备注应为0~300个字符"),

    MW_ENT_FILING_ENTCUSTNAME_INVALID(33205, "企业名称不能为空"),

    MW_ENT_FILING_ENTCUSTNAME_LENGTH_INVALID(33206, "企业名称应为2~128个字符"),

    MW_ENT_FILING_LEGALNAME_INVALID(33207, "企业法人代表不能为空"),

    MW_ENT_FILING_LEGALNAME_LENGTH_INVALID(33208, "企业法人代表应为2~20个字符"),

    MW_BIZREG_ENTTYPE_INVALID(33209, "企业类型不能为空"),

    MW_BIZREG_ENTTYPE_LENGTH_INVALID(33210, "企业类型应为2-20个字符"),

    MW_IDTYPE_INVALID(33211, "证件类型不能为空"),

    MW_ORGNO_INVALID(33212, "组织机构代码证号不能为空"),

    MW_BIZREG_ESTADATE_INVALID(33213, "成立日期不能为空"),

    MW_BIZREG_ESTADATE_FORM_INVALID(33214, "成立日期格式不正确"),

    MW_ENT_FILING_ENT_ADDRESS_INVALID(33215, "企业地址不能为空"),

    MW_ENT_FILING_ENT_ADDRESS_LENGTH_INVALID(33216, "企业地址应为2~128个字符"),

    MW_ENT_FILING_LICENSENO_INVALID(33217, "营业执照号不能为空"),

    MW_LICENSENO_INVALID(33218, "营业执照号不正确"),

    MW_BIZREG_MOBILE_INVALID(33219, "联系电话不能为空"),

    MW_BIZREG_MOBILE_FORM_INVALID(33220, "联系电话格式不正确"),

    MW_TAXNO_INVALID(33221, "纳税人识别号不能为空"),

    MW_ENT_FILING_DEALAREA_INVALID(33222, "企业经营范围不能为空"),

    MW_ENT_FILING_DEALAREA_LENGTH_INVALID(33223, "企业经营范围应为0~300个字符"),

    MW_BIZREG_LIMITDATE_INVALID(33224, "营业期限不能为空"),

    MW_BIZREG_LIMITDATE_LENGTH_INVALID(33225, "营业期限应为0~50个字符"),

    MW_ENT_FILING_LEGALNO_INVALID(33226, "法人证件号码不能为空"),

    MW_IDCARD_FORM_INVALID(33227, "身份证格式不正确"),

    MW_PASSPORT_FORM_INVALID(33228, "护照格式不正确"),

    MW_ENT_LINKINFO_LINKMAN_INVALID(33229, "联系人不能为空"),

    MW_ENT_LINKINFO_LINKMAN_LENGTH_INVALID(33230, "联系人应为2~20个字符"),

    MW_ENT_LINKINFO_ADDRESS_INVALID(33231, "联系地址不能为空"),

    MW_ENT_LINKINFO_ADDRESS_LENGTH_INVALID(33232, "联系地址应为2~128个字符"),

    MW_ENT_LINKINFO_MOBILE_INVALID(33233, "联系人手机不能为空"),

    MW_ENT_LINKINFO_MOBILE_FORM_INVALID(33234, "联系人手机格式不正确"),

    MW_ZIPCODE_INVALID(33235, "邮政编号不能为空"),

    MW_ZIPCODE_FORM_INVALID(33236, "邮政编码格式不正确"),

    MW_ENT_LINKINFO_EMAIL_INVALID(33237, "企业安全邮箱不能为空"),

    MW_ENT_LINKINFO_EMAIL_FORM_INVALID(33238, "企业安全邮箱格式不正确"),

    MW_ENT_LINKINFO_CERT_INVALID(33239, "联系人授权委托书不能为空"),

    MW_ENT_FILING_POSI_PIC_FILEID_INVALID(33240, "法人代表证件正面文件不能为空"),

    MW_ENT_FILING_BACK_PIC_FILEID_INVALID(33241, "法人代表证件反面文件不能为空"),

    MW_ENT_FILING_LICE_PIC_FILEID_INVALID(33242, "营业执照扫描件文件不能为空"),

    MW_DECLARE_ORG_PIC_FILEID_INVALID(33243, "组织机构代码证扫描件不能为空"),

    MW_DECLARE_TAX_PIC_FILEID_INVALID(33244, "税务登记证扫描件不能为空"),

    MW_DECLARE_PRO_PIC_FILEID_INVALID(33245, "创业帮扶协议不能为空"),

    MW_BANKNAME_INVALID(33246, "账户名称不能为空"),

    MW_CUR_CODE_INVALID(33247, "结算币种不能为空"),

    MW_BANKINFO_BANKCODE_INVALID(33248, "开户银行不能为空"),

    MW_BANKINFO_PROVINCENO_INVALID(33249, "所属省份不能为空"),

    MW_BANKINFO_CITYNO_INVALID(33250, "所属城市不能为空"),

    MW_BANK_NO_INVALID(33251, "银行账号不能为空"),

    MW_BANK_FORM_INVALID(33252, "银行账号格式不正确"),

    MW_CONFIRM_BANK_INVALID(33253, "确认账户不能为空"),

    MW_BANKINFO_TWO_TIMES_INVALID(33254, "两次账户输入不一致"),

    MW_DOULBE_USERNAME_INVALID(33255, "复核员用户名不能为空"),

    MW_DOULBE_PASSWORD_INVALID(33256, "复核员登陆密码不能为空"),

    MW_ENT_REVIEW_LENGTH_INVALID(33257, "审核意见应为0~300个字符"),

    MW_ENT_REVIEW_ISPASS_INVALID(33258, "审核结果不能为空"),

    MW_BANKINFO_INVALID(33259, "银行账户信息不能为空"),

    MW_ENT_LINKINFO_OBJ_INVALID(33260, "企业联系信息不能为空"),

    MW_BIZREG_INVALID(33261, "工商登记信息不能为空"),

    MW_REGINFO_CITY_INVALID(33262, "所属地区城市不能为空"),

    MW_REGINFO_COUNTRYNO_INVALID(33263, "所属地区国家不能为空"),

    MW_REGINFO_PROVINCENO_INVALID(33264, "所属地区省份不能为空"),

    MW_REGINFO_TOBUY_RESRANGE_INVALID(33265, "启用消费者资源类型不能为空"),

    MW_REGINFO_TOENTRESNO_INVALID(33266, "拟用企业管理号不能为空"),

    MW_REGINFO_TOCUSTTYPE_INVALID(33267, "申报类别不能为空"),

    MW_REGINFO_TOMRESNO_INVALID(33268, "所属管理公司互生号不能为空"),

    MW_REGINFO_NOT_FOUND(33271, "获取服务公司拟用互生号失败,原因：企业注册信息不完整"),

    MW_REGINFO_FW_AVAIL_QUOTA_INVALID(33272, "获取服务公司拟用互生号失败,原因：没有可用的服务公司互生号"),

    MW_DOULBE_USERID_INVALID(33269, "复核员ID不能为空"),

    MW_PICK_RESNO_INVALID(33270, "拟用企业互生号不能为空"),

    MW_OPRATOR_ENTCUSNTID(33301, "企业客户号不能为空"),

    MW_OPRATOR_ENTRESNO(33302, "企业互生号不能为空"),

    MW_OPRATOR_USERNAME(33303, "用户名（员工账号）不能为空"),

    MW_OPRATOR_LOGINPWD(33304, "初始登录密码不能为空"),

    MW_OPRATOR_ADMINCUSTID(33305, "管理员客户号不能为空"),

    MW_OPRATOR_OPTCUSTID(33306, "操作员客户号不能为空"),

    MW_GROUP_GROUPDESC(33307, "用户组描述不能为空"),

    MW_GROUP_GROUPNAME(33308, "用户组名称不能为空"),

    MW_GROUP_GROUPID(33309, "用户组编号不能为空"),

    MW_ROLE_ROLEDESC(33310, "角色描述不能为空"),

    MW_ROLE_ROLENAME(33311, "角色名称不能为空"),

    MW_ROLE_ROLEID(33312, "角色编号不能为空"),

    MW_PERMISSION_POWERID(33313, "权限不能为空"),

    MW_BANKINFO_ACCID_INVALID(33314, "银行账户编号不能为空"),

    MW_ENT_FILING_LEGALNO_TYPE_INVALID(33315, "法人代表证件类型不能为空"),

    MW_OPRATOR_HSNOBIND(33316, "要绑定的互生号不能为空"),
    
    MW_OPRATOR_MOBILE(33317,"手机号码不能为空"),
    
    MW_OPRATOR_REALNAME(33318,"用户姓名不能为空"),
    
    MW_OPRATOR_ACCOUNTSTATUS(33319,"用户状态不能为空"),

    /************** the end *****************/

    // 刷卡工具制作
    /************** 地区平台 34001-36999 *********************/
    APS_SKGJZZ_CKZDBH_CONFNO(34501, "配置单编号不能为空"),

    APS_SKGJZZ_GLXLH_OPERNO(34502, "操作人不能为空"),

    APS_SKGJZZ_GLXLH_ENTCUSTID(34503, "客户编号不能为空"),

    APS_SKGJZZ_GLXLH_SEQNO(34504, "序列号不能为空"),

    APS_HSKZZ_DOUBLESIGN_USERNAME(34505, "复核用户名不能为空"),

    APS_HSKZZ_DOUBLESIGN_PASSWORD(34506, "复核密码不能为空"),

    APS_HSKZZ_DOUBLESIGN_ENTRESNO(34507, "获取企业互生号失败"),

    APS_HSKZZ_DOUBLESIGN_STYLES(34508, "未选择卡样"),

    APS_HSKZZ_DOUBLESIGN_SUPPLEYS(34509, "未选择供应商"),

    APS_GJPSGL_SELECTTOOLNAME(34510, "请选择工具名称"),

    APS_GJPSGL_SELECTPSTYPE(34511, "请选择配送物品类型"),

    APS_GJPSGL_PSNUM(34512, "请输入配送数量"),

    APS_GJPSGL_PSNumISNUM(34513, "配送数量请输入有效数字"),

    APS_GJPSGL_PSNUMLESS(34514, "配送数量不能小于{0}"),

    APS_GJPSGL_PSNUMMAX(34515, "配送数量不能大于库存数量"),

    APS_GJPSGL_PSMODE(34516, "请选择配送方式"),

    APS_GJPSGL_SHIPPINGFEE(34517, "请输入运费"),

    APS_GJPSGL_SHIPPINGFEEISNUM(34518, "运费请输入有效数字"),

    APS_GJPSGL_TRACKINGNO(34519, "请输入货运单号"),

    APS_GJPSGL_RECEIVERINFO(34520, "请选择收件信息"),

    APS_GJPSGL_PSQDLBLACK(34521, "配送清单信息未填写完整"),

    APS_GJPSGL_SHIPPINGTYPE(34522, "发货单类型不能为空"),

    APS_GJPSGL_HSRESNO(34523, "企业互生号不能为空"),

    APS_GJPSGL_CUSTTYPE(34524, "客户类型不能为空"),

    APS_GJPSGL_RECEIVER(34525, "收件人不能为空"),

    APS_GJPSGL_RECEIVERADDR(34526, "收件地址不能为空"),

    APS_GJPSGL_RECEIVERMOBILE(34527, "收件人电话不能为空"),

    APS_GJPSGL_SMNAME(34528, "配送方式不能为空"),

    APS_GJPSGL_CONSIGNOR(34529, "发货人不能为空"),

    APS_GJPSGL_CONFNOS(34530, "发货人不能为空"),

    /** 实名认证审批 **/
    APS_SMRZSP_ENTCUSTNAME_NOT_NULL(34531, "企业名称不能为空"),

    APS_SMRZSP_ENTCUSTNAMEEN_NOT_NULL(34532, "企业英文名称不能为空"),

    APS_SMRZSP_ENTADDR_NOT_NULL(34533, "企业地址不能为空"),

    APS_SMRZSP_LEGALNAME_NOT_NULL(34534, "法人代表不能为空"),

    APS_SMRZSP_CERTTYPE_NOT_NULL(34535, "法人代表证件类型不能为空"),

    APS_SMRZSP_LEGALCRNO_NOT_NULL(34536, "法人代表证件号码不能为空"),

    APS_SMRZSP_LICENSENO_NOT_NULL(34537, "营业执照号不能为空"),

    APS_SMRZSP_ORGNO_NOT_NULL(34538, "组织机构代码证号不能为空"),

    APS_SMRZSP_TAXNO_NOT_NULL(34539, "纳税人识别号不能为空"),

    APS_SMRZSP_LRCFACEPIC_NOT_NULL(34540, "法人代表证件正面图片不能为空"),

    APS_SMRZSP_LRCBACKPIC_NOT_NULL(34541, "法人代表证件反面图片不能为空"),

    APS_SMRZSP_LICENSEPIC_NOT_NULL(34542, "营业执照扫描件不能为空"),

    APS_SMRZSP_ORGPIC_NOT_NULL(34543, "组织机构代码证扫描件不能为空"),

    APS_SMRZSP_TAXPIC_NOT_NULL(34544, "税务登记证扫描件不能为空"),

    APS_SMRZSP_CERTIFICTPIC_NOT_NULL(34545, "授权委托书不能为空"),

    APS_SMRZSP_APPLYID_NOT_NULL(34546, "申请编号不能为空"),

    APS_SMRZSP_ENTRESNO_NOT_NULL(34547, "企业互生号不能为空"),

    APS_SMRZSP_ENTCUSTID_NOT_NULL(34548, "企业客户号不能为空"),

    APS_SMRZSP_CUSTTYPE_NOT_NULL(34549, "客户类型不能为空"),

    APS_SMRZSP_LINKMAN_NOT_NULL(34550, "联系人不能为空"),

    APS_SMRZSP_MOBILE_NOT_NULL(34551, "联系人手机号不能为空"),

    APS_SMRZSP_APPRVEL_NOT_NULL(34552, "审批结果不能为空"),

    APS_SMRZSP_NAME_NOT_NULL(34553, "姓名不能为空"),

    APS_SMRZSP_SEX_NOT_NULL(34554, "性别不能为空"),

    APS_SMRZSP_COUNTRYNO_NOT_NULL(34555, "国籍不能为空"),

    APS_SMRZSP_NATION_NOT_NULL(34556, "民族不能为空"),

    APS_SMRZSP_BIRTHADDR_NOT_NULL(34557, "户籍地址不能为空"),

    APS_SMRZSP_LICENCEISSUING_NOT_NULL(34558, "发证机关不能为空"),

    APS_SMRZSP_CERTTYP_NOT_NULL(34559, "证件类型不能为空"),

    APS_SMRZSP_CREDENTIALSNO_NOT_NULL(34560, "证件号码不能为空"),

    APS_SMRZSP_CERPICA_NOT_NULL(34561, "证件正面照不能为空"),

    APS_SMRZSP_CERPICB_NOT_NULL(34562, "证件背面照不能为空"),

    APS_SMRZSP_CERPICH_NOT_NULL(34563, "手持证件照不能为空"),

    APS_SMRZSP_VALIDATE_NOT_NULL(34564, "证件有效期不能为空"),

    APS_SMRZSP_OPTCUSTID_NOT_NULL(34565, "操作员客户号不能为空"),

    APS_SMRZSP_OPTNAME_NOT_NULL(34566, "操作员名字不能为空"),

    APS_SMRZSP_OPTENTNAME_NOT_NULL(34567, "操作员所属公司名称/个人消费者名称不能为空"),

    APS_SMRZSP_DBLOPTCUSTID_NOT_NULL(34568, "双签操作员客户号不能为空"),

    APS_IMPORTANT_CHANGITEM_NOT_NULL(34569, "变更项不能为空"),

    APS_SMRZSP_ISPASS_NOT_NULL(34570, "实名认证结果不能为空"),

    /** 配置单号不能为空 */
    APS_CONFNO_NOT_NULL(34004, "配置单号不能为空"),
    /** 设备序列号不能为空 */
    APS_DEVICE_SEQNO_NOT_NULL(34005, "设备序列号不能为空"),
    /** 企业客户号不能为空 */
    APS_ENTRESNO_NOT_NULL(34006, "企业客户号不能为空"),
    /** 设备编号不能为空 */
    APS_DEVICENO_NOT_NULL(34007, "设备编号不能为空"),
    /** 机器码编号不能为空 */
    APS_MACHINENO_NOT_NULL(34008, "机器码编号不能为空"),
    /** 操作者客户号不能为空 */
    APS_OPERCUSTID_NOT_NULL(34009, "操作者客户号不能为空"),
    /** 工具类别不能为空 */
    APS_CATEGORYCODE_NOT_NLL(34010, "工具类别不能为空"),
    /** 设备状态不能为空 */
    APS_STATUS_NOT_NULL(34011, "设备状态不能为空"),
    /** 终端编号不能为空 */
    APS_TERMINALNO_NOT_NULL(34012, "终端编号不能为空"),
    /** 币种码不能为空 */
    APS_CURRENCY_CODE_NOT_NULL(34013, "币种码不能为空"),

    /** 无申请配额数据 */
    APS_NOT_APPLY_DATA(34014, " 无申请配额数据"),
    /** 省代码不能为空 */
    APS_PRO_CODE_NOT_NULL(34015, "省代码不能为空"),
    /** 申请类型不能为空 */
    APS_APPLY_TYPE_NOT_NULL(34016, "申请类型不能为空"),
    /** 申请数量不能为空 */
    APS_APPLY_NUM_NOT_NULL(34017, "申请数量不能为空"),
    /** 业务申请编号不能为空 */
    APS_APPLYID_NOT_NULL(34018, "业务申请编号不能为空"),
    /** 批复数量不能为空 */
    APS_APPNUM_NOT_NULL(34019, "批复数量不能为空"),
    /** 审核状态不能为空 */
    APS_APPLY_STATUS_NOT_NULL(34020, "审核状态不能为空"),
    /** 企业名称不能为空 */
    APS_ENTRES_NAME_NOT_NULL(34021, "企业名称不能为空"),
    /** 申请数量必须大于0 */
    APS_APPLY_NUM_NOT_LESS(34022, "申请数量必须大于0"),
    /** 城市代码不能为空 */
    APS_CITY_CODE_NOT_NULL(34023, "城市代码不能为空"),

    /** 订单号不能为空 */
    APS_ORDERNO_NOT_NULL(34024, "订单号不能为空"),
    /** 复核帐号不能为空 */
    APS_CHECK_ACCOUNT_NOT_NULL(34025, "复核帐号不能为空"),
    /** 复核密码不能为空 */
    APS_CHECK_PWD_NOT_NULL(34026, "复核密码不能为空"),
    /** 复核意见不能为空 */
    APS_CHECK_VIEWS_NOT_NULL(34027, "复核意见不能为空"),
    /** 城市人口数不能为空 */
    APS_POPULATION_NOT_NULL(34028, "城市人口数不能为空"),

    // 系统 安全
    APS_SAFE_SET_NO_EMPTY_OLD_PWD(343001, "旧密码不能为空"),

    APS_SAFE_SET_NO_EMPTY_NEW_PWD(343002, "新密码不能为空"),

    // 系统用户

    APS_SYS_USER_ROLE_PLAT_CODE_NO_EMPTY(344000, "平台代码不能为空"),

    APS_SYS_USER_ROLE_NAME_NO_EMPTY(344001, "角色名不能为空"),

    APS_SYS_USER_ROLE_ID_CODE_NO_EMPTY(344002, "角色ID不能为空"),

    APS_SYS_USER_PERM_ID_EMPTY(344003, "权限ID不能为空"),

    APS_SYS_USER_GROUP_ID_CODE_NO_EMPTY(344004, "用户组ID不能为空"),

    APS_SYS_USER_OPER_ID_EMPTY(344005, "操作员ID不能为空"),

    APS_SYS_USER_GROUP_NAME_EMPTY(344006, "用户组名不能为空"),

    APS_SYS_USER_OPER_NAME_EMPTY(344007, "用户组名不能为空"),

    APS_SYS_USER_OP_PWD_NAME_EMPTY(344008, "用户组名不能为空"),

    APS_USER_PWD_USER_NO_EXIST(160102, "用户不存在"),

    APS_USER_PWD_USER_PWD_ERROR(160109, "旧登录密码不正确"),

    /******************** 临账管理 35000-35999 start *************/
    APS_DEBIT_ACCOUNT_STATUS_TYPE_ERROR(35001, "帐户类型参数错误"),

    APS_DEBIT_ACCOUNT_STATUS_TYPE_EMPTY(35002, "帐户类型参数不能为空"),

    APS_DEBIT_ACCOUNT_NAME_EMPTY(35003, "收款账户名称不能为空"),

    APS_DEBIT_ACCOUNT_APP_NAME_EMPTY(35004, "收款账户简称不能为空"),

    APS_DEBIT_RECEIVE_ACCOUNT_ID_EMPTY(35005, "账户户名编号不能为空"),

    APS_DEBIT_BANK_BRANCH_NAME_EMPTY(35006, "收款开户行名称不能为空"),

    APS_DEBIT_RECEIVE_ACCOUNT_NO_EMPTY(35007, "收款账号不能为空"),

    APS_DEBIT_BANK_NAME_EMPTY(35008, "银行名称不能为空"),

    APS_DEBIT_BANK_ID_EMPTY(35009, "银行ID不能为空"),

    APS_DEBIT_PAY_AMOUNT_EMPTY(35010, "付款金额不能为空"),

    APS_DEBIT_PAY_TYPE_EMPTY(35011, "付款方式不能为空"),

    APS_DEBIT_PAY_TYPE_ERROR(35012, "付款方式不匹配"),

    APS_DEBIT_PAY_ENT_CUST_NAME_EMPTY(35013, "企业名称不能为空"),

    APS_DEBIT_PAY_BANK_NAME_EMPTY(35014, "付款银行名称不能为空"),

    APS_DEBIT_PAY_BANK_NO_EMPTY(35015, "付款银行账号不能为空"),

    APS_DEBIT_ACCOUNT_INFO_ID_EMPTY(35016, "收款账户信息id不能为空"),

    APS_DEBIT_ACCOUNT_RECEIVETIME_EMPTY(35017, "到账时间不能为空"),

    APS_DEBIT_CREATE_BY_EMPTY(35018, "创建人不能为空"),

    APS_DEBIT_PAY_TIME_EMPTY(35019, "付款时间不能为空"),

    APS_DEBIT_DEBIT_ID_EMPTY(35020, "临账ID不能为空"),

    APS_DEBIT_AUDIT_RECORD_EMPTY(35021, "复核/驳回备注"),

    APS_DEBIT_DEBIT_STATUS_EMPTY(35022, "临帐状态"),

    APS_DEBIT_UPDATE_BY_EMPTY(35023, "审批人不能为空"),

    APS_DEBIT_ORDER_NO_EMPTY(35024, "订单号不能为空"),

    APS_DEBIT_TOTAL_LINK_AMOUNT_EMPTY(35025, "关联总额不能为空"),

    APS_DEBIT_CASH_AMOUNT_EMPTY(35026, "订单金额不能为空"),

    APS_DEBIT_REQ_REMARK_EMPTY(35027, "备注不能为空"),

    APS_DEBIT_PAY_USE_EMPTY(35028, "付款用途不能为空"),

    APS_DEBIT_REFUND_AMOUNT_EMPTY(35029, "退款金额不能为空"),

    APS_DEBIT_DEBIT_IDS_EMPTY(35030, "至少选择一个临账"),

    APS_DEBIT_LINK_AMOUNTS_EMPTY(35031, "至少选择一个临账金额"),

    APS_DEBIT_BALANCE_RECORD_EMPTY(35032, "转不动款说明不能为空"),

    APS_DEBIT_APPR_REMARK_EMPTY(35033, "复核备注不能为空"),

    APS_DEBIT_DBL_OPT_CUSTID_EMPTY(35034, "复核员账号不能为空"),

    APS_DEBIT_PASSWORD_EMPTY(35035, "复核人密码不能为空"),

    APS_DEBIT_VERIFICATION_MODE_EMPTY(35036, "请选择一种验证方式"),

    /******************** 临账管理 35000-35100 end *************/

    /******************** 发票管理 35100-35999 start *************/
    APS_TAXRATECHANGE_APPLY_ID_EMPTY(35100, "税率申请编号不能为空"),

    APS_TAXRATECHANGE_STATUS_EMPTY(35101, "税率申请状态不能为空"),

    APS_TAXRATECHANGE_APPLEY_REASON_EMPTY(35102, "审批意见不能为空"),

    APS_TAXRATECHANGE_UPDATE_BY_EMPTY(35103, "审批人不能为空"),

    APS_TAXRATECHANGE_CUST_ID_EMPTY(35104, "企业客户号不能为空"),

    APS_INVOICE_RES_NO_EMPTY(35105, "企业互生号不能为空"),

    APS_INVOICE_CUST_NAME_EMPTY(35106, "企业名称不能为空"),

    APS_INVOICE_ALL_AMOUNT_EMPTY(35107, "发票金额不能为空"),

    APS_INVOICE_BANK_BRANCH_NAME_EMPTY(35108, "银行支行名称不能为空"),

    APS_INVOICE_BANK_NO_EMPTY(35109, "银行账号不能为空"),

    APS_INVOICE_ADDRESS_EMPTY(35110, "地址不能为空"),

    APS_INVOICE_TELEPHONE_EMPTY(35111, "电话不能为空"),

    APS_INVOICE_TAX_NO_EMPTY(35112, "纳税人识别号不能为空"),

    APS_INVOICE_OPEN_CONTENT_EMPTY(35113, "开票内容不能为空"),

    APS_INVOICE_BIZ_TYPE_EMPTY(35114, "业务类型不能为空"),

    APS_INVOICE_EXPRESS_NAME_EMPTY(35115, "快递公司名称不能为空"),

    APS_INVOICE_TRANKING_NO_EMPTY(35116, "运单号不能为空"),

    APS_INVOICE_INVOICE_ID_EMPTY(35117, "发票ID不能为空"),

    APS_INVOICE_STATUS_EMPTY(35118, "发票状态不能为空"),

    APS_INVOICE_INVOICE_MAKER_EMPTY(35119, "开票方不能为空"),

    APS_INVOICE_AMOUNT_LARGE_EMPTY(35120, "开票金额太大"),

    APS_INVOICE_AMOUNT_NOT_EQUAL_EMPTY(35121, "必须一次性开完发票"),

    APS_INVOICE_LIST_NULL_EMPTY(35122, "至少录入一条发票记录"),

    APS_INVOICE_TYPE_NULL_EMPTY(35123, "发票类型不能为空"),

    /******************** 发票管理 35100-35999 end *************/

    /******************** 企业申报管理 36000-36199 sta *************/
    APS_INTENTION_CUST_APPLYID_INVALID(36000, "意向客户申请编号不能为空"),

    APS_INTENTION_CUST_INVALID(36001, "未找到对应客户申请资料"),

    APS_APPLYID_INVALID(36002, "申请编号不能为空"),

    APS_ENT_FILING_DETAIL_INVALID(36003, "未找到对应的异议报备详情"),

    APS_APPR_STATUS_INVALID(36004, "审核状态不能为空"),

    APS_APPR_REMARK_LENGTH_INVALID(36005, "审核说明应为0~300个字符"),

    APS_REMARK_LENGTH_INVALID(36006, "备注说明应为0~300个字符"),

    APS_ENT_FILING_POSI_PIC_FILEID_INVALID(36007, "法人代表证件正面文件不能为空"),

    APS_ENT_FILING_BACK_PIC_FILEID_INVALID(36008, "法人代表证件反面文件不能为空"),

    APS_ENT_FILING_LICE_PIC_FILEID_INVALID(36009, "营业执照扫描件文件不能为空"),

    APS_DECLARE_ORG_PIC_FILEID_INVALID(36010, "组织机构代码证扫描件不能为空"),

    APS_DECLARE_TAX_PIC_FILEID_INVALID(36011, "税务登记证扫描件不能为空"),

    APS_DECLARE_PRO_PIC_FILEID_INVALID(36012, "创业帮扶协议不能为空"),

    APS_VIEW_MARK_LENGTH_INVALID(36013, "备注应为0~300个字符"),

    APS_ENT_LINKINFO_LINKMAN_INVALID(36014, "'联系人不能为空"),

    APS_ENT_LINKINFO_LINKMAN_LENGTH_INVALID(36015, "联系人应为2~20个字符"),

    APS_ENT_LINKINFO_ADDRESS_LENGTH_INVALID(36016, "联系地址应为2~128个字符"),

    APS_ENT_LINKINFO_MOBILE_INVALID(36017, "联系人手机不能为空"),

    APS_MOBILE_FORM_INVALID(36018, "联系人手机格式不正确"),

    APS_ZIPCODE_FORM_INVALID(36019, "邮政编码格式不正确"),

    APS_EMAIL_FORM_INVALID(36020, "邮箱格式不正确"),

    APS_ENT_LINKINFO_CERT_INVALID(36021, "联系人授权委托书不能为空"),

    APS_ENT_LINKINFO_OBJ_INVALID(36022, "企业联系信息不能为空"),

    APS_ENT_FILING_ENTCUSTNAME_INVALID(36023, "企业名称不能为空"),

    APS_ENT_FILING_ENTCUSTNAME_LENGTH_INVALID(36024, "企业名称应为2~128个字符"),

    APS_ENT_FILING_LEGALNAME_INVALID(36025, "企业法人代表不能为空"),

    APS_ENT_FILING_LEGALNAME_LENGTH_INVALID(36026, "企业法人代表应为2~20个字符"),

    APS_BIZREG_ENTTYPE_LENGTH_INVALID(36027, "企业类型应为2-20个字符"),

    APS_ORGNO_INVALID(36028, "组织机构代码证号不能为空"),

    APS_BIZREG_ESTADATE_FORM_INVALID(36029, "成立日期格式不正确"),

    APS_ENT_FILING_ENT_ADDRESS_INVALID(36030, "企业地址不能为空"),

    APS_ENT_FILING_ENT_ADDRESS_LENGTH_INVALID(36031, "企业地址应为2~128个字符"),

    APS_ENT_FILING_LICENSENO_INVALID(36032, "营业执照号不能为空"),

    APS_LICENSENO_INVALID(36033, "营业执照号格式不正确"),

    APS_BIZREG_MOBILE_FORM_INVALID(36034, "联系电话格式不正确"),

    APS_TAXNO_INVALID(36035, "纳税人识别号不能为空"),

    APS_ENT_FILING_DEALAREA_LENGTH_INVALID(36036, "企业经营范围应为0~300个字符"),

    APS_BIZREG_LIMITDATE_LENGTH_INVALID(36037, "营业期限应为0~50个字符"),

    APS_IDCARD_FORM_INVALID(36038, "身份证格式不正确"),

    APS_PASSPORT_FORM_INVALID(36039, "护照格式不正确"),

    APS_LEGALNO_INVALID(36040, "法人证件号码不能为空"),

    APS_BIZREG_INVALID(36041, "工商登记信息不能为空"),

    APS_BANKNAME_INVALID(36042, "账户名称不能为空"),

    APS_CUR_CODE_INVALID(36043, "结算币种不能为空"),

    APS_BANK_FORM_INVALID(36044, "银行卡号格式不正确"),

    APS_BANKINFO_TWO_TIMES_INVALID(36045, "两次账号输入不一致"),

    APS_BANKINFO_INVALID(36046, "银行账户信息不能为空"),

    APS_DOULBE_USERNAME_INVALID(36047, "复核员用户名不能为空"),

    APS_DOULBE_PASSWORD_INVALID(36048, "复核员登陆密码不能为空"),

    APS_REGINFO_TOCUSTTYPE_INVALID(36049, "申报类别不能为空"),

    APS_REGINFO_TOBUY_RESRANGE_INVALID(36050, "启用资源类型不能为空"),

    APS_REGINFO_ENTNAME_INVALID(36051, "企业中文名称不能为空"),

    APS_REGINFO_ENTNAME_LENGTH_INVALID(36052, "企业中文名称应为2~128个字符"),

    APS_REGINFO_ENTENNAME_LENGTH_INVALID(36053, "企业英文名称应为2~128个字符"),

    APS_REGINFO_TOENTRESNO_INVALID(36054, "拟用企业互生号不能为空"),

    APS_REGINFO_PROVINCENO_INVALID(36055, "所属地区省份不能为空"),

    APS_REGINFO_CITY_INVALID(36056, "所属地区城市不能为空"),

    APS_FWGS_RESNO_INVALID(36057, "服务公司互生号不能为空"),

    APS_REGINFO_COUNTRYNO_INVALID(36058, "所属地区国家不能为空"),

    APS_REGINFO_INVALID(36059, "企业系统注册信息不能为空"),

    APS_RESNO_FORM_INVALID(36060, "互生号格式不正确"),

    APS_DEC_RESNO_NOT_FOUND(36061, "申报单位互生号不存在"),

    APS_REGINFO_NODE_INVALID(36062, "未找到对应的企业增值信息"),

    APS_REGINFO_NOT_FOUND(36063, "未找到对应的系统注册信息"),

    APS_OPENSYSTEM_STATUS_INVALID(36064, "审核结果不能为空"),

    APS_OPENSYSTEM_APPR_LENGTH_INVALID(36065, "审核意见应为0~300个字符"),

    APS_DEC_RESNO_INVALID(36066, "申报单位互生号不能为空"),

    APS_DEC_RESNOINFO_NOT_FOUND(36067, "未找到对应的申报单位信息"),

    APS_REGINFO_TOMRESNO_INVALID(36068, "所属管理公司不能为空"),

    APS_PNODE_CUSTID_INVALID(36069, "被申报增值节点父节点客户号不能为空"),

    APS_PNODE_RESNO_INVALID(36070, "被申报增值节点父节点资源号不能为空"),

    APS_INODE_RESON_INVALID(36071, "被申报选择增值节点不能为空"),

    APS_INODE_LORR_INVALID(36072, "被申报选择增值节点对应区域不能为空"),

    APS_DEBTSYSTEM_STATUS_INVALID(36073, "开启系统欠费审核标志不能为空"),

    APS_ENT_FILING_SHAREHOLDER_INVALID(36074, "股东信息不能为空"),

    APS_ENT_FILING_APPLYID_INVALID(36075, "企业申请编号不能为空"),

    APS_ENT_FILING_OPTCUSTID_INVALID(36076, "操作员客户号不能为空"),

    APS_ENT_FILING_SHAREHOLDER_SHNAME_INVALID(36077, "股东名称不能为空"),

    APS_ENT_FILING_SHAREHOLDER_SHTYPE_INVALID(36078, "股东性质不能为空"),

    APS_ENT_FILING_SHAREHOLDER_IDTYPE_INVALID(36079, "证件类型不能为空"),

    APS_ENT_FILING_SHAREHOLDER_IDNO_INVALID(36080, "证件号码不能为空"),

    APS_ENT_FILING_SHAREHOLDER_PHONE_INVALID(36081, "联系电话不能为空"),

    APS_ENT_FILING_SHAREHOLDERID_INVALID(36082, "股东编号不能为空"),

    APS_ENT_FILING_BANK_PIC_FILEID_INVALID(36083, "银行资金证明文件不能为空"),

    APS_ENT_FILING_SHARE_PIC_FILEID_INVALID(36084, "合作股东证明文件文件不能为空"),

    APS_ENT_FILING_BASEINFO_INVALID(36085, "未找到对应的企业基本信息"),

    APS_ENT_FILING_OBJECT_INVALID(36086, "报备企业不能为空"),

    APS_ENT_FILING_LEGALTYPE_INVALID(36087, "证件类型不能为空"),

    APS_ENT_FILING_LINKEMAN_INVALID(36088, "联系人姓名不能为空"),

    APS_ENT_FILING_LEGALNO_INVALID(36089, "法人证件号码不能为空"),

    APS_ENT_FILING_PHONE_INVALID(36090, "联系人手机不能为空"),

    APS_ENT_FILING_APPRREMARK_LENGTH_INVALID(36091, "报备说明应为0~300个字符"),

    APS_ENT_FILING_LINKEMAN_LENGTH_INVALID(36092, "联系人姓名应为2~20个字符"),

    APS_ENT_FILING_ENTTYPE_LENGTH_INVALID(36093, "企业性质应为2~20个字符"),

    APS_PROVINCENO_INVALID(36094, "所属省份不能为空"),

    APS_CITYNO_INVALID(36095, "所属城市不能为空"),

    APS_LICENSENO_FORM_INVALID(36096, "营业执照号格式不正确"),

    APS_ENT_FILING_SHAREHOLDER_SHNAME_LENGTHINVALID(36097, "股东名称应为2~20个字符"),

    /******************** 企业申报管理 36000-36199 end *************/

    /******************** 工具后台管理 36200-36300 sta *************/

    APS_WHI_DOULBE_USERNAME_INVALID(36200, "复核员用户名不能为空"),

    APS_WHI_DOULBE_PASSWORD_INVALID(36201, "复核员登陆密码不能为空"),

    APS_WHI_CATEGORYCODE_INVALID(36202, "工具类别不能为空"),

    APS_WHI_PRODUCTID_INVALID(36203, "工具名称不能为空"),

    APS_WHI_SUPPLIERID_INVALID(36204, "供应商不能为空"),

    APS_WHI_WHID_INVALID(36205, "仓库不能为空"),

    APS_WHI_DEVICESEQNOS_INVALID(36206, "批次号不能为空"),

    APS_WHI_ENTERBATCH_INVALID(36207, "机器码文件不能为空"),

    APS_WHI_QUANTITY_NUMBER_INVALID(36208, "入库数量不是有效的数字"),

    APS_WHI_PURCHASE_PRICE_NUMBER_INVALID(36209, "进货价不是有效的数字"),

    APS_WHI_MARKET_PRICE_NUMBER_INVALID(36210, "市场价不是有效的数字"),

    APS_LOG_SMNAME_INVALID(36211, "配送方式名称不能为空"),

    APS_LOG_SORT_INVALID(36212, "排序值不能为空"),

    APS_LOG_SMID_INVALID(36213, "配送方式编号不能为空"),

    APS_LOG_SMNAME_LENGTH_INVALID(36214, "配送方式名称长度不能超过64个字"),

    APS_LOG_SMDESC_LENGTH_INVALID(36215, "配送说明长度不能超过200个字"),

    /******************** 工具后台管理 36200-36300 end *************/

    /******************** 工单管理 36301-36350 end *************/
    /** 工单号不能为空 */
    APS_BIZNO_NOT_NULL(36302, "工单号不能为空"),
    /** 原因信息不能为空 */
    APS_REASON_NOT_NULL(36303, "原因信息不能为空"),
    /** 派单模式不能为空 */
    APS_ASSIGNEDMODE_NOT_NULL(36304, "派单模式不能为空"),
    /** 指定值班员不能为空 */
    APS_OPTCUST_NOT_NULL(36305, "指定值班员不能为空"),
    /** 工单编号不能为空 */
    APS_SYSNO_NOT_NULL(36306, "工单编号不能为空"),
    /** 值班组名称不能为空 */
    APS_GROUP_NAME_NOT_NULL(36307, "值班组名称不能为空"),
    /** 值班组类型不能为空 */
    APS_GROUP_TYPE_NOT_NULL(36308, "值班组类型不能为空"),
    /** 组员不能为空 */
    APS_MEMBER_NOT_NULL(36309, "组员不能为空 "),
    /** 请选择值班主任 */
    APS_SELECT_DUTY_OFFICER(36310, "请选择值班主任 "),
    /** 值班组编号不能为空 */
    APS_GROUP_ID_NOT_NULL(36311, "值班组编号不能为空"),
    /** 值班员编号不能为空 */
    APS_OPTCUSTID_NOT_NULL(36312, "值班员编号不能为空"),
    /** 请选择值班员业务类型 */
    APS_SELECT_MEMBER_BIZTYPE(36313, "请选择值班员业务类型"),
    /** 值班计划编号不能为空 */
    APS_SCHEDULEID_NOT_NULL(36314, "值班计划编号不能为空"),
    /** 开关状态不能为空 */
    APS_OPEND_STATUS_NOT_NULL(36315, "开关状态不能为空"),
    /** 请选择年份 */
    APS_SELECT_YEAR(36316, "请选择年份"),
    /** 请选择月份 */
    APS_SELECT_MONTH(36317, "请选择月份"),
    /** 值班计划不能为空 */
    APS_SCHEDULE_NOT_NULL(36318, "值班计划不能为空"),
    /** 值班计划下载失败 */
    APS_SCHEDULE_DOWNLOAD_FAIL(36319, "值班计划下载失败"),

    /************** 工单管理 the end *****************/

    /******************** 平台信息 36351-36400 sta *************/

    APS_BIZREG_ENTTYPE_INVALID(36351, "企业类型不能为空"),

    APS_BANKINFO_BANKCODE_INVALID(36352, "开户行不能为空"),

    APS_BANKINFO_ACCID_INVALID(36353, "银行卡编号不能为空"),

    APS_BIZREG_ESTADATE_INVALID(36354, "成立日期不能为空"),

    APS_ENT_LINKINFO_EMAIL_FORM_INVALID(36355, "企业安全邮箱格式不正确"),

    APS_CONFIRM_BANK_INVALID(36356, "确认账户不能为空"),

    /******************** 平台信息 36351-36400 sta *************/
    
    /******************** 合同、证书 36401-36500 start *************/
	ASP_CONTRACT_ENTRESNO_INVALID(36401, "企业互生号不能为空"),

	ASP_CONTRACT_ENTCUSTID_INVALID(36402, "企业客户号不能为空"),

	ASP_CONTRACT_ENTCUSTNAME_INVALID(36403, "企业客户名称不能为空"),

	ASP_CONTRACT_ENTCUSTTYPE_INVALID(36404, "企业客户类型不能为空"),

	ASP_CONTRACT_RESTYPE_INVALID(36405, "启用资源类型不能为空"),

	ASP_CONTRACT_OPTCUSTID_INVALID(36406, "操作员客户号不能为空"),

	ASP_CONTRACTNO_INVALID(36407, "合同编号不能为空"),

	ASP_DOC_UNIQUENO_INVALID(36408, "唯一识别号不能为空"),

	ASP_DOC_TEMPNAME_INVALID(36409, "模板名称不能为空"),

	ASP_DOC_TEMPCONTENT_INVALID(36410, "模板内容不能为空"),

	ASP_DOC_TEMP_ID_INVALID(36411, "模板编号不能为空"),

	ASP_DOC_TEMP_APPRSTATUS_INVALID(36412, "审批结果不能为空"),

	ASP_CERTIFICATENO_INVALID(36413, "证书编号不能为空"),

	/******************** 合同、证书 36401-36500 end *************/

    /************************* 报表系统 39000-39999 end ***************************/

    /** 参数为空 **/
    RP_PARAMETER_NULL(39000,"参数为空"),
    /** 重复数据 **/
    RP_REPEATED_DATA(39001,"重复数据"),
    /** 参数格式错误 **/
    RP_PARAMETER_FORMAT_ERROR(39002,"参数格式错误"),
    /** 数据库异常 **/
    RP_SQL_ERROR(39003,"数据库异常"),
    /** IO流异常 **/
    RP_IO_ERROR(39004,"IO流异常"),
    /** 线程中断异常 **/
    RP_INTERRUPTED(39005,"线程中断异常"),
    /** 文件读写异常 **/
    RP_FILE(39006,"文件读写异常"),
    /** 文件导出最大数量异常 **/    
    RP_EXPORT_MAX_NUM(39007,"文件导出最大数量异常"),

    /************************* 报表参数 end ***************************/
    
    
    /************************* 业务参数 ***************************/

    /** 参数为空 **/
    BP_PARAMETER_NULL(44000,"参数为空"),
    /** 重复数据 **/
    BP_REPEATED_DATA(44001,"重复数据"),
    /** 参数格式错误 **/
    BP_PARAMETER_FORMAT_ERROR(44002,"参数格式错误"),
    /** 数据库异常 **/
    BP_SQL_ERROR(44003,"数据库异常"),
    /** REDIS和数据库不存在该数据 **/
    BP_RESULT_EMPTY(44004,"REDIS和数据库不存在该数据"),

    /************************* 业务参数 end ***************************/

    // 结束符
    END(00000);

    // 响应代码
    private int code;

    // 相应描述信息
    private String desc;

    RespCode(int code) {
        this.code = code;
    }

    RespCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return this.desc;
    }

    /**
     * 根据响应码反过来获取枚举对象
     * 
     * @param code
     * @return
     */
    public static RespCode valueOf(int code) {
        for (RespCode item : RespCode.values())
        {
            if (item.getCode() == code)
            {
                return item;
            }
        }
        return null;
    }

    public static void main(String[] args)

    {
        /**
         * 检查代码是否有重复
         */
        Set<Integer> codeSet = new HashSet<Integer>();
        for (RespCode item : RespCode.values())
        {
            if (codeSet.contains(item.getCode()))
            {
                System.err.println(item.name() + ":" + item.getCode() + " 代码有重复冲突，请修改！");
            }
            else
            {
                codeSet.add(item.getCode());
            }
        }
    }
}
