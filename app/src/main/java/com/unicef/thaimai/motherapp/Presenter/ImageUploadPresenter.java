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
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.interactor.ImageUploadInteractor;
import com.unicef.thaimai.motherapp.utility.VolleyMultipartRequest;
import com.unicef.thaimai.motherapp.view.ImageUploadViews;
import com.unicef.thaimai.motherapp.volleyservice.VolleySingleton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class ImageUploadPresenter implements ImageUploadInteractor {
    Activity activity;
    ImageUploadViews imageUploadViews;

    public ImageUploadPresenter(Activity activity,ImageUploadViews imageUploadViews) {
        this.activity = activity;
        this.imageUploadViews =imageUploadViews;
    }


    @Override
    public void uploadUserProfilePhoto(final String picmeId, final Bitmap bitmap) {
        imageUploadViews.showProgress();
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                Apiconstants.BASE_URL + Apiconstants.POST_UPLOAD_PROFILE_PHOTO,
                new Response.Listener<NetworkResponse>() {


                    @Override
                    public void onResponse(NetworkResponse response) {
                        imageUploadViews.hideProgress();
                        imageUploadViews.successUploadPhoto(response.toString());
//                        try {
//                            JSONObject obj = new JSONObject(new String(response.data));
//                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                imageUploadViews.hideProgress();
                imageUploadViews.errorUploadPhoto(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("picmeId", picmeId);
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

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("mPhoto", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }


            private byte[] getFileDataFromDrawable(Bitmap bitmap) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
                return byteArrayOutputStream.toByteArray();
            }
        };

        //adding the request to volley
//        Volley.newRequestQueue(this).add(volleyMultipartRequest);
        VolleySingleton.getInstance(activity).addToRequestQueue(volleyMultipartRequest);

    }

    @Override
    public void uploadVisitReportsPhoto(String picmeId, String visitId, String mid,  ArrayList<Bitmap> bitmap) {

    }

    @Override
    public void pnVisitUploadPhoto(String picmeId, String visitId, String mid, ArrayList<Bitmap> bitmap) {

    }
}

