package com.unicef.thaimai.motherapp.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.fragment.NotificationFragment;

public class PostnatalMotherVisitActivity extends AppCompatActivity {
LinearLayout postnatal_mother;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postnatal_mother_visit);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Add Records");

        actionBar.setHomeButtonEnabled(true);

        actionBar.setDisplayHomeAsUpEnabled(true);
        postnatal_mother = (LinearLayout)findViewById(R.id.ll_postnatal_mother);

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
                Intent i  = new Intent(getApplicationContext(), PostnatalMotherVisit_editActivity.class);
                startActivity(i);


                return true;
            default:
                super.onOptionsItemSelected(item);
                finish();
        }return true;


    }
}
