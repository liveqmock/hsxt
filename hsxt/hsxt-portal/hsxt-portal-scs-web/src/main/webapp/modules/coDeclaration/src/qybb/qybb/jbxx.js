define(['text!coDeclarationTpl/qybb/qybb/jbxx.html',
    'coDeclarationDat/qybb/qybb',
    'coDeclarationLan'], function (jbxxTpl, dataModoule) {
    var scs_qybb_jbxx = {
        showPage: function () {
            if (scs_qybb_jbxx.getApplyId() == null) {
                $('#qybb_view').html(_.template(jbxxTpl));
                scs_qybb_jbxx.initForm();
            } else {
                scs_qybb_jbxx.initData();
            }
        },
        /**
         * 初始化数据
         */
        initData: function () {
            var params = {applyId: scs_qybb_jbxx.getApplyId()};
            dataModoule.getEntFilingById(params, function (res) {
                $('#qybb_view').html(_.template(jbxxTpl, res.data));
                scs_qybb_jbxx.initForm(res.data.provinceNo, res.data.cityNo);
            });
        },
        /**
         * 初始化界面
         */
        initForm: function (prov, city) {
            $('#qybb_bc').css('display', '');
            $('#qybb_xyb').css('display', '');
            $('#qybb_syb').css('display', 'none');
            $('#qybb_tj').css('display', 'none');

            comm.initProvSelect('#provinceNo', {}, 80, null);
            comm.initCitySelect('#cityNo', {}, 80, null);

            //初始化省份
            cacheUtil.findCacheSystemInfo(function (sysRes) {
                cacheUtil.findCacheProvinceByParent(sysRes.countryNo, function (provArray) {
                    comm.initProvSelect('#provinceNo', provArray, 80, prov).change(function (e) {
                        cacheUtil.findCacheCityByParent(sysRes.countryNo, $(this).attr('optionValue'), function (cityArray) {
                            comm.initCitySelect('#cityNo', cityArray, 80).selectListIndex(0);
                        });
                    });
                });
                //初始化城市
                if (prov) {
                    cacheUtil.findCacheSystemInfo(function (sysRes) {
                        cacheUtil.findCacheCityByParent(sysRes.countryNo, prov, function (cityArray) {
                            comm.initCitySelect('#cityNo', cityArray, 80, city);
                        });
                    });
                }
            });

            //保存
            $('#qybb_bc').click(function () {
                if (scs_qybb_jbxx.validateData().form()) {
                    scs_qybb_jbxx.savaInfo(false);
                }
            });

            //下一步
            $('#qybb_xyb').click(function () {
                if (scs_qybb_jbxx.validateData().form()) {
                    scs_qybb_jbxx.savaInfo(true);
                }
            });

            //赋值国家代码
            $("#countryNo").val(comm.getCustPlace()[0]);
        },
        /**
         * 数据校验
         */
        validateData: function () {
            var validate = $("#filing_form").validate({
                rules: {
                    entCustName: {
                        required: true,
                        rangelength: [2, 64]
                    },
                    entAddress: {
                        required: true,
                        rangelength: [2, 128]
                    },
                    provinceNo: {
                        required: true
                    },
                    cityNo: {
                        required: true
                    },
                    legalName: {
                        required: true,
                        rangelength: [2, 20]
                    },
                    linkman: {
                        required: true,
                        rangelength: [2, 20]
                    },
                    licenseNo: {
                        required: true,
                        businessLicence: true
                    },
                    entType: {
                        required: false,
                        rangelength: [2, 20]
                    },
                    phone: {
                        required: true,
                        mobileNo: true
                    },
                    dealArea: {
                        required: false,
                        rangelength: [1, 300]
                    },
                    reqReason: {
                        required: false,
                        rangelength: [1, 300]
                    }
                },
                messages: {
                    entCustName: {
                        required: comm.lang("coDeclaration")[32617],
                        rangelength: comm.lang("coDeclaration")[32630]
                    },
                    entAddress: {
                        required: comm.lang("coDeclaration")[32618],
                        rangelength: comm.lang("coDeclaration")[32631]
                    },
                    provinceNo: {
                        required: comm.lang("coDeclaration")[32664]
                    },
                    cityNo: {
                        required: comm.lang("coDeclaration")[32665]
                    },
                    legalName: {
                        required: comm.lang("coDeclaration")[32620],
                        rangelength: comm.lang("coDeclaration")[32632]
                    },
                    linkman: {
                        required: comm.lang("coDeclaration")[32622],
                        rangelength: comm.lang("coDeclaration")[32633]
                    },
                    licenseNo: {
                        required: comm.lang("coDeclaration")[32619],
                        businessLicence: comm.lang("coDeclaration")[32507]
                    },
                    entType: {
                        required: comm.lang("coDeclaration")[32623],
                        rangelength: comm.lang("coDeclaration")[32634]
                    },
                    phone: {
                        required: comm.lang("coDeclaration")[32625],
                        mobileNo: comm.lang("coDeclaration")[32506]
                    },
                    dealArea: {
                        required: comm.lang("coDeclaration")[32626],
                        rangelength: comm.lang("coDeclaration")[32628]
                    },
                    reqReason: {
                        required: comm.lang("coDeclaration")[32627],
                        rangelength: comm.lang("coDeclaration")[32629]
                    }
                },
                errorPlacement: function (error, element) {
                    $(element).attr("title", $(error).text()).tooltip({
                        tooltipClass: "ui-tooltip-error",
                        destroyFlag: true,
                        destroyTime: 3000,
                        position: {
                            my: "left+2 top+30",
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
            return validate;
        },
        /**
         * 保存企业报备基本信息
         * @param autoNext 是否进行下一步
         */
        savaInfo: function (autoNext) {
            var scs_qybb_jbxx = this;

            var params = $("#filing_form").serializeJson();
            params.applyId = this.getApplyId();
            params.provinceNo = $("#provinceNo").attr('optionValue');
            params.cityNo = $("#cityNo").attr('optionValue');
            dataModoule.createEntFiling(params, function (res) {
                scs_qybb_jbxx.saveStep(1, res.data);
                if (autoNext) {
                    $('#qybb_gdxx').click();
                } else {
                    comm.alert({
                        content: comm.lang("coDeclaration")[22000], callOk: function () {
                        }
                    });
                }
            });
        },
        /**
         * 控制步骤
         * @param curStep 当前步骤
         * @param applyId 申请编号
         */
        saveStep: function (curStep, applyId) {
            var entFilling = comm.getCache("coDeclaration", "entFilling");
            if ((entFilling.curStep + 1) <= curStep) {
                entFilling.curStep = curStep;
            }
            entFilling.applyId = applyId;
        },
        /**
         * 获取申请编号
         */
        getApplyId: function () {
            return comm.getCache("coDeclaration", "entFilling").applyId;
        }
    };
    return scs_qybb_jbxx;
}); 
