package com.unicef.thaimai.motherapp.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;

import com.unicef.thaimai.motherapp.R;


public class ReferralList extends AppCompatActivity {

    CardView referral_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_referral);


        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Referral List");

        actionBar.setHomeButtonEnabled(true);

        actionBar.setDisplayHomeAsUpEnabled(true);

        CardView referral_display = (CardView)findViewById(R.id.referral_display);

        referral_display.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent e = new Intent(ReferralList.this, ReferralActivity.class);
                startActivity(e);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(ReferralList.this, AddReferral.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(ReferralList.this, MainActivity.class);
        finish();
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
