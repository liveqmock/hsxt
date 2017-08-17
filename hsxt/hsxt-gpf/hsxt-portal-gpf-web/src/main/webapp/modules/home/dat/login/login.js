define(function () {
    return {
        signIn: function (callback, params,failback) {  	//回调参数
            comm.syncRequest('signIn', params||{}, callback, 'home',failback);
        },
        checkToken: function (callback, params) {  	//回调参数
            //comm.syncRequest('checkToken', params||{}, callback, 'home');
            comm.Request('checkToken', {	//接口别
                dataType: 'json',
                data: params || {},
                success: function (response) {
                    callback(response);
                },
                error: function () {
                    comm.alert('请求数据出错！');
                }
            });
        },
        signOut: function (callback, params) {  	//回调参数
            comm.syncRequest('signOut', params||{}, callback, 'home');
        }
    };
});
