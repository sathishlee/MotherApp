package com.unicef.thaimai.motherapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.unicef.thaimai.motherapp.R;


/**
 * Created by suthishan on 1/8/2018.
 */

public class Help extends AppCompatActivity {
    ExpandableRelativeLayout expandableLayout1, expandableLayout2, expandableLayout3, expandableLayout4;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_help);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Help");

        actionBar.setHomeButtonEnabled(true);

        actionBar.setDisplayHomeAsUpEnabled(true);


    }
        public void expandableButton1(View view) {
            expandableLayout1 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout1);
            expandableLayout1.toggle(); // toggle expand and collapse
        }

        public void expandableButton2(View view) {
            expandableLayout2 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout2);
            expandableLayout2.toggle(); // toggle expand and collapse
        }

        public void expandableButton3(View view) {
            expandableLayout3 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout3);
            expandableLayout3.toggle(); // toggle expand and collapse
        }

        public void expandableButton4(View view) {
            expandableLayout4 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout4);
            expandableLayout4.toggle(); // toggle expand and collapse
        }

    @Override
    public void onBackPressed() {
        intent = new Intent(Help.this, MainActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(Help.this, MainActivity.class);
        finish();
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}

