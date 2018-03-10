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
import com.unicef.thaimai.motherapp.interactor.LoginInteractor;
import com.unicef.thaimai.motherapp.view.LoginViews;
import com.unicef.thaimai.motherapp.volleyservice.VolleySingleton;

import java.util.HashMap;
import java.util.Map;


public class LoginPresenter implements LoginInteractor {

    private LoginViews view;
    private Activity activity;

    public LoginPresenter(Activity activity, LoginViews view) {
        this.view = view;
        this.activity = activity;
    }

    @Override
    public void checkPickmeId(final String strPicmeId, final String strDob) {
        view.showProgress();
        String url = Apiconstants.BASE_URL + Apiconstants.LOG_IN_CHECK_PIKME;

        Log.d("Log in check Url--->",url);
        Log.d("Url--->",strPicmeId);
        Log.d("Dob--->",strDob);

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            view.hideProgress();
                Log.d("success",response);

                view.showPickmeResult(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.hideProgress();
                Log.d(" error",error.toString());

                view.showErrorMessage(error.toString());

            }
        }) {

                        @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("picmeId",strPicmeId);
                params.put("DOB",strDob);

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
        // Adding request to request queue
        VolleySingleton.getInstance(activity).addToRequestQueue(strReq);

    }

}
