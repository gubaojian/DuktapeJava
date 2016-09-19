/**
 * 
 */

var activityListener = {};
activityListener.onCreate = function(){
	print("activity onCreate");
	var listView = activity.findViewById(R.id.listView1);
	//listView.setAdapter(new );
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