<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="org.wjlmgqs.daomain.Teacher"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  <%--设置窗体右边显示界面的基本样式--%>  
 	<link media="screen" type="text/css" href="${pageContext.request.contextPath}/css/showRightStyle.css"	rel="stylesheet" />
	<%--使用jquery必须导入--%>
 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.3.2.min.js"></script>
	<%--验证框架框架--%>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easy_validator.pack.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.bgiframe.min.js"></script>
	<link  href="${pageContext.request.contextPath}/css/validate_failed.css" rel="stylesheet" type="text/css" />
	<%--扩展验证框架的自定义函数--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/remittence_my.js"></script>
	<%--上传框架--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.uploadify.v2.1.0.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/swfobject.js"></script>
	<link  href="${pageContext.request.contextPath}/css/uploadify.css" rel="stylesheet" type="text/css" />
	<%--消息对话框框架--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.alerts.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.draggable.js"></script>
	<link href="${pageContext.request.contextPath}/css/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<%--ajax异步消息函数--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/optionAjax.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#uploadify").uploadify({
			   'uploader': '${pageContext.request.contextPath}/swf/uploadify.swf?random=' + (new Date()).getTime(), 
			   'cancelImg':'${pageContext.request.contextPath}/images/cancel.png',
			   'script': '${pageContext.request.contextPath}/teacher/admin/account_student_update_photo',//要提交到的处理文件上传后台文件
			   'scriptData':{'studentId':$('#studentId').val()},
	           'method':'GET',
			    'folder': '${pageContext.request.contextPath}/${initParam.studentPhotoFolder}',//要上传到哪个目录下,可以使用../../images这样的路径
			   'auto'           : false, //是否自动开始   
	           'multi'          : false, //是否支持多文件上传   
	           'buttonText'     : 'browe', //按钮上的文字   
	          // 'buttonImg'		: '${pageContext.request.contextPath}/images/vtip_arrow.png',
	           'simUploadLimit' : 1, //一次同步上传的文件数目   
	           'sizeLimit'      : '${initParam.imageSizeLimit}',//设置单个文件大小限制   
	           'queueSizeLimit' : 1, //队列中同时存在的文件个数限制   
	           'fileDesc'       : '支持格式:jpg/gif/jpeg/png/bmp.', //如果配置了以下的'fileExt'属性，那么这个属性是必须的   
	           'fileExt'        : '*.jpg;*.gif;*.jpeg;*.png;*.bmp',//允许的格式     
	           onComplete: function (event, queueID, fileObj, response, data) {   
		           if(response.substr(response.lastIndexOf(".")+1).toLowerCase()=="jpeg"){
		        	 	jAlert('照片更新成功！', '服务端消息');
		        	 	$("#studentPhoto").attr("src",response+'?random='+ (new Date()).getTime()); 
			       }else{
			    	   jAlert(response, '服务端消息');
				    }
	           },   
	           onError: function(event, queueID, fileObj) {   
	               jAlert("照片:" + fileObj.name + "上传失败", '服务端消息') ;
	           },   
	           onCancel: function(event, queueID, fileObj){   
	               jAlert("取消了" + fileObj.name, '服务端消息');
	           }   
			});
		});		
	</script>
  </head>
  
  <body>
 	<center><h1>学生信息更改</h1>
 	<a href="${pageContext.request.contextPath}/teacher/admin/account_student">后退</a></center>
<hr/>
<table>
<tr>
<td>
	<img id="studentPhoto" src="${pageContext.request.contextPath}/${initParam.studentPhotoFolder}/${student.photo}" onerror= "javascript:this.src='${pageContext.request.contextPath}/images/loadError.jpg'"  width='250' height='200'/>
 	<input type="file" name="uploadify" id="uploadify" /> 
   	<a href="javascript:$('#uploadify').uploadifyUpload()">上传</a>|  
   	<a href="javascript:$('#uploadify').uploadifyClearQueue()">取消上传</a> 
   	<div id="fileQueue"></div> 
     <br/>
