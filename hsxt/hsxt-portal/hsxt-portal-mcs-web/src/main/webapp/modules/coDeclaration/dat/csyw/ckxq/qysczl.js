define(function () {
	return {
		/**
		 * 查询附件信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findAptitudeByApplyId : function(params, callback){
			comm.requestFun("findAptitudeByApplyId", params, callback, comm.lang("coDeclaration"));
		},
		/**
		 * 保存企业资料上传
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		saveDeclareAptitude : function(params, callback){
			comm.requestFun("saveDeclareAptitude", params, callback, comm.lang("coDeclaration"));
		}
	};
});