package com.unicef.thaimai.motherapp.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.unicef.thaimai.motherapp.R;


public class ClinicalReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_clinical);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Back");

        actionBar.setHomeButtonEnabled(true);

        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        Intent intent = new Intent(ClinicalReportActivity.this, MainActivity.class);
        finish();
//        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
