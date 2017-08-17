define(['text!coDeclarationTpl/qysbxz/tab.html',
    'coDeclarationSrc/qysbxz/change_custtype',
    'coDeclarationSrc/qysbxz/qygsdjxx',
    'coDeclarationSrc/qysbxz/qylxxx',
    'coDeclarationSrc/qysbxz/qyyhzhxx',
    'coDeclarationSrc/qysbxz/qysczl',
    'coDeclarationDat/qysbxz/tab',
    'coDeclarationLan'
], function (tab, custtype, qygsdjxx, qylxxx, qyyhzhxx, qysczl, dataModoule) {
    var scsDeclare = {
        showPage: function (applyId, curStep, custType) {
            //初始化缓存数据
            scsDeclare.initData(applyId, curStep, custType);

            custtype.showPage();
            scsDeclare.initForm();
        },
        /**
         * 初始化缓存数据
         */
        initData: function (applyId, curStep, custType) {
            comm.delCache("coDeclaration", "entDeclare");
            comm.delCache("coDeclaration", "updateFlag");
            if (applyId) {
                comm.setCache("coDeclaration", "entDeclare", {"curStep": curStep, "isSubmit": false, "applyId": applyId, "custType": custType});
            } else {
                comm.setCache("coDeclaration", "entDeclare", {"curStep": 0, "isSubmit": false, "applyId": null});
            }
        },
        /**
         * 初始化页面及事件
         */
        initForm: function () {
            $('.operationsInner').html(_.template(tab));

            //企业系统注册信息
            $('#qysbxz_qytxzcxx').click(function (e) {
                if (comm.isNotEmpty(custtype.getApplyId()) && comm.getCache("coDeclaration", "updateFlag")) {
                    comm.confirm({
                        imgFlag: true,
                        title: comm.lang("coDeclaration").confirmJump,
                        content: comm.lang("coDeclaration").giveUpChanged,
                        callOk: function () {
                            custtype.showPage();
                            comm.liActive($('#qysbxz_qytxzcxx'));
                            comm.delCache("coDeclaration", "updateFlag");
                        },
                        callCancel: function () {

                        }
                    });
                } else {
                    custtype.showPage();
                    comm.liActive($('#qysbxz_qytxzcxx'));
                }
            }.bind(this)).click();

            //企业工商登记信息
            $('#qysbxz_qygsdjxx').click(function (e) {
                if (scsDeclare.gotoStep(2)) {
                    if (comm.isNotEmpty(custtype.getApplyId()) && comm.getCache("coDeclaration", "updateFlag")) {
                        comm.confirm({
                            imgFlag: true,
                            title: comm.lang("coDeclaration").confirmJump,
                            content: comm.lang("coDeclaration").giveUpChanged,
                            callOk: function () {
                                qygsdjxx.showPage();
                                comm.liActive($('#qysbxz_qygsdjxx'));
                                comm.delCache("coDeclaration", "updateFlag");
                            },
                            callCancel: function () {

                            }
                        });
                    } else {
                        qygsdjxx.showPage();
                        comm.liActive($('#qysbxz_qygsdjxx'));
                    }
                }
            }.bind(this));
            //企业联系信息
            $('#qysbxz_qylxxx').click(function (e) {
                if (scsDeclare.gotoStep(3)) {
                    if (comm.isNotEmpty(custtype.getApplyId()) && comm.getCache("coDeclaration", "updateFlag")) {
                        comm.confirm({
                            imgFlag: true,
                            title: comm.lang("coDeclaration").confirmJump,
                            content: comm.lang("coDeclaration").giveUpChanged,
                            callOk: function () {
                                qylxxx.showPage();
                                comm.liActive($('#qysbxz_qylxxx'));
                                comm.delCache("coDeclaration", "updateFlag");
                            },
                            callCancel: function () {

                            }
                        });
                    } else {
                        qylxxx.showPage();
                        comm.liActive($('#qysbxz_qylxxx'));
                    }
                }
            }.bind(this));

            //企业银行账户信息
            $('#qysbxz_qyyhzhxx').click(function (e) {
                if (scsDeclare.gotoStep(4)) {
                    if (comm.isNotEmpty(custtype.getApplyId()) && comm.getCache("coDeclaration", "updateFlag")) {
                        comm.confirm({
                            imgFlag: true,
                            title: comm.lang("coDeclaration").confirmJump,
                            content: comm.lang("coDeclaration").giveUpChanged,
                            callOk: function () {
                                qyyhzhxx.showPage();
                                comm.liActive($('#qysbxz_qyyhzhxx'));
                                comm.delCache("coDeclaration", "updateFlag");
                            },
                            callCancel: function () {

                            }
                        });
                    } else {
                        qyyhzhxx.showPage();
                        comm.liActive($('#qysbxz_qyyhzhxx'));
                    }
                }
            }.bind(this));

            //企业上传资料
            $('#qysbxz_qysczl').click(function (e) {
                if (scsDeclare.gotoStep(5)) {
                    if (comm.isNotEmpty(custtype.getApplyId()) && comm.getCache("coDeclaration", "updateFlag")) {
                        comm.confirm({
                            imgFlag: true,
                            title: comm.lang("coDeclaration").confirmJump,
                            content: comm.lang("coDeclaration").giveUpChanged,
                            callOk: function () {
                                qysczl.showPage();
                                comm.liActive($('#qysbxz_qysczl'));
                                comm.delCache("coDeclaration", "updateFlag");
                            },
                            callCancel: function () {

                            }
                        });
                    } else {
                        qysczl.showPage();
                        comm.liActive($('#qysbxz_qysczl'));
                    }
                }
            }.bind(this));

            //申报提交
            $('#qysbxz_tj').click(function () {
                if (scsDeclare.gotoStep(4)) {
                    var params = {};
                    params.applyId = custtype.getApplyId();
                    //如果是编辑 且页面有修改 ，则询问是否保存后修改
                    if (comm.isNotEmpty(params.applyId) && comm.getCache("coDeclaration", "updateFlag")) {
                        comm.warn_alert(comm.lang("coDeclaration").hasChanged);
                        return;
                    }
                    comm.confirm({
                        imgFlag: true,
                        title: comm.lang("coDeclaration").confirmDeclareTitle,
                        content: comm.lang("coDeclaration").confirmDeclareContent,
                        callOk: function () {
                            dataModoule.submitDeclare(params, function (res) {
                                var entDeclare = comm.getCache("coDeclaration", "entDeclare");
                                entDeclare.isSubmit = true;

                                comm.delCache("coDeclaration", "updateFlag");

                                comm.alert({
                                    content: comm.lang("coDeclaration")[22000], callOk: function () {
                                        //跳转到企业申报查询页面
                                        $("li >a").each(function () {
                                            var arr = new Array();
                                            var menuurl = $(this).attr("menuurl");
                                            if (menuurl == "coDeclarationSrc/qysbcx/tab") {
                                                var id = $(this).attr("id");
                                                $("#" + id).trigger('click');
                                            }
                                        });
                                    }
                                });
                            });
                        },
                        callCancel: function () {

                        }
                    });
                }
            })
        },
        /**
         * 控制步骤
         * @param toStep 目标步骤
         */
        gotoStep: function (toStep) {
            var entDeclare = comm.getCache("coDeclaration", "entDeclare");
            if ((entDeclare.curStep + 1) >= toStep) {
                return true;
            }
            //申报必须按步骤申请，否则弹出警告
            /**
             * 提示信息
             * 1:'请先填写并保存企业系统注册信息.',
             * 2:'请先填写并保存企业工商业登记信息.',
             * 3:'请先填写并保存企业联系信息.',
             * 4:'请先填写并保存企业银行账户信息.',
             * 5:'请先填写并保存企业上传资料.'
             */
            comm.warn_alert(comm.lang("coDeclaration").entDeclareStepEnum[entDeclare.curStep + 1]);
            return false;
        }
    };
    return scsDeclare;
});

