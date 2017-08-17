define(function () {
	return {
		/**
		 * 企业报备-查询注册信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findDeclareByApplyId : function(params, callback){
			comm.requestFun("findDeclareByApplyId", params, callback, comm.lang("coDeclaration"));
		}
	};
});