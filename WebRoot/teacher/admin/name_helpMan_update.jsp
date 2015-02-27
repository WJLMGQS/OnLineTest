<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="org.wjlmgqs.daomain.Teacher"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  <%--设置窗体右边显示界面的基本样式--%>  
 	<link media="screen" type="text/css" href="${pageContext.request.contextPath}/css/showRightStyle.css"	rel="stylesheet" />
	<%--使用jquery必须导入--%>
 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.3.2.min.js"></script>
	<%--验证框架框架--%>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easy_validator.pack.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.bgiframe.min.js"></script>
	<link  href="${pageContext.request.contextPath}/css/validate_failed.css" rel="stylesheet" type="text/css" />
	<%--消息对话框框架--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.alerts.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.draggable.js"></script>
	<link href="${pageContext.request.contextPath}/css/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
  </head>
  <body>
 <center>
 	<h1>辅导员信息更改</h1>
 	<a href="${pageContext.request.contextPath}/teacher/admin/name_helpMan">后退</a>
<hr/>
	<form id="infoForm" action="${pageContext.request.contextPath}/teacher/admin/name_helpMan_update_info?helpId=${helpMan.id}" method="post">
		辅导员代码<input name="code" type="text" value="${helpMan.code}" reg="\S+" tip="不能为空！"/>
		&nbsp;&nbsp;
		所属学院<select name="departmentId" size="1" tip="请选择学院" reg="[1-9]+">
				<c:forEach items="${requestScope.departments}" var="department" step="1">
					<option value="${department.id}" ${department.id==helpMan.department.id?"selected":''}>${department.code}</option>
				</c:forEach>
			   </select>
		<br/>
		<input id="updateInfo" type="submit" value="更新"/>&nbsp;&nbsp;<input type="reset" value="重置"/>
	</form>
<hr/>
 </center>
 </body>
</html>
	<%@ include file="/jspf/showMessageContent.jspf" %>