define(function () {
	return {
		/*查看调账申请列表**/
		getCheckBalances:function(gridId, params,detail,del, add, edit){
			return comm.getCommBsGrid(gridId,"getCheckBalances",params,comm.lang("checkBalance"),detail,del, add, edit);
		},
		/*查看调账详情**/
		getCheckBalanceDetail:function(gridId, params,detail,del, add, edit){
			return comm.getCommBsGrid(gridId,"getCheckBalanceDetail",params,comm.lang("checkBalance"),detail,del, add, edit);
		},
		/*查看调账审核结果列表**/
		getCheckBalanceResults:function(gridId, params,detail){
			return comm.getCommBsGrid(gridId,"getCheckBalanceResults",params,comm.lang("checkBalance"),detail,null, null, null);
		},
		/** 添加调账申请的审批 */
		addCheckBalanceResult:function(params, callback){
			return comm.requestFun("addCheckBalanceResult", params, callback, comm.lang("checkBalance"));
			//  comm.getCommBsGrid(gridId,"addCheckBalanceResult",params,comm.lang("checkBalance"),detail,del, add, edit);
		},
		
		/** 添加调账申请 */
		addCheckBalance:function(params, callback){
			return comm.requestFun("addCheckBalance", params, callback, comm.lang("checkBalance"));
			//return comm.getCommBsGrid(gridId,"addCheckBalance",params,comm.lang("checkBalance"),detail,del, add, edit);
		},
		
		/** 查询用户名 */
		getUsernameByResNo:function(params, callback){
			return comm.requestFun("getUsernameByResNo", params, callback, comm.lang("checkBalance"));
			//return comm.getCommBsGrid(gridId,"addCheckBalance",params,comm.lang("checkBalance"),detail,del, add, edit);
		},
		
		/** 查询余额 */
		getBalance:function(params, callback){
			return comm.requestFun("getBalance", params, callback, comm.lang("checkBalance"));
			//return comm.getCommBsGrid(gridId,"addCheckBalance",params,comm.lang("checkBalance"),detail,del, add, edit);
		},
	};
});