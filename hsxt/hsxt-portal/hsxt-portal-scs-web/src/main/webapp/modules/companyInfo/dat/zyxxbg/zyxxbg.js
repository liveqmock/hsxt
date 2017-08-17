define(function () {
	return {
		/**
		 * 企业系统信息-创建企业重要信息变更
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		createEntChange : function(params, callback){
			comm.requestFun("createEntChange", params, callback, comm.lang("companyInfo"));
		},
		/**
		 * 企业系统信息-查询重要信息变更
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findEntChangeByApplyId : function(params, callback){
			comm.requestFun("findEntChangeByApplyId", params, callback, comm.lang("companyInfo"));
		},
		/**
		 * 企业系统信息-初始化重要信息变更
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		initEntChange : function(params, callback){
			comm.requestFun("initEntChange", params, callback, comm.lang("companyInfo"));
		},
		/**
		 * 企业系统信息-查询重要信息变更列表
		 * @param params 参数对象
		 * @param funOne 自定义函数
		 */
		findEntChangeList : function(params, detail){
			comm.getCommBsGrid(null, "findEntChangeList", params, comm.lang("companyInfo"), detail);
		}
	};
});