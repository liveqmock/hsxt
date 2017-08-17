<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>系统后台管理 - 互生系统全局配置管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="互生系统全局配置管理" >
<meta name="keywords" content="互生系统全局配置管理" >
<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon">
<link rel="stylesheet" href="../js/jquery-ui/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="../js/SimpleTree/tree_themes/SimpleTree.css"/>
<link rel="stylesheet" type="text/css" href="../css/main.css">
</head>
<body>
<div class="header">
	<div class="header-logo"><img src="../images/top.png" width="376" height="55"></div>
	<div class="header-info">
		<span>欢迎您，<%=request.getSession().getAttribute("userSessionName")%></span><b class="ln">|</b><a href="<%=this.getServletContext().getContextPath() %>/login/loginOut.do">注销</a>
	</div>
</div>
<div class="main">
	<div id="main-left" class="main-left">
		<div id="accordion"></div>
	</div>
	<div class="main-split"><a id="btn-switch" class="btn-switch" title="隐藏左侧菜单" href="javascript:;"></a></div>
	<div id="main-right" class="main-right">
		<div id="tabs">
			<ul>
				<li><a href="#tabs-1">主页</a></li>
			</ul>
			<div id="tabs-1"><div class="main-home-welcome"></div></div>
		</div>
	</div>
</div>
</body>
</html>
<script src="../js/jquery-1.8.0.min.js" type="text/javascript"></script>
<script src="../js/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="../js/SimpleTree/SimpleTree.js" type="text/javascript"></script>
<script src="../js/main.js" type="text/javascript"></script>