<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>城市代码管理</title>
<script src="../js/jquery-1.8.0.min.js" type="text/javascript"></script>
<script src="../js/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="../js/jqAjax.js" type="text/javascript"></script>
<script src="../js/OpenMsg.js" type="text/javascript"></script>
<link rel="stylesheet" href="../js/jquery-ui/jquery-ui.css">
</head>
<%String path = this.getServletContext().getContextPath(); %>
<body>
	<table width="100%" border="0" cellpadding="8" cellspacing="0">
		<tr
			style="text-align: left; background-image: url(../images/table_b.png); background-repeat: repeat-x; background-position: left center;">
			<td width="7%" align="center"><a href="#" id="add" class="ui-state-default ui-corner-all">添加</a></td>
			<td width="93%">代码：<input type="text" id="cityNos">
				&nbsp; 国家：<select id="countryNos">
			</select> &nbsp; 省份：<select id="provinceNos">
			</select> &nbsp; 中文名：<input type="text" id="cityNameCns"> &nbsp; <a href="#" id="sea" class="ui-state-default ui-corner-all">查 询</a></td>
		</tr>
	</table>
	<form id="forms">
		<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#cccccc">
			<tr style="text-align: center; background-image: url(../images/table_b.png); background-repeat: repeat-x; background-position: left center;">
				<th style="width: 8%">代码</th>
				<th style="width: 12%">省份</th>
				<th style="width: 13%">中文名</th>
				<th style="width: 12%">名称</th>
				<th style="width: 22%">全称</th>
				<th style="width: 10%">电话区号</th>
				<th style="width: 10%">邮政编号</th>
				<th style="width: 5%">版本号</th>
				<th style="width: 8%">操作</th>
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
						<td align="right">城市代码：</td>
						<td align="left"><input reg="^[0-9]+$" type="text"
							name="cityNo">数字（注：添加后不能修改）</td>
					</tr>
					<tr>
						<td width="25%" align="right">国家代码：</td>
						<td width="73%" align="left"><select reg="[^\s]"
							id="countryNo" name="countryNo">
						</select></td>
					</tr>
					<tr>
						<td width="25%" align="right">省份：</td>
						<td width="73%" align="left"><select reg="[^\s]"
							id="provinceNo" name="provinceNo">
						</select></td>
					</tr>
					<tr>
						<td width="25%" align="right">中文名：</td>
						<td width="73%" align="left"><input reg="[^\s]" type="text"
							name="cityNameCn"></td>
					</tr>
					<tr>
						<td width="25%" align="right">名称：</td>
						<td width="73%" align="left"><input reg="[^\s]" type="text"
							name="cityName" id="cityName"></td>
					</tr>
					<tr>
						<td width="25%" align="right">全称：</td>
						<td width="73%" align="left"><input style="width: 299px"
							readonly="readonly" type="text" id="cityFullName"
							name="cityFullName"></td>
					</tr>
					<tr>
						<td width="25%" align="right">电话区号：</td>
						<td width="73%" align="left"><input reg="[^\s]" type="text"
							name="phonePrefix"></td>
					</tr>
					<tr>
						<td width="25%" align="right">邮政编号：</td>
						<td width="73%" align="left"><input reg="[^\s]" type="text" name="postCode"></td>
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
						cityNo : $('#cityNos').val(),
						countryNo : $('#countryNos').val(),
						provinceNo : $('#provinceNos').val(),
						cityNameCn : $('#cityNameCns').val().replace(/%/g, "/%").replace(/_/g, "/_"),
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
								html += '<td title="cityNo">' + lists.cityNo
										+ '</td>';
								html += '<td title="provinceNo">'
										+ lists.provinceNo + '</td>';
								html += '<td title="cityNameCn">'
										+ lists.cityNameCn + '</td>';
								html += '<td title="cityName">'
										+ lists.cityName + '</td>';
								html += '<td title="cityFullName">'
										+ lists.cityFullName + '</td>';
								html += '<td title="phonePrefix">'
										+ lists.phonePrefix + '</td>';
								html += '<td title="postCode">'
										+ lists.postCode + '</td>';
								html += '<td>' + lists.version + '</td>';
								html += '<td><a href="#s" onclick="obj.edit(this);">修改</a> | <a href="#s" onclick="obj.del(\''
										+ lists.cityNo + '\');">删除</a></td>';
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
			parent.find("td").each(function(k) {
				var t = $(this);
				var v = t.text();
				if (t.attr("title") != null) {
					$(formid).find("[name='" + t.attr("title") + "']").val(v);
				}
			});
			var cityFullNames = $('#cityFullName').val().split('.');
			$('#countryNo option:contains(' + cityFullNames[0] + ')').each(
					function() {
						if ($(this).text() == cityFullNames[0]) {
							$(this).attr('selected', true);
							return false;
						}
					});
			obj.GetProvinceNo($('#countryNo').val());
			$('#provinceNo option:contains(' + cityFullNames[1] + ')').each(
					function() {
						if ($(this).text() == cityFullNames[1]) {
							$(this).attr('selected', true);
							return false;
						}
					});
			//修改城市代码
			opens.openDiv('#divs', '修改城市代码', 500, 460, function(objs) {
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
						},function(){}, true, false);
			});
		},//删除某条记录
		del : function(cityNo) {
			opens.confirms(function() {
				if (cityNo == null || cityNo == "") {
					opens.alerts('alert_err', '城市代码不能为空！');
					return;
				}
				JqueryAjaxPost("del.do", {
					cityNo : cityNo
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
				},function(){}, true, false);
			});
		},//加载国家代码
		GetCountryNo : function() {
			JqueryAjaxGet("<%=path%>/country/getCountryDropdownmenu.do", {},
					function() {
					}, function(data) {
						var list = data;
						var html = '<option value="">请选择</option>';
						if (list.length >= 1) {
							for ( var i = 0; i < list.length; i++) {
								var lists = list[i];
								html += '<option value="'+lists.countryNo+'">'
										+ lists.countryNameCn + '</option>';
							}
						}
						$('#countryNo,#countryNos').html(html);
					}, function() {
					}, true, false);
		},//加载省份代码
		GetProvinceNo : function(countryNo) {
			JqueryAjaxGet("<%=path%>/province/getProvinceDropdownmenu.do", {
				countryNo : countryNo
			}, function() {
			}, function(data) {
				var list = data;
				var html = '<option value="">请选择</option>';
				if (list.length >= 1) {
					for ( var i = 0; i < list.length; i++) {
						var lists = list[i];
						html += '<option value="'+lists.provinceNo+'">'
								+ lists.provinceNameCn + '</option>';
					}
				}
				$('#provinceNo,#provinceNos').html(html);
			}, function() {
			}, false, false);
		}
	};

	$(document).ready(function() {
		$("#add,#sea").button();
		obj.GetCountryNo();
		//重新加载数据
		obj.load(function() {
			page.GetPage();
		});
	});
	//添加
	$('#add').click(function() {
		//清空表单数据
		$('#forms2')[0].reset();
		$('#countryNo').val('');
		$('#provinceNo').html('');
		opens.openDiv('#divs', '添加城市代码', 500, 460, function(objs) {
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
			},function() {}, true, false);
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
	//选择国家时
	$('#countryNo,#countryNos').change(function() {
		obj.GetProvinceNo($(this).val());
	});
	$('#cityName,#countryNo,#provinceNo').blur(
			function() {
				var countryNo = $('#countryNo').find("option:selected").text();
				var provinceNo = $('#provinceNo').find("option:selected")
						.text();
				$('#cityFullName').val(
						countryNo.substring(countryNo.indexOf('|') + 1,
								countryNo.length)
								+ '.'
								+ provinceNo.substring(
										provinceNo.indexOf('|') + 1,
										provinceNo.length)
								+ '.'
								+ $('#cityName').val());
			});
</script>