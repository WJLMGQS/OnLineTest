<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%--使用jquery必须导入--%>
 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.3.2.min.js"></script>
	<%--消息对话框框架--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.alerts.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.draggable.js"></script>
	<link href="${pageContext.request.contextPath}/css/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<%--显示确认对话框框（基于框架实现）--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/showConfirmDialog.js"></script>
	<%--验证框架框架--%>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easy_validator.pack.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.bgiframe.min.js"></script>
	<link  href="${pageContext.request.contextPath}/css/validate_failed.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		#login-body{
			background-image: url('${pageContext.request.contextPath}/images/bg-login.gif');
			padding: 0 0px;
    		text-align: center;
    		width: 100%;
		}
		#login-top{
			background-image: url('${pageContext.request.contextPath}/images/bg-login-top.png');
			padding: 45px 0 0 0;
    		text-align: center;
    		width: 100%;
    		margin-top: 0px;
		}
		#login-content{
			padding-top:40px;
			text-align: center;
		}
		.buttonGroup{
			width:240px;
			display: inline-block;
			padding-left: 25px;
		}
		.button {
		  background: url("${pageContext.request.contextPath}/images/bg-button-green.gif") ;
		  border: 1px solid #459300 !important;
		  color: #FFFFFF !important;
		  cursor: pointer;
		  display: inline-block;
		  font-family: Verdana,Arial,sans-serif;
		  font-size: 11px !important;
		  padding: 4px 7px !important;
		  margin-right: 10px;
		  margin-left:15px;
		}

		label{
			 color: #FFFFFF;
			 margin-right: 15px;
		}
		.text-input{
			width: 190px;
		}
	</style>
  </head>
  <body id="login-body" >
  		<div id="login-top">
			<h1><label>网&nbsp;络&nbsp;考&nbsp;试&nbsp;系&nbsp;统</label></h1>
			<h1><label>教师端</label></h1>
		</div>
		<div id="login-content">
			<form name="loginForm" action="${pageContext.request.contextPath}/teacherLogin/redirect2Teacher" method="post">
				<p><label>账号</label><input class="text-input" type="text" name="userId" reg="\d{11}" tip="账号由11位数字组成！"/></p>
				<div class="clear"></div>
				<p><label>密码</label><input class="text-input" type="password" name="password" reg="\w{5,}" tip="密码至少5位，由数字/字母/下划线组成！"/></p>
				<div class="clear"></div>
				<p class="buttonGroup"><label>权限</label><input type="radio" name="power" value="0" reg="[0|1]" tip="管理员权限！"/><label>管理员</label><input type="radio" name="power" value="1" reg="[0|1]" tip="教师权限！" checked="checked"/><label>教师</label></p>
			 	<div class="clear"></div>
			 	<p class="buttonGroup"><input class="button" type="submit" value="提交" /> <input class="button" type="reset" value="重置"/></p>
			</form>
		</div>
 </body>
</html>
<%@ include file="/jspf/showMessageContent.jspf" %>