package com.unicef.thaimai.motherapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.DeliveryEntryPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.model.requestmodel.DeliveryEntryRequestModel;
import com.unicef.thaimai.motherapp.view.DeliveryEntryViews;

import org.json.JSONException;
import org.json.JSONObject;

public class DeliveryDetailsActivityEntry extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, DeliveryEntryViews  {

    EditText edt_delivery_date,edt_time_of_delivery, edt_infant_id, edt_infant_weight, edt_infant_height, edt_new_born_birth_date,
            edt_bcg_given_date,edt_opv_given_date, edt_hepb_given_date;

    Spinner sp_place, sp_delivery_details, sp_mother, sp_newborn, sp_birth_details, sp_breast_feeding_given, sp_admitted_in_sncu,
            sp_outcome;

    String strDeliveryDate, strDeliveryTime, strPlace, strDeliveryDetails, strMotherOutcome, strNewbornOutcome,
    strInfantID, strBirthdetails, strInfantWeight, strInfantHeight, strBreastFeeding, strAdmittedSNCU,
    strSNCUNewBornDate, strSNCUOutcome, strBCGDate, strOPVDate, strHEPDate;

    public static String strDid;

    Button btn_delivery_submit;

    TextInputLayout til_delivery_date, til_delivery_time, til_infant_id, til_infant_weight, til_infant_height, til_new_born_date,
            til_bcg_date, til_opv_date, til_hepb_date;

    ProgressDialog progressDialog;
    DeliveryEntryPresenter deliveryEntryPresenter;
    DeliveryEntryRequestModel deliveryEntryRequestModel;

