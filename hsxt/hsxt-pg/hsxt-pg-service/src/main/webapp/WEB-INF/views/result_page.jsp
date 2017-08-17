<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.gy.hsxt.pg.common.constant.Constant.SessionKey"%>
<%@ page import="com.gy.hsxt.pg.common.bean.RequestProxyData"%>
<%
	RequestProxyData requestProxyData = (RequestProxyData) request.getSession()
			.getAttribute(SessionKey.REQUEST_RESULT_DATA);
%>
<%=requestProxyData.getMessage()%>