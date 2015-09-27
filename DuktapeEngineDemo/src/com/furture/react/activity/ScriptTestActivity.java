package com.furture.react.activity;


import com.furture.react.DuktapeEngine;
import com.furture.react.JSApi;
import com.furture.react.demo.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ScriptTestActivity extends Activity {
	
	private DuktapeEngine duktapeEngine;
	private MediaApi mediaApi;
	private static final String ACTIVITY_LISTENER = "activityListener";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_script_test);
		duktapeEngine = new DuktapeEngine();
		duktapeEngine.init();
		duktapeEngine.register("activity",this);
		duktapeEngine.register("ui", new UIApi(this));
		mediaApi = new MediaApi(this);
		duktapeEngine.register("media", mediaApi);
		Object value = duktapeEngine.execute(AssetScript.toScript(getBaseContext(), getIntent().getStringExtra("file")));
		if (value != null) {
			((Button)findViewById(R.id.button1)).setText(value.toString());
		}else {
			((Button)findViewById(R.id.button1)).setText("Failed");
		}
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == MediaApi.CAMERA_REQUEST_CODE){
			mediaApi.onActivityResult(requestCode, resultCode, data);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		if (duktapeEngine != null) {
			duktapeEngine.destory();
			duktapeEngine = null;
		}
		super.onDestroy();
	}
	
	
	
	
}
