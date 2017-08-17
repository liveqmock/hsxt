define(['text!callCenterTpl/zxztjk/zxztjk.html', 'commSrc/frame/rightBar'], function (zxztjkTpl, rightBar) {
    return {
        showPage: function () {
            $('#busibox').html(_.template(zxztjkTpl));
            $('#seat_list').bind('contextmenu', function () {
                return false;
            });

            $(document).bind('click', function () {
                $('#seat_list').children('li').find('span').addClass('none');
                if(!-[1,]){   //坐席状态实时监控，只在IE下生效
                    heartbeat();
                }
            });
            function heartbeat() {
                    Test.JS_GetAgentStatusList();
                    setTimeout("heartbeat()", 1000);
            }
            $('#seat_list').children('li').each(function () {
                if ($(this).children('div').hasClass('color_qy') || $(this).children('div').hasClass('color_zx')) {

                    $(this).css('z-index', '0');

                    $(this).addClass('hover')
                        .append('<span class="seat_box_menu none"><a>置忙</a></span>')
                        .mousedown(function (e) {

                            if (e.which == '3') {

                                $(this).css('z-index', '1')
                                    .siblings('li.hover').css('z-index', '0');

                                $('#seat_list').children('li').find('span').addClass('none');
                                $(this).find('span').removeClass('none')
                                    .unbind('click').bind('click', function () {
                                        comm.alert({
                                            imgFlag: true,
                                            imgClass: 'tips_yes',
                                            content: '置忙'
                                        });
                                    });

                            }


                        });
                }
                ;
            });

            var jsons = {
                //"resultCode": "1",
                //"rowCount": "5",
                //"AgentStatusList": [
                //    {
                //        "Agent_Num": "801",
                //        "User_Name": "雷亚涛",
                //        "Work_Status": "10"
                //    }
                //]
            };

            var waitNumber=Test.JS_GetWaitingCallsNum();
            if(waitNumber>=0){
                $("#waitNumber").html(waitNumber);
            }else{
                comm.error_alert("获取等待接入");
            }

            //坐席状态监控事件绑定
            if (navigator.userAgent.indexOf("MSIE 8.0") > 0) {
                Test.attachEvent("JS_Evt_GetAgentStatusList", GetAgentStatusList);
            }
            var zx_qy = "<li title='签入'><div class='kftx_box color_qy'><i class='kftx'></i></div>	<div class='tc f14 mt5'></div></li>";
            var zx_zx = "<li title='示闲'><div class='kftx_box color_zx'><i class='kftx'></i></div>	<div class='tc f14 mt5'></div></li>";
            var zx_zm = "<li title='置忙'><div class='kftx_box color_zm'><i class='kftx'></i></div>	<div class='tc f14 mt5'></div></li>";
            var zx_jy = "<li title='静音'><div class='kftx_box color_jy'><i class='kftx'></i></div>	<div class='tc f14 mt5'></div></li>";
            var zx_qc = "<li title='签出'><div class='kftx_box color_qc'><i class='kftx'></i></div>	<div class='tc f14 mt5'></div></li>";
            //坐席状态 10, '签出' 20, '签入' 21, '空闲' 22, '置忙' 23, '静音' 30,通话中 31,振铃中 35,自我摘机-通话中
            function GetAgentStatusList(ret,id,jsons){
                if(ret==1){
                  /* comm.alert(jsons);*/
                    var zx_list=$.parseJSON(jsons);
                    $("#seat_list").children().remove();
                    for (var i = 0; i < parseInt(zx_list.rowCount); i++) {
                        //坐席状态 10, '签出' 20, '签入' 21, '空闲' 22, '置忙' 23, '静音' 30,通话中 31,振铃中 35,自我摘机-通话中
                        switch (parseInt(zx_list.AgentStatusList[i].Work_Status)) {
                            case 10:
                                $("#seat_list").append(zx_qc);
                                break;
                            case 20:
                                $("#seat_list").append(zx_qy);
                                break;
                            case 21:
                                $("#seat_list").append(zx_zx);
                                break;
                            case 22:
                                $("#seat_list").append(zx_zm);
                                break;
                            case 23:
                                $("#seat_list").append(zx_jy);
                                break;
                            default :
                        }
                        $(".tc.f14.mt5").last().html(zx_list.AgentStatusList[i].Agent_Num+" "+zx_list.AgentStatusList[i].User_Name);
                    }
                }
            }
            Test.JS_GetAgentStatusList();
        }
    }
});