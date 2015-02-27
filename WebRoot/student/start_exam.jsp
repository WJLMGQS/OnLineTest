<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>学生考试</title>
 	<link media="screen" type="text/css" href="${pageContext.request.contextPath}/css/showContentStyle.css"	rel="stylesheet" />
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
	<%--ajax异步消息函数--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/optionAjax.js"></script>
	<%-- 学生答卷检测js--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/exam_testUnits.js"></script>
	<script>
		$().ready(function(){
			var totalTime = '${lastTime}';
			var time = parseInt(totalTime);
			if (isNaN(time)){
				jAlert('当前页面异常，请重新进入考试！', '服务端消息');
			}else{
				updateExamTime(time);
			}
		});
		function updateExamTime(time){
			var timeSpan = $("#lastTime");
			var showTime = formatSeconds(time);
			timeSpan.text(showTime);
			setTimeout("updateExamTime("+(--time)+")",1000);
		}
	    function formatMinutes(minutes){
	        var day = parseInt(Math.floor(minutes / 1440));
	        var hour = day >0 ?Math.floor((minutes - day*1440)/60) :Math.floor(minutes/60);  
	        var minute = hour > 0 ? Math.floor(minutes -day*1440 - hour*60) :minutes;
	        var time="";       
	        if (day > 0) time += day + "天";
	        if (hour > 0) time += hour + "小时";
	        if (minute > 0) time += minute + "分钟";
	        return time;
	    }
	    //格式化秒数为时分秒
	    function formatSeconds(seconds) {
	        if(seconds >0){
	            var minutes = Math.floor(seconds/60);
	            seconds = seconds - minutes * 60;
	            return formatMinutes(minutes) + (seconds > 0 ? seconds + "秒" : "");
	        }else{
	        	alert("考试时间已到，系统自动保存试卷并提交！");
	        }
	        return seconds;
	    }

	</script>
</head>
<body>
<center>
	<div>
		<h3>${testPaper.name}</h3>
			科目：${testPaper.teacher.subject.code}	&nbsp;&nbsp;教师：${testPaper.teacher.name}	<br/>
			试卷总分：${testPaper.totalMark}分		
			&nbsp;&nbsp;		
			考试总时间：${testPaper.finishTime}
			&nbsp;&nbsp;	
			试卷难度：
				<c:choose>
					<c:when test="${testPaper.difficulty.index=='0'}">简单</c:when>
					<c:when test="${testPaper.difficulty.index=='1'}">较简单</c:when>
					<c:when test="${testPaper.difficulty.index=='2'}">一般</c:when>
					<c:when test="${testPaper.difficulty.index=='3'}">较困难</c:when>
					<c:when test="${testPaper.difficulty.index=='4'}">困难</c:when>
				</c:choose>
			<br/>
			考试剩余时间：<span id="lastTime">${lastTime}</span>
	</div>

<hr/>
	<div style="margin-top: 40px;">
		<h3>考试试题</h3>
		<a href="${pageContext.request.contextPath}/student/start_testUnits?type=0" target="_blank">单选题</a>[${testPaper.singleMarkUnitNumber}*${testPaper.singleMark}=${testPaper.singleMarkUnit}]
		&nbsp;&nbsp;	
		<a href="${pageContext.request.contextPath}/student/start_testUnits?type=1" target="_blank">多选题</a>[${testPaper.multipleMarkUnitNumber}*${testPaper.multipleMark}=${testPaper.multipleMarkUnit}]
		&nbsp;&nbsp;	
		<a href="${pageContext.request.contextPath}/student/start_testUnits?type=2" target="_blank">判断题</a>[${testPaper.judgeMarkUnitNumber}*${testPaper.judgeMark}=${testPaper.judgeMarkUnit}]
		&nbsp;&nbsp;	
		<a href="${pageContext.request.contextPath}/student/start_testUnits?type=3" target="_blank">填空题</a>[${testPaper.fillBlankMarkUnitNumber}*${testPaper.fillBlankMark}=${testPaper.fillBlankMarkUnit}]
	</div>
	<div style="margin-top: 20px;">
		<input type="button" value="提交试卷" onclick="submitExamPaper(${examID});"/>
	</div>
</center>
</body>
</html>
	<%@ include file="/jspf/showMessageContent.jspf" %>