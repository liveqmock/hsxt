define(['text!certificateManageTpl/xszgzsgl/zsgz.html',
    'text!certificateManageTpl/xszgzsgl/zsgz_ck.html',
    'certificateManageDat/certificate',
    'certificateManageLan'
], function (zsgzTpl, zsgz_ckTpl, dataModule) {
    var self =  {
        searchTable: null,
        showPage: function () {
            $('#busibox').html(_.template(zsgzTpl));

            /*下拉列表*/
            comm.initSelect('#sealStatus', comm.lang("certificateManage").sealStatusSelector, null, null, {name: '全部', value: ''});
            /*end*/

            /*表格数据模拟*/
            //var self = this;

            $("#qry_zsgz_btn").click(function () {
                var params = {
                    search_entResNo: $("#entResNo").val().trim(),			//企业互生号
                    search_entCustName: $("#entCustName").val().trim()		//企业名称
                };
                var sealStatus = $("#sealStatus").attr("optionvalue");
                if (sealStatus) {
                    params.search_sealStatus = sealStatus;
                }

                self.searchTable = comm.getCommBsGrid("searchTable", "find_sell_certificate_by_seal", params, comm.lang("certificateManage"), self.detail, self.edit, self.del);
            });
            /*end*/
        },
        detail: function (record, rowIndex, colIndex, options) {
            //var self=this;
            if (colIndex == 4) {
                return comm.lang("certificateManage").sealStatusEnum[record.sealStatus];
            }
            if (colIndex == 5) {
                var link1 = $('<a>查看</a>').click(function (e) {
                    var obj = self.searchTable.getRecord(rowIndex);
                    var chaKanParam = {
                        certificateNo: obj.certificateNo
                    };
                    dataModule.findSellCertificateById(chaKanParam, function (res) {
                        if (res.data) {
                            res.data.sealStatus = comm.lang("certificateManage").sealStatusEnum[res.data.status];
                            $('#busibox_ck').html(_.template(zsgz_ckTpl, res.data));
                            comm.liActive_add($('#ck'));
                            $("#busibox").addClass("none");
                            $("#zsgz_ckTpl").removeClass("none");
                            $('#busibox_ck').removeClass("none");
                        }
                        //返回按钮
                        $('#back_zsgz').click(function () {
                            //隐藏头部菜单
                            $('#zsgz_ckTpl').addClass('none');
                            $('#busibox').removeClass('none');
                            $('#ck').addClass("tabNone").find('a').removeClass('active');
                            $('#zsgz').find('a').addClass('active');
                            $('#busibox_ck').addClass("none");
                        });
                        //
                    });
                });
                return link1;
            }
        },

        edit: function (record, rowIndex, colIndex, options) {
            if (colIndex == 5) {
                var link2 = $('<a>证书预览</a>').click(function (e) {
                    var reqParam = {
                        certificateNo: record.certificateNo
                    };
                    if(record.sealStatus&&record.sealStatus==1) {
                        reqParam.realTime = false;
                    }else{
                        reqParam.realTime = true;
                    }
                    dataModule.findCertificateContentById(reqParam, function (res) {
                        var htmPath = comm.getFsServerUrl(res.data.tempFileId);
                        var imgPath = comm.getFsServerUrl(res.data.tempPicId);

                        var templateData = JSON.parse(res.data.varContent);
                        templateData.tempPicId = imgPath;
                        templateData.sealImg = "resources/img/certificate.png";
                        templateData.sealStatus = res.data.sealStatus;
                        $.get(htmPath, function (res) {
                            $('#dialogBox_zsyl').html(_.template(res, templateData));
                            if (templateData.sealStatus != 1) {
                                $("#xszs_seal_status").hide();
                            }
                            /*弹出框*/
                            $("#dialogBox_zsyl").dialog({
                                title: "证书预览",
                                width: "1137",
                                modal: true,
                                closeIcon: true,
                                buttons: {
                                    "关闭": function () {
                                        $(this).dialog("destroy");
                                    }
                                }
                            });
                        });
                    });
                });
                return link2;
            }
        },

        del: function (record, rowIndex, colIndex, options) {
            if (colIndex == 5) {
                var link3 = null;
                var obj = self.searchTable.getRecord(rowIndex);
                if (self.searchTable.getColumnValue(rowIndex, 'sealStatus') != '0') {
                    link3 = $('<a>重新盖章</a>').click(function (e) {
                        var reqParam = {
                            certificateNo: obj.certificateNo,
                            realTime: true
                        };
                        dataModule.findCertificateContentById(reqParam, function (res) {
                            var htmPath = comm.getFsServerUrl(res.data.tempFileId);//"http://192.168.41.171:8080/hsxt-portal-aps-web/modules/certificateManage/tpl/zsmb/xszsmb.html";//comm.getFsServerUrl(obj.tempFileId);
                            var imgPath = comm.getFsServerUrl(res.data.tempPicId);//"http://192.168.41.171:8080/hsxt-portal-aps-web/resources/img/2.jpg";//comm.getFsServerUrl(obj.tempPicId);

                            var templateData = JSON.parse(res.data.varContent);
                            templateData.tempPicId = imgPath;
                            templateData.sealImg = "resources/img/certificate.png";
                            $.get(htmPath, function (res) {
                                $('#dialogBox_cxgz').html(_.template(res, templateData));
                                /*弹出框*/
                                $("#dialogBox_cxgz").dialog({
                                    title: "重新盖章",
                                    width: "1137",
                                    modal: true,
                                    closeIcon: true,
                                    buttons: {
                                        "重新盖章": function () {
                                            var sealParam = {
                                                certificateNo: obj.certificateNo
                                            };
                                            dataModule.sellCertificateSeal(sealParam, function (res) {
                                                $('#dialogBox_cxgz').dialog("destroy");
                                                $("#qry_zsgz_btn").click();
                                            });
                                        },
                                        "关闭": function () {
                                            $('#dialogBox_cxgz').dialog("destroy");
                                        }
                                    }
                                });
                            });
                        });

                    });
                }
                else if (self.searchTable.getColumnValue(rowIndex, 'sealStatus') == '0') {
                    link3 = $('<a>盖章</a>').click(function (e) {
                        var reqParam = {
                            certificateNo: obj.certificateNo,
                            realTime: true
                        };
                        dataModule.findCertificateContentById(reqParam, function (res) {
                            var htmPath = comm.getFsServerUrl(res.data.tempFileId);//"http://192.168.41.171:8080/hsxt-portal-aps-web/modules/certificateManage/tpl/zsmb/xszsmb.html";//comm.getFsServerUrl(obj.tempFileId);
                            var imgPath = comm.getFsServerUrl(res.data.tempPicId);//"http://192.168.41.171:8080/hsxt-portal-aps-web/resources/img/2.jpg";//comm.getFsServerUrl(obj.tempPicId);
                            var templateData = JSON.parse(res.data.varContent);
                            templateData.tempPicId = imgPath;
                            templateData.sealImg = "resources/img/certificate.png";
                            $.get(htmPath, function (res) {
                                $('#dialogBox_cxgz').html(_.template(res, templateData));
                                /*弹出框*/
                                $("#dialogBox_cxgz").dialog({
                                    title: "盖章",
                                    width: "1137",
                                    modal: true,
                                    closeIcon: true,
                                    buttons: {
                                        "盖章": function () {
                                            var sealParam = {
                                                certificateNo: obj.certificateNo
                                            };
                                            dataModule.sellCertificateSeal(sealParam, function (res) {
                                                $('#dialogBox_cxgz').dialog("destroy");
                                                $('#zsgz').click();
                                            });
                                        },
                                        "关闭": function () {
                                            $('#dialogBox_cxgz').dialog("destroy");
                                        }
                                    }
                                });
                            });
                        });
                    });
                }
                return link3;
            }
        }
    };
    return self;
});