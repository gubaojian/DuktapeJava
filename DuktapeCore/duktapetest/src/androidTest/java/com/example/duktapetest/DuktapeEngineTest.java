package com.example.duktapetest;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.furture.react.DuktapeEngine;
import com.furture.react.JavaUtils;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by furture on 16/7/30.
 */
@RunWith(AndroidJUnit4.class)
public class DuktapeEngineTest extends ActivityInstrumentationTestCase2<MainActivity> {


    public DuktapeEngineTest() {
        super(MainActivity.class);
    }


    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void testJavaUtils(){
        DuktapeEngine engine = new DuktapeEngine();
        Object result = engine.execute(AssetScript.toScript(getActivity(), "JavaUtilsTest.js"));
        Assert.assertNotNull("script run error, please see logcat",result);
        Assert.assertEquals(result.toString(), "true");
        engine.destory();
    }


    @Test
    public void testArray(){
        DuktapeEngine engine = new DuktapeEngine();
        Object result = engine.execute(AssetScript.toScript(getActivity(), "ArrayTest.js"));
        Assert.assertNotNull("script run error, please see logcat",result);
        Assert.assertEquals(result.toString(), "true");
        engine.destory();
    }



    @Test
    public void testJSRef(){
        DuktapeEngine engine = new DuktapeEngine();
        Object result = engine.execute(AssetScript.toScript(getActivity(), "JSRefTest.js"));
        Assert.assertNotNull("script run error, please see logcat",result);
        Assert.assertEquals(result.toString(), "true");
        engine.destory();
    }


    @Test
    public void testJavaCallJSTest(){
        DuktapeEngine engine = new DuktapeEngine();
        Object result = engine.execute(AssetScript.toScript(getActivity(), "JavaCallJSTest.js"));
        Assert.assertNotNull("script run error, please see logcat",result);
        Assert.assertEquals(result.toString(), "true");
        engine.destory();
    }


    @Test
    public void testNormalEngine(){
        DuktapeEngine engine = new DuktapeEngine();
        Object result = engine.execute(AssetScript.toScript(getActivity(), "NormalEngineTest.js"));
        Assert.assertNotNull("script run error, please see logcat",result);
        Assert.assertEquals(result.toString(), "true");
        engine.destory();
    }


    @Test
    public void testNewInstance(){
        DuktapeEngine engine = new DuktapeEngine();
        Object result = engine.execute(AssetScript.toScript(getActivity(), "NewInstanceTest.js"));
        Assert.assertNotNull("script run error, please see logcat",result);
        Assert.assertEquals(result.toString(), "true");
        engine.destory();
    }
}
