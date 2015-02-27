<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  <%--设置窗体右边显示界面的基本样式--%>  
 	<link media="screen" type="text/css" href="${pageContext.request.contextPath}/css/showContentStyle.css"	rel="stylesheet" />
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
	<h1>参加考试</h1>
	<hr/>
	<table cellpadding="5" cellspacing="0" border="1" class="display" id="eachRow" style="text-align:center;">
		<thead>
			<tr><th>名称</th><th>描述</th><th>科目</th><th>总时间</th><th>总分</th><th>开始日期</th><th>结束日期</th><th>状态</th></tr>
		</thead>
		<tbody>
			<c:forEach items="${allExamRecords}" var="examRecord" varStatus="status" step="1" >
				<tr title="描述：${examRecord.description}">
					<td>${examRecord.name}</td>
					<td>
						<c:if test="${fn:length(examRecord.description) > 10}">
							${fn:substring(examRecord.description, 0, 10)}...
						</c:if>
						<c:if test="${fn:length(examRecord.description) <= 10}">
							${examRecord.description}
						</c:if>
					</td>
					<td>${examRecord.subject.code}</td>
					<td>${examRecord.finishTime}</td>
					<td>${examRecord.totalMark}</td>
					<td>
						<fmt:formatDate value="${examRecord.startTime}" pattern="yy/MM/dd hh:mm:ss"/>
					</td>
					<td>	
						<fmt:formatDate value="${examRecord.stopTime}" pattern="yy/MM/dd hh:mm:ss"/>
					</td>
					<td>	
						<c:choose>
						   <c:when test="${examRecord.status.index==0}"> 
								   等待
						   </c:when>
						   <c:when test="${examRecord.status.index==1}"> 
							   <a href="${pageContext.request.contextPath}/student/exam_join_testUI?recordId=${examRecord.id}">参加</a>
						   </c:when>
						   <c:otherwise>  
						   		已结束
						   </c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</center>
</body>
</html>
<%@ include file="/jspf/showMessageContent.jspf" %>