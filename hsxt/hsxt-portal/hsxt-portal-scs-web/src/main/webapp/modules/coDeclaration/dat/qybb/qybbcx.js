define(function () {
	return {
		/**
		 * 企业报备查询
		 * @param params 参数对象
		 * @param deitFun 自定义函数
		 * @param addFun 自定义函数
		 * @param delFun 自定义函数
		 */
		findEntFilingList : function(params, deitFun, addFun, delFun){
			comm.getCommBsGrid(null, "findEntFilingList", params, comm.lang("coDeclaration"), deitFun, addFun, delFun);
		},
		/**
		 * 企业报备查询-提异议
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		raiseDissent : function (params, callback){
			comm.requestFun("raiseDissent", params, callback, comm.lang("coDeclaration"));
		},
		/**
		 * 企业报备查看详情
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findFilingById : function (params, callback){
			comm.requestFun("findFilingById", params, callback, comm.lang("coDeclaration"));
		},
		/**
		 * 依据申请编号删除报备企业基本信息
		 * @param params 参数对象
		 * @param callBack 自定义函数
		 */
		delEntFilingById : function(params, callBack){
			comm.requestFun("delEntFilingById", params, callBack, comm.lang("coDeclaration"));
		},
		/**
		 * 依据申请编号查询报备进行步骤
		 * @param params 参数对象
		 * @param callBack 自定义函数
		 */
		findFilingStep : function(params, callBack){
			comm.requestFun("findFilingStep", params, callBack, comm.lang("coDeclaration"));
		},
	};
});