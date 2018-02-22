package com.unicef.thaimai.motherapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
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
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.model.responsemodel.LoginResponseModel;
import com.unicef.thaimai.motherapp.view.LoginViews;


public class splashscreen extends Activity  {
    ProgressDialog pDialog;
    PreferenceData preferenceData;
    SharedPreferences.Editor editor;
    Boolean session_status= false;
    Activity mActivity;
    JSONObject user_info;
public class Splashscreen extends Activity implements LoginViews {
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
        preferenceData = new PreferenceData(Splashscreen.this);
        editor = getSharedPreferences(AppConstants.PREF_NAME, MODE_PRIVATE).edit();
        mActivity=this;
        getUserInfoPresenter = new GetUserInfoPresenter(mActivity,this) ;

        Thread background = new Thread() {
            public void run() {

                try {
                    sleep(1000);

                    session_status= preferenceData.getLogin();
                    if(session_status)
                    {
                        startActivity(new Intent(new Intent(splashscreen.this,MainActivity.class)));
                        finish();
                    }else{
                        Intent i = new Intent(splashscreen.this, Login.class);
                        startActivity(i);
                    }
//                    finish();
                    sleep(4000);



                    session_status= preferenceData.getLogin();
                    if(session_status)
                    {
//                        getUserInfoPresenter.getUserInfo("100000000013");
                        startActivity(new Intent(new Intent(Splashscreen.this,MainActivity.class)));
                        finish();

                    }else{
                        Intent i = new Intent(Splashscreen.this, Login.class);
                        startActivity(i);
                    }
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        background.start();
//        if(session_status) {
            getUserInfo("100000000013");
//        }

    }

    private void getUserInfo(String picmeId) {



         pDialog.show();


        String url = Apiconstants.BASE_URL+Apiconstants.USER_INFO+picmeId;

        Log.e("user info url",url);
        StringRequest strReq = new StringRequest(Request.Method.POST,url , new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("User info", " Response: " + response.toString());
                pDialog.dismiss();
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONObject userinfo = jObj.getJSONObject("userinfo");
                    Log.e("motherName---->",userinfo.getString("motherName"));
                    Log.e("Age---->",userinfo.getString("Age"));
                    Log.e("picmeNO---->",userinfo.getString("picmeNO"));

                    JSONObject usermedical = jObj.getJSONObject("usermedical");
                    JSONArray EmergencyContacts = jObj.getJSONArray("EmergencyContacts");

                    int status = jObj.getInt("status");
                    // Check for error in json
                    if (status == 1) {
                        preferenceData.storeUserInfo(userinfo.toString());
                        preferenceData.storeusermedical(usermedical.toString());
                        preferenceData.storeEmergencyContacts(EmergencyContacts.toString());

                        startActivity(new Intent(new Intent(splashscreen.this,MainActivity.class)));
                        finish();


                    }
                    else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString("message"), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("User info", " Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                pDialog.dismiss();


                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//This indicates that the reuest has either time out or there is no connection

                } else if (error instanceof AuthFailureError) {
// Error indicating that there was an Authentication Failure while performing the request
                } else if (error instanceof ServerError) {
//Indicates that the server responded with a error response
                } else if (error instanceof NetworkError) {
//Indicates that there was network error while performing the request
                } else if (error instanceof ParseError) {
// Indicates that the server response could not be parsed
                }

            }
        }) {

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                String credentials ="admin"+":"+"1234";
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(),Base64.DEFAULT);
                HashMap<String,String> header = new HashMap<>();
                header.put("Content-Type","application/x-www-from-urlencoded; charset=utf-8");
                header.put("Authorization","Basic "+base64EncodedCredentials);
                return header;
            }
            public String getBodyContentType(){
                return "application/x-www-from-urlencoded; charset=utf-8";
            }
            public int getMethod(){
                return Method.GET;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance(this).addToRequestQueue(strReq);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        pDialog.show();
    }

    @Override
    public void hideProgress() {
        pDialog.dismiss();
    }

    @Override
    public void showPickmeResult(LoginResponseModel loginResponseModel) {

    }

    @Override
    public void showErrorMessage(String string) {

    }

    @Override
    public void showVerifyOtpResult(LoginResponseModel loginResponseModel) {

    }
}
}