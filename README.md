### DuktapeJava
Java NDK wrapper for Duktape javascript engine on android platform, which is tiny, only 200-300KB so and 1000 line java code. you can use any java method in javascript by just small engine, give you endless power integrating javascript with java.

### Get start

new DuktapeEngnine instance then init it.

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		duktapeEngine = new DuktapeEngine();
		duktapeEngine.init();
		duktapeEngine.register("activity",this);
		duktapeEngine.execute(AssetScript.toScript(getBaseContext(), "duk.js"));
		duktapeEngine.call("activityListener", "onCreate", savedInstanceState);
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

### Seamless Integrating Java with Javascript

 javascript can call any java method, and new java class instance and interface.


     importClass("android.widget.Toast")


     Toast.makeText(activity, "Javascript toast", Toast.LENGTH_SHORT).show();


java can also call any method or property on javascript object.

      importClass("com.efurture.react.DataUtils")

      var data = {};
      data.count =  10;
      data.getItem = function(index){
             return "Javascript Data " + index;
      }

      DataUtils.showData(data);

 DataUtils Java Code Sample   

      public class DataUtils{

         public static void showData(JSRef data){
             DuktapeEngine  engine =  data.getEngine();
             int count = (Integer)engine.call(data, "count");
             for(int i=0; i<count; i++){
                 Log.d("DataUtils", " Get JavaScript Data Success :  " + engine.call(data, "getItem", i));
             }
          }

      }   

### Product Usage

  AssetScript class and JSTransformer.jar is only for development usage. for Product, you should not include it on your app. you can use JSTransformer.jar transform JavaScript source, then include js in your app.

    cd JSTransformer/js
    java -jar JSTransformer.jar  ui.js  transformed/ui.js

   then copy transformed/ui.js to your app.

   you can also use gulp tool. see dev-server.  write source in src/
   then javascript will auto be transformed in build/ directory

    cd  dev-server
    npm install
    npm start



### Reference

<a href="https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino/Scripting_Java">https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino/Scripting_Java</a>

<a href="http://www.w3schools.com/js/default.asp">http://www.w3schools.com/js/default.asp</a>


<a href="https://github.com/jasonsantos/luajava">https://github.com/jasonsantos/luajava</a>
