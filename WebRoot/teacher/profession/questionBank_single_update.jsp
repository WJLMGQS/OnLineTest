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
	<%--上传框架--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.uploadify.v2.1.0.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/swfobject.js"></script>
	<link  href="${pageContext.request.contextPath}/css/uploadify.css" rel="stylesheet" type="text/css" />
	<%--消息对话框框架--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.alerts.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.draggable.js"></script>
	<link href="${pageContext.request.contextPath}/css/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<%-- 图片放大显示插件--%>
 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.imagePreview.1.0.js"></script>
 	<%-- 绑定图片错误显示时的处理函数和代替图片--%>
 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bindSimpleImageView.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			var imageUrl = "${pageContext.request.contextPath}/${initParam.questionBankFolder}/${single.image}?random=" + (new Date()).getTime();
			$("#questionImage").attr("src",imageUrl);
			$("#floatImage").attr("href",imageUrl);
			
			$("#uploadify").uploadify({
			   'uploader': '${pageContext.request.contextPath}/swf/uploadify.swf?random=' + (new Date()).getTime(), 
			   'cancelImg':'${pageContext.request.contextPath}/images/cancel.png',
			   'script': '${pageContext.request.contextPath}/teacher/profession/questionBank_single_update_image',//*要提交到的处理文件上传后台文件
			   'scriptData':{'singleId':$('#singleId').val()},//*
	           'method':'GET',
			    'folder': '${pageContext.request.contextPath}/${initParam.questionBankFolder}',//*要上传到哪个目录下,可以使用../../images这样的路径
			   'auto'           : false, //是否自动开始   
	           'multi'          : false, //是否支持多文件上传   
	           'buttonText'     : 'browe', //按钮上的文字   
	          // 'buttonImg'		: '${pageContext.request.contextPath}/images/vtip_arrow.png',
	           'simUploadLimit' : 1, //一次同步上传的文件数目   
	           'sizeLimit'      : '${initParam.imageSizeLimit}', //设置单个文件大小限制   
	           'queueSizeLimit' : 1, //队列中同时存在的文件个数限制   
	           'fileDesc'       : '支持格式:jpg/gif/jpeg/png/bmp.', //如果配置了以下的'fileExt'属性，那么这个属性是必须的   
	           'fileExt'        : '*.jpg;*.gif;*.jpeg;*.png;*.bmp',//允许的格式     
	           onComplete: function (event, queueID, fileObj, response, data) {   
		           if(response.substr(response.lastIndexOf(".")+1).toLowerCase()=="jpeg"){
		        	    var urlImage = response+'?random='+ (new Date()).getTime();
		        	 	$("#questionImage").attr("src",urlImage); 
		        	 	$("#floatImage").attr("href",urlImage);
		        		jAlert('图片更新成功！', '服务端消息');
			       }else{
			    	 	jAlert(response, '服务端消息');
				    }
	           },   
	           onError: function(event, queueID, fileObj) {   
	               jAlert("图片:" + fileObj.name + "上传失败", '服务端消息'); 
	           },   
	           onCancel: function(event, queueID, fileObj){   
	               jAlert("取消了" + fileObj.name, '服务端消息');
	           }   
			});
		});		
	</script>
  </head>
  
  <body>
 	<center><h1>单选试题信息更改</h1>
 	<a href="${pageContext.request.contextPath}/teacher/profession/questionBank_single">后退</a></center>
 
<hr/>
<table>
<tr>
<td>
	<a id="floatImage" target="_blank" class="preview" title="单选试题图片">
		<img id="questionImage" width='250' height='200' />
 	</a>
 	<input type="file" name="uploadify" id="uploadify" /> 
   	<a href="javascript:$('#uploadify').uploadifyUpload()">上传</a>|  
   	<a href="javascript:$('#uploadify').uploadifyClearQueue()">取消上传</a> 
   	<div id="fileQueue"></div> 
     <br/>
</td>
			
<td> <center>
	<input type="hidden" value="${single.id }" id="singleId"/>
	<form id="infoForm" action="${pageContext.request.contextPath}/teacher/profession/questionBank_single_update_info?singleId=${single.id}" method="post">
		文本内容 <textarea rows="6" cols="60"   name="content" reg="\S+" tip="不能为空！">${single.content }</textarea><br/>
	   	选项A<input type="text" name="SA" width="180" reg="\S+" tip="不能为空！" value="${single.SA}"/>&nbsp;&nbsp;
	  	选项B<input type="text" name="SB" width="180" reg="\S+" tip="不能为空！" value="${single.SB}"/><br/>
		选项C<input type="text" name="SC" width="180" reg="\S+" tip="不能为空！" value="${single.SC}"/>&nbsp;&nbsp;         
		选项D<input type="text" name="SD" width="180" reg="\S+" tip="不能为空！" value="${single.SD}"/><br/>               
	   	选项答案:	
	   		A<input type=radio name="result" value="A" ${single.result=='A'?'checked':''}/>  &nbsp;&nbsp;
	   		B<input type=radio name="result" value="B" ${single.result=='B'?'checked':''}/>  &nbsp;&nbsp;
	   		C<input type=radio name="result" value="C" ${single.result=='C'?'checked':''}/>  &nbsp;&nbsp;
	   		D<input type=radio name="result" value="D" ${single.result=='D'?'checked':''}/>  &nbsp;&nbsp;  <br/>      
	   	难度<select name="difficulty" size="1" tip="请选择难度" reg="[0|1|2|3|4]+">
						<option value="0" ${single.difficulty.index=='0'?'selected':'' }>简单</option>
						<option value="1" ${single.difficulty.index=='1'?'selected':'' }>较简单</option>
						<option value="2" ${single.difficulty.index=='2'?'selected':'' }>中等</option>
						<option value="3" ${single.difficulty.index=='3'?'selected':'' }>较困难</option>
						<option value="4" ${single.difficulty.index=='4'?'selected':'' }>困难</option>
			</select>          
	  	知识点<select name="knowledgeId" size="1" tip="请选择知识点" reg="[1-9]+">
					<c:forEach items="${requestScope.knowledges}" var="knowledge" step="1">
						<option value="${knowledge.id}" }  ${single.knowledge.id==knowledge.id?'selected':''}>${knowledge.code}</option>
					</c:forEach>
			</select>     
		解析<textarea rows="3" cols="25" name="analyse" reg="\S+" tip="不能为空！">${single.analyse }</textarea><br/>
		<br/>
		<input id="updateInfo" type="submit" value="更新" />&nbsp;&nbsp;<input type="reset" value="重置"/>
	</form>
<hr/>		
</center></td>
</tr>
</table>
</body>
</html>
<%@ include file="/jspf/showMessageContent.jspf" %>
