define(function () {
	return {
		/**
		 * 企业报备-异议报备查询
		 * @param params 参数对象
		 * @param deitFun 自定义函数
		 * @param delFun 自定义函数
		 */
		findDissEntFilingList : function(params, deitFun, delFun){
			comm.getCommBsGrid(null, "findDissEntFilingList", params, comm.lang("coDeclaration"), deitFun, delFun);
		},
		/**
		 * 企业报备-查询异议报备详情
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findFilingById : function(params, callback){
			comm.requestFun("findFilingById", params, callback, comm.lang("coDeclaration"));
		},
	};
});