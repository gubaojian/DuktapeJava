importClass("com.furture.react.demo.R")
importClass("android.view.View.OnClickListener")
importClass("android.widget.Toast")
importClass("java.lang.Runnable")
importClass("android.content.Intent")
importClass("android.os.Bundle")
importClass("com.furture.react.activity.test.TestRef")




var activityListener = {};
var extras;
var num;

activityListener.onCreate = function(){
	print("activity onCreate");
	activity.setContentView(R.layout.activity_duk)
	button1 = activity.findViewById(R.id.button1);
	button1.setOnClickListener(new OnClickListener(function(){
		Toast.makeText(activity, "duk.js JavaScript Button1 Clicked", Toast.LENGTH_SHORT).show();
		var intent = new Intent(activity, activity.getClass());
		intent.putExtras(extras);
		activity.startActivity(intent);
	}));
	
	button2 = activity.findViewById(R.id.button2);
	button2.setOnClickListener(new OnClickListener({
		onClick:function(){ 
		   Toast.makeText(activity, "duk.js JavaScript Button2 Clicked", Toast.LENGTH_SHORT).show();
		  
		   var a = {say:function(){
		   	  print("save hello world");
		   }};

		   var testRef = new TestRef(a);

		   var b = testRef.callJsRef(a);

		   for(var i=0; i<1000; i++){
		   	testRef.callJsRef(a);
		   }
		}
	}));
	
	button3 = activity.findViewById(R.id.button3);
	button3.setOnClickListener(new OnClickListener({
		onClick:function(){ 
		   Toast.makeText(activity, "duk.js JavaScript Button3 Clicked", Toast.LENGTH_SHORT).show();
		   activity.callTest();
		}
	}));
	
		extras = activity.getIntent().getExtras();
		//print("extras "  + extras)
		if(extras == null){
			extras = new Bundle();
		}
		num  = extras.getInt("num", 0);
		extras.putInt("num", parseInt(num) + 1);
		button2.setText("duk.js Button Num " + num);

		/**
		if(num < 100){
			button2.postDelayed(new Runnable(function(){
				print("ok");
				var intent = new Intent(activity, activity.getClass());
				intent.putExtras(extras);
				activity.startActivity(intent);
				
			}), 3000);
		}*/
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

activity.callTest();

