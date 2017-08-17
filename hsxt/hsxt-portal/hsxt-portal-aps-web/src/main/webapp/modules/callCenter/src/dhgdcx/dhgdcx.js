define(['text!callCenterTpl/dhgdcx/dhgdcx.html',
    'text!callCenterTpl/dhgdcx/dhgdcx_ck.html',
    'text!callCenterTpl/dhgdcx/dhgdcx_cl.html'
], function (dhgdcxTpl, dhgdcx_ckTpl, dhgdcx_clTpl) {
    return {

        showPage: function () {
            $('#busibox').html(_.template(dhgdcxTpl));
            var self = this;

            /*下拉列表*/
            $("#dhgdcx_type").selectList({
                options: [
                    {name: '运维类', value: 1},
                    {name: '商务类', value: 2},
                    {name: '诉建', value: 3}
                ]
            })
            //    .change(function (e) {
            //    //console.info($(this).val());
            //});

            $("#dhgdcx_status").selectList({
                options: [
                    {name: '已完成', value: 2},
                    {name: '处理中', value: 1},
                    {name: '无效单', value: 3}
                ]
            })
            //    .change(function (e) {
            //    //console.info($(this).val());
            //});


            /*end*/
            if (navigator.userAgent.indexOf("MSIE 8.0") > 0) {
                Test.attachEvent("JS_Evt_PhoneWorkOrder", PhoneWorkOrder);//电话工单查询
            }

            $.fn.bsgrid.init('searchTable', {
                localData: null
            });
            function PhoneWorkOrder(ret, id, json) {
                //console.info("查询工单结果" + ret + "<br>id" + id + "<br>json" + json);
                if (ret == 1) {
                    $("#searchTable").find("tbody").children().remove();
                    //$("#searchTable_pt").attr("align","center").html("未查到符合条件的数据");
                   var gridObj= $.fn.bsgrid.init('searchTable', {
                        //url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
                        autoLoad: true,
                        pageSizeSelect: true,
                        pageSize: 10,
                        stripeRows: true,  //行色彩分隔
                        displayBlankRows: false,   //显示空白行
                        localData: $.parseJSON(json).resultCode == 1 ? $.parseJSON(json).PhoneWorkOrder : null,
                        operate: {
                            detail: function (record, rowIndex, colIndex, options) {
                                if (colIndex == 5) {
                                    return comm.langConfig.common.CallerType[record.CallerType];
                                }
                                if (colIndex == 10) {
                                    return comm.langConfig.common.DealStatus[record.DealStatus];
                                }
                                if (colIndex == 11) {
                                    var link1 = $('<a>查看</a>').click(function (e) {
                                        self.chaKan(record);
                                    });
                                    return link1;
                                }
                            },
                            edit: function (record, rowIndex, colIndex, options) {
                                if (record.DealStatus == 1 && colIndex == 11) {
                                    var link1 = $('<a>处理</a>').click(function (e) {
                                        self.chuLi(record);
                                    });
                                    return link1;
                                }
                            }
                        }
                    });
                }

            }

            $("#dhgdcx_btn").click(function () {//查询事件
                var dhgdcx_resNo = $.trim($("#dhgdcx_resNo").val());
                var dhgdcx_tel = $.trim($("#dhgdcx_tel").val());
                var dhgdcx_type = parseInt($.trim($("#dhgdcx_type").attr("optionvalue")));
                var dhgdcx_status = parseInt($.trim($("#dhgdcx_status").attr("optionvalue")));

                if($.trim($("#dhgdcx_type").attr("optionvalue")).length>0 && $.trim($("#dhgdcx_status").attr("optionvalue")).length>0){
                    Test.JS_GetPhoneWorkOrder(dhgdcx_resNo, dhgdcx_tel, dhgdcx_type, dhgdcx_status);
                }else{
                    if(!$.trim($("#dhgdcx_type").attr("optionvalue")).length>0){comm.alert("请选择来电类型");return;}
                    if(!$.trim($("#dhgdcx_status").attr("optionvalue")).length>0){comm.alert("请选择处理状态");return;}
                }


            });


            /*end*/
        },

        chaKan: function (obj) {
            var self=this;
            $('#dhgdcx_ck_dlg').html(dhgdcx_ckTpl).dialog({
                title: '电话工单详情',
                width: '800',
                modal: true,
                closeIcon: true,
                buttons: {
                    '关闭': function () {
                        $(this).dialog('destroy');
                    }
                }
            });

            /*详细内容显示*/

            $('#sj_text').text(obj.StartTime);		//时间
            $('#ldhm_text').text(obj.CallingNum);	//来电号码
            $('#hsh_text').text(obj.SRN);			//互生号
            $('#mc_text').text(obj.UserName);				//名称
            $('#lxdh_text').text(obj.Contact);			//联系电话
            $('#ldlx_text').text(comm.langConfig.common.CallerType[obj.CallerType]);			//来电类型
            $('#wtms_text').text(obj.Describe);			//问题描述
            $('#thsc_text').text(obj.TalkTime);			//通话时长
            $('#jrgh_text').text(obj.JoinAgent);			//接入工号
            $('#clgh_text').text(obj.DealAgent);			//处理工号
            $('#clzt_text').text(comm.langConfig.common.CallerType[obj.DealStatus]);			//处理状态
            $("#voice_text").val(obj.Voice);
            /*end*/
            $("#play_start").click(function(){self.playVoc(obj.Voice);});//来电播放
            $("#play_stop").click(function(){self.stopVoc(obj.Voice);});//停止播放
            $(".play_stop").removeClass("play_icon");
        },
        chuLi: function (obj) {
            var self=this;
            $('#dhgdcx_cl_dlg').html(dhgdcx_clTpl).dialog({
                title: '电话工单处理',
                width: '800',
                modal: true,
                closeIcon: true,
                buttons: {
                    '处理完成': function () {
                        var desc=$("#cljg_text_cl").text()//处理结果
                        self.handle(obj.Guid,obj.Describe+"".concat(desc));
                        $(this).dialog('destroy');
                    },
                    '保存': function () {
                        var desc=$("#cljg_text_cl").text(desc)//处理结果
                        self.save(obj.Guid,obj.Describe+"".concat($("#cljg_text_cl").text()));
                        $(this).dialog('destroy');
                    },
                    '取消': function () {
                        $(this).dialog('destroy');
                    }
                }
            });

            /*详细内容显示*/
            $('#sj_text_cl').text(obj.StartTime);
            $('#ldhm_text_cl').text(obj.CallingNum);
            $('#hsh_text_cl').text(obj.SRN);
            $('#mc_text_cl').text(obj.UserName);
            $('#lxdh_text_cl').text(obj.Contact);
            $('#ldlx_text_cl').text(comm.langConfig.common.CallerType[obj.CallerType]);
            $('#wtms_text_cl').text(obj.Describe);
            $('#thsc_text_cl').text(obj.TalkTime);
            $('#jrgh_text_cl').text(obj.JoinAgent);
            $('#clgh_text_cl').text(obj.DealAgent);
            $('#clzt_text_cl').text(comm.langConfig.common.CallerType[obj.DealStatus]);
            $("#voice_text_cl").val(obj.Voice);
            $("#guid_text_cl").val(obj.Guid);
            /*end*/
            $("#play_start_cl").click(function(){self.playVoc(obj.Voice);});//来电播放
            $("#play_stop_cl").click(function(){self.stopVoc(obj.Voice);});//停止播放
            $(".play_stop").removeClass("play_icon");
        },
        //收听录音
        playVoc: function (voice) {
            var result = Test.JS_PlayVoc(Test.JS_GetUserNumByMac(Test.JS_GetMac()), voice);
            if (result == 1) {
                comm.alert("来电录音收听" + voice);
                //$("#searchTable a[data-type=play]").addClass("none");
                //$("#searchTable a[data-type=stop]").removeClass("none");
            } else {
                comm.alert("收听失败" + voice);
            }
            $(".play_stop").addClass("play_icon");
            $(".play_start").removeClass("play_icon");

        },
        //停止收听
        stopVoc: function (voice) {
            var result = Test.JS_StopPlay(Test.JS_GetUserNumByMac(Test.JS_GetMac()), voice);
            if (result == 1) {
                comm.alert("录音收听结束" + voice);
                //$("#searchTable a[data-type=stop]").addClass("none");
                //$("#searchTable a[data-type=play]").removeClass("none");
            } else {
                comm.alert("录音收听失败" + voice);
            }
            $(".play_start").addClass("play_icon");
            $(".play_stop").removeClass("play_icon");
        },
        //保存
        save:function(id,desc){
            var zx=Test.JS_GetUserNumByMac(Test.JS_GetMac());
            var ret= Test.JS_DealPhoneWorkOrder(id,zx, 2,  desc );
            if(ret==1){
                comm.alert("工单保存成功");
                Test.JS_GetPhoneWorkOrder("", "", 1, 2);
            }else{
                comm.alert("工单保存失败");
            }
        },
        //处理完成
        handle:function(id,desc){
            var zx=Test.JS_GetUserNumByMac(Test.JS_GetMac());
            var ret= Test.JS_DealPhoneWorkOrder(id,zx, 1,  desc );
            if(ret==1){
                comm.alert("工单处理成功");
                Test.JS_GetPhoneWorkOrder("", "", 1, 2);
            }else{
                comm.alert("工单处理失败");
            }
        }
    }
});