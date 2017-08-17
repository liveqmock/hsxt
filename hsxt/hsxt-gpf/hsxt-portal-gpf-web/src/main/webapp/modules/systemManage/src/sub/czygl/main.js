define(['text!systemManageTpl/sub/czygl/czygl.html',
    'text!systemManageTpl/sub/czygl/czygl_opt_jssz.html',
    'text!systemManageTpl/sub/czygl/czygl_opt.html',
    'text!systemManageTpl/sub/czygl/czygl_opt_xg.html',
    'text!systemManageTpl/sub/czygl/czygl_opt_tjyhz.html',
    'systemManageDat/czygl/czygl',
    'systemManageLan'
], function (czyglTpl, czygl_opt_jsszTpl, czygl_optTpl, czygl_opt_xgTpl, czygl_opt_tjyhzTpl, operAjax) {
    var operatorGrid = null;
    var operator = {
        showPage: function (tabid) {
            $('#main-content > div[data-contentid="' + tabid + '"]').html(czyglTpl);
            operator.opt_add();
            operatorGrid = operator.loadGrid();
            operator.opt_query();

        },
        loadGrid: function () {
            return comm.getCommBsGrid('czygl_ql', 'queryOperatorListForPage', null, operator.opt_edit, operator.opt_set, operator.opt_delete);
        },
        opt_query: function () {
            $('#czygl_tb_cx').click(function () {
                var params = {
                    loginUser: $('#czygl_tb_dlyhm').val(),
                    name: $('#czygl_tb_xm').val()
                };
                operatorGrid.search(params);
            });
        },
        opt_add: function () {
            //新增操作员
            $('#czygl_tb_xzczy').click(function () {
                $("#czygl_dlg").html(czygl_optTpl);

                var finalGroupIds = [], finalGroupNames = [];
                /*弹出框*/
                $("#czygl_dlg").dialog({
                    title: "新增操作员",
                    width: "1050",
                    modal: true,
                    closeIcon: true,
                    buttons: {
                        "保存": function () {
                            if (operator.validate(true)) {
                                var params = $('#czygl_optFrom').serializeJson();
                                var loginInfo = comm.getLoginInfo();
                                params.createdBy = loginInfo.loginUser;
                                params.groups = finalGroupIds.toString();
                                operAjax.saveOperator(function (result) {
                                    if (result) {
                                        comm.alert({
                                            imgClass: 'tips_yes',
                                            content: '操作成功'
                                        });
                                        $('#czygl_dlg').dialog("destroy");
                                        operatorGrid.refreshPage();
                                    }
                                }, params);
                            }
                        },
                        "取消": function () {
                            $(this).dialog("destroy");
                        }
                    }
                });
                /*end*/
                /*表单数据初始化*/
                $('#czygl_optFrom input').val('');
                $('#czygl_optFrom textarea').val('');

                /*end*/
                /*选择用户组，多选操作*/
                $('#czygl_opt_xzyhz').click(function () {
                    operAjax.queryAllGroupList(function (groups) {
                        $('#czygl_opt_dlg').empty().html(_.template(czygl_opt_tjyhzTpl, {groupList: groups, groupNames: finalGroupNames}));

                        /*弹出框*/
                        $("#czygl_opt_dlg").dialog({
                            title: "选择用户组",
                            width: "300",
                            modal: true,
                            closeIcon: true,
                            buttons: {
                                "确定": function () {
                                    var groupIdArr = $('#czygl_opt_tjyhzForm').serializeJson(), validGroupIds = [], validGroupNames = [];
                                    $.each(groups, function (ind, g) {
                                        if (groupIdArr[g.groupId]) {
                                            validGroupIds.push(g.groupId);
                                            validGroupNames.push(groupIdArr[g.groupId]);
                                        }
                                    });
                                    finalGroupIds = validGroupIds;
                                    finalGroupNames = validGroupNames;
                                    $('#czygl_optFrom #groupNames').val(validGroupNames.toString());
                                    $(this).dialog("destroy");
                                },
                                "取消": function () {
                                    $(this).dialog("destroy");
                                }
                            }
                        });
                    });
                });
            });
        },
        opt_set: function (record, rowIndex, colIndex, options) {
            return $('<a>角色设置</a>').click(function (e) {

                var rowData = operatorGrid.getRecord(rowIndex);

                $('#czygl_dlg').html(czygl_opt_jsszTpl);

                var orgIds = [];//原有角色ID

                var role_grid = $.fn.bsgrid.init('czygl_opt_jssz_ql', {
                    url: comm.UrlList['queryRoleListForPage'],
                    // autoLoad: false,
                    pageSizeSelect: true,
                    pageSize: 10,
                    stripeRows: true,  //行色彩分隔
                    displayBlankRows: false,   //显示空白行
                    additionalAfterRenderGrid: function (parseSuccess, gridData, options) {
                        if (parseSuccess) {
                            role_grid.getRows(options).each(function (index, row) {
                                var data = role_grid.getRecord(index);
                                if (_.contains(rowData.roles, data.roleName)) {
                                    $(row).find('td>input[type="checkbox"]').each(function () {
                                        $(this).prop('checked', true);
                                    });
                                    orgIds.push(data.roleId);
                                }
                            });
                        }
                    }
                });

                /*弹出框*/
                $("#czygl_dlg").dialog({
                    title: "角色设置",
                    width: "810",
                    modal: true,
                    closeIcon: true,
                    buttons: {
                        "下次再说": function () {
                            $(this).dialog("destroy");
                        },
                        "立即设置": function () {
                            var rows = role_grid.getCheckedRowsRecords();
                            var addRoleIds = [], delRoleIds = [], existRoleIds = [];
                            $.each(rows, function (index, row) {
                                if (_.contains(orgIds, row.roleId)) {
                                    existRoleIds.push(row.roleId);
                                } else {
                                    addRoleIds.push(row.roleId);
                                }
                            });
                            $.each(orgIds, function (ind, rId) {
                                if (!_.contains(existRoleIds, rId)) {
                                    delRoleIds.push(rId);
                                }
                            });
                            var params = {
                                operatorId: rowData.operatorId,
                                addRoleIds: addRoleIds.toString(),
                                delRoleIds: delRoleIds.toString()
                            };

                            operAjax.dealOperRoleList(function (result) {
                                if (result) {
                                    $('#czygl_dlg').dialog("destroy");
                                    operatorGrid.refreshPage();
                                }
                            }, params);
                        }
                    }
                });

            }.bind(this));
        }.bind(this),

        opt_edit: function (record, rowIndex, colIndex, options) {
            return $('<a>修改</a>').click(function (e) {
                var rowData = operatorGrid.getRecord(rowIndex);
                $('#czygl_dlg').empty().html(_.template(czygl_opt_xgTpl, rowData));
                //定义用户组ID数组
                var orgGroupIds = [], addGroupIds = [], delGroupIds = [], existGroupIds = [], validGroupIds = [], validGroupNames = [];
                /*弹出框*/
                $("#czygl_dlg").dialog({
                    title: "修改操作员",
                    width: "1050",
                    modal: true,
                    closeIcon: true,
                    buttons: {
                        "确认": function () {
                            if (operator.validate(false)) {
                                var params = $('#czygl_opt_xgFrom').serializeJson();
                                var loginInfo = comm.getLoginInfo();
                                params.updatedBy = loginInfo.loginUser;
                                params.addGroupIds = addGroupIds.toString();
                                params.delGroupIds = delGroupIds.toString();
                                operAjax.editOperator(function (result) {
                                    if (result) {
                                        comm.alert({
                                            imgClass: 'tips_yes',
                                            content: '操作成功'
                                        });
                                        $('#czygl_dlg').dialog("destroy");
                                        operatorGrid.refreshPage();
                                    }
                                }, params);
                            }
                        },
                        "取消": function () {
                            $(this).dialog("destroy");
                        }
                    }
                });

                /*选择用户组，多选操作*/

                $('#czygl_opt_xg_xzyhz').click(function () {
                    //查询所有用户组
                    operAjax.queryAllGroupList(function (groups) {
                        if (groups && groups.length > 0) {
                            //如果原始用户组为空，且操作员所属用户组存在
                            if (orgGroupIds.length == 0 && rowData.groups) {
                                $.each(groups, function (index, group) {
                                    if (_.contains(rowData.groups, group.groupName)) {
                                        orgGroupIds.push(group.groupId);
                                    }
                                });
                                validGroupNames = rowData.groups;
                            }
                            $('#czygl_opt_xg_dlg').empty().html(_.template(czygl_opt_tjyhzTpl, {groupList: groups, groupNames: validGroupNames}));

                            /*弹出框*/
                            $("#czygl_opt_xg_dlg").dialog({
                                title: "选择用户组",
                                width: "300",
                                modal: true,
                                closeIcon: true,
                                buttons: {
                                    "确定": function () {
                                        var groupIdArr = $('#czygl_opt_tjyhzForm').serializeJson(), groupNames = [];
                                        $.each(groups, function (ind, g) {
                                            if (groupIdArr[g.groupId]) {
                                                groupNames.push(groupIdArr[g.groupId]);
                                                validGroupIds.push(g.groupId);
                                                if (_.contains(orgGroupIds, g.groupId)) {
                                                    existGroupIds.push(g.groupId);
                                                } else {
                                                    addGroupIds.push(g.groupId);
                                                }
                                            }
                                        });
                                        $.each(orgGroupIds, function (i, gid) {
                                            if (!_.contains(existGroupIds, gid)) {
                                                delGroupIds.push(gid);
                                            }
                                        });

                                        $('#czygl_opt_xg_dlg').dialog("destroy");
                                        $('#czygl_opt_xgFrom #groupNames').val(groupNames.toString());
                                        orgGroupIds = validGroupIds;
                                        validGroupNames = groupNames;
                                    },
                                    "取消": function () {
                                        $(this).dialog("destroy");
                                    }
                                }
                            });
                        }
                    });
                });

                /*end*/
            }.bind(this));
        }.bind(this),
        opt_delete: function (record, rowIndex, colIndex, options) {
            return $('<a class="ml5">删除</a>').click(function (e) {
                var rowData = operatorGrid.getRecord(rowIndex);
                comm.confirm({
                    width: 500,
                    imgFlag: true,             //显示图片
                    imgClass: 'tips_ques',         //图片类名，默认tips_ques
                    content: '确认删除 用户名：<span class="red">' + rowData.loginUser + '</span>， 姓名：<span class="red">' + rowData.name + '</span> 操作员信息？',
                    callOk: function () {
                        operAjax.delOperator(function (result) {
                            if (result) {
                                comm.alert({
                                    imgClass: 'tips_yes',
                                    content: '操作成功'
                                });
                                operatorGrid.refreshPage();
                            }
                        }, {operatorId: rowData.operatorId});
                        comm.alert({
                            imgClass: 'tips_yes',
                            content: '删除成功！'
                        });
                    }
                });
            }.bind(this));
        }.bind(this),
        validate: function (isAdd) {
            var options = {
                formID: '#czygl_opt_xgFrom',
                top: -5,
                left: 260,
                rules: {
                    loginUser: {
                        required: true
                    },
                    name: {
                        required: true
                    },
                    loginPwd: {
                        passwordLogin:true,
                        passwordRule:true
                    },
                    loginPwd2: {
                        equalTo: '#loginPwd'
                    }
                },
                messages: {
                    loginUser: {
                        required: '登录用户名必填'
                    },
                    name: {
                        required: '姓名必填'
                    },
                    loginPwd2: {
                        equalTo: '两次输入的密码不一致'
                    }
                }
            };
            if (isAdd) {
                options.formID = '#czygl_optFrom';
                options.rules.loginPwd.required = true;
                options.messages = $.extend({
                    loginPwd: {
                        required: '登录密码必填'
                    }
                }, options.messages);
            }
            return comm.valid(options);
        }
    };

    return operator;
});
