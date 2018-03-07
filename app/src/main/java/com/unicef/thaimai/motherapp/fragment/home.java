package com.unicef.thaimai.motherapp.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.GetUserInfoPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.activity.profile;
import com.unicef.thaimai.motherapp.model.responsemodel.LoginResponseModel;
import com.unicef.thaimai.motherapp.view.LoginViews;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;


public class home extends Fragment implements LoginViews, View.OnClickListener {

    TextView txt_username, picme_id, txt_age,
            txt_risk,txt_gst_week,txt_weight,txt_next_visit,txt_lmp_date,txt_edd_date,txt_husb_name,txt_husb_mobile_number,txt_vhn_name,txt_vhn_mobile_number,txt_aww_name,txt_aww_mobile_number,txt_phc_name,txt_phc_mobile_number;
    ImageView img_call_husb,img_call_vhn,img_call_aww,img_call_phc;
    String strname, strpicmeId, strage,str_mobile_number_hsbn,str_mobile_number_vhn,str_mobile_number_aww,str_mobile_number_phc;

    CardView profile;




    PreferenceData preferenceData;
    SharedPreferences.Editor editor;
    ProgressDialog pDialog;

    GetUserInfoPresenter getUserInfoPresenter;

    Activity mActivity;

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
         initUI(view);
         onClickListner();








        profile = (CardView) view.findViewById(R.id.android_card_view_example);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), profile.class);
                getActivity().finish();
                startActivity(intent);

            }
        });



        return view;

    }



    private void initUI(View view) {

        preferenceData = new PreferenceData(getActivity());
        editor = getActivity().getSharedPreferences(AppConstants.PREF_NAME, MODE_PRIVATE).edit();

        strname = preferenceData.getMotherName();
        strpicmeId = preferenceData.getPicmeId();
        strage = preferenceData.getMotherAge();

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        getUserInfoPresenter =new GetUserInfoPresenter(getActivity().getApplicationContext(),this);
       Log.e("PICME_ID",preferenceData.getPicmeId());
        getUserInfoPresenter.getUserInfo(strpicmeId);
        getActivity().setTitle("Dashboard");
        txt_username = (TextView) view.findViewById(R.id.txt_username);
        picme_id = (TextView) view.findViewById(R.id.txt_picme_id);
        txt_age = (TextView) view.findViewById(R.id.txt_age);
        txt_gst_week = (TextView) view.findViewById(R.id.txt_gst_week);
        txt_weight = (TextView) view.findViewById(R.id.txt_weight);
        txt_next_visit = (TextView) view.findViewById(R.id.txt_next_visit);
        txt_lmp_date = (TextView) view.findViewById(R.id.txt_lmp_date);
        txt_edd_date = (TextView) view.findViewById(R.id.txt_edd_date);
        txt_risk = (TextView) view.findViewById(R.id.txt_risk);

        txt_husb_name = (TextView) view.findViewById(R.id.txt_husb_name);
        txt_husb_mobile_number = (TextView) view.findViewById(R.id.txt_husb_mobile_number);
        txt_vhn_name = (TextView) view.findViewById(R.id.txt_vhn_name);
        txt_vhn_mobile_number = (TextView) view.findViewById(R.id.txt_vhn_mobile_number);
        txt_aww_name = (TextView) view.findViewById(R.id.txt_aww_name);
        txt_aww_mobile_number = (TextView) view.findViewById(R.id.txt_aww_mobile_number);
        txt_phc_name = (TextView) view.findViewById(R.id.txt_phc_name);
        txt_phc_mobile_number = (TextView) view.findViewById(R.id.txt_phc_mobile_number);

        img_call_husb =(ImageView) view.findViewById(R.id.img_call_husb);
        img_call_vhn =(ImageView) view.findViewById(R.id.img_call_vhn);
        img_call_aww =(ImageView) view.findViewById(R.id.img_call_aww);
        img_call_phc =(ImageView) view.findViewById(R.id.img_call_phc);
        picme_id.setText(strpicmeId);
        txt_username .setText(strname);
        txt_age.setText(strage);

    }

    private void onClickListner() {
        img_call_husb.setOnClickListener(this);
        img_call_vhn.setOnClickListener(this);
        img_call_aww.setOnClickListener(this);
        img_call_phc.setOnClickListener(this);
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
        try {
            JSONObject jObj = new JSONObject(response);
            txt_lmp_date.setText(jObj.getString("mLMP"));
            txt_edd_date.setText(jObj.getString("mLMP"));
            txt_risk.setText(jObj.getString("mRiskStatus"));
            txt_weight.setText(jObj.getString("mWeight"));
            txt_husb_name.setText(jObj.getString("mHusbandName"));
            str_mobile_number_hsbn =jObj.getString("mHusbandMobile");
            txt_husb_mobile_number.setText(str_mobile_number_hsbn);
            txt_vhn_name.setText(jObj.getString("vhnName"));
            str_mobile_number_vhn =jObj.getString("vhnMobile");
            txt_vhn_mobile_number.setText(str_mobile_number_vhn);
            txt_aww_name.setText(jObj.getString("awwName"));
            str_mobile_number_aww = jObj.getString("awwMobile");
            txt_aww_mobile_number.setText(str_mobile_number_aww);
            txt_aww_mobile_number.setText(jObj.getString("phcName"));
            str_mobile_number_phc = jObj.getString("phcMobile");
            txt_phc_mobile_number.setText(str_mobile_number_phc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showErrorMessage(String string) {
        Log.e( "Error", string);

    }

    @Override
    public void showVerifyOtpResult(LoginResponseModel loginResponseModel) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_call_husb:
                makeCall(str_mobile_number_hsbn); break;
            case R.id.img_call_vhn:
                makeCall(str_mobile_number_vhn); break;
            case R.id.img_call_aww:
                makeCall(str_mobile_number_aww); break;
            case R.id.img_call_phc:
                makeCall(str_mobile_number_phc); break;
        }
    }

    public void makeCall(String str_mobile_number) {
        Toast.makeText(getActivity().getApplicationContext(),str_mobile_number,Toast.LENGTH_SHORT).show();

    }


        /*
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
//                        txt_age.setText(userinfo.getString("Age"));
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



    }*/

   /* private void getSharedValues() {
            String str_json_user_info = preferenceData.getUserInfo();
        Log.e("str_json_user_info---->",str_json_user_info);
        String str_json_UserMedical = preferenceData.getUserMedical();
        Log.e("UserMedical---->",str_json_UserMedical);
        String str_json_EmergencyContacts= preferenceData.getEmergencyContacts();
        Log.e("EmergencyContacts---->",str_json_EmergencyContacts);

    }*/


}
