package com.furture.react;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

/**
 *
 * 1、支持多态
 *
 * 补充功能测试用例
 * 补充性能测试用例
 *
 *
 *
 *
 * 2、支持多线程,
 * 多线程仅支持的执行
 *
 * DuktapeEngine 支持支持多线程，但单个实例同时仅支持一个线程执行。
 * 如果需要完全并发，建议利用Java层实现并发，然后把并发结果转交给DuktapeEngine。
 * 或者在调用的地方用synchronized (engine){
 *
 * }控制
 * */
public class DuktapeEngine {

	static{
	    System.loadLibrary("DuktapeEngine");
	}


	private static  final int FINALIZE_SIZE = 8;
	
	private long  ptr;

	private List<Integer> finalizedJSRefList;



    /**
	 * 创建DuktapeEngine
	 * 引擎使用完成后，调用destory()销毁引擎
	 * */
	public DuktapeEngine() {
		ptr = nativeInit();
		if (ptr == 0) {
			throw new RuntimeException("NativeInit Pointer Convert Error");
		}
		Set<Entry<String, Object>> entries = JSApi.getContext().entrySet();
		for(Entry<String, Object> entry : entries){
			put(entry.getKey(), entry.getValue());
		}
		finalizedJSRefList = new ArrayList<Integer>(FINALIZE_SIZE*2);
	}


	/**
	 * @param  script
	 * 执行JavaScript返回执行结果
	 * */
	public Object execute(String script){
		if(ptr != 0){
			return nativeExeclute(ptr, script);
		}else{
			return null;
		}
	}

	/**
	 * @param  key  导入java对象在javascript中的名字
	 * @param  value Java对象
	 * 引入Java对象到JavaScript引擎，JavaScript可以调用Java对象。
	 * 此方法可用于初始化一些公共的对象。
	 * */
	public void put(String key, Object value){
		nativeRegister(ptr, key, value);
	}

	/**
	 * @param jsRef        javascript对象
	 * @param methodName       js对象的方法名
	 * @param args          方法参数
     * 若javascript对象为function，忽略methodName，直接调用该function。对于直接function的调用，methodName可以传null
	 * 若javascript对象为object，则调用object中的methodName对应的function方法或者属性。
	 * 这样在javascript 通过两种方式书写回调。 如要写一个setOnClickListener(new OnClickListener())的回调。
	 * 在javascript中可以通过一下两种方式书写：
	 * view.setOnClickListener(new OnClickListener({
	 *    onClick : function(view){
	 *
	 *     }
	 * }));
	 *
	 * 或者采用更为简便的方式书写：
	 *
	 * view.setOnClickListener(new OnClickListener(function(view){
	 *
	 * }));
	 *
     * */
    public   Object call(JSRef jsRef, String methodName, Object... args){
			if(ptr != 0){
				return nativeCallJSRef(ptr, jsRef.getRef(),  methodName, args);
			}else{
				return null;
			}
	}







	/**
	 * @param objectName   js对象名字
	 * @param method       js对象的方法名
	 * @param args         方法参数
	 * 直接调用js中的方法
	 * */
    public  Object call(String objectName, String method, Object... args){
		if(ptr != 0){
			return nativeCallJs(ptr, objectName, method, args);
		}else {
			return null;
		}
	}


	

	/**
	 * 销毁引擎，释放引擎对应的naive资源。
	 * 引擎销毁后。调用讲不在生效。
	 * */
	public void destory(){
		synchronized (this) {
			if (ptr != 0) {
				nativeDestory(ptr);
				ptr = 0;
			}
		}
	}

	public void releaseFinalizedJSRefs(){
		if (finalizedJSRefList.size() <= FINALIZE_SIZE){
			return;
		}
		synchronized (finalizedJSRefList){
			synchronized (this){
				Iterator<Integer>  it =  finalizedJSRefList.iterator();
				while (it.hasNext()){
					if (ptr != 0) {
						int ref = it.next();
						nativeFinalizeJSRef(ptr, ref);
					}
					it.remove();
				}
			}
		}
	}

	/**
	 * 放到finalizedJSRefList中，然后批量回收。
	 * 不阻塞finalize java内存回收的进程。
	 * */
   void finalizeJSRef(final int jsRef){
	   synchronized (finalizedJSRefList){
		   finalizedJSRefList.add(jsRef);
	   }
	}


	private  native long nativeInit();
	private  native void nativeRegister(long ptr, String key, Object value);
	private  native Object nativeExeclute(long ptr, String script);
	private  native Object nativeCallJs(long ptr, String target, String method, Object... args);
	private  native Object nativeCallJSRef(long ptr, int jsRef, String methodName, Object... args);
	private native void nativeFinalizeJSRef(long ptr, int jsRef);
	private native void nativeDestory(long ptr);
}
