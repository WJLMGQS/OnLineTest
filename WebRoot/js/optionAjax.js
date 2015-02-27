// 处理缓存问题
function covertURL(url) {
	var timeURL = (new Date()).valueOf();
	if (url.indexOf("?") >= 0) {
		url = url + "&t=" + timeURL;
	} else {
		url = url + "?t=" + timeURL;
	}
	return url;
}
//在2个下拉表的共同作用下生成第三个下拉表，这里的函数用来清除第三个表的值
function back2Default() {
	$("#careerId").html("<option value='0'>----请选择以下专业----</option>");
}
//有学院和年级共同决定专业列表
function grade_Department2career() {
	var opvalue1 = $("#departmentId").val();
	var opvalue2 = $("#gradeId").val();
	if(opvalue1<1||opvalue2<1){
		jAlert('请先选中学院和年级！', '来自页面的消息');
		return;
	}
	// 设置与服务端进行交互的URL(包括中文处理)
	var url = "/OnLineTest/teacher/ajax/grade_Department2career?opvalue1=" + encodeURI(encodeURI(opvalue1))+"&opvalue2="+encodeURI(encodeURI(opvalue2));
	url = covertURL(url);
	// 与服务端进行交互,并显示数据
	$.get(url, null, function(data) {
		$("#careerId").html(data);
	});
}
//部门下拉列表选中后生成辅导员下拉列表
function department2helpMan(option) {
	var opvalue = option.value;
	// 设置与服务端进行交互的URL(包括中文处理)
	var url = "/OnLineTest/teacher/ajax/department2helpMan?opvalue=" + encodeURI(encodeURI(opvalue));
	url = covertURL(url);
	// 与服务端进行交互,并显示数据
	$.get(url, null, function(data) {
		$("#helpId").html(data);
	});
}

//专业下拉列表选中后生成班级下拉列表
function career2classes(option) {
	var opvalue = option.value;
	// 设置与服务端进行交互的URL(包括中文处理)
	var url = "/OnLineTest/teacher/ajax/career2classes?opvalue=" + encodeURI(encodeURI(opvalue));
	url = covertURL(url);
	// 与服务端进行交互,并显示数据
	$.get(url, null, function(data) {
		$("#classId").html(data);
	});
}

//知识点下拉列表选中后生成单选题下拉列表
function knowledge2singles(option) {
	var opvalue = option.value;
	// 设置与服务端进行交互的URL(包括中文处理),每次更改了知识点后就更新分页下标（由服务端更新）/teacher/ajax/knowledge2singles
	var url = "/OnLineTest/teacher/ajax/knowledge2singles?opvalue=" + encodeURI(encodeURI(opvalue));
	url = covertURL(url);
	// 与服务端进行交互,并显示数据
	$.get(url, null, function(data) {
		$("#leftBox").html(data);
		$("#rightBox").html('');
		$("#questionImage").attr("src",'/OnLineTest/questionBankImage/defaultQuestBank.jpg'); 
	});
}
//根据科目从选修表中获取专业
function subject2career(option) {
	var opvalue = option.value;
	// 设置与服务端进行交互的URL(包括中文处理),每次更改了知识点后就更新分页下标（由服务端更新）/teacher/ajax/knowledge2singles
	var url = "/OnLineTest/teacher/ajax/subject2career?opvalue=" + encodeURI(encodeURI(opvalue));
	url = covertURL(url);
	// 与服务端进行交互,并显示数据
	$.get(url, null, function(data) {
		$("#careerId").html(data);
	});
}
//显示试题对应的图片
function question2imageUrl(option) {
	var opvalue = option.value;
	// 设置与服务端进行交互的URL(包括中文处理),每次更改了知识点后就更新分页下标（由服务端更新）/teacher/ajax/knowledge2singles
	var url = "/OnLineTest/teacher/ajax/question2imageUrl?opvalue=" + encodeURI(encodeURI(opvalue));
	url = covertURL(url);
	// 与服务端进行交互,并显示数据
	$.get(url, null, function(data) {
		$("#questionImage").attr("src",data); 
	});
}