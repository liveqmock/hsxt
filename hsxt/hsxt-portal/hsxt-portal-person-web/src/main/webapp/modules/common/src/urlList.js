define(function () {
	comm.UrlList = {
		//查询所有的国家信息	
		"findContryAll" : "commController/find_contry_all",
		
		//根据国家查询下属省份	
		"findProvinceByParent" : "commController/find_province_by_parent",	
		
		//查询所有的银行列表
		"findBankAll" : "commController/findBankAll",
		
		//获取本平台基本信息	
		"findSystemInfo" : "commController/findSystemInfo",
		
		//根据国家省份查询下级城市
		"findCityByParent" : "commController/find_city_by_parent",	
		
		//公共部分-根据国家代码-省份代码-城市代码获取对应名称
		"getRegionByCode" : "commController/getRegionByCode",
		
		//公共部分-根据国家代码获取省份城市信息
		"findProvCity" : "commController/findProvCity",
		
		//公共部分-查询快捷支付银行列表
		"findPayBankAll" : "commController/findPayBankAll",
		
		//公共部分-获取示例图片、常用业务文档、办理业务文档列表
		"findDocList" : "commController/findDocList",
		
		//验证验证码
		"verificationCode" : "commController/verificationCode",
		
		//退出
		"loginOff" : "login/loginOff.do",
		
		//加载缓存数据
		"loadCache" : "loadCache.do",
		
		
		//获取用户登录信息
		"getLoginInfo" : "getLoginInfo.do",
		
		//查询预留信息
		"getReservedInfo" : "safe/getSafeReservedInfoByCustNo.do",
		

		//查询预留信息
		"getReservedInfo" : "safe/getSafeReservedInfoByCustNo.do",

		//获取验证码
		"generateSecuritCode" : "commController/generateSecuritCode",
		
		/********互生币账户*********/
		"testLogin" : "login/cardholderLogin",
		// 互生币账户查询
		"findHsbBlance" : "hsbAccount/find_hsb_blance",
		//互生币转货币-界面数据初始化
		"initHsbTransferCurrency" : "hsbAccount/init_hsb_transfer_currency", 
		//互生币转货币-数据提交
		"hsbTransferCurrency" : "hsbAccount/hsb_transfer_currency", 
		//兑换互生币-界面数据初始化
		"initTransferHsb" : "hsbAccount/init_transfer_hsb",
		//兑换互生币
		"transferHsb":"hsbAccount/transfer_hsb",
		//*互生币账户-明细查询
		"hsbDetailedPage":"hsbAccount/detailed_page",		
		
		/********货币账户*************/
		//货币账户-余额查询
		"findCurrencyBlance":"currencyAccount/find_currency_blance",
		//货币账户-货币转银行，初始化界面
		"initCurrencyTransferBank":"currencyAccount/init_currency_transfer_bank",
		//货币账户-货币转银行
		"currencyTransferBank" : "currencyAccount/currency_transfer_bank",
		//货币账户-货币转银行手续费
		"getBankTransFee" : "currencyAccount/getBankTransFee",
		
		//货币账户-货币转银行，获取确认页面数据
		"getCashCashConfimInfo" : "cashAccount/confirm_Info",
		
		//货币账户-积分明细查询
		"pointDetailQuery" : "cashAccount/list",
		//货币账户-明细查询
		"cashDetailedPage":"currencyAccount/detailed_page",
	
		
		/******积分账户********/
		//积分账户-积分账户余额查询
		"findIntegralBalance" : "integralAccount/find_integral_balance",
		//积分账户 - 初始化积分转互生币界面 
		"initIntegralTransferHsb" :"integralAccount/init_integral_transfer_Hsb",
		//积分账户 - 初始化积分转互生币界面 
		"integralTransferHsb" :"integralAccount/integral_transfer_Hsb",
		//积分账户 - 积分投资界面初始化信息
		"initIntegralInvestment" :"integralAccount/init_integral_investment",
		//积分账户 - 提交积分投资信息 
		"integralInvestmentInfo" :"integralAccount/integral_investment",
		//积分账户-明细查询
		"findIntegralList" : "integralAccount/list",
		
		/******投资账户********/
		// 投资账户-余额查询
		"findInvestmentBlance" : "investmentAccount/query_investment_blance",
		// 投资账户-积分投资明细查询
		"pointInvestPage" : "investmentAccount/point_invest_page",
		// 投资账户-投资分红明细查询
		"pointDividendPage" : "investmentAccount/point_dividend_page",
		// 投资账户-投资分红明细查询-详情
		"dividendDetailPage" : "investmentAccount/dividend_detail_page",
		// 投资账户-投资分红明细查询-详情统计
		"dividendDetailPageTotal" : "investmentAccount/dividendDetailPageTotal",
		// 投资账户-明细查询
		"pointDetailedPage":"integralAccount/detailed_page",
		
		
		//所有的明细 详情 根据 流水号查详情
		"queryDetailsByTransNo":"integralAccount/queryDetailsByTransNo",
		
		/**我的互生卡 */
		//获取银行列表数据
		"findBankBindList" : "bankCard/findBankBindList",
		//添加绑定银行卡信息
		"addBankBind"	:	"bankCard/add_bank_bind",
		//删除绑定银行卡
		"delBankCard"	:	"bankCard/del_bank_bind",
		
		//查询用户绑定的手机号码
		"findMobileByCustId":	"cardHolder/findMobileByCustId",
		//添加绑定手机号码
		"addBindMobile"		:	"cardHolder/add_bind_mobile",	
		//绑定绑定手机号码
		"editBindMobile"	:	"cardHolder/edit_bind_mobile",
		//发送短信验证码
		"mobileSendCode"	:	"cardHolder/mobileSendCode",
		
		//查询用户绑定的邮件
		"findEamilByCustId":	"cardHolder/findEamilByCustId",
		//添加绑定邮箱
		"addBindEmail"		:	"cardHolder/add_bind_email",	
		//修改绑定邮箱
		"mailModify"		:	"cardHolder/mail_modify",	
		//邮箱验证
		"checkMailCode"		:	"cardHolder/checkEmailCode",
		
		//持卡人登录后获取随机报文
		"findCardHolderToken"	:	"commController/find_card_holder_token",
		
		//获取互生卡挂失状态
		"findHsCardStatusInfoBycustId" : "cardLossReg/findHsCardStatusInfoBycustId",
		//互生卡挂失
		"hscReportLoss" : "cardLossReg/hscReportLoss",
		//互生卡解挂
		"hscSolutionLinked" : "cardLossReg/hscSolutionLinked",
		
		
		//初始化实名注册
		"queryConsumerInfo":	"cardHolder/init_registration",
		//实名注册
		"registration":	"cardHolder/registration",
		
		//初始化实名认证
		"initAuthentication":"cardHolder/init_authentication",
		//实名认证
		"authentication":"cardHolder/authentication",
		
		//添加实名认证信息
		"setVerification"	:	"account/real_name_authentication",
		
		
		
		//获得互生卡补办时的--收件信息
		"getUserAddressList" : "cardReapply/query_user_address",
		//获取 省、市、县（区）列表信息
		"getAreaLists" : "cardReapply/query_area_lists",
		//添加--收件信息
		"addUserAddress" : "cardReapply/add_user_address",
		//获得互生币余额 
		"getUserBalace" : "cardReapply/query_user_balace",
		//查询收货地址 
		"searchAddress" : "cardReapply/search_Address_List",
		//查询收货地址 
		"queryAddressList" : "cardReapply/query_Address_List",
		//增加收货地址 
		"addAddress" : "cardReapply/add_Address",
		//更新收货地址 
		"updateAddress" : "cardReapply/update_Address",
		//删除收货地址 
		"deleteAddress" : "cardReapply/delete_Address",
		"setDefaultAddress" : "cardReapply/set_Default_Addr",
		//补卡下单 
		"mendCardOrder" : "cardReapply/person_Mend_CardOrder",
		// 工具订单支付
		"toolOrderOayment" : "cardReapply/tool_order_payment",
		// 开通快捷支付
		"openQuickPay" : "cardReapply/open_quick_pay",
		// 发送快捷支付短信
		"sendQuickPaySms" : "cardReapply/send_quick_pay_sms",
		
		
		
		//获取用户重要信息变更状态
		"getImportantInfoVerification" : "customerinfo/query_important_status",
		//修改用户重要信息
		"changeUserImportantInfo" : "customerinfo/change_important_status",
		//查询客户id下是否有支付失败或未支付的补卡申请
		"getCardReapplyByCustId" : "cardReapply/query_user_apply",
		//生成补办互生卡订单号信息
		"addReMakeCardOrder" : "cardReapply/add_card_reapply",
		//互生卡补办-在线支付
		"addReMakeCardOrderByPayBtn" : "cardReapply/add_car_reapply_bypaybtn",
		"updateMail" : "cardReapply/updateMail",
		//重要信息变更-初始化界面
		"initPerChange" : "importantInfoChangeController/initPerChange",
		//重要信息变更-创建重要信息变更
		"createPerChange" : "importantInfoChangeController/createPerChange",
		//重要信息变更-修改重要信息变更
		"modifyPerChange" : "importantInfoChangeController/modifyPerChange",
		//快捷支付-查询绑定的快捷支付银行
		"findPayBanksByCustId":"payBankController/findPayBanksByCustId",
		//快捷支付-添加快捷支付银行
		"addPayBank":"payBankController/addPayBank",
		//快捷支付-删除快捷支付银行
		"delPayBank":"payBankController/delPayBank",
		
		/**投资账户 */
		//投资账户余额查询
		"getInvestAcctValue" : "investment/query_balance",
		//积分明细查询
		"IntegralInvestment" : "integralQuery/list",
		//投资分红明细查询
		"findIntegralInformation" : "investment/list",
		//投资分红详情
		"getInvestDividendsDetails" : "investment/query_repay_detail",
			
		
		/****系统服务****/	
		"findSystemLogList" : "systemServiceController/list",
			
		/**  积分福利模块***/
		//查询用户状态
		"getUserStatus"  :  "medicalSubsidyScheme/get_user_status",
	    // 查询用户积分
	    "getUserAccount"  :  "medicalSubsidyScheme/get_user_account",
	    "upload" : "medicalSubsidyScheme/fileUpload",

		/** 安全设置模块 * */
		// 查询是否设置过交易密码及预留信息
		"findSecuritySetType" : "safetySet/findSecuritySetType",
		// 获得所有密保问题
		"getAllPwdQuestion" : "safetySet/find_password_question",
		// 设置密保问题
		"setPwdQuestion" : "safetySet/save_password_question",
		//查询预留信息
		"findReservationInfo" : "safetySet/find_reservation_information",
		// 安全设置预留信息
		"setReserveInfo" : "safetySet/save_reservation_information",
		
		// 设置交易密码
		"setTradePwd" : "safetySet/set_tradePwd",
		//获取密码配置(交易密码,登录密码)
		"initTradePwd" : "safetySet/initTradePwd",
		// 修改交易密码
		"modfiyDealPwd" : "safetySet/modify_del_password",
		// 修改登录密码
		"modfiyLoginPwd" : "safetySet/modify_password",
		
		//得到预留信息
		"getReserveInfo" : "securitySet/getReserveInfo",
		
		
		// 用户信息验证 及身份验证
		"checkUserinfo" : "pwdSet/check_userinfo",
		//验证的校验 
		"checkIdentifyingCode" : "pwdSet/checkIdentifyingCode",
		//重置密码申请
		"resetTradePwd" : "pwdSet/reset_tradePwd",
		//获取密码配置(交易密码,登录密码)
		"get_password_config" : "pwdSet/get_password_config",
		
		
		/** 积分福利 start 2016-01-04 leiyt * */
		// 1.查询积分福利资格
		"findWelfareQualify" : "welfare/findWelfareQualify",
		// 2.意外伤害保障申请
		"applyAccidentSecurity" : "welfare/applyAccidentSecurity",
		// 3.互生医疗补贴计划申请
		"applyMedicalSubsidies" : "welfare/applyMedicalSubsidies",
		// 4.代他人申请死亡保障金
		"applyDieSecurity" : "welfare/applyDieSecurity",
		// 5.积分福利申请明细查询
		"listWelfareApply" : "welfare/list",
		// 6.积分福利申请详单查询
		"queryWelfareApplyDetail" : "welfare/queryWelfareApplyDetail",
		// 上传证件图片
		"upload" : "fileController/credencePicUpload",
		// 上传文件
		"fileupload" : "fileController/fileUpload",
		
		// 查询绑定的普通银行卡号
		"bankDetailSearch" : "bankDetailSearch/search_BankInfo_List",
		// 查询绑定的快捷银行卡号
		"quickBankDetailSearch" : "bankDetailSearch/search_Quick_BankInfo_List",
		// 校验支付密码
		"validatePayPwd" : "bankDetailSearch/validate_PayPwd",
		// 获取手机支付验证码
		"quickPayVerifyCode":  "bankDetailSearch/quickPay_VerifyCode",
		// 校验支付密码并进行货币支付
		"validateHbPay"	: "bankDetailSearch/validate_HbPay",
		// 获取支持快捷支付的银行列表
		"queryPayBankAll"	: "bankDetailSearch/query_PayBankAll",
		// 兑换互生币开通快捷并支付
		"dhhsbOpenQuickPay"	: "bankDetailSearch/open_QuickPay",
		// 网银支付
		"netPay"	: "bankDetailSearch/net_Pay",
		// 平安银行支付
		"pinganPay"	: "hsbAccount/convert_pay",
		
		// 补卡进行互生币支付
		"remarkOpenCard"	: "bankDetailSearch/remark_Open_Card",
		//初始化支付限额
		"payLimitSetting" : "hsbAccount/pay_Limit_Setting",
		// 支付限额修改 hsbAccount
		"payLimitUpdate": "hsbAccount/pay_Limit_Upate",
						
		/** 积分福利 end * */

		//获取操作员信息
		"operatorDetail" : "operator/get_operator_login_detail",
		
		
		
		/**********非持卡人*************/
		/** 安全设置模块 * */
		// 查询是否设置过交易密码及预留信息
		"no_findSecuritySetType" : "noCardHolder/findSecuritySetType",
		// 获得所有密保问题
		"no_getAllPwdQuestion" : "noCardHolder/find_password_question",
		// 设置密保问题
		"no_setPwdQuestion" : "noCardHolder/save_password_question",
		//查询预留信息
		"no_findReservationInfo" : "noCardHolder/find_reservation_information",
		// 安全设置预留信息
		"no_setReserveInfo" : "noCardHolder/save_reservation_information",
	
		// 设置交易密码
		"no_setTradePwd" : "noCardHolder/set_tradePwd",
		
		// 修改交易密码
		"no_modfiyDealPwd" : "noCardHolder/modify_del_password",
		// 修改登录密码
		"no_modfiyLoginPwd" : "noCardHolder/modify_password",
		
		//查询用户绑定的手机号码
		"no_findMobileByCustId":	"noCardHolder/findMobileByCustId",
		//添加绑定手机号码
		"no_addBindMobile"		:	"noCardHolder/add_bind_mobile",	
		//绑定绑定手机号码
		"no_editBindMobile"	:	"noCardHolder/edit_bind_mobile",
		//发送短信验证码
		"no_mobileSendCode"	:	"noCardHolder/mobileSendCode",
		//发送短信验证码
		"checkSmsCode"	:	"noCardHolder/checkSmsCode",
		
		//查询用户绑定的邮件
		"no_findEamilByCustId"	:	"noCardHolder/findEamilByCustId",
		//添加绑定邮箱
		"no_addBindEmail"	:	"noCardHolder/add_bind_email",	
		//修改绑定邮箱
		"no_mailModify"		:	"noCardHolder/mail_modify",	
		//邮箱验证
		"no_checkMailCode"	:	"noCardHolder/checkEmailCode",
		//获取银行列表数据
		"no_findBankBindList" : "noCardHolder/findBankBindList",
		//添加绑定银行卡信息
		"no_addBankBind"	:	"noCardHolder/add_bank_bind",
		//删除绑定银行卡
		"no_delBankCard"	:	"noCardHolder/del_bank_bind",
		//快捷支付-查询绑定的快捷支付银行
		"no_findPayBanksByCustId":"payBankController/findPayBanksByCustId",
		//快捷支付-添加快捷支付银行
		"no_addPayBank":"payBankController/addPayBank",
		//快捷支付-删除快捷支付银行
		"no_delPayBank":"payBankController/delPayBank",
		
		//根据客户号查询网络信息
		"findNetworkInfoByCustId":"noCardHolder/findNetworkInfoByCustId",
		
		//修改网络信息
		"updateNetworkInfo":"noCardHolder/updateNetworkInfo",
		//重置交易密码发送短信
		"delPwdSendMobileCode":"noCardHolder/delPwdSendMobileCode",
		//重置交易密码身份验证
		"checkNoCarderMainInfo":"noCardHolder/checkNoCarderMainInfo",
		
		//重置交易密码
		"no_resetTradePwd" : "noCardHolder/reset_tradePwd",
		
		//设置默认收货地址
		"removeAddr" : "noCardHolder/removeAddr",
		//设置默认收货地址
		"setDefaultAddr" : "noCardHolder/setDefaultAddr",
		//查询收货地址详情
		"findReceiveAddrInfo" : "noCardHolder/findReceiveAddrInfo",
		// 查询收货地址
		"findReceiveAddrByCustId" : "noCardHolder/findReceiveAddrByCustId",
		// 查询收货地址
		"updateReceiveAddr" : "noCardHolder/updateReceiveAddr",
	};
	return comm.UrlList;
});