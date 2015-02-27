<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head> 
		<title>学生用户主界面</title>
		<%--使用jquery必须导入--%>
 		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.3.2.min.js"></script>
 		<%--设置学生窗体内容界面的基本样式--%>  
 		<link media="screen" type="text/css" href="${pageContext.request.contextPath}/css/showContentStyle.css"	rel="stylesheet" />
 		<style type="text/css">
	 		 .main{
	 			text-align: center;
	 			width: 960px;
	 			margin: 0 auto;
	 		}
	 		.top{
	 			text-align: center;
	 			font-size: 38px;	
	 			margin-top: 30px;
	 			height: 80px;
	 		}
	 		.info{
	 			text-align: center;
	 		}
	 		.showInfo td{
	 			width:150px;
	 			height: 30px;
	 			text-align: left;
	 		}
 		</style>
	</head>
	<body>
		<div class="main">
			<div class="top">
				个人中心
			</div>
			<div class="info" style="margin-left: 170px;margin-top: 40px;">
				<table>
					<tr>
					<td><img id="studentPhoto" src="${pageContext.request.contextPath}/${initParam.studentPhotoFolder}/${studentRole.photo}" onerror= "javascript:this.src='${pageContext.request.contextPath}/images/loadError.jpg'"  width='140' height='170'/></td>
					<td>
					<div class="showInfo">
						<table>
							<tr><td colspan="2">学生编号：${studentRole.userId}</td><td>姓名：${studentRole.name}</td></tr>
							<tr><td colspan="2">学院名称：${studentRole.classes.career.department.code}</td><td>年级名称：${studentRole.classes.career.grade.code}</td></tr>
							<tr><td colspan="2">专业名称：${studentRole.classes.career.code}</td><td>班级名称：${studentRole.classes.code}</td></tr>
							<tr><td>账户状态：${studentRole.status.index=='1'?'开启':'关闭'}</td><td>性别：${studentRole.sex=='1'?'男':'女'}</td><td>电话：${studentRole.telephone}</td></tr>
						</table>
					</div>
					</td>
					</tr>
					
					
				</table>
			</div>
			<div>
			</div>
		</div>
	</body>
</html>