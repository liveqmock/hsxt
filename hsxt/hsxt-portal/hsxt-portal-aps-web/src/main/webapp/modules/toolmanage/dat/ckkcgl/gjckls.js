define(function () {
	return {
		/**
		 * 分页查询工具出库流水
		 * @param params 参数对象
		 * @param detail 自定义函数
		 */
		findToolOutSerialList:function(params, detail){
			return comm.getCommBsGrid("searchTable", "findToolOutSerialList", params, comm.lang("toolmanage"), detail);
		}
	};
});