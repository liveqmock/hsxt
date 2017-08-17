define(['resourceQuotaLan'],function () {
	
	return {
		//查询管理公司在地区平台上的配额
		quotaStatOfPlat : function (params, callback) {
			comm.syncRequest({domain:'gpf_res',url:'quotaStatOfPlat'}, params, callback, 'quota');
		},
	
		//申请配额
		applyQuota : function (params, callback) {
			comm.syncRequest({domain:'gpf_res',url:'applyQuota'}, params, callback, 'quota');
		},
		
		//审批配额申请
		apprQuotaApp: function (params, callback) {
			comm.syncRequest({domain:'gpf_res',url:'apprQuotaApp'}, params, callback, 'quota');
		},
		//查询所有平台列表
		platListAll: function (params, callback) {
			comm.syncRequest({domain:'gpf_res',url:'platListAll'}, params, callback, 'quota');
		},
		//查询所有平台列表
		queryIdTyp: function (params, callback) {
			comm.syncRequest({domain:'gpf_res',url:'queryIdTyp'}, params, callback, 'quota');
		}
		,
		//查询配额申请详情
		queryQuotaAppInfo: function (params, callback) {
			comm.syncRequest({domain:'gpf_res',url:'queryQuotaAppInfo'}, params, callback, 'quota');
		}
		,
		//同步配额数据到地区平台业务系统(BS)
		syncQuotaAllot: function (params, callback) {
			comm.syncRequest({domain:'gpf_res',url:'syncQuotaAllot'}, params, callback, 'quota');
		}
		,
		//同步路由数据到总平台全局配置系统(GCS)
		syncQuotaRoute: function (params, callback) {
			comm.syncRequest({domain:'gpf_res',url:'syncQuotaRoute'}, params, callback, 'quota');
		}
	};
});