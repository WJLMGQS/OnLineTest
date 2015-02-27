
//增加或删除行，用来实现填空题的选项设置
function addOrDeleteFillBlankRow(i){
	$("#checkBoxTable tr").eq(1).hide(); //隐藏模板tr //eq返回jquery对象，下标从0开始
	$("#BtAdd").click(function() { 
		  if(i>=10){
			  jAlert('本系统暂时只支持10个多选项！', '服务端消息');
			  return;
		  }
		  var tr = $("#checkBoxTable tr").eq(1).clone(); 
		  tr.find("td").get(0).innerHTML = ++i;//get返回的是dom对象
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
		}); 
		$("#CKA").attr("checked", false);
	}); 
	$("#BtIne").click(function() { //下面遍历的时候不包括遍历模板行
		$("#checkBoxTable tr:gt(1)").each(function() {//tr:gt(index)表示所有大于index+1的行元素；如：gt(0),表示第一行后面的所有元素
			if ($(this).find("#CK").get(0).checked == true) { 
				var appText = $(this).find("#TName").get(0).value+'░';
				$(this).find("#TName").get(0).value=appText;
			} 
		}); 
		i = 0; 
		$("#checkBoxTable tr:gt(1)").each(function() { 
			$(this).find("td").get(0).innerHTML = ++i; 
		}); 
		$("#CKA").attr("checked", false);
	}); 
	$("#CKA").click(function() { 
		  $("#checkBoxTable tr:gt(1)").each(function() { 
			$(this).find("#CK").get(0).checked = $("#CKA").get(0).checked; 
		  }); 
	}); 
	
}

//专门用来检测填空题时空白选项
function validateFillBlankFormBlank(){
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
		 jAlert('请至少创建一个空白项！', '服务端消息');
	 }else if(isRight==true){//题目存在，且文本不为空的情况下判断是否有答案
		 return true;
	 }else{
		 jAlert('选项的内容不能为空！', '服务端消息');
	 }
	 return false;
}
