	$(document).ready(function(){
			$(".imageLink").each(function(){
				var link = $(this).attr("value");
				$(this).parent().attr("href",link);
				$(this).parent().children("img").attr("src",link);
			});
		});
	//在试题显示的时候需要批量的显示试题的图片，但是不能直接写出src和href只能动态生成，这里通过hidden记录对应的试题图片路径然后赋值