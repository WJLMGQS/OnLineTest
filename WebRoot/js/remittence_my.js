function clearPwd(){
	//解决两个表单提交时都会受到密码校验的问题，
		$('#freshF').val("");
		$('#freshS').val("");
}
var isExtendsValidate = true;	//如果要试用扩展表单验证的话，该属性一定要申明
function extendsValidate(){	//函数名称，固定写法
	//密码匹配验证
	if( $('#freshF').val() == $('#freshS').val() ){	//匹配成功
		$('#freshS').validate_callback(null,"sucess");	//此次是官方提供的，用来消除以前错误的提示
		isExtendsValidate = true;
	}else{//匹配失败
		$('#freshS').validate_callback("密码不匹配","failed");	//此处是官方提供的API，效果则是当匹配不成功，pwd2表单 显示红色标注，并且TIP显示为“密码不匹配”
		isExtendsValidate = true;
		return false;
	}
}

//使用上面js时的条件：单个页面有两个表单，其中一个表单为内容，另一个为密码（2次:freshF,freshS）,如果表单内容中含有密码则不要使用name为freshF或freshS

