<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../jspf/professionLeftMenuHeadFile.jspf" %>
<html>
<head>
	<%--使用jquery必须导入--%>
 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.3.2.min.js"></script>
	<%--构建左边导航树的样式--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/simpla.jquery.configuration.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/facebox.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.wysiwyg.js"></script>
	<link media="screen" type="text/css" href="${pageContext.request.contextPath}/css/reset.css" rel="stylesheet">
	<link media="screen" type="text/css" href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
	<link media="screen" type="text/css" href="${pageContext.request.contextPath}/css/invalid.css" rel="stylesheet">
	<style type="text/css">
	</style>
</head>
<body>
	<div id="body-wrapper">
		<div id="sidebar">
	    <div id="sidebar-wrapper">
	      <a href="#"><img alt="Simpla Profession logo" src="${pageContext.request.contextPath}/images/logo.png" id="logo"></a>
	      <div id="profile-links"> 
	      	${sessionScope.professionTeacher.name }&nbsp;&nbsp;
	        <a title="退出登陆" href="${pageContext.request.contextPath}/teacher/teacherLogout" target='_parent'>用户注销</a> 
	      </div>
	     	<ul id="main-nav">
	        	<li> 
	        		<a class="nav-top-item" href="#" style="padding-right: 15px;">题库维护</a>
	          		<ul style="display: block;">
	          			<li><a href="${pageContext.request.contextPath}/teacher/profession/questionBank_single" target="professionRightShow">单选题库</a></li>
						<li><a href="${pageContext.request.contextPath}/teacher/profession/questionBank_multiple" target="professionRightShow">多选题库</a></li>
						<li><a href="${pageContext.request.contextPath}/teacher/profession/questionBank_judge" target="professionRightShow">判断题库</a></li>
						<li><a href="${pageContext.request.contextPath}/teacher/profession/questionBank_fillBlank" target="professionRightShow">填空题库</a></li>
	         		</ul>
	        	</li>
	        	<li> 
	        		<a class="nav-top-item" href="#" style="padding-right: 15px;">名词维护</a>
	          		<ul style="display: block;">
	          			<li> <a href="###" target="professionRightShow" onclick="return false;">学生</a></li>
<%--	          			<li><a href="${pageContext.request.contextPath}/teacher/profession/name_student" target="professionRightShow">学生</a></li>--%>
						<li><a href="${pageContext.request.contextPath}/teacher/profession/name_knowledge" target="professionRightShow">知识点</a></li>
						<li><a href="${pageContext.request.contextPath}/teacher/profession/name_examRecord" target="professionRightShow">考试记录</a></li>
	         		</ul>
	        	</li>
	        	<li> 
	        		<a class="nav-top-item" href="#" style="padding-right:15px;">账号维护</a>
	          		<ul style="display: block;">
	          			<li><a href="${pageContext.request.contextPath}/teacher/profession/account_professionSelf_update" target="professionRightShow">账户更新</a></li>
	         		</ul>
	        	</li>
	        	<li> 
	        		<a class="nav-top-item current" href="#" style="padding-right: 15px;"> 试卷维护 </a>
	          		<ul style="display: none;">
	            		<li><a href="${pageContext.request.contextPath}/teacher/profession/paper_buliding" target="professionRightShow">正在创建</a></li>
						<li><a href="${pageContext.request.contextPath}/teacher/profession/paper_create_labour" target="professionRightShow">手动组卷</a></li>
						<li><a href="${pageContext.request.contextPath}/teacher/profession/paper_create_auto" target="professionRightShow">遗传组卷</a></li>
						<li><a href="${pageContext.request.contextPath}/teacher/profession/paper_list?status=5" target="professionRightShow">所有试卷</a></li>
						<li><a href="${pageContext.request.contextPath}/teacher/profession/paper_list?status=0" target="professionRightShow">未审核</a></li>
						<li><a href="${pageContext.request.contextPath}/teacher/profession/paper_list?status=1" target="professionRightShow">待审核</a></li>
						<li><a href="${pageContext.request.contextPath}/teacher/profession/paper_list?status=2" target="professionRightShow">审核失败</a></li>
						<li><a href="${pageContext.request.contextPath}/teacher/profession/paper_list?status=3" target="professionRightShow">未发布</a></li>
						<li><a href="${pageContext.request.contextPath}/teacher/profession/paper_list?status=4" target="professionRightShow">已发布</a></li>
	          		</ul>
	        	</li>
	      </ul>
	    </div>
	  	</div>
	</div>

</body>
</html>