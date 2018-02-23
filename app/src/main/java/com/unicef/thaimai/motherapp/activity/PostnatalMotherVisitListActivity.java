package com.unicef.thaimai.motherapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.adapter.MothervistListAdapter;
import com.unicef.thaimai.motherapp.adapter.MyAdapter;
import com.unicef.thaimai.motherapp.model.NotificationModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostnatalMotherVisitListActivity extends AppCompatActivity {
MothervistListAdapter mothervistListAdapter;
ArrayList<String> mothersVisitList;
    private List<NotificationModel> visitList;

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postnatal_mother_visit_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.rcv_pnmlist);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        visitList=new ArrayList<>();
        // specify an adapter (see also next example)
        mothervistListAdapter = new MothervistListAdapter(this,Arrays.asList(getResources().getStringArray(R.array.array_visit)));
        mRecyclerView.setAdapter(mothervistListAdapter);
//        prepareMovieData();
    }
}
