define(function () {
    return {
        /**
         * 托管企业-复核查询
         * @param params 参数对象
         * @param detail 自定义函数
         */
        findDeclareReviewCodeList: function (params, detail, orderOpt) {
            return comm.getCommBsGrid(null, "findDeclareReviewCodeList", params, comm.lang("coDeclaration"), detail, orderOpt);
        }
    };
});