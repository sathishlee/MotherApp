package com.unicef.thaimai.motherapp.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.DeliveryEntryPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.model.requestmodel.DeliveryEntryRequestModel;
import com.unicef.thaimai.motherapp.utility.CheckNetwork;
import com.unicef.thaimai.motherapp.view.DeliveryEntryViews;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class DeliveryEditActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, DeliveryEntryViews {

    EditText edt_delivery_date, edt_time_of_delivery, edt_infant_id, edt_infant_weight, edt_infant_height, edt_new_born_birth_date,
            edt_bcg_given_date, edt_opv_given_date, edt_hepb_given_date;

    Spinner sp_place, sp_delivery_details, sp_mother, sp_newborn, sp_birth_details, sp_breast_feeding_given, sp_admitted_in_sncu,
            sp_outcome;

    String strDeliveryDate, strDeliveryTime, strPlace, strDeliveryDetails, strMotherOutcome, strNewbornOutcome,
            strInfantID, strBirthdetails, strInfantWeight, strInfantHeight, strBreastFeeding, strAdmittedSNCU,
            strSNCUNewBornDate, strSNCUOutcome, strBCGDate, strOPVDate, strHEPDate;

    public static String strDid, strPicmeId;

    Button btn_delivery_submit;

    private SimpleDateFormat dateFormatter;

    TextInputLayout til_delivery_date, til_delivery_time, til_infant_id, til_infant_weight, til_infant_height, til_new_born_date,
            til_bcg_date, til_opv_date, til_hepb_date;

    DeliveryEntryPresenter deliveryEntryPresenter;
    DeliveryEntryRequestModel deliveryEntryRequestModel;
    ProgressDialog progressDialog;
    PreferenceData preferenceData;
    CheckNetwork checkNetwork;
    Calendar mCurrentDate;
    int day, month, year, hour, minute, sec;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details_entry);
        showActionBar();
        initUI();
        onClickListner();
        OnItemSelectedListener();
    }

    private void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Delivery Details");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initUI() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        preferenceData = new PreferenceData(this);
        checkNetwork = new CheckNetwork(this);
        strDid = preferenceData.getDid();
        deliveryEntryPresenter = new DeliveryEntryPresenter(DeliveryEditActivity.this, this);
        if (checkNetwork.isNetworkAvailable()) {
            deliveryEntryPresenter.deliveryDetails(preferenceData.getPicmeId(), preferenceData.getMId(), preferenceData.getDid());
        }else{
            Toast.makeText(getApplicationContext(), "Check Internet Connection..." + checkNetwork.isNetworkAvailable(), Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);
        hour = mCurrentDate.get(Calendar.HOUR);
        minute = mCurrentDate.get(Calendar.MINUTE);
        sec = mCurrentDate.get(Calendar.SECOND);
        month = month + 1;
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
    private void onClickListner() {
        btn_delivery_submit.setOnClickListener(this);
        edt_delivery_date.setOnClickListener(this);
        edt_time_of_delivery.setOnClickListener(this);
        edt_new_born_birth_date.setOnClickListener(this);
        edt_bcg_given_date.setOnClickListener(this);
        edt_opv_given_date.setOnClickListener(this);
        edt_hepb_given_date.setOnClickListener(this);
    }

    private void selectDate(final EditText selectDate) {

        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(DeliveryEditActivity.this, R.style.DatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                selectDate.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        InputMethodManager imm =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(selectDate.getWindowToken(), 0);

        datePickerDialog.show();

    }

    private void selectTime(final EditText selectTime) {
        TimePickerDialog mTimePicker = new TimePickerDialog(DeliveryEditActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String time = sdf.format(Calendar.getInstance().getTime());
                Log.d("time-->",time);
                selectTime.setText(time);
            }
        }, hour, minute, true);
        InputMethodManager imm =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(selectTime.getWindowToken(), 0);
        mTimePicker.show();
    }

    private void OnItemSelectedListener() {
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
        finish();
        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delivery_submit:
                datatosever();
                break;
            case R.id.edt_delivery_date:
                selectDate(edt_delivery_date);
                break;
            case R.id.edt_time_of_delivery:
                selectTime(edt_time_of_delivery);
                break;
            case R.id.edt_new_born_birth_date:
                selectDate(edt_new_born_birth_date);
                break;
            case R.id.edt_bcg_given_date:
                selectDate(edt_bcg_given_date);
                break;
            case R.id.edt_opv_given_date:
                selectDate(edt_opv_given_date);
                break;
            case R.id.edt_hepb_given_date:
                selectDate(edt_hepb_given_date);
                break;
        }
    }

    private void datatosever() {
        editTextValues();

        if (TextUtils.isEmpty(strDid)) {
            Toast.makeText(getApplicationContext(),"DID is empty",Toast.LENGTH_SHORT).show();
        } else {

        }if (TextUtils.isEmpty(strDeliveryDate)) {
            til_delivery_date.setError("Delivery Date is Empty");
        } else {
            til_delivery_date.setError(null);
        }
        if (TextUtils.isEmpty(strDeliveryTime)) {
            til_delivery_time.setError("Delivery Time is Empty");
        } else {
            til_delivery_time.setError(null);
        }
        if (TextUtils.isEmpty(strInfantID)) {
            til_infant_id.setError("Infant Id is Empty");
        } else {
            til_infant_id.setError(null);
        }
        if (TextUtils.isEmpty(strInfantWeight)) {
            til_infant_weight.setError("Infant Weight is Empty");
        } else {
            til_infant_weight.setError(null);
        }
        if (TextUtils.isEmpty(strInfantHeight)) {
            til_infant_height.setError("Infant Height is Empty");
        } else {
            til_infant_height.setError(null);
        }
        if (TextUtils.isEmpty(strSNCUNewBornDate)) {
            til_new_born_date.setError("New Born Date is Empty");
        } else {
            til_new_born_date.setError(null);
        }
        if (TextUtils.isEmpty(strBCGDate)) {
            til_bcg_date.setError("BCG Date is Empty");
        } else {
            til_bcg_date.setError(null);
        }
        if (TextUtils.isEmpty(strOPVDate)) {
            til_opv_date.setError("OPV Date is Empty");
        } else {
            til_opv_date.setError(null);
        }
        if (TextUtils.isEmpty(strHEPDate)) {
            til_hepb_date.setError("HEP B Date is Empty");
        } else {
            til_hepb_date.setError(null);
        }

        if (strPlace.equalsIgnoreCase("--Select--")) {
            showAlert("Delivery Place is Empty");
        } else if (strDeliveryDetails.equalsIgnoreCase("--Select--")) {
            showAlert("Delivery Details is Empty");
        } else if (strMotherOutcome.equalsIgnoreCase("--Select--")) {
            showAlert("Mother Outcome is Empty");
        } else if (strNewbornOutcome.equalsIgnoreCase("--Select--")) {
            showAlert("New Born Outcome is Empty");
        } else if (strBirthdetails.equalsIgnoreCase("--Select--")) {
            showAlert("Birth Details is Empty");
        } else if (strBreastFeeding.equalsIgnoreCase("--Select--")) {
            showAlert("Breast Feeding is Empty");
        } else if (strAdmittedSNCU.equalsIgnoreCase("--Select--")) {
            showAlert("Admitted in SNCU is Empty");
        } else if (strSNCUOutcome.equalsIgnoreCase("--Select--")) {
            showAlert("SNCU Outcome is Empty");
        } else {
            deliveryEntryRequestModel = new DeliveryEntryRequestModel();
//            deliveryEntryRequestModel.setDpicmeId(preferenceData.getPicmeId());                         //preferenceData.getPicmeId()
            deliveryEntryRequestModel.setMid(preferenceData.getMId());
//            deliveryEntryRequestModel.setDid("2");
            deliveryEntryRequestModel.setDpicmeId(preferenceData.getPicmeId());
//            deliveryEntryRequestModel.setMid(preferenceData.getMId());
//            deliveryEntryRequestModel.setDid(strDid);

//            deliveryEntryRequestModel.setDid(preferenceData.getDid());
//            deliveryEntryRequestModel.setMid(preferenceData.getMId());
//            deliveryEntryRequestModel.setDid(preferenceData.getDid());
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

    private void editTextValues() {
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
        switch (parent.getId()) {
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
            case R.id.sp_birth_details:
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
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
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

        Log.e(DeliveryEditActivity.class.getSimpleName(), "Response Success--->" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            String msg = jsonObject.getString("message");
            if (status.equalsIgnoreCase("1")) {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deliveryentryFailiure(String response) {
        Log.d(DeliveryEditActivity.class.getSimpleName(), "Response Failiure-->" + response);
    }

    @Override
    public void getdeliveryNumberSuccess(String response) {
        Log.d(DeliveryEditActivity.class.getSimpleName(), "Response Success--->" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            String msg = jsonObject.getString("message");
            if (status.equalsIgnoreCase("1")) {
                strDid = jsonObject.getString("did");
                Log.w(DeliveryDetailsActivityEntry.class.getSimpleName(), "getdeliveryNumberSuccess" + strDid);
                preferenceData.storeDid(strDid);
//                strPicmeId = jsonObject.getString("dpicmeId");
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                if(msg.equalsIgnoreCase("Your account is Deactivated")){
                    preferenceData.setLogin(false);
                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getdeliveryNumberFailiure(String response) {
        Log.d(DeliveryDetailsActivityEntry.class.getSimpleName(), "Response getdeliveryNumberFailiure-->" + response);

    }

    @Override
    public void deliveryDetailsSuccess(String response) {
        Log.d(DeliveryEditActivity.class.getSimpleName(), "Success Response" + response);
        deliveryValues(response);
    }

    @Override
    public void deliveryDetailsFailure(String response) {
        Log.d(DeliveryEditActivity.class.getSimpleName(), "failure" + response);
    }
    public void deliveryValues(String response) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            String message = jsonObject.getString("message");

            if (status.equalsIgnoreCase("1")) {
                JSONObject  jsonObject_res = jsonObject.getJSONObject("Delevery_Info");
                strDid = jsonObject_res.getString("did");
                strPicmeId = jsonObject_res.getString("dpicmeId");
                strDeliveryDate = jsonObject_res.getString("ddatetime");
                strDeliveryTime = jsonObject_res.getString("dtime");
                strDeliveryDetails = jsonObject_res.getString("ddeleveryDetails");
                strNewbornOutcome = jsonObject_res.getString("dnewBorn");
                strInfantHeight = jsonObject_res.getString("dBirthHeight");
                strInfantWeight = jsonObject_res.getString("dBirthWeight");
                strInfantID = jsonObject_res.getString("dInfantId");
                strBirthdetails = jsonObject_res.getString("dBirthDetails");
                strBreastFeeding = jsonObject_res.getString("dBreastFeedingGiven");
                strAdmittedSNCU = jsonObject_res.getString("dAdmittedSNCU");
                strSNCUNewBornDate = jsonObject_res.getString("dSNCUDate");
                strSNCUOutcome = jsonObject_res.getString("dSNCUOutcome");
                strBCGDate = jsonObject_res.getString("dBCGDate");
                strOPVDate = jsonObject_res.getString("dOPVDate");
                strHEPDate = jsonObject_res.getString("dHEPBDate");
            } else {
                Log.d("message---->", message);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
