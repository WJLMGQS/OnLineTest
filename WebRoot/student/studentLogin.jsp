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
	<link href="${pageContext.request.contextPath}/css/validate_failed.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		#login-body{
			background:url('${pageContext.request.contextPath}/images/zving/loginimgbg.jpg') no-repeat center center; height:100%;
			padding: 0 0px;
    		text-align: center;
    		width: 100%;
		}
		#login-top{
			padding: 45px 0 0 0;
    		text-align: center;
    		width: 100%;
    		margin-top: -15px;
    	
		}
		#login-content{
			padding-top:75px;
			text-align: center;
		}
		.buttonGroup{
			width:360px;
			display: inline-block;
			padding-left: 25px;
		}
		.button {
		  background: url("${pageContext.request.contextPath}/images/zving/loginbtn.gif") ;
		  border: 1px solid #459300 !important;
		  color: #FFFFFF !important;
		  cursor: pointer;
		  display: inline-block;
		  font-family: Verdana,Arial,sans-serif;
		  font-size: 13px !important;
		  padding: 4px 7px !important;
		  margin-right: 10px;
		  margin-left:15px;
		}

		span{
			 color:black;
			 margin-right: 15px;
			 margin-top: 0px;
		}
		.text-input{
			width: 190px;
		}
	</style>
  </head>
  <body id="login-body">
  		<div id="login-top">
			<h1><span>网&nbsp;络&nbsp;考试&nbsp;系&nbsp;统</span></h1>
		</div>
		<div id="login-content">
			<h1><span>学生端</span></h1>
			<form name="loginForm" action="${pageContext.request.contextPath}/studentLogin/redirect2Student" method="post">
				<p><span>账号</span><input class="text-input" type="text" name="userId" reg="\d{11}" tip="账号由11位数字组成！"/></p>
				<div class="clear"></div>
				<p><span>密码</span><input class="text-input" type="password" name="password" reg="\w{5,}" tip="密码至少5位，由数字/字母/下划线组成！"/></p>
				<div class="clear"></div>
			 	<p class="buttonGroup">
				 	<input class="button" type="submit" value="登陆" /> 
				 	<input class="button" type="reset" value="重置"/>
				 	<input class="button" type="button" value="注册" onclick="javascript:location.href='${pageContext.request.contextPath}/studentRegistUI'"/>
			 	</p>
			</form>
		</div>
		<div align="center" style="margin-top: 150px;">
			<div>Copyright © 2013 www.wjlmgqs.org All Rights Reserved</div>
			<div>重庆师范大学涉外商贸学院数学与计算机学院 WJl 版权所有</div>
		</div>
 </body>
 		
</html>
<%@ include file="/jspf/showMessageContent.jspf" %>