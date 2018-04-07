package com.unicef.thaimai.motherapp.Presenter;

import android.app.Activity;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.interactor.ImmunizationEntryInteractor;
import com.unicef.thaimai.motherapp.model.requestmodel.ImmunizationEntryRequestModel;
import com.unicef.thaimai.motherapp.view.ImmunizationEntryView;
import com.unicef.thaimai.motherapp.volleyservice.VolleySingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class ImmunizationEntryPresenter implements ImmunizationEntryInteractor{

    Activity context;
    ImmunizationEntryView immunizationEntryView;

    public ImmunizationEntryPresenter(Activity context, ImmunizationEntryView immunizationEntryView){
        this.context = context;
        this.immunizationEntryView = immunizationEntryView;
    }


    @Override
    public void immunizationEntry(final String immunization_url,final ImmunizationEntryRequestModel immunizationEntryRequestModel) {
        String url = Apiconstants.BASE_URL + immunization_url ;
        Log.d("Log in check Url--->", url);
        immunizationEntryView.showProgress();

        Map<String, String> params = new HashMap<String, String>();

        params.put("mid",immunizationEntryRequestModel.getMid());
        params.put("picmeId",immunizationEntryRequestModel.getPicmeId());
        params.put("immDoseId",immunizationEntryRequestModel.getImmDoseId());
        params.put("immDoseNumber",immunizationEntryRequestModel.getImmDoseNumber());
//        params.put("immDoseIDValue", immunizationEntryRequestModel.getImmDoseIDValue());
        params.put("immDueDate", immunizationEntryRequestModel.getImmDueDate());
        params.put("immCarePovidedDate", immunizationEntryRequestModel.getImmCarePovidedDate());
        params.put("immOpvStatus",immunizationEntryRequestModel.getImmOpvStatus());
        params.put("immRotaStatus",immunizationEntryRequestModel.getImmRotaStatus());
        params.put("immPentanvalentStatus",immunizationEntryRequestModel.getImmPentanvalentStatus());
        params.put("immIpvStatus",immunizationEntryRequestModel.getImmIpvStatus());

        Log.d(ImmunizationEntryPresenter.class.getSimpleName(), new JSONObject(params).toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                immunizationEntryView.showProgress();
                immunizationEntryView.immunizationEntrySuccess(String.valueOf(response));
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                immunizationEntryView.hideProgress();
                immunizationEntryView.immunizationEntryFailure(error.toString());
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
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void immunizationID(final String strpicmeId, final String strmid) {

        String url = Apiconstants.BASE_URL + Apiconstants.IMMUNIZATION_ID;
        Log.d("Log in check Url--->", url);
        immunizationEntryView.showProgress();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                immunizationEntryView.immunizationIDSuccess(response);
                immunizationEntryView.hideProgress();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                immunizationEntryView.immunizationIDFailure(error.toString());
                immunizationEntryView.hideProgress();
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
                params.put("picmeId",strpicmeId);
                params.put("mid",strmid);

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
        VolleySingleton.getInstance(context).addToRequestQueue(request);

    }
    @Override
    public void getImmunizationByVisit(final String immId, final String mid) {

        immunizationEntryView.showProgress();
        String url = Apiconstants.BASE_URL + Apiconstants.GET_IMMUNIZATION_BY_VISIT;

        Log.d(ImmunizationListPresenter.class.getSimpleName(),"Immunization List "+url);
        Log.d("PicmeId--->",immId);
        Log.d("mid--->",mid);

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                immunizationEntryView.hideProgress();
                Log.d("Immun by visit success",response);
                immunizationEntryView.getImmunizationByVisitSuccess(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                immunizationEntryView.hideProgress();
                Log.d("Immun by visit error",error.toString());
                immunizationEntryView.getImmunizationByVisitFailure(error.toString());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("immId",immId);
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
        VolleySingleton.getInstance(context).addToRequestQueue(strReq);
    }

}
