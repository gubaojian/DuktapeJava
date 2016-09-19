package com.example.duktapetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String script = AssetScript.toScript(MainActivity.this, "PerformanceTest.js");


        findViewById(R.id.performance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ScriptEngine", script);
                PerformanceTest performanceTest = new PerformanceTest();
                performanceTest.start(MainActivity.this, 4, script);
                Toast.makeText(MainActivity.this, "start run script", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
