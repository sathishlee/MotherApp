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
import com.unicef.thaimai.motherapp.interactor.ReferalInteractor;
import com.unicef.thaimai.motherapp.view.ReferalViews;
import com.unicef.thaimai.motherapp.volleyservice.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sathish on 3/17/2018.
 */

public class ReferalPresenter implements ReferalInteractor {


    private ReferalViews view;
    private Activity activity;

    public ReferalPresenter(ReferalViews view, Activity activity) {
        this.view = view;
        this.activity = activity;
    }

    @Override
    public void getReffralNearestHospital(final String latitude, final String longitude) {
        view.showProgress();

        final String url = Apiconstants.BASE_URL + Apiconstants.POST_REFERAL_NEAREST_HOSPITAL;

        Log.e("Log in check Url--->", url);
        Log.e("latitude", latitude);
        Log.e("longitude", longitude);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("NearestHospital", response);
                view.hideProgress();
                view.successReferalNearestHospital(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.hideProgress();
                view.errorReferalNearestHospital(error.toString());

            }
        }) {


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

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("rLatitude", latitude);
                params.put("rLongitude", longitude);


                Log.d("params--->", "Reffral Nearest Hospital " + params.toString());

                return params;
            }

            //            public String getBodyContentType() {
            //                return "application/x-www-from-urlencoded; charset=utf-8";
            //            }

            public int getMethod() {
                return Method.POST;
            }
        };
        VolleySingleton.getInstance(activity).addToRequestQueue(stringRequest);
        VolleySingleton.getInstance(activity).getRequestQueue().getCache().remove(url);

    }

    @Override
    public void getReffralNeList(final String mid, final String phcId, final String vhnId, final String picmeId) {

        view.showProgress();

        final String url = Apiconstants.BASE_URL + Apiconstants.POST_REFERAL_LIST;

        Log.d("Log in check Url--->", url);
        Log.d("mid", mid);
        Log.d("phcId", phcId);
        Log.d("vhnId", vhnId);
        Log.d("picmeId", picmeId);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                view.hideProgress();
                view.successReferalList(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.hideProgress();
                view.errorReferalList(error.toString());


            }
        }) {


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

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("mid", mid);
                params.put("phcId", phcId);
                params.put("vhnId", vhnId);
                params.put("picmeId", picmeId);


                Log.d("params--->", "Reffral List " + params.toString());

                return params;
            }

            //            public String getBodyContentType() {
            //                return "application/x-www-from-urlencoded; charset=utf-8";
            //            }

            public int getMethod() {
                return Method.POST;
            }
        };
        VolleySingleton.getInstance(activity).addToRequestQueue(stringRequest);
        VolleySingleton.getInstance(activity).getRequestQueue().getCache().remove(url);

    }

    @Override
    public void checkReferalClosed(final String rid) {
        view.showProgress();

        final String url = Apiconstants.BASE_URL + Apiconstants.POST_REFERAL_CLOSED;

        Log.d("Log in check Url--->", url);
        Log.d("rid", rid);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                view.hideProgress();
                view.successReferalClosed(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.hideProgress();
                view.errorReferalClosed(error.toString());


            }
        }) {


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

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("rid", rid);



                Log.d("params--->", "Reffral List " + params.toString());

                return params;
            }

            //            public String getBodyContentType() {
            //                return "application/x-www-from-urlencoded; charset=utf-8";
            //            }

            public int getMethod() {
                return Method.POST;
            }
        };
        VolleySingleton.getInstance(activity).addToRequestQueue(stringRequest);
        VolleySingleton.getInstance(activity).getRequestQueue().getCache().remove(url);

    }

    @Override
    public void addNewReferal(final String picmeId, final String mid, final String vhnId, final String phcId, final String rReferalDate, final String rReferalTime,
                              final String rFacilityReferring, final String rFacilityReferredTo, final String rDiagonosis, final String rReferalReason, final String rReferredBy, final String rFacilityReferredCode, final String rFacilityReferredToCode) {
        view.showProgress();

        final String url = Apiconstants.BASE_URL + Apiconstants.POST_ADD_REFERAL;

//        addReferalRequestModel = new NearestReferalHospitalModel();
        Log.d("Log in check Url--->", url);
      /*  Log.d("picmeId", picmeId);
        Log.d("phcId", phcId);
        Log.d("mid", mid);
        Log.d("vhnId", vhnId);
        Log.d("rReferalDate", rReferalDate);
        Log.d("rReferalTime", rReferalTime);
        Log.d("rFacilityReferring", rFacilityReferring);
        Log.d("rFacilityReferredTo", rFacilityReferredTo);
        Log.d("rDiagonosis", rDiagonosis);
        Log.d("rReferalReason", rReferalReason);
        Log.d("rReferredBy", rReferredBy);
        Log.d("rFacilityReferring_code", rReferredBy);
        Log.d("rFacilityReferredTo_code", rReferredBy);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                view.hideProgress();
                view.successReferalAdd(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.hideProgress();
                view.errorReferalAdd(error.toString());
            }
        }) {

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

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("picmeId", picmeId);
                params.put("phcId", phcId);
                params.put("mid", mid);
                params.put("vhnId", vhnId);
                params.put("rReferalDate", rReferalDate);
                params.put("rReferalTime", rReferalTime);
                params.put("rFacilityReferring", rFacilityReferring);
                params.put("rFacilityReferredTo", rFacilityReferredTo);
                params.put("rDiagonosis", rDiagonosis);
                params.put("rReferalReason", rReferalReason);
                params.put("rReferredBy", rReferredBy);
                params.put("rFacilityReferring_code", rFacilityReferredCode);
                params.put("rFacilityReferredTo_code", rFacilityReferredToCode);

                Log.d("params--->", "Add new Refral " + params.toString());

                return params;
            }

            //            public String getBodyContentType() {
            //                return "application/x-www-from-urlencoded; charset=utf-8";
            //            }

            public int getMethod() {
                return Method.POST;
            }
        };
        VolleySingleton.getInstance(activity).addToRequestQueue(stringRequest);
        VolleySingleton.getInstance(activity).getRequestQueue().getCache().remove(url);

    }

    @Override
    public void updateReferal(final String rid, final String rUpdateDate, final String rUpdateTime, final String rUpdateReceivedBy, final String rUpdateReceivingFacility, final String rUpdateInLabour, final String rUpdateAdmitted) {
        view.showProgress();

        final String url = Apiconstants.BASE_URL + Apiconstants.POST_UPDATE_REFERAL;

//        addReferalRequestModel = new NearestReferalHospitalModel();
        Log.d("Log in check Url--->", url);
      /*  Log.d("picmeId", picmeId);
        Log.d("phcId", phcId);
        Log.d("mid", mid);
        Log.d("vhnId", vhnId);
        Log.d("rReferalDate", rReferalDate);
        Log.d("rReferalTime", rReferalTime);
        Log.d("rFacilityReferring", rFacilityReferring);
        Log.d("rFacilityReferredTo", rFacilityReferredTo);
        Log.d("rDiagonosis", rDiagonosis);
        Log.d("rReferalReason", rReferalReason);
        Log.d("rReferredBy", rReferredBy);
        Log.d("rFacilityReferring_code", rReferredBy);
        Log.d("rFacilityReferredTo_code", rReferredBy);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                view.hideProgress();
                view.successReferalAdd(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.hideProgress();
                view.errorReferalAdd(error.toString());
            }
        }) {

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

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("rid", rid);
                params.put("rUpdateDate", rUpdateDate);
                params.put("rUpdateTime", rUpdateTime);
//                params.put("rUpdateActualDateTime", rUpdateActualDateTime);
                params.put("rUpdateReceivedBy", rUpdateReceivedBy);
                params.put("rUpdateReceivingFacility", rUpdateReceivingFacility);
                params.put("rUpdateInLabour", rUpdateInLabour);
                params.put("rUpdateAdmitted", rUpdateAdmitted);
                Log.d("params--->", "Add new Refral " + params.toString());

                return params;
            }

            //            public String getBodyContentType() {
            //                return "application/x-www-from-urlencoded; charset=utf-8";
            //            }

            public int getMethod() {
                return Method.POST;
            }
        };
        VolleySingleton.getInstance(activity).addToRequestQueue(stringRequest);
        VolleySingleton.getInstance(activity).getRequestQueue().getCache().remove(url);

    }


}
