importClass("com.furture.react.activity.test.TestRef")


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