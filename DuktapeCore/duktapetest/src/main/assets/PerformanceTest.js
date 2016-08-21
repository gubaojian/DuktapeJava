importClass("android.content.Intent")
importClass("java.lang.System")
importClass("android.view.View")
importClass("android.view.View.OnClickListener")


/**
 * 目前性能
 * 模拟器 Nexus5
 * 10000次块调用 3000-4000ms
 *
 * 华为CL100 真机  13500ms
 *
 */

function performanceTest(){
    var times = 10000;

    var start = System.currentTimeMillis();
    var intent = new Intent();
    var json = {
        data: 1,
    };
    for(var i=0; i<times; i++){
        var view = new View(activity);
        var func = function(){
        };
        view.setOnClickListener(new OnClickListener(func));
        view.setOnClickListener(func);
        intent.putExtra('string' + i,  i);
        intent.putExtra('string' + i,  i);
        intent.putExtra('string' + i,  i);
        intent.putExtra('string' + i,  i);
        intent.putExtra('string' + i,   view); //放到Intent中一直, 测试10000个View内存压力下的表现
        json.data = json.data + 1;
        intent.putExtra('string' + i,  json);
    }

    print("performance test "+ times + " times used " + (System.currentTimeMillis() - start) + " ms ");


}

performanceTest();

true;