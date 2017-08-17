define(function () {
	return {
		/**
		 * 查看消费者所有信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		searchConsumerAllInfo : function(params, callback){
			comm.requestFun("searchConsumerAllInfo", params, callback, comm.lang("infoManage"));
		},
		/**
		 * 修改消费者信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		updateConsumerInfo : function(params, callback){
			comm.requestFun("updateConsumerInfo", params, callback, comm.lang("infoManage"));
		}
		
	};
});