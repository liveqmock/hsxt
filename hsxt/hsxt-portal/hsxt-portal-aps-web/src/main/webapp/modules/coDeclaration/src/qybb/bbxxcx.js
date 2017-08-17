define(['text!coDeclarationTpl/qybb/bbxxcx.html',
    'text!coDeclarationTpl/qybb/bbxxcx/bbxxcx_dialog_1.html',
    'text!coDeclarationTpl/qybb/bbxxcx/bbxxcx_dialog_2.html',
    'text!coDeclarationTpl/qybb/same_item_dialog.html',
    'coDeclarationDat/qybb/qybb',
    'coDeclarationDat/qybb/bbxxcx',
    'coDeclarationLan'], function (bbxxcxTpl, bbxxcx_dialog_1Tpl, bbxxcx_dialog_2Tpl,same_item_dialogTpl,qybbAjax, dataModoule) {
    var aps_bbxxcx = {
        showPage: function () {
            this.initForm();
        },
        /**
         * 初始化页面
         */
        initForm: function () {
            $('#busibox').html(_.template(bbxxcxTpl));
            cacheUtil.findCacheSystemInfo(function (sysRes) {
                cacheUtil.findCacheProvinceByParent(sysRes.countryNo, function (provArray) {
                    comm.initProvSelect('#search_provinceNo', provArray, 100, null, {name: '', value: ''});
                });
            });
            //时间控件
            comm.initBeginEndTime('#search_startDate', '#search_endDate');
            comm.initSelect('#search_status', comm.lang("coDeclaration").declaraStatusEnum, null, null, {name: '全部', value: ''});
            $('#queryBtn').click(function () {
                aps_bbxxcx.initData();
            });
        },
        /**
         * 初始化数据
         */
        initData: function () {
            var valid = comm.queryDateVaild("search_form");
            if (!valid.form()) {
                return false;
            }
            var params = {
                search_entName: $("#search_entName").val(),
                search_linkman: $("#search_linkman").val(),
                search_startDate: $("#search_startDate").val(),
                search_endDate: $("#search_endDate").val(),
                search_provinceNo: comm.removeNull($("#search_provinceNo").attr('optionValue')),
                search_status: comm.removeNull($("#search_status").attr('optionValue'))
            };
            cacheUtil.findProvCity();
            dataModoule.findFilingList(params, this.detail);
        },
        /**
         * 查看详细
         */
        detail: function (record, rowIndex, colIndex) {
            if (colIndex == 1) {
                return comm.getRegionByCode(record['countryNo'], record['provinceNo'], record['cityNo']);
            }
            if (colIndex == 5) {
                if (record['existSameItem']) {
                    return $('<a class="existSameItem">查看</a>').click(function () {
                        aps_bbxxcx.querySameItem(record);
                    });
                } else {
                    return comm.lang("coDeclaration").no;
                }
            }
            if (colIndex == 6) {
                return comm.getNameByEnumId(record['status'], comm.lang("coDeclaration").declaraStatusEnum);
            }
            return $('<a data-type="' + record['applyId'] + '" >查看</a>').click(function (e) {
                var status = record['status'];
                var params = {applyId: record['applyId']};
                var tpl = (status == 1) ? bbxxcx_dialog_1Tpl : bbxxcx_dialog_2Tpl;
                //查看详情
                dataModoule.findFilingById(params, function (res_) {
                    $('#qybbcx_dialog > p').empty().html(_.template(tpl, res_.data.FILING_APP));
                    $("#region").html(comm.getRegionByCode(res_.data.FILING_APP['countryNo'], res_.data.FILING_APP['provinceNo'], res_.data.FILING_APP['cityNo']));
                    $("#legalType").html(comm.getNameByEnumId(res_.data.FILING_APP.legalType, comm.lang("coDeclaration").idTypeEnum));
                    if (status != 1) {
                        $("#status").html(comm.getNameByEnumId(res_.data.FILING_APP.status, comm.lang("coDeclaration").declaraStatusEnum));
                        //获取报备操作员
                        cacheUtil.searchOperByCustId(res_.data.FILING_APP.createdBy, function (res) {
                            $("#createdBy").html(comm.getOperNoName(res));
                        });
                        //获取审核操作员
                        cacheUtil.searchOperByCustId(res_.data.FILING_APP.updatedBy, function (res) {
                            $("#updatedBy").html(comm.getOperNoName(res));
                        });
                    } else {
                        //获取报备操作员
                        cacheUtil.searchOperByCustId(res_.data.FILING_APP.createdBy, function (res) {
                            $("#createdBy").html(comm.getOperNoName(res));
                        });
                    }
                    if (res_.data.FILING_APP.existSameItem) {
                        $("#remark").html("与其它企业存在相同项");
                    }
                    //载入股东信息
                    comm.getEasyBsGrid("tableDetail1", res_.data.FILING_SH, function (record_, rowIndex_, colIndex_, options_) {
                        if (colIndex_ == 0) {
                            return rowIndex_ + 1;
                        } else if (colIndex_ == 3) {
                            return comm.getNameByEnumId(record_['idType'], comm.lang("coDeclaration").idTypeEnum);
                        } else if (colIndex_ == 2) {
                            return comm.getNameByEnumId(record_['shType'], comm.lang("coDeclaration").shTypeEnum);
                        }
                    });
                    //显示附件信息
                    var files = res_.data.FILING_APT;
                    var picSize = [];
                    if (files) {
                        for (var k in files) {
                            if(files[k].fileId) {
                                var title = comm.getNameByEnumId(files[k].aptType, comm.lang("coDeclaration").aptTypeEnum);
                                comm.initPicPreview("#picFileId-" + files[k].aptType, files[k].fileId, title);
                                picSize.push(files[k].aptType);
                            }
                        }
                    }
                    //没有的附件，隐藏点击链接
                    for (var i = 1; i <= 5; i++) {
                        if (!_.contains(picSize, i)) {
                            $('#picFileId-' + i).hide();
                        }
                    }
                    $('#qybbcx_dialog').dialog({
                        width: 1000, title: '报备信息详情', buttons: {
                            '确定': function () {
                                $(this).dialog('destroy')
                            }
                        }
                    });
                });
            });
        },
        /**
         * 查看相同项
         */
        querySameItem: function (record) {
            qybbAjax.findSameItem({applyId: record.applyId}, function (resp) {
                if (resp.data) {
                    var sameData = $.extend({}, resp.data);
                    sameData.area = comm.getRegionByCode(sameData.countryNo, sameData.provinceNo, sameData.cityNo);
                    if (sameData.sameShareHolders && sameData.sameShareHolders.length > 0) {
                        var group = [], count = [];
                        $.each(sameData.sameShareHolders, function (index, h) {
                            var name = $.trim(h[2]) + '-' + $.trim(h[3]) + '-' + $.trim(h[4]);
                            if (!_.contains(group, name)) {
                                group.push(name);
                            }
                        });
                        $.each(sameData.sameShareHolders, function (index, sh) {
                            var shn = $.trim(sh[2]) + '-' + $.trim(sh[3]) + '-' + $.trim(sh[4]);
                            for (var j = 0; j < group.length; j++) {
                                if (group[j] == shn) {
                                    if (count[j] || count[j] >= 1) {
                                        count[j] = count[j] + 1;
                                    } else {
                                        count[j] = 1;
                                    }
                                }
                            }
                        });
                        sameData.group = group;
                        sameData.count = count;
                    }
                    $('#qybbcx_dialog > p').empty().html(_.template(same_item_dialogTpl, sameData));

                    $("#qybbcx_dialog").dialog({
                        title: comm.lang("coDeclaration").sameItemTitle,
                        width: "1000",
                        modal: true,
                        closeIcon: true,
                        buttons: {
                            "确定": function () {
                                $("#qybbcx_dialog").dialog("destroy");
                            }
                        }
                    });
                } else {
                }
            });
        }
    };
    return aps_bbxxcx;
});