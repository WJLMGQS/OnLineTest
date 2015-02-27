  $(document).ready(function(){  
$(".title").each(function(){
	$(this).click(function(){  
    	var $hiddenDiv = $(this).next();
    	$hiddenDiv.toggle("normal");
    	$hiddenDiv.toggleClass("title2");
        });
    });
});