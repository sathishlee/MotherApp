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
import com.unicef.thaimai.motherapp.interactor.ImmunizationListInteractor;
import com.unicef.thaimai.motherapp.view.ImmunizationListViews;
import com.unicef.thaimai.motherapp.volleyservice.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class ImmunizationListPresenter implements ImmunizationListInteractor {

    private ImmunizationListViews views;
    private Activity activity;

    public ImmunizationListPresenter(Activity activity, ImmunizationListViews views){
        this.activity = activity;
        this.views = views;
    }

    @Override
    public void getImmunizationList(final String picmeId, final String mid) {

        views.showProgress();
        String url = Apiconstants.BASE_URL + Apiconstants.IMMUNIZATION_LIST;

        Log.d(ImmunizationListPresenter.class.getSimpleName(),"Immunization List "+url);
        Log.d("PicmeId--->",picmeId);
        Log.d("mid--->",picmeId);

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                views.hideProgress();
                Log.d("Immun List success",response);
                views.getImmunizationListSuccess(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                views.hideProgress();
                Log.d("Immun List error",error.toString());
                views.getImmunizationListFailure(error.toString());
            }
        }) {

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
        // Adding request to request queue
        VolleySingleton.getInstance(activity).addToRequestQueue(strReq);
        VolleySingleton.getInstance(activity).getRequestQueue().getCache().remove(url);




    }

}
