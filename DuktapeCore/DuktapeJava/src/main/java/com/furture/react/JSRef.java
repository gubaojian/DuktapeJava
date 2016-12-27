package com.furture.react;


/**
 * JavaScript对象的引用， 映射到引擎中的存JavaScript对象.
 * 可通过call(methodName, args)方法调用JavaScript对象的方法，或者获取其对应的属性。
 * java引用同一个javascript对象是单一的。
 * javascript到java的引用可以多个的，可以多个javascript对象引用一个Java对象。每个javascript持有一个引用
 * 相当于一个引用计数，javascript对象销毁时，删除对java对象的引用，非常节省内存占用。
 * 这样可以避免javascript对象保持单一java对象映射的生命周期问题, 以及采用包装模拟导致的内存暴涨问题。
 * */
public class JSRef {

	private final DuktapeEngine engine;

	private int ref;

	public JSRef(DuktapeEngine engine, int ref) {
		super();
		this.engine = engine;
		this.ref = ref;
		engine.releaseFinalizedJSRefs(ref);
	}

	public DuktapeEngine getEngine() {
		return engine;
	}
	
	
	public int getRef() {
		return ref;
	}

	/**
	 * @param methodName   js对象的方法名
	 * @param args         方法参数
	 *
	 *
	 * @return 调用异常返回null，异常信息输出到logcat中
	 * <br>
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
	public  Object call(String methodName, Object... args){
		return  getEngine().call(this, methodName, args);
	}


	
	@Override
	public String toString() {
		if(ref != 0){
			Object result = getEngine().call(this, "toString");
			if(result != null){
				return result.toString();
			}
		}
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
