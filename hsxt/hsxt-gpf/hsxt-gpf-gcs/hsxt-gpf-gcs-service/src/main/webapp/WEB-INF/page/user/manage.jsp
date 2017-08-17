<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台用户管理</title>
<script src="../js/jquery-1.8.0.min.js" type="text/javascript"></script>
<script src="../js/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="../js/jqAjax.js" type="text/javascript"></script>
<script src="../js/OpenMsg.js" type="text/javascript"></script>
<link rel="stylesheet" href="../js/jquery-ui/jquery-ui.css">
</head>
<body>

	<table width="100%" border="0" cellpadding="8" cellspacing="0">
		<tr
			style="text-align: left; background-image: url(../images/table_b.png); background-repeat: repeat-x; background-position: left center;">
			<td><a href="#s" id="add" class="ui-state-default ui-corner-all">添加</a></td>
		</tr>
	</table>
	<form id="forms">
		<table width="100%" border="0" cellpadding="5" cellspacing="1"
			bgcolor="#cccccc">
			<tr
				style="text-align: center; background-image: url(../images/table_b.png); background-repeat: repeat-x; background-position: left center;">
				<th style="width: 25%">用户名</th>
				<th style="width: 20%">密码</th>
				<th style="width: 20%">操作</th>
			</tr>
			<tbody id="tbd">
			</tbody>
		</table>
	</form>
	<div id="divs" style="width: 100%; display: none;">
		<form id="forms2">
			<table width="100%" border="0" cellpadding="5" cellspacing="1">
				<tbody>
					<tr>
						<td align="right">用户：</td>
						<td align="left"><input reg="[^\s]" type="text"
							name="userName"></td>
					</tr>
					<tr>
						<td width="25%" align="right">密码：</td>
						<td width="73%" align="left"><input reg="[^\s]"
							type="password" name="userPwd"></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>

<script type="text/javascript">
	var opens = new onpenMsg();
	//创建对象
	var obj = {
		//加载数据
		load : function() {
			JqueryAjaxGet(
					"managePost.do",
					{},
					function() {
					},
					function(data) {
						var list = data;
						if (list.length >= 1) {
							var html = '';
							for ( var i = 0; i < list.length; i++) {
								html += '<tr style="background-color:#FFFFFF; text-align: center;">';
								html += '<td title="userName">'
										+ list[i].userName + '</td>';
								html += '<td title="userPwd">'
										+ list[i].userPwd + '</td>';
								html += '<td title="edit"><a href="#s" onclick="obj.edit(this);">修改</a> | <a href="#s" onclick="obj.del(\''
										+ list[i].userName
										+ '\');">删除</a></td>';
								html += '</tr>';
							}
							$('#tbd').html(html);
						}
					}, function() {
					}, true, false);
		},
		//点击修改某条数据
		edit : function(ths, Cancel) {
			//取消动作
			if (Cancel == "Cancel") {
				var th = $(ths);
				var parent = th.parent().parent();
				var htm = '';
				var user = "";
				parent.find("td").each(
						function(k) {
							var t = $(this);
							var v = t.find('input').eq(0).val();
							if (t.attr("title") == "edit") {
								return false;
							} else {
								if (t.attr("title") == "userName") {
									user = v;
								}
								htm += '<td title="' + t.attr("title") + '">'
										+ v + '</td>';
							}
						});
				htm += '<td title="edit"><a href="#s" onclick="obj.edit(this);">修改</a> | <a href="#s" onclick="obj.del(\''
						+ user + '\');">删除</a></td>';
				parent.html(htm);
			} else {//修改动作
				if ($('#tbd input[name="userName"]').length > 0) {
					opens.alerts('alert_err', '已有一条记录正在修改！');
					return;
				}
				var th = $(ths);
				var parent = th.parent().parent();
				var htm = '';
				var user = "";
				parent.find("td").each(
						function(k) {
							var t = $(this);
							var v = t.text();
							if (t.attr("title") == "edit") {
								return false;
							} else {
								if (t.attr("title") == "userName") {
									user = v;
								}
								htm += '<td><input reg="[^\s]" name="'
										+ t.attr("title") + '" value="' + v
										+ '" /></td>';
							}
						});
				htm += '<td title="edit"><a href="#s" onclick="obj.save(this);">确定</a> | <a href="#s" onclick="obj.edit(this,\'Cancel\');">取消</a> | <a href="#s" onclick="obj.del(\''
						+ user + '\');">删除</a></td>';
				parent.html(htm);
			}
		},
		//修改确定保存
		save : function(ths) {
			var isSubmit = true;
			$("#forms [reg]").each(function() {
				if (!validate($(this))) {
					isSubmit = false;
				}
			});
			if (!isSubmit) {
				opens.alerts('alert_err', '红框的为必填项！');
				return;
			}
			var th = $(ths);
			var parent = th.parent().parent();
			JqueryAjaxPost("Upadte.do", $("#forms").serialize(), function() {
			}, function(data) {

				if (data == 1) {
					opens.alerts('alert_suc', '您的操作成功！');
					obj.load();
				} else
					opens.alerts('alert_err', '您的操作失败！');
			}, function() {
			}, true, false);
		},//删除某个用户
		del : function(user) {
			opens.confirms(function() {
				if (user == null || user == "") {
					opens.alerts('alert_err', '用户名不能为空！');
					return;
				}
				JqueryAjaxPost("del.do", {
					userName : user
				}, function() {
				}, function(data) {
					if (data == 1) {
						opens.alerts('alert_suc', '您的操作成功！');
						obj.load();
					} else
						opens.alerts('alert_err', '您的操作失败！');
				}, function() {
				}, true, false);
			});

		}
	};

	$(document).ready(function() {
		$("#add").button();
		obj.load();
	});
	//添加
	$('#add').click(function() {
		opens.openDiv('#divs', '添加用户', 300, 210, function(objs) {
			var isSubmit = true;
			$("#forms2 [reg]").each(function() {
				if (!validate($(this))) {
					isSubmit = false;
				}
			});
			if (!isSubmit) {
				opens.alerts('alert_err', '红框的为必填项！');
				return;
			}
			JqueryAjaxPost("add.do", $("#forms2").serialize(), function() {
			}, function(data) {
				if (data == 1) {
					opens.alerts('alert_suc', '您的操作成功！');
					obj.load();
					objs.dialog("close");
				} else
					opens.alerts('alert_err', '您的操作失败！');
			}, function() {
			}, true, false);

		});
	})
</script>