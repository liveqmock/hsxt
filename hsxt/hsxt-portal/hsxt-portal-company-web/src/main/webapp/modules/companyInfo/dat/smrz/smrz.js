define(function () {
	return {
		/**
		 * 初始化企业实名认证
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		initEntRealName : function(params, callback){
			comm.requestFun("initEntRealName", params, callback, comm.lang("companyInfo"));
		},
		/**
		 * 创建企业实名认证信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		createEntRealNameAuth : function(params, callback,failCallback){
			comm.requestFunForHandFail("createEntRealNameAuth", params, callback,failCallback, comm.lang("companyInfo"));
		},
		//验证码生成URL地址
		getCodeUrl:function(){
			var param=comm.getRequestParams();
			return comm.domainList['companyWeb']+comm.UrlList["generateSecuritCode"]+"?custId="+param.custId+"&type=realNameAuth&"+(new Date()).valueOf();
		}
	};
});