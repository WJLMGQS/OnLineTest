<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>新试卷创建--手工创建</title>
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
	<%--整数微调控件--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQuery.fineTuning.js"></script>
</head>
<body>
<center>
	<div>
		<h3>试卷创建--${professionTeacher.subject.code}</h3>
			试卷编号：${testPaper.id} 	&nbsp;&nbsp;	教师编号：${testPaper.teacher.userId}	&nbsp;&nbsp;	试卷总分：${testPaper.totalMark}分			
			<br/>
		<form id="infoForm" action="${pageContext.request.contextPath}/teacher/profession/paper_update_info" method="post">
			考试总时间：<input type="text" name="finishTime" maxlength="3" size="1" value="${testPaper.finishTime}"  tip="考试总时间必须为大于0且小于1000的正整数！" reg="^[1-9][0-9]{0,2}$"/>	
			&nbsp;&nbsp;
			试卷名称：<input type="text" name="name" value="${testPaper.name}" reg="\S+" tip="不能为空！"/>
			&nbsp;&nbsp;	
			试卷难度： <select name="difficulty" size="1" tip="请选择方式" reg="[0|1|2|3|4]+">
						<option value="0" ${testPaper.difficulty.index=='0'?'selected':''}>简单	</option>
						<option value="1" ${testPaper.difficulty.index=='1'?'selected':''}>较简单			</option>
						<option value="2" ${testPaper.difficulty.index=='2'?'selected':''}>一般			</option>
						<option value="3" ${testPaper.difficulty.index=='3'?'selected':''}>较困难			</option>
						<option value="4" ${testPaper.difficulty.index=='4'?'selected':''}>困难			</option>
					</select>    
			&nbsp;&nbsp;	
			使用方式: <select name="useType" size="1" tip="请选择方式" reg="[0|1|2]+">
						<option value="0" ${testPaper.useType.index=='0'?'selected':''}>考试_模拟	</option>
						<option value="1" ${testPaper.useType.index=='1'?'selected':''}>考试			</option>
						<option value="2" ${testPaper.useType.index=='2'?'selected':''}>模拟			</option>
					</select>   
			<br/>
			<!--注释：(^0\.50*$)|(^[1-9]+(\.[05])?0*$)   分值必须为0.5的整倍数！-->
			单选题分值：每题<input name="singleMark" type="text" value="${testPaper.singleMark}" size="1" reg="(^0\.50*$)|(^[1-9]+\d*(\.[05])?0*$)" tip="分值必须为0.5的整倍数！"/>分		<br/>
			多选题分值：每题<input name="multipleMark" type="text" value="${testPaper.multipleMark}" size="1" reg="(^0\.50*$)|(^[1-9]+\d*(\.[05])?0*$)" tip="分值必须为0.5的整倍数！"/>分	<br/>
			判断题分值：每题<input name="judgeMark" type="text" value="${testPaper.judgeMark}" size="1" reg="(^0\.50*$)|(^[1-9]+\d*(\.[05])?0*$)" tip="分值必须为0.5的整倍数！"/>分		<br/>
			填空题分值：每空<input name="fillBlankMark" type="text" value="${testPaper.fillBlankMark}" size="1" reg="(^0\.50*$)|(^[1-9]+\d*(\.[05])?0*$)" tip="分值必须为0.5的整倍数！"/>分	<br/>
				<input id="updateInfo" type="submit" value="${testPaper.save==true?'保存修改':'创建试卷'}" />														
		</form>
					试卷创建时间：<fmt:formatDate value="${testPaper.createTime}" pattern="yyyy-MM-dd hh:mm:ss"/>
		&nbsp;&nbsp;试卷修改时间：<fmt:formatDate value="${testPaper.updateTime}" pattern="yyyy-MM-dd hh:mm:ss"/>	<br/>
	</div>

<hr/>
	<div>
		<h3>试题阅览</h3>
		<a href="${pageContext.request.contextPath}/teacher/profession/paper_singles">单选题</a>(总分：${testPaper.singleMarkUnitNumber}*${testPaper.singleMark}=${testPaper.singleMarkUnit})<br/>
		<a href="${pageContext.request.contextPath}/teacher/profession/paper_multiples">多选题</a>(总分：${testPaper.multipleMarkUnitNumber}*${testPaper.multipleMark}=${testPaper.multipleMarkUnit})<br/>
		<a href="${pageContext.request.contextPath}/teacher/profession/paper_judges">判断题</a>(总分：${testPaper.judgeMarkUnitNumber}*${testPaper.judgeMark}=${testPaper.judgeMarkUnit})<br/>
		<a href="${pageContext.request.contextPath}/teacher/profession/paper_fillBlanks">填空题</a>(总分：${testPaper.fillBlankMarkUnitNumber}*${testPaper.fillBlankMark}=${testPaper.fillBlankMarkUnit})
	</div>
</center>
</body>
</html>
	<%@ include file="/jspf/showMessageContent.jspf" %>