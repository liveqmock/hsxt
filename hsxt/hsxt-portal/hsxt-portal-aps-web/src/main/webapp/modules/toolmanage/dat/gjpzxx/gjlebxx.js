define(function () {
	return {
		/**
		 * 查询所有工具类别
		 * @param params 参数
		 * @param callback 回调函数
		 */
		listToolCategory : function(params, callback){
			 comm.requestFun("listToolCategory", params, callback, comm.lang("toolmanage"));
		}
		
	};
});