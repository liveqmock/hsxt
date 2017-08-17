define(function () {
	return {
		/**
		 * 查询所有工具类别
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findAllToolTypeList : function(params, callback){
			comm.requestFun("tooltype_all_lsit", params, callback, comm.lang("toolmanage"));
		}
	};
});