package com.furture.react;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import android.support.v4.util.LruCache;

import com.furture.react.ext.JSAynckTask;

public class JSApi {

	  private static Map<Class<?>, Class<?>> javaClassMap = new HashMap<Class<?>, Class<?>>();
	  static{
		  javaClassMap.put(AsyncTask.class, JSAynckTask.class);
	  }
	  public static void registerClassMap(Class<?> sourceClass, Class<?> targetClass){
		     javaClassMap.put(sourceClass, targetClass);
	   }
	
	
	
	public static final Object invoke(Object target, String methodName, Object ...args) throws Exception{
		Class<?> targetClass = null;
		if (target  instanceof Class<?>) {
			targetClass = (Class<?>) target;
		}else{
		    targetClass = target.getClass();
		}
		Method method = getMethod(targetClass, methodName, args);
		if (method == null) {
			throw new RuntimeException("Cann't find method " + methodName + " with args " + args + " on target " + target);
		}
		Object valueObject = null;
		Class<?>[] parameterTypes =  method.getParameterTypes();
		if (args.length == parameterTypes.length) {
			 return method.invoke(target, args);
		}
		int length = 0;
		if (args.length > parameterTypes.length) {
			length = parameterTypes.length;
		}else {
			length = args.length;
		 }
			if(length == 1){
				valueObject = method.invoke(target, args[0]);
			}else if(length == 2){
				valueObject = method.invoke(target, args[0], args[1]);
			}else if(length == 3){
				valueObject = method.invoke(target, args[0], args[1], args[2]);
			}else if(length == 4){
				valueObject = method.invoke(target, args[0], args[1], args[2], args[3]);
			}else if(length == 0){
				valueObject = method.invoke(target, (Object)new Object[]{});
			}else if(length == 5){
				valueObject = method.invoke(target, args[0], args[1], args[2], args[3], args[4]);
			}else if(length == 6){
				valueObject = method.invoke(target, args[0], args[1], args[2], args[3], args[4], args[5]);
			}else{
			  throw new RuntimeException( method.getName() + " method has too many arguments" + args.length);
			}
		   return valueObject;
	}
	
