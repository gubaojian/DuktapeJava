importClass("android.content.Intent")
importClass("java.lang.System")
importClass("android.view.View")
importClass("android.view.View.OnClickListener")

var start = System.currentTimeMillis();

var intent = new Intent();
for(var i=0; i<1000; i++){
    var view = new View(activity);
    view.setOnClickListener(new OnClickListener(function(){

    }));
    intent.putExtra('string' + i,  i);
}

print("performance test used " + (System.currentTimeMillis() - start) + " ms ");