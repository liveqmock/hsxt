define(['text!coDeclarationTpl/kqxtyw/qyxtzcxx.html',
    'coDeclarationDat/kqxtyw/qyxtzcxx',
    'coDeclarationLan'
], function (qyxtzcxxTpl, dataModoule) {
    return {
        showPage: function () {
            this.initData();
        },
        /**
         * 初始化页面
         */
        initForm: function (data) {
            $('#infobox').html(_.template(qyxtzcxxTpl, data));
            var custType = $("#custType").val();
            var qyxtzcxx = null;//修改js路径
            if (custType == "2") {
                $("#view-qyxtzcxx_sbTr").hide();
                $("#view-qyxtzcxx_nyqyhshTr").show();
                $("#view-toBuyResRangeText").html("启用资源类型");
                $("#view-toBuyResRange").html(comm.getNameByEnumId(data.toBuyResRange, comm.lang("coDeclaration").cyToBuyResRangeEnum));
                $("#view-toCustType").html(comm.getNameByEnumId(2, comm.lang("coDeclaration").toCustTypeEnum));
                $("#view-availQuotaText").html("成员企业可用配额");
                $("#view-spreadEntResNoText").html("服务公司互生号");
                $("#view-spreadEntNameText").html("所属服务公司名称");
                qyxtzcxx = "coDeclarationSrc/kqxtyw/qyxtzcxx_cyqy";
                this.findResNoListAndQuota(2, data.spreadEntResNo, data.toBuyResRange);
            } else if (custType == "3") {
                $("#view-qyxtzcxx_sbTr").hide();
                $("#view-qyxtzcxx_nyqyhshTr").show();
                $("#view-toBuyResRangeText").html("启用资源类型");
                $("#view-toBuyResRange").html(comm.getNameByEnumId(data.toBuyResRange, comm.lang("coDeclaration").toBuyResRangeEnum));
                $("#view-toCustType").html(comm.getNameByEnumId(3, comm.lang("coDeclaration").toCustTypeEnum));
                $("#view-availQuotaText").html("托管企业可用配额");
                $("#view-spreadEntResNoText").html("服务公司互生号");
                $("#view-spreadEntNameText").html("所属服务公司名称");
                qyxtzcxx = "coDeclarationSrc/kqxtyw/qyxtzcxx_tgqy";
                this.findResNoListAndQuota(3, data.spreadEntResNo, data.toBuyResRange);
            } else {
                $("#view-toBuyResRangeText").html("经营类型");
                $("#view-toBuyResRange").html(comm.getNameByEnumId(data.toBusinessType, comm.lang("common").toBusinessTypes));
                $("#view-toCustType").html(comm.getNameByEnumId(4, comm.lang("coDeclaration").toCustTypeEnum));
                $("#view-availQuotaText").html("服务公司可用配额");
                $("#view-spreadEntResNoText").html("管理公司互生号");
                $("#view-spreadEntNameText").html("所属管理公司名称");
                $("#view-qyxtzcxx_sbTr").show();
                $("#view-qyxtzcxx_nyqyhshTr").hide();
                qyxtzcxx = "coDeclarationSrc/kqxtyw/qyxtzcxx_fwgs";
                this.findEntInfo(data.spreadEntResNo);
                this.findManageEntAndQuota(data);
            }

            //获取地区信息
            cacheUtil.syncGetRegionByCode(data.countryNo, data.provinceNo, data.cityNo, "", function (resdata) {
                $("#view-placeName").html(resdata);
            });

            //点击查看页面取消按钮
            $('#view-qyxtzcxx_qx_btn').triggerWith('#' + $("#menuName").val());

            //修改按钮
            $('#view-qyxtzcxx_xg_btn').click(function () {
                $('#qyxtzcxxTpl').addClass('none');
                $('#qyxtzcxx_xgTpl').removeClass('none');
                require([qyxtzcxx], function (qyxtzcxx) {
                    qyxtzcxx.showPage(data);
                });
            });

            //初始化积分增值设置
            this.findIncrement(data);
        },
        /**
         * 初始化数据
         */
        initData: function () {
            var self = this;
            dataModoule.findDeclareByApplyId({"applyId": $("#applyId").val()}, function (res) {
                self.initForm(res.data);
            });
        },
        /**
         * 查询企业配额数和可用互生号列表
         * @param toCustType 申请类别
         * @param spreadEntResNo 服务公司互生号
         * @param buyResRange 启用资源类型
         */
        findResNoListAndQuota: function (toCustType, spreadEntResNo, buyResRange) {
            $("#view-spreadEntResNo").html(comm.removeNull(spreadEntResNo));
            var self = this;
            if (comm.removeNull(buyResRange) == "" || comm.removeNull(spreadEntResNo) == "") {
                return;
            }
            var params = {};
            params.toCustType = toCustType;//申报类别
            params.buyResRange = buyResRange;//启用资源类型
            params.serResNo = spreadEntResNo;//服务公司互生号
            dataModoule.findResNoListAndQuota(params, function (res) {
                $("#view-availQuota").html(comm.removeNull(res.data.availQuota));
                $("#view-spreadEntName").html(comm.removeNull(res.data.serCustName));
            });
        },
        /**
         * 依据国家、省份、城市获取服管理公司信息
         * @param data 注册信息
         */
        findManageEntAndQuota: function (data) {
            var self = this;
            if (comm.removeNull(data.countryNo) == "" || comm.removeNull(data.provinceNo) == "" || comm.removeNull(data.cityNo) == "") {
                return;
            }
            var params = {};
            params.countryNo = data.countryNo;
            params.provinceNo = data.provinceNo;
            params.cityNo = data.cityNo;
            dataModoule.findManageEntAndQuota(params, function (res) {
                if (res.data) {
                    var entName = (res.data.manageEnt) ? res.data.manageEnt.entName : "";//管理公司名称
                    var entResNo = (res.data.manageEnt) ? res.data.manageEnt.entResNo : "";//管理公司互生号
                    $("#view-availQuota").html(comm.removeNull(res.data.quota));
                    $("#view-spreadEntResNo").html(comm.removeNull(entResNo));
                    $("#view-spreadEntName").html(comm.removeNull(entName));
                }
            });
        },
        /**
         * 依据互生号查询企业信息
         * @param spreadEntResNo 申报单位互生号
         */
        findEntInfo: function (spreadEntResNo) {
            if (!spreadEntResNo) {
                return;
            }
            $("#view-fw_spreadEntResNo").html(spreadEntResNo);
            dataModoule.findEntInfo({spreadEntResNo: spreadEntResNo}, function (res) {
                $("#view-fw_spreadEntCustName").html(comm.removeNull(res.data.entName));
            });
        },
        /**
         * 查询消费增值信息
         * @param resNo 企业互生号
         * @param chooseId 选中哪一个单选框
         */
        findIncrement: function (data) {
            if (comm.removeNull(data.toPnodeResNo) == "") {
                return;
            }
            var self = this;
            $("#view-zzdqyglh").val(data.toPnodeResNo);
            var score = comm.removeNull(data.toInodeResNo);//1增值分配点
            var chooseId = "view-increment-1" + ((data.toInodeLorR == 0) ? "-l" : "-r");
            if (score.indexOf("R") != -1) {//3增值分配点
                chooseId = "view-increment-3" + ((data.toInodeLorR == 0) ? "-l" : "-r");
            } else if (score.indexOf("L") != -1) {//2增值分配点
                chooseId = "view-increment-2" + ((data.toInodeLorR == 0) ? "-l" : "-r");
            }
            $("input[name='view-increment']").attr('checked', false);
            if (chooseId) {
                $("#" + chooseId).attr("checked", true);
            }
            var spreadEntResNo = data.spreadEntResNo;
            var toMResNo = data.toMResNo;
            var toPnodeResNo = data.toPnodeResNo;
            var serRegex = /^(([1-9]\d){1}|(\d[1-9]){1})([1-9]\d{2}|\d{2}[1-9]|\d[1-9]\d)([0]{6})$/;
            if (serRegex.test(spreadEntResNo) && toMResNo.substring(0, 2) == spreadEntResNo.substring(0, 2)) {
                //申报公司和被申报服务公司是属于同一个管理公司
                dataModoule.findIncrement({spreadEntResNo: spreadEntResNo, subResNo: toPnodeResNo}, function (res) {
                    if (res.data) {
                        self.fillTable(res.data.incrementMap);
                    }
                });
            } else if (toMResNo != toPnodeResNo) {
                dataModoule.findIncrement({spreadEntResNo: toMResNo, subResNo: toPnodeResNo}, function (res) {
                    if (res.data) {
                        self.fillTable(res.data.incrementMap);
                    }
                });
            }
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
                        $("#view-increment-3-lP").html(data[k].lP || data[k].lp);
                        $("#view-increment-3-lCount").html(data[k].lCount || data[k].lcount);
                        $("#view-increment-3-rP").html(data[k].rP || data[k].rp);
                        $("#view-increment-3-rCount").html(data[k].rCount || data[k].rcount);
                    } else if (k.indexOf("L") > 0) {
                        $("#view-increment-2-lP").html(data[k].lP || data[k].lp);
                        $("#view-increment-2-lCount").html(data[k].lCount || data[k].lcount);
                        $("#view-increment-2-rP").html(data[k].rP || data[k].rp);
                        $("#view-increment-2-rCount").html(data[k].rCount || data[k].rcount);
                    } else {
                        $("#view-increment-1-lP").html(data[k].lP || data[k].lp);
                        $("#view-increment-1-lCount").html(data[k].lCount || data[k].lcount);
                        $("#view-increment-1-rP").html(data[k].rP || data[k].rp);
                        $("#view-increment-1-rCount").html(data[k].rCount || data[k].rcount);
                    }
                    $("#view-toPnodeResNo").val(data[k].resNo);
                    $("#view-toPnodeCustId").val(data[k].custId);
                }
                $("#view-increment-1-table").hide();
                $("#view-increment-3-table").show();
            } else {
                for (var k in data) {
                    $("#view-increment-lP").html(data[k].lP || data[k].lp);
                    $("#view-increment-lCount").html(data[k].lCount || data[k].lcount);
                    $("#view-increment-rP").html(data[k].rP || data[k].rp);
                    $("#view-increment-rCount").html(data[k].rCount || data[k].rcount);
                    $("#view-toPnodeResNo").val(data[k].resNo);
                    $("#view-toPnodeCustId").val(data[k].custId);
                }
                $("#view-increment-1-table").show();
                $("#view-increment-3-table").hide();
            }
        }
    }
});