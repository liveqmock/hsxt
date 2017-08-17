define(['text!coDeclarationTpl/kqxtyw/qyxtzcxx_fwgs.html',
    'text!coDeclarationTpl/kqxtyw/confirm_dialog.html',
    'coDeclarationDat/qyxz/qyxtzcxx_fwgs',
    'coDeclarationLan'], function (qyxtzcxx_fwgsTpl, confirm_dialogTpl, dataModoule) {
    var kqxtyw_fwgs = {
        applyId: null,//申请编号
        odata: null,//修改前的数据
        pdata: null,//原始数据
        toPnodeCustId: "",//修改前被申报增值节点父节点客户号
        toPnodeResNo: "",//修改前被申报增值节点父节点资源
        toInodeResNo: "",//修改前被申报选择增值节点
        toInodeLorR: "",//修改前被申报选择增值节点对应区域
        showPage: function (data) {
            odata = data;
            pdata = data;
            kqxtyw_fwgs.applyId = data.applyId;
            kqxtyw_fwgs.initData(data);
        },
        /**
         * 获取申请编号
         */
        getApplyId: function () {
            return kqxtyw_fwgs.applyId;
        },
        /**
         * 初始化表单
         */
        initForm: function (data) {
            $('#qyxtzcxx_xgTpl').html(_.template(qyxtzcxx_fwgsTpl, data));

            //隐藏积分增值设置
            // $("#comboxDiv").hide();

            /**申报类型*/
            $("#toCustType").val(comm.lang("coDeclaration").fwEntDesc);

            //刷新增值点数据
            $('#refincrement').click(function () {
                var spreadEntResNo = $("#spreadEntResNo").val();
                var zzdqyglh = $.trim($("#zzdqyglh").val());
                var toMResNo = $.trim($('#toMResNo').val());
                var mRegex = /^(([1-9]\d){1}|(\d[1-9]){1})([0]{9})$/;
                if (mRegex.test(zzdqyglh))return;
                //申报单位和被申报服务公司是属于同一个管理公司
                if (toMResNo.substring(0, 2) == spreadEntResNo.substring(0, 2)) {
                    kqxtyw_fwgs.findIncrement(spreadEntResNo, zzdqyglh || spreadEntResNo, true);
                } else {
                    if (zzdqyglh) {
                        kqxtyw_fwgs.findIncrement(toMResNo, zzdqyglh, true);
                    } else {
                        var message = comm.lang("coDeclaration").resnoFormError;
                        message = message.replace('serResNo', toMResNo);
                        comm.warn_alert(message, 600);
                    }
                }
            });

            //保存
            $('#qyxtzcxx_save').click(function () {
                kqxtyw_fwgs.saveRegInfoStepOne();
            });

            //申报单位互生号失去焦点事件
            comm.delCache("coDeclaration", "cacheSpreadEntResNo");
            $('#spreadEntResNo').blur(function () {
                var spreadEntResNo = $("#spreadEntResNo").val();
                var cacheSpreadEntResNo = comm.getCache("coDeclaration", "cacheSpreadEntResNo");
                if (cacheSpreadEntResNo == null || cacheSpreadEntResNo != spreadEntResNo) {
                    kqxtyw_fwgs.findEntInfo(spreadEntResNo);
                }
            });

            //取消
            $('#qyxtzcxx_canc').click(function () {
                $('#qyxtzcxxTpl').removeClass('none');
                $('#qyxtzcxx_xgTpl').addClass('none');
                //如果数据有变动需要重新加载展示界面数据
                if (odata != pdata) {
                    require(["coDeclarationSrc/kqxtyw/qyxtzcxx"], function (qyxtzcxx) {
                        qyxtzcxx.initForm(odata);
                    });
                }
            });

            //获取地区信息
            cacheUtil.syncGetRegionByCode(data.countryNo, data.provinceNo, data.cityNo, "", function (resdata) {
                $('#countryNo').val(data.countryNo);
                $("#placeName").html(resdata);
            });

            if (data.toBusinessType||data.toBusinessType==0) {
                $("#toBusinessType_" + data.toBusinessType).attr("checked", true);
            }
            $("#toEntEnName").val(comm.removeNull(data.toEntEnName));
            $("#spreadEntResNo").val(comm.removeNull(data.spreadEntResNo));
            kqxtyw_fwgs.findManageEntAndQuota(data.countryNo, data.provinceNo, data.cityNo);
            kqxtyw_fwgs.findEntInfo(comm.removeNull(data.spreadEntResNo));
        },
        /**
         * 依据互生号查询企业信息
         * @param spreadEntResNo 申报单位互生号
         */
        findEntInfo: function (spreadEntResNo) {
            if (spreadEntResNo && spreadEntResNo.length == 11) {
                comm.delCache("coDeclaration", "cacheSpreadEntResNo");
                comm.setCache("coDeclaration", "cacheSpreadEntResNo", spreadEntResNo);
                dataModoule.findEntInfo({spreadEntResNo: spreadEntResNo}, function (res) {
                    $("#spreadEntCustId").val(comm.removeNull(res.data.entCustId));
                    $("#spreadEntCustName").val(comm.removeNull(res.data.entName));
                });
            }
        },
        /**
         * 初始化数据
         */
        initData: function (data) {
            kqxtyw_fwgs.initForm(data);
            if (data.toPnodeResNo) {
                kqxtyw_fwgs.toPnodeCustId = comm.removeNull(data.toPnodeCustId);//修改前被申报增值节点父节点客户号
                kqxtyw_fwgs.toPnodeResNo = comm.removeNull(data.toPnodeResNo);//修改前被申报增值节点父节点资源
                kqxtyw_fwgs.toInodeResNo = comm.removeNull(data.toInodeResNo);//修改前被申报选择增值节点
                kqxtyw_fwgs.toInodeLorR = comm.removeNull(data.toInodeLorR);//修改前被申报选择增值节点对应区域
                $("#zzdqyglh").val(data.toPnodeResNo);
                var score = comm.removeNull(data.toInodeResNo);//1增值分配点
                var chooseId = "increment-1" + ((data.toInodeLorR == 0) ? "-l" : "-r");
                if (score.indexOf("R") != -1) {//3增值分配点
                    chooseId = "increment-3" + ((data.toInodeLorR == 0) ? "-l" : "-r");
                } else if (score.indexOf("L") != -1) {//2增值分配点
                    chooseId = "increment-2" + ((data.toInodeLorR == 0) ? "-l" : "-r");
                }
                var spreadEntResNo = data.spreadEntResNo;
                var toMResNo = data.toMResNo;
                var toPnodeResNo = data.toPnodeResNo;
                var serRegex = /^(([1-9]\d){1}|(\d[1-9]){1})([1-9]\d{2}|\d{2}[1-9]|\d[1-9]\d)([0]{6})$/;
                if (serRegex.test(spreadEntResNo) && toMResNo.substring(0, 2) == spreadEntResNo.substring(0, 2)) {
                    //申报公司和被申报服务公司是属于同一个管理公司
                    kqxtyw_fwgs.findIncrement(spreadEntResNo, toPnodeResNo, false, chooseId);
                } else if (toMResNo != toPnodeResNo) {
                    kqxtyw_fwgs.findIncrement(toMResNo, toPnodeResNo, false, chooseId);
                }
            }
        },
        /**
         * 依据国家、省份、城市获取服管理公司信息
         * @param countryNo 国家代码
         * @param provinceNo 省份代码
         * @param cityNo 城市代码
         */
        findManageEntAndQuota: function (countryNo, provinceNo, cityNo) {
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
                    kqxtyw_fwgs.setHtmlData(entResNo, res.data.quota, entName);
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
        },
        /**
         * 查询消费增值信息
         * @param spreadEntResNo 企业互生号
         * @param subResNo 企业互生号
         * @param isAlert 是否显示提示
         * @param chooseId 选中哪一个单选框
         */
        findIncrement: function (spreadEntResNo, subResNo, isAlert, chooseId) {
            if (comm.removeNull(subResNo) == "") {
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
                        kqxtyw_fwgs.fillTable(res.data.incrementMap);
                        if (chooseId) {
                            $("#" + chooseId).attr("checked", true);
                        }
                        if (isAlert) {
                            comm.alert({ content: comm.lang("coDeclaration").refSuccess, callOk: function () {}});
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
        },
        /**
         * 获取积分增值点数
         */
        getIncrementScore: function (score) {
            if (comm.removeNull(score) == "") {
                return "";
            }
            if (score.indexOf("R") != -1) {//3增值分配点
                return "3";
            } else if (score.indexOf("L") != -1) {//2增值分配点
                return "2";
            } else {//1增值分配点
                return "1";
            }
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
                if(toPnodeCustId&&toPnodeResNo) {
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
                    toEntEnName: {
                        required: false,
                        rangelength: [2, 128]
                    },
                    entResNo: {
                        required: true
                    },
                    spreadEntResNo: {
                        required: true,
                        rangelength: [11, 11]
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
                    toEntEnName: {
                        rangelength: comm.lang("coDeclaration")[36053]
                    },
                    entResNo: {
                        required: comm.lang("coDeclaration")[36054]
                    },
                    spreadEntResNo: {
                        required: comm.lang("coDeclaration")[36066],
                        rangelength: comm.lang("coDeclaration")[36060]
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
         * 表单校验
         */
        validateViewMarkData: function () {
            return $("#confirm_dialog_form").validate({
                rules: {
                    viewMark: {
                        rangelength: [0, 300]
                    },
                    doubleUserName: {
                        required: true
                    },
                    doublePassword: {
                        required: true
                    }
                },
                messages: {
                    viewMark: {
                        rangelength: comm.lang("coDeclaration")[36006]
                    },
                    doubleUserName: {
                        required: comm.lang("coDeclaration")[36047]
                    },
                    doublePassword: {
                        required: comm.lang("coDeclaration")[36048]
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
        },
        /**
         * 保存注册信息
         */
        saveRegInfoStepOne: function () {
            var availQuota = $("#availQuota").val();
            if (parseInt(availQuota) == 0) {
                comm.error_alert(comm.lang("coDeclaration")[32691]);
                return;
            }
            if (!this.validateData().form()) {
                return;
            }
            var kqxtyw_fwgs = this;
            var array = this.getPnode();//获取积分增值点设置
            if (array == null) {
                var spreadEntResNo = $("#spreadEntResNo").val();
                var toMResNo = $.trim($('#toMResNo').val());
                var serRegex = /^(([1-9]\d){1}|(\d[1-9]){1})([1-9]\d{2}|\d{2}[1-9]|\d[1-9]\d)([0]{6})$/;
                if (serRegex.test(spreadEntResNo) && toMResNo.substring(0, 2) == spreadEntResNo.substring(0, 2)) {
                    comm.warn_alert(comm.lang("coDeclaration").nosetincrement);
                    return;
                }
                kqxtyw_fwgs.saveRegInfo([null, null, null, null]);
            } else {
                kqxtyw_fwgs.saveRegInfo(array);
            }
        },
        /**
         * 保存注册信息
         * @param array 积分增值信息
         */
        saveRegInfo: function (array) {
            var ckArray = [];//存入需要检查是否改变的对象
            $(".isChange").each(function () {
                var desc = $(this).prev()[0].innerText;
                ckArray.push({name: this.name, value: this.value, desc: desc});
            });
            var ndata = comm.cloneJSON(odata);
            var trs = "";
            var chg = {};
            for(var key = 0;key < ckArray.length; key++){
                if (comm.removeNull(odata[ckArray[key].name]) != ckArray[key].value) {
                    trs += "<tr><td class=\"view_item\">" + ckArray[key].desc + "</td><td class=\"view_text\">" + comm.removeNull(odata[ckArray[key].name]) + "</td><td class=\"view_text\">" + ckArray[key].value + "</td></tr>";
                    chg[ckArray[key].name] = {"old": comm.removeNull(odata[ckArray[key].name]), "new": ckArray[key].value};
                    ndata[ckArray[key].name] = ckArray[key].value;
                }
            }

            //经营类型
            var newToBusinessType = comm.removeNull($('input[name="toBusinessType"]:checked').val());
            if (comm.removeNull(odata.toBusinessType) != newToBusinessType) {
                var newText = comm.removeNull(comm.getNameByEnumId(newToBusinessType, comm.lang("common").toBusinessTypes));
                var oldText = comm.removeNull(comm.getNameByEnumId(odata.toBusinessType, comm.lang("common").toBusinessTypes));
                trs += "<tr><td class=\"view_item\">经营类型</td><td class=\"view_text\">" + oldText + "</td><td class=\"view_text\">" + newText + "</td></tr>";
                chg.toBusinessType = {"old": comm.removeNull(odata.toBusinessType), "new": newToBusinessType};
                ndata.toBusinessType = newToBusinessType;
            }

            //增值系统位置企业互生号
            var oldPnodeResNo = comm.removeNull(kqxtyw_fwgs.toPnodeResNo);//修改前增值系统位置企业互生号
            var newPnodeResNo = comm.removeNull(array[1]);//修改后增值系统位置企业互生号
            if (oldPnodeResNo != newPnodeResNo) {
                trs += "<tr><td class=\"view_item\">增值系统位置企业互生号</td><td class=\"view_text\">" + oldPnodeResNo + "</td><td class=\"view_text\">" + newPnodeResNo + "</td></tr>";
                chg.toPnodeResNo = {"old": oldPnodeResNo, "new": newPnodeResNo};
                ndata.toPnodeResNo = newPnodeResNo;
            }

            //增值分配点
            var oldInodeResNo = comm.removeNull(kqxtyw_fwgs.toInodeResNo);//修改前增值分配点
            var newInodeResNo = comm.removeNull(array[2]);//修改后增值分配点
            if (oldInodeResNo != newInodeResNo) {
                var oldInodeResNoText = kqxtyw_fwgs.getIncrementScore(oldInodeResNo) + "增值分配点";//修改前增值分配点
                var newInodeResNoText = kqxtyw_fwgs.getIncrementScore(newInodeResNo) + "增值分配点";//修改后增值分配点
                trs += "<tr><td class=\"view_item\">增值分配点</td><td class=\"view_text\">" + oldInodeResNoText + "</td><td class=\"view_text\">" + newInodeResNoText + "</td></tr>";
                chg.toInodeResNo = {"old": kqxtyw_fwgs.toInodeResNo, "new": newInodeResNo};
                ndata.toInodeResNo = newInodeResNo;
            }

            //增值区
            var oldInodeLorR = comm.removeNull(kqxtyw_fwgs.toInodeLorR);//修改前增值分配点
            var newInodeLorR = comm.removeNull(array[3]);//修改后增值分配点
            if (oldInodeLorR != newInodeLorR) {
                var oldInodeLorRText = "";//修改前增值分配点
                var newInodeLorRText = "";//修改后增值分配点
                if (oldInodeLorR == 0 || oldInodeLorR == 1) {
                    oldInodeLorRText = (oldInodeLorR == 0) ? "左增值区" : "右增值区";
                }
                if (newInodeLorR == 0 || newInodeLorR == 1) {
                    newInodeLorRText = (newInodeLorR == 0) ? "左增值区" : "右增值区";
                }
                trs += "<tr><td class=\"view_item\">增值区</td><td class=\"view_text\">" + oldInodeLorRText + "</td><td class=\"view_text\">" + newInodeLorRText + "</td></tr>";
                chg.toInodeLorR = {"old": oldInodeLorR, "new": newInodeLorR};
                ndata.toInodeLorR = newInodeLorR;
            }

            //被申报增值节点父节点客户号
            var oldPnodeCustId = comm.removeNull(kqxtyw_fwgs.toPnodeCustId);//修改前被申报增值节点父节点客户号
            var newPnodeCustId = comm.removeNull(array[0]);//修改后被申报增值节点父节点客户号
            if (oldPnodeCustId != newPnodeCustId) {
                chg.toPnodeCustId = {"old": oldPnodeCustId, "new": newPnodeCustId};
                ndata.toPnodeCustId = newInodeLorR;
            }

            if (!trs) {
                comm.warn_alert(comm.lang("coDeclaration").noUpdate);
                return;
            }
            //提交
            $('#qyxtzcxx_tj_dialog > p').html(_.template(confirm_dialogTpl));
            $('#copTable tr:eq(1)').before(trs);
            kqxtyw_fwgs.initVerificationMode();
            $('#qyxtzcxx_tj_dialog').dialog({
                width: 800,
                buttons: {
                    '确定': function () {
                        if (!kqxtyw_fwgs.validateViewMarkData().form()) {
                            return;
                        }
                        //验证双签
                        comm.verifyDoublePwd($("#doubleUserName").val(), $("#doublePassword").val(), 1, function (verifyRes) {
                            ndata.verifyDouble = "true";
                            ndata.dblOptCustId = verifyRes.data;
                            ndata.optRemark = $("#viewMark").val();
                            ndata.changeContent = JSON.stringify(chg);
                            dataModoule.saveDeclare(ndata, function (res) {
                                odata = ndata;//保存成功后将新数据赋值给修改前数据
                                kqxtyw_fwgs.toPnodeCustId = array[0];//修改后被申报增值节点父节点客户号
                                kqxtyw_fwgs.toPnodeResNo = array[1];//修改后被申报增值节点父节点资源
                                kqxtyw_fwgs.toInodeResNo = array[2];//修改后被申报选择增值节点
                                kqxtyw_fwgs.toInodeLorR = array[3];//修改后被申报选择增值节点对应区域
                                comm.alert({
                                    content: comm.lang("coDeclaration")[22000], callOk: function () {
                                        $("#qyxtzcxx_tj_dialog").dialog('destroy');
                                    }
                                });
                            });
                        });
                    },
                    '取消': function () {
                        $(this).dialog('destroy');
                    }
                }
            });
        },
        /**
         * 初始化验证方式
         */
        initVerificationMode: function () {
            comm.initSelect("#verificationMode", comm.lang("coDeclaration").verificationMode, null, '1').change(function (e) {
                var val = $(this).attr('optionValue');
                switch (val) {
                    case '1':
                        $('#fhy_userName, #fhy_password').removeClass('none');
                        $('#verificationMode_prompt').addClass('none');
                        break;
                    case '2':
                        $('#fhy_userName, #fhy_password').addClass('none');
                        $('#verificationMode_prompt').removeClass('none');
                        break;
                    case '3':
                        $('#fhy_userName, #fhy_password').addClass('none');
                        $('#verificationMode_prompt').removeClass('none');
                        break;
                }
            });
        }
    };
    return kqxtyw_fwgs;
}); 

