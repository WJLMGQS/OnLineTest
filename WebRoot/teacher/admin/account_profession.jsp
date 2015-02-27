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
	<%--对象创建和更新时所在的层的伸缩--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/hiddenDivContent.js"></script>
  	<link href="${pageContext.request.contextPath}/css/style_divHidden.css" rel="stylesheet" type="text/css" />
  	<%--显示确认对话框框（基于框架实现）--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/showConfirmDialog.js"></script>
	<%--表格框架--%>
  	<style type="text/css" title="currentStyle">
			@import "${pageContext.request.contextPath}/css/demo_page.css";
			@import "${pageContext.request.contextPath}/css/jquery.dataTables.css";
	</style>	
 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"  ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/loadTableAble.js"></script>
  </head>
  
 <body>
 <center>
 	<h1>教师账号信息</h1>
	<!-- 收缩展开效果 -->
	<div class="hiddenDiv">
		<div class="hiddenTitle"><h3>创建新账号</h3></div>
		<div class="hiddenContent">
			<form name="regestListener" action="${pageContext.request.contextPath}/teacher/admin/account_profession_create" method="post">
				教师编号<input type="text" name="userId" reg="\d{11}" tip="账号由11位数字组成！"/>
				姓名<input type="text" name="name" reg="\S+" tip="不能为空！"/>
				电话<input type="text" name="telephone" reg="[\d-]+" tip="由数字和-组成,不能为空！"/>
				性别<input type="radio" name="sex" value="1" checked="checked"/>男
					<input type=radio name="sex" value="0"/>女
				<br/>
				初始密码<input id="freshF" type="password" name="freshF" reg="\w{5,}" value="123456" tip="密码至少5位，由数字/字母/下划线组成！"/>
				&nbsp;&nbsp;
				密码确认<input id="freshS" type="password" name="freshS" reg="\w{5,}" value="123456" tip="密码至少5位，由数字/字母/下划线组成！"/>
				&nbsp;&nbsp;&nbsp;
				监考科目<select name="subjectId" size="1" tip="请选择科目" reg="[1-9]+">
						<c:forEach items="${subjects}" var="subject" step="1">
							<option value="${subject.id}">${subject.code}</option>
						</c:forEach>
					   </select>
				&nbsp;&nbsp;&nbsp;
				账户状态<input type="radio" name="status" value="1" checked="checked"/>开启
						<input type=radio name="status" value="0"/>关闭
				<br/>
				<input type="submit" value="创建"/>
				<input type="reset" value="重置"/>
			</form>
		</div>
	</div>
<hr/>
	<table cellpadding="5" cellspacing="0" border="1" class="display" id="eachRow" style="text-align: center" >
		<thead>
			<tr><th>序号</th><th>教师编号</th><th>姓名</th><th>性别</th><th>电话</th><th>科目</th><th>账户状态</th><th>账户操作</th></tr>
		</thead>
		<tbody>
			<c:forEach items="${professions}" var="profession" varStatus="status" step="1">
				<tr>
					<td>${status.count}</td>
					<td>${profession.userId}</td><td>${profession.name}</td>
					<td>${profession.sex=='1'?'男':'女'}</td><td>${profession.telephone}</td>
					<td> ${profession.subject.code}</td><td>${profession.status.name}</td>
					<td><a href="###" onclick="showConfirmDialog('account_profession_delete?userId=${profession.userId}')" title="删除"><img src="${pageContext.request.contextPath}/images/icons/cross.png" alt="删除"/></a>
						&nbsp;&nbsp;
						<a href="${pageContext.request.contextPath}/teacher/admin/account_profession_update?userId=${profession.userId}" title="编辑"><img src="${pageContext.request.contextPath}/images/icons/pencil.png" alt="编辑"/></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
 </center>
  </body>
</html>
	<%@ include file="/jspf/hiddenDivContentStatus.jspf" %>
	<%@ include file="/jspf/showMessageContent.jspf" %>