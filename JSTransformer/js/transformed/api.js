defineClass("File");
var ui = api.__c("get", "ui");
var http = api.__c("get", "http");
var f = new File(arguments.__g(0));
var o = {};
var line;
while ((line = f.__c("readLine")) != null) 
  {
    o[line] = true;
  }
for (i in o) 
  {
    print(i);
  }
