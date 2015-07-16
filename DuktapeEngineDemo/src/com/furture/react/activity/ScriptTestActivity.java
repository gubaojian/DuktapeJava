package com.furture.react.activity;

import com.furture.react.DuktapeEngine;
import com.furture.react.demo.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ScriptTestActivity extends Activity {
	private DuktapeEngine duktapeEngine;
	private static final String ACTIVITY_LISTENER = "activityListener";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_script_test);
		duktapeEngine = new DuktapeEngine();
		duktapeEngine.init();
		duktapeEngine.register("activity",this);
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
}
