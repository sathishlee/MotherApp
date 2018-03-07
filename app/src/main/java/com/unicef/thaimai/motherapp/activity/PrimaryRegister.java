package com.unicef.thaimai.motherapp.activity;

import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.constant.AppConstants;


public class PrimaryRegister extends AppCompatActivity {
    TextView txtMotherName, txtMotherAge;
    EditText edtLmpDate, edtEddDate, edtAgeAtMarriage, edtRegWeek, edtANTT1st, edtANTT2nd, edtFIAStartDate, edtHeight;
    Spinner spMotherOcc, spHusbandOcc, spConsangulneousMarriage, spHistoryIllness,spHistoryIllnessFmly, spAnySurgeryBefore,
            spDoseTobacco, spDoseAlcohol, spDoseOnAnyMedication, spDoseAllergictoDrugs,
            spPrePregnancy, spLSCSDone, spComDuringPrgncy,
            spPrePrgncyG, spPrePrgncyP, spPrePrgncyA, spPrePrgncyL, spBloodGroup, spHIV, spVDRL, spHelpatitis, spHusbBloodGroup, spHusbHIV, spHusbVDRL, spHusbHelpatitis;
    Button butSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.primary_register);
        showActionBar();
        initUI();
        onClickListner();
    }

    private void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Primary Register");
        if (AppConstants.BACK_BUTTON_GONE) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    private void initUI() {
        txtMotherName = (TextView) findViewById(R.id.txt_name);
        txtMotherAge = (TextView) findViewById(R.id.txt_mother_age);
        edtLmpDate = (EditText) findViewById(R.id.edt_lmp_date);
        edtEddDate = (EditText) findViewById(R.id.edt_edd_date);

        spMotherOcc = (Spinner) findViewById(R.id.sp_mthr_occ);
        spHusbandOcc = (Spinner) findViewById(R.id.sp_hsbd_occ);

        edtAgeAtMarriage = (EditText) findViewById(R.id.edt_age_at_marriage);

        spConsangulneousMarriage = (Spinner) findViewById(R.id.sp_consangulneous_marriage);
        spHistoryIllness = (Spinner) findViewById(R.id.sp_history_illness);
        spHistoryIllnessFmly = (Spinner) findViewById(R.id.sp_historyIllness_fmly);
        spAnySurgeryBefore = (Spinner) findViewById(R.id.sp_any_surgery_before);

    }

    private void onClickListner() {

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(PrimaryRegister.this, MainActivity.class);
        finish();
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
