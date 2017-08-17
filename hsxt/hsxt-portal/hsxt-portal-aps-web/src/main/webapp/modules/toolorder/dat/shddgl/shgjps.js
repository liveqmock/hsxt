define(function () {
	return {
		/**
		 * 工具制作管理-个性卡定制查询列表
		 * @param params 参数对象
		 * @param detail 自定义函数
		 */
		findShipingList:function(params, detail){
			return comm.getCommBsGrid("searchTable", "findShipingList", params, comm.lang("toolorder"), detail);
		},
		/**
		 * 售后订单管理-添加售后服务发货单
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		addToolShippingAfter : function(params, callback){
			comm.requestFun("addToolShippingAfter", params, callback, comm.lang("toolorder"));
		},
		/**
		 * 售后订单管理-查询发货单的数据
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findAfterShipingData : function(params, callback){
			comm.requestFun("findAfterShipingData", params, callback, comm.lang("toolorder"));
		},
		/**
		 * 售后订单管理-查询企业信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findCompanyInfo : function(params, callback){
			comm.requestFun("findCompanyInfo", params, callback, comm.lang("toolorder"));
		}
	}
});