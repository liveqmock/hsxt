define(function () {
	return {
		
		/**
		 * 查询企业信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findEntAllInfo : function(params, callback){
			comm.requestFun("findEntAllInfo", params, callback, comm.lang("companyInfo"));
		},
		
		/**
		 * 企业系统信息-添加银行卡
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		addBank : function(params, callback){
			comm.requestFun("addBank", params, callback, comm.lang("companyInfo"));
		},
		/**
		 * 企业系统信息-删除银行卡
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		delBank : function(params, callback){
			comm.requestFun("delBank", params, callback, comm.lang("companyInfo"));
		},
		/**
		 * 企业系统信息-查询银行卡
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findBanksByCustId : function(params, callback){
			comm.requestFun("findBanksByCustId", params, callback, comm.lang("companyInfo"));
		}
	};
});