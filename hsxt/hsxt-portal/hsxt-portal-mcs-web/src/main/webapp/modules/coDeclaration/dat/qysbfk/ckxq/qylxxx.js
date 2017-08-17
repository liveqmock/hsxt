define(function () {
	return {
		/**
		 * 查询企业联系信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findLinkmanByApplyId : function(params, callback){
			comm.requestFun("findLinkmanByApplyId", params, callback, comm.lang("coDeclaration"));
		},
		/**
		 * 保存联系人信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		saveLinkInfo : function(params, callback){
			comm.requestFun("saveLinkInfo", params, callback, comm.lang("coDeclaration"));
		}
	};
});