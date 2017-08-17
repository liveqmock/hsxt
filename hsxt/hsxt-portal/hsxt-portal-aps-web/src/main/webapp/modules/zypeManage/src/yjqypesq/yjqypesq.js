define(['text!zypeManageTpl/yjqypesq/yjqypesq.html',
    'text!zypeManageTpl/yjqypesq/yjqypesq_pesq_dialog.html',
    "zypeManageDat/zypeManage", "zypeManageLan"
], function (yjqypesqTpl, yjqypesq_pesq_dialogTpl, zypeManage) {
    var aps_yjqypesq = {
        showPage: function () {

            $('#busibox').html(_.template(yjqypesqTpl));

            //查找数据
            zypeManage.applyPlatQuotaPage({}, function (rsp) {
                var isAutoLoad = true; //是否自动加载数据
                if (rsp.data == null || rsp.data.length == 0) {
                    isAutoLoad = false;
                }

                $.fn.bsgrid.init('searchTable', {
                    autoLoad: isAutoLoad,
                    pageSizeSelect: true,
                    pageSize: 10,
                    stripeRows: true,  //行色彩分隔
                    displayBlankRows: false,   //显示空白行
                    localData: rsp.data,
                    operate: {
                        detail: function (record, rowIndex, colIndex, options) {
                            return $("<a>" + comm.lang("zypeManage").quota_app_title + "</a>").click(function (e) {
                                aps_yjqypesq.pesq(record);
                            });
                        }
                    }
                });
            });
        },
        pesq: function (obj) {
            $('#dialogBox').html(_.template(yjqypesq_pesq_dialogTpl, obj));

            //失去焦点计算配额
            $("#txtAppNum").blur(function () {
                if ($("#txtAppNum").val() != "") {
                    aps_yjqypesq.appCheck();
                }
            });


            /*弹出框*/
            $("#dialogBox").dialog({
                title: comm.lang("zypeManage").quota_app_title,
                width: "800",
                modal: true,
                buttons: {
                    "提交申请": function () {
                        var dl = this;
                        if (!aps_yjqypesq.appCheck()) {
                            return false;
                        }

                        //提交申请
                        var applyParam = {
                            "applyEntResNo": obj.entResNo,
                            "applyEntCustName": obj.entCustName,
                            "applyNum": $("#txtAppNum").val(),
                            "reqRemark": $("#txtRemark").val()
                        };
                        zypeManage.applyPlatQuota(applyParam, function (rsp) {
                            comm.alert({
                                content: comm.lang("zypeManage").quota_app_success,
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
        //申请数据验证
        appCheck: function () {
            var reg = /^[1-9]+[0-9]*$/;  //数字格式规范：首个数字必须大于0
            var $appNum = $("#txtAppNum").val(); //申请数量
            var $mayAppNum = $("#tdMayAppNum").text(); //当前可申请数

            if (!reg.test($appNum)) {
                comm.alert({imgClass: 'tips_i', content: comm.lang("zypeManage").quota_num_prompt});
                return false;
            }
            //可用剩余申请数
            var usableNum = parseInt($mayAppNum) - parseInt($appNum);
            //判断取值范围超出
            if (usableNum < 0) {
                comm.alert({imgClass: 'tips_i', content: comm.lang("zypeManage").quota_num_error_prompt});
                return false;
            }

            $("#tdUsableNum").text(usableNum);

            return true;
        }
    };
    return aps_yjqypesq;
});