define(function () {
	return {
		/**
		 * 开启系统
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		openSystem : function(params, callback){
			comm.requestFun("openSystem", params, callback, comm.lang("coDeclaration"));
		}
	};
});