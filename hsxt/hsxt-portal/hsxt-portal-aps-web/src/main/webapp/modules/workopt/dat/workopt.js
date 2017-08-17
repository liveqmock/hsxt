define(function() {
	return {
		// 工单挂起
	/*	workOrderSuspend : function(data, callback) {
			comm.requestFun("workOrderSuspend", data, callback, comm.lang("workopt"));
		},*/
		// 工单拒绝受理
		workOrderDoor : function(data, callback) {
			comm.requestFun("workOrderDoor", data, callback, comm.lang("workopt"));
		},
		//获取工单明细
		getTmTaskByBizNo : function(data, callback) {
			comm.requestFun("getTmTaskByBizNo", data, callback, comm.lang("workopt"));
		}
	};
});