define(function () {
	return {
		/**
		 * 分页查询库存预警
		 * @param params 参数对象
		 * @param detail1 自定义函数1
		 * @param detail2 自定义函数2
		 * @param detail3 自定义函数3
		 */
		findConfigToolStockList:function(params, detail1, detail2, detail3){
			return comm.getCommBsGrid("searchTable", "findConfigToolStockList", params, comm.lang("toolmanage"), detail1, detail2, detail3);
		},
		/**
		 * 库存统计-查看机器码
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findPosDeviceSeqNoDetail : function(params, callback){
			comm.requestFun("findPosDeviceSeqNoDetail", params, callback, comm.lang("toolmanage"));
		},
		/**
		 * 库存统计-盘库
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		toolEnterInventory : function(params, callback){
			comm.requestFun("toolEnterInventory", params, callback, comm.lang("toolmanage"));
		},
		/**
		 * 库存统计-入库抽检
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		toolEnterCheck : function(params, callback){
			comm.requestFun("toolEnterCheck", params, callback, comm.lang("toolmanage"));
		},
		/**
		 * 查询所有仓库
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findAllWarehouseList : function(params, callback){
			comm.requestFun("findAllWarehouseList", params, callback, comm.lang("toolmanage"));
		},
		/**
		 * 查询工具列表
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findToolProductList : function(params, callback){
			comm.requestFun("findToolProductList", params, callback, comm.lang("toolmanage"));
		},
		/**
		 * 查询工具列表
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findToolProductAll : function(params, callback){
			comm.requestFun("findToolProductAll", params, callback, comm.lang("toolmanage"));
		}
	};
});