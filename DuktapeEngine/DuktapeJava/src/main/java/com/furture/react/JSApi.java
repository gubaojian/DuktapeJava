package com.furture.react;

import java.util.HashMap;
import java.util.Map;

import android.widget.BaseAdapter;

import com.furture.react.ext.JSBaseAdapter;

public class JSApi {

	
	 /**
	  * JavaClassMap，用于在JS中实现Java抽象类,通过用具体的非抽象类,代替抽象类进行实现
	  * */
	 private static Map<Class<?>, Class<?>> javaClassMap = new HashMap<Class<?>, Class<?>>();
	 static{
		javaClassMap.put(BaseAdapter.class, JSBaseAdapter.class);
	 }

	/**
	 * JavaObjectMap，用于定位引擎初始化时,默认引入的对象.
	 * */
	 private static Map<String, Object> javaObjectMap = new HashMap<String, Object>();
	 
	 
	 
	 public static void registerJavaClass(Class<?> abstractClass, Class<?> targetClass){
		    javaClassMap.put(abstractClass, targetClass);
	 }
	 
	 public static Map<Class<?>, Class<?>> getJavaClassMap(){
		    return javaClassMap;
	 }
	 
	 public static void registerJavaObject(String name, Object object){
		 javaObjectMap.put(name, object);
	 }
	 
	 public static Map<String, Object> getJavaObjectMap(){
		 return javaObjectMap;
	 }
	
}
