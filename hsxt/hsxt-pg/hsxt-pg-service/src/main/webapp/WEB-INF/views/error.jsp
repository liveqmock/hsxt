<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="com.gy.hsxt.pg.common.constant.Constant.SessionKey"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
.font_style {
	font-size: 14px;
	color: #FF5151;
}
</style>
<title></title>
</head>
<body class="font_style">
	<p align="left">
		<span class="font_style"><%=(String) request.getSession().getAttribute(
					SessionKey.ILLEGAL_REQ_ERROR)%></span>
	</p>
</body>
</html>