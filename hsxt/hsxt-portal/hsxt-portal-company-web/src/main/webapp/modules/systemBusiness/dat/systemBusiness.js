define(function () {
	return {
		//获得服务器地址getFsServerUrl
		getFsServerUrl : function(pictureId) {
			return  comm.domainList['fsServerUrl']+pictureId+"?userId="+$.cookie('custId')+"&token="+$.cookie('token');
		},
		//查询可以购买的工具
		seachMayBuyTool : function(data,callback){
			comm.requestFun("query_may_buy_tool",data,callback,comm.lang("systemBusiness"));
		},
		//新增收货地址
		addDeliverAddress : function(data,callback){
			comm.requestFun("add_deliver_address",data,callback,comm.lang("systemBusiness"));
		},
		//修改收货地址
		modifyDeliverAddress : function(data,callback){
			comm.requestFun("modify_deliver_address",data,callback,comm.lang("systemBusiness"));
		},
		//删除收货地址
		removeDeliverAddress : function(data,callback){
			comm.requestFun("remove_deliver_address",data,callback,comm.lang("systemBusiness"));
		},
		//查收收货地址
		queryDeliverAddress : function(data,callback){
			comm.requestFun("query_deliver_address",data,callback,comm.lang("systemBusiness"));
		},
		//查询卡样
		serchHsCardStyle : function(data,callback){
			comm.requestFun("query_hscard_style",data,callback,comm.lang("systemBusiness"));
		},
		//查询所选工具类型企业可以购买的数量
		serchMayBuyToolNum : function(data,callback){
			comm.syncRequestFun("query_tool_num",data,callback,comm.lang("systemBusiness"));
		},
		//工具申购-查询企业收货地址
		serchEntAddress : function(data,callback){
			comm.requestFun("query_ent_address",data,callback,comm.lang("systemBusiness"));
		},
		//提交订单
		commitByToolOrder : function(data,callback){
			comm.requestFun("commit_bytool_order",data,callback,comm.lang("systemBusiness"));
		},
		//购买系统资源
		commitBytoolOrderCard : function(data,callback){
			comm.requestFun("commit_bytool_order_card",data,callback,comm.lang("systemBusiness"));
		},
		//查询企业可以购买的资源段
		queryEntResourceSegment : function(callback){
			comm.requestFun("query_ent_resource_segment",null,callback,comm.lang("systemBusiness"));
		},
		//查询企业可以购买的资源段(新)
		queryEntResourceSegmentNew : function(callback){
			comm.requestFun("query_ent_resource_segment_new",null,callback,comm.lang("systemBusiness"));
		},
		//获取企业可以选择的卡样
		getEntCardStyleByAll : function(callback){
			comm.requestFun("get_ent_card_style_by_all",null,callback,comm.lang("systemBusiness"));
		},
		//订单支付
		toolOrderPayment : function(data,callback){
			comm.requestFun("tool_order_payment",data,callback,comm.lang("systemBusiness"));
		},
		//查询工具订单详情
		toolOrderDetail : function(data,callback){
			comm.requestFun("tool_order_detail",data,callback,comm.lang("systemBusiness"));
		},
		//查询工具订单详情
		getToolConfigByNo : function(data,callback){
			comm.requestFun("get_tool_config_by_no",data,callback,comm.lang("systemBusiness"));
		},
		//工具订单撤单
		toolOrderCancel : function(data,callback){
			comm.requestFun("tool_order_cancel",data,callback,comm.lang("systemBusiness"));
		},		
		//修改终端设备状态
		updateDeviceStatus : function(data,callback){ 
			comm.requestFun("modify_device_status",data,callback,comm.lang("systemBusiness"));
		},
		//查询企业个性卡列表
		entSpecialCardStyle : function(data,callback){ 
			comm.requestFun("ent_special_card_style",data,callback,comm.lang("systemBusiness"));
		},
		//个性订制服务下单
		submitSpecialCardStyleOrder : function(data,callback){ 
			comm.requestFun("submit_special_card_style",data,callback,comm.lang("systemBusiness"));
		},
		//上传个性卡素材
		addSpecialCardStyle : function(data,callback){ 
			comm.requestFun("add_special_card_style",data,callback,comm.lang("systemBusiness"));
		},
		//个性卡样确认
		confirmCardStyle : function(data,callback){ 
			comm.requestFun("confirm_card_style",data,callback,comm.lang("systemBusiness"));
		},
		//上传卡制作卡样确认文件
		uploadCardMarkConfirmFile : function(data,callback){ 
			comm.requestFun("upload_card_mark_confirm_file",data,callback,comm.lang("systemBusiness"));
		},
		//获取绑定的快捷支付银行
		getBandQuickBank : function(data,callback){ 
			comm.requestFun("get_band_quick_bank",data,callback,comm.lang("systemBusiness"));
		},
		//获取支持快捷支付的银行列表
		getQuickPayBank : function(data,callback){ 
			comm.requestFun("get_quick_pay_bank",data,callback,comm.lang("systemBusiness"));
		},
		//开通快捷支付
		openQuickPay : function(data,callback){ 
			comm.requestFun("open_quick_pay",data,callback,comm.lang("systemBusiness"));
		},
		//发送快捷支付短信
		sendQuickPaySms : function(data,callback){ 
			comm.requestFun("send_quick_pay_sms",data,callback,comm.lang("systemBusiness"));
		},
		//缴纳系统使用年费基础信息查询
		serchAnnualfeeInfo : function(data,callback){
			comm.requestFun("serch_annualfee_info",data,callback,comm.lang("systemBusiness"));
		},
		//缴纳系统使用年费
		payAnnualFee : function(data,callback){
			comm.requestFun("pay_annualfee_info",data,callback,comm.lang("systemBusiness"));
		},
		//缴纳系统使用年费
		createAnnualFeeOrder : function(data,callback){
			comm.requestFun("create_annualfee_order",data,callback,comm.lang("systemBusiness"));
		},
		//查询企业状态
		searchEntStatusInfo : function(data,callback){
			comm.requestFun("query_ent_status",data,callback,comm.lang("systemBusiness"));
		},
		//查询业务办理申请书 和申请书模板
		searchBusinessApply : function(data,callback){
			comm.requestFun("query_business_apply",data,callback,comm.lang("systemBusiness"));
		},
		//查询企业积分预付款账户余额、货币转换比率、货币转换费、银行账户、账户名称、开户银行、开户地区、银行账号、结算币种
		searchBusinessAcountInfo : function(data,callback){
			comm.requestFun("query_business_accountinfo",data,callback,comm.lang("systemBusiness"));
		},
		//文件上传路径
		getUploadFilePath : function(){
			return  comm.domainList['companyWeb']+comm.UrlList["upload"];
		},
		//成员企业注销
		memberEnterpriseApplyQuit : function(data,callback){
			comm.requestFun("member_apply_quit",data,callback,comm.lang("systemBusiness"));
		},
		//查询企业积分预付款余额、货币转换比率
		searchPointNum : function(data,callback){
			comm.requestFun("query_point_num",data,callback,comm.lang("systemBusiness"));
		},
		//积分活动申请
		pointActivityApply : function(data,callback){
			comm.requestFun("apply_pointactivity_apply",data,callback,comm.lang("systemBusiness"));
		},
		//系统业务订单查询url
		getSysBusOrderUrl : function(){
			return comm.domainList['companyWeb']+comm.UrlList["query_sysbusorder_list"];
		},
		//获取年费订单详情 
		getAnnualfeeOrderDetail : function(data,callback){
			comm.requestFun("get_annualfee_order_detail",data,callback,comm.lang("systemBusiness"));
		},
		//发货单确认收货
		confirmReceipt : function(data,callback){
			comm.Request({url : 'tool_accept_confirm', domain : 'companyWeb'},{
				data : data,
				success :function(response){
					callback(response);
				},
				error : function(response) {
					alert(comm.lang("systemBusiness").requestError);
				}
			});
		},
		//去付款
		toPay : function(data,callback){
			comm.requestFun("tool_order_topay",data,callback,comm.lang("systemBusiness"));
		},
		//设置企业积分比例
		setEntPointRate : function(data,callback){
			comm.requestFun("set_pointrate",data,callback,comm.lang("systemBusiness"));
		},
		//查询企业积分比例
		queryEntPointRate : function(data,callback){
			comm.requestFun("query_pointrate",data,callback,comm.lang("systemBusiness"));
		},
		//修改企业积分比例
		updateEntPointRate : function(data,callback){
			comm.requestFun("update_pointrate",data,callback,comm.lang("systemBusiness"));
		},
		//查询企业积分比例菜单
		query_pointrateMenu : function(data,callback){
			comm.requestFun("query_pointrateMenu",data,callback,comm.lang("systemBusiness"));
		},
		//网上积分登记查询
		pointRegisterList : function(gridId,params,callback){
			 comm.getCommBsGrid(gridId,"pointRegisterList", params, callback);
		},
		//网上积分登记
		pointRegister : function(params,callback){
			 comm.requestFun("pointRegister", params,callback,comm.lang("systemBusiness"));
		},
		//查询流水号
		getSequence : function(params,callback){
			 comm.requestFun("getSequence", params,callback,comm.lang("systemBusiness"));
		},
		
		/**
		 * 发票管理 列表，查看发票总计，查看发票详细，客户开发票，客户申请发票，
		 */
		listInvoice : function(gridId,params,callback,callback1){
			 return comm.getCommBsGrid(gridId,"listInvoice", params, callback,callback1);
		},
		queryInvoiceSum : function(params,callback){
			 comm.requestFun("queryInvoiceSum", params,callback,comm.lang("systemBusiness"));
		},
		getInvoiceById : function(params,callback){
			comm.requestFun("getInvoiceById", params, callback, comm.lang("systemBusiness"));
		},
		custOpenInvoice : function(params,callback){
			comm.requestFun("custOpenInvoice", params, callback, comm.lang("systemBusiness"));
		},
		custApplyInvoice : function(params,callback){
			comm.requestFun("custApplyInvoice", params, callback, comm.lang("systemBusiness"));
		},
		changePostWay : function(params,callback){
			comm.requestFun("changePostWay", params, callback, comm.lang("systemBusiness"));
		},
		/**
		 *根据resNo查询重要信息,默认银行账户信息
		 */		
		findMainInfoDefaultBankByResNo : function(params,callback){
			comm.requestFun("findMainInfoDefaultBankByResNo", params, callback, comm.lang("systemBusiness"));
		},
		
		
		
		//查找账户余额和货币兑换比率
		findExchangeHsbInfo	:	function(params,callback){
			comm.requestFun("findExchangeHsbInfo", params, callback, comm.lang("systemBusiness"));
		},
		getCardHolderDayLimit	:	function(params,callback){
			comm.requestFun("getCardHolderDayLimit", params, callback, comm.lang("systemBusiness"));
		},
		
		//根据消费者互生号查找消费者客户号
		findCustIdByResno	:	function(params,callback){
			comm.requestFun("findCustIdByResno", params, callback, comm.lang("systemBusiness"));
		},
		exchange	:	function(params,callback){
			comm.requestFun("exchange", params, callback, comm.lang("systemBusiness"));
		},
		/**
		 * 根据互生号查询默认银行账户信息
		 */		
		findDefaultBankByResNo : function(params, callback){
			comm.requestFun("findDefaultBankByResNo", params, callback, comm.lang("businessService"));
		}
	};
});