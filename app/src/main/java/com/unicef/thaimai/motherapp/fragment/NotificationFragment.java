package com.unicef.thaimai.motherapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.unicef.thaimai.motherapp.R;
import  com.unicef.thaimai.motherapp.adapter.MyAdapter;
import  com.unicef.thaimai.motherapp.model.NotificationModel;


public class NotificationFragment extends Fragment {

    MyAdapter mAdapter;
    ArrayList<NotificationModel> moviesList;
    NotificationModel movie;
    LinearLayoutManager mLayoutManager;
    RecyclerView mRecyclerView;

    SwipeRefreshLayout mSwipeRefreshLayout;
    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=null;

        view = inflater.inflate(R.layout.fragment_notification, container, false);
        mRecyclerView = view. findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
          mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
         moviesList=new ArrayList<>();
        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(moviesList);
        mRecyclerView.setAdapter(mAdapter);
        prepareMovieData();

        return view;


    }

    private void prepareMovieData() {

        movie = new NotificationModel("Notification Title 1", "Message Deatils", "02/05/2018");
        moviesList.add(movie);

        movie = new NotificationModel("Notification Title 2", "Message Deatils", "02/05/2018");
        moviesList.add(movie);

        movie = new NotificationModel("Notification Title 3", "Message Deatils", "02/05/2018");
        moviesList.add(movie);

        movie = new NotificationModel("Notification Title 4", "Message Deatils", "02/05/2018");

        movie = new NotificationModel("Notification Title 5", "Message Deatils", "02/05/2018");
        moviesList.add(movie);

        movie = new NotificationModel("Notification Title 6", "Message Deatils", "02/05/2018");
        moviesList.add(movie);

        movie = new NotificationModel("Notification Title 7", "Message Deatils", "02/05/2018");
        moviesList.add(movie);

        movie = new NotificationModel("Notification Title 8", "Message Deatils", "02/05/2018");
        moviesList.add(movie);



        mAdapter.notifyDataSetChanged();
    }


}
