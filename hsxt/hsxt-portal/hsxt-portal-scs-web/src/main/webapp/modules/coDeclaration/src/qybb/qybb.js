define(['text!coDeclarationTpl/qybb/qybb.html',
    'coDeclarationSrc/qybb/qybb/jbxx',
    'coDeclarationSrc/qybb/qybb/gdxx',
    'coDeclarationSrc/qybb/qybb/fjxx',
    'coDeclarationDat/qybb/qybb',
    'coDeclarationLan'
], function (qybbTpl, jbxx, gdxx, fjxx, dataModoule) {
    var scs_qybb = {
        showPage: function (applyId, curStep) {

            $('#contentWidth_2').html(_.template(qybbTpl));
            $('#contentWidth_2').show();
            $('#contentWidth_1').hide();

            //如果是修改显示取消按钮,否则隐藏
            if (applyId) {
                $("#qybb_qx").show();
            } else {
            	$('#contentWidth_1').empty();
                $("#qybb_qx").hide();
            }

            //初始化数据
            this.initData(applyId, curStep);

            $('#qybb_qyjbxx').click(function (e) {
                if (!scs_qybb.isSaveFjxx()) {
                    comm.i_confirm(comm.lang("coDeclaration").noSubmitAttachement, function () {
                        jbxx.showPage();
                        comm.liActive($('#qybb_qyjbxx'));
                    }, 320);
                } else {
                    jbxx.showPage();
                    comm.liActive($('#qybb_qyjbxx'));
                }
            }.bind(this)).click();

            //股东信息
            $('#qybb_gdxx').click(function (e) {
                if (scs_qybb.gotoStep(2)) {
                    if (!scs_qybb.isSaveFjxx()) {
                        comm.i_confirm(comm.lang("coDeclaration").noSubmitAttachement, function () {
                            gdxx.showPage();
                            comm.liActive($('#qybb_gdxx'));
                        }, 320);
                    } else {
                        gdxx.showPage();
                        comm.liActive($('#qybb_gdxx'));
                    }
                }
            }.bind(this));

            //取消修改
            $('#qybb_qx').click(function (e) {
            	 $('#contentWidth_2').empty();
            	 $('#contentWidth_2').hide();
                 $('#contentWidth_1').show();
            }.bind(this));

            //附件信息
            $('#qybb_fjxx').click(function (e) {
                if (scs_qybb.gotoStep(3)) {
                    fjxx.showPage();
                    comm.liActive($('#qybb_fjxx'));
                }
            }.bind(this));

            //报备提交
            $('#qybb_tj').click(function () {
                if (scs_qybb.gotoStep(4)) {
                    scs_qybb.realSubmit();
                }else{
                    fjxx.aptitudeUpload(function () {
                        scs_qybb.realSubmit();
                    });
                }
            });
        },
        /**
         * 初始化缓存数据
         */
        initData: function (applyId, curStep) {
            comm.delCache("coDeclaration", "entFilling");
            if (!applyId) {
                comm.setCache("coDeclaration", "entFilling", {"curStep": 0, "isSubmit": false, "applyId": null});
            } else {
                comm.setCache("coDeclaration", "entFilling", {"curStep": curStep, "isSubmit": false, "applyId": applyId});
            }
        },
        realSubmit:function () {
            var params = {applyId: scs_qybb.getApplyId()};
            comm.confirm({
                imgFlag: true,
                title: comm.lang("coDeclaration").confirmFillingTitle,
                content: comm.lang("coDeclaration").confirmFillingContent,
                callOk: function () {
                    var str;
                    dataModoule.isExistSameOrSimilar(params, function (res) {
                        str = res.data;
                        if (str != "0") {
                            comm.confirm({
                                imgFlag: true,
                                title: comm.lang("coDeclaration").confirmFillingTitle,
                                content: str,
                                callOk: function () {
                                    dataModoule.submitFiling(params, function (res) {
                                        var entDeclare = comm.getCache("coDeclaration", "entFilling");
                                        entDeclare.isSubmit = true;
                                        comm.alert({
                                            content: comm.lang("coDeclaration").submitSuccessContent, callOk: function () {
                                                $($("#bbMenuId").val()).click();
                                            }
                                        });
                                    });
                                },
                                callCancel: function () {
                                }
                            });
                        } else {
                            //直接提交
                            dataModoule.submitFiling(params, function (res) {
                                var entDeclare = comm.getCache("coDeclaration", "entFilling");
                                entDeclare.isSubmit = true;
                                comm.alert({
                                    content: comm.lang("coDeclaration").submitSuccessContent, callOk: function () {
                                        $($("#bbMenuId").val()).click();
                                    }
                                });
                            });
                        }

                    })
                },
                callCancel: function () {
                }
            });
        },
        /**
         * 控制步骤
         */
        gotoStep: function (toStep) {
            var entFilling = comm.getCache("coDeclaration", "entFilling");
            if (toStep == 4 && entFilling.curStep != 3) {
                // comm.warn_alert(comm.lang("coDeclaration").entFillingStepEnum[entFilling.curStep + 1]);
                return false;
            }
            if (entFilling.curStep >= 1) {
                return true;
            }
            comm.warn_alert(comm.lang("coDeclaration").entFillingStepEnum[entFilling.curStep + 1]);
            return false;
        },
        /**
         * 获取申请编号
         */
        getApplyId: function () {
            return comm.getCache("coDeclaration", "entFilling").applyId;
        },
        isSaveFjxx: function () {
            if (comm.isNotEmpty($('#isSaveFj').val())) {
                return true;
            }
            if ($('i[id^=realPicFileId-] img') && $('i[id^=realPicFileId-] img').length > 0 && comm.isEmpty($("#fileId-1").val())) {
                return false;
            }
            return true;
        }
    };
    return scs_qybb;
}); 
