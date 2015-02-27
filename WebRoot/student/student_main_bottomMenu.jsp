<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head> 
		<%--使用jquery必须导入--%>
 		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/css-dock-menu/js/interface.js"></script>
		<!--[if lt IE 7]>
		 <style type="text/css">
		 .dock img { behavior: url('${pageContext.request.contextPath}/js/css-dock-menu/js/iepngfix.htc') }
		 </style>
		<![endif]-->
		<link href="${pageContext.request.contextPath}/js/css-dock-menu/style.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
	<!--bottom dock -->
	<div class="dock" id="dock2">
	  <div class="dock-container2">
	  <a class="dock-item2" href="${pageContext.request.contextPath}/student/persion_home"  target="student_persion_home"><span>首页</span><img src="${pageContext.request.contextPath}/js/css-dock-menu/images/person_center.gif" alt="首页" /></a> 
	  <a class="dock-item2" href="${pageContext.request.contextPath}/student/join_exam"  target="student_persion_home"><span>参加考试</span><img src="${pageContext.request.contextPath}/js/css-dock-menu/images/join_exam.gif" alt="参加考试" /></a> 
	  <a class="dock-item2" href="${pageContext.request.contextPath}/student/refer_mark"  target="student_persion_home"><span>查看成绩</span><img src="${pageContext.request.contextPath}/js/css-dock-menu/images/look_mark.jpg" alt="查看成绩" /></a> 
<%--	  <a class="dock-item2" href="${pageContext.request.contextPath}/student/imitate_exam"  target="student_persion_home"><span>模拟考试</span><img src="${pageContext.request.contextPath}/js/css-dock-menu/images/imitate.jpg" alt="查看成绩" /></a> --%>
<%--	  <a class="dock-item2" href="${pageContext.request.contextPath}/student/error_book"  target="student_persion_home"><span>错题本</span><img src="${pageContext.request.contextPath}/js/css-dock-menu/images/error_book.jpg" alt="错题本" /></a> --%>
	  <a class="dock-item2" href="${pageContext.request.contextPath}/student/account_update"  target="student_persion_home"><span>编辑个人信息</span><img src="${pageContext.request.contextPath}/js/css-dock-menu/images/edit_info.jpg" alt="编辑个人信息"/></a> 
	  <a class="dock-item2" href="${pageContext.request.contextPath}/student/studentLogout"  target='_parent'><span>注销登录</span><img src="${pageContext.request.contextPath}/js/css-dock-menu/images/student_logout.jpg" alt="注销登录"/></a> 
	  </div>
	</div>
	
	<!--dock menu JS options -->
	<script type="text/javascript">
		
		$(document).ready(
			function()
			{
				$('#dock2').Fisheye(
					{
						maxWidth: 60,
						items: 'a',
						itemsText: 'span',
						container: '.dock-container2',
						itemWidth: 40,
						proximity: 80,
						alignment : 'left',
						valign: 'bottom',
						halign : 'center'
					}
				)
			}
		);
	
	</script>
	</body>
</html>