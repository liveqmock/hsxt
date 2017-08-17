
(function ($) {

	getSession();    
    fetchBusList();
    
    bindInputKepress(['#serviceName','#serviceGroup']);
    
    $("#query_submit").on("click", function (e) {
    	fetchBusList();
    });
})(jQuery);

// 渲染主列表
function fetchBusList() {
    var serviceName = $("#serviceName").val();
    var serviceGroup = $("#serviceGroup").val();
    var serviceStatus = $("#serviceStatus").val();
    
    if(serviceName) {
    	serviceName = serviceName.replace(/,$/, "");
    }

    $.ajax({
        type: "GET",
        url: "api/bus/list",
        data: {
            "serviceName": serviceName,
            "serviceGroup": serviceGroup,
            "serviceStatus": serviceStatus
        }
    }).done(function (data) {
        if (data.success === "true") {
            var html = "";
            var result = data.page.result;
            $.each(result, function (index, item) {
                html += renderItem(item, index);
            });
            if (html != "") {
                $("#accountBody").html(html);
            } else {
                $("#accountBody").html("");
            }
        } else {
            $("#accountBody").html("");
        }

        bindDetailEvent(result);
    });
    
    var mainTpl = $("#tbodyTpl").html();
    
    // 渲染主列表
    function renderItem(item, i) {
        
        var modify_link = '<a target="_self" href="modifyService.html?busId=' + item.busId
            + '"><i title="修改" class="icon-edit"></i></a>';
        
        var task_link = "";
        var del_link = "";
        var timeLogo = "";
        
        if(item.hasTaskFlag=="1"){
            del_link = '<a id="itemDel' + item.busId + '" style="cursor:pointer;cursor:hand;"><i title="删除" class="icon-remove"></i></a>';
            timeLogo = '<a target="_self" href="newjob.html?busId=' + item.busId + '">'
                      +'<span title="尚未配置定时任务，点击进行配置" style="color:black;">未配置</span><i title="定时任务配置" class="icon-tint"></i></a>';
        } else {
        	var href='modifyJob.html?triggerName='+item.serviceName+'&triggerGroup='+item.serviceGroup;
            timeLogo = '<a target="_self" href="'+href+'" title="已配置定时任务, 点击可修改"><img src="assets/img/alarm-clock-vector.png" width="20" height="20" /></a>';
            
            del_link = '<a id="itemDel_i_' + item.busId + '" style="cursor:pointer;cursor:hand;"><i title="删除" class="icon-remove"></i></a>';
        }
        
        var front_link = '<a target="_self" href="frontService.html?busId=' + item.busId
            + '"><i title="前置业务配置" class="icon-hand-up"></i></a>';
        
        var post_link = '<a target="_self" href="postService.html?busId=' + item.busId
            + '"><i title="后置业务配置" class="icon-hand-down"></i></a>';
        
        var execute_link = '<a id="itemExe' + item.busId
            + '" style="cursor: pointer; cursor: hand; " ><i title="手动执行" class="icon-step-forward"></i></a>';
        
        var serviceName = '<span title="'+item.desc+'">' + item.serviceName + '</span>';
        
        var fontJobServiceGroup = item.fontJobServiceGroup;
        var postJobServiceGroup = item.postJobServiceGroup;
        
        if(!fontJobServiceGroup) {
        	fontJobServiceGroup = "无";
        } else {
        	fontJobServiceGroup = fontJobServiceGroup.replace(/,/g, ',<br/>');
        }
        
        if(!postJobServiceGroup) {
        	postJobServiceGroup = "无";
        } else {
        	postJobServiceGroup = postJobServiceGroup.replace(/,/g, ',<br/>');
        }
        
    	var state = item.serviceState;
    	
    	if("成功" == state) {
    		state = "<font color='green'>"+ state +"</font>";
    	} else if("失败" == state) {
    		state = "<font color='red' style='font-weight:bold;'>"+ state +"</font>";
    		state = '<a href="javascript:void(0);" onClick="javascript:openDetailErrDesc(\''+item.lastExcecuteId+'\');" data-placement="left" title="点击查看失败原因">'+state+'</a>';
    	} else if("未执行" == state) {
    		state = "<font color='black' style='font-weight:bold;'>"+ state +"</font>";
    		state = '<a href="javascript:void(0);" onClick="javascript:openDetailErrDesc(\''+item.lastExcecuteId+'\');" data-placement="left" title="点击查看原因">'+state+'</a>';
    	}
        
        return Util.string.format(mainTpl,'', i + 1, item.serviceGroup,
        		serviceName , item.servicePara, state, item.desc, del_link, modify_link, task_link, front_link, post_link, execute_link, item.stateUpdateTime, item.version, fontJobServiceGroup, postJobServiceGroup, timeLogo);
    }
}

