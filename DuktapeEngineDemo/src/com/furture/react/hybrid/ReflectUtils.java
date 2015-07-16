/**
 * 
 */
package com.furture.react.hybrid;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import android.support.v4.util.LruCache;

/**
 * @author 剑白
 * @date 2014年2月27日
 *  */
class ReflectUtils {
	
   private static final  LruCache<String, Method> methodsCache = new LruCache<String, Method>(8);
	
   public static Object getValue(Object target, String name) {
	 try {
		if (target instanceof Class<?>) {
			Field field =  ((Class<?>)target).getField(name);
			field.setAccessible(true);
			return field.get(null);
		}else {
			Field field =  target.getClass().getField(name);
			field.setAccessible(true);
			return field.get(target);
		}
	}catch (Exception e) {
		e.printStackTrace();
		return null;
	}
   }
   
   public static Object invoke(Object target, String name, Object... args) {
		 try {
			    Method method = findMethod(target, name, args);
			    if (method == null) {
					return null;
				}
			    if (target instanceof Class<?>) {
   	    	            return method.invoke(null, args);	
				}else {
					return method.invoke(target, args);	
				}
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	  }
   
   private static Method findMethod(Object target, String name, Object... args){
	    Class<?> c  = null;
	    if (target instanceof Class<?>) {
	    	    c = (Class<?>) target;
		}else {
			c = target.getClass();
		}
	    String key = c.getName() + "_"+ name;
	    Method cache = methodsCache.get(key);
	    if (cache != null) {
			return cache;
		}
	    for (; c != null; c = c.getSuperclass()) {
		   for (Method method : c.getDeclaredMethods()) {
		     if (name.equals(method.getName()) &&  isMatchParameterTypes(method.getGenericParameterTypes(), args) ) {
		    	    method.setAccessible(true);
		    	    methodsCache.put(key, method);
		    	    return method;
		    }
		  }
	   }
	   return null;
   }
   
    private static boolean isMatchParameterTypes(Type[] types,  Object... args){
    	     if (types.length !=  args.length) {
				return false;
		 }
    	     if (types.length  == 0 ) {
				return true;
		 }
    	     for(int i=0; i<args.length; i++){
    	    	       Object object = args[i];
    	    	       Type type = types[i];
    	    	       Class<?> cls = null;
    	    	       if (type instanceof Class<?>) {
						cls = (Class<?>) type;
			   }
    	    	       if (cls == null) {
					return true;	
			   }
    	    	       if (object.getClass() == type) {
					continue;	
			   }
    	     }
    	     return true;
    }
   
   
   
}
