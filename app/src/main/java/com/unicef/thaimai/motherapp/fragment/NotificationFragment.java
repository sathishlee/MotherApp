package com.unicef.thaimai.motherapp.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.GetUserInfoPresenter;
import com.unicef.thaimai.motherapp.Presenter.NotificationPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.activity.MainActivity;
import com.unicef.thaimai.motherapp.adapter.MyAdapter;
import com.unicef.thaimai.motherapp.model.NotificationListResponseModel;
import com.unicef.thaimai.motherapp.model.NotificationModel;
import com.unicef.thaimai.motherapp.view.NotificationViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class NotificationFragment extends Fragment implements NotificationViews {

    MyAdapter mAdapter;
    ArrayList<NotificationListResponseModel.NotificationList> moviesList;
    NotificationListResponseModel.NotificationList movie;
    LinearLayoutManager mLayoutManager;
    RecyclerView mRecyclerView;

    SwipeRefreshLayout mSwipeRefreshLayout;

    PreferenceData preferenceData;
    ProgressDialog pDialog;

    NotificationPresenter notificationPresenter;

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
        View  view = inflater.inflate(R.layout.fragment_notification, container, false);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        preferenceData = new PreferenceData(getActivity());
        notificationPresenter = new NotificationPresenter(getActivity(), this);
        notificationPresenter.getNotificationList(preferenceData.getPicmeId(),preferenceData.getMId());
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        moviesList = new ArrayList<>();
        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(moviesList);
        mRecyclerView.setAdapter(mAdapter);
//        prepareMovieData();

        return view;


    }

    private void prepareMovieData() {

       /* movie = new NotificationModel("Notification Title 1", "Message Deatils", "02/05/2018");
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
*/


//        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void showProgress() {
        pDialog.show();
    }

    @Override
    public void hideProgress() {
        pDialog.dismiss();
    }

    @Override
    public void NotificationResponseSuccess(String response) {
        Log.d(NotificationFragment.class.getSimpleName(), "Notification List Success response" + response);

        Log.d(MainActivity.class.getSimpleName(), "Notification count response success" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            String msg = jsonObject.getString("message");
            movie = new NotificationListResponseModel.NotificationList();
            if (status.equalsIgnoreCase("1")) {
                JSONArray jsonArray = jsonObject.getJSONArray("notificationList");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    movie.setSubject(jsonObject1.getString("subject"));
                    movie.setMessage(jsonObject1.getString("message"));
                    movie.setNoteStartDateTime(jsonObject1.getString("noteStartDateTime"));
                    moviesList.add(movie);

                }
                mAdapter.notifyDataSetChanged();
            } else {
                Log.d(MainActivity.class.getSimpleName(), "Notification messsage-->" + msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        prepareMovieData(response);
    }

    @Override
    public void NotificationResponseError(String response) {
        Log.d(NotificationFragment.class.getSimpleName(), "Notification List Error response" + response);
    }
}
