package com.unicef.thaimai.motherapp.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.LoginPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.model.responsemodel.LoginResponseModel;
import com.unicef.thaimai.motherapp.view.LoginViews;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;


public class Login extends AppCompatActivity implements View.OnClickListener, LoginViews
{
    Button btn_login, btn_otp_submit;
    EditText edtPicme, edtDob, edt_otp;
    TextInputLayout iplPicmeId, iplDob, input_layout_otp;
    TextView txtForgetPicme;
    String strPicme, strDob, strOtp;
    ProgressDialog pDialog;
    LoginPresenter loginPresenter;
    PreferenceData preferenceData;
    ConnectivityManager conMgr;
    Calendar mCurrentDate;
    int day, month, year, hour, minute, sec;
    private String message;
    LinearLayout ll_signin, ll_otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        initUI();
        onClickListner();
    }

    private void initUI() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);
        hour = mCurrentDate.get(Calendar.HOUR);
        minute = mCurrentDate.get(Calendar.MINUTE);
        sec = mCurrentDate.get(Calendar.SECOND);
        loginPresenter = new LoginPresenter(Login.this, this);
        preferenceData = new PreferenceData(this);
        btn_login = (Button) findViewById(R.id.btn_submit);
        edtPicme = (EditText) findViewById(R.id.edt_picme_id);
        edtDob = (EditText) findViewById(R.id.edt_dob);
        txtForgetPicme = (TextView) findViewById(R.id.txt_forgot_picme);
        iplPicmeId = (TextInputLayout) findViewById(R.id.input_layout_picme_id);
        iplDob = (TextInputLayout) findViewById(R.id.input_layout_dob);
        ll_signin = (LinearLayout) findViewById(R.id.ll_signin);
        ll_otp = (LinearLayout) findViewById(R.id.ll_otp);

        input_layout_otp = (TextInputLayout) findViewById(R.id.input_layout_otp);
        edt_otp = (EditText) findViewById(R.id.edt_otp);
        btn_otp_submit = (Button) findViewById(R.id.btn_otp_submit);
    }

    private void onClickListner() {
        btn_login.setOnClickListener(this);
        txtForgetPicme.setOnClickListener(this);
        edtDob.setOnClickListener(this);
        btn_otp_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                getValue();
                break;
            case R.id.btn_otp_submit:
                enterOtp();
                break;

            case R.id.txt_forgot_picme:
                goforgetPicmepage();
                break;

                case R.id.edt_dob:
//                getDob(edtDob);
                break;
        }

    }

    private void enterOtp() {
        strOtp = edt_otp.getText().toString();

        if(strOtp.equalsIgnoreCase("")){
            input_layout_otp.setError("Please Enter OTP");
        }else{
            loginPresenter.checkOtp(preferenceData.getCheckPicmeID(),preferenceData.getCheckDob(),preferenceData.getDeviceId(),strOtp);
        }
    }

    private void getDob(final EditText edtDob) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(Login.this, R.style.DatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear = monthOfYear + 1;
                edtDob.setText(dayOfMonth + "-" + monthOfYear + "-" + year);
            }
        }, year, month, day);
        datePickerDialog.show();

    }

    private void goforgetPicmepage() {
        startActivity(new Intent(getApplicationContext(), forgot_password.class));
    }

    private void getValue() {
        strPicme = edtPicme.getText().toString();
        strDob = edtDob.getText().toString();
        preferenceData.getDeviceId();

        if (strPicme.equalsIgnoreCase("")) {
            iplPicmeId.setError("Pickme ID is Empty");
        }
        if (strDob.equalsIgnoreCase("")) {
            iplDob.setError("Dob is Empty");
        }
        if (strPicme.length() < 10) {
            iplPicmeId.setError("Enter Correct Picme ID");
        }
        if (strPicme.length() > 14) {
            iplPicmeId.setError("Enter Correct Picme ID");
        } else {
//            loginPresenter.checkPickmeId(strPicme, strDob, "dT7h3twBpWU:APA91bHQqQOCBueyUGhvY2uIsMNfIfM7ynMlVzm89tTTWDeKhXzMWCS9WZL1gu8nFz_nkwU5Po9i8ytXHmjoxAeu36BTbIFHwWhWfjbWtO-EjG6n7zW4M_PFCCOID8eE0fQX4RPPHfBQ");
            loginPresenter.checkPickmeId(strPicme, strDob, preferenceData.getDeviceId());

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
    public void loginSuccess(String response) {
        Log.d("Response success",response);
        JSONObject jsonObject = null;
        try{
            jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            String message = jsonObject.getString("message");

            if (status.equalsIgnoreCase("1")){
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                ll_otp.setVisibility(View.VISIBLE);
                ll_signin.setVisibility(View.GONE);
                preferenceData.storePicmeInfo(jsonObject.getString("picmeId"),jsonObject.getString("DOB"));
            }else {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                ll_otp.setVisibility(View.GONE);
                ll_signin.setVisibility(View.VISIBLE);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loginError(String string) {
        Log.d("Login Error-->", string);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (pDialog!=null && pDialog.isShowing() ){
            pDialog.cancel();
        }
    }

    @Override
    public void showPickmeResult(String response) {
        Log.d("Response success", response);
        JSONObject jObj = null;
        try {
            jObj = new JSONObject(response);
            int status = jObj.getInt("status");
            String message = jObj.getString("message");
            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            if (status == 1) {
                Log.d("message---->", message);
                preferenceData.storeUserInfo(jObj.getString("picmeId"),jObj.getString("mid"),
                        jObj.getString("mName"), jObj.getString("motherAge"),
                        jObj.getString("motherStatus"), jObj.getString("phcId"), jObj.getString("vhnId"), jObj.getString("awwId"), jObj.getString("mGesWeek"), jObj.getString("vhnMobile"));
                preferenceData.setLogin(true);
               preferenceData.setMainScreenOpen(0);
               AppConstants.POP_UP_COUNT=0;
                if (message.equalsIgnoreCase("Successfully Logined")) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    AppConstants.BACK_BUTTON_GONE = false;
                    finish();

                }
            } else {
                Log.d("message---->", message);
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showErrorMessage(String errormsg) {
        Log.d("Response field", errormsg);
    }


}
