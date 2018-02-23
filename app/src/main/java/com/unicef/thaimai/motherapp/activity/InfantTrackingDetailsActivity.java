package com.unicef.thaimai.motherapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.unicef.thaimai.motherapp.R;

public class InfantTrackingDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infant_tracking_details);
        this.setTitle("Infant Tracking Details");

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.menu_edit:
                Intent i  = new Intent(getApplicationContext(), InfantTrackingDetailsEditActivity.class);
                startActivity(i);


                return true;
            default:
                super.onOptionsItemSelected(item);
        }return true;


    }
}
