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
		long start = System.currentTimeMillis();
		duktapeEngine.execute(AssetScript.read(getBaseContext(), "convert/index.js"));
		System.out.println("used " + (System.currentTimeMillis() - start) + "  ms ");
		duktapeEngine.call(ACTIVITY_LISTENER, "onCreate", savedInstanceState);
	}

	@Override
	protected void onStart() {
		super.onStart();
		duktapeEngine.call(ACTIVITY_LISTENER, "onStart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		duktapeEngine.call(ACTIVITY_LISTENER, "onResume");
	}
	
	

	@Override
	protected void onPause() {
		duktapeEngine.call(ACTIVITY_LISTENER, "onPause");
		super.onPause();
	}

	@Override
	protected void onStop() {
		duktapeEngine.call(ACTIVITY_LISTENER, "onStop");
		super.onStop();
	}

	@Override
	public void finish() {
		duktapeEngine.call(ACTIVITY_LISTENER, "finish");
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
