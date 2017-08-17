define(function(){
	return {
		//积分账户余额查询
		findIntegralBalance : function (callback) {
			comm.requestFun("findIntegralBalance" , null, callback,comm.lang("accountManage"));
		},
		//积分明细查询
		integralDetailedPage:function(params,callback){
			return comm.getCommBsGrid("tableDetail", "integralDetailedPage", params, comm.lang("accountManage"),callback);
		},
		getdetailInfo : function(data,callback){
			comm.requestFun("getdetailInfo",data,callback,comm.lang("accountManage"));
		},
		//消费积分分配明细查询
		pointAllocDetail : function (params,callback) {
			comm.requestFun("pointAllocDetail" , params, callback,comm.lang("accountManage"));
		},
		pointAllocDetailList:function(gridId,params,callback){
			return comm.getCommBsGrid(gridId, "pointAllocDetailList", params, comm.lang("accountManage"),callback);
		}
	};	
});
