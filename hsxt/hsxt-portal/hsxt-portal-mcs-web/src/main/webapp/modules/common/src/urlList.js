define(function() { 
	comm.UrlList= {
		/***公共URL***/
		//公共部分-查询所有的国家信息
		"findContryAll" : "commController/find_contry_all",
		//公共部分-根据国家查询下属省份	
		"findProvinceByParent" : "commController/find_province_by_parent",
		//公共部分-查询所有的银行列表
		"findBankAll" : "commController/findBankAll",
		//公共部分-获取本平台基本信息	
		"findSystemInfo" : "commController/findSystemInfo",
		//公共部分-根据国家省份查询下级城市
		"findCityByParent" : "commController/find_city_by_parent",
		//公共部分-根据国家代码-省份代码-城市代码获取对应名称
		"getRegionByCode" : "commController/getRegionByCode",
		//公共部分-上传证件图片
		"upload" : "filesOperation/credencePicUpload",
		//公共部分-上传文件
		"fileupload" : "fileController/fileUpload",
		//公共部分-获取验证码
		"generateSecuritCode" : "commController/generateSecuritCode",
		//公共部分-获取token
		"getToken":"commController/get_token",
		//公共部分-验证双签登陆
		"verifyDoublePwd" : "commController/verifyDoublePwd",
		//公共部分-根据国家代码获取省份城市信息
		"findProvCity" : "commController/findProvCity",
		//公共部分-获取示例图片、常用业务文档、办理业务文档列表
		"findDocList" : "commController/findDocList",
		//公共部分-根据客户号查询操作员名称
		"searchOperByCustId":"commController/searchOperByCustId",
		
		//根据客户号获取权限信息
		"findPermissionByCustidList"	:	"commController/findPermissionByCustidList",

		//工单拒绝受理
		"work_task_refuse_accept":"commController/work_task_refuse_accept",
		//工单挂起
		"work_task_hang_up":"commController/work_task_hang_up",
		
		/********************************企业申报管理模块********************************/
		//授权码查询
		"findAuthCodeList" : "authCodeController/list",
		//初审业务查询
		"findDeclareTrialList" : "entDeclareTrialController/list",
		//初审业务-审核提交
		"managerApprDeclare" : "entDeclareTrialController/managerApprDeclare",
		//复核业务查询
		"findDeclareReviewCodeList" : "entDeclareReviewController/list",
		//复核业务-审核提交
		"managerReviewDeclare" : "entDeclareReviewController/managerReviewDeclare",
		//复核业务-查询服务公司可用互生号
		"findServicesPointList" : "entDeclareReviewController/findSerResNos",
		//复核业务-查询服务公司可用互生号--顺序选配
		"findSerResNo" : "entDeclareReviewController/findSerResNo",
		//复核业务-查询服务公司互生号列表
		"findSerResNoList" : "entDeclareReviewController/findSerResNoList",
		//复核业务-选号
		"managePickResNo" : "entDeclareReviewController/managePickResNo",
		//审批统计查询
		"findDeclareStatisticsList" : "entDeclareStatisticsController/list",
		//查询办理状态
		"findOperationHisList" : "entDeclareStatisticsController/findOperationHisList",
		//审批统计查询-查询联系人信息
		"findLinkmanByApplyId" : "entDeclareStatisticsController/findLinkmanByApplyId",
		//审批统计查询-查询工商登记信息
		"findDeclareEntByApplyId" : "entDeclareStatisticsController/findDeclareEntByApplyId",
		//审批统计查询-查询企业资料上传
		"findAptitudeByApplyId" : "entDeclareStatisticsController/findAptitudeByApplyId",
		//审批统计查询-查询系统注册信息
		"findDeclareByApplyId" : "entDeclareStatisticsController/findDeclareByApplyId",
		//审批统计查询-查询银行账户信息
		"findBankByApplyId" : "entDeclareStatisticsController/findBankByApplyId",
		//审批统计查询-查询申报信息
		"findDeclareAppInfoByApplyId" : "entDeclareStatisticsController/findDeclareAppInfoByApplyId",
		//保存银行账户信息
		"saveBankInfo" : "entDeclareTrialController/saveBankInfo",
		//保存系统注册信息
		"saveDeclare" : "entDeclareTrialController/saveDeclare",
		//保存附件信息
		"saveDeclareAptitude" : "entDeclareTrialController/saveDeclareAptitude",
		//保存企业联系信息
		"saveLinkInfo" : "entDeclareTrialController/saveLinkInfo",
		//保存工商登记信息
		"saveDeclareEnt" : "entDeclareTrialController/saveDeclareEnt",
		
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
		//获取操作员信息
		"operatorDetail" : "operator/get_operator_login_detail",
		
		/********************用户组管理 **********************************/
		//查询用户组列表
		"list_entgroup" : "entgroup/list",
		//添加用户组
		"add_entgroup" : "entgroup/add",
		//修改用户组
		"update_entgroup" : "entgroup/update",
		//删除用户组
		"delete_entgroup" : "entgroup/delete",
		//查询用户组成员列表
		"userlist_entgroup" : "entgroup/userlist",
		//用户组添加用户
		"addgroupuser_entgroup" : "entgroup/addgroupuser",
		//删除用户组用户
		"delgroupuser_entgroup" : "entgroup/delgroupuser",
		//查询归属地区平台
		"entinfo_platform" : "entinfo/platform",
		
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
		//邮箱验证
		"checkMailCode"		:	"companyInfoController/checkEmailCode",
		//发送验证邮箱的邮件
		"validEmail" : "companyInfoController/validEmail",
		
		/*********************系统安全设置*********************/	
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
		//获取交易密码和密保问题是否已设置
		"get_is_safe_set" : "pwdSet/get_is_safe_set",
		
		/*********************系统安全设置*********************/	
		/*********************账户管理 start*********************/	
		//货币账户余额
		"findCurrencyBlance" : "currencyAccount/find_currency_blance",
		//货币账户明细查询
		"currencyDetailedPage" : "currencyAccount/detailed_page",
		"getdetailInfo" : "currencyAccount/getdetailInfo",
		//积分账户余额
		"findIntegralBalance" : "integralAccount/find_integral_balance",
		//积分账户明细查询
		"integralDetailedPage" : "integralAccount/detailed_page",
		//消费积分分配明细查询
		"pointAllocDetail" : "integralAccount/pointAllocDetail",
		"pointAllocDetailList" : "integralAccount/pointAllocDetailList",
		
		//分页查询消费积分分配列表
		"queryPointDetailSumPage" : "integralAccount/queryPointDetailSumPage",
		
		/*********************账户管理   end*********************/	
		
		/*********************系统资源管理 start*********************/	
		//消费者资源详情
		"personResDetail" : "person_res/person_res_detail",
		//企业下的消费者统计
		"entNextPersonStatisticsPage" : "person_res/ent_next_person_statistics_page",
		
		//企业资源一览
		"entResPage" : "ent_res/ent_res_page",
		//获取企业明细
		"getEntAllInfo" : "ent_res/get_ent_all_info",
		//获取企业银行列表
		"getEntBankList" : "ent_res/get_ent_bank_list",
		
		//统计管理公司下的资源数据
		"statResDetailOfManager" : "resGlanceController/statResDetailOfManager",
		//统计二级区域下的资源数据
		"statResDetailOfProvince" : "resGlanceController/statResDetailOfProvince",
		//三级区域(城市)下的资源列表
		"listResInfoOfCity" : "resGlanceController/listResInfoOfCity",
		//统计管理公司下的企业资源
		"statResCompanyResM" : "resGlanceController/statResCompanyResM",
		//统计服务公司的企业资源
		"findCompanyResMList" : "resGlanceController/list",
		
		/*********************系统资源管理   end*********************/	
		
		/** 工单管理 start */
		"workOrderPage" : "workOrder/work_order_page", // 工单分页查询
		//workOrderTermination" : "workOrder/termination", // 工单终止
		"workOrderToDo" : "workOrder/to_do", // 工单转入待办
		"workOrderSuspend" : "workOrder/suspend", // 工单挂起
		"workOrderReminders" : "workOrder/reminders", // 工单催办
		"workOrderDoor" : "workOrder/door", // 工单拒绝受理
		"getAttendantList" : "workOrder/get_attendant_list", // 值班员列表	
		"getBizAuthList" : "workOrder/get_bizAuth_list", // 查询地区平台业务类型列表
		
		"getGroupList" : "schedule/get_group_list", // 获取值班组信息
		"getMembersSchedule" : "schedule/get_members_schedule", //获取工作组下组员日程安排
		"getGroupInfo" : "schedule/get_groupInfo", // 查询值班组信息
		"saveGroup" : "schedule/save_group", //保存值班组
		"addBizType" : "schedule/add_biztype", // 新增值班员业务节点
		"deleteBizType" : "schedule/delete_biztype", // 删除值班员业务节点
		"removeOperator" : "schedule/remove_operator", // 移除值班员
		"udpateGroupStatus" : "schedule/udpate_group_status", //值班组开启或关闭
		"groupAdd" : "schedule/group_add", // 工作组添加
		"groupUpdate" : "schedule/group_update", // 工作组添加
		"getAttendantInfo" : "schedule/get_attendant_info", // 获取值班员明细
		"executeSchedule": "schedule/execute_schedule", // 执行值班计划
		"pauseSchedule": "schedule/pause_schedule", // 暂停值班组计划
		"saveSchedule": "schedule/save_schedule", // 保存值班计划
		"getEntOperList": "schedule/get_ent_oper_list", // 获取企业员工
		"exportSchedule": "schedule/export",// 导出值班计划
		"getBizTypeList" : "schedule/get_biz_type_list", // 获取操作员对应的业务类型
		"isChief" : "schedule/is_chief", // 操作员是否为值班主任
		
		/** 工单管理 end */
		
		/********************* 消息中心 ****************************/
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
		/*********************消息中心   end*********************/
		
		/********************合同管理 start**********************/
		//合同查询
		"findContractList" : "contractManageController/list",
		// 查询盖章合同内容
		"find_contract_content_by_seal" : "contractManageController/find_contract_content_by_seal",
		//打印模板
		"printContract"	:	"contractManageController/printContract",
		/********************合同管理 end**********************/

		/************************	呼叫中心 start	************************/
		//查询消费者所有信息
		"getPersonAllInfo":"callCenter/getPersonAllInfo",
		//查询账户余额信息   param  resNo 互生号   accType 账户类型 积分10110 互生币（流通币20110 定向消费币20120 慈善救助金20130） 货币30110
		"getAccountBalance":"callCenter/getAccountBalance",
		//消费者今日积分查询
		"searchPerIntegralByToday":"callCenter/searchPerIntegralByToday",
		//企业昨日积分查询
		"searchEntIntegralByYesterday":"callCenter/searchEntIntegralByYesterday",
		//投资及投资分红查询
		"findInvestDividendInfo":"callCenter/findInvestDividendInfo",
		//查询积分福利资格 param resNo
		"getWelfareQualify":"callCenter/getWelfareQualify",
		//积分福利信息查询，包括历史保单
		"getWelfareList":"callCenter/getWelfareList",
		//积分福利申请列表查询
		"getListWelfareApply":"callCenter/getListWelfareApply",
		//互生卡补办记录查询
		"findCardapplyList":"callCenter/findCardapplyList",
		//企业联系信息查询  用于呼叫中心右上角
		"searchEntContactInfo":"callCenter/searchEntContactInfo",
		//企业所有信息查询  用于呼叫中心右上角
		"searchEntAllInfo":"callCenter/searchEntAllInfo",
		//报送服务公司信息查询

		//查询银行账号列表信息
		"getBankAccountList":"callCenter/getBankAccountList",
		//快捷支付卡列表信息
		"getQkBankAccountList":"/callCenter/getQkBankAccountList",
		//重要信息变更信息查询-消费者企业通用
		"getImptChangeInfo" : "callCenter/getImptChangeInfo",
		//手机号码归属地查询
		"getMobilePhoneCity":"callCenter/getMobilePhoneCity",
		//呼叫中心，报表统计导出
		"excelExport":"callCenter/excelExport",
		//年费信息查询
		"getAnnualFeeInfo":"callCenter/queryAnnualFeeInfo",
		//操作许可查询(服务公司，企业，消费者)
		"queryBusinessPmInfo":"callCenter/queryBusinessPmInfo",
		/************************	呼叫中心 end 	************************/
		
		/*********************互动信息 start*********************/
		//互动信息-查询分组及用户
		"findTaskGroupInfo" : "operator/findTaskGroupInfo",
        //互动信息-查询历史记录
    	"queryMessageRecordList" : "message//queryMessageRecordList",
    	//互动信息-查询最近联系人
    	"queryRecentContactsMsgList" : "message/queryRecentContactsMsgList"
    	/*********************互动信息 end*********************/
	};
	return comm.UrlList;
});