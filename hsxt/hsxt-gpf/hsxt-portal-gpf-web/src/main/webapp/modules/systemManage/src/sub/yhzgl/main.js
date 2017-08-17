define(['text!systemManageTpl/sub/yhzgl/yhzgl.html',
    'text!systemManageTpl/sub/yhzgl/yhzgl_opt.html',
    'text!systemManageTpl/sub/yhzgl/yhzgl_zywh.html',
    'text!systemManageTpl/sub/yhzgl/yhzgl_zywh_tj.html',
    'systemManageDat/yhzgl/yhzgl',
    'systemManageLan'
], function (yhzglTpl, yhzgl_optTpl, yhzgl_zywhTpl, yhzgl_zywh_tjTpl, groupAjax) {
    var groupGrid = null;
    var group = {
        showPage: function (tabid) {
            $('#main-content > div[data-contentid="' + tabid + '"]').html(_.template(yhzglTpl));
            group.opt_add();
            group.loadGrid();
        },
        loadGrid: function () {
            var grid_edit = function (record, rowIndex, colIndex, options) {
                return $('<a>修改</a>').click(function (e) {
                    group.opt_edit(groupGrid.getRecord(rowIndex));
                }.bind(this));
            }.bind(this);
            var grid_zywh = function (record, rowIndex, colIndex, options) {
                return $('<a>组员维护</a>').click(function (e) {
                    group.opt_zywh(groupGrid.getRecord(rowIndex));
                }.bind(this));
            }.bind(this);
            var grid_del = function (record, rowIndex, colIndex, options) {
                return $('<a>删除</a>').click(function (e) {
                    group.opt_del(groupGrid.getRecord(rowIndex));
                }.bind(this));
            }.bind(this);
            groupGrid = comm.getCommBsGrid('yhzgl_ql', 'queryGroupListForPage', null, grid_edit, grid_del, grid_zywh);
        },
        opt_add: function () {
            $('#yhzgl_tb_xzyhzmc').click(function () {
                $('#yhzgl_dlg').empty().html(yhzgl_optTpl);

                /*弹出框*/
                $("#yhzgl_dlg").dialog({
                    title: "新增用户组",
                    width: "500",
                    modal: true,
                    closeIcon: true,
                    buttons: {
                        "确定": function () {
                            if (group.opt_validate()) {
                                var params = $('#yhzgl_optForm').serializeJson();
                                var loginInfo = comm.getLoginInfo();
                                if (loginInfo) {
                                    params.createdBy = loginInfo.loginUser;
                                }
                                groupAjax.saveGroup(function (result) {
                                    if (result) {
                                        comm.confirm({
                                            width: 500,
                                            imgFlag: true,
                                            imgClass: 'tips_yes',
                                            content: '用户组名称创建成功!',
                                            ok: '完成',
                                            del: '继续添加用户组',
                                            cancel: '添加用户组成员',
                                            callOk: function () {
                                                groupGrid.refreshPage();
                                                $('#yhzgl_dlg').dialog("destroy");
                                            },
                                            callOther: function () {
                                                group.opt_add();
                                            },
                                            callCancel: function () {
                                                $('#yhzgl_dlg').dialog("destroy");
                                                group.opt_zywh(result);
                                            }
                                        });
                                    }
                                }, params);
                            }
                        },
                        "取消": function () {
                            $(this).dialog("destroy");
                        }
                    }
                });

                /*表单初始化*/
                $('#yhzgl_optForm input,#yhzgl_optForm textarea').val('');
            });
        },
        opt_edit: function (rowData) {
            $('#yhzgl_dlg').empty().html(yhzgl_optTpl);

            /*弹出框*/
            $("#yhzgl_dlg").dialog({
                title: "修改用户组",
                width: "500",
                modal: true,
                closeIcon: true,
                buttons: {
                    "确定": function () {
                        if (group.opt_validate()) {
                            var params = $('#yhzgl_optForm').serializeJson();
                            params.groupId = rowData.groupId;
                            var loginInfo = comm.getLoginInfo();
                            if (loginInfo) {
                                params.updatedBy = loginInfo.loginUser;
                            }
                            groupAjax.editGroup(function (result) {
                                if (result) {
                                    comm.confirm({
                                        width: 500,
                                        imgFlag: true,
                                        imgClass: 'tips_yes',
                                        content: '用户组修改成功!',
                                        ok: '完成',
                                        cancel: '添加用户组成员',
                                        callOk: function () {
                                            groupGrid.refreshPage();
                                            $('#yhzgl_dlg').dialog("destroy");
                                        },
                                        callCancel: function () {
                                            $('#yhzgl_dlg').dialog("destroy");
                                            group.opt_zywh(rowData);
                                        }
                                    });
                                }
                            }, params);
                        }
                    },
                    "取消": function () {
                        $(this).dialog("destroy");
                    }
                }
            });
            $('#yhzgl_optForm #groupName').val(rowData.groupName);
            $('#yhzgl_optForm #description').val(rowData.description);
        },
        //删除用户组
        opt_del: function (rowData) {
            comm.confirm({
                width: 400,
                imgFlag: true,
                imgClass: 'tips_ques',
                content: '确认删除' + rowData.groupName,
                callOk: function () {
                    groupAjax.delGroup(function (result) {
                        if (result) {
                            groupGrid.refreshPage();
                        }
                    }, {groupId: rowData.groupId});
                }
            });
        },
        //组员维护
        opt_zywh: function (rowData) {
            $('#yhzgl_dlg').empty().html(yhzgl_zywhTpl);
            /*弹出框*/
            $("#yhzgl_dlg").dialog({
                title: "组员维护",
                width: "810",
                closeIcon: true,
                buttons: {
                    "关闭": function () {
                        groupGrid.refreshPage();
                        $(this).dialog("destroy");
                    }
                }
            });
            //加载用户组中的操作员列表
            var userLinkGrid = comm.getCommBsGrid('yhzgl_zywh_ql', 'queryOperatorList', {groupId: rowData.groupId, related: true}, function (record, rowIndex, colIndex, options) {
                return $('<a style="color:#f60;">移除</a>').click(function (e) {
                    var user = userLinkGrid.getRecord(rowIndex);
                    comm.confirm({
                        width: 500,
                        imgFlag: true,
                        imgClass: 'tips_ques',
                        content: '您确认移除用户名：<span class="red">' + user.loginUser + '</span>？',
                        callOk: function () {
                            groupAjax.delOperatorId(function (result) {
                                if (result) {
                                    userLinkGrid.refreshPage();
                                }
                            }, {groupId: rowData.groupId, operatorId: user.operatorId});
                        }
                    });
                }.bind(this));
            }.bind(this));
            //添加操作员
            $('#yhzgl_zywh_tj').click(function () {

                $('#yhzgl_zywh_dlg').empty().html(yhzgl_zywh_tjTpl);

                var userUnlinkGrid = comm.getCommBsGrid('yhzgl_zywh_tj_ql', 'queryOperatorList', {groupId: rowData.groupId, related: false});
                /*弹出框*/
                $("#yhzgl_zywh_dlg").dialog({
                    title: "添加组员",
                    width: "810",
                    closeIcon: true,
                    buttons: {
                        "确定": function () {
                            var rows = userUnlinkGrid.getCheckedRowsRecords();
                            if (rows && rows.length > 0) {
                                var operIds = [];
                                $.each(rows, function (index, oper) {
                                    operIds.push(oper.operatorId);
                                });
                                groupAjax.saveOperatorIds(function (result) {
                                    if (result) {
                                        userLinkGrid.refreshPage();
                                        $('#yhzgl_zywh_dlg').dialog("destroy");
                                    }
                                }, {groupId: rowData.groupId, operators: operIds.toString()});

                            } else {
                                comm.alert({
                                    imgClass: 'tips_warn',
                                    content: '请选择组员!'
                                });
                            }
                        },
                        "取消": function () {
                            $(this).dialog("destroy");
                        }
                    }
                });
            });
        },
        opt_validate: function () {
            return comm.valid({
                formID: '#yhzgl_optForm',
                left: 200,
                rules: {
                    groupName: {
                        required: true
                    }
                },
                messages: {
                    groupName: {
                        required: '用户组名称必填'
                    }
                }
            });
        }

    };
    return group;
})
;