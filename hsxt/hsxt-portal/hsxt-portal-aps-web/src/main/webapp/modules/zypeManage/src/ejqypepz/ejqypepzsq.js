define(['text!zypeManageTpl/ejqypepz/ejqypepzsq.html',
    'text!zypeManageTpl/ejqypepz/ejqypepzsq_tjbz_dialog.html',
    "zypeManageDat/zypeManage", "zypeManageLan"
], function (ejqypepzsqTpl, ejqypepzsq_tjbz_dialogTpl, zypeManage) {
    var aps_ejqypepzsq = {
        showPage: function () {
            $('#busibox').html(_.template(ejqypepzsqTpl));

            //加载地区缓存
            cacheUtil.findProvCity();

            //查询管理公司
            zypeManage.getEntResList({}, function (rsp) {
                var options = [];
                $.each(rsp.data, function (k, v) {
                    if (k == 0) {
                        options.push({name: v.mEntResNo, value: v.mEntResNo, selected: true});
                        //调用省份数据
                        aps_ejqypepzsq.getProvince(v.mEntResNo);
                        //管理公司配额详情
                        aps_ejqypepzsq.getManageAllot(v.mEntResNo);
                    } else {
                        options.push({name: v.mEntResNo, value: v.mEntResNo});
                    }
                });

                //填充数据
                $("#company").selectList({
                    options: options
                }).change(function (e) {
                    //管理公司配额详情
                    aps_ejqypepzsq.getManageAllot($(this).val());
                    aps_ejqypepzsq.getProvince($(this).val());
                });
            });

            //加载配置类型
            comm.initSelect("#type", comm.lang("zypeManage").quotaTypeEnume);

            //初始化分页数据
            var gridObj = aps_ejqypepzsq.pageShow(null);
            //添加数据
            $('#tj_btn').click(function () {
                $('#dialogBox').html(_.template(ejqypepzsq_tjbz_dialogTpl));

                /*弹出框*/
                $("#dialogBox").dialog({
                    title: comm.lang("zypeManage").add_remark,
                    width: "400",
                    height: "180",
                    modal: true,
                    buttons: {
                        "确定": function () {
                            var dl = this;

                            //获取已存在的申请配额
                            var applyQuota = gridObj.getAllRecords();
                            var $entResNo = $("#company").attr("optionvalue");	//管理公司号
                            var $provinceNo = $("#area").attr("optionvalue");  //省份代码
                            var $applyType = $("#type").attr("optionvalue");  //申请类型
                            var $applyNum = $("#txtApplyNum").val();  //申请数量
                            var $remark = $("#txtRemark").val();	//备注

                            //判断省份存在申请记录
                            if (!aps_ejqypepzsq.chkProAppAdd(applyQuota)) {
                                return false;
                            }

                            //添加json到数组
                            applyQuota.push({
                                "applyEntResNo": $entResNo,
                                "provinceNo": $provinceNo, "provinceName": comm.getProvNameByCode($provinceNo),
                                "applyType": parseInt($applyType), "applyTypeName": $("#type").val(),
                                "applyNum": parseInt($applyNum), "reqRemark": $remark
                            });

                            //添加到列表
                            gridObj = aps_ejqypepzsq.pageShow(applyQuota);

                            //统计二级区域配额数据
                            aps_ejqypepzsq.statisticsEntData(applyQuota);

                            $(dl).dialog("destroy");
                        },
                        "取消": function () {
                            $(this).dialog("destroy");
                        }
                    }
                });

            });

            //提交申请
            $('#tjsq_btn').click(function () {
                var quotaList = gridObj.getAllRecords();  //获取申请配额列表

                //判断有申请数据提交
                if (quotaList.length != 1) {
                    comm.alert({imgClass: 'tips_i', content: comm.lang("zypeManage").add_apply_record});
                    return false;
                }

                zypeManage.provinceQuotaApply(quotaList[0], function (rsp) {
                    var prompt = comm.lang("zypeManage").apply_sub_success;
                    comm.alert({
                        content: prompt,
                        callOk: function () {
                            $("#ejqypepzsq").click();
                        }
                    });
                });

            });


            //统计二级区域配额数据
            //aps_ejqypepzsq.statisticsEntData();
        },
        //分页显示数据
        pageShow: function (pageJson) {
            var isAutoLoad = true;
            //数据为空不自动加载
            if (pageJson == null || pageJson.length == 0) {
                isAutoLoad = false;
            }

            return $.fn.bsgrid.init('searchTable_1', {
                autoLoad: isAutoLoad,
                pageAll: true,
                pageSizeSelect: true,
                pageSize: 100,
                stripeRows: true,  //行色彩分隔
                displayBlankRows: false,   //显示空白行
                localData: pageJson

            });

        },//管理公司配额详情
        getManageAllot: function (mEntResNo) {
            zypeManage.manageAllotDetail({"mEntResNo": mEntResNo}, function (rsp) {
                var entInfo = rsp.data;
                $("#hglgsmc").text(comm.lang("zypeManage").entName + "：" + (entInfo.entCustName == null ? mEntResNo : entInfo.entCustName));
                $("#skyzypezs").text(entInfo.totalNum == null ? "0" : entInfo.totalNum);
                $("#syfppes").text(entInfo.allotedNum == null ? "0" : entInfo.allotedNum);
                $("#ssqzpes").text(entInfo.wApprNum == null ? "0" : entInfo.wApprNum);
                $("#sksqpes").text(entInfo.mayAppNum == null ? "0" : entInfo.mayAppNum);
            });
        },
        //根据管理公司获取省份
        getProvince: function (mEntResNo) {
            //获取省份
            cacheUtil.findCacheSystemInfo(function (sysInfo) {
                cacheUtil.findCacheProvinceByParent(sysInfo.countryNo, function (provinceList) {

                    //查询管理公司可以进行分配或调整配额的二级区域(省)
                    zypeManage.getProvinceNoAllot({"mEntResNo": mEntResNo}, function (rsp) {
                        var options = [];
                        if (rsp.data != undefined && rsp.data != null) {
                            $.each(rsp.data, function (k, v) {
                                $(provinceList).each(function (i2, v2) {
                                    var k = 0;
                                    if (v == v2.provinceNo) {
                                        if (k == 0) {
                                            options.push({name: v2.provinceName, value: v2.provinceNo, selected: true});
                                        } else {
                                            options.push({name: v2.provinceName, value: v2.provinceNo});
                                        }
                                        k++;
                                    }
                                });
                            });
                        }

                        //填充数据前清空存在的文本值
                        $("#area").attr({"optionvalue": "", "value": ""});
                        $("#txtApplyNum").val("");

                        //填充省份数据
                        $("#area").selectList({
                            options: options,
                            optionWidth: 120, //列表宽度
                            optionHeight: 200//列表高度
                        });
                    });
                });
            });
        },
        //验证省份申请新增
        chkProAppAdd: function (applyJson) {
            var reg = /^[1-9]+[0-9]*$/;  //数字格式规范：首个数字必须大于0
            var $entResNo = $("#company").attr("optionvalue");	//管理公司号
            var $appNum = $("#txtApplyNum").val(); //申请数量
            var $provinceNo = $("#area").attr("optionvalue");  //省份代码
            var $applyType = $("#type").attr("optionvalue");  //申请类型
            var $mayApplyNum = parseInt($("#sksqpes").text()); //可申请配额数
            var $alrApplyNum = parseInt($("#ssqysqzjpes").text()); //已申请配额数
            var $mayAllocatedNum = parseInt($("#syfppes").text()); //已分配配额数

            if (!reg.test($appNum)) {
                comm.alert({imgClass: 'tips_i', content: comm.lang("zypeManage").input_apply_quota_num});
                return false;
            }

            if ($provinceNo == "" || $provinceNo == undefined) {
                comm.alert({imgClass: 'tips_i', content: comm.lang("zypeManage").select_apply_pro});
                return false;
            }

            if ($applyType == "" || $applyType == undefined) {
                comm.alert({imgClass: 'tips_i', content: comm.lang("zypeManage").select_cfg_type});
                return false;
            }

            if (applyJson.length > 0) {
                comm.alert({imgClass: 'tips_i', content: comm.lang("zypeManage").no_exist_ans});
                return false;
            }

            //首次配额、增加配额判断申请数量合法
            if ($applyType != "3" && $alrApplyNum + parseInt($appNum) > $mayApplyNum) {
                comm.alert({imgClass: 'tips_i', content: comm.lang("zypeManage")[12001]});
                return false;
            }

            //减少配额判断申请数量合法
            if ($applyType == "3" && parseInt($appNum) > $mayAllocatedNum) {
                comm.alert({imgClass: 'tips_i', content: comm.lang("zypeManage")[12002]});
                return false;
            }
            return true;
        },
        //统计管理公司配额申请数据
        statisticsEntData: function (applyJson) {

            var ysqzjpes = 0; //已申请增加配额数
            var ysqjspes = 0; //已申请减少配额数

            //判断数据存在
            if (applyJson != null && applyJson != undefined) {
                //循环获取
                $.each(applyJson, function (k, v) {
                    if (v.applyType == "3") {
                        ysqjspes += parseInt(v.applyNum);
                    } else {
                        ysqzjpes += parseInt(v.applyNum);
                    }
                });
            }

            $("#ssqglgsh").text($("#company").val());
            $("#ssqysqzjpes").text(ysqzjpes);
            $("#ssqysqjspes").text(ysqjspes);
            $("#ssqksqpes").text($("#sksqpes").text() - ysqzjpes + ysqjspes);

        }
    };
    return aps_ejqypepzsq;
});
