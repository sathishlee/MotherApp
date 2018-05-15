package com.unicef.thaimai.motherapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
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
import com.unicef.thaimai.motherapp.model.responsemodel.ImmunizationResponseModel;
import com.unicef.thaimai.motherapp.model.responsemodel.NearHospitalResponseModel;
import com.unicef.thaimai.motherapp.view.ImmunizationEntryView;
import com.unicef.thaimai.motherapp.view.ImmunizationListViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ImmunizationActivity extends AppCompatActivity implements  ImmunizationListViews {

    ProgressDialog pDialog;
    ImmunizationListPresenter immunizationListPresenter;
    PreferenceData preferenceData;
    private List<ImmunizationResponseModel.Result> mResult ;
    ImmunizationResponseModel.Result mresponseResult;
    private RecyclerView recyclerView;
    private ImmunizationListAdapter mAdapter;
    TextView txt_no_records_found;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_immunization);
        showActionBar();
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        preferenceData =new PreferenceData(this);
        immunizationListPresenter = new ImmunizationListPresenter(ImmunizationActivity.this,this);
        immunizationListPresenter.getImmunizationList(preferenceData.getPicmeId(),preferenceData.getMId());
        mResult = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.rec_immunization);
        txt_no_records_found = (TextView) findViewById(R.id.txt_no_records);
        txt_no_records_found.setVisibility(View.GONE);


        mAdapter = new ImmunizationListAdapter(mResult, ImmunizationActivity.this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ImmunizationActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


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
                txt_no_records_found.setVisibility(View.GONE);
                JSONArray jsonArray = mJsnobject.getJSONArray("result");
                for (int i = 0; i < jsonArray.length(); i++) {
                    mresponseResult = new ImmunizationResponseModel.Result();

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    mresponseResult.setImmDoseNumber(jsonObject.getString("immDoseNumber"));
                    mresponseResult.setImmDueDate(jsonObject.getString("immDueDate"));
                    mresponseResult.setImmCarePovidedDate(jsonObject.getString("immCarePovidedDate"));
                    mresponseResult.setImmOpvStatus(jsonObject.getString("immOpvStatus"));
                    mresponseResult.setImmPentanvalentStatus(jsonObject.getString("immPentanvalentStatus"));
                    mresponseResult.setImmRotaStatus(jsonObject.getString("immRotaStatus"));
                    mresponseResult.setImmIpvStatus(jsonObject.getString("immIpvStatus"));

                    mResult.add(mresponseResult);
                    mAdapter.notifyDataSetChanged();
                }
            }else{
                txt_no_records_found.setVisibility(View.VISIBLE);

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void getImmunizationListFailure(String response) {
        Log.e(ImmunizationActivity.class.getSimpleName(),"Response error"+response);
    }

}