    PreferenceData preferenceData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details_entry);
        showActionBar();
        initUI();
        onClickListner();
        OnItemSelectedListener();

    }

    public void showActionBar(){
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Enter Delivery Details");

        actionBar.setHomeButtonEnabled(true);

        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    public void initUI(){


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        preferenceData = new PreferenceData(this);
        deliveryEntryPresenter = new DeliveryEntryPresenter(DeliveryDetailsActivityEntry.this, this);

        edt_delivery_date = (EditText) findViewById(R.id.edt_delivery_date);
        edt_time_of_delivery = (EditText) findViewById(R.id.edt_time_of_delivery);
        edt_infant_id = (EditText) findViewById(R.id.edt_infant_id);
        edt_infant_weight = (EditText) findViewById(R.id.edt_infant_weight);
        edt_infant_height = (EditText) findViewById(R.id.edt_infant_height);
        edt_new_born_birth_date = (EditText) findViewById(R.id.edt_new_born_birth_date);
        edt_bcg_given_date = (EditText) findViewById(R.id.edt_bcg_given_date);
        edt_opv_given_date = (EditText) findViewById(R.id.edt_opv_given_date);
        edt_hepb_given_date = (EditText) findViewById(R.id.edt_hepb_given_date);

        btn_delivery_submit = (Button) findViewById(R.id.btn_delivery_submit);

        sp_place = (Spinner) findViewById(R.id.sp_place);
        sp_delivery_details = (Spinner) findViewById(R.id.sp_delivery_details);
        sp_mother = (Spinner) findViewById(R.id.sp_mother);
        sp_newborn = (Spinner) findViewById(R.id.sp_newborn);
        sp_birth_details = (Spinner) findViewById(R.id.sp_birth_details);
        sp_breast_feeding_given = (Spinner) findViewById(R.id.sp_breast_feeding_given);
        sp_admitted_in_sncu = (Spinner) findViewById(R.id.sp_admitted_in_sncu);
        sp_outcome = (Spinner) findViewById(R.id.sp_outcome);

        til_delivery_date = (TextInputLayout) findViewById(R.id.til_delivery_date);
        til_delivery_time = (TextInputLayout) findViewById(R.id.til_delivery_time);
        til_infant_id = (TextInputLayout) findViewById(R.id.til_infant_id);
        til_infant_weight = (TextInputLayout) findViewById(R.id.til_infant_weight);
        til_infant_height = (TextInputLayout) findViewById(R.id.til_infant_height);
        til_new_born_date = (TextInputLayout) findViewById(R.id.til_new_born_date);
        til_bcg_date = (TextInputLayout) findViewById(R.id.til_bcg_date);
        til_opv_date = (TextInputLayout) findViewById(R.id.til_opv_date);
        til_hepb_date = (TextInputLayout) findViewById(R.id.til_hepb_date);

    }

    public void onClickListner(){
        btn_delivery_submit.setOnClickListener(this);

    }
    public void OnItemSelectedListener(){

        sp_place.setOnItemSelectedListener(this);
        sp_delivery_details.setOnItemSelectedListener(this);
        sp_mother.setOnItemSelectedListener(this);
        sp_newborn.setOnItemSelectedListener(this);
        sp_birth_details.setOnItemSelectedListener(this);
        sp_admitted_in_sncu.setOnItemSelectedListener(this);
        sp_breast_feeding_given.setOnItemSelectedListener(this);
        sp_outcome.setOnItemSelectedListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(DeliveryDetailsActivityEntry.this, MainActivity.class);
        finish();
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delivery_submit:
                datatosever();
                break;
        }

    }
    public void datatosever(){
        editTextValues();

        if(TextUtils.isEmpty(strDeliveryDate)){
            til_delivery_date.setError("Delivery Date is Empty");
        }
        else{
            til_delivery_date.setError(null);
        }
        if (TextUtils.isEmpty(strDeliveryTime)){
            til_delivery_time.setError("Delivery Time is Empty");
        }
        else{
            til_delivery_time.setError(null);
        }
        if(TextUtils.isEmpty(strInfantID)){
            til_infant_id.setError("Infant Id is Empty");
        }
        else{
            til_infant_id.setError(null);
        }
        if (TextUtils.isEmpty(strInfantWeight)){
            til_infant_weight.setError("Infant Weight is Empty");
        }
        else{
            til_infant_weight.setError(null);
        }
        if(TextUtils.isEmpty(strInfantHeight)){
            til_infant_height.setError("Infant Height is Empty");
        }
        else{
            til_infant_height.setError(null);
        }
        if(TextUtils.isEmpty(strSNCUNewBornDate)){
            til_new_born_date.setError("New Born Date is Empty");
        }
        else{
            til_new_born_date.setError(null);
        }
        if(TextUtils.isEmpty(strBCGDate)){
            til_bcg_date.setError("BCG Date is Empty");
        }
        else{
            til_bcg_date.setError(null);
        }
        if (TextUtils.isEmpty(strOPVDate)){
            til_opv_date.setError("OPV Date is Empty");
        }
        else{
            til_opv_date.setError(null);
        }
        if(TextUtils.isEmpty(strHEPDate)){
            til_hepb_date.setError("HEP B Date is Empty");
        }
        else{
            til_hepb_date.setError(null);
        }

        if (strPlace.equalsIgnoreCase("")){
            showAlert("Delivery Place is Empty");
        }
        else if (strDeliveryDetails.equalsIgnoreCase("")){
            showAlert("Delivery Details is Empty");
        }
        else if(strMotherOutcome.equalsIgnoreCase("")){
            showAlert("Mother Outcome is Empty");
        }
        else if(strNewbornOutcome.equalsIgnoreCase("")){
            showAlert("New Born Outcome is Empty");
        }
        else if(strBirthdetails.equalsIgnoreCase("")){
            showAlert("Birth Details is Empty");
        }
        else if(strBreastFeeding.equalsIgnoreCase("")){
            showAlert("Breast Feeding is Empty");
        }
        else if(strAdmittedSNCU.equalsIgnoreCase("")){
            showAlert("Admitted in SNCU is Empty");
        }
        else if(strSNCUOutcome.equalsIgnoreCase("")){
            showAlert("SNCU Outcome is Empty");
        }
            else {
            deliveryEntryRequestModel = new DeliveryEntryRequestModel();
            deliveryEntryRequestModel.setDpicmeId(preferenceData.getPicmeId());
            deliveryEntryRequestModel.setMid(preferenceData.getMid());
            deliveryEntryRequestModel.setDdatetime(strDeliveryDate);
            deliveryEntryRequestModel.setDtime(strDeliveryTime);
            deliveryEntryRequestModel.setDplace(strPlace);
            deliveryEntryRequestModel.setDdeleveryDetails(strDeliveryDetails);
            deliveryEntryRequestModel.setDdeleveryOutcomeMother(strMotherOutcome);
            deliveryEntryRequestModel.setDnewBorn(strNewbornOutcome);
            deliveryEntryRequestModel.setDInfantId(strInfantID);
            deliveryEntryRequestModel.setDBirthDetails(strBirthdetails);
            deliveryEntryRequestModel.setDBirthWeight(strInfantWeight);
            deliveryEntryRequestModel.setDBirthHeight(strInfantHeight);
            deliveryEntryRequestModel.setDBreastFeedingGiven(strBreastFeeding);
            deliveryEntryRequestModel.setDAdmittedSNCU(strAdmittedSNCU);
            deliveryEntryRequestModel.setDSNCUDate(strSNCUNewBornDate);
            deliveryEntryRequestModel.setDSNCUOutcome(strSNCUOutcome);
            deliveryEntryRequestModel.setDBCGDate(strBCGDate);
            deliveryEntryRequestModel.setDOPVDate(strOPVDate);
            deliveryEntryRequestModel.setDHEPBDate(strHEPDate);

            deliveryEntryPresenter.deliveryEntry(deliveryEntryRequestModel);
        }

    }

    public void editTextValues(){

        strDeliveryDate = edt_delivery_date.getText().toString();
        strDeliveryTime = edt_time_of_delivery.getText().toString();
        strInfantID = edt_infant_id.getText().toString();
        strInfantWeight = edt_infant_weight.getText().toString();
        strInfantHeight = edt_infant_height.getText().toString();
        strSNCUNewBornDate = edt_new_born_birth_date.getText().toString();
        strBCGDate = edt_bcg_given_date.getText().toString();
        strOPVDate = edt_opv_given_date.getText().toString();
        strHEPDate = edt_hepb_given_date.getText().toString();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch(parent.getId()){
            case R.id.sp_place:
                strPlace = parent.getSelectedItem().toString();
                break;
            case R.id.sp_delivery_details:
                strDeliveryDetails = parent.getSelectedItem().toString();
                break;
            case R.id.sp_mother:
                strMotherOutcome = parent.getSelectedItem().toString();
                break;
            case R.id.sp_newborn:
                strNewbornOutcome = parent.getSelectedItem().toString();
                break;
            case  R.id.sp_birth_details:
                strBirthdetails = parent.getSelectedItem().toString();
                break;
            case R.id.sp_breast_feeding_given:
                strBreastFeeding = parent.getSelectedItem().toString();
                break;
            case R.id.sp_admitted_in_sncu:
                strAdmittedSNCU = parent.getSelectedItem().toString();
                break;
            case R.id.sp_outcome:
                strSNCUOutcome = parent.getSelectedItem().toString();
                break;
        }

    }

    private void showAlert(String msg) {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void deliveryentrySuccess(String response) {
        Log.e(DeliveryDetailsActivityEntry.class.getSimpleName(), "Response Success--->" + response);
        try {
            JSONObject jsonObject =new JSONObject(response);
            String status =jsonObject.getString("status");
            String msg = jsonObject.getString("message");
            if (status.equalsIgnoreCase("1")){
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deliveryentryFailiure(String response) {
        Log.d(DeliveryDetailsActivityEntry.class.getSimpleName(),"Response Failiure-->" + response);
    }

    @Override
    public void getdeliveryNumberSuccess(String response) {
        Log.d(DeliveryDetailsActivityEntry.class.getSimpleName(), "Response Success--->" + response);
        try{
            JSONObject jsonObject = new JSONObject(response);
            String status =jsonObject.getString("status");
            String msg = jsonObject.getString("message");
            strDid = jsonObject.getString("did");
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void getdeliveryNumberFailiure(String response) {
        Log.d(DeliveryDetailsActivityEntry.class.getSimpleName(),"Response Failiure-->" + response);
    }
}
