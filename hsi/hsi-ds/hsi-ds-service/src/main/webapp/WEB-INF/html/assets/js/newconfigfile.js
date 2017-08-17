var appId = -1;
var envId = -1;
var version = "";
getSession();

var uploadWithFile = -1;

var upload_status = 0;
//
// 校验
//
function validate(formData, jqForm, options) {

	var myfilerar = $('input[name=myfilerar]')

	var val = myfilerar.val();
	if (validate_file_name(val)) {
		return true;
	}

	return false;
}

//
// 校验文件名
//
function validate_file_name(fileName) {
	
	if(!fileName) {
		errorrar.html("请选择要上传的文件！");
		
		window.alert("请选择要上传的文件！");
		
		return false;
	}	

	if ((0 < fileName.lastIndexOf('\disconf.properties'))
			|| (0 < fileName.lastIndexOf('/disconf.properties'))) {
		errorrar.html("错误: "
				+ "文件名称非法, disconf.properties是系统内部文件, 您上传的文件名称不能与之重复！");

		return false;
	}

	switch (fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase()) {
		case 'properties':
			break;
		case 'xml':
			break;
		default:
			errorrar.html("错误: " + "文件类型必须是properties/xml");
			return false;
	}

	return true;
}

function validate2(formData, jqForm, options) {

	validate(formData, jqForm, options);
//
//	if (version == '自定义版本') {
//		version = $('#selfversion_value').val();
//	}
	
	version = $('#versionChoice').val();

	// 验证
	if (appId < 1) {
		$("#error").show();
		$("#error").html("请选择APP！");
		return false;
	}	
	
//	// 验证
//	if (version == "") {
//		$("#error").show();
//		$("#error").html("请选择版本！");
//		return false;
//	}	
//	
//	// 验证
//	if (envId < 1) {
//		$("#error").show();
//		$("#error").html("请选择环境！");
//		return false;
//	}
	
	var reg =/^\d+(\.\d+){3}$/;
	var r = version.match(reg);  
    if(r==null){
    	$("#error").show();
    	$("#error").html("对不起，您输入的版本格式不正确！正确格式形如：1.0.0.0");
    	return false;
    }  
    
	formData.push({
		name : 'appId',
		value : appId
	});
	formData.push({
		name : 'version',
		value : '1.0.0.0'
		// value : version
	});
	formData.push({
		name : 'envId',
		value : '4'
		// value : envId
	});

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
var add_file_but = $('#open_dialog_rar')
$('#myfilerar').change(function(evt) {
	errorrar.empty();
	upload_status = 0;
	add_file_but.html("重新选择配置文件...")
	var ret = validate(null, null, null);
	if (ret == true) {
		upload_status = 1;
		var myfilerar = $('input[name=myfilerar]')
		var val = myfilerar.val();
		errorrar.html("&nbsp;&nbsp;准备上传: " + val);
	}
});

var options = {

	url : "api/web/config/file",
	beforeSubmit : validate2,
	beforeSend : function(xhr) {
		$("#myfilerar").bind("updatecomplete", function() {
			xhr.abort();
		});
		$("#error").html("");
		var percentVal = '0%';
		progress_rar.show();
		bar.width(percentVal)
		percent.html(percentVal);
		errorrar.html("&nbsp;&nbsp;正在上传....请稍候...");
	},
	uploadProgress : function(event, position, total, percentComplete) {
		var percentVal = percentComplete + '%';
		upload_status = 1;
		bar.width(percentVal)
		percent.html(percentVal);
		errorrar.html("&nbsp;&nbsp;正在上传....请稍候...");
	},
	success : function() {
		var percentVal = '100%';
		bar.width(percentVal)
		percent.html(percentVal);
		progress_rar.hide();
	},
	complete : function(xhr) {
		var is_ok = true;
		if (xhr.status != 200 && xhr.status != 0) {
			errorrar.html("上传失败，请重新上传. 状态码：" + xhr.status);
			$("#error").html("上传失败，请重新上传. 状态码：" + xhr.status);
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
				$("#error").html(data.result);
			} else {
				add_file_but.html("添加资源文件...")
				Util.input.whiteError(errorrar, data);
				is_ok = false;
			}
		}
		if (is_ok == true) {
			add_file_but.html("继续上传")
			errorrar.html("&nbsp;&nbsp;上传成功！");
			upload_status = 2;
		} else {
			upload_status = 3;
			bar.width(0)
			percent.html(0);
		}
	}
};

//
$("#file_submit").unbind('click').on('click', function(e) {
	if (uploadWithFile == -1) {
		$("#error").show();
		$("#error").html("请选择上传方式");
		return false;
	}
});

$("#fileContent").val("");

//
// 上传方式
//
$("#uploadChoice").on(
		'click',
		'li a',
		function() {
			
			$("#uploadChoiceA span:first-child").text($(this).text());

			if ($(this).text() == "上传配置文件") {
				$("#file_upload_choice").show().children().show();
				$("#text_upload_choice").hide();
				
				uploadWithFile = 1;
			} else if ($(this).text() == "输入文本") {

				$("#file_upload_choice").hide();
				$("#text_upload_choice").show().children().show();
				$("#fileContent").val('');
				
				uploadWithFile = 0;
			}

			//
			// 清除错误信息
			//
			$("#error").html("");
			errorrar.html("");

			//
			// 事件绑定
			//
			if (uploadWithFile) {

				$("#file_submit").unbind('click').on('click', function(e) {
					// 提交
					$('#form').ajaxSubmit(options);
				});

			} else {
				$("#file_submit").unbind('click').on('click',
						function(e) {
							$("#error").addClass("hide");

							var fileName = $("#fileName").val();
							var fileContent = $("#fileContent").val();
							version = $('#versionChoice').val();
							envId = '4';
							
							// 验证
							if (appId < 1) {
								$("#error").show();
								$("#error").html("请选择APP！");
								return false;
							}	
							
							// 验证
//							if (version == "") {
//								$("#error").show();
//								$("#error").html("请选择版本！");
//								return false;
//							}	
							
							// 验证
//							if (envId < 1) {
//								$("#error").show();
//								$("#error").html("请选择环境！");
//								return false;
//							}

							// 验证
							if (fileName == "") {
								$("#error").show();
								$("#error").html("文件名称不能为空！");
								return false;
							}
							
							// 验证
							if (fileContent == "") {
								$("#error").show();
								$("#error").html("文件内容不能为空！");
								return false;
							}

							// 验证文件名
							if (validate_file_name(fileName) == false) {
								$("#error").html("仅支持.properties/.xml！");
								$("#error").show();
								return false;
							}

							$.ajax({
								type : "POST",
								url : "api/web/config/filetext",
								data : {
									"appId" : appId,
									"envId" : envId,
									"version" : version,
									"fileContent" : fileContent,
									"fileName" : fileName
								}
							}).done(function(data) {
								$("#error").removeClass("hide");
								if (data.success === "true") {
									$("#error").show();
									$("#error").html(data.result);
								} else {
									Util.input.whiteError($("#error"), data);
								}
								return true;
							});

						});
			}
});