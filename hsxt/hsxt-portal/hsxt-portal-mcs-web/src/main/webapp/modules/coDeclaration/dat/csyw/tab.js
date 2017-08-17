define(function () {
	return {
		/**
		 * 初审业务-审核提交
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		managerApprDeclare : function(params, callback){
			comm.requestFun("managerApprDeclare", params, callback, comm.lang("coDeclaration"));
		}
	};
});