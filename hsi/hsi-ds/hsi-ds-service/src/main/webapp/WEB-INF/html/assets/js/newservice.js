getSession();

$("#serviceGroup").on("keyup", function (e) {
	var value = $("#serviceGroup").val();
	
	if(value) {
		$("#serviceGroup").val(value.toUpperCase());
	}
});

// 提交
$("#item_submit").on("click", function (e) {
    $("#error").addClass("hide");
    var serviceName = $("#serviceName").val();
    var serviceGroup = $("#serviceGroup").val();
    var servicePara = $("#servicePara").val();
    var desc = $("#desc").val();
    var version = $("#version").val();

    // 验证
    if (!serviceName) {
        $("#error").removeClass("hide");
        $("#error").html("业务名称不能为空！");
        $("#serviceName").focus();
        
        return;
    } else {
        //正则中添加需要验证的特殊字符
        var vkeyWords=/[`~!@#$^&*()"%\^+=|\[\]\{\};'\\\,.<>/?]/;
        
        if(vkeyWords.test(serviceName)){
        	$("#error").removeClass("hide");
            $("#error").html("业务名称不能含有特殊字符: ~!@#$^&*()\"%^+=|[]{};'\,.<>/? ");
            $("#serviceName").focus();
            
            return;
        }
    }
    
    // 验证
    if (!serviceGroup) {
        $("#error").removeClass("hide");
        $("#error").html("DUBBO服务名不能为空！");
        
        $("#serviceGroup").focus();
        
        return;
    } else {
    	//正则中添加需要验证的特殊字符
    	var vkeyWords=/[`~!@#$^&*()"%\^+=|\[\]\{\};'\\\,.<>/?]/;
        
        if(vkeyWords.test(serviceGroup)){
        	$("#error").removeClass("hide");
            $("#error").html("DUBBO服务名不能含有特殊字符: ~!@#$^&*()\"%^+=|[]{};'\,.<>/? ");
            $("#serviceGroup").focus();
            
            return;
        }
    }
    
    // 验证
    if (!version) {
        $("#error").removeClass("hide");
        $("#error").html("DUBBO服务版本不能为空！");
        return;
    }
    
    // 验证
    if (!desc) {
        $("#error").removeClass("hide");
        $("#error").html("业务描述不能为空！");
        return;
    }
    
	var reg =/^\d+(\.\d+){2}$/;
	var r = version.match(reg);  
    if(r==null){
    	$("#error").show();
    	$("#error").html("输入的DUBBO服务版本格式不正确！正确格式形如：1.0.0");
    	return false;
    }  
    
    if (servicePara != null && servicePara != undefined && servicePara != '') {
	    //var reg =/^([A-Za-z0-9\_]{1,}[=]{1}[A-Za-z0-9\_]{1,}){1}([;]{1}[A-Za-z0-9\_]{1,}[=]{1}[A-Za-z0-9\_]{1,}){0,}$/;
    	var reg =/^([A-Za-z0-9\_]{1,}[=]{1}[^\f\n\r\t\v;]{1,}){1}([;]{1}[A-Za-z0-9\_]{1,}[=]{1}[^\f\n\r\t\v;]{1,}){0,}$/;
	    var r = servicePara.match(reg);  
	    if(r==null){
	        $("#error").show();
	        $("#error").html("对不起，业务参数格式：key=value;key=value... key只能是字母、数字、下划线, value可以是除英文分号之外的任何字符。");
	        return;
	    }
    }
    
    $.ajax({
        type: "POST",
        dataType:"json",
        url: "api/bus",
        data: {
            "serviceName": serviceName,
            "serviceGroup": serviceGroup,
            "servicePara": servicePara,
            "version": version,
            "desc": desc
        }
    }).done(function (data) {
        $("#error").removeClass("hide");
        if (data.success === "true") {
            $("#error").html(data.result);
            
            window.alert(data.result);
            
            window.location.href="business.html";
        } else {
            Util.input.whiteError($("#error"), data);
        }
    });
});
