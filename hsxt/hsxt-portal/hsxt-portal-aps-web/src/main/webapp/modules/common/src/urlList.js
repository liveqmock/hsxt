define(function() {
	comm.UrlList = {
		/** *公共URL** */
		// 查询所有的国家信息
		"findContryAll" : "commController/find_contry_all",

		// 根据国家查询下属省份
		"findProvinceByParent" : "commController/find_province_by_parent",

		// 查询所有的银行列表
		"findBankAll" : "commController/findBankAll",

		// 获取本平台基本信息
		"findSystemInfo" : "commController/findSystemInfo",

		// 根据国家省份查询下级城市
		"findCityByParent" : "commController/find_city_by_parent",

		// 持卡人登录后获取随机报文
		"findCardHolderToken" : "commController/find_card_holder_token",

		// 公共部分-根据国家代码-省份代码-城市代码获取对应名称
		"getRegionByCode" : "commController/getRegionByCode",

		// 公共部分-根据国家代码获取省份城市信息
		"findProvCity" : "commController/findProvCity",
		
		//获取地区平台信息
		"get_localInfo":"commController/get_localInfo",
		
		//公共部分-获取示例图片、常用业务文档、办理业务文档列表
		"findDocList" : "commController/findDocList",
		
		//公共部分-根据货币代码查询货币信息
		"findCurrencyByCode" : "commController/findCurrencyByCode",
		
		//公共部分-根据客户号查询操作员名称
		"searchOperByCustId":"commController/searchOperByCustId",
		
		//公共部分-依据企业互生号查询企业信息
		"findMainInfoByResNo":"commController/findMainInfoByResNo",

		"findPermissionByCustidList":"commController/findPermissionByCustidList",
		//工单拒绝受理
		"work_task_refuse_accept":"commController/work_task_refuse_accept",
		//工单挂起
		"work_task_hang_up":"commController/work_task_hang_up",
		//查询企业扩展信息
		"find_company_ext_info":"commController/find_company_ext_info",
		//查询企业一般信息
		"find_company_base_info":"commController/find_company_base_info",
		//查询企业重要信息
		"find_company_main_info":"commController/find_company_main_info",
		//查询企业所有信息
		"find_company_all_info":"commController/find_company_all_info",
		//查询企业状态信息
		"find_company_status_info":"commController/find_company_status_info",
		//根据企业资源号获取企业重要信息
		"find_company_main_info_by_res_no":"commController/find_company_main_info_by_res_no",
		//查询企业的联系信息
		"find_company_contact_info":"commController/find_company_contact_info",
		//查询企业客户号 ,通过企业互生号
		"find_company_cust_id_by_res_no":"commController/find_company_cust_id_by_res_no",	
		// 上传证件图片
		// "upload" : "filesOperation/credencePicUpload",
		// 上传文件
		// "fileupload" : "filesOperation/fileUpload",
		// 获取验证码
		"generateSecuritCode" : "commController/generateSecuritCode",
		/** ***获得企业重要信息的所有证件的图片在服务器上的ID***** */
		"findSamplePictureId" : "importantInfo/findSamplePictureId",
		// 加载验证码
		"loadVerifiedCode" : "realNameAuth/loadVerifiedCode",
		// 验证双签登陆
		"verifyDoublePwd" : "commController/verifyDoublePwd",

		// 初审业务
		'tgqycsyw' : 'modules/coDeclaration/dat/csyw/tgqycsyw.asp',
		// 上传证件图片
		"upload" : "fileController/credencePicUpload",
		// 上传文件
		"fileupload" : "fileController/fileUpload",
		/** 工具制作管理 * */
		// 工具上传文件
		"toolfileupload" : "fileController/tool_fileUpload",
		// 工具制作管理 --工具订单列表
		'tool_order_list' : 'toolorder/list',
		// 查看订单详情
		'tool_order_detail' : 'toolorder/query_detail',
		// 确认订单
		'tool_confirm' : 'toolorder/confirm_order',
		// 关闭订单
		'tool_close' : 'toolorder/close_order',

		"tgqycsyw" : 'modules/coDeclaration/dat/csyw/tgqycsyw.asp',
		//工具制作管理-个性卡定制查询列表
		"findSpecialCardStyleList" : 'specialCardController/list',
		//工具制作管理-根据订单号查询互生卡样
		"findCardStyleByOrderNo" : 'specialCardController/findCardStyleByOrderNo',
		//工具制作管理-上传卡样制作文件
		"uploadCardStyleMarkFile" : 'specialCardController/uploadCardStyleMarkFile',
		//售后订单管理-添加售后服务发货单
		"addToolShippingAfter" : 'toolDispatchingController/addToolShippingAfter',
		//售后订单管理-查询发货单的数据
		"findAfterShipingData" : 'toolDispatchingController/findAfterShipingData',
		//售后订单管理-分页查询售后工具配送配送单
		"findShipingList" : 'toolDispatchingController/list',
		//售后订单管理-消查询发货清单
		"findShippingAfterById" : 'toolListPrintController/findShippingAfterById',
		//售后订单管理-售后工具配送列表
		"findShippingAfterList" : 'toolListPrintController/list',
		//售后订单管理-企业信息查询
		"findCompanyInfo" : 'toolListPrintController/findCompanyInfo',
		//查询售后发货清单列表
		"findPrintListResult" : "toolListPrintController/findPrintListResult",
		//查询售后发货清单打印信息
		"findPrintDetailAfterById" : "toolListPrintController/findPrintDetailAfterById",
		//工具制作管理-售后订单管理-售后工具列表
		"queryEntDeviceList"	:	"recylePointTools/queryEntDeviceList",
		//工具制作管理-售后订单管理-上传订单文件
		"uploadSeqFile"			:	"recylePointTools/uploadSeqFile",
		//工具制作管理-售后订单管理-批量导入重新关联
		"batchUpload"			:	"recylePointTools/batchUpload",
		//工具制作管理-售后订单管理-售后订单审核列表
		"queryAfterOrderApprPage"	:	"afterPaidOrderAppr/list",
		//工具制作管理-售后订单管理-售后审核订单查询
		"queryAfterServiceByNo"		:	"afterPaidOrderAppr/searchOrderDetail",
		//工具制作管理-售后订单管理-售后订单审核
		"apprAfterService"		:	"afterPaidOrderAppr/apprAfterService",
		//工具制作管理-售后订单管理-挂起
		"suspendOrder" : "afterPaidOrderAppr/suspend", // 工单挂起
		//工具制作管理-售后订单管理-拒绝受理
		"rejectOrder" : "afterPaidOrderAppr/door", // 工单拒绝受理
		//工具制作管理-售后订单管理-售后工具制作查询
		"queryAfterConfigDetail"		:	"afterPaidToolMake/queryAfterConfigDetail",
		//工具制作管理-售后订单管理-添加售后订单
		"addAfterPaidOrder"		:	"recylePointTools/addAfterPaidOrder",
		//工具制作管理-售后订单管理-售后工具制作
		"queryToolMakeAfterPaid":	"afterPaidToolMake/list",
		//工具制作管理-售后订单管理-重新关联
		"reassociation"			:	"afterPaidToolMake/reassociation",
		//工具制作管理-售后订单管理-保持关联
		"keepAssociation"		:	"afterPaidToolMake/keepAssociation",
		//分页查询售后订单
		"find_after_order_plat_page":"afterPaidOrderAppr/find_after_order_plat_page",

		/** 资源配额管理 start */
		"getEntResList" : "res_quota_common/get_ent_res_list", // 获取地区平台下的管理公司
		"getEntResDetail" : "res_quota_common/get_ent_res_detail", // 获取管理公司详情
		"manageAllotDetail" : "res_quota_common/manage_allot_detail", // 管理公司配额分配详情
		"cityResStatusDetail" : "res_quota_common/city_res_status_detail", // 城市资源状态详情

		"listTable" : "res_data_query/list_table", // 数据资源一览表
		"resQuotaQuery" : "res_data_query/res_quota_query", // 资源配额查询
		"applyPlatQuotaPage" : "area_quota_query/apply_plat_quota_page", // 一级区域配额申请分页查询
		"applyPlatQuotaQueryPage" : "area_quota_query/apply_plat_quota_query_page", // 一级区域配额申请查询分页查询
		"applyPlatQuotaDetail" : "area_quota_query/apply_plat_quota_detail", // 查询一级区域(地区平台)配额分配详情
		"getProvinceNoAllot" : "area_quota_query/get_province_no_allot", // 查询管理公司可以进行分配或调整配额的二级区域
		"provinceQuotaPage" : "area_quota_query/province_quota_page", // 二级区域配额配置查询
		"provinceQuotaDetail" : "area_quota_query/province_quota_detail", // 二级区域配额配置详情
		"allocatedQuotaProvinceQuery" : "area_quota_query/allocated_quota_province_query", // 查询地区平台分配了配额的省
		"cityQuotaApplyPage" : "area_quota_query/city_quota_apply_page", // 三级区域配额配置申请分页查询
		"cityQuotaApplyQeryPage" : "area_quota_query/city_quota_apply_qery_page", // 三级区域配额配置查询分页查询
		"cityQuotaDetail" : "area_quota_query/city_quota_detail", // 三级区域配额配置详情
		"statQuotaByCity" : "area_quota_query/stat_quota_by_city", // 统计城市配额分配使用情况
		

		"applyPlatQuota" : "area_quota_operate/apply_plat_quota", // 一级区域配额申请
		"provinceQuotaApply" : "area_quota_operate/province_quota_apply", // 二级区域配额申请
		"provinceQuotaApprove" : "area_quota_operate/province_quota_approve", // 二级区域配额审批
		"cityQuotaUpdate" : "area_quota_operate/city_quota_update", // 三级区域配额修改
		"cityQuotaInit" : "area_quota_operate/city_quota_init", // 三级区域配额初始化
		"cityQuotaApprove" : "area_quota_operate/city_quota_approve", // 三级区域配额审批
		/** 资源配额管理 end */

		/** 收款管理 start */
		"businessOrderPage" : "receivManage/business_order_page", // 订单查询
		"getTempAcctPayInfo" : "receivManage/get_tempAcct_payInfo", // 获取订单临帐支付详情
		"exportBusinessOrder" : "receivManage/export_business_order", // 订单导出
		"getOrderDetail" : "receivManage/get_order_detail", // 查找订单明细
		"closeOrder" : "receivManage/close_order", // 订单关闭
		"annualFeeOrderPage" : "receivManage/annual_fee_order_page", // 分页查询年费业务单
		"findOrderOperator" : "receivManage/findOrderOperator", // 分页查询年费业务单
		// queryAnnualFeeOrder : "receivManage/query_annual_fee_order", //
		// 查找年费订单明细
		"debtOrderPage" : "receivManage/debt_order_page", // 系统销售费分页查询
		//网银收款对账-收款管理数据对帐
		"dataReconciliation" : "paymentOrderController/dataReconciliation",
		//网银收款对账-收款管理订单详情
		"findPaymentOrderInfo" : "paymentOrderController/findPaymentOrderInfo",
		//网银收款对账-收款管理订单列表
		"findPaymentOrderList" : "paymentOrderController/list",
		
		/** 收款管理 end */

		/** 工单管理 start */
		"workOrderPage" : "workOrder/work_order_page", // 工单分页查询
		"workOrderTermination" : "workOrder/termination", // 工单终止
		"workOrderToDo" : "workOrder/to_do", // 工单转入待办
		"workOrderSuspend" : "workOrder/suspend", // 工单挂起
		"workOrderReminders" : "workOrder/reminders", // 工单催办
		"workOrderDoor" : "workOrder/door", // 工单拒绝受理
		"getAttendantList" : "workOrder/get_attendant_list", // 值班员列表
		"getBizAuthList" : "workOrder/get_bizAuth_list", // 查询地区平台业务类型列表
		"getTmTaskByBizNo" : "workOrder/getTmTaskByBizNo", // 根据业务编号获取工单明细

		"getGroupList" : "schedule/get_group_list", // 获取值班组信息
		"getMembersSchedule" : "schedule/get_members_schedule", // 获取工作组下组员日程安排
		"getGroupInfo" : "schedule/get_groupInfo", // 查询值班组信息
		"saveGroup" : "schedule/save_group", // 保存值班组
		"addBizType" : "schedule/add_biztype", // 新增值班员业务节点
		"deleteBizType" : "schedule/delete_biztype", // 删除值班员业务节点
		"removeOperator" : "schedule/remove_operator", // 移除值班员
		"udpateGroupStatus" : "schedule/udpate_group_status", // 值班组开启或关闭
		"groupAdd" : "schedule/group_add", // 工作组添加
		"groupUpdate" : "schedule/group_update", // 工作组添加
		"getAttendantInfo" : "schedule/get_attendant_info", // 获取值班员明细
		"executeSchedule" : "schedule/execute_schedule", // 执行值班计划
		"pauseSchedule" : "schedule/pause_schedule", // 暂停值班组计划
		"saveSchedule" : "schedule/save_schedule", // 保存值班计划
		"getEntOperList" : "schedule/get_ent_oper_list", // 获取企业员工
		"exportSchedule" : "schedule/export", // 导出值班计划
		"getBizTypeList" : "schedule/get_biz_type_list", // 获取操作员对应的业务类型
		"isChief" : "schedule/is_chief", // 操作员是否为值班主任

		/** 工单管理 end */

		/** 刷卡工具制作* */
		// 刷卡工具制作列表
		'swipecard_list' : 'swipecard/list',
		//刷卡工具配置单
		'queryServiceToolConfig_list' : 'swipecard/queryServiceToolConfigPage',
		// 终端列表
		'terminal_list' : 'swipecard/terminallist',
		// 添加关联之前的校验
		'add_relation' : 'swipecard/addrelation',
		//批量添加关联
		'add_batch_relation':'swipecard/addbatchrelation',
		//查询设备关联详情
		'queryDeviceRelevanceDetail':'swipecard/queryDeviceRelevanceDetail',
		// 查询企业重要信息 by客户号
		'query_entmaininfo' : 'entinfo/query_entmain',
		// 查询企业重要信息 by互生号
		'query_entmaininfo_byresno' : 'entinfo/query_entmain_byresno',
		// 互生卡制作列表
		'hscardmade_list' : 'hscardmade/list',
		// 查询互生卡密码
		'hscardmade_password_list' : 'hscardmade/findcardpwd',
		//互生卡发放申请
		'hscardtoolapply':'hscardtoolapply/queryCardProvideApplyByPage',
		// 开卡
		'hscardmade_opencard' : 'hscardmade/opencard',
		// 关闭订单
		'hscardmade_closeorder' : 'hscardmade/closeorder',
		// 制作单作成查询
		'hscardmade_zzdzc' : 'hscardmade/zzdzc',
		// 互生卡制作单作成
		'hscardmade_cardmark' : 'hscardmade/cardmark',
		// 查询卡制作数据
		'hscardmade_querycardmark' : 'hscardmade/querycardmark',
		// 查询互生卡配置卡样
		'hscardmade_querycardstyle' : 'hscardmade/querycardstyle',
		// 卡样上传
		'hscardmade_uploadcardstyle' : 'hscardmade/uploadcardstyle',
		// 互生卡入库查询
		'hscardmade_querycardin' : 'hscardmade/querycardin',
		// 互生卡入库
		'hscardmade_cardin' : 'hscardmade/cardin',
		// 导出密码
		'hscardmade_exportpassword' : 'hscardmade/exportmm',
		// 修改卡样解锁状态
		'hscardmade_modifycarylock' : 'hscardmade/modifycarylock',
		// 制作卡数据下载
		'hscardmade_exportdark' : 'hscardmade/exportdark',
		// 工具清单打印 查询列表
		'query_toollistprint' : 'toollistprint/list',
		// 工具清单打印 查询打印的详细信息
		'findPrintDetailById' : 'toollistprint/findPrintDetailById',
		// 工具配送管理列表查询
		'query_tooldispatching_list' : 'tooldispatching/list',
		// 查询配送信息
		'query_tooldispatching_detail' : 'tooldispatching/querytooldispatch',
		// 查看配送单
		'query_queryshipping_detail' : 'tooldispatching/queryshipping',
		// 查询工具数量
		'tooldispatching_query_tool_num' : 'tooldispatching/querytoolnum',
		// 添加配送单
		'add_tooldispatching' : 'tooldispatching/addtooldispatch',

		/** 客户信息管理 * */
		// 消费者资源一览
		'resourcedirect_person_page' : 'person_resour_cedirect/listConsumerInfo',  //消费者资源一览分页查询
		'person_resource_dowload' : 'person_resour_cedirect/person_resource_dowload',  //消费者资源下载
		'resourcedirect_listConsumerInfo' : 'resourcedirect/listConsumerInfo',
		'resourcedirect_queryConsumerAllInfo' : 'resourcedirect/queryConsumerAllInfo',
		'resourcedirect_queryConsumerBanks' : 'resourcedirect/listBanksByCustId',
		'resourcedirect_queryConsumerQkBanks' : 'resourcedirect/listQkBanksByCustId',
		'ent_resource_dowload' : 'resourcedirect/ent_resource_dowload',	 //企业资源下载
		

		// 托管企业 成员企业、服务公司详情
		'resourceFindEntAllInfo' : 'resourcedirect/resourceFindEntAllInfo',
		// 账户资源管理-企业资源
		'findResEntInfoList' : 'resAccountManageController/list',
		// 资源名录-企业资源
		'findReportResEntInfoList' : 'resAccountManageController/findReportResEntInfoList',
		// 资源名录-消费者资源
		'findResReportConsumerInfoList' : 'resAccountManageController/findReportConsumerInfoList',
		// 账户资源管理-消费者资源
		'findResConsumerInfoList' : 'resAccountManageController/findConsumerInfoList',

		// 托管企业 成员企业、服务公司一览
		'resourcedirect_list' : 'resourcedirect/list',
		// 点击互生号查看详细信息
		'resourcedirect_detail' : 'resourcedirect/queryentinfo',
		// 成员企业资格注销审批查询列表
		'membercompquit_list' : 'membercompquit/list',
		// 成员企业资格注销审批列表
		'membercompquit_apprlist' : 'membercompquit/appr_list',
		// 成员企业资格注销审批 详情
		'membercompquit_detail' : 'membercompquit/query_detail',
		// 提交资格注销申请
		'membercompquit_approval' : 'membercompquit/approval',
		// 复核资格注销申请
		'membercompquit_review' : 'membercompquit/review',
		// 积分活动审批查询列表
		'activityapply_list' : 'activityapply/list',
		// 积分活动审批列表
		'activityapply_apprlist' : 'activityapply/appr_list',
		// 查询积分活动详细信息
		'activityapply_query_detail' : 'activityapply/query_detail',
		// 提交积分活动审批
		'activityapply_apprval' : 'activityapply/approval',
		// 提交积分活动复核
		'activityapply_review' : 'activityapply/review',
		// 企业实名认证列表
		'entrealnameidentific_list' : 'entrealnameidentific/list',
		// 企业实名认证审批列表
		'entrealnameidentific_apprlist' : 'entrealnameidentific/appr_list',
		// 企业实名认证所有的审批信息列表
		'entrealnameidentific_pagelist' : 'perrealnameidentific/page_ent_appr',
		// 查询企业实名认证详情
		'query_entrealnameidentific' : 'entrealnameidentific/query_detail',
		// 审批企业实名认证
		'appr_entrealnameidentific' : 'entrealnameidentific/appr',
		// 修改企业实名认证信息
		'modify_entrealnameidentific' : 'entrealnameidentific/modify',
		// 复核企业实名认证
		'review_entrealnameidentific' : 'entrealnameidentific/review',
		// 查询企业实名认证状态列表信息
		'entrealnameidentific_status_list' : 'entrealnameidentific/status_list',
		// 消费者实名认证列表
		'perrealnameidentific_list' : 'perrealnameidentific/list',
		// 消费者实名认证审批列表
		'perrealnameidentific_apprlist' : 'perrealnameidentific/appr_list',
		// 消费者实名认证所有的审批信息列表
		'perrealnameidentific_pagelist' : 'perrealnameidentific/page_appr',
		// 查询消费者实名认证详情
		'query_perrealnameidentific' : 'perrealnameidentific/query_detail',
		// 审批消费者实名认证
		'appr_perrealnameidentific' : 'perrealnameidentific/appr',
		// 复核消费者实名认证
		'review_perrealnameidentific' : 'perrealnameidentific/review',
		// 修改消费者实名认证信息
		'modify_perrealnameidentific' : 'perrealnameidentific/modify',
		// 查询消费者实名认证状态列表信息
		'perrealnameidentific_status_list' : 'perrealnameidentific/status_list',
		// 消费者重要信息变更列表
		'perimportantinfochange_list' : 'perimportantinfochange/list',
		// 消费者重要信息变更审批列表
		'perimportantinfochange_apprlist' : 'perimportantinfochange/appr_list',
		// 审批消费者重要信息
		'appr_perimportantinfochange' : 'perimportantinfochange/appr',
		// 复核消费者重要信息
		'review_perimportantinfochange' : 'perimportantinfochange/review',
		// 查询消费者重要信息申请详细
		'query_perimportantinfochange' : 'perimportantinfochange/query_detail',
		// 修改消费者重要信息申请
		'modify_perimportantinfochange' : 'perimportantinfochange/modify',
		// 查询消费者重要信息状态列表
		'perimportantinfochange_status_list' : 'perimportantinfochange/status_list',
		// 企业重要信息变更列表
		'entimportantinfochange_list' : 'entimportantinfochange/list',
		// 企业重要信息变更审批列表
		'entimportantinfochange_apprlist' : 'entimportantinfochange/appr_list',
		// 审批企业重要信息
		'appr_entimportantinfochange' : 'entimportantinfochange/appr',
		// 复核企业重要信息
		'review_entimportantinfochange' : 'entimportantinfochange/review',
		// 修改企业重要信息
		'modify_entimportantinfochange' : 'entimportantinfochange/modify',
		// 查询企业重要信息申请
		'query_entimportantinfochange' : 'entimportantinfochange/query_detail',
		// 查询企业重要信息状态列表
		'entimportantinfochange_status_list' : 'entimportantinfochange/status_list',
		
		/****************************客户信息管理补充  *********************/
		
		//业务许可信息查询
		"findBusinessPmInfo":"resourcedirect/findBusinessPmInfo",
		//业务许可信息设置
		"setBusinessPmInfo":"resourcedirect/setBusinessPmInfo",
		//消费者重要信息变更审批查询
		"queryPerImportSpInfo":"perimportantinfochange/list",
		//企业重要信息变更审批查询
		"queryEntImportSpInfo":"entimportantinfochange/list",
		//修改企业的一般信息
		"updateEntBaseInfos":"resourcedirect/updateEntBaseInfo",
		//关联企业信息管理查询
		"queryRelateCompanyList":"relateCompanyManage/list",
		//关联消费者信息管理查询
		"queryRelateConsumerInfo":"relateConsumerManage/list",
		//修改企业的一般信息,并保存记录
		"updateRelEntBaseInfo":"relateCompanyManage/updateEntBaseInfo",
		//查看企业信息
		"searchEntExtInfo":"relateCompanyManage/searchEntExtInfo",
		//企业系统信息-查询银行卡
		"findBanksByBelongCustId":"relateCompanyManage/findBanksByBelongCustId",
		//企业系统信息-添加银行卡，并保存记录
		"addBankInfo":"relateCompanyManage/UpdateEntBankLog",
		//企业系统信息-删除银行卡，并保存记录
		"delBankInfo":"relateCompanyManage/UpdateEntBankLog",
		//查看企业的修改记录信息
		"queryEntInfoBak":"relateCompanyManage/queryEntInfoBak",
		//查询消费者所有信息
		"searchConsumerAllInfo":"relateConsumerManage/searchConsumerAllInfo",
		//修改消费者信息
		"updateConsumerInfo":"relateConsumerManage/updateConsumerInfo",
		//查看消费者的修改记录信息
		"queryConsumberInfoBakList":"relateConsumerManage/queryConsumberInfoBakList",
		//企业上传资料验证码校验
		"validateSmsCode":"relateCompanyManage/validateSmsCode",
		/****************************客户信息管理    *********************/
		
		/** *****************平台代购工具 ***************************** */
		// 查询可以购买的工具类型
		'query_tool_type' : 'platform/query_tooltype',
		// 查询工具类别可以购买的数量
		'query_tool_num' : 'platform/query_toolnum',
		// 查询联系地址、企业地址
		'query_address' : 'platform/query_address',
		// 查询卡样列表
		'query_car_style' : 'platform/query_car_style',
		// 提交平台代购订单
		'add_plat_proxyorder' : 'platform/addplatproxyorder',
		// 查询平台代购工具复核列表
		'query_proxyorder_auditing_list' : 'auditing/list',
		// 查询代购工具订单详情
		'query_proxyorder_auditing_detail' : 'auditing/queryproxyorder',
		// 审核代购工具订单
		'auditing_proxyorder' : 'auditing/auditingproxyorder',
		/** *******企业系统信息******** */
		// 系统登记信息
		"findSysRegisterInfo" : "systemRegister/findSysRegisterInfo",
		// 工商登记 信息查询
		"findSaicRegisterInfo" : "saicRegister/findSaicRegisterInfo",
		// 工商登记信息,联系人信息信修改
		"updateSaicRegisterInfo" : "saicRegister/updateSaicRegisterInfo",
		"updateEntBaseInfo" : "saicRegister/updateEntBaseInfo",

		/** ******联系人信息******** */
		// 联系人信息查询
		"findContactInfo" : "contactInfo/findContactInfo",
		// 更新联系人信息
		"updateContactInfo" : "contactInfo/updateContactInfo",
		// 邮箱验证
		"checkMailCode" : "contactInfo/checkEmailCode",
		// 发送验证邮箱的邮件
		"validEmail" : "contactInfo/validEmail",

		/** 实名认证* */
		// 实名认证信息查询
		"findRealNameAuthInfo" : "realNameAuth/findRealNameAuthInfo",
		// /***文件上传****/
		"checkVerifiedCode" : "realNameAuth/checkVerifiedCode",
		// 加载图片
		"loadPicture" : "realNameAuth/loadPicture",
		// 提交
		"submitRealNameAuthData" : "realNameAuth/submitRealNameAuthData",
		"findRealNameAuthApply" : "realNameAuth/findRealNameAuthApply",

		/** *银行帐户** */
		// 银行卡列表
		"findCardList" : "bankAccountInfo/findCardList",
		// 删除银行帐号
		"delBankAccountInfo" : "bankAccountInfo/delBankAccountInfo",
		// 修改银行帐户信息
		"updateBankAccountInfo" : "bankAccountInfo/updateBankAccountInfo",
		// 绑定银行卡
		"addBankCard" : "bankAccountInfo/addBankCard",
		// 解除银行卡绑定
		"delBankCard" : "bankAccountInfo/delBankCard",
		// 查询银行列表
		"findApplyBankList" : "bankAccountInfo/findApplyBankList",
		// 查询省份列表
		"findProvinceList" : "bankAccountInfo/findProvinceList",
		// 查询城市列表
		"findCityList" : "bankAccountInfo/findCityList",

		/** ********安全设置******* */
		"findLoginInfo" : "safeSetController/findLoginInfo",
		"updatePwd" : "safeSetController/updatePwd",

		/** **系统用户管理*** */
		// 查找所有操作员
		"findOperatorList" : "operatorController/list",
		// 增加操作员
		"addOperator" : "operatorController/addOperator",
		// 更新操作员信息
		"updataOperatorInfo" : "operatorController/updataOperatorInfo",
		// 删除操作员
		"delOperator" : "operatorController/delOperator",
		// 绑定互生卡
		"bindHsCard" : "operatorController/bindHsCard",
		// 解除绑定的互生卡
		"removeHsCard" : "operatorController/removeHsCard",
		"updataOperRoles" : "operatorController/updataOperRoles",
		// 操作员的变更，包括新增、修改、设置角色功能
		"operChange" : "operatorController/operChange",

		// 角色列表查找
		"findRoleList" : "roleManage/findRoleList",
		"deleteRole" : "roleManage/deleteRole",
		"findMenus" : "permissionController/findMenuList",
		"findFunList" : "permissionController/findFunList",
		"addRole" : "roleManage/addRole",
		"setPermissionForRole" : "roleManage/setPermissionForRole",

		// 用户组
		"findGroups" : "groupController/list",
		"delUserFromGroup" : "groupController/delUserFromGroup",
		"delGroup" : "groupController/delGroup",
		"updGroup" : "groupController/updGroup",
		"addGroup" : "groupController/addGroup",
		"addOperInGroup" : "groupController/addOperInGroup",

		/**
		 * ******************************临账管理
		 * start*******************************
		 */
		// 收款账户名称管理
		"createAccount" : "receiveAccountName/create",
		"listAccount" : "receiveAccountName/list",
		"updateAccout" : "receiveAccountName/update",
		"getAccountById" : "receiveAccountName/findById",
		"listAccountMemu" : "receiveAccountName/getAccountMenu",

		// 收款账户管理
		"createAccounting" : "receiveAccount/create",
		"listAccounting" : "receiveAccount/list",
		"updateAccouting" : "receiveAccount/update",
		"getAccountingById" : "receiveAccount/findById",
		"listAccountingMemu" : "receiveAccount/getAccountingMenu",
		"forbidAccountInfo" : "receiveAccount/forbidAccountInfo",

		// 临账录入、查看、更新，列表，录入复核，转不动款，不动款统计,复核列表
		"createDebit" : "tempDebit/create",
		"getDebitById" : "tempDebit/findById",
		"updateDebit" : "tempDebit/update",
		"listDebit" : "tempDebit/list",
		"approveDebit" : "tempDebit/approve",
		"turnNotMove" : "tempDebit/turnNotMovePayment",
		"notMoveSum" : "tempDebit/notMoveSum",
		"queryDebitTaskListPage" : "tempDebit/queryDebitTaskListPage",
		"queryDebitListByQuery" : "tempDebit/queryDebitListByQuery",

		// 临账退款申请、查看、申请退款列表、退款审核
		"createRefundDebit" : "refundTempDebit/create",
		"getRefundDebitById" : "refundTempDebit/findById",
		"listRefundDebit" : "tempDebit/list",
		"approveRefundDebit" : "refundTempDebit/approve",

		// 临账关联:查询未付款订单，申请关联,复核临账关联，根据订单编号查询对应的临账关联申请，关联未复核订单列表,关联的订单列表,
		"listNopayOrder" : "debitLink/list",
		"createDebitLink" : "debitLink/create",
		"approveDebitLink" : "debitLink/approve",
		"findDebitLinkByOrderNo" : "debitLink/findByOrderNo",
		"noApproveList" : "debitLink/noApproveList",
		"queryTempOrderByDebitId" : "debitLink/queryTempOrderByDebitId",
		
		// 根据订单ID查看订单详情
		"invoiceGetOrderByOrderId" : "debitLink/getOrderByOrderId",
		"queryTempAcctLinkListByDebitId" : "debitLink/queryTempAcctLinkListByDebitId",
		"queryDebitLinkInfo" : "debitLink/queryDebitLinkInfo",
		
		// 临账统计 查看 导出
		"listSumDebit" : "tempDebitSum/list",
		"getSumDetail" : "tempDebitSum/getSumDetail",
		"exporTempDebitSum" : "tempDebitSum/exporTempDebitSum",

		/** ******************************临账管理 end******************************* */

		/**
		 * ******************************发票管理
		 * start*******************************
		 */
		// 税率调整 审核，查看，列表,,审批列表
		"apprTaxrateChange" : "taxrateChange/approve",
		"getTaxrateChangeById" : "taxrateChange/findById",
		"listTaxrateChange" : "taxrateChange/list",
		"approveTaxrateChangeList" : "taxrateChange/approveTaxrateChangeList",

		// 发票管理
		// 发票列表
		"listInvoice" : "receiveInvoice/list",
		// 根据ID查询发票详情
		"getInvoiceById" : "receiveInvoice/findById",
		// 签收发票
		"signInvoice" : "receiveInvoice/sign",
		// 发票池列表
		"invoicePoolList" : "receiveInvoice/invoicePoolList",
		// 平台开发票
		"platCreateInvoice" : "receiveInvoice/create",
		// 修改配送方式
		"changePostWay" : "receiveInvoice/changePostWay",
		// 审核驳回
		"rejectionInvoice" : "receiveInvoice/rejection",
		// 审核开发票
		"platApproveInvoice" : "receiveInvoice/platApproveInvoice",
		// 客户开发票
		"custOpenInvoice" : "receiveInvoice/custOpenInvoice",

		// 企业信息
		// 根据custId查询所有信息
		"invoiceGetAllCompanyInfor" : "invoiceCompanyInfor/findAllByCustId",
		// 根据custId查询重要信息
		"invoiceGetMainCompanyInfor" : "invoiceCompanyInfor/findMainByCustId",
		"findMainInfoDefaultBankByResNo":"invoiceCompanyInfor/findMainInfoDefaultBankByResNo",
		// 根据互生号查询重要信息
		"mainCompanyInforByResNo" : "invoiceCompanyInfor/findMainByResNo",
		// 查询企业状态
		"searchEntStatusInfo" : "invoiceCompanyInfor/searchEntStatusInfo",
		// 根据custId查询一般信息
		"invoiceGetBaseCompanyInfor" : "invoiceCompanyInfor/findBaseByCustId",
		// 根据custId查询默认银行账号
		"invoiceGetDefaultBankInfor" : "invoiceCompanyInfor/findDefaultBankByCustId",
		// 根据互生号查询custId
		"findEntCustIdByEntResNo" : "invoiceCompanyInfor/findEntCustIdByEntResNo",
		// 获取平台互生号
		"getPlatResNo" : "invoiceCompanyInfor/getPlatResNo",

		/** ******************************发票管理 end******************************* */

		/** ******************************企业申报管理模块******************************* */
		// 意向客户查询
		"findIntentionCustList" : "intentionCustController/list",
		// 查看意向客户申请资料
		"findIntentCustById" : "intentionCustController/findIntentCustById",
		// 授权码查询
		"findAuthCodeList" : "authCodeController/list",
		// 授权码发送
		"sendAuthCode" : "authCodeController/sendAuthCode",
		// 审批统计查询
		"findDeclareStatisticsList" : "entDeclareStatisticsController/list",
		//企业申报查询-查询申报进行步骤
		"findDeclareStep" : "entDeclareStatisticsController/findDeclareStep",
		//企业申报查询-删除申报信息
		"delUnSubmitDeclare" : "entDeclareStatisticsController/delUnSubmitDeclare",
		// 查询办理状态
		"findOperationHisList" : "entDeclareStatisticsController/findOperationHisList",
		// 审批统计查询-查询联系人信息
		"findLinkmanByApplyId" : "entDeclareStatisticsController/findLinkmanByApplyId",
		// 审批统计查询-查询工商登记信息
		"findDeclareEntByApplyId" : "entDeclareStatisticsController/findDeclareEntByApplyId",
		// 审批统计查询-查询企业资料上传
		"findAptitudeByApplyId" : "entDeclareStatisticsController/findAptitudeByApplyId",
		// 审批统计查询-查询系统注册信息
		"findDeclareByApplyId" : "entDeclareStatisticsController/findDeclareByApplyId",
		// 审批统计查询-查询银行账户信息
		"findBankByApplyId" : "entDeclareStatisticsController/findBankByApplyId",
		// 审批统计查询-查询申报信息
		"findDeclareAppInfoByApplyId" : "entDeclareStatisticsController/findDeclareAppInfoByApplyId",
		// 企业报备-企业报备审核查询
		"findApprFilingList" : "entFilingController/findApprFilingList",
		// 企业报备-异议报备审核查询
		"findDisagreedFilingList" : "entFilingController/findDisagreedFilingList",
		// 企业报备-报备信息查询
		"findFilingList" : "entFilingController/list",
		// 企业报备-查看报备详细信息
		"findFilingById" : "entFilingController/findFilingById",
		// 企业报备-地区平台审批企业报备
		"apprCommFiling" : "entFilingController/apprCommFiling",
		// 企业报备-地区平台审批企业异议报备
		"apprDisaFiling" : "entFilingController/apprDisaFiling",
		// 企业报备-查询企业基本信息
		"getEntFilingById" : "entFilingController/getEntFilingById",
		// 企业报备-初始化附件信息
		"initUpload" : "entFilingController/initUpload",
		// 企业报备-保存附件信息
		"saveAptitude" : "entFilingController/saveAptitude",
		// 企业报备-新增股东信息
		"createShareholder" : "entFilingController/createShareholder",
		// 企业报备-修改股东信息
		"updateShareholder" : "entFilingController/updateShareholder",
		// 企业报备-删除股东信息
		"deleteShareholder" : "entFilingController/deleteShareholder",
		// 企业报备-查询股东信息
		"findShareholderList" : "entFilingController/findShareholderList",
		// 保存企业报备
		"createEntFiling" : "entFilingController/createEntFiling",
		//公共部分-依据企业互生号查询企业信息
		"findSameItem":"entFilingController/findSameItem",
		// 企业新增-保存附件信息
		"saveDeclareAptitude" : "entDeclareController/saveDeclareAptitude",
		// 企业新增-保存企业联系信息
		"saveLinkInfo" : "entDeclareController/saveLinkInfo",
		// 企业新增-保存企业工商登记信息
		"saveDeclareEnt" : "entDeclareController/saveDeclareEnt",
		// 企业新增-保存企业银行账户息
		"saveBankInfo" : "entDeclareController/saveBankInfo",
		// 开启系统业务-列表查询
		"findOpenSysApprList" : "staffQueryController/list",
		// 开启系统业务-开启系统
		"openSystem" : "staffQueryController/openSystem",
		// 企业新增-查询企业服务公司互生号列表
		"findSerResNoList" : "entDeclareController/findSerResNoList",
		// 企业新增-查询管理公司信息和服务公司配额数
		"findManageEntAndQuota" : "entDeclareController/findManageEntAndQuota",
		// 企业新增-依据互生号查询企业信息
		"findEntInfo" : "entDeclareController/findEntInfo",
		// 企业新增-查询推广服务公司互生号资源详情
		"findResNoDetail" : "entDeclareController/findResNoDetail",
		// 企业新增-申报提交
		"submitDeclare" : "entDeclareController/submitDeclare",
		// 企业新增-刷新增值点信息
		"findIncrement" : "entDeclareController/findIncrement",
		// 企业新增-保存系统注册信息
		"saveDeclare" : "entDeclareController/saveDeclare",
		// 企业新增-校验企业互生号是否可以用
		"checkValidEntResNo" : "entDeclareController/checkValidEntResNo",
		// 企业新增-查询企业配额数和可用互生号列表
		"findResNoListAndQuota" : "entDeclareController/findResNoListAndQuota",
		// 申报信息维护-查询申报增值点列表
		"findIncrementList" : "entDeclareDefendController/list",
		// 申报信息维护-保存申报增值点信息
		"saveIncrement" : "entDeclareDefendController/saveIncrement",
		// 开系统欠款审核-查询
		"findOpenSysList" : "openSystemController/list",
		// 开系统欠款审核-审核
		"apprDebtOpenSys" : "openSystemController/apprDebtOpenSys",
		// 企业新增-查询服务公司可用互生号
		"findServicesPointList" : "entDeclareController/findSerResNos",
		// 企业新增-查询成员企业、托管企业可用互生号
		"findMemberPointList" : "entDeclareController/findEntResNos",

		/** ******************************工具后台管理模块******************************* */
		// 工具入库-查询所有仓库
		"findAllWarehouseList" : "warehouseInventoryController/findAllWarehouseList",
		// 工具入库-查询所有供应商
		"findAllupplierList" : "warehouseInventoryController/findAllupplierList",
		// 工具入库-查询工具列表
		"findToolProductList" : "warehouseInventoryController/findToolProductList",
		// 工具入库-查询工具列表(包含未上架 )
		"findToolProductAll" : "warehouseInventoryController/findToolProductAll",
		// 分页查询工具列表
		"findToolProductPage" : "warehouseInventoryController/findToolProductPage",
		//新增工具详情
		"addToolProduct":"warehouseInventoryController/addToolProduct",
		//修改工具详情
		"modifyToolProduct":"warehouseInventoryController/modifyToolProduct",
		//查询工具详情
		"queryToolProductInfo":"warehouseInventoryController/queryToolProductInfo",
		//根据申请编号查询上下架详情
		"queryToolProductUpDownById":"warehouseInventoryController/queryToolProductUpDownById",
		//工具价格修改
		"applyChangePrice":"warehouseInventoryController/applyChangePrice",
		//工具产品下架申请
		"applyToolProductToDown":"warehouseInventoryController/applyToolProductToDown",
		//查询工具下架审批列表
		"findToolDoawnList":"warehouseInventoryController/findToolDoawnList",
		//工具产品下架审批
		"apprToolProductForDown":"warehouseInventoryController/apprToolProductForDown",
		//查询工具价格审批列表
		"findToolPriceList":"warehouseInventoryController/findToolPriceList",
		//工具产品上架(价格)审批
		"apprToolProductForUp":"warehouseInventoryController/apprToolProductForUp",
		//工具价格审批，工具下架审批，拒绝受理，挂起
		"refuseAndHoldOperate":"warehouseInventoryController/refuseAndHoldOperate",
		//工具入库-入库提交
		// 工具入库-入库提交
		"productEnter" : "warehouseInventoryController/productEnter",
		// 工具入库-解析机器码文件
		"getDeviceSeq" : "warehouseInventoryController/getDeviceSeq",
		// 物流配送信息-配送方式查询
		"findLogisticsList" : "logisticsController/list",
		// 物流配送信息-添加配送方式
		"addShipping" : "logisticsController/addShipping",
		// 物流配送信息-删除配送方式
		"removeShipping" : "logisticsController/removeShipping",
		// 物流配送信息-修改配送方式
		"modifyShipping" : "logisticsController/modifyShipping",
		// 仓库信息-分页查询仓库
		"findWarehouseList" : "warehouseInventoryController/list",
		// 仓库信息-查询系统角色列表
		"findListRole" : "warehouseInventoryController/listRole",
		// 仓库信息-保存仓库
		"saveWarehouse" : "warehouseInventoryController/saveWarehouse",
		// 仓库信息-查看仓库信息
		"findWarehouseById" : "warehouseInventoryController/findWarehouseById",
		// 仓库信息-分页查询库存预警
		"findToolEnterStockWarningList" : "warehouseInventoryController/findToolEnterStockWarningList",
		// 仓库信息-工具查询
		"findToolDeviceUseList" : "warehouseInventoryController/findToolDeviceUseList",
		// 仓库信息-分页查询工具入库流水
		"findToolEnterSerialList" : "warehouseInventoryController/findToolEnterSerialList",
		// 仓库信息-分页查询工具出库流水
		"findToolOutSerialList" : "warehouseInventoryController/findToolOutSerialList",
		// 仓库信息-统计地区平台仓库工具
		"findStatisticPlatWhToollList" : "warehouseInventoryController/findStatisticPlatWhToollList",
		// 工具登记-设备序列号查询设备清单
		"findDeviceDetailByNo" : "warehouseInventoryController/findDeviceDetailByNo",
		// 工具登记-工具登记
		"addDeviceUseRecord" : "warehouseInventoryController/addDeviceUseRecord",
		// 库存统计
		"findConfigToolStockList" : "warehouseInventoryController/findConfigToolStockList",
		// 库存统计-查看机器码
		"findPosDeviceSeqNoDetail" : "warehouseInventoryController/findPosDeviceSeqNoDetail",
		// 库存统计-盘库
		"toolEnterInventory" : "warehouseInventoryController/toolEnterInventory",
		// 库存统计-入库抽检
		"toolEnterCheck" : "warehouseInventoryController/toolEnterCheck",
		// 工具配置信息-工具类别信息
		"listToolCategory" : "toolConfigController/all_list",
		// 工具配置信息-供应商列表
		"findSupplierList" : "toolConfigController/findSupplierList",
		// 工具配置信息-删除供应商
		"removeSupplier" : "toolConfigController/removeSupplier",
		//查询刷卡器导出记录
		"queryKsnExportRecord":"toolConfigController/queryKsnExportRecord",
		// 工具配置信息-依据供应商编号查询供应商详情
		"findSupplierById" : "toolConfigController/findSupplierById",
		// 工具配置信息-保存供应商
		"saveSupplier" : "toolConfigController/saveSupplier",
		// 工具配置信息-查询刷卡器KSN生成记录
		"findMcrKsnRecordList" : "toolConfigController/findMcrKsnRecordList",
		// 互生卡样-分页查询互生卡样
		"findCardStyleList" : "toolConfigController/findCardStyleList",
		// 互生卡样-添加互生卡样
		"addCardStyle" : "toolConfigController/addCardStyle",
		// 互生卡样-互生卡样的启用/停用
		"cardStyleEnableOrStop" : "toolConfigController/cardStyleEnableOrStop",
		// 互生卡样-查询互生卡样
		"findCardStyleById" : "toolConfigController/findCardStyleById",

		// 工具配置信息-查看积分KSN信息
		"findPointKSNInfo" : "toolConfigController/findPointKSNInfo",
		// 工具配置信息-查看消费KSN信息
		"findConsumeKSNInfo" : "toolConfigController/findConsumeKSNInfo",
		// 工具配置信息-导出积分KSN信息
		"exportPointKSNInfo" : "toolConfigController/exportPointKSNInfo",
		// 工具配置信息-导出消费KSN信息
		"exportConsumeKSNInfo" : "toolConfigController/exportConsumeKSNInfo",
		// 工具配置信息-生成积分KSN信息
		"createPointKSNInfo" : "toolConfigController/createPointKSNInfo",
		// 工具配置信息-生解析消费刷卡器KSN文件
		"parseKSNSeq" : "toolConfigController/parseKSNSeq",
		// 工具配置信息-导入消费刷卡器KSN
		"importConsumeKSNInfo" : "toolConfigController/importConsumeKSNInfo",

		/** ******************************平台信息******************************* */
		// 企业系统信息-查询企业的所有信息
		"findEntAllInfo" : "companyInfoController/findEntAllInfo",
		// 企业系统信息-更新企业联系信息
		"updateLinkInfo" : "companyInfoController/updateLinkInfo",
		// 企业系统信息-更新企业工商登记信息
		"updateEntExtInfo" : "companyInfoController/updateEntExtInfo",
		// 企业系统信息-添加银行卡
		"addBank" : "companyInfoController/addBank",
		// 企业系统信息-删除银行卡
		"delBank" : "companyInfoController/delBank",
		// 企业系统信息-查询银行卡
		"findBanksByCustId" : "companyInfoController/findBanksByCustId",

		/** ******************************业务文件管理******************************* */
		// 查询常用业务文档列表
		"findBusinessNormalDocList" : "businessDocController/findBusinessNormalDocList",
		// 查询办理业务文档列表
		"findBusinessBizDocList" : "businessDocController/findBusinessBizDocList",
		// 保存业务文件-示例图片
		"savePicDoc" : "businessDocController/savePicDoc",
		// 保存业务文件-常用业务文档
		"saveComDoc" : "businessDocController/saveComDoc",
		// 保存业务文件-办理业务申请书
		"saveBusDoc" : "businessDocController/saveBusDoc",
		// 发布文档
		"releaseBusinessDoc" : "businessDocController/releaseBusinessDoc",
		// 删除文档
		"deleteBusinessDoc" : "businessDocController/deleteBusinessDoc",
		// 停用文档
		"stopUsedBusinessDoc" : "businessDocController/stopUsedBusinessDoc",
		// 查询文档详情
		"findBusinessDocInfo" : "businessDocController/findBusinessDocInfo",
		// 获取示例图片版本
		"findBusinessImageDocHis" : "businessDocController/findBusinessImageDocHis",
		// 获取示例图片管理列表
		"findBusinessImageDocList" : "businessDocController/findBusinessImageDocList",
		// 恢复示例图片版本
		"recoveryBusinessImageDoc" : "businessDocController/recoveryBusinessImageDoc",
		
		/** ******************************服务消息管理******************************* */
		//消息模版列表
		"findMessageTplList" : "serviceMsgManageController/list",
		//消息模版审批列表
		"findMessageTplApprList" : "serviceMsgManageController/findMessageTplApprList",
		//保存消息模版
		"saveMessageTpl" : "serviceMsgManageController/saveMessageTpl",
		//查询模版详情
		"findMessageTplInfo" : "serviceMsgManageController/findMessageTplInfo",
		//复核模版
		"reviewTemplate" : "serviceMsgManageController/reviewTemplate",
		//启用/停用模版
		"startOrStopTpl" : "serviceMsgManageController/startOrStopTpl",
		//模版审批历史列表
		"findMsgTplApprHisList" : "serviceMsgManageController/findMsgTplApprHisList",
		//分页查询短信消息发送
		"findNoteByPage" : "serviceMsgManageController/findNoteByPage",
		//分页查询邮件消息发送
		"findEmailByPage" : "serviceMsgManageController/findEmailByPage",
		//分页查询业务互动消息发送
		"findDynamicBizByPage" : "serviceMsgManageController/findDynamicBizByPage",

		/** ***************** 积分福利管理 start ********************* */

		// --------------------积分福利资格查询-----------------------
		// 分页查询积分福利资格
		"listWelfareQualify" : "welfareQualify/listWelfareQualify",
		// 查询个人福利资格信息
		"findWelfareQualify" : "welfareQualify/findWelfareQualify",
		// 查询用户是否有福利资格
		"isHaveWelfareQualify" : "welfareQualify/isHaveWelfareQualify",

		// -------------------- 积分福利审批 -----------------------
		// 积分福利审批
		"approvalWelfare" : "welfareApproval/approvalWelfare",
		// 查询待审批记录
		"listPendingApproval" : "welfareApproval/listPendingApproval",
		// 查询已审批记录
		"listApprovalRecord" : "welfareApproval/listApprovalRecord",
		// 查询审批详情
		"queryApprovalDetail" : "welfareApproval/queryApprovalDetail",
		// 更新任务状态
		"updateWsTask" : "welfareApproval/updateWsTask",

		// -------------------- 理赔核算 -----------------------
		// 查询待核算理赔单列表
		"listPendingClaimsAccounting" : "welfareClaimsAccounting/listPendingClaimsAccounting",
		// 查询已核算理赔单列表
		"listHisClaimsAccounting" : "welfareClaimsAccounting/listHisClaimsAccounting",
		// 理赔核算
		"countMedicalDetail" : "welfareClaimsAccounting/countMedicalDetail",
		// 理赔核算
		"confirmClaimsAccounting" : "welfareClaimsAccounting/confirmClaimsAccounting",
		// 查询理赔核算明细
		"queryClaimsAccountingDetail" : "welfareClaimsAccounting/queryClaimsAccountingDetail",

		// -------------------- 福利发放 -----------------------
		// 查询待发放记录
		"listPendingGrant" : "welfareGrant/listPendingGrant",
		// 查询已发放记录
		"listHistoryGrant" : "welfareGrant/listHistoryGrant",
		// 查询发放详情
		"queryGrantDetail" : "welfareGrant/queryGrantDetail",
		// 发放操作
		"grantWelfare" : "welfareGrant/grantWelfare",
		/** ***************** 积分福利管理 end ********************* */

		/** 消息中心 开始* */
		// 创建发送消息
		"send_message" : "messagecenter/sendMsg",
		// 创建发送消息
		"edit_message" : "messagecenter/editMsg",
		// 查询消息列表
		"find_message_list" : "messagecenter/list",
		// 查看消息详情
		"find_message_detail" : "messagecenter/query",
		// 删除消息
		"del_message" : "messagecenter/delete",
		// 删除多条消息
		"del_messages" : "messagecenter/deleteMessages",
		/** 消息中心 结束* */

		/** ******* 银行转账管理 开始********** */
		// 银行转账查询
		"find_transferRecord_list" : "cashTransfer/list",
		// 银行转账查询统计
		"get_transferRecord_listcount" : "cashTransfer/getTransferRecordListCount",
		// 批量提交转账
		"transBatch" : "cashTransfer/transBatch",
		// 转账撤单
		"transRevoke" : "cashTransfer/transRevoke",
		// 银行对账查询
		"find_checkUp_list" : "cashTransfer/getCheckUpList",
		// 转账失败处理
		"trans_failBack_list" : "cashTransfer/transFailBack",
		// 转账对账
		"trans_checkUp_Account" : "cashTransfer/transCheckUpAccount",
		//转账查询导出
		"transfer_record_export" : "cashTransfer/transfer_record_export",
		/** ******* 银行转账管理 结束********** */

		/** ************************操作员管理 ******************************* */
		// 查询操作员列表（企业客户号查询所有）
		"list_operator_buentcustId" : "operator/query_list",
		// 查询操作员列表（用户名、真实姓名、企业客户号）
		"list_operator" : "operator/list",
		// 添加操作员
		"add_operator" : "operator/add",
		// 修改操作员
		"update_operator" : "operator/update",
		// 删除操作员
		"delete_operator" : "operator/delete",
		// 查询操作员
		"query_operator" : "operator/query",
		// 获取操作员信息
		"operatorDetail" : "operator/get_operator_login_detail",

		/** ******************用户组管理 ********************************* */
		// 查询用户组列表
		"list_entgroup" : "entgroup/list",
		// 添加用户组
		"add_entgroup" : "entgroup/add",
		// 修改用户组
		"update_entgroup" : "entgroup/update",
		// 删除用户组
		"delete_entgroup" : "entgroup/delete",
		// 查询用户组成员列表
		"userlist_entgroup" : "entgroup/userlist",
		// 用户组添加用户
		"addgroupuser_entgroup" : "entgroup/addgroupuser",
		// 删除用户组用户
		"delgroupuser_entgroup" : "entgroup/delgroupuser",
		// 查询归属地区平台
		"entinfo_platform" : "entinfo/platform",

		/** *******************角色管理******************************* */
		// 查询角色列表
		"list_role" : "role/list",
		// 添加角色
		"add_role" : "role/add",
		// 修改角色
		"update_role" : "role/update",
		// 删除角色
		"delete_role" : "role/delete",
		// 给操作员赋角色
		"grantrole_role" : "role/grantrole",
		// 给操作员重设角色
		"resetrole_role" : "role/resetrole",
		// 不分页查询角色列表
		"rolelist_role" : "role/rolelist",

		/** *************** 权限管理 ************* */
		// 赋权限
		"grantperm_perm" : "perm/grantperm",
		// 查询权限列表
		"listperm_perm" : "perm/listperm",
		// 查看角色已有权限
		"roleperm_perm" : "perm/roleperm",

		/** *************** 用户密码业务 ************* */
		// 查询消费者信息列表
		"listconsumer_pwd" : "consumerPwd/listconsumerinfopage",

		// 查询企业信息列表
		"listbelongent_pwd" : "entPwd/listbelongentinfopage",

		// 查询交易密码企业待审核信息列表
		"listentjy_pwd" : "entjyPwd/listEntJypwdinfopage",

		// 根据互生号查询交易密码企业信息
		"findAllByResNo" : "entjyPwd/findAllByResNo",

		// 根据互生号查询交易密码企业银行默认账户信息
		"findDefaultBankByEntResNo" : "entjyPwd/findDefaultBankByEntResNo",

		// 查询交易密码企业信息列表
		"listentjylist_pwd" : "entjylistPwd/listEntJypwdinfopage",

		// 企业交易密码重置
		"listentjycz_pwd" : "entjylistPwd/apprResetTranPwdApply",

		// 消费者登陆密码重置
		"consumerdl_pwd" : "consumerPwd/resetLogPwdForCarderByReChecker",

		// 消费者交易密码重置
		"consumerjy_pwd" : "consumerPwd/resetTradePwdForCarderByReChecker",

		// 企业登陆密码重置
		"entdlcz_pwd" : "entPwd/resetLogPwdForOperatorByReChecker",

		/** *************** 合同业务 ************* */
		// 分页查询合同
		"find_contarct_by_page" : "contractManage/find_contarct_by_page",
		// 查询打印合同内容
		"find_contract_content_by_print" : "contractManage/find_contract_content_by_print",
		// 分页查询盖章合同
		"find_seal_contarct_by_page" : "contractManage/find_seal_contarct_by_page",
		// 查询盖章合同内容
		"find_contract_content_by_seal" : "contractManage/find_contract_content_by_seal",
		// 查询合同预览内容
		"find_contract_content_by_view" : "contractManage/find_contract_content_by_view",
		// 分页查询合同发放
		"find_contract_give_out_by_page" : "contractManage/find_contract_give_out_by_page",
		//合同盖章
		"sealContract"					:	"contractManage/sealContract",
		// 合同发放
		"opt_contract_give_out" : "contractManage/opt_contract_give_out",
		// 查询合同发放历史
		"find_contract_give_out_recode" : "contractManage/find_contract_give_out_recode",
		// 分页查询合同模板
		"find_contract_temp_by_page" : "contractTempManage/find_contract_temp_by_page",
		// 根据编号查询合同模板
		"find_contract_temp_by_id" : "contractTempManage/find_contract_temp_by_id",
		// 创建合同模板
		"create_contract_temp" : "contractTempManage/create_contract_temp",
		// 修改合同模板
		"modify_contract_temp" : "contractTempManage/modify_contract_temp",
		// 合同模板启用
		"enable_contract_temp" : "contractTempManage/enable_contract_temp",
		// 停用合同模板
		"stop_contract_temp" : "contractTempManage/stop_contract_temp",
		// 分页查询合同模板审批
		"find_contract_temp_appr_by_page" : "contractTempManage/find_contract_temp_appr_by_page",
		// 合同模板审批
		"contract_temp_appr" : "contractTempManage/contract_temp_appr",
		//打印模板
		"printContract"	:	"contractManage/printContract",
		/** *************** 合同业务 ************* */

		/** *************** 证书业务 ************* */
		// 分页查询消费资格证书盖章
		"find_sell_certificate_by_seal" : "sellCertificate/find_sell_certificate_by_seal",
		// 销售资格证书盖章
		"sell_certificate_seal" : "sellCertificate/sell_certificate_seal",
		// 根据ID查询销售资格证书
		"find_sell_certificate_by_id" : "sellCertificate/find_sell_certificate_by_id",
		// 查询销售资格证书内容
		"find_sell_certificate_content" : "sellCertificate/find_sell_certificate_content",
		// 分页查询证书发放
		"find_sell_certificate_give_out_by_page" : "sellCertificate/find_sell_certificate_give_out_by_page",
		// 打印销售资格证书
		"print_sell_certificate" : "sellCertificate/print_sell_certificate",
		// 销售资格证书发放
		"give_out_sell_certificate" : "sellCertificate/give_out_sell_certificate",
		// 查询销售资格证书发放历史
		"find_sell_certificate_give_out_recode" : "sellCertificate/find_sell_certificate_give_out_recode",
		// 分页查询销售资格证书发放历史
		"find_sell_certificate_recode_by_page" : "sellCertificate/find_sell_certificate_recode_by_page",
		// 分页查询第三方证书发放
		"find_third_certificate_give_out_by_page" : "thirdCertificate/find_third_certificate_give_out_by_page",
		// 打印第三方证书
		"print_third_certificate" : "thirdCertificate/print_third_certificate",
		// 发放第三方证书
		"give_out_third_certificate" : "thirdCertificate/give_out_third_certificate",
		//通过ID查询证书内容
		"find_certificate_content_by_id":"thirdCertificate/find_certificate_content_by_id",
		// 查询第三方证书发放历史
		"find_third_certificate_give_out_recode" : "thirdCertificate/find_third_certificate_give_out_recode",
		// 分页查询第三方证书发放历史
		"find_third_certificate_recode_by_page" : "thirdCertificate/find_third_certificate_recode_by_page",
		// 分页查询证书模板
		"find_certificate_temp_by_page" : "certificateTemp/find_certificate_temp_by_page",
		// 新增证书模板
		"create_certificate_temp" : "certificateTemp/create_certificate_temp",
		// 修改证书模板
		"modify_certificate_temp" : "certificateTemp/modify_certificate_temp",
		// 根据ID查询证书模板
		"find_certificate_temp_by_id" : "certificateTemp/find_certificate_temp_by_id",
		// 启用证书模板
		"enable_certificate_temp" : "certificateTemp/enable_certificate_temp",
		// 停用证书模板
		"stop_certificate_temp" : "certificateTemp/stop_certificate_temp",
		// 分页查询证书模板审批
		"find_certificate_temp_appr_by_page" : "certificateTemp/find_certificate_temp_appr_by_page",
		// 证书模板审批
		"certificate_temp_appr" : "certificateTemp/certificate_temp_appr",
		//查询证书模板最新审核记录详情
		"queryCertiLastTemplateAppr" : "certificateTemp/queryLastTemplateAppr",
		/** *************** 证书业务 ************* */
		/** *************** 开启关闭系统管理 start ************* */
		// 开启系统
		"closeopenOpenSystem" : "closeopenSystem/open",
		// 关闭系统
		"closeSystem" : "closeopenSystem/close",
		// 根据ID查询详情
		"closeopenSystemDetail" : "closeopenSystem/findById",
		// 审批开启关闭系统
		"approveSystem" : "closeopenSystem/apprCloseOpenEnt",
		// 更新工单
		"updateTask" : "closeopenSystem/updateTask",
		// 分页查询开关系统申请
		"queryCloseOpenEnt4Appr" : "closeopenSystem/queryCloseOpenEnt4Appr",
		// 开关系统审核查询
		"queryCloseOpenEnt" : "closeopenSystem/list",
		// 分页查询所有企业
		"getAllEnt" : "closeopenSystem/getAllEnt",
		// 根据ID查询企业上一次关开系统信息
		"queryLastCloseOpenEntInfo" : "closeopenSystem/queryLastCloseOpenEntInfo",

		/** *************** 开启关闭系统管理 end ************* */

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

		/** ******************* 工具类型 ******************** */
		"tooltype_all_lsit" : "tooltype/all_list",

		/** ******************* 企业账户查询 ******************** */
		// 账户 明细查询
		"query_company_mxcx" : "integralAccount/detailed_page",
		// 账户明细详单
		"get_account_company_detailed" : "integralAccount/get_acc_opt_detailed",
		//查询消费积分分配详情
		"getPointAllotDetailedList":"integralAccount/get_point_allot_detailed_list",
		//查询消费积分分配详情
		"getPointAllotDetailed":"integralAccount/get_point_allot_detailed",
		// 投资分红
		"tzfh_Company_detailed_page" : "integralAccount/tzfh_detailed_page",

		/** *******************消费者账户流水查询 ******************** */
		// 账户 明细查询
		"query_per_mxcx" : "integralPersonAccount/detailed_page",
		//非持卡人互生币明细查询
		"query_perNoCard_hsbmxcx" : "hsbPerNoCardAccount/detailed_page",
		//非持卡人货币明细查询
		"query_perNoCard_hbmxcx" : "hbPerNoCardAccount/detailed_page",
		// 账户明细详单
		"get_per_detailed" : "integralPersonAccount/get_acc_opt_detailed",
		// 投资分红流水
		"tzfh_detailed_page" : "integralPersonAccount/tzfh_detailed_page",
		/** **************************平台代理业务 ******************** */
		"queryAnnualFeeInfo" : "annualFeeOrder/queryAnnualFeeInfo",
		"submitAnnualFeeOrder" : "annualFeeOrder/submitAnnualFeeOrder",
		"getEntBuyHsbLimit" : "annualFeeOrder/getEntBuyHsbLimit",
		"saveBuyHsb" : "annualFeeOrder/saveBuyHsb",
		//工具申购-查询可以购买的工具
		"seachMayBuyTool" : "toolmanage/query_may_buy_tool",
		//查询企业可以购买工具的数量
		"serchMayBuyToolNum" : "toolmanage/query_tool_num",
		//新增收货地址
		"addDeliverAddress" : "toolmanage/add_deliver_address",
		//工具申购-查询企业地址
		"serchEntAddress" : "toolmanage/query_ent_address",
		//工具申购  --提交订单
		"commitByToolOrder" : "toolmanage/commit_bytool_order",
		//工具申购  --提交订单(互生卡)
		"commitByToolOrderCard" : "toolmanage/commit_bytool_order_card",
		//分页查询企业工具订单
		"toolOrderList" : "toolmanage/tool_order_list",
		//查询工具订单详情
		"orderDetail" : "toolmanage/tool_order_detail",
		//平台代购订单审批
		"proxyOrderAppr" : "toolmanage/proxy_order_appr",
		//定制互生卡样下单
		"addCardStyleFeeOrder" : "toolmanage/addCardStyleFeeOrder",
		//系统资源申购
		"commit_bytool_order_card" : "toolmanage/commit_bytool_order_card",
		//查询企业的系统资源段
		"query_ent_resource_segment" : "toolmanage/query_ent_resource_segment",
		//查询企业可以购买的资源段(新)
		"query_ent_resource_segment_new" : "toolmanage/query_ent_resource_segment_new",
		//分页查询平台代购订单记录
		"find_plat_proxy_order_record_page" : "toolmanage/find_plat_proxy_order_record_page",
		
		/*********************互动信息 start*********************/
		//互动信息-查询分组及用户
		"findTaskGroupInfo" : "operator/findTaskGroupInfo",
        //互动信息-查询历史记录
    	"queryMessageRecordList" : "message//queryMessageRecordList",
    	//互动信息-查询最近联系人
    	"queryRecentContactsMsgList" : "message/queryRecentContactsMsgList",
    	/*********************互动信息 end*********************/
    		
    	/*********************平台扣款业务 start*********************/
    	//平台扣款申请
    	"applyHsbDeduction" : "platformDebit/apply_hsb_deduction",
    	//平台互生币扣款申请分页查询
    	"hsbDeductionPage" : "platformDebit/hsb_deduction_page",
    	//平台互生币扣款申请复核分页查询
    	"deductReviewPage" : "platformDebit/deduct_review_page",
    	//通过互生号查找详情
    	"getResNoInfo" : "platformDebit/get_resNo_info",
    	//获取扣款申请详情
    	"queryDeductDetail" : "platformDebit/query_deductDetail",
    	//扣款申请复核
    	"apprHsbDeduction" : "platformDebit/appr_hsbDeduction"
    	      
    	/*********************平台扣款业务 start*********************/
    		

	};
	return comm.UrlList;
});