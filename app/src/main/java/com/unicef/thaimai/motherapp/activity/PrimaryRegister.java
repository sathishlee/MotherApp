package com.unicef.thaimai.motherapp.activity;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.unicef.thaimai.motherapp.Presenter.PrimaryRegisterPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.view.PrimaryRegisterViews;


public class PrimaryRegister extends AppCompatActivity implements View.OnClickListener, PrimaryRegisterViews {
    TextView txtMotherName, txtMotherAge;
    EditText edtLmpDate, edtEddDate, edtAgeAtMarriage, edtRegWeek, edtANTT1st, edtANTT2nd, edtFIAStartDate, edtHeight,
            edtOthers,edtMedicationSpecify,edtAllergictoDrugsSpecify;
    Spinner spMotherOcc, spHusbandOcc, spConsangulneousMarriage, spHistoryIllness,spHistoryIllnessFmly, spAnySurgeryBefore,
            spDoseTobacco, spDoseAlcohol, spDoseOnAnyMedication, spDoseAllergictoDrugs,
            spPrePregnancy, spLSCSDone, spComDuringPrgncy,
            spPrePrgncyG, spPrePrgncyP, spPrePrgncyA, spPrePrgncyL, spBloodGroup, spHIV, spVDRL,
            spHelpatitis, spHusbBloodGroup, spHusbHIV, spHusbVDRL, spHusbHelpatitis;
    Button butSubmit;
    ProgressDialog pDialog;

    PrimaryRegisterPresenter primaryRegisterPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.primary_register);
        showActionBar();
        initUI();
        onClickListner();
        getAllValues();
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
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        primaryRegisterPresenter = new PrimaryRegisterPresenter(PrimaryRegister.this, this);
        primaryRegisterPresenter.getAllMotherPrimaryRegistration("1000000000001");
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
        spDoseTobacco = (Spinner) findViewById(R.id.sp_dose_tobacco);
        spDoseAlcohol = (Spinner) findViewById(R.id.sp_dose_alcohol);

        edtOthers = (EditText) findViewById(R.id.edt_others);
        edtMedicationSpecify = (EditText) findViewById(R.id.edt_edication_specify);
        edtAllergictoDrugsSpecify = (EditText) findViewById(R.id.edt_allergicto_drugs_specify);

        spDoseOnAnyMedication =(Spinner) findViewById(R.id.sp_dose_on_any_medication);
        spDoseAllergictoDrugs =(Spinner) findViewById(R.id.sp_dose_allergicto_drugs);
        spPrePregnancy =(Spinner) findViewById(R.id.sp_pre_pregnancy);
        spLSCSDone =(Spinner) findViewById(R.id.sp_lscs_done);
        spComDuringPrgncy =(Spinner) findViewById(R.id.sp_comDuring_prgncy);
        spPrePrgncyG =(Spinner) findViewById(R.id.sp_pre_prgncy_g);
        spPrePrgncyP =(Spinner) findViewById(R.id.sp_pre_prgncy_p);
        spPrePrgncyA =(Spinner) findViewById(R.id.sp_pre_prgncy_a);
        spPrePrgncyL =(Spinner) findViewById(R.id.sp_pre_prgncy_l);

        edtRegWeek = (EditText) findViewById(R.id.edt_reg_week);
        edtANTT1st = (EditText) findViewById(R.id.edt_antt1);
        edtANTT2nd = (EditText) findViewById(R.id.edt_antt2);
        edtFIAStartDate = (EditText) findViewById(R.id.edt_fias_tart_date);
        edtHeight = (EditText) findViewById(R.id.edt_height);

        spBloodGroup =(Spinner) findViewById(R.id.sp_blood_group);
        spHIV =(Spinner) findViewById(R.id.sp_hiv);
        spVDRL =(Spinner) findViewById(R.id.sp_vdrl);
        spHelpatitis =(Spinner) findViewById(R.id.sp_helpatitis);
        spHusbBloodGroup =(Spinner) findViewById(R.id.sp_husb_blood_group);
        spHusbHIV =(Spinner) findViewById(R.id.sp_husb_hiv);
        spHusbVDRL =(Spinner) findViewById(R.id.sp_husb_vdrl);
        spHusbHelpatitis =(Spinner) findViewById(R.id.sp_husb_helpatitis);

        butSubmit = (Button) findViewById(R.id.btn_submit);

    }

    private void onClickListner() {
        butSubmit.setOnClickListener(this);
    }
    private void getAllValues() {

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(PrimaryRegister.this, MainActivity.class);
        finish();
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }





    @Override
    public void onClick(View v) {

    }

    @Override
    public void showProgress() {
        pDialog.show();
    }

    @Override
    public void hideProgress() {
        pDialog.hide();

    }

    @Override
    public void getAllMotherPrimaryRegisterSuccess(String response) {
        Log.d(PrimaryRegister.class.getSimpleName(),"Success response"+response);
    }

    @Override
    public void getAllMotherPrimaryRegisterFailiur(String response) {
        Log.d(PrimaryRegister.class.getSimpleName(),"failiur"+response);

    }
}
