package com.furture.react.ext;

import com.furture.react.JSRef;

import android.os.AsyncTask;

public class JSAynckTask extends AsyncTask<Object, Void, Object> {

	private JSRef mJSRef;
	
	public JSAynckTask(JSRef jsRef) {
		super();
		this.mJSRef = jsRef;
	}


	@Override
	protected Object doInBackground(Object... params) {
		return mJSRef.getEngine().callJSRef(mJSRef, "doInBackground", params);
	}


	@Override
	protected void onPostExecute(Object result) {
		mJSRef.getEngine().callJSRef(mJSRef, "onPostExecute", result);
	}
	
}
