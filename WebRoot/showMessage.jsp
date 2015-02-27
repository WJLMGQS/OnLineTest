<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>消息页面</title>
    <%--设置窗体右边显示界面的基本样式--%>  
 	<link media="screen" type="text/css" href="${pageContext.request.contextPath}/css/showRightStyle.css"	rel="stylesheet" />
  </head>
  <body>
    <%
    	String showMessage = (String)request.getAttribute("showMessage");
    	if(showMessage==null){
    	}else{
    		%>
    		<%=showMessage%>
    		<%
    	}
    %>
  </body>
</html>
