package com.unicef.thaimai.motherapp.Presenter;

import android.app.DownloadManager;
import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.interactor.GetVisitHealthRecordsInteractor;
import com.unicef.thaimai.motherapp.view.GetVisitHelthRecordsViews;
import com.unicef.thaimai.motherapp.volleyservice.VolleySingleton;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sathish on 3/9/2018.
 */

public class GetVisitHealthRecordsPresenter implements GetVisitHealthRecordsInteractor {
    Context mContext;
    GetVisitHelthRecordsViews view;

    public GetVisitHealthRecordsPresenter(Context mContext, GetVisitHelthRecordsViews view) {
        this.mContext = mContext;
        this.view = view;
    }

    @Override
    public void getAllVistHeathRecord(String strUrl,final String strPicmeId, final String mid) {
        view.showProgress();
//        String url = Apiconstants.BASE_URL + Apiconstants.POST_VIST_HEALTH_RECORD;
//        String url = Apiconstants.BASE_URL + Apiconstants.POST_VIST_HEALTH_RECORD_PICME;
        String url = Apiconstants.BASE_URL + strUrl;

        Log.d("Log in check Url--->",url);
        Log.d("Picme--->",strPicmeId);
        Log.d("Mid--->",mid);
        StringRequest strReq =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                view.hideProgress();
                view.getVisitHealthRecordsSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.hideProgress();
                view.getVisitHealthRecordsFailiur(error.toString());
            }
        }){


            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("picmeId",strPicmeId);
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
        VolleySingleton.getInstance(mContext).addToRequestQueue(strReq);
    }

    @Override
    public void getPN_HBNC_VisitRecord(String strUrl, final String strPicmeId, final String mid) {

        view.showProgress();
        String url = Apiconstants.BASE_URL + strUrl;

        Log.d("Log in check Url--->",url);
        Log.d("Url--->",strPicmeId);
        Log.d("Dob--->",mid);
        StringRequest strReq =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("PN_HBNC succes response",response);
                view.hideProgress();
                view.getPNHBNCVisitRecordsSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.hideProgress();
                Log.d("PN_HBNC erorr response",error.toString());

                view.getPNHBNCVisitRecordsFailiur(error.toString());
            }
        }){


            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("picmeId",strPicmeId);
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
        VolleySingleton.getInstance(mContext).addToRequestQueue(strReq);

    }


}
