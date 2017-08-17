<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>用户登录 - 互生系统全局配置管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="互生系统全局配置管理" >
<meta name="keywords" content="互生系统全局配置管理" >
<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="css/login.css">
</head>
<body>
<% String path = this.getServletContext().getContextPath(); 
   System.out.println("====================path="+path);
 %>
<div class="login-top"></div>
<div class="login-center">
	<form id="form-login">
	<table width="980" border="0" align="center" cellpadding="5" cellspacing="0">
		<tr>
			<td width="378" height="137" rowspan="5" align="right" valign="top"><img src="images/login_logo.png" width="378" height="179"></td>
			<td width="82" height="44" align="right" valign="bottom">&nbsp;</td>
			<td width="490" align="left" valign="bottom"><img src="images/login_t.png"  align="absmiddle"></td>
		</tr>
		<tr>
			<td height="29" align="right">账号</td>
			<td><input type="text" id="userName" name="userName" class="txt" data-rule-required="true" data-msg-required="请输入账号！"></td>
		</tr>
		<tr>
			<td height="28" align="right">密码</td>
			<td><input type="password" id="userPwd" name="userPwd" class="txt" data-rule-required="true" data-msg-required="请输入密码！"></td>
		</tr>
		<tr>
			<td height="29" align="right">&nbsp;</td>
			<td><input type="button" id="btn-submit" value="登录"><label id="login-msg"></label></td>
		</tr>
	</table>
	</form>
</div>
<div class="login-footer">©2013-2015 深圳市归一科技研发有限公司 粤ICP证B2-20130469号</div>
<script src="js/jquery-1.8.0.min.js" type="text/javascript"></script>
<script src="js/jquery.validate.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){$("#userName").focus();$("#form-login").keydown(function(e){$("#login-msg").html("");if(e.keyCode==13){$("#form-login").submit();return false;}}).validate({submitHandler:function(){$.post("<%=path%>/login/loginPost.do",$("#form-login").serialize(),function(data){if(data==1){window.location='<%=path%>/main/main.do';}else{$("#login-msg").html("用户名或密码错误！");}});}});$("#btn-submit").click(function(){$("#form-login").submit();});});
</script>
</body>
</html>