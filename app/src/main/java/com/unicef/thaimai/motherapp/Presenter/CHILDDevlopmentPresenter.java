package com.unicef.thaimai.motherapp.Presenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.interactor.CHILDDevlopmentInteractor;
import com.unicef.thaimai.motherapp.model.ChildDevelopmentModdel.ChildDevQuestionModel;
import com.unicef.thaimai.motherapp.utility.VolleyMultipartRequest;
import com.unicef.thaimai.motherapp.view.CHILDDevlopmentViews;
import com.unicef.thaimai.motherapp.volleyservice.VolleySingleton;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CHILDDevlopmentPresenter implements CHILDDevlopmentInteractor {


    Activity activity;
    CHILDDevlopmentViews childDevlopmentViews;

    public CHILDDevlopmentPresenter(Activity activity, CHILDDevlopmentViews childDevlopmentViews) {
        this.activity = activity;
        this.childDevlopmentViews = childDevlopmentViews;
    }
    @Override
    public void getCurrentChildDevMonth(final String strPickmeId, final String strMid) {
        String url = Apiconstants.BASE_URL + Apiconstants.CHILD_DEVELOPMENT_CURRRENT_MONTH;
        Log.d(" Url--->", url);
        Log.d("strPickmeId --->", strPickmeId);
        Log.d("strMid--->", strMid);
        childDevlopmentViews.showProgress();

        StringRequest stringRequest =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                childDevlopmentViews.hideProgress();
                Log.e("response",response);
                childDevlopmentViews.getCurrentmonthSuccess(String.valueOf(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                childDevlopmentViews.hideProgress();
                Log.e("error",error.toString());

                childDevlopmentViews.getCurrentmonthFailiure(error.toString());
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
                params.put("picmeId",strPickmeId);
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
        VolleySingleton.getInstance(activity).addToRequestQueue(stringRequest);
        VolleySingleton.getInstance(activity).getRequestQueue().getCache().remove(url);
    }

    @Override
    public void getAllChildDevelopmentRecords(final String strPickmeId, final String strMid) {

        String url = Apiconstants.BASE_URL + Apiconstants.CHILD_DEVELOPMENT_GET_All_REPORT;
        Log.e(" Url--->", url);
        Log.e("strPickmeId --->", strPickmeId);
        Log.e("strMid--->", strMid);
        childDevlopmentViews.showProgress();

        StringRequest stringRequest =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                childDevlopmentViews.hideProgress();
                Log.e("response",response);
//                childDevlopmentViews.getCurrentmonthSuccess(String.valueOf(response));
                childDevlopmentViews.getQuestionReportSuccess(String.valueOf(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                childDevlopmentViews.hideProgress();
                Log.e("error",error.toString());

//                childDevlopmentViews.getCurrentmonthFailiure(error.toString());
                childDevlopmentViews.getQuestionReportFailiure(error.toString());

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

//                params.put("picmeId",strPickmeId);

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
        VolleySingleton.getInstance(activity).addToRequestQueue(stringRequest);
        VolleySingleton.getInstance(activity).getRequestQueue().getCache().remove(url);

    }

    @Override
    public void getAllQuestions() {

        String url = Apiconstants.BASE_URL + Apiconstants.CHILD_DEVELOPMENT_GET_All_QUESTION_MASTER;
        Log.d(" Url--->", url);
//        Log.d("strPickmeId --->", strPickmeId);
//        Log.d("strMid--->", strMid);
        childDevlopmentViews.showProgress();

        StringRequest stringRequest =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                childDevlopmentViews.hideProgress();
                Log.e("response",response);
                childDevlopmentViews.getQuestionsSuccess(String.valueOf(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                childDevlopmentViews.hideProgress();
                Log.e("error",error.toString());

                childDevlopmentViews.getQuestionsFailiure(error.toString());
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

//                params.put("picmeId",strPickmeId);

//                params.put("mid",strMid);

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
        VolleySingleton.getInstance(activity).addToRequestQueue(stringRequest);
        VolleySingleton.getInstance(activity).getRequestQueue().getCache().remove(url);


    }

    @Override
    public void updateChildDevQuestions(final String strPickmeId, final String strMid, final String strVhnId, final String mtrmonthCheck, final ArrayList<ChildDevQuestionModel> childDevQuestionModel) {
        childDevlopmentViews.showProgress();

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Apiconstants.BASE_URL + Apiconstants.CHILD_DEVELOPMENT_ADD, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                childDevlopmentViews.hideProgress();
                try {
                    Log.e(CHILDDevlopmentPresenter.class.getSimpleName(),new String(response.data,"UTF-8"));

                    childDevlopmentViews.UpdateQuestinsSuccess(new String(response.data,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                childDevlopmentViews.hideProgress();
                Log.e(CHILDDevlopmentPresenter.class.getSimpleName(),error.toString());

                childDevlopmentViews.UpdateQuestinsFailiure(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mid", strMid);
                params.put("picmeId", strPickmeId);
                params.put("vhnId", strVhnId);
                params.put("monthcheck",mtrmonthCheck );
                for (int i = 0; i < childDevQuestionModel.size(); i++) {

                    params.put("setquestion[" + i + "]", childDevQuestionModel.get(i).getSetquestion());
                    params.put("setanswer[" + i + "]", childDevQuestionModel.get(i).getSetanswer());
                    params.put("domain[" + i + "]", childDevQuestionModel.get(i).getDomain());
                }

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = "admin" + ":" + "1234";
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
                HashMap<String, String> header = new HashMap<>();
                //                header.put("Content-Type", "application/x-www-from-urlencoded; charset=utf-8");
                header.put("Authorization", "Basic " + base64EncodedCredentials);


                return header;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                for (int i = 0; i < childDevQuestionModel.size(); i++) {
                    if (childDevQuestionModel.get(i).getSetanswer().equalsIgnoreCase("No")){
                        Log.e(CHILDDevlopmentPresenter.class.getSimpleName(),"NOIMAGE  ---->"+i);
                    }else {
                        if (childDevQuestionModel.get(i).getSetphoto() != null) {
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            childDevQuestionModel.get(i).getSetphoto().compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
//                    params.put("setphoto[" + i + "]", new DataPart(imagename + ".jpg", getFileDataFromDrawable(childDevQuestionModel.get(i).getSetphoto())));
                            params.put("setphoto[" + i + "]", new DataPart(imagename + ".jpg", byteArrayOutputStream.toByteArray()));
                           }
                    }
                }
                return params;
            }

            private byte[] getFileDataFromDrawable(Bitmap bitmap) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
                return byteArrayOutputStream.toByteArray();
            }

        };
        VolleySingleton.getInstance(activity).addToRequestQueue(volleyMultipartRequest);
    }
    }
