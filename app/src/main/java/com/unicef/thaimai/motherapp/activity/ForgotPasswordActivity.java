package com.unicef.thaimai.motherapp.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.LoginPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.utility.CheckNetwork;
import com.unicef.thaimai.motherapp.view.LoginViews;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Suthishan on 20/1/2018.
 */

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener, LoginViews {
    EditText edt_pickme_number, edt_mobile_no;
    TextInputLayout iplPicmeId, iplDob, input_layout_otp;

    String strPicme, strMob;

    Button btn_submit;
    TextView txt_backto_login;
    ProgressDialog pDialog;
    LoginPresenter loginPresenter;
    PreferenceData preferenceData;
    ConnectivityManager conMgr;
    CheckNetwork checkNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_picme);
        initUI();
        onClickListner();
        showActionbar();
    }

    private void showActionbar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Forgot Password");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void onClickListner() {
        btn_submit.setOnClickListener(this);
        txt_backto_login.setOnClickListener(this);
    }

    private void initUI() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        loginPresenter = new LoginPresenter(ForgotPasswordActivity.this, this);
        preferenceData = new PreferenceData(this);
        checkNetwork = new CheckNetwork(this);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        edt_pickme_number = (EditText) findViewById(R.id.edt_pickme_number);
        edt_mobile_no = (EditText) findViewById(R.id.edt_mobile_no);
        txt_backto_login = (TextView) findViewById(R.id.txt_backto_login);
        iplPicmeId = (TextInputLayout) findViewById(R.id.input_layout_mobile_number);
        iplDob = (TextInputLayout) findViewById(R.id.input_regDob);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                getValue();
                break;
            case R.id.txt_forgot_picme:
                goforgetPicmepage();
                break;
        }
    }

    private void goforgetPicmepage() {
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();

    }

    private void getValue() {

        strPicme = edt_pickme_number.getText().toString();
        strMob = edt_mobile_no.getText().toString();
        preferenceData.getDeviceId();

        if (strPicme.equalsIgnoreCase("")) {
            iplPicmeId.setError("Enter Picme Id");
        }
        if (strMob.equalsIgnoreCase("")) {
            iplDob.setError("Enter Primary Number");
        } else {
            if (checkNetwork.isNetworkAvailable()) {
                loginPresenter.forgetPassword(strPicme, strMob);
            }else {
                startActivity(new Intent(getApplicationContext(), NoInternetConnection.class));
            }
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

    }

    @Override
    public void loginError(String string) {

    }

    @Override
    public void showPickmeResult(String loginResponseModel) {

    }

    @Override
    public void showErrorMessage(String string) {

    }

    @Override
    public void showForgetResult(String response) {
        Log.d("Response success", response);
        JSONObject jObj = null;
        try {
            jObj = new JSONObject(response);
            int status = jObj.getInt("status");
            String message = jObj.getString("message");
            if (status == 1) {
                JSONObject  jObjDetails =  jObj.getJSONObject("Login Details");
                String mPicmeId = jObjDetails.getString("mPicmeId");
                String mDOB = jObjDetails.getString("mDOB");
                Toast.makeText(getApplicationContext(), mDOB, Toast.LENGTH_SHORT).show();

                showDailog(mDOB);
            }else{
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//                showDailog(message);

            }

        }catch (JSONException e){
            e.printStackTrace();

        }
    }

    private void showDailog(String mDOB) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Password is - "+mDOB)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        startActivity(new Intent(getApplicationContext(),Login.class));
                        finish();
                    }
                });


        // Create the AlertDialog object and return it
         AlertDialog dialog =builder.create();
         dialog.show();

    }

    @Override
    public void showForgetErrorMessage(String errormsg) {
        Log.d("Response field", errormsg);

    }

    @Override
    public void showUninstallSuccess(String response) {

    }

    @Override
    public void showUninstallError(String string) {

    }
}
