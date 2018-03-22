package com.unicef.thaimai.motherapp.Presenter;

import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.unicef.thaimai.motherapp.activity.PNHBNCVisitEntry;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.interactor.PNHBNCVisitInteractor;
import com.unicef.thaimai.motherapp.model.requestmodel.PNHBNCVisitEntryRequestModel;
import com.unicef.thaimai.motherapp.model.responsemodel.PnHbncVisitRecordsModel;
import com.unicef.thaimai.motherapp.view.PNHBNCVisitViews;
import com.unicef.thaimai.motherapp.volleyservice.VolleySingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class PNHBNCVisitPresenter implements PNHBNCVisitInteractor {

    PNHBNCVisitEntry pnhbncVisitEntry;
    PNHBNCVisitViews pnhbncVisitViews;

    public PNHBNCVisitPresenter(PNHBNCVisitEntry pnhbncVisitEntry,PNHBNCVisitViews pnhbncVisitViews){
        this.pnhbncVisitEntry = pnhbncVisitEntry;
        this.pnhbncVisitViews = pnhbncVisitViews;

    }


    @Override
    public void getPNHBNCVisitCount(final String strPicmeId, final String strMid) {
        String url = Apiconstants.BASE_URL + Apiconstants.PN_HBNC_VISITNUMBER;
        Log.d("Log in check Url--->", url);
        pnhbncVisitViews.showProgress();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pnhbncVisitViews.hideProgress();
                pnhbncVisitViews.getpnhbncVisitNumberSuccess(String.valueOf(response));
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pnhbncVisitViews.hideProgress();
                pnhbncVisitViews.getpnhbncVisitNumberFailiure(error.toString());
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
                params.put("picmeId",strPicmeId);
                params.put("mid",strMid);

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
        VolleySingleton.getInstance(pnhbncVisitEntry).addToRequestQueue(request);
    }

    @Override
    public void insertPNHBNCVistRecords(PNHBNCVisitEntryRequestModel pnhbncVisitEntryRequestModel) {

        String url = Apiconstants.BASE_URL + Apiconstants.PN_HBNC_VISIT_INSERT;
        Log.d("Log in check Url--->", url);
        pnhbncVisitViews.showProgress();

        Map<String, String> params = new HashMap<String, String>();

        params.put("mid",pnhbncVisitEntryRequestModel.getMid());
        params.put("picmeId",pnhbncVisitEntryRequestModel.getPicmeId());
        params.put("pnVisitNo",pnhbncVisitEntryRequestModel.getPnVisitNo());
        params.put("pnVisitId",pnhbncVisitEntryRequestModel.getPnVisitId());
        params.put("pnDueDate",pnhbncVisitEntryRequestModel.getPnDueDate());
        params.put("pnCareProvidedDate",pnhbncVisitEntryRequestModel.getPnCareProvidedDate());
        params.put("pnPlace",pnhbncVisitEntryRequestModel.getPnPlace());
        params.put("pnAnyComplaints",pnhbncVisitEntryRequestModel.getPnAnyComplaints());
        params.put("pnBPSystolic",pnhbncVisitEntryRequestModel.getPnBPSystolic());
        params.put("pnBPDiastolic",pnhbncVisitEntryRequestModel.getPnBPDiastolic());
        params.put("pnPulseRate",pnhbncVisitEntryRequestModel.getPnPulseRate());
        params.put("pnTemp",pnhbncVisitEntryRequestModel.getPnTemp());
        params.put("pnEpistomyTear",pnhbncVisitEntryRequestModel.getPnEpistomyTear());
        params.put("pnPVDischarge",pnhbncVisitEntryRequestModel.getPnPVDischarge());
        params.put("pnBreastFeeding",pnhbncVisitEntryRequestModel.getPnBreastFeeding());
        params.put("pnBreastFeedingReason",pnhbncVisitEntryRequestModel.getPnBreastFeedingReason());
        params.put("pnBreastExamination",pnhbncVisitEntryRequestModel.getPnBreastExamination());
        params.put("pnOutCome",pnhbncVisitEntryRequestModel.getPnOutCome());
        params.put("cWeight",pnhbncVisitEntryRequestModel.getCWeight());
        params.put("cTemp",pnhbncVisitEntryRequestModel.getCTemp());
        params.put("cUmbilicalStump",pnhbncVisitEntryRequestModel.getCUmbilicalStump());
        params.put("cCry",pnhbncVisitEntryRequestModel.getCCry());
        params.put("cEyes",pnhbncVisitEntryRequestModel.getCEyes());
        params.put("cSkin",pnhbncVisitEntryRequestModel.getCSkin());
        params.put("cBreastFeeding",pnhbncVisitEntryRequestModel.getCBreastFeeding());
        params.put("cBreastFeedingReason",pnhbncVisitEntryRequestModel.getCBreastFeedingReason());
        params.put("cOutCome",pnhbncVisitEntryRequestModel.getCOutCome());

        Log.d(PNHBNCVisitPresenter.class.getSimpleName(), new JSONObject(params).toString());



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pnhbncVisitViews.hideProgress();
                pnhbncVisitViews.pnhbncVisitSuccess(String.valueOf(response));


            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pnhbncVisitViews.hideProgress();
                pnhbncVisitViews.pnhbncVisitFailiure(error.toString());
            }
        }){
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
        VolleySingleton.getInstance(pnhbncVisitEntry).addToRequestQueue(jsonObjectRequest);

    }
}
