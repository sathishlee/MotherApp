package com.unicef.thaimai.motherapp.broadCastReceivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.activity.LocationUpdateActivity;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.utility.LocationMonitoringService;

import net.alexandroid.gps.GpsStatusDetector;

import static com.unicef.thaimai.motherapp.constant.AppConstants.EXTRA_LATITUDE;
import static com.unicef.thaimai.motherapp.constant.AppConstants.EXTRA_LONGITUDE;

/**
 * Created by sathish on 3/30/2018.
 */

public class GpsLocationReceiver extends BroadcastReceiver {
//    private GpsStatusDetector mGpsStatusDetector;

    public static final String LOCATION_CHANGED = "LOCATION_CHANGED";
    public static final String ACTION_LOCATION_BROADCAST = LocationMonitoringService.class.getName() + "LocationBroadcast";

    Location location;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {

           /* Toast.makeText(context, "in android.location.PROVIDERS_CHANGED",
                    Toast.LENGTH_SHORT).show();
            Intent pushIntent = new Intent(context, LocationUpdateActivity.class);
            pushIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(pushIntent);*/
//            mGpsStatusDetector = new GpsStatusDetector((Activity) context.getApplicationContext());
//            mGpsStatusDetector.checkGpsStatus();
            Intent pushIntent = new Intent(context, LocationMonitoringService.class);
            context.startService(pushIntent);
        }

    }


  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mGpsStatusDetector.checkOnActivityResult(requestCode, resultCode);
    }

    @Override
    public void onGpsSettingStatus(boolean enabled) {
        Log.d("TAG", "onGpsSettingStatus: " + enabled);
        if(!enabled){
            mGpsStatusDetector.checkGpsStatus();
        }
    }

    @Override
    public void onGpsAlertCanceledByUser() {
        Log.d("TAG", "onGpsAlertCanceledByUser");
    }*/
}
