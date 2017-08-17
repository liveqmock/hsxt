define(function () {
	return {
		/**
		 * 查询绑定的快捷支付银行
		 * @param params 参数信息
		 * @param callBack 回调函数
		 */
		findPayBanksByCustId : function(params, callBack){
			comm.requestFun("findPayBanksByCustId" , params, callBack, comm.lang("companyInfo"));
		},
		/**
		 * 添加快捷支付银行
		 * @param params 参数信息
		 * @param callBack 回调函数
		 */
		addPayBank : function(params, callBack){
			comm.requestFun("addPayBank", params, callBack, comm.lang("companyInfo"));
		},
		/**
		 * 删除快捷支付银行
		 * @param params 参数信息
		 * @param callBack 回调函数
		 */
		delPayBank : function(params, callBack){
			comm.requestFun("delPayBank" , params, callBack, comm.lang("companyInfo"));
		}
	};
});