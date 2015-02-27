<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	<%--显示确认对话框框（基于框架实现）--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/showConfirmDialog.js"></script>
	<%--试题创建和更新时所在的层的伸缩--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/hiddenDivContent.js"></script>
  	<link href="${pageContext.request.contextPath}/css/style_divHidden.css" rel="stylesheet" type="text/css" />
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
 	<h1>试题库-判断题</h1>
 	<!-- 收缩展开效果 -->
	<div class="hiddenDiv">
		<div class="hiddenTitle"><h3>创建新试题</h3></div>
		<div class="hiddenContent">
			<form action="${pageContext.request.contextPath}/teacher/profession/questionBank_judge_create" method="post">
			  	文本内容 <textarea rows="6" cols="60" name="content" reg="\S+" tip="不能为空！"></textarea><br/>
			   	试题答案:	
			   		T<input type=radio name="result" value="T" checked="checked"/>  &nbsp;&nbsp;
			   		F<input type=radio name="result" value="F"/>  &nbsp;&nbsp;
			   	难度<select name="difficulty" size="1" tip="请选择难度" reg="[0|1|2|3|4]+">
						<option value="0" selected >简单</option>
						<option value="1" >较简单</option>
						<option value="2" >中等</option>
						<option value="3" >较困难</option>
						<option value="4" >困难</option>
					</select>        
			  	知识点<select name="knowledgeId" size="1" tip="请选择知识点" reg="[1-9]+">
						<c:forEach items="${requestScope.knowledges}" var="knowledge" step="1">
							<option value="${knowledge.id}" }>${knowledge.code}</option>
						</c:forEach>
					</select>     
				解析<textarea rows="3" cols="25" name="analyse" reg="\S+" tip="不能为空！">   </textarea><br/>
				<br/>
				<input type="submit" value="创建" />
			</form>
		</div>
	</div>
<hr/>
	<table cellpadding="5" cellspacing="0" border="1" class="display" id="eachRow" style="text-align: center" >
		<thead>
			<tr><th>序号</th><th>文本内容</th><th>知识点</th><th>难度</th><th>修改时间</th><th>操作</th></tr>
		</thead>
		<tbody>
			<c:forEach items="${judges}" var="judge" varStatus="status" step="1" >
				<tr>
					<td>${status.count}</td> 
					<td>${judge.content}</td> 
					<td>${judge.knowledge.code}</td> 
					<td>${judge.difficulty.name}</td> 
					<td><fmt:formatDate value="${judge.createTime}" pattern="yy/MM/dd HH:mm:ss"/></td> 
					<td><a href="###" onclick="showConfirmDialog('questionBank_judge_delete?judgeId=${judge.id}')" title="删除"><img src="${pageContext.request.contextPath}/images/icons/cross.png" alt="删除"/></a>
						&nbsp;&nbsp;
						<a href="${pageContext.request.contextPath}/teacher/profession/questionBank_judge_update?judgeId=${judge.id}" title="编辑"><img src="${pageContext.request.contextPath}/images/icons/pencil.png" alt="编辑"/></a>
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