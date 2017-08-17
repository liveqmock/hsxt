define(function () {
	return {
		/**
		 * 查询附件信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findAptitudeByApplyId : function(params, callback){
			comm.requestFun("findAptitudeByApplyId", params, callback, comm.lang("coDeclaration"));
		}
	};
});