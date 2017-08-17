define(['text!callCenterTpl/hjzxbb/hjzxbb.html',
    'text!callCenterTpl/hjzxbb/hjzxbb_ck1.html',
    'text!callCenterTpl/hjzxbb/hjzxbb_ck2.html',
    'text!callCenterTpl/hjzxbb/hjzxbb_ck3.html',
    'callCenterDat/callCenter'
], function (hjzxbbTpl, hjzxbb_ck1Tpl, hjzxbb_ck2Tpl, hjzxbb_ck3Tpl, callCenter) {
    return {
        showPage: function () {
            $('#busibox').html(_.template(hjzxbbTpl));
            var self = this;
            /*下拉列表*/
            $("#sjzq").selectList({
                options: [
                    {name: '当日', value: '1'},
                    {name: '最近一周', value: '2'},
                    {name: '最近一月', value: '3'}
                ]
            });

            //报表请求事件绑定
            if (navigator.userAgent.indexOf("MSIE 8.0") > 0) {
                Test.attachEvent("JS_Evt_Report", Report);
                Test.attachEvent("JS_Evt_OnLineTimeReport", OnLineTimeReport);//签入签出事件
                Test.attachEvent("JS_Evt_TalkTimeReport", TalkTimeReport);// 通话时长事件
                Test.attachEvent("JS_Evt_BusyTimeReport", BusyTimeReport);//置忙时长统计
            }

            function OnLineTimeReport(ret, id, json) {
                //console.info("签入签出统计" + json);
                var data = $.parseJSON(json);
                var gridObj = $.fn.bsgrid.init('ck1Table', {
                    //url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
                    // autoLoad: false,
                    pageSizeSelect: true,
                    pageSize: 10,
                    stripeRows: true,  //行色彩分隔
                    displayBlankRows: false,   //显示空白行
                    localData: data.resultCode == 1 ? data.OnLineTimeReport : null
                });
                var title = "日期,签入时间,签出时间,在线时长";
                var param = {
                    type:1,
                    fileName: "签入签出时间统计",
                    sheetName: "签入签出时间统计",
                    title: title,
                    jsonStr: JSON.stringify(data.OnLineTimeReport)
                };
                $("#hjzxbb_ck1_export").click(function () {
                    self.excelExport(param);
                });
            }

            function TalkTimeReport(ret, id, json) {
                //console.info("通话时长统计" + json);
                var data = $.parseJSON(json);
                var gridObj = $.fn.bsgrid.init('ck2Table', {
                    //url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
                    // autoLoad: false,
                    pageSizeSelect: true,
                    pageSize: 10,
                    stripeRows: true,  //行色彩分隔
                    displayBlankRows: false,   //显示空白行
                    localData: data.resultCode == 1 ? data.TalkTimeReport : null,
                    operate: {
                        add: function (record, rowIndex, colIndex, options) {
                            if (colIndex == 2) {
                                return record.CallType == "I" ? "呼入" : "呼出";
                            }
                        }
                    }
                });


                var title = "日期,电话号码,呼入/呼出,开始时间,结束时间,通话时长";
                var param = {
                    type:2,
                    fileName: "通话时间统计",
                    sheetName: "通话时间统计",
                    title: title,
                    jsonStr: JSON.stringify(data.TalkTimeReport)
                };
                $("#hjzxbb_ck2_export").click(function () {
                    //alert("导出");
                    self.excelExport(param);
                });
            }

            function BusyTimeReport(ret, id, json) {
                //console.info("置忙时长统计" + json);
                var data = $.parseJSON(json);
                var gridObj = $.fn.bsgrid.init('ck3Table', {
                    //url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
                    // autoLoad: false,
                    pageSizeSelect: true,
                    pageSize: 10,
                    stripeRows: true,  //行色彩分隔
                    displayBlankRows: false,   //显示空白行
                    localData: data.resultCode == 1 ? data.BusyTimeReport : null
                });
                var title = "日期,置忙开始时间,置忙结束时间,置忙时长";
                var param = {
                    type:3,
                    fileName: "置忙时间统计",
                    sheetName: "置忙时间统计",
                    title: title,
                    jsonStr: JSON.stringify(data.BusyTimeReport)
                };
                $("#hjzxbb_ck3_export").click(function () {
                    self.excelExport(param);
                });
            }

            /**
             *  呼叫中心报表
             *  timeType：(1:日,2:周,3:月)
             *  Date：具体时间
             *  AgentNum：坐席号，
             *  UserName：姓名
             *  LONG JS_GetReport (LONG timeType, LPCTSTR Date, LPCTSTR AgentNum, LPCTSTR UserName);
             */
            $.fn.bsgrid.init('searchTable', {
                localData: null
            });
            //呼叫中心报表
            function Report(ret, id, json) {
                //console.info("报表数据：" + json);
                if (ret == 1) {
                    var list = comm.isEmpty($.parseJSON(json).ReportList) ? null : $.parseJSON(json).ReportList;
                    
                  if(null!=list && 0<list.length){
                    var gridObj = $.fn.bsgrid.init('searchTable', {
                        //url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
                        // autoLoad: false,
                        pageSizeSelect: true,
                        pageSize: 10,
                        stripeRows: true,  //行色彩分隔
                        displayBlankRows: false,   //显示空白行
                        localData: comm.isEmpty($.parseJSON(json).ReportList) ? null : $.parseJSON(json).ReportList,
                        operate: {
                            add: function (record, rowIndex, colIndex, options) {
                                var link1 = null;
                                if (colIndex == '2') {
                                    if (record.LogInTime != 0) {
                                        link1 = $('<a>' + record.LogInTime + '</a>').click(function (e) {
                                            self.chaKan_1(record);
                                        });
                                    } else {
                                        link1 = 0;
                                    }
                                }
                                else if (colIndex == '4') {
                                    if (record.TalkTime != 0) {
                                        link1 = $('<a>' + record.TalkTime + '</a>').click(function (e) {
                                            self.chaKan_2(record);
                                        });
                                    } else {
                                        link1 = 0;
                                    }
                                }
                                else if (colIndex == '5') {
                                    if (record.BusyTime != 0) {
                                        link1 = $('<a>' + record.BusyTime + '</a>').click(function (e) {
                                            self.chaKan_3(record);
                                        });
                                    } else {
                                        link1 = 0;
                                    }
                                }
                                else if (colIndex == '8') {
                                    link1 = comm.percentage( record.WorkTimeUsage);
                                }
                                return link1;
                            }
                        }
                    });
                   
                  }else{
                	  $("#searchTable").find("tbody").children().remove();
					  $("#searchTable_pt_outTab").find('td').remove();
					  $("#searchTable_pt_outTab").attr('style','min-width: 666px; width: 1030px;');
					  $("#searchTable_pt_outTab").find('tr').append("<td style='text-align:center'>未找到符合条件的数据</td>");
                  } 
                }
            }

            //报表查询事件
            $("#hjzxbb_search_btn").click(function () {
                var sjzq = parseInt($.trim($("#sjzq").attr("optionvalue")));//时间周期
                var num = $("#hjzxbb_search_zxnum").val();
                var name = $("#hjzxbb_search_name").val();

                if ($.trim($("#sjzq").attr("optionvalue")).length > 0) {
                    var ret = Test.JS_GetReport(sjzq, "", num, name);
                } else {
                    comm.alert("请选择时间周期");
                }

            });
            /*end*/
        },
        /*下拉列表事件*/
        chaKan_1: function (obj) {
            var sjzq = parseInt($.trim($("#sjzq").attr("optionvalue")));//时间周期
            Test.JS_GetOnLineTimeReport(obj.AgentNum, obj.UserName, sjzq);
            $('#hjzxbb_ck1_dlg').html(hjzxbb_ck1Tpl).dialog({
                title: '签入签出时间统计',
                width: '820',
                modal: true,
                closeIcon: true,
                buttons: {
                    '关闭': function () {
                        $(this).dialog('destroy');
                    }
                }
            });
            $('#gh_text_ck1').text(obj.AgentNum);//工号
            $('#xm_text_ck1').text(obj.UserName); //姓名
            var sjzq = parseInt($.trim($("#sjzq").attr("optionvalue")));//时间周期
            $('#sjzq_text_ck1').text(comm.langConfig.common.callCenterReport[sjzq]); //时间类型 1当日 2最近一周 3最近一月
            $('#zjzxsc_text_ck1').text(obj.LogInTime);   //总计在线时长
        },
        chaKan_2: function (obj) {
            var sjzq = parseInt($.trim($("#sjzq").attr("optionvalue")));//时间周期
            Test.JS_GetTalkTimeReport(obj.AgentNum, obj.UserName, sjzq);
            $('#hjzxbb_ck2_dlg').html(hjzxbb_ck2Tpl).dialog({
                title: '通话时间统计',
                width: '820',
                modal: true,
                closeIcon: true,
                buttons: {
                    '关闭': function () {
                        $(this).dialog('destroy');
                    }
                }
            });

            $('#gh_text_ck2').text(obj.AgentNum);
            $('#xm_text_ck2').text(obj.UserName);
            var sjzq = parseInt($.trim($("#sjzq").attr("optionvalue")));//时间周期
            $('#sjzq_text_ck2').text(comm.langConfig.common.callCenterReport[sjzq]); //时间类型 1当日 2最近一周 3最近一月
            $('#zjthsc_text_ck2').text(obj.TalkTime);
            $('#hrcs_text_ck2').text(obj.CallInTimes);
            $('#hccs_text_ck2').text(obj.CallOutTimes);
        },

        chaKan_3: function (obj) {
            var sjzq = parseInt($.trim($("#sjzq").attr("optionvalue")));//时间周期
            Test.JS_GetBusyTimeReport(obj.AgentNum, obj.UserName, sjzq);
            $('#hjzxbb_ck3_dlg').html(hjzxbb_ck3Tpl).dialog({
                title: '置忙时间统计',
                width: '820',
                modal: true,
                closeIcon: true,
                buttons: {
                    '关闭': function () {
                        $(this).dialog('destroy');
                    }
                }
            });

            $('#gh_text_ck3').text(obj.AgentNum);
            $('#xm_text_ck3').text(obj.UserName);
            var sjzq = parseInt($.trim($("#sjzq").attr("optionvalue")));//时间周期
            $('#sjzq_text_ck3').text(comm.langConfig.common.callCenterReport[sjzq]); //时间类型 1当日 2最近一周 3最近一月
            $('#zjzmsc_text_ck3').text(obj.BusyTime);
        },
        //文件导出
        excelExport: function (param) {
            window.open(callCenter.excelExport1(param),'_blank')
            /*callCenter.excelExport(param, function (request) {
                comm.alert(request);
                if (request.retCode == 22000) {
                    if (request.data) {
                        comm.alert("导出成功<a href='" + param.filePath + "/" + param.fileName + "'>" + param.filePath + "/" + param.fileName + "</a>")
                    } else {
                        comm.alert("导出失败")
                    }
                } else {
                    comm.alert("导出失败");
                }
            });*/
        }
    }
});