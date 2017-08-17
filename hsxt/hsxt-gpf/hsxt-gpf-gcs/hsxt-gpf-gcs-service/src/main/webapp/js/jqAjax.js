//p_isasync:false 同步加载，默认异步;p_noloading：true没有加载提示
function JqueryAjaxPost(url, data, beforeSend, callback, err, p_isasync,
		p_noloading) {
	$.ajax({
		url : url,
		type : "POST",
		data : data,
		async : p_isasync,
		dataType : "json",
		beforeSend : function() {
			beforeSend();
			if (p_noloading == true) {
				opens.loading(1);
			}
		},
		success : function(data) {
			if (p_noloading == true) {
				opens.loading(0);
			}
			callback(data);
		},
		error : function(XmlHttpRequest) {
			if (p_noloading == true) {
				opens.loading(0);
			}
			err();
			error(XmlHttpRequest);
		}
	});
}
// p_isasync:false 同步加载，默认异步;p_noloading：true没有加载提示
function JqueryAjaxGet(url, data, beforeSend, callback, err, p_isasync,
		p_noloading) {
	$.ajax({
		url : url,
		type : "GET",
		data : data,
		dataType : "json",
		async : p_isasync,
		beforeSend : function() {
			beforeSend();
			if (p_noloading == true) {
				opens.loading(1);
			}
		},
		success : function(data) {
			if (p_noloading == true) {
				opens.loading(0);
			}
			callback(data);
		},
		error : function(XmlHttpRequest) {
			if (p_noloading == true) {
				opens.loading(0);
				;
			}
			err();
			error(XmlHttpRequest);
		}
	});
}
// ajax提交 不带参数，带表单
function jqAjaxSerialize(url, data, beforeSend, callback, err, p_noloading) {
	$.ajax({
		url : url,
		type : "POST",
		data : data,
		dataType : "json",
		beforeSend : function() {
			beforeSend();
			if (p_noloading == true) {
				opens.loading(1);
			}
		},
		success : function(data) {
			if (p_noloading == true) {
				opens.loading(0);
			}
			callback(data);
		},
		error : function(XmlHttpRequest) {
			if (p_noloading == true) {
				opens.loading(0);
				;
			}
			err();
			error(XmlHttpRequest);
		}
	});
}
// 后来发生异常
function error(XmlHttpRequest) {
	var err = XmlHttpRequest.responseText;
	if (err.indexOf("window.parent.location") >= 0) {
		alert('登录超时，请重新登录！');
		window.parent.location = '/';
	} else {
		alert("操作失败;" + XmlHttpRequest.responseText);
	}
	return;
}
// 回车事件
function keyCode(obj, calbaack) {
	$(obj).keydown(function(e) {
		if (e.keyCode == 13) {
			calbaack();
			return false;
		}

	});
}
