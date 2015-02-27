<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
	<h1>${examsStatus.name}的考试</h1>
	<hr/>
	<table cellpadding="5" cellspacing="0" border="1" class="display" id="eachRow" style="text-align:center;">
		<thead>
			<tr><th>名称</th><th>描述</th><th>科目</th><th>总时间</th><th>总分</th><th>开始日期</th><th>结束日期</th><th>操作</th></tr>
		</thead>
		<tbody>
			<c:forEach items="${allExamRecords}" var="examRecord" varStatus="status" step="1" >
				<tr>
					<td  title="名称：${examRecord.name}">
						<c:if test="${fn:length(examRecord.name) > 5}">
							${fn:substring(examRecord.name, 0, 5)}...
						</c:if>
						<c:if test="${fn:length(examRecord.name) <= 5}">
							${examRecord.name}
						</c:if>
					</td>
					<td  title="描述：${examRecord.description}">
						<c:if test="${fn:length(examRecord.description) > 8}">
							${fn:substring(examRecord.description, 0, 8)}...
						</c:if>
						<c:if test="${fn:length(examRecord.description) <= 8}">
							${examRecord.description}
						</c:if>
					</td>
					<td   title="学科：${examRecord.subject.code}">
						<c:if test="${fn:length(examRecord.subject.code) > 10}">
							${fn:substring(examRecord.subject.code, 0, 10)}...
						</c:if>
						<c:if test="${fn:length(examRecord.subject.code) <= 10}">
							${examRecord.subject.code}
						</c:if>
					</td>
					<td>${examRecord.finishTime}</td><td>${examRecord.totalMark}</td>
					<td>
						<fmt:formatDate value="${examRecord.startTime}" pattern="yy/MM/dd hh:mm:ss"/>
					</td>
					<td>	
						<fmt:formatDate value="${examRecord.stopTime}" pattern="yy/MM/dd hh:mm:ss"/>
					</td>
					<td>
						<a href="###" onclick="showConfirmDialog('paper_buliding_delete?testPaperId=${examRecord.id}')" title="删除"><img src="${pageContext.request.contextPath}/images/icons/cross.png" alt="删除"/></a>
						<a href="${pageContext.request.contextPath}/teacher/admin/paper_buliding_update?bulidId=${examRecord.id}" title="查看"><img src="${pageContext.request.contextPath}/images/icons/pencil.png" alt="查看"/></a>
						<c:if test="${examRecord.status.index!=0}">
							<a href="${pageContext.request.contextPath}/teacher/admin/exam_status_order?bulidId='${examRecord.id}'&status=0&local=${examsStatus.index}" title="待开放"><img src="${pageContext.request.contextPath}/images/icons/status/status0.png" style="width:16px;height:16px;" alt="待开放"/></a>
						</c:if>
						<c:if test="${examRecord.status.index!=1}">
							<a href="${pageContext.request.contextPath}/teacher/admin/exam_status_order?bulidId='${examRecord.id}'&status=1&local=${examsStatus.index}" title="开放中"><img src="${pageContext.request.contextPath}/images/icons/status/status2.png" style="width:16px;height:16px;" alt="开放中"/></a>
						</c:if>
						<c:if test="${examRecord.status.index!=2}">
							<a href="${pageContext.request.contextPath}/teacher/admin/exam_status_order?bulidId='${examRecord.id}'&status=2&local=${examsStatus.index}" title="已结束"><img src="${pageContext.request.contextPath}/images/icons/status/status4.png" style="width:16px;height:16px;" alt="已结束"/></a>
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