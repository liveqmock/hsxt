define(function () {
	return {
		/**
		 * 企业申报-申报提交
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		submitDeclare : function(params, callback){
			comm.requestFun("submitDeclare", params, callback, comm.lang("coDeclaration"));
		}
	};
});