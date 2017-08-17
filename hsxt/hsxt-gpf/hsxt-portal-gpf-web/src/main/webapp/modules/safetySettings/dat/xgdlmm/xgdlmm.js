define(function () {
    return {
        editLoginPassword: function (callback, params) {  	//回调参数
            comm.syncRequest('editLoginPassword', params, callback, 'safetySettings');
        }
    };
});