	public static Object newInstance(String className, Object ...args) throws Exception{
		  Class<?> targetClass = loadClass(className);
		  if (targetClass.isInterface()) {
			  Class<?>[] interfaces = {targetClass};
			  if (args == null || args.length < 0) {
				 throw new RuntimeException("interface " + className + " must have a javascript object or function constructor parameter");
			 }
			 return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfaces, new JSInvocationHandler(args[0]));
		  }
		  if(Modifier.isAbstract(targetClass.getModifiers())){
			  Class<?> sourceClass  = javaClassMap.get(targetClass);
			  if (sourceClass == null) {
				  throw new RuntimeException(" Cannot find abstract class implelation, please register implelation to javaClassMap. class name " + targetClass.getName());
			  }
			  targetClass = sourceClass;
		  }
		  if (args == null || args.length == 0) {
			  return targetClass.newInstance();
		  }
		  Constructor<?> constructor =  getClassConstructor(targetClass, args);
		  return constructor.newInstance(args);
	  }
	  
	
	public static Object get(Object target, String fieldName){
		  if (target == null) {
			  throw new RuntimeException(" Cannot get property '" + fieldName +"' of null");
		  }
		  if (target instanceof Map<?, ?>) {
			  Map<?,?> map = (Map<?, ?>) target;
			  return  map.get(fieldName);
		  }
		  if (target instanceof List<?>) {
			  List<?> list = (List<?>)target;
			  try {
					int index = Integer.parseInt(fieldName);
					return list.get(index);
			    }
				catch (Exception e) {
					throw new RuntimeException("invalid list index " + fieldName + " on target " + target, e);
				}
		  }
		  
		  Class<?> targetClass = target.getClass();
		  if (targetClass.isArray()) {
			  try {
					int index = Integer.parseInt(fieldName);
					return Array.get(target, index);
			  }
			  catch (Exception e) {
					throw new RuntimeException("invalid array index " + fieldName + " on target " + target, e);
			  }
		  }
		   

		  if (target instanceof Class<?>) {
			  targetClass = (Class<?>) target;
		  }
		  
		   String key = targetClass.getName() +  "@get@" + fieldName;
		   Method method = methodCache.get(key);
		   if (method != null) {
				try {
					return method.invoke(target);
				}catch (Exception e) {
					throw new RuntimeException("invoke get field method " + fieldName + " error on target " + target, e);
				}
			}
			
			Field field = fieldCache.get(key);
			if (field != null) {
				try {
					return field.get(target);
				}
				catch (Exception e) {
					throw new RuntimeException("invoke field get for " + fieldName + " error on target " + target, e);
				}
			}
			
			if (notExistMethodCache.get(key) != MARK) {
				try {
					String methodName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
					method = targetClass.getMethod(methodName);
					methodCache.put(key, method);
					return method.invoke(target);
				}catch (NoSuchMethodException notExistGetAccessError){
				    try {
				    	    String methodName = "is" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
						method = targetClass.getMethod(methodName);
						methodCache.put(key, method);
						return method.invoke(target);
					}
					catch (NoSuchMethodException notExistIsAccessError) {
						notExistMethodCache.put(key, MARK);
					}catch (Exception invokeError) {
						throw new RuntimeException("invoke is field  method for " + fieldName + " error on target " + target, invokeError);
						
					}
				}catch (Exception e) {
					throw new RuntimeException("invoke get field method " + fieldName + " error on target " + target, e);
				}
			}

			if (notExistFieldCache.get(key) != MARK) {
				try {
					field = targetClass.getField(fieldName);
					fieldCache.put(key, field);
					return field.get(target);
				}catch(NoSuchFieldException e){
					notExistFieldCache.put(key, MARK);
				}catch (Exception e) {
					throw new RuntimeException("invoke field get for " + fieldName + " error on target " + target, e);
				}
			}
			try {
				return loadClass(targetClass.getName() + "." + fieldName);
			} catch (ClassNotFoundException e) {}
			throw new RuntimeException("cann't find field " + fieldName + " get method on target class " + targetClass.getName());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void set(Object target, String fieldName, Object fieldValue){
		 if (target == null) {
			  throw new RuntimeException(" Cannot set property '" + fieldName +"' for null");
		  }
		  if (target instanceof Map) {
			  Map  map = (Map) target;
			  map.put(fieldName, fieldValue);
			  return;
		  }
		  if (target instanceof List<?>) {
			  List  list = (List)target;
			  try {
					int index = Integer.parseInt(fieldName);
					list.set(index, fieldValue);
					return;
			    }
				catch (Exception e) {
					throw new RuntimeException( "set property '" + fieldName + "'" + " invalid list index " + fieldName + " on target " + target, e);
				}
		  }
		  
		  Class<?> targetClass = target.getClass();
		  if (targetClass.isArray()) {
			  try {
					int index = Integer.parseInt(fieldName);
					Array.set(target, index, fieldValue);
					return;
			  }
			  catch (Exception e) {
					throw new RuntimeException("set property '" + fieldName + "'" + " invalid array index " + fieldName + " on target " + target, e);
			  }
		  }
		   

		  if (target instanceof Class<?>) {
			  targetClass = (Class<?>) target;
		  }
		  
		   String key = targetClass.getName() +  "@set@" + fieldName;
		   Method method = methodCache.get(key);
		   if (method != null) {
				try {
					 method.invoke(target, fieldValue);
					 return;
				}catch (Exception e) {
					throw new RuntimeException("invoke set method " + fieldName + " error on target " + target, e);
				}
			}
			
			Field field = fieldCache.get(key);
			if (field != null) {
				try {
					 field.set(target, fieldValue);
					 return;
				}
				catch (Exception e) {
					throw new RuntimeException("invoke set field method  for " + fieldName + " error on target " + target, e);
				}
			}
			
			if (notExistMethodCache.get(key) != MARK) {
				try {
					String methodName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
					method = targetClass.getMethod(methodName);
					methodCache.put(key, method);
					method.invoke(target, fieldValue);
					return;
				}catch (NoSuchMethodException notExistGetAccessError){
					notExistMethodCache.put(key, MARK);
				}catch (Exception e) {
					throw new RuntimeException("invoke set field method " + fieldName + " error on target " + target, e);
				}
			}

			if (notExistFieldCache.get(key) != MARK) {
				try {
					field = targetClass.getField(fieldName);
					fieldCache.put(key, field);
					field.set(target, fieldValue);
					return;
				}catch(NoSuchFieldException e){
					notExistFieldCache.put(key, MARK);
				}catch (Exception e) {
					throw new RuntimeException("invoke set field for " + fieldName + " error on target " + target, e);
				}
			}
			throw new RuntimeException("cann't set field " + fieldName +" with value " + fieldValue + " on target " + target);
	}
	
	
	  
	  public static Class<?> importClass(String className) throws ClassNotFoundException{
		  return loadClass(className);
	  }
	  
	 
	  
	  public static String getStackTrace(Throwable throwable) {
		     StringWriter sw = new StringWriter();
		     PrintWriter pw = new PrintWriter(sw, true);
		     if (throwable instanceof InvocationTargetException && throwable.getCause() != null) {
				 throwable.getCause().printStackTrace(pw);
			 }else{
			     throwable.printStackTrace(pw);
			 }
		     return sw.getBuffer().toString();
	 }
	  
	  private static Class<?>  loadClass(String className) throws ClassNotFoundException{
		  Class<?> targetClass = null; 
		  try {
			  targetClass = Class.forName(className);
		  } catch (ClassNotFoundException e) { 
			   int index = className.lastIndexOf('.');
			   if (index <= 0) {
				   throw e;
			   }
			   String innerClass = className.substring(0, index)  + "$" + className.substring(index + 1);
			   try {
				   targetClass = Class.forName(innerClass);
			   } catch (ClassNotFoundException cne) {
				     throw e;
			   }
		  }
		  return targetClass;
	  }
	  
	  private static Constructor<?> getClassConstructor(Class<?> targetClass, Object ...args){
		  Constructor<?>[]  classConstructors = targetClass.getConstructors();
		  Constructor<?> constructor = null;
		  for(int m = 0; m < classConstructors.length; m++){
			  Constructor<?> classConstructor  = classConstructors[m];
			  Class<?>[] parameterTypes = classConstructor.getParameterTypes();
			  if (parameterTypes.length != args.length){
		           continue;
		      }
			  boolean okConstructor =  true;
	    	      for (int i = 0; i < parameterTypes.length; i++){
	    	    	        Class<?> parameterType = parameterTypes[i];
	    	    	        Object arg = args[i];
	    	    	        if (arg == null && !parameterType.isPrimitive()) {
					   continue;
				    }
	    	    	        Class<?> argsClass = arg.getClass();
	    	    	        if (argsClass == parameterType) {
					    continue;
	    	    	        }
	    	    	        okConstructor = false;
	    	    	        break;
	    	      }
	    	      if (okConstructor){
	    	    	      constructor = classConstructor;
	    		      break;
	    		  }
		  }
		  if (constructor != null) {
			 return constructor;
		  }
		  
		  for (int m = 0; m < classConstructors.length; m++){
			  Constructor<?> classConstructor  = classConstructors[m];
		      Class<?>[] parameters = classConstructor.getParameterTypes();
		      if (parameters.length != args.length){
		           continue;
		      }
		      boolean okConstructor = true;
		      for (int j = 0; j < parameters.length; j++){
		        try{
		           	args[j] = compareTypes(parameters[j], args[j]);
		        }catch (Exception e){
		          okConstructor = false;
		          break;
		        }
		      }
		      if (okConstructor){
		        constructor = classConstructor;
		        break;
		      }
		  }
		  return constructor;
	  }
	  
	  
	  
	  private static LruCache<String, List<Method>> classMethodListCache = new LruCache<String, List<Method>>(64);
	  private static Method getMethod(Class<?> clazz, String methodName, Object ...args){
		  String key = clazz.getName() + "@" + methodName;
		  List<Method> targetMethods  = classMethodListCache.get(key);
		  if (targetMethods == null) {
			     Method[] classMethods = clazz.getMethods();
			     targetMethods = new ArrayList<Method>(2);
			     for(Method classMethod : classMethods){
			    	      if (!methodName.equals(classMethod.getName())){
					      continue;
				      }
			    	      targetMethods.add(classMethod);
			     }
			     classMethodListCache.put(key, targetMethods);
		 }
	    
	     Method method = null;
	     for(Method targetMethod : targetMethods){
	    	       Class<?>[] parameterTypes  = targetMethod.getParameterTypes();
	    	      if (args.length !=  parameterTypes.length) {
					continue;
			  }
	    	      boolean okMethod =  true;
	    	      for (int i = 0; i < parameterTypes.length; i++){
	    	    	        Class<?> parameterType = parameterTypes[i];
	    	    	        Object arg = args[i];
	    	    	        if (arg == null && !parameterType.isPrimitive()) {
					   continue;
				    }
	    	    	        Class<?> argsClass = arg.getClass();
	    	    	        if (argsClass == parameterType) {
					    continue;
	    	    	        }
	    	    	        if (parameterType.isPrimitive()) {
						if (parameterType == Integer.TYPE && argsClass == Integer.class) {
							continue;
						}
						if (parameterType == Boolean.TYPE && argsClass == Boolean.class) {
							continue;
						}	
						if (parameterType == Double.TYPE && argsClass == Double.class) {
							continue;
						}
						
						if (parameterType == Long.TYPE && argsClass == Long.class) {
							continue;
						}

						if (parameterType == Float.TYPE && argsClass == Float.class) {
							continue;
						}
						
						if (parameterType == Short.TYPE && argsClass == Short.class) {
							continue;
						}
						
						if (parameterType == Byte.TYPE && argsClass == Byte.class) {
							continue;
						}
						
						if (parameterType == Character.TYPE && argsClass == Character.class) {
							continue;
						}
					}
	    	    	        okMethod = false;
	    	    	        break;
	    	      }
	    	      if (okMethod){
	    		      method = targetMethod;
	    		      break;
	    		  }
	     }
	     
	     if (method != null) {
			 return   method;
		 }
	     
	     for(Method targetMethod : targetMethods){
		  	      Class<?>[] parameterTypes  = targetMethod.getParameterTypes();
		  	      if (args.length !=  parameterTypes.length) {
						continue;
				  }
		  	      boolean okMethod =  true;
		  	      for (int i = 0; i < parameterTypes.length; i++){
		  	    	         try{
				  	    	   Class<?> parameterType = parameterTypes[i];
				  	    	   Object arg = args[i];
		  		        	   Object valueObject = compareTypes(parameterType, arg);
		  		        	   args[i] = valueObject;
		  		         }catch (Exception e){
		  		            okMethod = false;
		  		            break;
		  		         }
		  	      }
		  	      if (okMethod){
		  		      method = targetMethod;
		  		      break;
		  		  }
		   }
	       if(method != null){
	    	        return method;
	       }
	       
	       for(Method targetMethod : targetMethods){
		  	      Class<?>[] parameterTypes  = targetMethod.getParameterTypes();
		  	      if (parameterTypes.length == 0) {
						continue;
				  }
		  	      Class<?> lastParameterType = parameterTypes[parameterTypes.length - 1];
		  	      if(!lastParameterType.isArray()){
		  	    	       continue;
		  	      }
		  	      boolean okMethod =  true;
		  	      for (int i = 0; i < parameterTypes.length - 1; i++){
		  	    	         try{
				  	    	     Class<?> parameterType = parameterTypes[i];
				  	    	     if (args.length <= i) {
								 if (parameterType.isArray()) {
									break;
								 }
						    }
				  	    	    Object arg = args[i];
		  		        	    Object valueObject = compareTypes(parameterType, arg);
		  		        	    args[i] = valueObject;
		  		         }catch (Exception e){
		  		            okMethod = false;
		  		            break;
		  		         }
		  	      }
		  	      if (!okMethod) {
					   continue;
				  }
		  	      
		  	      Class<?> componentType = lastParameterType.getComponentType();
		  	      if (args.length >= parameterTypes.length) {
		  	          Object  argsArray	= Array.newInstance(componentType, args.length - parameterTypes.length + 1);
		  	          for (int i = parameterTypes.length - 1; i < args.length; i++){
		  	        	        Object arg = args[i];
		  	               	Object valueObject = compareTypes(componentType, arg);
		  	               	int index = args.length - i -1;
		  	                Array.set(argsArray, index, valueObject);
		  	          }
		  	    	      args[parameterTypes.length - 1] = argsArray;
				  }else {
					 if (args.length != parameterTypes.length - 1) {
						 okMethod = false;
					 }
				  }
		  	      
		  	      if (okMethod){
		  		      method = targetMethod;
		  		      break;
		  		  }
		   }
		   return method;
	  }
	
	  private static Object compareTypes(Class<?> parameterType, Object value){
		     if (value == null) {
		    	    if (parameterType.isPrimitive()) {
					if (Integer.TYPE == parameterType 
							|| Double.TYPE == parameterType
							|| Long.TYPE == parameterType
							|| Short.TYPE == parameterType
							|| Float.TYPE == parameterType
							|| Byte.TYPE == parameterType) {
						return 0;
					}else if (parameterType == Boolean.TYPE) {
						return false;
					}
				}
				return value;
			}
		    Class<?> valueClass = value.getClass();
		    if (valueClass == parameterType) {
				return value;
			}
		    if (parameterType.isAssignableFrom(valueClass)) {
				return value;
			}
		    if (parameterType.isArray()) {
		    	    if(valueClass.isArray()){
		    	        if(parameterType.getComponentType().isAssignableFrom(valueClass.getComponentType())){
					    return value;
					}
		    	    }
			}
		    if (parameterType == Integer.TYPE || parameterType == Integer.class) {
			      if (value instanceof Integer) {
					return value;
				 }else if (value instanceof Number) {
					 return Integer.valueOf(((Number)value).intValue());
				 }else if (value instanceof Boolean) {
					  return Integer.valueOf((Boolean)value ? 1 : 0);
				 }else{
					 if (value == null || value.toString().length() == 0) {
						return Integer.valueOf(0);
					 }
					 return Double.valueOf(value.toString()).intValue();
				 }
			 }
			 
             if (parameterType == Long.TYPE || parameterType == Long.class) {
                 if (value instanceof Long) {
					  return value;
				 }else if (value instanceof Number) {
					 return Long.valueOf(((Number)value).longValue());
				 }else if (value instanceof Boolean) {
					  return Long.valueOf((Boolean)value ? 1 : 0);
				 }else{
					 if (value == null || value.toString().length() == 0) {
						return Long.valueOf(0);
					 }
					 return Double.valueOf(value.toString()).longValue();
				 }
			 }
            
            if (parameterType == Boolean.TYPE || parameterType == Boolean.class) {
				  if (value instanceof Boolean) {
					return value;
				  }else if (value instanceof Number) {
					 return Boolean.valueOf(((Number)value).doubleValue() != 0);
				  }else{
					 return Boolean.valueOf(value != null);
				  }
			}
			 
            
             if (parameterType == Float.TYPE || parameterType == Float.class) {
            	     if (value instanceof Float) {
					  return  value;
				 }else if (value instanceof Number) {
					 return Float.valueOf(((Number)value).floatValue());
				 }else if (value instanceof Boolean) {
					  return Float.valueOf((Boolean)value ? 1 : 0);
				 }else{
					 if (value == null || value.toString().length() == 0) {
						return Float.valueOf(0);
					 }
					 return Float.valueOf(value.toString());
				 }
			 }
            
            if (parameterType == Double.TYPE || parameterType == Double.class) {
                  if (value instanceof Double) {
					  return  value;
				 }else if (value instanceof Number) {
					 return ((Number) value).doubleValue();
				 }
				 else if (value instanceof Boolean) {
					  return Double.valueOf((Boolean)value ? 1 : 0);
				 }else{
					 if (value == null || value.toString().length() == 0) {
						return Double.valueOf(0);
					 }
					 return Double.valueOf(value.toString());
				 }
			 }
            
            if (parameterType == Byte.TYPE || parameterType == Byte.class) {
               	 if (value instanceof Byte) {
					  return value;
				 }else if (value instanceof Number) {
					 return Byte.valueOf(((Number)value).byteValue());
				 }
				 else if (value instanceof Boolean) {
					  return Byte.valueOf((byte)((Boolean)value ? 1 : 0));
				 }else{
					 if (value == null || value.toString().length() == 0) {
						return Byte.valueOf((byte)0);
					 }
					 return Double.valueOf(value.toString()).byteValue();
				 }
			 }
            
            if (parameterType == Character.TYPE || parameterType == Character.class) {
                 if (value instanceof Character) {
					  return value;
				 }else if (value instanceof Number) {
					 return Character.valueOf((char) (((Number)value).intValue()));
				 }
				 else if (value instanceof Boolean) {
					  return Character.valueOf((char)((Boolean)value ? 1 : 0));
				 }else{
					 if (value == null || value.toString().length() == 0) {
						return Character.valueOf((char)0);
					 }
					 return Character.valueOf((char)Double.valueOf(value.toString()).intValue());
				 }
			 }
            
             if (CharSequence.class.isAssignableFrom(parameterType)) {
            	      return value.toString();
             }
             throw new RuntimeException(String.format("Invalid Parameters. %s cann't cast to class %s", value, parameterType.getName()));
		}
	  
		private static final LruCache<String, Method> methodCache = new LruCache<String, Method>(64);
		private static final LruCache<String, String> notExistMethodCache = new LruCache<String, String>(16);
		private static final LruCache<String, Field> fieldCache = new LruCache<String, Field>(32);
		private static final LruCache<String, String> notExistFieldCache = new LruCache<String, String>(16);
		private static final String MARK = "";
}
