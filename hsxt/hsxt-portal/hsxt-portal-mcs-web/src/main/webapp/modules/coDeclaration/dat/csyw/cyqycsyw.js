define(function () {
    return {
        /**
         * 成员企业-初审查询
         * @param params 参数对象
         * @param detail 自定义函数
         */
        findDeclareTrialList: function (params, detail, orderOpt) {
            return comm.getCommBsGrid(null, "findDeclareTrialList", params, comm.lang("coDeclaration"), detail, orderOpt);
        }
    };
});