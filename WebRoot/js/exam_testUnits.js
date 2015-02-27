//该js依赖	<%--ajax异步消息函数--%>optionAjax.js

//显示确认对话框
function confirmDialog(showMessage,type,results){
	jConfirm('第'+showMessage+'题尚未完成，确定提交？', '来自页面的消息', function(r) {
		if(r){
			commitResoponse(type,results);
		}
	});
}

//检测用户是否有未保存的单选题，提交模板：type=0&results=A,D,C,B,C,D
function isSaveSingle(type){
	var rarr = new Array();
	var results = new Array();
	var singles = $(".defineTop");
	singles.each(function(){
		var result = $(this).find(":radio:checked");
		if(result.length<=0){
			rarr.push($(this).attr("tip"));
			results.push(null);
		}else{
			results.push(result.val());
		}
	});
	if(rarr.length>0){//尚未选中该题
		confirmDialog(rarr.toString(),type,results);
	}else{
		commitResoponse(type,results);
	}
}

//检测用户是否有为保存的判断题，提交模板：type=0&results=T,F,T,F,F
function isSaveJudge(type){
	var rarr = new Array();
	var results = new Array();
	var judges = $(".defineTop");
	judges.each(function(){
		var result = $(this).find(":radio:checked");
		if(result.length<=0){
			rarr.push($(this).attr("tip"));
			results.push(null);
		}else{
			results.push(result.val());
		}
	});
	if(rarr.length>0){//尚未选中该题
		confirmDialog(rarr.toString(),type,results);
	}else{
		commitResoponse(type,results);
	}
}

//检测用户是否有为保存的多选题，提交模板：type=0&results=A░B░C,D,E░F,D
function isSaveMultiple(type){
	var rarr = new Array();
	var results = new Array();
	var multiples = $(".defineTop");
	multiples.each(function(){
		var result = $(this).find("input[name=results]");
		var flag = true;//未填写
		var row = "";
		if($(this).find("input:checked").length>0){
			flag = false;
		}
		$(result).each(function(){
			if($(this).attr("checked")==true){
				row = row + "1░";
			}else{
				row = row + "░";
			}
		});
		if(flag==true){//表示该题有空的
			rarr.push($(this).attr("tip"));
		}
		results.push(row);
	});
	if(rarr.length>0){//尚未选中该题
		confirmDialog(rarr.toString(),type,results);
	}else{
		commitResoponse(type,results);
	}
}

//检测用户是否有为保存的填空题，提交模板：type=0&results=sdf░sdf░盛大富翁,耳光,sdf░为,说的
function isSaveFillBlank(type){
	var rarr = new Array();
	var results = new Array();
	var fillBlanks = $(".defineTop");
	fillBlanks.each(function(){
		var result = $(this).find("input[name=results]");
		var flag = false;
		var row = "";
		$(result).each(function(){
			var local = $(this).val();
			if( local==null || local==''){
				flag = true;
				row = row + "░";
			}else{
				row = row + local+"░";
			}
		});
		if(flag){//表示该题有空的
			rarr.push($(this).attr("tip"));
		}
		results.push(row);
	});
	if(rarr.length>0){//尚未选中该题
		confirmDialog(rarr.toString(),type,results);
	}else{
		commitResoponse(type,results);
	}
}


function submitExamPaper(examID){
	var url = "/OnLineTest/student/ajax/submit_exam?examID="+examID;
	url = covertURL(url);
	// 与服务端进行交互,并反馈结果
	$.get(url, null, function(data) {
		if(data=='T'){
			 jAlert('提示信息：考卷提交成功！');
			 setTimeout(function(){ location.href = '/OnLineTest/student/persion_home';},3000);
		}else{
			 jAlert(data);
		}
	});
}

//异步保存试题答案
function commitResoponse(type,results){
	// 设置与服务端进行交互的URL(包括中文处理),每次更改了知识点后就更新分页下标（由服务端更新）/teacher/ajax/knowledge2singles
	var url = "/OnLineTest/student/ajax/saveTestUnits?type=" + type + "&results=" + encodeURI(encodeURI(results));
	url = covertURL(url);
	// 与服务端进行交互,并反馈结果
	$.get(url, null, function(data) {
		 jAlert(data);
	});
}