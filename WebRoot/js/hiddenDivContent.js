$(document).ready(function() {
	//$("div.hiddenContent").hide();//默认隐藏div，或者在样式表中添加.text{display:none}，推荐使用后者
	$(".hiddenTitle").css("cursor","pointer").click(function(){
		$(this).next(".hiddenContent").slideToggle("slow");
	})
});