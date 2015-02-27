$(document).ready(function() {
	$("a.preview").preview();	 //添加图片放大查看效果
	$("a.preview").each(function(){
		$(this).children("img").error(function() { //当图片不存在时显示提示图片
			//注：如果在标签中已经有默认值的话就不会执行这里的函数
			$(this).attr("src",'/OnLineTest/images/loadError.jpg');
			$(this).parent().attr("title","试题图片加载出错");
			$(this).parent().attr("href",'/OnLineTest/images/loadError.jpg');
		});
	});
});

//提供给添加试题和显示试题  的 图片显示错误处理