// 详细列表绑定事件
function bindDetailEvent(result) {
    if (result == null) {
        return;
    }
    $.each(result, function (index, item) {
        var id = item.busId;
        
        // 绑定删除事件
        $("#itemDel_i_" + id).on("click", function (e) {
            window.alert("该业务已经被放入'定时任务队列'中, 您删除该业务之前必须首先将其从'定时任务队列'中删除！");
        });

        // 绑定删除事件
        $("#itemDel" + id).on("click", function (e) {
            deleteDetailTable(id, item.serviceName);
        });
        
        // 绑定手动执行事件
        $("#itemExe" + id).on("click", function (e) {
            executeDetailTable(item.serviceName, item.serviceGroup);
        });
    });
}

// 删除
function deleteDetailTable(id, name) {

    var ret = confirm("你确定要删除业务‘" + name + "’吗 ?");
    if (ret == false) {
        return false;
    }

    $.ajax({
    	type: "POST",
        url: "api/bus/delete/" + id
    }).done(function (data) {
        if (data.success === "true") {
            fetchBusList();
        }
    });
}

//
//绑定输入框的回车事件
//
function bindInputKepress(inputFieldIds) {		
  $.each(inputFieldIds, function (index, inputFieldId) {    	    	
  	// 绑定回车事件
  	$(inputFieldId).bind('keypress', function(event){
  		if(13 == event.keyCode){ 
  			fetchBusList();
  		}
  	})
  });
}

function getHistoryTempArgs(name) {
	$.ajax({
        type: "GET",
        url: "api/bus/getHistoryTempArgs",
        data: {
            "serviceName": name
        }
    }).done(
        function (data) {
            if (data.success === "true") {
               var result = data.result;                
            }
    });
}

var hisTempArgs = '';

function toPreviousHisTempArgs() {	
	$("#tempArgs").val(hisTempArgs);
}

// 手动执行
function executeDetailTable(name, group) {	
	
	$.ajax({
        type: "GET",
        url: "api/bus/getHistoryTempArgs",
        data: {
            "serviceName": name
        }
    }).done(
        function (data) {
            if (data.success === "true") {
            	var resp = data.result;
            	
            	if(resp) {
            		hisTempArgs = resp.tempArgs;
            	}
            	
            	var blank = '&nbsp;&nbsp;&nbsp;';
            	var hisButton = '<img src="assets/img/his.png" width="25" height="30" style="cursor: pointer; cursor: hand; margin-left:10px; margin-bottom:5px;" onclick="javascript:toPreviousHisTempArgs();" title="上一次输入的临时参数" />';
            	var html = '<form id="form1" name="form1">'
               	  +'<div style="text-align:center; padding-top:10px; padding-bottom:15px;"><h4>请输入手工执行参数</h4></div>'
               	  +'  <table width="100%" border="0" cellspacing="0" cellpadding="0">'
                  +'    <tr><td width="85px" height="44" style="font-weight:bold;">业务名称：</td><td>'+name+'</td></tr>'
                  +'    <tr><td width="85px" height="45" style="font-weight:bold;">临时参数：</td><td><input type="text" id="tempArgs" name="tempArgs" value="'+resp.tempArgsKeys+'" style="width:380px;font-weight:bold;" title="请输入临时参数"/>'+hisButton+'</td></tr>'
                  +'    <tr><td width="85px" height="30"></td><td style="color:#999999;">(可选输入, 键值对形式, 格式形如：key1=value1;key2=value2;)</td></tr>'
                  +'  </table>'
                  +'  <table width="100%" border="0" cellspacing="0" cellpadding="0">'
                  +'   <tr>'
                  +'    <td height="40"><label><input id="ignoreFront" name="ignoreFront" type="checkbox" style="margin-left: 30px;" checked/>&nbsp;依赖前置业务</label></td>'
                  +'   </tr>'
                  +'   <tr>'
                  +'     <td height="40"><label><input id="recurPost" name="recurPost" type="checkbox" style="margin-left: 30px;" checked/>&nbsp;执行成功后触发后置业务</label></td>'
                  +'   </tr>'
                  +'  </table>'
                  +'  <table width="100%" border="0" cellspacing="0" cellpadding="0">'
                  +'   <tr>'
                  +' 	 <td height="60" align="center">'
                  +'        <a id="execute_submit" class="btn btn-primary" onclick="javascript:doExecuteTask(\''+name+'\',\''+group+'\');">'+blank+'执行'+blank+'</a>'
                  +'        <a id="execute_submit" class="btn" style="margin-left: 60px;" onclick="javascript:fadeOut();">'+blank+'取消'+blank+'</a>'
                  +'     </td>'
                  +'   </tr>'
                  +'  </table>'
                  +'</form>';
                
                $("#light").html('<div style="padding:20px;">'+html+'</div>');

                $("#light").fadeIn();
                $("#fade").fadeIn();
            }
    });
}

