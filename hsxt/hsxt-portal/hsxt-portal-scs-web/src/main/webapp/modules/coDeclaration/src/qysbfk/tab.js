define(['text!coDeclarationTpl/qysbfk/tab.html',
    'text!coDeclarationTpl/qysbfk/ckxq/skqr_dialog.html',
    'coDeclarationSrc/qysbfk/tgqysbfk',
    'coDeclarationSrc/qysbfk/cyqysbfk',
    'coDeclarationSrc/qysbfk/fwgssbfk',

    //子标签切换
    'coDeclarationSrc/qysbfk/ckxq/sbxx',
    'coDeclarationSrc/qysbfk/ckxq/qyxtzcxx',
    'coDeclarationSrc/qysbfk/ckxq/qygsdjxx',
    'coDeclarationSrc/qysbfk/ckxq/qylxxx',
    'coDeclarationSrc/qysbfk/ckxq/qyyhzhxx',
    'coDeclarationSrc/qysbfk/ckxq/qysczl',
    'coDeclarationSrc/qysbfk/ckxq/blztxx',
    'coDeclarationDat/qysbfk/tab'
], function (tab, skqr_dialogTpl, tgqysbfk, cyqysbfk, fwgssbfk, sbxx, qyxtzcxx, qygsdjxx, qylxxx, qyyhzhxx, qysczl, blztxx, dataModoule) {
    return {
        showPage: function () {

            $('.operationsInner').html(_.template(tab));
            //
            $('#qysbfk_tgqysbfk').click(function (e) {
                tgqysbfk.showPage();
                comm.liActive($('#qysbfk_tgqysbfk'));
                $('#ckxq_qx').triggerWith('#qysbfk_tgqysbfk');
            }.bind(this));

            //
            $('#qysbfk_cyqysbfk').click(function (e) {
                cyqysbfk.showPage();
                comm.liActive($('#qysbfk_cyqysbfk'));
                $('#ckxq_qx').triggerWith('#qysbfk_cyqysbfk');
            }.bind(this));
            //
            $('#qysbfk_fwgssbfk').click(function (e) {
                fwgssbfk.showPage();
                comm.liActive($('#qysbfk_fwgssbfk'));
                $('#ckxq_qx').triggerWith('#qysbfk_fwgssbfk');
            }.bind(this));

            //权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
            var match = comm.findPermissionJsonByParentId("030203000000");

            //遍历企业申报复核的子菜单设置默认选中
            for (var i = 0; i < match.length; i++) {
                //托管企业申报复核
                if (match[i].permId == '030203010000') {
                    $('#qysbfk_tgqysbfk').show();
                    $('#qysbfk_tgqysbfk').click();
                    //已经设置默认值
                    isModulesDefault = true;
                }
                //成员企业申报复核
                else if (match[i].permId == '030203020000') {
                    $('#qysbfk_cyqysbfk').show();
                    if (isModulesDefault == false) {
                        //默认选中
                        $('#qysbfk_cyqysbfk').click();
                        //已经设置默认值
                        isModulesDefault = true;
                    }
                }
                //服务公司申报复核
                else if (match[i].permId == '030203030000') {
                    $('#qysbfk_fwgssbfk').show();
                    if (isModulesDefault == false) {
                        //默认选中
                        $('#qysbfk_fwgssbfk').click();
                        //已经设置默认值
                        isModulesDefault = true;
                    }
                }
            }
            /*
             *  子标签切换
             */

            $('#ckxq_sbxx').click(function (e) {
                sbxx.showPage();
                comm.liActive($('#ckxq_sbxx'));
                $('#ckxq_xg').text('修　改');
                $("#skqr_tj").show();
            }.bind(this));

            $('#ckxq_qyxtzcxx').click(function (e) {
                qyxtzcxx.showPage();
                comm.liActive($('#ckxq_qyxtzcxx'));
                $('#ckxq_xg').text('修　改');
                $("#skqr_tj").show();
            }.bind(this));


            $('#ckxq_qygsdjxx').click(function (e) {
                qygsdjxx.showPage();
                comm.liActive($('#ckxq_qygsdjxx'));
                $('#ckxq_xg').text('修　改');
                $("#skqr_tj").show();
            }.bind(this));


            $('#ckxq_qylxxx').click(function (e) {
                qylxxx.showPage();
                comm.liActive($('#ckxq_qylxxx'));
                $('#ckxq_xg').text('修　改');
                $("#skqr_tj").show();
            }.bind(this));

            $('#ckxq_qyyhzhxx').click(function (e) {
                qyyhzhxx.showPage();
                comm.liActive($('#ckxq_qyyhzhxx'));
                $('#ckxq_xg').text('修　改');
                $("#skqr_tj").show();
            }.bind(this));

            $('#ckxq_qysczl').click(function (e) {

                qysczl.showPage();
                comm.liActive($('#ckxq_qysczl'));
                $('#ckxq_xg').text('修　改');
                $("#skqr_tj").show();
            }.bind(this));

            $('#ckxq_blztxx').click(function (e) {
                blztxx.showPage();
                comm.liActive($('#ckxq_blztxx'));
                $('#ckxq_xg').text('修　改');
                $("#skqr_tj").show();
            }.bind(this));


            //审核确认
            $('#skqr_tj').click(function () {
                $('#skqr_dialog>p').html(skqr_dialogTpl);
                $("input[name='isPass']").attr('checked', false);
                $('#skqr_dialog').dialog({
                    width: 480,
                    buttons: {
                        '确认': function () {
                            var params = {};
                            params.applyId = $("#editApplyId").val();
                            var pass = $("input[name='isPass']:checked").val();
                            if(!pass){
                                comm.warn_alert('请选择审核结果！');
                                return;
                            }
                            params.isPass = (pass == "0");
                            params.apprRemark = $("#apprRemark").val();
                            dataModoule.serviceApprDeclare(params, function (res) {
                                comm.alert({
                                    content: comm.lang("coDeclaration")[res.retCode], callOk: function () {
                                        $("#skqr_dialog").dialog('destroy');
                                        var custType = $("#custType").val();
                                        if (custType == "4") {
                                            $('#qysbfk_fwgssbfk').click();
                                        } else if (custType == "2") {
                                            $('#qysbfk_cyqysbfk').click();
                                        } else {
                                            $('#qysbfk_tgqysbfk').click();
                                        }
                                    }
                                });
                            });
                        },
                        '取消': function () {
                            $(this).dialog('destroy');
                        }
                    }
                });
            });
        }
    }
}); 

