package com.furture.react.activity.ext;

import android.os.AsyncTask;

import com.furture.react.JSRef;

/**
 * Created by furture on 16/6/24.
 */
public class AsyncTaskImpl extends AsyncTask {

    JSRef jsRef;


    public AsyncTaskImpl(JSRef jsRef) {
        this.jsRef = jsRef;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return jsRef.getEngine().call(jsRef, "doInBackground", objects);
    }

    @Override
    protected void onPostExecute(Object o) {
         jsRef.getEngine().call(jsRef, "onPostExecute", o);
    }

    @Override
    protected void onCancelled(Object o) {
        jsRef.getEngine().call(jsRef, "onCancelled", o);
    }



}
