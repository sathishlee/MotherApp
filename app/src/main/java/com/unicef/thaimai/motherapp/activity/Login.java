package com.unicef.thaimai.motherapp.activity;

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
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.helper.apiconstants;
import com.unicef.thaimai.motherapp.volleyservice.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity {

    ProgressDialog pDialog;
    Button btn_login;
    TextView forgot_picme;
    EditText txt_picmeId, txt_otp;
    Intent intent;
    TextInputLayout input_layout_picme_id, input_layout_otp;
    int success;
    ConnectivityManager conMgr;

    private static final String url = apiconstants.URL;

    private static final String TAG = Login.class.getSimpleName();

    String tag_json_obj = "string_req";

    SharedPreferences sharedpreferences;
    Boolean session = false;
    String id, name, picmeId;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    boolean isPickmeAvailable = false;

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

        input_layout_picme_id = (TextInputLayout) findViewById(R.id.input_layout_picme_id);
        input_layout_otp = (TextInputLayout) findViewById(R.id.input_layout_otp);
        input_layout_otp.setVisibility(View.GONE);

        txt_picmeId = (EditText) findViewById(R.id.picme_id);
        txt_otp = (EditText) findViewById(R.id.otp);


        forgot_picme = (TextView) findViewById(R.id.forgot_picme);
//        forgot_picme.setOnClickListener(this);


        btn_login = (Button) findViewById(R.id.btn_login);


        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String picmeId = txt_picmeId.getText().toString();
                String otp = txt_otp.getText().toString();
                if (picmeId.equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Pickme id is empty", Toast.LENGTH_SHORT).show();
                } else {

                        if (isPickmeAvailable) {
                        if (otp.equalsIgnoreCase("")) {
                            isPickmeAvailable = false;
                            Toast.makeText(getApplicationContext(), "otp is empty", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("verifyOtp", String.valueOf(isPickmeAvailable) + "\t" + otp + "\t" + picmeId);
                            verifyOtp(otp, picmeId);
                        }

                    } else {
                        Log.e("checkLogin", String.valueOf(isPickmeAvailable) + "\t" + picmeId);

                        checkLogin(picmeId);

                    }
                }

            }
        });


    }

    private void verifyOtp(String otp, String picmeId) {


        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.GET, url + picmeId + "/" + otp, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Verify Otp " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt("status");
                    if (success == 1) {
                        input_layout_otp.setVisibility(View.VISIBLE);
                        isPickmeAvailable = true;
                    } else {
                        isPickmeAvailable = true;

                        Toast.makeText(getApplicationContext(), jObj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

              /*  try {
                    JSONObject jObj = null;
                    try {
                        jObj = new JSONObject(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    success = jObj.getInt(Constants.TAG_SUCCESS);

                    // Check for error in json
                    if (success == 1) {

                        String picmeId = jObj.getString(Constants.PICME_ID);
                        String Otp = jObj.getString(Constants.OTP);

                        Log.e("User Found!", jObj.toString());

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(session_status, true);
                        editor.putString(Constants.TAG_USERNAME, name);
                        editor.putString(Constants.PICME_ID, picmeId);
                        editor.commit();

                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.putExtra(Constants.TAG_USERNAME, name);
                        intent.putExtra(Constants.PICME_ID, picmeId);
                        finish();
                        startActivity(intent);
                        input_layout_otp.setVisibility(View.VISIBLE);
                    }
                    else {
//                        Toast.makeText(getApplicationContext(),
//                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }*/

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.toString(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

//            @Override
//            protected Map<String, String> getParams() {
//                // Posting parameters to login url
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("name", name);
//                params.put("picmeId", picmeId);
//
//                return params;
//            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = "admin" + ":" + "1234";
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                headers.put("Authorization", "Basic " + base64EncodedCredentials);
//                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=utf-8";
            }

            @Override
            public int getMethod() {
                return Method.GET;
            }


        };

        // Adding request to request queue
        VolleySingleton.getInstance(this).addToRequestQueue(strReq);


    }

    private void checkLogin(final String picmeId) {

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.GET, url + picmeId, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt("status");
                    if (success == 1) {
                        input_layout_otp.setVisibility(View.VISIBLE);
                        isPickmeAvailable = true;
                    } else {
                        isPickmeAvailable = true;

                        Toast.makeText(getApplicationContext(), jObj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

              /*  try {
                    JSONObject jObj = null;
                    try {
                        jObj = new JSONObject(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    success = jObj.getInt(Constants.TAG_SUCCESS);

                    // Check for error in json
                    if (success == 1) {

                        String picmeId = jObj.getString(Constants.PICME_ID);
                        String Otp = jObj.getString(Constants.OTP);

                        Log.e("User Found!", jObj.toString());

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(session_status, true);
                        editor.putString(Constants.TAG_USERNAME, name);
                        editor.putString(Constants.PICME_ID, picmeId);
                        editor.commit();

                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.putExtra(Constants.TAG_USERNAME, name);
                        intent.putExtra(Constants.PICME_ID, picmeId);
                        finish();
                        startActivity(intent);
                        input_layout_otp.setVisibility(View.VISIBLE);
                    }
                    else {
//                        Toast.makeText(getApplicationContext(),
//                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }*/

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.toString(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

//            @Override
//            protected Map<String, String> getParams() {
//                // Posting parameters to login url
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("name", name);
//                params.put("picmeId", picmeId);
//
//                return params;
//            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = "admin" + ":" + "1234";
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                headers.put("Authorization", "Basic " + base64EncodedCredentials);
//                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=utf-8";
            }

            @Override
            public int getMethod() {
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


}
