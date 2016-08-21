package com.furture.react;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import android.support.v4.util.LruCache;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * java工具类，封装公共方法，供JNI以及JavaScript调用。
 * */
public class JavaUtils {

	private static final Object[] EMPTY_OBJECT_ARRAY = new Object[]{};
	private static final Pattern CALL_METHOD_SIGN_SPLIT_PATTERN = Pattern.compile("\\(|\\)");
	private static final Pattern TYPES_SPLIT_PATTERN = Pattern.compile(",");

	/**
	 *  callNew方法用于支持多态的类实例构建以及强制指定方法调用。对于多态的类型方法，可通过指定类名的关键字区分多态构建函数。
	 *  示例：
	 *  如Java中：
	 *
	 *   new People(person);
	 *
	 *  JavaScript:
	 *
	 *  JavaUtils.callNew(People, 'Person', person);
	 *
	 * */
	public static final Object callNew(Object target, String constructorTypes, Object... args) throws Exception {
		if(args == null){
			args = EMPTY_OBJECT_ARRAY;
		}
		Class<?> targetClass = null;
		if (target  instanceof Class<?>) {
			targetClass = (Class<?>) target;
		}else{
			targetClass = target.getClass();
		}
		String[] types = TYPES_SPLIT_PATTERN.split(constructorTypes, -1);
		Constructor<?> constructor =  getCallNewConstructor(targetClass, types, args);
		if (constructor == null){
			throw new RuntimeException("Cann't find constructor with types "  + constructorTypes + " on class " + targetClass);
		}
		return  newConstructorWithArgs(targetClass, constructor, args);
	}

	public static final Object callNewClassName(String className, String constructorTypes, Object... args) throws Exception {
	    return callNew(importClass(className), constructorTypes, args);
	}






	/**
	 *  call方法用于支持多态的方法调用。对于多态的类型方法，可通过指定类名的关键字指定特定的调用方法
	 *  示例：
	 *  如Java中：
	 *
	 *  Intent intent;
	 *  intent.putExtra("key", 2.0);
	 *
	 *  JavaScript
	 *  JavaUtils.call(intent, 'putExtra(String,Float)', 'key', 2.0);
	 *
	 * */

	public static final Object call(Object target, String methodNameWithSign, Object... args) throws Exception {
		 if(args == null){
		 	 args = EMPTY_OBJECT_ARRAY;
		 }
		 Class<?> targetClass = null;
		 if (target  instanceof Class<?>) {
			 targetClass = (Class<?>) target;
		 }else{
			 targetClass = target.getClass();
		 }
		 String []  methodSign = CALL_METHOD_SIGN_SPLIT_PATTERN.split(methodNameWithSign);
		 if(methodSign.length != 2){
			 throw new RuntimeException(methodNameWithSign +  " is illegal method sign, method sign sample putExtra(String,String)");
		 }
		 String methodName = methodSign[0];
		 String[] types = TYPES_SPLIT_PATTERN.split(methodSign[1], -1);
		 Method method =  getCallMethod(targetClass, methodName, types, args);
         if (method == null) {
			 throw new RuntimeException("Cann't find method " + methodNameWithSign + " on target " + target);
		 }
		 return  invokeTargetMethodWithArgs(target, method, args);
	 }


    /**
	 * @param  target 对象
	 * @param  methodName 方法名字
	 * @param  args 方法调用参数
	 * 仅找到匹配方法，此方法不严格区分多态调用，如果需要区分调用多态方法，请使用call方法
	 * */
	public static final Object invoke(Object target, String methodName, Object ...args) throws Exception{
		if(args == null){
			args = EMPTY_OBJECT_ARRAY;
		}
		Class<?> targetClass = null;
		if (target  instanceof Class<?>) {
			targetClass = (Class<?>) target;
		}else{
			targetClass = target.getClass();
		}
		Method method = getInvokeMethod(targetClass, methodName, args);
		if (method == null) {
			throw new RuntimeException("Cann't find method " + methodName + " with args " + Arrays.toString(args) + " on target " + target);
		}
		return  invokeTargetMethodWithArgs(target, method, args);
	}


