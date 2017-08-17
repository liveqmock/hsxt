define(function () {
	return {
		/**
		 * 查询申报增值点列表
		 * @param params 参数对象
		 * @param detail 函数
		 */
		findIncrementList : function(params, detail){
			comm.getCommBsGrid(null, "findIncrementList", params, comm.lang("coDeclaration"), detail);
		},
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
		 * 企业申报-查询企业配额数和可用互生号列表
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findResNoListAndQuota : function(params, callback){
			comm.requestFun("findResNoListAndQuota", params, callback, comm.lang("coDeclaration"));
		},
		/**
		 * 企业申报-依据互生号查询企业信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findEntInfo : function(params, callback){
			comm.requestFun("findEntInfo", params, callback, comm.lang("coDeclaration"));
		},
		/**
		 * 企业申报-查询管理公司信息和服务公司配额数
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findManageEntAndQuota : function(params, callback){
			comm.requestFun("findManageEntAndQuota", params, callback, comm.lang("coDeclaration"));
		},
		/**
		 * 企业申报-保存申报增值点信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		saveIncrement : function(params, callback){
			comm.requestFun("saveIncrement", params, callback, comm.lang("coDeclaration"));
		},
	};
});