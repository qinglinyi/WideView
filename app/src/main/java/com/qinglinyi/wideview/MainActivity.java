package com.qinglinyi.wideview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WideView mWideView = (WideView) findViewById(R.id.mWideView);

        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                if (mWideView != null) {
                    mWideView.start();
                }
            }
        });
    }
}
