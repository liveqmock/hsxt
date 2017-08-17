define(['text!coDeclarationTpl/qyxz/qyxtzcxx_fwgs.html',
    'coDeclarationDat/qyxz/qyxtzcxx_fwgs',
    'coDeclarationLan'], function (qyxtzcxx_fwgsTpl, dataModoule) {
    /*
     *推广互生号
     */
    jQuery.validator.addMethod("spreadEntResNoCheck", function(value, element, param) {
        var serRegex = /^(([1-9]\d){1}|(\d[1-9]){1})([1-9]\d{2}|\d{2}[1-9]|\d[1-9]\d)([0]{6})$/;
        var platRegex = /^([0]{8})(([1-9]\d{2})|(\d[1-9]\d)|(\d{2}[1-9]))$/;
        return this.optional(element) || serRegex.test(value) || platRegex.test(value);
    }, comm.lang('coDeclaration').rightSpreadNo);
    var fwgssb = {
        showPage: function () {
            fwgssb.initData();
        },
        /**
         * 获取申请编号
         */
        getApplyId: function () {
            return comm.getCache("coDeclaration", "entDeclare").applyId;
        },
        /**
         * 初始化表单
         */
        initForm: function (data) {
            $('#busibox').html(_.template(qyxtzcxx_fwgsTpl, data));

            //禁用积分增值设置
            $("#comboxDiv").find("input:radio").attr("disabled", true);

            comm.initProvSelect('#province_slt', {}, 70, null);
            comm.initCitySelect('#city_slt', {}, 70, null);

            /**申报类型*/
            $("#toCustType").selectList({
                width: 185,
                optionWidth: 185,
                options: [
                    {name: comm.lang("coDeclaration").tgEntDesc, value: '3'},
                    {name: comm.lang("coDeclaration").cyEntDesc, value: '2'},
                    {name: comm.lang("coDeclaration").fwEntDesc, value: '4', selected: true}
                ]
            }).change(function (e) {
                var str = $(this).attr('optionValue');
                switch (str) {
                    case '3' :
                        require(['coDeclarationSrc/qyxz/qyxtzcxx_tgqy'], function (qyxtzcxx_tgqy) {
                            qyxtzcxx_tgqy.showPage();
                        });
                        break;
                    case '2' :
                        require(['coDeclarationSrc/qyxz/qyxtzcxx_cyqy'], function (qyxtzcxx_cyqy) {
                            qyxtzcxx_cyqy.showPage();
                        });
                        break;
                }
            });

            //刷新增值点数据
            $('#refincrement').click(function () {
                var spreadEntResNo = $("#spreadEntResNo").val();
                var zzdqyglh = $.trim($("#zzdqyglh").val());
                var toMResNo = $.trim($('#toMResNo').val());
                if (!spreadEntResNo || !toMResNo) {
                    comm.warn_alert(comm.lang('coDeclaration').firstFillCityAndSpread);
                    return;
                }
                var mRegex = /^(([1-9]\d){1}|(\d[1-9]){1})([0]{9})$/;
                if (mRegex.test(zzdqyglh)||mRegex.test(spreadEntResNo))return;
                //申报单位和被申报服务公司是属于同一个管理公司
                if (toMResNo.substring(0, 2) == spreadEntResNo.substring(0, 2)) {
                    fwgssb.findIncrement(spreadEntResNo, zzdqyglh || spreadEntResNo, true);
                } else {
                    if (zzdqyglh) {
                        fwgssb.findIncrement(toMResNo, zzdqyglh, true);
                    } else {
                        var message = comm.lang("coDeclaration").resnoFormError;
                        message = message.replace('serResNo', toMResNo);
                        comm.warn_alert(message, 600);
                    }
                }
            });

            //处理返回按钮
            if ($('#050100000000_subNav_050101000000').hasClass('s_hover')) {
                $(qyxtzcxx_back).hide();
            } else {
                $('#qyxtzcxx_back').show();
            }
            $('#qyxtzcxx_back').click(function () {
                $('#050100000000_subNav_050103000000').click();
            });

            //保存
            $('#qyxtzcxx_save').click(function () {
                fwgssb.saveRegInfoStepOne(false);
            });

            //申报单位互生号失去焦点事件
            $('#spreadEntResNo_refresh').click(function () {
                var serRegex = /^(([1-9]\d){1}|(\d[1-9]){1})([1-9]\d{2}|\d{2}[1-9]|\d[1-9]\d)([0]{6})$/;
                var platRegex = /^([0]{8})(([1-9]\d{2})|(\d[1-9]\d)|(\d{2}[1-9]))$/;
                var spreadEntResNo = $.trim($("#spreadEntResNo").val());//申报公司互生号
                var toMResNo = $.trim($('#toMResNo').val());
                if (!spreadEntResNo || !toMResNo) {
                    comm.warn_alert(comm.lang('coDeclaration').firstFillCityAndSpread);
                    return;
                }
                $("input[name='increment']").attr('checked', false);
                if (serRegex.test(spreadEntResNo) || platRegex.test(spreadEntResNo)) {
                    fwgssb.findEntInfo(spreadEntResNo);
                } else {
                    comm.warn_alert(comm.lang('coDeclaration').rightSpreadNo);
                    return;
                }
                if (serRegex.test(spreadEntResNo)) {
                    //申报公司和被申报服务公司是属于同一个管理公司
                    if (toMResNo.substring(0, 2) == spreadEntResNo.substring(0, 2)) {
                        fwgssb.findIncrement(spreadEntResNo, spreadEntResNo, false);
                    }
                }
            });

            //下一步
            $('#qyxtzcxx_next').click(function () {
                fwgssb.saveRegInfoStepOne(true);
            });

            //设置所属国家
            cacheUtil.findCacheSystemInfo(function (sysRes) {
                $('#countryNo').val(sysRes.countryNo);
                $('#countryText').html(sysRes.countryName);
                if (data) {
                    if (data.toBusinessType || data.toBusinessType == 0) {
                        $("#toBusinessType_" + data.toBusinessType).attr("checked", true);
                    }
                    $("#spreadEntResNo").val(comm.removeNull(data.spreadEntResNo));
                    fwgssb.initPCList(data.provinceNo, data.cityNo);
                    fwgssb.findManageEntAndQuota();
                    fwgssb.findEntInfo(data.spreadEntResNo);
                } else {
                    fwgssb.initPCList(null, null);
                }
            });

            $("#zzdqyglh").focus(function () {
                $("#zzdqyglh").attr("title", "如因业务关系需填写下属企业管理号，须填写完并刷新增值点数据后再选择增值点，否则不需输入直接选择增值点则可。").tooltip({
                    position: {
                        my: "left top+33",
                        at: "left top"
                    }
                }).tooltip("open");
            });
        },
        /**
         * 依据互生号查询企业信息
         * @param spreadEntResNo 申报单位互生号
         */
        findEntInfo: function (spreadEntResNo) {
            if (spreadEntResNo && spreadEntResNo.length == 11) {
                dataModoule.findEntInfo({spreadEntResNo: spreadEntResNo}, function (res) {
                    $("#spreadEntCustId").val(comm.removeNull(res.data.entCustId));
                    $("#spreadEntCustName").val(comm.removeNull(res.data.entName));
                });
            }
        },
        /**
         * 初始化数据
         */
        initData: function () {
            var applyId = fwgssb.getApplyId();
            if (applyId == null) {
                fwgssb.initForm(null);
            } else {
                dataModoule.findDeclareByApplyId({applyId: applyId}, function (res) {
                    fwgssb.initForm(res.data);
                    if (res.data.toPnodeResNo) {
                        $("#zzdqyglh").val(res.data.toPnodeResNo);
                        var score = comm.removeNull(res.data.toInodeResNo);//1增值分配点
                        var chooseId = "increment-1" + ((res.data.toInodeLorR == 0) ? "-l" : "-r");
                        if (score.indexOf("R") != -1) {//3增值分配点
                            chooseId = "increment-3" + ((res.data.toInodeLorR == 0) ? "-l" : "-r");
                        } else if (score.indexOf("L") != -1) {//2增值分配点
                            chooseId = "increment-2" + ((res.data.toInodeLorR == 0) ? "-l" : "-r");
                        }
                        var spreadEntResNo = res.data.spreadEntResNo;
                        var toMResNo = res.data.toMResNo;
                        var toPnodeResNo = res.data.toPnodeResNo;
                        var serRegex = /^(([1-9]\d){1}|(\d[1-9]){1})([1-9]\d{2}|\d{2}[1-9]|\d[1-9]\d)([0]{6})$/;
                        if (serRegex.test(spreadEntResNo) && toMResNo.substring(0, 2) == spreadEntResNo.substring(0, 2)) {
                            //申报公司和被申报服务公司是属于同一个管理公司
                            fwgssb.findIncrement(spreadEntResNo, toPnodeResNo, false, chooseId);
                        } else if (toMResNo != toPnodeResNo) {
                            fwgssb.findIncrement(toMResNo, toPnodeResNo, false, chooseId);
                        }
                    }
                });
            }
        },
        /**
         * 初始化地区下拉
         * @param provCode 省份代码
         * @param cityCode 城市代码
         */
        initPCList: function (provCode, cityCode) {
            //初始化省份
            cacheUtil.findCacheSystemInfo(function (sysRes) {
                cacheUtil.findCacheProvinceByParent(sysRes.countryNo, function (provArray) {
                    comm.initProvSelect('#province_slt', provArray, 70, provCode).change(function (e) {
                        cacheUtil.findCacheCityByParent(sysRes.countryNo, $(this).attr('optionValue'), function (cityArray) {
                            var cSelect = comm.initCitySelect('#city_slt', cityArray, 70, "", {name: '', value: ''});
                            fwgssb.cityChange(cSelect);
                        });
                    });
                });
            });
            //初始化城市
            if (provCode) {
                cacheUtil.findCacheSystemInfo(function (sysRes) {
                    cacheUtil.findCacheCityByParent(sysRes.countryNo, provCode, function (cityArray) {
                        var cSelect = comm.initCitySelect('#city_slt', cityArray, 70, cityCode, {name: '', value: ''});
                        fwgssb.cityChange(cSelect);
                    });
                });
            }
        },
        /**
         * 切换城市
         */
        cityChange: function (cSelect) {
            cSelect.change(function (e) {
                fwgssb.setHtmlData("", "", "", "");
                fwgssb.findManageEntAndQuota();
            });
        },
        /**
         * 依据国家、省份、城市获取服管理公司信息
         * @param selValue 默认值
         */
        findManageEntAndQuota: function () {
            var countryNo = $("#countryNo").val();
            var provinceNo = $("#province_slt").attr('optionValue');
            var cityNo = $("#city_slt").attr('optionValue');
            if (comm.removeNull(countryNo) == "" || comm.removeNull(provinceNo) == "" || comm.removeNull(cityNo) == "") {
                return;
            }
            var params = {};
            params.countryNo = countryNo;
            params.provinceNo = provinceNo;
            params.cityNo = cityNo;
            dataModoule.findManageEntAndQuota(params, function (res) {
                if (res.data) {
                    var entName = (res.data.manageEnt) ? res.data.manageEnt.entName : "";//管理公司名称
                    var entResNo = (res.data.manageEnt) ? res.data.manageEnt.entResNo : "";//管理公司互生号
                    fwgssb.setHtmlData(entResNo, res.data.quota, entName);
                }
            });
        },
        /**
         * 设置管理公司信息和服务公司配额数
         * @param toMResNo 管理公司互生号
         * @param availQuota 服务公司可用配额
         * @param spreadEntName 所属服务公司名称
         */
        setHtmlData: function (toMResNo, availQuota, spreadEntName, spreadEntCustId) {
            $("#toMResNo").val(comm.removeNull(toMResNo));
            $("#availQuota").val(comm.removeNull(availQuota));
            $("#spreadEntName").val(comm.removeNull(spreadEntName));

            var serRegex = /^(([1-9]\d){1}|(\d[1-9]){1})([1-9]\d{2}|\d{2}[1-9]|\d[1-9]\d)([0]{6})$/;
            var spreadEntResNo = $.trim($("#spreadEntResNo").val());//申报公司互生号
            if (!spreadEntResNo || !toMResNo) return;
            $("input[name='increment']").attr('checked', false);
            if (serRegex.test(spreadEntResNo)) {
                //申报公司和被申报服务公司是属于同一个管理公司
                if (toMResNo.substring(0, 2) == spreadEntResNo.substring(0, 2)) {
                    fwgssb.findIncrement(spreadEntResNo, spreadEntResNo, false);
                }
                $('#zzdqyglh').val('');
                $("#toPnodeResNo").val('');
                $("#toPnodeCustId").val('');
            }
        },
        /**
         * 查询消费增值信息
         * @param spreadEntResNo 企业互生号
         * @param subResNo 企业互生号
         * @param isAlert 是否显示提示
         * @param chooseId 选中哪一个单选框
         */
        findIncrement: function (spreadEntResNo, subResNo, isAlert, chooseId) {
            if (!spreadEntResNo) {
                comm.warn_alert(comm.lang("coDeclaration")[36066]);
                return;
            }
            $("input[name='increment']").attr('checked', false);
            dataModoule.findIncrement({spreadEntResNo: spreadEntResNo, subResNo: subResNo}, function (res) {
                if (res.data) {
                    if (!res.data.incrementMap || $.isEmptyObject(res.data.incrementMap) || res.data.subRelation == false) {
                        var message = comm.lang("coDeclaration").resnoFormError;
                        message = message.replace('serResNo', spreadEntResNo);
                        comm.warn_alert(message, 600);
                    } else {
                        fwgssb.fillTable(res.data.incrementMap);
                        if (isAlert) {
                            comm.alert({
                                content: comm.lang("coDeclaration").refSuccess, callOk: function () {
                                }
                            });
                        }
                        if (chooseId) {
                            $("#" + chooseId).attr("checked", true);
                        }
                    }
                } else {
                    if (isAlert) {
                        comm.error_alert(comm.lang("coDeclaration").refFailed);
                    }
                }
            });
        },
        /**
         * 填充增值信息
         */
        fillTable: function (data) {
            if (!data) {
                return;
            }
            var size = 0;
            for (var k in data) {
                size++;
            }
            if (size == 3) {
                for (var k in data) {
                    if (k.indexOf("R") > 0) {
                        $("#increment-3-lP").html(data[k].lP || data[k].lp);
                        $("#increment-3-lCount").html(data[k].lCount || data[k].lcount);
                        $("#increment-3-rP").html(data[k].rP || data[k].rp);
                        $("#increment-3-rCount").html(data[k].rCount || data[k].rcount);
                    } else if (k.indexOf("L") > 0) {
                        $("#increment-2-lP").html(data[k].lP || data[k].lp);
                        $("#increment-2-lCount").html(data[k].lCount || data[k].lcount);
                        $("#increment-2-rP").html(data[k].rP || data[k].rp);
                        $("#increment-2-rCount").html(data[k].rCount || data[k].rcount);
                    } else {
                        $("#increment-1-lP").html(data[k].lP || data[k].lp);
                        $("#increment-1-lCount").html(data[k].lCount || data[k].lcount);
                        $("#increment-1-rP").html(data[k].rP || data[k].rp);
                        $("#increment-1-rCount").html(data[k].rCount || data[k].rcount);
                    }
                    $("#toPnodeResNo").val(data[k].resNo);
                    $("#toPnodeCustId").val(data[k].custId);
                }
                $("#increment-1-table").hide();
                $("#increment-3-table").show();
            } else {
                for (var k in data) {
                    $("#increment-lP").html(data[k].lP || data[k].lp);
                    $("#increment-lCount").html(data[k].lCount || data[k].lcount);
                    $("#increment-rP").html(data[k].rP || data[k].rp);
                    $("#increment-rCount").html(data[k].rCount || data[k].rcount);
                    $("#toPnodeResNo").val(data[k].resNo);
                    $("#toPnodeCustId").val(data[k].custId);
                }
                $("#increment-1-table").show();
                $("#increment-3-table").hide();
            }
            $("#comboxDiv").find("input:radio").attr("disabled", false);
        },
        /**
         * 获取积分增值点设置
         * @return array array[0]表示：被申报增值节点父节点客户号array[1]表示：被申报增值节点父节点互生号array[2]表示：被申报选择增值节点array[3]表示：被申报选择增值节点对应区域
         */
        getPnode: function () {
            var val = $('input[name="increment"]:checked').val();
            if (val) {
                var toPnodeCustId = comm.removeNull($("#toPnodeCustId").val()).replace("R", "").replace("L", "");
                var toPnodeResNo = $("#toPnodeResNo").val();
                if(toPnodeCustId&&toPnodeResNo){
                    if (val == "1-L") {//选择1增值分配点左增值区
                        return [toPnodeCustId, toPnodeResNo, toPnodeCustId, 0];
                    } else if (val == "1-R") {//选择1增值分配点右增值区
                        return [toPnodeCustId, toPnodeResNo, toPnodeCustId, 1];
                    } else if (val == "2-L") {//选择2增值分配点左增值区
                        return [toPnodeCustId, toPnodeResNo, toPnodeCustId + "L", 0];
                    } else if (val == "2-R") {//选择2增值分配点右增值区
                        return [toPnodeCustId, toPnodeResNo, toPnodeCustId + "L", 1];
                    } else if (val == "3-L") {//选择3增值分配点左增值区
                        return [toPnodeCustId, toPnodeResNo, toPnodeCustId + "R", 0];
                    } else if (val == "3-R") {//选择3增值分配点右增值区
                        return [toPnodeCustId, toPnodeResNo, toPnodeCustId + "R", 1];
                    }
                }
            }
            return null;
        },
        /**
         * 表单校验
         */
        validateData: function () {
            var validate = $("#qyxtzcxx_fwgs_form").validate({
                rules: {
                    toCustType: {
                        required: true
                    },
                    toEntCustName: {
                        required: true,
                        // custname:true,
                        rangelength: [2, 128]
                    },
                    entResNo: {
                        required: true
                    },
                    province_slt: {
                        required: true
                    },
                    city_slt: {
                        required: true
                    },
                    spreadEntResNo: {
                        required: true,
                        spreadEntResNoCheck: true
                    },
                    toBusinessType: {
                        required: true
                    }
                },
                messages: {
                    toCustType: {
                        required: comm.lang("coDeclaration")[36049]
                    },
                    toEntCustName: {
                        required: comm.lang("coDeclaration")[36051],
                        rangelength: comm.lang("coDeclaration")[36052]
                    },
                    entResNo: {
                        required: comm.lang("coDeclaration")[36054]
                    },
                    province_slt: {
                        required: comm.lang("coDeclaration")[36055]
                    },
                    city_slt: {
                        required: comm.lang("coDeclaration")[36056]
                    },
                    spreadEntResNo: {
                        required: comm.lang("coDeclaration")[36066]
                    },
                    toBusinessType: {
                        required: comm.lang("common")[22057]
                    }
                },
                errorPlacement: function (error, element) {
                    if ($(element).is(":text")) {
                        $(element).attr("title", $(error).text()).tooltip({
                            tooltipClass: "ui-tooltip-error",
                            destroyFlag: true,
                            destroyTime: 1000,
                            position: {
                                my: "left top+30",
                                at: "left top"
                            }
                        }).tooltip("open");
                        $(".ui-tooltip").css("max-width", "230px");
                    } else {
                        $(element.parent()).attr("title", $(error).text()).tooltip({
                            tooltipClass: "ui-tooltip-error",
                            destroyFlag: true,
                            destroyTime: 1000,
                            position: {
                                my: "left top+30",
                                at: "left top"
                            }
                        }).tooltip("open");
                    }
                },
                success: function (element) {
                    $(element).tooltip();
                    $(element).tooltip("destroy");
                }
            });
            return validate;
        },
        /**
         * 保存注册信息第一步
         * @param autoNext 是否自动进行下一步
         */
        saveRegInfoStepOne: function (autoNext) {
            var availQuota = $("#availQuota").val();
            if (parseInt(availQuota) == 0) {
                comm.error_alert(comm.lang("coDeclaration").nofwPointNo);
                return;
            }
            if (!fwgssb.validateData().form()) {
                return;
            }
            var array = fwgssb.getPnode();//获取积分增值点设置
            if (array == null) {
                var spreadEntResNo = $("#spreadEntResNo").val();
                var toMResNo = $.trim($('#toMResNo').val());
                if (!spreadEntResNo || !toMResNo) {
                    comm.warn_alert(comm.lang('coDeclaration').firstFillCityAndSpread);
                    return;
                }
                var serRegex = /^(([1-9]\d){1}|(\d[1-9]){1})([1-9]\d{2}|\d{2}[1-9]|\d[1-9]\d)([0]{6})$/;
                if (serRegex.test(spreadEntResNo) && toMResNo.substring(0, 2) == spreadEntResNo.substring(0, 2)) {
                    comm.warn_alert(comm.lang("coDeclaration").nosetincrement);
                    return;
                }
                fwgssb.saveRegInfo(autoNext, [null, null, null, null]);
            } else {
                fwgssb.saveRegInfo(autoNext, array);
            }
        },
        /**
         * 保存注册信息
         * @param autoNext 是否自动跳到下一步
         * @param array 积分增值信息
         */
        saveRegInfo: function (autoNext, array) {
            var params = {};
            params.applyId = fwgssb.getApplyId();//申请编号
            params.toEntCustName = $("#toEntCustName").val();//被申报企业名称
            params.toEntResNo = "";//被申报企业启用资源号,成员企业和托管企业时必填,服务公司不填
            params.countryNo = $("#countryNo").val();//所属国家
            params.provinceNo = $("#province_slt").attr('optionValue');//所属省份
            params.cityNo = $("#city_slt").attr('optionValue');//所属城市
            params.toCustType = 4;//被申报企业客户类型
            params.toBusinessType = comm.removeNull($('input[name="toBusinessType"]:checked').val());//经营类型
            params.toBuyResRange = "";//启用资源类型，托管企业、成员企业必填
            params.toPnodeCustId = array[0];//被申报增值节点父节点客户号
            params.toPnodeResNo = array[1];//被申报增值节点父节点互生号
            params.toInodeResNo = array[2];//被申报选择增值节点
            params.toInodeLorR = array[3];//被申报选择增值节点对应区域
            params.toMResNo = $("#toMResNo").val();//所属管理公司互生号
            params.spreadEntResNo = $("#spreadEntResNo").val();//推广企业企业客户号
            params.spreadEntCustId = $("#spreadEntCustId").val();//推广企业企业客户号
            params.spreadEntCustName = $("#spreadEntCustName").val();//推广企业企业名称
            dataModoule.saveDeclare(params, function (res) {
                fwgssb.saveStep(1, res.data);
                if (autoNext) {
                    $('#qygsdjxx').click();
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
            var entDeclare = comm.getCache("coDeclaration", "entDeclare");
            if ((entDeclare.curStep) <= curStep) {
                entDeclare.curStep = curStep;
            }
            entDeclare.applyId = applyId;
            entDeclare.custType = 4;
        }
    };
    return fwgssb;
}); 

