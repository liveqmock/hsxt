define(function () {
	return {
		/**
		 * 售后订单管理-售后工具配送列表
		 * @param params 参数对象
		 * @param detail 自定义函数1
		 * @param del 自定义函数2
		 */
		findShippingAfterList:function(params, detail, del){
			return comm.getCommBsGrid("searchTable", "findPrintListResult", params, comm.lang("toolorder"), detail, del);
		},
		/**
		 * 售后订单管理-消查询发货清单
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findShippingAfterById : function(params, callback){
			comm.requestFun("findShippingAfterById", params, callback, comm.lang("toolorder"));
		},
		/**
		 * 售后订单管理-查询企业信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findCompanyInfo : function(params, callback){
			comm.requestFun("findCompanyInfo", params, callback, comm.lang("toolorder"));
		},
		/**
		 * 查询售后发货清单打印信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findPrintDetailAfterById : function(params, callback){
			comm.requestFun("findPrintDetailAfterById", params, callback, comm.lang("toolorder"));
		}
	}
});