define(['areaPlatformLan'],function () {
	
	return {
		//查询未初始化的平台信息
		unInitPlatList : function (params, callback) {
			comm.syncRequest({domain:'gpf_res',url:'unInitPlatList'}, params, callback, 'plat');
		},
		//查询平台信息列表
		platList : function (params, callback) {
			comm.syncRequest({domain:'gpf_res',url:'platList'}, params, callback, 'plat');
		},
		//添加平台
		addPlatInfo : function (params, callback) {
			comm.syncRequest({domain:'gpf_res',url:'addPlatInfo'}, params, callback, 'plat');
		},
		//修改平台信息
		updatePlatInfo : function (params, callback) {
			comm.syncRequest({domain:'gpf_res',url:'updatePlatInfo'}, params, callback, 'plat');
		},
		//同步平台信息到用户中心
		syncPlatToUc : function (params, callback) {
			comm.syncRequest({domain:'gpf_res',url:'syncPlatToUc'}, params, callback, 'plat');
		}
	};
});