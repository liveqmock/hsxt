define(['text!callCenterTpl/zxztjk/zxztjk.html', 'commSrc/frame/rightBar'], function (zxztjkTpl, rightBar) {
    return {
        showPage: function () {
        	
            $('#busibox').html(_.template(zxztjkTpl));
            $('#seat_list').bind('contextmenu', function () {
                return false;
            });


            //原来的写法并没有执行(原名)heartbeat,修改后可以执行但界面会直接卡死(修改成全tab渲染后再调用,间隔1秒)
            function contRefresh() {
            		setTimeout(function(){
            			Test.JS_GetAgentStatusList()}, 0);
            };
            
            var waitNumber=Test.JS_GetWaitingCallsNum();
            if(waitNumber>=0){
                $("#waitNumber").html(waitNumber);
            }else{
                comm.error_alert("获取等待接入");
            }

            //坐席状态监控事件绑定
            if (navigator.userAgent.indexOf("MSIE 8.0") > 0) {
            	if(comm.getCallCache('zxztjk_bd')!='yes'){
            		Test.attachEvent("JS_Evt_GetAgentStatusList", GetAgentStatusList);
            		comm.setCallCache('zxztjk_bd','yes')
            	}
            }
            
            var zx_qy = "<li title='签入'><div class='kftx_box color_qy'><i class='kftx'></i></div>	<div class='tc f14 mt5'></div></li>";
            var zx_zx = "<li title='示闲'><div class='kftx_box color_zx'><i class='kftx'></i></div>	<div class='tc f14 mt5'></div></li>";
            var zx_zm = "<li title='置忙'><div class='kftx_box color_zm'><i class='kftx'></i></div>	<div class='tc f14 mt5'></div></li>";
            var zx_jy = "<li title='静音'><div class='kftx_box color_jy'><i class='kftx'></i></div>	<div class='tc f14 mt5'></div></li>";
            var zx_qc = "<li title='签出'><div class='kftx_box color_qc'><i class='kftx'></i></div>	<div class='tc f14 mt5'></div></li>";
            var zx_th = "<li title='通话中'><div class='kftx_box color_th'><i class='kftx'></i></div>	<div class='tc f14 mt5'></div></li>";
            
            //坐席状态 10'签出', 20'签入',  21'空闲',  22'置忙', 23'静音', 30通话中 ,31振铃中 ,35自我摘机-通话中
            function GetAgentStatusList(ret,id,jsons){
                if(ret==1){
                	
                    var zx_list=$.parseJSON(jsons);
                    $("#seat_list").children().remove();
                    for (var i = 0; i < parseInt(zx_list.rowCount); i++) {
                        //所有筛选 -- 坐席状态 10'签出', 20'签入',  21'空闲',  22'置忙', 23'静音', 30通话中 ,31振铃中 ,35自我摘机-通话中
                    	if('zxzt_sy'==cur){
	                        switch (parseInt(zx_list.AgentStatusList[i].Work_Status)) {
	                            case 10: $("#seat_list").append(zx_qc); break;
	                            case 20: $("#seat_list").append(zx_qy); break;
	                            case 21: $("#seat_list").append(zx_zx); break;
	                            case 22: $("#seat_list").append(zx_zm); break;
	                            case 23: $("#seat_list").append(zx_jy); break;
	                            case 30: $("#seat_list").append(zx_th); break;
	                        }
	                        $(".tc.f14.mt5").last().html(zx_list.AgentStatusList[i].Agent_Num+" "+zx_list.AgentStatusList[i].User_Name);
                    	};
                    	//签入筛选
                    	if('zxzt_qr'==cur){
                    		switch (parseInt(zx_list.AgentStatusList[i].Work_Status)) {
                    		   case 20: $("#seat_list").append(zx_qy); break;
                            }
                            $(".tc.f14.mt5").last().html(zx_list.AgentStatusList[i].Agent_Num+" "+zx_list.AgentStatusList[i].User_Name);
                    	};
                    	//示闲筛选
                    	if('zxzt_sx'==cur){
                    		switch (parseInt(zx_list.AgentStatusList[i].Work_Status)) {
                    		   case 21: $("#seat_list").append(zx_zx); break;
                            }
                            $(".tc.f14.mt5").last().html(zx_list.AgentStatusList[i].Agent_Num+" "+zx_list.AgentStatusList[i].User_Name);
                    	};
                    	//置忙筛选
                    	if('zxzt_zm'==cur){
                    		switch (parseInt(zx_list.AgentStatusList[i].Work_Status)) {
                    		   case 22: $("#seat_list").append(zx_zm); break;
                            }
                            $(".tc.f14.mt5").last().html(zx_list.AgentStatusList[i].Agent_Num+" "+zx_list.AgentStatusList[i].User_Name);
                    	};
                    	//静音筛选
                    	if('zxzt_jy'==cur){
                    		switch (parseInt(zx_list.AgentStatusList[i].Work_Status)) {
                    		   case 23: $("#seat_list").append(zx_jy); break;
                            }
                            $(".tc.f14.mt5").last().html(zx_list.AgentStatusList[i].Agent_Num+" "+zx_list.AgentStatusList[i].User_Name);
                    	};
                    	//签出筛选
                    	if('zxzt_qc'==cur){
                    		switch (parseInt(zx_list.AgentStatusList[i].Work_Status)) {
                    		   case 10: $("#seat_list").append(zx_qc); break;
                            }
                            $(".tc.f14.mt5").last().html(zx_list.AgentStatusList[i].Agent_Num+" "+zx_list.AgentStatusList[i].User_Name);
                    	};
                    	//通话中筛选
                    	if('zxzt_th'==cur){
                    		switch (parseInt(zx_list.AgentStatusList[i].Work_Status)) {
                    		   case 30: $("#seat_list").append(zx_th); break;
                            }
                            $(".tc.f14.mt5").last().html(zx_list.AgentStatusList[i].Agent_Num+" "+zx_list.AgentStatusList[i].User_Name);
                    	};
                    }
                }
            };
            
            //Tab初始化加载数据
            Test.JS_GetAgentStatusList();
            $('#zxzt_sy').addClass('zxzt_cl');
           
            var cur = 'zxzt_sy';
            //座席状态监控分状态
            $("#callCen_zxzt").children().children().children().bind('click',function(e){
            	var id = $(e.currentTarget).attr("id");
            	if('zxzt_sy'==id || 'zxzt_qr'==id || 'zxzt_sx'==id || 'zxzt_zm'==id || 'zxzt_jy'==id || 'zxzt_qc'==id || 'zxzt_th'==id){
            		if(cur!=id){
            			cur = id;
            		}
            		var liObj = $('#'+id);
            		liObj.parent('ul').find('li').removeClass('zxzt_cl');
    				liObj.addClass('zxzt_cl');
            		contRefresh();
            		return;
            	}
            });
            
        }
    }
});