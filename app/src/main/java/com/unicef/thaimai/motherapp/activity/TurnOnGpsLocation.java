package com.unicef.thaimai.motherapp.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.broadCastReceivers.GpsLocationReceiver;

import net.alexandroid.gps.GpsStatusDetector;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class TurnOnGpsLocation extends AppCompatActivity implements GpsStatusDetector.GpsStatusDetectorCallBack {
    Button btn_turn_on;

    GpsLocationReceiver gpsReceiver;
    IntentFilter intentFilter;
    private GpsStatusDetector mGpsStatusDetector;
    boolean mISGpsStatusDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_off_screen);
        initUI();
    }

    private void initUI() {
        btn_turn_on = (Button) findViewById(R.id.btn_turn_on);
        gpsReceiver = new GpsLocationReceiver();
        intentFilter = new IntentFilter("android.location.PROVIDERS_CHANGED");
        mGpsStatusDetector = new GpsStatusDetector(this);
        mGpsStatusDetector.checkGpsStatus();
        btn_turn_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOnGps();
            }
        });
    }

    public void turnOnGps() {
        startActivity(new Intent(getApplicationContext(),LocationUpdateActivity.class));
        mGpsStatusDetector.checkGpsStatus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mGpsStatusDetector.checkOnActivityResult(requestCode, resultCode);
    }

    @Override
    public void onGpsSettingStatus(boolean enabled) {
        Log.d("TAG", "onGpsSettingStatus: " + enabled);
        mISGpsStatusDetector = enabled;
        if(!enabled){
            mGpsStatusDetector.checkGpsStatus();
        }
    }

    @Override
    public void onGpsAlertCanceledByUser() {
        Log.d("TAG", "onGpsAlertCanceledByUser");
        startActivity(new Intent(getApplicationContext(),TurnOnGpsLocation.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
