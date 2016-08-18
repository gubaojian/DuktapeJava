importClass("android.content.Intent")
importClass("java.lang.Integer")
importClass("junit.framework.Assert");
importClass('com.furture.react.JavaUtils');


/**
 * 数字自动转换成 double 类型
*/
function testInt(){
   var intent = new Intent();
   var numInt = Integer.valueOf(11);
   intent.putExtra("numInt", numInt);
   var getInt = intent.getIntExtra("numInt", 0);
   Assert.assertEquals("整数变为浮点数获取失败", 0, getInt);



   intent.putExtra("valueKey", '2');
    var getValue = intent.getStringExtra("valueKey");
    Assert.assertEquals("String类型唯一转换", getValue, '2');

}
testInt();


true;
