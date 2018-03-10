package com.unicef.thaimai.motherapp.Presenter;

import android.app.Activity;
import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.unicef.thaimai.motherapp.activity.AddRecords;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
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
    Activity context;
    AddRecordViews views;

    public AddVisitRecordsPresenter(Activity context, AddRecordViews views) {
        this.context = context;
        this.views = views;
    }

    @Override
    public void insertVistRecords(final AddRecordRequestModel addRecordRequestModel) {
        String url = Apiconstants.BASE_URL + Apiconstants.POST_VIST_HEALTH_RECORD_INSERT;
        Log.d("Log in check Url--->", url);
        views.showProgress();
        Map<String, String> params = new HashMap<String, String>();
        params.put("vid", addRecordRequestModel.getPicmeId());
        params.put("vDate", addRecordRequestModel.getPicmeId());
        params.put("visitId", addRecordRequestModel.getPicmeId());
        params.put("mid", addRecordRequestModel.getPicmeId());
        params.put("picmeId", addRecordRequestModel.getPicmeId());
        params.put("vtypeOfVisit", addRecordRequestModel.getPicmeId());
        params.put("vClinicalBPSystolic", addRecordRequestModel.getPicmeId());
        params.put("vClinicalBPDiastolic", addRecordRequestModel.getPicmeId());
        params.put("vEnterPulseRate", addRecordRequestModel.getPicmeId());
        params.put("vEnterWeight", addRecordRequestModel.getPicmeId());
        params.put("vFundalHeight", addRecordRequestModel.getPicmeId());
        params.put("vFHS", addRecordRequestModel.getPicmeId());
        params.put("vPedalEdemaPresent", addRecordRequestModel.getPicmeId());
        params.put("vBodyTemp", addRecordRequestModel.getPicmeId());
        params.put("vHemoglobin", addRecordRequestModel.getPicmeId());
        params.put("vRBS", addRecordRequestModel.getPicmeId());
        params.put("vFBS", addRecordRequestModel.getPicmeId());
        params.put("vPPBS", addRecordRequestModel.getPicmeId());
        params.put("vGTT", addRecordRequestModel.getPicmeId());
        params.put("vUrinSugar", addRecordRequestModel.getPicmeId());
        params.put("vAlbumin", addRecordRequestModel.getPicmeId());
        params.put("usgGestationSac", addRecordRequestModel.getPicmeId());
        params.put("usgLiquor", addRecordRequestModel.getPicmeId());
        params.put("usgPlacenta", addRecordRequestModel.getPicmeId());
        params.put("vTSH", addRecordRequestModel.getPicmeId());
        Log.d(AddVisitRecordsPresenter.class.getSimpleName(),new JSONObject(params).toString());
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
}
