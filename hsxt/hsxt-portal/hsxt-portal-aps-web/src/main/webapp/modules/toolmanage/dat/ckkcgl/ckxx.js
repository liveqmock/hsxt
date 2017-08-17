define(function () {
	return {
		/**
		 * 分页查询仓库信息
		 * @param params 参数对象
		 * @param detail 自定义函数1
		 * @param del 自定义函数2
		 */
		findWarehouseList:function(params, detail, del){
			return comm.getCommBsGrid("searchTable", "findWarehouseList", params, comm.lang("toolmanage"), detail, del);
		},
		/**
		 * 查询系统角色列表
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findListRole : function(params, callback){
			comm.requestFun("findListRole", params, callback, comm.lang("toolmanage"));
		},
		/**
		 * 依据仓库ID查询仓库信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findWarehouseById : function(params, callback){
			comm.requestFun("findWarehouseById", params, callback, comm.lang("toolmanage"));
		},
		/**
		 * 保存仓库信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		saveWarehouse : function(params, callback){
			comm.requestFun("saveWarehouse", params, callback, comm.lang("toolmanage"));
		}
	};
});