package com.unicef.thaimai.motherapp.Presenter;

import android.app.Activity;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.interactor.ProfileInteractor;
import com.unicef.thaimai.motherapp.view.ProfileView;
import com.unicef.thaimai.motherapp.volleyservice.VolleySingleton;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Suthishan on 20/1/2018.
 */

public class ProfilePresenter implements ProfileInteractor {
    private ProfileView view;
    private Activity activity;

    public ProfilePresenter(ProfileView view, Activity activity) {
        this.view = view;
        this.activity = activity;
    }

    @Override
    public void getMotherProfile(final String mid, final String picmeId) {
        view.showProgress();
        String url = Apiconstants.BASE_URL+Apiconstants.POST_EDIT_PROFILE;
        Log.d("Url--->", url);
        Log.d("mid", mid);
        Log.d("picmeId", picmeId);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                view.hideProgress();
                view.successViewProfile(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.hideProgress();
                view.errorViewProfile(error.toString());

            }
        }){



            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = "admin" + ":" + "1234";
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
                HashMap<String, String> header = new HashMap<>();
                //                header.put("Content-Type", "application/x-www-from-urlencoded; charset=utf-8");
                header.put("Authorization", "Basic " + base64EncodedCredentials);
                Log.d("Credentials ", "Basic " + base64EncodedCredentials.toString());

                return header;
            }

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("mid", mid);
                params.put("picmeId", picmeId);


                Log.d("params--->", "Edit Profile view" + params.toString());

                return params;
            }

            //            public String getBodyContentType() {
            //                return "application/x-www-from-urlencoded; charset=utf-8";
            //            }

            public int getMethod() {
                return Method.POST;
            }
        };
        VolleySingleton.getInstance(activity).addToRequestQueue(stringRequest);
    }

    @Override
    public void sendMotherProfile(final String mid, final String picmeId, final String number, final String hus_number) {
        view.showProgress();
        String url = Apiconstants.BASE_URL+Apiconstants.UPDATE_PROFILE;
        Log.d("Url--->", url);
        Log.d("mid", mid);
        Log.d("picmeId", picmeId);
        Log.d("number", number);
        Log.d("hus_number", picmeId);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                view.hideProgress();
                view.successupdateProfile(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.hideProgress();
                view.errorUpdateProfile(error.toString());

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = "admin" + ":" + "1234";
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
                HashMap<String, String> header = new HashMap<>();
                //                header.put("Content-Type", "application/x-www-from-urlencoded; charset=utf-8");
                header.put("Authorization", "Basic " + base64EncodedCredentials);
                Log.d("Credentials ", "Basic " + base64EncodedCredentials.toString());

                return header;
            }

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("mid", mid);
                params.put("mPicmeId", picmeId);
                params.put("mMotherMobile", number);
                params.put("mHusbandMobile", hus_number);


                Log.d("params--->", "Edit Profile view" + params.toString());

                return params;
            }

            //            public String getBodyContentType() {
            //                return "application/x-www-from-urlencoded; charset=utf-8";
            //            }

            public int getMethod() {
                return Method.POST;
            }
        };
        VolleySingleton.getInstance(activity).addToRequestQueue(stringRequest);
    }
}
