package com.unicef.thaimai.motherapp.utility;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.activity.FcmMessageDetails;
import com.unicef.thaimai.motherapp.activity.MainActivity;
import com.unicef.thaimai.motherapp.constant.AppConstants;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String click_action = remoteMessage.getNotification().getClickAction();
            Log.d(MyFirebaseMessagingService.class.getSimpleName(), "Message data payload: " + remoteMessage.getData());


        sendNotification(remoteMessage.getNotification().getBody(),click_action);


    }

        private void sendNotification(String message, String click_action){/*
            Intent intent = new Intent(this, FcmMessageDetails.class);
//            Intent intent = new Intent(click_action);
            intent.putExtra("message",message);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

            Uri u = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher));
            builder.setSmallIcon(R.drawable.ic_launcher);

//            builder.setSmallIcon(R.drawable.ic_stat_name);

            builder.setContentTitle("THAIMAI");
            builder.setContentText(message);
            builder.setAutoCancel(true);
            builder.setSound(u);
            builder.setContentIntent(pendingIntent);

            NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
       */


            Intent resultIntent = new Intent(this, MainActivity.class);
            AppConstants.OPENFRAGMENT ="00";
//        Intent resultIntent = new Intent(this, SosAlertListActivity.class);
            Bundle bundle =new Bundle();
            bundle.putString("fcm",message);
            resultIntent.putExtras(bundle);
            // Creating a artifical activity stack for the notification activity
            android.support.v4.app.TaskStackBuilder stackBuilder = android.support.v4.app.TaskStackBuilder.create(this);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(resultIntent);

            // Pending intent to the notification manager
            PendingIntent resultPending = stackBuilder
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            // Building the notification
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_launcher) // notification icon
                    .setContentTitle("Mother") // main title of the notification
                    .setContentText(message) // notification text
                    .setContentIntent(resultPending); // notification intent
            NotificationManager mNotificationManager =    (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            // mId allows you to update the notification later on.
            mNotificationManager.notify(10, mBuilder.build());
    }

}
