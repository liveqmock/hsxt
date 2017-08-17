define(function () {
	return {
		/**
		 * 查询工商登记信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		searchEntExtInfo : function(params, callback){
			comm.requestFun("searchEntExtInfo", params, callback, comm.lang("infoManage"));
		},
		/**
		 * 保存工商登记信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		updateDeclareEnt : function(params, callback){
			comm.requestFun("updateRelEntBaseInfo", params, callback, comm.lang("infoManage"));
		}
	};
});