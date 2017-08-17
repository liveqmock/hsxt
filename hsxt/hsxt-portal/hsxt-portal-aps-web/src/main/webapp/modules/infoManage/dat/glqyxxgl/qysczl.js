define(function () {
	return {
		/**
		 * 查询附件信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findAptitudeByApplyId : function(params, callback){
			comm.requestFun("searchEntExtInfo", params, callback, comm.lang("infoManage"));
		},
		/**
		 * 保存企业资料上传
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		saveDeclareAptitude : function(params, callback){
			comm.requestFun("updateRelEntBaseInfo", params, callback, comm.lang("infoManage"));
		},
		//验证码生成URL地址
		getCodeUrl:function(){
			var param=comm.getRequestParams();
			return comm.domainList['apsWeb']+comm.UrlList["generateSecuritCode"]+"?custId="+param.custId+"&type=qysczlType&"+(new Date()).valueOf();
		},
		//校验验证码
		validateSmsCode:function(params, callback){
			comm.requestFun("validateSmsCode", params, callback, comm.lang("infoManage"));
		}
	};
});