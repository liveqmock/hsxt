<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.gy.hsxt.pg.common.constant.Constant.SessionKey"%>
<%@ page import="com.gy.hsxt.pg.common.bean.RequestProxyData"%>
<%
	RequestProxyData requestProxyData = (RequestProxyData) request.getSession()
			.getAttribute(SessionKey.REQUEST_PROXY_DATA);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
.font_style {
	font-size: 12px;
	font-color: #8FBC8F;
}
</style>
<title><%=requestProxyData.getMessage()%></title>
</head>
<body>
	<p align="left">
	    <%=requestProxyData.getFormHtml()%>
		<span style=""><%=requestProxyData.getMessage()%></span>
	</p>
</body>
</html>