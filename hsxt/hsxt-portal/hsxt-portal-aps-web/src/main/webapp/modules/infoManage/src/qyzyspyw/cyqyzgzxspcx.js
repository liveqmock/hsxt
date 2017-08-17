define(['text!infoManageTpl/qyzyspyw/cyqyzgzxspcx.html',
    'text!infoManageTpl/qyzyspyw/cyqyzgzxspcx_ck_dialog.html',
    'infoManageDat/infoManage'
], function (cyqyzgzxspcxTpl, cyqyzgzxspcx_ck_dialogTpl, infoManage) {
    var self = {
        showPage: function () {

            $('#busibox').html(_.template(cyqyzgzxspcxTpl));

            /*下拉列表*/
            comm.initSelect("#status", comm.lang("infoManage").appStatusEnum);

            $("#searchBtn").click(function () {
                var params = {
                    search_entResNo: $("#entResNo").val(),			//企业互生号
                    search_entName: $("#entName").val(),			//企业名称
                    search_linkman: $("#linkman").val(), 			//联系人
                    search_status: $("#status").attr("optionvalue")//状态
                };
                comm.getCommBsGrid("searchTable", "membercompquit_list", params, comm.lang("infoManage"), self.detail);

            });
        },
        detail: function (record, rowIndex, colIndex, options) {
            if (colIndex == 5) {
                return comm.lang("infoManage").appStatusEnum[record.status];
            }
            if (colIndex == 7) {
                return $('<a>' + comm.lang("infoManage").query + '</a>').click(function (e) {
                    self.chaKan(record);

                });
            }
        },
        chaKan: function (obj) {
            infoManage.seachMemberQuitDetail({applyId: obj.applyId}, function (res) {
                var quitData = res.data.MEMBER_QUIT;
                //企业实地考察报告
                if (quitData.reportFile != null) {
                    quitData['reportFilePath'] = infoManage.getFsServerUrl(quitData.reportFile);
                }
                //企业双方声明文件
                if (quitData.statementFile != null) {
                    quitData['statementFilePath'] = infoManage.getFsServerUrl(quitData.statementFile);
                }
                //其他文件
                if (quitData.otherFile != null) {
                    quitData['otherFilePath'] = infoManage.getFsServerUrl(quitData.otherFile);
                }
                //业务办理书
                if (quitData.bizApplyFile != null) {
                    quitData['bizApplyFilePath'] = infoManage.getFsServerUrl(quitData.bizApplyFile);
                }

                $("#dialogBox > div").html(_.template(cyqyzgzxspcx_ck_dialogTpl, res.data));

                /*弹出框*/
                $("#dialogBox").dialog({
                    title: comm.lang("infoManage").queryTitile,//"成员企业资格注销审批查询详情",
                    width: "600",
                    modal: true,
                    closeIcon: true,
                    buttons: {
                        "确定": function () {
                            $(this).dialog("destroy");
                        }
                    }
                });
            });
        }
    };
    return self;
});