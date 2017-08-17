define(['text!coDeclarationTpl/sbxxwh/glqysbzzdwh.html',
    'text!coDeclarationTpl/sbxxwh/glqysbzzdwh_xg.html',
    'coDeclarationDat/sbxxwh/glqysbzzdwh',
    'coDeclarationLan'], function (glqysbzzdwhTpl, glqysbzzdwh_xgTpl, dataModoule) {
    var spreadEntResNo = null, toMResNo = null;
    var sbxxwh = {
        toPnodeCustId_: "",//修改前被申报增值节点父节点客户号
        toPnodeResNo_: "",//修改前被申报增值节点父节点资源
        toInodeResNo_: "",//修改前被申报选择增值节点
        toInodeLorR_: "",//修改前被申报选择增值节点对应区域
        showPage: function () {
            this.initForm();
        },
        /**
         * 初始化页面
         */
        initForm: function () {
            $('#busibox').empty().html(_.template(glqysbzzdwhTpl)).append(glqysbzzdwh_xgTpl);
            //时间控件
            comm.initBeginEndTime('#search_startDate', '#search_endDate');

            //初始化下拉框
            comm.initSelect('#search_status', comm.lang("coDeclaration").defendStatusEnum, null, null, {name: '全部', value: ''});

            //绑定查询事件
            $('#queryBtn').click(function () {
                sbxxwh.initData();
            });
            comm.initSelect("#quickDate",comm.lang("common").quickDateEnum);
            $("#quickDate").change(function(){
                comm.quickDateChange($(this).attr("optionvalue"),'#search_startDate', '#search_endDate');
            });
            //刷新增值点数据
            $('#refincrement').click(function () {
                var zzdqyglh = $("#zzdqyglh").val();
                if (comm.removeNull(zzdqyglh).length != 11) {
                    comm.warn_alert(comm.lang("coDeclaration")[36060]);
                    return;
                }
                var mRegex = /^(([1-9]\d){1}|(\d[1-9]){1})([0]{9})$/;
                if (mRegex.test(zzdqyglh))return;
                //申报单位和被申报服务公司是属于同一个管理公司
                if (toMResNo.substring(0, 2) == spreadEntResNo.substring(0, 2)) {
                    sbxxwh.findIncrement(spreadEntResNo, zzdqyglh || spreadEntResNo, true);
                } else {
                    if (zzdqyglh) {
                        sbxxwh.findIncrement(toMResNo, zzdqyglh, true);
                    } else {
                        var message = comm.lang("coDeclaration").resnoFormError;
                        message = message.replace('serResNo', toMResNo);
                        comm.warn_alert(message, 600);
                    }
                }
            });
        },
        /**
         * 初始化数据
         */
        initData: function () {
            if (!comm.queryDateVaild("search_form").form()) {
                return;
            }
            var jsonParam = {
                search_resNo: $("#search_resNo").val(),
                search_entName: $("#search_entName").val(),
                search_startDate: $("#search_startDate").val(),
                search_endDate: $("#search_endDate").val(),
                search_status: $("#search_status").attr('optionValue')
            };
            dataModoule.findIncrementList(jsonParam, this.detail);
        },
        /**
         * 查看动作
         */
        detail: function (record, rowIndex, colIndex, options) {
            if (colIndex == 0) {
                if (!record['entResNo']) {
                    return '';
                }
                return $('<a id="' + record['applyId'] + '" >' + record['entResNo'] + '</a>').click(function (e) {
                    require(['coDeclarationSrc/sbxxwh/sub_tab'], function (tab) {
                        tab.showPage(record['applyId'], record['custType']);
                    });
                });
            }
            if (colIndex == 2) {
                return comm.getNameByEnumId(record['custType'], comm.lang("coDeclaration").toCustTypeEnum);
            }
            if (colIndex == 7) {
                return comm.getNameByEnumId(record['status'], comm.lang("coDeclaration").declarationStatusEnum);
            }
            if(record.joinBM) {
                return $('<a id="' + record['applyId'] + '" >修改增值点</a>').click(function (e) {
                    sbxxwh.xgzzd(record['custType'], record['applyId']);
                });
            }
            return '';
        },
        /**
         * 修改增值点
         * @param type 申报类别
         * @param applyId 申请编号
         */
        xgzzd: function (type, applyId) {
            $('#glqysbzzdwhTpl').addClass('none');
            $('#glqysbzzdwh_xgTpl').removeClass('none');
            comm.liActive_add($('#xgzzd'));

            //根据类型显示
            switch (type) {
                case 4:
                    $('#glqysbzzdwh_nyqyhsh_ul').addClass('none');
                    $("#toBuyResRangeText").html("经营类型");
                    $('#glqysbzzdwh_kype_name').text('服务公司可用配额');
                    $('#glqysbzzdwh_ssmc_name').text('所属管理公司名称');
                    $('#glqysbzzdwh_hsh_name').text('管理公司互生号');
                    break;
                case 2:
                    $("toBuyResRangeText").html("启用资源类型");
                    $('#glqy_sbdwhsh, #glqy_sbdwmc').addClass('none');
                    $('#glqysbzzdwh_kype_name').text('成员企业可用配额');
                    $('#glqysbzzdwh_ssmc_name').text('所属服务公司名称');
                    $('#glqysbzzdwh_hsh_name').text('服务公司互生号');
                    break;
                case 3:
                    $("toBuyResRangeText").html("启用资源类型");
                    $('#glqy_sbdwhsh, #glqy_sbdwmc').addClass('none');
                    $('#glqysbzzdwh_kype_name').text('托管企业可用配额');
                    $('#glqysbzzdwh_ssmc_name').text('所属服务公司名称');
                    $('#glqysbzzdwh_hsh_name').text('服务公司互生号');
                    break;
            }

            dataModoule.findDeclareByApplyId({applyId: applyId}, function (res) {
                var data = res.data;
                $("#toCustType").val(comm.getNameByEnumId(data.toCustType, comm.lang("coDeclaration").toCustTypeEnum));//申报类别
                $("#toEntEnName").val(comm.removeNull(data.toEntEnName));//企业英文名称
                $("#toEntCustName").val(comm.removeNull(data.toEntCustName));//企业中文名称
                $("#toEntCustName_").html(comm.removeNull(data.toEntCustName));//企业中文名称
                $("#old_zzdqyglh").html(comm.removeNull(data.toPnodeResNo));//修改前被申报增值节点父节点互生号
                $("#toEntResNo_").html(comm.removeNull(data.toEntResNo));//拟用企业互生号
                //获取地区信息
                cacheUtil.syncGetRegionByCode(null, data.provinceNo, data.cityNo, "", function (resdata) {
                    $("#placeName").val(resdata);
                });
                if (type == 2 || type == 3) {//成员企业、托管企业
                    $("#toBuyResRange").val(comm.getNameByEnumId(data.toBuyResRange, comm.lang("coDeclaration").allToBuyResRangeEnum));//启用资源类型
                    $("#fwgshsh").val(comm.removeNull(data.spreadEntResNo));//服务公司互生号
                    sbxxwh.findResNoListAndQuota(data.toCustType, data.spreadEntResNo, data.toBuyResRange);
                    if (data.toSelectMode && data.toSelectMode == 1) {
                        $('#rgxp1').attr('checked', true);
                    } else {
                        $('#sxxp1').attr('checked', true);
                    }
                } else {//服务公司
                    $("#toBuyResRange").val(comm.getNameByEnumId(data.toBusinessType, comm.lang("common").toBusinessTypes));//经营类型
                    $("#sbdwhsh").val(comm.removeNull(data.spreadEntResNo));//申报单位互生号
                    sbxxwh.findEntInfo(data.spreadEntResNo);
                    sbxxwh.findManageEntAndQuota(data.countryNo, data.provinceNo, data.cityNo);
                }
                spreadEntResNo = data.spreadEntResNo;
                toMResNo = data.toMResNo;
                //禁用积分增值设置
                $("#comboxDiv").find("input:radio").attr("disabled", true);
                //设置积分增值信息
                if (data.toPnodeResNo) {
                    sbxxwh.toPnodeCustId_ = comm.removeNull(data.toPnodeCustId);//修改前被申报增值节点父节点客户号
                    sbxxwh.toPnodeResNo_ = comm.removeNull(data.toPnodeResNo);//修改前被申报增值节点父节点资源
                    sbxxwh.toInodeResNo_ = comm.removeNull(data.toInodeResNo);//修改前被申报选择增值节点
                    sbxxwh.toInodeLorR_ = comm.removeNull(data.toInodeLorR);//修改前被申报选择增值节点对应区域
                    $("#zzdqyglh").val(comm.removeNull(data.toPnodeResNo));
                    var score = comm.removeNull(data.toInodeResNo);//1增值分配点
                    var chooseId = "increment-1" + ((data.toInodeLorR == 0) ? "-l" : "-r");
                    if (score.indexOf("R") != -1) {//3增值分配点
                        chooseId = "increment-3" + ((data.toInodeLorR == 0) ? "-l" : "-r");
                    } else if (score.indexOf("L") != -1) {//2增值分配点
                        chooseId = "increment-2" + ((data.toInodeLorR == 0) ? "-l" : "-r");
                    }
                    if (toMResNo != data.toPnodeResNo) {
                        sbxxwh.findIncrement(data.spreadEntResNo, data.toPnodeResNo, false, chooseId);
                    }
                }
            });

            $('#tj_btn').click(function () {
                var array = sbxxwh.getPnode();
                if (array == null) {
                    comm.error_alert("未设置积分增值信息");
                    return;
                }
                if (array[0] == sbxxwh.toPnodeCustId_ && array[1] == sbxxwh.toPnodeResNo_ && array[2] == sbxxwh.toInodeResNo_ && array[3] == sbxxwh.toInodeLorR_) {
                    comm.warn_alert("积分增值信息无修改");
                    return;
                }
                if (sbxxwh.toInodeResNo_ != "") {
                    $("#old_toInodeResNo").html(sbxxwh.getIncrementScore(sbxxwh.toInodeResNo_) + "增值分配点");
                }
                if (sbxxwh.toInodeLorR_ == 0 || sbxxwh.toInodeLorR_ == 1) {
                    $("#old_toInodeLorR").html((sbxxwh.toInodeLorR_ == 0) ? "左增值区" : "右增值区");
                }
                $("#new_toInodeLorR").html((array[3] == 0) ? "左增值区" : "右增值区");
                $("#new_toInodeResNo").html(sbxxwh.getIncrementScore(array[2]) + "增值分配点");
                $("#new_zzdqyglh").html(array[1]);
                $("#tj_content").dialog({
                    title: "积分增值点修改确认",
                    width: "800",
                    height: "510",
                    modal: true,
                    buttons: {
                        "确定": function () {
                            //表单验证
                            if (!sbxxwh.validateViewMarkData().form()) {
                                return;
                            }
                            //验证双签
                            comm.verifyDoublePwd($("#doubleUserName").val(), $("#doublePassword").val(), 1, function (verifyRes) {
                                var params = {};
                                params.applyId = applyId;
                                params.dblOptCustId = verifyRes.data;
                                params.toPnodeCustId = array[0];
                                params.toPnodeResNo = array[1];
                                params.toInodeResNo = array[2];
                                params.toInodeLorR = array[3];
                                dataModoule.saveIncrement(params, function (res) {
                                    sbxxwh.toPnodeCustId_ = array[0];//修改后被申报增值节点父节点客户号
                                    sbxxwh.toPnodeResNo_ = array[1];//修改后被申报增值节点父节点资源
                                    sbxxwh.toInodeResNo_ = array[2];//修改后被申报选择增值节点
                                    sbxxwh.toInodeLorR_ = array[3];//修改后被申报选择增值节点对应区域
                                    comm.alert({
                                        content: comm.lang("coDeclaration")[22000], callOk: function () {
                                            $("#tj_content").dialog("destroy");
                                        }
                                    });
                                });
                            });
                        },
                        "取消": function () {
                            $("#tj_content").dialog("destroy");
                        }
                    }
                });

                sbxxwh.initVerificationMode();
            });

            $('#cancel_btn').click(function () {
                $('#glqysbzzdwh').click();
            });
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
                    $("#fwgshsh").val((res.data.manageEnt) ? res.data.manageEnt.entName : "");//管理公司互生号
                    $("#ssfwgsmc").val((res.data.manageEnt) ? res.data.manageEnt.entResNo : "");//管理公司名称
                    $("#availableQuota").val(res.data.quota);//服务公司可用配额
                }
            });
        },
        /**
         * 依据互生号查询企业信息
         * @param spreadEntResNo 申报单位互生号
         */
        findEntInfo: function (spreadEntResNo) {
            if (spreadEntResNo) {
                dataModoule.findEntInfo({spreadEntResNo: spreadEntResNo}, function (res) {
                    $("#sbdwmc").val(comm.removeNull(res.data.entName));//申报单位名称
                });
            }
        },
        /**
         * 查询企业配额数和可用互生号列表
         * @param toCustType 启用资源类型
         * @param spreadEntResNo 服务公司互生号
         * @param buyResRange 启用资源类型
         */
        findResNoListAndQuota: function (toCustType, spreadEntResNo, buyResRange) {
            if (comm.removeNull(buyResRange) == "" || comm.removeNull(spreadEntResNo) == "") {
                return;
            }
            var params = {};
            params.toCustType = toCustType;//申报类别
            params.buyResRange = buyResRange;//启用资源类型
            params.serResNo = spreadEntResNo;//服务公司互生号
            dataModoule.findResNoListAndQuota(params, function (res) {
                $("#ssfwgsmc").val(res.data.serCustName);//所属服务公司名称
                $("#availableQuota").val(res.data.availQuota);//可用配额
            });
        },
        /**
         * 查询消费增值信息
         * @param spreadEntResNo 推广互生号
         * @param subResNo 挂载互生号
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
                        sbxxwh.fillTable(res.data.incrementMap);
                        if (chooseId) {
                            $("#" + chooseId).attr("checked", true);
                        }
                        if (isAlert) {
                            comm.alert({
                                content: comm.lang("coDeclaration").refSuccess, callOk: function () {
                                }
                            });
                        }
                        $("#comboxDiv").find("input:radio").attr("disabled", false);
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
                    $("#increment-rCount").html(data[k].rCount || data[k].lcount);
                    $("#toPnodeResNo").val(data[k].resNo);
                    $("#toPnodeCustId").val(data[k].custId);
                }
                $("#increment-1-table").show();
                $("#increment-3-table").hide();
            }
        },
        /**
         * 获取积分增值点设置
         * @return array array[0]表示：被申报增值节点父节点客户号array[1]表示：被申报增值节点父节点互生号array[2]表示：被申报选择增值节点array[3]表示：被申报选择增值节点对应区域
         */
        getPnode: function () {
            var val = $('input[name="increment"]:checked').val();
            if (val == null || val == "") {
                return null;
            }
            var toPnodeCustId = comm.removeNull($("#toPnodeCustId").val()).replace("R", "").replace("L", "");
            var toPnodeResNo = $("#toPnodeResNo").val();
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
            return null;
        },
        /**
         * 表单校验
         */
        validateViewMarkData: function () {
            return $("#qysbzzdwh_dialog_form").validate({
                rules: {
                    doubleUserName: {
                        required: true
                    },
                    doublePassword: {
                        required: true
                    }
                },
                messages: {
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
    return sbxxwh;
});