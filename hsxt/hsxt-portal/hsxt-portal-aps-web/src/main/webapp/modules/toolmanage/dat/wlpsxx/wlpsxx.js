define(function () {
	return {
		/**
		 * 配送方式查询
		 * @param params 参数对象
		 * @param delFun 删除动作
		 * @param detFun 详情动作
		 * @param update 修改动作
		 */
		findLogisticsList : function(params, delFun, detFun, update){
			comm.getCommBsGrid(null, "findLogisticsList", params, comm.lang("toolmanage"), delFun, detFun, update);
		},
		/**
		 * 添加配送方式
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		addShipping : function(params, callback){
			comm.requestFun("addShipping", params, callback, comm.lang("toolmanage"));
		},
		/**
		 * 修改配送方式
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		modifyShipping : function(params, callback){
			comm.requestFun("modifyShipping", params, callback, comm.lang("toolmanage"));
		},
		/**
		 * 删除配送方式
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		removeShipping : function(params, callback){
			comm.requestFun("removeShipping", params, callback, comm.lang("toolmanage"));
		},
	};
});