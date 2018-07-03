package com.unicef.thaimai.motherapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.ImmunizationListPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.adapter.ImmunizationListAdapter;
import com.unicef.thaimai.motherapp.adapter.NearByHospitalAdapter;
import com.unicef.thaimai.motherapp.app.RealmController;
import com.unicef.thaimai.motherapp.model.responsemodel.ImmunizationResponseModel;
import com.unicef.thaimai.motherapp.model.responsemodel.NearHospitalResponseModel;
import com.unicef.thaimai.motherapp.realmDbModelClass.ImmunizationRealmModel;
import com.unicef.thaimai.motherapp.utility.CheckNetwork;
import com.unicef.thaimai.motherapp.view.ImmunizationEntryView;
import com.unicef.thaimai.motherapp.view.ImmunizationListViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ImmunizationActivity extends AppCompatActivity implements  ImmunizationListViews {

    ProgressDialog pDialog;
    ImmunizationListPresenter immunizationListPresenter;
    PreferenceData preferenceData;
    private List<ImmunizationResponseModel.Result> mResult ;
    ImmunizationResponseModel.Result mresponseResult;
    private RecyclerView recyclerView;
    private ImmunizationListAdapter mAdapter;
    TextView txt_no_records_found;
    Realm realm;
    CheckNetwork checkNetwork;
    boolean isoffline = false;
    ImmunizationRealmModel immunizationRealmModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkNetwork = new CheckNetwork(this);
        realm = RealmController.with(this).getRealm();
        setContentView(R.layout.layout_immunization);
        showActionBar();
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");

        preferenceData =new PreferenceData(this);
        immunizationListPresenter = new ImmunizationListPresenter(ImmunizationActivity.this,this);
        if(checkNetwork.isNetworkAvailable()){
            immunizationListPresenter.getImmunizationList(preferenceData.getPicmeId(),preferenceData.getMId());
        }else{
            isoffline = true;
        }
        mResult = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.rec_immunization);
        txt_no_records_found = (TextView) findViewById(R.id.txt_no_records);
        txt_no_records_found.setVisibility(View.GONE);


        mAdapter = new ImmunizationListAdapter(mResult, ImmunizationActivity.this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ImmunizationActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        if(isoffline){
            offlineData();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Record Not Found");
            builder.create();
        }


    }



    public void showActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Immunization List");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
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
    public void onDestroy(){
        super.onDestroy();
        if (pDialog!=null && pDialog.isShowing() ){
            pDialog.cancel();
        }
    }

    @Override
    public void getImmunizationListSuccess(String response) {

        Log.e(ImmunizationActivity.class.getSimpleName(), "Response success" + response);

        try {
            JSONObject mJsnobject = new JSONObject(response);
            String status = mJsnobject.getString("status");
            String message = mJsnobject.getString("message");
            Toast.makeText(ImmunizationActivity.this,message, Toast.LENGTH_SHORT).show();
            if (status.equalsIgnoreCase("1")) {
                JSONArray jsonArray = mJsnobject.getJSONArray("result");
                RealmResults<ImmunizationRealmModel> immunizationRealmModels = realm.where(ImmunizationRealmModel.class).findAll();
                Log.e("Realm size ---->", immunizationRealmModels.size() + "");
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(ImmunizationRealmModel.class);
                    }
                });

                if (jsonArray.length() != 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    txt_no_records_found.setVisibility(View.GONE);
                    realm.beginTransaction();
                    ImmunizationRealmModel immunizationRealmModel = null;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        immunizationRealmModel = realm.createObject(ImmunizationRealmModel.class);
                        mresponseResult = new ImmunizationResponseModel.Result();

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        immunizationRealmModel.setImmDoseNumber(jsonObject.getString("immDoseNumber"));
                        immunizationRealmModel.setImmDueDate(jsonObject.getString("immDueDate"));
                        immunizationRealmModel.setImmCarePovidedDate(jsonObject.getString("immCarePovidedDate"));
                        immunizationRealmModel.setImmOpvStatus(jsonObject.getString("immOpvStatus"));
                        immunizationRealmModel.setImmPentanvalentStatus(jsonObject.getString("immPentanvalentStatus"));
                        immunizationRealmModel.setImmRotaStatus(jsonObject.getString("immRotaStatus"));
                        immunizationRealmModel.setImmIpvStatus(jsonObject.getString("immIpvStatus"));

//                        mResult.add(mresponseResult);
//                        mAdapter.notifyDataSetChanged();
                    }
                    realm.commitTransaction();       //create or open
                }
            }else{
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                txt_no_records_found.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

        offlineData();
    }

    private void offlineData() {
        realm.beginTransaction();
        RealmResults<ImmunizationRealmModel> immunizationRealmModels = realm.where(ImmunizationRealmModel.class).findAll();
        Log.e("realm Size ->", immunizationRealmModels.size() + "");
        for (int i = 0; i < immunizationRealmModels.size(); i++) {
            mresponseResult = new ImmunizationResponseModel.Result();
            ImmunizationRealmModel immunizationRealmModel = immunizationRealmModels.get(i);

            mresponseResult.setImmDoseNumber(immunizationRealmModel.getImmDoseNumber());
            mresponseResult.setImmDueDate(immunizationRealmModel.getImmDueDate());
            mresponseResult.setImmCarePovidedDate(immunizationRealmModel.getImmCarePovidedDate());
            mresponseResult.setImmOpvStatus(immunizationRealmModel.getImmOpvStatus());
            mresponseResult.setImmPentanvalentStatus(immunizationRealmModel.getImmPentanvalentStatus());
            mresponseResult.setImmRotaStatus(immunizationRealmModel.getImmRotaStatus());
            mresponseResult.setImmIpvStatus(immunizationRealmModel.getImmIpvStatus());

            mResult.add(mresponseResult);
            mAdapter.notifyDataSetChanged();
        }
        realm.commitTransaction();
    }

    @Override
    public void getImmunizationListFailure(String response) {
        Log.e(ImmunizationActivity.class.getSimpleName(),"Response error"+response);
    }

}
