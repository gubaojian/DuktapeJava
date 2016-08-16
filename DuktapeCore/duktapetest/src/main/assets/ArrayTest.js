importClass("junit.framework.Assert");



function testArray(){
    var array = ["item0", "item1", "item2"];

    Assert.assertEquals(array[0], "item0");
    Assert.assertEquals(array[1], "item1");

    var obj = {};

    obj.years = array;

    Assert.assertEquals(obj.years[0], "item0");
    Assert.assertEquals(obj.years[1], "item1");
}
testArray();



true;