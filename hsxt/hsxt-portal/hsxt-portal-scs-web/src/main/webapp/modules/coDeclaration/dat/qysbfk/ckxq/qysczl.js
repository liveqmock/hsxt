define(function () {
	return {
		/**
		 * 企业报备-查询附件信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findAptitudeByApplyId : function(params, callback){
			comm.requestFun("findAptitudeByApplyId", params, callback, comm.lang("coDeclaration"));
		},
		/**
		 * 企业申报复核-保存企业资料上传
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		saveDeclareAptitude : function(params, callback){
			comm.requestFun("saveDeclareAptitude", params, callback, comm.lang("coDeclaration"));
		},
		//验证码生成URL地址
		getCodeUrl:function(){
			var param=comm.getRequestParams();
			return comm.domainList['scsWeb']+comm.UrlList["generateSecuritCode"]+"?custId="+param.custId+"&type=entApply&"+(new Date()).valueOf();
		}
	};
});