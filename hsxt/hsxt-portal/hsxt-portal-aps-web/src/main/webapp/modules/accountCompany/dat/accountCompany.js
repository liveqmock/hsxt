define(function () {
	return {
		//账户操作明细
		getAccoutGrid : function (gridId,data, callback) {
			comm.getCommBsGrid(gridId,"query_company_mxcx",data,null,callback);
		},
		//账户操作明细详单
		getAccountDetailed : function (data, callback) {
			comm.requestFun("get_account_company_detailed",data,callback,comm.lang("accountCompany"));
		},
		//投资分红流水明细查询
		tzfh_detailed_page:function(gridId,params,callback){
			return comm.getCommBsGrid(gridId, "tzfh_Company_detailed_page", params,null, callback);
		},
		//查询消费积分分配详情
		getPointAllotDetailedList : function (gridId,data, callback) {
			comm.getCommBsGrid(gridId,"getPointAllotDetailedList", data,comm.lang("accountManage"), callback);
		},
		//查询消费积分分配详情
		getPointAllotDetailed : function (data, callback) {
			comm.requestFun("getPointAllotDetailed" , data, callback,comm.lang("accountManage"));
		}
	};
});