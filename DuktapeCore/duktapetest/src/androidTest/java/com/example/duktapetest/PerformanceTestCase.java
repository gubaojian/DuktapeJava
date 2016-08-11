package com.example.duktapetest;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.furture.react.DuktapeEngine;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(AndroidJUnit4.class)
public class PerformanceTestCase extends ActivityInstrumentationTestCase2<MainActivity> {


    Activity mActivity;
    DuktapeEngine engine;
    String script;

    public PerformanceTestCase() {
        super(MainActivity.class);
    }



    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
        engine = new DuktapeEngine();
        engine.put("activity", getActivity());
        script = AssetScript.toScript(getActivity(), "PerformanceTest.js");
    }

    @After
    public void tearDown() throws Exception {
        engine.destory();
        super.tearDown();
    }





    @Test
    public void testPerformance(){
        Object object = engine.execute(script);
        Assert.assertTrue(object != null);
    }


    @Test
    public void testPerformance2(){
        Object object = engine.execute(script);
        Assert.assertTrue(object != null);
    }


    @Test
    public void testPerformance3(){
        Object object = engine.execute(script);
        Assert.assertTrue(object != null);
    }


    @Test
    public void testPerformance4(){
        Object object = engine.execute(script);
        Assert.assertTrue(object != null);
    }


    @Test
    public void testPerformance5(){
        Object object = engine.execute(script);
        Assert.assertTrue(object != null);
    }


    @Test
    public void testPerformance6(){
        Object object = engine.execute(script);
        Assert.assertTrue(object != null);
    }


    @Test
    public void testPerformance7(){
        Object object = engine.execute(script);
        Assert.assertTrue(object != null);
    }


    @Test
    public void testPerformance8(){
        Object object = engine.execute(script);
        Assert.assertTrue(object != null);
    }


    @Test
    public void testPerformance9(){
        Object object = engine.execute(script);
        Assert.assertTrue(object != null);
    }



    @Test
    public void testPerformance10(){
        Object object = engine.execute(script);
        Assert.assertTrue(object != null);
    }

    @Test
    public void testPerformance11(){
        Object object = engine.execute(script);
        Assert.assertTrue(object != null);
    }

    @Test
    public void testPerformance12(){
        Object object = engine.execute(script);
        Assert.assertTrue(object != null);
    }

    @Test
    public void testPerformanceMuti(){
        int num = 20;
        final CountDownLatch count = new CountDownLatch(num);
        for(int i=0; i<num; i++){
            new Thread(new Runnable(){

                @Override
                public void run() {
                    DuktapeEngine engine;
                    engine = new DuktapeEngine();
                    engine.put("activity", getActivity());
                    Object object = engine.execute(script);
                    Log.e("duktape", "result " + object);
                    engine.destory();
                    count.countDown();
                }
            }).start();
        }
        try {
            count.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }
}