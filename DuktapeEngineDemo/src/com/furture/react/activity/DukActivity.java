package com.furture.react.activity;

import android.app.Activity;
import android.os.Bundle;

import com.furture.react.DuktapeEngine;

public class DukActivity  extends Activity{
	
	private DuktapeEngine duktapeEngine;
	private static final String ACTIVITY_LISTENER = "activityListener";
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		duktapeEngine = new DuktapeEngine();
		duktapeEngine.init();
		duktapeEngine.register("activity",this);
		duktapeEngine.execute(AssetScript.toScript(getBaseContext(), "duk.js"));
		duktapeEngine.callJs(ACTIVITY_LISTENER, "onCreate", savedInstanceState);
	}

	@Override
	protected void onStart() {
		super.onStart();
		duktapeEngine.callJs(ACTIVITY_LISTENER, "onStart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		duktapeEngine.callJs(ACTIVITY_LISTENER, "onResume");
	}
	
	

	@Override
	protected void onPause() {
		duktapeEngine.callJs(ACTIVITY_LISTENER, "onPause");
		super.onPause();
	}

	@Override
	protected void onStop() {
		duktapeEngine.callJs(ACTIVITY_LISTENER, "onStop");
		super.onStop();
	}

	@Override
	public void finish() {
		duktapeEngine.callJs(ACTIVITY_LISTENER, "finish");
		super.finish();
	}

	@Override
	protected void onDestroy() {
		if (duktapeEngine != null) {
			duktapeEngine.destory();
			duktapeEngine = null;
		}
		super.onDestroy();
	}
	
	
	public void callTest(){
		callTest1();
	}
	
	public void callTest1(){
		callTest2();
	}
	
	public void callTest2(){
		throw new RuntimeException("Call RuntimeException Test");
	}
	
	
}
