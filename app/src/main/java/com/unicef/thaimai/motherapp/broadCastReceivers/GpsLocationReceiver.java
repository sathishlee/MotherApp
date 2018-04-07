package com.unicef.thaimai.motherapp.broadCastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.activity.LocationUpdateActivity;

/**
 * Created by sathish on 3/30/2018.
 */

public class GpsLocationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
            Toast.makeText(context, "in android.location.PROVIDERS_CHANGED",
                    Toast.LENGTH_SHORT).show();
//            Intent pushIntent = new Intent(context, LocationUpdateActivity.class);
//            context.startActivity(pushIntent);
        }
    }
}
