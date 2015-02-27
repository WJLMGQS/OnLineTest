<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head> 
		<title>学生用户主界面</title>
	</head>
	<frameset rows="*,25%" frameborder="no" border="0" framespacing="0">
		<frame src="${pageContext.request.contextPath}/student/persion_home" scrolling="no" name="student_persion_home" border="0" noresize="noresize"/>
		<frame src="${pageContext.request.contextPath}/student/student_main_bottomMenu" name="student_main_bottomMenu" scrolling="yes" noresize="noresize" />
	</frameset>
</html>