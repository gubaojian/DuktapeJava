### DuktapeJava
JavaScript Engine on android platform base on Duktape, which is tiny, powerful, low memory cost. you can use any java method in javascript by just small engine, give you endless power integrating java with javascript on android platform.

#### Quick Start

1、use engine in your project

```bash
compile 'com.furture.react:DuktapeJava:1.0.0'
```

2、new DuktapeEngine instance,put js context then execute javascript code.

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	duktapeEngine = new DuktapeEngine();
	duktapeEngine.put("activity",this);
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

```

3、duk.js javascript code sample

```javascript

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
};

 Toast.makeText(activity, "Hello World JavaScript", Toast.LENGTH_SHORT).show();

```
### Javascript Guide

1、JavaScript call java method like java and support new instance.

```javascript
     importClass("android.widget.Toast")
     Toast.makeText(activity, "Javascript Hi Toast", Toast.LENGTH_SHORT).show();
```

```javascript
importClass("android.view.View")

var view = new View(activity);
```
2、JavaScript new java Interface

```javascript
importClass("android.view.View.OnClickListener")
view.setOnClickListener(new OnClickListener(function(){
	Toast.makeText(activity, "Button1 Clicked", Toast.LENGTH_SHORT).show();
	var intent = new Intent(activity, "com.furture.react.activity.DetailActivity");
	activity.startActivity(intent);
}));

view2.setOnClickListener(new OnClickListener({
	onClick:function(){
		 Toast.makeText(activity, "Button2 Clicked", Toast.LENGTH_SHORT).show();
	}
}));
```

```javascript
importClass("android.view.View")

var view = new View(activity);
```

3、JavaScript new java abstract instance with specific implemation

```javascript
importClass("com.furture.react.ext.JSBaseAdapter")


gridView.setAdapter(new JSBaseAdapter({
	getCount : function() {
		return 8;
	},
	getView : function(position,  convertView, parent){
			if(convertView == null){
					convertView = ui.fromXml(gridItemXml, null);
			}
			return convertView;
	},
	getViewTypeCount : function () {
			return 1;
	}
}));
```


### Java Guide

1、Java Call JavaScript Method

Java Code Sample   

```java
public class DataUtils{
	public static void showData(JSRef ref){
			int count = ((Number)ref.call("count")).intValue();
			for(int i=0; i<count; i++){
					Log.d("DataUtils", "Call JavaScript getItem Method :  " + ref.call("getItem", i));
			}
	 }
}   
```
javascript code sample
```javascript
  importClass("com.efurture.react.DataUtils")
  var data = {};
  data.count =  10;
  data.getItem = function(index){
         return "Javascript Data " + index;
  }
  DataUtils.showData(data);
```

### Config Common Context For Multi Engine Instance

JSConfig  Config Share Context For Multi DuktapeEngine, All Context Will Auto Be Import To DuktapeEngine. code sample

```java
JSConfig.put("application", application);
```

### Product Usage

  AssetScript class and JSTransformer.jar is only for development usage. for Product, you should not include it on your app. you can use JSTransformer.jar transform JavaScript source, then use transformed js in your app.

```bash
cd JSTransformer/js
java -jar JSTransformer.jar  ui.js  transformed/ui.js
```
   then use transformed/ui.js to your app.

   you can also use gulp tool. see dev-server demo.  write source in src/
   then javascript will auto be transformed in build/ directory

```bash
cd  dev-server
npm install
npm start
```


### Reference

<a href="http://gubaojian.github.io/DuktapeJava/javadoc/">DuktapeJava Java Docs</a>

<a href="https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino/Scripting_Java">https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino/Scripting_Java</a>

<a href="http://www.w3schools.com/js/default.asp">http://www.w3schools.com/js/default.asp</a>


<a href="https://github.com/jasonsantos/luajava">https://github.com/jasonsantos/luajava</a>
