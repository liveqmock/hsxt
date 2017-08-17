define(['text!zypeManageTpl/zypzsp/sjzypzsp.html',
    'text!zypeManageTpl/zypzsp/sjzypzsp_sp_dialog.html',
    "zypeManageDat/zypeManage",
    "zypeManageSrc/zype_comm",
    "zypeManageLan"
], function (sjzypzspTpl, sjzypzsp_sp_dialogTpl, zypeManage, zypeComm) {
    var aps_sjzypzsp = {
        currencyName: null,//货币名称
        showPage: function () {
            $('#busibox').html(_.template(sjzypzspTpl));
            //加载地区缓存
            cacheUtil.findProvCity();
            //货币名称
            zypeComm.getCurrencyName(function (data) {
                aps_sjzypzsp.currencyName = data;
            });

            //时间控件
            comm.initBeginEndTime('#search_startDate', '#search_endDate');
            /*end*/
            //加载配置类型
            comm.initSelect("#type", comm.lang("zypeManage").quotaTypeEnume);

            //加载审核状态
            /*comm.initSelect("#state",comm.lang("zypeManage").quotaStatusEnume);*/
            /**
             * 查询事件
             */
            $("#btnQuery").click(function () {
                if (!comm.queryDateVaild("search_form").form()) {
                    return;
                }
                aps_sjzypzsp.queryPage();
            });
            /*end*/
        },
        //分页查询
        queryPage: function () {
            //经办人操作号
            var exeCustId = comm.getCookie("custId");

            //判断操作员是否为管理员
            if (comm.getCookie("isAdmin") == "true") {
                exeCustId = "";
            }

            var queryParam = {
                "search_startDate": $("#search_startDate").val(), "search_endDate": $("#search_endDate").val(),
                "search_cityName": $("#txtCityName").val(), "search_applyType": $("#type").attr("optionvalue"),
                "search_status": 0, "search_exeCustId": exeCustId
            };
            zypeManage.cityQuotaApplyQeryPage("searchTable", queryParam, function (record, rowIndex, colIndex, options) {
                if (colIndex == 0) {
                    return comm.getCityNameByCode(record['provinceNo'], record['cityNo']);
                }
                if (colIndex == 1) {
                    return comm.lang("zypeManage").quotaTypeEnume[record.applyType];
                }
                if (colIndex == 5) {
                    return comm.lang("zypeManage").quotaStatusEnume[record.status];
                }
                return $("<a>" + comm.lang("zypeManage").approve_title + "</a>").click(function (e) {
                    aps_sjzypzsp.shenPi(record);
                });
            });

        },
        shenPi: function (record) {
            //加载审批信息
            zypeManage.getEntResDetail({"mEntResNo": record.entResNo}, function (rsp1) {
                zypeManage.resQuotaQuery({"provinceNo": record.provinceNo, "cityNo": record.cityNo}, function (rsp2) {
                    var entDetail = rsp1.data;
                    var scDetail = rsp2.data.cityList[0];
                    var reason = comm.lang('zypeManage').appReasonEnum[record.applyReason];
                    record.reqRemark = record.applyReason == '0' || !reason ? record.otherReason : reason;
                    var detail = {"entDetail": entDetail, "scDetail": scDetail, "applyDetail": record, "currencyName": aps_sjzypzsp.currencyName};
                    $('#dialogBox').empty().html(_.template(sjzypzsp_sp_dialogTpl, detail));

                    /*弹出框*/
                    $("#dialogBox").dialog({
                        title: comm.lang("zypeManage").city_res_quota_approve,
                        width: "800",
                        modal: true,
                        buttons: {
                            "确定": function () {
                                var dl = this;

                                if (!aps_sjzypzsp.approveCheck(record)) {
                                    return false;
                                }

                                //提交申请
                                var approveParam = {
                                    "applyId": record.applyId,
                                    "apprNum": $("#txtApprNum").val(),
                                    "status": $("input[name='radStatus']:checked").val(),
                                    "apprRemark": $("#txtApprRemark").val()
                                };

                                zypeManage.cityQuotaApprove(approveParam, function (rsp) {
                                    comm.alert({
                                        content: comm.lang("zypeManage").approve_success_point,
                                        callOk: function () {
                                            $(dl).dialog("destroy");
                                            aps_sjzypzsp.queryPage();//刷新数据
                                        }
                                    });
                                });

                            },
                            "取消": function () {
                                $(this).dialog("destroy");
                            }
                        }
                    });
                });
            });
            /*end*/
        },
        //审批验证
        approveCheck: function (obj) {
            var reg = /^[1-9]+[0-9]*$/;  //数字格式规范：首个数字必须大于0
            var $apprNum = $("#txtApprNum").val(); //申请数量

            if (!reg.test($apprNum)) {
                comm.alert({imgClass: 'tips_i', content: comm.lang("zypeManage").input_appr_num});
                return false;
            }

            //批复数量,不可大于申请数
            if (obj.applyNum < parseInt($apprNum)) {
                comm.alert({imgClass: 'tips_i', content: comm.lang("zypeManage").appr_num_error_point});
                return false;
            }

            return true;
        }
    };
    return aps_sjzypzsp;
});
