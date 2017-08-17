define(function () {
    return {
        saveOperator: function (callback, params) {  	//回调参数
            comm.syncRequest('saveOperator', params, callback, 'systemManage');
        },
        editOperator: function (callback, params) {  	//回调参数
            comm.syncRequest('editOperator', params, callback, 'systemManage');
        },
        delOperator: function (callback, params) {  	//回调参数
            comm.syncRequest('delOperator', params, callback, 'systemManage');
        },
        dealOperRoleList: function (callback, params) {  	//回调参数
            comm.syncRequest('dealOperRoleList', params, callback, 'systemManage');
        },
        queryAllGroupList: function (callback, params) {  	//回调参数
            comm.syncRequest('queryAllGroupList', params, callback, 'systemManage');
        }
    };
});
