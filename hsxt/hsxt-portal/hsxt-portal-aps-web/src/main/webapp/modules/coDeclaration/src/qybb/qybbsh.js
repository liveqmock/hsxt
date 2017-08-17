define(['text!coDeclarationTpl/qybb/qybbsh.html',
    'text!coDeclarationTpl/qybb/qybbsh/qybbsh_dialog_1.html',
    'text!coDeclarationTpl/qybb/qybbsh/qybbsh_dialog_2.html',
    'text!coDeclarationTpl/qybb/qybbsh/check_dialog.html',
    'text!coDeclarationTpl/qybb/same_item_dialog.html',
    'coDeclarationDat/qybb/qybbsh',
    'coDeclarationDat/qybb/qybb',
    "commDat/common",
    'coDeclarationLan'], function (qybbshTpl, qybbsh_dialog_1Tpl, qybbsh_dialog_2Tpl, check_dialogTpl, same_item_dialogTpl, dataModoule,qybbAjax,commAjax) {
    var aps_qybbsh = {
        bgGrid:null,
        showPage: function () {
            $('#busibox').html(_.template(qybbshTpl));
            comm.initSelect('#quickDate', comm.lang("coDeclaration").quickDateEnum);
            cacheUtil.findCacheSystemInfo(function (sysRes) {
                cacheUtil.findCacheProvinceByParent(sysRes.countryNo, function (provArray) {
                    comm.initProvSelect('#provinceNo', provArray, 100, null, {name: '', value: ''});
                });
            });
            comm.initSelect('#search_status', comm.lang("coDeclaration").checkStatusEnum, null, null, {name: '全部', value: ''});
            $('#queryBtn').click(function () {
                aps_qybbsh.initData();
            });
            aps_qybbsh.initForm();
        },
        initForm: function () {
            //时间控件		    
            comm.initBeginEndTime('#search_startDate', '#search_endDate');
            //快捷选择日期事件
            $("#quickDate").bind("change", function () {
                var method = {
                    'today': 'getTodaySE',
                    'week': 'getWeekSE',
                    'month': 'getMonthSE'
                }[$(this).attr('optionValue')];
                var arr = method ? comm[method]() : ['', ''];
                $("#search_startDate").val(arr[0]);
                $("#search_endDate").val(arr[1]);
            });
        },
        initData: function () {
            if (!comm.queryDateVaild("search_form").form()) {
                return;
            }
            var params = {
                search_startDate: $("#search_startDate").val(),
                search_endDate: $("#search_endDate").val(),
                search_linkman: $("#search_linkman").val(),
                search_entName: $("#search_entName").val(),
                search_provinceNo: comm.removeNull($("#provinceNo").attr('optionValue')),
                search_status: $("#search_status").attr('optionValue')
            };
            cacheUtil.findProvCity();
           aps_qybbsh.bgGrid =  dataModoule.findApprFilingList(params, this.det, this.del, this.add);
        },
        /**
         * 显示相同项
         */
        det: function (record, rowIndex, colIndex) {
            if (colIndex == 1) {
                return comm.getRegionByCode(record['countryNo'], record['provinceNo'], record['cityNo']);
            } else if (colIndex == 5) {
                if (record['existSameItem']) {
                    return $('<a class="existSameItem">查看</a>').click(function () {
                        aps_qybbsh.querySameItem(record);
                    }).bind(this);
                } else {
                    return comm.lang("coDeclaration").no;
                }
            } else if (colIndex == 6) {
                return comm.getNameByEnumId(record['status'], comm.lang("coDeclaration").declaraStatusEnum);
            } else if (colIndex == 7) {
                /*return  $('<a data-type="detail" >查看</a>').click(function(e) {
                 var status = record['status'];
                 var tpl = (status == 1)?qybbsh_dialog_1Tpl:qybbsh_dialog_2Tpl;
                 //查看详情
                 dataModoule.findFilingById({applyId:record['applyId']}, function(res_){
                 $('#detail_dialog > p').html(_.template(tpl, res_.data.FILING_APP));
                 $("#region").html(comm.getRegionByCode(res_.data.FILING_APP['countryNo'], res_.data.FILING_APP['provinceNo'], res_.data.FILING_APP['cityNo']));
                 $("#legalType").html(comm.getNameByEnumId(res_.data.FILING_APP.legalType, comm.lang("coDeclaration").idTypeEnum));
                 if(status != 1){
                 $("#status").html(comm.getNameByEnumId(res_.data.FILING_APP.status, comm.lang("coDeclaration").declaraStatusEnum));
                 //获取审核操作员
                 cacheUtil.searchOperByCustId(res_.data.FILING_APP.updatedBy, function(res){
                 $("#updatedBy").html(comm.getOperNoName(res));
                 });
                 }
                 if(res_.data.FILING_APP.existSameItem){
                 $("#remark").html("与其它企业存在相同项");
                 }
                 comm.getEasyBsGrid("tableDetail1", res_.data.FILING_SH, function(record_, rowIndex_, colIndex_, options_){
                 if(colIndex_ == 0){
                 return rowIndex_+1;
                 }else if(colIndex_ == 3){
                 return comm.getNameByEnumId(record_['idType'], comm.lang("coDeclaration").idTypeEnum);
                 }else if(colIndex_ == 2){
                 return comm.getNameByEnumId(record_['shType'], comm.lang("coDeclaration").shTypeEnum);
                 }
                 });
                 var files = res_.data.FILING_APT;
                 if(files){
                 for(var k in files){
                 var title = comm.getNameByEnumId(files[k].aptType, comm.lang("coDeclaration").aptTypeEnum);
                 comm.initPicPreview("#picFileId-"+files[k].aptType, files[k].fileId, title);
                 }
                 }
                 $('#detail_dialog').dialog({width:1000,title:'企业报备详情',buttons:{'确定':function(){$(this).dialog('destroy')}}});
                 });
                 });
                 */
            } else if (colIndex == 8) {
                return $('<a>' + comm.lang("coDeclaration").refuseAccept + '</a>').click(function (e) {
                    comm.i_confirm(comm.lang("coDeclaration").optRefuseAccept, function () {
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
         * 修改报备
         */
        del: function (record, rowIndex, colIndex) {
            if (colIndex == 7) {
                var status = record['status'];
                if (status == 1 || status == 2 || status == 3 || status == 4) {
                    return $('<a data-type="update" >修改</a>').click(function (e) {
                        comm.liActive_add($('#qybbxg'));
                        require(['coDeclarationSrc/qybb/sub_tab'], function (tab) {
                            tab.showPage(record['applyId'], "qybbsh");
                        });
                    });
                }
            }else if(colIndex == 8) {
                return comm.workflow_operate(comm.lang("coDeclaration").hangUp, comm.lang("coDeclaration").optHangUp, function(){
                    require(["workoptSrc/gdgq"],function(gdgq){
                        gdgq.guaQi(record.applyId,aps_qybbsh.bgGrid);
                    });
                });
            }
        },
        /**
         * 弹出审核
         */
        add: function (record, rowIndex, colIndex) {
            if (colIndex == 7) {
                var status = record['status'];
                if (status == 1 || status == 2 || status == 3 || status == 4) {
                    return $('<a data-type="check" >审核</a>').click(function (e) {
                        var status = record['status'];
                        //查看详情
                        dataModoule.findFilingById({applyId: record['applyId']}, function (res_) {
                            $('#detail_dialog > p').empty().html(_.template(check_dialogTpl, res_.data.FILING_APP));
                            $("#region").html(comm.getRegionByCode(res_.data.FILING_APP['countryNo'], res_.data.FILING_APP['provinceNo'], res_.data.FILING_APP['cityNo']));
                            $("#legalType").html(comm.getNameByEnumId(res_.data.FILING_APP.legalType, comm.lang("coDeclaration").idTypeEnum));
                            $("#choose-" + status).show();
                            if (res_.data.FILING_APP.existSameItem) {
                                $("#remark").html("与其它企业存在相同项");
                            }
                            //获取报备操作员
                            cacheUtil.searchOperByCustId(res_.data.FILING_APP.createdBy, function (res) {
                                $("#createdBy").html(comm.getOperNoName(res));
                            });
                            //查询报备企业信息
                            dataModoule.findMainInfoByResNo({companyResNo: res_.data.FILING_APP.opResNo}, function (res) {
                                $("#entName").html(res.data.entName);
                                $("#contactPhone").html(res.data.contactPhone);
                                $("#contactPerson").html(res.data.contactPerson);
                            });
                            comm.getEasyBsGrid("tableDetail1", res_.data.FILING_SH, function (record_, rowIndex_, colIndex_, options_) {
                                if (colIndex_ == 0) {
                                    return rowIndex_ + 1;
                                } else if (colIndex_ == 3) {
                                    return comm.getNameByEnumId(record_['idType'], comm.lang("coDeclaration").idTypeEnum);
                                } else if (colIndex_ == 2) {
                                    return comm.getNameByEnumId(record_['shType'], comm.lang("coDeclaration").shTypeEnum);
                                }
                            });
                            var files = res_.data.FILING_APT;
                            var picSize = [];
                            if (files) {
                                for (var k in files) {
                                    var title = comm.getNameByEnumId(files[k].aptType, comm.lang("coDeclaration").aptTypeEnum);
                                    comm.initPicPreview("#picFileId-" + files[k].aptType, files[k].fileId, title);
                                    picSize.push(files[k].aptType);
                                }
                            }
                            //没有的附件，隐藏点击链接
                            for (var i = 1; i <= 5; i++) {
                                if (!_.contains(picSize, i)) {
                                    $('#picFileId-' + i).hide();
                                }
                            }
                            var radioName = "status-" + status;
                            $('#detail_dialog').dialog({
                                width: 1000, title: '企业报备审核', buttons: {
                                    '确定': function () {
                                        var params_ = {};
                                        params_.applyId = record['applyId'];
                                        params_.apprRemark = $("#apprRemark").val();
                                        params_.status = $("input[name=" + radioName + "]:checked").val();
                                        dataModoule.apprCommFiling(params_, function (filingRes) {
                                            comm.alert({
                                                content: comm.lang("coDeclaration")[22000], callOk: function () {
                                                    $("#detail_dialog").dialog('destroy');
                                                    $('#queryBtn').click();
                                                }
                                            });
                                        });
                                    },
                                    '取消': function () {
                                        $(this).dialog('destroy')
                                    }
                                }
                            });
                        });
                    });
                }
            }
        },
        /**
         * 查看相同项
         */
        querySameItem: function (record) {

            qybbAjax.findSameItem({applyId: record.applyId},function (resp) {
                if(resp.data) {
                    var sameData = $.extend({}, resp.data);
                    sameData.area = comm.getRegionByCode(sameData.countryNo, sameData.provinceNo, sameData.cityNo);
                    if(sameData.sameShareHolders&&sameData.sameShareHolders.length>0) {
                        var group = [], count=[];
                        $.each(sameData.sameShareHolders,function(index,h){
                            var name = $.trim(h[2])+'-'+$.trim(h[3])+'-'+$.trim(h[4]);
                            if(!_.contains(group,name)){
                                group.push(name);
                            }
                        });
                        $.each(sameData.sameShareHolders,function(index,sh){
                            var shn = $.trim(sh[2])+'-'+$.trim(sh[3])+'-'+$.trim(sh[4]);
                            for(var j=0; j < group.length; j++){
                                if(group[j]==shn){
                                    if(count[j]||count[j]>=1){
                                        count[j] = count[j]+1;
                                    }else{
                                        count[j] = 1;
                                    }
                                }
                            }
                        });
                        sameData.group = group;
                        sameData.count = count;
                    }
                    $('#detail_dialog > p').empty().html(_.template(same_item_dialogTpl,sameData));

                    $("#detail_dialog" ).dialog({
                        title:comm.lang("coDeclaration").sameItemTitle,
                        width:"1000",
                        modal:true,
                        closeIcon : true,
                        buttons:{
                            "确定":function(){
                                $("#detail_dialog" ).dialog( "destroy" );
                            }
                        }
                    });
                }else{
                }
            });
        }
    };
    return aps_qybbsh;
});