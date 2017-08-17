define(function () {
    return {
        saveRole: function (callback, params) {  	//回调参数
            comm.syncRequest('saveRole', params, callback, 'systemManage');
        },
        editRole: function (callback, params) {  	//回调参数
            comm.syncRequest('editRole', params, callback, 'systemManage');
        },
        delRole: function (callback, params) {  	//回调参数
            comm.syncRequest('delRole', params, callback, 'systemManage');
        },
        dealRoleMenuList: function (callback, params) {  	//回调参数
            comm.syncRequest('dealRoleMenuList', params, callback, 'systemManage');
        },
        queryMenuTree: function (callback, params) {  	//回调参数
            comm.syncRequest('queryMenuTree', params, callback, 'systemManage');
        }
    };
});
