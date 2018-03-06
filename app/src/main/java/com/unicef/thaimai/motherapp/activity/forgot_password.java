package com.unicef.thaimai.motherapp.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.unicef.thaimai.motherapp.R;


/**
 * Created by Suthishan on 20/1/2018.
 */

public class forgot_password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_picme);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Forgot Password");

        actionBar.setHomeButtonEnabled(true);

        actionBar.setDisplayHomeAsUpEnabled(true);

    }
}
