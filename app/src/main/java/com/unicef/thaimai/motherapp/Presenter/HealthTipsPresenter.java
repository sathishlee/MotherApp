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
import com.unicef.thaimai.motherapp.interactor.HealthTipsInteractor;
import com.unicef.thaimai.motherapp.view.HealthTipsViews;
import com.unicef.thaimai.motherapp.volleyservice.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class HealthTipsPresenter implements HealthTipsInteractor {

    private HealthTipsViews healthTipsViews;
    private Activity activity;

    public HealthTipsPresenter(Activity activity, HealthTipsViews healthTipsViews){
        this.healthTipsViews = healthTipsViews;
        this.activity = activity;
    }

    @Override
    public void getHealthTipsVideo(final String picmeId) {

        healthTipsViews.showProgress();
        String url = Apiconstants.BASE_URL + Apiconstants.HEALTH_TIPS_VIDEO;

        Log.d("PicmeID----->",picmeId);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                healthTipsViews.hideProgress();
                Log.d("Video Response--->", response);
                healthTipsViews.getHealthTipsVideoSuccess(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                healthTipsViews.hideProgress();
                Log.d("Error Response",error.toString());
                healthTipsViews.getHealthTipsVideoFailure(error.toString());

            }
        })

        {
            @Override
            protected Map<String, String> getParams(){

                Map<String, String> params = new HashMap<>();
                params.put("picmeId",picmeId);
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
        VolleySingleton.getInstance(activity).addToRequestQueue(request);
        VolleySingleton.getInstance(activity).getRequestQueue().getCache().remove(url);

    }

    @Override
    public void healthTipsMessage(final String mid) {
        healthTipsViews.showProgress();
        String url = Apiconstants.BASE_URL + Apiconstants.HEALTH_TIPS_MESSAGE_DAILY;

        Log.d("mid----->",mid);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                healthTipsViews.hideProgress();
                Log.d("Video Response--->", response);
                healthTipsViews.healthTipsMessageSuccess(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                healthTipsViews.hideProgress();
                Log.d("Error Response",error.toString());
                healthTipsViews.healthTipsMessageFailure(error.toString());

            }
        })

        {
            @Override
            protected Map<String, String> getParams(){

                Map<String, String> params = new HashMap<>();
                params.put("mid",mid);
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
        VolleySingleton.getInstance(activity).addToRequestQueue(request);
        VolleySingleton.getInstance(activity).getRequestQueue().getCache().remove(url);

    }

    @Override
    public void healthTipsImageWeekly(final String mid) {
        healthTipsViews.showProgress();
        String url = Apiconstants.BASE_URL + Apiconstants.HEALTH_TIPS_IMAGE_WEEKLY;

        Log.d("MID----->",mid);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                healthTipsViews.hideProgress();
                Log.d("Video Response--->", response);
                healthTipsViews.getHealthTipsVideoSuccess(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                healthTipsViews.hideProgress();
                Log.d("Error Response",error.toString());
                healthTipsViews.getHealthTipsVideoFailure(error.toString());

            }
        })

        {
            @Override
            protected Map<String, String> getParams(){

                Map<String, String> params = new HashMap<>();
                params.put("mid",mid);
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
        VolleySingleton.getInstance(activity).addToRequestQueue(request);
        VolleySingleton.getInstance(activity).getRequestQueue().getCache().remove(url);
    }
}
