package com.furture.react.activity.test;

import android.util.Log;

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
		Log.e("ScriptEngine", "ScriptEngine JSRef " + args.getRef());
		return jsRef;
	}


	public Object javaRef(){
		Log.e("ScriptEngine", "ScriptEngine  javaRef ");
		return this;
	}
}
