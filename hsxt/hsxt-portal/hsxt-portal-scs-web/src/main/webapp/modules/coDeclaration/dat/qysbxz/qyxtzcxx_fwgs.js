define(function () {
	return {
		/**
		 * 企业申报-查询系统注册信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findDeclareByApplyId : function(params, callback){
			comm.requestFun("findDeclareByApplyId", params, callback, comm.lang("coDeclaration"));
		},
		/**
		 * 企业申报-查询增值信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findIncrement : function(params, callback){
			comm.requestFun("findIncrement", params, callback, comm.lang("coDeclaration"));
		},
		/**
		 * 企业申报新增-保存系统注册信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		saveDeclare : function(params, callback){
			comm.requestFun("saveDeclare", params, callback, comm.lang("coDeclaration"));
		},
		/**
		 * 企业申报-查询管理公司信息和服务公司配额数
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findManageEntAndQuota : function(params, callback){
			comm.requestFun("findManageEntAndQuota", params, callback, comm.lang("coDeclaration"));
		},
	};
});