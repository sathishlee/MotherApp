package com.unicef.thaimai.motherapp.Presenter;

import android.app.Activity;
import android.app.DownloadManager;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.unicef.thaimai.motherapp.activity.DeliveryDetailsActivityEntry;
import com.unicef.thaimai.motherapp.activity.DeliveryDetailsView;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.interactor.DeliveryEntryInteractor;
import com.unicef.thaimai.motherapp.model.requestmodel.DeliveryEntryRequestModel;
import com.unicef.thaimai.motherapp.view.DeliveryEntryViews;
import com.unicef.thaimai.motherapp.volleyservice.VolleySingleton;

import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class DeliveryEntryPresenter implements DeliveryEntryInteractor {
    Activity context;
    DeliveryEntryViews views;

    public DeliveryEntryPresenter (Activity context, DeliveryEntryViews views){
        this.context = context;
        this.views = views;
    }

    @Override
    public void deliveryEntry (final DeliveryEntryRequestModel deliveryEntryRequestModel){
        String url = Apiconstants.BASE_URL + Apiconstants.DELIVERY_DETAIL_ENTRY;
        Log.d("Log in check Url--->", url);
        views.showProgress();

        Map<String, String> params = new HashMap<String, String>();

        params.put("dpicmeId",deliveryEntryRequestModel.getDpicmeId());
        params.put("mid",deliveryEntryRequestModel.getMid());
        params.put("did",deliveryEntryRequestModel.getDid());
        params.put("ddatetime",deliveryEntryRequestModel.getDdatetime());
        params.put("dtime",deliveryEntryRequestModel.getDtime());
        params.put("dplace",deliveryEntryRequestModel.getDplace());
        params.put("ddeleveryDetails",deliveryEntryRequestModel.getDdeleveryDetails());
        params.put("ddeleveryOutcomeMother",deliveryEntryRequestModel.getDdeleveryOutcomeMother());
        params.put("dnewBorn",deliveryEntryRequestModel.getDnewBorn());
        params.put("dInfantId",deliveryEntryRequestModel.getDInfantId());
        params.put("dBirthDetails",deliveryEntryRequestModel.getDBirthDetails());
        params.put("dBirthWeight",deliveryEntryRequestModel.getDBirthWeight());
        params.put("dBirthHeight",deliveryEntryRequestModel.getDBirthHeight());
        params.put("dBreastFeedingGiven",deliveryEntryRequestModel.getDBreastFeedingGiven());
        params.put("dAdmittedSNCU",deliveryEntryRequestModel.getDAdmittedSNCU());
        params.put("dSNCUDate",deliveryEntryRequestModel.getDSNCUDate());
        params.put("dSNCUOutcome",deliveryEntryRequestModel.getDSNCUOutcome());
        params.put("dBCGDate",deliveryEntryRequestModel.getDBCGDate());
        params.put("dOPVDate",deliveryEntryRequestModel.getDOPVDate());
        params.put("dHEPBDate",deliveryEntryRequestModel.getDHEPBDate());

        Log.d(DeliveryEntryPresenter.class.getSimpleName(), new JSONObject(params).toString());

        JsonObjectRequest req = new JsonObjectRequest(url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                views.hideProgress();

                views.deliveryentrySuccess(String.valueOf(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                views.hideProgress();
                views.deliveryentryFailiure(error.toString());
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = "admin" + ":" + "1234";
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
                HashMap<String, String> header = new HashMap<>();
                //                header.put("Content-Type", "application/x-www-from-urlencoded; charset=utf-8");
                header.put("Authorization", "Basic " + base64EncodedCredentials);
                header.put("Content-Type", "application/json; charset=utf-8");

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
        VolleySingleton.getInstance(context).addToRequestQueue(req);

    }

    @Override
    public void deliveryNumber(final String strPicmeid, final String strMid) {

        String url = Apiconstants.BASE_URL + Apiconstants.DELIVERY_NUMBER;
        Log.d("Log in check Url--->", url);
        views.showProgress();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                views.getdeliveryNumberSuccess(response);
                views.hideProgress();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                views.hideProgress();
                views.getdeliveryNumberFailiure(error.toString());
            }
        })
        {


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
                params.put("dpicmeId",strPicmeid);
                params.put("mid",strMid);

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
        VolleySingleton.getInstance(context).addToRequestQueue(strReq);



    }

    @Override
    public void deliveryDetails(final String strPicmeid, final String strMid, final String strDid) {

        String url = Apiconstants.BASE_URL + Apiconstants.DELIVERY_DETAILS;
        Log.d("Log in check Url--->", url);
        views.showProgress();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(DeliveryEntryPresenter.class.getSimpleName(), "Success response" + response);
                views.deliveryDetailsSuccess(response);
                views.hideProgress();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(DeliveryEntryPresenter.class.getSimpleName(), "Success response" + error.toString());
                views.hideProgress();
                views.deliveryDetailsFailure(error.toString());
            }
        })
        {


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
                params.put("dpicmeId",strPicmeid);
                params.put("mid",strMid);
                params.put("did",strDid);

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
        VolleySingleton.getInstance(context).addToRequestQueue(strReq);



    }

}
