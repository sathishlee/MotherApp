package com.unicef.thaimai.motherapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
//import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
//import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;
import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.LoginPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.model.responsemodel.LoginResponseModel;
import com.unicef.thaimai.motherapp.view.LoginViews;
import com.unicef.thaimai.motherapp.volleyservice.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Login extends AppCompatActivity implements LoginViews {

    ProgressDialog pDialog;
    Button btn_login;
    TextView forgot_picme, worng_picme;
    EditText txt_picmeId;
    EditText txt_otp;
    Intent intent;
    TextInputLayout input_layout_picme_id, input_layout_otp;
    Activity mActivity;
    ConnectivityManager conMgr;
    String url=null;
//    private static final String url = Apiconstants.URL + "login.php";

    private static final String TAG = Login.class.getSimpleName();



    String tag_json_obj = "json_obj_req";

    SharedPreferences sharedpreferences;
    Boolean session = false;
    String id, name, picmeId;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    LoginPresenter loginPresenter;
    PreferenceData preferenceData;
//    private SmsVerifyCatcher smsVerifyCatcher;

    boolean isPickmeAvailable=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mActivity=this;
         preferenceData = new PreferenceData(mActivity);




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

        input_layout_picme_id = (TextInputLayout) findViewById(R.id.input_layout_picme_id);

        input_layout_otp.setVisibility(View.GONE);

        txt_picmeId = (EditText) findViewById(R.id.picme_id);

//        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
//            @Override
//            public void onSmsCatch(String message) {
//                String code = parseCode(message);//Parse verification code
//                otp.setText(code);//set code in edit text
//                //then you can send verification code to server
//            }
//        });

        //set phone number filter if needed
//        smsVerifyCatcher.setPhoneNumberFilter("TX-SATVAT");


        forgot_picme = (TextView) findViewById(R.id.forgot_picme);

        forgot_picme.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent intent = new Intent(Login.this, ForgotPicme.class);
                startActivity(intent);
            }
        });



//        loginPresenter = new LoginPresenter(mActivity,this);


        btn_login = (Button) findViewById(R.id.btn_submit);

        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String picmeId = txt_picmeId.getText().toString();
                String otp = txt_otp.getText().toString();



                if (picmeId.equalsIgnoreCase("")){
                    input_layout_picme_id.setError("Pickme ID is Empty");
                }
                if (picmeId.length()<12){
                    input_layout_picme_id.setError("Enter Correct Picme ID");
                }    if (picmeId.length()>12){
                    input_layout_picme_id.setError("Enter Correct Picme ID");
                }
                else{
                    if (isPickmeAvailable){
                        if (otp.equalsIgnoreCase("")){
                            input_layout_otp.setError("Enter Otp");
                            isPickmeAvailable=false;
                        }else{
//                            checkLogin(picmeId,Otp);

                            url = Apiconstants.BASE_URL+Apiconstants.LOG_IN_CHECK_PIKME+picmeId+"/"+ otp;

                            verifyOtp(url);
//                            verifyOtp(picmeId,Otp);
//                            loginPresenter.verifyOtp(picmeId,Otp);
                        }
                    }else{
                        url = Apiconstants.BASE_URL+Apiconstants.LOG_IN_CHECK_PIKME+picmeId;
//                        checkLogin(picmeId,"");
                        checkLogin(url);

//                        loginPresenter.checkPickmeId(picmeId);

                    }
                }
            }
        });
    }

    private void verifyOtp(String url) {

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        showDialog();


        Log.e("verifyOtp   url",url);

        StringRequest strReq = new StringRequest(Request.Method.POST,url , new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
//                    success = jObj.getInt(Constants.TAG_SUCCESS);
                    int status = jObj.getInt("status");
                    // Check for error in json
                    if (status == 1) {

                        Toast.makeText(getApplicationContext(),
                                jObj.getString("message"), Toast.LENGTH_LONG).show();
                        preferenceData.setLogin(true);
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);

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
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();


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

    private void checkLogin(final String url) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        showDialog();

        /*    if (!isPickmeAvailable){

                url = Apiconstants.BASE_URL+Apiconstants.LOG_IN_CHECK_PIKME+picmeId;

            }else{
                url = Apiconstants.BASE_URL+Apiconstants.LOG_IN_CHECK_PIKME+picmeId+"/"+txt_otp;

            }*/
                    Log.e("url",url);

        StringRequest strReq = new StringRequest(Request.Method.POST,url , new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
//                    success = jObj.getInt(Constants.TAG_SUCCESS);
                    int status = jObj.getInt("status");
                    // Check for error in json
                    if (status == 1) {
                        input_layout_otp.setVisibility(View.VISIBLE);
                        isPickmeAvailable = true;
                        txt_picmeId.setEnabled(false);
//                        String picmeId = jObj.getString(Constants.PICME_ID);
//                        String Otp = jObj.getString(Constants.OTP);
                        txt_picmeId.setEnabled(false);
//                        String picmeId = jObj.getString(Constants.PICME_ID);
//                        String Otp = jObj.getString(Constants.OTP);
                        input_layout_picme_id.setEnabled(false);
                        input_layout_picme_id.setFocusable(false);
                        input_layout_picme_id.setBackgroundColor(Color.TRANSPARENT);
                        forgot_picme.setVisibility(View.GONE);
                        worng_picme.setVisibility(View.VISIBLE);
                        worng_picme.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v){
                                input_layout_picme_id.setFocusable(true);
                                input_layout_picme_id.setEnabled(true);
                                worng_picme.setVisibility(View.GONE);
                                input_layout_otp.setVisibility(View.GONE);
                                forgot_picme.setVisibility(View.VISIBLE);
                            }
                        });

                        Log.e("User Found!", jObj.toString());
                        Toast.makeText(getApplicationContext(),
                                jObj.getString("message"), Toast.LENGTH_LONG).show();
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
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

//            @Override
//            protected Map<String, String> getParams() {
//                // Posting parameters to login url
//                Map<String, String> params = new HashMap<String, String>();
//
//
//                return params;
//            }
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


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    @Override
    public void showProgress() {

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        showProgress();
    }

    @Override
    public void hideProgress() {
            pDialog.dismiss();
    }

    @Override
    public void showPickmeResult(LoginResponseModel loginResponseModel) {
        Toast.makeText(getApplicationContext(),loginResponseModel.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessage(String string) {

    }

    @Override
    public void showVerifyOtpResult(LoginResponseModel loginResponseModel) {

    }

    private String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{4}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }

    @Override
    protected void onStart() {
        super.onStart();
//        smsVerifyCatcher.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        smsVerifyCatcher.onStop();
    }

    /**
     * need for Android 6 real time permissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
