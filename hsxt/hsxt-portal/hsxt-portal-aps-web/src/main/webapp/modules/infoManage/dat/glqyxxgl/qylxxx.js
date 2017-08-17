define(function () {
	return {
		/**
		 * 查询企业联系信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		searchEntExtInfo : function(params, callback){
			comm.requestFun("searchEntExtInfo", params, callback, comm.lang("infoManage"));
		},
		/**
		 * 保存联系人信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		updateLinkInfo : function(params, callback){
			comm.requestFun("updateRelEntBaseInfo", params, callback, comm.lang("infoManage"));
		}
	};
});