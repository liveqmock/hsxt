define(['text!certificateManageTpl/xszgzsgl/zsffgl.html',
    'text!certificateManageTpl/xszgzsgl/zsffgl_ck.html',
    'text!certificateManageTpl/xszgzsgl/zsffgl_ffzs.html',
    'certificateManageSrc/zsffrz/zsffrz',
    'certificateManageSrc/zsff/zsff',
    'certificateManageDat/certificate',
    'certificateManageLan'
], function (zsffglTpl, zsffgl_ckTpl, zsffgl_ffzsTpl, zsffrz, zsff, dataModule) {
    var self =  {
        searchTable: null,
        showPage: function () {
            $('#busibox').html(_.template(zsffglTpl));

            /*下拉列表*/
            comm.initSelect('#printStatus', comm.lang("certificateManage").printStatusEnum, null, null, {name: '全部', value: ''});
            comm.initSelect('#sendStatus', comm.lang("certificateManage").sendStatusEnum, null, null, {name: '全部', value: ''});
            /*end*/

            /*表格数据模拟*/

            //var self = this;
            $("#qry_zffsgl_btn").click(function () {
                var params = {
                    search_entResNo: $("#entResNo").val().trim(),			//企业互生号
                    search_certificateNo: $("#certificateNo").val().trim(),
                    search_entCustName: $("#entCustName").val().trim()		//企业名称
                };
                var printStatus = $("#printStatus").attr("optionvalue");
                if (printStatus) {
                    params.search_printStatus = printStatus;
                }
                var sendStatus = $("#sendStatus").attr("optionvalue");
                if (sendStatus) {
                    params.search_sendStatus = sendStatus;
                }

                self.searchTable = comm.getCommBsGrid("searchTable", "find_sell_certificate_give_out_by_page", params, comm.lang("certificateManage"), self.detail, self.edit, self.del);
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
                    var obj = self.searchTable.getRecord(rowIndex);
                    zsffrz.showPage(obj, 'zsffgl');
                    /*
                     var chaKanParam = {
                     certificateNo	:	obj.certificateNo
                     };
                     dataModule.findSellCertificateById(chaKanParam, function(res){
                     if(res.data){
                     comm.liActive_add($('#ck'));
                     res.data.sealStatus = comm.lang("certificateManage").sealStatusEnum[res.data.status];
                     $('#busibox').html(_.template(zsgz_ckTpl, res.data));
                     $('#back_zsffgl').triggerWith('#zsffgl');
                     }
                     });
                     */
                });
                return link1;
            }
        },

        edit: function (record, rowIndex, colIndex, options) {
            if (colIndex == 7 && record.isPrint) {
                var obj = self.searchTable.getRecord(rowIndex);
                var link2 = $('<a>发放证书</a>').click(function (e) {
                    zsff.showPage(obj, "zsffgl");
                });
                return link2;
            }
        },

        del: function (record, rowIndex, colIndex, options) {
            if (colIndex == 7 && !record.isPrint) {
                var link3 = $('<a>打印</a>').click(function (e) {
                    var reqParam = {
                        certificateNo: record.certificateNo,
                        realTime:false
                    };
                    dataModule.findCertificateContentById(reqParam, function (res) {
                        var htmPath = comm.getFsServerUrl(res.data.tempFileId);//"http://192.168.41.171:8080/hsxt-portal-aps-web/modules/certificateManage/tpl/zsmb/xszsmb.html";//comm.getFsServerUrl(obj.tempFileId);
                        var imgPath = comm.getFsServerUrl(res.data.tempPicId);//"http://192.168.41.171:8080/hsxt-portal-aps-web/resources/img/2.jpg";//comm.getFsServerUrl(obj.tempPicId);
                        var templateData = JSON.parse(res.data.varContent);
                        templateData.tempPicId = imgPath;
                        templateData.sealImg = "resources/img/certificate.png";
                        templateData.sealStatus = res.data.sealStatus;
                        $.get(htmPath, function (res) {
                            $('#dialogBox').html(_.template(res, templateData));
                            /*弹出框*/
                            $("#dialogBox").dialog({
                                title: "打印证书",
                                width: "1137",
                                modal: true,
                                closeIcon: true,
                                buttons: {
                                    "打印": function () {
                                       /* var bdhtml = window.document.body.innerHTML;
                                        var sprnstr = "<!--startprint-->";
                                        var eprnstr = "<!--endprint-->";
                                        var prnhtml = bdhtml.substring(bdhtml.indexOf(sprnstr) + 17);
                                        prnhtml = prnhtml.substring(0, prnhtml.indexOf(eprnstr));
                                        var myWindow = window.open('', '', '');
                                        myWindow.document.write(window.document.documentElement.outerHTML);
                                        myWindow.document.body.innerHTML = prnhtml;
                                        myWindow.focus();
                                        myWindow.print();*/
                                    	
                                    	var prnhtml =$('#1457491177842_dialog_content').parent().html();
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

                                        var printParam = {
                                            certificateNo: record.certificateNo
                                        };
                                        dataModule.printThirdCertificate(printParam, function (res) {
                                            /* bdhtml=window.document.body.innerHTML;
                                             sprnstr="<!--startprint-->";
                                             eprnstr="<!--endprint-->";
                                             prnhtml=bdhtml.substring(bdhtml.indexOf(sprnstr)+17);
                                             prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
                                             window.document.body.innerHTML=prnhtml;
                                             window.print();*/
                                        });
                                    },
                                    "关闭": function () {
                                        $(this).dialog("destroy");
                                    }
                                }
                            });
                        });
                    });
                });
            }
            return link3;
        },
        chaKan_1: function (obj) {
        },
        chaKan_2: function (obj) {
        },
        ffzs: function (obj) {
            comm.liActive_add($('#ffzs'));
            $('#busibox').html(_.template(zsffgl_ffzsTpl, obj));
            $('#ffzs_cancel').triggerWith('#zsffgl');

        },
        daYin: function (obj) {

        }
    };
    return self;
});