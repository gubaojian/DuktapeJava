importClass("com.example.duktapetest.TestJSRefGet")
importClass("junit.framework.Assert");

var test = new TestJSRefGet();


var json = {"name":"gubaojian", "love": "coding"};
test.testGet(json);
Assert.assertEquals(json["name"],"gubaojian-set");


var array = ["index 0", "index 1", "index 2", "index 3"];
test.testGetLength(array);
Assert.assertEquals(array["0"],"set-0");

true;