define(['text!zypeManageTpl/yjqypesq/yjqypesqcx.html',
    'text!zypeManageTpl/yjqypesq/yjqypesqcx_ck_dialog.html',
    "zypeManageDat/zypeManage", "zypeManageLan"
], function (yjqypesqcxTpl, yjqypesqcx_ck_dialogTpl, zypeManage) {
    var aps_yjqypesqcx = {
        showPage: function () {
            $('#busibox').html(_.template(yjqypesqcxTpl));

            //加载分页
            aps_yjqypesqcx.pageQuery();

            //查询事件
            $("#btnQuery").click(function () {
                aps_yjqypesqcx.pageQuery();
            });

        },
        //分页查询
        pageQuery: function () {
            //经办人操作号
            var exeCustId = comm.getCookie("custId");

            //判断操作员是否为管理员
            if (comm.getCookie("isAdmin") == "true") {
                exeCustId = "";
            }

            //查询条件
            var queryParam = {
                "search_mEntResNo": $("#txtmEntResNo").val(), "search_mEntResName": $("#txtmEntResName").val(),
                "search_exeCustId": exeCustId
            };
            zypeManage.applyPlatQuotaQueryPage("searchTable", queryParam, function (record, rowIndex, colIndex, options) {
                    if (colIndex == 5) {
                        return comm.lang("zypeManage").quotaStatusEnume[record.status];
                    }
                    return $("<a>" + comm.lang("zypeManage").quota_query_title + "</a>").click(function (e) {
                        aps_yjqypesqcx.chaKan(record);
                    });
                }
            );
        },
        chaKan: function (obj) {
            //获取申请内容
            zypeManage.applyPlatQuotaDetail({"applyId": obj.applyId}, function (rsp) {
                $('#dialogBox').html(_.template(yjqypesqcx_ck_dialogTpl, rsp.data));
            });

            /*弹出框*/
            $("#dialogBox").dialog({
                title: comm.lang("zypeManage").quota_detail,
                width: "800",
                modal: true,
                buttons: {
                    "确定": function () {
                        $(this).dialog("destroy");
                    }
                }
            });
        }
    };
    return aps_yjqypesqcx;
});