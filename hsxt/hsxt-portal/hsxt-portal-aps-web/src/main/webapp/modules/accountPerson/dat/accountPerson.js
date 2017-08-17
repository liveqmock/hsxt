define(function () {
	return {
		//账户明细查询
		getAccoutGrid:function(gridId,params,callback){
			return comm.getCommBsGrid(gridId, "query_per_mxcx", params,null, callback);
		},
		//账户明细详情
		getAccountDetailed : function(data, callback){
			comm.requestFun("get_per_detailed" , data, callback,comm.lang("accountPerson"));
		},
		// 查询消费者分红流水详情
		getPerTzfhDeatail : function(params, callback) {
			comm.requestFun("get_per_tzfh_detailed",params,callback,comm.lang("accountPerson"));
		},
		//投资分红流水明细查询
		tzfh_detailed_page:function(gridId,params,callback){
			return comm.getCommBsGrid(gridId, "tzfh_detailed_page", params,null, callback);
		},
	};
});