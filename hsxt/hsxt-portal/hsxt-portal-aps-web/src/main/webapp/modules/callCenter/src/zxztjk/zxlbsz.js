define(['text!callCenterTpl/zxztjk/zxlbsz.html',
    'text!callCenterTpl/zxztjk/zxlbsz_xz.html',
    'text!callCenterTpl/zxztjk/zxlbsz_xg.html'
], function (zxlbszTpl, zxlbsz_xzTpl, zxlbsz_xgTpl) {
    return {
        showPage: function () {
            $('#busibox').html(zxlbszTpl);
            var self = this;
            //坐席列表查询事件绑定
            if (navigator.userAgent.indexOf("MSIE 8.0") > 0) {
                Test.attachEvent("JS_Evt_GetAgentMacList", GetAgentMacList);
            }
            //坐席列表查询
            function GetAgentMacList(ret, id, json) {

                //console.info("坐席列表查询"+json);
                if($.parseJSON(json).resultCode==1){
                    $("#searchTable").find("tbody").children().remove();
                    var gridObj = $.fn.bsgrid.init('searchTable', {
                        //url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
                        // autoLoad: false,
                        pageSizeSelect: true,
                        pageSize: 10,
                        stripeRows: true,  //行色彩分隔
                        displayBlankRows: false,   //显示空白行
                        localData: comm.isEmpty($.parseJSON(json).AgentMacList) ? null : $.parseJSON(json).AgentMacList,
                        operate: {
                            detail: function (record, rowIndex, colIndex, options) {
                                var link1 = $('<a>修改</a>').click(function (e) {
                                    self.xiuGai(record);
                                })
                                return link1;
                            }
                        }
                    });
                }else{
                    $("#searchTable").find("tbody").children().remove();
                    $("#searchTable_pt_outTab").attr('style','min-width: 666px; width: 1030px;');
                    $("#searchTable_pt_outTab").find('td').remove();
                    $("#searchTable_pt_outTab").find('tr').append("<td style='text-align:center'>未找到符合条件的数据</td>");
                }
            }
            // Test.JS_GetAgentMacList("");//坐席列表查询
            $.fn.bsgrid.init('searchTable', {
                localData: null
            });
            /*end*/
            //坐席列表查询
            $("#zxlbsz_search_btn").click(function(){
	           	 setTimeout(function(){
	           		 Test.JS_GetAgentMacList($("#zx_input").val());
	           	 },300);
           });

            $('#zxlbsz_xz_btn').click(function () {
                $('#zxlbsz_xz_dlg').html(zxlbsz_xzTpl).dialog({
                    title: '新增坐席配置',
                    width: '380',
                    closeIcon: true,
                    modal: true,
                    buttons: {
                        '确定': function () {
                            $(this).dialog('destroy');
                        },
                        '取消': function () {
                            $(this).dialog('destroy');
                        }
                    }
                });
                $("#zxlbsz_xz_dlg").next().find("button").first().click(function () {
                    var mac = $.trim($("#sz_mac").val());
                    var txtnum = $.trim($("#sz_txtnum").val());
                    var remack = $.trim($("#sz_remark").val());
                    var result = -1;
                     if(self.validate(mac,txtnum,remack)){
                    //坐席列表设置提交
                    result = Test.JS_SetUserNumMac(mac, txtnum, remack);
                    if (result > 0) {
                        comm.alert("坐席绑定成功");
                        $('#zxlbsz').click();
                    } else {
                        comm.error_alert("坐席绑定失败", 300);
                    }
                   }
                });
            });
        },
        xiuGai: function (obj) {
            $('#zxlbsz_xg_dlg').html(zxlbsz_xgTpl).dialog({
                title: '修改坐席配置',
                width: '380',
                closeIcon: true,
                modal: true,
                buttons: {
                    '确定': function () {
                        $(this).dialog('destroy');
                    },
                    '取消': function () {
                        $(this).dialog('destroy');
                    }
                }
            });
            $("#xg_mac").val(obj.Mac);
            $("#xg_txtnum").val(obj.Agent_Num);
            $("#xg_remark").val(obj.Remark);
            $("#zxlbsz_xg_dlg").next().find("button").first().click(function () {
                var mac= $("#xg_mac").val();
                var txtnum=$("#xg_txtnum").val();
                var remack=$("#xg_remark").val();
                result=Test.JS_SetUserNumMac(mac,txtnum,remack);
                if(result>0){
                    comm.alert("坐席修改成功");
                    $('#zxlbsz').click();
                }else{
                    comm.error_alert("坐席修改失败",300);
                }
            });
        },
        validate: function (a, b, c) {
            var reg_mac = new RegExp("^([0-9a-fA-F]{2})(([/\s:-][0-9a-fA-F]{2}){5})$");
            var reg_txtnum = new RegExp("^[0-9]{3}$");
            if (!reg_mac.test(a)) {
                comm.alert("请输入正确的MAC地址");
                return false;
            }
            if (!reg_txtnum.test(b)) {
                comm.alert("请输入正确的坐席号");
                return false;
            }
//            if (c.length <= 0) {
//                comm.alert("请输入备注信息");
//                return false;
//            }
            return true;
        }
    }
});