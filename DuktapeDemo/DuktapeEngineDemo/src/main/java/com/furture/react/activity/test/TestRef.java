package com.furture.react.activity.test;

import com.furture.react.DLog;
import com.furture.react.JSRef;

public class TestRef {
	JSRef jsRef;
	public TestRef(JSRef jsRef){
		this.jsRef = jsRef;
	}
	
	public Object callJsRef(JSRef args){
		if(jsRef != args){
			throw new RuntimeException("TestRef Failed Must Same ref");
		}
		DLog.e("ScriptEngine", "ScriptEngine JSRef " + args.getRef());
		return jsRef;
	}


	public Object javaRef(){
		DLog.e("ScriptEngine", "ScriptEngine  javaRef ");
		return this;
	}
}
