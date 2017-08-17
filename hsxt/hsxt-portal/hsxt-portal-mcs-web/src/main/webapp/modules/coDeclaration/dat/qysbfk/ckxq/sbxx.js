define(function () {
	return {
		/**
		 * 查询申报信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findDeclareAppInfoByApplyId : function(params, callback){
			comm.requestFun("findDeclareAppInfoByApplyId", params, callback, comm.lang("coDeclaration"));
		},
		/**
		 * 查询可用的服务公司互生号
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findSerResNoList : function(params, callback){
			comm.requestFun("findSerResNoList", params, callback, comm.lang("coDeclaration"));
		},
		/**
		 * 管理公司选服务公司互生号
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		managePickResNo : function(params, callback){
			comm.requestFun("managePickResNo", params, callback, comm.lang("coDeclaration"));
		},
		/**
		 * 查询服务公司可用互生号--顺序选号
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findSerResNo : function(params, callback){
			comm.requestFun("findSerResNo", params, callback, comm.lang("coDeclaration"));
		}
	};
});