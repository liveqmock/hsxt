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
		 * 企业申报-查询企业配额数和可用互生号列表
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findResNoListAndQuota : function(params, callback){
			comm.requestFun("findResNoListAndQuota", params, callback, comm.lang("coDeclaration"));
		},
		/**
		 * 企业申报-查询企业服务公司互生号列表
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findSerResNoList : function(params, callback){
			comm.requestFun("findSerResNoList", params, callback, comm.lang("coDeclaration"));
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
		 * 企业申报新增-查询推广服务公司互生号资源详情
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findResNoDetail : function(params, callback){
			comm.requestFun("findResNoDetail", params, callback, comm.lang("coDeclaration"));
		},
		/**
		 * 企业申报新增-校验互生号是否被校验
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		checkValidEntResNo : function(params, callback){
			comm.requestFun("checkValidEntResNo", params, callback, comm.lang("coDeclaration"));
		}
	};
});