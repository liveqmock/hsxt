var appId = -1;
var envId = -1;
var version = "";
getSession();

$("#app").on("keyup", function (e) {
	var appName = $("#app").val();
	
	if(appName) {
		$("#app").val(appName.toUpperCase());
	}
});

$("#app").on("blur", function (e) {
	var appName = $("#app").val();
	
	if(appName) {
		$("#app").val(appName.toUpperCase());
	}
});

// 提交
$("#item_submit").on("click", function (e) {
    $("#error").addClass("hide");
    var app = $("#app").val();
    var desc = $("#desc").val();
    var emails = $("#emails").val();

    // 验证
    if (!app) {
        $("#error").removeClass("hide");
        $("#error").html("APP名称不能为空！");
        return;
    }
    
    // 验证
    if (!desc) {
        $("#error").removeClass("hide");
        $("#error").html("描述不能为空！");
        return;
    }
    
	var reg =/^[0-9A-Z-]+$/;
	var r = app.match(reg);  
    if(r==null){
    	$("#error").show();
    	$("#error").html("对不起，APP名称只可以为大写字母、下划线或横杠组合！");
    	return;
    }  
    
    $.ajax({
        type: "POST",
        dataType:"json",
        url: "api/app",
        data: {
            "app": app,
            "desc": desc,
            "emails": emails
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
