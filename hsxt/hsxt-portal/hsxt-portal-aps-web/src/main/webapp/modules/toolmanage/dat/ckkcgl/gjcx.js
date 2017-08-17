define(function () {
	return {
		/**
		 * 工具查询
		 * @param params 参数对象
		 * @param detail 自定义函数
		 */
		findToolDeviceUseList:function(params, detail){
			return comm.getCommBsGrid("searchTable", "findToolDeviceUseList", params, comm.lang("toolmanage"), detail);
		}
	};
});