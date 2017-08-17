define(['text!zypeManageTpl/zypzspcx/sjzypzspcx.html',
    'text!zypeManageTpl/zypzspcx/sjzypzspcx_ck_dialog.html',
    "zypeManageDat/zypeManage",
    "zypeManageSrc/zype_comm",
    "zypeManageLan"
], function (sjzypzspcxTpl, sjzypzspcx_ck_dialogTpl, zypeManage, zypeComm) {
    var aps_sjzypzspcx = {
        currencyName: null,//货币名称
        showPage: function () {
            $('#busibox').html(_.template(sjzypzspcxTpl));
            //货币名称
            zypeComm.getCurrencyName(function (data) {
                aps_sjzypzspcx.currencyName = data;
            });

            //加载地区缓存
            cacheUtil.findProvCity();

            //时间控件
            comm.initBeginEndTime('#search_startDate', '#search_endDate');

            //加载配置类型
            comm.initSelect("#type", comm.lang("zypeManage").quotaTypeEnume);

            //加载审核状态
            comm.initSelect("#state", comm.lang("zypeManage").quotaStatusEnume);

            /** 查询事件*/
            $("#btnQuery").click(function () {
                if (!comm.queryDateVaild("search_form").form()) {
                    return;
                }
                aps_sjzypzspcx.queryPage();
            });
            /*end*/
        },
        //分页查询
        queryPage: function () {

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
                    return comm.lang("zypeManage").quotaTypeEnume[record.applyType];
                }

                if (colIndex == 5) {
                    return comm.lang("zypeManage").quotaStatusEnume[record.status];
                }

                return $("<a>" + comm.lang("zypeManage").quota_query_title + "</a>").click(function (e) {
                    aps_sjzypzspcx.chaKan(record);
                });
            });

        },
        chaKan: function (record) {
            zypeManage.getEntResDetail({"mEntResNo": record.entResNo}, function (rsp1) {
                zypeManage.statQuotaByCity({"provinceNo": record.provinceNo, "cityNo": record.cityNo}, function (rsp2) {
                    zypeManage.cityQuotaDetail({"applyId": record.applyId}, function (rsp3) {
                        var entDetail = rsp1.data;
                        var scDetail = rsp2.data;
                        var areaDetail = rsp3.data;

                        var obj = {"entDetail": entDetail, "scDetail": scDetail, "areaDetail": areaDetail, "currencyName": aps_sjzypzspcx.currencyName};
                        $('#dialogBox').html(_.template(sjzypzspcx_ck_dialogTpl, obj));
                    });
                });
            });

            /*弹出框*/
            $("#dialogBox").dialog({
                title: comm.lang("zypeManage").city_res_quota_detail,
                width: "800",
                modal: true,
                buttons: {
                    "确定": function () {
                        $(this).dialog("destroy");
                    }
                }
            });
        }
    };
    return aps_sjzypzspcx;
});
