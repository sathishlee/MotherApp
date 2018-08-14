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
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.interactor.GetVisitReportsInteractor;
import com.unicef.thaimai.motherapp.view.GetAllReportsViews;
import com.unicef.thaimai.motherapp.volleyservice.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class GetVisitReportsPresenter implements GetVisitReportsInteractor {

    Activity activity;
    GetAllReportsViews getAllReportsViews;

    public GetVisitReportsPresenter(Activity activity, GetAllReportsViews getAllReportsViews){
        this.activity = activity;
        this.getAllReportsViews = getAllReportsViews;
    }


    @Override
    public void getallVisitReports(final String picmeId, final String mid) {
        getAllReportsViews.showProgress();
        String url = Apiconstants.BASE_URL + Apiconstants.GET_ALL_VISIT_REPORTS;

        Log.d("Url--->",url);
        Log.d("Picme--->",picmeId);
        Log.d("Mid--->",mid);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getAllReportsViews.hideProgress();
                getAllReportsViews.getVisitReportsSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getAllReportsViews.hideProgress();
                getAllReportsViews.getVisitReportsFailure(error.toString());
            }
        }){


            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("picmeId",picmeId);
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
    public void getallPNVisitReports(final String picmeId, final String mid) {
        getAllReportsViews.showProgress();
        String url = Apiconstants.BASE_URL + Apiconstants.GET_PN_VISIT_REPORTS;

        Log.d("Url--->",url);
        Log.d("Picme--->",picmeId);
        Log.d("Mid--->",mid);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getAllReportsViews.hideProgress();
                getAllReportsViews.getVisitReportsSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getAllReportsViews.hideProgress();
                getAllReportsViews.getVisitReportsFailure(error.toString());
            }
        }){


            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("picmeId",picmeId);
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
