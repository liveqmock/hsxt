define(function () {
	return {
		/**
		 * 工具配置信息-供应商列表
		 * @param params 参数对象
		 * @param detail 自定义函数1
		 * @param del 自定义函数2
		 */
		findSupplierList:function(params, detail, del){
			return comm.getCommBsGrid("searchTable", "findSupplierList", params, comm.lang("toolmanage"), detail, del);
		},
		/**
		 * 工具配置信息-删除供应商
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		removeSupplier : function(params, callback){
			comm.requestFun("removeSupplier", params, callback, comm.lang("toolmanage"));
		},
		/**
		 * 工具配置信息-依据供应商编号查询供应商详情
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findSupplierById : function(params, callback){
			comm.requestFun("findSupplierById", params, callback, comm.lang("toolmanage"));
		},
		/**
		 * 工具配置信息-保存供应商
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		saveSupplier : function(params, callback){
			comm.requestFun("saveSupplier", params, callback, comm.lang("toolmanage"));
		}
	};
});