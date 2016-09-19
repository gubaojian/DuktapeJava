package com.furture.react;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityUnitTestCase;
import android.widget.TextView;
import static org.junit.Assert.*;

/**
 * Created by furture on 16/7/27.
 */
public class HelloActivity extends ActivityUnitTestCase<HelloActivity.MyActivity> {

    /**
     * For Eclipse with ADT
     */
    public static junit.framework.Test suite() {
        // Should use AndroidJUnit4TestAdapter for to running AndroidDependent
        // TestCases.
        return new AndroidJUnit4TestAdapter(HelloActivity.class);
    }

    public static class MyActivity extends Activity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            TextView view = new TextView(this);
            view.setText("Hello, activity.");
            view.setId(android.R.id.text1);
            setContentView(view);
        }
    }

    private Intent startIntent;

    public HelloActivity() {
        super(MyActivity.class);
    }

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        startIntent = new Intent(Intent.ACTION_MAIN);
    }

    @Test
    public void assertPreconditions() {
        startActivity(startIntent, null, null);
        assertNotNull(getActivity());
    }

    @Test
    public void sayHello() {
        startActivity(startIntent, null, null);
        assertThat(((TextView) getActivity().findViewById(android.R.id.text1)).getText()
                .toString(), equalTo("Hello, activity."));
    }

}