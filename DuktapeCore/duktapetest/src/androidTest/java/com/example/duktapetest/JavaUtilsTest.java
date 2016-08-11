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
public class JavaUtilsTest extends ActivityInstrumentationTestCase2<MainActivity> {


    public JavaUtilsTest() {
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
        Assert.assertTrue(Boolean.valueOf(result.toString()));
        engine.destory();
    }
}
