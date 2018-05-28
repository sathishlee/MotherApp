package com.unicef.thaimai.motherapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.unicef.thaimai.motherapp.R;


/**
 * Created by Suthishan on 20/1/2018.
 */

public class LowerVersionActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_close;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lower_version);
        initUi();
        onClickUI();

    }

    private void onClickUI() {
        btn_close.setOnClickListener(this);
    }

    private void initUi() {
        btn_close = (Button) findViewById(R.id.btn_close);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close:
                closeApp();
                break;
        }
    }

    private void closeApp() {
            AlertDialog.Builder builder = new AlertDialog.Builder(LowerVersionActivity.this);
            builder.setTitle(R.string.app_name);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage("Your Android Device Does not Support this Application...!")
                    .setCancelable(false)
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
    }
}
