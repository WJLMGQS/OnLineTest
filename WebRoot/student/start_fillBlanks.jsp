<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
  	<%-- 图片放大显示插件--%>
 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.imagePreview.1.0.js"></script>
	<%-- 绑定图片错误显示时的处理函数和代替图片--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bindSimpleImageView.js"></script>
 	<%-- 显示试题的显示样式--%>
	<link href="${pageContext.request.contextPath}/css/style_exam_question.css" rel="stylesheet" type="text/css"  />
	<%-- 动态为图片列表赋值以添加放大效果--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/setListImageLink.js"></script>
	<%--ajax异步消息函数--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/optionAjax.js"></script>
	<%-- 学生答卷检测js--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/exam_testUnits.js"></script>
  </head>
 <body>
 <center>
 	<h1>填空题</h1>
<hr/>

	<c:forEach items="${fillBlanks}" var="fillBlank" varStatus="status" step="1" >
		<div id="showfillBlankItem" class="defineTop"  tip="${status.count}">
			<div class="defineTitle"><strong>第${status.count}题</strong></div>
			<div class="defineTitle">
					<input type="hidden" name="flag" flag="false"/>
					<c:choose>
						<c:when test="${fillBlank.image=='defaultQuestBank.jpg'}">[当前试题无图]</c:when>
						<c:otherwise>
							<a id="floatImage" target="_blank" class="preview" title="单选试题图片">
								<img id="questionImage"  width='30px' height='30px' />
								<%--这里动态记录对应的试题图片路径，然后由jquery动态赋值--%>
								<input type="hidden" class="imageLink" value="${pageContext.request.contextPath}/questionBankImage/${fillBlank.image}"/>
							</a>						
						</c:otherwise>
					</c:choose>
			</div>
			<div class="defineBase">
				<div>	
					<fieldset>
						<legend>内容：</legend>
						<textarea rows="4" cols="75" id="content" disabled="disabled">${fillBlank.content}</textarea>
					</fieldset>	
				</div>	
				<table>
				<c:forEach items="${fillBlank.results}" var="results" varStatus="statusT" step="1" >
					<tr>
						<td>${statusT.count}:<input type="text" name="results" value="${fillBlank.results[statusT.count-1]}"/></td>
					</tr>
				</c:forEach>
				</table>
			</div>
		</div>
		<br/><br/>
	</c:forEach>
	<div><a href="###" onclick="isSaveFillBlank('3')" title="保存">保存试题</a></div>
 </center>
  </body>
</html>
<%@ include file="/jspf/showMessageContent.jspf" %>