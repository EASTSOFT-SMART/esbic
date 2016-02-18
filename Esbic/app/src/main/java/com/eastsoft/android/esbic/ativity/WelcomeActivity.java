package com.eastsoft.android.esbic.ativity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.eastsoft.android.esbic.R;

/**
 * Created by sofa on 2016/1/22.
 */
public class WelcomeActivity extends Activity {
    private static final int SPLASH_TIME=3000;
    Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_main);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.setClass(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
            }
        },SPLASH_TIME);
    }
}
