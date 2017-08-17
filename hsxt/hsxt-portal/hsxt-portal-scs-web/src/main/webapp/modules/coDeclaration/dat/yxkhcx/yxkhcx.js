define(function () {
	return {
		/**
		 * 意向客户查询
		 * @param params 参数对象
		 * @param deitFun 自定义函数
		 * @param delFun 自定义函数
		 */
		findIntentionCustList : function(params, deitFun, delFun){
			return comm.getCommBsGrid(null, "findIntentionCustList", params, comm.lang("coDeclaration"), deitFun, delFun);
		},
		/**
		 * 意向客户查询详情
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findIntentCustById : function(params, callback){
			comm.requestFun("findIntentCustById", params, callback, comm.lang("coDeclaration"));
		},
		/**
		 * 处理客户意向
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		dealIntentCustById : function(params, callback){
			comm.requestFun("dealIntentCustById", params, callback, comm.lang("coDeclaration"));
		},
	};
});