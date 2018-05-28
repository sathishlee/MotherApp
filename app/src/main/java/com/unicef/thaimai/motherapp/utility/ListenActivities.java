package com.unicef.thaimai.motherapp.utility;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.Preference.PreferenceData;

import java.util.List;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class ListenActivities extends Thread {
    boolean exit = false;
    ActivityManager am = null;
    Context context = null;

    public ListenActivities(Context con){
        context = con;
        am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    }

    public void run(){

        Looper.prepare();

        while(!exit){

            // get the info from the currently running task
            List< ActivityManager.RunningTaskInfo > taskInfo = am.getRunningTasks(MAX_PRIORITY);

            String activityName = taskInfo.get(0).topActivity.getClassName();


            Log.d("topActivity", "CURRENT Activity ::"+ activityName);



            if (activityName.equals("com.android.packageinstaller.UninstallerActivity")) {
                // User has clicked on the Uninstall button under the Manage Apps settings

                //do whatever pre-uninstallation task you want to perform here
                // show dialogue or start another activity or database operations etc..etc..
                Log.d("topActivity", "CURRENT Activity ::"+ activityName);

                Toast.makeText(context, "Done with preuninstallation tasks... Exiting Now", Toast.LENGTH_SHORT).show();
                // context.startActivity(new Intent(context, MyPreUninstallationMsgActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                exit = true;
            } else if(activityName.equals("com.android.settings.ManageApplications")) {
                // back button was pressed and the user has been taken back to Manage Applications window
                // we should close the activity monitoring now
                exit=true;
            }
        }
        Looper.loop();
    }

}
