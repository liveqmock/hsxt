define(function () {
	return {
		/*积分福利资格分页查询**/
		listWelfareQualify:function(gridId, params,detail,del, add, edit){
			return comm.getCommBsGrid(gridId,"listWelfareQualify",params,comm.lang("pointWelfare"),detail,del, add, edit);
		},
		
		/**积分福利待审批记录查询   appovalStep 0 初审  1 复审**/
		listPendingApproval:function(gridId, params,detail,del, add, edit){
			return comm.getCommBsGrid(gridId,"listPendingApproval",params,comm.lang("pointWelfare"),detail,del, add, edit);
		},
		/**积分福利审批记录查询 **/
		listApprovalRecord:function(gridId, params,detail,del, add, edit){
			return comm.getCommBsGrid(gridId,"listApprovalRecord",params,comm.lang("pointWelfare"),detail,del, add, edit);
		},
		/**积分福利待发放记录查询 **/
		listPendingGrant:function(gridId, params,detail,del, add, edit){
			return comm.getCommBsGrid(gridId,"listPendingGrant",params,comm.lang("pointWelfare"),detail,del, add, edit);
		},
		/**积分福利发放记录查询 **/
		listHistoryGrant:function(gridId, params,detail,del, add, edit){
			return comm.getCommBsGrid(gridId,"listHistoryGrant",params,comm.lang("pointWelfare"),detail,del, add, edit);
		},
		/**查询待核算理赔单**/
		listPendingClaimsAccounting:function(gridId, params,detail,del, add, edit){
			return comm.getCommBsGrid(gridId,"listPendingClaimsAccounting",params,comm.lang("pointWelfare"),detail,del, add, edit);
		},
		/** **/
		queryClaimsAccountingDetail:function(data, callback){
			comm.requestFun("queryClaimsAccountingDetail",data,callback,comm.lang("pointWelfare"));
		},
		queryApprovalDetail:function(data, callback){
			comm.requestFun("queryApprovalDetail",data,callback,comm.lang("pointWelfare"));
		},
		grantWelfare:function(data, callback){
			comm.requestFun("grantWelfare",data,callback,comm.lang("pointWelfare"));
		},
		countMedicalDetail:function(data, callback){
			comm.requestFun("countMedicalDetail",data,callback,comm.lang("pointWelfare"));
		},
		confirmClaimsAccounting:function(data, callback){
			comm.requestFun("confirmClaimsAccounting",data,callback,comm.lang("pointWelfare"));
		},
		updateWsTask : function(params,callback){
			comm.requestFun("updateWsTask", params, callback, comm.lang("pointWelfare"));
		},
		approvalWelfare:function(data, callback){
			comm.requestFun("approvalWelfare",data,callback,comm.lang("pointWelfare"));
		}
	};
});