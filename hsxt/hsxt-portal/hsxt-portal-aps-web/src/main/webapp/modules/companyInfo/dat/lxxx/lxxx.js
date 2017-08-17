define(function () {
	return {
		/**
		 * 查询企业信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findEntAllInfo : function(params, callback){
			comm.requestFun("findEntAllInfo", params, callback, comm.lang("companyInfo"));
		},
		/**
		 * 更新企业联系信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		updateLinkInfo : function(params, callback){
			comm.requestFun("updateLinkInfo", params, callback, comm.lang("companyInfo"));
		},
		/**
		 * 发送验证邮箱的邮件
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		validEmail : function(params, callback){
			comm.requestFun("validEmail", params, callback, comm.lang("companyInfo"));
		}
	};
});