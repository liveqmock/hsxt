<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人号平台路由管理</title>
<script src="../js/jquery-1.8.0.min.js" type="text/javascript"></script>
<script src="../js/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="../js/jqAjax.js" type="text/javascript"></script>
<script src="../js/OpenMsg.js" type="text/javascript"></script>
<link rel="stylesheet" href="../js/jquery-ui/jquery-ui.css">
</head>
<%String path = this.getServletContext().getContextPath(); %>
<body>
	<table width="100%" border="0" cellpadding="8" cellspacing="0">
		<tr style="text-align: left; background-image: url(../images/table_b.png); background-repeat: repeat-x; background-position: left center;">
			<td width="7%" align="center"><a href="#" id="add" class="ui-state-default ui-corner-all">添加</a></td>
			<td width="93%">
			             服务资源号前5位：<input type="text" id="resNo">&nbsp; 
			             平台代码：<select id="platNos_q"></select>&nbsp; 
			    <a href="#" id="query" class="ui-state-default ui-corner-all">查 询</a>
			</td>
		</tr>
	</table>
	<form id="listForm">
		<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#cccccc">
			<tr style="text-align: center; background-image: url(../images/table_b.png); background-repeat: repeat-x; background-position: left center;">
				<th style="width: 33%">服务资源号前5位</th>
				<th style="width: 34%">平台代码</th>
				<th style="width: 33%">操作</th>
			</tr>
			<tbody id="tbd">
			</tbody>
			<jsp:include page="../page.jsp"/>
		</table>
	</form>
	<div id="editFormDiv" style="width: 100%; display: none;">
		<form id="editForm">
			<table width="100%" border="0" cellpadding="5" cellspacing="1">
				<tbody>
					<tr>
						<td align="right">服务资源号前5位：</td>
						<td align="left">
							<input type="text" name="resNo" reg="[^\s]">
						</td>
					</tr>
					<tr>
						<td width="30%" align="right">平台代码：</td>
						<td width="70%" align="left">
							<select  id="platNos_e" name="platNo" reg="[^\s]"></select>
						</td>
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
						resNo : $('#resNo').val(),
						platNo : $('#platNos_q').val(),
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
								var objValue = list[i];
								html += '<tr style="background-color:#FFFFFF; text-align: center;">';
								html += '<td title="resNo">' + objValue.resNo + '</td>';
								html += '<td title="platNo">' + objValue.platNo + '</td>';
								html += '<td><a href="#s" onclick="obj.edit(this);">修改</a> | <a href="#s" onclick="obj.del(\'' + objValue.resNo + '\');">删除</a></td>';
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
					}, true, true);
		},
		//修改
		edit : function(ths) {
			var th = $(ths);
			var parent = th.parent().parent();
			var formid = $("#editForm");
			parent.find("td").each(function(k) {
				var t = $(this);
				var v = t.text();
				if (t.attr("title") != null) {
					$(formid).find("[name='" + t.attr("title") + "']").val(v);
				}
			});
			opens.openDiv('#editFormDiv', '修改个人号平台路由', 500, 420, function(objs) {
				var isSubmit = true;
				$("#editForm [reg]").each(function() {
					if (!validate($(this))) {
						isSubmit = false;
					}
				});
				if (!isSubmit) {
					opens.alerts('alert_err', '红框的为必填项！');
					return;
				}
				JqueryAjaxPost("update.do", $("#editForm").serialize(),
						function() {
						}, function(data) {
							if (data) {
								opens.alerts('alert_suc', '您的操作成功！');
								//清空表单数据
								$('#editForm')[0].reset();
								objs.dialog("close");
								//重新加载数据
								obj.load(function() {
								});
							} else {
								opens.alerts('alert_err', '您的操作失败！');
						    }
						}, function() {
						}, true, false);
			});
		},
		//删除
		del : function(resNo) {
			opens.confirms(function() {
				if (resNo == null || resNo == "") {
					opens.alerts('alert_err', '个人资源号不能为空！');
					return;
				}
				JqueryAjaxPost("del.do", {
					resNo : resNo
				}, function() {
				}, function(data) {
					if (data) {
						opens.alerts('alert_suc', '您的操作成功！');
						pageNo = 1;
						obj.load(function() {
							page.GetPage();
						});
					} else {
						opens.alerts('alert_err', '您的操作失败！');
					}
				}, function() {
				}, true, false);
			});
		},
		//加载平台代码
		getPlatNos : function() {
			JqueryAjaxGet("<%=path%>/plat/getPlatDropdownmenu.do", {}, function() {
			}, function(data) {
				var list = data;
				var html = '<option value="">-----请选择-----</option>';
				if (list.length >= 1) {
					for ( var i = 0; i < list.length; i++) {
						var objValue = list[i];
						html += '<option value="'+objValue.platNo+'">' + objValue.platNo+'|'+objValue.platNameCn + '</option>';
					}
				}
				$('#platNos_q,#platNos_e').html(html);
			}, function() {
			}, true, false);
		}
	};

	$(document).ready(function() {
		$("#add,#query").button();
		obj.getPlatNos();

		obj.load(function() {
			page.GetPage();
		});
	});
	//添加
	$('#add').click(function() {
		//清空表单数据
		$('#editForm')[0].reset();
		opens.openDiv('#editFormDiv', '添加个人号平台路由', 500, 460, function(objs) {
			var isSubmit = true;
			
			$("#editForm [reg]").each(function() {
				if (!validate($(this))) {
					isSubmit = false;
				}
			});
			if (!isSubmit) {
				opens.alerts('alert_err', '红框的为必填项！');
				return;
			}
			JqueryAjaxPost("add.do", $("#editForm").serialize(), function() {
			}, function(data) {
				if(2==data){
					opens.alerts('alert_err', '此记录已存在！');
				}else if (1==data) {
					opens.alerts('alert_suc', '您的操作成功！');
					objs.dialog("close");
					//重新加载数据
					pageNo = 1;
					obj.load(function() {
						page.GetPage();
					});
					//清空表单数据
					$('#editForm')[0].reset();
				} else  {
					opens.alerts('alert_err', '您的操作失败！');
				}
			}, function() {
			}, true, false);
		});
	});
	//查询
	$('#query').click(function() {
		pageNo = 1;
		obj.load(function() {
			page.GetPage();
		});
	});
	
</script>