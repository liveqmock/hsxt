define(['text!coDeclarationTpl/kqxtyw/tgqykqxtyw.html',
    'coDeclarationDat/kqxtyw/kqxtywcx',
    "commDat/common",
    'coDeclarationLan'], function (tgqykqxtywTpl, dataModoule,commAjax) {
    var aps_tgqykqxtyw = {
        bgGrid :null,
        showPage: function () {
            aps_tgqykqxtyw.initForm();
        },
        /**
         * 初始化页面
         */
        initForm: function () {
            $('#busibox').empty().html(_.template(tgqykqxtywTpl));
            //时间控件
            comm.initBeginEndTime('#search_startDate', '#search_endDate');
            //绑定查询事件
            $('#queryBtn').click(function () {
                aps_tgqykqxtyw.initData();
            });
            comm.initSelect("#quickDate",comm.lang("common").quickDateEnum);
            $("#quickDate").change(function(){
                comm.quickDateChange($(this).attr("optionvalue"),'#search_startDate', '#search_endDate');
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
                search_custType: 3
            };
            aps_tgqykqxtyw.bgGrid =  dataModoule.findOpenSysApprList(jsonParam, aps_tgqykqxtyw.detail,aps_tgqykqxtyw.orderOpt);
        },
        /**
         * 查看动作
         */
        detail: function (record, rowIndex, colIndex, options) {
            if (colIndex == 6) {
                return comm.getNameByEnumId(record['status'], comm.lang("coDeclaration").declarationStatusEnum);
            } else if (colIndex == 8) {
                return $('<a id="' + record['applyId'] + '" >开启系统</a>').click(function (e) {
                    $("#applyId").val(record['applyId']);
                    $("#custType").val("3");
                    $("#menuName").val("tgqykqxtyw");
                    require(['coDeclarationSrc/kqxtyw/sub_tab'], function (tab) {
                        tab.showPage();
                    });
                }.bind(this));
            } else if(colIndex == 9){
                return $('<a>' + comm.lang("coDeclaration").refuseAccept + '</a>').click(function (e) {
                    comm.i_confirm(comm.lang("coDeclaration").openTRefuseAccept, function () {
                        commAjax.workTaskRefuseAccept({bizNo: record.applyId}, function (resp) {
                            comm.yes_alert(comm.lang("coDeclaration")[22000], null, function () {
                                $('#queryBtn').trigger('click');
                            });
                        });
                    });
                });
            }
        },
        /**
         * 工单操作
         */
        orderOpt: function (record, rowIndex, colIndex, options) {
            if(colIndex == 9) {
                return comm.workflow_operate(comm.lang("coDeclaration").hangUp, comm.lang("coDeclaration").openTHangUp, function(){
                    require(["workoptSrc/gdgq"],function(gdgq){
                        gdgq.guaQi(record.applyId,aps_tgqykqxtyw.bgGrid);
                    });
                });
            }
        }
    };
    return aps_tgqykqxtyw;
});