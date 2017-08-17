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
		 * 获取验证码
		 * @param callBack 自定义函数
		 */
		generateSecuritCode : function(callBack){
			var param=comm.getRequestParams();
			return comm.domainList['apsWeb']+comm.UrlList["generateSecuritCode"]+"?custId="+param.custId+"&type=entDeclare&"+(new Date()).valueOf();
			
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