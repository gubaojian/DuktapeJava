importClass("com.furture.react.activity.test.TestRef")


/**
 *  测试引用，
 * javascript 到java的引用，可以是多个javascript对象引用一个Java对象。保持单一引用并不好。
 * java引用同一个javascript对象是单一的。
 */

var a = {say:function(){
	  print("save hello world");
}};

var testRef = new TestRef(a);

var b = testRef.callJsRef(a);

for(var i=0; i<10; i++){
	testRef.callJsRef(a);
	testRef.callJsRef(b);
}

b.say();
a.say();
print(a == b)

for(var i=0; i< 10; i++){
     testRef.javaRef();
}


true;