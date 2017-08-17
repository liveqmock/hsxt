define(function () {
	return {
		/**
		 * 开启关闭系统
		 * 开启申请，关闭申请，查看详情，查看上次申请记录，审批申请，列出所有状态申请或者复核，列表所有申请，列出所有企业
		 * 
		 * 
		 */
		open : function(params, callback){
			comm.requestFun("closeopenOpenSystem", params, callback, comm.lang("shutdownsystem"));
		},
		close : function(params,callback){
			comm.requestFun("closeSystem", params, callback, comm.lang("shutdownsystem"));
		},
		getDetail : function(params,callback){
			comm.requestFun("closeopenSystemDetail", params, callback, comm.lang("shutdownsystem"));
		},
		queryLastCloseOpenEntInfo : function(params,callback){
			comm.requestFun("queryLastCloseOpenEntInfo", params, callback, comm.lang("shutdownsystem"));
		},
		approve : function(params,callback){
			comm.requestFun("approveSystem", params, callback, comm.lang("shutdownsystem"));
		},
		updateTask : function(params,callback){
			comm.requestFun("updateTask", params, callback, comm.lang("shutdownsystem"));
		},
		queryCloseOpenEnt : function(gridId,params,callback){
			 comm.getCommBsGrid(gridId,"queryCloseOpenEnt", params,comm.lang("shutdownsystem"), callback);
		},
		queryCloseOpenEnt4Appr : function(gridId,params,detail, del){
			return comm.getCommBsGrid(gridId,"queryCloseOpenEnt4Appr", params,comm.lang("shutdownsystem"), detail, del);
		},
		getAllEnt : function(gridId,params,callback){
			 comm.getCommBsGrid(gridId,"getAllEnt", params,comm.lang("shutdownsystem"), callback);
		}
		
	};
});