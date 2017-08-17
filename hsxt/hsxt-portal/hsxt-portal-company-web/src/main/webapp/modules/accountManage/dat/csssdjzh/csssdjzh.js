define(function() {
	return {
		// 税收账户余额查询
		cityTaxBalance : function(callback) {
			comm.requestFun("findCityTaxBlance", null, callback, comm.lang("accountManage"));
		},
		//税收账户操作详情
		getAccOptDetailed : function (data, callback) {
			comm.requestFun("getCastAccOptDetailed" , data, callback,comm.lang("accountManage"));
		},
		// 税收明细查询
		cityTaxDetailedPage : function(gridId,params, callback) {
			return comm.getCommBsGrid(gridId, "cityTaxDetailedPage",params,callback);
		},
		// 税率查询
		queryTax : function(callback) {
			comm.requestFun("queryTax", null, callback, comm.lang("accountManage"));
		},
		// 税率调整申请
		taxAdjustmentApply : function(data, callback) {
			comm.requestFun("taxAdjustmentApply", data, callback, comm.lang("accountManage"));
		},
		// 税率调整申请查询
		taxAdjustmentApplyPage : function(params, callback) {
			return comm.getCommBsGrid("scpoint_result_table", "taxAdjustmentApplyPage",params,callback);
		},
		// 税率调整申请详情
		taxApplyDetail : function(data, callback) {
			comm.requestFun("taxApplyDetail", data, callback, comm.lang("accountManage"));
		},
			
		//获得服务器地址
		getFsServerUrl : function(pictureId) {
			var param=comm.getRequestParams();
			return  comm.domainList['fsServerUrl']+pictureId+"?userId="+param.custId+"&token="+param.token;
		},
		//查询企业税率调整的最新审批结果
		getLastTaxApply:function(callback){
			comm.requestFun("getLastTaxApply",{},callback,comm.lang("accountManage"));
		}
	};
});