define(function () {
    return {
        /**
         * 服务公司-复核查询
         * @param params 参数对象
         * @param detail 自定义函数
         */
        findDeclareReviewCodeList: function (params, detail, orderOpt) {
            return comm.getCommBsGrid(null, "findDeclareReviewCodeList", params, comm.lang("coDeclaration"), detail, orderOpt);
        }
    };
});