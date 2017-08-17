define(function () {
	return {
		/**
		 * 税率调整 审核，查看，列表,审批列表
		 * 
		 * 
		 */
		apprTaxrateChange : function(params, callback){
			comm.requestFun("apprTaxrateChange", params, callback, comm.lang("invoice"));
		},
		queryTaxrateChange : function(params,callback){
			comm.requestFun("getTaxrateChangeById", params, callback, comm.lang("invoice"));
		},
		listTaxrateChange : function(gridId,params,callback,callback1){
			 comm.getCommBsGrid(gridId,"listTaxrateChange", params,comm.lang("invoice"), callback,callback1);
		},
		approveTaxrateChangeList : function(gridId,params,callback,callback1,callback2){
			 return comm.getCommBsGrid(gridId,"approveTaxrateChangeList", params,comm.lang("invoice"), callback,callback1,callback2);
		},
		/**
		 * 发票管理 列表，查看，签收，发票池列表，平台开发票
		 */
		listInvoice : function(gridId,params,callback,callback1,callback2){
			return comm.getCommBsGrid(gridId,"listInvoice", params,comm.lang("invoice"), callback,callback1,callback2);
		},
		invoicePoolList : function(gridId,params,callback){
			 comm.getCommBsGrid(gridId,"invoicePoolList", params,comm.lang("invoice"), callback);
		},
		getInvoiceById : function(params,callback){
			comm.requestFun("getInvoiceById", params, callback, comm.lang("invoice"));
		},
		signInvoice : function(params,callback){
			comm.requestFun("signInvoice", params, callback, comm.lang("invoice"));
		},
		invoicePoolList : function(gridId,params,callback,callback1){
			 comm.getCommBsGrid(gridId,"invoicePoolList", params,comm.lang("invoice"), callback,callback1);
		},
		platCreateInvoice : function(params,callback){
			comm.requestFun("platCreateInvoice", params, callback, comm.lang("invoice"));
		},

		changePostWay : function(params,callback){
			comm.requestFun("changePostWay", params, callback, comm.lang("invoice"));
		},
		rejectionInvoice : function(params,callback){
			comm.requestFun("rejectionInvoice", params, callback, comm.lang("invoice"));
		},
		platApproveInvoice : function(params,callback){
			comm.requestFun("platApproveInvoice", params, callback, comm.lang("invoice"));
		},
		custOpenInvoice : function(params,callback){
			comm.requestFun("custOpenInvoice", params, callback, comm.lang("invoice"));
		},
		/**
		 * 企业信息 根据custId查询所有信息、重要信息、一般信息,默认银行账户信息
		 */
		allCompanyInfor : function(params,callback){
			comm.requestFun("invoiceGetAllCompanyInfor", params, callback, comm.lang("invoice"));
		},
		mainCompanyInfor : function(params,callback){
			comm.requestFun("invoiceGetMainCompanyInfor", params, callback, comm.lang("invoice"));
		},
		baseCompanyInfor : function(params,callback){
			comm.requestFun("invoiceGetBaseCompanyInfor", params, callback, comm.lang("invoice"));
		},
		defaultBankInfor : function(params,callback){
			comm.requestFun("invoiceGetDefaultBankInfor", params, callback, comm.lang("invoice"));
		},
		findMainInfoDefaultBankByResNo : function(params,callback){
			comm.requestFun("findMainInfoDefaultBankByResNo", params, callback, comm.lang("invoice"));
		},
		findEntCustIdByEntResNo : function(params,callback){
			comm.requestFun("findEntCustIdByEntResNo", params, callback, comm.lang("invoice"));
		},
		getPlatResNo : function(params,callback){
			comm.requestFun("getPlatResNo", params, callback, comm.lang("invoice"));
		},
		mainCompanyInforByResNo:function(params,callback){
			comm.requestFun("mainCompanyInforByResNo", params, callback, comm.lang("invoice"));
		},
		resourceFindEntAllInfo : function(data, callback){
			comm.requestFun("resourceFindEntAllInfo", data, callback, comm.lang("invoice"));
		},
	};
});