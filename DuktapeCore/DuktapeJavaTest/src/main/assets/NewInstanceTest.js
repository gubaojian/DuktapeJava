importClass("com.example.duktapetest.Person");
importClass("com.example.duktapetest.VarPerson");
importClass("junit.framework.Assert");

/**
 *  测试正常的创建类的实例
 */
function testNormalNew(){
    var person = new Person();
    Assert.assertNotNull("new person success", person);
}
testNormalNew();

//多个可变参数
function testVarArgsNew(){

    var person = new VarPerson();
    Assert.assertNotNull("new person success args 0", person);

    var person = new VarPerson("xiaoming");
    Assert.assertNotNull("new person success args 1", person);


    var person = new VarPerson("xiaoming", "li lei");
    Assert.assertNotNull("new person success args 2", person);
}

testVarArgsNew();



true;