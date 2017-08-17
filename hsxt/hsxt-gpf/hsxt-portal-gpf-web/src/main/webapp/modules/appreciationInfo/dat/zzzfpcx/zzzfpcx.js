define(function () {
    return {
        queryBmlmListPage: function (callback, params) {  	//回调参数
            comm.syncRequest({domain: 'gpf_bm', url: 'queryBmlmListPage'}, params, callback, 'appreciationInfo');
        }
    };
});
