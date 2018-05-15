package com.unicef.thaimai.motherapp.activity;

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
}
