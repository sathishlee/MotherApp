package com.unicef.thaimai.motherapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.unicef.thaimai.motherapp.R;

public class PNMotherVisit_editActivity extends AppCompatActivity {
Button btn_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postnatal_mother_visit_edit);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("PN Visit Edit");

        actionBar.setHomeButtonEnabled(true);

        actionBar.setDisplayHomeAsUpEnabled(true);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(PNMotherVisit_editActivity.this, PNMotherVisitActivity.class);
        finish();
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}

