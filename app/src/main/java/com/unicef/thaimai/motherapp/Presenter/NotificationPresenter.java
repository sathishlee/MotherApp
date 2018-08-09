package com.unicef.thaimai.motherapp.Presenter;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.interactor.NotificationInteractor;
import com.unicef.thaimai.motherapp.view.NotificationViews;
import com.unicef.thaimai.motherapp.volleyservice.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sathish on 3/29/2018.
 */

public class NotificationPresenter implements NotificationInteractor {

    Context context;
    NotificationViews notificationViews;

    public NotificationPresenter(Context context, NotificationViews notificationViews) {
        this.context = context;
        this.notificationViews = notificationViews;
    }

    @Override
    public void getNotificationCount(final String mid, final String picmeId) {

        String url = Apiconstants.BASE_URL + Apiconstants.POST_NOTIFICATION_COUNT;
        Log.d("Url--->", url);
        Log.d("mid--->", mid);
        Log.d("picmeId--->", picmeId);
        notificationViews.showProgress();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                notificationViews.hideProgress();
                notificationViews.NotificationCountSuccess(String.valueOf(response));
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                notificationViews.hideProgress();
                notificationViews.NotificationCountError(error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = "admin" + ":" + "1234";
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
                HashMap<String, String> header = new HashMap<>();
                //                header.put("Content-Type", "application/x-www-from-urlencoded; charset=utf-8");
                header.put("Authorization", "Basic " + base64EncodedCredentials);
//                header.put("Content-Type", "application/json; charset=utf-8");
                Log.d("Credentials ", "Basic " + base64EncodedCredentials.toString());

                return header;
            }
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("mid",mid);
                params.put("picmeId",picmeId);

                Log.d("params--->",params.toString());

                return params;
            }

            //            public String getBodyContentType() {
            //                return "application/x-www-from-urlencoded; charset=utf-8";
            //            }

            public int getMethod() {
                return Method.POST;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(request);
        VolleySingleton.getInstance(context).getRequestQueue().getCache().remove(url);

    }

    @Override
    public void getNotificationList(final String mid, final String picmeId) {


        String url = Apiconstants.BASE_URL + Apiconstants.POST_NOTIFICATION_LIST;
        Log.d("Url--->", url);
        Log.d("mid--->", mid);
        Log.d("picmeId--->",picmeId);
        notificationViews.showProgress();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                notificationViews.hideProgress();
                notificationViews.NotificationResponseSuccess(String.valueOf(response));
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                notificationViews.hideProgress();
                notificationViews.NotificationResponseError(error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = "admin" + ":" + "1234";
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
                HashMap<String, String> header = new HashMap<>();
                //                header.put("Content-Type", "application/x-www-from-urlencoded; charset=utf-8");
                header.put("Authorization", "Basic " + base64EncodedCredentials);
//                header.put("Content-Type", "application/json; charset=utf-8");
                Log.d("Credentials ", "Basic " + base64EncodedCredentials.toString());

                return header;
            }
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("mid",mid);
                params.put("picmeId",picmeId);

                Log.d("params--->",params.toString());

                return params;
            }

            //            public String getBodyContentType() {
            //                return "application/x-www-from-urlencoded; charset=utf-8";
            //            }

            public int getMethod() {
                return Method.POST;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(request);
        VolleySingleton.getInstance(context).getRequestQueue().getCache().remove(url);

    }

    @Override
    public void getNotificationDetails(String mid) {

    }
}
