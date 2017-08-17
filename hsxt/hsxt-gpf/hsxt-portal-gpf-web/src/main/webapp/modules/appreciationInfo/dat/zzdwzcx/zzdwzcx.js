define(function () {
    return {
        queryIncrementByResNo: function (callback, params) {  	//回调参数
            comm.syncRequest({domain: 'gpf_bm', url: 'queryIncrementByResNo'}, params, callback, 'appreciationInfo');
        },
        addIncrementNode: function (callback, params) {  	//回调参数
            comm.syncRequest({domain: 'gpf_bm', url: 'addIncrementNode'}, params, callback, 'appreciationInfo');
        },
        queryParentListByResNo: function (callback, params) {  	//回调参数
            comm.syncRequest({domain: 'gpf_bm', url: 'queryParentListByResNo'}, params, callback, 'appreciationInfo');
        },
        queryLongNodeByResNo: function (callback, params) {  	//回调参数
            comm.syncRequest({domain: 'gpf_bm', url: 'queryLongNodeByResNo'}, params, callback, 'appreciationInfo');
        }
    };
});
