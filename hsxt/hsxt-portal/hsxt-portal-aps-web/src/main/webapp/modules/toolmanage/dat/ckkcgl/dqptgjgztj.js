define(function () {
	return {
		/**
		 * 统计地区平台仓库工具
		 * @param params 参数对象
		 * @param detail 自定义函数
		 */
		findStatisticPlatWhToollList:function(params, detail){
			return comm.getCommBsGrid("searchTable", "findStatisticPlatWhToollList", params, comm.lang("toolmanage"), detail);
		}
	};
});