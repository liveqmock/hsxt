define(function () {
	return {
		/**
		 * 企业系统信息-添加银行卡，并保存记录
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		addBankInfo : function(params, callback){
			comm.requestFun("addBankInfo", params, callback, comm.lang("infoManage"));
		},
		/**
		 * 企业系统信息-删除银行卡，并保存记录
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		delBankInfo : function(params, callback){
			comm.requestFun("delBankInfo", params, callback, comm.lang("infoManage"));
		},
		/**
		 * 企业系统信息-查询银行卡
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findBanksByBelongCustId : function(params, callback){
			comm.requestFun("findBanksByBelongCustId", params, callback, comm.lang("infoManage"));
		}
	};
});