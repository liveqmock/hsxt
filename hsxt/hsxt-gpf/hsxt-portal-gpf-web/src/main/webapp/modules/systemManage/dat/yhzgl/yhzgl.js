define(function () {
    return {
        saveGroup: function (callback, params) {  	//回调参数
            comm.syncRequest('saveGroup', params, callback, 'systemManage');
        },
        editGroup: function (callback, params) {  	//回调参数
            comm.syncRequest('editGroup', params, callback, 'systemManage');
        },
        delGroup: function (callback, params) {  	//回调参数
            comm.syncRequest('delGroup', params, callback, 'systemManage');
        },
        delOperatorId: function (callback, params) {  	//回调参数
            comm.syncRequest('delOperatorId', params, callback, 'systemManage');
        },
        saveOperatorIds: function (callback, params) {  	//回调参数
            comm.syncRequest('saveOperatorIds', params, callback, 'systemManage');
        }
    };
});
