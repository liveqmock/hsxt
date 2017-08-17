define(function(){
	return {
		//货币账户余额查询
		findCurrencyBlance : function (callback) {
			comm.requestFun("findCurrencyBlance" , null, callback,comm.lang("accountManage"));
		},
		//货币明细查询
		currencyDetailedPage:function(params,callback){
			return comm.getCommBsGrid("tableDetail", "currencyDetailedPage", params, comm.lang("accountManage"),callback);
		},
		getdetailInfo : function(data,callback){
			comm.requestFun("getdetailInfo",data,callback,comm.lang("accountManage"));
		}
	};	
});
