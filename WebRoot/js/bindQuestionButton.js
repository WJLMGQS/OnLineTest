$(document).ready(function() {
	$("#checkReset").click(function() { 
		$("#eachRow").find("#options").attr("checked", false);
	}); 
	$("#checkInsert").click(function() { 
		var rarr = new Array();
		$("#eachRow").find("#options").each(function() { 
			var res = $(this).attr("checked");
			if(res){
				rarr.push($(this).attr("title"));
			}
		}); 
		if(rarr.length<=0){
			 jAlert('您尚未选择任何试题，不能进行试题插入！', '来自页面的消息');
			 return false;
		}else{
			var url = $("#checkInsert").attr("href");
			url = url+'Ids='+rarr;
			$("#checkInsert").attr("href",url);
			return true;
		}
	});
});
