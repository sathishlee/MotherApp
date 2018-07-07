package com.unicef.thaimai.motherapp.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.NotificationPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.activity.MainActivity;
import com.unicef.thaimai.motherapp.adapter.NotificationAdapter;
import com.unicef.thaimai.motherapp.model.NotificationListResponseModel;
import com.unicef.thaimai.motherapp.view.NotificationViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class NotificationFragment extends Fragment implements NotificationViews {

    NotificationAdapter mAdapter;
    ArrayList<NotificationListResponseModel.NotificationList> moviesList;
    NotificationListResponseModel.NotificationList movie;
    LinearLayoutManager mLayoutManager;
    RecyclerView mRecyclerView;

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
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        preferenceData = new PreferenceData(getActivity());
        notificationPresenter = new NotificationPresenter(getActivity(), this);
        notificationPresenter.getNotificationList(preferenceData.getMId(), preferenceData.getPicmeId());
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        moviesList = new ArrayList<>();
        mAdapter = new NotificationAdapter(moviesList);
        mRecyclerView.setAdapter(mAdapter);
        return view;

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
                    movie.setMPicmeId(jsonObject1.getString("mName"));
                    movie.setMessage(jsonObject1.getString("subject"));
                    movie.setDateTime(jsonObject1.getString("dateTime"));
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

    @Override
    public void NotificationCountError(String response) {
    }

    @Override
    public void NotificationCountSuccess(String response) {

    }
}