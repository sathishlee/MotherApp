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
import com.unicef.thaimai.motherapp.interactor.SosAlertInteractor;
import com.unicef.thaimai.motherapp.view.SosAlertViews;
import com.unicef.thaimai.motherapp.volleyservice.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sathish on 3/16/2018.
 */

public class SosAlertPresenter implements SosAlertInteractor {

    private SosAlertViews view;
    private Activity activity;

    public SosAlertPresenter(SosAlertViews view, Activity activity) {
        this.view = view;
        this.activity = activity;
    }

    @Override
    public void postSosAlert(final String pickmeid, final String mid, final String vhnId, final String phcid, final String awwid, final String tokenId) {
        String url = Apiconstants.BASE_URL + Apiconstants.POST_SOS_ALERT;
        Log.d("Log in check Url--->", url);
        Log.d("picmeId--->", pickmeid);
        Log.d("phcId--->", phcid);
        Log.d("mid--->", mid);
        Log.d("awwId--->", awwid);
        Log.d("vhnId--->", vhnId);
        Log.d("tokenId--->",tokenId);
        view.showProgress();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                view.hideProgress();
                view.showPickmeResult(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.hideProgress();
                view.showPickmeResult(error.toString());
            }
        }){



            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("picmeId", pickmeid);
                params.put("phcId", phcid);
                params.put("mid", mid);
                params.put("awwId", awwid);
                params.put("vhnId", vhnId);
                params.put("tokenId",tokenId);

                Log.d("SOS params--->", params.toString());

                return params;
            }

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
