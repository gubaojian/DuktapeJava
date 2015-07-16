package com.furture.react.activity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.efurture.script.CodeTransformer;

import android.content.Context;
import android.util.Log;

public class AssetScript {

	
	public static String toScript(Context context, String fileName){
		try {
			InputStream is = context.getAssets().open(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String script = CodeTransformer.parse(br);
			Log.e("ScriptEngine", script);
			return script;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public static String toScript(String fileName){
		try {
			InputStream is = new FileInputStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String script =  CodeTransformer.parse(br);
			Log.e("ScriptEngine", script);
			return script;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public static String toScript(InputStream is){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String script =  CodeTransformer.parse(br);
			Log.e("ScriptEngine", script);
			return script;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
