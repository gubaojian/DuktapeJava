importClass("android.widget.ImageView")
importClass("android.widget.LinearLayout$LayoutParams", "LayoutParams")
importClass("java.lang.Runnable")
importClass("java.lang.Thread")

print('Hello world');
print(activity);


appName = activity.getBaseContext().getResources().getIdentifier("app_name", "string", "com.furture.react");
print("appName" + appName   + " ")

print("Activity.RESULT_OK" +   activity.RESULT_OK);
	    
print("R.id.execute" +  R.id.execute);
	    
l = "love";
l = l.substring(1);

print(l + "appName" + R.id.execute )

var textView = activity.findViewById(R.id.execute);
print("textView" + textView);

var imageView = new ImageView(activity);

print("imageView" + imageView);

print("imageView" + imageView);

print("imageView" + ImageView.VISIBLE);

text = "";
for(i=0; i<10;  i++){
   text += " text " + i  + "\n";
}


textView.setText("JavaScript" + Date() + text);


container = activity.findViewById(R.id.container);

print(container);

imageView.setImageResource(R.drawable.ic_launcher);

var param = new LayoutParams(400, 400);
container.addView(imageView, param);
param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
param  = new LayoutParams(200, 200);

print(param);


a = function(){
		print("javascript runable called" + param);
};

task = new Runnable(a);

task.run();

task.run();

task = new Runnable(function(){
		print("javascript runable called" + param);
});

task.run();

task.run();

task = new Runnable({run:function(){
	print("javascript object runable called" + param);
}});

task.run();

task.run();


try{
	var ak;
	ak.b.c
	aj.cc()
}catch(e){
   print(e, e.lineNumber);
}

a = "javascript return value";

b = {"object":"ddd"}

true;
