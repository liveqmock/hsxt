define(function () {
	return {
		/**
		 * 分页查询库存预警
		 * @param params 参数对象
		 * @param detail 自定义函数1
		 */
		findToolEnterStockWarningList:function(params, detail){
			return comm.getCommBsGrid("searchTable", "findToolEnterStockWarningList", params, comm.lang("toolmanage"), detail);
		}
	};
});