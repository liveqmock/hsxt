<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统代码管理</title>
<script src="../js/jquery-1.8.0.min.js" type="text/javascript"></script>
<script src="../js/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="../js/jqAjax.js" type="text/javascript"></script>
<script src="../js/OpenMsg.js" type="text/javascript"></script>
<link rel="stylesheet" href="../js/jquery-ui/jquery-ui.css">
</head>
<body>
	<table width="100%" border="0" cellpadding="8" cellspacing="0">
		<tr style="text-align: left; background-image: url(../images/table_b.png); background-repeat: repeat-x; background-position: left center;">
			<td width="7%" align="center"><a href="#s" id="add"
				class="ui-state-default ui-corner-all">添加</a></td>
			<td width="93%">代码：<input type="text" id="subsysCodes"> &nbsp; 中文名：<input type="text" id="sysNames"> &nbsp; <a href="#s" id="sea" class="ui-state-default ui-corner-all">查 询</a></td>
		</tr>
	</table>
	<form id="forms">
		<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#CCCCCC">
			<tr style="text-align: center; background-image: url(../images/table_b.png); background-repeat: repeat-x; background-position: left center;">
				<th style="width: 8%">代码</th>
				<th style="width: 30%">中文名</th>
				<th style="width: 30%">描述</th>
				<th style="width: 22%">版本号</th>
				<th style="width: 10%">操作</th>
			</tr>
			<tbody id="tbd">
			</tbody>
			<jsp:include page="../page.jsp" />
		</table>
	</form>
	<div id="divs" style="width: 100%; display: none;">
		<form id="forms2">
			<table width="100%" border="0" cellpadding="5" cellspacing="1">
				<tbody>
					<tr>
						<td align="right">系统代码：</td>
						<td align="left"><input type="text" name="subsysCode"></td>
					</tr>
					<tr>
						<td width="25%" align="right">中文名：</td>
						<td width="73%" align="left"><input reg="[^\s]" type="text" name="sysName"></td>
					</tr>
					<tr>
						<td width="25%" align="right">系统描述：</td>
						<td width="73%" align="left"><input maxlength="200" type="text" name="sysDesc"></td>
					</tr>
				</tbody>

			</table>
		</form>
	</div>
</body>
</html>

<script type="text/javascript">
var pagecount = 0;
var pageNo = 1;
var pagesize = $('#pagesizeID').val();
	var opens = new onpenMsg();
	//创建对象
	var obj = {
		//加载数据
		load : function(callback) {
			JqueryAjaxGet(
					"managePost.do",
					{
						subsysCode : $('#subsysCodes').val(),
						sysName : $('#sysNames').val().replace(/%/g, "/%").replace(/_/g, "/_"),
						pageSize : pagesize,
						pageNo : pageNo
					},
					function() {
					},
					function(data) {
						var list = data.list;
						var html = '';
						if (list.length >= 1) {
							for ( var i = 0; i < list.length; i++) {
								var lists = list[i];
								html += '<tr style="background-color:#FFFFFF; text-align: center;">';
								html += '<td title="subsysCode">'
										+ lists.subsysCode + '</td>';
								html += '<td title="sysName">' + lists.sysName
										+ '</td>';
								html += '<td title="sysDesc">' + lists.sysDesc
										+ '</td>';
								html += '<td>' + lists.version + '</td>';
								html += '<td><a href="#s" onclick="obj.edit(this);">修改</a> | <a href="#s" onclick="obj.del(\''
										+ lists.subsysCode
										+ '\');">删除</a></td>';
								html += '</tr>';
							}
						}
						$('#tbd').html(html);
						pagecount = data.TotalPages;
						$('#data-max-page').attr('data-max-page', pagecount);
						$('#counts').html(data.totalCount);
						//返回回调方法
						callback();
					}, function() {
					}, true, false);
		},//点击修改
		edit : function(ths) {
			var th = $(ths);
			var parent = th.parent().parent();
			var formid = $("#forms2");
			$("#forms2 [name='subsysCode']").attr("readonly", true);
			parent.find("td").each(function(k) {
				var t = $(this);
				var v = t.text();
				if (t.attr("title") != null) {
					$(formid).find("[name='" + t.attr("title") + "']").val(v);
				}
			});
			//修改系统代码
			opens.openDiv('#divs', '修改系统代码', 500, 260, function(objs) {
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
				JqueryAjaxPost("upadte.do", $("#forms2").serialize(),
						function() {
						}, function(data) {
							if (data == 1) {
								opens.alerts('alert_suc', '您的操作成功！');
								//清空表单数据
								$('#forms2')[0].reset();
								objs.dialog("close");
								//重新加载数据
								obj.load(function() {
									
								});
							} else
								opens.alerts('alert_err', '您的操作失败！');
						}, function() {
						}, true, false);
			});
		},//删除某条记录
		del : function(subsysCode) {
			opens.confirms(function() {
				if (subsysCode == null || subsysCode == "") {
					opens.alerts('alert_err', '系统代码不能为空！');
					return;
				}
				JqueryAjaxPost("del.do", {
					subsysCode : subsysCode
				}, function() {
				}, function(data) {
					if (data == 1) {
						opens.alerts('alert_suc', '您的操作成功！');
						//重新加载数据
						pageNo = 1;
						obj.load(function() {
							page.GetPage();
						});
					} else
						opens.alerts('alert_err', '您的操作失败！');
				}, function() {
				}, true, false);
			});
		}
	};

	$(document).ready(function() {
		$("#add,#sea").button();
		//重新加载数据
		obj.load(function() {
			page.GetPage();
		});
	});
	//添加
	$('#add').click(function() {
		//清空表单数据
		$('#forms2')[0].reset();
		$("#forms2 [name='subsysCode']").attr("readonly", false);
		opens.openDiv('#divs', '添加系统代码', 500, 260, function(objs) {
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
					objs.dialog("close");
					//重新加载数据
					pageNo = 1;
					obj.load(function() {
						page.GetPage();
					});
					//清空表单数据
					$('#forms2')[0].reset();
				} else
					opens.alerts('alert_err', '您的操作失败！');
			}, function() {
			}, true, false);

		});
	});
	//查询
	$('#sea').click(function() {
		//重新加载数据
		pageNo = 1;
		obj.load(function() {
			page.GetPage();
		});
	});
</script>