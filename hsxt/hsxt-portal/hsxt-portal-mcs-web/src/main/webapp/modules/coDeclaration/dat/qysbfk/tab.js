define(function () {
	return {
		/**
		 * 复核业务-审核提交
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		managerReviewDeclare : function(params, callback){
			comm.requestFun("managerReviewDeclare", params, callback, comm.lang("coDeclaration"));
		}
	};
});