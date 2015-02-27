<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>任课教师管理界面</title>
  	</head>
	<frameset cols="230,*" frameborder="no" border="0" framespacing="0" style="text-align:center; margin:0px; padding:0px;">
		<frame src="${pageContext.request.contextPath}/teacher/profession_main_leftMenu" name="professionLeftMenu" scrolling="no" noresize="noresize" />
		<frame src="${pageContext.request.contextPath}/teacher/profession_main_rightShow" name="professionRightShow" scrolling="yes" noresize="noresize" />
	</frameset>
</html>
