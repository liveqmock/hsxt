define(function () {
	return {
		/**
		 * 企业报备-查询企业联系信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findLinkmanByApplyId : function(params, callback){
			comm.requestFun("findLinkmanByApplyId", params, callback, comm.lang("coDeclaration"));
		}
	};
});