package com.unicef.thaimai.motherapp.Presenter;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.unicef.thaimai.motherapp.activity.AddRecords;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.interactor.AddRecordsInteractor;
import com.unicef.thaimai.motherapp.model.requestmodel.AddRecordRequestModel;
import com.unicef.thaimai.motherapp.view.AddRecordViews;
import com.unicef.thaimai.motherapp.volleyservice.VolleySingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sathish on 3/9/2018.
 */

public class AddVisitRecordsPresenter implements AddRecordsInteractor {
    Activity activity;
    AddRecordViews views;

    public AddVisitRecordsPresenter(Activity activity, AddRecordViews views) {
        this.activity = activity;
        this.views = views;
    }

    @Override
    public void getVisitCount(final String strPickmeId, final String strMid) {
        String url = Apiconstants.BASE_URL + Apiconstants.POST_VIST_HEALTH_RECORD_NUMBER;
        Log.e("Log in check Url--->", url);
        views.showProgress();
        StringRequest stringRequest =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                views.hideProgress();

                views.getVisitIDSuccess(String.valueOf(response));
                Log.e("response--->", response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                views.hideProgress();
                views.getVisitIDFailiure(error.toString());
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
                Log.e("Credentials ", "Basic " + base64EncodedCredentials.toString());

                return header;
            }
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
//                params.put("picmeId",strPickmeId);
//                params.put("mid",strMid);
                params.put("mLMP", AppConstants.LMP_DATE);

                Log.e("params--->",params.toString());

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
    public void insertVistRecords(final AddRecordRequestModel addRecordRequestModel) {
        String url = Apiconstants.BASE_URL + Apiconstants.POST_VIST_HEALTH_RECORD_INSERT;
        Log.e("Log in check Url--->", url);
        views.showProgress();

        Map<String, String> params = new HashMap<String, String>();

//        params.put("vid", addRecordRequestModel.getVid());
        params.put("vDate", addRecordRequestModel.getVDate());
        params.put("visitId", addRecordRequestModel.getVisitId());
        params.put("mid", addRecordRequestModel.getMid());
        params.put("picmeId", addRecordRequestModel.getPicmeId());
        params.put("vtypeOfVisit", addRecordRequestModel.getVtypeOfVisit());
        params.put("vFacility", addRecordRequestModel.getVFacility());
        if (addRecordRequestModel.getvAnyComplaintsOthers().equalsIgnoreCase("")){
            params.put("vAnyComplaints", addRecordRequestModel.getVAnyComplaints());

        }else{
            params.put("vAnyComplaints", addRecordRequestModel.getVAnyComplaints()+"-"+addRecordRequestModel.getvAnyComplaintsOthers());
        }
        params.put("vFacilityOthers", addRecordRequestModel.getVFacility());
        params.put("vAnyComplaintsOthers", addRecordRequestModel.getVAnyComplaints());

        params.put("vClinicalBPSystolic", addRecordRequestModel.getVClinicalBPSystolic());
        params.put("vClinicalBPDiastolic", addRecordRequestModel.getVClinicalBPDiastolic());
        params.put("vEnterPulseRate", addRecordRequestModel.getVEnterPulseRate());
        params.put("vEnterWeight", addRecordRequestModel.getVEnterWeight());
        params.put("vFundalHeight", addRecordRequestModel.getVFundalHeight());
        params.put("vFHS", addRecordRequestModel.getVFHS());
        params.put("vPedalEdemaPresent", addRecordRequestModel.getVPedalEdemaPresent());
        params.put("vBodyTemp", addRecordRequestModel.getVBodyTemp());
        params.put("vHemoglobin", addRecordRequestModel.getVHemoglobin());
        params.put("vRBS", addRecordRequestModel.getVRBS());
        params.put("vFBS", addRecordRequestModel.getVFBS());
        params.put("vPPBS", addRecordRequestModel.getVPPBS());
        params.put("vGTT", addRecordRequestModel.getVGTT());
        params.put("vUrinSugar", addRecordRequestModel.getVUrinSugar());
        params.put("vAlbumin", addRecordRequestModel.getVAlbumin());
        params.put("usgGestationSac", addRecordRequestModel.getUsgGestationSac());
        params.put("usgLiquor", addRecordRequestModel.getUsgLiquor());
        params.put("usgPlacenta", addRecordRequestModel.getUsgPlacenta());
        params.put("usgFetus", addRecordRequestModel.getUsgFetus());
        params.put("vTSH", addRecordRequestModel.getVTSH());

        Log.e(AddVisitRecordsPresenter.class.getSimpleName(),new JSONObject(params).toString());

        JsonObjectRequest req = new JsonObjectRequest(url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                views.hideProgress();
                views.insertRecordSuccess(String.valueOf(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                views.hideProgress();
                views.insertRecordFailiure(error.toString());
            }
        }) {
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

//                        public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }

            public int getMethod() {
                return Method.POST;
            }
        };
        VolleySingleton.getInstance(activity).addToRequestQueue(req);
        VolleySingleton.getInstance(activity).getRequestQueue().getCache().remove(url);


    }
}
