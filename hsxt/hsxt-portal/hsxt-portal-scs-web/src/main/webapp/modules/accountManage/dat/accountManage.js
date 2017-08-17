define(function () {
	return {
		//账户明细查询
		detailedPage:function(gridId,params,callback){
			return comm.getCommBsGrid(gridId, "detailedPage", params,comm.lang("accountManage"),callback);
		},
		//账户操作明细详单
		getAccOptDetailed : function (data, callback) {
			comm.requestFun("getAccOptDetailed",data,callback,comm.lang("accountManage"));
		}
	};
});