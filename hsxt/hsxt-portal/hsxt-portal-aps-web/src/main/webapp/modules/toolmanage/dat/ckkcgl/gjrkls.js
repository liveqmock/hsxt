define(function () {
	return {
		/**
		 * 分页查询工具入库流水
		 * @param params 参数对象
		 * @param detail 自定义函数
		 */
		findToolEnterSerialList:function(params, detail){
			return comm.getCommBsGrid("searchTable", "findToolEnterSerialList", params, comm.lang("toolmanage"), detail);
		}
	};
});