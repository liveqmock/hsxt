getSession();

var configId = Util.param.getConfigId();

fetchFileData();

//
// 渲染主列表
//
function fetchFileData() {
    //
    // 获取APP信息
    //
    $.ajax({
        type: "GET",
        url: "api/web/config/" + configId
    }).done(
        function (data) {
            if (data.success === "true") {
                var result = data.result;
                $("#app").text(
                        result.appName + ' (appid=' + result.appId + ')');
                $("#version").text(result.version);
                $("#env").text(result.envName);
                $("#key").text(result.key);
                $("#oldvalue").text(result.value);
                $("#currentData").text(result.appName);
                // 获取APP下的配置数据
                fetchItems(result.appId, result.envId, result.version,
                    configId);
            }
        });
}

var upload_status = 0;
//
// 校验
//
function validate(formData, jqForm, options) {

    var myfilerar = $('input[name=myfilerar]');

    var val = myfilerar.val();
    if (!validate_file_name(val)) {
        return false;
    }
    
    if(uploadWithFile == 1) {
    	if(!myfilerar) {
    		 $("#error").show();
    		 $("#error").html("请选择上传的文件！");
    	}
    }
    
    return true;
}

//
// 校验文件名
//
function validate_file_name(fileName) {

    switch (fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase()) {
        case 'properties':
            break;
        case 'xml':
            break;
        default:
            //errorrar.html("错误: " + "文件类型必须是properties/.xml");
            //return false;
            return true;
    }

    return true;
}

//
// 上传按钮
//
var bar = $('.bar_rar');
var percent = $('.percent_rar');
var progress_rar = $('.progress_rar');
var myfilerar = $('#myfilerar');
var errorrar = $('#error_rar');
var alertrar = $('#alert_rar');
var add_file_but = $('#open_dialog_rar')

$('#myfilerar').change(function (evt) {
    //
    // 清空错误及显示
    //
    errorrar.empty();
    $("#error").empty();
    upload_status = 0;
    add_file_but.html("上传配置文件...")
    var ret = validate(null, null, null);
    if (ret == true) {
        upload_status = 1;
        var myfilerar = $('input[name=myfilerar]')
        var val = myfilerar.val();
        errorrar.html("&nbsp;&nbsp;准备上传: " + val);
    }
});

// 提交
var options = {

    url: getContextPath()+'/api/web/config/file/' + configId,
    beforeSubmit: validate,
    beforeSend: function (xhr) {
        $("#myfilerar").bind("updatecomplete", function () {
            xhr.abort();
        });
        var percentVal = '0%';
        progress_rar.show();
        bar.width(percentVal)
        percent.html(percentVal);
        errorrar.html("&nbsp;&nbsp;正在上传....请稍候...");
    },
    uploadProgress: function (event, position, total, percentComplete) {
        var percentVal = percentComplete + '%';
        upload_status = 1;
        bar.width(percentVal)
        percent.html(percentVal);
        errorrar.html("&nbsp;&nbsp;正在上传....请稍候...");
    },
    success: function () {
        var percentVal = '100%';
        bar.width(percentVal)
        percent.html(percentVal);
        progress_rar.hide();
    },
    complete: function (xhr) {

        $("#error").show();
        errorrar.show();

        var is_ok = true;
        if (xhr.status != 200 && xhr.status != 0) {
            errorrar.html("上传失败，请重新上传. 状态码：" + xhr.status);
            is_ok = false;
        } else if (xhr.aborted == 1) {
            is_ok = false;
        } else if (xhr.statusText == "abort") {
            is_ok = false;
        } else {
            xhr.responseText = xhr.responseText.replace("<PRE>", "");
            xhr.responseText = xhr.responseText.replace("</PRE>", "");
            data = $.parseJSON(xhr.responseText);
            if (data.success === "true") {
            	 $("#error").hide();
            	 $("#alertInfo").show();
                 $("#alertInfo").html(data.result);
                 
                 setTimeout(function() {
                 	$("#alertInfo").hide();
                 }, 2000);
            } else {
                add_file_but.html("添加资源文件...")
                Util.input.whiteError(errorrar, data);
                is_ok = false;
            }
        }
        if (is_ok == true) {
            add_file_but.html("重新上传");
            
            $("#error").hide();
            
            errorrar.hide();
            
            alertrar.show();
            alertrar.html("&nbsp;&nbsp;上传成功！");
            
            setTimeout(function() {
            	alertrar.hide();
            }, 2000);
            
            upload_status = 2;
            // 重新刷新
            fetchFileData();
        } else {
            upload_status = 3;
            bar.width(0)
            percent.html(0);
        }
    }
};

