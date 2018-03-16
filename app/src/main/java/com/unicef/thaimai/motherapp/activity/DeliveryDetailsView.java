package com.unicef.thaimai.motherapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.unicef.thaimai.motherapp.R;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class DeliveryDetailsView extends AppCompatActivity{

    FloatingActionButton fab_edi_details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_baby_delivery_details);
        initUI();
        onClickListner();

    }

    private void onClickListner() {
        fab_edi_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DeliveryDetailsActivityEntry.class));
            }
        });
    }

   private void initUI(){

   }
}
