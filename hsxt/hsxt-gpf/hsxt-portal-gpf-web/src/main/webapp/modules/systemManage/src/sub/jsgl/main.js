define(['text!systemManageTpl/sub/jsgl/jsgl.html',
    'text!systemManageTpl/sub/jsgl/jsgl_opt.html',
    'systemManageDat/jsgl/jsgl',
    'jqueryztree',
    'systemManageLan'
], function (jsglTpl, jsgl_optTpl, roleAjax) {
    var roleGrid = null;
    var role = {
        showPage: function (tabid) {
            $('#main-content > div[data-contentid="' + tabid + '"]').html(_.template(jsglTpl));
            role.loadGrid();
            //初始化添加角色的点击事件
            $('#jsgl_tb_tjjs').click(function () {
                role.opt_add();
            });
            role.opt_query();
        },
        loadGrid: function () {
            var grid_edit = function (record, rowIndex, colIndex, options) {
                var rowData = roleGrid.getRecord(rowIndex);
                if (rowData.roleType == 1) return;
                return $('<a>修改</a>').click(function (e) {
                    this.opt_edit(rowData);
                }.bind(this));
            }.bind(this);

            var grid_del = function (record, rowIndex, colIndex, options) {
                var rowData = roleGrid.getRecord(rowIndex);
                if (rowData.roleType == 1) return;
                return $('<a>删除</a>').click(function (e) {
                    this.opt_del(rowData);
                }.bind(this));
            }.bind(this);

            var grid_set = function (record, rowIndex, colIndex, options) {
                var rowData = roleGrid.getRecord(rowIndex);
                if (rowData.roleType == 1) return;
                return $('<a>菜单授权</a>').click(function (e) {
                    role.menuAuth(rowData);
                }.bind(this));
            }.bind(this);

            roleGrid = comm.getCommBsGrid('jsgl_ql', 'queryRoleListForPage', null, grid_edit, grid_del, grid_set);
        },
        opt_add: function () {
            //加载页面
            $('#jsgl_dlg').empty().html(jsgl_optTpl);
            //初始化页面
            $('#jsgl_optForm input, #jsgl_optForm textarea').val('');
            /*弹出框*/
            $("#jsgl_dlg").dialog({
                width: "500",
                title: "添加角色",
                modal: true,
                closeIcon: true,
                buttons: {
                    "确定": function () {
                        if (role.opt_validate()) {
                            var params = $('#jsgl_optForm').serializeJson();
                            var loginInfo = comm.getLoginInfo();
                            if (loginInfo) {
                                params.createdBy = loginInfo.loginUser;
                            }
                            roleAjax.saveRole(function (result) {
                                if (result) {
                                    $('#jsgl_dlg').dialog("destroy");
                                    roleGrid.refreshPage();
                                    comm.confirm({
                                        imgFlag: true,
                                        imgClass: 'tips_ques',
                                        content: '角色添加成功，是否进行菜单授权？',
                                        callOk: function () {
                                            role.menuAuth(result);
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
        },
        opt_query: function () {
            $('#jsgl_tb_cx').click(function () {
                var params = {
                    roleName: $('#jsgl_tb_jsmc').val()
                };
                roleGrid.search(params);
            });
        },
        opt_del: function (rowData) {
            comm.confirm({
                width: 500,
                imgFlag: true,
                imgClass: 'tips_ques',
                content: '确认删除 <span class="red">' + rowData.roleName + '</span> 角色？',
                callOk: function () {
                    roleAjax.delRole(function (result) {
                        if (result) {
                            roleGrid.refreshPage();
                        }
                    }, {roleId: rowData.roleId});
                }
            });
        },
        opt_edit: function (rowData) {
            $('#jsgl_dlg').empty().html(jsgl_optTpl);
            /*弹出框*/
            $("#jsgl_dlg").dialog({
                width: "500",
                title: "修改角色",
                modal: true,
                closeIcon: true,
                buttons: {
                    "确定": function () {
                        if (role.opt_validate()) {
                            var params = $('#jsgl_optForm').serializeJson();
                            var loginInfo = comm.getLoginInfo();
                            if (loginInfo) {
                                params.updatedBy = loginInfo.loginUser;
                                params.roleId = rowData.roleId;
                            }
                            roleAjax.editRole(function (result) {
                                if (result) {
                                    $('#jsgl_dlg').dialog("destroy");
                                    roleGrid.refreshPage();
                                    comm.confirm({
                                        imgFlag: true,
                                        imgClass: 'tips_ques',
                                        content: '角色修改成功，是否进行菜单授权？',
                                        callOk: function () {
                                            role.menuAuth(rowData);
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
            $('#jsgl_optForm #roleName').val(rowData.roleName);
            $('#jsgl_optForm #description').val(rowData.description);
        },

        //菜单授权
        //flag：调用标识，从新增修改的提交按钮点击调用
        menuAuth: function (rowData) {

            $('#jsgl_dlg').html('<ul id="jsgl_cdsq" class="ztree"></ul>');
            //树型 角色 展示
            var setting = {
                check: {
                    enable: true,
                    nocheckInherit:true,
                    chkDisabledInherit:true
                },
                data: {
                    simpleData: {
                        enable: true,
                        idKey: "menuNo",
                        pIdKey: "parentNo",
                        rootPId: ""
                    }
                }
            };

            roleAjax.queryMenuTree(function(trees) {
                var menuTree = $.fn.zTree.init($("#jsgl_cdsq"), setting, trees);

                $("#jsgl_dlg").dialog({
                    width: 680,
                    title: '菜单授权',
                    closeIcon: true,
                    buttons: {
                        "下次再说": function () {
                            $(this).dialog('destroy');

                        },
                        "立即设置": function () {
                            var nodes = menuTree.getChangeCheckedNodes();
                            var addNos = [], delNos = [];

                            $.each(nodes, function (index, node) {
                                if (node.checked) {
                                    addNos.push(node.menuNo);
                                } else {
                                    delNos.push(node.menuNo);
                                }
                            });
                            var params = {
                                roleId: rowData.roleId,
                                addMenuNos: addNos.toString(),
                                delMenuNos: delNos.toString()
                            };

                            roleAjax.dealRoleMenuList(function (result) {
                                if(result) {
                                    $('#jsgl_dlg').dialog('destroy');
                                }
                            },params);
                        }
                    }
                });
            },{roleId:rowData.roleId});
        },
        opt_validate: function () {
            return comm.valid({
                formID: '#jsgl_optForm',
                left: 200,
                rules: {
                    roleName: {
                        required: true
                    }
                },
                messages: {
                    roleName: {
                        required: '角色名称必填'
                    }
                }
            });
        }


    };
    return role;
}); 
