package com.unicef.thaimai.motherapp.utility;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.activity.FcmMessageDetails;
import com.unicef.thaimai.motherapp.activity.MainActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String click_action = remoteMessage.getNotification().getClickAction();
            Log.d(MyFirebaseMessagingService.class.getSimpleName(), "Message data payload: " + remoteMessage.getData());


        sendNotification(remoteMessage.getNotification().getBody(),click_action);


    }

        private void sendNotification(String message, String click_action){
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
        }

}
