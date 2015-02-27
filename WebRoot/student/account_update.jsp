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
			   'script': '${pageContext.request.contextPath}/student/account_update_photo',//要提交到的处理文件上传后台文件
			   'scriptData':{'studentId':$('#studentId').val()},
	           'method':'GET',
			    'folder': '${pageContext.request.contextPath}/${initParam.studentPhotoFolder}',//要上传到哪个目录下,可以使用../../images这样的路径
			   'auto'           : false, //是否自动开始   
	           'multi'          : false, //是否支持多文件上传   
	           'buttonText'     : 'Choice', //按钮上的文字   
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
	<style>
		.main{
	 		width: 960px;
	 		margin: 0 auto;
	 		margin-top: 30px;
		}
		.top{
			text-align: center;
		}
		.info{
			 width: 830px;
			 margin-right: 240px;
			 text-align: center;
			 margin-top: 40px;
			 padding: 30px;
		}
		.photo{
			text-align: left;
			width: 260px;
			float: left;
		}
	</style>
  </head>
  
  <body class="main">
  	<div class="top">
 		<h1>个人信息更改</h1><br/>学生编号：${sessionScope.studentRole.userId}<input type="hidden" id="studentId" value="${sessionScope.studentRole.userId}"/>
 	</div>
<hr/>
	<div class="photo">
		<img id="studentPhoto" src="${pageContext.request.contextPath}/${initParam.studentPhotoFolder}/${studentRole.photo}" onerror= "javascript:this.src='${pageContext.request.contextPath}/images/loadError.jpg'"  width='140' height='170'/>
	 	<input type="file" name="uploadify" id="uploadify" /> 
	   	<a href="javascript:$('#uploadify').uploadifyUpload()">上传</a>|  
	   	<a href="javascript:$('#uploadify').uploadifyClearQueue()">取消上传</a> 
	   	<div id="fileQueue"></div> 
    </div>
	<div class="info">
		<form id="infoForm" action="${pageContext.request.contextPath}/student/account_update_info" method="post">
			姓名:<input name="name" type="text" value="${studentRole.name}" reg="\S+" tip="不能为空！"/>
		&nbsp;&nbsp;
			电话:<input name="telephone" type="text" value="${studentRole.telephone}" reg="[\d-]+" tip="由数字和-组成,不能为空！"/>
		&nbsp;&nbsp;
			性别:<input type="radio" name="sex" value="1" ${studentRole.sex=='1'?'checked':''}/>男
			   <input type="radio" name="sex" value="0" ${studentRole.sex=='0'?'checked':''}/>女
			<br/>	<br/>
			<input id="updateInfo" type="submit" value="更新" onclick="clearPwd()"/>&nbsp;&nbsp;<input type="reset" value="重置"/>
		</form>
		<hr/>
		<form id="pwdForm" action="${pageContext.request.contextPath}/student/account_update_pwd" method="post">
			重置新密码:<input id="freshF" name="freshF"  type="password" reg="\w{5,}" tip="密码至少5位，由数字/字母/下划线组成！" value="123456" />
		&nbsp;&nbsp;
			确认新密码:<input id="freshS" name="freshS" type="password" reg="\w{5,}" tip="请确保与新密码输入一致！" value="123456" />
			<br/><br/>
			<input id="updatePwd" type="submit" value="更新"/>&nbsp;&nbsp;<input type="reset" value="重置"/><br/>
		</form>
	</div>
</body>
</html>
<%@ include file="/jspf/showMessageContent.jspf" %>
