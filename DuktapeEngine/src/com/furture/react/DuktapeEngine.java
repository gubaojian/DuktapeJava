package com.furture.react;

import java.util.Set;
import java.util.Map.Entry;

/**
 * 
 * Duktape Engine , JavaScript Must Be Execute In Main Thread
 * 
 * */
public class DuktapeEngine {
    
	static{
	    System.loadLibrary("DuktapeEngine");
	}
	
	private long  ptr;
	
	public synchronized void init(){
		ptr = nativeInit();
        if (ptr == 0) {
			throw new RuntimeException("NativeInit Pointer Convert Error");
		}
        Set<Entry<String, Object>> entries = JSApi.getJavaObjectMap().entrySet();
        for(Entry<String, Object> entry : entries){
        	      register(entry.getKey(), entry.getValue());
        }
	}
	
	public synchronized void importClass(Class<?> importClass){
		nativeRegister(ptr, importClass.getSimpleName(), importClass);
	}
	
	public synchronized void register(String key, Object value){
		nativeRegister(ptr, key, value);
	}
	
    
    /**
     * 调用heapPtr中方法未methodName的方法。
     * 如果heapPtr是函数，则直接调用此实现
     * */
    public  synchronized Object callJSRef(JSRef jsRef, String methodName, Object... args){
    	     if(ptr != 0){
    	 		return nativeCallJSRef(ptr, jsRef.getRef(),  methodName, args);
    	     }else{
    	    	    return null;
    	     }
	}
    
    

	/**
	 * 直接调用js中的方法
	 * */
    public synchronized Object callJs(String target, String method, Object... args){
    	    if(ptr != 0){
		   return nativeCallJs(ptr, target, method, args);
    	     }else {
		    return null;
		}
	}
	
	public  synchronized Object execute(String script){
		if(ptr != 0){
		   return nativeExeclute(ptr, script);
		}else{
			return null;
		}
	}
	
	public void destory(){
		if(ptr != 0){
		   nativeDestory(ptr);
		   ptr = 0;
		}
	}
	
   void finalizeJSRef(int jsRef){
		if (ptr != 0 ) {
		    nativeFinalizeJSRef(ptr, jsRef);
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		if (ptr != 0 ) {
			destory();
		}
		super.finalize();
	}

	private  native long nativeInit();
	private  native void nativeRegister(long ptr, String key, Object value);
	private  native Object nativeExeclute(long ptr, String script);
	private  native Object nativeCallJs(long ptr, String target, String method, Object... args);
	private  native Object nativeCallJSRef(long ptr, int jsRef, String methodName, Object... args);
	private native void nativeFinalizeJSRef(long ptr, int jsRef);
	private native void nativeDestory(long ptr);
}
