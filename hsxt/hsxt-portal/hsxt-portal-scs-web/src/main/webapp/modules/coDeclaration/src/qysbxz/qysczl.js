define(['text!coDeclarationTpl/qysbxz/qysczl.html',
    'coDeclarationDat/qysbxz/qysczl',
    'coDeclarationLan'], function (qysczlTpl, dataModoule) {
    var scs_qysczl = {
        creType:null,
        showPage: function () {
            $('#contentWidth_2').html(_.template(qysczlTpl));
            scs_qysczl.initForm();
            scs_qysczl.initData();
        },
        getApplyId: function () {
            return comm.getCache("coDeclaration", "entDeclare").applyId;
        },
        initForm: function () {
            //处理返回按钮
            if ($('#030200000000_subNav_030201000000').hasClass('s_hover')) {
                $('#qysczl_fh').hide();
            } else {
                $('#qysczl_fh').show();
            }
            $('#qysczl_fh').click(function () {
                $('#030200000000_subNav_030202000000').click();
            });

            //保存
            $('#qysczl_save').click(function () {
                scs_qysczl.aptitudeUpload(false);
            });

            //判断页面是否有更新（编辑时候用）
            $("#qysczl_form :input").change(function () {
                comm.setCache("coDeclaration", "updateFlag", true);
            });

            //申报提交
            $('#qysczl_sbtj').click(function () {
                scs_qysczl.submitData();
            });

            scs_qysczl.initTmpPicPreview();

            //加载样例图片
            cacheUtil.findDocList(function (map) {
                comm.initPicPreview("#case-fileId-1", comm.getPicServerUrl(map.picList, '1003'), null);
                comm.initPicPreview("#case-fileId-2", comm.getPicServerUrl(map.picList, '1004'), null);
                comm.initPicPreview("#case-fileId-3", comm.getPicServerUrl(map.picList, '1010'), null);
                comm.initPicPreview("#case-fileId-6", comm.getPicServerUrl(map.picList, '1006'), null);
                comm.initPicPreview("#case-fileId-7", comm.getPicServerUrl(map.picList, '1007'), null);
                comm.initDownload("#case-fileId-8", map.comList, '1006');
            });
        },
        /**
         * 绑定图片预览
         */
        initTmpPicPreview: function () {
            var btnIds = ['#lrCredentialFrontFileId', '#lrCredentialBackFileId', '#busLicenceFileId', '#organizationFileId', '#taxplayerFileId', '#venBefProtocolId'];
            var imgIds = ['#thum-1', '#thum-2', '#thum-3', '#thum-6', '#thum-7', '#thum-8'];
            $("body").on("change", "", function () {
                for (var k in imgIds) {
                    comm.initTmpPicPreview(imgIds[k], $(imgIds[k]).children().first().attr('src'));
                }
            });
            comm.initUploadBtn(btnIds, imgIds);
        },
        /**
         * 初始化数据
         */
        initData: function () {
            var params = {
                applyId: this.getApplyId()
            };
            dataModoule.findAptitudeByApplyId(params, function (res) {
                var realList = res.data.realList;//附件信息
                var venBefFlag = res.data.venBefFlag;//是否需要填写创业帮扶协议
                scs_qysczl.creType = res.data.creType;
                $("#aptRemark").val(comm.removeNull(res.data.aptRemark));//备注信息
                if (realList) {
                    for (var k in realList) {
                        var title = comm.getNameByEnumId(realList[k].aptitudeType, comm.lang("coDeclaration").aptitudeTypeEnum);
                        comm.initPicPreview("#thum-" + realList[k].aptitudeType, realList[k].fileId, title);
                        $("#thum-" + realList[k].aptitudeType).html("<img width='107' height='64' src='" + comm.getFsServerUrl(realList[k].fileId) + "'/>");
                        $("#aptitude-" + realList[k].aptitudeType).val(realList[k].aptitudeId);
                        $("#real-fileId-" + realList[k].aptitudeType).val(realList[k].fileId);
                    }
                }
                if (venBefFlag == "true") {
                    $("#li-venBefProtocolId").show();
                } else {
                    $("#li-venBefProtocolId").hide();
                }
                if(scs_qysczl.creType&&scs_qysczl.creType==2) {
                    $('#case-fileId-1').hide();
                    // $('#case-fileId-2').hide();
                    $('#lrCredentialBackFileDiv').hide();
                }else{
                    $('#case-fileId-1').show();
                    // $('#case-fileId-2').show();
                    $('#lrCredentialBackFileDiv').show();
                }
                $("#venBefFlag").val(venBefFlag);
            });
        },
        /**
         * 表单校验
         */
        validateData: function () {
            var validate = $("#qysczl_form").validate({
                rules: {
                    lrCredentialFrontFileId: {
                        required: true
                    },
                    lrCredentialBackFileId: {
                        required: true
                    },
                    busLicenceFileId: {
                        required: true
                    },
                    organizationFileId: {
                        required: true
                    },
                    taxplayerFileId: {
                        required: true
                    },
                    venBefProtocolId: {
                        required: false
                    },
                    aptRemark: {
                        rangelength: [0, 300]
                    }
                },
                messages: {
                    lrCredentialFrontFileId: {
                        required: comm.lang("coDeclaration")[32637]
                    },
                    lrCredentialBackFileId: {
                        required: comm.lang("coDeclaration")[32638]
                    },
                    busLicenceFileId: {
                        required: comm.lang("coDeclaration")[32639]
                    },
                    organizationFileId: {
                        required: comm.lang("coDeclaration")[32680]
                    },
                    taxplayerFileId: {
                        required: comm.lang("coDeclaration")[32681]
                    },
                    venBefProtocolId: {
                        required: comm.lang("coDeclaration")[32682]
                    },
                    aptRemark: {
                        rangelength: comm.lang("common")[22063]
                    }
                },
                errorPlacement: function (error, element) {
                    $(element).attr("title", $(error).text()).tooltip({
                        tooltipClass: "ui-tooltip-error",
                        destroyFlag: true,
                        destroyTime: 3000,
                        position: {
                            my: "left+200 top+30",
                            at: "left top"
                        }
                    }).tooltip("open");
                    $(".ui-tooltip").css("max-width", "230px");
                },
                success: function (element) {
                    $(element).tooltip();
                    $(element).tooltip("destroy");
                }
            });
            /**
             * 如果是修改则无需对上传文件进行必填校验
             */
            if ($("#real-fileId-1").val() != "") {
                validate.settings.rules.lrCredentialFrontFileId = {required: false};
            }
            if (scs_qysczl.creType==2||$("#real-fileId-2").val() != "") {
                validate.settings.rules.lrCredentialBackFileId = {required: false};
            }
            if ($("#real-fileId-3").val() != "") {
                validate.settings.rules.busLicenceFileId = {required: false};
            }
            if ($("#real-fileId-6").val() != "") {
                validate.settings.rules.organizationFileId = {required: false};
            }
            if ($("#real-fileId-7").val() != "") {
                validate.settings.rules.taxplayerFileId = {required: false};
            }
            //为托管企业需要校验创业帮扶协议件
            if ($("#venBefFlag").val() == "true" && $("#real-fileId-8").val() == "") {
                validate.settings.rules.venBefProtocolId = {required: true};
            } else {
                validate.settings.rules.venBefProtocolId = {required: false};
            }
            return validate;
        },
        /**
         * 企业申报-上传附件信息
         * @param direct 是否直接提交
         */
        aptitudeUpload: function (direct) {
            if (!this.validateData().form()) {
                return;
            }
            var ids = [];
            var delFileIds = [];
            if ($("#lrCredentialFrontFileId").val() != "") {
                if ($("#real-fileId-1").val() != "") {
                    delFileIds[delFileIds.length] = $("#real-fileId-1").val();
                }
                ids[ids.length] = "lrCredentialFrontFileId";
            }
            if ($("#lrCredentialBackFileId").val() != "") {
                if ($("#real-fileId-2").val() != "") {
                    delFileIds[delFileIds.length] = $("#real-fileId-2").val();
                }
                ids[ids.length] = "lrCredentialBackFileId";
            }
            if ($("#busLicenceFileId").val() != "") {
                if ($("#real-fileId-3").val() != "") {
                    delFileIds[delFileIds.length] = $("#real-fileId-3").val();
                }
                ids[ids.length] = "busLicenceFileId";
            }
            if ($("#organizationFileId").val() != "") {
                if ($("#real-fileId-6").val() != "") {
                    delFileIds[delFileIds.length] = $("#real-fileId-6").val();
                }
                ids[ids.length] = "organizationFileId";
            }
            if ($("#taxplayerFileId").val() != "") {
                if ($("#real-fileId-7").val() != "") {
                    delFileIds[delFileIds.length] = $("#real-fileId-7").val();
                }
                ids[ids.length] = "taxplayerFileId";
            }
            if ($("#venBefProtocolId").val() != "") {
                if ($("#real-fileId-8").val() != "") {
                    delFileIds[delFileIds.length] = $("#real-fileId-8").val();
                }
                ids[ids.length] = "venBefProtocolId";
            }
            if (ids.length == 0) { // by likui 重新提交时，没有修改图片则提交
                //comm.warn_alert(comm.lang("coDeclaration").nopicUpdate);
                scs_qysczl.saveAptitudeInfo(direct);
                return;
            }
            comm.uploadFile(null, ids, function (data) {
                if (data.lrCredentialFrontFileId) {
                    $("#real-fileId-1").val(data.lrCredentialFrontFileId);
                }
                if (data.lrCredentialBackFileId) {
                    $("#real-fileId-2").val(data.lrCredentialBackFileId);
                }
                if (data.busLicenceFileId) {
                    $("#real-fileId-3").val(data.busLicenceFileId);
                }
                if (data.organizationFileId) {
                    $("#real-fileId-6").val(data.organizationFileId);
                }
                if (data.taxplayerFileId) {
                    $("#real-fileId-7").val(data.taxplayerFileId);
                }
                if (data.venBefProtocolId) {
                    $("#real-fileId-8").val(data.venBefProtocolId);
                }
                scs_qysczl.saveAptitudeInfo(direct);
            }, function (err) {
                scs_qysczl.initTmpPicPreview();
            }, {delFileIds: delFileIds});
        },
        /**
         * 企业申报-保存附件资料
         * @param direct 是否直接提交
         */
        saveAptitudeInfo: function (direct) {
            var params = $("#qysczl_form").serializeJson();
            params.applyId = this.getApplyId();
            params.aptRemark = $("#aptRemark").val();
            params.lrCredentialFrontFileId = $("#real-fileId-1").val();
            params.lrCredentialBackFileId = $("#real-fileId-2").val();
            params.busLicenceFileId = $("#real-fileId-3").val();
            params.organizationFileId = $("#real-fileId-6").val();
            params.taxplayerFileId = $("#real-fileId-7").val();
            params.venBefProtocolFileId = $("#real-fileId-8").val();
            params.lrCredentialFrontAptitudeId = $("#aptitude-1").val();
            params.lrCredentialBackAptitudeId = $("#aptitude-2").val();
            params.busLicenceAptitudeId = $("#aptitude-3").val();
            params.organizPicAptitudeId = $("#aptitude-6").val();
            params.taxRegPicAptitudeId = $("#aptitude-7").val();
            params.venBefProtocolAptitudeId = $("#aptitude-8").val();
            params.venBefFlag = $("#venBefFlag").val();
            params.creType = scs_qysczl.creType;
            dataModoule.saveDeclareAptitude(params, function (res) {

                scs_qysczl.saveStep(5);

                comm.delCache("coDeclaration", "updateFlag");

                var realList = res.data;
                if (realList) {
                    for (var k in realList) {
                        $("#aptitude-" + realList[k].aptitudeType).val(realList[k].aptitudeId);
                    }
                }
                if(direct) {
                    scs_qysczl.realSubmit();
                }else {
                    comm.alert({
                        content: comm.lang("coDeclaration")[21000], callOk: function () {
                            scs_qysczl.initForm();
                        }
                    });
                }
            });
        },
        /**
         * 提交申报
         */
        submitData: function () {
            if (scs_qysczl.gotoStep(6)) {
                scs_qysczl.realSubmit();
            }else{
                scs_qysczl.aptitudeUpload(true);
            }
        },
        realSubmit:function () {
            if (!this.validateData().form()) {
                return;
            }
            var params = {};
            params.applyId = this.getApplyId();

            //如果是编辑 且页面有修改 ，则询问是否保存后修改
            if (comm.isNotEmpty(params.applyId) && comm.getCache("coDeclaration", "updateFlag")) {
                comm.warn_alert(comm.lang("coDeclaration").hasChanged);
                return;
            }

            comm.confirm({
                imgFlag: true,
                title: comm.lang("coDeclaration").confirmDeclareTitle,
                content: comm.lang("coDeclaration").confirmDeclareContent,
                callOk: function () {
                    dataModoule.submitDeclare(params, function (res) {
                        var entDeclare = comm.getCache("coDeclaration", "entDeclare");
                        entDeclare.isSubmit = true;

                        comm.delCache("coDeclaration", "updateFlag");

                        comm.alert({
                            content: comm.lang("coDeclaration")[22000], callOk: function () {
                                //跳转到企业申报查询页面
                                $('a[menuurl="coDeclarationSrc/qysbcx/tab"]').trigger('click');
                            }
                        });
                    });
                },
                callCancel: function () {}
            });
        },
        /**
         * 控制步骤
         * @param curStep 当前步骤
         */
        saveStep: function (curStep) {
            var entDeclare = comm.getCache("coDeclaration", "entDeclare");
            if ((entDeclare.curStep) <= curStep) {
                entDeclare.curStep = curStep;
            }
        },
        /**
         * 控制步骤
         * @param toStep 目标步骤
         */
        gotoStep: function (toStep) {
            var entDeclare = comm.getCache("coDeclaration", "entDeclare");
            if ((entDeclare.curStep + 1) >= toStep) {
                return true;
            }
            // comm.warn_alert(comm.lang("coDeclaration").entDeclareStepEnum[entDeclare.curStep + 1]);
            return false;
        }
    };
    return scs_qysczl;
}); 
