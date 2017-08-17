define(function () {
	return {
		/**
		 * 查询申报信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findDeclareAppInfoByApplyId : function(params, callback){
			comm.requestFun("findDeclareAppInfoByApplyId", params, callback, comm.lang("coDeclaration"));
		}
	};
});