importClass("android.content.Intent")
importClass("java.lang.System")
importClass("android.view.View")
importClass("android.view.View.OnClickListener")


/**
 * 目前性能 10000次循环调用 3000-4000ms
*/

var times = 10000;

var start = System.currentTimeMillis();
var intent = new Intent();
var json = {
    data: 1,
};
for(var i=0; i<10000; i++){
    var view = new View(activity);
    var func = function(){
    };
    view.setOnClickListener(new OnClickListener(func));
    view.setOnClickListener(func);
    intent.putExtra('string' + i,  i);
    intent.putExtra('string' + i,  i);
    intent.putExtra('string' + i,  i);
    intent.putExtra('string' + i,  i);
    intent.putExtra('string' + i,   view); //放到Intent中一直, 测试内存压力下的表现

    json.data = json.data + 1;
    intent.putExtra('string' + i,  json);
}

print("performance test "+ times + " times used " + (System.currentTimeMillis() - start) + " ms ");

true;