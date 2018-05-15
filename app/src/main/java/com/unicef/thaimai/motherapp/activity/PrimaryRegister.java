    package com.unicef.thaimai.motherapp.activity;

    import android.annotation.SuppressLint;
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

    import com.unicef.thaimai.motherapp.Preference.PreferenceData;
    import com.unicef.thaimai.motherapp.Presenter.PrimaryRegisterPresenter;
    import com.unicef.thaimai.motherapp.R;
    import com.unicef.thaimai.motherapp.constant.AppConstants;
    import com.unicef.thaimai.motherapp.model.requestmodel.PrimaryDataRequestModel;
    import com.unicef.thaimai.motherapp.view.PrimaryRegisterViews;

    import org.json.JSONException;
    import org.json.JSONObject;

    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.HashMap;
    import java.util.List;


    public class PrimaryRegister extends AppCompatActivity implements View.OnClickListener, PrimaryRegisterViews, AdapterView.OnItemSelectedListener {

        TextView txtMotherName, txtMotherAge;
        EditText edtLmpDate, edtEddDate, edtAgeAtMarriage, edtRegWeek, edtANTT1st, edtANTT2nd, edtFIAStartDate, edtHeight,
                edtOthers, edtMedicationSpecify, edtAllergictoDrugsSpecify, edt_primary_mobile_number, edt_alternative_mobile_number,
                edt_history_illness,edt_history_illness_fmly,edt_any_surgery_before,edt_comDuring_prgncy;

        Spinner spMotherOcc, spHusbandOcc, spConsangulneousMarriage, spHistoryIllness, spHistoryIllnessFmly, spAnySurgeryBefore,
                spDoseTobacco, spDoseAlcohol, spDoseOnAnyMedication, spDoseAllergictoDrugs,
                spPrePregnancy, spLSCSDone, spComDuringPrgncy,
                spPrePrgncyG, spPrePrgncyP, spPrePrgncyA, spPrePrgncyL, spBloodGroup, spHIV, spVDRL,
                spHelpatitis, spHusbBloodGroup, spHusbHIV, spHusbVDRL, spHusbHelpatitis;

        public String strId, strMasterId, strPicmeId, strMotherName, strMotherAge, strMotherOcc, strHusbandOcc, strConsangulneousMarriage, strHistoryIllness, strHistoryIllnessFmly, strAnySurgeryBefore,
                strDoseTobacco, strDoseAlcohol, strDoseOnAnyMedication, strDoseAllergictoDrugs,
                strPrePregnancy, strLSCSDone, strComDuringPrgncy,
                strPrePrgncyG, strPrePrgncyP, strPrePrgncyA, strPrePrgncyL, strBloodGroup, strHIV, strVDRL,
                strHelpatitis, strHusbBloodGroup, strHusbHIV, strHusbVDRL, strHusbHelpatitis,
                strLmpDate, strEddDate, strAgeAtMarriage, strRegWeek, strANTT1st, strANTT2nd, strFIAStartDate, strHeight,
                strOthers, strMedicationSpecify, strAllergictoDrugsSpecify, strPrimaryMobileNumber, strAlternativeMobileNumber,
                strHistory_illness_other,strHhistory_illness_fmly_other,strAny_surgery_before_other,strAnyComDuring_prgncy_other;
ArrayList ysList,occList;
        Button butSubmit;
        ProgressDialog pDialog;

        PrimaryRegisterPresenter primaryRegisterPresenter;
        PrimaryDataRequestModel primaryDataRequestModel;
        PreferenceData preferenceData;

        String[] Occ = {"--Select--","Home Maker", "Private Sector", "Govt Sector"};
        String[] yn ={"--Select--","Yes","No"};
        String[] hdcdt ={"--Select--","Hypertention", "Diabetes", "Congenital Heart Disease", "Tb", "Others"};
        String[] hoaif = {"--Select--","Htn","DM","CHD","TB","Others"};
        String[] num = {"--Select--","1","2","3","4","5","6","7","8","9","10"};
        String[] bg = {"--Select--","A+ve","A-ve","B+ve","B-ve","O+ve","O-ve","AB+ve","AB-ve"};
        String [] rnr ={"--Select--","Reactive", "Non Reactive"};
        String[] acdp = {"--Select--","Hypertention", "Diabetes", "Congenital Heart Disease", "Tb", "Others"};

        @SuppressLint("ResourceType")
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
            preferenceData = new PreferenceData(this);
            strPicmeId = preferenceData.getPicmeId();
            strMotherName = preferenceData.getMotherName();
            strMotherAge = preferenceData.getMotherAge();
            primaryRegisterPresenter = new PrimaryRegisterPresenter(PrimaryRegister.this, this);
            primaryRegisterPresenter.getAllMotherPrimaryRegistration(strPicmeId);

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


            edt_history_illness = (EditText) findViewById(R.id.edt_other_history_illness);
            edt_history_illness_fmly = (EditText) findViewById(R.id.edt_history_illness_fmly);
            edt_any_surgery_before = (EditText) findViewById(R.id.edt_any_surgery_before);
            edt_comDuring_prgncy = (EditText) findViewById(R.id.edt_comDuring_prgncy);

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

        private void getallEditTextvalues() {
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
            strHistory_illness_other =edt_history_illness.getText().toString();
            strHhistory_illness_fmly_other =edt_history_illness_fmly.getText().toString();
            strAny_surgery_before_other =edt_any_surgery_before.getText().toString();
            strAnyComDuring_prgncy_other =edt_comDuring_prgncy.getText().toString();
        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            finish();
            return super.onOptionsItemSelected(item);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_submit:
                    sendtoServer();
                    break;
            }
        }

        private void sendtoServer() {
            getallEditTextvalues();

            if (strMasterId.equalsIgnoreCase("")){
                showAlert("MasterId is Empty");
            }else if (strId.equalsIgnoreCase("")){
                showAlert("Id is Empty");

            }else if (preferenceData.getPicmeId().equalsIgnoreCase("")){
                showAlert("Id is Empty");

            }else if (preferenceData.getMotherName().equalsIgnoreCase("")){
                showAlert("Id is Empty");

            }else if (preferenceData.getMotherAge().equalsIgnoreCase("")){
                showAlert("Id is Empty");

            }
            else if (strLmpDate.equalsIgnoreCase("")){
                showAlert("LMP is Empty");

            }else if (strEddDate.equalsIgnoreCase("")){
                showAlert("EDD is Empty");

            }else if (strPrimaryMobileNumber.equalsIgnoreCase("")){
                showAlert("Primary Mobile Number is Empty");

            }else if (strAlternativeMobileNumber.equalsIgnoreCase("")){
                showAlert("Alternative Mobile Number is Empty");

            }else if (strMotherOcc.equalsIgnoreCase("--Select--")){
                showAlert("Mother Occupation is Empty");

            }else if (strHusbandOcc.equalsIgnoreCase("--Select--")){
                showAlert("Husband Occupation is Empty");

            }else if (strAgeAtMarriage.equalsIgnoreCase("")){
                showAlert("Age at Marriage is Empty");

            }else if (strConsangulneousMarriage.equalsIgnoreCase("--Select--")){
                showAlert("Consangulneous Marriage is Empty");

            }else if (strHistoryIllness.equalsIgnoreCase("--Select--")){
                showAlert("Id is Empty");

            }else if (strHistoryIllnessFmly.equalsIgnoreCase("--Select--")){
                showAlert("History of illness is Empty");

            }else if (strAnySurgeryBefore.equalsIgnoreCase("--Select--")){
                showAlert("Any Surgery Before is Empty");

            }else if (strDoseTobacco.equalsIgnoreCase("--Select--")){
                showAlert("Tobacco is Empty");

            }else if (strDoseAlcohol.equalsIgnoreCase("--Select--")){
                showAlert("Alcohol is Empty");

            }else if (strDoseOnAnyMedication.equalsIgnoreCase("--Select--")){
                showAlert("On any Medication is Empty");

            }else if (strDoseAllergictoDrugs.equalsIgnoreCase("--Select--")){
                showAlert("Allergic to Drug is Empty");

            }else if (strPrePregnancy.equalsIgnoreCase("--Select--")){
                showAlert("Pre Pregnancy is Empty");

            }else if (strLSCSDone.equalsIgnoreCase("--Select--")){
                showAlert("LSC Done is Empty");

            }else if (strComDuringPrgncy.equalsIgnoreCase("--Select--")){
                showAlert("During Pregnancy is Empty");

            }else if (strPrePrgncyG.equalsIgnoreCase("--Select--")){
                showAlert("G is Empty");

            }else if (strPrePrgncyP.equalsIgnoreCase("--Select--")){
                showAlert("P is Empty");

            }else if (strPrePrgncyA.equalsIgnoreCase("--Select--")){
                showAlert("A is Empty");

            }else if (strPrePrgncyL.equalsIgnoreCase("--Select--")){
                showAlert("L is Empty");

            }else if (strRegWeek.equalsIgnoreCase("")){
                showAlert("Registration Week is Empty");

            }else if (strANTT1st.equalsIgnoreCase("")){
                showAlert("AN TT1st is Empty");

            }else if (strANTT2nd.equalsIgnoreCase("")){
                showAlert("AN TT2nd is Empty");

            }
            else if (strFIAStartDate.equalsIgnoreCase("")){
                showAlert("FIA Start Date is Empty");

            }else if (strHeight.equalsIgnoreCase("")){
                showAlert("Height is Empty");

            }else if (strBloodGroup.equalsIgnoreCase("--Select--")){
                showAlert("Blood Group is Empty");

            }else if (strHIV.equalsIgnoreCase("--Select--")){
                showAlert("HIV is Empty");

            }else if (strVDRL.equalsIgnoreCase("--Select--")){
                showAlert("VDRL is Empty");

            }else if (strHelpatitis.equalsIgnoreCase("--Select--")){
                showAlert("Helpatitis is Empty");

            }else if (strHusbBloodGroup.equalsIgnoreCase("--Select--")){
                showAlert("Husband Blood Group is Empty");

            }else if (strHusbHIV.equalsIgnoreCase("--Select--")){
                showAlert("Husband Hiv is Empty");

            }else if (strHusbVDRL.equalsIgnoreCase("--Select--")){
                showAlert("Husband VDRL is Empty");

            }else if (strHusbHelpatitis.equalsIgnoreCase("--Select--")){
                showAlert("HUSBAND Helpatities is Empty");

            }
            else {
                primaryDataRequestModel = new PrimaryDataRequestModel();
                primaryDataRequestModel.setMasterId(strMasterId);
                primaryDataRequestModel.setMid(strId);
                primaryDataRequestModel.setMPicmeId(preferenceData.getPicmeId());
                primaryDataRequestModel.setMName(preferenceData.getMotherName());
                primaryDataRequestModel.setMAge(preferenceData.getMotherAge());
                primaryDataRequestModel.setMLMP(strLmpDate);
                primaryDataRequestModel.setMEDD(strEddDate);
                primaryDataRequestModel.setMMotherMobile(strPrimaryMobileNumber);
                primaryDataRequestModel.setMHusbandMobile(strAlternativeMobileNumber);
                primaryDataRequestModel.setMMotherOccupation(strMotherOcc);
                primaryDataRequestModel.setMHusbandOccupation(strHusbandOcc);
                primaryDataRequestModel.setMAgeatMarriage(strAgeAtMarriage);
                primaryDataRequestModel.setMConsanguineousMarraige(strConsangulneousMarriage);
                primaryDataRequestModel.setMHistoryIllness(strHistoryIllness);
                primaryDataRequestModel.setMHistoryIllnessFamily(strHistoryIllnessFmly);
                primaryDataRequestModel.setMAnySurgeryBefore(strAnySurgeryBefore);
                primaryDataRequestModel.setMUseTobacco(strDoseTobacco);
                primaryDataRequestModel.setMUseAlcohol(strDoseAlcohol);
                primaryDataRequestModel.setMAnyMeditation(strDoseOnAnyMedication);
                primaryDataRequestModel.setMAllergicToanyDrug(strDoseAllergictoDrugs);
                primaryDataRequestModel.setMHistroyPreviousPreganancy(strPrePregnancy);
                primaryDataRequestModel.setMLscsDone(strLSCSDone);
                primaryDataRequestModel.setMAnyComplecationDuringPreganancy(strComDuringPrgncy);
                primaryDataRequestModel.setMPresentPreganancyG(strPrePrgncyG);
                primaryDataRequestModel.setMPresentPreganancyP(strPrePrgncyP);
                primaryDataRequestModel.setMPresentPreganancyA(strPrePrgncyA);
                primaryDataRequestModel.setMPresentPreganancyL(strPrePrgncyL);
                primaryDataRequestModel.setMRegistrationWeek(strRegWeek);
                primaryDataRequestModel.setMANTT1(strANTT1st);
                primaryDataRequestModel.setMANTT2(strANTT2nd);
                primaryDataRequestModel.setMIFAStateDate(strFIAStartDate);
                primaryDataRequestModel.setMHeight(strHeight);
                primaryDataRequestModel.setMBloodGroup(strBloodGroup);
                primaryDataRequestModel.setMHIV(strHIV);
                primaryDataRequestModel.setMVDRL(strVDRL);
                primaryDataRequestModel.setMHepatitis(strHelpatitis);
                primaryDataRequestModel.setHBloodGroup(strHusbBloodGroup);
                primaryDataRequestModel.setHHIV(strHusbHIV);
                primaryDataRequestModel.setHVDRL(strHusbVDRL);
                primaryDataRequestModel.setHHepatitis(strHusbHelpatitis);

                primaryDataRequestModel.setMHistoryIllnessOthers(strHistory_illness_other);
                primaryDataRequestModel.setMHistoryIllnessFamilyOthers(strHhistory_illness_fmly_other);
                primaryDataRequestModel.setMAnySurgeryBeforeOthers(strAny_surgery_before_other);
                primaryDataRequestModel.setMAnyComplecationDuringOthers(strAnyComDuring_prgncy_other);

                primaryRegisterPresenter.postprimaryData(strPicmeId, primaryDataRequestModel);
            }
        }

        private void showAlert(String msg) {
            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
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
        public void onDestroy(){
            super.onDestroy();
            if (pDialog!=null && pDialog.isShowing() ){
                pDialog.cancel();
            }
        }

        @Override
        public void getAllMotherPrimaryRegisterSuccess(String response) {
            Log.d(PrimaryRegister.class.getSimpleName(), "Success response" + response);
            setValuetoUI(response);
        }

        @Override
        public void getAllMotherPrimaryRegisterFailiur(String response) {
            Log.d(PrimaryRegister.class.getSimpleName(), "failure" + response);

        }

        @Override
        public void postDataSuccess(String response) {
            Log.d(PrimaryRegister.class.getSimpleName(), "Success post method" + response);
            startActivity(new Intent(getApplicationContext(),PrimaryRegisterView.class));
            finish();
        }

        @Override
        public void postDataFailiure(String response) {
            Log.d(PrimaryRegister.class.getSimpleName(), "fail post method" + response);
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.sp_hsbd_occ:
                    strHusbandOcc = parent.getSelectedItem().toString();
                    break;
                case R.id.sp_mthr_occ:
                    strMotherOcc = parent.getSelectedItem().toString();
                    break;

                case R.id.sp_consangulneous_marriage:
                    strConsangulneousMarriage = parent.getSelectedItem().toString();
                    break;

                case R.id.sp_history_illness:
                    strHistoryIllness = parent.getSelectedItem().toString();
                    if (strHistoryIllness.equalsIgnoreCase("Others")) {
                        edt_history_illness.setVisibility(View.VISIBLE);
                    }
                    else {
                        edt_history_illness.setVisibility(View.GONE);

                    }
                    break;

                case R.id.sp_historyIllness_fmly:
                    strHistoryIllnessFmly = parent.getSelectedItem().toString();
                    if (strHistoryIllnessFmly.equalsIgnoreCase("Others")) {
                        edt_history_illness_fmly.setVisibility(View.VISIBLE);
                    }
                    else {
                        edt_history_illness_fmly.setVisibility(View.GONE);

                    }
                    break;

                case R.id.sp_any_surgery_before:
                    strAnySurgeryBefore = parent.getSelectedItem().toString();
                    if (strAnySurgeryBefore.equalsIgnoreCase("Yes")) {
                        edt_any_surgery_before.setVisibility(View.VISIBLE);
                    }
                    else {
                        edt_any_surgery_before.setVisibility(View.GONE);

                    }
                    break;

                case R.id.sp_dose_tobacco:
                    strDoseTobacco = parent.getSelectedItem().toString();
                    break;


                case R.id.sp_dose_alcohol:
                    strDoseAlcohol = parent.getSelectedItem().toString();
                    break;


                case R.id.sp_dose_on_any_medication:
                    strDoseOnAnyMedication = parent.getSelectedItem().toString();
                    break;


                case R.id.sp_dose_allergicto_drugs:
                    strDoseAllergictoDrugs = parent.getSelectedItem().toString();
                    break;


                case R.id.sp_pre_pregnancy:
                    strPrePregnancy = parent.getSelectedItem().toString();
                    break;


                case R.id.sp_lscs_done:
                    strLSCSDone = parent.getSelectedItem().toString();
                    break;


                case R.id.sp_comDuring_prgncy:
                    strComDuringPrgncy = parent.getSelectedItem().toString();
                    if (strComDuringPrgncy.equalsIgnoreCase("Others")) {
                        edt_comDuring_prgncy.setVisibility(View.VISIBLE);
                    }
                    else {
                        edt_comDuring_prgncy.setVisibility(View.GONE);

                    }
                    break;


                case R.id.sp_pre_prgncy_g:
                    strPrePrgncyG = parent.getSelectedItem().toString();
                    break;

                case R.id.sp_pre_prgncy_p:
                    strPrePrgncyP = parent.getSelectedItem().toString();
                    break;


                case R.id.sp_pre_prgncy_l:
                    strPrePrgncyL = parent.getSelectedItem().toString();
                    break;

                case R.id.sp_pre_prgncy_a:
                    strPrePrgncyA = parent.getSelectedItem().toString();
                    break;


                case R.id.sp_blood_group:
                    strBloodGroup = parent.getSelectedItem().toString();
                    break;

                case R.id.sp_hiv:
                    strHIV = parent.getSelectedItem().toString();
                    break;


                case R.id.sp_vdrl:
                    strVDRL = parent.getSelectedItem().toString();
                    break;

                case R.id.sp_helpatitis:
                    strHelpatitis = parent.getSelectedItem().toString();
                    break;


                case R.id.sp_husb_blood_group:
                    strHusbBloodGroup = parent.getSelectedItem().toString();
                    break;

                case R.id.sp_husb_hiv:
                    strHusbHIV = parent.getSelectedItem().toString();
                    break;


                case R.id.sp_husb_vdrl:
                    strHusbVDRL = parent.getSelectedItem().toString();
                    break;
                case R.id.sp_husb_helpatitis:
                    strHusbHelpatitis = parent.getSelectedItem().toString();
                    break;


            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

        private void setValuetoUI(String response) {
//            JSONObject jObj = null;
            try {

                JSONObject jObj = new JSONObject(response);

                int status = jObj.getInt("status");
                String message = jObj.getString("message");
                if (status == 1) {
                    Log.d("message---->", message);
                    strId = jObj.getString("mid");
                    strMasterId = jObj.getString("masterId");
                    strPicmeId = jObj.getString("mPicmeId");
                    txtMotherName.setText(jObj.getString("mName"));
                    txtMotherAge.setText(jObj.getString("mAge"));
                    edtLmpDate.setText(jObj.getString("mLMP"));
                    edtEddDate.setText(jObj.getString("mEDD"));
                    edt_primary_mobile_number.setText(jObj.getString("mMotherMobile"));
                    edt_alternative_mobile_number.setText(jObj.getString("mHusbandMobile"));
                    edtAgeAtMarriage.setText(jObj.getString("mAgeatMarriage"));
                    edtRegWeek.setText(jObj.getString("mRegistrationWeek"));
                    edtANTT1st.setText(jObj.getString("mANTT1"));
                    edtANTT2nd.setText(jObj.getString("mANTT2"));
                    edtFIAStartDate.setText(jObj.getString("mIFAStateDate"));
                    edtHeight.setText(jObj.getString("mHeight"));

                    spMotherOcc.setSelection(getListPosition(Occ,jObj.getString("mMotherOccupation")));
                    spHusbandOcc.setSelection(getListPosition(Occ,jObj.getString("mHusbandOccupation")));    //Private Sector, Govt Sector
                    spConsangulneousMarriage.setSelection(getListPosition(yn,jObj.getString("mConsanguineousMarraige")));   // Yes,No
                    spHistoryIllness.setSelection(getListPosition(hdcdt,jObj.getString("mHistoryIllness")));    //Hypertention, Diabetes, Congenital Heart Disease, Tb, Others
                    spHistoryIllnessFmly.setSelection(getListPosition(hoaif,jObj.getString("mHistoryIllnessFamily"))); //Hypertention, Diabetes, Congenital Heart Disease, Tb, Others
                    spAnySurgeryBefore.setSelection(getListPosition(yn,jObj.getString("mAnySurgeryBefore")));  //Yes,No
                    spDoseTobacco.setSelection(getListPosition(yn,jObj.getString("mUseTobacco")));       //Yes,No
                    spDoseAlcohol.setSelection(getListPosition(yn,jObj.getString("mUseAlcohol")));       //Yes,No
                    spDoseOnAnyMedication.setSelection(getListPosition(yn,jObj.getString("mAnyMeditation"))); //Yes,No
                    spDoseAllergictoDrugs.setSelection(getListPosition(yn,jObj.getString("mAllergicToanyDrug"))); //Yes,No
                    spPrePregnancy.setSelection(getListPosition(yn,jObj.getString("mHistroyPreviousPreganancy")));          //Yes,No
                    spLSCSDone.setSelection(getListPosition(yn,jObj.getString("mLscsDone")));             //Yes,No
                    spComDuringPrgncy.setSelection(getListPosition(acdp,jObj.getString("mAnyComplecationDuringPreganancy")));  //Hypertention, Diabetes, Congenital Heart Disease, Tb, Others
                    spPrePrgncyG.setSelection(getListPosition(num,jObj.getString("mPresentPreganancyG")));  //1234567890
                    spPrePrgncyP.setSelection(getListPosition(num,jObj.getString("mPresentPreganancyP")));  //1234567890
                    spPrePrgncyA.setSelection(getListPosition(num,jObj.getString("mPresentPreganancyA")));  //1234567890
                    spPrePrgncyL.setSelection(getListPosition(num,jObj.getString("mPresentPreganancyL")));   //1234567890
                    spBloodGroup.setSelection(getListPosition(bg,jObj.getString("mBloodGroup")));   //A+ve, A-, B+, B-, O+, O-,
                    spHIV.setSelection(getListPosition(rnr,jObj.getString("mHIV")));      //Reactive, Non Reactive
                    spVDRL.setSelection(getListPosition(rnr,jObj.getString("mVDRL")));      //Reactive, Non Reactive
                    spHelpatitis.setSelection(getListPosition(rnr,jObj.getString("mHepatitis")));   //Reactive, Non Reactive
                    spHusbBloodGroup.setSelection(getListPosition(bg,jObj.getString("hBloodGroup")));  //A+ve, A-, B+, B-, O+, O-,
                    spHusbHIV.setSelection(getListPosition(rnr,jObj.getString("hHIV")));         //Reactive, Non Reactive
                    spHusbVDRL.setSelection(getListPosition(rnr,jObj.getString("hVDRL")));       //Reactive, Non Reactive
                    spHusbHelpatitis.setSelection(getListPosition(rnr,jObj.getString("hHepatitis")));  //Reactive, Non Reactive

                } else {
                    Log.d("message---->", message);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private int getListPosition(String[] occ, String mMotherOccupation) {
           int x=0;
            List<String> listOcc = Arrays.asList(occ);
            if (listOcc.contains(mMotherOccupation)) {
                for (int i=0;i<listOcc.size();i++) {
                    if (mMotherOccupation.equalsIgnoreCase(occ[i]))
                        x =i;
                }
            } else {

            }
            return x;
        }
    }

