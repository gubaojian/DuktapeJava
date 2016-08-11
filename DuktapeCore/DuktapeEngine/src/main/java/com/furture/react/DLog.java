package com.furture.react;

import android.util.Log;

/**
 *
 * 
 * */
public class DLog {
	
	public static boolean DEBUG = true;
	
	
	public static void d(String tag, String msg) {
		  if (DEBUG) {
			   Log.d(tag, msg);
		  }
	}

	public static void e(String tag,String message, Throwable e) {
		Log.e(tag, message, e);
	}
	  
}
