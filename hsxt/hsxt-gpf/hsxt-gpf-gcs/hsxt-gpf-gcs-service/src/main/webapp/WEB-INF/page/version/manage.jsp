<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通知变更</title>
<script src="../js/jquery-1.8.0.min.js" type="text/javascript"></script>
<script src="../js/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="../js/jqAjax.js" type="text/javascript"></script>
<script src="../js/OpenMsg.js" type="text/javascript"></script>
<link rel="stylesheet" href="../js/jquery-ui/jquery-ui.css">
</head>
<body>
	<form id="listForm">
		<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#cccccc">
			<tr style="text-align: center; background-image: url(../images/table_b.png); background-repeat: repeat-x; background-position: left center;">
				<th style="width: 25%">表名</th>
				<th style="width: 25%">版本号</th>
				<th style="width: 25%">是否通知变更</th>
				<th style="width: 25%">操作</th>
			</tr>
			<tbody id="tbd">
			</tbody>
		</table>
	</form>
</body>
</html>

<script type="text/javascript">

	var opens = new onpenMsg();
	//创建对象
	var obj = {
		//加载数据
		load : function(callback) {
			JqueryAjaxGet(
					"managePost.do",
					{
					},
					function() {
					},
					function(data) {
						var list = data;
						var html = '';
						if (list.length >= 1) {
							for ( var i = 0; i < list.length; i++) {
								var objValue = list[i];
								html += '<tr style="background-color:#FFFFFF; text-align: center;">';
								html += '<td title="versionCode">' + objValue.versionCode + '</td>';
								html += '<td title="version">' + objValue.version + '</td>';
								html += '<td title="notifyFlag">' + objValue.notifyFlag + '</td>';
								html += '<td><a href="#s" onclick="obj.notifyChange(\'' + objValue.versionCode + '\');">通知变更</a></td>';
								html += '</tr>';
							}
						}
						$('#tbd').html(html);
						//返回回调方法
						callback();
					}, function() {
					}, true, true);
		},
		
		//通知变更
		notifyChange : function(versionCode) {
			opens.confirms(function() {
				JqueryAjaxPost("notifyChange.do", {
					versionCode : versionCode
				}, function() {
				}, function(data) {
					if (data) {
						opens.alerts('alert_suc', '您的操作成功！');
						obj.load(function() {
						});
					} else {
						opens.alerts('alert_err', '您的操作失败！');
					}
				}, function() {
				}, true, false);
			});
		}
	};

	$(document).ready(function() {
		obj.load(function() {
		});
	});
</script>