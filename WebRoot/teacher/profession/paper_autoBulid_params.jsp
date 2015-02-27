<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>遗传算法自动组卷</title>
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
	<%--表格框架--%>
  	<style type="text/css" title="currentStyle">
			@import "${pageContext.request.contextPath}/css/demo_page.css";
			@import "${pageContext.request.contextPath}/css/jquery.dataTables.css";
	</style>	
 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"  ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/loadTableAble.js"></script>
    <script type="text/javascript">
    	$(document).ready(function() {
    		$.spin.imageBasePath = '${pageContext.request.contextPath}/images/icons/';
    		$('.questionStyle').spin({
    			max: 60,
    			min: 0
    		});
    		$('#finishTime').spin({
    			max: 240,
    			min: 10
    		});
    		$('.questionMark').spin({
    			max: 10,
    			min: 0.5,
    			interval: 0.5
    		});
        });
   
	</script>
	<style type="text/css">
		.questionStyle,.questionMark,#finishTime{
			width:30px;
		}
	</style>
	<script type="text/javascript">
		function checkKids(){
			var kids = $("#knowledgeIds");
			var length = kids.find("td :checked").length;
			if(length<=0){
				 jAlert('您尚未选择任何知识点，不能进行试卷构建！', '来自页面的消息');
				return false;
			}else{
				return true;
			}
		}
	</script>
  </head>
  
  <body>
  		<div style="text-align:center;">
		<h3>遗传组卷</h3>
			教师编号：${professionTeacher.userId}&nbsp;&nbsp;&nbsp;&nbsp;	科目：${professionTeacher.subject.code}	&nbsp;&nbsp;&nbsp;&nbsp;试卷总分：<input type="text" name="totalMark" value="0" disabled="disabled" style="width:50px;"/>分	
			<br/>
		<form id="infoForm" action="${pageContext.request.contextPath}/teacher/profession/paper_autoBulid_create" method="post">
<%--硬性指标--%>
			<hr/>
			<fieldset>
				<legend>标题内容设置</legend>
				试卷名称：<input type="text" name="name" reg="\S+" tip="不能为空！"/> 
				&nbsp;&nbsp; 
					
				考试总时间：<input type="text" name="finishTime" id="finishTime" value="120"  tip="请在[10,240]区间内去整数值！" reg="^[1-9][0-9]{1}|1[0-9]{2}|2[0-3][0-9]|240$"/>	
				&nbsp;&nbsp; 
				试卷总体难度：				
				<select name="difficulty" size="1" tip="请选择难度" reg="[0|1|2|3|4]+">
					<option value="0" selected >简单</option>
					<option value="1" >较简单</option>
					<option value="2" >中等</option>
					<option value="3" >较困难</option>
					<option value="4" >困难</option>
				</select> 
				 &nbsp;&nbsp; 
				  使用方式: 
				<select name="useType" size="1" tip="请选择方式" reg="[0|1|2]+">
					<option value="0" ${testPaper.useType.index=='0'?'selected':''}>考试_模拟	</option>
					<option value="1" ${testPaper.useType.index=='1'?'selected':''}>模拟			</option>
					<option value="2" ${testPaper.useType.index=='2'?'selected':''}>考试			</option>
				</select>  
			</fieldset>	
			
