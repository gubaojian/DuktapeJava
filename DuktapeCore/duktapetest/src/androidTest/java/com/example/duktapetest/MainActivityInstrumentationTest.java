package com.example.duktapetest;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;


import com.furture.react.DuktapeEngine;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by furture on 16/7/27.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentationTest extends ActivityInstrumentationTestCase2<MainActivity> {


    Activity mActivity;
    public MainActivityInstrumentationTest() {
        super(MainActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        // Injecting the Instrumentation instance is required
        // for your test to run with AndroidJUnitRunner.
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
    }


    @Test
    public void testInit(){
        DuktapeEngine engine = new DuktapeEngine();
    }



}
