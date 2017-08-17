define(['text!coDeclarationTpl/kqxtyw/fwgskqxtyw.html',
    'coDeclarationDat/kqxtyw/kqxtywcx',
    "commDat/common",
    'coDeclarationLan'], function (fwgskqxtywTpl, dataModoule,commAjax) {
    var aps_fwgskqxtyw_table=null;
    return {
        showPage: function () {
            this.initForm();
        },
        /**
         * 初始化页面
         */
        initForm: function () {
            var self = this;
            $('#busibox').empty().html(_.template(fwgskqxtywTpl));
            //时间控件
            comm.initBeginEndTime('#search_startDate', '#search_endDate');
            //绑定查询事件
            $('#queryBtn').click(function () {
                self.initData();
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
                search_custType: 4
            };
            cacheUtil.findProvCity();
            aps_fwgskqxtyw_table = dataModoule.findOpenSysApprList(jsonParam, this.detail,this.orderOpt);
        },
        /**
         * 查看动作
         */
        detail: function (record, rowIndex, colIndex, options) {
            if (colIndex == 4) {
            	var result = comm.getRegionByCode(record['countryNo'], record['provinceNo'], record['cityNo']);

            	return "<span title='" + result + "'>" + result + "</span>";
            } else if (colIndex == 6) {
                return comm.getNameByEnumId(record['status'], comm.lang("coDeclaration").declarationStatusEnum);
            } else if(colIndex == 8) {
                return $('<a id="' + record['applyId'] + '" >开启系统</a>').click(function (e) {
                    $("#applyId").val(record['applyId']);
                    $("#custType").val("4");
                    $("#menuName").val("fwgskqxtyw");
                    require(['coDeclarationSrc/kqxtyw/sub_tab'], function (tab) {
                        tab.showPage();
                    });
                }.bind(this));
            }else if(colIndex == 9){
                return $('<a>' + comm.lang("coDeclaration").refuseAccept + '</a>').click(function (e) {
                    comm.i_confirm(comm.lang("coDeclaration").openSRefuseAccept, function () {
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
                return comm.workflow_operate(comm.lang("coDeclaration").hangUp, comm.lang("coDeclaration").openSHangUp, function(){
                    require(["workoptSrc/gdgq"],function(gdgq){
                        gdgq.guaQi(record.applyId,aps_fwgskqxtyw_table);
                    });
                });
            }
        }
    }
});