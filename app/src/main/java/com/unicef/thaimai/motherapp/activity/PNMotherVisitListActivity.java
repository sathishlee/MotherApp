package com.unicef.thaimai.motherapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.adapter.MothervistListAdapter;
import com.unicef.thaimai.motherapp.adapter.MyAdapter;
import com.unicef.thaimai.motherapp.model.NotificationModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PNMotherVisitListActivity extends AppCompatActivity {
MothervistListAdapter mothervistListAdapter;
ArrayList<String> mothersVisitList;
    private List<NotificationModel> visitList;

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;

    TextView txt_edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_postnatal_mother_visit_list);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postnatal_mother_visit);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("PN Mother Visits");

        actionBar.setHomeButtonEnabled(true);

        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView txt_edit = (TextView) findViewById(R.id.txt_edit);

        txt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PNMotherVisitListActivity.this, PNMotherVisit_editActivity.class);
                startActivity(i);
            }
        });

//        mRecyclerView = (RecyclerView) findViewById(R.id.rcv_pnmlist);
//        mRecyclerView.setHasFixedSize(true);
//
//        // use a linear layout manager
//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
////        visitList=new ArrayList<>();
//        // specify an adapter (see also next example)
//        mothervistListAdapter = new MothervistListAdapter(this,Arrays.asList(getResources().getStringArray(R.array.array_visit)));
//        mRecyclerView.setAdapter(mothervistListAdapter);
////        prepareMovieData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(PNMotherVisitListActivity.this, PNMotherVisitActivity.class);
        finish();
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

}