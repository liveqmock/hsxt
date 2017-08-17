define(function () {
	return {
		//查询可以购买的工具
		seachMemberQuitDetail : function(data,callback){
			comm.requestFun("find_quit_detailInfo",data,callback,comm.lang("businessService"));
		},
		//获得文件在文件系统服务器中的url
		getFsServerUrl : function(fileId) {
			var custId = $.cookie('custId'); 	// 读取 cookie 客户号
			var token = $.cookie('token');
			
			return  comm.domainList['fsServerUrl']+fileId+"?userId="+custId+"&token="+token;
		},
		//成员企业注销审批提交
		commitMemberQuitAppr : function(data,callback){
			comm.requestFun("approval_quit_apply",data,callback,comm.lang("businessService"));
		},
		//积分活动申请详情
		seachPointActivityDetail : function(data,callback){
			comm.requestFun("find_activity_apply_detail",data,callback,comm.lang("businessService"));
		},
		//提交审批积分活动申请信息
		commitPointActivityAppr : function(data,callback){
			comm.requestFun("approval_activity_apply",data,callback,comm.lang("businessService"));
		},
		/**
		 * 发票管理 列表，查看发票总计，查看发票详细，客户开发票，客户申请发票，
		 */
		listInvoice : function(gridId,params,callback,callback1){
			 return comm.getCommBsGrid(gridId,"listInvoice", params,comm.lang("businessService"), callback,callback1);
		},
		queryInvoiceSum : function(params,callback){
			 comm.requestFun("queryInvoiceSum", params,callback,comm.lang("businessService"));
		},
		getInvoiceById : function(params,callback){
			comm.requestFun("getInvoiceById", params, callback, comm.lang("businessService"));
		},
		custOpenInvoice : function(params,callback){
			comm.requestFun("custOpenInvoice", params, callback, comm.lang("businessService"));
		},
		custApplyInvoice : function(params,callback){
			comm.requestFun("custApplyInvoice", params, callback, comm.lang("businessService"));
		},
		changePostWay : function(params,callback){
			comm.requestFun("changePostWay", params, callback, comm.lang("businessService"));
		},
		/**
		 * 根据互生号查询重要信息,默认银行账户信息
		 */		
		findMainInfoDefaultBankByResNo : function(params,callback){
			comm.requestFun("findMainInfoDefaultBankByResNo", params, callback, comm.lang("businessService"));
		},
		/**
		 * 根据互生号查询默认银行账户信息
		 */		
		findDefaultBankByResNo : function(params, callback){
			comm.requestFun("findDefaultBankByResNo", params, callback, comm.lang("businessService"));
		}
	};
});