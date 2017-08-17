define(function () {
	return {
		/**
		 * 工具登记-设备序列号查询设备清单
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findDeviceDetailByNo : function(params, callback){
			comm.requestFun("findDeviceDetailByNo", params, callback, comm.lang("toolmanage"));
		},
		/**
		 * 工具登记
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		addDeviceUseRecord : function(params, callback){
			comm.requestFun("addDeviceUseRecord", params, callback, comm.lang("toolmanage"));
		}
	};
});