define(function () {
	return {
		/**
		 * 审批申报
		 * @param params 参数对象
		 * @param callBack 自定义函数
		 */
		serviceApprDeclare : function(params, callBack){
			comm.requestFun("serviceApprDeclare", params, callBack, comm.lang("coDeclaration"));
		}
	};
});