<%--组卷指标--%>
			<fieldset>
				<legend>试题数量设置</legend>
				单选题数量<input type="text" id="singleNumber" name="singleNumber"	class="questionStyle" value="10" tip="请在[0,60]区间内去整数值！" reg="^(0){1}|[1-5]?[0-9]{0,1}|(60)$"/>&nbsp;&nbsp; 
				判断题数量<input type="text" id="judgeNumber"	name="judgeNumber"	class="questionStyle" value="10" tip="请在[0,60]区间内去整数值！" reg="^(0){1}|[1-5]?[0-9]{0,1}|(60)$"/>	&nbsp;&nbsp;
				多选题数量<input type="text" id="multipleNumber" name="multipleNumber"	class="questionStyle" value="10" tip="请在[0,60]区间内去整数值！" reg="^(0){1}|[1-5]?[0-9]{0,1}|(60)$"/>	&nbsp;&nbsp; 
				填空题数量<input type="text" id="fillBlankNumber" name="fillBlankNumber"	class="questionStyle" value="10" tip="请在[0,60]区间内去整数值！" reg="^(0){1}|[1-5]?[0-9]{0,1}|(60)$"/>	&nbsp;&nbsp;
			</fieldset>	
			
			<fieldset>
				<legend>试题分值设置</legend>
				单选题：每题<input class="questionMark" name="singleMark" type="text" value="1" size="1" reg="(^0\.50*$)|(^[1-9]+\d*(\.[05])?0*$)" tip="分值必须为0.5的整倍数,且在[0.5,10]范围内！"/>分				&nbsp;&nbsp;
				多选题：每题<input class="questionMark" name="multipleMark" type="text" value="3" size="1" reg="(^0\.50*$)|(^[1-9]+\d*(\.[05])?0*$)" tip="分值必须为0.5的整倍数,且在[0.5,10]范围内！"/>分			&nbsp;&nbsp;
				判断题：每题<input class="questionMark" name="judgeMark" type="text" value="1" size="1" reg="(^0\.50*$)|(^[1-9]+\d*(\.[05])?0*$)" tip="分值必须为0.5的整倍数,且在[0.5,10]范围内！"/>分				&nbsp;&nbsp;
				填空题：每空<input class="questionMark" name="fillBlankMark" type="text" value="2" size="1" reg="(^0\.50*$)|(^[1-9]+\d*(\.[05])?0*$)" tip="分值必须为0.5的整倍数,且在[0.5,10]范围内！"/>分		&nbsp;&nbsp;															
			</fieldset>	
			
			<fieldset>
				<legend>试题难度设置</legend>
				单选总体难度：				
				<select name="singleDifficulty" size="1" tip="请选择难度" reg="[0|1|2|3|4]+">
					<option value="0" selected >简单</option>
					<option value="1" >较简单</option>
					<option value="2" >中等</option>
					<option value="3" >较困难</option>
					<option value="4" >困难</option>
				</select> 
				 &nbsp;&nbsp; 
				判断总体难度：				
				<select name="judgeDifficulty" size="1" tip="请选择难度" reg="[0|1|2|3|4]+">
					<option value="0" selected >简单</option>
					<option value="1" >较简单</option>
					<option value="2" >中等</option>
					<option value="3" >较困难</option>
					<option value="4" >困难</option>
				</select> 
				 &nbsp;&nbsp; 
				多选总体难度：				
				<select name="multipleDifficulty" size="1" tip="请选择难度" reg="[0|1|2|3|4]+">
					<option value="0" selected >简单</option>
					<option value="1" >较简单</option>
					<option value="2" >中等</option>
					<option value="3" >较困难</option>
					<option value="4" >困难</option>
				</select> 
				 &nbsp;&nbsp; 
				填空总体难度：				
				<select name="fillBlankDifficulty" size="1" tip="请选择难度" reg="[0|1|2|3|4]+">
					<option value="0" selected >简单</option>
					<option value="1" >较简单</option>
					<option value="2" >中等</option>
					<option value="3" >较困难</option>
					<option value="4" >困难</option>
				</select> 
			</fieldset>	
			
			<fieldset>
				<legend>知识点范围设置</legend>
				<table cellpadding="5" cellspacing="0" border="1" class="display" id="eachRow" style="text-align: center" >
					<thead>
						<tr><th>序号</th><th>当前可选知识点</th><th>加入</th></tr>
					</thead>
					<tbody align="center" id="knowledgeIds">
						<c:forEach items="${requestScope.knowledges}" var="knowledge" varStatus="status">
							<tr>
								<td width="9%">${status.count }</td>
								<td>${knowledge.code}</td>
								<td width="9%"><input type="checkbox" name="knowledgeIds" value="${knowledge.id}"/></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</fieldset>	
			<hr/>
			<input type="submit" value="立即创建" title="立即根据给定的试卷参数构建试卷" onclick="return checkKids()"/>
		</form>
	</div>
  </body>
</html>
<%@ include file="/jspf/showMessageContent.jspf" %>