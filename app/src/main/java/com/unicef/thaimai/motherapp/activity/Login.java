package com.unicef.thaimai.motherapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
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
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unicef.thaimai.motherapp.Presenter.LoginPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.helper.Constants;
import com.unicef.thaimai.motherapp.model.responsemodel.LoginResponseModel;
import com.unicef.thaimai.motherapp.view.LoginViews;
import com.unicef.thaimai.motherapp.volleyservice.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity implements LoginViews {

    ProgressDialog pDialog;
    Button btn_login;
    TextView forgot_picme, worng_picme;
    EditText txt_picmeId;
    EditText otp;
    Intent intent;
    TextInputLayout input_layout_picme_id, input_layout_otp;
    Activity mActivity;
    ConnectivityManager conMgr;

//    private static final String url = Apiconstants.URL + "login.php";

    private static final String TAG = Login.class.getSimpleName();



    String tag_json_obj = "json_obj_req";

    SharedPreferences sharedpreferences;
    Boolean session = false;
    String id, name, picmeId;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    LoginPresenter loginPresenter;

    boolean isPickmeAvailable=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mActivity=this;




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
        input_layout_otp = (TextInputLayout) findViewById(R.id.input_layout_otp);
        input_layout_otp.setVisibility(View.GONE);

        txt_picmeId = (EditText) findViewById(R.id.picme_id);
        otp = (EditText) findViewById(R.id.otp);


        forgot_picme = (TextView) findViewById(R.id.forgot_picme);
        worng_picme = (TextView) findViewById(R.id.worng_picme);
        forgot_picme.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent intent = new Intent(Login.this, ForgotPicme.class);
                startActivity(intent);
            }
        });



        loginPresenter = new LoginPresenter(mActivity,this);
//        loginPresenter = new LoginPresenter(mActivity,this);


        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String picmeId = txt_picmeId.getText().toString();
                String Otp = otp.getText().toString();

//                Toast.makeText(getApplicationContext(),picmeId,Toast.LENGTH_SHORT).show();


                if (picmeId.equalsIgnoreCase("")){
                    input_layout_picme_id.setError("Pickme ID is Empty");
                }
//                if (picmeId.length()==12){
//                    input_layout_picme_id.setError("Enter Correct Picme ID");
//                }
                else{
                    if (isPickmeAvailable){
                        if (Otp.equalsIgnoreCase("")){
                            input_layout_otp.setError("Enter Otp");
                            isPickmeAvailable=false;
                        }else{
//                            checkLogin(picmeId,Otp);
                            verifyOtp(picmeId,Otp);
//                            loginPresenter.verifyOtp(picmeId,Otp);
                        }
                    }else{

                        checkLogin(picmeId,"");

//                        loginPresenter.checkPickmeId(picmeId);

                    }
                }

            }
        });


    }

    private void verifyOtp(String picmeId, String otp) {

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        showDialog();
        String url=null;

        url = Apiconstants.BASE_URL+Apiconstants.LOG_IN_CHECK_PIKME+picmeId+"/"+otp;

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

                        Intent intent = new Intent(Login.this, Register.class);
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

    private void checkLogin(final String picmeId, String otp) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        showDialog();
        String url=null;
            if (!isPickmeAvailable){

                url = Apiconstants.BASE_URL+Apiconstants.LOG_IN_CHECK_PIKME+picmeId;

            }else{
                url = Apiconstants.BASE_URL+Apiconstants.LOG_IN_CHECK_PIKME+picmeId+"/"+otp;

            }
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
                        String picmeId = jObj.getString(Constants.PICME_ID);
                        String Otp = jObj.getString(Constants.OTP);

                        txt_picmeId.setEnabled(false);


                        worng_picme.setVisibility(View.VISIBLE);
                        worng_picme.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v){
                                txt_picmeId.setEnabled(true);
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
}
