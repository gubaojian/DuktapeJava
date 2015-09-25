package com.furture.react;

import java.util.HashMap;
import java.util.Map;

import android.widget.BaseAdapter;

import com.furture.react.ext.JSBaseAdapter;

public class JSApi {

	
	 /**
	  * JavaClassMap，用于在JS中定义Java抽象类的实现。
	  * */
	 private static Map<Class<?>, Class<?>> javaClassMap = new HashMap<Class<?>, Class<?>>();
	 static{
		javaClassMap.put(BaseAdapter.class, JSBaseAdapter.class);
	 }
	 
	 private static Map<String, Object> javaObjectMap = new HashMap<String, Object>();
	 
	 
	 
	 public static void registerJavaClass(Class<?> sourceClass, Class<?> targetClass){
		    javaClassMap.put(sourceClass, targetClass);
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
