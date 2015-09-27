package com.furture.react;

public class JSRef {

	private DuktapeEngine engine;
	private int ref;
	public JSRef(DuktapeEngine engine, int ref) {
		super();
		this.engine = engine;
		this.ref = ref;
	}
	public DuktapeEngine getEngine() {
		return engine;
	}
	
	
	public int getRef() {
		return ref;
	}
	
	
	@Override
	public String toString() {
		Object value = engine.call(this, "toString");
		if (value != null) {
			return value.toString();
		}
		return super.toString();
	}
	@Override
	protected void finalize() throws Throwable {
		if(ref > 0){
			engine.finalizeJSRef(ref);;
			ref = -1;
		}
		super.finalize();
	}
	
	
}
