package com.unicef.thaimai.motherapp.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.ReferalPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.model.responsemodel.NearestReferalHospitalModel;
import com.unicef.thaimai.motherapp.utility.CheckNetwork;
import com.unicef.thaimai.motherapp.view.ReferalViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AddReferral extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, ReferalViews {
    LinearLayout llAddNewReferal, llUpdateRefral;
    EditText edtDateOfReferral, edtTimeOfReferral, edtUPDateOfReferral, edtUPTimeOfReferral, edtDiagnosis;
    Spinner spUPReceivedBy, spUPReferringFacility, sp_referred_by, sp_referring_facility_start, sp_facility_referred_to_start, sp_reason_for_referral_start;
    RadioGroup rgInLabour, rgAdmitted;
    Button btnReferalSubmit;
    String strDateOfReferral, strTimeOfReferral, strDiagnosis, strReferredBy, strReferringFacility, strFacilityReferredTo, strReasonForReferral;
    String strFacilityReferredToCode = "1001";
    String strReferringFacilityCode = "1002";
    String strUPDateOfReferral, strUPTimeOfReferral, strUPReceivedBy, strUPReferringFacility, strInLabour, strAdmitted;
    ProgressDialog pDialog;
    PreferenceData preferenceData;

    ReferalPresenter referalPresenter;
    NearestReferalHospitalModel.NearestHospitals nearestReferalHospitalModel;
    ArrayList<NearestReferalHospitalModel.NearestHospitals> nearestReferalHospitalList;

    ArrayList<String> arr_nearstReferalHospitalList;
    ArrayList<String> arr_nearstReferalHospitalList_name_distance;
    ArrayList<String> arr_nearstReferalHospitalList_hospital_name;
    ArrayList<String> arr_nearstReferalHospitalList_hospital_id;

    Calendar mCurrentDate;
    int day, month, year, hour, minute, sec;
    CheckNetwork checkNetwork;

    private int mYear,mMonth,mDay;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppConstants.CREATE_NEW_REFRAL = false;
        if (pDialog!=null && pDialog.isShowing() ){
            pDialog.cancel();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_refrrel);
        showActionBar();
        initUI();

        if (AppConstants.CREATE_NEW_REFRAL) {
            llUpdateRefral.setVisibility(View.GONE);
        } else {
            llAddNewReferal.setVisibility(View.GONE);
        }
        onClickListner();
        OnItemSelectedListener();


    }

    private void OnItemSelectedListener() {

        sp_referred_by.setOnItemSelectedListener(this);
        sp_referring_facility_start.setOnItemSelectedListener(this);
        sp_facility_referred_to_start.setOnItemSelectedListener(this);
        sp_reason_for_referral_start.setOnItemSelectedListener(this);
        spUPReceivedBy.setOnItemSelectedListener(this);
        spUPReferringFacility.setOnItemSelectedListener(this);

        rgInLabour.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                }
                strInLabour = rb.getText().toString();
            }
        });
        rgAdmitted.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                }
                strAdmitted = rb.getText().toString();
            }
        });

    }

    private void onClickListner() {
        edtDateOfReferral.setOnClickListener(this);
        edtTimeOfReferral.setOnClickListener(this);
        edtUPDateOfReferral.setOnClickListener(this);
        edtUPTimeOfReferral.setOnClickListener(this);
        btnReferalSubmit.setOnClickListener(this);
    }

    private void initUI() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        preferenceData = new PreferenceData(this);
        checkNetwork = new CheckNetwork(this);


        referalPresenter = new ReferalPresenter(AddReferral.this, this);
//        if (AppConstants.CREATE_NEW_REFRAL) {
        if (checkNetwork.isNetworkAvailable()) {
//            referalPresenter.getReffralNearestHospital(AppConstants.EXTRA_LATITUDE, AppConstants.EXTRA_LONGITUDE);
            referalPresenter.getReffralNearestHospital(preferenceData.gettCurentlatitude(), preferenceData.gettCurentlongitude());
        }else{
            Toast.makeText(getApplicationContext(), "Check Internet Connection... Try Again After Sometimes", Toast.LENGTH_LONG).show();
            finish();
        }

