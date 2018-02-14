package com.unicef.thaimai.motherapp.activity;

import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.unicef.thaimai.motherapp.R;


public class AddReferral extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_refrrel);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Add Referral");

        actionBar.setHomeButtonEnabled(true);

        actionBar.setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(AddReferral.this, Referral.class);
        finish();
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
