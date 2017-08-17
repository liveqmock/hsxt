<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>省份代码管理</title>
<script src="../js/jquery-1.8.0.min.js" type="text/javascript"></script>
<script src="../js/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="../js/jqAjax.js" type="text/javascript"></script>
<script src="../js/OpenMsg.js" type="text/javascript"></script>
<link rel="stylesheet" href="../js/jquery-ui/jquery-ui.css">
</head>
<%String path = this.getServletContext().getContextPath(); %>
<body>
	<table width="100%" border="0" cellpadding="8" cellspacing="0">
		<tr style="text-align: left;background-image: url(../images/table_b.png);background-repeat: repeat-x;background-position: left center;">
			<td width="7%" align="center"><a href="#s" id="add" class="ui-state-default ui-corner-all">添加</a></td>
			<td width="93%">代码：<input type="text" id="provinceNos">
				&nbsp; 国家代码：<select id="countryNos" name="countryNos">
			</select> &nbsp; 名称：<input type="text" id="provinceNameCns"> &nbsp; <a href="#s" id="sea" class="ui-state-default ui-corner-all">查 询</a></td>
		</tr>
	</table>
	<form id="forms">
		<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#CCCCCC">
			<tr style="text-align: center;background-image: url(../images/table_b.png);background-repeat: repeat-x;background-position: left center;">
				<th style="width: 8%">省份代码</th>
				<th style="width: 8%">国家代码</th>
				<th style="width: 10%">国家名称</th>
				<th style="width: 21%">中文名</th>
				<th style="width: 25%">名称</th>
				<th style="width: 8%">是否直辖市</th>
				<th style="width: 10%">版本号</th>
				<th style="width: 10%">操作</th>
			</tr>
			<tbody id="tbd">
			</tbody>
			<jsp:include page="../page.jsp"/>
		</table>
	</form>

	<div id="divs" style="width: 100%; display: none;">
		<form id="forms2">
			<table width="100%" border="0" cellpadding="5" cellspacing="1">
				<tbody>
					<tr>
						<td align="right">省份代码：</td>
						<td align="left"><input reg="^[0-9]+$" type="text"
							name="provinceNo">数字（注：添加后不能修改）</td>
					</tr>
					<tr>
						<td width="25%" align="right">国家代码：</td>
						<td width="73%" align="left"><select reg="[^\s]"
							id="countryNo" name="countryNo">
						</select></td>
					</tr>
					<tr>
						<td width="25%" align="right">中文名：</td>
						<td width="73%" align="left"><input reg="[^\s]" type="text"
							name="provinceNameCn"></td>
					</tr>
					<tr>
						<td width="25%" align="right">名称：</td>
						<td width="73%" align="left"><input reg="[^\s]" type="text"
							name="provinceName"></td>
					</tr>
					<tr>
						<td width="25%" align="right">是否直辖市：</td>
						<td width="73%" align="left">是<input type="radio" value="1"
							name="directedCity"> &nbsp;&nbsp; 否<input type="radio"
							value="0" name="directedCity">
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
						provinceNo : $('#provinceNos').val(),
						countryNo : $('#countryNos').val(),
						provinceNameCn : $('#provinceNameCns').val().replace(/%/g, "/%").replace(/_/g, "/_"),
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
								html += '<td title="provinceNo">'
										+ lists.provinceNo + '</td>';
								html += '<td title="countryNo">'
										+ lists.countryNo + '</td>';
								html += '<td>'
										+ ReturnSelected('#countryNos',
												lists.countryNo) + '</td>';
								html += '<td title="provinceNameCn">'
										+ lists.provinceNameCn + '</td>';
								html += '<td title="provinceName">'
										+ lists.provinceName + '</td>';
								html += '<td title="directedCity">'
										+ (lists.directedCity == 1 ? "是" : "否")
										+ '</td>';

								html += '<td>' + lists.version + '</td>';
								html += '<td><a href="#s" onclick="obj.edit(this);">修改</a> | <a href="#s" onclick="obj.del(\''
										+ lists.provinceNo
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
					}, true, true);
		},//点击修改
		edit : function(ths) {

			var th = $(ths);
			var parent = th.parent().parent();
			var formid = $("#forms2");
			$("#forms2 [name='provinceNo']").attr("readonly", true);
			parent.find("td").each(
					function(k) {
						var t = $(this);
						var v = t.text();
						if (t.attr("title") != null) {
							var inputs = $(formid).find(
									"[name='" + t.attr("title") + "']");
							if (inputs.attr('type') == "radio") {
								v = (v == "是" ? "1" : "0");
								SetCheckbox(inputs, v);
							} else
								inputs.val(v);
						}
					});
			//修改省份代码
			opens.openDiv('#divs', '修改省份代码', 500, 320, function(objs) {
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
								opens.alertscb('alert_suc', '您的操作成功！',
										function() {
											//清空表单数据
											$('#forms2')[0].reset();
											objs.dialog("close");
											//重新加载数据
											obj.load(function(){
												
											});
										});
							} else
								opens.alerts('alert_err', '您的操作失败！');
						}, function() {
						}, true, true);
			});
		},//删除某条记录
		del : function(provinceNo) {
			opens.confirms(function() {
				if (provinceNo == null || provinceNo == "") {
					opens.alerts('alert_err', '省份代码不能为空！');
					return;
				}
				JqueryAjaxPost("del.do", {
					provinceNo : provinceNo
				}, function() {
				}, function(data) {
					if (data == 1) {
						opens.alerts('alert_suc', '您的操作成功！');
						//重新加载数据
						pageNo = 1;
						obj.load(function(){
							page.GetPage();
						});
					} else
						opens.alerts('alert_err', '您的操作失败！');
				}, function() {
				}, true, true);
			});
		},//加载国家代码
		GetCountryNo : function() {
			JqueryAjaxGet("<%=path%>/country/getCountryDropdownmenu.do", {}, function() {
			}, function(data) {
				var list = data;
				var html = '<option value="">请选择</option>';
				if (list.length >= 1) {
					for ( var i = 0; i < list.length; i++) {
						var lists = list[i];
						html += '<option value="'+lists.countryNo+'">'+ lists.countryNameCn
								+ '</option>';
					}
				}
				$('#countryNo,#countryNos').html(html);
			}, function() {
			}, false, false);
		},
		GetPage : function() {
			//分页
			$('#pagination').jqPagination({
				link_string : '/?page={page_number}',
				max_page : pagecount,
				paged : function(page) {
					pageNo = page;
					obj.load(function() {
					});
				}
			});
		}
	};

	$(document).ready(function() {
		$("#add,#sea").button();
		//加载国家代码
		obj.GetCountryNo();
		obj.load(function(){
			page.GetPage();
		});
	});
	//添加
	$('#add').click(function() {
		//清空表单数据
		$('#forms2')[0].reset();
		$("#forms2 [name='provinceNo']").attr("readonly", false);
		opens.openDiv('#divs', '添加省份代码', 500, 320, function(objs) {
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
					obj.load(function(){
						page.GetPage();
					});
					//清空表单数据
					$('#forms2')[0].reset();
				} else
					opens.alerts('alert_err', '您的操作失败！');
			}, function() {
			}, true, true);

		});
	});
	//查询
	$('#sea').click(function() {
		pageNo = 1;
		obj.load(function(){
			page.GetPage();
		});
	});
</script>