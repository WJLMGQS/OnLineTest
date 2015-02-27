<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  <%--设置窗体右边显示界面的基本样式--%>  
 	<link media="screen" type="text/css" href="${pageContext.request.contextPath}/css/showRightStyle.css"	rel="stylesheet" />
  	<%--使用jquery必须导入--%>
 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.3.2.min.js"></script>
 	<%--消息对话框框架--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.alerts.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.draggable.js"></script>
	<link href="${pageContext.request.contextPath}/css/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
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
	<h1>${paperStatus.name}的试卷</h1>
	<hr/>
	<table cellpadding="5" cellspacing="0" border="1" class="display" id="eachRow" style="text-align: center;">
		<thead>
			<tr><th>试卷编号</th><th>试卷名称</th><th>科目</th><th>组卷</th><th>使用</th><th>创建日期</th><th>修改日期</th><th>操作</th><th>审核</th></tr>
		</thead>
		<tbody>
			<c:forEach items="${allTestPapers}" var="_testPaper" varStatus="status" step="1" >
				<tr><td>${_testPaper.id}</td><td>${_testPaper.name}</td><td>${_testPaper.teacher.subject.code}</td>
					<td>${_testPaper.bulidType.name}</td><td>${_testPaper.useType.name}</td><td>${_testPaper.status.name}</td><td><fmt:formatDate value="${_testPaper.createTime}" pattern="yy/MM/dd hh:mm:ss"/>	</td>
					<td><fmt:formatDate value="${_testPaper.updateTime}" pattern="yy/MM/dd hh:mm:ss"/>	</td>
					<td style="text-align:left;"><a href="###" onclick="showConfirmDialog('paper_buliding_delete?testPaperId=${_testPaper.id}')" title="删除"><img src="${pageContext.request.contextPath}/images/icons/cross.png" alt="删除"/></a>
						<c:if test="${_testPaper.status.index==0 || _testPaper.status.index==2}">
							<a href="${pageContext.request.contextPath}/teacher/profession/paper_buliding_update?bulidId=${_testPaper.id}" title="编辑"><img src="${pageContext.request.contextPath}/images/icons/pencil.png" alt="编辑"/></a>
						</c:if>
						<c:if test="${_testPaper.status.index==1}">
							&nbsp;&nbsp;
						</c:if>
						<c:if test="${_testPaper.status.index==0 || _testPaper.status.index==2}">
							<a href="${pageContext.request.contextPath}/teacher/profession/paper_status_order?bulidId='${_testPaper.id}'&status=1&local=${paperStatus.index}" title="审核"><img src="${pageContext.request.contextPath}/images/icons/status/status1.png" style="width:16px;height:16px;" alt="审核"/></a>
						</c:if>
						<c:if test="${_testPaper.status.index==1}">
							<a href="${pageContext.request.contextPath}/teacher/profession/paper_status_order?bulidId='${_testPaper.id}'&status=0&local=${paperStatus.index}" title="取消审核"><img src="${pageContext.request.contextPath}/images/icons/status/status0.png" style="width:16px;height:16px;" alt="取消审核"/></a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</center>
</body>
</html>
<%@ include file="/jspf/showMessageContent.jspf" %>