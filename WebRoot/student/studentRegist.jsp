<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="org.wjlmgqs.daomain.Student"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
 	 <%--设置窗体右边显示界面的基本样式--%>  
 	<link media="screen" type="text/css" href="${pageContext.request.contextPath}/css/showContentStyle.css"	rel="stylesheet" />
	<%--使用jquery必须导入--%>
 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.3.2.min.js"></script>
	<%--验证框架框架--%>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easy_validator.pack.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.bgiframe.min.js"></script>
	<link  href="${pageContext.request.contextPath}/css/validate_failed.css" rel="stylesheet" type="text/css" />
	<%--扩展验证框架的自定义函数--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/remittence_my.js"></script>
	<%--ajax异步调用--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/optionAjax.js"></script>
	<%--消息对话框框架--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.alerts.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.draggable.js"></script>
	<link href="${pageContext.request.contextPath}/css/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<style type="text/css">
		.showBody {
			text-align: center;
			margin-left:40%; 
			margin-top: 50px;
		}
		.rigthS{
			width: 240px;
			height: 30px;
			text-align: left;
		}
		.leftS{
			width: 80px;
			height: 30px;
			text-align: right;
		}
		.registerBtn {
		    background: url("${pageContext.request.contextPath}/images/student_register.jpg") no-repeat scroll 0 0 transparent;
		    border: medium none;
		    cursor: pointer;
		    height: 30px;
		    width: 80px;
		}
		.lookBtn {
		    background: url("${pageContext.request.contextPath}/images/look_career.jpg") no-repeat scroll 0 0 transparent;
		    border: medium none;
		    cursor: pointer;
		    height: 18px;
		    width: 60px;
		}
		.resetBtn{
		 	color: #505050;
		    font-size: 12px;
		    margin-left: 12px;
		    color: #333333;
		    text-decoration: none;
		    height: 12px;
		    width: 18px;
		    text-align: center;
		}
	</style>
  </head>
  
  <body>
 	<center><h1>学生注册</h1></center><hr/>
 	<div class="showBody">
		<form id="infoForm" action="${pageContext.request.contextPath}/account_regist" method="post">
			<table>
				<tr>
					<td class="leftS" style="width: 40px;">学生编号</td>	
					<td class="rigthS"><input name="userId" type="text" reg="\d{11}" tip="账号由11位数字组成！"/></td>
				</tr>
				<tr>
					<td class="leftS">姓名</td>
					<td class="rigthS"><input name="name" type="text" reg="\S+" tip="不能为空！"/></td></tr>
				<tr>
					<td class="leftS">电话</td><td class="rigthS"><input name="telephone" type="text" reg="[\d-]+" tip="由数字和-组成,不能为空！"/></td></tr>
				<tr>
					<td class="leftS">性别</td><td class="rigthS"><input type="radio" name="sex" value="1" checked/>男
			  			 <input type="radio" name="sex" value="0" />女</td></tr>
				<tr>
					<td class="leftS">学院名称</td>
					<td class="rigthS">
						<select name="departmentId" id="departmentId" size="1" onchange="back2Default()"  tip="请选择学院" reg="[1-9]+">
							<option value="0" >----请选择以下学院----</option>
							<c:forEach items="${requestScope.departments}" var="department" step="1">
								<option value="${department.id}" } >${department.code}</option>
							</c:forEach>
					   </select>
					</td>
				</tr>
				<tr>
					<td class="leftS">年级名称</td>
					<td class="rigthS">
						<select name="gradeId" id="gradeId" size="1" tip="请选择年级" onchange="back2Default()"  reg="[1-9]+">
							<option value="0">----请选择以下年级----</option>
							<c:forEach items="${requestScope.grades}" var="grade" step="1">
								<option value="${grade.id}" }>${grade.code}</option>
							</c:forEach>
					   </select>
					   <input type="button" onclick="grade_Department2career()" value="查看专业" class="lookBtn"/>
				   </td>
				</tr>
				<tr>
					<td class="leftS">专业名称</td>
					<td class="rigthS">
						<select name="careerId" id="careerId" size="1" onchange="career2classes(this)" tip="请点击查看专业并选择专业" reg="[1-9]+">
							<option value="0">----请选择以下专业----</option>
							<c:forEach items="${requestScope.careers}" var="career" step="1">
								<option value="${career.id}" } >${career.code}</option>
							</c:forEach>
			  			</select>
		  			</td>
		  		</tr>
				<tr>
					<td class="leftS">班级名称</td>
					<td class="rigthS">
						<select name="classId" id="classId" size="1"  tip="请选择班级" reg="[1-9]+">
							<option value="0" >----请选择以下班级----</option>
							<c:forEach items="${requestScope.classess}" var="classes" step="1">
								<option value="${classes.id}" }>${classes.code}</option>
							</c:forEach>
				  		 </select>
				   </td>
				</tr>
				<tr>
					<td class="leftS">登陆密码</td><td class="rigthS"><input id="freshF" name="freshF"  type="password" reg="\w{5,}" tip="密码至少5位，由数字/字母/下划线组成！"/></td></tr>
				<tr>
					<td class="leftS">密码确认</td><td class="rigthS">	<input id="freshS" name="freshS" type="password" reg="\w{5,}" tip="请确保与新密码输入一致！" /></td></tr>
				<tr>
					<td colspan="2">
						<input id="regist" type="submit" value="" class="registerBtn"/>
							<DL>
								<DD><a class="resetBtn" href="javascript:location.reload();" >重置</a>
								<DD><a class="resetBtn" href="${pageContext.request.contextPath}/studentLogin" >登陆</a>
							</DL>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
<%@ include file="/jspf/showMessageContent.jspf" %>