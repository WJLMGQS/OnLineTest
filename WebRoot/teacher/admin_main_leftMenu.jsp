<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../jspf/adminLeftMenuHeadFile.jspf" %>
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
</head>
<body>
	<div id="sidebar">
    <div id="sidebar-wrapper">
      <a href="#"><img alt="Simpla Admin logo" src="${pageContext.request.contextPath}/images/logo.png" id="logo"></a>
      <div id="profile-links"> 
      	${sessionScope.adminTeacher.name }&nbsp;&nbsp;
        <a title="退出登陆" href="${pageContext.request.contextPath}/teacher/teacherLogout" target='_parent'>用户注销</a> 
      </div>
     	<ul id="main-nav">
     	   <li> 
        		<a class="nav-top-item current" href="#" style="padding-right: 15px;"> 考试维护 </a>
          		<ul style="display: none;">
					<li><a href="${pageContext.request.contextPath}/teacher/admin/exam_list?status=3" target="adminRightShow">所有的考试</a></li>
					<li><a href="${pageContext.request.contextPath}/teacher/admin/exam_list?status=2" target="adminRightShow">已结束的考试</a></li>
					<li><a href="${pageContext.request.contextPath}/teacher/admin/exam_list?status=1" target="adminRightShow">开放中的考试</a></li>
					<li><a href="${pageContext.request.contextPath}/teacher/admin/exam_list?status=0" target="adminRightShow">待开放的考试</a></li>
					<li><a href="${pageContext.request.contextPath}/teacher/admin/exam_createUI" target="adminRightShow">新建考试</a></li>
          		</ul>
        	</li>
     		<li> 
        		<a class="nav-top-item" href="#" style="padding-right: 15px;"> 试卷维护 </a>
          		<ul style="display: none;">
					<li><a href="${pageContext.request.contextPath}/teacher/admin/paper_list?status=5" target="adminRightShow">所有试卷</a></li>
					<li><a href="${pageContext.request.contextPath}/teacher/admin/paper_list?status=0" target="adminRightShow">未审核</a></li>
					<li><a href="${pageContext.request.contextPath}/teacher/admin/paper_list?status=1" target="adminRightShow">待审核</a></li>
					<li><a href="${pageContext.request.contextPath}/teacher/admin/paper_list?status=2" target="adminRightShow">审核失败</a></li>
					<li><a href="${pageContext.request.contextPath}/teacher/admin/paper_list?status=3" target="adminRightShow">未发布</a></li>
					<li><a href="${pageContext.request.contextPath}/teacher/admin/paper_list?status=4" target="adminRightShow">已发布</a></li>
          		</ul>
        	</li>
        	<li> 
        		<a class="nav-top-item" href="#" style="padding-right: 15px;">账号维护</a>
          		<ul style="display: block;">
            		<li><a href="${pageContext.request.contextPath}/teacher/admin/account_adminSelf_update" target="adminRightShow">管理员</a></li>
            		<li><a href="${pageContext.request.contextPath}/teacher/admin/account_profession" target="adminRightShow" >任课教师</a></li>
            		<li><a href="${pageContext.request.contextPath}/teacher/admin/account_student" target="adminRightShow">学生</a></li>
         		</ul>
        	</li>
        	<li> 
        		<a class="nav-top-item" href="#" style="padding-right: 15px;">名词维护</a>
          		<ul style="display: block;">
          			<li><a href="${pageContext.request.contextPath}/teacher/admin/name_department" target="adminRightShow">学院</a></li>
					<li><a href="${pageContext.request.contextPath}/teacher/admin/name_grade" target="adminRightShow">年级</a></li>
					<li><a href="${pageContext.request.contextPath}/teacher/admin/name_career" target="adminRightShow">专业</a></li>
					<li><a href="${pageContext.request.contextPath}/teacher/admin/name_helpMan" target="adminRightShow">辅导员</a></li>
					<li><a href="${pageContext.request.contextPath}/teacher/admin/name_classes" target="adminRightShow">班级</a></li>
					<li><a href="${pageContext.request.contextPath}/teacher/admin/name_subject"  target="adminRightShow">科目</a></li>
					<li><a href="${pageContext.request.contextPath}/teacher/admin/name_careerSubjectSelected" target="adminRightShow">选课</a></li>
         		</ul>
        	</li>
      </ul>
     
    </div>
  </div>
</body>
</html>