var uploadWithFile = -1;

//
$("#file_submit").unbind('click').on('click', function (e) {
    if (uploadWithFile == -1) {
        $("#error").show();
        $("#error").html("请选择上传方式");
        return false;
    }
    
    if(uploadWithFile == 1) {
    	if(!myfilerar) {
    		 $("#error").show();
    		 $("#error").html("请选择上传的文件！");
    		 
    		 return false;
    	}
    }
});

$("#oldvalue").dblclick(function (e) {
    $("#file_upload_choice").hide();
    // $("#text_upload_choice").show().children().show();
     $("#fileContent").text($("#oldvalue").text());
	$("#oldvalue").hide();
    $("#fileContent").show();
    
    $("#uploadChoiceA span:first-child").text("输入文本");
    actionChoiceSelected($("#uploadChoice li a"), $("#uploadChoiceA"));
});

//
// 上传方式
//
$("#uploadChoice").on('click', 'li a', function () {    
	actionChoiceSelected(this, null);
});

function actionChoiceSelected(_this, choiceA) {
	
	var selText = (choiceA) ? $(choiceA).text() : $(_this).text();

    //
    // 清除错误信息
    //
    $("#error").html("");
    errorrar.html("");
    
    $("#uploadChoiceA span:first-child").text(selText);

    if (0 <= selText.indexOf("上传配置文件")) {
    	
        $("#oldvalue").show();
        $("#fileContent").hide();

        $("#file_upload_choice").show().children().show();
        $("#text_upload_choice").hide();
        uploadWithFile = 1;
        myfilerar = null;
        
		setTimeout(function() {
			window.location = "#bottom";
		}, 50);

    } else if (0 <= selText.indexOf("输入文本")) {
    	$("#file_upload_choice").hide();
       // $("#text_upload_choice").show().children().show();
        $("#fileContent").text($("#oldvalue").text());
        $("#oldvalue").hide();
        $("#fileContent").show();
        
        uploadWithFile = 0;        

		setTimeout(function() {
			window.location = "#fileContent";
		}, 50);
    }

    //
    // 事件绑定
    //
    if (uploadWithFile) {
        $("#file_submit").unbind('click').on('click', function (e) {
            if (!confirm("确认修改？")) {
                return;
            }
            // 提交
            $('#form').ajaxSubmit(options);
        });
    } else {
        $("#file_submit").unbind('click').on('click', function (e) {
            var fileContent = $("#fileContent").val();

            $.ajax({
                type: "POST",
                dataType:"json",
                url: "api/web/config/filetext/" + configId,
                data: {
                    "fileContent": fileContent
                }

            }).done(function (data) {
                if (!confirm("确认修改？")) {
                    return false;
                }

                errorrar.show();

                if (data.success === "true") {
                    $("#alertInfo").show();
                    $("#alertInfo").html(data.result);
                    
                    setTimeout(function() {
                    	$("#alertInfo").hide();
                    }, 2000);
                } else {
                    $("#error").show();
                    Util.input.whiteError($("#error"), data);
                    
                    setTimeout(function() {
                    	$("#error").hide();
                    }, 3000);
                }
                // 重新刷新
                fetchFileData();
            });
        });
    }
}
