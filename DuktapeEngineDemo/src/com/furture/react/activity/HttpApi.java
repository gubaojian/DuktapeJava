package com.furture.react.activity;

import java.io.IOException;

import org.json.JSONObject;

import android.os.Handler;
import android.os.Looper;

import com.furture.react.DLog;
import com.furture.react.JSRef;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class HttpApi {
	
	private static final JSONObject emptyJson = new JSONObject();
	private static final String CALL_BACK_METHOD_NAME = "response";
 	
	public void get(String url, JSRef callback){
		Request request = new Request.Builder().get().url(url).build();
		execute(request, callback);
	} 
	
	public void post(String url, String json, JSRef callback){
		RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
		Request request = new Request.Builder().post(requestBody).url(url).build();
		execute(request, callback);
	} 
	
	
	private void  execute(Request request, final JSRef callback){
		okHttpClient.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(Response response) throws IOException {
				
				try {
					final JSONObject json = new JSONObject(response.body().string());
					handler.post(new Runnable() {
						@Override
						public void run() {
							callback.getEngine().call(callback, CALL_BACK_METHOD_NAME, json);
						}
					});
				} catch (Exception e) {
					DLog.e("Http Client Parse JSON Error",  e.getMessage());
					handler.post(new Runnable() {
						@Override
						public void run() {
							callback.getEngine().call(callback, CALL_BACK_METHOD_NAME, emptyJson);
						}
					});
				}
			}
			
			@Override
			public void onFailure(Request req, IOException exception) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						callback.getEngine().call(callback, CALL_BACK_METHOD_NAME, emptyJson);
					}
				});
			}
		});
	}
	
	private static final OkHttpClient okHttpClient = new OkHttpClient();
	private static final Handler handler = new Handler(Looper.getMainLooper());

}
