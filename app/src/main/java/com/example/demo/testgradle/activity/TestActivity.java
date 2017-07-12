package com.example.demo.testgradle.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.demo.testgradle.R;
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class TestActivity  extends AppCompatActivity {
    private static final int REQUEST_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        findViewById(R.id.checkIfPossibleToRecordButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ScreenshotManager.INSTANCE.requestScreenshotPermission(TestActivity.this, REQUEST_ID);
            }
        });
        findViewById(R.id.takeScreenshotButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ScreenshotManager.INSTANCE.takeScreenshot(TestActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ID)
            ScreenshotManager.INSTANCE.onActivityResult(resultCode, data);
    }
}