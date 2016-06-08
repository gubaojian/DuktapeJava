importClass("android.widget.Toast")

http.get("http://cookiemapping.wrating.com/link.html", function(data){
	Toast.makeText(activity, data, Toast.LENGTH_SHORT).show();
});


http.post("http://cookiemapping.wrating.com/link.html", "{dddd}", function(data){
	Toast.makeText(activity, data, Toast.LENGTH_SHORT).show();
});


true;