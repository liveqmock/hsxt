define(function () {
	return {
		/**
		 * 操作员列表查询
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findOperatorList : function(params, callback){
			comm.requestFun("list_operator_buentcustId", params, callback, comm.lang("systemManage"),true);
		},
		/**
		 * 获取地区平台信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findMainInfoByResNo : function(params, callback){
			comm.requestFun("findMainInfoByResNo", params, callback, comm.lang("systemManage"));
		}
	};
});