//        }
        nearestReferalHospitalList = new ArrayList<>();
        arr_nearstReferalHospitalList = new ArrayList<>();
        arr_nearstReferalHospitalList_name_distance = new ArrayList<>();
        arr_nearstReferalHospitalList_hospital_name = new ArrayList<>();
        arr_nearstReferalHospitalList_hospital_id = new ArrayList<>();

        arr_nearstReferalHospitalList_name_distance.add("--Select--");
        arr_nearstReferalHospitalList.add("--Select--");
        arr_nearstReferalHospitalList_hospital_name.add("--Select--");
        arr_nearstReferalHospitalList_hospital_id.add("--Select--");
        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);
        hour = mCurrentDate.get(Calendar.HOUR);
        minute = mCurrentDate.get(Calendar.MINUTE);
        sec = mCurrentDate.get(Calendar.SECOND);

        month = month + 1;
//        edt_delivery_date.setText(day + "-" + month + "-" + year);

        llAddNewReferal = (LinearLayout) findViewById(R.id.ll_add_new_referal);
        llUpdateRefral = (LinearLayout) findViewById(R.id.ll_update_refral);
        edtDateOfReferral = (EditText) findViewById(R.id.edt_date_of_referral_start);
//        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        SimpleDateFormat dateF = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String date = dateF.format(Calendar.getInstance().getTime());
        edtDateOfReferral.setText(date);
        edtTimeOfReferral = (EditText) findViewById(R.id.edt_time_of_referral_start);
        SimpleDateFormat timeF = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String time = timeF.format(Calendar.getInstance().getTime());
        edtTimeOfReferral.setText(time);
        sp_referred_by = (Spinner) findViewById(R.id.sp_referred_by);
        sp_referring_facility_start = (Spinner) findViewById(R.id.sp_referring_facility_start);
        sp_facility_referred_to_start = (Spinner) findViewById(R.id.sp_facility_referred_to_start);
        sp_reason_for_referral_start = (Spinner) findViewById(R.id.sp_reason_for_referral_start);
        edtDiagnosis = (EditText) findViewById(R.id.edt_diagnosis);
        edtUPDateOfReferral = (EditText) findViewById(R.id.edt_date_of_referral_end);
        String date1 = dateF.format(Calendar.getInstance().getTime());
        edtUPDateOfReferral.setText(date1);
        edtUPTimeOfReferral = (EditText) findViewById(R.id.edt_time_of_referral_end);
        String time1 = timeF.format(Calendar.getInstance().getTime());
        edtUPTimeOfReferral.setText(time1);
        spUPReceivedBy = (Spinner) findViewById(R.id.sp_received_by_end);
        spUPReferringFacility = (Spinner) findViewById(R.id.sp_receiving_facility_end);
        rgInLabour = (RadioGroup) findViewById(R.id.rg_labour);
        rgAdmitted = (RadioGroup) findViewById(R.id.rg_admitted);
        btnReferalSubmit = (Button) findViewById(R.id.btn_referal_submit);

        sp_referring_facility_start.setAdapter(new ArrayAdapter<String>(AddReferral.this,
                android.R.layout.simple_spinner_dropdown_item,
                arr_nearstReferalHospitalList_hospital_name));

        sp_facility_referred_to_start.setAdapter(new ArrayAdapter<String>(AddReferral.this,
                android.R.layout.simple_spinner_dropdown_item,
                arr_nearstReferalHospitalList_name_distance));
    }

    private void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Update Referral");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Intent intent = new Intent(AddReferral.this, ReferralList.class);
        finish();
