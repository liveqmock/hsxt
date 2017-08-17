<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>枚举信息管理</title>
<script src="../js/jquery-1.8.0.min.js" type="text/javascript"></script>
<script src="../js/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="../js/jqAjax.js" type="text/javascript"></script>
<script src="../js/OpenMsg.js" type="text/javascript"></script>
<link rel="stylesheet" href="../js/jquery-ui/jquery-ui.css">
</head>
<%String path = this.getServletContext().getContextPath(); %>
<body>
	<table width="100%" border="0" cellpadding="8" cellspacing="0">
		<tr style="text-align: left; background-color:#faf8f9; background-image: url(../images/table_b.png); background-repeat: repeat-x; background-position: left center;">
			<td width="15%" align="center"><a href="#" id="add" class="ui-state-default ui-corner-all">添加</a></td>
			<td width="70%">
				<table width="866" border="0" cellpadding="3" cellspacing="0">
					<tr>
						<td width="117" align="right">语言代码：</td>
						<td width="180"><select name="select" id="languageCodes_q">
						  </select>						</td>
						<td width="276">枚举组类型：<input type="text" id="groupCode" ></td>
						<td width="269">枚举值代码：<input type="text" id="itemCode" ></td>
					</tr>
					<tr>
						<td align="right">枚举值显示名：</td>
						<td><input name="text" type="text" id="itemName" ></td>
						<td>枚举值内容：<input type="text" id="itemValue" ></td>
						<td></td>
					</tr>
			  </table>
		  <td width="15%" align="left"><a href="#" id="query" class="ui-state-default ui-corner-all">查 询</a></td>
		</tr>
	</table>
	<form id="listForm">
		<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#cccccc">
			<tr style="text-align: center; background-image: url(../images/table_b.png); background-repeat: repeat-x; background-position: left center;">
				<th style="width: 18%">语言代码</th>
				<th style="width: 18%">枚举组类型</th>
				<th style="width: 18%">枚举值代码</th>
				<th style="width: 18%">枚举值显示名</th>
				<th style="width: 18%">枚举值内容</th>
				<th style="width: 18%">操作</th>
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
						<td width="30%" align="right">语言代码：</td>
						<td width="70%" align="left">
							<select  id="languageCodes_e" name="languageCode" reg="[^\s]" ></select>
						</td>
					</tr>
					<tr>
						<td align="right">枚举组类型：</td>
						<td align="left">
							<input type="text" name="groupCode" reg="[^\s]">
						</td>
					</tr>
					<tr>
						<td align="right">枚举值代码：</td>
						<td align="left">
							<input type="text" name="itemCode" reg="[^\s]">
						</td>
					</tr>
					<tr>
						<td align="right">枚举值显示名：</td>
						<td align="left">
							<input type="text" name="itemName" reg="[^\s]">
						</td>
					</tr>
					<tr>
						<td align="right">枚举值内容：</td>
						<td align="left">
							<input type="text" name="itemValue" reg="[^\s]">
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
						languageCode : $('#languageCodes_q').val(),
						groupCode : $('#groupCode').val(),
						itemCode : $('#itemCode').val(),
						itemName : $('#itemName').val(),
						itemValue : $('#itemValue').val(),
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
								html += '<td title="languageCode">' + objValue.languageCode + '</td>';
								html += '<td title="groupCode">' + objValue.groupCode + '</td>';
								html += '<td title="itemCode">' + objValue.itemCode + '</td>';
								html += '<td title="itemName">' + objValue.itemName + '</td>';
								html += '<td title="itemValue">' + objValue.itemValue + '</td>';
								html += '<td><a href="#s" onclick="obj.edit(this);">修改</a> | <a href="#s" onclick="obj.del(\'' + objValue.languageCode + '\',\'' + objValue.groupCode + '\',\'' + objValue.itemCode + '\');">删除</a></td>';
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
			opens.openDiv('#editFormDiv', '修改枚举信息', 500, 420, function(objs) {
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
		del : function(languageCode,groupCode,itemCode) {
			opens.confirms(function() {
				JqueryAjaxPost("del.do", {
					languageCode : languageCode,
					groupCode : groupCode,
					itemCode : itemCode
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
		//加载语言代码
		getLanguages : function() {
			JqueryAjaxGet("<%=path%>/language/languageDropDownList.do", {}, function() {
			}, function(data) {
				var list = data;
				var html = '<option value="">-----请选择-----</option>';
				if (list.length >= 1) {
					for ( var i = 0; i < list.length; i++) {
						var objValue = list[i];
						html += '<option value="'+objValue.languageCode+'">' + objValue.languageCode + '|'+objValue.chineseName + '</option>';
					}
				}
				$('#languageCodes_q,#languageCodes_e').html(html);
			}, function() {
			}, true, false);
		}
	};

	$(document).ready(function() {
		$("#add,#query").button();
		obj.getLanguages();

		obj.load(function() {
			page.GetPage();
		});
	});
	//添加
	$('#add').click(function() {
		//清空表单数据
		$('#editForm')[0].reset();
		opens.openDiv('#editFormDiv', '添加枚举信息', 500, 460, function(objs) {
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