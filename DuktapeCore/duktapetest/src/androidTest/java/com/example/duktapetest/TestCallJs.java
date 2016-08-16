package com.example.duktapetest;

import com.furture.react.JSRef;

import junit.framework.Assert;

/**
 * Created by furture on 16/8/16.
 */
public class TestCallJs {

    JSRef jsRef;

    public TestCallJs(JSRef jsRef) {
        this.jsRef = jsRef;
    }

    public void  testCallJSRef(){
        Object result =  jsRef.call("run", "hello javascript");
        Assert.assertEquals(result.toString(), "hello from javascript ref");
    }


    public void  testCallJSRefFunction(JSRef func){
        Object result =  func.call("run");
        Assert.assertEquals(result.toString(), "hello from javascript ref function");


    }


    public void  testCallHello(){
        String result = (String) jsRef.getEngine().call("hello", null);
        Assert.assertEquals(result, "hello from javascript plain function");

    }

    public void  testCallPageHello(){
        String result = (String) jsRef.getEngine().call("page", "hello");
        Assert.assertEquals(result, "hello from javascript page object function");
    }
}
