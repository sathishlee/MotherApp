package com.unicef.thaimai.motherapp.broadCastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.unicef.thaimai.motherapp.utility.ListenActivities;

import java.util.Iterator;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class UninstallIntentReceiver extends BroadcastReceiver {

    /*@Override
    public void onReceive(Context context, Intent intent) {

        String[] packageNames = intent.getStringArrayExtra("android.intent.extra.PACKAGES");

        if(packageNames!=null){
            for(String packageName: packageNames){
                if(packageName!=null && packageName.equals("com.unicef.thaimai.motherapp")){
                    // User has selected our application under the Manage Apps settings
                    // now initiating background thread to watch for activity
                    new ListenActivities(context).start();

                }
            }
        }

    }*/

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        Iterator<String> it = bundle.keySet().iterator();
        while (it.hasNext()){
            String key = it.next();

            Log.e("DDDD--->", key + "=" + bundle.get(key));
        }

        // install call
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            //code here on install
            Log.i("Installed:", intent.getDataString());
        }

        // uninstall call
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            //code here on uninstall
            Log.i("Uninstalled:", intent.getDataString());

        }
    }
}
