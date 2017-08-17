define(['text!zypeManageTpl/sjqypepz/sjqypepzcx.html',
    'text!zypeManageTpl/sjqypepz/sjqypepzcx_ck_dialog.html',
    "zypeManageDat/zypeManage",
    "zypeManageSrc/zype_comm",
    "zypeManageLan"
], function (sjqypepzcxTpl, sjqypepzcx_ck_dialogTpl, zypeManage, zypeComm) {
    var aps_sjqypepzcx = {
        currencyName: null,//货币名称
        showPage: function () {
            $('#busibox').html(_.template(sjqypepzcxTpl));
            //加载地区缓存
            cacheUtil.findProvCity();
            //货币名称
            zypeComm.getCurrencyName(function (data) {
                aps_sjqypepzcx.currencyName = data;
            });
            //时间控件
            comm.initBeginEndTime('#search_startDate', '#search_endDate');
            /*end*/

            //加载配置类型
            comm.initSelect("#type", comm.lang("zypeManage").quotaTypeEnume);

            //加载审核状态
            comm.initSelect("#state", comm.lang("zypeManage").quotaStatusEnume);


            /**
             * 查询事件
             */
            $("#btnQuery").click(function () {
                if (!comm.queryDateVaild("search_form").form()) {
                    return;
                }
                aps_sjqypepzcx.queryPage();
            });

            /*end*/
        },
        //分页查询
        queryPage: function () {
            if ($("#city").attr("optionvalue") == "") {
                comm.warn_alert("请输入省/州/直辖市");
                return;
            }
            var queryParam = {
                "search_startDate": $("#search_startDate").val(), "search_endDate": $("#search_endDate").val(),
                "search_cityName": $("#txtCityName").val(), "search_applyType": $("#type").attr("optionvalue"),
                "search_status": $("#state").attr("optionvalue")
            };
            zypeManage.cityQuotaApplyQeryPage("searchTable", queryParam, function (record, rowIndex, colIndex, options) {

                if (colIndex == 0) {
                    return comm.getCityNameByCode(record['provinceNo'], record['cityNo']);
                }

                if (colIndex == 1) {
                    return comm.lang("zypeManage").quotaTypeEnume[record["applyType"]];
                }

                if (colIndex == 5) {
                    return comm.lang("zypeManage").quotaStatusEnume[record["status"]];
                }

                return $("<a>" + comm.lang("zypeManage").quota_query_title + "</a>").click(function (e) {
                    aps_sjqypepzcx.chaKan(record);
                });
            });
        },
        /**
         * 查看
         */
        chaKan: function (record) {
            zypeManage.getEntResDetail({"mEntResNo": record.entResNo}, function (rsp1) {
                zypeManage.statQuotaByCity({"provinceNo": record.provinceNo, "cityNo": record.cityNo}, function (rsp2) {
                    zypeManage.cityQuotaDetail({"applyId": record.applyId}, function (rsp3) {
                        var entDetail = rsp1.data;
                        var scDetail = rsp2.data;
                        var areaDetail = rsp3.data;
                        var reason = comm.lang('zypeManage').appReasonEnum[areaDetail.applyReason];
                        areaDetail.apprRemark = areaDetail.applyReason=='0'||!reason?areaDetail.otherReason:reason;

                        var obj = {"entDetail": entDetail, "scDetail": scDetail, "areaDetail": areaDetail, "currencyName": aps_sjqypepzcx.currencyName};
                        $('#dialogBox').empty().html(_.template(sjqypepzcx_ck_dialogTpl, obj));
                        /*弹出框*/
                        $("#dialogBox").dialog({
                            title: comm.lang("zypeManage").city_res_quota_config_detail,
                            width: "800",
                            modal: true,
                            buttons: {
                                "确定": function () {
                                    $(this).dialog("destroy");
                                },
                                "取消": function () {
                                    $(this).dialog("destroy");
                                }
                            }
                        });
                    });
                });
            });
            /*end*/
        }
    };

    return aps_sjqypepzcx;
});
