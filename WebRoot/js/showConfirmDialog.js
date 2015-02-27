//显示确认对话框
function showConfirmDialog(redriectUrl){
	//alert('1');
	jConfirm('确定移除当前记录?', '来自页面的消息', function(r) {
		//alert(r);
		if(r==true){
			window.location.href=redriectUrl;	
		}
	});
}