//        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_referal_submit:

                if (AppConstants.CREATE_NEW_REFRAL) {
                    addReferalToServer();
                } else {
                    UpdateReferalOnServer();
                }

                break;
            case R.id.edt_date_of_referral_start:
                pickDate(edtDateOfReferral);

                break;
            case R.id.edt_time_of_referral_start:
                pickTime(edtTimeOfReferral);

                break;

            case R.id.edt_date_of_referral_end:
                pickDate(edtUPDateOfReferral);

                break;
            case R.id.edt_time_of_referral_end:
                pickTime(edtUPTimeOfReferral);
                break;
        }
    }

    private void pickTime(final EditText setTimeOfReferral) {
        TimePickerDialog mTimePicker = new TimePickerDialog(AddReferral.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String time = sdf.format(Calendar.getInstance().getTime());
                Log.d("time-->",time);
                setTimeOfReferral.setText(time);
            }
        }, hour, minute, true);
        InputMethodManager imm =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(setTimeOfReferral.getWindowToken(), 0);
        mTimePicker.show();
    }

    private void pickDate(final EditText setDateOfReferral) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddReferral.this, R.style.DatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                monthOfYear = monthOfYear + 1;
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String date = sdf.format(Calendar.getInstance().getTime());
                Log.d("date-->",date);

                Calendar c=Calendar.getInstance();
                mYear=c.get(Calendar.YEAR);
                mMonth=c.get(Calendar.MONTH);
                mDay=c.get(Calendar.DAY_OF_MONTH);
                SimpleDateFormat ssdf = new SimpleDateFormat("dd-MM-yyyy");
                setDateOfReferral.setText(ssdf.format(c.getTime()));
            }
        }, year, month, day);
        InputMethodManager imm =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(setDateOfReferral.getWindowToken(), 0);
        datePickerDialog.show();
    }

    private void UpdateReferalOnServer() {
        getallUpdateEditTextvalues();
        if (strUPDateOfReferral.equalsIgnoreCase("")) {
            showAlert("Date is Empty");
        } else if (strUPTimeOfReferral.equalsIgnoreCase("")) {
            showAlert("Time of is Empty");
        } else if (AppConstants.REFERAL_ID.equalsIgnoreCase("")) {
            showAlert("Referal Id is Empty");
        } else if (strUPReceivedBy.equalsIgnoreCase("--Select--")) {
            showAlert("Received By  is Empty");
        } else if (strUPReferringFacility.equalsIgnoreCase("--Select--")) {
            showAlert("Referring Facility is Empty");
        } else if (strInLabour.equalsIgnoreCase("")) {
            showAlert("Labour is Empty");
        } else if (strAdmitted.equalsIgnoreCase("")) {
            showAlert("Admitted is Empty");
        } else {

            if (checkNetwork.isNetworkAvailable()) {
                referalPresenter.updateReferal(AppConstants.REFERAL_ID, strUPDateOfReferral, strUPTimeOfReferral,
                        strUPReceivedBy, strUPReferringFacility, strInLabour, strAdmitted);
            }else{
                Toast.makeText(getApplicationContext(), "Check Internet Connection... Try Again After Sometimes", Toast.LENGTH_LONG).show();
                finish();
            }

        }
    }

    private void getallUpdateEditTextvalues() {
        strUPDateOfReferral = edtUPDateOfReferral.getText().toString();
        strUPTimeOfReferral = edtUPTimeOfReferral.getText().toString();
    }

    private void addReferalToServer() {
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
            if (checkNetwork.isNetworkAvailable()) {
                referalPresenter.addNewReferal(preferenceData.getPicmeId(),
                        preferenceData.getMId(),
                        preferenceData.getVhnId(),
                        preferenceData.getPhcId(), strDateOfReferral, strTimeOfReferral, strReferringFacility, strReferringFacility, strDiagnosis,
                        strReasonForReferral, strReferredBy, strReferringFacilityCode, strFacilityReferredToCode);
            }else{
                Toast.makeText(getApplicationContext(), "Check Internet Connection... Try Again After Sometimes", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    private void showAlert(String msg) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(AddReferral.this);


//        builder.setTitle("Hi Tamil Selvi,");
//        builder.setMessage("Have you take tablets regulerlly: ");
            builder.setTitle("Hi " +preferenceData.getMotherName()+ ",");
            builder.setMessage(msg);


            //Yes Button
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getApplicationContext(),"Take care",Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(),"Alert has set to VHN,  They will contact soon..",Toast.LENGTH_LONG).show();
//                AppConstants.isMainActivityOpen=false;

                    dialog.dismiss();
                }
            });
         builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getApplicationContext(),"Alert has set to VHN,  They will contact soon..",Toast.LENGTH_LONG).show();
//                AppConstants.isMainActivityOpen=false;
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
                if (parent.getSelectedItem().toString().equalsIgnoreCase("--Select--")) {
                }else{
                    strReferringFacilityCode = arr_nearstReferalHospitalList_hospital_id.get(position);
                    Log.e("ReferringFacToCode-->",position+""+strReferringFacilityCode);

                    //                Log.d("RefergFaciyCode",position+"-->"+strReferringFacilityCode);
                }
                break;
            case R.id.sp_facility_referred_to_start:
