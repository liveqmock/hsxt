function onpenMsg() {
			// 弹出提示
			this.alerts = function(type, cont, h) {
				if (type.indexOf('alert') >= 0) {
					h = null ? 150 : h;
					var img = '<img src="../js/jquery-ui/images/err.png"/>';
					if (type.indexOf('suc') >= 0)
						img = '<img src="../js/jquery-ui/images/suc.png"/>';

					var conts = '<div style="float:left;width:20%;padding-top:8px; padding-bottom:8px;">'
							+ img + '</div>';

					conts += '<div style="float:left;width:78%; font-size:14px;padding-top:8px; padding-bottom:8px;line-height:26px;">'
							+ cont + '</div>';
					var html = '<div style="font-size:13px;" id="dialog-modal" title="系统提示">'
							+ conts + '</div>';
					$('body').append(html);
					$("#dialog-modal").dialog({
						height : h,
						modal : true,
						buttons : {
							"确定" : function() {
								$(this).dialog("close");
							}
						},
						close : function() {
							html = null;
							conts = null;
							img = null;
							$("#dialog-modal").remove();
						}
					});
				}
			},
			// 弹出提示 带返回方法
			this.alertscb = function(type, cont, callback, h) {
				if (type.indexOf('alert') >= 0) {
					h = null ? 150 : h;
					var img = '<img src="../js/jquery-ui/images/err.png"/>';
					if (type.indexOf('suc') >= 0)
						img = '<img src="../js/jquery-ui/images/suc.png"/>';

					var conts = '<div style="float:left;width:20%;padding-top:8px; padding-bottom:8px;">'
							+ img + '</div>';

					conts += '<div style="float:left;width:78%; font-size:14px;padding-top:8px; padding-bottom:8px;line-height:26px;">'
							+ cont + '</div>';
					var html = '<div style="font-size:13px;" id="dialog-modal" title="系统提示">'
							+ conts + '</div>';
					$('body').append(html);
					$("#dialog-modal").dialog({
						height : h,
						modal : true,
						buttons : {
							"确定" : function() {
								$(this).dialog("close");
								callback();
							}
						},
						close : function() {
							html = null;
							conts = null;
							img = null;
							$("#dialog-modal").remove();
						}
					});
				}
			},
			// 确认框
			this.confirms = function(callback, cont) {
				var img = '<img src="../js/jquery-ui/images/confirm.png"/>';
				var conts = '<div style="float:left;width:20%;padding-top:8px; padding-bottom:8px;">'
						+ img + '</div>';

				conts += '<div style="float:left;width:78%; font-size:14px;padding-top:8px; padding-bottom:8px;line-height:26px;">'
						+ (cont == null ? "确定执行操作吗？" : cont) + '</div>';
				var html = '<div style="font-size:13px;" id="dialog-modalc" title="系统提示">'
						+ conts + '</div>';
				$('body').append(html);
				$("#dialog-modalc").dialog({
					modal : true,
					buttons : {
						"确定" : function() {
							$(this).dialog("close");
							callback();
						},
						"取消" : function() {
							$(this).dialog("close");
						}
					},
					close : function() {
						html = null;
						conts = null;
						img = null;
						$("#dialog-modalc").remove();
					}
				});
			},
			// 弹出div
			this.openDiv = function(id, title, w, h, callback) {
				$(id).dialog({
					title : title,
					width : w,
					height : h,
					modal : true,
					buttons : {
						"确定" : function() {
							callback($(this));
						},
						"取消" : function() {
							$(this).dialog("close");
						}
					}
				});
			},
			// 弹出iframe
			this.openIframe = function(title, w, h, url) {
				var html = '<div id="dialog-iframe" title="'
						+ title
						+ '"><iframe scrolling="yes" src="'
						+ url
						+ '" frameborder="0" style="width:100%;height:100%;"></iframe></div>';
				$('body').append(html);
				$("#dialog-iframe").dialog({
					width : w,
					height : h,
					modal : true,
					buttons : {
						"确定" : function() {
							$(this).dialog("close");
						},
						"取消" : function() {
							$(this).dialog("close");
						}
					},
					close : function() {
						html = null;
						$("#dialog-iframe").find('iframe').attr("src", null);
						$("#dialog-iframe").remove();
					}
				});
			},// 调用关闭
			this.closeIframe = function() {
				$("#dialog-iframe").dialog("close");
			},// 弹出正在处理
			this.loading = function(type, cont) {
				cont = (cont == null ? "正在处理中..." : cont)
				if (type == 1) {
					var conts = '<div id="dialog-modal-loading" style="font-size:14px;padding-top:1px; padding-bottom:1px;line-height:22px;">'
							+ cont + '</div>';
					$('body').append(conts);
					$("#dialog-modal-loading").dialog({
						title : false,
						height : 70,
						width : 70,
						modal : true,
						close : function() {
							conts = null;
							$("#dialog-modal-loading").remove();
						}
					});
					$(".ui-dialog-titlebar").hide();
				} else if (type == 0) {
					$(".ui-dialog-titlebar").show();
					$("#dialog-modal-loading").dialog("close");
				}
			}
}
// 验证表单
$(document).ready(function() {
	$("[reg]").blur(function() {
		validate($(this));
	});
});
// 获取页面的REG的正则表达式
function validate(obj) {
	var reg = new RegExp(obj.attr("reg"));
	var objValue = obj.attr("value");
	if (!reg.test(objValue)) {
		obj.addClass("inputerr");
		return false;
	} else {
		obj.removeClass("inputerr");
		return true;
	}
}

// select selected选中
function selected(id, v) {
	$(id + " option").each(function() {
		if ($(this).val() == v)
			$(this).attr("selected", true);
		else
			$(this).attr("selected", false);
	});
}

// 返回selected选中值的text文本
function ReturnSelected(id, v) {
	var val = '';
	$(id + " option").each(function(i) {
		if ($(this).val() == v) {
			val = $(id).find("option").eq(i).text();
			return false;
		}
	});
	return val.replace(v + '|', '');
}
// 获取单选项的值
function GetRadio(obj) {
	var val = '';
	$(obj).each(function() {
		if ($(this).attr("checked"))
			val = $(this).val();
	});
	return val;
}
// 获取多选项的值
function GetCheckbox(obj) {
	var val = '', i = 0;
	$(obj).each(function() {
		if ($(this).attr("checked")) {
			i++;
			if (i > 1)
				val += ',';
			val += $(this).val();
		}
	});
	return val;
}
// 判断多选项 勾选
function SetCheckbox(obj, Arry) {
	$(obj).each(function() {
		var t = $(this);
		var v = t.val();
		if (Arry.indexOf(v) >= 0) {
			t.attr("checked", true);
		}
	});
}
// 全选
function CheckAll(id, nid) {
	var chk_ID = window.document.getElementsByName(nid);
	var chk_value = window.document.getElementById(id).checked;
	for ( var i = 0; i < chk_ID.length; ++i) {
		chk_ID[i].checked = chk_value;
	}
	window.document.getElementById(id).checked = chk_value;
}
