<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币种管理</title>
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
			<td width="7%" align="center"><a href="#s" id="add"
				class="ui-state-default ui-corner-all">添加</a></td>
			<td width="93%">代码：<input type="text" id="currencyNos">
				&nbsp; 英文代码：<input type="text" id="currencyCodes"> &nbsp;
				中文名：<input type="text" id="currencyNameCns"> &nbsp; <a
				href="#s" id="sea" class="ui-state-default ui-corner-all">查 询</a></td>
		</tr>
	</table>
	<form id="forms">
		<table width="100%" border="0" cellpadding="5" cellspacing="1"
			bgcolor="#CCCCCC">
			<tr
				style="text-align: center; background-image: url(../images/table_b.png); background-repeat: repeat-x; background-position: left center;">
				<th style="width: 7%">代码</th>
				<th style="width: 10%">英文代码</th>
				<th style="width: 15%">中文名</th>
				<th style="width: 8%">币种符号</th>
				<th style="width: 8%">币种精度</th>
				<th style="width: 8%">货币单位</th>
				<th style="width: 8%">货币转换比率</th>
				<th style="width: 9%">版本号</th>
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
						<td align="right">币种代码：</td>
						<td align="left"><input reg="^[0-9]+$" type="text"
							name="currencyNo">数字</td>
					</tr>
					<tr>
						<td width="25%" align="right">英文代码：</td>
						<td width="73%" align="left"><input reg="^[A-Za-z]+$"
							type="text" name="currencyCode">字母</td>
					</tr>
					<tr>
						<td width="25%" align="right">中文名：</td>
						<td width="73%" align="left"><input reg="[^\s]" type="text"
							name="currencyNameCn"></td>
					</tr>
					<tr>
						<td width="25%" align="right">币种符号：</td>
						<td width="73%" align="left"><input reg="[^\s]" maxlength="1"
							type="text" name="currencySymbol"></td>
					</tr>
					<tr>
						<td width="25%" align="right">币种精度：</td>
						<td width="73%" align="left"><input maxlength="6" reg="[^\s]"
							type="text" name="precisionNum"></td>
					</tr>
					<tr>
						<td width="25%" align="right">货币单位：</td>
						<td width="73%" align="left"><input maxlength="10"
							reg="[^\s]" type="text" name="unitCode"></td>
					</tr>
					<tr>
						<td width="25%" align="right">货币转换比率：</td>
						<td width="73%" align="left"><input maxlength="6" reg="[^\s]"
							type="text" name="exchangeRate"></td>
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
						currencyNo : $('#currencyNos').val(),
						currencyCode : $('#currencyCodes').val(),
						currencyNameCn : $('#currencyNameCns').val().replace(/%/g, "/%").replace(/_/g, "/_"),
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
								html += '<td title="currencyNo">'
										+ lists.currencyNo + '</td>';
								html += '<td title="currencyCode">'
										+ lists.currencyCode + '</td>';
								html += '<td title="currencyNameCn">'
										+ lists.currencyNameCn + '</td>';
								html += '<td title="currencySymbol">'
										+ lists.currencySymbol + '</td>';
								html += '<td title="precisionNum">'
										+ lists.precisionNum + '</td>';
								html += '<td title="unitCode">'
										+ lists.unitCode + '</td>';
								html += '<td title="exchangeRate">'
										+ lists.exchangeRate + '</td>';
								html += '<td>' + lists.version + '</td>';
								html += '<td><a href="#s" onclick="obj.edit(this);">修改</a> | <a href="#s" onclick="obj.del(\''
										+ lists.currencyNo
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
			$("#forms2 [name='currencyNo']").attr("readonly", true);
			parent.find("td").each(function(k) {
				var t = $(this);
				var v = t.text();
				if (t.attr("title") != null) {
					$(formid).find("[name='" + t.attr("title") + "']").val(v);
				}
			});
			//修改币种
			opens.openDiv('#divs', '修改币种', 500, 390, function(objs) {
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
				JqueryAjaxPost("Upadte.do", $("#forms2").serialize(),
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
						},function() {
						}, true, false);
			});
		},//删除某条记录
		del : function(currencyNo) {
			opens.confirms(function() {
				if (currencyNo == null || currencyNo == "") {
					opens.alerts('alert_err', '币种不能为空！');
					return;
				}
				JqueryAjaxPost("del.do", {
					currencyNo : currencyNo
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
				},function() {
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
		$("#forms2 [name='currencyNo']").attr("readonly", false);
		opens.openDiv('#divs', '添加币种', 500, 390, function(objs) {
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
			},function() {
			}, true, false);

		});
	});
	//查询
	$('#sea').click(function() {
		pageNo = 1;
		obj.load(function() {
			page.GetPage();
		});
	});
</script>