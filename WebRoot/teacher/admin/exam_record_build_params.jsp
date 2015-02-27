<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>创建考试</title>
     <%--设置窗体右边显示界面的基本样式--%>  
 	<link media="screen" type="text/css" href="${pageContext.request.contextPath}/css/showRightStyle.css"	rel="stylesheet" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.3.2.min.js"></script>
    <%--整数微调控件--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-spin.js"></script>
	<%--验证框架框架--%>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easy_validator.pack.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.bgiframe.min.js"></script>
	<link  href="${pageContext.request.contextPath}/css/validate_failed.css" rel="stylesheet" type="text/css" />
	<%--消息对话框框架--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.alerts.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.draggable.js"></script>
	<link href="${pageContext.request.contextPath}/css/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<%--日期选择器插件	--%>
	<link href="${pageContext.request.contextPath}/css/datePicker.css" rel="stylesheet" type="text/css"  />
	<script src="${pageContext.request.contextPath}/js/jquery.datePicker-min.js" type="text/javascript"></script>
	<%--表格框架--%>
  	<style type="text/css" title="currentStyle">
			@import "${pageContext.request.contextPath}/css/demo_page.css";
			@import "${pageContext.request.contextPath}/css/jquery.dataTables.css";
	</style>	
 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"  ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/loadTableAble.js"></script>
	<%--异步加载数据--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/optionAjax.js"></script>
	 <%-- 处理考试试卷表格显示的函数定义--%>
 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/dealExamRecordView.js"></script>
    <script type="text/javascript">
    	$(document).ready(function() {
    		$.spin.imageBasePath = '${pageContext.request.contextPath}/images/icons/';
    		$('#finishTime').spin({
    			max: 240,
    			min: 10
    		});
    		$('#startTime,#stopTime').datePicker({clickInput:true});
        	$('.dp-choose-date').hide();
    	});
		function checkKids(){
			var kids = $("#eachRow");
			var length = kids.find("td :checked").length;
			if(length<=0){
				 jAlert('您尚未选择任何考卷，不能进行考试构建！', '来自页面的消息');
				return false;
			}else{
				return true;
			}
		}
	</script>
	<style type="text/css">
		#finishTime{
			width:30px;
		}
	</style>
  </head>
  <body>
  		<div style="text-align:center;">
		<form id="checkInsert" action="${pageContext.request.contextPath}/teacher/admin/exam_record_create" method="post">
<%--硬性指标--%>
			<hr/>
			<fieldset>
				<legend>考试内容设置</legend>
					考试名称：<input type="text" name="name" reg="\S+" tip="不能为空！"/> 
					&nbsp;&nbsp; 
					详细描述：<input type="text" name="description" reg="\S+" tip="不能为空！"  style="width:150px;"/> 
					&nbsp;&nbsp; 	
					开始时间：<input type="text" name="startTime" reg="(?:0[1-9]|[12][0-9]|3[01])\/(?:0[1-9]|1[0-2])\/((?:19|20)\d{2})" tip="不能为空！" id="startTime" style="width:100px" onfocus="HS_setDate(this) ">
					&nbsp;&nbsp; 	
					结束时间：<input type="text" name="stopTime" reg="(?:0[1-9]|[12][0-9]|3[01])\/(?:0[1-9]|1[0-2])\/((?:19|20)\d{2})" tip="不能为空！" id="stopTime" style="width:100px" onfocus="HS_setDate(this)">
			</fieldset>	
<%--构建指标--%>
			
			<fieldset>
				<legend>试卷范围设置</legend>
			<table style="margin-left: 240px;text-align: left ;">
				<tr>
					<td width="220px;" >
						试卷总分：<input type="text" name="totalMark" id="totalMark" value="100" style="width:50px;"/>分	
						<br/>	<br/>
						考试总时间：<input type="text" name="finishTime" id="finishTime" value="120"  tip="请在[10,240]区间内去整数值！" reg="^[1-9][0-9]{1}|1[0-9]{2}|2[0-3][0-9]|240$"/>	
						<br/>	<br/>
						考试学科：	
							<select id="subjectId" name="subjectId" size="1" onchange="subject2career(this)"   tip="参加考试的学科不能为空！" reg="[1-9]+">
								<option value='0' selected>--请选择学科--</option>
								<c:forEach items="${requestScope.subjects}" var="subject" step="1">
									<option value="${subject.id}">${subject.code}</option>
								</c:forEach>
							</select>
						<br/><br/>
						<input type="button" value="搜索" onclick="initPaperTableByAjax();"/>	
					</td>
					<td width="280px;">
						专业设置: <br/>
						<select name="careerId" id="careerId" multiple="multiple" size="10" tip="参加考试的专业不能为空！" reg="[1-9]+" style="width：270px;">
							<option value='0' selected>--请选择专业--</option>
						</select>
					</td>				
				</tr>
			</table>


		
	<table cellpadding="0" cellspacing="0" border="1" style="text-align: center" class="display" id="eachRow">
		<thead>
			<tr align="center">
				<th width="5%"><img src="${pageContext.request.contextPath}/images/plusItem.png" style="width:20px;height:20px;"/></th>
				<th width="10%">教师编号</th>
				<th width="10%">试卷名称</th>
				<th width="8%">组卷</th>
				<th width="12%">使用</th>
				<th width="12%">科目</th>
				<th width="8%">难度</th>
				<th width="15%">修改时间</th>
				<th width="15%">创建时间</th>
				<th width="5%">编号</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
			</fieldset>	
			<hr/>
			<input type="submit" value="立即创建" title="立即根据给定的试卷参数构建考试" onclick="return checkKids()"/>
		</form>
	</div>
  </body>
</html>
<%@ include file="/jspf/showMessageContent.jspf" %>