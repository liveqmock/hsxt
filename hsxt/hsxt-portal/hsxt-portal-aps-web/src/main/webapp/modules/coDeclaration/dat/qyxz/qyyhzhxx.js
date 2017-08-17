define(function () {
	return {
		/**
		 * 企业申报新增-查询银行账户信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findBankByApplyId : function(params, callback){
			comm.requestFun("findBankByApplyId", params, callback, comm.lang("coDeclaration"));
		},
		/**
		 * 企业申报新增-保存银行账户信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		saveBankInfo : function(params, callback){
			comm.requestFun("saveBankInfo", params, callback, comm.lang("coDeclaration"));
		}
	};
});