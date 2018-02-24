package com.unicef.thaimai.motherapp.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.unicef.thaimai.motherapp.R;

public class InfantTrackingActivity extends AppCompatActivity {

    CardView infant_tracking_visit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infant_tracking);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Infant Tracking");

        actionBar.setHomeButtonEnabled(true);

        actionBar.setDisplayHomeAsUpEnabled(true);

      CardView infant_tracking_visit = (CardView) findViewById(R.id.infant_tracking_visit);

        infant_tracking_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InfantTrackingActivity.this,InfantTrackingVisits.class);
                startActivity(i);
            }
        });



    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.edit_menu, menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//
//
//            case R.id.menu_edit:
//                Intent i  = new Intent(getApplicationContext(), InfantTrackingEditActivity.class);
//                startActivity(i);
//                return true;
//            default:
//                super.onOptionsItemSelected(item);
//        }return true;
//
//
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(InfantTrackingActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
