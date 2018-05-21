package com.unicef.thaimai.motherapp.utility;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.activity.NoInternetConnection;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    PreferenceData preferenceData;
    CheckNetwork checkNetwork;
    public MyFirebaseInstanceIDService() {


    }

    private Activity activity;
    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        final Intent intent = new Intent("tokenReceiver");
        // You can also include some extra data.
        final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        intent.putExtra("token",refreshedToken);
        broadcastManager.sendBroadcast(intent);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        sendRegistrationToServer(refreshedToken);

        storeToken(refreshedToken);
    }
    private void storeToken(String token) {

        preferenceData = new PreferenceData(this);
        checkNetwork = new CheckNetwork(this);
        //saving the token on shared preferences
        if (checkNetwork.isNetworkAvailable()) {
//            Toast.makeText(getApplicationContext(), "Internet connection is" + checkNetwork.isNetworkAvailable(), Toast.LENGTH_SHORT).show();
            preferenceData.setDeviceId(token);
        } else {
            Toast.makeText(getApplicationContext(), "Internet connection is" + checkNetwork.isNetworkAvailable(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),NoInternetConnection.class));

        }

    }

    // [END refresh_token]



    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
//    private void sendRegistrationToServer(final String token) {
//
//        String url = ApiConstants.BASE_URL;
//        Log.d("Log in check Url--->", url);
//        Log.d("Token--->",token);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("id",token);
//                return params;
//            }
//
//
//
//            public int getMethod() {
//                return Method.POST;
//            }
//        };
//        VolleySingleton.getInstance(activity).addToRequestQueue(stringRequest);
//
//
//        // TODO: Implement this method to send token to your app server.
//    }
}
