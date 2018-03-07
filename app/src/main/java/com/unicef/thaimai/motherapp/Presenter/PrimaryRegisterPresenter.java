package com.unicef.thaimai.motherapp.Presenter;

import android.app.Activity;
import android.app.DownloadManager;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unicef.thaimai.motherapp.activity.PrimaryRegister;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.interactor.PrimaryRegisterInteractor;
import com.unicef.thaimai.motherapp.view.PrimaryRegisterViews;
import com.unicef.thaimai.motherapp.volleyservice.VolleySingleton;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by sathish on 3/7/2018.
 */

public class PrimaryRegisterPresenter implements PrimaryRegisterInteractor {
    private PrimaryRegisterViews primaryRegisterViews;
    private Activity activity;

    public PrimaryRegisterPresenter(PrimaryRegisterViews primaryRegisterViews, Activity activity) {
        this.primaryRegisterViews = primaryRegisterViews;
        this.activity = activity;
    }


    @Override
    public void getAllMotherPrimaryRegistration(final  String strPicmeId) {
        String url = Apiconstants.BASE_URL + Apiconstants.POST_PRIMARY_INFO;
        Log.d("Log in check Url--->",url);
        Log.d("picmeId--->",strPicmeId);
primaryRegisterViews.showProgress();
        StringRequest stringRequest =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(PrimaryRegisterPresenter.class.getSimpleName(),"Success response"+response);

                primaryRegisterViews.hideProgress();
                primaryRegisterViews.getAllMotherPrimaryRegisterSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(PrimaryRegisterPresenter.class.getSimpleName(),"Success response"+error.toString());

                primaryRegisterViews.hideProgress();
                primaryRegisterViews.getAllMotherPrimaryRegisterFailiur(error.toString());

            }
        }){


            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("picmeId",strPicmeId);

                Log.d("params--->",params.toString());

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = "admin" + ":" + "1234";
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
                HashMap<String, String> header = new HashMap<>();
//                header.put("Content-Type", "application/x-www-from-urlencoded; charset=utf-8");
                header.put("Authorization", "Basic " + base64EncodedCredentials);
                Log.d("Credentials ","Basic " +base64EncodedCredentials.toString());

                return header;
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
