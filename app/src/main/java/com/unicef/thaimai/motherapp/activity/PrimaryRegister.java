package com.unicef.thaimai.motherapp.activity;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.Presenter.PrimaryRegisterPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.view.PrimaryRegisterViews;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class PrimaryRegister extends AppCompatActivity implements View.OnClickListener, PrimaryRegisterViews, AdapterView.OnItemSelectedListener {
    TextView txtMotherName, txtMotherAge;
    EditText edtLmpDate, edtEddDate, edtAgeAtMarriage, edtRegWeek, edtANTT1st, edtANTT2nd, edtFIAStartDate, edtHeight,
            edtOthers, edtMedicationSpecify, edtAllergictoDrugsSpecify,edt_primary_mobile_number,edt_alternative_mobile_number;
    Spinner spMotherOcc, spHusbandOcc, spConsangulneousMarriage, spHistoryIllness, spHistoryIllnessFmly, spAnySurgeryBefore,
            spDoseTobacco, spDoseAlcohol, spDoseOnAnyMedication, spDoseAllergictoDrugs,
            spPrePregnancy, spLSCSDone, spComDuringPrgncy,
            spPrePrgncyG, spPrePrgncyP, spPrePrgncyA, spPrePrgncyL, spBloodGroup, spHIV, spVDRL,
            spHelpatitis, spHusbBloodGroup, spHusbHIV, spHusbVDRL, spHusbHelpatitis;

    String  strMotherOcc, strHusbandOcc, strConsangulneousMarriage, strHistoryIllness, strHistoryIllnessFmly, strAnySurgeryBefore,
            strDoseTobacco, strDoseAlcohol, strDoseOnAnyMedication, strDoseAllergictoDrugs,
            strPrePregnancy, strLSCSDone, strComDuringPrgncy,
            strPrePrgncyG, strPrePrgncyP, strPrePrgncyA, strPrePrgncyL, strBloodGroup, strHIV, strVDRL,
            strHelpatitis, strHusbBloodGroup, strHusbHIV, strHusbVDRL, strHusbHelpatitis,
            strLmpDate, strEddDate, strAgeAtMarriage, strRegWeek, strANTT1st, strANTT2nd, strFIAStartDate, strHeight,
            strOthers, strMedicationSpecify, strAllergictoDrugsSpecify,strPrimaryMobileNumber,strAlternativeMobileNumber;

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
        OnItemSelectedListener();
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
        edt_primary_mobile_number = (EditText) findViewById(R.id.edt_primary_mobile_number);
        edt_alternative_mobile_number = (EditText) findViewById(R.id.edt_alternative_mobile_number);

        spConsangulneousMarriage = (Spinner) findViewById(R.id.sp_consangulneous_marriage);
        spHistoryIllness = (Spinner) findViewById(R.id.sp_history_illness);
        spHistoryIllnessFmly = (Spinner) findViewById(R.id.sp_historyIllness_fmly);
        spAnySurgeryBefore = (Spinner) findViewById(R.id.sp_any_surgery_before);
        spDoseTobacco = (Spinner) findViewById(R.id.sp_dose_tobacco);
        spDoseAlcohol = (Spinner) findViewById(R.id.sp_dose_alcohol);

        edtOthers = (EditText) findViewById(R.id.edt_others);
        edtMedicationSpecify = (EditText) findViewById(R.id.edt_edication_specify);
        edtAllergictoDrugsSpecify = (EditText) findViewById(R.id.edt_allergicto_drugs_specify);

        spDoseOnAnyMedication = (Spinner) findViewById(R.id.sp_dose_on_any_medication);
        spDoseAllergictoDrugs = (Spinner) findViewById(R.id.sp_dose_allergicto_drugs);
        spPrePregnancy = (Spinner) findViewById(R.id.sp_pre_pregnancy);
        spLSCSDone = (Spinner) findViewById(R.id.sp_lscs_done);
        spComDuringPrgncy = (Spinner) findViewById(R.id.sp_comDuring_prgncy);
        spPrePrgncyG = (Spinner) findViewById(R.id.sp_pre_prgncy_g);
        spPrePrgncyP = (Spinner) findViewById(R.id.sp_pre_prgncy_p);
        spPrePrgncyA = (Spinner) findViewById(R.id.sp_pre_prgncy_a);
        spPrePrgncyL = (Spinner) findViewById(R.id.sp_pre_prgncy_l);

        edtRegWeek = (EditText) findViewById(R.id.edt_reg_week);
        edtANTT1st = (EditText) findViewById(R.id.edt_antt1);
        edtANTT2nd = (EditText) findViewById(R.id.edt_antt2);
        edtFIAStartDate = (EditText) findViewById(R.id.edt_fias_tart_date);
        edtHeight = (EditText) findViewById(R.id.edt_height);

        spBloodGroup = (Spinner) findViewById(R.id.sp_blood_group);
        spHIV = (Spinner) findViewById(R.id.sp_hiv);
        spVDRL = (Spinner) findViewById(R.id.sp_vdrl);
        spHelpatitis = (Spinner) findViewById(R.id.sp_helpatitis);
        spHusbBloodGroup = (Spinner) findViewById(R.id.sp_husb_blood_group);
        spHusbHIV = (Spinner) findViewById(R.id.sp_husb_hiv);
        spHusbVDRL = (Spinner) findViewById(R.id.sp_husb_vdrl);
        spHusbHelpatitis = (Spinner) findViewById(R.id.sp_husb_helpatitis);

        butSubmit = (Button) findViewById(R.id.btn_submit);

    }

    private void onClickListner() {
        butSubmit.setOnClickListener(this);
    }


    private void OnItemSelectedListener() {
        spMotherOcc.setOnItemSelectedListener(this);
        spHusbandOcc.setOnItemSelectedListener(this);
        spConsangulneousMarriage.setOnItemSelectedListener(this);
        spHistoryIllness.setOnItemSelectedListener(this);
        spHistoryIllnessFmly.setOnItemSelectedListener(this);
        spAnySurgeryBefore.setOnItemSelectedListener(this);
        spDoseTobacco.setOnItemSelectedListener(this);
        spDoseAlcohol.setOnItemSelectedListener(this);
        spDoseOnAnyMedication.setOnItemSelectedListener(this);
        spDoseAllergictoDrugs.setOnItemSelectedListener(this);
        spPrePregnancy.setOnItemSelectedListener(this);
        spLSCSDone.setOnItemSelectedListener(this);
        spComDuringPrgncy.setOnItemSelectedListener(this);
        spPrePrgncyG.setOnItemSelectedListener(this);
        spPrePrgncyP.setOnItemSelectedListener(this);
        spPrePrgncyA.setOnItemSelectedListener(this);
        spPrePrgncyL.setOnItemSelectedListener(this);
        spBloodGroup.setOnItemSelectedListener(this);
        spHIV.setOnItemSelectedListener(this);
        spVDRL.setOnItemSelectedListener(this);
        spHelpatitis.setOnItemSelectedListener(this);
        spHusbBloodGroup.setOnItemSelectedListener(this);
        spHusbHIV.setOnItemSelectedListener(this);
        spHusbVDRL.setOnItemSelectedListener(this);
        spHusbHelpatitis.setOnItemSelectedListener(this);
    }

    private void getAllValues() {
        strLmpDate = edtLmpDate.getText().toString();
        strEddDate = edtEddDate.getText().toString();
        strAgeAtMarriage = edtAgeAtMarriage.getText().toString();
        strRegWeek = edtRegWeek.getText().toString();
        strANTT1st = edtANTT1st.getText().toString();
        strANTT2nd = edtANTT2nd.getText().toString();
        strFIAStartDate = edtFIAStartDate.getText().toString();
        strHeight = edtHeight.getText().toString();
        strOthers = edtOthers.getText().toString();
        strMedicationSpecify = edtMedicationSpecify.getText().toString();
        strAllergictoDrugsSpecify = edtAllergictoDrugsSpecify.getText().toString();
        strPrimaryMobileNumber = edt_primary_mobile_number.getText().toString();
        strAlternativeMobileNumber = edt_alternative_mobile_number.getText().toString();


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
        switch (v.getId()) {
            case R.id.btn_submit:
                getAllValues();
                break;
        }
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
        Log.d(PrimaryRegister.class.getSimpleName(), "Success response" + response);
        setValuetoUI(response);
    }

    @Override
    public void getAllMotherPrimaryRegisterFailiur(String response) {
        Log.d(PrimaryRegister.class.getSimpleName(), "failiur" + response);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_hsbd_occ:
                strMotherOcc = parent.getSelectedItem().toString();
                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.sp_mthr_occ:
                strMotherOcc = parent.getSelectedItem().toString();
                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.sp_consangulneous_marriage:
                strConsangulneousMarriage = parent.getSelectedItem().toString();
                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.sp_history_illness:
                strHistoryIllness = parent.getSelectedItem().toString();
                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.sp_historyIllness_fmly:
                strHistoryIllnessFmly = parent.getSelectedItem().toString();
                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.sp_any_surgery_before:
                strAnySurgeryBefore = parent.getSelectedItem().toString();
                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.sp_dose_tobacco:
                strDoseTobacco = parent.getSelectedItem().toString();
                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;


            case R.id.sp_dose_alcohol:
                strDoseAlcohol = parent.getSelectedItem().toString();
                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;


            case R.id.sp_dose_on_any_medication:
                strDoseOnAnyMedication = parent.getSelectedItem().toString();
                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;


            case R.id.sp_dose_allergicto_drugs:
                strDoseAllergictoDrugs = parent.getSelectedItem().toString();
                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;


            case R.id.sp_pre_pregnancy:
                strPrePregnancy = parent.getSelectedItem().toString();
                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;


            case R.id.sp_lscs_done:
                strLSCSDone = parent.getSelectedItem().toString();
                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;


            case R.id.sp_comDuring_prgncy:
                strComDuringPrgncy = parent.getSelectedItem().toString();
                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;


            case R.id.sp_pre_prgncy_g:
                strPrePrgncyG = parent.getSelectedItem().toString();
                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.sp_pre_prgncy_p:
                strPrePrgncyP = parent.getSelectedItem().toString();

                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;


            case R.id.sp_pre_prgncy_l:
                strPrePrgncyL = parent.getSelectedItem().toString();

                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.sp_pre_prgncy_a:
                strPrePrgncyA = parent.getSelectedItem().toString();

                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;


            case R.id.sp_blood_group:
                strBloodGroup = parent.getSelectedItem().toString();
                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.sp_hiv:
                strHIV = parent.getSelectedItem().toString();
                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;


            case R.id.sp_vdrl:
                strVDRL = parent.getSelectedItem().toString();
                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.sp_helpatitis:
                strHelpatitis = parent.getSelectedItem().toString();
                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;


            case R.id.sp_husb_blood_group:
                strHusbBloodGroup = parent.getSelectedItem().toString();

                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.sp_husb_hiv:
                strHusbHIV = parent.getSelectedItem().toString();

                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;


            case R.id.sp_husb_vdrl:
                strHusbVDRL = parent.getSelectedItem().toString();

                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.sp_husb_helpatitis:
                strHusbHelpatitis = parent.getSelectedItem().toString();

                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setValuetoUI(String response) {
        JSONObject jObj = null;
        try {
            jObj = new JSONObject(response);
            int status = jObj.getInt("status");
            String message = jObj.getString("message");
            if (status==1){
                Log.d("message---->",message);
               /* txtMotherName.setText(jObj.getString("mName"));
                txtMotherAge.setText(jObj.getString("mAge"));
                edtLmpDate.setText(jObj.getString("mLMP"));
                edtEddDate.setText(jObj.getString("mEDD"));
                edtAgeAtMarriage.setText(jObj.getString("mAgeatMarriage"));
                edtRegWeek.setText(jObj.getString("mRegistrationWeek"));
                edtANTT1st.setText(jObj.getString("mANTT1"));
                edtANTT2nd.setText(jObj.getString("mANTT2"));
                edtFIAStartDate.setText(jObj.getString("mIFAStateDate"));
                spMotherOcc.setPrompt("mMotherOccupation");
                spHusbandOcc.setPrompt("mHusbandOccupation");*/
            }else{
                Log.d("message---->",message);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
