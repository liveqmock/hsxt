define(function () {
	return {
		/**
		 * 企业申报-查询企业资料上传
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findAptitudeByApplyId : function(params, callback){
			comm.requestFun("findAptitudeByApplyId", params, callback, comm.lang("coDeclaration"));
		},
		/**
		 * 企业申报-保存企业资料上传
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		saveDeclareAptitude : function(params, callback){
			comm.requestFun("saveDeclareAptitude", params, callback, comm.lang("coDeclaration"));
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