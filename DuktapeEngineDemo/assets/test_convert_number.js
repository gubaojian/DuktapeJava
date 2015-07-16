/**
 * 
 */
importClass("android.widget.Toast")
importClass("java.lang.Integer")
importClass("java.lang.Double")

var a = new Number(20);
var numInt = Integer.valueOf(a)
print(numInt);
print(numInt.getClass());

var numDouble = Double.valueOf(a);

print(numDouble);
print(numDouble.getClass());

var addNum = numDouble + numInt;

print("addNum"  + addNum);
print("addNum"  + (addNum == 40));
print(addNum);

if(numDouble.equals(numDouble)){
	print("40 == 40");
}
if(!numInt.equals(numDouble)){
	print("false == 40");
}

if(parseInt(numInt) == parseInt(numDouble)){
	print("40 == 40");
}

a = new Number(3);
numInt = Integer.valueOf(a)

Toast.makeText(activity, "中文", Toast.LENGTH_SHORT).show();

true;