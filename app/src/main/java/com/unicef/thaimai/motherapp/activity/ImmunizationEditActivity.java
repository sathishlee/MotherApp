package com.unicef.thaimai.motherapp.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.ImmunizationEntryPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.model.requestmodel.ImmunizationEntryRequestModel;
import com.unicef.thaimai.motherapp.view.ImmunizationEntryView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ImmunizationEditActivity extends AppCompatActivity implements View.OnClickListener, OnItemSelectedListener, ImmunizationEntryView {

    Spinner sp_dose_number, sp_opv, sp_pentavalent, sp_rota, sp_ipv;

    TextView txt_dose_number_value;

    TextInputLayout til_due_date, til_care_provided_date;

    EditText edt_due_date, edt_care_provided_date;

    String strDoseNumber, strOPV, strPentavalent, strRota, strIPV, strDueDate, strCareDate, strDoseNumberValue;

    Button btn_immunization_submit;

    ProgressDialog progressDialog;
    ImmunizationEntryPresenter immunizationEntryPresenter;
    ImmunizationEntryRequestModel immunizationEntryRequestModel;

    String [] dosenumber = new String[]{
            "--Select--",
            "Dose 1 (Day 45)",
            "Dose 2 (Day 75)",
            "Dose 3 (Day 105)",
            "Dose 4 (Day 270)"
    };
    String[] yn ={"--Select--","Yes","No"};

    String [] dosenumber_i = {"1", "2","3", "4"};

    PreferenceData preferenceData;

    Calendar mCurrentDate;
    int day, month, year, hour, minute, sec, value;

    public static String strImmuID="-1";

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immunization_edit);

        showActionBar();
        initUI();
        onClickListner();
        OnItemSelectedListener();

    }

    public void showActionBar(){

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Immunization Entry");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void initUI(){

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        preferenceData = new PreferenceData(this);
        immunizationEntryPresenter = new ImmunizationEntryPresenter(ImmunizationEditActivity.this, this);
        immunizationEntryPresenter.immunizationID(preferenceData.getPicmeId(),preferenceData.getMId());

        /*if (AppConstants.IMMUNIZATION_EDIT) {
            Log.e(ImmunizationEditActivity.class.getSimpleName(),"IS FROM Main"+AppConstants.IMMUNIZATION_EDIT);
            immunizationEntryPresenter.immunizationID(preferenceData.getPicmeId(), preferenceData.getMId());

        }else{
            strImmuID =AppConstants.ImmuID;
            Log.e(ImmunizationEditActivity.class.getSimpleName(),"IS FROM Main"+AppConstants.IMMUNIZATION_EDIT);

            immunizationEntryPresenter.getImmunizationByVisit(strImmuID,preferenceData.getMId());
        }*/



        sp_dose_number = (Spinner) findViewById(R.id.sp_dose_number);
//        sp_dose_number.setSelection(Integer.parseInt(strImmuID));

        sp_opv = (Spinner) findViewById(R.id.sp_opv);
        sp_pentavalent = (Spinner) findViewById(R.id.sp_pentavalent);
        sp_rota = (Spinner) findViewById(R.id.sp_rota);
        sp_ipv = (Spinner) findViewById(R.id.sp_ipv);

        txt_dose_number_value = (TextView) findViewById(R.id.txt_dose_number_value);

        til_due_date = (TextInputLayout) findViewById(R.id.til_due_date);
        til_care_provided_date = (TextInputLayout) findViewById(R.id.til_care_provided_date);

        btn_immunization_submit = (Button) findViewById(R.id.btn_immunization_submit);

        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);
        hour = mCurrentDate.get(Calendar.HOUR);
        minute = mCurrentDate.get(Calendar.MINUTE);
        sec = mCurrentDate.get(Calendar.SECOND);

        month = month + 1;

        edt_due_date = (EditText) findViewById(R.id.edt_due_date);
        edt_due_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ImmunizationEditActivity.this, R.style.DatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                        edt_due_date.setText(dayOfMonth + "-" + monthOfYear + "-" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }

        });
        edt_care_provided_date = (EditText) findViewById(R.id.edt_care_provided_date);
        edt_care_provided_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ImmunizationEditActivity.this, R.style.DatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                        edt_care_provided_date.setText(dayOfMonth + "-" + monthOfYear + "-" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }

        });


    }


    public void onClickListner(){
        btn_immunization_submit.setOnClickListener(this );
    }
    public void OnItemSelectedListener(){

        sp_dose_number.setOnItemSelectedListener(this);
        sp_opv.setOnItemSelectedListener(this);
        sp_pentavalent.setOnItemSelectedListener(this);
        sp_rota.setOnItemSelectedListener(this);
        sp_ipv.setOnItemSelectedListener(this);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(ImmunizationEditActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_immunization_submit:
                dataSendtosever();
                break;
        }

    }

    public void dataSendtosever(){
        editTextValues();
        JSONObject jObj = null;

        if (TextUtils.isEmpty(strDueDate)) {
            edt_due_date.setError("Due Date is Empty");
        } else {
            edt_due_date.setError(null);
        }
        if (TextUtils.isEmpty(strCareDate)) {
            edt_care_provided_date.setError("Care Provided Date is Empty");
        } else {
            edt_care_provided_date.setError(null);
        }

        if (strDoseNumber.equalsIgnoreCase("--Select--")) {

//            for(int i= 0;i<dosenumber.length;i++){
//                if(dosenumber[i]==strImmuID){
//                    sp_dose_number.setSelection(i);
//                }
//            }

            showAlert("Dose Number is Empty");
        } else if (strOPV.equalsIgnoreCase("--Select--")) {
            showAlert("OPV is Empty");
        }else if (strPentavalent.equalsIgnoreCase("--Select--")) {
            showAlert("Pentavalent is Empty");
        }else if (strRota.equalsIgnoreCase("--Select--")) {
            showAlert("Rota is Empty");
        }else if (strIPV.equalsIgnoreCase("--Select--")) {
            showAlert("IPV is Empty");
        }


        else{
            immunizationEntryRequestModel = new ImmunizationEntryRequestModel();
            immunizationEntryRequestModel.setPicmeId(preferenceData.getPicmeId());
            immunizationEntryRequestModel.setMid(preferenceData.getMId());
            immunizationEntryRequestModel.setImmDoseId(strImmuID);
//            immunizationEntryRequestModel.setImmDoseIDValue(strDoseNumberValue);
            immunizationEntryRequestModel.setImmDueDate(strDueDate);
            immunizationEntryRequestModel.setImmCarePovidedDate(strCareDate);

            immunizationEntryRequestModel.setImmDoseNumber(strDoseNumber);
            immunizationEntryRequestModel.setImmOpvStatus(strOPV);
            immunizationEntryRequestModel.setImmPentanvalentStatus(strPentavalent);
            immunizationEntryRequestModel.setImmRotaStatus(strRota);
            immunizationEntryRequestModel.setImmIpvStatus(strIPV);

            /*if (AppConstants.IMMUNIZATION_EDIT) {
                immunizationEntryPresenter.immunizationEntry(Apiconstants.IMMUNIZATION_ENTRY, immunizationEntryRequestModel);
            }else {
                immunizationEntryPresenter.immunizationEntry(Apiconstants.IMMUNIZATION_BY_VISIT_EDIT, immunizationEntryRequestModel);
            }*/
            immunizationEntryPresenter.immunizationEntry(Apiconstants.IMMUNIZATION_ENTRY, immunizationEntryRequestModel);

        }
    }

    public void editTextValues(){
        strDueDate = edt_due_date.getText().toString();
        strCareDate = edt_care_provided_date.getText().toString();
    }

    private void showAlert(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.sp_dose_number:
                strDoseNumber = parent.getSelectedItem().toString();

                if(strDoseNumber.equalsIgnoreCase("--Select--")) {
                    position = -1;
                    strImmuID = String.valueOf(position);
                    immunizationEntryPresenter.checkImmunizationId(preferenceData.getPicmeId(), preferenceData.getMId(), strImmuID);

                }else{
                    strImmuID = String.valueOf(position);
                    immunizationEntryPresenter.checkImmunizationId(preferenceData.getPicmeId(), preferenceData.getMId(), strImmuID);
                }

                /*strImmuID = String.valueOf(position);
                immunizationEntryPresenter.checkImmunizationId(preferenceData.getPicmeId(), preferenceData.getMId(), strImmuID);
*/




                break;
            case R.id.sp_opv:
                strOPV = parent.getSelectedItem().toString();
                break;
            case R.id.sp_pentavalent:
                strPentavalent = parent.getSelectedItem().toString();
                break;
            case R.id.sp_rota:
                strRota = parent.getSelectedItem().toString();
                break;
            case R.id.sp_ipv:
                strIPV = parent.getSelectedItem().toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void showProgress() {
//        pDialog.show();
    }

    @Override
    public void hideProgress() {
//        pDialog.hide();
    }

    @Override
    public void immunizationEntrySuccess(String response) {
        Log.e(ImmunizationEditActivity.class.getSimpleName(), "Response Success--->" + response);
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
    public void immunizationEntryFailure(String response) {
        Log.d(ImmunizationEditActivity.class.getSimpleName(), "Response Failiure-->" + response);
    }

    @Override
    public void immunizationIDSuccess(String response) {
        Log.d(ImmunizationEditActivity.class.getSimpleName(), "Response Success--->" + response);
        /*try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            String msg = jsonObject.getString("message");
            if (status.equalsIgnoreCase("1")){
                Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
            }
//            strImmuID = jsonObject.getString("immDoseId");
//            preferenceData.storeImmuid(strImmuID);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void immunizationIDFailure(String response) {
        Log.d(ImmunizationEditActivity.class.getSimpleName(), "Response Failiure-->" + response);
    }

    @Override
    public void getImmunizationByVisitSuccess(String response) {
        Log.d(ImmunizationEditActivity.class.getSimpleName(), "ImmunizationByVisit Success-->" + response);
        /*try {
            JSONObject jsonObject  = new JSONObject(response);
            JSONObject jsonObject_immList = jsonObject.getJSONObject("immList");
            String status = jsonObject.getString("status");
            String msg = jsonObject.getString("message");

            String strImmId = jsonObject_immList.getString("immId");
            String strImID = jsonObject_immList.getString("mid");
            String strPicmeId = jsonObject_immList.getString("picmeId");
            txt_dose_number_value.setText(jsonObject_immList.getString("immDoseNumber"));
          String strImmDoseId= jsonObject_immList.getString("immDoseId");
            edt_due_date.setText( jsonObject_immList.getString("immDueDate"));
            edt_care_provided_date.setText( jsonObject_immList.getString("immCarePovidedDate"));

//            spHusbHelpatitis.setSelection(nr,
            sp_dose_number.setSelection(getListPosition(dosenumber,jsonObject_immList.getString("immDoseNumber")));
            sp_opv.setSelection(getListPosition(yn,jsonObject_immList.getString("immOpvStatus")));
            sp_pentavalent.setSelection(getListPosition(yn,jsonObject_immList.getString("immPentanvalentStatus")));
            sp_rota.setSelection(getListPosition(yn,jsonObject_immList.getString("immRotaStatus")));
            sp_ipv.setSelection(getListPosition(yn,jsonObject_immList.getString("immIpvStatus")));


        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void getImmunizationByVisitFailure(String response) {
        Log.d(ImmunizationEditActivity.class.getSimpleName(), "ImmunizationByVisit Failiure-->" + response);

    }

    @Override
    public void checkImmunizationIdSuccess(String response) {

        try{
            JSONObject jsonObject = new JSONObject(response);
            String status =jsonObject.getString("status");
            String msg = jsonObject.getString("message");

            if (status.equalsIgnoreCase("1")){
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void checkImmunizationFailure(String response) {
        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
    }


//    private int getListPosition(String[] doseId, String doseNumber) {
//        int x=0;
//        List<String> listOcc = Arrays.asList(doseId);
//        if (listOcc.contains(doseNumber)) {
//            for (int i=0;i<listOcc.size();i++) {
//                if (doseNumber.equalsIgnoreCase(doseId[i]))
//                    x =i;
//            }
//        } else {
//
//        }
//        return x;
//    }

    private int getListPosition(String[] occ, String mMotherOccupation) {
        int x=0;
        List<String> listOcc = Arrays.asList(occ);
        if (listOcc.contains(mMotherOccupation)) {
            for (int i=0;i<listOcc.size();i++) {
                if (mMotherOccupation.equalsIgnoreCase(occ[i]))
                    x =i;
            }
        } else {

        }
        return x;
    }
}
