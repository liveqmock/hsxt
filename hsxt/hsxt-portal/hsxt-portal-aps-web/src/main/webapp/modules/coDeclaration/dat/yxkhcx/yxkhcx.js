define(function () {
	return {
		/**
		 * 意向客户查询
		 * @param params 参数对象
		 * @param deitFun 自定义函数
		 */
		findIntentionCustList : function(params, deitFun){
			return comm.getCommBsGrid(null, "findIntentionCustList", params, comm.lang("coDeclaration"), deitFun);
		},
		/**
		 * 意向客户查询详情
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findIntentCustById : function(params, callback){
			comm.requestFun("findIntentCustById", params, callback, comm.lang("coDeclaration"));
		}
	};
});