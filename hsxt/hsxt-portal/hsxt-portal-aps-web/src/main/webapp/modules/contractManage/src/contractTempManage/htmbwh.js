define(['text!contractManageTpl/contractTempManage/htmbwh.html',
    'text!contractManageTpl/contractTempManage/xzmb.html',
    'text!contractManageTpl/contractTempManage/htmbwh_xgmb.html',
    'text!contractManageTpl/contractTempManage/htmbwh_ck.html',
    'text!contractManageTpl/contractSeal/seal.html',
    'contractManageDat/contract',
    'contractManageLan'
], function (htmbwhTpl, xzmbTpl, htmbwh_xgmbTpl, htmbwh_ckTpl, htgz_dialogTpl, dataModule) {
    var self = {
        searchTable: null,
        showPage: function () {
            $('#busibox').html(_.template(htmbwhTpl));

            //var self = this;

            /*下拉列表*/

            comm.initSelect('#custType', comm.lang("contractManage").custType, null, null, {name: '全部', value: ''});
            comm.initSelect('#status', comm.lang("contractManage").templetStatusEnum, null, null, {name: '全部', value: ''});

            /*end*/

            /*按钮事件*/
            $('#xzmb_btn').click(function () {
                $('#busibox').html(_.template(xzmbTpl));
                comm.liActive_add($('#xzmb'));
                $('#resSelector').hide();
                /*下拉列表*/
                comm.initSelect('#custType_xzmb', comm.lang("contractManage").custType, 185);
                $("#custType_xzmb").change(function (e) {
                    $('#resSelector').hide();
                    var custType_xzmb_val = $("#custType_xzmb").attr("optionvalue");
                    if (custType_xzmb_val == 2) {
                        //成员企业
                        comm.initSelect('#status_xzmb', comm.lang("contractManage").bSelector, 160, 4);
                        $('#resSelector').show();
                    } else if (custType_xzmb_val == 3) {
                        //托管企业
                        comm.initSelect('#status_xzmb', comm.lang("contractManage").tSelector, 160, 1);
                        $('#resSelector').show();
                    }
                });


                /*end*/

                /*富文本框*/
                $('#template_content_xzmb').xheditor({
                    upLinkUrl: "upload.php",
                    upLinkExt: "zip,rar,txt",
                    upImgUrl: "upload.php",
                    upImgExt: "jpg,jpeg,gif,png",
                    upFlashUrl: "upload.php",
                    upFlashExt: "swf",
                    upMediaUrl: "upload.php",
                    upMediaExt: "wmv,avi,wma,mp3,mid",
                    width: 678,
                    height: 150
                });
                /*end*/
                $('#xzmb_save_btn').click(function () {
                    if (!self.validateAddData()) {
                        return;
                    }
                    var saveParam = {
                        templetName: $('#templetName_xzmb').val().trim(),
                        templetContent: $('#template_content_xzmb').val().trim(),
                        reqOperator: $.cookie('custId'),
                        createdBy: $.cookie('custId')
                    };

                    var custType = $("#custType_xzmb").attr("optionvalue");
                    if (custType) {
                        saveParam.custType = custType;
                        if (custType == 2 || custType == 3) {
                            var resType = $("#status_xzmb").attr("optionvalue");
                            if (resType) {
                                saveParam.resType = resType;
                            }
                        }
                    }


                    dataModule.createContractTemp(saveParam, function (res) {
                        if (res.retCode = 22000) {
                            $("#051100000000_subNav_051104000000").click();
                        }
                    });
                });
                $('#xzmb_back').triggerWith('#htmbwh');

            });
            /*end*/


            /*表格数据模拟*/
            $("#qry_htmb_btn").click(function () {
                var params = {
                    search_templetName: $("#templetName").val().trim()	//企业名称
                };
                var custType = $("#custType").attr("optionvalue");
                if (custType) {
                    params.search_custType = custType;
                }
                var status = $("#status").attr("optionvalue");
                if (status) {
                    params.search_status = status;
                }
                self.searchTable = comm.getCommBsGrid("searchTable", "find_contract_temp_by_page", params, comm.lang("certificateManage"), self.detail, self.edit, self.add);
            });

            /*end*/
        },
        validateAddData: function () {
            return comm.valid({
                formID: '#htmbglForm',
                rules: {
                    templetName_xzmb: {
                        required: true,
                        maxlength: 100
                    }/*,
                     template_content_xzmb : {
                     required : true
                     }*/
                },
                messages: {
                    templetName_xzmb: {
                        required: comm.lang("contractManage")[36409],
                        maxlength: comm.lang("contractManage")[36400]
                    }/*,
                     template_content_xzmb : {
                     required : comm.lang("contractManage")[36410]
                     }*/
                }

            });
        },
        detail: function (record, rowIndex, colIndex, options) {
            if (colIndex == 1) {
                return comm.lang("contractManage").custType[record.custType];
            }
            if (colIndex == 2) {
                return comm.lang("contractManage").resTypeEnum[record.resType];
            }
            if (colIndex == 3) {
                return comm.lang("contractManage").templetStatusEnum[record.apprStatus];
            }
            if (colIndex == 4) {
                if (record.updatedDate) {
                    return record.updatedDate;
                }
                return record.createdDate;

            }
            if (colIndex == 5) {
                record.custTypeName = comm.lang("contractManage").custType[record.custType];
                record.resTypeName = comm.lang("contractManage").resTypeEnum[record.resType];
                var link1 = $('<a>查看</a>').click(function (e) {

                    $('#chaKan_content').html(_.template(htmbwh_ckTpl, record));
                    record.content = record.templetContent;
                    $('#viewContent').click(function () {
                        $('#dialogBox').html(_.template(htgz_dialogTpl, record));

                        /*弹出框*/
                        $("#dialogBox").dialog({
                            title: "查看模板",
                            width: "1000",
                            modal: true,
                            closeIcon: true,
                            buttons: {
                                "关闭": function () {
                                    $(this).dialog("destroy");
                                }
                            }
                        });
                    });
                    $("#chaKan_content").dialog({
                        title: "查看模版",
                        modal: true,
                        closeIcon: true,
                        width: "900",
                        buttons: {
                            "确定": function () {
                                $(this).dialog("destroy");
                            }
                        }
                    });

                });
                return link1;
            }
        },

        edit: function (record, rowIndex, colIndex, options) {
            if (colIndex == 5) {
                var obj = self.searchTable.getRecord(rowIndex);
                var link2 = '';
                if (self.searchTable.getRecordIndexValue(record, 'apprStatus') == '0') {//已启用

                    link2 = $('<a>停用</a>').click(function (e) {
                        var confirmObj = {
                            imgFlag: true,             //显示图片
                            content: '您确认停用此模版？',
                            callOk: function () {
                                var stopParam = {
                                    templetId: obj.templetId,
                                    custId: $.cookie('custId')
                                };
                                dataModule.stopContractTemp(stopParam, function (res) {
                                    if (res.retCode = 22000) {
                                        $("#051100000000_subNav_051104000000").click();
                                    }
                                });
                            }
                        };
                        comm.confirm(confirmObj);
                    });

                }
                else if (self.searchTable.getRecordIndexValue(record, 'apprStatus') == '1') {//待启用

                    link2 = $('<a>启用</a>').click(function (e) {
                        var confirmObj = {
                            imgFlag: true,             //显示图片
                            content: '您确认启用此模版？',
                            callOk: function () {
                                var stopParam = {
                                    templetId: obj.templetId,
                                    custId: $.cookie('custId')
                                };
                                dataModule.enableContractTemp(stopParam, function (res) {
                                    if (res.retCode = 22000) {
                                        $("#051100000000_subNav_051104000000").click();
                                    }
                                });
                            }
                        };
                        comm.confirm(confirmObj);
                    });

                }

                return link2;
            }
        },

        add: function (record, rowIndex, colIndex, options) {
            if (colIndex == 5) {
                var link3 = null;
                var obj = self.searchTable.getRecord(rowIndex);
                if (self.searchTable.getRecordIndexValue(record, 'apprStatus') == '1') {

                    link3 = $('<a>修改</a>').click(function (e) {

                        $('#busibox').html(_.template(htmbwh_xgmbTpl, obj));
                        comm.liActive_add($('#xgmb'));
                        $('#editResSelector').hide();
                        /*下拉列表*/
                        comm.initSelect('#enterprise_type_xgmb', comm.lang("contractManage").custType, 185, obj.custType);
                        if (obj.custType == 2) {
                            //成员企业
                            comm.initSelect('#resources_state_xgmb', comm.lang("contractManage").bSelector, 160, obj.resType);
                            $('#editResSelector').show();
                        } else if (obj.custType == 3) {
                            //托管企业
                            comm.initSelect('#resources_state_xgmb', comm.lang("contractManage").tSelector, 160, obj.resType);
                            $('#editResSelector').show();
                        }
                        $("#enterprise_type_xgmb").change(function (e) {
                            $('#editResSelector').hide();
                            var custType_xgmb_val = $("#enterprise_type_xgmb").attr("optionvalue");
                            if (custType_xgmb_val == 2) {
                                //成员企业
                                comm.initSelect('#resources_state_xgmb', comm.lang("contractManage").bSelector, 160, 4);
                                $('#editResSelector').show();
                            } else if (custType_xgmb_val == 3) {
                                //托管企业
                                comm.initSelect('#resources_state_xgmb', comm.lang("contractManage").tSelector, 160, 1);
                                $('#editResSelector').show();
                            }
                        });


                        /*end*/

                        /*组合框*/
                        $('#template_content_xgmb').xheditor({
                            upLinkUrl: "upload.php",
                            upLinkExt: "zip,rar,txt",
                            upImgUrl: "upload.php",
                            upImgExt: "jpg,jpeg,gif,png",
                            upFlashUrl: "upload.php",
                            upFlashExt: "swf",
                            upMediaUrl: "upload.php",
                            upMediaExt: "wmv,avi,wma,mp3,mid",
                            width: 678,
                            height: 150
                        });
                        /*end*/
                        $('#xzmb_edit_btn').click(function () {
                            var saveParam = {
                                templetId: obj.templetId,
                                templetName: $('#templetName_xgmb').val().trim(),
                                templetContent: $('#template_content_xgmb').val().trim(),
                                reqOperator: $.cookie('custId'),
                                updatedBy: $.cookie('custId')
                            };

                            var custType = $("#enterprise_type_xgmb").attr("optionvalue");
                            if (custType) {
                                saveParam.custType = custType;
                                if (custType == 2 || custType == 3) {
                                    var resType = $("#resources_state_xgmb").attr("optionvalue");
                                    if (resType) {
                                        saveParam.resType = resType;
                                    }
                                }
                            }


                            dataModule.modifyContractTemp(saveParam, function (res) {
                                if (res.retCode = 22000) {
                                    $("#051100000000_subNav_051104000000").click();
                                }
                            });
                        });

                        $('#xgmb_back').triggerWith('#htmbwh');
                    });

                }

                return link3;
            }

        }
    };

    return self;
});