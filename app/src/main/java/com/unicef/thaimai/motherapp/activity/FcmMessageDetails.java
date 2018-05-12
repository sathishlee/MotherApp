package com.unicef.thaimai.motherapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.unicef.thaimai.motherapp.R;

public class FcmMessageDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcm_message_details);
        Intent message = getIntent();
        String messagedetails = message.getStringExtra("message");
        Log.e("FCM Messaage Details",messagedetails);
    }
}
