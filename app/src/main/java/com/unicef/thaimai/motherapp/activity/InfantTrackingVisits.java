package com.unicef.thaimai.motherapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.unicef.thaimai.motherapp.R;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class InfantTrackingVisits extends AppCompatActivity {

    TextView txt_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infant_tracking_details);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Infant Visit");

        actionBar.setHomeButtonEnabled(true);

        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView txt_edit = (TextView) findViewById(R.id.txt_edit);

        txt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InfantTrackingVisits.this, InfantTrackingEditActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(InfantTrackingVisits.this, InfantTrackingActivity.class);
        finish();
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
