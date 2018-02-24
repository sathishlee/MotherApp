package com.unicef.thaimai.motherapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.unicef.thaimai.motherapp.R;

public class PNMotherVisitActivity extends AppCompatActivity {
LinearLayout postnatal_mother;
    CardView pn_visit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pn_mother_visits);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("PN Mother Visits");

        actionBar.setHomeButtonEnabled(true);

        actionBar.setDisplayHomeAsUpEnabled(true);

        CardView pn_visit = (CardView) findViewById(R.id.pn_visit);

        pn_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PNMotherVisitActivity.this, PNMotherVisitListActivity.class);
                startActivity(i);
            }
        });

//        postnatal_mother = (LinearLayout)findViewById(R.id.ll_postnatal_mother);

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
//            case R.id.menu_edit:
//                Intent i  = new Intent(getApplicationContext(), PNMotherVisit_editActivity.class);
//                startActivity(i);
//                return true;
//            default:
//                super.onOptionsItemSelected(item);
//                finish();
//        }return true;
//
//    }
@Override
public boolean onOptionsItemSelected(MenuItem item) {
    Intent intent = new Intent(PNMotherVisitActivity.this, MainActivity.class);
    finish();
    startActivity(intent);
    return super.onOptionsItemSelected(item);
}

}
