define(['text!callCenterTpl/ldtp/ldtp_cyqy.html',
    'text!callCenterTpl/ldtp/ldtp_cyqy_xq.html',
    'callCenterDat/callCenter',
    'jquerydialogr'
], function (ldtp_cyqyTpl, ldtp_cyqy_xqTpl, callCenter) {
    return {
        showPage: function (tel, resno) {
            var self = this;
            $('#ldtp_dlg').html(ldtp_cyqyTpl);

            $('#ldtp_dlg').dialogr({
                title: "来电号码：" + tel + " 成员企业资源号：" + resno,  //设置弹屏title
                width: "1200",
                height: "800",
                autoOpen: false
            });
            $("#ldtp_cyqyTpl #latp_tel").text(tel);
            $("#ldtp_cyqyTpl #ldtp_resno").val(resno);
            /*下拉列表*/
            $("#ldtp_lx").selectList({
                width: 120,
                optionWidth: 125,
                borderWidth: 1,
                borderColor: '#CCC',
                options: [
                    {name: '运维类', value: '1'},
                    {name: '商务类', value: '2'},
                    {name: '诉建', value: '3'}
                ]
            });
            /*end*/
            if (navigator.userAgent.indexOf("MSIE 8.0") > 0) {
                //已接来电历史--电话工单
                Test.attachEvent("JS_Evt_PhoneWorkOrder", self.PhoneWorkOrder);
                Test.JS_GetPhoneWorkOrder(resno, "", 0, 0);//通话历史记录
                Test.attachEvent("JS_Evt_TrunkCallUser_UserOffHook", self.TrunkCallUser_UserOffHook);   //摘机事件绑定
            }

            //创建工单事件
            $("#ldtp_zgd_btn_per").click(function () {//转工单
                self.createWorkOrder(tel, "1");
            });
            $("#ldtp_ywc_btn_per").click(function () {//已完成
                self.createWorkOrder(tel, "2");
            });
            $("#ldtp_wx_btn_per").click(function () {//无效
                self.createWorkOrder(tel, "3");
            });

            var ldtp_cyqy_jbxx_btn = $('#ldtp_cyqy_jbxx_btn');
            ldtp_cyqy_jbxx_btn.children('li').children('a.a1').click(function () {
                ldtp_cyqy_jbxx_btn.find('span').hide();
                ldtp_cyqy_jbxx_btn.find('a').removeClass('hover');
                $(this).addClass('hover');
                $(this).siblings('span').show().mouseover(function () {
                    $(this).show();
                }).mouseout(function () {
                    $(this).hide();

                });
            }).mouseout(function () {
                ldtp_cyqy_jbxx_btn.find('span').hide();
                ldtp_cyqy_jbxx_btn.find('a').removeClass('hover');
            });


            //成员企业基本信息查询
            self.searchEntAllInfo_cyqy(resno);
            self.searchEntContactInfo_cyqy(resno, 0);//成员企业联系信息查询
            self.searchBankList_cyqy(resno);//银行卡列表信息
            self.getQkBankAccountList_cyqy(resno);//快捷支付卡列表信息
            self.getImptChangeInfo_cyqy(resno);//重要信息变更信息查询
            self.searchAccountBalance_cyqy(resno);//账户余额查询
            self.queryAnnualFeeInfo_cyqy(resno);//年费查询

            //详情事件
            $('#ldtp_cyqy_xq_btn').click(function () {
                $('#ldtp_cyqy_xqTpl').html(ldtp_cyqy_xqTpl);
                self.showTpl($('#ldtp_cyqy_xqTpl'));
                self.searchEntAllInfo_cyqy(resno);//成员企业信息
                self.searchEntContactInfo_cyqy(resno, 0);//成员企业联系信息查询
                self.searchBankList_cyqy(resno);//银行卡列表信息
                self.getQkBankAccountList_cyqy(resno);//快捷支付卡列表信息
                self.getImptChangeInfo_cyqy(resno);//重要信息变更信息查询
                self.searchAccountBalance_cyqy(resno);//账户余额查询

                $('#ldtp_cyqy_xq_fh_btn').click(function () {
                    self.showTpl($('#ldtp_cyqyTpl'));
                });
            });

            var ldtp_cyqy_zhxx_btn = $('#ldtp_cyqy_zhxx_btn');
            ldtp_cyqy_zhxx_btn.find('a').click(function () {

                ldtp_cyqy_zhxx_btn.find('span').hide();
                ldtp_cyqy_zhxx_btn.find('a').removeClass('hover');
                $(this).addClass('hover');
                $(this).siblings('span').show().mouseover(function () {
                    $(this).show();
                }).mouseout(function () {
                    $(this).hide();

                });

            }).mouseout(function () {
                ldtp_cyqy_zhxx_btn.find('span').hide();
                ldtp_cyqy_zhxx_btn.find('a').removeClass('hover');
            });

            $('#ldtp_dlg').dialogr('open');
            $('.ui-dialog ').css('border', '1px solid #b3c4ae');
            $('.ui-dialog-titlebar-close').addClass('ui-dialogr-titlebar-close');
            $('#dialog-maximize, #dialog-minimize, #dialog-restore').children('span').remove();
            /*end*/
            self.showEntCallerInfo(tel,resno);
        },
        showTpl: function (tplObj) {
            $('#ldtp_cyqyTpl, #ldtp_cyqy_xqTpl').addClass('none');
            tplObj.removeClass('none');
            $(".jqp-tabs_2").tabs();
        },
        //银行卡列表查询
        searchBankList_cyqy: function (resno) {
            var param = {resNo: resno};
            callCenter.getBankAccountList(param, function (request) {
                if (request.retCode == 22000) {
                    var data = request.data;
                    if (comm.isNotEmpty(request.data)) {
                        var bankListstr = "";
                        for (var i = 0; i < data.length; i++) {
                            bankListstr += data[i].bankAccNo.concat("&nbsp;&nbsp;" + data[i].bankName) + "<br/>";
                        }
                        $("#ldtp_bank_list").html(bankListstr);
                    }
                    var json_fwgs_yhzhxx = comm.isNotEmpty(request.data) ? request.data : null;
                    $.fn.bsgrid.init('searchTable_cyqy_yhzhxx_ldtp', {
                        pageSizeSelect: true,
                        //pageSize: 10,
                        stripeRows: true,  //行色彩分隔
                        displayBlankRows: false,   //显示空白行
                        localData: json_fwgs_yhzhxx,
                        operate: {
                            detail: function (record, rowIndex, colIndex, options) {
                                if(1==colIndex){
                                	var moneyType = (record.countryNo==156 ? '人民币': '未知');
                                	return moneyType;
                                }
                                if(3==colIndex){
                                	cacheUtil.syncGetRegionByCode(record.countryNo, record.provinceNo, record.cityNo, "", function (resdata) {
                                		$("#searchTable_cyqy_yhzhxx_ldtp   tr:eq("+(rowIndex+1)+") td:eq(3)").html(resdata);
                                    });
                                }
                                if(5==colIndex){
                                	var defAccount = (record.isDefaultAccount== 1? '是': '否');
                                	return defAccount;
                                }
                               
                            }
                        }

                    });
                } else {
                    $("#ldtp_bank_list").html("未绑定");
                    $.fn.bsgrid.init('searchTable_cyqy_yhzhxx_ldtp', {
                        localData: null
                    });
                }
            });
        },
        //快捷卡支付信息
        getQkBankAccountList_cyqy: function (resno) {
            var param = {resNo: resno};
            callCenter.getQkBankAccountList(param, function (request) {
                if (request.retCode == 22000) {
                    var data = request.data;
                    if (comm.isNotEmpty(data)) {
                        var bankListstr = "";
                        for (var i = 0; i < data.length; i++) {
                            bankListstr += data[i].bankCardNo.concat("&nbsp;&nbsp;" + data[i].bankName) + "<br/>";
                        }
                        $("#ldtp_kjzf_list_cyqy").html(bankListstr);
                    } else {
                        $("#ldtp_kjzf_list_cyqy").html("未绑定");
                    }
                } else {
                    $("#ldtp_kjzf_list_cyqy").html("未绑定");
                }
            });
        },
        //成员企业重要信息变更查询
        getImptChangeInfo_cyqy: function (resno) {
            var param = {resNo: resno};
            callCenter.getImptChangeInfo(param, function (request) {
                if (request.retCode == 22000) {
                    var data = request.data != "" ? request.data : null;
                    if (comm.isNotEmpty(request.data)) {
                        $("#ldtp_smrz_zyxxbg").text(comm.langConfig.common.importantChanteStatus[data.status]);//互生卡补办状态//未完成
                    } else {
                        $("#ldtp_smrz_zyxxbg").text("无记录");//{0: "待审批", 1: "通过地区平台初审", 2: "通过地区平台复核", 3: "申请驳回"}
                    }
                }
            });
        },
        //年费查询
        queryAnnualFeeInfo_cyqy: function (resno) {
            var param = {resNo: resno};
            callCenter.getAnnualFeeInfo(param, function (request) {
                if (request.retCode == 22000) {
                    var data = request.data != "" ? request.data : null;
                    if (comm.isNotEmpty(request.data)) {
                        $("#ldtp_b_yearFee_good").text(data.endDate);//年费有效期
                    } else {
                        $("#ldtp_b_yearFee_good").text("无记录");//
                    }
                } else {
                    $("#ldtp_b_yearFee_good").text("无记录");//
                }
            });
        },

        //成员企业信息查询
        searchEntAllInfo_cyqy: function (resno) {
            var self = this;
            var param = {resNo: resno};
            callCenter.searchEntAllInfo(param, function (request) {
                if (request.retCode == 22000) {
                    var data = request.data;
                    //工单自动补全
                    $("#ldtp_hsh").val(resno);
                   /* $("#ldtp_mc").val(data.authInfo.userName);
                    $("#ldtp_username").val(data.authInfo.userName);*/
                    //系统登记信息
                    $("#cyqy_resno").text(resno);//企业互生号
                    $("#cyqy_create_date").text(data.extendInfo.buildDate);//系统开启日期
                    $("#cyqy_cn_name").text(data.baseInfo.entName);//企业中文名称
                    $("#ldtp_username").val(data.baseInfo.entName);
                    $("#ldtp_mc").val(data.baseInfo.entName);
                    $("#cyqy_en_name").text(comm.isEmpty(data.baseInfo.entNameEn) ? "" : data.baseInfo.entNameEn);//企业英文名称
                    cacheUtil.syncGetRegionByCode(data.baseInfo.countryCode, data.baseInfo.provinceCode, data.baseInfo.cityCode, "", function (resdata) {
                        $("#cyqy_area").html(resdata);//所属地区
                    });
                    $("#cyqy_currency").html(data.baseInfo.countryCode == "156" ? "人民币" : "未知");//币种
                    $("#cyqy_custType").html(data.extendInfo.businessType== 1? '连锁企业' : '普通企业');//经营类型
                    //工商登记信息
                    $("#cyqy_gsdjxx tr:eq(0) td:odd:eq(0)").html(data.baseInfo.entName); //企业名称
                    $("#cyqy_gsdjxx tr:eq(0) td:odd:eq(1)").html(data.baseInfo.contactAddr);  //企业地址
                    $("#cyqy_gsdjxx tr:eq(1) td:odd:eq(0)").html(data.mainInfo.creName); //法人代表
                    //$("#cyqy_gsdjxx tr:eq(1) td:odd:eq(1)").html(data.mainInfo.contactPhone);//联系电话
                    //$("#cyqy_gsdjxx tr:eq(2) td:odd:eq(0)").html(data.mainInfo.creType);//法人代表证件类型
                    //$("#cyqy_gsdjxx tr:eq(2) td:odd:eq(1)").html(data.mainInfo.creNo);//法人代表证件号码
                    $("#cyqy_gsdjxx tr:eq(2) td:odd:eq(0)").html(data.baseInfo.nature);//企业类型
                    $("#cyqy_gsdjxx tr:eq(2) td:odd:eq(1)").html(data.mainInfo.busiLicenseNo);//营业执照注册号
                    //$("#cyqy_gsdjxx tr:eq(4) td:odd:eq(0)").html(data.mainInfo.orgCodeNo);//组织机构代码证号
                    //$("#cyqy_gsdjxx tr:eq(4) td:odd:eq(1)").html(data.mainInfo.taxNo);//纳税人识别号
                    $("#cyqy_gsdjxx tr:eq(3) td:odd:eq(0)").html(data.extendInfo.buildDate);//成立日期
                    $("#cyqy_gsdjxx tr:eq(3) td:odd:eq(1)").html(data.extendInfo.endDate);//营业期限
                    $("#cyqy_gsdjxx tr:eq(4) td:odd:eq(0)").html(data.extendInfo.businessScope);//经营范围
                    $("#ldtp_cyqy_busiLicenseImg").attr("src", comm.getFsServerUrl(data.extendInfo.busiLicenseImg));//营业执照扫描件

                    //联系信息
                    $("#cyqy_lxxx tr:eq(0) td:odd:eq(0)").html(data.mainInfo.contactPerson);//联系人姓名
                    $("#cyqy_lxxx tr:eq(0) td:odd:eq(1)").html(data.mainInfo.contactPhone);//联系人手机
                    $("#cyqy_lxxx tr:eq(1) td:odd:eq(0)").html(data.baseInfo.contactAddr);//联系地址
                    $("#cyqy_lxxx tr:eq(1) td:odd:eq(1)").html(data.baseInfo.postCode);//邮政编码
                    $("#cyqy_lxxx tr:eq(2) td:odd:eq(0)").html(data.baseInfo.contactEmail);//企业安全邮箱
                    //委托授权书
                    var abc = comm.getFsServerUrl(data.mainInfo.authProxyFile);
                    $("#ldtp_cyqy_authProxyFile").attr("src", comm.getFsServerUrl(data.mainInfo.authProxyFile));

                    //银行账户信息--专用接口
                    //实名认证信息
                    $("#ldtp_smrz_cyqy").html(data.statusInfo.isRealnameAuth == 0 ? "未认证" : ("已认证".concat("&nbsp;" + data.baseInfo.buildDate)));//实名认证日期缺少接口
                    //申报单位信息
                    self.searchEntContactInfo_cyqy(data.extendInfo.applyEntResNo, 1);//申报单位
                    self.searchSuperEntInfo_cyqy(data.extendInfo.superEntResNo);//所属服务公司


                    /*******************************成员企业详情*************************************************/
                    $("#ldtp_b_xq tr td:odd:eq(0)").html(resno);	//企业互生号
                    $("#ldtp_b_xq tr td:odd:eq(1)").html(data.baseInfo.entName);//企业名称
                    $("#ldtp_b_xq tr td:odd:eq(2)").html(comm.langConfig.common.entStatus[data.statusInfo.baseStatus]);//企业状态
                    //系统登记信息
                    $("#ldtp_b_xq_xtdjxx tr:eq(0) td:odd:eq(0)").html(data.extendInfo.entResNo);//企业资源号
                    $("#ldtp_b_xq_xtdjxx tr:eq(0) td:odd:eq(1)").html(data.extendInfo.buildDate);//企业注册日期
                    $("#ldtp_b_xq_xtdjxx tr:eq(1) td:odd:eq(0)").html(data.baseInfo.entName);//企业中文名称
                    $("#ldtp_b_xq_xtdjxx tr:eq(1) td:odd:eq(1)").html(data.extendInfo.businessType== 1? '连锁企业' : '普通企业');//经营类型
                    cacheUtil.syncGetRegionByCode(data.baseInfo.countryCode, data.baseInfo.provinceCode, data.baseInfo.cityCode, "", function (resdata) {
                        $("#ldtp_b_xq_xtdjxx tr:eq(2) td:odd:eq(0)").html(resdata);//获取地区信息
                    });
                    $("#ldtp_b_xq_xtdjxx tr:eq(2) td:odd:eq(1) span").html(data.baseInfo.countryCode == "156" ? "人民币" : "未知");//币种

                    //工商登记信息
                    $("#ldtp_b_xq_gsdjxx tr:eq(0) td:odd:eq(0)").html(data.baseInfo.entName); //企业名称
                    $("#ldtp_b_xq_gsdjxx tr:eq(0) td:odd:eq(1)").html(data.baseInfo.contactAddr);  //企业地址
                    $("#ldtp_b_xq_gsdjxx tr:eq(1) td:odd:eq(0)").html(data.mainInfo.creName); //法人代表
//                    $("#ldtp_b_xq_gsdjxx tr:eq(1) td:odd:eq(1)").html(data.mainInfo.contactPhone);//联系电话
                    $("#ldtp_b_xq_gsdjxx tr:eq(2) td:odd:eq(0)").html(data.baseInfo.nature);//企业类型
                    $("#ldtp_b_xq_gsdjxx tr:eq(2) td:odd:eq(1)").html(data.mainInfo.busiLicenseNo);//营业执照号
//                    $("#ldtp_b_xq_gsdjxx tr:eq(3) td:odd:eq(0)").html(data.mainInfo.orgCodeNo);//组织机构代码证号
//                    $("#ldtp_b_xq_gsdjxx tr:eq(3) td:odd:eq(1)").html(data.mainInfo.taxNo);//纳税人识别号
                    $("#ldtp_b_xq_gsdjxx tr:eq(3) td:odd:eq(0)").html(data.extendInfo.buildDate);//成立日期
                    $("#ldtp_b_xq_gsdjxx tr:eq(3) td:odd:eq(1)").html(data.extendInfo.endDate);//营业期限
                    $("#ldtp_b_xq_gsdjxx tr:eq(4) td:odd:eq(0)").html(data.extendInfo.businessScope);//经营范围
                    $("#ldtp_cyqy_xq_busiLicenseImg").attr("src", comm.getFsServerUrl(data.extendInfo.busiLicenseImg));//营业执照扫描件
                    //联系信息
                    $("#ldtp_b_xq_lxxx tr:eq(0) td:odd:eq(0)").html(data.mainInfo.contactPerson);     //联系人姓名
                    $("#ldtp_b_xq_lxxx tr:eq(0) td:odd:eq(1)").html(data.mainInfo.contactPhone); //联系人手机
                    $("#ldtp_b_xq_lxxx tr:eq(1) td:odd:eq(0)").html(data.baseInfo.contactAddr); //联系地址
                    $("#ldtp_b_xq_lxxx tr:eq(1) td:odd:eq(1)").html(data.baseInfo.postCode); //邮政编码
//                    $("#ldtp_b_xq_lxxx tr:eq(2) td:odd:eq(0)").html(data.baseInfo.webSite); //企业网址
                    $("#ldtp_b_xq_lxxx tr:eq(2) td:odd:eq(0)").html(data.baseInfo.contactEmail); //企业安全邮箱
                    //委托授权书
                    $("#ldtp_cyqy_xq_authProxyFile").attr("src", comm.getFsServerUrl(data.mainInfo.authProxyFile));
//                    $("#ldtp_b_xq_lxxx tr:eq(3) td:odd:eq(0)").html(data.baseInfo.officeTel); //办公电话
//                    $("#ldtp_b_xq_lxxx tr:eq(3) td:odd:eq(1)").html(data.baseInfo.officeFax); //传真号码
//                    $("#ldtp_b_xq_lxxx tr:eq(4) td:odd:eq(0)").html(data.baseInfo.contactDuty); //联系人职务
//                    $("#ldtp_b_xq_lxxx tr:eq(4) td:odd:eq(1)").html(data.baseInfo.officeQq); //企业联系QQ
                    //银行账户信息
                    //账户信息
                    //报送服务公司信息
                    $("#apply_org_no").val(data.extendInfo.applyEntResNo); //报送单位资源号
                    $("#apply_company_entName").html(); //企业姓名
                    $("#apply_company_cont_name").html(); //联系人姓名
                    $("#apply_company_cont_mobile").html(); //联系人手机
                    $("#apply_company_cont_address").html(); //联系地址
                }
            });
        },
        //所属服务公司
        searchSuperEntInfo_cyqy: function (resno) {
            var param = {resNo: resno};
            callCenter.searchEntAllInfo(param, function (request) {
                if (request.retCode == 22000) {
                    var data = request.data;
                    $("#ldtp_super tr:eq(0) td:odd:eq(0)").html(resno); //企业互生号
                    $("#ldtp_super tr:eq(1) td:odd:eq(0)").html(data.extendInfo.buildDate);  //系统开启日期
                    $("#ldtp_super tr:eq(2) td:odd:eq(0)").html(data.baseInfo.entName); //企业名称
//                    $("#ldtp_super tr:eq(3) td:odd:eq(1)").html(comm.isEmpty(data.baseInfo.entNameEn) ? "" : data.baseInfo.entNameEn);//企业英文名称
                    cacheUtil.syncGetRegionByCode(data.baseInfo.countryCode, data.baseInfo.provinceCode, data.baseInfo.cityCode, "", function (resdata) {
                        $("#ldtp_super tr:eq(3) td:odd:eq(0)").html(resdata);//所属地区
                    });
                    $("#ldtp_super tr:eq(4) td:odd:eq(0)").html(data.baseInfo.countryCode == "156" ? "人民币" : "未知");//结算币种
                    $("#ldtp_super tr:eq(5) td:odd:eq(0)").html(data.extendInfo.businessType== 1? '连锁企业' : '普通企业');//经营类型
                }
            });

        },
        //联系信息查询、报送单位查询
        searchEntContactInfo_cyqy: function (resno, type) {
            var applyParam = {resNo: resno};
            callCenter.searchEntContactInfo(applyParam, function (request) {
                if (request.retCode == 22000) {
                    var apply = request.data;
                    switch (type) {
                        case 0://企业联系信息
                            $("#apply_ent_resNo").html(apply.entResNo);//企业管理号
                            $("#apply_ent_name").html(apply.entName); //企业姓名
                            $("#apply_ent_cont_name").html(apply.contactPerson); //联系人姓名
                            $("#apply_ent_cont_mobile").html(apply.contactPhone); //联系人手机
                            $("#apply_ent_cont_address").html(apply.entRegAddr); //联系地址
                            break;
                        case 1://报送单位
                            //弹屏按钮
                            $("#ldtp_bsdw tr:eq(0) td:odd:eq(0)").html(apply.entResNo);//企业管理号
                            $("#ldtp_bsdw tr:eq(1) td:odd:eq(0)").html(apply.entName); //企业姓名
                            $("#ldtp_bsdw tr:eq(2) td:odd:eq(0)").html(apply.contactPerson); //联系人姓名
                            $("#ldtp_bsdw tr:eq(3) td:odd:eq(0)").html(apply.contactPhone); //联系人手机
                            $("#ldtp_bsdw tr:eq(4) td:odd:eq(0)").html(apply.entRegAddr); //联系地址

                            //详单tab
                            $("#ldtp_b_xq_sbdwxx tr:eq(0) td:odd:eq(0)").html(apply.entResNo);//企业管理号
                            $("#ldtp_b_xq_sbdwxx tr:eq(0) td:odd:eq(1)").html(apply.entName); //企业姓名
                            $("#ldtp_b_xq_sbdwxx tr:eq(1) td:odd:eq(0)").html(apply.contactPerson); //联系人姓名
                            $("#ldtp_b_xq_sbdwxx tr:eq(1) td:odd:eq(1)").html(apply.contactPhone); //联系人手机
                            $("#ldtp_b_xq_sbdwxx tr:eq(2) td:odd:eq(0)").html(apply.entRegAddr); //联系地址
                            break;
                    }
                }
            });
        },
        //账户余额查询
        searchAccountBalance_cyqy: function (resno) {
            //查询账户余额
            /*
             积分余额:   10110
             积分投资：  10410
             流通币：    20110
             定向消费：  20120
             慈善救助：  20130
             货币：      30110
             税收： 积分 10510   互生币  20610   货币  30310
             */

            var param = {resNo: resno};
            var point_yestday = 0;
            callCenter.searchEntIntegralByYesterday(param, function (request) {
                if (request.retCode == 22000) {
                    if (comm.isNotEmpty(request.data)) {
                        point_yestday = request.data.sumAmount;
                    }
                }
            });
            //本年度 投资及投资分红查询
            var invest_income_sum = 0;    //投资
            var invest_income_ltb = 0;    //流通币
            var invest_income_csjz = 0;   //慈善救助金
            callCenter.findInvestDividendInfo(param, function (request) {
                if (request.retCode == 22000) {
                    if (comm.isNotEmpty(request.data)) {
                        invest_income_sum = request.data.totalDividend;
                        invest_income_ltb = request.data.normalDividend;
                        invest_income_csjz = request.data.directionalDividend;
                    }
                }
            });
            callCenter.getAccountBalance(param, function (request) {
                if (request.retCode == 22000) {
                    var balance = request.data;
                    if(comm.isNotEmpty(balance)){
                        /********************弹屏界面*******************/
                    	//积分账户余数
                    	var pointBalance = comm.isNotEmpty(balance[10110]) ? balance[10110].accBalance : 0;
                        $("#ldtp_b_pointBalance").html(comm.formatMoneyNumberAps(pointBalance));
                        //可用积分数
                        var pointBalance_usable = comm.isNotEmpty(balance[10110]) ? (balance[10110].accBalance - 10) : 0;
                        $("#ldtp_b_pointBalance_usable").html(comm.formatMoneyNumberAps(pointBalance_usable));
                        //货币账户余额
                        var cashBalance = comm.isNotEmpty(balance[30110]) ? balance[30110].accBalance : 0;
                        $("#ldtp_b_cashBalance").html(comm.formatMoneyNumberAps(cashBalance));
                        //流通币
                        var hsb_ltb = comm.isNotEmpty(balance[20110]) ? balance[20110].accBalance : 0;
                        $("#ldtp_b_hsb_ltb").html(comm.formatMoneyNumberAps(hsb_ltb));
                        //慈善救助金
                        var hsb_csjz = comm.isNotEmpty(balance[20130]) ? balance[20130].accBalance : 0;
                        $("#ldtp_b_hsb_csjz").html(comm.formatMoneyNumberAps(hsb_csjz));
                        //积分税收金额总数
                        var point_tax = comm.isNotEmpty(balance[10510]) ? balance[10510].accBalance : 0;
                        $("#ldtp_b_point_tax").html(comm.formatMoneyNumberAps(point_tax));
                        //互生币税收金额总数
                        var hsb_tax = comm.isNotEmpty(balance[20610]) ? balance[20610].accBalance : 0;
                        $("#ldtp_b_hsb_tax").html(comm.formatMoneyNumberAps(hsb_tax));
                        //货币(人民币)税收金额总数
                        var cash_tax = comm.isNotEmpty(balance[30310]) ? balance[30310].accBalance : 0
                        $("#ldtp_b_cash_tax").html(comm.formatMoneyNumberAps(cash_tax));


                        /******************详单账户tab******************/
                        /**积分账户**/
                        $("#ldtp_b_xq_zhxx #company_point_balance").html(comm.formatMoneyNumberAps(pointBalance));   //积分账户余数
                        $("#ldtp_b_xq_zhxx #company_point_balance_usable").html(comm.formatMoneyNumberAps(pointBalance_usable));//可用积分数
                        $("#ldtp_b_xq_zhxx #company_point_yestday").html(comm.formatMoneyNumberAps(point_yestday));//昨日积分数
                        /** 投资账户 **/
                        var invest_sum = comm.isNotEmpty(balance[10410]) ? balance[10410].accBalance : 0;
                        $("#ldtp_b_xq_zhxx #company_invest_sum").html(comm.formatMoneyNumberAps(invest_sum));//积分投资总数
                        $("#ldtp_b_xq_zhxx #company_invest_income").html(comm.formatMoneyNumberAps(invest_income_sum));//本年度投资分红互生币
                        $("#ldtp_b_xq_zhxx #company_invest_income_ltb").html(comm.formatMoneyNumberAps(invest_income_ltb));//本年度流通币
                        $("#ldtp_b_xq_zhxx #company_invest_income_csjz").html(comm.formatMoneyNumberAps(invest_income_csjz));//本年度慈善救助
                        /** 货币账户 **/
                        $("#ldtp_b_xq_zhxx #company_currency_balance").html(comm.formatMoneyNumberAps(cashBalance));//货币账户余额
                        /** 互生币账户 **/
                        $("#ldtp_b_xq_zhxx #company_hsb_ltb").html(comm.formatMoneyNumberAps(hsb_ltb));//流通币
                        $("#ldtp_b_xq_zhxx #company_hsb_csjz").html(comm.formatMoneyNumberAps(hsb_csjz));//慈善救助金
                        /** 城市税收对接账户 **/
                        $("#ldtp_b_xq_zhxx #company_point_tax").html(comm.formatMoneyNumberAps(point_tax)).attr("style", "margin-right:20px");//积分税收金额总数
                        $("#ldtp_b_xq_zhxx #company_hsb_tax").html(comm.formatMoneyNumberAps(hsb_tax)).attr("style", "margin-right:20px");//互生币税收金额总数
                        $("#ldtp_b_xq_zhxx #company_currency_tax").html(comm.formatMoneyNumberAps(cash_tax)).attr("style", "margin-right:20px");//货币(人民币)税收金额总数
                    }
                }
            });
        },
        //创建工单
        createWorkOrder: function (tel, dealStatus) {
            var SRN = $("#ldtp_hsh").val();//	互生号
            var AgentNum = Test.JS_GetUserNumByMac(Test.JS_GetMac());//	座席号
            var UserName = $("#ldtp_mc").val();//	用户名称
            var Caller = tel;//	来电号码
            var CallerType = $("#ldtp_lx").attr("optionvalue");//	来电类型，1：运维类 2：商务类 3：投诉与建议
            var DealStatus = dealStatus;//	处理状态，1：已完成 2：处理单 3：无效单
            var Priority = $('input[name="ldtp_yxj"]:checked ').val();//	优先级： 1 低； 2 高
            var FileName = $("#sound_path").val();//	语音文件路径
            var Description = $("#ldtp_content").val();//	问题描述
            if ($.trim(SRN).length <= 0) {
                alert("请输入资源号");
                return;
            }
            if ($.trim(UserName).length == 0) {
                alert("请输入来电者姓名");
                return;
            }
            if ($.trim(CallerType).length == 0) {
                alert("请选择来电类型");
                return;
            }
            if ($.trim(Priority).length == 0) {
                alert("请选择优先级");
                return;
            }
            if ($.trim(Description).length == 0) {
                alert("请输入问题描述");
                return;
            }
            var ret = Test.JS_TransPhoneWorkOrder(SRN, AgentNum, UserName, Caller, CallerType, DealStatus, Priority, FileName, Description);
            if (ret == 1) {
                $("#ldtp_content").val();
                alert("电话工单创建成功");
            } else {
                alert("电话工单创建失败");
            }
        },
        //摘机事件获取录音文件地址，用于创建工单用
        TrunkCallUser_UserOffHook: function (callerNum, userNum, resNo, FileName) {
            $("#sound_path").val(FileName);
        },
        //来电历史查询-工单
        PhoneWorkOrder: function (ret, id, json) {
            //console.info("查询工单结果" + ret + "<br>id" + id + "<br>json" + json);
            if (ret == 1) {
                //$("#thlsjlTable").find("tbody").children().remove();
                //$("#searchTable_pt").attr("align","center").html("未查到符合条件的数据");
                var gridObj = $.fn.bsgrid.init('thlsjlTable', {
                    //url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
                    autoLoad: true,
                    pageSizeSelect: true,
                    pageSize: 5,
                    stripeRows: true,  //行色彩分隔
                    displayBlankRows: false,   //显示空白行
                    localData: $.parseJSON(json).resultCode == 1 ? $.parseJSON(json).PhoneWorkOrder : null,
                    operate: {
                        detail: function (record, rowIndex, colIndex, options) {
                            if (colIndex == 3) {
                                return comm.langConfig.common.CallerType[record.CallerType];
                            }
                            if (colIndex == 4) {
                                return comm.langConfig.common.DealStatus[record.DealStatus];
                            }
                            if (colIndex == 5) {
                                return comm.langConfig.common.Priority[record.Priority];
                            }
                            if (colIndex == 6) {
                                //return comm.langConfig.common.DealStatus[record.DealStatus];
                                return "暂未评价";
                            }
                        }
                    }
                });
            }
        }, 
        /* 2016-06-03
         * 右侧拨号盘下方显示呼叫方信息
         * author:taojy
         */ 
        showEntCallerInfo: function (tel,resno) {
     	  var userName = '';
          var param = {resNo: resno};
           
          callCenter.searchEntAllInfo(param, function (request) {
           //保留在cookie是完整的信息
           var fullRecord = tel+resno;	   
           var showRecord ="";	    
           if (request.retCode == 22000) {
        	  var data = request.data;
         	  entName = (data.baseInfo.entName==null?'': data.baseInfo.entName);
         	  fullRecord = tel+entName+resno;
         	  //直接看到的是截取过的,防止企业名过长滚动条出现时内容会挤压变形
         	  showRecord = (25<fullRecord.length ? fullRecord.substring(0,23)+'..' : fullRecord);
              }
            
          	var content ="";
          	//toLocalDateString在IE中是年月日分割方式返回,google中是/分割方式返回
          	var date = new Date().toLocaleDateString();
          	var key =  $.cookie("custId")+date.replaceAll('/','').replaceAll('年','').replaceAll('月','').replaceAll('日','');
          	var value = comm.getCookie(key);
          	var record = "<li style='list-style-type:none'><span title='"+fullRecord+"'><font color='#2E8B57'>"+showRecord+"</font></span></li>";
          	
          	if(null!=value){
          		var cookieRecord = value.split(';');
          		var cookieLeng = cookieRecord.length;
          		var showLimit = 20;
          		var i= (cookieLeng< showLimit? cookieLeng : showLimit);
          		for(j=0; j<i; j++){
          			var cookieFullVal = cookieRecord[j];
          			//有效长度是25,超过上下层内容会重叠
          			var cookieShowVal = 25<cookieFullVal.length ? cookieFullVal.substring(0,23)+'..' : cookieFullVal;
          			content = content + "<li style='list-style-type:none'><span title='"+cookieFullVal+"'><font color='#2E8B57'>"+cookieShowVal+"</font></span></li>";
          		}
          		content = record+content;
          		comm.setSelfDefCookie(key,fullRecord+';'+value,1);
          	}else{
          		comm.setSelfDefCookie(key,fullRecord,1);
          		content=record;
          	}
          	
          	var callList = $("#callHistoryList");
            callList.children().remove();
          	callList.addClass(".record_list");
          	callList.html(content);
 
           })
         }
        
        
    }
});