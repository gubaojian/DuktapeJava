package com.furture.react.hybrid;

import java.io.File;
import java.net.URL;

import org.mozilla.javascript.ast.NewExpression;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.furture.react.DuktapeEngine;
import com.furture.react.demo.R;
import com.furture.react.activity.AssetScript;

public class HybridActivity extends Activity{
	
	private static final String DIR = "/web_app/";
	private static final String ACTIVITY_LISTENER = "activityListener";
	
	private DuktapeEngine duktapeEngine;
	private ProgressBar progessBar;
	private HybridManager mHybridManager;
	private AsyncTask<Void, Void, String> loadAsyncTask;

	private String uri;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hybrid);
		progessBar = (ProgressBar) findViewById(R.id.progress_bar);
		String url = getIntent().getStringExtra("url");
		if (TextUtils.isEmpty(url)) {
			Toast.makeText(getBaseContext(), "Invalid Request URL", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		loadPage(url, savedInstanceState);
	}
	
	protected void loadPage(final String pageUrl, final Bundle savedInstanceState){
		loadAsyncTask = new  AsyncTask<Void, Void, String>(){

			@Override
			protected String doInBackground(Void... params) {
				try {
					URL url = new URL(pageUrl);
					String name = url.getPath();
					File appFile  =  new File(Environment.getExternalStorageDirectory() + name);
					if(!appFile.exists()){
						File appPath = new File(getBaseContext().getFilesDir().getAbsolutePath() +  DIR);
						if (!appPath.exists()) {
							  appPath.mkdirs();
						}
						appFile  = new File(appPath + "/" + name);
						DownloadUtils.downloadZip(url, appFile);
					}
					mHybridManager = new HybridManager(HybridActivity.this, appFile);
					duktapeEngine.put("app",mHybridManager);
					ZipUtils.unzip(appFile, new File(Environment.getExternalStorageDirectory() + "/loginapk/"));
					return AssetScript.toScript(mHybridManager.getAssets().open("login.js"));
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}

			@Override
			protected void onPostExecute(String indexJS) {
				if (TextUtils.isEmpty(indexJS)) {
					Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
					finish();
					return;
				}
				duktapeEngine.execute(indexJS);
				duktapeEngine.call(ACTIVITY_LISTENER, "onCreate", savedInstanceState);
			}
		};
		if (duktapeEngine != null) {
			duktapeEngine.call(ACTIVITY_LISTENER, "unLoad");
			duktapeEngine.destory();
		}
		duktapeEngine = new DuktapeEngine();
		duktapeEngine.put("activity",this);
		loadAsyncTask.execute();
	}

	@Override
	protected void onStart() {
		super.onStart();
		if(duktapeEngine != null){
		    duktapeEngine.call(ACTIVITY_LISTENER, "onStart");
		}
	}

    public void onTemplateLoaded(){
    	    
    }
    
     public void onTemplateFailed(){
    	
    }
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		if(duktapeEngine != null){
		    duktapeEngine.call(ACTIVITY_LISTENER, "onResume");
		}
	}
	
	

	@Override
	protected void onPause() {
		if(duktapeEngine != null){
		     duktapeEngine.call(ACTIVITY_LISTENER, "onPause");
		}
		super.onPause();
	}

	@Override
	protected void onStop() {
		if(duktapeEngine != null){
		    duktapeEngine.call(ACTIVITY_LISTENER, "onStop");
		}
		super.onStop();
	}

	@Override
	public void finish() {
		if(duktapeEngine != null){
			duktapeEngine.call(ACTIVITY_LISTENER, "unLoad");
		    duktapeEngine.call(ACTIVITY_LISTENER, "finish");
		}
		super.finish();
	}

	@Override
	protected void onDestroy() {
		if(loadAsyncTask != null){
			loadAsyncTask.cancel(true);
			loadAsyncTask = null;
		}
		if (duktapeEngine != null) {
			duktapeEngine.destory();
			duktapeEngine = null;
		}
		super.onDestroy();
	}
}
