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
import com.unicef.thaimai.motherapp.interactor.PrimaryRegisterInteractor;
import com.unicef.thaimai.motherapp.model.requestmodel.PrimaryDataRequestModel;
import com.unicef.thaimai.motherapp.view.PrimaryRegisterViews;
import com.unicef.thaimai.motherapp.volleyservice.VolleySingleton;
import org.json.JSONObject;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
* Created by sathish on 3/7/2018.
*/
public class PrimaryRegisterPresenter implements PrimaryRegisterInteractor {
        private PrimaryRegisterViews primaryRegisterViews;
        private Activity activity;

    public PrimaryRegisterPresenter(PrimaryRegisterViews primaryRegisterViews, Activity activity) {
        this.primaryRegisterViews = primaryRegisterViews;
        this.activity = activity;
    }

    @Override
    public void getAllMotherPrimaryRegistration(final String strPicmeId) {
        String url = Apiconstants.BASE_URL + Apiconstants.POST_PRIMARY_INFO;
        Log.d("Log in check Url--->", url);
        Log.d("picmeId--->", strPicmeId);
        primaryRegisterViews.showProgress();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        @Override
            public void onResponse(String response) {
            Log.d(PrimaryRegisterPresenter.class.getSimpleName(), "Success response" + response);
            primaryRegisterViews.hideProgress();
            primaryRegisterViews.getAllMotherPrimaryRegisterSuccess(response);
            }
        }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(PrimaryRegisterPresenter.class.getSimpleName(), "Success response" + error.toString());
                    primaryRegisterViews.hideProgress();
                    primaryRegisterViews.getAllMotherPrimaryRegisterFailiur(error.toString());

                }
            }) {


                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("picmeId", strPicmeId);

                    Log.d("params--->", params.toString());

                    return params;
                }

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
            VolleySingleton.getInstance(activity).addToRequestQueue(stringRequest);
        VolleySingleton.getInstance(activity).getRequestQueue().getCache().remove(url);


    }

        @Override
        public void postprimaryData(final String strPicmeId, final PrimaryDataRequestModel primaryDataRequestModel) {
            String url = Apiconstants.BASE_URL + Apiconstants.POST_PRIMARY_INFO_UPDATE;
            Log.d("Log in check Url--->", url);
            Log.d("picmeId--->", strPicmeId);
            primaryRegisterViews.showProgress();

            Map<String, String> params = new HashMap<String, String>();

            params.put("mPicmeId", primaryDataRequestModel.getMPicmeId());
            params.put("masterId", primaryDataRequestModel.getMasterId());
            params.put("mid", primaryDataRequestModel.getMid());
            params.put("mName", primaryDataRequestModel.getMName());
            params.put("mAge", primaryDataRequestModel.getMAge());
            params.put("mLMP", primaryDataRequestModel.getMLMP());
            params.put("mEDD", primaryDataRequestModel.getMEDD());
            params.put("mMotherMobile", primaryDataRequestModel.getMMotherMobile());
            params.put("mHusbandMobile", primaryDataRequestModel.getMHusbandMobile());
            params.put("mMotherOccupation", primaryDataRequestModel.getMMotherOccupation());
            params.put("mHusbandOccupation", primaryDataRequestModel.getMHusbandOccupation());
            params.put("mAgeatMarriage", primaryDataRequestModel.getMAgeatMarriage());
            params.put("mConsanguineousMarraige", primaryDataRequestModel.getMConsanguineousMarraige());
            params.put("mHistoryIllness", primaryDataRequestModel.getMHistoryIllness());
            params.put("mHistoryIllnessFamily", primaryDataRequestModel.getMHistoryIllnessFamily());
            params.put("mAnySurgeryBefore", primaryDataRequestModel.getMAnySurgeryBefore());
            params.put("mUseTobacco", primaryDataRequestModel.getMUseTobacco());
            params.put("mUseAlcohol", primaryDataRequestModel.getMUseAlcohol());
            params.put("mAnyMeditation", primaryDataRequestModel.getMAnyMeditation());
            params.put("mAllergicToanyDrug", primaryDataRequestModel.getMAllergicToanyDrug());
            params.put("mHistroyPreviousPreganancy", primaryDataRequestModel.getMHistroyPreviousPreganancy());
            params.put("mLscsDone", primaryDataRequestModel.getMLscsDone());
            params.put("mAnyComplecationDuringPreganancy", primaryDataRequestModel.getMAnyComplecationDuringPreganancy());
            params.put("mPresentPreganancyG", primaryDataRequestModel.getMPresentPreganancyG());
            params.put("mPresentPreganancyP", primaryDataRequestModel.getMPresentPreganancyP());
            params.put("mPresentPreganancyA", primaryDataRequestModel.getMPresentPreganancyA());
            params.put("mPresentPreganancyL", primaryDataRequestModel.getMPresentPreganancyL());
            params.put("mRegistrationWeek", primaryDataRequestModel.getMRegistrationWeek());
            params.put("mANTT1", primaryDataRequestModel.getMANTT1());
            params.put("mANTT2", primaryDataRequestModel.getMANTT2());
            params.put("mIFAStateDate", primaryDataRequestModel.getMIFAStateDate());
            params.put("mHeight", primaryDataRequestModel.getMHeight());
            params.put("mBloodGroup", primaryDataRequestModel.getMBloodGroup());
            params.put("mHIV", primaryDataRequestModel.getMHIV());
            params.put("mVDRL", primaryDataRequestModel.getMVDRL());
            params.put("mHepatitis", primaryDataRequestModel.getMHepatitis());
            params.put("hBloodGroup", primaryDataRequestModel.getHBloodGroup());
            params.put("hHIV", primaryDataRequestModel.getHHIV());
            params.put("hVDRL", primaryDataRequestModel.getHVDRL());
            params.put("hHepatitis", primaryDataRequestModel.getMHepatitis());
            params.put("mHistoryIllnessOthers", primaryDataRequestModel.getMHistoryIllnessOthers());
            params.put("mHistoryIllnessFamilyOthers", primaryDataRequestModel.getMHistoryIllnessFamilyOthers());
            params.put("mAnySurgeryBeforeOthers", primaryDataRequestModel.getMAnySurgeryBeforeOthers());
            params.put("mAnyComplecationDuringOthers", primaryDataRequestModel.getMAnyComplecationDuringOthers());


            Log.d("params send to server", String.valueOf(new JSONObject(params)));

            JsonObjectRequest req = new JsonObjectRequest(url, new JSONObject(params), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    primaryRegisterViews.hideProgress();
                    primaryRegisterViews.postDataSuccess(String.valueOf(response));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    primaryRegisterViews.hideProgress();
                    primaryRegisterViews.postDataFailiure(error.toString());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    String credentials = "admin" + ":" + "1234";
                    String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
                    HashMap<String, String> header = new HashMap<>();
    //                header.put("Content-Type", "application/x-www-from-urlencoded; charset=utf-8");
                    header.put("Content-Type", "application/json; charset=utf-8");
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
            VolleySingleton.getInstance(activity).addToRequestQueue(req);
            VolleySingleton.getInstance(activity).getRequestQueue().getCache().remove(url);


        }
    }
