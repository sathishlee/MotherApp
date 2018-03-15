package com.unicef.thaimai.motherapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.GetUserInfoPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.volleyservice.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.unicef.thaimai.motherapp.model.responsemodel.LoginResponseModel;
import com.unicef.thaimai.motherapp.view.LoginViews;


public class splashscreen extends Activity  {
    ProgressDialog pDialog;
    PreferenceData preferenceData;
    SharedPreferences.Editor editor;
    Boolean session_status= false;
    Activity mActivity;
    JSONObject user_info;
public class Splashscreen extends Activity {
    ProgressDialog pDialog;
    PreferenceData preferenceData;
    SharedPreferences.Editor editor;
    Boolean session_status= false;

    GetUserInfoPresenter getUserInfoPresenter;

    Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.splash);

        preferenceData = new PreferenceData(splashscreen.this);
        editor = getSharedPreferences(AppConstants.PREF_NAME, MODE_PRIVATE).edit();
        mActivity=this;
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        pDialog.show();
        session_status= preferenceData.getLogin();
        if(session_status)
        {
            pDialog.hide();
            startActivity(new Intent(new Intent(splashscreen.this,MainActivity.class)));
            finish();
        }else{
            pDialog.hide();
            Intent i = new Intent(splashscreen.this, Login.class);
            startActivity(i);
        }
    }

    }
}