package com.example.duktapetest;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.furture.react.DuktapeEngine;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
        engine.put("activity", getActivity());
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
        Object result = engine.execute(AssetScript.toScript(getActivity(), "JavaCallJsTest.js"));
        Assert.assertNotNull("script run error, please see logcat",result);
        Assert.assertEquals(result.toString(), "true");
        engine.destory();
    }


    @Test
    public void testNormalEngine(){
        DuktapeEngine engine = new DuktapeEngine();
        engine.put("activity", getActivity());
        Object result = engine.execute(AssetScript.toScript(getActivity(), "NormalEngineTest.js"));
        Assert.assertNotNull("script run error, please see logcat",result);
        Assert.assertEquals(result.toString(), "true");
        engine.destory();
    }


    @Test
    public void testNewInstance(){
        DuktapeEngine engine = new DuktapeEngine();
        engine.put("activity", getActivity());
        Object result = engine.execute(AssetScript.toScript(getActivity(), "NewInstanceTest.js"));
        Assert.assertNotNull("script run error, please see logcat",result);
        Assert.assertEquals(result.toString(), "true");
        engine.destory();
    }


    @Test
    public void testIntentNumber(){
        DuktapeEngine engine = new DuktapeEngine();
        engine.put("activity", getActivity());
        Object result = engine.execute(AssetScript.toScript(getActivity(), "NumberIntentTest.js"));
        Assert.assertNotNull("script run error, please see logcat",result);
        Assert.assertEquals(result.toString(), "true");
        engine.destory();
    }

    @Test
    public void testPureJavaScript(){
        DuktapeEngine engine = new DuktapeEngine();
        engine.put("activity", getActivity());
        Object result = engine.execute(AssetScript.toScript(getActivity(), "PureJavaScript.js"));
        Assert.assertNotNull("script run error, please see logcat",result);
        Assert.assertEquals(result.toString(), "true");
        engine.destory();
    }

    @Test
    public void testBugMultiError(){
        DuktapeEngine engine = new DuktapeEngine();
        engine.put("activity", getActivity());
        Object result = engine.execute(AssetScript.toScript(getActivity(), "bug/multiError.js"));
        Assert.assertNotNull("script run error, please see logcat",result);
        Assert.assertEquals(result.toString(), "true");
        engine.destory();
    }

    @Test
    public void testExpectEqualsErrorRunFail(){
        DuktapeEngine engine = new DuktapeEngine();
        engine.put("activity", getActivity());
        Object result = engine.execute(AssetScript.toScript(getActivity(), "bug/expectEqualsErrorRunFail.js"));
        Assert.assertNull("script run error, return null",result);
        engine.destory();
    }


    @Test
    public  void testErrorMe(){
        /**
         * 09-01 15:25:24.108 22890-22890/com.efurture.hybrid.demo E/ScriptEngine: ScriptEngine CallJSRef  1629993456 method getItemViewType exception TypeError: undefined not callable
         duk_js_call.c:776
         * */
    }
}