</td>
<td> 
	<center>
	<form id="infoForm" action="${pageContext.request.contextPath}/teacher/admin/account_student_update_info?studentId=${student.userId}" method="post">
		学生编号<input type="text" value="${student.userId}" id="studentId" disabled="disabled"/>
		姓名<input name="name" type="text" value="${student.name}" reg="\S+" tip="不能为空！"/>
		&nbsp;&nbsp;
		电话<input name="telephone" type="text" value="${student.telephone}" reg="[\d-]+" tip="由数字和-组成,不能为空！"/>
		<br/>
		性别<input type="radio" name="sex" value="1" ${student.sex=='1'?'checked':''}/>男
		   <input type="radio" name="sex" value="0" ${student.sex=='0'?'checked':''}/>女
		&nbsp;&nbsp;
		账户状态<input type="radio" name="status" value="1" ${student.status.index=='1'?'checked':''}/>开启
			   <input type="radio" name="status" value="0" ${student.status.index=='0'?'checked':''}/>关闭
		<br/>
		学院名称<select name="departmentId" id="departmentId" size="1" onchange="back2Default()"  tip="请选择学院" reg="[1-9]+">
					<option value="0" >----请选择以下学院----</option>
					<c:forEach items="${requestScope.departments}" var="department" step="1">
						<option value="${department.id}" } ${department.id==student.classes.career.department.id?"selected":"" }>${department.code}</option>
					</c:forEach>
			   </select>
		&nbsp;&nbsp;
		年级名称<select name="gradeId" id="gradeId" size="1" tip="请选择年级" onchange="back2Default()"  reg="[1-9]+">
					<option value="0">----请选择以下年级----</option>
					<c:forEach items="${requestScope.grades}" var="grade" step="1">
						<option value="${grade.id}" } ${grade.id==student.classes.career.grade.id?"selected":"" }>${grade.code}</option>
					</c:forEach>
			   </select>
		<input type="button" onclick="grade_Department2career()" value="查看专业"/>
		&nbsp;&nbsp;
		专业名称<select name="careerId" id="careerId" size="1" onchange="career2classes(this)" tip="请点击查看专业并选择专业" reg="[1-9]+">
					<option value="0">----请选择以下专业----</option>
					<c:forEach items="${requestScope.careers}" var="career" step="1">
						<option value="${career.id}" } ${career.id==student.classes.career.id?"selected":"" }>${career.code}</option>
					</c:forEach>
	  			</select>
	  	<br/>
	  	班级名称<select name="classId" id="classId" size="1"  tip="请选择班级" reg="[1-9]+">
					<option value="0" >----请选择以下班级----</option>
					<c:forEach items="${requestScope.classess}" var="classes" step="1">
						<option value="${classes.id}" } ${classes.id==student.classes.id?"selected":"" }>${classes.code}</option>
					</c:forEach>
			   </select>
		<br/>
		<input id="updateInfo" type="submit" value="更新" onclick="clearPwd()"/>&nbsp;&nbsp;<input type="reset" value="重置"/>
	</form>
<hr/>
	<form id="pwdForm" action="${pageContext.request.contextPath}/teacher/admin/account_student_update_pwd?studentId=${student.userId}" method="post">
		重置新密码<input id="freshF" name="freshF"  type="password" reg="\w{5,}" tip="密码至少5位，由数字/字母/下划线组成！" value="123456" />
		&nbsp;&nbsp;
		确认新密码<input id="freshS" name="freshS" type="password" reg="\w{5,}" tip="请确保与新密码输入一致！" value="123456" />
		&nbsp;&nbsp;
		<input id="updatePwd" type="submit" value="更新"/>&nbsp;&nbsp;<input type="reset" value="重置"/><br/>
	</form>
 </center>
 </td>
</tr>
</table>
</body>
</html>
	<%@ include file="/jspf/showMessageContent.jspf" %>