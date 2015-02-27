<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  <%--设置窗体右边显示界面的基本样式--%>  
 	<link media="screen" type="text/css" href="${pageContext.request.contextPath}/css/showRightStyle.css"	rel="stylesheet" />
 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.3.2.min.js"></script>
	<%--消息对话框框架--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.alerts.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.draggable.js"></script>
	<link href="${pageContext.request.contextPath}/css/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<%--显示确认对话框框（基于框架实现）--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/showConfirmDialog.js"></script>
	<%--ajax异步消息函数--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/optionAjax.js"></script>
	<%--表格框架--%>
  	<style type="text/css" title="currentStyle">
			@import "${pageContext.request.contextPath}/css/demo_page.css";
			@import "${pageContext.request.contextPath}/css/jquery.dataTables.css";
	</style>
 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"  ></script>
	<%-- 图片放大显示插件--%>
 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.imagePreview.1.0.js"></script>
 	<%-- 绑定图片错误显示时的处理函数和代替图片--%>
 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bindSimpleImageView.js"></script>
 	<%-- 绑定试题插入时确定插入和重置按钮--%>
 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bindQuestionButton.js"></script>
 	<%-- 处理试题插入和表格显示的函数定义--%>
 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/dealMultipleView.js"></script>
	<%-- 显示试题的显示样式--%>
	<link href="${pageContext.request.contextPath}/css/style_question.css" rel="stylesheet" type="text/css"  />
  </head>
  <body onload="initMultiplesTableByAjax(0)">
	<select id="knowledgeId" size="1" onchange="initMultiplesTableByAjax(this.value);">
		<option value='0' selected>--请选择知识点--</option>
		<c:forEach items="${requestScope.knowledges}" var="knowledge" step="1">
			<option value="${knowledge.id}" }>${knowledge.code}</option>
		</c:forEach>
	</select>
	<a href="###" id="checkReset">重置</a>	&nbsp;&nbsp;	<a href='${pageContext.request.contextPath}/teacher/profession/paper_multiples_insert?' id="checkInsert">插入到试卷</a>	<br/>
	<table cellpadding="0" cellspacing="0" border="1" style="text-align: center"  class="display" id="eachRow">
		<thead>
			<tr align="center">
				<th width="5%"><img src="${pageContext.request.contextPath}/images/plusItem.png" style="width:20px;height:20px;"/></th>
				<th width="65%">内容</th>
				<th width="10%">难度</th>
				<th width="15%">创建时间</th>
				<th width="5%">编号</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	
	<br/><br/>
	<div class="defineTop" >
		<div><h4 align="center">选择题详情</h4></div>
		<div class="defineBase">
			<%--hidden域是为了缓解每次点击同一行从服务器加载重复数据--%>
			<input type="hidden" id="bId"/>
			<div>	
				<fieldset>
					<legend>内容：</legend>
					<textarea rows="4" cols="55" id="content" disabled="disabled"></textarea>
				</fieldset>	
			</div>
			<table id="checkBoxTable" border='1' width='480px' >
			</table>
			<div>	难度：<input id="difficulty" type="text" size="2" disabled="disabled"/>&nbsp;&nbsp;创建时间：<input id="createTime" type="text" size="20" disabled="disabled"/></div>	
			<div>	
				<fieldset>
					<legend>分析：</legend>
					<textarea id="analyse" rows="4" cols="55" disabled="disabled"></textarea>
				</fieldset>	
			</div>		
			<div>	图片<a id="floatImage" target="_blank" class="preview" title="多选试题图片">
							<img id="questionImage" width='30px' height='30px'/>
						</a>
			</div>		
		</div>
	</div>
	<br/><br/><br/><br/><br/><br/><br/><br/>
 </body>
</html>
