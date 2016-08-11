importClass('com.furture.react.JavaUtils');
importClass("android.content.Intent");
importClass("junit.framework.Assert");
importClass("java.lang.String");


/**
 * 测试自动类型转换
 */
function testNormalCallNoneType(){
    var intent = new Intent();

    intent.putExtra("keyDouble2", 2);
    var value = intent.getDoubleExtra("keyDouble2", 0);
    Assert.assertEquals(value, 2);

    //类型转换成double， 获取失败，返回默认值0
    intent.putExtra("keyInt2", 2);
    var value = intent.getIntExtra("keyInt2", 0);
    Assert.assertEquals(value, 0);
}
testNormalCallNoneType();


/**
 * 指定方法的签名进行方法调用, 可以应对多态的情况
 */
function testCallWithType(){
    var intent = new Intent();
    JavaUtils.call(intent, "putExtra(String,int)", "keyInt2", 2);
    JavaUtils.call(intent, "putExtra(String,int)", "keyInt3", 3);
    var value = intent.getIntExtra("keyInt2", 0);
    Assert.assertEquals(value, 2);
}
testCallWithType();


/**
 * 可变参数测试, 测试三种场景，少一个参数，传入正常参数，传入多个参数
 */
 function testVarArgs(){
     var  hello = String.format("hello %s", "world");
     Assert.assertEquals(hello, "hello world");

     var  hello = String.format("hello");
     Assert.assertEquals(hello, "hello");

     var  hello = String.format("hello %s %s", "happy", "world");
     Assert.assertEquals(hello, "hello happy world");

 }
 testVarArgs();

 function testVarArgsWithCall(){
      var  hello = JavaUtils.call(String, "format(String,)", "hello %s", "world");
      Assert.assertEquals(hello, "hello world");

      var  hello = JavaUtils.call(String, "format(String,)", "hello");
      Assert.assertEquals(hello, "hello");

      var  hello = JavaUtils.call(String, "format(String,Object)", "hello %s %s", "happy", "world");
      Assert.assertEquals(hello, "hello happy world");

      var  hello = JavaUtils.call(String, "format(String,Object;)", "hello %s %s", "happy", "world");
      Assert.assertEquals(hello, "hello happy world");

  }
  testVarArgsWithCall();


// TODO
  /**
   *  测试访问Bean表达式
   *  Map  List， Object JSONObject Array等
   */
  function testSetMethod(){

  }




true;