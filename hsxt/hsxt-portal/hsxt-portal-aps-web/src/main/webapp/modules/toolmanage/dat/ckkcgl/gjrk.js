define(function () {
	return {
		/**
		 * 查询所有仓库
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findAllWarehouseList : function(params, callback){
			comm.requestFun("findAllWarehouseList", params, callback, comm.lang("toolmanage"));
		},
		/**
		 * 查询所有供应商
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findAllupplierList : function(params, callback){
			comm.requestFun("findAllupplierList", params, callback, comm.lang("toolmanage"));
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
		 * 入库提交
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		productEnter : function(params, callback){
			comm.requestFun("productEnter", params, callback, comm.lang("toolmanage"));
		}
	};
});