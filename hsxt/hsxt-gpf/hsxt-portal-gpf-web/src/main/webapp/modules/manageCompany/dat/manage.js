define(['manageCompanyLan'],function () {
	
	return {
		//查询管理公司信息
		manageEntList : function (params, callback) {
			comm.syncRequest({domain:'gpf_res',url:'manageEntList'}, params, callback, 'manage');
		},
		//添加管理公司及其与平台关系
		addPlatMent : function (params, callback) {
			comm.syncRequest({domain:'gpf_res',url:'addPlatMent'}, params, callback, 'manage');
		},
		//同步管理公司信息到用户中心
		syncManageToUc : function (params, callback) {
			comm.syncRequest({domain:'gpf_res',url:'syncManageToUc'}, params, callback, 'manage');
		},
		//同步管理公司信息到业务系统
		syncManageToBs : function (params, callback) {
			comm.syncRequest({domain:'gpf_res',url:'syncManageToBs'}, params, callback, 'manage');
		}
	};
});