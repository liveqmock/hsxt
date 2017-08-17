define(function () {
	return {
		/**
		 * 查询企业信息
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findCompanyAllInfo : function(params, callback){
			comm.requestFun("findCompanyAllInfo", params, callback, comm.lang("companyInfo"));
		},
		/**
		 * 企业资源管理查询
		 * @param params 参数对象
		 * @param detail 自定义函数
		 */
		findBelongEntInfoList : function(gridId, params, funOne, funTwo, funThree){
			return comm.getCommBsGrid(gridId, "findBelongEntInfoList", params, comm
					.lang("resouceManage"), funOne, funTwo, funThree);
		},
		
		/**
		 * 企业系统信息-查询银行卡
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		findCompanyBanksByCustId : function(params, callback){
			comm.requestFun("findCompanyBanksByCustId", params, callback, comm.lang("resouceManage"));
		},
		/**
		 * 企业系统信息-重新启用成员企业
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		updateEntStatusInfo : function(params, callback){
			comm.requestFun("updateEntStatusInfo", params, callback, comm.lang("resouceManage"));
		},
		/**
		 * 企业系统信息-注销成员企业
		 * @param params 参数对象
		 * @param callback 回调函数
		 */
		createMemberQuit : function(params, callback){
			comm.requestFun("createMemberQuit", params, callback, comm.lang("resouceManage"));
		},
		/**
		 * 成员企业资格维护-列表查询
		 * @param params 参数对象
		 * @param funOne 自定义函数
		 * @param funTwo 自定义函数
		 * @param funThree 自定义函数
		 */
		findQualMainList : function(gridId, params, funOne, funTwo, funThree) {
			return comm.getCommBsGrid(gridId, "findQualMainList", params, comm
					.lang("resouceManage"), funOne, funTwo, funThree);
		}
	};
});