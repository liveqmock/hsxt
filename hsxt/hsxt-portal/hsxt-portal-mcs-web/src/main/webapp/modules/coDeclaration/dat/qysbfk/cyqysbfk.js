define(function () {
    return {
        /**
         * 成员企业-复核查询
         * @param params 参数对象
         * @param detail 自定义函数
         */
        findDeclareReviewCodeList: function (params, detail, orderOpt) {
            return comm.getCommBsGrid(null, "findDeclareReviewCodeList", params, comm.lang("coDeclaration"), detail, orderOpt);
        }
    };
});