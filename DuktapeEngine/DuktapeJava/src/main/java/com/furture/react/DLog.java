package com.furture.react;

import android.util.Log;

/**
 * 1、100个activity进行测试
 * 
 * 
 * */
public class DLog {
	
	  public static boolean DEBUG = true;
	
	
	  public static void e(String tag, String msg) {
		  if (DEBUG) {
			   Log.e(tag, msg);
		  }
	  }
	  
}
