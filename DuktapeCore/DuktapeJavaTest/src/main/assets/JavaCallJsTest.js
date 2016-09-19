importClass("com.example.duktapetest.TestCallJs")
importClass("junit.framework.Assert");


/**
 *  测试JavaScript和Java之间相互调用。
 */

function hello(){
   return "hello from javascript plain function"
}

var a  = {
    run : function(){
       return "hello from javascript ref"
    },
};

var page = {
   hello : function(){
      return 'hello from javascript page object function';
   },
};

var javaObject = new TestCallJs(a);
javaObject.testCallJSRef();
javaObject.testCallJSRefFunction(function(){
    return "hello from javascript ref function"
});


javaObject.testCallHello();
javaObject.testCallPageHello()


//return true pass test
true;