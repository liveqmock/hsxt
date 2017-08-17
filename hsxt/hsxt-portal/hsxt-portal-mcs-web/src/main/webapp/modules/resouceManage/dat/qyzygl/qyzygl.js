define(function(){
	return {
		//企业下的消费者资源统计
		entResPage:function(params,callBack){
			return comm.getCommBsGrid("detailTable", "entResPage", params, comm.lang("resouceManage"),callBack);
		},
		//获取企业明细
		getEntAllInfo : function (params,callback) {
			comm.requestFun("getEntAllInfo" , params, callback,comm.lang("resouceManage"));
		},
		//获取企业银行列表
		getEntBankList : function (params,callback) {
			comm.requestFun("getEntBankList" , params, callback,comm.lang("resouceManage"));
		}
	
	};	
});
