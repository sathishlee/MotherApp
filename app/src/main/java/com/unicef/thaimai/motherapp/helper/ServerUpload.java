package com.unicef.thaimai.motherapp.helper;

/**
 * Created by Suthishan on 20/1/2018.
 */

import android.app.DownloadManager;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.unicef.thaimai.motherapp.Interface.Nbh;
import com.unicef.thaimai.motherapp.activity.MainActivity;
import com.unicef.thaimai.motherapp.activity.NearbyHospital;
import com.unicef.thaimai.motherapp.volleyservice.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public class ServerUpload  {

    private static final String REGISTER_REQUEST_URL_UPLOAD = "http://192.168.100.19/maps/";

    private static final String POST_UPLOAD_LOCATION = "sample.php";

    private static final String POST_NEAR_HOSPITAL = "near_by.php?lt=13.0054576&lg=80.2552421";
    Nbh nbh;

    String location;
    MainActivity mainActivity;

//    public ServerUpload( MainActivity mainActivity) {
////        this.location = location;
//        this.mainActivity = mainActivity;
//    }

    public void sendlocationtServer(final String location, final String latitude, final String longitude, MainActivity mainActivity){
        this.mainActivity=mainActivity;


        Log.e("base url",REGISTER_REQUEST_URL_UPLOAD);
        Log.e("location",location);
        Log.e("response",latitude);
        Log.e("response",longitude);

        StringRequest strReq =new StringRequest(Request.Method.POST, REGISTER_REQUEST_URL_UPLOAD+POST_UPLOAD_LOCATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error",error.toString());

            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
//                params.put("msg_view", location);
                params.put("latitude", latitude);
                params.put("longitude", longitude);

                return params;
            }



            //            public String getBodyContentType(){
//                return "application/x-www-from-urlencoded; charset=utf-8";
//            }
            public int getMethod(){
                return Method.POST;
            }
        };

        VolleySingleton.getInstance(mainActivity).addToRequestQueue(strReq);



    }


    public void getNearByHospitals(final String latitude, final String longitude, NearbyHospital nearByHospital, final Nbh nbh)
    {                Log.e("URL", REGISTER_REQUEST_URL_UPLOAD + POST_NEAR_HOSPITAL);
        this.nbh = nbh;
        StringRequest getNearHospitals =new StringRequest(Request.Method.GET, REGISTER_REQUEST_URL_UPLOAD + POST_NEAR_HOSPITAL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("nearbyHosital response", response.toString());
                nbh.getNBHSuccess(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("nearbyHosital error", error.toString());
                nbh.getNBHfield(error.toString());

            }
        })
        {
            public int getMethod () {
                return Request.Method.GET;
            }
        };
        VolleySingleton.getInstance(nearByHospital).addToRequestQueue(getNearHospitals);


    }


}