//                strFacilityReferredTo = parent.getSelectedItem().toString();
                strFacilityReferredTo = arr_nearstReferalHospitalList_hospital_name.get(position).toString();
//                parent.getSelectedItem().toString();

                if (parent.getSelectedItem().toString().equalsIgnoreCase("--Select--")) {

                }else{
                    strFacilityReferredToCode=arr_nearstReferalHospitalList_hospital_id.get(position);
                    Log.e("FacilityRefToCode-->",position+""+strFacilityReferredToCode);
                }
//                Log.d("RefergFaciyToCode",position+"-->"+strFacilityReferredToCode);


                break;

            case R.id.sp_reason_for_referral_start:
                strReasonForReferral = parent.getSelectedItem().toString();
                break;

            case R.id.sp_received_by_end:
                strUPReceivedBy = parent.getSelectedItem().toString();
                break;

            case R.id.sp_receiving_facility_end:
                strUPReferringFacility = parent.getSelectedItem().toString();
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
            JSONObject jsonObject = new JSONObject(response);
            String status =jsonObject.getString("status");
            String msg = jsonObject.getString("message");
            if (status.equalsIgnoreCase("1")) {
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                showAlert(msg,"gomain");
            }else{
             showAlert(msg);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String msg, String gomain) {


        final AlertDialog.Builder builder = new AlertDialog.Builder(AddReferral.this);


//        builder.setTitle("Hi Tamil Selvi,");
//        builder.setMessage("Have you take tablets regulerlly: ");
        builder.setTitle("Hi " +preferenceData.getMotherName()+ ",");
        builder.setMessage(msg);


        //Yes Button
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getApplicationContext(),"Take care",Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(),"Alert has set to VHN,  They will contact soon..",Toast.LENGTH_LONG).show();
//                AppConstants.isMainActivityOpen=false;
//                dialog.dismiss();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
        /*builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getApplicationContext(),"Alert has set to VHN,  They will contact soon..",Toast.LENGTH_LONG).show();
//                AppConstants.isMainActivityOpen=false;
                dialog.dismiss();
            }
        });*/

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


    @Override
    public void errorReferalAdd(String response) {
        Log.d("AddReferal error", response);

    }

    @Override
    public void successReferalNearestHospital(String response) {
        Log.d("AddReferal", " success NearestHospital" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            if (status.equalsIgnoreCase("1")) {
                JSONArray jsonArray = jsonObject.getJSONArray("nearestHospitals");
                Log.e("nearestHospitals arr", jsonArray.length() + "");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    nearestReferalHospitalModel = new NearestReferalHospitalModel.NearestHospitals();
                    nearestReferalHospitalModel.setDistance(jsonObject1.getString("phcId"));
                    nearestReferalHospitalModel.setPhcCode(jsonObject1.getString("phcCode"));
                    nearestReferalHospitalModel.setPhcId(jsonObject1.getString("distance"));
                    arr_nearstReferalHospitalList_hospital_name.add(jsonObject1.getString("phcCode")+"  ("+jsonObject1.getString(""+"f_facility_name")+")");

                    arr_nearstReferalHospitalList.add(jsonObject1.getString("phcId")+"-"+jsonObject1.getString("distance"));
                    arr_nearstReferalHospitalList_name_distance.add(jsonObject1.getString("phcCode")+"   "+jsonObject1.getString("distance").substring(0,2)+" km"+"  ("+jsonObject1.getString("" +
                            "f_facility_name")+")");
                    arr_nearstReferalHospitalList_hospital_id.add(jsonObject1.getString("phcId"));/*+"-"+jsonObject1.getString("distance")*/
                    nearestReferalHospitalList.add(nearestReferalHospitalModel);
                }

//                sp_referring_facility_start.setAdapter((SpinnerAdapter) nearestReferalHospitalList);
//                sp_facility_referred_to_start.setAdapter((SpinnerAdapter) nearestReferalHospitalList);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void errorReferalNearestHospital(String response) {

        Log.d("AddReferal", " error NearestHospital" + response);


    }

    @Override
    public void successReferalList(String response) {

    }

    @Override
    public void errorReferalList(String response) {

    }

    @Override
    public void successReferalClosed(String response) {

    }

    @Override
    public void errorReferalClosed(String response) {

    }


}
