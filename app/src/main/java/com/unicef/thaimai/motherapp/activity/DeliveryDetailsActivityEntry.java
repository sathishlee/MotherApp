package com.unicef.thaimai.motherapp.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.view.DeliveryEntryViews;

public class DeliveryDetailsActivityEntry extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, DeliveryEntryViews  {

    EditText edt_delivery_date,edt_time_of_delivery, edt_infant_id, edt_infant_weight, edt_infant_height, edt_new_born_birth_date,
            edt_bcg_given_date,edt_opv_given_date, edt_hepb_given_date;

    Spinner sp_place, sp_delivery_details, sp_mother, sp_newborn, sp_birth_details, sp_breast_feeding_given, sp_admitted_in_sncu,
            sp_sncu_name,sp_outcome;

    String strDeliveryDate, strDeliveryTime, strPlace, strDeliveryDetails, strMotherOutcome, strNewbornOutcome,
    strInfantID, strBirthdetails, strInfantWeight, strInfantHeight, strBreastFeeding, strAdmittedSNCU, strSNCUName,
    str
    Button btn_delivery_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details_entry);
        showActionBar();
        initUI();
        onClickListner();
        OnItemSelectedListener();




    }

    public void showActionBar(){
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Enter Delivery Details");

        actionBar.setHomeButtonEnabled(true);

        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    public void initUI(){

        edt_delivery_date = (EditText) findViewById(R.id.edt_delivery_date);
        edt_time_of_delivery = (EditText) findViewById(R.id.edt_time_of_delivery);
        edt_infant_id = (EditText) findViewById(R.id.edt_infant_id);
        edt_infant_weight = (EditText) findViewById(R.id.edt_infant_weight);
        edt_infant_height = (EditText) findViewById(R.id.edt_infant_height);
        edt_new_born_birth_date = (EditText) findViewById(R.id.edt_new_born_birth_date);
        edt_bcg_given_date = (EditText) findViewById(R.id.edt_bcg_given_date);
        edt_opv_given_date = (EditText) findViewById(R.id.edt_opv_given_date);
        edt_hepb_given_date = (EditText) findViewById(R.id.edt_hepb_given_date);

        btn_delivery_submit = (Button) findViewById(R.id.btn_delivery_submit);

        sp_place = (Spinner) findViewById(R.id.sp_place);
        sp_delivery_details = (Spinner) findViewById(R.id.sp_delivery_details);
        sp_mother = (Spinner) findViewById(R.id.sp_mother);
        sp_newborn = (Spinner) findViewById(R.id.sp_newborn);
        sp_birth_details = (Spinner) findViewById(R.id.sp_birth_details);
        sp_breast_feeding_given = (Spinner) findViewById(R.id.sp_breast_feeding_given);
        sp_admitted_in_sncu = (Spinner) findViewById(R.id.sp_admitted_in_sncu);
        sp_sncu_name = (Spinner) findViewById(R.id.sp_sncu_name);
        sp_outcome = (Spinner) findViewById(R.id.sp_outcome);



    }

    public void onClickListner(){
        btn_delivery_submit.setOnClickListener(this);

    }
    public void OnItemSelectedListener(){
        sp_place.setOnItemSelectedListener(this);
        sp_delivery_details.setOnItemSelectedListener(this);
        sp_mother.setOnItemSelectedListener(this);
        sp_newborn.setOnItemSelectedListener(this);
        sp_birth_details.setOnItemSelectedListener(this);
        sp_breast_feeding_given.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(DeliveryDetailsActivityEntry.this, MainActivity.class);
        finish();
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delivery_submit:
                datatosever();
                break;
        }

    }
    public void datatosever(){

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void deliveryentrySuccess(String response) {

    }

    @Override
    public void deliveryentryFailiure(String response) {

    }
}
