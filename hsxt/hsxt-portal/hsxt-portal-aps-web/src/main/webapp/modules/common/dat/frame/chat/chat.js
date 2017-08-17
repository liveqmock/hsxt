define(function () {
	return {
		/**
		 * 查询分组及用户
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findTaskGroupInfo : function(params, callback){
			comm.requestFun("findTaskGroupInfo", params, callback, comm.lang("common"));
		}
	};
});