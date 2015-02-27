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
	<%--扩展验证框架的自定义函数--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/remittence_my.js"></script>
	<%--消息对话框框架--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.alerts.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.draggable.js"></script>
	<link href="${pageContext.request.contextPath}/css/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
  </head>
  
  <body>
 <center>
 	<h1>账户信息更改</h1>
  	${refreshLeftMenu}
  	教师编号-${sessionScope.professionTeacher.userId}
<hr/>
	<form id="infoForm" action="${pageContext.request.contextPath}/teacher/profession/account_professionSelf_update_info" method="post">
		姓名<input name="name" type="text" value="${sessionScope.professionTeacher.name}" reg="\S+" tip="不能为空！"/>
		&nbsp;&nbsp;
		电话<input name="telephone" type="text" value="${sessionScope.professionTeacher.telephone}" reg="[\d-]+" tip="由数字和-组成,不能为空！"/>
		<br/>
		性别<input type="radio" name="sex" value="1" ${sessionScope.professionTeacher.sex=='1'?'checked':''}/>男
			<input type="radio" name="sex" value="0" ${sessionScope.professionTeacher.sex=='0'?'checked':''}/>女
		<br/>
		<input id="updateInfo" type="submit" value="更新" onclick="clearPwd()"/>&nbsp;&nbsp;<input type="reset" value="重置"/>
	</form>
<hr/>
	<form id="pwdForm" action="${pageContext.request.contextPath}/teacher/profession/account_professionSelf_update_pwd" method="post">
		原密码<input id="original" name="original" type="password" reg="\w{5,}" tip="密码至少5位，由数字/字母/下划线组成！"/><br/>
		重置新密码<input id="freshF" name="freshF"  type="password" reg="\w{5,}" tip="密码至少5位，由数字/字母/下划线组成！"/>
		&nbsp;&nbsp;
		确认新密码<input id="freshS" name="freshS" type="password" reg="\w{5,}" tip="请确保与新密码输入一致！"/>
		&nbsp;&nbsp;
		<input id="updatePwd" type="submit" value="更新"/>&nbsp;&nbsp;<input type="reset" value="重置"/><br/>
	</form>
</center>
</body>
</html>
	<%@ include file="/jspf/showMessageContent.jspf" %>