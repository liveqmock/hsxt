define(function() {
	return {
		/** **********投资账户************ */
		// 查询余额
		findInvestmentBlance : function(jsonParam, callback) {
			comm.requestFun("findInvestmentBlance", jsonParam, callback, comm.lang("investAccount"));
		},
		// 积分投资明细查询
		pointInvestPage : function(params,detail) {
			return comm.getCommBsGrid("sinvest_result_table", "pointInvestPage", params, comm.lang("investAccount"),detail);
		},
		// 投资分红明细查询
		pointDividendPage : function(gridId, params, detailCallback) {
			return comm.getCommBsGrid(gridId, "pointDividendPage", params, comm.lang("investAccount"), detailCallback);
		},
		// 投资分红明细-详细信息
		dividendDetailPage : function(gridId, params, callback) {
			return comm.getCommBsGrid(gridId, "dividendDetailPage", params,comm.lang("investAccount"), callback);
		},
		// 投资分红明细-详细信息统计
		dividendDetailPageTotal : function(jsonParam, callback) {
			comm.requestFun("dividendDetailPageTotal", jsonParam, callback, comm.lang("investAccount"));
		},
	}
});
