define(['text!certificateManageTpl/dsfywzsgl/zsffgl.html',
    'text!certificateManageTpl/dsfywzsgl/zsffgl_ck.html',
    'text!certificateManageTpl/dsfywzsgl/zsffgl_ffzs.html',
    'certificateManageDat/certificate',
    'certificateManageLan'
], function (zsffglTpl, zsffgl_ckTpl, zsffgl_ffzsTpl, dataModule) {
    var aps_dsfywzsgl_zsffgl = {
        showPage: function () {
            $('#busibox').html(_.template(zsffglTpl));

            /*下拉列表*/
            comm.initSelect('#printStatus', comm.lang("certificateManage").printStatusEnum, null, null, {name: '全部', value: ''});
            comm.initSelect('#sendStatus', comm.lang("certificateManage").sendStatusEnum, 125, null, {name: '全部', value: ''});

            /*end*/

            /*表格数据模拟*/

            $("#qry_third_certificate_btn").click(function () {
                var params = {
                    search_entResNo: $("#entResNo").val().trim(),			//开始时间
                    search_certificateNo: $("#certificateNo").val().trim(),		//结束时间
                    search_entCustName: $("#entCustName").val().trim()		//互生号/手机号
                };
                var printStatus = $("#printStatus").attr("optionvalue");
                if (printStatus) {
                    params.search_printStatus = printStatus;
                }
                var sendStatus = $("#sendStatus").attr("optionvalue");
                if (sendStatus) {
                    params.search_sendStatus = sendStatus;
                }

                comm.getCommBsGrid("searchTable", "find_third_certificate_give_out_by_page", params, comm.lang("certificateManage"), aps_dsfywzsgl_zsffgl.detail, aps_dsfywzsgl_zsffgl.edit, aps_dsfywzsgl_zsffgl.del);
            });

            /*end*/
        },
        detail: function (record, rowIndex, colIndex, options) {
            if (colIndex == 1) {
                return record.certificateNo ? record.certificateNo.substring(11, record.certificateNo.length) : '';
            }
            if (colIndex == 5) {
                return comm.lang("certificateManage").printStatusEnum[record.isPrint];
            }
            if (colIndex == 6) {
                return comm.lang("certificateManage").sendStatusEnum[record.isSend];
            }
            if (colIndex == 7) {
                var link1 = $('<a>查看</a>').click(function (e) {
                    aps_dsfywzsgl_zsffgl.chaKan(record);
                });
                return link1;
            }
        },

        edit: function (record, rowIndex, colIndex, options) {
            if (colIndex == 7 && record.isPrint) {
                var link2 = $('<a>发放证书</a>').click(function (e) {
                    aps_dsfywzsgl_zsffgl.ffzs(record);
                });
                return link2;
            }
        },

        del: function (record, rowIndex, colIndex, options) {
            if (colIndex == 7) {
                if (record.isPrint) {
                    return $('<a>重新打印</a>').click(function (e) {
                        aps_dsfywzsgl_zsffgl.daYin(record);
                    });
                } else {
                    return $('<a>打印</a>').click(function (e) {
                        aps_dsfywzsgl_zsffgl.daYin(record);
                    });
                }
            }
        },
        chaKan: function (obj) {
            $('#busibox_ck').html(_.template(zsffgl_ckTpl, obj));
            $('#busibox_ck').removeClass("none");
            comm.liActive_add($('#ck'));
            $("#busibox").addClass("none");
            $("#zsffgl_ckTpl").removeClass("none");

            /*表格数据模拟*/
            var chaKanParam = {
                search_certificateNo: obj.certificateNo
            };

            comm.getCommBsGrid("chakanSearchTable", "find_third_certificate_give_out_recode", chaKanParam, comm.lang("certificateManage"), aps_dsfywzsgl_zsffgl.chaKanDetail);

            /*end*/

            //$('#back_zsffgl').triggerWith('#zsffgl');
            //返回按钮
            $('#back_zsffgl').click(function () {
                //隐藏头部菜单
                $('#zsffgl_ckTpl').addClass('none');
                $('#busibox').removeClass('none');
                $('#ck').addClass("tabNone").find('a').removeClass('active');
                $('#zsffgl').find('a').addClass('active');
                $('#busibox_ck').addClass("none");
            });
            //

        },
        chaKanDetail: function (record, rowIndex, colIndex, options) {
        	if (colIndex == 1) {
                return record.certificateNo ? record.certificateNo.substring(11, record.certificateNo.length) : '';
            }
            // if (colIndex == 2) {
            //     return comm.lang("certificateManage").sealStatusEnum[record.sealStatus];
            // }
            if (colIndex == 2) {
                return comm.lang("certificateManage").printStatusEnum[record.isPrint];
            }
            if (colIndex == 5) {
                if (record.sendRemark) {
                    return $('<a>查看</a>').click(function (e) {
                        comm.alert(record.sendRemark);
                    });
                }
            }
            if (colIndex == 6) {
                if (record.recRemark) {
                    return $('<a>查看</a>').click(function (e) {
                        comm.alert(record.recRemark);
                    });
                }
            }
            return "";
        },
        ffzs: function (obj) {
            var reqParam = {
                certificateNo: obj.certificateNo
            };
            dataModule.findSellCertificateById(reqParam, function (res) {
                if (res.data) {
                    var reqData = res.data;
                    reqData.statusName = comm.lang("certificateManage").sealStatusEnum[reqData.status];
                    res.data['isPrint']=obj.isPrint;
                    $('#busibox_ck').html(_.template(zsffgl_ffzsTpl, res.data));
                    $('#busibox_ck').removeClass("none");
                    comm.liActive_add($('#ffzs'));
                    $("#busibox").addClass("none");
                    $("#zsffgl_ffzsTpl").removeClass("none");

                    //$('#ffzs_cancel').triggerWith('#zsffgl');

                    //返回按钮
                    $('#ffzs_cancel').click(function () {
                        //隐藏头部菜单
                        $('#zsffgl_ffzsTpl').addClass('none');
                        $('#busibox').removeClass('none');
                        $('#ffzs').addClass("tabNone").find('a').removeClass('active');
                        $('#zsffgl').find('a').addClass('active');
                        $('#busibox_ck').addClass("none");
                    });
                    //

                    $('#submit_btn').click(function () {
                        var submitParam = {
                            certificateNo: reqData.certificateNo
                        };
                        var sendRemark = $.trim($("#sendRemark").val());
                        if (sendRemark) {
                            submitParam.sendRemark = sendRemark;
                        } else {
                            comm.warn_alert(comm.lang("certificateManage").zsSendRemarkError);
                            return false;
                        }

                        if (obj.isSend) {
                        	//原件是否已收回
                            var originalIsRec = $('input:radio[name=originalIsRec]:checked').val();
                            var recRemark = $.trim($('#recRemark').val());
                           
                            if (originalIsRec == 1 && comm.isEmpty(recRemark)) {
                            	  comm.error_alert(comm.lang("certificateManage").zsRecvRemarkError);
                                  return false;
                            } else {
                            	  submitParam.recRemark = recRemark;
                            }
                            if (originalIsRec) {
                                submitParam.originalIsRec =originalIsRec;
                            }
                        }

                        dataModule.giveOutThirdCertificate(submitParam, function (res) {
                            if (res.retCode == '22000') {
                                //$("#zsffgl").click();
                                $('#zsffgl_ffzsTpl').addClass('none');
                                $('#busibox').removeClass('none');
                                $('#ffzs').addClass("tabNone").find('a').removeClass('active');
                                $('#zsffgl').find('a').addClass('active');
                                $('#qry_third_certificate_btn').click();
                            }
                        });
                    });
                } else {
                    comm.error_alert("error");//comm.lang("messageCenter")[32702]);
                }
            })
        },
        daYin: function (obj) {
            var reqParam = {
                certificateNo: obj.certificateNo,
                realTime: true
            };
            dataModule.findCertificateContentById(reqParam, function (res) {
                var htmPath = comm.getFsServerUrl(res.data.tempFileId);//"http://192.168.41.171:8080/hsxt-portal-aps-web/modules/certificateManage/tpl/zsmb/dsfzsmb.html";//comm.getFsServerUrl(obj.tempFileId);
                var imgPath = comm.getFsServerUrl(res.data.tempPicId);//"http://192.168.41.171:8080/hsxt-portal-aps-web/resources/img/3.jpg";//comm.getFsServerUrl(obj.tempPicId);

                var templateData = JSON.parse(res.data.varContent);
                templateData.tempPicId = imgPath;

                if (res.data.entResNo == null || res.data.entResNo.length != 11) {
                    comm.error_alert("企业信息错误");
                    return;
                }
                var ctfNo = res.data.entResNo.split("");
                templateData.ctfNo0 = ctfNo[0];
                templateData.ctfNo1 = ctfNo[1];
                templateData.ctfNo2 = ctfNo[2];
                templateData.ctfNo3 = ctfNo[3];
                templateData.ctfNo4 = ctfNo[4];
                templateData.ctfNo5 = ctfNo[5];
                templateData.ctfNo6 = ctfNo[6];
                templateData.ctfNo7 = ctfNo[7];
                templateData.ctfNo8 = ctfNo[8];
                templateData.ctfNo9 = ctfNo[9];
                templateData.ctfNo10 = ctfNo[10];
                $.get(htmPath, function (res) {
                    $('#dialogBox').html(_.template(res, templateData));
                    /*弹出框*/
                    $("#dialogBox").dialog({
                        title: "打印证书",
                        width: "1045",
                        modal: true,
                        closeIcon: true,
                        buttons: {
                            "打印": function () {
                            	/*var bdhtml = window.document.body.innerHTML;
                                var sprnstr = "<!--startprint-->";
                                var eprnstr = "<!--endprint-->";
                                var prnhtml = bdhtml.substring(bdhtml.indexOf(sprnstr) + 17);
                                prnhtml = prnhtml.substring(0, prnhtml.indexOf(eprnstr));
                                var myWindow = window.open('', '', '');
                                myWindow.document.write(window.document.documentElement.outerHTML);
                                myWindow.document.body.innerHTML = prnhtml;
                                myWindow.focus();
                                myWindow.print();*/
                                
                                var prnhtml =$('#tContent').parent().html();
        						var myWindow = window.open('print.html');
        						if($.browser.msie) {
        							myWindow.onload = new function(){
        								myWindow.document .body.innerHTML = prnhtml;
        								myWindow.print();
        							}; 
        						}else{
        							myWindow.onload = function(){
        								myWindow.document .body.innerHTML = prnhtml;
        								myWindow.print();
        							}; 
        						}

                                var printParam = {certificateNo: templateData.entResNo+templateData.uniqueCode};
                                dataModule.printThirdCertificate(printParam, function (res) {
                                    /* bdhtml=window.document.body.innerHTML;
                                     sprnstr="<!--startprint-->";
                                     eprnstr="<!--endprint-->";
                                     prnhtml=bdhtml.substring(bdhtml.indexOf(sprnstr)+17);
                                     prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
                                     //var openWin = window.open("","","");
                                     //openWin.document.write(prnhtml);
                                     //openWin.print();
                                     window.document.body.innerHTML=prnhtml;
                                     window.print();*/
                                });
                                $(this).dialog("destroy");
                                $("#qry_third_certificate_btn").click();
                            },
                            "关闭": function () {
                                $(this).dialog("destroy");
                                $('#qry_third_certificate_btn').click();
                                $('#busibox_ck').addClass("none");
                            }
                        }
                    });
                });
            });
        }
    };
    return aps_dsfywzsgl_zsffgl;
});