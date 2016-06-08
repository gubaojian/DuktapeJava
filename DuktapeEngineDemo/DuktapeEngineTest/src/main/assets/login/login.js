/**
 * 
 */
importClass("android.view.View.OnClickListener")
importClass("android.widget.Toast")

var activityListener = {};
activityListener.onCreate = function(){
	activity.setContentView(app.inflate(activity, "login"))
	
}

activityListener.onStart = function(){ 
	print("activity onStart");
}

activityListener.onResume = function(){ 
	print("activity onResume");
}


activityListener.onPause = function(){ 
	print("activity onPause");
}

activityListener.onStop = function(){
	print("activity onStop");
}

activityListener.finish = function(){
	print("activity finish" + num);
}

