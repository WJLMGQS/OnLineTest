
//增加或删除行，用来实现多选题的选项设置
function addOrDeleteMultipleRow(i){
  $("#checkBoxTable tr").eq(1).hide(); //隐藏模板tr //eq返回jquery对象，下标从0开始
  $("#BtAdd").click(function() { 
	  if(i>=10){
		  jAlert('本系统暂时只支持10个多选项！', '服务端消息');
		  return;
	  }
	  var tr = $("#checkBoxTable tr").eq(1).clone(); 
	  tr.find("td").get(0).innerHTML = ++i;//get返回的是html数组
	  tr.find("td").get(3).innerHTML = "<input id='KR' type='checkbox' name='results'  value='"+i+"' />";
	  tr.show(); 
	  tr.appendTo("#checkBoxTable"); 
  }); 
  $("#BtDel").click(function() { //下面遍历的时候不包括遍历模板行
	$("#checkBoxTable tr:gt(1)").each(function() {//tr:gt(index)表示所有大于index+1的行元素；如：gt(0),表示第一行后面的所有元素
		if ($(this).find("#CK").get(0).checked == true) { 
			$(this).remove(); 
		} 
	}); 
	i = 0; 
	$("#checkBoxTable tr:gt(1)").each(function() { 
		$(this).find("td").get(0).innerHTML = ++i; 
		$(this).find("td").get(3).innerHTML = "<input id='KR'  type='checkbox' name='results' value='"+i+"' />";//同步更近每个选项答案的value值,并打伤全选的表示id
	}); 
	$("#CKA").attr("checked", false);
	$("#CKR").attr("checked", false);
  }); 

  $("#CKA").click(function() { 
	  $("#checkBoxTable tr:gt(1)").each(function() { 
		$(this).find("#CK").get(0).checked = $("#CKA").get(0).checked; 
	  }); 
  }); 
  $("#CKR").click(function() { 
	  $("#checkBoxTable tr:gt(1)").each(function() { //这里tr:gt(1)表示从找到的第1个tr开始遍历,项目中的模板行没有id属性
		$(this).find("#KR").get(0).checked = $("#CKR").get(0).checked; //这里get(0)表示在find到的一堆标签中取出第一个标签对象
	  }); 
  }); 
	
}

//专门用来检测多选题时空白选项和没有答案选中
function validateMultipleFormBlank(){
	var isRight = true;//是否为空
	var isExist = false;//题目是否存在
	 $("#checkBoxTable tr:gt(1)").each(function() { //检测空格处是否为空
		 if(isExist==false){
			 isExist = true;			 
		 }
	 	if($.trim($(this).find("#TName").get(0).value).length==0){//选项内容去掉前后空格后的字符长度
	 		//alert($.trim($(this).find("#TName").get(0).value).length);
	 		isRight = false;
	 	}
	});
	 if(isExist==false){
		  jAlert('请至少创建一个选项！', '服务端消息');
		 return false;
	 }else{
		 if(isRight==true){//题目存在，且文本不为空的情况下判断是否有答案
			 isRight = false;
			 $("#checkBoxTable tr:gt(1)").each(function() {  
				 if($(this).find("#KR").get(0).checked){
					 if(isRight==false){
						 isRight = true;	 
					 }
				 }
			});
		 }else{
			 jAlert('选项的内容不能为空！', '服务端消息');
			 return false;
		 }
	 }

	 if(isRight==false){
		 jAlert('请至少选中一个选项答案！', '服务端消息');
		 return false;
	 }else{
		 return true;	 
	 }
}
