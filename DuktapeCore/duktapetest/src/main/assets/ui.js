

ui.toast("你好，你好，你好");


ui.toast("33333");


ui.alert("提示","我的ui", "确定", function(){
	
	ui.toast("确定");
	
}, "取消", function(){
	
	ui.toast("取消");
});

ui.alert(null,"我的ui", "确定", function(){
	
	ui.toast("确定");
	
}, "取消", function(){
	
	ui.toast("取消");
});



true;
