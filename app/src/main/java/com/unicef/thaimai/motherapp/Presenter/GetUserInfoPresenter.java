package com.unicef.thaimai.motherapp.Presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Base64;
import android.util.Log;
import android.view.View;
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
    private Activity activity;

    public GetUserInfoPresenter(Activity mActivity, LoginViews view) {
        this.view = view;
        this.activity = mActivity;

    }

    @Override
    public void getUserInfo(String pickmeid) {

    }

/*    @Override
    public void getUserInfo(String pickmeid) {

      view.showProgress();


        Log.e("url", Apiconstants.BASE_URL+Apiconstants.USER_INFO);

        StringRequest strReq = new StringRequest(Request.Method.POST,Apiconstants.BASE_URL+Apiconstants.USER_INFO+pickmeid , new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("user info", " Response: " + response.toString());
                view.hideProgress();

                try {
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
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("user info Login Error:", "Login Error: " + error.getMessage());
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
        VolleySingleton.getInstance(activity).addToRequestQueue(strReq);

    }*/
}
