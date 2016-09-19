importClass('com.furture.react.JavaUtils');
importClass("android.content.Intent");
importClass("junit.framework.Assert");
importClass("java.lang.String");
importClass("com.example.duktapetest.Person");
importClass("com.example.duktapetest.SubPerson");
importClass("com.example.duktapetest.People");
importClass("java.util.HashMap");
importClass("java.util.ArrayList");
importClass("org.json.JSONObject");
importClass("java.lang.reflect.Array");

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


  /**
   *  测试访问Bean表达式
   *  Map  List， Object JSONObject Array等
   */
   /**
    * 测试访问get set 以及 is方法， 其中is方法包含自动装箱
    */
  function testBeanSetGetMethod(){
    var person = new Person();
    person.name = "setNameVia.Name";
    Assert.assertEquals(person.name, "setNameVia.Name");
    Assert.assertEquals(person.getName(), "setNameVia.Name");

    person.setName("setNameViaSetName");
    Assert.assertEquals(person.name, "setNameViaSetName");
    Assert.assertEquals(person.getName(), "setNameViaSetName");
    Assert.assertEquals(person['name'], "setNameViaSetName");

    Assert.assertEquals(person.china, false);
    person.china  = true;
    Assert.assertEquals(person.china, true);
    Assert.assertEquals(person.isChina(), true);
  }
  testBeanSetGetMethod();



   /**
   *  map类型测试
   */
   function testMapSetGetMethod(){
       var map = new HashMap();
       Assert.assertEquals(map.keyA, null);
       map.put("keyA", "HikeyAViaPut");
       Assert.assertEquals(map.keyA, "HikeyAViaPut");
       Assert.assertEquals(map.get("keyA"), "HikeyAViaPut");
       Assert.assertEquals(map['keyA'], "HikeyAViaPut");


       map.keyA = "KeyAViaSet";
       Assert.assertEquals(map.keyA, "KeyAViaSet");
       Assert.assertEquals(map.get("keyA"), "KeyAViaSet");
       Assert.assertEquals(map['keyA'], "KeyAViaSet");


       var person = new Person();
       Assert.assertNull("person sites is null", person.sites);
       person.sites = map;
       Assert.assertEquals(person.sites.keyA, "KeyAViaSet");
    }
    testMapSetGetMethod();



  /**
   *  list类型测试
   */
   function testListSetGetMethod(){
       var list = new ArrayList();
       for(var i=0;i<10; i++){
         list.add("item" + i);
       }
       Assert.assertEquals(10, list.size());
       Assert.assertEquals(list.get(0), "item0");
       Assert.assertEquals(list.get(1), "item1");

      //支持这种方式访问未
      Assert.assertEquals(list[0], "item0");
      Assert.assertEquals(list[1], "item1");

      var person = new Person();
      Assert.assertNull("person sites is null", person.loves);
      person.loves = list;
      Assert.assertEquals(person.loves.get(0), "item0");


    }
    testListSetGetMethod();


  /**
   *  json类型测试
   */
   function testJSONSetGetMethod(){
       var json = new JSONObject();
       for(var i=0;i<10; i++){
         json.put(i, "item" + i);
       }

       Assert.assertEquals(json.opt(0), "item0");
       Assert.assertEquals(json.opt(1), "item1");

       //未转化为javascript的array,但支持此种方式访问
       //double  用0 取不到
       Assert.assertEquals(json[0], null);
       Assert.assertEquals(json['0.0'], 'item0');
    }
    testJSONSetGetMethod();



  /**
   *  array类型测试
   */
   function testArraySetGetMethod(){
       var years = ['item0', 'item1'];
       var array = Array.newInstance(String, 2);
       Array.set(array, 0, "item0");
       Array.set(array, 1, "item1");
       var person = new Person();
       Assert.assertNull("person sites is null", person.years);
       person.years = array;
       Assert.assertEquals(person.years[0], "item0");


    }
    testArraySetGetMethod();


  /**
   *  测试callNew方法，强制指定构建函数
   */
  function testCallNew(){
     var subPerson = new SubPerson();
     var people = new People(subPerson);
     Assert.assertEquals("constructor from subperson", people.from, "SubPerson");


     var callNewPeople = JavaUtils.callNew(People, "com.example.duktapetest.Person", person);
     Assert.assertEquals("constructor from subperson", people.from, "Person");

     var callNewSubPeople = JavaUtils.callNew(People, "com.example.duktapetest.SubPerson", person);
     Assert.assertEquals("constructor from subperson", people.from, "SubPerson");
  }

 /**
  *
  *  FIXME 测试getInvokeMethod方法
  */





   true;

