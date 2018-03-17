package com.unicef.thaimai.motherapp.activity;

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
import android.widget.EditText;
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


public class Login extends AppCompatActivity implements View.OnClickListener, LoginViews
{
    Button btn_login;
    EditText edtPicme, edtDob;
    TextInputLayout iplPicmeId, iplDob;
    TextView txtForgetPicme;
    String strPicme, strDob;
    ProgressDialog pDialog;
    LoginPresenter loginPresenter;
    PreferenceData preferenceData;
    ConnectivityManager conMgr;

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
        loginPresenter = new LoginPresenter(Login.this, this);
        preferenceData = new PreferenceData(this);
        btn_login = (Button) findViewById(R.id.btn_submit);
        edtPicme = (EditText) findViewById(R.id.edt_picme_id);
        edtDob = (EditText) findViewById(R.id.edt_dob);
        txtForgetPicme = (TextView) findViewById(R.id.txt_forgot_picme);
        iplPicmeId = (TextInputLayout) findViewById(R.id.input_layout_picme_id);
        iplDob = (TextInputLayout) findViewById(R.id.input_layout_dob);
    }

    private void onClickListner() {
        btn_login.setOnClickListener(this);
        txtForgetPicme.setOnClickListener(this);
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
        startActivity(new Intent(getApplicationContext(), ForgotPicme.class));
    }

    private void getValue() {
        strPicme = edtPicme.getText().toString();
        strDob = edtDob.getText().toString();

        if (strPicme.equalsIgnoreCase("")) {
            iplPicmeId.setError("Pickme ID is Empty");
        }
        if (strDob.equalsIgnoreCase("")) {
            iplDob.setError("Dob is Empty");
        }
        if (strPicme.length() < 13) {
            iplPicmeId.setError("Enter Correct Picme ID");
        }
        if (strPicme.length() > 13) {
            iplPicmeId.setError("Enter Correct Picme ID");
        } else {
            loginPresenter.checkPickmeId(strPicme, strDob);

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
    public void showPickmeResult(String response) {
        Log.d("Response success", response);
        JSONObject jObj = null;
        try {
            jObj = new JSONObject(response);
            int status = jObj.getInt("status");
            String message = jObj.getString("message");
            if (status == 1) {
                Log.d("message---->", message);
                preferenceData.storeUserInfo(jObj.getString("picmeId"),jObj.getString("mid"),
                        jObj.getString("mName"), jObj.getString("motherAge"),
                        jObj.getString("motherStatus"), jObj.getString("phcId"), jObj.getString("vhnId"), jObj.getString("awwId"));
                preferenceData.setLogin(true);
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
