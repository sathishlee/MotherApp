package com.unicef.thaimai.motherapp.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.unicef.thaimai.motherapp.R;

public class ImmunizationVisit extends AppCompatActivity {


    TextView txt_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immunization);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Immunization Visit");

        actionBar.setHomeButtonEnabled(true);

        actionBar.setDisplayHomeAsUpEnabled(true);


        TextView txt_edit = (TextView) findViewById(R.id.txt_edit);

        txt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ImmunizationVisit.this, ImmunizationEditActivity.class);
                startActivity(i);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(ImmunizationVisit.this, ImmunizationActivity.class);
        finish();
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

}
