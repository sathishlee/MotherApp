package com.unicef.thaimai.motherapp.activity;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.RegisterPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.model.requestmodel.BlockSpinnerModel;
import com.unicef.thaimai.motherapp.model.requestmodel.PHCSpinnerModel;
import com.unicef.thaimai.motherapp.model.requestmodel.StateSpinnerModel;
import com.unicef.thaimai.motherapp.utility.CheckNetwork;
import com.unicef.thaimai.motherapp.utility.LocationMonitoringService;
import com.unicef.thaimai.motherapp.view.RegisterViews;

import net.alexandroid.gps.GpsStatusDetector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,
        RegisterViews, AdapterView.OnItemSelectedListener, GpsStatusDetector.GpsStatusDetectorCallBack {

    ProgressDialog pDialog;
    CheckNetwork checkNetwork;
    PreferenceData preferenceData;
    Button btn_register;
    EditText user_name, user_dob, mobile, alternative_number,hus_name;
    TextInputLayout input_layout_name,input_layout_dob,
            input_layout_mobile_number, input_layout_alternative_number,input_layout_husname;
    Spinner sp_district,sp_block,sp_phc;
    String strUserName, strDOD, strMobile, strAlterMobile, strDist, strBlock, strPHC, strHusName;
    Calendar mCurrentDate;
    int day, month, year;
    private int mYear,mMonth,mDay;
    RegisterPresenter registerPresenter;
    StateSpinnerModel.Alldist stateSpinnerModel;
    PHCSpinnerModel.Block phclist;
    BlockSpinnerModel.Dist dist;
    ArrayList<String> stateList;
    ArrayList<String> stateSpinner;
    ArrayList<String> dist_id;
    ArrayList<String> block;
    ArrayList<String> block_id;
    ArrayList<String> phc;
    ArrayList<String> phc_id;
    ArrayList<StateSpinnerModel.Alldist> alldists;
    ArrayList<BlockSpinnerModel.Dist> dists;
    ArrayList<PHCSpinnerModel.Block> blocks;
    LinearLayout ll_phc, ll_block;

    ArrayAdapter<String> arrayAdapterState;
    ArrayAdapter<String> arrayAdapterBlock;
    ArrayAdapter<String> arrayAdapterPhc;

    private GpsStatusDetector mGpsStatusDetector;
    boolean mISGpsStatusDetector;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        startService(new Intent(this, LocationMonitoringService.class));
        initUI();
        onClicklistiner();
        onItemSelectedlistiner();
    }

    private void onItemSelectedlistiner() {
        sp_district.setOnItemSelectedListener(this);
        sp_block.setOnItemSelectedListener(this);
        sp_phc.setOnItemSelectedListener(this);
    }

    private void onClicklistiner() {
        user_dob.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    private void initUI() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        mGpsStatusDetector = new GpsStatusDetector(this);
        mGpsStatusDetector.checkGpsStatus();
        checkNetwork = new CheckNetwork(this);
        preferenceData = new PreferenceData(this);
        registerPresenter = new RegisterPresenter(this, this);
        registerPresenter.getDistrictJson();
        btn_register = (Button) findViewById(R.id.btn_register);
        input_layout_name =(TextInputLayout) findViewById(R.id.input_layout_name);
        input_layout_dob = (TextInputLayout) findViewById(R.id.input_layout_dob);
        input_layout_mobile_number =(TextInputLayout)findViewById(R.id.input_layout_mobile_number);
        input_layout_alternative_number = (TextInputLayout) findViewById(R.id.input_layout_alternative_number);
        input_layout_husname = (TextInputLayout) findViewById(R.id.input_layout_husname);
        //EditText Declaration
        user_name = (EditText)findViewById(R.id.name);
        user_dob = (EditText) findViewById(R.id.dob);
        mobile = (EditText) findViewById(R.id.mobile_number);
        alternative_number = (EditText) findViewById(R.id.alternative_number);
        hus_name = (EditText) findViewById(R.id.hus_name);
        sp_district = (Spinner) findViewById(R.id.sp_district);
        sp_block = (Spinner) findViewById(R.id.sp_block);
        sp_phc = (Spinner) findViewById(R.id.sp_phc);
        ll_block = (LinearLayout) findViewById(R.id.ll_block);
        ll_block.setVisibility(View.GONE);
        ll_phc = (LinearLayout) findViewById(R.id.ll_phc);
        ll_phc.setVisibility(View.GONE);
        alldists = new ArrayList<>();
        dists = new ArrayList<>();
        blocks = new ArrayList<>();
        stateList = new ArrayList<>();
        stateSpinner = new ArrayList<>();
        dist_id = new ArrayList<>();
        block = new ArrayList<>();
        block_id = new ArrayList<>();
        phc = new ArrayList<>();
        phc_id = new ArrayList<>();
        stateList.add("--Select--");
        stateSpinner.add("--Select--");
        dist_id.add("--Select--");
        block.add("--Select--");
        block_id.add("--Select--");
        phc.add("--Select--");
        phc_id.add("--Select--");

        arrayAdapterState = new ArrayAdapter<String>(RegisterActivity.this,
                android.R.layout.simple_spinner_dropdown_item,stateSpinner);
        arrayAdapterState.notifyDataSetChanged();
        sp_district.setAdapter(arrayAdapterState);
        arrayAdapterBlock = new ArrayAdapter<String>(RegisterActivity.this,
                android.R.layout.simple_spinner_dropdown_item,block);
        arrayAdapterBlock.notifyDataSetChanged();
        sp_block.setAdapter(arrayAdapterBlock);

        arrayAdapterPhc = new ArrayAdapter<String>(RegisterActivity.this,
                android.R.layout.simple_spinner_dropdown_item,phc);
        arrayAdapterPhc.notifyDataSetChanged();
        sp_phc.setAdapter(arrayAdapterPhc);

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Are you Sure do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    private void pickDate(final EditText setDateOfReferral) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, R.style.DatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                sendValues();
                break;
            case R.id.dob:
                pickDate(user_dob);
                break;
        }
    }

    private void sendValues() {
        edtTextValues();
        if(strUserName.equalsIgnoreCase("")){
            showAlert("Please Enter Your Name");
        }else if(strDOD.equalsIgnoreCase("")){
            showAlert("Please Enter DOB");
        }else if(strDist.equalsIgnoreCase("--Select--")){
            showAlert("Please Select District");
        }else if(strBlock.equalsIgnoreCase("--Select--")){
            showAlert("Please Select Block");
        }else if(strPHC.equalsIgnoreCase("--Select--")){
            showAlert("Please Select PHC");
        }else if(strMobile.equalsIgnoreCase("")){
            showAlert("Please Enter Mobile Number");
        }else if(strAlterMobile.equalsIgnoreCase("")){
            showAlert("Please Enter Alternative Number");
        }else {
            if(checkNetwork.isNetworkAvailable()){
                if(mISGpsStatusDetector){
                    registerPresenter.submitRegisterMother(preferenceData.getDeviceId(),
                            AppConstants.EXTRA_LATITUDE,AppConstants.EXTRA_LONGITUDE,
                            strUserName,strDOD,strDist,strBlock,strPHC,strMobile,strAlterMobile,strHusName);
                }else{
                    Toast.makeText(getApplicationContext(), "Turn On Gps Location..", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(getApplicationContext(), "Check Internet Connection... Try Again After Sometimes", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    private void edtTextValues() {
        strUserName = user_name.getText().toString();
        strDOD = user_dob.getText().toString();
        strHusName = hus_name.getText().toString();
        strMobile = mobile.getText().toString();
        strAlterMobile = alternative_number.getText().toString();
    }

    private void showAlert(String msg) {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
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
    public void getRegisterSuccess(String response) {
        Log.d("Register", "District Data" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            if (status.equalsIgnoreCase("1")) {
                JSONArray jsonArray = jsonObject.getJSONArray("alldist");
                Log.e("alldist arr", jsonArray.length() + "");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    stateSpinnerModel = new StateSpinnerModel.Alldist();
                    stateSpinnerModel.setDst_code(jsonObject1.getString("dst_code"));
//                    strDist = stateSpinnerModel.setDst_code(jsonObject1.getString("dst_code"));
//                    Log.e("dist Selected-->", strDist+ "");
                    stateSpinnerModel.setDst_name(jsonObject1.getString("dst_name"));
                    dist_id.add(jsonObject1.getString("dst_code"));
                    stateSpinner.add(jsonObject1.getString("dst_name"));
                    alldists.add(stateSpinnerModel);
                }
                sp_district.setAdapter(arrayAdapterState);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getRegisterFailure(String response) {

    }

    @Override
    public void getAllDistrictDataSuccess(String response) {
        Log.d("Register", "District Data" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            if (status.equalsIgnoreCase("1")) {
                JSONArray jsonArray = jsonObject.getJSONArray("alldist");
                Log.e("alldist arr", jsonArray.length() + "");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    stateSpinnerModel = new StateSpinnerModel.Alldist();
                    stateSpinnerModel.setDst_code(jsonObject1.getString("dst_code"));
//                    strDist = stateSpinnerModel.setDst_code(jsonObject1.getString("dst_code"));
//                    Log.e("dist Selected-->", strDist+ "");
                    stateSpinnerModel.setDst_name(jsonObject1.getString("dst_name"));
                    dist_id.add(jsonObject1.getString("dst_code"));
                    stateSpinner.add(jsonObject1.getString("dst_name"));
                    alldists.add(stateSpinnerModel);
                }
                sp_district.setAdapter(arrayAdapterState);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAllDistrictDataFailure(String response) {
        Log.e("Register--->", "Data" + response);
    }

    @Override
    public void getAllBlockDataSuccess(String response) {
        Log.e("Block--->", "Data" + response);

        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            if (status.equalsIgnoreCase("1")) {
                JSONArray jsonArray = jsonObject.getJSONArray("dist");
                Log.e("block", jsonArray.length() + "");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    dist = new BlockSpinnerModel.Dist();
                    dist.setF_sub_district_nam(jsonObject1.getString("f_sub_district_nam"));
                    dist.setBkid(jsonObject1.getString("bkid"));
//                    arrayAdapterBlock.add("--Select--");
                    block.add(jsonObject1.getString("f_sub_district_nam"));
//                    arrayAdapterBlock.add(jsonObject1.getString("f_sub_district_nam"));
                    block_id.add(jsonObject1.getString("bkid"));
                    dists.add(dist);
                }

                sp_block.setAdapter(arrayAdapterBlock);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getAllBlockDataFailure(String response) {
        Log.e("Block Failure-->", "value" + response);

    }

    @Override
    public void getAllPHCDataSuccess(String response) {
        Log.e("PHC--->", "Data" + response);

        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            if (status.equalsIgnoreCase("1")) {
                JSONArray jsonArray = jsonObject.getJSONArray("block");
                Log.e("PHC", jsonArray.length() + "");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    phclist = new PHCSpinnerModel.Block();

                    phclist.setF_facility_name(jsonObject1.getString("f_facility_name"));
                    phclist.setBkid(jsonObject1.getString("phid"));
//                    arrayAdapterPhc.add("--Select--");
//                    arrayAdapterPhc.add(jsonObject1.getString("f_facility_name"));
                    phc.add(jsonObject1.getString("f_facility_name"));
                    phc_id.add(jsonObject1.getString("phid"));
                    blocks.add(phclist);
                }
                sp_phc.setAdapter(arrayAdapterPhc);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getAllPHCDataFailure(String response) {
        Log.e("PHC Failure-->", "Data" + response);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_district:
                strDist = parent.getSelectedItem().toString();
                if(strDist.equalsIgnoreCase("--Select--")){
                    Toast.makeText(getApplicationContext(),"Please Select Spinner",
                            Toast.LENGTH_LONG).show();
                    ll_block.setVisibility(View.GONE);
                }else{
//                    strDist = String.valueOf(position);
                    strDist = String.valueOf(Integer.parseInt(dist_id.get(position)));
                    Log.e("state-->",""+strDist);
                    registerPresenter.getBlockJson(strDist);
                    ll_block.setVisibility(View.VISIBLE);
                    reset(strDist);

                }
//                arrayAdapterBlock.clear();
                break;
            case R.id.sp_block:
                strBlock = parent.getSelectedItem().toString();
                Log.e("Block Name-->",""+strBlock);
                
                if(strBlock.equalsIgnoreCase("--Select--")){
                    Toast.makeText(getApplicationContext(),"Please Select Spinner",
                            Toast.LENGTH_LONG).show();
                    ll_phc.setVisibility(View.GONE);
                }else{
//                    arrayAdapterBlock.clear();
//                    arrayAdapterBlock.notifyDataSetChanged();
                    strBlock = String.valueOf(Integer.parseInt(block_id.get(position)));
                    Log.e("Block-->",""+strBlock);
                    Log.e("DISTRICT ID-->",""+strDist);
//                    arrayAdapterPhc.clear();
                    registerPresenter.getPHCJson(strDist,strBlock);
                    ll_phc.setVisibility(View.VISIBLE);
                    resetBlock(strBlock);
//                    arrayAdapterPhc.notifyDataSetChanged();
                }
                /*arrayAdapterBlock = new ArrayAdapter<String>(RegisterActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,block);
//                arrayAdapterBlock.notifyDataSetChanged();*/
//                sp_block.setAdapter(arrayAdapterBlock);
                break;
            case R.id.sp_phc:
                strPHC = parent.getSelectedItem().toString();
                if(strPHC.equalsIgnoreCase("--Select--")){
                    Toast.makeText(getApplicationContext(),"Please Select Spinner",
                            Toast.LENGTH_LONG).show();
                }else {
                    strPHC = String.valueOf(Integer.parseInt(phc_id.get(position)));
                    Log.e("PHC-->",""+strPHC);

                }
                break;

        }
    }

    private void resetBlock(String strBlock) {
        phc.removeAll(phc);
        phc_id.removeAll(phc_id);
        if(strBlock.equalsIgnoreCase("--Select--")){
            Toast.makeText(getApplicationContext(),"Please Select Spinner",
                    Toast.LENGTH_LONG).show();
        }else{
            arrayAdapterPhc.add("--Select--");
            phc_id.add("--Select--");
        }
        arrayAdapterPhc = new ArrayAdapter<String>(RegisterActivity.this,
                android.R.layout.simple_spinner_dropdown_item,phc);
        arrayAdapterBlock.notifyDataSetChanged();
        sp_phc.setAdapter(arrayAdapterPhc);
    }

    private void reset(String strDist) {
        block.removeAll(block);
        block_id.removeAll(block_id);
        if(strDist.equalsIgnoreCase("--Select--")){
            Toast.makeText(getApplicationContext(),"Please Select Spinner",
                    Toast.LENGTH_LONG).show();
        }else{
            arrayAdapterBlock.add("--Select--");
            block_id.add("--Select--");
        }
        arrayAdapterBlock = new ArrayAdapter<String>(RegisterActivity.this,
                android.R.layout.simple_spinner_dropdown_item,block);
//        arrayAdapterBlock.notifyDataSetChanged();
        sp_block.setAdapter(arrayAdapterBlock);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onGpsSettingStatus(boolean enabled) {
        Log.d("TAG", "onGpsSettingStatus: " + enabled);
        mISGpsStatusDetector = enabled;
        if(!enabled){
            mGpsStatusDetector.checkGpsStatus();
        }
    }

    @Override
    public void onGpsAlertCanceledByUser() {
        Log.d("TAG", "onGpsAlertCanceledByUser");
        startActivity(new Intent(getApplicationContext(),TurnOnGpsLocation.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mGpsStatusDetector.checkOnActivityResult(requestCode, resultCode);
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, LocationMonitoringService.class));
        super.onDestroy();
    }

}
