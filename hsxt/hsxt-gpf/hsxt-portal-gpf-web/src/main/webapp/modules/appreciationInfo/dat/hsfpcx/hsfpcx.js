define(function () {
    return {
        queryPointValueListPage: function (callback, params) {  	//回调参数
            comm.syncRequest({domain: 'gpf_bm', url: 'queryPointValueListPage'}, params, callback, 'appreciationInfo');
        }
    };
});
