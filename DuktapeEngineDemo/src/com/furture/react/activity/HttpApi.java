package com.furture.react.activity;

import java.io.IOException;

import android.os.Handler;
import android.os.Looper;

import com.furture.react.JSRef;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class HttpApi {
	
	
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
				final String json = response.body().string();
				handler.post(new Runnable() {
					@Override
					public void run() {
						callback.getEngine().callJSRef(callback, "response", json);
					}
				});
			}
			
			@Override
			public void onFailure(Request req, IOException exception) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						callback.getEngine().callJSRef(callback, "response", "");
					}
				});
			}
		});
	}
	
	private static final OkHttpClient okHttpClient = new OkHttpClient();
	private static final Handler handler = new Handler(Looper.getMainLooper());

}
