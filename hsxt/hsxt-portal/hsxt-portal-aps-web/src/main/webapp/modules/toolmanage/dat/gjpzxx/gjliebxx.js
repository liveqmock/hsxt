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
		findToolProductList : function(params, detail,edit,add){
			return comm.getCommBsGrid("searchTable", "findToolProductPage", params, comm.lang("toolmanage"), detail, edit,add);
		},
		/**
		 * 查询工具详情
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		queryToolProductInfo : function(params, callback){
			comm.requestFun("queryToolProductInfo", params, callback,comm.lang("toolmanage"));
		},
		/**
		 * 根据申请编号查询上下架详情
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		queryToolProductUpDownById : function(params, callback){
			comm.requestFun("queryToolProductUpDownById", params, callback,comm.lang("toolmanage"));
		},
		
		/**add
		 * 新增工具详情
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		addToolProduct : function(params, callback,callback1){
			comm.requestFun("addToolProduct", params, callback,comm.lang("toolmanage"),callback1);
		},

		/**modify
		 * 修改工具
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		modifyToolProduct : function(params, callback,callback1){
			comm.requestFun("modifyToolProduct", params, callback,comm.lang("toolmanage"),callback1);
		},
		/**
		 * 下架工具申请
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		applyToolProductToDown : function(params, callback){
			comm.requestFun("applyToolProductToDown", params, callback,comm.lang("toolmanage"));
		},
		/**
		 * 工具价格修改申请
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		applyChangePrice : function(params, callback){
			comm.requestFun("applyChangePrice", params, callback,comm.lang("toolmanage"));
		},
		
		/**
		 * 查询工具下架审批列表
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findToolDoawnList : function(params, detail,edit,callback){
			return comm.getCommBsGrid("searchTable", "findToolDoawnList", params, comm.lang("toolmanage"), detail,edit,callback);
		},
		/**
		 * 工具产品下架审批
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		apprToolProductForDown : function(params, callback){
			comm.requestFun("apprToolProductForDown", params, callback,comm.lang("toolmanage"));
		},
		/**
		 * 查询工具价格审批列表
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findToolPriceList : function(params, detail,edit,callback){
			return comm.getCommBsGrid("searchTable", "findToolPriceList", params, comm.lang("toolmanage"), detail,edit,callback);
		},
		/**
		 * 工具产品上架(价格)审批
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		apprToolProductForUp : function(params, callback){
			comm.requestFun("apprToolProductForUp", params, callback,comm.lang("toolmanage"));
		},
		/**
		 * 工具价格审批，工具下架审批，拒绝受理，挂起
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		refuseAndHoldOperate : function(params, callback){
			comm.requestFun("refuseAndHoldOperate", params, callback,comm.lang("toolmanage"));
		},
		/**
		 * 入库提交
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		productEnter : function(params, callback){
			comm.requestFun("productEnter", params, callback, comm.lang("toolmanage"));
		},
	};
});