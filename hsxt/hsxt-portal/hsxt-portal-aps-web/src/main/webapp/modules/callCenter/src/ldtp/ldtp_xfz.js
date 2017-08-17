define(['text!callCenterTpl/ldtp/ldtp_xfz.html',
    'text!callCenterTpl/ldtp/ldtp_xfz_xq.html',
    'callCenterDat/callCenter',
    'jquerydialogr'
], function (ldtp_xfzTpl, ldtp_xfz_xqTpl, callCenter) {
    return {
        showPage: function (tel, resno) {

            var self = this;
            $('#ldtp_dlg').html(ldtp_xfzTpl);
            $('#ldtp_dlg').dialogr({
                title: "来电号码：" + tel + " 积分卡号：" + resno,  //设置弹屏title
                width: "1200",
                height: "800",
                autoOpen: false
            });
            $("#ldtp_resno").val(resno);
            $("#latp_tel").text(tel);
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

            var ldtp_xfz_jbxx_btn = $('#ldtp_xfz_jbxx_btn');
            ldtp_xfz_jbxx_btn.children('li').children('a.a1').click(function () {
                ldtp_xfz_jbxx_btn.find('span').hide();
                ldtp_xfz_jbxx_btn.find('a').removeClass('hover');
                $(this).addClass('hover');

                $(this).siblings('span').show().mouseover(function () {
                    $(this).show();
                }).mouseout(function () {
                    $(this).hide();
                });
            }).mouseout(function () {
                ldtp_xfz_jbxx_btn.find('span').hide();
                ldtp_xfz_jbxx_btn.find('a').removeClass('hover');
            });


            //基本信息
              self.searchPersonInfo(resno);//消费者信息
              self.searchBankList(resno);//银行卡列表信息
              self.getQkBankAccountList(resno)//快捷支付银行卡列表信息  缺少接口
              self.searchPersonHskbb(resno);//互生卡补办
              self.getImptChangeInfo(resno);//重要信息变更，缺少接口 0: "待审批", 1: "通过地区平台初审", 2: "通过地区平台复核", 3: "申请驳回"
            //消费者来电弹屏详情
            $('#ldtp_xfz_xq_btn').click(function () {
                $('#ldtp_xfz_xqTpl').html(ldtp_xfz_xqTpl);
                self.searchPersonInfo(resno);//消费者信息
                self.showTpl($('#ldtp_xfz_xqTpl'));
                self.searchAccountBalance(resno);//查询账户余额
                /*end*/

                $('#ldtp_xfz_xq_fh_btn').click(function () {
                    self.showTpl($('#ldtp_xfzTpl'));
                });
            });
            //来电弹屏账户余额
            var ldtp_xfz_zhxx_btn = $('#ldtp_xfz_zhxx_btn');
            ldtp_xfz_zhxx_btn.find('a').click(function () {

                ldtp_xfz_zhxx_btn.find('span').hide();
                ldtp_xfz_zhxx_btn.find('a').removeClass('hover');
                $(this).addClass('hover');
                self.searchAccountBalance(resno);//查询账户余额
                $(this).siblings('span').show().mouseover(function () {
                    $(this).show();
                }).mouseout(function () {
                    $(this).hide();

                });
            }).mouseout(function () {
                ldtp_xfz_zhxx_btn.find('span').hide();
                ldtp_xfz_zhxx_btn.find('a').removeClass('hover');
            });

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
            /*end*/

            /*弹出框*/
            /*$( "#ldtp_dlg" ).dialog({
             title:"来电号码：6433 积分卡号：06186010006",
             width:"1200",
             height:"800",
             modal:true,
             closeIcon : true,
             buttons:{
             "关闭":function(){
             $( this ).dialog( "destroy" );
             }
             }
             });*/


            /*var ul_width = 0;
             ldtp_xfz_jbxx_btn.find('li').each(function(){

             ul_width += ($(this).width() + 30);

             });
             ldtp_xfz_jbxx_btn.width(ul_width);*/
            $('#ldtp_dlg').dialogr('open');
            $('.ui-dialog ').css('border', '1px solid #b3c4ae');
            $('.ui-dialog-titlebar-close').addClass('ui-dialogr-titlebar-close');
            $('#dialog-maximize, #dialog-minimize, #dialog-restore').children('span').remove();
            //拨号盘下方显示呼叫方信息
            self.showCallerInfo(tel,resno);
            /*end*/

        },
        showTpl: function (tplObj) {
            var self = this;
            $('#ldtp_xfzTpl, #ldtp_xfz_xqTpl').addClass('none');
            tplObj.removeClass('none');
            $(".jqp-tabs_3").tabs();


            /*下拉列表*/
            $("#sqfllb_ldtp").selectList({
                width: 150,
                optionWidth: 150,
                options: [
                    {name: comm.langConfig.common.welfareType[0], value: '0'},
                    {name: comm.langConfig.common.welfareType[2], value: '2'},
                    {name: comm.langConfig.common.welfareType[1], value: '1'}
                ]
            });
            /*end*/

            self.getWelfareList($("#ldtp_resno").val());//积分福利信息查询
            self.getListWelfareApply($("#ldtp_resno").val(), "0");//积分福利申请类型查询
            self.findCardapplyList($("#ldtp_resno").val());//积分卡补办记录查询

            $("#ldtp_jffl_qry_btn").click(function () {
                self.getListWelfareApply($("#ldtp_resno").val(), $("#sqfllb_ldtp").attr("optionvalue"));//积分福利查询
            });


            /*end*/
        },
        //银行卡列表查询
        searchBankList: function (resno) {
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
                } else {
                    $("#ldtp_bank_list").html("未绑定");
                }
            });
        },
        //快捷卡支付信息
        getQkBankAccountList: function (resno) {
            var param = {resNo: resno};
            callCenter.getQkBankAccountList(param, function (request) {
                if (request.retCode == 22000) {
                    var data = request.data;
                    if (comm.isNotEmpty(data)) {
                        var bankListstr = "";
                        for (var i = 0; i < data.length; i++) {
                            bankListstr += data[i].bankCardNo.concat("&nbsp;&nbsp;" + data[i].bankName) + "<br/>";
                        }
                        $("#ldtp_kjzf_list").html(bankListstr);
                    }
                } else {
                    $("#ldtp_kjzf_list").html("未绑定");
                }
            });
        },
        //互生卡补办记录查询
        searchPersonHskbb: function (resno) {
/*            var param = {search_hsResNo: resno};
            callCenter.findCardapplyList(param, function (request) {
                if (request.retCode == 22000) {
                    var data = request.data != "" ? request.data : null;
                    if (comm.isNotEmpty(request.data)) {
                        $("#ldtp_hskbb").text(comm.langConfig.common.hsCardOrderStatus[data[data.length - 1].orderStatus]);//互生卡补办状态//未完成
                    } else {
                        $("#ldtp_hskbb").text("无记录");//互生卡补办状态//未完成
                    }
                }
            });*/
            var param = {search_hsResNo: resno};
            callCenter.findCardapplyList("searchTable_xfz_hskbb_ldtp",param,function(record, rowIndex, colIndex, options){
                if (colIndex == 3) {
                    return comm.langConfig.common.hsCardOrderStatus[record.orderStatus];
                }
            });
        },
        //消费者重要信息变更查询
        getImptChangeInfo: function (resno) {
            var param = {resNo: resno};
            callCenter.getImptChangeInfo(param, function (request) {
                if (request.retCode == 22000) {
                    var data = request.data != "" ? request.data : null;
                    if (comm.isNotEmpty(request.data)) {
                        $("#ldtp_zyxxbg").text(comm.langConfig.common.importantChanteStatus[data.status]);//互生卡补办状态//未完成
                    } else {
                        $("#ldtp_zyxxbg").text("无记录");//{0: "待审批", 1: "通过地区平台初审", 2: "通过地区平台复核", 3: "申请驳回"}
                    }
                }
            });
        },
        //消费者信息查询
        searchPersonInfo: function (resno) {
            var param = {resNo: resno};
            callCenter.getPersonAllInfo(param, function (request) {
                if (request.retCode == 22000) {
                    var data = request.data;
                    //工单自动补全
                    $("#ldtp_hsh").val(resno);
                    $("#ldtp_mc").val(data.authInfo.userName);
                    $("#ldtp_username").val(data.authInfo.userName);
                    //来电弹屏基本信息
                    $("#ldtp_resno").val(resno);
                    $("#ldtp_email").text(comm.isNotEmpty(data.baseInfo.email)?(data.baseInfo.email.concat("(" + (data.baseInfo.isAuthEmail ? "已验证" : "未验证") + ")")):"未绑定");	//邮箱显示
                    $("#ldtp_mobile").text(comm.isNotEmpty(data.baseInfo.mobile)?(data.baseInfo.mobile.concat("(" + (data.baseInfo.isAuthMobile ? "已验证" : "未验证") + ")")):"未绑定");//手机显示
                    if (data.authInfo.realNameStatus == 2) {//2：已实名注册（有名字和身份证）
                        $("#ldtp_smzc").text("已注册 ".concat(data.authInfo.realnameRegDate));//实名注册状态+日期
                        $("#ldtp_smrz").text("未认证 ");//实名认证状态+日期
                    } else if (data.authInfo.realNameStatus == 3) {  //实名认证
                        $("#ldtp_smzc").text("已注册 ".concat(data.authInfo.realnameRegDate));//实名注册状态+日期
                        $("#ldtp_smrz").text("已认证 ".concat(data.authInfo.realnameAuthDate));//实名认证状态+日期
                    } else {  //未实名注册
                        $("#ldtp_smzc").text("未注册");//实名注册状态+日期
                        $("#ldtp_smrz").text("未认证");//实名认证状态+日期
                    }
                    $("#ldtp_hskgs").text(comm.langConfig.common.cardStatus[data.cardInfo.cardStatus]);// 卡状态(1：启用、2：挂失);


                    //详单内容
                    $('#ldtp_xfz_xqTpl #p_username').html(data.authInfo.userName);//姓名
                    $('#ldtp_xfz_xqTpl #p_username1').html(data.authInfo.userName);//姓名
                    $('#ldtp_xfz_xqTpl #p_resNo').html(data.baseInfo.perResNo.substring(0, 2) + "&nbsp;" + data.baseInfo.perResNo.substring(2, 5) + "&nbsp;" + data.baseInfo.perResNo.substring(5, 7) + "&nbsp;" + data.baseInfo.perResNo.substring(7, 11) + "&nbsp;");         //资源号
                    //authInfo.realNameStatus 实名状态  1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证
                    if (data.authInfo.realNameStatus == 2) {//2：已实名注册（有名字和身份证）
                        $('#ldtp_xfz_xqTpl #p_real_reg').attr("class", "fl service_icon service_icon_zc ml5").attr("title", "已实名注册");
                        $('#ldtp_xfz_xqTpl #p_real_iden').attr("class", "fl service_icon service_icon_rz_disable ml5").attr("title", "未实名认证");
                    } else if (data.authInfo.realNameStatus == 3) {  //实名认证
                        $('#ldtp_xfz_xqTpl #p_real_reg').attr("class", "fl service_icon service_icon_zc ml5").attr("title", "已实名注册");
                        $('#ldtp_xfz_xqTpl #p_real_iden').attr("class", "fl service_icon service_icon_rz ml5").attr("title", "已实名认证");
                    } else {  //未实名注册
                        $('#ldtp_xfz_xqTpl #p_real_reg').attr("class", "fl service_icon service_icon_rz_disable ml5").attr("title", "未实名注册");
                        $('#ldtp_xfz_xqTpl #p_real_iden').attr("class", "fl service_icon service_icon_rz_disable ml5").attr("title", "未实名认证");
                    }
                    $('#ldtp_xfz_xqTpl #p_mobile').html(data.baseInfo.mobile);//手机号
                    $('#ldtp_xfz_xqTpl #p_cardStatus').html(comm.langConfig.common.cardStatus[data.cardInfo.cardStatus]);// 卡状态(1：启用、2：挂失)

                    /***消费者详情***/
                        //头部信息
                    $('#ldtp_xfz_xqTpl #p_res_no').html(resno);
                    if (data.authInfo.realNameStatus == 2) {//2：已实名注册（有名字和身份证）
                        $('#ldtp_xfz_xqTpl #p_real_reg1').attr("class", "fl service_icon service_icon_Bbd ml10").attr("title", "已实名注册");
                        $('#ldtp_xfz_xqTpl #p_real_auth1').attr("class", "fl service_icon service_icon_Brz_disable ml10").attr("title", "未实名认证");
                    } else if (data.authInfo.realNameStatus == 3) {  //实名认证
                        $('#ldtp_xfz_xqTpl #p_real_reg1').attr("class", "fl service_icon service_icon_Bbd ml10").attr("title", "已实名注册");
                        $('#ldtp_xfz_xqTpl #p_real_auth1').attr("class", "fl service_icon service_icon_Bbd ml10").attr("title", "已实名认证");
                    } else {  //未实名注册
                        $('#ldtp_xfz_xqTpl #p_real_reg1').attr("class", "fl service_icon service_icon_Bzc_disable ml10").attr("title", "未实名注册");
                        $('#ldtp_xfz_xqTpl #p_real_auth1').attr("class", "fl service_icon service_icon_Brz_disable ml10").attr("title", "未实名认证");
                    }
                    $('#ldtp_xfz_xqTpl #p_card_status').html(comm.langConfig.common.cardStatus[data.cardInfo.cardStatus]);
                    //互生卡身份信息
                    $('#ldtp_xfz_xqTpl #p_person_resNo').html(resno);    //互生卡号
                    $('#ldtp_xfz_xqTpl #p_person_cre_type').html(comm.langConfig.common.creTypes[data.authInfo.cerType]);    //证件类型
                    $('#ldtp_xfz_xqTpl #p_person_name').html(data.authInfo.userName);     //姓名
                    cacheUtil.syncGetRegionByCode(data.authInfo.countryCode, "", "", "", function (resdata) {
                        $('#ldtp_xfz_xqTpl #p_nationality').html(resdata);    //国籍
                    });
                    $('#ldtp_xfz_xqTpl #p_cre_no').html(data.authInfo.cerNo);    //证件号码
                    $('#ldtp_xfz_xqTpl #p_gender').html(comm.langConfig.common.sexTypes[data.authInfo.sex]);    //性别
                    $('#ldtp_xfz_xqTpl #p_cre_end').html(data.authInfo.validDate);    //证件有效期
                    $('#ldtp_xfz_xqTpl #p_cre_address').html(data.authInfo.birthAddress);    //户籍地址
                    $('#ldtp_xfz_xqTpl #p_cre_gov').html(data.authInfo.issuingOrg);    //发证机关
                    //消费者详单
                }
           });
        },
        //账户余额查询
        searchAccountBalance: function (resno) {
            var param = {resNo: resno};
            var point_today = 0;
            //昨日积分数查询
            callCenter.searchPerIntegralByToday(param, function (request) {
                if (request.retCode == 22000) {
                    if (comm.isNotEmpty(request.data)) {
                        point_yestday = request.data.sumAmount;
                    }
                }
            });
            //本年度 投资及投资分红查询
            var invest_income_sum = 0;    //投资
            var invest_income_ltb = 0;    //流通币
            var invest_income_dxxf = 0;   //定向消费币
            callCenter.findInvestDividendInfo(param, function (request) {
                if (request.retCode == 22000) {
                    if (comm.isNotEmpty(request.data)) {
                        invest_income_sum = request.data.totalDividend;
                        invest_income_ltb = request.data.normalDividend;
                        invest_income_dxxf = request.data.directionalDividend;
                    }
                }
            });
            //账户余额查询
            callCenter.getAccountBalance(param, function (request) {
                if (request.retCode == 22000) {
                    var balance = comm.isNotEmpty(request.data) ? request.data : null;
                    if(comm.isNotEmpty(balance)){
                        /**积分账户**/
                        $('#ldtp_xfz_xqTpl').find("#p_point_balance").html(comm.isNotEmpty(balance[10110]) ? balance[10110].accBalance : 0);   //积分账户余数
                        $('#ldtp_xfz_xqTpl').find("#p_point_usable").html(comm.isNotEmpty(balance[10110]) ? (balance[10110].accBalance - 10) : 0);//可用积分数
                        $('#ldtp_xfz_xqTpl').find("#p_point_today").html(point_today);//今日积分数
                        /** 货币账户 **/
                        $('#ldtp_xfz_xqTpl').find("#p_currency_balance").html(comm.isNotEmpty(balance[30110]) ? balance[30110].accBalance : 0);//货币账户余额
                        /** 互生币账户 **/
                        $('#ldtp_xfz_xqTpl').find("#p_hsb_ltb").html(comm.isNotEmpty(balance[20110]) ? balance[20110].accBalance : 0);//流通币
                        $('#ldtp_xfz_xqTpl').find("#p_hsb_dxxf").html(comm.isNotEmpty(balance[20120]) ? balance[20120].accBalance : 0);//定向消费
                        /** 投资账户 **/
                        $('#ldtp_xfz_xqTpl').find("#p_invest_sum").html(comm.isNotEmpty(balance[10410]) ? balance[10410].accBalance : 0);//积分投资总数
                        $('#ldtp_xfz_xqTpl').find("#p_invest_income_sum").html(invest_income_sum);//本年度投资分红互生币
                        $('#ldtp_xfz_xqTpl').find("#p_invest_income_ltb").html(invest_income_ltb);//本年度流通币
                        $('#ldtp_xfz_xqTpl').find("#p_invest_income_dxxf").html(invest_income_dxxf);//本年度慈善救助

                        //快速查询
                        $("#ldtp_point").html("积分余数为：" + (comm.isNotEmpty(balance[10110]) ? balance[10110].accBalance : 0) + "<br>可用积分余数：" + (comm.isNotEmpty(balance[10110]) ? (balance[10110].accBalance - 10) : 0));
                        $("#ldtp_cash").html("货币账户余额为：" + (comm.isNotEmpty(balance[30110]) ? balance[30110].accBalance : 0));
                        $("#ldtp_invest").html("投资积分总数为：" + (comm.isNotEmpty(balance[10410]) ? balance[10410].accBalance : 0) + "<br /> 本年度流通币：" + invest_income_ltb + "<br /> 本年度定向消费币：" + invest_income_dxxf);
                        $("#ldtp_hsb").html("流通币余额为：" + (comm.isNotEmpty(balance[20110]) ? balance[20110].accBalance : 0) + "<br /> 定向消费币余额为：" + (comm.isNotEmpty(balance[20120]) ? balance[20120].accBalance : 0));
                    }
                }
            });
        },
        //积分福利信息查询
        getWelfareList: function (resno) {
            var param = {search_resNo: resno, search_welfareType: 0};
            callCenter.getWelfareList("searchTable_xfz_jfflxx_ldtp",param,function(record, rowIndex, colIndex, options){comm.alert(record);});
        },
        //积分福利申请记录查询 资源号， 福利类型
        getListWelfareApply: function (resno, welfareType) {
            //积分福利申请记录
            var param = { search_resNo: resno, search_welfareType: welfareType};
            callCenter.getListWelfareApply("searchTable_xfz_jffl_ldtp",param,function(record, rowIndex, colIndex, options){
                if (colIndex == 2) {//申请类型
                    return comm.langConfig.common.welfareType[record.welfareType];
                }
                if (colIndex == 3) {
                    return comm.langConfig.common.welfareApprovalStatus[record.approvalStatus]; //0 受理中 1 已受理 2 驳回
                }
            });
        },
        //互生卡补办记录信息
        findCardapplyList: function (p_resNo) {
            var param = {search_hsResNo: p_resNo};
            callCenter.findCardapplyList("searchTable_xfz_hskbb_ldtp",param,function(record, rowIndex, colIndex, options){
                if (colIndex == 3) {
                    return comm.langConfig.common.hsCardOrderStatus[record.orderStatus];
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
            // comm.alert("查询工单结果" + ret + "<br>id" + id + "<br>json" + json);
            if (ret == 1) {
            /*    $("#thlsjlTable").find("tbody").children().remove();
                $("#searchTable_pt").attr("align","center").html("未查到符合条件的数据");*/
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
        /* 2016-06-02
         * 右侧拨号盘下方显示呼叫方信息
         * author:taojy
         */ 
        showCallerInfo: function (tel,resno) {
     	  var userName = '';
           var param = {resNo: resno};
           
           callCenter.getPersonAllInfo(param, function (request) {
    	   //保留在cookie是完整的信息
           var fullRecord = tel+resno;	   
           var showRecord ="";	    
           if (request.retCode == 22000) {
        	  var data = request.data;
        	  userName = (data.authInfo.userName==null?'': data.authInfo.userName);
         	  fullRecord = tel+userName+resno;
         	  //直接看到的是截取过的,防止用户名过长滚动条出现时内容会挤压变形
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
