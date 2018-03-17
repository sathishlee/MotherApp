package com.unicef.thaimai.motherapp.activity;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.ReferalPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.model.responsemodel.NearestReferalHospitalModel;
import com.unicef.thaimai.motherapp.view.ReferalViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AddReferral extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, ReferalViews {
    LinearLayout llAddNewReferal, llUpdateRefral;
    EditText edtDateOfReferral, edtTimeOfReferral, edtDiagnosis;
    Spinner sp_referred_by, sp_referring_facility_start, sp_facility_referred_to_start, sp_reason_for_referral_start;
    Button btnReferalSubmit;
    String strDateOfReferral, strTimeOfReferral, strDiagnosis, strReferredBy, strReferringFacility, strFacilityReferredTo,strReferringFacilityCode, strFacilityReferredToCode, strReasonForReferral;
    ProgressDialog pDialog;
    PreferenceData preferenceData;;
    ReferalPresenter referalPresenter;
    NearestReferalHospitalModel.NearestHospitals nearestReferalHospitalModel;
    ArrayList<NearestReferalHospitalModel.NearestHospitals> nearestReferalHospitalList;
    ArrayList<String> f_name;
    ArrayList<String> f_phcId;

    ArrayAdapter aa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_refrrel);
        showActionBar();
        initUI();
        onClickListner();
        OnItemSelectedListener();


    }

    private void OnItemSelectedListener() {
        sp_referred_by.setOnItemSelectedListener(this);
        sp_referring_facility_start.setOnItemSelectedListener(this);
        sp_facility_referred_to_start.setOnItemSelectedListener(this);
        sp_reason_for_referral_start.setOnItemSelectedListener(this);
    }

    private void onClickListner() {
        edtDateOfReferral.setOnClickListener(this);
        edtTimeOfReferral.setOnClickListener(this);
        btnReferalSubmit.setOnClickListener(this);
    }

    private void initUI() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        preferenceData = new PreferenceData(this);

        aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,f_name);

        referalPresenter = new ReferalPresenter(AddReferral.this, this);
        referalPresenter.getReffralNearestHospital(AppConstants.EXTRA_LATITUDE,AppConstants.EXTRA_LONGITUDE);
        nearestReferalHospitalList =new ArrayList<>();
        f_name =new ArrayList<>();
        f_phcId =new ArrayList<>();
        llAddNewReferal = (LinearLayout) findViewById(R.id.ll_add_new_referal);
        llUpdateRefral = (LinearLayout) findViewById(R.id.ll_update_refral);
        edtDateOfReferral = (EditText) findViewById(R.id.edt_date_of_referral_start);
        edtTimeOfReferral = (EditText) findViewById(R.id.edt_time_of_referral_start);
        sp_referred_by = (Spinner) findViewById(R.id.sp_referred_by);
        sp_referring_facility_start = (Spinner) findViewById(R.id.sp_referring_facility_start);
        sp_facility_referred_to_start = (Spinner) findViewById(R.id.sp_facility_referred_to_start);
        sp_reason_for_referral_start = (Spinner) findViewById(R.id.sp_reason_for_referral_start);
        edtDiagnosis = (EditText) findViewById(R.id.edt_diagnosis);
        btnReferalSubmit = (Button) findViewById(R.id.btn_referal_submit);
    }

    private void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Update Referral");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(AddReferral.this, ReferralList.class);
        finish();
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_referal_submit:
                sendtoServer();
                break;
            case R.id.edt_date_of_referral_start:
                break;
            case R.id.edt_time_of_referral_start:
                break;
        }
    }

    private void sendtoServer() {
        getallEditTextvalues();
        if (strDateOfReferral.equalsIgnoreCase("")) {
            showAlert("Date is Empty");
        } else if (strTimeOfReferral.equalsIgnoreCase("")) {
            showAlert("Time of is Empty");
        } else if (strDiagnosis.equalsIgnoreCase("")) {
            showAlert("Diagnosis is Empty");
        } else if (strReferredBy.equalsIgnoreCase("")) {
            showAlert("ReferredBy is Empty");
        } else if (strReferringFacility.equalsIgnoreCase("")) {
            showAlert("ReferringFacility is Empty");
        } else if (strFacilityReferredTo.equalsIgnoreCase("")) {
            showAlert("FacilityReferredTo is Empty");
        } else if (strReasonForReferral.equalsIgnoreCase("")) {
            showAlert("ReasonForReferral is Empty");
        } else {

            referalPresenter.addNewReferal(preferenceData.getPicmeId(),
                    preferenceData.getMId(),
                    preferenceData.getVhnId(),
                    preferenceData.getPhcId(), strDateOfReferral, strTimeOfReferral, strReferringFacility, strReferringFacility, strDiagnosis,
                    strReasonForReferral, strReferredBy,strReferringFacilityCode,strFacilityReferredToCode);
        }
    }

    private void showAlert(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

    }


    private void getallEditTextvalues() {
        strDateOfReferral = edtDateOfReferral.getText().toString();
        strTimeOfReferral = edtTimeOfReferral.getText().toString();
        strDiagnosis = edtDiagnosis.getText().toString();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_referred_by:
                strReferredBy = parent.getSelectedItem().toString();
                break;

            case R.id.sp_referring_facility_start:
                strReferringFacility = parent.getSelectedItem().toString();
                strReferringFacilityCode=f_phcId.get(position).toString();
                Log.d("RefergFaciyCode",position+"-->"+strReferringFacilityCode);
                break;
            case R.id.sp_facility_referred_to_start:
                strFacilityReferredTo = parent.getSelectedItem().toString();
                strFacilityReferredToCode=f_phcId.get(position).toString();
                Log.d("RefergFaciyToCode",position+"-->"+strFacilityReferredToCode);


                break;

            case R.id.sp_reason_for_referral_start:
                strReasonForReferral = parent.getSelectedItem().toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
    public void successReferalAdd(String response) {
        Log.d("AddReferal success", response);


        try {
            JSONObject jsonObject =new JSONObject(response);
            String status =jsonObject.getString("status");
            String msg = jsonObject.getString("message");
            Log.d("AddReferal success", msg);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void errorReferalAdd(String response) {
        Log.d("AddReferal error", response);

    }

    @Override
    public void successReferalNearestHospital(String response) {
        Log.d("AddReferal"," success NearestHospital"+ response);
        try {
            JSONObject jsonObject =new JSONObject(response);
            String status =jsonObject.getString("status");
            if (status.equalsIgnoreCase("1")){
                JSONArray jsonArray = new JSONArray("nearestHospitals");
                Log.d("NearestHospital size",jsonArray.length()+"");
                for (int i=0;i<jsonArray.length();i++) {
                    JSONObject jsonObject1 =jsonArray.getJSONObject(i);
                    nearestReferalHospitalModel = new NearestReferalHospitalModel.NearestHospitals();
                    nearestReferalHospitalModel.setDistance(jsonObject1.getString("phcId"));
                    nearestReferalHospitalModel.setPhcCode(jsonObject1.getString("phcCode"));
                    nearestReferalHospitalModel.setPhcId(jsonObject1.getString("distance"));
                    nearestReferalHospitalList.add(nearestReferalHospitalModel);
                    f_name.add(jsonObject1.getString("phcCode"));
                    f_phcId.add(jsonObject1.getString("phcId"));
                }

                sp_referring_facility_start.setAdapter((SpinnerAdapter) nearestReferalHospitalList);
                sp_facility_referred_to_start.setAdapter((SpinnerAdapter) nearestReferalHospitalList);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void errorReferalNearestHospital(String response) {

        Log.d("AddReferal"," error NearestHospital"+ response);


    }
}
