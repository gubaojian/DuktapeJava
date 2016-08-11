package com.furture.react;


/**
 * JavaScript对象的引用， 映射到引擎中的存JavaScript对象.
 * 可通过call(methodName, args)方法调用JavaScript对象的方式，或者获取其对应的属性。
 * java引用同一个javascript对象是单一的。
 * javascript到java的引用可以多个的，可以多个javascript对象引用一个Java对象。每个javascript持有一个引用
 * 相当于一个引用计数，javascript对象销毁时，删除对java对象的引用。这样可以避免javascript对象保持单一java对象映射的生命周期问题
 * */
public class JSRef {

	private final DuktapeEngine engine;

	private int ref;

	public JSRef(DuktapeEngine engine, int ref) {
		super();
		this.engine = engine;
		this.ref = ref;
		engine.releaseFinalizedJSRefs();
	}

	public DuktapeEngine getEngine() {
		return engine;
	}
	
	
	public int getRef() {
		return ref;
	}


	public  Object call(String methodName, Object... args){
		getEngine().call(this, methodName, args);
		return  ref;
	}
	
	
	@Override
	public String toString() {
		return "JSRef@" + ref;
	}


	@Override
	protected void finalize() throws Throwable {
		if(ref != 0){
			engine.finalizeJSRef(ref);
			ref = 0;
		}
		super.finalize();
	}
	
	
}
