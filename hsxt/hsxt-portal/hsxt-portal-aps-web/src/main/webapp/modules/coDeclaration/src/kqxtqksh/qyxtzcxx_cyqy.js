define(['text!coDeclarationTpl/kqxtqksh/qyxtzcxx_cyqy.html',
    'text!coDeclarationTpl/kqxtqksh/confirm_dialog.html',
    'coDeclarationSrc/qyxz/point_choose',
    'coDeclarationDat/qyxz/qyxtzcxx_cyqy',
    'coDeclarationLan'], function (qyxtzcxx_cyqyTpl, confirm_dialogTpl, pointChoose, dataModoule) {
    var kqxtqksh_cyqy = {
        applyId: null,
        odata: null,//修改前的数据
        pdata: null,//原始数据
        toPnodeCustId: "",//修改前被申报增值节点父节点客户号
        toPnodeResNo: "",//修改前被申报增值节点父节点资源
        toInodeResNo: "",//修改前被申报选择增值节点
        toInodeLorR: "",//修改前被申报选择增值节点对应区域
        showPage: function (data) {
            odata = data;
            pdata = data;
            this.applyId = data.applyId;
            // $("#comboxDiv").hide();
            this.initData(data);
        },
        /**
         * 获取申请编号
         */
        getApplyId: function () {
            return this.applyId;
        },
        /**
         * 初始化表单
         */
        initForm: function (data) {

            $('#qyxtzcxx_xgTpl').html(_.template(qyxtzcxx_cyqyTpl, data));

            /**启用资源类型*/
            comm.initSelect('#toBuyResRange', comm.lang("coDeclaration").cyToBuyResRangeEnum, 185).change(function (e) {
                var spreadEntResNo = $("#spreadEntResNo").val();
                var buyResRange = $("#toBuyResRange").attr('optionValue');
                kqxtqksh_cyqy.showIncrementDiv();
                kqxtqksh_cyqy.findResNoListAndQuota(spreadEntResNo, buyResRange, null);
            });

            /**申报类型*/
            $("#toCustType").val(comm.lang("coDeclaration").cyEntDesc);

            /*			//顺序选配
             $('#sxxp1').click(function(){
             $("#select_btn").hide();
             var spreadEntResNo = $("#spreadEntResNo").val();
             var buyResRange = $("#toBuyResRange").attr('optionValue');
             kqxtqksh_cyqy.findResNoListAndQuota(spreadEntResNo, buyResRange, null);
             });

             //人工选配
             $('#rgxp1').click(function(){
             $("#select_btn").show();
             $("#entResNo").val("");
             });*/

            $('input[name="chkType"]').attr('disabled', true);
            if (data.toSelectMode && data.toSelectMode == 1) {
                $('#rgxp1').attr('checked', true);
                // $("#select_btn").show();
            } else {
                $('#sxxp1').attr('checked', true);
            }

            //刷新增值点数据
            $('#refincrement').click(function () {
                var spreadEntResNo = $("#spreadEntResNo").val();
                var zzdqyglh = $.trim($("#zzdqyglh").val()) || spreadEntResNo;

                if (zzdqyglh.substring(0, 2) == spreadEntResNo.substring(0, 2)) {
                    kqxtqksh_cyqy.findIncrement(spreadEntResNo, zzdqyglh, true);
                } else {
                    var message = comm.lang("coDeclaration").resnoFormError;
                    message = message.replace('serResNo', spreadEntResNo);
                    comm.warn_alert(message, 600);
                }
            });

            //保存
            $('#qyxtzcxx_save').click(function () {
                kqxtqksh_cyqy.saveRegInfoStepOne();
            });

            //取消
            $('#qyxtzcxx_canc').click(function () {
                $('#qyxtzcxxTpl').removeClass('none');
                $('#qyxtzcxx_xgTpl').addClass('none');
                //如果数据有变动需要重新加载展示界面数据
                if (odata != pdata) {
                    require(["coDeclarationSrc/kqxtqksh/qyxtzcxx"], function (qyxtzcxx) {
                        qyxtzcxx.initForm(odata);
                    });
                }
            });

            //选择互生号
            /*			$('#select_btn').click(function(){
             var spreadEntResNo = $("#spreadEntResNo").val();
             var buyResRange = $("#toBuyResRange").attr('optionValue');
             pointChoose.findMemberPointList(spreadEntResNo, 2, buyResRange, "#entResNo");
             });*/

            //获取地区信息
            cacheUtil.syncGetRegionByCode(data.countryNo, data.provinceNo, data.cityNo, "", function (resdata) {
                $('#countryNo').val(data.countryNo);
                $("#placeName").html(resdata);
            });

            $("#toBuyResRange").selectListValue(data.toBuyResRange);
            $("#toBuyResRange").selectEnabled(false);
            kqxtqksh_cyqy.showIncrementDiv();
            kqxtqksh_cyqy.findResNoListAndQuota(data.spreadEntResNo, data.toBuyResRange, data.toEntResNo);
        },
        /**
         * 显示与隐藏积分增值信息
         */
        showIncrementDiv: function () {
            var toBuyResRange = $("#toBuyResRange").attr('optionValue');
            if (toBuyResRange == "5") {
                $("#incrementDiv").hide();
            } else {
                $("#incrementDiv").show();
            }
        },
        /**
         * 初始化数据
         * @param data 注册信息
         */
        initData: function (data) {
            this.initForm(data);
            if (data.toPnodeResNo) {
                kqxtqksh_cyqy.toPnodeCustId = comm.removeNull(data.toPnodeCustId);//修改前被申报增值节点父节点客户号
                kqxtqksh_cyqy.toPnodeResNo = comm.removeNull(data.toPnodeResNo);//修改前被申报增值节点父节点资源
                kqxtqksh_cyqy.toInodeResNo = comm.removeNull(data.toInodeResNo);//修改前被申报选择增值节点
                kqxtqksh_cyqy.toInodeLorR = comm.removeNull(data.toInodeLorR);//修改前被申报选择增值节点对应区域
                $("#zzdqyglh").val(data.toPnodeResNo);
                var score = comm.removeNull(data.toInodeResNo);//1增值分配点
                var chooseId = "increment-1" + ((data.toInodeLorR == 0) ? "-l" : "-r");
                if (score.indexOf("R") != -1) {//3增值分配点
                    chooseId = "increment-3" + ((data.toInodeLorR == 0) ? "-l" : "-r");
                } else if (score.indexOf("L") != -1) {//2增值分配点
                    chooseId = "increment-2" + ((data.toInodeLorR == 0) ? "-l" : "-r");
                }
                kqxtqksh_cyqy.findIncrement(data.spreadEntResNo, data.toPnodeResNo, false, chooseId);
            }
        },
        /**
         * 查询企业配额数和可用互生号列表
         * @param spreadEntResNo 服务公司互生号
         * @param buyResRange 启用资源类型
         * @param resNo 拟用互生号
         */
        findResNoListAndQuota: function (spreadEntResNo, buyResRange, resNo) {
            if (comm.removeNull(buyResRange) == "" || comm.removeNull(spreadEntResNo) == "") {
                return;
            }
            $("#spreadEntResNo").val(spreadEntResNo);
            var params = {};
            params.toCustType = 2;//申报类别
            params.buyResRange = buyResRange;//启用资源类型
            params.serResNo = spreadEntResNo;//服务公司互生号
            dataModoule.findResNoListAndQuota(params, function (res) {
                if (resNo) {
                    kqxtqksh_cyqy.setHtmlData(resNo, res.data.availQuota, res.data.serCustName, res.data.serCustId);
                } else {
                    var chkType = $('input[name="chkType"]:checked').val();
                    if (chkType == '0') {
                        kqxtqksh_cyqy.setHtmlData(res.data.defaultEntResNo, res.data.availQuota, res.data.serCustName, res.data.serCustId);
                    } else {
                        kqxtqksh_cyqy.setHtmlData("", res.data.availQuota, res.data.serCustName, res.data.serCustId);
                    }
                }
            });
        },
        /**
         * 设置服务公司可用配额及管理公司信息
         * @param resNo 拟用启用互生号
         * @param availQuota 托管企业可用配额
         * @param spreadEntName 所属服务公司名称
         * @param spreadEntCustId 所属服务公司企业客户号
         */
        setHtmlData: function (resNo, availQuota, spreadEntName, spreadEntCustId) {
            $("#entResNo").val(comm.removeNull(resNo));
            $("#availQuota").val(comm.removeNull(availQuota));
            $("#spreadEntName").val(comm.removeNull(spreadEntName));
            $("#spreadEntCustId").val(comm.removeNull(spreadEntCustId));
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
                        kqxtqksh_cyqy.fillTable(res.data.incrementMap);
                        if (chooseId) {
                            $("#" + chooseId).attr("checked", true);
                        }
                        if (isAlert) {
                            comm.alert({
                                content: comm.lang("coDeclaration").refSuccess, callOk: function () {
                                }
                            });
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
         * 表单校验
         */
        validateData: function () {
            var validate = $("#qyxtzcxx_cyqy_form").validate({
                rules: {
                    toCustType: {
                        required: true
                    },
                    toBuyResRange: {
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
                        required: true
                    }
                },
                messages: {
                    toCustType: {
                        required: comm.lang("coDeclaration")[36049]
                    },
                    toBuyResRange: {
                        required: comm.lang("coDeclaration")[36050]
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
                        required: comm.lang("coDeclaration")[36057]
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
            var availQuota = $("#availQuota").html();
            if (parseInt(availQuota) == 0) {
                comm.error_alert(comm.lang("coDeclaration")[32691]);
                return;
            }
            if (!this.validateData().form()) {
                return;
            }
            var toBuyResRange = $("#toBuyResRange").attr('optionValue');
            if (toBuyResRange == "5") {//免费资源无需检查积分增值设置
                kqxtqksh_cyqy.saveRegInfo([null, null, null, null]);
            } else {
                var array = this.getPnode();//获取积分增值点设置
                if (toBuyResRange == "4") {
                    if (array && array.length > 0) {
                        kqxtqksh_cyqy.saveRegInfo(array);
                    } else {
                        comm.warn_alert(comm.lang("coDeclaration").nosetincrement);
                    }
                }
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

            /*            //启用资源类型
             var oldToBuyResRange = comm.removeNull(odata.toBuyResRange);//修改前启用资源类型
             var newToBuyResRange = $("#toBuyResRange").attr('optionValue');//修改后启用资源类型
             if (oldToBuyResRange != newToBuyResRange) {
             var oldToBuyResRangeText = comm.getNameByEnumId(oldToBuyResRange, comm.lang("coDeclaration").cyToBuyResRangeEnum);
             var newToBuyResRangeText = comm.getNameByEnumId(newToBuyResRange, comm.lang("coDeclaration").cyToBuyResRangeEnum);
             trs += "<tr><td class=\"view_item\">启用资源类型</td><td class=\"view_text\">" + oldToBuyResRangeText + "</td><td class=\"view_text\">" + newToBuyResRangeText + "</td></tr>";
             chg.toBuyResRange = {"old": oldToBuyResRange, "new": newToBuyResRange};
             ndata.toBuyResRange = newToBuyResRange;
             }

             //拟用企业互生号
             var oldToEntResNo = comm.removeNull(odata.toEntResNo);//修改前拟用企业互生号
             var newToEntResNo = $("#entResNo").val();//修改后拟用企业互生号
             if (oldToEntResNo != newToEntResNo) {
             trs += "<tr><td class=\"view_item\">拟用企业互生号</td><td class=\"view_text\">" + oldToEntResNo + "</td><td class=\"view_text\">" + newToEntResNo + "</td></tr>";
             chg.toEntResNo = {"old": oldToEntResNo, "new": newToEntResNo};
             ndata.toEntResNo = newToEntResNo;
             }*/

            //增值系统位置企业互生号
            var oldPnodeResNo = comm.removeNull(kqxtqksh_cyqy.toPnodeResNo);//修改前增值系统位置企业互生号
            var newPnodeResNo = comm.removeNull(array[1]);//修改后增值系统位置企业互生号
            if (oldPnodeResNo != newPnodeResNo) {
                trs += "<tr><td class=\"view_item\">增值系统位置企业互生号</td><td class=\"view_text\">" + oldPnodeResNo + "</td><td class=\"view_text\">" + newPnodeResNo + "</td></tr>";
                chg.toPnodeResNo = {"old": oldPnodeResNo, "new": newPnodeResNo};
                ndata.toPnodeResNo = newPnodeResNo;
            }

            //增值分配点
            var oldInodeResNo = comm.removeNull(kqxtqksh_cyqy.toInodeResNo);//修改前增值分配点
            var newInodeResNo = comm.removeNull(array[2]);//修改后增值分配点
            if (oldInodeResNo != newInodeResNo) {
                var oldInodeResNoText = kqxtqksh_cyqy.getIncrementScore(oldInodeResNo) + "增值分配点";//修改前增值分配点
                var newInodeResNoText = kqxtqksh_cyqy.getIncrementScore(newInodeResNo) + "增值分配点";//修改后增值分配点
                trs += "<tr><td class=\"view_item\">增值分配点</td><td class=\"view_text\">" + oldInodeResNoText + "</td><td class=\"view_text\">" + newInodeResNoText + "</td></tr>";
                chg.toInodeResNo = {"old": kqxtqksh_cyqy.toInodeResNo, "new": newInodeResNo};
                ndata.toInodeResNo = newInodeResNo;
            }

            //增值区
            var oldInodeLorR = comm.removeNull(kqxtqksh_cyqy.toInodeLorR);//修改前增值分配点
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
            var oldPnodeCustId = comm.removeNull(kqxtqksh_cyqy.toPnodeCustId);//修改前被申报增值节点父节点客户号
            var newPnodeCustId = comm.removeNull(array[0]);//修改后被申报增值节点父节点客户号
            if (oldPnodeCustId != newPnodeCustId) {
                chg.toPnodeCustId = {"old": oldPnodeCustId, "new": newPnodeCustId};
                ndata.toPnodeCustId = newInodeLorR;
            }

            if (trs == "") {
                comm.warn_alert(comm.lang("coDeclaration").noUpdate);
                return;
            }
            //提交
            $('#qyxtzcxx_tj_dialog > p').html(_.template(confirm_dialogTpl));
            $('#copTable tr:eq(1)').before(trs);
            kqxtqksh_cyqy.initVerificationMode();
            $('#qyxtzcxx_tj_dialog').dialog({
                width: 800,
                buttons: {
                    '确定': function () {
                        if (!kqxtqksh_cyqy.validateViewMarkData().form()) {
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
                                kqxtqksh_cyqy.toPnodeCustId = array[0];//修改后被申报增值节点父节点客户号
                                kqxtqksh_cyqy.toPnodeResNo = array[1];//修改后被申报增值节点父节点资源
                                kqxtqksh_cyqy.toInodeResNo = array[2];//修改后被申报选择增值节点
                                kqxtqksh_cyqy.toInodeLorR = array[3];//修改后被申报选择增值节点对应区域
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
         * 获取拟用企业互生号
         */
        getToEntResNo: function () {
            return $("#entResNo").val();
        },
        /**
         * 获取所属管理公司互生号,服务公司截取2位后补9个0
         */
        getToMResNo: function () {
            var curPointNO = $("#spreadEntResNo").val();
            if (curPointNO == null || curPointNO.length < 2) {
                return "";
            } else {
                return curPointNO.substring(0, 2) + "000000000";
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
    return kqxtqksh_cyqy;
}); 

