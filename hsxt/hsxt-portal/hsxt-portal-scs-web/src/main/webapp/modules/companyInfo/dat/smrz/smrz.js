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
		createEntRealNameAuth : function(params, callback){
			comm.requestFun("createEntRealNameAuth", params, callback, comm.lang("companyInfo"));
		}
	};
});