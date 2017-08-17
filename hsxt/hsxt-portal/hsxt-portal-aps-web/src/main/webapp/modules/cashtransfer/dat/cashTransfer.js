define(function () {
	return {
		//银行转账查询统计信息
		getTransferRecordListCount : function(data,callback){
			comm.requestFun("get_transferRecord_listcount",data,callback,comm.lang("cashTransfer"));
		},
		//删除消息
		transBatch : function(data,callback){
			comm.requestFun("transBatch",data,callback,comm.lang("cashTransfer"));
		},
		//删除多条消息
		transRevoke : function(data,callback){
			comm.requestFun("transRevoke",data,callback,comm.lang("cashTransfer"));
		},
		//转账失败处理
		trans_failBack_list : function(data,callback){
			comm.requestFun("trans_failBack_list",data,callback,comm.lang("cashTransfer"));
		},
		//转账对账
		trans_checkUp_Account : function(data,callback){
			comm.requestFun("trans_checkUp_Account",data,callback,comm.lang("cashTransfer"));
		},
		//银行对账
		transCheckUpAccount: function(data,callback){
			comm.requestFun("trans_checkUp_Account",data,callback,comm.lang("cashTransfer"));
		}
	};
});