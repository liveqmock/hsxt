<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>平台代码管理</title>
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
			<td width="13%" align="left"><a href="#s" id="add"
				class="ui-state-default ui-corner-all">添加</a></td>
			<td width="93%">代码：<input type="text" id="platNos">
				&nbsp; &nbsp; 中文名：<input type="text" id="platNameCns">
				&nbsp; <a href="#s" id="sea" class="ui-state-default ui-corner-all">查
					询</a></td>
		</tr>
	</table>
	<form id="forms">
		<table width="100%" border="0" cellpadding="5" cellspacing="1"
			bgcolor="#CCCCCC">
			<tr
				style="text-align: center; background-image: url(../images/table_b.png); background-repeat: repeat-x; background-position: left center;">
				<th style="width: 8%">平台代码</th>
				<th style="width: 10%">币种代码</th>
				<th style="width: 15%">中文名</th>
				<th style="width: 10%">语言代码</th>
				<th style="width: 10%">国家代码</th>
				<th style="width: 10%">综合前置IP</th>
				<th style="width: 10%">综合前置端口</th>
				<th style="width: 10%">时间偏移量</th>
				<th style="width: 10%">版本号</th>
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
						<td width="35%" align="right">平台代码：</td>
						<td width="65%" align="left"><input reg="^[0-9]+$" type="text"
							name="platNo">数字</td>
					</tr>
					<tr>
						<td align="right">币种代码：</td>
						<td align="left"><select reg="[^\s]"
							id="currencyNo" name="currencyNo">
						</select></td>
					</tr>
					<tr>
						<td align="right">语言代码：</td>
						<td align="left"><select reg="[^\s]"
							id="languageCode" name="languageCode">
						</select></td>
					</tr>
					<tr>
						<td align="right">国家代码：</td>
						<td align="left"><select reg="[^\s]"
							id="countryNo" name="countryNo">
						</select></td>
					</tr>
					<tr>
						<td align="right">中文名：</td>
						<td align="left"><input reg="[^\s]" type="text"
							name="platNameCn"></td>
					</tr>
					<tr>
						<td align="right">综合前置IP：</td>
						<td align="left"><input
							reg="^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])$"
							maxlength="100" type="text" name="platEntryIP"></td>
					</tr>
					<tr>
						<td align="right">综合前置端口：</td>
						<td align="left"><input reg="^[0-9]+$"
							maxlength="100" type="text" name="platEntryPort"></td>
					</tr>
					<tr>
						<td align="right">时间偏移量：</td>
						<td align="left"><input maxlength="5" reg="^[0-9]+([.][0-9]+){0,1}$"
							type="text" name="timeOffset"></td>
					</tr>
					<tr>
						<td align="right">技术支持方地址：</td>
						<td align="left"><input maxlength="200" 
							type="text" name="techSupportAddress"></td>
					</tr>
					<tr>
						<td align="right">技术支持方联系电话：</td>
						<td align="left"><input maxlength="60" 
							type="text" name="techSupportPhone"></td>
					</tr>
					<tr>
						<td align="right">技术支持方授权代表：</td>
						<td align="left"><input maxlength="64" 
							type="text" name="techSupportContact"></td>
					</tr>
					<tr>
						<td align="right">技术支持方名称：</td>
						<td align="left"><input maxlength="240" 
							type="text" name="techSupportCorpName"></td>
					</tr>
					<tr>
						<td align="right">合同证书验证地址：</td>
						<td align="left"><input maxlength="250" 
							type="text" name="contractVerifyAddr"></td>
					</tr>
					<tr>
						<td align="right">平台官网后台地址：</td>
						<td align="left"><input maxlength="250" 
							type="text" name="officialWebBack"></td>
					</tr>
					<tr>
						<td align="right">平台统一登录后台地址：</td>
						<td align="left"><input maxlength="250" 
							type="text" name="ulsWebBack"></td>
					</tr>
					<tr>
						<td align="right">互生地区平台门户：</td>
						<td align="left"><input maxlength="250" 
							type="text" name="platWebSite"></td>
					</tr>
					<tr>
						<td align="right">互生管理公司门户：</td>
						<td align="left"><input maxlength="250" 
							type="text" name="manageWebSite"></td>
					</tr>
					<tr>
						<td align="right">互生服务公司门户：</td>
						<td align="left"><input maxlength="250" 
							type="text" name="serviceWebSite"></td>
					</tr>
					<tr>
						<td align="right">互生企业门户：</td>
						<td align="left"><input maxlength="250" 
							type="text" name="companyWebSite"></td>
					</tr>
					<tr>
						<td align="right">互生消费者门户：</td>
						<td align="left"><input maxlength="250" 
							type="text" name="personWebSite"></td>
					</tr>
					<tr>
						<td align="right">互生支付结果跳转地址：</td>
						<td align="left"><input maxlength="250" 
							type="text" name="webPayJumpUrl"></td>
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
						platNo : $('#platNos').val(),
						platCode : $('#platCodes').val(),
						platNameCn : $('#platNameCns').val().replace(/%/g, "/%").replace(/_/g, "/_"),
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
								html += '<td title="platNo">' + lists.platNo
										+ '</td>';
								html += '<td title="currencyNo">'
										+ lists.currencyNo + '</td>';
								html += '<td title="platNameCn">'
										+ lists.platNameCn + '</td>';
								html += '<td title="languageCode">'
										+ lists.languageCode + '</td>';
								html += '<td title="countryNo">'
										+ lists.countryNo + '</td>';
								html += '<td title="platEntryIP">'
										+ lists.platEntryIP + '</td>';
								html += '<td title="platEntryPort">'
										+ lists.platEntryPort + '</td>';
								html += '<td title="timeOffset">'
										+ lists.timeOffset + '</td>';
								html += '<td>' + lists.version + '</td>';
								html += '<td style="display:none"  title="techSupportAddress">'
									+ lists.techSupportAddress + '</td>';
								html += '<td style="display:none"  title="techSupportPhone">'
									+ lists.techSupportPhone + '</td>';
								html += '<td style="display:none"  title="techSupportContact">'
									+ lists.techSupportContact + '</td>';
								html += '<td style="display:none"  title="techSupportCorpName">'
									+ lists.techSupportCorpName + '</td>';
								html += '<td style="display:none"  title="contractVerifyAddr">'
									+ lists.contractVerifyAddr + '</td>';
								html += '<td style="display:none"  title="officialWebBack">'
									+ lists.officialWebBack + '</td>';
								html += '<td style="display:none"  title="ulsWebBack">'
									+ lists.ulsWebBack + '</td>';
								html += '<td style="display:none"  title="platWebSite">'
									+ lists.platWebSite + '</td>';
								html += '<td style="display:none"  title="manageWebSite">'
									+ lists.manageWebSite + '</td>';
								html += '<td style="display:none"  title="serviceWebSite">'
									+ lists.serviceWebSite + '</td>';
								html += '<td style="display:none"  title="companyWebSite">'
									+ lists.companyWebSite + '</td>';
								html += '<td style="display:none"  title="personWebSite">'
									+ lists.personWebSite + '</td>';
								html += '<td style="display:none"  title="webPayJumpUrl">'
									+ lists.webPayJumpUrl + '</td>';
								html += '<td><a href="#s" onclick="obj.edit(this);">修改</a> | <a href="#s" onclick="obj.del(\''
										+ lists.platNo + '\');">删除</a></td>';
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
			$("#forms2 [name='platNo']").attr("readonly", true);
			parent.find("td").each(function(k) {
				var t = $(this);
				var v = t.text();
				if (t.attr("title") != null) {
					$(formid).find("[name='" + t.attr("title") + "']").val(v);
				}
			});
			//修改平台代码
			opens.openDiv('#divs', '修改平台代码', 500, 460, function(objs) {
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
		del : function(platNo) {
			opens.confirms(function() {
				if (platNo == null || platNo == "") {
					opens.alerts('alert_err', '平台代码不能为空！');
					return;
				}
				JqueryAjaxPost("del.do", {
					platNo : platNo
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
					}, false, false);
		},//加载币种代码
		GetcurrencyNo : function() {
			JqueryAjaxGet("<%=path%>/currency/getCurrencyDropdownmenu.do", {},
					function() {
					}, function(data) {
						var list = data;
						var html = '<option value="">请选择</option>';
						if (list.length >= 1) {
							for ( var i = 0; i < list.length; i++) {
								var lists = list[i];
								html += '<option value="'+lists.currencyNo+'">'
										+ lists.currencyNameCn + '</option>';
							}
						}
						$('#currencyNo,#currencyNos').html(html);
					}, function() {
					}, false, false);
		},//加载语言代码
		GetlanguageNo : function() {
			JqueryAjaxGet(
					"<%=path%>/language/languageDropDownList.do",
					{},
					function() {
					},
					function(data) {
						var list = data;
						var html = '<option value="">请选择</option>';
						if (list.length >= 1) {
							for ( var i = 0; i < list.length; i++) {
								var lists = list[i];
								html += '<option value="'+lists.languageCode+'">'
										+ lists.chineseName + '</option>';
							}
						}
						$('#languageCode,#languageCodes').html(html);
					}, function() {
					}, false, false);
		}
	};

	$(document).ready(function() {
		$("#add,#sea").button();
		obj.GetCountryNo();
		obj.GetcurrencyNo();
		obj.GetlanguageNo();
		//重新加载数据
		obj.load(function() {
			page.GetPage();
		});
	});
	//添加
	$('#add').click(function() {
		//清空表单数据
		$('#forms2')[0].reset();
		$("#forms2 [name='platNo']").attr("readonly", false);
		opens.openDiv('#divs', '添加平台代码', 500, 460, function(objs) {
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