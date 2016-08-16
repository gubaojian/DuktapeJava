package com.furture.react;

import java.util.HashMap;
import java.util.Map;

import android.widget.BaseAdapter;

import com.furture.react.ext.JSBaseAdapter;

public class JSApi {

	
	 /**
	  * 抽象类映射，在初始化抽象类时。可用通过映射到具体的实体类完成抽象类的初始化。
	  * 用于在JavaScript中实现Java抽象类,通过用具体的非抽象类,代替抽象类进行实现
	  * */
	 private static Map<Class<?>, Class<?>> javaClassMap = new HashMap<Class<?>, Class<?>>();
	 static{
		javaClassMap.put(BaseAdapter.class, JSBaseAdapter.class);
	 }

	 /**
	  * @param  abstractClass 抽象类
	  * @param  targetClass   抽象类的具体实现
	  * */
	 public static void registerAbstractClass(Class<?> abstractClass, Class<?> targetClass){
		    javaClassMap.put(abstractClass, targetClass);
	 }

	 /**
	  * 返回抽象类和具体类的具体映射，DukatepEnine引擎在处理抽象类利用映射关系，把抽象类转换成具体的类。
	  * */
	 public static Map<Class<?>, Class<?>> getAbstractClassMap(){
		    return javaClassMap;
	 }

	 /**
	  * @param  name 对象的名字
	  * @param  object java对象
	  * 所有引擎共享的全局对象以及Java对象。
	  * 对象会在DuktapeEngine初始化时放入到全局对象中供JavaScript调用
	  * */
	 public static void put(String name, Object object){
		 globalContextMap.put(name, object);
	 }

	 /**
	  * 返回全局的Context对象，DuktapeEngine会在初始化时自动导入对象。供JavaScript引擎使用
	  * */
	 public static Map<String, Object> getContext(){
		 return globalContextMap;
	 }

	 /**
	 * JavaObjectMap，用于定位引擎初始化时,默认引入的对象.
	 * */
	 private static Map<String, Object> globalContextMap = new HashMap<String, Object>();
}
