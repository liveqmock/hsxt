//语音留言
define(['text!callCenterTpl/ldgl/yyly.html'], function (yylyTpl) {
    return {
        showPage: function () {
            $('#busibox').html(_.template(yylyTpl));

            /*日期控件*/
            $("#timeRange_start").datepicker({
                dateFormat: 'yy-mm-dd',
                onSelect: function (dateTxt, inst) {
                    var d = dateTxt.replace('-', '/');
                    $("#timeRange_end").datepicker('option', 'minDate', new Date(d));
                }
            });
            $("#timeRange_end").datepicker({
                dateFormat: 'yy-mm-dd',
                onSelect: function (dateTxt, inst) {
                    var d = dateTxt.replace('-', '/');
                    $("#timeRange_start").datepicker('option', 'maxDate', new Date(d));
                }
            });
            /*end*/

            /*表格请求数据*/
            var json = [{
                "th_1": "0",
                "th_2": "2015-06-12 12:30:00",
                "th_3": "00:30:07"
            },
                {
                    "th_1": "0",
                    "th_2": "2015-08-12 09:25:00",
                    "th_3": "00:21:00"
                }];
            if (navigator.userAgent.indexOf("MSIE 8.0") > 0) {
                //语音留言
                Test.attachEvent("JS_Evt_GetCommentsList_Complete", GetCommentsList_Complete);
            }
            var gridObj;
            //正在通话
            function GetCommentsList_Complete(ret, id, json) {
                if (ret == 1) {
                    //console.info("语音留言"+json);
                    if($.parseJSON(json).resultCode==0){
                        $("#searchTable").find("tbody").children().remove();
                        $("#searchTable_pt_outTab").find('td').remove();
                        $("#searchTable_pt_outTab").attr('style','min-width: 666px; width: 1030px;');
                        $("#searchTable_pt_outTab").find('tr').append("<td style='text-align:center'>未找到符合条件的数据</td>");
                    }else{
                        gridObj = $.fn.bsgrid.init('searchTable', {
                            //url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
                            // autoLoad: false,
                            pageSizeSelect: true,
                            pageSize: 10,
                            stripeRows: true,  //行色彩分隔
                            displayBlankRows: false,   //显示空白行
                            localData: comm.isEmpty($.parseJSON(json).CommentsList)?null:$.parseJSON(json).CommentsList,
                            operate: {
                                //收听留言
                                add: function (record, rowIndex, colIndex, options) {
                                    var link1 = $('<a data-type="play">收听</a> ').click(function (e) {
                                        //收听录音
                                        var result=Test.JS_PlayVoc(Test.JS_GetUserNumByMac(Test.JS_GetMac()),record.File);
                                        if(result==1){
                                            comm.alert("来电留言收听"+record.File);
                                            $("#searchTable a[data-type=play]").addClass("none");
                                            $("#searchTable tr:nth-child("+(rowIndex+1)+")").find("a[data-type=stop]").removeClass("none");
                                            $("#searchTable a[data-type=download]").addClass("none");
                                        }else{
                                            comm.alert("收听失败"+record.File);
                                        }
                                    });
                                    return link1;
                                },
                                //停止收听
                                edit: function (record, rowIndex, colIndex, options) {
                                    var link1 = $('<a data-type="stop" class="none">停止</a>').click(function (e) {

                                        var result=Test.JS_StopPlay(Test.JS_GetUserNumByMac(Test.JS_GetMac()),record.File);
                                        if(result==1){
                                            comm.alert("留言收听结束"+record.File);
                                            $("#searchTable a[data-type=stop]").addClass("none");
                                            $("#searchTable a[data-type=play]").removeClass("none");
                                            $("#searchTable a[data-type=download]").removeClass("none");
                                        }else{
                                            comm.alert("停止收听失败"+record.File);
                                        }
                                    });
                                    return link1;
                                },
                                //下载留言
                                detail: function (record, rowIndex, colIndex, options) {
                                    var link1 = $('<a data-type="download">下载</a>').click(function (e) {
                                        var result=Test.JS_LoadFile(record.File);
                                        if(result>0){
                                            comm.alert("文件下载成功");
                                        }
                                    });
                                    return link1;
                                }
                            }
                        });
                        //移除排序字段栏出现的无效箭头
            			$("#yyly_startTime").find("a").remove();
                    }
                    //$('#' + gridObj.options.pagingOutTabId + ' tr:eq(0)').prepend(buttonHtml);
                }
            }
            $.fn.bsgrid.init('searchTable', {
                localData: null
            });
            //移除排序字段栏出现的无效箭头
			$("#yyly_startTime").find("a").remove();

            $("#yyly_search_btn").click(function(){
                var startTime=$("#timeRange_start").val();
                var entTime=$("#timeRange_end").val();
                var zx=$("#call_tel_input").val();
                Test.JS_GetCommentsList(startTime,entTime,zx);
            });

/*
            var buttonHtml = '<td style="text-align: left;width:100px;">';
            buttonHtml += '<table><tr>';
            buttonHtml += '<td><input type="button" value="下载录音" class="btn-search" id="xzly_btn" /></td>';
            buttonHtml += '</tr></table>';
            buttonHtml += '</td>';

            $('#xzly_btn').click(function () {
                alert("下载录音操作");
            });*/

            /*end*/
        }
    }
});