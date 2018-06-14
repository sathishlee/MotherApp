package com.unicef.thaimai.motherapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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

        AlertDialog.Builder builder = new AlertDialog.Builder(NoInternetConnection.this);
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
