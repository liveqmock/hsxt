define(function () {
	return {
		/**
		 * 企业申报新增-查询联系人信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findLinkmanByApplyId : function(params, callback){
			comm.requestFun("findLinkmanByApplyId", params, callback, comm.lang("coDeclaration"));
		},
		/**
		 * 企业申报新增-保存联系人信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		saveLinkInfo : function(params, callback){
			comm.requestFun("saveLinkInfo", params, callback, comm.lang("coDeclaration"));
		},
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