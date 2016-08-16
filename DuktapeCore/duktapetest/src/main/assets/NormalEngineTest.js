
/**
 *  测试引擎的主功能
 */
importClass("android.content.Intent")
importClass("java.lang.System")
importClass("android.view.View")
importClass("android.view.View.OnClickListener")


/**
 * 测试New
 */
function testNew(){
    var intent = new Intent();
    var view = new View(activity);
    print("testNew " + view + " " + intent);
}
testNew();


/**
 * 测试主方法
 */
function testMethod(){
    var intent = new Intent();
    var view = new View(activity);
    view.setOnClickListener(new OnClickListener(func));
    view.setOnClickListener(func);
    intent.putExtra('string' + i,  i);
}

testMethod();



true;