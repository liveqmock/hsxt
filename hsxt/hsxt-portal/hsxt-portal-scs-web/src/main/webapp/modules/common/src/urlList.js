define(function() { 
		comm.UrlList= {
				/***公共URL***/
				
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
				
				//公共部分-获取地区平台信息
				"findMainInfoByResNo" : "commController/findMainInfoByResNo",
				
				//公共部分-根据国家代码-省份代码-城市代码获取对应名称
				"getRegionByCode" : "commController/getRegionByCode",
				
				//公共部分-根据客户号查询操作员名称
				"searchOperByCustId":"commController/searchOperByCustId",
				
				//公共部分-根据国家代码获取省份城市信息
				"findProvCity" : "commController/findProvCity",
				
				//公共部分-查询快捷支付银行列表
				"findPayBankAll" : "commController/findPayBankAll",
				
				//公共部分-获取示例图片、常用业务文档、办理业务文档列表
				"findDocList" : "commController/findDocList",
				
				//持卡人登录后获取随机报文
				"findCardHolderToken"	:	"commController/get_token",
				
				//持卡人登录后获取随机报文
				"findPerontIdByPermission"	:	"commController/findPerontIdByPermission",
				
				//持卡人登录后获取随机报文
				"findPermissionByCustidList"	:	"commController/findPermissionByCustidList",
				
				//验证验证码
				"verificationCode" : "commController/verificationCode",
				
				//上传证件图片
				"upload" : "filesOperation/credencePicUpload",	
				
				//上传文件
				"fileupload" : "fileController/fileUpload",
				
				//获取验证码
				"generateSecuritCode" : "commController/generateSecuritCode",

				/*****获得企业重要信息的所有证件的图片在服务器上的ID******/
				"findSamplePictureId" : "importantInfo/findSamplePictureId",
				//加载验证码
				"loadVerifiedCode" : "realNameAuth/loadVerifiedCode",
				//获取token
				"getToken":"commController/get_token",
				
				/*********企业系统信息*********/
				//系统登记信息
				"findSysRegisterInfo" : "systemRegister/findSysRegisterInfo",
				//工商登记 信息查询
				"findSaicRegisterInfo" : "saicRegister/findSaicRegisterInfo",
				//工商登记信息,联系人信息信修改
				"updateSaicRegisterInfo" : "saicRegister/updateSaicRegisterInfo",
				"updateEntBaseInfo" : "saicRegister/updateEntBaseInfo",
				
				
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

			  
			   //商品查询
				//"qryGoods":"modules/myShop/dat/goods/goods.do",
				
				//积分账户明细查询
				"jfzhmxcx":"modules/accountManage/dat/jfzh/mxcx.do",
				//测试
				"test" : "modules/accountManage/dat/jfzh/test.do",
				
				/********************************企业申报管理模块********************************/
				//授权码查询
				"findAuthCodeList" : "authCodeController/list",
				//合同管理
				"findContractList" : "contractManageController/list",
				// 查询打印合同内容
				"find_contract_content_by_print" : "contractManageController/find_contract_content_by_print",
				//打印模板
				"printContract"	:	"contractManageController/printContract",
				//意向客户查询
				"findIntentionCustList" : "intentionCustController/list",
				//查看意向客户申请资料
				"findIntentCustById" : "intentionCustController/findIntentCustById",
				//处理意向客户申请
				"dealIntentCustById" : "intentionCustController/dealIntentCustById",
				//企业申报查询
				"findEntDeclareQueryList" : "entDeclareQueryController/list",
				//企业申报查询-查询申报进行步骤
				"findDeclareStep" : "entDeclareQueryController/findDeclareStep",
				//企业申报查询-删除申报信息
				"delUnSubmitDeclare" : "entDeclareQueryController/delUnSubmitDeclare",
				//企业申报复核
				"findEntDeclareReviewList" : "entDeclareReviewController/list",
				//企业申报复核-审批申报
				"serviceApprDeclare" : "entDeclareReviewController/serviceApprDeclare",
				//企业申报复核-查询申报信息
				"findDeclareAppInfoByApplyId" : "entDeclareReviewController/findDeclareAppInfoByApplyId",
				//企业申报复核-查询办理状态
				"findOperationHisList" : "entDeclareReviewController/findOperationHisList",
				//企业报备查询
				"findEntFilingList" : "entFilingController/list",
				//异议报备查询
				"findDissEntFilingList" : "entFilingController/dissentList",
				//查看异议报备详情
				"findFilingById" : "entFilingController/findFilingById",
				//提异议
				"raiseDissent" : "entFilingController/raiseDissent",
				//新增企业报备
				"createEntFiling" : "entFilingController/createEntFiling",
				//删除企业报备
				"delEntFilingById" : "entFilingController/delEntFilingById",
				//企业报备-新增股东信息
				"createShareholder" : "entFilingController/createShareholder",
				//企业报备-删除股东信息
				"deleteShareholder" : "entFilingController/deleteShareholder",
				//企业报备-修改股东信息
				"updateShareholder" : "entFilingController/updateShareholder",
				//企业报备-查询股东信息
				"findShareholderList" : "entFilingController/findShareholderList",
				//企业报备-初始化附件信息
				"initUpload" : "entFilingController/initUpload",
				//企业报备-保存附件信息
				"saveAptitude" : "entFilingController/saveAptitude",
				//企业报备-查询企业基本信息
				"getEntFilingById" : "entFilingController/getEntFilingById",
				//企业报备-提交报备申请
				"submitFiling" : "entFilingController/submitFiling",
				//企业报备-提交报备申请
				"isExistSameOrSimilar" : "entFilingController/isExistSameOrSimilar",
				//企业报备-依据申请编号查询报备进行步骤
				"findFilingStep" : "entFilingController/findFilingStep",
				//企业申报-查询联系人信息
				"findLinkmanByApplyId" : "entDeclareController/findLinkmanByApplyId",
				//企业申报-保存联系人信息
				"saveLinkInfo" : "entDeclareController/saveLinkInfo",
				//企业申报-查询银行账户信息
				"findBankByApplyId" : "entDeclareController/findBankByApplyId",
				//企业申报-保存银行账户信息
				"saveBankInfo" : "entDeclareController/saveBankInfo",
				//企业申报-查询工商登记信息
				"findDeclareEntByApplyId" : "entDeclareController/findDeclareEntByApplyId",
				//企业申报-保存工商登记信息
				"saveDeclareEnt" : "entDeclareController/saveDeclareEnt",
				//企业申报-查询企业资料上传
				"findAptitudeByApplyId" : "entDeclareController/findAptitudeByApplyId",
				//企业申报-保存企业资料上传
				"saveDeclareAptitude" : "entDeclareController/saveDeclareAptitude",
				//企业申报-提交申报
				"submitDeclare" : "entDeclareController/submitDeclare",
				//企业申报-根据申请编号查询客户类型
				"findCustTypeByApplyId" : "entDeclareController/findCustTypeByApplyId",
				//企业申报-查询系统注册信息
				"findDeclareByApplyId" : "entDeclareController/findDeclareByApplyId",
				//企业申报-查询管理公司信息和服务公司配额数
				"findManageEntAndQuota" : "entDeclareController/findManageEntAndQuota",
				//企业申报-查询增值信息
				"findIncrement" : "entDeclareController/findIncrement",
				//企业申报-保存系统注册信息
				"saveDeclare" : "entDeclareController/saveDeclare",
				//企业申报-保存系统注册信息
				"checkValidEntResNo" : "entDeclareController/checkValidEntResNo",
				//企业申报-成员企业、托管企业配额数和可用互生号列表
				"findResNoListAndQuota" : "entDeclareController/findResNoListAndQuota",
				//企业新增-查询服务公司可用互生号
				"findServicesPointList" : "entDeclareController/findSerResNos",
				//企业新增-查询成员企业、托管企业可用互生号
				"findMemberPointList" : "entDeclareController/findEntResNos",
				
				/**系统安全设置**/	
				//修改登录密码
				"update_login_password" : "pwdSet/update_login_password",
				//获取密码配置(交易密码,登录密码)
				"get_password_config" : "pwdSet/get_password_config",
				//新增交易密码
				"add_trading_password" : "pwdSet/add_trading_password",
				//修改交易密码
				"update_trading_password" : "pwdSet/update_trading_password",
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
				//获取交易密码重置申请业务文件下载地址
				"get_tradPwd_request_file" : "pwdSet/get_tradPwd_request_file",
				//获取交易密码和密保问题已设置
				"query_tradPwd_lastApply" : "pwdSet/query_tradPwd_lastApply",
				//获取交易密码和密保问题是否已设置
				"get_is_safe_set" : "pwdSet/get_is_safe_set",
				
				/**系统安全设置**/	
				
				/**账户管理**/
				// 账户-明细查询
				"detailedPage":"integralAccount/detailed_page",
				//查询账户明细详情
				"getAccOptDetailed":"integralAccount/get_acc_opt_detailed",
				/**积分账户管理**/
				
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
				
				/**积分账户管理**/
				
				/**互生币账户管理**/
				// 互生币账户查询
				"findHsbBlance" : "hsbAccount/find_hsb_blance",
				//互生币转货币-界面数据初始化
				"initHsbTransferCurrency" : "hsbAccount/init_hsb_transfer_currency", 
				//互生币转货币-数据提交
				"hsbTransferCurrency" : "hsbAccount/hsb_transfer_currency", 

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
				// 兑换互生币支付
				"convertPay" : "hsbAccount/convert_pay",
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
				
				//货币账户余额查询
				"find_currency_blance" : "currencyAccount/find_currency_blance",

				
				//货币账户-余额查询
				"findCurrencyBlance":"currencyAccount/find_currency_blance",
				//货币账户-货币转银行，初始化界面
				"initCurrencyTransferBank":"currencyAccount/init_currency_transfer_bank",
				//货币账户-货币转银行
				"currencyTransferBank" : "currencyAccount/currency_transfer_bank",
				//货币账户-货币转银行手续费
				"getBankTransFee" : "currencyAccount/getBankTransFee",
				
				
				
				
				
				
				
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
				//获取税率调整证明材料模块
				"getProofMaterialModule":"cityTaxAccount/get_proof_material_module",
				//查询企业税率调整的最新审批结果
				"getLastTaxApply":"cityTaxAccount/get_last_tax_apply",
				/**城市税收对接账户**/
				
				//*互生币账户-明细查询
				"hsbDetailedPage":"hsbAccount/detailed_page",
				//货币账户-明细查询
				"cashDetailedPage":"currencyAccount/detailed_page",
				//查询货币账户明细详情
				"getCastAccOptDetailed":"currencyAccount/get_acc_opt_detailed",
				// 积分账户-明细查询
				"pointDetailedPage":"integralAccount/detailed_page",
				
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
                "hsbADPvToHsbInfo":"hsbAccountDetail/getPvToHsbInfo",
                
                /****************************互生币账户-明细查询-查看详单  end  *********************/
				/**企业业务服务 **/
				
				//查询成员企业资格注销申请列表
				"membercomp_quit_list" : "membercompquit/list",
				//查询注销申请详细信息
				"find_quit_detailInfo" : "membercompquit/query_detail",
				//审批注销申请
				"approval_quit_apply" : "membercompquit/approval",
				//查询积分活动申请列表
				"find_activity_apply_list" : "activityapply/list",
				//查询积分活动申请详细信息
				"find_activity_apply_detail" : "activityapply/query_detail",
				//审批积分活动申请
				"approval_activity_apply" : "activityapply/approval",
				
				
				/**文档下载服务 **/
				//查询成员企业资格注销申请列表
				"download_agreement_file" : "fileDownload/listDownloadFile",
				
				/** 系统年费缴纳 **/
				//缴纳年费信息查询
				"find_entannualfee_info" : "sysannualfee/query_entannualfee_info",
				//缴纳年费
				"pay_annualfee_info" : "sysannualfee/pay_annualfee",
				//查询年费缴纳列表
				"find_entannualfee_list" : "sysannualfee/list",
				//查询订单详情
				"query_annualfee_order" : "sysannualfee/query_annualfee_order",
				//查询localinfo
				"init_LocalInfo" : "sysannualfee/init_localinfo",
				//查询年费区间
				"find_entannual_interval" : 'sysannualfee/interval',
				//提交年费订单
				"create_annualfee_order" : "sysannualfee/create_annualfee_order",
				//获取绑定的快捷支付银行
				"get_band_quick_bank" : "quickPay/get_band_quick_bank",
				//获取支持快捷支付的银行
				"get_quick_pay_bank" : "quickPay/get_quick_pay_bank",
				//发送快捷支付短信
				"send_quick_pay_sms" : "quickPay/send_quick_pay_sms",
				//开通快捷支付
				"open_quick_pay" : "quickPay/open_quick_pay",
				
				
				/** 系统资源管理 */
				//资源一览查询
				"findBelongEntInfoList" : "ent_sys_res/list",
				//资源一览-查询企业的所有信息
				"findCompanyAllInfo" : "ent_sys_res/findCompanyAllInfo",
				//资源一览-查询银行卡
				"findCompanyBanksByCustId" : "ent_sys_res/findCompanyBanksByCustId",
				//成员企业资格维护-列表查询
				"findQualMainList" : "ent_sys_res/findQualMainList",
				//成员企业资格维护-重新启用成员企业
				"updateEntStatusInfo" : "ent_sys_res/updateEntStatusInfo",
				//成员企业资格维护-注销成员企业
				"createMemberQuit" : "ent_sys_res/createMemberQuit",
				
				//消费者关联资源统计
				"get_correlat_statistics":"customer_sys_res/get_correlat_statistics",
				//消费者关联资源分页查询
				"correlat_statistics_page":"customer_sys_res/list",
				/** 系统资源管理 **/
				
				/** 消息中心 **/
				//创建发送消息
				"send_message" : "messagecenter/sendMsg",
				//创建发送消息
				"edit_message" : "messagecenter/editMsg",
				//查询消息列表
				"find_message_list" : "messagecenter/list",
				//查看消息详情
				"find_message_detail" : "messagecenter/query",
				//删除消息
				"del_message" : "messagecenter/delete",
				//删除多条消息
				"del_messages" : "messagecenter/deleteMessages",
				
				
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
				//企业系统信息-保存经纬度
				"updateLagInfo":"companyInfoController/updateLagInfo",
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
				
				/********************************使用文档下载********************************/
				//使用文档下载
				"findDownloadList" : "fileDownload/list",
				
				/***************投资账户***************/
				//查询积分投资列表  
				"pointInvestPage" : "investmentAccount/point_invest_page",   
				//查询积分投资分红列表   
				"pointDividendPage" : "investmentAccount/point_dividend_page",  
				//查询积分投资分红计算明细  
				"dividendDetailPage" : "investmentAccount/dividend_detail_page",
				//积分投资总数
				"inverstment_total" : "investmentAccount/query_investment_blance",
				/***************投资账户***************/
				
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
				// 修改配送方式
				"changePostWay" : "invoice/changePostWay",
				//根据 resNo 查询客户信息和默认银行账户
                "findMainInfoDefaultBankByResNo":"invoice/findMainInfoDefaultBankByResNo",
				//初始化支付限额
				"payLimitSetting" : "accountSecurity/pay_Limit_Setting",
				// 支付限额修改 
				"payLimitUpdate": "accountSecurity/pay_Limit_Upate",
				
				/*********************系统资源管理 start*********************/	
				//消费者资源详情
				"personResDetail" : "person_res/person_res_detail",
				//企业下的消费者统计
				"entNextPersonStatisticsPage" : "person_res/ent_next_person_statistics_page",
				//查询企业默认银行
				"findDefaultBankByResNo" : "invoice/findDefaultBankByResNo",
				
				/********************************发票管理   end********************************/
				
				
				
				
				
				
				
				
				
				
				
				
				
				/*******************************************互商菜单开始*****************************************/	
				//电子抵扣劵活动
				"listCoupon": '/coupon/listCoupon' ,
				"addCoupon" : "/coupon/addCoupon",
				"stopCoupon":  "/coupon/stopCoupon",
				"listConsumer": "/coupon/listConsumer",
				"updateCoupon":"/coupon/updateCoupon",
				"getCouponDetail":"/coupon/getCouponDetail",
				"listEnterprise":"/coupon/listEnterprise",
				//"searchCoupon":"/coupon/searchCoupon",
				//企业抵扣券查询
				"searchCompanyCoupon":"/coupon/searchCompanyCoupon",
				"getFaceValue":"/coupon/getFaceValue",
				"getShopCouponPartitionList":"/coupon/getShopCouponPartitionList",
				//订单管理
				"orderList":"/order/orderList",
				"orderDetail":"/order/queryOrderInfo",
				//网上商城
				"findVirtualShops" : "/virtualShop/findVirtualShops",
				"openVirtualShop" : "/virtualShop/openVirtualShop",
				"stopVirtualShop" : "/virtualShop/stopVirtualShop",
				"selectArea" : "/virtualShop/selectArea",
				"editVirtualShop" : "/virtualShop/editVirtualShop",
				"getVirtualShopById" : "/virtualShop/getVirtualShopById",
				"getVirtualShopByIdNew" : "/virtualShop/getVirtualShopById",//调用时改变传参方式
				"cleanVsLogo" : "/virtualShop/cleanVsLogo",
				"deleteVirtualShopPics" : "/virtualShop/deleteVirtualShopPics", //zhanghh add 20150824
				//售后管理
				"findSaleSupports" : "/saleSupport/findSaleSupports",
				"serviceProcComplain" : "/saleSupport/serviceProcComplain",//zhanghh add
				"getDictionarysByType" : "/saleSupport/getDictionarysByType",//根据类型获取处理类型
				//统计分析
				"saleTotal" : "/saleTotal/findSaleTotal",
				//日志
				"getLogInfo":"/log/getLogInfo",
				//商品
				"getItemManager" : "/itemInfoEcos/getItemManager",
				"itemCheck" : "/itemInfoEcos/itemCheck",
				"itemCheckHistory" : "/itemInfoEcos/itemCheckHistory",
				"bitchItemCheck" : "/itemInfoEcos/bitchItemCheck",
				"getCategorySelectData" : "/itemInfoEcos/getCategorySelectData",
				"searchItemList" : "/itemInfoEcos/searchItemList",
				"searchComplainItemList" : "/itemInfoEcos/searchComplainItemList",
				"itemPutOn" : "/itemInfoEcos/itemPutOn",//上架
				"itemPutOff" : "/itemInfoEcos/itemPutOff",//下架
				"soldOut" : "/itemInfoEcos/soldOut",
				"getUrl":"/itemCategory/getUrl",
				
				//商品类目
				"findItemCategorys" : "/itemCategory/findItemCategorys",
				//商品类目属性
				"findItemProps" : "/itemCategoryProp/findItemProps",
				"openItemProp" : "/itemCategoryProp/openItemProp",
				"stopItemProp" : "/itemCategoryProp/stopItemProp",
				"addItemProp" : "/itemCategoryProp/addItemProp",
				"modifyItemProp" : "/itemCategoryProp/modifyItemProp",
				"getItemProp" : "/itemCategoryProp/getItemProp",
				//商品品牌
				"findItemBrands" : "/itemBrand/findItemBrands",
				"findItemCategories" : "/itemBrand/findItemCategories",
				"addOrEditItemBrand" : "/itemBrand/addOrEditItemBrand",
				"getItemBrand" : "/itemBrand/getItemBrand",
				//实体店分类
				"saveAndUpdateCategory":"/SalerShopCategory/saveAndUpdateCategory",
				"delCategory":"/SalerShopCategory/delCategory",
				"getCategory":"/SalerShopCategory/getCategory",
				"findItemCategories" : "/itemBrand/findItemCategories",
				"openItemBrand" : "/itemBrand/openItemBrand",
				"stopItemBrand" : "/itemBrand/stopItemBrand",
				"delSalerShop" : "/salerShopEcos/delSalerShop",
				//行政地区
				"saveSysArea" : "/area/saveSysArea",
				"getAreaList" : "/area/getAreaList",
				"updateSysArea" : "/area/updateSysArea",
				"deleteSysArea" : "/area/deleteSysArea",
				"findSysArea" : "/area/findSysArea",
				"getRootArea" : "/area/getRootArea",
				//消费者
				"findCustomers" : "/customer/findCustomers",
				"getCustomer" : "/customer/getCustomer",
				//评价管理
				"findJudges" : "/judge/findJudges",
				"deleteJudge" : "/judge/deleteJudge",
				"deleteAppendComment" : "/judge/deleteAppendComment",
				"findSalerShops" : "/judge/findSalerShops",
				"getFoodItemCommentList":"/order/getFoodItemCommentList",
				//系统参数配置
				"findSysParams" : "/sysParam/findSysParams",
				"addSysParam" : "/sysParam/addSysParam",
				"editSysParam" : "/sysParam/editSysParam",
				"getSysParamById" : "/sysParam/getSysParamById",
				"enableSysParam" : "/sysParam/enableSysParam",
				"disableSysParam" : "/sysParam/disableSysParam",
				"upSortOrderSysParam" : "/sysParam/upSortOrderSysParam",
				"downSortOrderSysParam" : "/sysParam/downSortOrderSysParam",
				//物流
				"findLogistics" : "/logistic/findLogistics",
				"addLogistic" : "/logistic/addLogistic",
				"editLogistic" : "/logistic/editLogistic",
				"enableLogistic" : "/logistic/enableLogistic",
				"disableLogistic" : "/logistic/disableLogistic",
				"getLogistic" : "/logistic/getLogistic",
				//商区
				"findLocations" : "/location/findLocations",
				"findLocationByAreaCode" : "/location/findLocationByAreaCode",
				"addLocation" : "/location/addLocation",
				"editLocation" : "/location/editLocation",
				"delLocation" : "/location/delLocation",
				//地铁
				"findSubways" : "/subway/findSubways",
				"addSubway" : "/subway/addSubway",
				"editSubway" : "/subway/editSubway",
				"deleteSubway" : "/subway/deleteSubway",
				"findSubwayLineByAreaCode" : "/subway/findSubwayLineByAreaCode",
				"findSubwayStationByLine" : "/subway/findSubwayStationByLine",
				//举报
				"getComplainsByService" : "/complainEcos/getComplainsByService",
				"processComplaints" : "/complainEcos/processComplaints",
				"processVoilate" : "/complainEcos/processVoilate",
				"getComplainByParam" : "/complainEcos/getComplainByParam",//zhanghh add
				"getComplainDetailById" : "/complainEcos/getComplainDetailById",//zhanghh add
				//敏感词
				"findSensitiveWords" : "/sensitiveWord/findSensitiveWords",
				"addSensitiveWord" : "/sensitiveWord/addSensitiveWord",
				"editSensitiveWord" : "/sensitiveWord/editSensitiveWord",
				"enableSensitiveWord" : "/sensitiveWord/enableSensitiveWord",
				"disableSensitiveWord" : "/sensitiveWord/disableSensitiveWord",
				"getSensitiveWord" : "/sensitiveWord/getSensitiveWord",
				//实体店 管理
				"listSalerShop":"/salerShopEcos/listSalerShop",
				"findSalerShop":"/salerShopEcos/findSalerShop",
				"updateSalerShop":"/salerShopEcos/updateSalerShop",
				"pendingSalershop":"/salerShopEcos/pendingSalershop",
				"getNewlySalerShopCheckHistory":"/salerShopEcos/getNewlySalerShopCheckHistory",
				//服务公司 管理
				"findSerComUserList" : "/serCompany/findSerComUserList",
				//晒单管理 zhanghh add 20150807 17:00
				"listShareOrderByParam" : "/shareOrderEcos/listShareOrderByParam",
				"deleteShardeOrderById" : "/shareOrderEcos/deleteShardeOrderById",
				//电商运营end
				//-------------------------------------------------------------------//
				//用户协议
				"hasConfirmProtocol" : "/login/hasConfirmProtocol",//是否已同意互生协议
				"confirmProtocol" : "/login/confirmProtocol",//同意
				"companyServiceContent" : "/login/companyServiceContent",//服务公司协议内容
				//-------------------------------餐饮管理开始 zhanghh add 20151102------------------------------------//
				//餐饮订单
				"queryOrderFoods":"/order/queryOrderFoods",
				"queryOrderFoodsDetail":"/order/queryOrderFoodsDetail",
				//餐饮商品
				"listItemInfoFood":"/itemInfoEcos/listItemInfoFood",
				"deleteItemInfoFood":"/itemInfoEcos/deleteItemInfoFood",
				"deleteItemFoodByIds":"/itemInfoEcos/deleteItemFoodByIds",
				//餐饮分类
				"queryCategory":"/itemInfoEcos/queryCategory",
				//上架
				"itemPutOnFood":"/itemInfoEcos/itemPutOnFood",
				//下架
				"itemPutOffFood":"/itemInfoEcos/itemPutOffFood",
				//批量上架
				"batchItemPutOn":"/itemInfoEcos/batchItemPutOn",
				//批量下架
				"batchItemPutOff":"/itemInfoEcos/batchItemPutOff",
				//餐饮菜单
				"listItemMenuFood":"/itemMenuFoodEcos/listItemMenuFood",
				//服务开通
				"serviceOpenList":"/provisioningFoodEcos/serviceOpenList",
				//暂停启用商城
                "changeVshopStatus":"/virtualShop/changeVshopStatus",
                
                /*********************互动信息 start*********************/
                //互动信息-查询历史记录
            	"queryMessageRecordList" : "message//queryMessageRecordList",
            	//互动信息-查询最近联系人
            	"queryRecentContactsMsgList" : "message/queryRecentContactsMsgList"
		};
		return comm.UrlList;
});