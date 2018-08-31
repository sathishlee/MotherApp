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
import com.unicef.thaimai.motherapp.interactor.RegisterInteractor;
import com.unicef.thaimai.motherapp.view.RegisterViews;
import com.unicef.thaimai.motherapp.volleyservice.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class RegisterPresenter implements RegisterInteractor {

    private Activity activity;
    private RegisterViews registerViews;

    public RegisterPresenter(Activity activity, RegisterViews registerViews){
        this.activity = activity;
        this.registerViews = registerViews;
    }
    @Override
    public void getDistrictJson() {
        registerViews.showProgress();
        final String url = Apiconstants.BASE_URL + Apiconstants.GET_DISTRICT_BLOCK_PHC_DATA;

        Log.e("Log in check Url--->", url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("District", response);
                registerViews.hideProgress();
                registerViews.getAllDistrictDataSuccess(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                registerViews.hideProgress();
                registerViews.getAllDistrictDataFailure(error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = "admin" + ":" + "1234";
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
                HashMap<String, String> header = new HashMap<>();
                header.put("Authorization", "Basic " + base64EncodedCredentials);
                Log.d("Credentials ", "Basic " + base64EncodedCredentials.toString());
                return header;
            }
            public int getMethod() {
                return Method.POST;
            }
        };
        VolleySingleton.getInstance(activity).addToRequestQueue(stringRequest);
        VolleySingleton.getInstance(activity).getRequestQueue().getCache().remove(url);
    }

    @Override
    public void getBlockJson(final String selDistrict) {
        registerViews.showProgress();
        final String url = Apiconstants.BASE_URL + Apiconstants.GET_DISTRICT_BLOCK_PHC_DATA;

        Log.e("Log in check Url--->", url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Block", response);
                registerViews.hideProgress();
                registerViews.getAllBlockDataSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                registerViews.hideProgress();
                registerViews.getAllBlockDataFailure(error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = "admin" + ":" + "1234";
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
                HashMap<String, String> header = new HashMap<>();
                header.put("Authorization", "Basic " + base64EncodedCredentials);
                Log.d("Credentials ", "Basic " + base64EncodedCredentials.toString());
                return header;
            }
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("selDistrict", selDistrict);
                Log.d("params--->", "Select District " + params.toString());

                return params;
            }
            public int getMethod() {
                return Method.POST;
            }
        };
        VolleySingleton.getInstance(activity).addToRequestQueue(stringRequest);
        VolleySingleton.getInstance(activity).getRequestQueue().getCache().remove(url);
    }

    @Override
    public void getPHCJson(final String selDistrict, final String selBlock) {
        registerViews.showProgress();
        final String url = Apiconstants.BASE_URL + Apiconstants.GET_DISTRICT_BLOCK_PHC_DATA;

        Log.e("Log in check Url--->", url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Block", response);
                registerViews.hideProgress();
                registerViews.getAllPHCDataSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                registerViews.hideProgress();
                registerViews.getAllPHCDataFailure(error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = "admin" + ":" + "1234";
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
                HashMap<String, String> header = new HashMap<>();
                header.put("Authorization", "Basic " + base64EncodedCredentials);
                Log.d("Credentials ", "Basic " + base64EncodedCredentials.toString());
                return header;
            }
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("selDistrict", selDistrict);
                params.put("selBlock", selBlock);
                Log.d("params--->", "Select District " + params.toString());

                return params;
            }
            public int getMethod() {
                return Method.POST;
            }
        };
        VolleySingleton.getInstance(activity).addToRequestQueue(stringRequest);
        VolleySingleton.getInstance(activity).getRequestQueue().getCache().remove(url);
    }

    @Override
    public void submitRegisterMother(final String deviceToken, final String mLat, final String mLong, final String name, final String dob, final String district,
                                     final String block, final String phc, final String primaryNumber,
                                     final String secNumber,final String husName) {
        registerViews.showProgress();
        final String url = Apiconstants.BASE_URL + Apiconstants.REGISTER_URL;

        Log.e("Log in check Url--->", url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Block", response);
                registerViews.hideProgress();
                registerViews.getAllPHCDataSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                registerViews.hideProgress();
                registerViews.getAllPHCDataFailure(error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = "admin" + ":" + "1234";
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
                HashMap<String, String> header = new HashMap<>();
                header.put("Authorization", "Basic " + base64EncodedCredentials);
                Log.d("Credentials ", "Basic " + base64EncodedCredentials.toString());
                return header;
            }
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("deviceToken", deviceToken);
                params.put("mlat", mLat);
                params.put("mlong", mLong);
                params.put("mName", name);
                params.put("mDOB", dob);
                params.put("dist", district);
                params.put("block", block);
                params.put("phc", phc);
                params.put("mMobileNumber", primaryNumber);
                params.put("mHusbandMobile", secNumber);
                params.put("mHusbandName", husName);
                Log.d("params--->", "Values " + params.toString());

                return params;
            }
            public int getMethod() {
                return Method.POST;
            }
        };
        VolleySingleton.getInstance(activity).addToRequestQueue(stringRequest);
        VolleySingleton.getInstance(activity).getRequestQueue().getCache().remove(url);
    }
}
