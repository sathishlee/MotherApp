package com.unicef.thaimai.motherapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.adapter.MyAdapter;
import com.unicef.thaimai.motherapp.adapter.VisitAdapter;
import com.unicef.thaimai.motherapp.model.NotificationModel;
import com.unicef.thaimai.motherapp.model.VisitModel;

import java.util.ArrayList;


public class visits extends Fragment {

    VisitAdapter mAdapter;
    ArrayList<VisitModel> visitlist;
    VisitModel mVisitModel;
    LinearLayoutManager mLayoutManager;
    RecyclerView mRecyclerView;
    public static visits newInstance()
    {
        visits fragment = new visits();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
//        return inflater.inflate(R.layout.fragment_visits, container, false);

        View view=null;

        view =  inflater.inflate(R.layout.fragment_visits, container, false);
        mRecyclerView = view. findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        visitlist=new ArrayList<>();
        // specify an adapter (see also next example)
        mAdapter = new VisitAdapter(visitlist);
        mRecyclerView.setAdapter(mAdapter);
        prepareMovieData();

        return view;
    }

    private void prepareMovieData() {
        mVisitModel = new VisitModel("02/08/2017", "Scheduled Visit", "DW","Fever");
        visitlist.add(mVisitModel);

        mVisitModel = new VisitModel("02/09/2017", "Scheduled Visit", "DW","Giddiness ");
        visitlist.add(mVisitModel);

        mVisitModel = new VisitModel("02/10/2017", "Scheduled Visit", "DW","Vomiting");
        visitlist.add(mVisitModel);

        mVisitModel = new VisitModel("02/11/2017", "Scheduled Visit", "DW","Breathlessness");
        visitlist.add(mVisitModel);

        mVisitModel = new VisitModel("02/12/2017", "Scheduled Visit", "DW","Burning Mictutrion");
        visitlist.add(mVisitModel);

        mVisitModel = new VisitModel("02/01/2018", "Scheduled Visit", "DW","Bleeding PV");
        visitlist.add(mVisitModel);
        mVisitModel = new VisitModel("02/02/2018", "Scheduled Visit", "DW","Leaking PV");
        visitlist.add(mVisitModel);
        mVisitModel = new VisitModel("02/03/2018", "Scheduled Visit", "DW",", Pain abdomen");
        visitlist.add(mVisitModel);

        mAdapter.notifyDataSetChanged();

    }
}