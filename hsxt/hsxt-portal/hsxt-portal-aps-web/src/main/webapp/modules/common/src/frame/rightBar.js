define(["text!commTpl/frame/rightBar.html", /*"commSrc/frame/dialing"*/"callCenterSrc/callCenter/dialing", 'callCenterSrc/ldtp/ldtp',
    'callCenterSrc/ldtp/ldtp_xfz',
    'callCenterSrc/ldtp/ldtp_cyqy',
    'callCenterSrc/ldtp/ldtp_tgqy',
    'callCenterSrc/ldtp/ldtp_fwgs',
    'callCenterDat/callCenter',
    'commonSrc/frame/seatgroup/seatgroup',
	'commonSrc/frame/chat/contact'], function (tpl, dialingObj, ldtp, ldtp_xfz, ldtp_cyqy, ldtp_tgqy, ldtp_fwgs, callCenter, seatgroup,chatSerive) {

	//加载右边服务模块
    $('#service').html(tpl).append(dialingObj.tpl);

    //加载消费服务
	chatSerive.showPage();

    //初始弹出框
    require(["jquerydialogr"], function () {
        $("#laidiantanping").dialogr({autoOpen: false, maximized: true, minWidth: 500, height: 800, width: 1200});
        $("#qiye").dialogr({autoOpen: false, maximized: true, minWidth: 500, height: 800, width: 1200});
        $("#xiaofeizhe").dialogr({autoOpen: false, maximized: true, minWidth: 500, height: 800, width: 1200});
        //$('#ldtp_dlg').dialogr('open');
        $('.ui-dialog ').css('border', '1px solid #b3c4ae');
    });

    var zx_num ='801';
    var company_busiLicenseImg = '';
    var company_contactProxy = '';
    var company_helpAgreement = '';
    
    var scs_busiLicenseImg = '';
    var scs_contactProxy = '';
    if (navigator.userAgent.indexOf("MSIE 8.0") > 0) {
        zx_num = Test.JS_GetUserNumByMac(Test.JS_GetMac());
    }

    //服务公司手机号呼叫
    $("#scs_panel .service_icon_microphone").first().click(function () {
        var mobile = $("#scs_mobile").text();
        getMobilePhoneCity(mobile);
    });
    //服务公司联系电话呼叫
    $("#scs_panel .service_icon_microphone").last().click(function () {
        var tel = $("#scs_tel").text().replace("-", "");
        comm.alert("服务公司联系电话" + tel);
        callOutLine(tel);
    });
    //托管成员企业手机呼叫
    $("#company_panel .service_icon_microphone").first().click(function () {
        var mobile = $("#company_mobile").text();
        getMobilePhoneCity(mobile);
    });
    //托管成员企业电话呼叫
    $("#company_panel .service_icon_microphone").last().click(function () {
        var tel = $("#company_tel").text().replace("-", "");
        comm.alert("企业公司联系电话" + tel);
        callOutLine(tel);
    });

    //消费者电话外呼
    $("#person_panel .service_icon_microphone").last().click(function () {
        var mobile = $("#p_mobile").text();
        getMobilePhoneCity(mobile);
    });
    //手机外呼
    function getMobilePhoneCity(mobile) {
        var param = {num: mobile};
        callCenter.getMobilePhoneCity(param, function (request) {
            if (JSON.parse(request.data).ret_code == 0) {
                if (JSON.parse(request.data).city == "深圳市") {
                    callOutLine(mobile);
                } else {
                    callOutLine("0" + mobile);    //异地号码前加0
                }
            }
        });
    }

    //电话外呼
    function callOutLine(mobile) {
        var ret = Test.JS_UserCallTrunk(Test.JS_GetUserNumByMac(Test.JS_GetMac()), comm.getPrefix(mobile));
        if (ret != 1) {
            comm.callCenter_error(ret);
        } else {
            comm.alert("电话呼叫中");
        }
    }

    //来电弹屏模拟  模拟--消费者
    $("#xfz").click(function () {
        //s1 来电号码13826599007  s2 坐席号 801  s3 资源号06186010001
        var s1 = '13826599007';
        var s2 = zx_num;
        var s3 = '06033110975';
        MyFuncCallIn(s1, s2, s3);
        //TrunkCallUser_UserOffHook(s1, s2, s3, "D:\\workspace");
    });

    //来电弹屏模拟  模拟--成员企业
    $("#cyqy").click(function () {
        //s1 来电号码13826599007  s2 坐席号 801  s3 资源号 06186000001
        var s1 = '13826599007';
        var s2 = zx_num;
        var s3 = '06071000001';
        MyFuncCallIn(s1, s2, s3);
    });

    //来电弹屏模拟  模拟--托管企业
    $("#tgqy").click(function () {
        //s1 来电号码13826599007  s2 坐席号 801  s3 资源号 06186010000
        var s1 = '13826599007';
        var s2 = zx_num;
        var s3 = '06033110000';
        MyFuncCallIn(s1, s2, s3);
    });
    //来电弹屏模拟  模拟--服务公司
    $("#fwgs").click(function () {
        //s1 来电号码13826599007  s2 坐席号 801  s3 资源号 06186000000
        var s1 = '13826599007';
        var s2 = zx_num;
        var s3 = '06033000000';
        MyFuncCallIn(s1, s2, s3);
    });

    if (navigator.userAgent.indexOf("MSIE 8.0") > 0) {
        Test.attachEvent("JS_Evt_CallIn", MyFuncCallIn);    //来电弹屏事件绑定
        Test.attachEvent("JS_Evt_GetBusinessAgentReport", GetBusinessAgentReport);//分组查询坐席列表
    }


    //移除rightBar组件自带的class
   /* var sv2_2 = $("#serviceContent_2_2"),
        sv2_4 = $("#serviceContent_2_4");
    sv2_2.children("div").attr("class", "serviceItem").children("h3").removeAttr("class").unbind("mouseenter").click(function () {
        $(this).removeAttr("class");
    }).children("span").remove();
    sv2_4.children("div").attr("class", "serviceItem").children("h3").removeAttr("class").unbind("mouseenter").click(function () {
        $(this).removeAttr("class");
    }).children("span").remove();
    sv2_2.children("div").children("div").attr("class", "tabDiv_h");
    sv2_4.children("div").children("div").attr("class", "tabDiv_h");*/

    //坐席分组查询
    var zxfz_list=null;
    function GetBusinessAgentReport(ret, id, json) {
        if(!location.host.indexOf("192.168.41.54")) {
            //console.info("坐席分组查询：" + json);
        }
        var data = $.parseJSON(json);
        if (data.resultCode == 1) {
            seatgroup.showPage(data);
            var seat_count=0;
            //计算坐席个数
            for(var i=0;i<data.BusinessAgentReport.length;i++){
                seat_count=seat_count+data.BusinessAgentReport[i].groupList.length;
            }
            $("#sategroup_count").html("("+seat_count+")");
        } else {
            comm.i_alert("坐席未签入，不能查询坐席分组列表", "380px");
        }
    }
    //来电弹屏事件
    function MyFuncCallIn(tel, zx, resno) {
        //tel 来电号码13826599007  zx 坐席号 801  resno 资源号06186010001
        //comm.alert(tel + " " + zx + " " + resno);

        //查询历史通话记录 参数 1开始日期，2截止日期，3坐席号，4，来电号码，5 呼入，呼出I/O
        //Test.JS_GetCallRecordList('2015-01-01 00:00:00','2016-01-31 00:00:00',zx,tel,'');

        //根据资源号类型选择弹屏类型 P消费者 B成员企业 T托管企业  S服务公司
        if (comm.isNotEmpty(resno) && resno.length == 11) {
            var resType = comm.getResNoType(resno);
            switch (resType) {
                case 'P':
                    ldtp_xfz.showPage(tel, resno);
                    break;
                case 'B':
                    ldtp_cyqy.showPage(tel, resno);
                    break;
                case 'T':
                    ldtp_tgqy.showPage(tel, resno);
                    break;
                case 'S':
                    ldtp_fwgs.showPage(tel, resno);
                    break;
                default :
                	//20160614经确认,未知来电仅记录,不弹屏
                	ldtp.recordUnkown(tel, resno);
            }
        } else {
        	//20160614经确认,未知来电仅记录,不弹屏
        	ldtp.recordUnkown(tel, resno);
        }
    }



    //选项菜单事件开始
    var arrow = "<i class='nav_arrow'></i>",
        serviceNav_1 = $("#serviceNav_1"),
        serviceNav_2 = $("#serviceNav_2");


    serviceNav_1.children(":first").append(arrow);
    serviceNav_2.children(":first").append(arrow);

    serviceNav_1.children().click(function () {
        $("#serviceItem_div1").children().remove();
        $("#serviceContent_2_2").hide();
        serviceNav($(this));

    });

    serviceNav_2.children().click(function () {
        serviceNav($(this));
    });

    function serviceNav(currentLi) {
        currentTab = currentLi.index();
        currentLi.siblings().removeClass("nav_bt_hover").addClass("nav_bt")
            .children().remove(".nav_arrow").end().end()
            .addClass("nav_bt_hover");

        if (currentLi.children(".nav_arrow").length == 0) {
            currentLi.append(arrow);
        }

        var liId = (currentLi.parent("ul").attr("id") || ''),
            numArr = liId.split("_"),
            num = numArr[1];

        for (var i = 0; i <= 4; i += 2) {
            $("#serviceContent_" + num + "_" + i).hide();
        }
        $("#serviceContent_" + num + "_" + currentTab).show();
        $('.listScroll').jScrollPane();
        if(currentTab==2){
            Test.JS_GetBusinessAgentReport();//坐席分组查询
        }
        $( ".serviceItem" ).children('div.tabDiv_h').css('height', 'auto');
    }

    //选项菜单事件结束

    //拨号记录选中高亮背景

    $("#recordList").children().click(function () {
        $(this).siblings().removeClass("record_list_bg");
        $(this).addClass("record_list_bg");
    });

    //拨号子菜单选中状态
//	$("#serviceSubNavBt").children().click(function(){
//		$(this).siblings().children().removeClass("serviceSubNav_bt_hover");
//		$(this).children().addClass("serviceSubNav_bt_hover");
//	});

    //拨号子菜单选中状态
    $("#serviceSubNavBt").children().children().click(function (e) {
        var target = $(e.currentTarget).attr("id");
        $(this).siblings().children().removeClass("serviceSubNav_bt_hover");
        $(this).children().addClass("serviceSubNav_bt_hover");
        // 0 签入 1 签出 2 置忙 3 转内 4 转外 5 静音
        switch (target) {
            case "login":
                dialingObj.JSLogIn();//签入

                break;
            case "logout":
                dialingObj.JS_LogOut();//签出
                break;
            case "setuserbusy":
                dialingObj.JS_SetUserBusy();//置忙
                break;
            case "trunktransferuser" :
                dialingObj.JS_TrunkTransferUser($("#dial_input").val().trim());       //转内
                break;
            case "trunkcalltrunk" :
                dialingObj.JS_TrunkCallTrunk($("#dial_input").val().trim());        //转外
                break;
            case "setuserquiet" :
                dialingObj.JS_SetUserQuiet();//静音
                break;
            case "stoptrunkcalltrunk" :
                dialingObj.JS_StopTrunkCallTrunk(); //停止转外
                break;
            case "stoptrunkcalltrunk1" :
                dialingObj.JS_StopTrunkCallTrunk1(); //停止转内
                break;
            default :
                dialingObj.JS_SetUserFree();// 空闲
                break;
        }
    });

    //其它按钮点击选中状态


    //拨号键盘选中状态
    $("#dialPanel").children().bind({
        mousedown: function (e) {
            phoneVal($(this).attr("data"));
            var currentBt = $(e.currentTarget);
            var currentClass = currentBt.attr("class");
            currentBt.addClass(currentClass + "_hover");
        },
        mouseup: function (e) {
            mouseUpOut(e);
        },
        mouseout: function (e) {
            mouseUpOut(e);
        }
    });

    function mouseUpOut(e) {
        var currentBt = $(e.currentTarget);
        var currentClass = currentBt.attr("class");
        var arr = new Array();
        arr = currentClass.split(' ');
        for (var i = 1, l = arr.length; i <= l; i++) {//20150209 add
            currentBt.removeClass(arr[i]);
        }
    }

//拨号 点击事件 值处理
    function phoneVal(data) {
        var inputVal = $("#dial_input").val();
        switch (data) {
            case "asterisk": // *
                inputVal += "*";
                break;
            case "other": //#
                inputVal += "#";
                break;
            case "answer": //拨出
                // 06186010001 消费者(CUSTTYPE_P)  06186010000/06186000015 托管/成员企业(CUSTTYPE_C/CUSTTYPE_B) 06186000000 服务公司(CUSTTYPE_S)
                //dialingObj.showModal("95105105","803","06186010001");  //弹框
                //dialingObj.bindingclick("95105105","803","06186010001");
                dialingObj.answer($("#dial_input").val());
                break;
            case "c": //清除
                inputVal = "";
                break;
            case "hangup"://挂机
                dialingObj.JS_StopUserCallTrunk();
                break;
            default :
                inputVal += data;
                break;
        }
        setInputVal(inputVal);
    }

//设置文本框的值
    function setInputVal(val) {
        $("#dial_input").val(val);
    }


    /**
     * 右上角弹屏查询详情
     */

    //服务公司详情
    var scs_resNo = $("#intpu_search_scs").val();
    var param = {resNo: scs_resNo};
    $("#fwgsDetail_btn").click(function () {
        $("#fwgsDetail_content").dialog({
            title: "服务公司详细信息",
            width: "980",
            height: "580",
            modal: true,
            buttons: {
                "确定": function () {
                    $(this).dialog("destroy");
                }
            }
        });
        $(".jqp-tabs_1").tabs();
        $("[href=#jqp-tabs_1-1]").click();
        scs_resNo = $("#intpu_search_scs").val();
        param = {resNo: scs_resNo};
    });
    //查询银行卡列表
    $("a[href=#jqp-tabs_1-4]").click(function () {
        callCenter.getBankAccountList(param, function (request) {
            if (request.retCode == 22000 && comm.isNotEmpty(request.data)) {
                comm.call_bank_List_print("jqp-tabs_1-4 ul", request.data);
            }
        });
    });
    //快捷支付卡列表
    $("a[href=#jqp-tabs_1-5]").click(function () {
        callCenter.getQkBankAccountList(param, function (request) {
            if (request.retCode == 22000 && comm.isNotEmpty(request.data)) {
                comm.call_bank_List_print("jqp-tabs_1-5 ul", request.data);
            }
        });
    });
    //业务操作许可
    $("a[href=#jqp-tabs_1-6]").click(function () {
        callCenter.queryBusinessPmInfo(param, function (request) {
            if (request.retCode == 22000 && comm.isNotEmpty(request.data)) {
                var obj =request.data;
                var pvToHsb = "0" == obj.PV_TO_HSB ? "启用" : "限制";
				var pvInvest = "0" == obj.PV_INVEST ? "启用" : "限制";
				var buyHsb = "0" == obj.BUY_HSB ? "启用" : "限制";
				var hsbToCash = "0" == obj.HSB_TO_CASH ? "启用" : "限制";
				var cashToBank = "0" == obj.CASH_TO_BANK ? "启用" : "限制";
				
				var pvToHSBRemark = $.trim(obj.PV_TO_HSBRemark);
				var pvINVESTRemark = $.trim(obj.PV_INVESTRemark);
				var buyHSBRemark = $.trim(obj.BUY_HSBRemark);
				var hsbToCASHRemark = $.trim(obj.HSB_TO_CASHRemark);
				var cashToBANKRemark = $.trim(obj.CASH_TO_BANKRemark);
				
                
				//积分转互生币
				 $("#jqp-tabs_1-6 tr:eq(1) td:eq(1)").addClass("view_text").html(pvToHsb);    
				 $("#jqp-tabs_1-6 tr:eq(1) td:eq(2)").addClass("view_text").html(pvToHSBRemark); 
                	//积分投资
                 $("#jqp-tabs_1-6 tr:eq(2)").addClass();
                 $("#jqp-tabs_1-6 tr:eq(2) td:eq(1)").addClass("view_text").html(pvInvest);
                 $("#jqp-tabs_1-6 tr:eq(2) td:eq(2)").addClass("view_text").html(pvINVESTRemark);
                    //兑换互生币
                $("#jqp-tabs_1-6 tr:eq(3) td:eq(1)").addClass("view_text").html(buyHsb);
                $("#jqp-tabs_1-6 tr:eq(3) td:eq(2)").addClass("view_text").html(buyHSBRemark);
                //互生币转货币
                $("#jqp-tabs_1-6 tr:eq(4) td:eq(1)").addClass("view_text").html(hsbToCash);
                $("#jqp-tabs_1-6 tr:eq(4) td:eq(2)").addClass("view_text").html(hsbToCASHRemark);
                //货币转银行
                $("#jqp-tabs_1-6 tr:eq(5) td:eq(1)").addClass("view_text").html(cashToBank);
                $("#jqp-tabs_1-6 tr:eq(5) td:eq(2)").addClass("view_text").html(cashToBANKRemark);
                
            }
        });
    });
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
    $("a[href=#jqp-tabs_1-7]").click(function () {
        var point_yestday = 0;
       
        //本年度 投资及投资分红查询
        var invest_income_sum = 0;    //投资
        var invest_income_ltb = 0;    //流通币
        var invest_income_csjz = 0;   //慈善救助金
        var last_year=2015;
        
        callCenter.getAccountBalance(param, function (request) {
        	
            if (request.retCode == 22000) {
                var balance = request.data;
                if (comm.isNotEmpty(balance)) {
                    /**积分账户**/
                    $("#scs_point_balance").html(comm.formatMoneyNumberAps(comm.isNotEmpty(balance[10110]) ? balance[10110].accBalance : 0));   //积分账户余数
                    $("#scs_point_balance_usable").html(comm.formatMoneyNumberAps(comm.isNotEmpty(balance[10110]) ? (balance[10110].accBalance<10 ? 0:(balance[10110].accBalance - 10)) : 0));//可用积分数
                    callCenter.searchEntIntegralByYesterday(param, function (request) {
                        if (request.retCode == 22000) {
                            if (comm.isNotEmpty(request.data)) {
                                point_yestday = request.data.sumAmount;
                            }
                        }//异步请求作用域内变量才有效
                        $("#scs_point_yestday").html(comm.formatMoneyNumberAps(point_yestday));//昨日积分数
                    });
                    
                    /** 投资账户 **/
                    $("#scs_invest_sum").html(comm.formatMoneyNumberAps(comm.isNotEmpty(balance[10410]) ? balance[10410].accBalance : 0));//积分投资总数
                    callCenter.findInvestDividendInfo(param, function (request) {
                        if (request.retCode == 22000) {
                            if (comm.isNotEmpty(request.data)) {
                                invest_income_sum = request.data.totalDividend;
                                invest_income_ltb = request.data.normalDividend;
                                invest_income_csjz = request.data.directionalDividend;
                                last_year=request.data.dividendYear;
                            }
                        }//异步请求作用域内变量才有效
                        $("#scs_invest_income").html(last_year+"年度投资分红互生币："+comm.formatMoneyNumberAps(invest_income_sum));//本年度投资分红互生币
                        $("#scs_invest_income_ltb").html(comm.formatMoneyNumberAps(invest_income_ltb));//本年度流通币
                        $("#scs_invest_income_csjz").html(comm.formatMoneyNumberAps(invest_income_csjz));//本年度慈善救助
                    });
                    
                    /** 货币账户 **/
                    $("#scs_currency_balance").html(comm.formatMoneyNumberAps(comm.isNotEmpty(balance[30110]) ? balance[30110].accBalance : 0));//货币账户余额
                    /** 互生币账户 **/
                    $("#scs_hsb_ltb").html(comm.formatMoneyNumberAps(comm.isNotEmpty(balance[20110]) ? balance[20110].accBalance : 0));//流通币
                    $("#scs_hsb_csjz").html(comm.formatMoneyNumberAps(comm.isNotEmpty(balance[20130]) ? balance[20130].accBalance : 0));//慈善救助金
                    /** 城市税收对接账户 **/
                    $("#scs_point_tax").html(comm.formatMoneyNumberAps(comm.isNotEmpty(balance[10510]) ? balance[10510].accBalance : 0)).attr("style", "margin-right:20px");//积分税收金额总数
                    $("#scs_hsb_tax").html(comm.formatMoneyNumberAps(comm.isNotEmpty(balance[20610]) ? balance[20610].accBalance : 0)).attr("style", "margin-right:20px");//互生币税收金额总数
                    $("#scs_currency_tax").html(comm.formatMoneyNumberAps(comm.isNotEmpty(balance[30310]) ? balance[30310].accBalance : 0)).attr("style", "margin-right:20px");//货币(人民币)税收金额总数
                }else{
                    /**积分账户**/
                    $("#scs_point_balance").html(comm.formatMoneyNumberAps(0));   //积分账户余数
                    $("#scs_point_balance_usable").html(comm.formatMoneyNumberAps(0));//可用积分数
                    $("#scs_point_yestday").html(comm.formatMoneyNumberAps(0));//昨日积分数
                    /** 投资账户 **/
                    $("#scs_invest_sum").html(comm.formatMoneyNumberAps(0));//积分投资总数
                    $("#scs_invest_income").html(last_year+"年度投资分红互生币："+comm.formatMoneyNumberAps(0));//本年度投资分红互生币
                    $("#scs_invest_income_ltb").html(comm.formatMoneyNumberAps(0));//本年度流通币
                    $("#scs_invest_income_csjz").html(comm.formatMoneyNumberAps(0));//本年度慈善救助
                    /** 货币账户 **/
                    $("#scs_currency_balance").html(comm.formatMoneyNumberAps( 0));//货币账户余额
                    /** 互生币账户 **/
                    $("#scs_hsb_ltb").html(comm.formatMoneyNumberAps( 0));//流通币
                    $("#scs_hsb_csjz").html(comm.formatMoneyNumberAps( 0));//慈善救助金
                    /** 城市税收对接账户 **/
                    $("#scs_point_tax").html(comm.formatMoneyNumberAps(0)).attr("style", "margin-right:20px");//积分税收金额总数
                    $("#scs_hsb_tax").html(comm.formatMoneyNumberAps(0)).attr("style", "margin-right:20px");//互生币税收金额总数
                    $("#scs_currency_tax").html(comm.formatMoneyNumberAps(0)).attr("style", "margin-right:20px");//货币(人民币)税收金额总数
                }
            }
        });
    });
    //报送单位查询（服务公司）
    $("a[href=#jqp-tabs_1-8]").click(function () {
        var applyParam = {resNo: $("#apply_org_no").val()};
        callCenter.searchEntContactInfo(applyParam, function (request) {
            if (request.retCode == 22000) {
                var apply = request.data;
                $("#apply_ent_resNo").html(apply.entResNo);//企业管理号
                $("#apply_ent_name").html(apply.entName); //企业姓名
                $("#apply_ent_cont_name").html(apply.contactPerson); //联系人姓名
                $("#apply_ent_cont_mobile").html(apply.contactPhone); //联系人手机
                $("#apply_ent_cont_address").html(apply.contactAddr); //联系地址
            }
        });
    });
    /*end*/

    //企业详情
    var scs_resNo = $("#intpu_search_company").val();
    var param = {resNo: scs_resNo};
    $("#qyDetail_btn").click(function () {
        $("#qyDetail_content").dialog({
            title: "企业详细信息",
            width: "980",
			height:"580",
            modal: true,
            buttons: {
                "确定": function () {
                    $(this).dialog("destroy");
                }
            }
        });
        $(".jqp-tabs_2").tabs();
        $("[href=#jqp-tabs_2-1]").click();
        scs_resNo = $("#intpu_search_company").val();
        param = {resNo: scs_resNo};
    });
//查询银行卡列表
    $("a[href=#jqp-tabs_2-4]").click(function(){
        callCenter.getBankAccountList(param, function (request) {
            if (request.retCode == 22000 && comm.isNotEmpty(request.data)) {
                comm.call_bank_List_print("jqp-tabs_2-4 ul",request.data);
            }
        });
    });
    //快捷支付卡列表
    $("a[href=#jqp-tabs_2-5]").click(function(){
        callCenter.getQkBankAccountList(param, function (request) {
            if (request.retCode == 22000 && comm.isNotEmpty(request.data)) {
                comm.call_bank_List_print("jqp-tabs_2-5 ul",request.data);
            }
        });
    });
    //业务操作许可
    $("a[href=#jqp-tabs_2-6]").click(function(){
        callCenter.queryBusinessPmInfo(param,function(request){
            if(request.retCode == 22000 && comm.isNotEmpty(request.data)){
                var obj =request.data;
                var pvToHsb = "0" == obj.PV_TO_HSB ? "启用" : "限制";
				var pvInvest = "0" == obj.PV_INVEST ? "启用" : "限制";
				var buyHsb = "0" == obj.BUY_HSB ? "启用" : "限制";
				var hsbToCash = "0" == obj.HSB_TO_CASH ? "启用" : "限制";
				var cashToBank = "0" == obj.CASH_TO_BANK ? "启用" : "限制";
				
				var pvToHSBRemark = $.trim(obj.PV_TO_HSBRemark);
				var pvINVESTRemark = $.trim(obj.PV_INVESTRemark);
				var buyHSBRemark = $.trim(obj.BUY_HSBRemark);
				var hsbToCASHRemark = $.trim(obj.HSB_TO_CASHRemark);
				var cashToBANKRemark = $.trim(obj.CASH_TO_BANKRemark);
				
                
				//积分转互生币
				 $("#jqp-tabs_2-6 tr:eq(1) td:eq(1)").addClass("view_text").html(pvToHsb);    
				 $("#jqp-tabs_2-6 tr:eq(1) td:eq(2)").addClass("view_text").html(pvToHSBRemark); 
                
                if(comm.getResNoType(param.resNo)=="B"){    //成员企业没有积分投资
                    $("#jqp-tabs_2-6 tr:eq(2)").addClass("none");
                }else{
                	//积分投资
                    $("#jqp-tabs_2-6 tr:eq(2)").addClass();
                    $("#jqp-tabs_2-6 tr:eq(2) td:eq(1)").addClass("view_text").html(pvInvest);
                    $("#jqp-tabs_2-6 tr:eq(2) td:eq(2)").addClass("view_text").html(pvINVESTRemark);
                }
                    //兑换互生币
                $("#jqp-tabs_2-6 tr:eq(3) td:eq(1)").addClass("view_text").html(buyHsb);
                $("#jqp-tabs_2-6 tr:eq(3) td:eq(2)").addClass("view_text").html(buyHSBRemark);
                //互生币转货币
                $("#jqp-tabs_2-6 tr:eq(4) td:eq(1)").addClass("view_text").html(hsbToCash);
                $("#jqp-tabs_2-6 tr:eq(4) td:eq(2)").addClass("view_text").html(hsbToCASHRemark);
                //货币转银行
                $("#jqp-tabs_2-6 tr:eq(5) td:eq(1)").addClass("view_text").html(cashToBank);
                $("#jqp-tabs_2-6 tr:eq(5) td:eq(2)").addClass("view_text").html(cashToBANKRemark);
            }
        });
    })
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
    $("a[href=#jqp-tabs_2-7]").click(function(){
        var param = {resNo: scs_resNo};
        var point_yestday = 0;
       
        //本年度 投资及投资分红查询
        var invest_income_sum = 0;    //投资
        var invest_income_ltb = 0;    //流通币
        var invest_income_csjz = 0;   //慈善救助金
        var last_year=2015;
       
        callCenter.getAccountBalance(param, function (request) {
        	
            if (request.retCode == 22000) {
                var balance = request.data;
                if(comm.isNotEmpty(balance)){
                    /**积分账户**/
                    $("#company_point_balance").html(comm.formatMoneyNumberAps(comm.isNotEmpty(balance[10110]) ? balance[10110].accBalance : 0));   //积分账户余数
                    $("#company_point_balance_usable").html(comm.formatMoneyNumberAps(comm.isNotEmpty(balance[10110]) ? (balance[10110].accBalance<10 ? 0:(balance[10110].accBalance - 10)) : 0));//可用积分数
                    //昨日积分数
                    callCenter.searchEntIntegralByYesterday(param, function (request) {
                        if (request.retCode == 22000) {
                            if (comm.isNotEmpty(request.data)) {
                                point_yestday = request.data.sumAmount;
                            }
                        }//异步请求作用域内变量才有效
                        $("#company_point_yestday").html(comm.formatMoneyNumberAps(point_yestday));
                    });
                    
                    /** 投资账户 **/
                    if(comm.getResNoType(param.resNo)=="B"){//成员企业没有积分账户，没有慈善救助金
                        $("#company_invest_sum").parent().parent().parent().attr("style","display:none");
                        $("#company_hsb_csjz").parent().attr("style","display:none");
                    }else{
                        $("#company_invest_sum").parent().parent().parent().removeAttr("style","display:none");
                        $("#company_hsb_csjz").parent().removeAttr("style","display:none");
                        $("#company_invest_sum").html(comm.formatMoneyNumberAps(comm.isNotEmpty(balance[10410]) ? balance[10410].accBalance : 0));//积分投资总数
                        $("#company_hsb_csjz").html(comm.formatMoneyNumberAps(comm.isNotEmpty(balance[20130]) ? balance[20130].accBalance : 0));//慈善救助金
                      //本年度投资分红互生币,本年度流通币,本年度慈善救助
                        callCenter.findInvestDividendInfo(param, function (request) {
                            if (request.retCode == 22000) {
                                if (comm.isNotEmpty(request.data)) {
                                    invest_income_sum = request.data.totalDividend;
                                    invest_income_ltb = request.data.normalDividend;
                                    invest_income_csjz = request.data.directionalDividend;
                                    last_year=request.data.dividendYear;
                                }
                            }//异步请求作用域内变量才有效
                            $("#company_invest_income").html(last_year+"年度投资分红互生币："+comm.formatMoneyNumberAps(invest_income_sum));//本年度投资分红互生币
                            $("#company_invest_income_ltb").html(comm.formatMoneyNumberAps(invest_income_ltb));//本年度流通币
                            $("#company_invest_income_csjz").html(comm.formatMoneyNumberAps(invest_income_csjz));//本年度慈善救助
                        });
                        
                    }
                    /** 货币账户 **/
                    $("#company_currency_balance").html(comm.formatMoneyNumberAps(comm.isNotEmpty(balance[30110]) ? balance[30110].accBalance : 0));//货币账户余额
                    /** 互生币账户 **/
                    $("#company_hsb_ltb").html(comm.formatMoneyNumberAps(comm.isNotEmpty(balance[20110]) ? balance[20110].accBalance : 0));//流通币
                    /** 城市税收对接账户 **/
                    $("#company_point_tax").html(comm.formatMoneyNumberAps(comm.isNotEmpty(balance[10510]) ? balance[10510].accBalance : 0)).attr("style", "margin-right:20px");//积分税收金额总数
                    $("#company_hsb_tax").html(comm.formatMoneyNumberAps(comm.isNotEmpty(balance[20610]) ? balance[20610].accBalance : 0)).attr("style", "margin-right:20px");//互生币税收金额总数
                    $("#company_currency_tax").html(comm.formatMoneyNumberAps(comm.isNotEmpty(balance[30310]) ? balance[30310].accBalance : 0)).attr("style", "margin-right:20px");//货币(人民币)税收金额总数
                }else{
                    /**积分账户**/
                    $("#company_point_balance").html(comm.formatMoneyNumberAps(0));   //积分账户余数
                    $("#company_point_balance_usable").html(comm.formatMoneyNumberAps(0));//可用积分数
                    $("#company_point_yestday").html(comm.formatMoneyNumberAps(0));//昨日积分数
                    /** 投资账户 **/
                    if(comm.getResNoType(param.resNo)=="B"){//成员企业没有积分账户，没有慈善救助金
                        $("#company_invest_sum").parent().parent().parent().attr("style","display:none");
                        $("#company_hsb_csjz").parent().attr("style","display:none");
                    }else{
                        $("#company_invest_sum").parent().parent().parent().removeAttr("style","display:none");
                        $("#company_hsb_csjz").parent().removeAttr("style","display:none");
                        $("#company_invest_sum").html(comm.formatMoneyNumberAps(0));//积分投资总数
                        $("#company_invest_income").html(last_year+"年度投资分红互生币："+comm.formatMoneyNumberAps(0));//本年度投资分红互生币
                        $("#company_invest_income_ltb").html(comm.formatMoneyNumberAps(0));//本年度流通币
                        $("#company_invest_income_csjz").html(comm.formatMoneyNumberAps(0));//本年度慈善救助
                        $("#company_hsb_csjz").html(comm.formatMoneyNumberAps(0));//慈善救助金
                    }
                    /** 货币账户 **/
                    $("#company_currency_balance").html(comm.formatMoneyNumberAps(0));//货币账户余额
                    /** 互生币账户 **/
                    $("#company_hsb_ltb").html(comm.formatMoneyNumberAps(0));//流通币
                    /** 城市税收对接账户 **/
                    $("#company_point_tax").html(comm.formatMoneyNumberAps(0)).attr("style", "margin-right:20px");//积分税收金额总数
                    $("#company_hsb_tax").html(comm.formatMoneyNumberAps(0)).attr("style", "margin-right:20px");//互生币税收金额总数
                    $("#company_currency_tax").html(comm.formatMoneyNumberAps(0)).attr("style", "margin-right:20px");//货币(人民币)税收金额总数
                }
            }
        });
    });
    //报送单位查询（服务公司）
    $("a[href=#jqp-tabs_2-8]").click(function(){
        var applyParam = {resNo: $("#apply_org_no").val()};
        callCenter.searchEntContactInfo(applyParam, function (request) {
            if (request.retCode == 22000) {
                var apply = request.data;
                $("#apply_company_resNo").html(apply.entResNo);//企业管理号
                $("#apply_company_entName").html(apply.entName); //企业姓名
                $("#apply_company_cont_name").html(apply.contactPerson); //联系人姓名
                $("#apply_company_cont_mobile").html(apply.contactPhone); //联系人手机
                $("#apply_company_cont_address").html(apply.contactAddr); //联系地址
            }
        });
    });
    /*end*/

    //消费者详情
    $("#xfzDetail_btn").click(function () {
        $("#xfzDetail_content").dialog({
            title: "消费者详细信息",
            width: "1000",
			height:"600",
            modal: true,
            buttons: {
                "确定": function () {
                    $(this).dialog("destroy");
                }
            }
        });

        $(".jqp-tabs_3").tabs();
        $("[href=#jqp-tabs_3-1]").click();
        p_resNo = $("#intpu_search_person").val();
        param = {resNo: p_resNo};

    });
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
    $("a[href=#jqp-tabs_3-7]").click(function(){
        var p_resNo = $("#intpu_search_person").val();
        var param = {resNo: p_resNo};
        var point_today = 0;
        
        //本年度 投资及投资分红查询
        var invest_income_sum = 0;    //投资
        var invest_income_ltb = 0;    //流通币
        var invest_income_dxxf = 0;   //定向消费币
        var last_year=2015;
       
        //账户余额查询
        callCenter.getAccountBalance(param, function (request) {
        	
            if (request.retCode == 22000) {
                var balance = comm.isNotEmpty(request.data) ? request.data : null;
                if(comm.isNotEmpty(balance)){
                    
                	/**积分账户**/
                	$("#jqp-tabs_3-7 #p_point_balance").html(comm.formatMoneyNumberAps(comm.isNotEmpty(balance[10110]) ? balance[10110].accBalance : 0));   //积分账户余数
                	$("#jqp-tabs_3-7 #p_point_usable").html(comm.formatMoneyNumberAps(comm.isNotEmpty(balance[10110]) ? (balance[10110].accBalance<10 ? 0:(balance[10110].accBalance - 10)) : 0));//可用积分数
                    //消费者今日积分数
                    callCenter.searchPerIntegralByToday(param, function (request) {
                       if (request.retCode == 22000) {
                           if (comm.isNotEmpty(request.data)) {
                               point_today = request.data.sumAmount;
                           }
                       }//异步请求作用域内才能获取
                       $("#jqp-tabs_3-7 #p_point_today").html(comm.formatMoneyNumberAps(point_today)); 
                    }); 
                    
                    /** 货币账户 **/
                    $("#jqp-tabs_3-7 #p_currency_balance").html(comm.formatMoneyNumberAps(comm.isNotEmpty(balance[30110]) ? balance[30110].accBalance : 0));//货币账户余额
                    /** 互生币账户 **/
                    $("#jqp-tabs_3-7 #p_hsb_ltb").html(comm.formatMoneyNumberAps(comm.isNotEmpty(balance[20110]) ? balance[20110].accBalance : 0));//流通币
                    $("#jqp-tabs_3-7 #p_hsb_dxxf").html(comm.formatMoneyNumberAps(comm.isNotEmpty(balance[20120]) ? balance[20120].accBalance : 0));//定向消费
                    
                    /** 投资账户 **/
                    $("#jqp-tabs_3-7 #p_invest_sum").html(comm.formatMoneyNumberAps(comm.isNotEmpty(balance[10410]) ? balance[10410].accBalance : 0));//积分投资总数
                    //本年度投资分红互生币,本年度流通币,本年度慈善救助
                    callCenter.findInvestDividendInfo(param, function (request) {
                        if (request.retCode == 22000) {
                            if (comm.isNotEmpty(request.data)) {
                                invest_income_sum = request.data.totalDividend;
                                invest_income_ltb = request.data.normalDividend;
                                invest_income_dxxf = request.data.directionalDividend;
                                last_year=request.data.dividendYear;
                            }
                        }//异步请求作用域内才能获取
                        $("#jqp-tabs_3-7 #p_invest_income_sum").html(last_year+"年度投资分红互生币："+comm.formatMoneyNumberAps(invest_income_sum));//本年度投资分红互生币
                        $("#jqp-tabs_3-7 #p_invest_income_ltb").html(comm.formatMoneyNumberAps(invest_income_ltb));//本年度流通币
                        $("#jqp-tabs_3-7 #p_invest_income_dxxf").html(comm.formatMoneyNumberAps(invest_income_dxxf));//本年度慈善救助
                    });
                }else{
                    /**积分账户**/
                    $("#jqp-tabs_3-7 #p_point_balance").html(comm.formatMoneyNumberAps(0));   //积分账户余数
                    $("#jqp-tabs_3-7 #p_point_usable").html(comm.formatMoneyNumberAps(0));//可用积分数
                    $("#jqp-tabs_3-7 #p_point_today").html(comm.formatMoneyNumberAps(0));//今日积分数
                    /** 货币账户 **/
                    $("#jqp-tabs_3-7 #p_currency_balance").html(comm.formatMoneyNumberAps(0));//货币账户余额
                    /** 互生币账户 **/
                    $("#jqp-tabs_3-7 #p_hsb_ltb").html(comm.formatMoneyNumberAps(0));//流通币
                    $("#jqp-tabs_3-7 #p_hsb_dxxf").html(comm.formatMoneyNumberAps(0));//定向消费
                    /** 投资账户 **/
                    $("#jqp-tabs_3-7 #p_invest_sum").html(comm.formatMoneyNumberAps(0));//积分投资总数
                    $("#jqp-tabs_3-7 #p_invest_income_sum").html(last_year+"年度投资分红互生币："+comm.formatMoneyNumberAps(0));//本年度投资分红互生币
                    $("#jqp-tabs_3-7 #p_invest_income_ltb").html(comm.formatMoneyNumberAps(0));//本年度流通币
                    $("#jqp-tabs_3-7 #p_invest_income_dxxf").html(comm.formatMoneyNumberAps(0));//本年度慈善救助
                }
            }
        });
    });

    //积分福利资格列表（此处数据有问题，需要黄高阳查询接口）
    $("a[href=#jqp-tabs_3-8]").click(function(){
        var param = {search_resNo: p_resNo, search_welfareType: 0};
        callCenter.getWelfareList("searchTable_xfz_jfflxx",param,function(record, rowIndex, colIndex, options){comm.alert(record);});
    });
    /*下拉列表*/
    $("#sqfllb").selectList({
        width: 150,
        optionWidth: 150,
        options: [
            {name: comm.langConfig.common.welfareType[0], value: '0'},
            {name: comm.langConfig.common.welfareType[2], value: '2'},
            {name: comm.langConfig.common.welfareType[1], value: '1'}
        ]
    }).change(function (e) {
    });
    /*end*/
    //积分福利申请记录
    $("#qry_btn").click(function() {
        var p_welfareType=$("#sqfllb").next().find(".selectList-active").attr("data-value");
        p_welfareType=comm.isNotEmpty(p_welfareType)?p_welfareType:4;
        if(comm.isNotEmpty(p_welfareType)){
            param = {
                search_resNo: p_resNo,
                search_welfareType: p_welfareType
            };
            callCenter.getListWelfareApply("searchTable_xfz_jffl",param,function(record, rowIndex, colIndex, options){
                if (colIndex == 2) {//申请类型
                    return comm.langConfig.common.welfareType[record.welfareType];
                }
                if (colIndex == 3) {
                    return comm.langConfig.common.welfareApprovalStatus[record.approvalStatus]; //0 受理中 1 已受理 2 驳回
                }
            });
        }else{
            comm.error_alert(comm.langConfig.common.welfareTypeEmpty);
        }
    });
    $("#qry_btn").click();
    //互生卡补办记录查询
    $("a[href=#jqp-tabs_3-10]").click(function(){
        var p_resNo = $("#intpu_search_person").val();
        param = {search_hsResNo: p_resNo};
        callCenter.findCardapplyList("searchTable_xfz_hskbb",param,function(record, rowIndex, colIndex, options){
            if (colIndex == 3) {
                return comm.langConfig.common.hsCardOrderStatus[record.orderStatus];
            }
        });
    });
    //查询银行卡列表
    $("a[href=#jqp-tabs_3-4]").click(function(){
        var p_resNo = $("#intpu_search_person").val();
        param = {resNo: p_resNo};
        callCenter.getBankAccountList(param, function (request) {
            if (request.retCode == 22000 && comm.isNotEmpty(request.data)) {
                comm.call_bank_List_print("jqp-tabs_3-4 ul",request.data);
            }
        });
    });
    //快捷支付卡列表
    $("a[href=#jqp-tabs_3-5]").click(function(){
        var p_resNo = $("#intpu_search_person").val();
        var param = {resNo: p_resNo};
        callCenter.getQkBankAccountList(param, function (request) {
            if (request.retCode == 22000 && comm.isNotEmpty(request.data)) {
                comm.call_bank_List_print("jqp-tabs_3-5 ul", request.data);
            }
        });
    });
    //业务操作许可
    $("a[href=#jqp-tabs_3-6]").click(function(){
        var p_resNo = $("#intpu_search_person").val();
        var param = {resNo: p_resNo};
       /* callCenter.queryBusinessPmInfo(param,function(request){
            if(request.retCode == 22000 && comm.isNotEmpty(request.data)){
                var data=request.data;
                if(data.PV_TO_HSB!=0)   { $("#jqp-tabs_3-6 tr:eq(0) td:odd  span").removeClass().addClass("red").html("限制");}    //积分转互生币
                if(data.PV_INVEST!=0)   { $("#jqp-tabs_3-6 tr:eq(1) td:odd  span").removeClass().addClass("red").html("限制");}    //积分投资
                if(data.BUY_HSB!=0)     { $("#jqp-tabs_3-6 tr:eq(2) td:odd  span").removeClass().addClass("red").html("限制");}    //兑换互生币
                if(data.HSB_TO_CASH!=0) { $("#jqp-tabs_3-6 tr:eq(3) td:odd  span").removeClass().addClass("red").html("限制");}    //互生币转货币
                if(data.CASH_TO_BANK!=0){ $("#jqp-tabs_3-6 tr:eq(4) td:odd  span").removeClass().addClass("red").html("限制");}    //货币转银行
            }
        });*/
        
        callCenter.queryBusinessPmInfo(param,function(request){
            if(request.retCode == 22000 && comm.isNotEmpty(request.data)){
                var obj =request.data;
                var pvToHsb = "0" == obj.PV_TO_HSB ? "启用" : "限制";
				var pvInvest = "0" == obj.PV_INVEST ? "启用" : "限制";
				var buyHsb = "0" == obj.BUY_HSB ? "启用" : "限制";
				var hsbToCash = "0" == obj.HSB_TO_CASH ? "启用" : "限制";
				var cashToBank = "0" == obj.CASH_TO_BANK ? "启用" : "限制";
				
				var pvToHSBRemark = $.trim(obj.PV_TO_HSBRemark);
				var pvINVESTRemark = $.trim(obj.PV_INVESTRemark);
				var buyHSBRemark = $.trim(obj.BUY_HSBRemark);
				var hsbToCASHRemark = $.trim(obj.HSB_TO_CASHRemark);
				var cashToBANKRemark = $.trim(obj.CASH_TO_BANKRemark);
				
                
				//积分转互生币
				 $("#jqp-tabs_3-6 tr:eq(1) td:eq(1)").addClass("view_text").html(pvToHsb);    
				 $("#jqp-tabs_3-6 tr:eq(1) td:eq(2)").addClass("view_text").html(pvToHSBRemark); 
                
               
            	//积分投资
                $("#jqp-tabs_3-6 tr:eq(2)").addClass();
                $("#jqp-tabs_3-6 tr:eq(2) td:eq(1)").addClass("view_text").html(pvInvest);
                $("#jqp-tabs_3-6 tr:eq(2) td:eq(2)").addClass("view_text").html(pvINVESTRemark);
                
                //兑换互生币
                $("#jqp-tabs_3-6 tr:eq(3) td:eq(1)").addClass("view_text").html(buyHsb);
                $("#jqp-tabs_3-6 tr:eq(3) td:eq(2)").addClass("view_text").html(buyHSBRemark);
                //互生币转货币
                $("#jqp-tabs_3-6 tr:eq(4) td:eq(1)").addClass("view_text").html(hsbToCash);
                $("#jqp-tabs_3-6 tr:eq(4) td:eq(2)").addClass("view_text").html(hsbToCASHRemark);
                //货币转银行
                $("#jqp-tabs_3-6 tr:eq(5) td:eq(1)").addClass("view_text").html(cashToBank);
                $("#jqp-tabs_3-6 tr:eq(5) td:eq(2)").addClass("view_text").html(cashToBANKRemark);
            }
        });
    });
    /**rightBar.html 用户信息查询  右边顶上**/
        //消费者信息查询
    $("#search_person").click(function () {
        $("#person_panel").addClass("none");
        var p_resNo = $("#intpu_search_person").val();
        var param = {resNo: p_resNo};
        if (comm.getResNoType(p_resNo) == "P") {
            callCenter.getPersonAllInfo(param, function (request) {
                if (request.retCode == 22000) {

                    var data = request.data;
                    var userName = data.authInfo.userName;
                    $("#person_panel").removeClass("none");
                    if(comm.isNotEmpty(userName)){
                        userName = userName.length<3 ? userName : userName.substring(0,2)+"..";
                    }
                    $("#p_username").html(userName);//姓名
                    $("#p_username").attr('title',userName);//姓名
                    $("#p_username1").html(userName);//姓名
                    $("#p_resNo").html(data.baseInfo.perResNo.substring(0, 2) + "&nbsp;" + data.baseInfo.perResNo.substring(2, 5) + "&nbsp;" + data.baseInfo.perResNo.substring(5, 7) + "&nbsp;" + data.baseInfo.perResNo.substring(7, 11) + "&nbsp;");         //资源号
                    //authInfo.realNameStatus 实名状态  1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证
                    if (data.authInfo.realNameStatus == 2) {//2：已实名注册（有名字和身份证）
                        $("#p_real_reg").attr("class", "fl service_icon service_icon_zc ml5").attr("title", "已实名注册");
                        $("#p_real_iden").attr("class", "fl service_icon service_icon_rz_disable ml5").attr("title", "未实名认证");
                    } else if (data.authInfo.realNameStatus == 3) {  //实名认证
                        $("#p_real_reg").attr("class", "fl service_icon service_icon_zc ml5").attr("title", "已实名注册");
                        $("#p_real_iden").attr("class", "fl service_icon service_icon_rz ml5").attr("title", "已实名认证");
                    } else {  //未实名注册
                        $("#p_real_reg").attr("class", "fl service_icon service_icon_rz_disable ml5").attr("title", "未实名注册");
                        $("#p_real_iden").attr("class", "fl service_icon service_icon_rz_disable ml5").attr("title", "未实名认证");
                    }
                    $("#p_mobile").html(data.baseInfo.mobile);//手机号
                    $("#p_cardStatus").html(comm.langConfig.common.cardStatus[data.cardInfo.cardStatus]);// 卡状态(1：启用、2：挂失)

                    /***消费者详情***/
                        //头部信息
                    $("#p_res_no").html(p_resNo);
                    if (data.authInfo.realNameStatus == 2) {//2：已实名注册（有名字和身份证）
                        $("#p_real_reg1").attr("class", "fl service_icon service_icon_Bbd ml10").attr("title", "已实名注册");
                        $("#p_real_auth1").attr("class", "fl service_icon service_icon_Brz_disable ml10").attr("title", "未实名认证");
                    } else if (data.authInfo.realNameStatus == 3) {  //实名认证
                        $("#p_real_reg1").attr("class", "fl service_icon service_icon_Bbd ml10").attr("title", "已实名注册");
                        $("#p_real_auth1").attr("class", "fl service_icon service_icon_Bbd ml10").attr("title", "已实名认证");
                    } else {  //未实名注册
                        $("#p_real_reg1").attr("class", "fl service_icon service_icon_Bzc_disable ml10").attr("title", "未实名注册");
                        $("#p_real_auth1").attr("class", "fl service_icon service_icon_Brz_disable ml10").attr("title", "未实名认证");
                    }
                    $("#p_card_status").html(comm.langConfig.common.cardStatus[data.cardInfo.cardStatus]);
                    //互生卡身份信息
                    var cerType = data.authInfo.cerType;
                    //通过认证方式不同动态展示-消费者信息
                    dynCustomerInfoTab(cerType,data);
                    //手机
                    if(comm.isNotEmpty(data.baseInfo.mobile)){
                        $("#jqp-tabs_3-2 td:odd span").html(data.baseInfo.mobile);
                        $("#jqp-tabs_3-2 td:odd span:eq(1)").html(data.baseInfo.isAuthMobile==0?"[未验证]":"[已验证]");
                    }else{
                        $("#jqp-tabs_3-2 td:odd span").html("未绑定");
                        $("#jqp-tabs_3-2 td:odd span:eq(1)").html("");
                    }
                    //邮箱
                    if(comm.isNotEmpty(data.baseInfo.email)){
                        $("#jqp-tabs_3-3 td:odd span:eq(0)").html(data.baseInfo.email);
                        $("#jqp-tabs_3-3 td:odd span:eq(1)").html(data.baseInfo.isAuthEmail==0?"[未验证]":"[已验证]");
                    }else{
                        $("#jqp-tabs_3-3 td:odd span:eq(0)").html("未绑定");
                        $("#jqp-tabs_3-3 td:odd span:eq(1)").html("");
                    }
                    //账户信息 ok

                    //积分福利信息 ok

                    //积分福利申请信息 ok

                    //互生卡补办记录信息 ok

                }
            });
        } else {
            comm.alert("请输入正确的11位消费者互生号");
        }
    });
    
    /**
     * 2016-05-26 
     * 根据认证类型-动态展示消费者信息
     * author:taojy
     */
    function dynCustomerInfoTab(cerType, data){
    	var tab = $("#jqp-tabs_3-1 tbody");
    	tab.children().remove();
    	
    	var content,firLine,secLine,thrLine,fouLine,fifLine,sixLine;
    	var cerPica,cerPicb,cerPich;
    	var info = []
    	var userId=comm.getSysCookie('custId');
    	var token =comm.getSysCookie('token');
    	var imgPara='?channel=1&userId='+userId+'&token='+token;
    	
    	//认证类型:身份证(未认证的也用身份证样式展示)
    	if(null==cerType||1==cerType){
    	firLine ="<tr><td class='view_item' width='20%'>互生卡号</td>"+
            "<td class='view_text' width='30%'><span class='hskh_txt'><%=obj.p_resNo%></span></td>"+
            "<td class='view_item' width='20%'>姓名</td>"+
            "<td class='view_text' width='30%'><span class='mc_txt'><%=obj.userName%></span></td></tr>";
	    secLine ="<tr><td class='view_item' width='20%'>国籍</td>"+
            "<td class='view_text' width='30%' id='p_countryCode'><span class='hskh_txt'><%=obj.countryCode%></span></td>"+
            "<td class='view_item' width='20%'>证件类型</td>"+
            "<td class='view_text' width='30%'><span class='mc_txt'><%=obj.cerType%></span></td></tr>";
	    thrLine ="<tr><td class='view_item' width='20%'>证件号码</td>"+
            "<td class='view_text' width='30%'><span class='hskh_txt'><%=obj.cerNo%></span></td>"+
            "<td class='view_item' width='20%'>性别</td>"+
            "<td class='view_text' width='30%'><span class='mc_txt'><%=obj.sex%></span></td></tr>";
	    fouLine ="<tr><td class='view_item' width='20%'>职业</td>"+
            "<td class='view_text' width='30%'><span class='hskh_txt'><%=obj.job%></span></td>"+
            "<td class='view_item' width='20%'>证件有效期</td>"+
            "<td class='view_text' width='30%'><span class='mc_txt'><%=obj.validDate%></span></td></tr>";
	    fifLine ="<tr><td class='view_item' width='20%'>发证机关</td>"+
            "<td class='view_text' width='30%'><span class='hskh_txt'><%=obj.issuingOrg%></span></td>"+
            "<td class='view_item' width='20%'>户籍地址</td>"+
            "<td class='view_text' width='30%'><span class='mc_txt'><%=obj.birthAddress%></span></td></tr>";
	    sixLine ="<tr><td class='view_item' width='20%'>证件图片</td>"+
            "<td colspan='3' class='view_text'><span id='p_info_img'><%=obj.pImg%></span></td></tr>";
	    
	    content = firLine + secLine + thrLine + fouLine + fifLine + sixLine;
	    
	    cerPica=comm.isNotEmpty(data.authInfo.cerPica)?'<a class="blue ml10 viewCardStyleImg" id="p_cre_img1" data-imgsrc="'
	   			 +comm.domainList.fsServerUrl+data.authInfo.cerPica+imgPara+'">查看正面照</a>':'';
	    cerPicb=comm.isNotEmpty(data.authInfo.cerPicb)?'<a class="blue ml10 viewCardStyleImg" id="p_cre_img2" data-imgsrc="'
	    		 +comm.domainList.fsServerUrl+data.authInfo.cerPicb+imgPara+'">查看反面照</a>':'';
	    cerPich=comm.isNotEmpty(data.authInfo.cerPich)?'<a class="blue ml10 viewCardStyleImg" id="p_cre_img3" data-imgsrc="'
	       		+comm.domainList.fsServerUrl+data.authInfo.cerPich+imgPara+'">查看手持证件大头照</a>':'';
	    
	    var	result = { p_resNo     : data.baseInfo.perResNo,
					   userName    : data.authInfo.userName,
					   cerType     : comm.langConfig.common.creTypes[data.authInfo.cerType],
					   cerNo       : data.authInfo.cerNo,
					   sex         : comm.langConfig.common.sexTypes[data.authInfo.sex],
					   job         :  data.authInfo.job,
					   validDate   : data.authInfo.validDate,
					   issuingOrg  : data.authInfo.issuingOrg,
			           birthAddress: data.authInfo.birthAddress,
			           pImg        : cerPica + cerPicb + cerPich
		          }
		 info.push(_.template(content, result));
		
		 //国籍查询接口基于异步,特殊处理一下
		 cacheUtil.syncGetRegionByCode(data.authInfo.countryCode, "", "", "", function (resdata) {
			 $("#p_countryCode").html(resdata);    //国籍
	     });
    	}
    	
    	//认证类型:护照
    	if(2==cerType){
    		
    	firLine ="<tr><td class='view_item' width='20%'>互生卡号</td>"+
            "<td class='view_text' width='30%'><span class='hskh_txt'><%=obj.p_resNo%></span></td>"+
            "<td class='view_item' width='20%'>姓名</td>"+
            "<td class='view_text' width='30%'><span class='mc_txt'><%=obj.userName%></span></td></tr>";
	    secLine ="<tr><td class='view_item' width='20%'>性别</td>"+
            "<td class='view_text' width='30%'><span class='hskh_txt'><%=obj.sex%></span></td>"+
            "<td class='view_item' width='20%'>国籍</td>"+
            "<td class='view_text' width='30%' id='p_countryCode'><span class='mc_txt'><%=obj.countryCode%></span></td></tr>";
	    thrLine ="<tr><td class='view_item' width='20%'>证件类型</td>"+
            "<td class='view_text' width='30%'><span class='hskh_txt'><%=obj.cerType%></span></td>"+
            "<td class='view_item' width='20%'>证件号码</td>"+
            "<td class='view_text' width='30%'><span class='mc_txt'><%=obj.cerNo%></span></td></tr>";
	    fouLine ="<tr><td class='view_item' width='20%'>证件有效期</td>"+
            "<td class='view_text' width='30%'><span class='hskh_txt'><%=obj.validDate%></span></td>"+
            "<td class='view_item' width='20%'>出生地点</td>"+
            "<td class='view_text' width='30%'><span class='mc_txt'><%=obj.birthAddress%></span></td></tr>";
	    fifLine ="<tr><td class='view_item' width='20%'>签发地点</td>"+
            "<td class='view_text' width='30%'><span class='hskh_txt'><%=obj.issuePlace%></span></td>"+
            "<td class='view_item' width='20%'>签发机关</td>"+
            "<td class='view_text' width='30%'><span class='mc_txt'><%=obj.issuingOrg%></span></td></tr>";
	    sixLine ="<tr><td class='view_item' width='20%'>证件图片</td>"+
            "<td colspan='3' class='view_text'><span id='p_info_img'><%=obj.pImg%></span></td></tr>";
	    content = firLine + secLine + thrLine + fouLine + fifLine + sixLine;
	                                                                        
	    cerPica=comm.isNotEmpty(data.authInfo.cerPica)?'<a class="blue ml10 viewCardStyleImg" id="p_cre_img1" data-imgsrc="'
   			 +comm.domainList.fsServerUrl+data.authInfo.cerPica+imgPara+'">查看正面照</a>':'';
        cerPich=comm.isNotEmpty(data.authInfo.cerPich)?'<a class="blue ml10 viewCardStyleImg" id="p_cre_img3" data-imgsrc="'
       		+comm.domainList.fsServerUrl+data.authInfo.cerPich+imgPara+'">查看手持证件大头照</a>':'';
	    
    	var	result = { p_resNo     : data.baseInfo.perResNo,
    				   userName    : data.authInfo.userName,
    				   sex         : comm.langConfig.common.sexTypes[data.authInfo.sex],
    				   cerType     : comm.langConfig.common.creTypes[data.authInfo.cerType],
    				   cerNo       : data.authInfo.cerNo,
    				   validDate   : data.authInfo.validDate,
    		           birthAddress: data.authInfo.birthAddress,
    		           issuePlace  : data.authInfo.issuePlace,
    		           issuingOrg  : data.authInfo.issuingOrg,
    		           pImg        : cerPica+cerPich
    		          }
    	 info.push(_.template(content, result));
    	
    	 //国籍查询接口基于异步,特殊处理一下
    	 cacheUtil.syncGetRegionByCode(data.authInfo.countryCode, "", "", "", function (resdata) {
    		 $("#p_countryCode").html(resdata);    //国籍
          });
    	}
    	
    	//认证类型:营业执照
    	if(3==cerType){
    		
    	firLine ="<tr><td class='view_item' width='20%'>互生卡号</td>"+
            "<td class='view_text' width='30%'><span class='hskh_txt'><%=obj.p_resNo%></span></td>"+
            "<td class='view_item' width='20%'>企业名称</td>"+
            "<td class='view_text' width='30%'><span class='mc_txt'><%=obj.entName%></span></td></tr>";
	    secLine ="<tr><td class='view_item' width='20%'>证件类型</td>"+
            "<td class='view_text' width='30%'><span class='hskh_txt'><%=obj.cerType%></span></td>"+
            "<td class='view_item' width='20%'>证件号码</td>"+
            "<td class='view_text' width='30%'><span class='mc_txt'><%=obj.cerNo%></span></td></tr>";
	    thrLine ="<tr><td class='view_item' width='20%'>注册地址</td>"+
            "<td class='view_text' width='30%'><span class='hskh_txt'><%=obj.entRegAddr%></span></td>"+
            "<td class='view_item' width='20%'>公司类型</td>"+
            "<td class='view_text' width='30%'><span class='mc_txt'><%=obj.entType%></span></td></tr>";
	    fouLine ="<tr><td class='view_item' width='20%'>成立日期</td>"+
            "<td class='view_text' width='30%'><span class='hskh_txt'><%=obj.entBuildDate%></span></td>"+
            "<td class='view_item' width='20%'></td>"+
            "<td class='view_text' width='30%'></td></tr>";
	    fifLine ="<tr><td class='view_item' width='20%'>证件图片</td>"+
            "<td colspan='3' class='view_text'><span id='p_info_img'><%=obj.pImg%></span></td></tr>";
	    content = firLine + secLine + thrLine + fouLine + fifLine;
    		
	    cerPica=comm.isNotEmpty(data.authInfo.cerPica)?'<a class="blue ml10 viewCardStyleImg" id="p_cre_img1" data-imgsrc="'
   			 +comm.domainList.fsServerUrl+data.authInfo.cerPica+imgPara+'">查看正面照</a>':'';
        cerPich=comm.isNotEmpty(data.authInfo.cerPich)?'<a class="blue ml10 viewCardStyleImg" id="p_cre_img3" data-imgsrc="'
       		+comm.domainList.fsServerUrl+data.authInfo.cerPich+imgPara+'">查看手持证件大头照</a>':'';
	    
    	var	result = { p_resNo     : data.baseInfo.perResNo,
    			       entName     : data.authInfo.entName,
    				   cerType     : comm.langConfig.common.creTypes[data.authInfo.cerType],
    				   cerNo       : data.authInfo.cerNo,
    				   entRegAddr  : data.authInfo.entRegAddr,
    				   entType     : data.authInfo.entType,
    				   entBuildDate: data.authInfo.entBuildDate,
    		           pImg        : cerPica+cerPich
    		          }
    	 info.push(_.template(content, result));
    		
    	}
    	
    	tab.html(info);
    	
    	 $("#p_info_img .viewCardStyleImg").click(function(){
             $('#viewConfirmCardStyle > p').html('<img width="970" height="550" src="'+$(this).attr('data-imgsrc')+'"/>');
             $('#viewConfirmCardStyle').dialog({width:'1000',height:'650',title:"证件预览" ,closeIcon:true,buttons:{"关闭":function(){$(this).dialog( "destroy" );}}});
         });
    }

    //企业信息查询
    $("#search_company").click(function () {
        var company_resNo = $("#intpu_search_company").val();
        if (comm.getResNoType(company_resNo) == "T" || comm.getResNoType(company_resNo) == "B") {
            //查询企业联系信息
            var param = {resNo: company_resNo};
            callCenter.searchEntAllInfo(param, function (request) {
                if (request.retCode == 22000) {
                    var data = request.data;
                    $("#company_panel").removeClass("none");
                    $("#company_mobile").html(data.extendInfo.contactPhone);//联系人手机号
                    var contactPerson = data.extendInfo.contactPerson;
                    contactPerson = contactPerson.length<4 ? contactPerson : contactPerson.substring(0,3)+"...";
                    $("#company_contact").html(contactPerson);  //联系人姓名
                    $("#company_contact").attr('title',data.extendInfo.contactPerson);  //联系人姓名
                    // $("#company_tel").html(data.extendInfo.officeTel);  //工作电话
                    if (comm.isEmpty(data.extendInfo.officeTel)) {
                        $("#company_tel").prev().hide();
                        $("#company_tel").next().hide();
                    }
                    $("#company_resNo").html(data.extendInfo.entResNo.substring(0, 2) + "&nbsp;" + data.extendInfo.entResNo.substring(2, 5) + "&nbsp;" + data.extendInfo.entResNo.substring(5, 7) + "&nbsp;" + data.extendInfo.entResNo.substring(7, 11) + "&nbsp;");
                    var baseStatusInfo = comm.langConfig.common.entStatus[data.statusInfo.baseStatus];
                    if(baseStatusInfo && baseStatusInfo.length > 3){
                    	baseStatusInfo = baseStatusInfo.substr(0,3) + "...";
                    }
                    $("#company_type").html(comm.getResNoType(company_resNo) == "T" ? "托管&nbsp;" + baseStatusInfo : "成员&nbsp;" + baseStatusInfo);
                    $("#company_type").attr('title',comm.langConfig.common.entStatus[data.statusInfo.baseStatus]);
                    var ent_name=request.data.extendInfo.entCustName;
                    $("#company_entCustName").html(ent_name.length<=15?ent_name:ent_name.substring(0,14)+"...");//企业名称
                    $("#company_entCustName").attr('title',request.data.extendInfo.entCustName);  //企业名称

                    //托管成员企业详情
                    $("#ent_res_no").html(data.extendInfo.entResNo);
                    $("#company_name").html(data.extendInfo.entCustName);
                    //系统登记信息
                    $("#company_baseStatus").html(comm.langConfig.common.entStatus[data.statusInfo.baseStatus]);//企业状态
                    $("#company_resNo_1").html(data.extendInfo.entResNo);//企业资源号
                    $("#company_reg_date_1").html(data.statusInfo.openDate);//企业开启日期
                    $("#company_ent_name_cn").html(data.baseInfo.entName);//企业中文名称
                    $("#company_ent_name_en").html(data.baseInfo.entNameEn);//企业英文名称
                    $("#company_start_res_type").html(comm.langConfig.common.startResType[data.baseInfo.startResType]);//启用资源类型 企业资源类型 1：首段资源  2：创业资源  3：全部资源 4：正常成员企业 5：免费成员企业
                    //获取地区信息
                    cacheUtil.syncGetRegionByCode(data.baseInfo.countryCode, data.baseInfo.provinceCode, data.baseInfo.cityCode, "", function (resdata) {
                        $("#company_ent_area").html(resdata);
                    });
                    $("#company_ent_currency").html(data.baseInfo.countryCode == "156" ? "人民币" : "未知");//币种
                    //工商登记信息
                    $("#company_ent_name_2").html(data.baseInfo.entName); //企业名称
                    $("#company_ent_address_2").html(data.mainInfo.entRegAddr);  //企业地址
                    $("#company_cer_name").html(data.mainInfo.creName); //法人代表
                    $("#company_cer_mobile").html(data.baseInfo.officeTel);//联系电话
                    $("#company_frdb_creType").html(comm.langConfig.common.creTypes[data.mainInfo.creType])//法人代表证件类型
                    $("#company_frdb_creNo").html(data.mainInfo.creNo)//法人代表证件号码
                    $("#ent_type").html(data.baseInfo.nature);//企业类型
                    $("#company_license").html(data.mainInfo.busiLicenseNo);//营业执照号
                    $("#company_gov_no").html(data.mainInfo.orgCodeNo);//组织机构代码证号
                    $("#company_tax_no").html(data.mainInfo.taxNo);//纳税人识别号
                    $("#company_createDate").html(data.extendInfo.buildDate);//成立日期
                    $("#company_end_date").html(data.extendInfo.endDate);//营业期限
                    $("#company_bussiness_scope").html(data.extendInfo.businessScope);//经营范围
                   
                    //联系信息
                    $("#company_cont_name").html(data.mainInfo.contactPerson);     //联系人姓名
                    $("#company_cont_mobile").html(data.mainInfo.contactPhone); //联系人手机
                    $("#company_cont_address").html(data.extendInfo.contactAddr); //联系地址
                    $("#company_cont_post").html(data.baseInfo.postCode); //邮政编码
                    //$("#company_cont_webSite").html(data.baseInfo.webSite); //企业网址
                    $("#company_cont_email").html(data.baseInfo.contactEmail); //企业安全邮箱
                    //$("#company_off_tel").html(data.baseInfo.officeTel); //办公电话
                    //$("#company_off_fax").html(data.baseInfo.officeFax); //传真号码
                    //$("#company_cont_duty").html(data.baseInfo.contactDuty); //联系人职务
                    //$("#company_cont_qq").html(data.baseInfo.officeQq); //企业联系QQentRegAddr
                    
                    $("#company_helpAgreement_img").parent().parent().parent().parent().hide();
                    $("#company_busi_license_img").attr("src", "resources/img/noImg.gif");
                    $("#company_contactProxy_img").attr("src", "resources/img/noImg.gif");
                    $("#company_helpAgreement_img").attr("src", "resources/img/noImg.gif");
                    if(data && data.extendInfo){
                    	var contactProxy = data.extendInfo.contactProxy;
        				company_contactProxy = contactProxy;
        				if(null != contactProxy && '' != contactProxy){
        					$("#company_contactProxy_img").attr("src", comm.getFsServerUrl(contactProxy));//联系人授权委托书
        				}
        				var busiLicenseImg = data.extendInfo.busiLicenseImg;
        				company_busiLicenseImg = busiLicenseImg;
        				if(null != busiLicenseImg && '' != busiLicenseImg){
        					$("#company_busi_license_img").attr("src", comm.getFsServerUrl(busiLicenseImg));
        				}
        				var custType = data.extendInfo.custType;
        				var startResType = data.extendInfo.startResType;
        				if(3 == custType && 2 == startResType){
        					$("#company_helpAgreement_img").attr("src", "resources/img/noImg.gif");
        					var helpAgreement = data.extendInfo.helpAgreement;
        					company_helpAgreement = helpAgreement;
        					if(null != helpAgreement && '' != helpAgreement){
            					$("#company_helpAgreement_img").attr("src", comm.getFsServerUrl(helpAgreement));
            				}
        					$("#company_helpAgreement_img").parent().parent().parent().parent().show();
        					 
        				}
        			}
                    
                    
                 
                   
                    //属于创业资源的托管公司,需要显示"创业帮扶协议"
                    if("T"== comm.getResNoType(company_resNo) && 2==data.extendInfo.startResType){
                    	$("#tgqy_bfxy").removeClass("none");
                    	$("#company_helpAgreement").attr("src", comm.getFsServerUrl(data.extendInfo.helpAgreement));//创业帮扶协议
                        $("#company_help_bimg").click(function(){//图片预览
                            comm.initTmpPicPreview("#company_helpAgreement",comm.getFsServerUrl(data.extendInfo.helpAgreement),"创业帮扶协议预览");
                        })
                    }else{
                    	$("#tgqy_bfxy").addClass("none");
                    }

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
        } else {
            comm.alert("请输入正确的11位企业互生号");
        }
    });
    $("#company_busi_license_img").click(function(){//托管、成员企业营业执照扫描件图片预览
        comm.initTmpPicPreview("#company_busi_license_span",comm.getFsServerUrl(company_busiLicenseImg),"营业执照扫描件预览");
    });
    
    $("#company_contactProxy_img").click(function(){//托管、成员企业联系人授权委托书图片预览
        comm.initTmpPicPreview("#company_contactProxy_span",comm.getFsServerUrl(company_contactProxy),"联系人授权委托书预览");
    });
    
    $("#company_helpAgreement_img").click(function(){//托管企业（创业资源）创业帮扶协议图片预览
        comm.initTmpPicPreview("#company_helpAgreement_span",comm.getFsServerUrl(company_helpAgreement),"联系人授权委托书预览");
    });
    
    $("#scs_busi_license_img").click(function(){//服务公司营业执照扫描件图片预览
        comm.initTmpPicPreview("#scs_busi_license_span",comm.getFsServerUrl(scs_busiLicenseImg),"营业执照扫描件预览");
    });
    
    $("#scs_contactProxy_img").click(function(){//服务公司联系人授权委托书图片预览
        comm.initTmpPicPreview("#scs_contactProxy_span",comm.getFsServerUrl(scs_contactProxy),"联系人授权委托书预览");
    });


    //服务公司信息查询
    $("#search_scs").click(function () {
        var scs_resNo = $("#intpu_search_scs").val();
        if (comm.getResNoType(scs_resNo.toString()) == "S") {
            //查询企业联系信息
            var param = {resNo: scs_resNo};
            callCenter.searchEntAllInfo(param, function (request) {
                if (request.retCode == 22000) {
                    var data = request.data;
                    $("#scs_panel").removeClass("none");
                    $("#scs_mobile").html(data.extendInfo.contactPhone);//联系人手机号
                    var contactPerson = data.extendInfo.contactPerson;
                    contactPerson = contactPerson.length<4?contactPerson:contactPerson.substring(0,3)+"...";
                    $("#scs_contact").html(contactPerson);  //联系人姓名
                    $("#scs_contact").attr('title',data.extendInfo.contactPerson);  //联系人姓名
                    //$("#scs_contact").html(data.extendInfo.contactPerson);  //联系人姓名
                    // $("#scs_tel").html(data.extendInfo.officeTel);  //工作电话
                    // if (comm.isEmpty(data.extendInfo.officeTel)) {
                    //     $("#company_tel").prev().hide();
                    //     $("#company_tel").next().hide();
                    // }
                    $("#scs_resNo").html(data.extendInfo.entResNo.substring(0, 2) + "&nbsp;" + data.extendInfo.entResNo.substring(2, 5) + "&nbsp;" + data.extendInfo.entResNo.substring(5, 7) + "&nbsp;" + data.extendInfo.entResNo.substring(7, 11) + "&nbsp;");
                    var scs_baseStatisInfo = comm.langConfig.common.entStatus[data.statusInfo.baseStatus];
                    if(scs_baseStatisInfo && scs_baseStatisInfo.length > 3){
                    	scs_baseStatisInfo = scs_baseStatisInfo.substr(0,3) + "...";
                    }
                    var scsTypeInfo = "服务公司&nbsp;" + scs_baseStatisInfo;
                    $("#scs_type").html(scsTypeInfo);
                    $("#scs_type").attr('title',comm.langConfig.common.entStatus[data.statusInfo.baseStatus]); 
                    var ent_name=data.extendInfo.entCustName;
                    $("#scs_entCustName").html(ent_name.length<=12?ent_name:ent_name.substring(0,11)+"...");//企业名称
                    $("#scs_entCustName").attr('title',data.extendInfo.entCustName); 
                    //服务公司详情
                    $("#fwgs_ResNo").html(data.extendInfo.entResNo);
                    $("#fwgs_Name").html(data.extendInfo.entCustName);
                    //系统登记信息
                    $("#fwgs_status").html(comm.langConfig.common.entStatus[data.statusInfo.baseStatus]);//企业状态
                    
                    $("#fwgsResNo").html(data.extendInfo.entResNo);//企业资源号
                    $("#fwgsCreateDate").html(data.statusInfo.openDate);//企业开启日期
                    $("#fwgsCompanyNameCN").html(data.baseInfo.entName);//企业中文名称
                    $("#fwgsCompanyNameEN").html(data.baseInfo.entNameEn);//企业英文名称
                    //获取地区信息
                    cacheUtil.syncGetRegionByCode(data.baseInfo.countryCode, data.baseInfo.provinceCode, data.baseInfo.cityCode, "", function (resdata) {
                        $("#fwgsArea").html(resdata);
                    });
                    $("#fwgsCurrency").html(data.baseInfo.countryCode == "156" ? "人民币" : "未知");//币种
                    $("#businessType").html(comm.langConfig.common.businessType[data.extendInfo.businessType]);
                    //工商登记信息
                    $("#fwgs_firmName").html(data.baseInfo.entName); //企业名称
                    $("#fwgs_address").html(data.mainInfo.entRegAddr);  //企业地址
                    $("#fwgs_frdb_name").html(data.mainInfo.creName); //法人代表
                    $("#fwgs_frdb_mobile").html(data.baseInfo.officeTel);//联系电话
                    $("#fwgs_frdb_creType").html(comm.langConfig.common.creTypes[data.mainInfo.creType])//法人代表证件类型
                    $("#fwgs_frdb_creNo").html(data.mainInfo.creNo)//法人代表证件号码
                    //$("#fwgs_type").html(comm.langConfig.common.custType[data.mainInfo.entCustType]);//企业类型 data.baseInfo.nature
                    $("#fwgs_type").html(data.baseInfo.nature);
                    $("#fwgs_license").html(data.mainInfo.busiLicenseNo);//营业执照号
                    $("#fwgs_gov_no").html(data.mainInfo.orgCodeNo);//组织机构代码证号
                    $("#fwgs_tax_no").html(data.mainInfo.taxNo);//纳税人识别号
                    $("#fwgs_createDate").html(data.extendInfo.buildDate);//成立日期
                    $("#fwgs_operating_end").html(data.extendInfo.endDate);//营业期限
                    $("#fwgs_business_scope").html(data.extendInfo.businessScope);//经营范围
                   
                    //联系信息
                    $("#cont_name").html(data.mainInfo.contactPerson);     //联系人姓名
                    $("#cont_mobile").html(data.extendInfo.contactPhone); //联系人手机
                    $("#cont_address").html(data.extendInfo.contactAddr); //联系地址
                    $("#cont_post").html(data.baseInfo.postCode); //邮政编码
                    //$("#webSite").html(data.baseInfo.webSite); //企业网址
                    $("#scs_email").html(data.baseInfo.contactEmail); //企业安全邮箱
                    //$("#off_tel").html(data.baseInfo.officeTel); //办公电话
                    //$("#off_fax").html(data.baseInfo.officeFax); //传真号码
                    //$("#cont_tudy").html(data.baseInfo.contactDuty); //联系人职务
                    //$("#cont_qq").html(data.baseInfo.officeQq); //企业联系QQ
                    //$("#scs_contactProxy").html(data.extendInfo.contactProxy)//联系人授权委托书
                    //if(res.data.link && res.data.link.certificateFile){
                    //    comm.initPicPreview("#scs_contactProxy", res.data.link.certificateFile, "");
                       
                    //}
                    $("#scs_busi_license_img").attr("src", "resources/img/noImg.gif");
                    $("#scs_contactProxy_img").attr("src", "resources/img/noImg.gif");
                    
                    if(data){
                    	var busiLicenseImg = data.extendInfo.busiLicenseImg;
                    	scs_busiLicenseImg = busiLicenseImg;
        				if(null != busiLicenseImg && '' != busiLicenseImg){
        					$("#scs_busi_license_img").attr("src", comm.getFsServerUrl(busiLicenseImg));
        				}
        				var contactProxy = data.extendInfo.contactProxy;
        				scs_contactProxy = contactProxy;
        				if(null != contactProxy && '' != contactProxy){
        					$("#scs_contactProxy_img").attr("src", comm.getFsServerUrl(contactProxy));//联系人授权委托书
        				}
        			}
                    //银行账户信息
                    //银行账号列表查询


                    //账户信息
                    //报送服务公司信息

                    $("#apply_org_no").val(data.extendInfo.applyEntResNo); //报送单位资源号
                    $("#apply_ent_name").html(); //企业姓名
                    $("#apply_ent_cont_name").html(); //联系人姓名
                    $("#apply_ent_cont_mobile").html(); //联系人手机
                    $("#apply_ent_cont_address").html(); //联系地址
                }
            });
        } else {
            comm.alert("请输入正确的11位服务公司互生号");
        }
    });
    //end
    return tpl;
});