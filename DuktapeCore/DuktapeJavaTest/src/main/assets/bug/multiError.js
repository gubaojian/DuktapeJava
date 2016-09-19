/**
 * 
 */
importClass("android.content.Intent")
importClass("java.lang.Integer")


var intent = new Intent();
var numInt = Integer.valueOf(11);

intent.putExtra("numInt", numInt);

var getInt = intent.getIntExtra("numInt", 0);

print(getInt  + "  " + numInt);
print(typeof numInt);
print("number "  + getInt + "   " + numInt);


var c = Integer.valueOf(numInt);
c = Integer.valueOf(c);
c = Integer.valueOf(c);
c = Integer.valueOf(c);
c = Integer.valueOf(c);

intent.putExtra("numInt", numInt);

getInt = intent.getIntExtra("numInt", 0);

print(getInt  + "  " + numInt);

print(getInt + numInt);

if(getInt == numInt){
	print("getInt.equals numInt ");
}

if(getInt == 2){
	print("getInt.equals 2");
}
print("print a.b =30");
print(getInt);
print("print a.b =30");
print("get");
print(getInt);


var a = new Number(20);

a.b = "int";
print(a)
print(a.b)
print(a + 30)

Integer.valueOf(a)


true;
