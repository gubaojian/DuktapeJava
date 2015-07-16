### DuktapeJava
A Java wrapper for duktape javascript engine on android platform, which is tiny small 200-300KB. you can use any java class by just small engine 200-300kb so and 1000 line java code, give you endless power integrating  javascript with java. 

### Get start

new DuktapeEngnine instance and init

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		duktapeEngine = new DuktapeEngine();
		duktapeEngine.init();
		duktapeEngine.register("activity",this);
		duktapeEngine.execute(AssetScript.toScript(getBaseContext(), "duk.js"));
		duktapeEngine.callJs("activityListener", "onCreate", savedInstanceState);
	} 
    
    
    @Override
	protected void onDestroy() {
		if (duktapeEngine != null) {
			duktapeEngine.destory();
			duktapeEngine = null;
		}
		super.onDestroy();
	}
	

duk.js javascript code

    importClass("com.furture.react.R")
	importClass("android.view.View.OnClickListener")
	importClass("android.widget.Toast")
	importClass("java.lang.Runnable")
	
	
	var activityListener = {};
	activityListener.onCreate = function(){
			print("activity onCreate onJavaScript");
			activity.setContentView(R.layout.activity_duk)
			button1 = activity.findViewById(R.id.button1);
			button1.setOnClickListener(new OnClickListener(function(){
				Toast.makeText(activity, "Button1 Clicked", Toast.LENGTH_SHORT).show();
				var intent = new Intent(activity, "com.furture.react.activity.DetailActivity");
				activity.startActivity(intent);
			}));
		
		button2 = activity.findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener({
			onClick:function(){ 
			   Toast.makeText(activity, "Button2 Clicked", Toast.LENGTH_SHORT).show();
			}
		}));
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

###Seamless Integrating Java and Javascript
 
 javascript can call any javamethod, and new java class instance and interface. 
 
 
     importClass("android.widget.Toast")
     
     
     Toast.makeText(activity, "Javascript toast", Toast.LENGTH_SHORT).show();


java can also call any method or get property on javascript object.


      importClass("com.efurture.react.DataUtils")
      
      var data = {};
      data.count =  10;
      data.getItem = function(var index){
             return "Javascript Data " + index;
      }
      
      DataUtils.showData(data);
      
 DataUtils Java Code Sample   
   
      public class DataUtils{
      
         public static void showData(JSRef data){
             DuktapeEngine  engine =  data.getEngine();
             int count = (Integer)engine.callJsRef(data, "count");
             for(int i=0; i<count; i++){
                 Log.d("DataUtils", " Get JavaScript Data Success :  " + engine.callJsRef(data, "getItem", i));
             }
          }
      
      }   

### 参考资料

<a href="https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino/Scripting_Java">https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino/Scripting_Java</a>

<a href="http://www.w3schools.com/js/default.asp">http://www.w3schools.com/js/default.asp</a>


<a href="https://github.com/jasonsantos/luajava">https://github.com/jasonsantos/luajava</a>
 






    