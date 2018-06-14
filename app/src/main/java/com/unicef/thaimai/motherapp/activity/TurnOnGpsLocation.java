package com.unicef.thaimai.motherapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
//        intentFilter = new IntentFilter("android.location.PROVIDERS_CHANGED");
        mGpsStatusDetector = new GpsStatusDetector(this);
        btn_turn_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGpsStatusDetector.checkGpsStatus();
//                turnOnGps();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGpsSwitchStateReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGpsSwitchStateReceiver);
    }
    private BroadcastReceiver mGpsSwitchStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
                Log.d("Gps Check:","Is gps enabled: " + isGpsEnabled(TurnOnGpsLocation.this));
                if (!isGpsEnabled(TurnOnGpsLocation.this)) {
                    mGpsStatusDetector.checkGpsStatus();
                }
            }
        }
    };

    private boolean isGpsEnabled(Activity activity) {
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    public void turnOnGps() {
        startActivity(new Intent(getApplicationContext(),LocationUpdateActivity.class));
        mGpsStatusDetector.checkGpsStatus();
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mGpsStatusDetector.checkOnActivityResult(requestCode, resultCode);
    }*/

    @Override
    public void onGpsSettingStatus(boolean enabled) {
        Log.d("TAG", "onGpsSettingStatus: " + enabled);
        mISGpsStatusDetector = enabled;
        if(mISGpsStatusDetector = enabled){
            startActivity(new Intent(getApplicationContext(),LocationUpdateActivity.class));
        }
        if(!enabled){
            mGpsStatusDetector.checkGpsStatus();
        }else{
            startActivity(new Intent(getApplicationContext(),LocationUpdateActivity.class));
        }
        /*if(enabled){
            startActivity(new Intent(getApplicationContext(),LocationUpdateActivity.class));
        }*/
        showSnackBar(enabled ? "GPS enabled" : "GPS disabled");
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

    private void showSnackBar(String text) {
        Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(TurnOnGpsLocation.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Are you Sure do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
