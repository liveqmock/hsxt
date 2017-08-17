define(function () {
	return {
		findEntAllInfo : function(params,callback){
			comm.requestFun("findEntAllInfo", params, callback, comm.lang("platformProxy"));
		},
		companyInfor : function(params,callback){
			comm.requestFun("mainCompanyInforByResNo", params, callback, comm.lang("platformProxy"));
		},
		searchEntStatusInfo : function(params,callback){
			comm.requestFun("searchEntStatusInfo", params, callback, comm.lang("platformProxy"));
		},
		queryAnnualFeeInfo : function(params,callback){
			comm.requestFun("queryAnnualFeeInfo", params, callback, comm.lang("platformProxy"));
		},
		submitAnnualFeeOrder : function(params,callback){
			comm.requestFun("submitAnnualFeeOrder", params, callback, comm.lang("platformProxy"));
		},
		getEntBuyHsbLimit : function(params,callback){
			comm.requestFun("getEntBuyHsbLimit", params, callback, comm.lang("platformProxy"));
		},
		saveBuyHsb : function(params,callback){
			comm.requestFun("saveBuyHsb", params, callback, comm.lang("platformProxy"));
		},
		//查询可以购买的工具
		seachMayBuyTool : function(data,callback){
			comm.requestFun("seachMayBuyTool",data,callback,comm.lang("platformProxy"));
		},
		//新增收货地址
		addDeliverAddress : function(data,callback){
			comm.requestFun("addDeliverAddress",data,callback,comm.lang("platformProxy"));
		},
		//查询工具订单详情
		orderDetail : function(data,callback){
			comm.requestFun("orderDetail",data,callback,comm.lang("platformProxy"));
		},
		//查询所选工具类型企业可以购买的数量
		serchMayBuyToolNum : function(data,callback){
			comm.syncRequestFun("serchMayBuyToolNum",data,callback,comm.lang("platformProxy"));
		},
		//工具申购-查询企业收货地址
		serchEntAddress : function(data,callback){
			comm.requestFun("serchEntAddress",data,callback,comm.lang("platformProxy"));
		},
		//提交订单
		commitByToolOrder : function(data,callback){
			comm.requestFun("commitByToolOrder",data,callback,comm.lang("platformProxy"));
		},
		//提交订单(互生卡)
		commitByToolOrderCard : function(data,callback){
			comm.requestFun("commitByToolOrderCard",data,callback,comm.lang("platformProxy"));
		},
		//工具订单列表
		tool_order_list : function(gridId,data,callback,callback2,callback3,callback4){
			comm.getCommBsGrid(gridId,"toolOrderList",data,comm.lang("platformProxy"),callback,callback2,callback3,callback4);
		},
		//平台代购订单审批
		proxy_order_appr : function(data,callback){
			comm.requestFun("proxyOrderAppr",data,callback,comm.lang("platformProxy"));
		},
		//定制互生卡样下单
		addCardStyleFeeOrder : function(data,callback){
			comm.requestFun("addCardStyleFeeOrder",data,callback,comm.lang("platformProxy"));
		},
		//系统资源申购下单
		commitBytoolOrderCard : function(data,callback){
			comm.requestFun("commit_bytool_order_card",data,callback,comm.lang("platformProxy"));
		},
		//查询企业的系统资源段
		queryEntResourceSegment : function(data,callback){
			comm.requestFun("query_ent_resource_segment",data,callback,comm.lang("platformProxy"));
		},
		//查询企业可以购买的资源段(新)
		queryEntResourceSegmentNew : function(data,callback){
			comm.requestFun("query_ent_resource_segment_new",data,callback,comm.lang("systemBusiness"));
		},
	};
});