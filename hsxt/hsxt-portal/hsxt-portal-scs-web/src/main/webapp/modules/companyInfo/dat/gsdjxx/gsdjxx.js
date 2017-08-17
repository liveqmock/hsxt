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
		 * 更新企业信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		updateEntExtInfo : function(params, callback){
			comm.requestFun("updateEntExtInfo", params, callback, comm.lang("companyInfo"));
		},
		/**
		 * 保存经纬度
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		updateLagInfo : function(params, callback){
			comm.requestFun("updateLagInfo", params, callback, comm.lang("companyInfo"));
		}
	};
});