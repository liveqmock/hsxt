define(function () {
	return {
		/**
		 * 选择服务公司互生号
		 * @param params 参数对象
		 * @param detail 自定义函数
		 * @param selectRowEvent 自定义函数
		 */
		findServicesPointList : function(params, detail, selectRowEvent){
			return comm.getCommBsGrid("pointTable", "findServicesPointList", params, comm.lang("coDeclaration"), detail, null, null, null, null, selectRowEvent);
		}
	};
});