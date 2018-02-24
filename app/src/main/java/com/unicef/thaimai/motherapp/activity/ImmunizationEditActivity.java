package com.unicef.thaimai.motherapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.unicef.thaimai.motherapp.R;

public class ImmunizationEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immunization_edit);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Immunization");

        actionBar.setHomeButtonEnabled(true);

        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(ImmunizationEditActivity.this, ImmunizationVisit.class);
        finish();
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