	private  static Object invokeTargetMethodWithArgs(Object target, Method method, Object ...args) throws Exception{
		if(!method.isAccessible()){
			method.setAccessible(true);
		}
		Class<?>[] parameterTypes =  method.getParameterTypes();
		if (args.length == parameterTypes.length) {
			return method.invoke(target, args);
		}

		if (!(parameterTypes.length > 0 && parameterTypes[parameterTypes.length -1].isArray())
				|| (parameterTypes.length - 1) > args.length) {
			throw new RuntimeException("Cann't find method " + method.getName() + " with args " + args + " on target " + target);
		}

		/**
		 *  处理可变参数列表的调用情况
		 * */
		Object lastArgs = null;
		if(args.length >= parameterTypes.length){
			lastArgs = args[parameterTypes.length -1];
		}else{
			lastArgs = Array.newInstance(parameterTypes[parameterTypes.length -1].getComponentType(), 0);
		}
		int length = parameterTypes.length;
		Object valueObject = null;
		switch (length){
			case 0 :
			case 1 :{
				valueObject = method.invoke(target, lastArgs);
			}
			break;
			case 2 : {
				valueObject = method.invoke(target, args[0], lastArgs);
			}
			break;
			case 3 : {
				valueObject = method.invoke(target, args[0], args[1], lastArgs);
			}
			break;
			case 4 : {
				valueObject = method.invoke(target, args[0], args[1], args[2], lastArgs);
			}
			break;
			case 5 : {
				valueObject = method.invoke(target, args[0], args[1], args[2], args[3], lastArgs);
			}
			break;
			case 6 : {
				valueObject = method.invoke(target, args[0], args[1], args[2], args[3], args[4], lastArgs);
			}
			break;
			case 7 : {
				valueObject = method.invoke(target, args[0], args[1], args[2], args[3], args[4], args[5], lastArgs);
			}
			break;
			default:{
				throw new RuntimeException(target  + " method " + method.getName() + " has too many arguments " + args.length);
			}
		}
		return valueObject;
	}



	/**
	 * @param  className 类名字
	 * @param   args     参数的名字
	 * 创建指定名字的类的实例
	 * */
	public static Object newInstance(String className, Object ...args) throws Exception{
		  if(args == null){
			  args = EMPTY_OBJECT_ARRAY;
		  }
		  return  newInstanceClass(loadClass(className), args);
	}


