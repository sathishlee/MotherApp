package com.unicef.thaimai.motherapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.utility.CheckNetwork;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class NoInternetConnection extends AppCompatActivity {
    CheckNetwork checkNetwork;
    Button btn_refresh;
    PreferenceData preferenceData;
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_internet_connection);
        initUI();
    }

    private void initUI() {
        checkNetwork = new CheckNetwork(this);
        preferenceData = new PreferenceData(this);

        btn_refresh = (Button) findViewById(R.id.btn_refresh);
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                internet();
            }
        });
    }

    private void internet() {
        if(checkNetwork.isNetworkAvailable()) {
            startActivity(new Intent(getApplicationContext(),LocationUpdateActivity.class));

        }
        else{
            startActivity(new Intent(getApplicationContext(),NoInternetConnection.class));
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if(doubleBackToExitPressedOnce){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            startActivity(intent);
            finish();
            System.exit(0);
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