function openDetailErrDesc(lastExecuteId) {
	
	// 为了规避ajax无法传递#井号问题
	lastExecuteId = lastExecuteId.replace("#","@").replace("\.","@@");
		
    $.ajax({
        type: "GET",
        url: "api/detail/get_detail_err_desc/" + lastExecuteId
    }).done(
        function (data) {
            if (data.success === "true") {
               var result = Util.input.escapeHtml(data.result);
                
               var close ='<div style="text-align:right; padding-right:10px; padding-bottom:0px;"><a href="javascript:fadeOut();" title="关闭对话框">关闭</a></div>';
                                   
               $("#light").html(close+'<div style="padding-bottom:5px;font-weight:bold;">失败信息描述：</div><div id="content" class="content"><pre style="color:red;">' + result + '</pre></div>');
    
               $("#light").fadeIn();
               $("#fade").fadeIn();
            }
        });
}

function doExecuteTask(name, group) {	
	var tempArgs = $("#tempArgs").val();
	
    if (tempArgs) {
    	//var reg =/^([A-Za-z0-9\_]{1,}[=]{1}[^\f\n\r\t\v=;]{1,}){1}([;]{1}[A-Za-z0-9\_]{1,}[=]{1}[^\f\n\r\t\v=;]{1,}){0,}$/;
    	var reg =/^([A-Za-z0-9\_]{1,}[=]{1}[^\f\n\r\t\v;]{0,}[;]{0,1}){1}([;]{1}[A-Za-z0-9\_]{1,}[=]{1}[^\f\n\r\t\v;]{0,}[;]{0,1}){0,}$/;

		if (!tempArgs.match(reg)) {
			window.alert("对不起，业务参数格式必须是键值对方式：key=value;key=value... key只能是字母、数字、下划线, value可以是除英文分号之外的任何字符。");			
			return;
		}
    }
    
    {
    	// 忽略前置业务 = 即：不依赖前置业务, 值正好相反的, 这个要注意
	    var ignoreFront = 1;
	    $('input[name="ignoreFront"]:checked').each(function(){
	    	ignoreFront = 0;
	    });
	    
	    // 是否触发后置业务
	    var recurPost = 0;
	    $('input[name="recurPost"]:checked').each(function(){    
	    	recurPost = 1;
	    });    
    }
    
    tempArgs += ';IGNORE_FRONT='+ignoreFront+';RECUR_POST='+recurPost+';';

    $.ajax({
    	type: "POST",
        dataType:"json",
        url: "api/bus/executeOnce",
        data: {
            "serviceName": name,
            "serviceGroup": group,
            "tempArgs":tempArgs
        }
    }).done(function (data) {
        if (data.success === "true") {
        	fadeOut();
            
            setTimeout(function() {
            	fetchBusList();
            	window.alert('"'+name+'" '+data.result +'。');
            }, 400);
        }
    });
}

function fadeOut() {
	$("#light").fadeOut();
	$("#fade").fadeOut();
}

center("#light");
center("#fade");
