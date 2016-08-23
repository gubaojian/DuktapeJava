importClass("junit.framework.Assert");
/**
 * 单纯的JavaScript代码，测试JavaScript正常执行以及错误信息的显示
 */
function testPerson(){
    function Person(firstName, lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
    }

    Person.prototype.getName = function() {
            return this.firstName + ' ' + this.lastName;
    }

    Person.prototype.show = function(prefix) {
                return prefix + this.firstName + ' ' + this.lastName;
    }

    var person = new Person("Baojian", "Gu");


    print("person.getName() " + person.getName());
    print("person.show " + person.show("China"));
    Assert.assertEquals("Baojian Gu Equals", person.getName(), "Baojian Gu");
    Assert.assertEquals("China Baojian Gu Equals", person.show("China"), "ChinaBaojian Gu");

}

testPerson();




true;

