define(function() {
		comm.UrlList = {
				
				/***公共URL***/
				//上传证件图片
				"upload" : "filesOperation/credencePicUpload",	
				//上传文件
				"fileupload" : "filesOperation/fileUpload",
				//获取验证码
				"generateSecuritCode" : "commController/generateSecuritCode",
				
				//加载验证码
				"loadVerifiedCode" : "realNameAuth/loadVerifiedCode",
				//获取token
				"getToken":"commController/get_token",
				//获取地区平台信息
				"get_localInfo":"commController/get_localInfo",
				//获取国家列表
				"get_country_all":"commController/get_country_all",
				//获取国家下的省
				"get_province_list":"commController/get_province_list",
				//获取省下的城市
				"get_city_list":"commController/get_city_list",
				//获取银行列表
				"get_bank_list":"commController/get_bank_list",
				//根据城市编号查询城市信息
				"get_city_by_id":"commController/get_city_by_id",
				//公共部分-根据国家代码-省份代码-城市代码获取对应名称
				"getRegionByCode" : "commController/getRegionByCode",
				//地区信息连接
				"getAreaSplitJoint" : "commController/getAreaSplitJoint",
				//公共部分-根据国家代码获取省份城市信息
				"findProvCity" : "commController/findProvCity",
				//公共部分-查询快捷支付银行列表
				"findPayBankAll" : "commController/findPayBankAll",
				//公共部分-获取示例图片、常用业务文档、办理业务文档列表
				"findDocList" : "commController/findDocList",
				//公共部分-根据客户号查询操作员名称
				"searchOperByCustId":"commController/searchOperByCustId",
				//公共部分-获取地区平台信息
				"findMainInfoByResNo" : "commController/findMainInfoByResNo",
				//获取所有菜单权限
				"findPermissionByCustidList":"commController/findPermissionByCustidList",
				//验证验证码
				"verificationCode":"commController/verificationCode",
				
				/*********企业系统信息(作废)*********/
				//系统登记信息
				"findSysRegisterInfo" : "systemRegister/findSysRegisterInfo",
				//工商登记 信息查询
				"findSaicRegisterInfo" : "saicRegister/findSaicRegisterInfo",
				//工商登记信息,联系人信息信修改
				"updateSaicRegisterInfo" : "saicRegister/updateSaicRegisterInfo",
				"updateEntBaseInfo" : "saicRegister/updateEntBaseInfo",
				
				
				/********************************企业系统信息********************************/
				//企业系统信息-查询企业的所有信息
				"findEntAllInfo" : "companyInfoController/findEntAllInfo",
				//企业系统信息-更新企业联系信息
				"updateLinkInfo" : "companyInfoController/updateLinkInfo",
				//企业系统信息-更新企业工商登记信息
				"updateEntExtInfo" : "companyInfoController/updateEntExtInfo",
				//企业系统信息-添加银行卡
				"addBank" : "companyInfoController/addBank",
				//企业系统信息-删除银行卡
				"delBank" : "companyInfoController/delBank",
				//企业系统信息-查询银行卡
				"findBanksByCustId" : "companyInfoController/findBanksByCustId",
				//企业系统信息-初始化企业实名认证
				"initEntRealName" : "companyInfoController/initEntRealName",
				//企业系统信息-创建企业实名认证信息
				"createEntRealNameAuth" : "companyInfoController/createEntRealNameAuth",
				//企业系统信息-创建企业重要信息变更
				"createEntChange" : "companyInfoController/createEntChange",
				//企业系统信息-查询重要信息变更
				"findEntChangeByApplyId" : "companyInfoController/findEntChangeByApplyId",
				//企业系统信息-查询重要信息变更列表
				"findEntChangeList" : "companyInfoController/list",
				//企业系统信息-初始化重要信息变更
				"initEntChange" : "companyInfoController/initEntChange",
				//快捷支付-查询绑定的快捷支付银行
				"findPayBanksByCustId":"payBankController/findPayBanksByCustId",
				//快捷支付-添加快捷支付银行
				"addPayBank":"payBankController/addPayBank",
				//快捷支付-删除快捷支付银行
				"delPayBank":"payBankController/delPayBank",
				
				/********联系人信息*********/
				//联系人信息查询
				"findContactInfo" : "contactInfo/findContactInfo",
				//更新联系人信息
				"updateContactInfo" : "contactInfo/updateContactInfo",
				//邮箱验证
				"checkMailCode"		:	"contactInfo/checkEmailCode",
				//发送验证邮箱的邮件
				"validEmail" : "contactInfo/validEmail",
				
				
				/**************重要信息*************/
				//重要信息查询
				"findImportantInfo" : "importantInfo/findImportantInfo",
				//加载证件类型列表 
				"loadCredenceTypes" : "importantInfo/loadCredenceTypes",
				"submitChangeApply" : "importantInfo/submitChangeApply",
				"findChangeRecord" : "importantInfo/list",
				"findEntInfoChangedDetail" : "importantInfo/findEntInfoChangedDetail",
				"findChangeApplyOrder" : "importantInfo/findChangeApplyOrder",
				
	
				/**实名认证**/
				//实名认证信息查询
				"findRealNameAuthInfo" : "realNameAuth/findRealNameAuthInfo",
				///***文件上传****/
				"checkVerifiedCode" : "realNameAuth/checkVerifiedCode",
				//加载图片
				"loadPicture" : "realNameAuth/loadPicture",
				//提交
				"submitRealNameAuthData" : "realNameAuth/submitRealNameAuthData",
				"findRealNameAuthApply" : "realNameAuth/findRealNameAuthApply",
				
				
				/***银行帐户***/
				//银行卡列表
				"findCardList" : "bankAccountInfo/findCardList",
				//删除银行帐号
				"delBankAccountInfo" : "bankAccountInfo/delBankAccountInfo",
				//修改银行帐户信息
				"updateBankAccountInfo" : "bankAccountInfo/updateBankAccountInfo",
				//绑定银行卡
				"addBankCard" : "bankAccountInfo/addBankCard",
				//解除银行卡绑定
				"delBankCard" : "bankAccountInfo/delBankCard",
				//查询银行列表
				"findApplyBankList" : "bankAccountInfo/findApplyBankList",
				//查询省份列表
				"findProvinceList" : "bankAccountInfo/findProvinceList",
				//查询城市列表 
				"findCityList" : "bankAccountInfo/findCityList",
				//必要 的参数 
				"findRequired" : "bankAccountInfo/findRequired",

				/********** 账户管理 start***********/
				/********** 积分账户 ***********/
				//积分账户余额查询
				"queryzhye" : "integralAccount/find_integral_balance",
				//初始化积分转互生币
				"init_integral_transfer_Hsb" : "integralAccount/init_integral_transfer_Hsb",
				//积分转互生币 提交
				"commitJfzhsb" : "integralAccount/integral_transfer_Hsb",
				//初始化积分投资页面
				"init_integral_investment":"integralAccount/init_integral_investment",
				//积分投资
				"commitJftz" : "integralAccount/integral_investment",
				//查询消费积分分配详情
				"getPointAllotDetailedList":"integralAccount/get_point_allot_detailed_list",
				//查询消费积分分配详情
				"getPointAllotDetailed":"integralAccount/get_point_allot_detailed",
				
				//分页查询再增值积分汇总列表
				"queryBmlmListPage" : "integralAccount/queryBmlmListPage",
				//分页查询互生积分分配列
				"queryMlmListPage" : "integralAccount/queryMlmListPage",
				//分页查询消费积分分配列表
				"queryPointDetailSumPage" : "integralAccount/queryPointDetailSumPage",
				
				/********** 互生币账户 ***********/
				//互生币账户余额查询
				"find_hsb_blance" : "hsbAccount/find_hsb_blance",
				//初始化兑换互生币
				"init_transfer_hsb" : "hsbAccount/init_transfer_hsb",
				//货币转互生币 提交
				"transfer_hsb" : "hsbAccount/transfer_hsb",
				//初始化互生币转货币页面
				"init_hsb_transfer_currency":"hsbAccount/init_hsb_transfer_currency",
				//互生币转货币提交
				"hsb_transfer_currency" : "hsbAccount/hsb_transfer_currency",
				//　兑换互生币网银支付
				"net_Pay" : "hsbAccount/net_Pay",
				// 平安网银支付
				"pingAnPay"	: "hsbAccount/convert_pay",
				//　兑换互生币快捷支付
				"validate_PayPwd" : "hsbAccount/validate_PayPwd",
				//　兑换互生币开通快捷并支付
				"open_QuickPay" : "hsbAccount/open_QuickPay",
				//　兑换互生币货币支付
				"validate_HbPay" : "hsbAccount/validate_HbPay",
				//获取绑定的快捷支付银行卡集合
				"quickBankDetailSearch": "hsbAccount/search_Quick_BankInfo_List",
				//获取快捷支付验证码
				"quickPayVerifyCode": "hsbAccount/quickPay_VerifyCode",
				//获取全部支持快捷支付银行
				"queryPayBankAll": "hsbAccount/query_PayBankAll",
				//初始化支付限额
				"payLimitSetting" : "hsbAccount/pay_Limit_Setting",
				// 支付限额修改 
				"payLimitUpdate": "hsbAccount/pay_Limit_Upate",
				
				//*互生币账户-明细查询
				"detailedPage":"balanceController/detailed_page",
				//账户操作明细详单
				"get_acc_opt_detailed":"balanceController/get_acc_opt_detailed",
			
				//查询网上商城、线下销售收入详情流水
				"getMallDetail":"integralAccount/getMallDetail",
				"getSaleDetail":"integralAccount/getSaleDetail",
				
				/****************************互生币账户-明细查询-查看详单  start *********************/
				//查询积分投资分红详情
                "hsbADPointDividendInfo":"hsbAccountDetail/getPointDividendInfo",
				//获取年费订单详情
                "hsbADAnnualFeeOrder":"hsbAccountDetail/queryAnnualFeeOrder",
                //查询线下兑换互生币详情
                "hsbADGetOrder":"hsbAccountDetail/getOrder",
                //查询互生币转货币详情
                "hsbADHsbToCashInfo":"hsbAccountDetail/getHsbToCashInfo",
                //查询兑换互生币详情
                "hsbADBuyHsbInfo":"hsbAccountDetail/getBuyHsbInfo",
                //查询积分转互生币详情
                "getPvToHsbInfo":"hsbAccountDetail/getPvToHsbInfo",
                //消费积分扣除统计查询
                "reportsPointDeduction":"hsbAccountDetail/reportsPointDeduction",
                //消费积分扣除终端统计查询
                "reportsPointDeductionByChannel":"hsbAccountDetail/reportsPointDeductionByChannel",
                //消费积分扣除操作员统计查询
                "reportsPointDeductionByOper":"hsbAccountDetail/reportsPointDeductionByOper",
                
                //消费积分扣除统计查询汇总
                "reportsPointDeductionSum":"hsbAccountDetail/reportsPointDeductionSum",
                //消费积分扣除终端统计查询汇总
                "reportsPointDeductionByChannelSum":"hsbAccountDetail/reportsPointDeductionByChannelSum",
                //消费积分扣除操作员统计查询汇总
                "reportsPointDeductionByOperSum":"hsbAccountDetail/reportsPointDeductionByOperSum",
                //获取终端编号列表
                "getPosDeviceNoList":"hsbAccountDetail/getPosDeviceNoList",
                //获取操作员编号列表
                "getOperaterNoList":"hsbAccountDetail/getOperaterNoList",
                
                
                /****************************互生币账户-明细查询-查看详单  end  *********************/
                
				/********** 货币账户 ***********/
				//货币账户余额查询
				"find_currency_blance" : "currencyAccount/find_currency_blance",
				//初始化货币转银行界面
				"init_currency_transfer_bank" : "currencyAccount/init_currency_transfer_bank",
				//初始化货币转银行确认界面
				"getBankTransFee" : "currencyAccount/getBankTransFee",
				//货币转银行 提交
				"currency_transfer_bank" : "currencyAccount/currency_transfer_bank",
				
				/********** 投资账户 ***********/
				//投资余额查询
				"query_investment_blance" : "investmentAccount/query_investment_blance",
				"getPointInvestList" : "investmentAccount/getPointInvestList",
				"getPointDividendList" : "investmentAccount/getPointDividendList",
				"getDividendDetailList" : "investmentAccount/getDividendDetailList",
				"getInvestDividendInfo" : "investmentAccount/getInvestDividendInfo",
				
				/**城市税收对接账户**/
				//城市税收对接账户-余额查询
				"findCityTaxBlance" : "cityTaxAccount/find_cityTax_blance",
				//城市税收对接账户-明细查询
				"cityTaxDetailedPage" : "cityTaxAccount/detailed_page",
				//城市税收对接账户-税率查询 
				"queryTax":"cityTaxAccount/query_tax",
				//城市税收对接账户-税率调整申请
				"taxAdjustmentApply":"cityTaxAccount/tax_adjustment_apply",
				//城市税收对接账户-税率调整申请查询
				"taxAdjustmentApplyPage":"cityTaxAccount/tax_adjustment_apply_page",
				//城市税收对接账户-税率调整申请查询-明细
				"taxApplyDetail":"cityTaxAccount/tax_apply_detail",
				
				//查询企业税率调整的最新审批结果
				"getLastTaxApply":"cityTaxAccount/get_last_tax_apply",
				/**城市税收对接账户**/
				
				
				/********** 账户管理 end  ********************************************/
				
				//积分账户 明细查询
				"query_jfzh_mxcx" : "integralAccount/detailed_page",
				//积分账户操作明细详单
				"get_point_acc_opt_detailed":"integralAccount/get_acc_opt_detailed",
				//查询消费积分分配详情
				"getPointAllotDetailedList":"integralAccount/get_point_allot_detailed_list",
				//查询消费积分分配详情
				"getPointAllotDetailed":"integralAccount/get_point_allot_detailed",
				/** 商品管理 **/
				//查询所有商品分类
				"query_spfl_tree" : "goodsmanage/query_goodstype_tree",
				//查询商品分类关联的商品
				"query_spfl_goods" : "goodsmanage/query_spfl_goods",
				
				/** 工具管理 **/
				//工具申购-查询可以购买的工具
				"query_may_buy_tool" : "toolmanage/query_may_buy_tool",
				//查询卡样
				"query_hscard_style" : "toolmanage/query_hscard_style",
				//查询企业可以购买工具的数量
				"query_tool_num" : "toolmanage/query_tool_num",
				//新增收货地址
				"add_deliver_address" : "toolmanage/add_deliver_address",
				//修改收货地址
				"modify_deliver_address" : "toolmanage/modify_deliver_address",
				//删除收货地址
				"remove_deliver_address" : "toolmanage/remove_deliver_address",
				//查收收货地址
				"query_deliver_address" : "toolmanage/query_deliver_address",
				//工具申购-查询企业地址
				"query_ent_address" : "toolmanage/query_ent_address",
				//工具申购  --提交订单
				"commit_bytool_order" : "toolmanage/commit_bytool_order",
				//购买系统资源
				"commit_bytool_order_card" : "toolmanage/commit_bytool_order_card",
				//查询企业可以购买的资源段
				"query_ent_resource_segment" : "toolmanage/query_ent_resource_segment",
				//查询企业可以购买的资源段(新)
				"query_ent_resource_segment_new" : "toolmanage/query_ent_resource_segment_new",
				//获取企业可以选择的卡样
				"get_ent_card_style_by_all" : "toolmanage/get_ent_card_style_by_all",
				//工具订单支付
				"tool_order_payment" : "toolmanage/tool_order_payment",
				//分页查询工具订单
				"tool_order_list" : "toolmanage/tool_order_list",
				//查询工具订单详情
				"tool_order_detail" : "toolmanage/tool_order_detail",
				//根据订单号查询配置单
				"get_tool_config_by_no" : "toolmanage/get_tool_config_by_no",
				//工具订单撤单
				"tool_order_cancel" : "toolmanage/tool_order_cancel",
				//终端管理 - 查询终端列表
				"tool_terminal_list" : "toolmanage/tool_terminal_list",
				//分页查询个性卡订制服务
				"special_card_style_list" : "toolmanage/special_card_style_list",
				//查询企业的个性卡列表
				"ent_special_card_style" : "toolmanage/ent_special_card_style",
				//个性卡订制服务下单
				"submit_special_card_style" : "toolmanage/submit_special_card_style",
				//上传卡样素材
				"add_special_card_style" : "toolmanage/add_special_card_style",
				//个性卡样确认
				"confirm_card_style" : "toolmanage/confirm_card_style",
				//上传卡制作卡样确认文件
				"upload_card_mark_confirm_file" : "toolmanage/upload_card_mark_confirm_file",
				//修改设备状态
				"modify_device_status" : "toolmanage/modify_device_status",
				/** 快捷支付 **/
				//获取绑定的快捷支付银行
				"get_band_quick_bank" : "quickPay/get_band_quick_bank",
				//获取支持快捷支付的银行
				"get_quick_pay_bank" : "quickPay/get_quick_pay_bank",
				//开通快捷支付
				"open_quick_pay" : "quickPay/open_quick_pay",
				//发送快捷支付短信
				"send_quick_pay_sms" : "quickPay/send_quick_pay_sms",
				
				
				//查看年费缴纳基础信息
				"serch_annualfee_info" : "annualfee/query_annualfee_info",
				//缴纳年费
				"pay_annualfee_info" : "annualfee/pay_annualfee",
				//提交年费订单
				"create_annualfee_order" : "annualfee/create_annualfee_order",
				
				/** 网上登记积分 **/
				//设置企业积分比例
				"set_pointrate" : "pointrate/add",
				//查询企业积分比例
				"query_pointrate" : "pointrate/query",
				//修改企业积分比例
				"update_pointrate" : "pointrate/update",
				//查询企业积分比例菜单
				"query_pointrateMenu" : "pointrate/queryMenu",
				//网上积分登记
				"pointRegister" : "pointRegister/register",
				//网上积分登记查询
				"pointRegisterList" : "pointRegister/list",
				//查询流水号
				"getSequence" : "pointRegister/getSequence",
				
				
				/** 成员企业资格维护 **/
				//查询企业状态信息
				"query_ent_status" : "memberenterprise/query_enterprise_status",
				//查询成员企业资格注销申请书、模板
				"query_business_apply" : "memberenterprise/query_business_apply",
				//查询企业积分预付款账户余额、货币转换比率、货币转换费、银行账户、账户名称、开户银行、开户地区、银行账号、结算币种
				"query_business_accountinfo" : "memberenterprise/query_business_accountinfo",
				//成员企业注销
				"member_apply_quit" : "memberenterprise/member_apply_quit",
				
				/** 积分活动 **/
				//查询企业积分预付款余额、货币转换比率
				"query_point_num" : "pointactivity/query_point_num",
				//停止积分活动申请
				"apply_pointactivity_apply" : "pointactivity/apply_pointactivity_apply",
				
				/** 系统业务订单查询 **/
				"query_system_order_list" : "sysbusorder/list",
				//获取年费订单详情
				"get_annualfee_order_detail" : "sysbusorder/get_annualfee_order_detail",
				//** 发货单确认收货 **/
				"tool_accept_confirm" : "deliverorder/tool_accept_confirm",
				//订单去付款
				"tool_order_topay" : "sysbusorder/tool_order_topay",
				//积分活动申请（停止/参与）
				"apply_pointactivity_apply" : "pointactivity/apply_pointactivity_apply",
					
				/***********代兑互生币*************/
				"getExchangeHsbValidate":   "exchangeHsb/getExchangeHsbValidate",
				"findExchangeHsbInfo"	:	"exchangeHsb/find_exchangeHsb_info",
				"getCardHolderDayLimit"	:	"exchangeHsb/getCardHolderDayLimit",
				
				"findCustIdByResno"		:	"exchangeHsb/find_custid_by_resno",
				
				"exchange"				:	"exchangeHsb/exchange",
				/******系统用户*********/
				/**系统安全设置**/	
				//修改登录密码
				"update_login_password" : "pwdSet/update_login_password",
				//获取密码配置(交易密码,登录密码)
				"get_password_config" : "pwdSet/get_password_config",
				//新增交易密码
				"add_trading_password" : "pwdSet/add_trading_password",
				//修改交易密码
				"update_trading_password" : "pwdSet/update_trading_password",
				//获取交易密码和密保问题已设置
				"query_tradPwd_lastApply" : "pwdSet/query_tradPwd_lastApply",
				//获取密保列表
				"get_question_list" : "securitySet/get_question_list",
				//设置密保问题答案
				"set_pwd_question" : "securitySet/set_pwd_question",
				//获取预留信息
				"get_reserve_info" : "securitySet/get_reserve_info",
				//设置预留信息
				"set_reserve_info" : "securitySet/set_reserve_info",
				//修改预留信息
				"update_reserve_info" : "securitySet/update_reserve_info",
				//获取企业明细
				"get_ent_info" : "pwdSet/get_ent_info",
				//重置交易密码
				"reset_trading_password" : "pwdSet/reset_trading_password",
				//申请交易密码重置
				"requested_reset_trading_password" : "pwdSet/requested_reset_trading_password",
				
				//获取交易密码和密保问题是否已设置
				"get_is_safe_set" : "pwdSet/get_is_safe_set",
				
				/**系统安全设置**/	
				
				/********************************发票管理   start********************************/
				//发票列表
				"listInvoice" : "invoice/list",
				//根据ID查询发票详情
				"getInvoiceById" : "invoice/findById",
				//客户开发票
				"custOpenInvoice" : "invoice/custOpenInvoice",
				//客户申请发票
				"custApplyInvoice" : "invoice/custApplyInvoice",
				//查询发票统计总数
				"queryInvoiceSum" : "invoice/queryInvoiceSum",	
				//配送
				"changePostWay" : "invoice/changePostWay",
				//根据互生号查询默认银行账号重要信息
                "findMainInfoDefaultBankByResNo":"invoice/findMainInfoDefaultBankByResNo",
                //查询企业默认银行
				"findDefaultBankByResNo" : "invoice/findDefaultBankByResNo",
				/********************************发票管理   end********************************/
				
				
				/********************************资源查询管理   start********************************/
				//资源统计查询
				"resource_unify_search" : "resource/resource_unify_search",
				//消费者资源查询
				"consumer_resource_search" : "resource/consumer_resource_search",
				
				/********************************资源查询管理   end********************************/
				
				
				/**************************操作员管理 ********************************/
				//查询操作员列表（企业客户号查询所有）
				"list_operator_buentcustId" : "operator/query_list",
				//查询操作员列表（用户名、真实姓名、企业客户号）
				"list_operator" : "operator/list",
				//添加操作员
				"add_operator" : "operator/add",
				//修改操作员
				"update_operator" : "operator/update",
				//删除操作员
				"delete_operator" : "operator/delete",
				//查询操作员
				"query_operator" : "operator/query",
				//绑定互生卡
				"bindcard_operator" : "operator/bindcard",
				//解绑互生卡
				"unbindcard_operator" : "operator/unbindcard",
				//获取操作员信息
				"operatorDetail" : "operator/get_operator_login_detail",
				
				/******************营业点 ****************/
				"setSalerStoreEmployeeRelation" : "storeemployee/setSalerStoreEmployeeRelation",
				"getSalerStoresByEntResNoAndEmpActNo" : "storeemployee/getSalerStoresByEntResNoAndEmpActNo",
				"deleteStoreEmployeesByEntResNoAndEmpActNo" : "storeemployee/deleteStoreEmployeesByEntResNoAndEmpActNo",
				
				/*********************角色管理********************************/
				//查询角色列表
				"list_role" : "role/list",
				//添加角色
				"add_role" : "role/add",
				//修改角色
				"update_role" : "role/update",
				//删除角色
				"delete_role" : "role/delete",
				//给操作员赋角色
				"grantrole_role" : "role/grantrole",
				//给操作员重设角色
				"resetrole_role" : "role/resetrole",
				//不分页查询角色列表
				"rolelist_role" : "role/rolelist",
				
				/***************** 权限管理 **************/
				//赋权限
				"grantperm_perm" : "perm/grantperm",
				//查询权限列表
				"listperm_perm" : "perm/listperm",
				//查看角色已有权限
				"roleperm_perm" : "perm/roleperm",
				
				/********货币账户*************/
				//货币账户-余额查询
				"findCurrencyBlance":"currencyAccount/find_currency_blance",
				//货币账户-货币转银行，初始化界面
				"initCurrencyTransferBank":"currencyAccount/init_currency_transfer_bank",
				//货币账户-货币转银行
				"currencyTransferBank" : "currencyAccount/currency_transfer_bank",
				//货币账户-货币转银行手续费
				"getBankTransFee" : "currencyAccount/getBankTransFee",
				
				
				
				
				
				
				
									
				//########## 电商请求链接#######################################################
				"orderFoodList" : "food/orders/orderList",
				//商品列表
				"getItemInfo":"food/itemInfo/getItemInfo",
				//餐饮菜品列表
				"queryItemList":"item/queryItemList",
				//晒单
				"listShareOrderByParam":"shareOrder/listShareOrderByParam",
				/**********************************网上商城信息*****************************************/
				//查询营业点列表
				"listItemInfoFoodQueryParam" : "salerShopFood/listItemInfoFoodQueryParam",
				//查询送货员
				"listSalerShopDeliver":"deliverMan/listSalerShopDeliver",
				//抵扣券设置
				"listShopCouponPartition":"shopcoupon/listShopCouponPartition",
				//运费设置
				"listShipping":"eShop/listSalerStorage",
				//服务开通
				"provisioningFood":"provisioningFoodWeb/provisioningFood",
				/************************************系统用户管理***************************************/
				//网上商城自定分类
				"queryShopCategoryGrid":"food/itemSalerCategory/queryShopCategoryGrid",
				//零售商品信息关联查询
				"getRelationItem":"food/itemSalerCategory/getRelationItem",
				//订单管理
				"queryOrders":"order/queryOrders",
				//订单详情
				"queryOrderInfo":"order/queryOrderInfo",
				//查询退换货记录
				"getRefundByUserId":"refund/getRefundByUserId",
				//[评价管理：商品管理]
				"getCommentsByShopGrid":"comment/getCommentsByShopGrid",
				//[评价管理：营业点评价]
				"queryItemListCommentGrid":"food/evaluate/getFoodItemCommentListGrid",
				
				/*********************互动信息 start*********************/
                //互动信息-查询历史记录
            	"queryMessageRecordList" : "message/queryMessageRecordList",
            	//互动信息-查询最近联系人
            	"queryRecentContactsMsgList" : "message/queryRecentContactsMsgList",
		};
		return comm.UrlList;
});