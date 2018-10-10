package com.unicef.thaimai.motherapp.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.HealthTipsPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.adapter.ImageAdapter;
import com.unicef.thaimai.motherapp.adapter.MessageAdapter;
import com.unicef.thaimai.motherapp.model.responsemodel.ImageFragmentModel;
import com.unicef.thaimai.motherapp.model.responsemodel.MessageFragmentModel;
import com.unicef.thaimai.motherapp.realmDbModelClass.ImageFragmentRealmModel;
import com.unicef.thaimai.motherapp.realmDbModelClass.MessageRealmModel;
import com.unicef.thaimai.motherapp.utility.CheckNetwork;
import com.unicef.thaimai.motherapp.view.HealthTipsViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class ImageFragment extends Fragment implements HealthTipsViews {

    RecyclerView message_recycler_view;
    PreferenceData preferenceData;
    ProgressDialog pDialog;
    HealthTipsPresenter healthTipsPresenter;
    ImageFragmentModel.HealthtipsImage healthtips;
    ArrayList<ImageFragmentModel.HealthtipsImage> healthtipses;
    TextView txt_no_records_found;
    ImageAdapter imageAdapter;
    Realm realm;
    CheckNetwork checkNetwork;
    boolean isoffline = false;

    private boolean isFragmentLoaded=false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.image_fragment, container, false);
        initUI(view);
        onClickListner();
        return view;
    }

    private void onClickListner() {

    }

    private void initUI(View view) {
        checkNetwork = new CheckNetwork(getActivity());
        preferenceData = new PreferenceData(getActivity());
        healthTipsPresenter = new HealthTipsPresenter(getActivity(),this);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");


        healthtipses = new ArrayList<>();
        message_recycler_view = (RecyclerView) view.findViewById(R.id.image_recycler_view);
        txt_no_records_found = (TextView) view.findViewById(R.id.txt_no_records_found);
//        txt_no_records_found.setVisibility(View.GONE);

        imageAdapter = new ImageAdapter(getActivity(),healthtipses);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        message_recycler_view.setLayoutManager(mLayoutManager);
        message_recycler_view.setItemAnimator(new DefaultItemAnimator());
        message_recycler_view.setAdapter(imageAdapter);

        if (isoffline) {
            messageImageOffline();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Record Not Found");
            builder.create();
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isFragmentLoaded ) {
            // Load your data here or do network operations here
           loadData();
            isFragmentLoaded = true;

        }
    }

    private void loadData() {
        if(checkNetwork.isNetworkAvailable()){
            healthTipsPresenter.healthTipsImageWeekly(preferenceData.getMId());
        }else {
            isoffline = true;
        }
    }


    @Override
    public void showProgress() {
        pDialog.show();
    }

    @Override
    public void hideProgress() {
        pDialog.hide();
    }

    @Override
    public void getHealthTipsVideoSuccess(String response) {

    }

    @Override
    public void getHealthTipsVideoFailure(String response) {

    }

    @Override
    public void healthTipsMessageSuccess(String response) {

    }

    @Override
    public void healthTipsMessageFailure(String response) {

    }

    @Override
    public void healthTipsImageSuccess(String response) {
        Log.d(ImageFragment.class.getSimpleName(), "Message --->" + response);

        try {
            JSONObject mJsnobject = new JSONObject(response);
            String status = mJsnobject.getString("status");
            String message = mJsnobject.getString("message");
            if (status.equalsIgnoreCase("1")) {
                Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
                txt_no_records_found.setVisibility(View.GONE);
                message_recycler_view.setVisibility(View.VISIBLE);
                JSONArray jsonArray = mJsnobject.getJSONArray("healthtipsImage");
                if (jsonArray.length() != 0) {
                    RealmResults<ImageFragmentRealmModel> imageFragmentRealmModels = realm.where(ImageFragmentRealmModel.class).findAll();
                    Log.e("Realm size ---->", imageFragmentRealmModels.size() + "");
                    if (imageFragmentRealmModels.size()!=0){
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.delete(ImageFragmentRealmModel.class);
                            }
                        });
                    }
                    txt_no_records_found.setVisibility(View.GONE);
                    message_recycler_view.setVisibility(View.VISIBLE);
                    realm.beginTransaction();
                    ImageFragmentRealmModel messageRealmModel = null;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        messageRealmModel = realm.createObject(ImageFragmentRealmModel.class);
                        healthtips = new ImageFragmentModel.HealthtipsImage();

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        messageRealmModel.setSubject(jsonObject.getString("subject"));
                        messageRealmModel.setMessage(jsonObject.getString("message"));
                        messageRealmModel.setMessage(jsonObject.getString("health_pic"));
                    }
                    realm.commitTransaction();       //create or open
                }else{
                    Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                    txt_no_records_found.setVisibility(View.VISIBLE);
                    message_recycler_view.setVisibility(View.GONE);

                }
            }else{
                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                txt_no_records_found.setVisibility(View.VISIBLE);
                message_recycler_view.setVisibility(View.GONE);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        messageImageOffline();
    }

    private void messageImageOffline() {
        realm.beginTransaction();
        RealmResults<ImageFragmentRealmModel> messageRealmModels = realm.where(ImageFragmentRealmModel.class).findAll();
        Log.e("realm Size ->", messageRealmModels.size() + "");
        if(messageRealmModels.size()!=0) {
            txt_no_records_found.setVisibility(View.GONE);
            message_recycler_view.setVisibility(View.VISIBLE);
            for (int i = 0; i < messageRealmModels.size(); i++) {
                healthtips = new ImageFragmentModel.HealthtipsImage();
                ImageFragmentRealmModel messageRealmModel = messageRealmModels.get(i);
                healthtips.setSubject(messageRealmModel.getSubject());
                healthtips.setMessage(messageRealmModel.getMessage());
                healthtips.setHealth_pic(messageRealmModel.getHealth_pic());
                healthtipses.add(healthtips);
                imageAdapter.notifyDataSetChanged();
            }
        }
        else{
//                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
            txt_no_records_found.setVisibility(View.VISIBLE);
            message_recycler_view.setVisibility(View.GONE);

        }
        realm.commitTransaction();
    }

    @Override
    public void healthtipsImageFailure(String response) {
        Log.d(ImageFragment.class.getSimpleName(), "Message --->" + response);
    }
}
