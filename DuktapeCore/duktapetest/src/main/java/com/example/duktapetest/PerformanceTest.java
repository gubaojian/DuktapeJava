package com.example.duktapetest;

import android.app.Activity;
import android.util.Log;

import com.furture.react.DuktapeEngine;


/**
 * Created by furture on 16/7/30.
 */
public class PerformanceTest {


    public void start(final Activity activity, int num, final String script){
        for(int i=0; i<num; i++){
            new Thread(new Runnable(){

                @Override
                public void run() {
                    DuktapeEngine engine;
                    engine = new DuktapeEngine();
                    engine.put("activity", activity);
                    Object object = engine.execute(script);
                    Log.e("duktape", "result " + object);
                    engine.destory();
                }
            }).start();
        }
    }
}
