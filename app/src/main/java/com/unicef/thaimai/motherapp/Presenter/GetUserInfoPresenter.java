package com.unicef.thaimai.motherapp.Presenter;

import android.app.Activity;
import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.interactor.UserInfoInteractor;
import com.unicef.thaimai.motherapp.view.LoginViews;
import com.unicef.thaimai.motherapp.volleyservice.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sathish on 2/20/2018.
 */

public class GetUserInfoPresenter implements UserInfoInteractor{
    private LoginViews view;
    private Context activity;

    public GetUserInfoPresenter(Context mActivity, LoginViews view) {
        this.view = view;
        this.activity = mActivity;
    }



    @Override
    public void getUserInfo(final String pickmeid, final String versionCode) {

      view.showProgress();

        Log.d("Versioncode", versionCode);


        Log.e("url", Apiconstants.BASE_URL+Apiconstants.POST_DASH_BOARD);

        StringRequest strReq = new StringRequest(Request.Method.POST,Apiconstants.BASE_URL+Apiconstants.POST_DASH_BOARD , new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("user info", " Response: " + response.toString());
                view.hideProgress();
                view.showPickmeResult(response);

                /*try {
                    JSONObject jObj = new JSONObject(response);
//                    success = jObj.getInt(Constants.TAG_SUCCESS);
                    int status = jObj.getInt("status");
                    // Check for error in json
                    if (status == 1) {
                        view.showErrorMessage("success");
                        Log.e("User Found!", jObj.toString());
                        Toast.makeText(activity.getApplicationContext(),
                                jObj.getString("message"), Toast.LENGTH_LONG).show();

                    }
                    else {
                        Toast.makeText(activity.getApplicationContext(),
                                jObj.getString("message"), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }*/

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("User Info Login Error:", "Login Error: " + error.getMessage());
                Toast.makeText(activity.getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                view.hideProgress();

            }
        }) {


            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                String credentials ="admin"+":"+"1234";
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(),Base64.DEFAULT);
                HashMap<String,String> header = new HashMap<>();
//                header.put("Content-Type","application/x-www-from-urlencoded; charset=utf-8");
                header.put("Authorization","Basic "+base64EncodedCredentials);
                return header;
            }

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("picmeId",pickmeid);
                params.put("appversion",versionCode);

                Log.d("params--->",params.toString());

                return params;
            }
//            public String getBodyContentType(){
//                return "application/x-www-from-urlencoded; charset=utf-8";
//            }
            public int getMethod(){
                return Method.POST;
            }
        };


        // Adding request to request queue
        VolleySingleton.getInstance(activity).addToRequestQueue(strReq);

    }
}
