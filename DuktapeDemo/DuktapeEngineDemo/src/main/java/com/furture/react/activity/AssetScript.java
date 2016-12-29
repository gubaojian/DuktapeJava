package com.furture.react.activity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.efurture.script.JSTransformer;

public class AssetScript {


	private static Map<String,String> scripts =new HashMap<String,String>();

	public static String read(Context context, String fileName){
		try {
			InputStream is = context.getAssets().open(fileName);
			ByteArrayOutputStream output = new ByteArrayOutputStream();

			BufferedInputStream bufferIn = new BufferedInputStream(is);
			byte[] buffer = new byte[2048];
			int length = 0;
			while ((length = bufferIn.read(buffer)) >= 0){
				 output.write(buffer, 0, length);
			}
			String script = output.toString();
			is.close();
			Log.e("compiled ScriptEngine", script);
			return script;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String toScript(Context context, String fileName){
		try {
			String script = scripts.get(fileName);
			if(!TextUtils.isEmpty(script)){
				return  script;
			}
			Log.e("DuktapeJava", "Read" );
			InputStream is = context.getAssets().open(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			Log.e("DuktapeJava", "Transform" );
			script = JSTransformer.parse(br);
			is.close();
			scripts.put(fileName, script);
			Log.e("ScriptEngine", script);
			return script;
		} catch (Exception e) {
			Log.e("ScriptEngine", e.toString());
			throw new RuntimeException(e);
		}
	}


	
	
	public static String toScript(String fileName){
		try {
			InputStream is = new FileInputStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String script =  JSTransformer.parse(br);
			is.close();
			Log.e("ScriptEngine", script);
			return script;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public static String toScript(InputStream is){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String script =  JSTransformer.parse(br);
			Log.e("ScriptEngine", script);
			return script;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
