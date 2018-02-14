package com.unicef.thaimai.motherapp.activity;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.app.AppController;
import com.unicef.thaimai.motherapp.helper.apiconstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Register extends AppCompatActivity {

    ProgressDialog pDialog;

    Button btn_register, btn_login;

   private EditText user_name, picme_id, user_dob, mobile, alternative_number, alternative_number1;

    private TextInputLayout input_layout_name,input_layout_picme_id, input_layout_dob,
            input_layout_mobile_number, input_layout_alternative_number, input_layout_alternative_number1;

    Locale mylocale;
    TextView tam,eng;

    Intent intent;

    Calendar mCurrentDate;
    int day, month, year;

    int success;
    ConnectivityManager conMgr;

    private static final String url = apiconstants.URL + "Registration/addRegistration";

    private static final String TAG = Register.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";

    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

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


        btn_register = (Button) findViewById(R.id.btn_register);

        input_layout_name =(TextInputLayout) findViewById(R.id.input_layout_name);
        input_layout_picme_id = (TextInputLayout) findViewById(R.id.input_layout_picme_id);
        input_layout_dob = (TextInputLayout) findViewById(R.id.input_layout_dob);
        input_layout_mobile_number =(TextInputLayout)findViewById(R.id.input_layout_mobile_number);
        input_layout_alternative_number = (TextInputLayout) findViewById(R.id.input_layout_alternative_number);
        input_layout_alternative_number1 = (TextInputLayout) findViewById(R.id.input_layout_alternative_number1);

        //EditText Declaration
        user_name = (EditText)findViewById(R.id.name);
        picme_id = (EditText) findViewById(R.id.picme_id);
        user_dob = (EditText) findViewById(R.id.dob);
        mobile = (EditText) findViewById(R.id.mobile_number);
        alternative_number = (EditText) findViewById(R.id.alternative_number);
        alternative_number1 = (EditText) findViewById(R.id.alternative_number1);

        //TextView Language
        tam = (TextView)findViewById(R.id.tam);
        eng = (TextView) findViewById(R.id.eng);
        tam.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                builder.setMessage("Are you Sure do you want to Change Language?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                setLanguage("ta");
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
        });
        
        eng.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                builder.setMessage("Are you Sure do you want to Change Language?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                setLanguage("en");
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
        });

        user_dob = (EditText) findViewById(R.id.dob);
        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);
        month = month + 1;
        user_dob.setText(day + "/" + month + "/" + year);

        user_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Register.this, R.style.DatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                        user_dob.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }

        });


        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                submitForm();
            }
        });

    }

    protected void setLanguage(String language){
        mylocale=new Locale(language);
        Resources resources=getResources();
        DisplayMetrics dm=resources.getDisplayMetrics();
        Configuration conf= resources.getConfiguration();
        conf.locale=mylocale;
        resources.updateConfiguration(conf,dm);
        Intent refreshIntent=new Intent(Register.this,Register.class);
        finish();
        startActivity(refreshIntent);
    }



    private void checkRegister(final String regName, final String picmeid, final String regDob, final String regMobile,
                              final String regAlterMobile, final String regAlterMobile1) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Register ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error mode in json
                    if (success == 1) {

                        Log.e("Successfully Register!", jObj.toString());

                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(Register.this, Login.class);
                        finish();
                        startActivity(intent);

                        user_name.setText("");
                        picme_id.setText("");
                        user_dob.setText("");
                        mobile.setText("");
                        alternative_number.setText("");
                        alternative_number1.setText("");

                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("regName", regName);
                params.put("picmeid", picmeid);
                params.put("regDob",regDob);
                params.put("regMobile",regMobile);
                params.put("regAlterMobile",regAlterMobile);
                params.put("regAlterMobile1",regAlterMobile1);

                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
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
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
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

    private void submitForm(){

        if (!validateName()) {
            return;
        }

        if (!validatePicmeid()) {
            return;
        }

        if (!validateDob()) {
            return;
        }
        if (!validateMobile()) {
            return;
        }
        if (!validateAlterMobile()){
            return;
        }
        if (!validateAlterMobile1()){
            return;
        }

        String regName = user_name.getText().toString();
        String picmeid = picme_id.getText().toString();
        String regMobile = mobile.getText().toString();
        String regDob = user_dob.getText().toString();
        String regAlterMobile = alternative_number.getText().toString();
        String regAlterMobile1 = alternative_number1.getText().toString();

        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            checkRegister(regName, picmeid, regMobile, regDob, regAlterMobile, regAlterMobile1);
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateName(){
        if (user_name.getText().toString().trim().isEmpty()) {
            user_name.setError(getString(R.string.err_msg_name));
            requestFocus(user_name);
            return false;
        } else {
            input_layout_name.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePicmeid(){
        if (picme_id.getText().toString().trim().isEmpty() || picme_id.length() < 12) {
            picme_id.setError(getString(R.string.err_msg_picme));
            requestFocus(picme_id);
            return false;
        } else {
            input_layout_picme_id.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateDob(){
        if (user_dob.getText().toString().trim().isEmpty()) {
            user_dob.setError(getString(R.string.err_msg_dob));
            requestFocus(user_dob);
            return false;
        } else {
            input_layout_dob.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateMobile(){
        if (mobile.getText().toString().trim().isEmpty() || mobile.length() < 10) {
            mobile.setError(getString(R.string.err_msg_mobile));
            requestFocus(mobile);
            return false;
        } else {
            input_layout_mobile_number.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateAlterMobile(){

        if (alternative_number.getText().toString().trim().isEmpty() || alternative_number.length() < 10) {
            alternative_number.setError(getString(R.string.err_msg_alterMobile));
            requestFocus(alternative_number);
            return false;
        } else {
            input_layout_alternative_number.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateAlterMobile1(){
        if (alternative_number1.getText().toString().trim().isEmpty() || alternative_number1.length() < 10) {
            alternative_number1.setError(getString(R.string.err_msg_alterMobile));
            requestFocus(alternative_number1);
            return false;
        } else {
            input_layout_alternative_number1.setErrorEnabled(false);
        }
        return true;
    }



    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
