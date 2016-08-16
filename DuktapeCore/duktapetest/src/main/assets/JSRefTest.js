importClass("com.example.duktapetest.TestRef")
importClass("junit.framework.Assert");

/**
 *  测试java和javascript引用关系
 * javascript 到java的引用，可以是多个javascript对象引用一个Java对象
 * java引用同一个javascript对象是单一的。
 *
 */
 function testJSRef(){
     var a = {
        sayHello:function(){
            print("save hello world");
        }
     };

     var testRef = new TestRef(a);
     var b = testRef.callJsRef(a);

     Assert.assertSame("jsRef should be same", a, b);
     Assert.assertTrue("jsRef should be same", a === b);
      for(var i=0; i<10; i++){
 	    var c =  testRef.callJsRef(a);
 	    var d = testRef.callJsRef(b);
 	    Assert.assertSame("jsRef should be same c a", c, a);
 	    Assert.assertSame("jsRef should be same c d", c, d);
    }
 }
 testJSRef();

 /**
 * 测试Java引用，多个javascript对象保持对一个java对象的引用。
 */
 function testJavaRef(){
     var a = {
        sayHello:function(){
            print("save hello world");
        }
     };
     var testRef = new TestRef(a);
     var javaRefA = testRef.javaRef();
     var javaRefB = testRef.javaRef();
     Assert.assertFalse("javaRef should not same javascript object", javaRefA === javaRefB);
     Assert.assertTrue("javaRef should  same java object", javaRefA.equals(javaRefB));
 }
 testJavaRef();



true;