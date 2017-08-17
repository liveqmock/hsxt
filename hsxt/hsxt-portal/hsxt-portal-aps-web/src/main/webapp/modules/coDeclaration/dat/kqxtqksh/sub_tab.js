define(function () {
	return {
		/**
		 * 开系统欠款审核-审核
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		apprDebtOpenSys : function(params, callback){
			comm.requestFun("apprDebtOpenSys", params, callback, comm.lang("coDeclaration"));
		}
	};
});