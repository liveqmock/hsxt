var appId = -1;
var envId = -1;
var version = "";
getSession();

// 提交
$("#item_submit").on("click", function (e) {
    $("#error").addClass("hide");

    if (version == '自定义版本') {
        version = $('#selfversion_value').val();
    }

    var key = $("#key").val();
    var value = $("#value").val();
    
    // 验证
	if (appId < 1) {
		$("#error").removeClass("hide");
		$("#error").html("请选择APP！");
		return;
	}	
	
	// 验证
	if (version == "") {
		$("#error").removeClass("hide");
		$("#error").html("请选择版本！");
		return;
	}	
	
	// 验证
	if (envId < 1) {
		$("#error").removeClass("hide");
		$("#error").html("请选择环境！");
		return;
	}

    // 验证
    if (!value || !key) {
        $("#error").removeClass("hide");
        $("#error").html("配置项和配置值均不能为空！");
        return;
    }
    
	var reg =/^\d+(\.\d+){3}$/;
	var r = version.match(reg);  
    if(r==null){
    	$("#error").show();
    	$("#error").html("对不起，您输入的版本格式不正确！正确格式形如：1.0.0.0");
    	return;
    }
    
    $.ajax({
        type: "POST",
        dataType:"json",
        url: "api/web/config/item",
        data: {
            "appId": appId,
            "version": version,
            "key": key,
            "envId": envId,
            "value": value
        }
    }).done(function (data) {
        $("#error").removeClass("hide");
        if (data.success === "true") {
            $("#error").html(data.result);
        } else {
            Util.input.whiteError($("#error"), data);
        }
    });
});
