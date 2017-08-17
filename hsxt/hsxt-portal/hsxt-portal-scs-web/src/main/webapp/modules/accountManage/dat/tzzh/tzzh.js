define(function () {
	return {
		//积分投资明细查询
		pointInvestPage:function(params,callback){
			return comm.getCommBsGrid("tableDetail", "pointInvestPage", params, comm.lang("accountManage"),callback);
		},
		//投资分红明细查询
		pointDividendPage:function(params,detailCallback){
			return comm.getCommBsGrid("tableDetail", "pointDividendPage", params, comm.lang("accountManage"), detailCallback);
		},
		//积分投资总数
		inverstmentTotal: function (data, callback) {
			comm.requestFun("inverstment_total", data, callback,comm.lang("accountManage"));
		}
	};
});