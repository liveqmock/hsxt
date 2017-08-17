define(['text!zypeManageTpl/sjqypepz/sjqypepzsq.html',
    'text!zypeManageTpl/sjqypepz/sjqypepzsq_ck_dialog.html',
    'text!zypeManageTpl/sjqypepz/sjqypepzsq_xg_dialog.html',
    'text!zypeManageTpl/sjqypepz/sjqypepzsq_csh_dialog.html',
    "zypeManageDat/zypeManage",
    "zypeManageSrc/zype_comm",
    "zypeManageLan"
], function (sjqypepzsqTpl, sjqypepzsq_ck_dialogTpl, sjqypepzsq_xg_dialogTpl, sjqypepzsq_csh_dialogTpl, zypeManage, zypeComm) {
    var aps_sjqypepzsq = {
        currencyName: null,//货币名称
        showPage: function () {
            $('#busibox').html(_.template(sjqypepzsqTpl));
            //加载地区缓存
            cacheUtil.findProvCity();
            //货币名称
            zypeComm.getCurrencyName(function (data) {
                aps_sjqypepzsq.currencyName = data;
            });
            //获取省份
            cacheUtil.findCacheSystemInfo(function (sysInfo) {
                cacheUtil.findCacheProvinceByParent(sysInfo.countryNo, function (provinceList) {

                    //获取已分配配额省份
                    zypeManage.allocatedQuotaProvinceQuery({}, function (rsp) {
                        var options = [];
                        $.each(rsp.data, function (k, v) {
                            $(provinceList).each(function (i2, v2) {
                                var k = 0;
                                if (v.provinceNo == v2.provinceNo) {
                                    if (k == 0) {
                                        options.push({name: v2.provinceName, value: v2.provinceNo, selected: true});
                                    } else {
                                        options.push({name: v2.provinceName, value: v2.provinceNo});
                                    }
                                    $("#btnQuery").data(v.provinceNo, v.mEntResNo);
                                    k++;
                                }
                            });
                        });
                        /*下拉列表*/
                        $("#area").selectList({
                            options: options,
                            optionWidth: 120, //列表宽度
                            optionHeight: 200//列表高度
                        });
                        //加载分页
                        aps_sjqypepzsq.queryPage();
                    });
                });

            });
            /** 查询事件*/
            $("#btnQuery").click(function () {
                aps_sjqypepzsq.queryPage();
            });
        },
        //查询分页
        queryPage: function () {

            //分页查询
            var queryParam = {"provinceNo": $("#area").attr("optionvalue")};
            zypeManage.cityQuotaApplyPage(queryParam, function (rsp) {
                var isAutoLoad = true; //是否自动加载数据
                if (rsp.data == null || rsp.data.length == 0) {
                    isAutoLoad = false;
                }

                var gridObj = $.fn.bsgrid.init('searchTable', {
                    autoLoad: isAutoLoad,
                    pageSizeSelect: true,
                    pageSize: 10,
                    stripeRows: true,  //行色彩分隔
                    displayBlankRows: false,   //显示空白行
                    localData: rsp.data,
                    operate: {
                        detail: function (record, rowIndex, colIndex, options) {
                            if (colIndex == 0) {
                                return comm.getCityNameByCode(queryParam.provinceNo, record['cityNo']);
                            }
                            if (colIndex == 6) {
                                //判断是否已初始化
                                if (record.isInit != true) {
                                    return $('<a>配额初始化</a>').click(function (e) {
                                        record.mEntResNo = $("#btnQuery").data($("#area").attr("optionvalue"));	//获取省份对应公司编号
                                        record.provinceNo = $("#area").attr("optionvalue");//省份代码
                                        aps_sjqypepzsq.pecsh(record);
                                    });
                                }
                            }
                        },
                        edit: function (record, rowIndex, colIndex, options) {
                            if (colIndex == 6) {
                                if (record.isInit == true) {
                                    return $('<a>修改配额</a>').click(function (e) {
                                        record.mEntResNo = $("#btnQuery").data($("#area").attr("optionvalue"));	//获取省份对应公司编号
                                        record.provinceNo = $("#area").attr("optionvalue");//省份代码
                                        aps_sjqypepzsq.xgpe(record);
                                    });
                                }
                            }
                        },
                        del: function (record, rowIndex, colIndex, options) {
                            if (colIndex != 7) {
                                return $('<a>' + gridObj.getCellRecordValue(rowIndex, colIndex) + '</a>').click(function (e) {
                                    if (gridObj.getCellRecordValue(rowIndex, colIndex) != 0) {
                                        aps_sjqypepzsq.ck_dialog(record, colIndex);
                                    }
                                });
                            }
                        }
                    }

                });
            });
        },
        pecsh: function (obj) {
            $('#dialogBox_csh').html(_.template(sjqypepzsq_csh_dialogTpl, obj));

            /*弹出框*/
            $("#dialogBox_csh").dialog({
                title: "配额初始化",
                width: "600",
                modal: true,
                buttons: {
                    "提交": function () {
                        var dl = this;
                        if (!aps_sjqypepzsq.quotaInitCheck()) {
                            return false;
                        }
                        //提交申请
                        var initParam = {
                            "quotaEntResNo": obj.mEntResNo,
                            "provinceNo": obj.provinceNo,
                            "cityNo": obj.cityNo,
                            "population": $("#txtPopulation").val(),
                            "applyNum": $("#txtInitApplyNum").val()
                        };
                        zypeManage.cityQuotaInit(initParam, function (rsp) {
                            comm.alert({
                                content: "初始化成功",
                                callOk: function () {
                                    $(dl).dialog("destroy");
                                }
                            });
                        });
                    },
                    "取消": function () {
                        $(this).dialog("destroy");
                    }
                }
            });
            /*end*/
        },
        xgpe: function (obj) {
            //console.log("打印参数结果："+JSON.stringify(obj));
            var detail;
            zypeManage.getEntResDetail({"mEntResNo": obj.mEntResNo}, function (rsp1) {
                zypeManage.resQuotaQuery({"provinceNo": obj.provinceNo, "cityNo": obj.cityNo}, function (rsp2) {
                    var entDetail = rsp1.data;
                    var scDetail = rsp2.data.cityList[0];

                    detail = {"provinceNo": rsp2.data.provinceNo, "entDetail": entDetail, "scDetail": scDetail, "currencyName": aps_sjqypepzsq.currencyName};
                    $('#dialogBox_xg').html(_.template(sjqypepzsq_xg_dialogTpl, detail));

                    /*显示/隐藏输入变更理由*/
                    $('#sqly_input').children('input:not(:last)').click(function () {
                        $('#sqly_textarea').hide();
                    });

                    $('#sqly_input').children('input:last').click(function () {
                        $('#sqly_textarea').show();
                    });
                });
            });


            /*弹出框*/
            $("#dialogBox_xg").dialog({
                title: "修改配额",
                width: "800",
                modal: true,
                buttons: {
                    "提交": function () {
                        var dl = this;
                        if (!aps_sjqypepzsq.quotaUpdateCheck()) {
                            return false;
                        }
                        var type = '2';
                        $.each($('input:radio[name="radApplyType"]'),function (i, p) {
                            if(p.checked == true) {
                                type = p.value;
                            }
                        });
                        var reason='4';
                        $.each($("input:radio[name='radRemark']"), function (index, inp) {
                            if (inp.checked == true) {
                                reason = inp.value;
                            }
                        });
                        //提交申请
                        var updateParam = {
                            "quotaEntResNo": obj.mEntResNo,
                            "provinceNo": obj.provinceNo,
                            "cityNo": obj.cityNo,
                            "applyType": type,
                            "applyNum": $("#txtApplyNum").val(),
                            "applyReason": reason,
                            "otherReason": $("#txtRemark").val()
                        };
                        zypeManage.cityQuotaUpdate(updateParam, function (rsp) {
                            comm.alert({
                                content: "提交成功",
                                callOk: function () {
                                    $(dl).dialog("destroy");
                                }
                            });
                        });
                    },
                    "取消": function () {
                        $(this).dialog("destroy");
                    }
                }
            });
            /*end*/
        },
        ck_dialog: function (obj, colIndex) {
            $('#dialogBox_ck').html(_.template(sjqypepzsq_ck_dialogTpl));
            var status = [];
            var title = null, table = null;
            switch (colIndex) {
                case 2:
                    status = null;
                    title = '城市资源配额';
                    table = 'searchTable_ck';
                    $('#searchTable_ck').removeClass('tabNone');
                    $('#searchTable_ck2').addClass('tabNone');
                    break;
                case 3:
                    status = [1];
                    title = '已使用资源配额';
                    table = 'searchTable_ck';
                    $('#searchTable_ck').removeClass('tabNone');
                    $('#searchTable_ck2').addClass('tabNone');
                    break;
                case 4:
                    status = [0];
                    title = '未分配资源';
                    table = 'searchTable_ck2';
                    $('#searchTable_ck').addClass('tabNone');
                    $('#searchTable_ck2').removeClass('tabNone');
                    break;
                case 5:
                    status = [2];
                    title = '拟分配资源';
                    table = 'searchTable_ck';
                    $('#searchTable_ck').removeClass('tabNone');
                    $('#searchTable_ck2').addClass('tabNone');
                    break;
            }

            zypeManage.cityResStatusDetail({"cityNo": obj.cityNo, "status": status}, function (rsp) {
                var isAutoLoad = true; //是否自动加载数据
                if (rsp.data == null || rsp.data.length == 0) {
                    isAutoLoad = false;
                }

                $.fn.bsgrid.init(table, {
                    autoLoad: isAutoLoad,
                    pageSizeSelect: true,
                    pageSize: 10,
                    stripeRows: true,  //行色彩分隔
                    displayBlankRows: false,   //显示空白行
                    localData: rsp.data

                });
            });
            /*end*/

            /*弹出框*/
            $("#dialogBox_ck").dialog({
                title: title,
                width: "820",
                modal: true,
                buttons: {
                    "确定": function () {
                        $(this).dialog("destroy");
                    }
                }
            });
            /*end*/
        },
        //配额修改数据验证
        quotaUpdateCheck: function () {
            var reg = /^[1-9]+[0-9]*$/;  //数字格式规范：首个数字必须大于0
            var $appNum = $("#txtApplyNum").val(); //申请数量
            var $usableNum = $("#tdUsableNum").text(); //尚可用配额

            if (!reg.test($appNum)) {
                comm.alert({imgClass: 'tips_i', content: "请正确输入申请数量！"});
                return false;
            }
            //可用剩余申请数
            /*var usableNum=parseInt($usableNum)-parseInt($appNum);
             //判断取值范围超出
             if(usableNum<0){
             comm.alert({imgClass: 'tips_i' ,content: "本次申请数不能超过尚可用配额！"});
             return false;
             }*/
            return true;
        },
        //配额初始化数据验证
        quotaInitCheck: function () {
            var reg = /^[1-9]+[0-9]*$/;  //数字格式规范：首个数字必须大于0
            var $appNum = $("#txtInitApplyNum").val(); //申请数量
            var $population = $("#txtPopulation").val(); //人口数

            if (!reg.test($appNum)) {
                comm.alert({imgClass: 'tips_i', content: "请正确输入申请数量！"});
                return false;
            }

            if (!reg.test($population)) {
                comm.alert({imgClass: 'tips_i', content: "请正确输入人口数！"});
                return false;
            }

            return true;
        }

    };
    return aps_sjqypepzsq;
});
