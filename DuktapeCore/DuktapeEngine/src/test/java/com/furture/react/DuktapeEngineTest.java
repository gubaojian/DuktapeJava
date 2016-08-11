package com.furture.react;

import org.junit.Before;
import org.junit.runner.RunWith;
import android.support.test.runner.AndroidJUnit4;

import static org.junit.Assert.*;

/**
 * Created by furture on 16/7/27.
 */
@RunWith(AndroidJUnit4.class)
public class DuktapeEngineTest {

    static {
        System.setProperty("java.library.path", "/Users/furture/code/DuktapeJava/DuktapeCore/DuktapeEngine/src/main/jniLibs/x86");
    }

    @Before
    @UiThreadTest
    public void setUp() throws Exception {
        DuktapeEngine duktapeEngine = new DuktapeEngine();
    }

    @org.junit.Test
    public void testInit() throws Exception {
        //duktapeEngine.init();
    }


}