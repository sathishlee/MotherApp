package com.unicef.thaimai.motherapp.activity;

import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import suthishan.navigationwithbottom.R;

public class PrimaryRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.primary_register);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Primary Register");

        actionBar.setHomeButtonEnabled(true);

        actionBar.setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(PrimaryRegister.this, Referral.class);
        finish();
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
