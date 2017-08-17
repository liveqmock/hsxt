define(function () {
	return {
		/**
		 * 企业报备-查询工商登记信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findDeclareEntByApplyId : function(params, callback){
			comm.requestFun("findDeclareEntByApplyId", params, callback, comm.lang("coDeclaration"));
		},
		/**
		 * 企业申报复核-保存工商登记信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		saveDeclareEnt : function(params, callback){
			comm.requestFun("saveDeclareEnt", params, callback, comm.lang("coDeclaration"));
		}
	};
});