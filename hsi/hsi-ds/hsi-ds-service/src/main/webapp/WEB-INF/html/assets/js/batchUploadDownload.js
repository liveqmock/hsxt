var appId = -1;
var envId = -1;
var version = "";
getSession();

var uploadWithFile = 0;

var upload_status = 0;
//
// 校验
//
function validate(formData, jqForm, options) {

	var myfilerar = $('input[name=myfilerar]');

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
	
	switch (fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase()) {
		case 'zip':
			break;
		default:
			errorrar.html("错误: " + "文件类型必须是zip");
			return false;
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
var add_file_but = $('#open_dialog_rar')
$('#myfilerar').change(function(evt) {
	errorrar.empty();
	upload_status = 0;
	add_file_but.html("重新选择zip文件...")
	var ret = validate(null, null, null);
	if (ret == true) {
		upload_status = 1;
		var myfilerar = $('input[name=myfilerar]')
		var val = myfilerar.val();
		errorrar.html("&nbsp;&nbsp;准备上传: " + val);
	}
});

var options = {

	url : "api/web/config/fileUploadByBatch",
	beforeSubmit : validate,
	beforeSend : function(xhr) {
		$("#myfilerar").bind("updatecomplete", function() {
			xhr.abort();
		});
		$("#error").html("");
		var percentVal = '0%';
		progress_rar.show();
		bar.width(percentVal);
		percent.html(percentVal);
		errorrar.html("&nbsp;&nbsp;正在上传....请稍候...");
	},
	uploadProgress : function(event, position, total, percentComplete) {
		var percentVal = percentComplete + '%';
		upload_status = 1;
		bar.width(percentVal);
		percent.html(percentVal);
		errorrar.html("&nbsp;&nbsp;正在上传....请稍候...");
	},
	success : function() {
		var percentVal = '100%';
		bar.width(percentVal);
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
			
			window.alert("上传成功。");
		} else {
			upload_status = 3;
			bar.width(0)
			percent.html(0);
		}
	}
};

//
$("#file_submit").unbind('click').on('click', function(e) {
	// 提交
	$('#form').ajaxSubmit(options);
});

//
$("#file_batchdownload").unbind('click').on('click', function(e) {
	// 提交
	window.location="api/web/config/downloadAllAppFiles";
});



