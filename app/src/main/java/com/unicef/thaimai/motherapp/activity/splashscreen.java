package com.unicef.thaimai.motherapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.unicef.thaimai.motherapp.R;


public class splashscreen extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.splash);


        Thread background = new Thread() {
            public void run() {

                try {
                    sleep(4000);
                    Intent i = new Intent(getApplicationContext(), Register.class);
                    startActivity(i);


                    finish();

                } catch (Exception e) {

                }
            }
        };


        background.start();


    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }
}