	public static Object newInstanceClass(Class<?> targetClass, Object ...args) throws Exception{
		int modifiers = getClassModifiers(targetClass);
		if (Modifier.isInterface(modifiers)) {
			Class<?>[] interfaces = {targetClass};
			if (args == null || args.length < 0) {
				throw new RuntimeException("interface " + targetClass + " must have a javascript object or function constructor parameter");
			}
			return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfaces, new JSInvocationHandler(args[0]));
		}
		if(Modifier.isAbstract(modifiers)){
			Class<?> sourceClass  = JSApi.getAbstractClassMap().get(targetClass);
			if (sourceClass == null) {
				throw new RuntimeException(" Cannot find abstract class implemation, please registerAbstractClass to JSApi. class name " + targetClass.getName());
			}
			targetClass = sourceClass;
		}
		Constructor<?> constructor =  getNewConstructor(targetClass, args);
		if (constructor == null){
			throw new RuntimeException("Cann't find constructor with args " + Arrays.toString(args) + " on class " + targetClass);
		}
		return  newConstructorWithArgs(targetClass, constructor, args);
	}


	private  static Object newConstructorWithArgs(Class<?> targetClass, Constructor<?> constructor,Object ...args) throws IllegalAccessException, InvocationTargetException, InstantiationException {
		Class<?>[] parameterTypes = constructor.getParameterTypes();
		if(args.length == parameterTypes.length){
			return constructor.newInstance(args);
		}

		if (!(parameterTypes.length > 0 && parameterTypes[parameterTypes.length -1].isArray())
				|| (parameterTypes.length - 1) > args.length) {
			throw new RuntimeException("Cann't find constructor with args " + args + " on class " + targetClass);
		}


		/**
		 *  处理可变参数列表的调用情况
		 * */
		Object lastArgs = null;
		if(args.length >= parameterTypes.length){
			lastArgs = args[parameterTypes.length -1];
		}else{
			lastArgs = Array.newInstance(parameterTypes[parameterTypes.length -1].getComponentType(), 0);
		}
		int length = parameterTypes.length;
		switch (length){
			case 0 :
			case 1 :{
				return  constructor.newInstance(lastArgs);
			}
			case 2 : {
				return  constructor.newInstance(args[0], lastArgs);
			}
			case 3 : {
				return  constructor.newInstance(args[0], args[1], lastArgs);
			}
			case 4 : {
				return  constructor.newInstance(args[0], args[1], args[2], lastArgs);
			}
			case 5 : {
				return  constructor.newInstance(args[0], args[1], args[2], args[3], lastArgs);
			}
			case 6 : {
				return  constructor.newInstance(args[0], args[1], args[2], args[3], args[4], lastArgs);
			}
			case 7 : {
				return  constructor.newInstance(args[0], args[1], args[2], args[3], args[4], args[5], lastArgs);
			}
			default:{
				throw new RuntimeException(constructor  + " constructor on class " + targetClass + " has too many arguments " + args.length);
			}
		}
	}


	/**
	 * @param  target  对象
	 * @param  fieldName 属性名
	 * 获取target的对象属性为fieldName的值
	 * 若target为Map或者JSONObject则获取对应的filedName为key，获取对应的值
	 * 若target为List或者JSONArray则获取filedName对应的index的值
	 * 若target为其它的Java对象，则获取符合JavaBean规范的fieldName对应的值
	 * */
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

		  if(target instanceof JSONObject){
			  return  ((JSONObject) target).opt(fieldName);
		  }

		  if(target instanceof JSONArray){
			  int index = Integer.parseInt(fieldName);
			  return  ((JSONArray) target).opt(index);
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
		   Method method = getMethodCache.get(key);
		   if (method != null) {
				try {
					if(method.isAccessible()){
						method.setAccessible(true);
					}
					return method.invoke(target);
				}catch (Exception e) {
					throw new RuntimeException("invoke get field method " + fieldName + " error on target " + target, e);
				}
			}

			Field field = fieldCache.get(key);
			if (field != null) {
				try {
					if(field.isAccessible()){
						field.setAccessible(true);
					}
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
					if(method.isAccessible()){
						method.setAccessible(true);
					}
					getMethodCache.put(key, method);
					return method.invoke(target);
				}catch (NoSuchMethodException notExistGetAccessError){
				    try {
				    	String methodName = "is" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
						method = targetClass.getMethod(methodName);
						if(method.isAccessible()){
							method.setAccessible(true);
						}
						getMethodCache.put(key, method);
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
					if(field.isAccessible()){
						field.setAccessible(true);
					}
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


	/**
	 * @param  target  对象
	 * @param fieldName 属性名
	 * @param fieldValue 属性值
	 * 设置target对应的fieldName属性的值为fieldValue
	 * 若target为Map或者JSONObject设置对应的filedName为key的值为fieldValue
	 * 若target为List或者JSONArray设置filedName对应的index的值为fieldValue
	 * 若target为其它的Java对象，设置符合JavaBean规范的fieldName的值为fieldValue
	 * 不支持多态的set方法
	 * */
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

		  if(target instanceof  JSONObject){
			  try {
				  ((JSONObject) target).put(fieldName, fieldValue);
			  } catch (JSONException e) {
				  throw new RuntimeException( "set property '" + fieldName + "'" + "  on target " + target, e);
			  }
		  }

		  if(target instanceof  JSONArray){
			  try {
				  int index = Integer.parseInt(fieldName);
				  ((JSONArray) target).put(index, fieldValue);
			  } catch (JSONException e) {
				  throw new RuntimeException( "set property '" + fieldName + "'" + "  on target " + target, e);
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
		   Method method = setMethodCache.get(key);
		   if (method != null) {
				try {
					 if(!method.isAccessible()){
						 method.setAccessible(true);
					 }
					 method.invoke(target, fieldValue);
					 return;
				}catch (Exception e) {
					throw new RuntimeException("invoke set method " + fieldName + " error on target " + target, e);
				}
			}

			Field field = fieldCache.get(key);
			if (field != null) {
				try {
					 if(field.isAccessible()){
						 field.setAccessible(true);
					 }
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
					method = getInvokeMethod(targetClass, methodName, fieldValue);
					if(method != null){
						if(!method.isAccessible()){
							method.setAccessible(true);
						}
						setMethodCache.put(key, method);
						method.invoke(target, fieldValue);
						return;
					}else{
						notExistMethodCache.put(key, MARK);
					}
				}catch (Exception e) {
					throw new RuntimeException("invoke set field method " + fieldName + " error on target " + target, e);
				}
			}

			if (notExistFieldCache.get(key) != MARK) {
				try {
					field = targetClass.getField(fieldName);
					if(field.isAccessible()){
						field.setAccessible(true);
					}
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




    /**
	 *  @param  className 类名字
	 *  JavaScript中importClass对应的实现，对于需要自定义ClassLoader的，
	 *  可通过修改此方法，采用自定义的ClassLoader加载对应的类
	 * */
	public static Class<?> importClass(String className) throws ClassNotFoundException{
		  return loadClass(className);
	}


	/**
	 *  @param  throwable 异常
	 *  JNI通过调用此方法把异常转换成字符串，然后在log中显示出来。
	 * */
	public static String getStackTrace(Throwable throwable) {
		     if (throwable instanceof InvocationTargetException && throwable.getCause() != null) {
				 throwable = throwable.getCause();
			 }
		     DLog.e("ScriptEngine", "ScriptEngine JavaScript call Java Error", throwable);
		     return throwable.getMessage();
	 }

	  private static Class<?>  loadClass(String className) throws ClassNotFoundException{
		  Class<?> targetClass = classCache.get(className);
		  if(targetClass != null){
			  return  targetClass;
		  }
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
		  classCache.put(className, targetClass);
		  return targetClass;
	  }


	/**
	 * 找到适合的匹配类型的构建器
	 * */
	private static final LruCache<String, Constructor<?>[]> classConstructorsCache = new LruCache<String, Constructor<?>[]>(64);
	private static Constructor<?> getNewConstructor(Class<?> targetClass, Object ...args){
		  Constructor<?>[]  classConstructors = classConstructorsCache.get(targetClass.getName());
		  if(classConstructors == null){
			  classConstructors =  targetClass.getConstructors();
			  classConstructorsCache.put(targetClass.getName(), classConstructors);
		  }
		  Constructor<?> constructor = null;
		  for(int m = 0; m < classConstructors.length; m++){
			  Constructor<?> classConstructor  = classConstructors[m];
			  Class<?>[] parameterTypes = classConstructor.getParameterTypes();
			  if (parameterTypes.length != args.length){
				  //可变参数方法
				  if(!(parameterTypes.length > 0
						  && parameterTypes[parameterTypes.length -1].isArray()
				          && classConstructor.isVarArgs())){
					  continue;
				  }
		      }
			  boolean okConstructor =  true;
			  int i = 0;
			  for (; i < parameterTypes.length; i++){
					Class<?> parameterType = parameterTypes[i];
					  if (args.length < parameterTypes.length) {
						  okConstructor = false;
						  break;
					  }
					Object arg = args[i];
					if (arg == null) {
					   if(parameterType.isPrimitive()){
						   okConstructor = false;
						  break;
					   }
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
					okConstructor = false;
					break;
			  }
			  if (okConstructor){
				  constructor = classConstructor;
				  break;
			  }
			  //可变参数方法
			  if(i == parameterTypes.length -1){
				  Class<?>  parameterType = parameterTypes[i];
				  if(parameterType.isArray()){
					  if(convertLastToVarArgs(parameterTypes, args)){
						  constructor = classConstructor;
						  break;
					  }
				  }

			  }

		  }

		  if (constructor != null) {
			 return constructor;
		  }

		  for (int m = 0; m < classConstructors.length; m++){
			  Constructor<?> classConstructor  = classConstructors[m];
		      Class<?>[] parameterTypes = classConstructor.getParameterTypes();
		      if (parameterTypes.length != args.length){
				  if(!(parameterTypes.length > 0
						  && parameterTypes[parameterTypes.length -1].isArray())){
					  continue;
				  }
		      }
		      boolean okConstructor = true;
			  int i = 0;
		      for (; i < parameterTypes.length; i++){
				  if (i >= args.length) {
					  okConstructor = false;
					  break;
				  }
		        try{
		           	args[i] = compareTypes(parameterTypes[i], args[i]);
		        }catch (Exception e){
		          okConstructor = false;
		          break;
		        }
		      }
		      if (okConstructor){
		        constructor = classConstructor;
		        break;
		      }

			  /**
			   * 处理可变参数的情况，如object... args
			   * */
			  if(i == parameterTypes.length - 1 && parameterTypes[i].isArray() && classConstructor.isVarArgs()){
				  if(convertLastToVarArgs(parameterTypes, args)){
					  constructor = classConstructor;
					  break;
				  }
			  }
		  }
		  return constructor;
	  }

    /**根据方法签名找到指定的构建函数*/
	private static Constructor<?> getCallNewConstructor(Class<?> targetClass, String[] types, Object... args){
		Constructor<?>[]  classConstructors = classConstructorsCache.get(targetClass.getName());
		if(classConstructors == null){
			classConstructors =  targetClass.getConstructors();
			classConstructorsCache.put(targetClass.getName(), classConstructors);
		}
		Constructor<?> constructor = null;
		for(int m = 0; m < classConstructors.length; m++){
			Constructor<?> classConstructor  = classConstructors[m];
			Class<?>[] parameterTypes = classConstructor.getParameterTypes();
			if (parameterTypes.length != types.length){
				continue;
			}
			boolean okConstructor =  true;
			int i = 0;
			for (; i < parameterTypes.length; i++){
				Class<?> parameterType = parameterTypes[i];
				String   argsType = types[i];
				if(TextUtils.isEmpty(argsType)){
					continue;
				}
				String name = parameterType.getName();
				if(name.lastIndexOf(argsType) >= 0){
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
		//未找到直接返回
		if (constructor == null) {
			return constructor;
		}

		//根据参数类型进行强制的类型转换
		Class<?>[] parameterTypes = constructor.getParameterTypes();
		for (int i = 0; i < parameterTypes.length; i++) {
			Class<?> parameterType = parameterTypes[i];
			try {
				Object arg = args[i];
				Object valueObject = compareTypes(parameterType, arg);
				args[i] = valueObject;
			} catch (Exception e) {
				if(i == parameterTypes.length -1 && parameterType.isArray()){
					convertLastToVarArgs(parameterTypes,args);
				}
			}
		}
		return  constructor;
	}



	/**
	 * @param  clazz      类
	 * @param  methodName 方法的名字
	 * @param  argsTypes  类型列表
	 * @param  args       方法参数
	 * 返回该类中所有指定名字的方法列表
	 * */
	private static Method getCallMethod(Class<?> clazz, String methodName, String[] argsTypes, Object... args) {
		List<Method> targetMethods = findMethod(clazz, methodName);
		Method method = null;
		for (Method targetMethod : targetMethods) {
			Class<?>[] parameterTypes = targetMethod.getParameterTypes();
			if (parameterTypes.length != argsTypes.length) {
				continue;
			}
			boolean okMethod = true;
			for (int i = 0; i < parameterTypes.length; i++) {
				Class<?> parameterType = parameterTypes[i];
				String   argsType = argsTypes[i];
				if(TextUtils.isEmpty(argsType)){
					continue;
				}
				String name = parameterType.getName();
				if(name.lastIndexOf(argsType) >= 0){
					continue;
				}
				okMethod = false;
				break;
			}
			if (okMethod) {
				method = targetMethod;
				break;
			}
		}
		//未找到方法，直接返回
		if(method == null){
			return method;
		}

		//根据参数类型进行类型转换
		Class<?>[] parameterTypes = method.getParameterTypes();
		for (int i = 0; i < parameterTypes.length; i++) {
			Class<?> parameterType = parameterTypes[i];
			try {
				Object arg = args[i];
				Object valueObject = compareTypes(parameterType, arg);
				args[i] = valueObject;
			} catch (Exception e) {
				if(i == parameterTypes.length -1 && parameterType.isArray()){
					convertLastToVarArgs(parameterTypes,args);
				}
			}
		}
		return  method;
	}


    /**
	 * 过滤所有方法为指定名字的方法
	 * */
	private static LruCache<String, Method[]> classMetaMethodsCache = new LruCache<String, Method[]>(128);
	private static LruCache<String, List<Method>> classMethodNameListCache = new LruCache<String, List<Method>>(128);
	private static void filterMethod(Method[] methods, String methodName, List<Method> targetMethods){
		for (Method method : methods) {
			if (!methodName.equals(method.getName())) {
				continue;
			}
			if(!targetMethods.contains(method)){
				targetMethods.add(method);
			}
		}
	}
	/**
	 * @param  clazz 类
	 * @param  methodName 方法的名字
	 * 返回该类中所有指定名字的方法列表
	 * */
	private static  List<Method> findMethod(Class<?> clazz, String methodName){
		String key = clazz.getName() + "@" + methodName;
		List<Method> targetMethods = classMethodNameListCache.get(key);
		if (targetMethods == null) {
			targetMethods = new ArrayList<Method>(2);
			for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
				Method[] methods =  classMetaMethodsCache.get(c.getName());
				if(methods == null){
					methods =  c.getDeclaredMethods();
					classMetaMethodsCache.put(c.getName(), methods);
				}
				filterMethod(methods, methodName, targetMethods);
				for (Class<?> ifc : c.getInterfaces()) {
					methods = ifc.getDeclaredMethods();
					filterMethod(methods, methodName, targetMethods);
					classMetaMethodsCache.put(ifc.getName(), methods);
				}
			}
			classMethodNameListCache.put(key, targetMethods);
		}
		return  targetMethods;
	}




	  /**
	   *
	   * @param clazz 类名
	   * @param methodName 方法的名字
	   * @param args 参数的名字
	   *
	   * 根据参数查找符合的方法，但不一定是最匹配的方法
	   *
	   * 首先根据类型进行匹配，寻找能方法名字和匹配的类型。如果找到则直接返回
	   * 如果找不到，则寻找最近可能匹配的方法，返回此方法，但并不一定是最匹配的方法。
	   * 如果JavaScript需要调用Java的多态方法，而此方法无法找到最匹配的类型时。
	   * 请使用JavaUtils.call方法，通过指定参数类型，进行多态的方法调用。
	   * */
	   private static Method getInvokeMethod(Class<?> clazz, String methodName, Object... args) {
		List<Method> targetMethods = findMethod(clazz, methodName);
		Method method = null;
		for (Method targetMethod : targetMethods) {
			Class<?>[] parameterTypes = targetMethod.getParameterTypes();
			if(parameterTypes.length != args.length){
				//可变参数方法
				if(!(parameterTypes.length > 0
						&& parameterTypes[parameterTypes.length -1].isArray()
				        && targetMethod.isVarArgs())){
					continue;
				}
			}
			boolean okMethod = true;
			int i=0;
			for (; i < parameterTypes.length; i++) {
				Class<?> parameterType = parameterTypes[i];
				if (args.length < parameterTypes.length) {
					okMethod = false;
					break;
				}
				Object arg = args[i];
				if (arg == null) {
					if(parameterType.isPrimitive()){
						okMethod = false;
						break;
					}
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
			if (okMethod) {
				method = targetMethod;
				break;
			}
			//可变参数方法
			if(i == parameterTypes.length -1){
				Class<?>  parameterType = parameterTypes[i];
				if(parameterType.isArray() && targetMethod.isVarArgs()){
					if(convertLastToVarArgs(parameterTypes, args)){
						method = targetMethod;
						break;
					}
				}

			}
		}

		if (method != null) {
			return method;
		}


		/**
		 *  查找通过类型转换适配可以调用的方法
		 * */
		for (Method targetMethod : targetMethods) {
			Class<?>[] parameterTypes = targetMethod.getParameterTypes();
			if(parameterTypes.length != args.length){
				if(!(parameterTypes.length > 0
						&& parameterTypes[parameterTypes.length -1].isArray())){
					continue;
				}
			}
			boolean okMethod = true;
			int i=0;
			for (; i < parameterTypes.length; i++) {
				if (i >= args.length) {
					okMethod = false;
					break;
				}
				Class<?> parameterType = parameterTypes[i];
				Object arg = args[i];
				try {
					Object valueObject = compareTypes(parameterType, arg);
					args[i] = valueObject;
				} catch (Exception e) {
					okMethod = false;
					break;
				}
			}
			if (okMethod) {
				method = targetMethod;
				break;
			}
			/**
			 * 处理可变参数的情况，如object... args
			 * */
			if(i == parameterTypes.length - 1  && parameterTypes[i].isArray() && targetMethod.isVarArgs()){
				if(convertLastToVarArgs(parameterTypes, args)){
					method = targetMethod;
					break;
				}
			}
		}
		if (method != null) {
			 return method;
		}
		return method;
	}

	private static boolean convertLastToVarArgs(Class<?>[] parameterTypes, Object[] args){
		try {
			int size = args.length - parameterTypes.length + 1;
			if(size <= 0){
				return  true;
			}
			int i = parameterTypes.length - 1;
			Class<?> componentType = parameterTypes[i].getComponentType();
			Object arrays = Array.newInstance(componentType, size);
			for(int m=0; m<size; m++){
				Object valueObject = compareTypes(componentType, args[m + i]);
				Array.set(arrays, m, valueObject);
			}
			args[i] = arrays;
			return  true;
		} catch (Exception arrayE) {
			return  false;
		}
	}

	/**
	 * 获取类的修饰符
	 * */
	private static final LruCache<String, Integer> classModifiersCache = new LruCache<String, Integer>(64);
	private  static int getClassModifiers(Class<?> targetClass){
		Integer classModifier = classModifiersCache.get(targetClass.getName());
		if(classModifier == null){
			classModifier = targetClass.getModifiers();
			classModifiersCache.put(targetClass.getName(), classModifier);
		}
		return  classModifier;
	}


	/**
	 * 比较类型，并尝试进行类型的转换。
	 * value为null， 对于基本类型，则转换成0或false，
	 * */
	private static Object compareTypes(Class<?> parameterType, Object value) throws Exception {
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

			if (CharSequence.class.isAssignableFrom(parameterType)) {
				return value.toString();
			}


		    if(parameterType.isPrimitive() || Number.class.isAssignableFrom(parameterType)){
				if (parameterType == Integer.TYPE
						|| parameterType == Integer.class) {
					if (value instanceof Integer) {
						return value;
					}else if (value instanceof Number) {
						return Integer.valueOf(((Number)value).intValue());
					}else if (value instanceof Boolean) {
						return Integer.valueOf((Boolean)value ? 1 : 0);
					}else{
						if (value == null) {
							return Integer.valueOf(0);
						}
						String valueString =  value.toString();
						if(valueString.length() == 0){
							return Integer.valueOf(0);
						}
						return Double.valueOf(valueString).intValue();
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
						if (value == null) {
							return Float.valueOf(0);
						}
						String valueString =  value.toString();
						if(valueString.length() == 0){
							return Float.valueOf(0);
						}
						return Float.valueOf(valueString);
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
						if (value == null) {
							return Double.valueOf(0);
						}
						String valueString =  value.toString();
						if(valueString.length() == 0){
							return Double.valueOf(0);
						}
						return Double.valueOf(valueString);
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

				if (parameterType == Byte.TYPE || parameterType == Byte.class) {
					if (value instanceof Byte) {
						return value;
					}else if (value instanceof Number) {
						return Byte.valueOf(((Number)value).byteValue());
					}
					else if (value instanceof Boolean) {
						return Byte.valueOf((byte)((Boolean)value ? 1 : 0));
					}else{
						if (value == null) {
							return Byte.valueOf((byte)0);
						}
						String valueString =  value.toString();
						if(valueString.length() == 0){
							return Byte.valueOf((byte)0);
						}
						return Double.valueOf(valueString).byteValue();
					}
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

			if (parameterType.isArray()) {
				if(valueClass.isArray()){
					if(parameterType.getComponentType().isAssignableFrom(valueClass.getComponentType())){
						return value;
					}
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
					 if (value == null) {
						return Character.valueOf((char)0);
					 }
					 String valueString =  value.toString();
					 if(valueString.length() == 0){
						 return Character.valueOf((char) 0);
					 }
					 return  Character.valueOf((char) Double.valueOf(valueString).intValue());
				 }
			 }

			 /**
			 * 如果传入参数是JSRef，且方法参数是接口或者抽象类，自动转换成对应的实例。
			 * 方便事件监听或者方法调用
			 * */
			 if(value.getClass() == JSRef.class){
				 int modifiers = getClassModifiers(parameterType);
				 if(Modifier.isInterface(modifiers) || Modifier.isAbstract(modifiers)){
					 return  newInstanceClass(parameterType, value);
				 }
			 }

		     /**
			  * 无法找到参数转换的类型
			  * */
		     throw new RuntimeException(String.format("Invalid Parameters. %s cann't cast to class %s", value, parameterType.getName()));
		}


	    private static final LruCache<String, Method> getMethodCache = new LruCache<String, Method>(64);
	    private static final LruCache<String, Method> setMethodCache = new LruCache<String, Method>(64);
	    private static final LruCache<String, Field> fieldCache = new LruCache<String, Field>(32);
	    private static final LruCache<String, String> notExistMethodCache = new LruCache<String, String>(16);
	    private static final LruCache<String, String> notExistFieldCache = new LruCache<String, String>(16);
	    private static final String MARK = "";
	    private static final LruCache<String, Class<?>> classCache = new LruCache<String, Class<?>>(64);
}
