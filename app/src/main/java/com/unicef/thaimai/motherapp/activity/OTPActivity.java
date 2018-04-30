/*
package com.unicef.thaimai.motherapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.LoginPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.view.LoginViews;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

*/
/**
 * Created by Suthishan on 20/1/2018.
 *//*


public class OTPActivity extends AppCompatActivity implements View.OnClickListener, LoginViews {

    Button btn_otp_submit;
    EditText edt_otp;
    TextInputLayout input_layout_otp;
    String strOtp;
    ProgressDialog pDialog;
    LoginPresenter loginPresenter;
    PreferenceData preferenceData;
    ConnectivityManager conMgr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
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
        loginPresenter = new LoginPresenter(OTPActivity.this, this);
        preferenceData = new PreferenceData(this);
        input_layout_otp = (TextInputLayout) findViewById(R.id.input_layout_otp);
        edt_otp = (EditText) findViewById(R.id.edt_otp);
        btn_otp_submit = (Button) findViewById(R.id.btn_otp_submit);
    }

    private void onClickListner() {
        btn_otp_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_otp_submit:
                enterOtp();
                break;
        }

    }

    private void enterOtp() {
        strOtp = edt_otp.getText().toString();

        if(strOtp.equalsIgnoreCase("")){
            input_layout_otp.setError("Please Enter OTP");
        }else{
            loginPresenter.checkOtp(strOtp);
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
    public void onDestroy(){
        super.onDestroy();
        if (pDialog!=null && pDialog.isShowing() ){
            pDialog.cancel();
        }
    }

    @Override
    public void loginSuccess(String response) {

    }

    @Override
    public void loginError(String string) {

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
    public void showErrorMessage(String string) {
        Log.d("Response field", string);
    }
}
*/
