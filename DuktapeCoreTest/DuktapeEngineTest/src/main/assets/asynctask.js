/**
 * 会线程死锁，目前多线程在heap被销毁时，影响垃圾回收, 继续执行很容易crash。
 * 暂时不支持多线程
 */
 importClass("android.os.AsyncTask")
 importClass("android.widget.Toast")
 importClass("java.lang.Thread")
 
 var activityListener = {};
 
 var asyncTask = new AsyncTask({
	 doInBackground:function(){
		   return "doInBackground";
	 },
	 onPostExecute : function(message){
		 Toast.makeText(activity, "onPostExecute" + message, Toast.LENGTH_SHORT).show();
	 }
 });
 
 asyncTask.execute();
 asyncTask.get();
 
 asyncTask = new AsyncTask({
	 doInBackground:function(msg){
		   Thread.sleep(20000);
		   return "doInBackgroundTwo" + msg;
	 },
	 onPostExecute : function(message){
		 Toast.makeText(activity, "onPostExecute" + message, Toast.LENGTH_SHORT).show();
	 }
 });
 
 asyncTask.execute("Hello");
 
 activityListener.finish = function(){
	 asyncTask.cancel(true);
	 asyncTask.get();
 }
 //asyncTask.get();
 
 
 true;
 
 