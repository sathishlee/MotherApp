package com.unicef.thaimai.motherapp.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.activity.Login;
import com.unicef.thaimai.motherapp.activity.MainActivity;
import com.unicef.thaimai.motherapp.activity.splashscreen;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.activity.profile;
import com.unicef.thaimai.motherapp.volleyservice.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class home extends Fragment {

    Button btn_logout;
    TextView txt_username, picme_id, dob;
//    TextView g_week,weight, lmpdate,edddate,risk;
    String id, name, picmeId, regDob;

    SharedPreferences sharedpreferences;
    // Context
    Context _context;
    CardView profile, next_visit;


    private static final String TAG_ID = "id";
    private static final String TAG_USERNAME = "name";
    private static final String PICME_ID = "picmeId";
    private static final String AGE = "Age";


    PreferenceData preferenceData;
    SharedPreferences.Editor editor;

    ProgressDialog pDialog;

    public static home newInstance()
    {
        home fragment = new home();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
         View view = inflater.inflate(R.layout.fragment_home, container, false);
        preferenceData = new PreferenceData(getActivity());
        editor = getActivity().getSharedPreferences(AppConstants.PREF_NAME, MODE_PRIVATE).edit();
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");


        getUserInfo("100000000013");



        txt_username = (TextView) view.findViewById(R.id.txt_username);
        picme_id = (TextView) view.findViewById(R.id.txt_picme_id);
        dob = (TextView) view.findViewById(R.id.txt_age);
       /* g_week = (TextView) view.findViewById(R.id.txt_g_age);
        weight = (TextView) view.findViewById(R.id.txt_weight);
        lmpdate = (TextView) view.findViewById(R.id.txt_lmp_date);
        edddate = (TextView) view.findViewById(R.id.txt_edd_date);
        risk = (TextView) view.findViewById(R.id.txt_risk);
*/
        profile = (CardView) view.findViewById(R.id.android_card_view_example);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), profile.class);
                getActivity().finish();
                startActivity(intent);

            }
        });

//        getSharedValues();

//        id = getActivity().getIntent().getStringExtra(TAG_ID);
//        name = getActivity().getIntent().getStringExtra(TAG_USERNAME);
//        picmeId = getActivity().getIntent().getStringExtra(PICME_ID);
//        regDob = getActivity().getIntent().getStringExtra(AGE);
        id = getActivity().getIntent().getStringExtra(TAG_ID);
        name = getActivity().getIntent().getStringExtra(TAG_USERNAME);
        picmeId = getActivity().getIntent().getStringExtra(PICME_ID);
        regDob = getActivity().getIntent().getStringExtra(AGE);

        return view;

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
//                        JSONObject userinfo = jObj.getJSONObject("userinfo");
//                        txt_username.setText(userinfo.getString("motherName"));
//                        picme_id.setText(userinfo.getString("picmeNO"));
//                        dob.setText(userinfo.getString("Age"));
//                        Log.e("motherName---->",userinfo.getString("motherName"));
//                        Log.e("Age---->",userinfo.getString("Age"));
//                        Log.e("picmeNO---->",userinfo.getString("picmeNO"));

//                        JSONObject usermedical = jObj.getJSONObject("usermedical");
//                        g_week.setText("");
//                        weight.setText(usermedical.getString("weight"));
//                        lmpdate.setText(usermedical.getString("LMP"));
//                        edddate.setText(usermedical.getString("EDD"));
//                        JSONArray EmergencyContacts = jObj.getJSONArray("EmergencyContacts");

//                        int status = jObj.getInt("status");
                        // Check for error in json
//                        if (status == 1) {
//                            preferenceData.storeUserInfo(userinfo.toString());
//                            preferenceData.storeusermedical(usermedical.toString());
//                            preferenceData.storeEmergencyContacts(EmergencyContacts.toString());
//
//                            startActivity(new Intent(new Intent(splashscreen.this,MainActivity.class)));
//                            finish();
//
//
//                        }
//                        else {
//                            Toast.makeText(getApplicationContext(),
//                                    jObj.getString("message"), Toast.LENGTH_LONG).show();
//
//                        }
                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("User info", " Error: " + error.getMessage());
                    Toast.makeText(getActivity(),
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
            VolleySingleton.getInstance(getActivity()).addToRequestQueue(strReq);



    }

   /* private void getSharedValues() {
            String str_json_user_info = preferenceData.getUserInfo();
        Log.e("str_json_user_info---->",str_json_user_info);
        String str_json_UserMedical = preferenceData.getUserMedical();
        Log.e("UserMedical---->",str_json_UserMedical);
        String str_json_EmergencyContacts= preferenceData.getEmergencyContacts();
        Log.e("EmergencyContacts---->",str_json_EmergencyContacts);

    }*/


}
