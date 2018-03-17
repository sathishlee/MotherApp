package com.unicef.thaimai.motherapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.unicef.thaimai.motherapp.R;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class DeliveryDetailsView extends AppCompatActivity{

    TextView txt_delivery_date, txt_delivery_time, txt_delivery_place, txt_delivery_type, txt_delivery_mother_outcome,
            txt_delivery_new_born, txt_infant_height, txt_infant_weight, txt_infant_id, txt_infant_birth_type,
            txt_breast_feeding_given, txt_admitted_in_sncu, new_born_sncu_date, txt_new_born_outcome, txt_bcg_given_date,
            txt_opv_given_date, txt_hepb_given_date;

    ProgressDialog progressDialog;

    FloatingActionButton fab_edi_details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details_view);
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

       progressDialog = new ProgressDialog(this);
       progressDialog.setCancelable(false);
       progressDialog.setMessage("Please Wait ...");


       txt_delivery_date = (TextView) findViewById(R.id.txt_delivery_date);
       txt_delivery_time = (TextView) findViewById(R.id.txt_delivery_time);
       txt_delivery_place = (TextView) findViewById(R.id.txt_delivery_place);
       txt_delivery_type = (TextView) findViewById(R.id.txt_delivery_type);
       txt_delivery_mother_outcome = (TextView) findViewById(R.id.txt_delivery_mother_outcome);
       txt_delivery_new_born = (TextView) findViewById(R.id.txt_delivery_new_born);
       txt_infant_height = (TextView) findViewById(R.id.txt_infant_height);
       txt_infant_weight = (TextView) findViewById(R.id.txt_infant_weight);
       txt_infant_id = (TextView) findViewById(R.id.txt_infant_id);
       txt_infant_birth_type = (TextView) findViewById(R.id.txt_infant_birth_type);
       txt_breast_feeding_given = (TextView) findViewById(R.id.txt_breast_feeding_given);
       txt_admitted_in_sncu = (TextView) findViewById(R.id.txt_admitted_in_sncu);
       new_born_sncu_date = (TextView) findViewById(R.id.new_born_sncu_date);
       txt_new_born_outcome = (TextView) findViewById(R.id.txt_new_born_outcome);
       txt_bcg_given_date = (TextView) findViewById(R.id.txt_bcg_given_date);
       txt_opv_given_date = (TextView) findViewById(R.id.txt_opv_given_date);
       txt_hepb_given_date = (TextView) findViewById(R.id.txt_hepb_given_date);

   }
}
