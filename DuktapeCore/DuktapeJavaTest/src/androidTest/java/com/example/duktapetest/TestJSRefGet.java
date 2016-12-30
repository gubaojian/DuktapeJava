package com.example.duktapetest;

import com.furture.react.JSRef;

import junit.framework.Assert;

/**
 * Created by furture on 16/12/29.
 */

public class TestJSRefGet {


    public void testGet(JSRef args){
        String name = args.getString("name");
        Assert.assertEquals("gubaojian", name);
        args.set("name", "gubaojian-set");
        name = args.getString("name");
        Assert.assertEquals("gubaojian-set", name);
    }


    public void testGetLength(JSRef args){
        String name = args.getString("length");
        Assert.assertEquals("index 0", args.getString("0"));
        Assert.assertTrue("length > 0", Double.parseDouble(name) > 0);
        args.set("0", "set-0");
        Assert.assertEquals("set-0", args.getString("0"));
    }
}
