define(function () {
    return {
        queryMenuList: function (callback, params) {  	//回调参数
            comm.syncRequest('queryMenuList', params||{}, callback, 'home');
        }
    };
});
