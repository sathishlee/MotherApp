package com.unicef.thaimai.motherapp.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.suthishan.multiselectspineer.MultiSelectSpinner;
import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.PrimaryRegisterPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.model.requestmodel.PrimaryDataRequestModel;
import com.unicef.thaimai.motherapp.utility.CheckNetwork;
import com.unicef.thaimai.motherapp.view.PrimaryRegisterViews;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;


public class PrimaryRegister extends AppCompatActivity implements View.OnClickListener, PrimaryRegisterViews,
        AdapterView.OnItemSelectedListener, MultiSelectSpinner.OnMultipleItemsSelectedListener {

    TextView txtMotherName, txtMotherAge;

    EditText edtLmpDate, edtEddDate, edtAgeAtMarriage, edtRegWeek, edtANTT1st, edtANTT2nd, edtFIAStartDate, edtHeight,
            edtOthers, edtMedicationSpecify, edtAllergictoDrugsSpecify, edt_primary_mobile_number, edt_alternative_mobile_number,
            edt_history_illness, edt_history_illness_fmly, edt_any_surgery_before, edt_comDuring_prgncy, edt_husb_blood_group, edt_other_husb_blood_group;

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
            strHistory_illness_other, strHhistory_illness_fmly_other, strAny_surgery_before_other, strAnyComDuring_prgncy_other;
    ArrayList ysList, occList;
    Button butSubmit;
    ProgressDialog pDialog;
    private SimpleDateFormat dateFormatter;
    private static final String TAG = "MainActivity";


    PrimaryRegisterPresenter primaryRegisterPresenter;
    PrimaryDataRequestModel primaryDataRequestModel;
    PreferenceData preferenceData;
    CheckNetwork checkNetwork;
    MultiSelectSpinner spinner1;


    String[] Occ = {"--Select--", "Home Maker", "Private Sector", "Govt Sector"};
    String[] yn = {"--Select--", "Yes", "No"};
    String[] hdcdt = {"--Select--", "Hypertention", "Diabetes", "Congenital Heart Disease", "Tb", "Others"};
    String[] hoaif = {"--Select--", "Htn", "DM", "CHD", "TB", "Others"};
    String[] num = {"--Select--", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    String[] numG = {"--Select--", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    String[] numP = {"--Select--", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    String[] numA = {"--Select--", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    String[] numL = {"--Select--", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    String[] bg = {"--Select--", "A+ve", "A-ve", "B+ve", "B-ve", "O+ve", "O-ve", "AB+ve", "AB-ve"};
    String[] rnr = {"--Select--", "Reactive", "Non Reactive"};
    //        String[] acdp = {"--Select--","Hypertention", "Diabetes", "Congenital Heart Disease", "Tb", "Others"};
    String[] acdp = {"--Select--", "PIH", "GDM", "PPH", "APH", "SEVEREANEMIA", "PREMATURE DELIVERY", "STILL BIRTH"};


    LinearLayout ll_medication_specify, ll_allergicto_drugs_specify;

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
//            if (AppConstants.BACK_BUTTON_GONE) {
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
//            }
    }

    private void initUI() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        preferenceData = new PreferenceData(this);
        checkNetwork = new CheckNetwork(this);

        strPicmeId = preferenceData.getPicmeId();
        strMotherName = preferenceData.getMotherName();
        strMotherAge = preferenceData.getMotherAge();
        primaryRegisterPresenter = new PrimaryRegisterPresenter(PrimaryRegister.this, this);
        if (checkNetwork.isNetworkAvailable()) {
            primaryRegisterPresenter.getAllMotherPrimaryRegistration(strPicmeId);
        } else {
            Toast.makeText(getApplicationContext(), "Check Internet Connection...Try Agian After Sometimes", Toast.LENGTH_LONG).show();
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

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
//            spPrePregnancy = (Spinner) findViewById(R.id.sp_pre_pregnancy);
        spLSCSDone = (Spinner) findViewById(R.id.sp_lscs_done);
//            spComDuringPrgncy = (Spinner) findViewById(R.id.sp_comDuring_prgncy);
        spinner1 = (MultiSelectSpinner) findViewById(R.id.mySpinner1);
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
        edt_husb_blood_group = (EditText) findViewById(R.id.edt_blood_group);
        edt_husb_blood_group.setVisibility(View.GONE);
        edt_other_husb_blood_group = (EditText) findViewById(R.id.edt_other_husb_blood_group);
        edt_other_husb_blood_group.setVisibility(View.GONE);
//            edt_comDuring_prgncy = (EditText) findViewById(R.id.edt_comDuring_prgncy);
        ll_medication_specify = (LinearLayout) findViewById(R.id.ll_medication_specify);
        ll_allergicto_drugs_specify = (LinearLayout) findViewById(R.id.ll_allergicto_drugs_specify);

        spinner1.setItems(acdp);
        spinner1.hasNoneOption(true);
        spinner1.setSelection(new int[]{0});
        spinner1.setListener(this);


    }

    private void onClickListner() {
        butSubmit.setOnClickListener(this);
        edtRegWeek.setOnClickListener(this);
        edtANTT1st.setOnClickListener(this);
        edtANTT2nd.setOnClickListener(this);
        edtFIAStartDate.setOnClickListener(this);

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
//            spPrePregnancy.setOnItemSelectedListener(this);
        spLSCSDone.setOnItemSelectedListener(this);
//            spComDuringPrgncy.setOnItemSelectedListener(this);
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
        Log.e(PrimaryRegister.class.getSimpleName(),"strHeight"+strHeight);
        strOthers = edtOthers.getText().toString();
        strMedicationSpecify = edtMedicationSpecify.getText().toString();
        strAllergictoDrugsSpecify = edtAllergictoDrugsSpecify.getText().toString();
        strPrimaryMobileNumber = edt_primary_mobile_number.getText().toString();
        strAlternativeMobileNumber = edt_alternative_mobile_number.getText().toString();
        strHistory_illness_other = edt_history_illness.getText().toString();
        strHhistory_illness_fmly_other = edt_history_illness_fmly.getText().toString();
        strAny_surgery_before_other = edt_any_surgery_before.getText().toString();
//            strAnyComDuring_prgncy_other =edt_comDuring_prgncy.getText().toString();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//            finish();
        startActivity(new Intent(getApplicationContext(), PrimaryRegisterView.class));
        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edt_reg_week:
                pickDate(edtRegWeek);
                break;
            case R.id.edt_antt1:
                pickDate(edtANTT1st);
                break;
            case R.id.edt_antt2:
                pickDate(edtANTT2nd);
                break;

            case R.id.edt_fias_tart_date:
                pickDate(edtFIAStartDate);
                break;

            case R.id.btn_submit:
                if (checkNetwork.isNetworkAvailable()) {

                    sendtoServer();
                } else {
                    Toast.makeText(getApplicationContext(), "Check Internet Connection...Try Agian After Sometimes", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                break;
        }
    }

    private void pickDate(final EditText edt_date) {

        Calendar newCalendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(PrimaryRegister.this, R.style.DatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
//                edtDob.setText(dayOfMonth + "-" + monthOfYear + "-" + year);
                dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

                edt_date.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        InputMethodManager imm =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edt_date.getWindowToken(), 0);
        datePickerDialog.show();
    }

    private void sendtoServer() {
        getallEditTextvalues();

        if (strMasterId.equalsIgnoreCase("")) {
            showAlert("MasterId is Empty");
        } else if (strId.equalsIgnoreCase("")) {
            showAlert("Id is Empty");

        } else if (preferenceData.getPicmeId().equalsIgnoreCase("")) {
            showAlert("Id is Empty");

        } else if (preferenceData.getMotherName().equalsIgnoreCase("")) {
            showAlert("Id is Empty");

        } else if (preferenceData.getMotherAge().equalsIgnoreCase("")) {
            showAlert("Id is Empty");

        } else if (strLmpDate.equalsIgnoreCase("")) {
            showAlert("LMP is Empty");

        } else if (strLmpDate.equalsIgnoreCase("null")) {
            showAlert("Please Enter Lmp Date");
        } else if (strEddDate.equalsIgnoreCase("")) {
            showAlert("EDD is Empty");

        } else if (strEddDate.equalsIgnoreCase("null")) {
            showAlert("Please Enter EDD Date");
        } else if (strPrimaryMobileNumber.equalsIgnoreCase("")) {
            showAlert("Primary Mobile Number is Empty");

        } else if (strPrimaryMobileNumber.equalsIgnoreCase("null")) {
            showAlert("Please Enter Primary Mobile Number");

        } else if (strAlternativeMobileNumber.equalsIgnoreCase("")) {
            showAlert("Alternative Mobile Number is Empty");

        } else if (strAlternativeMobileNumber.equalsIgnoreCase("null")) {
            showAlert("Please Enter Alternative Mobile Number");

        } else if (strMotherOcc.equalsIgnoreCase("--Select--")) {
            showAlert("Mother Occupation is Empty");

        } else if (strHusbandOcc.equalsIgnoreCase("--Select--")) {
            showAlert("Husband Occupation is Empty");

        } else if (strAgeAtMarriage.equalsIgnoreCase("")) {
            showAlert("Age at Marriage is Empty");

        } else if (strMotherAge.equalsIgnoreCase("null")) {
            showAlert("Enter Age at Marriage");

        } else if (strConsangulneousMarriage.equalsIgnoreCase("--Select--")) {
            showAlert("Consangulneous Marriage is Empty");

        } else if (strHistoryIllness.equalsIgnoreCase("--Select--")) {
            showAlert("Id is Empty");

        } else if (strHistoryIllnessFmly.equalsIgnoreCase("--Select--")) {
            showAlert("History of illness is Empty");

        } else if (strAnySurgeryBefore.equalsIgnoreCase("--Select--")) {
            showAlert("Any Surgery Before is Empty");

        } else if (strDoseTobacco.equalsIgnoreCase("--Select--")) {
            showAlert("Tobacco is Empty");

        } else if (strDoseAlcohol.equalsIgnoreCase("--Select--")) {
            showAlert("Alcohol is Empty");

        } else if (strDoseOnAnyMedication.equalsIgnoreCase("--Select--")) {
            showAlert("On any Medication is Empty");

        } else if (strDoseAllergictoDrugs.equalsIgnoreCase("--Select--")) {
            showAlert("Allergic to Drug is Empty");

        }/*else if (strPrePregnancy.equalsIgnoreCase("--Select--")){
                showAlert("Pre Pregnancy is Empty");

            }*/ else if (strLSCSDone.equalsIgnoreCase("--Select--")) {
            showAlert("LSC Done is Empty");

        }/*else if (strComDuringPrgncy.equalsIgnoreCase("--Select--")){
                showAlert("During Pregnancy is Empty");

            }*/ else if (strPrePrgncyG.equalsIgnoreCase("--Select--")) {
            showAlert("G is Empty");

        } else if (strPrePrgncyP.equalsIgnoreCase("--Select--")) {
            showAlert("P is Empty");

        } else if (strPrePrgncyA.equalsIgnoreCase("--Select--")) {
            showAlert("A is Empty");

        } else if (strPrePrgncyL.equalsIgnoreCase("--Select--")) {
            showAlert("L is Empty");

        } else if (strRegWeek.equalsIgnoreCase("")) {
            showAlert("Registration Week is Empty");

        } else if (strRegWeek.equalsIgnoreCase("null")) {
            showAlert("Please Enter Registration Week");

        } else if (strANTT1st.equalsIgnoreCase("")) {
            showAlert("AN TT1st is Empty");

        } else if (strANTT1st.equalsIgnoreCase("null")) {
            showAlert("Please Enter AN TT1st");

        } else if (strANTT2nd.equalsIgnoreCase("")) {
            showAlert("AN TT2nd is Empty");
        } else if (strANTT2nd.equalsIgnoreCase("null")) {
            showAlert("Please Enter AN TT2nd");
        } else if (strFIAStartDate.equalsIgnoreCase("")) {
            showAlert("FIA Start Date is Empty");
        } else if (strFIAStartDate.equalsIgnoreCase("null")) {
            showAlert("Please Enter FIA Start Date");
        } else if (strHeight.equalsIgnoreCase("")) {
            showAlert("Height is Empty");
        } else if (strHeight.equalsIgnoreCase("null")) {
            showAlert("Height is Empty");
        } else if (strBloodGroup.equalsIgnoreCase("--Select--")) {
            showAlert("Blood Group is Empty");
        } else if (strHIV.equalsIgnoreCase("--Select--")) {
            showAlert("HIV is Empty");
        } else if (strVDRL.equalsIgnoreCase("--Select--")) {
            showAlert("VDRL is Empty");
        } else if (strHelpatitis.equalsIgnoreCase("--Select--")) {
            showAlert("Helpatitis is Empty");
        } else if (strHusbBloodGroup.equalsIgnoreCase("--Select--")) {
            showAlert("Husband Blood Group is Empty");
        } else if (strHusbHIV.equalsIgnoreCase("--Select--")) {
            showAlert("Husband Hiv is Empty");
        } else if (strHusbVDRL.equalsIgnoreCase("--Select--")) {
            showAlert("Husband VDRL is Empty");
        } else if (strHusbHelpatitis.equalsIgnoreCase("--Select--")) {
            showAlert("HUSBAND Helpatities is Empty");
        } else {
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
            if(strAnySurgeryBefore.equalsIgnoreCase("Yes")){
                primaryDataRequestModel.setMAnySurgeryBefore(strAnySurgeryBefore+ " - " +strAny_surgery_before_other);
            }else{
                primaryDataRequestModel.setMAnySurgeryBefore(strAnySurgeryBefore);
            }
//            primaryDataRequestModel.setMAnySurgeryBefore(strAnySurgeryBefore);
            primaryDataRequestModel.setMUseTobacco(strDoseTobacco);
            primaryDataRequestModel.setMUseAlcohol(strDoseAlcohol);
            if (strDoseOnAnyMedication.equalsIgnoreCase("Yes")) {
                primaryDataRequestModel.setMAnyMeditation(strDoseOnAnyMedication + " - " + strMedicationSpecify);
            } else {
                primaryDataRequestModel.setMAnyMeditation(strDoseOnAnyMedication);
            }
            if (strDoseAllergictoDrugs.equalsIgnoreCase("Yes")) {
                primaryDataRequestModel.setMAllergicToanyDrug(strDoseAllergictoDrugs + " - " + strAllergictoDrugsSpecify);
            } else {
                primaryDataRequestModel.setMAllergicToanyDrug(strDoseAllergictoDrugs);
            }
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

            if (strBloodGroup.equalsIgnoreCase("Yes")) {
                primaryDataRequestModel.setMBloodGroup(strBloodGroup + "-" + edt_husb_blood_group.getText().toString());
            } else {
                primaryDataRequestModel.setMBloodGroup(strBloodGroup);
            }

            primaryDataRequestModel.setMHIV(strHIV);
            primaryDataRequestModel.setMVDRL(strVDRL);
            primaryDataRequestModel.setMHepatitis(strHelpatitis);
            if (strHusbBloodGroup.equalsIgnoreCase("Yes")) {
                primaryDataRequestModel.setHBloodGroup(strHusbBloodGroup + "-" + edt_other_husb_blood_group.getText().toString());
            } else {
                primaryDataRequestModel.setHBloodGroup(strHusbBloodGroup);
            }

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
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
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
    public void onDestroy() {
        super.onDestroy();
        if (pDialog != null && pDialog.isShowing()) {
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
        startActivity(new Intent(getApplicationContext(), PrimaryRegisterView.class));
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
                } else {
                    edt_history_illness.setVisibility(View.GONE);
                }
                break;

            case R.id.sp_historyIllness_fmly:
                strHistoryIllnessFmly = parent.getSelectedItem().toString();
                if (strHistoryIllnessFmly.equalsIgnoreCase("Others")) {
                    edt_history_illness_fmly.setVisibility(View.VISIBLE);
                } else {
                    edt_history_illness_fmly.setVisibility(View.GONE);
                }
                break;

            case R.id.sp_any_surgery_before:
                strAnySurgeryBefore = parent.getSelectedItem().toString();
                if (strAnySurgeryBefore.equalsIgnoreCase("Yes")) {
                    edt_any_surgery_before.setVisibility(View.VISIBLE);
                } else {
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
                Toast.makeText(getApplicationContext(), strDoseOnAnyMedication, Toast.LENGTH_LONG).show();
                if (strDoseOnAnyMedication.equalsIgnoreCase("Yes")) {
                    ll_medication_specify.setVisibility(View.VISIBLE);
                } else {
                    ll_medication_specify.setVisibility(View.GONE);
                }
                break;
            case R.id.sp_dose_allergicto_drugs:
                strDoseAllergictoDrugs = parent.getSelectedItem().toString();
                if (strDoseAllergictoDrugs.equalsIgnoreCase("Yes")) {
                    ll_allergicto_drugs_specify.setVisibility(View.VISIBLE);
                } else {
                    ll_allergicto_drugs_specify.setVisibility(View.GONE);

                }
                break;
               /* case R.id.sp_pre_pregnancy:
                    strPrePregnancy = parent.getSelectedItem().toString();
                    break;*/
            case R.id.sp_lscs_done:
                strLSCSDone = parent.getSelectedItem().toString();
                break;
            case R.id.mySpinner1:
                strComDuringPrgncy = parent.getSelectedItem().toString();
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
                if (strBloodGroup.equalsIgnoreCase("Others")) {
                    edt_husb_blood_group.setVisibility(View.VISIBLE);
                } else {
                    edt_husb_blood_group.setVisibility(View.GONE);
                }
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
                if (strHusbBloodGroup.equalsIgnoreCase("Others")) {
                    edt_other_husb_blood_group.setVisibility(View.VISIBLE);

                } else {
                    edt_other_husb_blood_group.setVisibility(View.GONE);

                }
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
                if (jObj.getString("mName").equalsIgnoreCase("null")) {
                    txtMotherName.setText("-");
                } else {
                    txtMotherName.setText(jObj.getString("mName"));
                }
                if (jObj.getString("mAge").equalsIgnoreCase("null")) {
                    txtMotherAge.setText("-");

                }else {
                    txtMotherAge.setText(jObj.getString("mAge"));
                }
                if (jObj.getString("mLMP").equalsIgnoreCase("null")) {
                    edtLmpDate.setText("-");

                }else {
                    edtLmpDate.setText(jObj.getString("mLMP"));
                } if (jObj.getString("mEDD").equalsIgnoreCase("null")) {
                    edtEddDate.setText("-");

                }else {
                    edtEddDate.setText(jObj.getString("mEDD"));
                }if (jObj.getString("mMotherMobile").equalsIgnoreCase("null")) {
                    edt_primary_mobile_number.setText("-");

                }else {
                    edt_primary_mobile_number.setText(jObj.getString("mMotherMobile"));

                }if (jObj.getString("mHusbandMobile").equalsIgnoreCase("null")) {
                    edt_alternative_mobile_number.setText("-");

                }else {
                    edt_alternative_mobile_number.setText(jObj.getString("mHusbandMobile"));
                }if (jObj.getString("mAgeatMarriage").equalsIgnoreCase("null")) {
                    edtAgeAtMarriage.setText("-");

                }else {
                    edtAgeAtMarriage.setText(jObj.getString("mAgeatMarriage"));
                }if (jObj.getString("mRegistrationWeek").equalsIgnoreCase("null")) {
                    edtRegWeek.setText("-");

                }else {
                    edtRegWeek.setText(jObj.getString("mRegistrationWeek"));
                }if (jObj.getString("mANTT1").equalsIgnoreCase("null")) {
                    edtANTT1st.setText("NA");

                }else {
                    edtANTT1st.setText(jObj.getString("mANTT1"));

                }if (jObj.getString("mANTT2").equalsIgnoreCase("null")) {
                    edtANTT2nd.setText("NA");

                }else {
                    edtANTT2nd.setText(jObj.getString("mANTT2"));
                }if (jObj.getString("mIFAStateDate").equalsIgnoreCase("null")) {
                    edtFIAStartDate.setText("NA");

                }else {
                    edtFIAStartDate.setText(jObj.getString("mIFAStateDate"));
                }if (jObj.getString("mHeight").equalsIgnoreCase("null")) {
                    edtHeight.setText("NA");

                }else {
                    edtHeight.setText(jObj.getString("mHeight"));
                }
                spMotherOcc.setSelection(getListPosition(Occ, jObj.getString("mMotherOccupation")));
                spHusbandOcc.setSelection(getListPosition(Occ, jObj.getString("mHusbandOccupation")));    //Private Sector, Govt Sector
                spConsangulneousMarriage.setSelection(getListPosition(yn, jObj.getString("mConsanguineousMarraige")));   // Yes,No
                spHistoryIllness.setSelection(getListPosition(hdcdt, jObj.getString("mHistoryIllness")));    //Hypertention, Diabetes, Congenital Heart Disease, Tb, Others
                spHistoryIllnessFmly.setSelection(getListPosition(hoaif, jObj.getString("mHistoryIllnessFamily"))); //Hypertention, Diabetes, Congenital Heart Disease, Tb, Others
                String surgeryBefore = jObj.getString("mAnySurgeryBefore");
                Log.e("surgeryBefore--->",surgeryBefore);
                if(surgeryBefore.equalsIgnoreCase("No")){
                    spAnySurgeryBefore.setSelection(getListPosition(yn,jObj.getString("mAnySurgeryBefore")));
                }else{
                    StringTokenizer tokens = new StringTokenizer(surgeryBefore, " -");
                    String first = tokens.nextToken();
                    String second = tokens.nextToken();
                    spAnySurgeryBefore.setSelection(getListPosition(yn,first));
                    edt_any_surgery_before.setText(second);
                }
                spDoseTobacco.setSelection(getListPosition(yn, jObj.getString("mUseTobacco")));       //Yes,No
                spDoseAlcohol.setSelection(getListPosition(yn, jObj.getString("mUseAlcohol")));       //Yes,No
                String onAnyMedication = jObj.getString("mAnyMeditation");
                if(onAnyMedication.equalsIgnoreCase("No")){
                    spDoseOnAnyMedication.setSelection(getListPosition(yn, jObj.getString("mAnyMeditation"))); //Yes,No
                }else {
                    StringTokenizer tokens = new StringTokenizer(onAnyMedication, " -");
                    String first = tokens.nextToken();
                    String second = tokens.nextToken();
                    spDoseOnAnyMedication.setSelection(getListPosition(yn,first));
                    edtMedicationSpecify.setText(second);
                }
                String allerGicDrug = jObj.getString("mAllergicToanyDrug");
                if(allerGicDrug.equalsIgnoreCase("No")){
                    spDoseAllergictoDrugs.setSelection(getListPosition(yn, jObj.getString("mAllergicToanyDrug"))); //Yes,No
                }else {
                    StringTokenizer tokens = new StringTokenizer(allerGicDrug, " -");
                    String first = tokens.nextToken();
                    String second = tokens.nextToken();
                    spDoseAllergictoDrugs.setSelection(getListPosition(yn,first));
                    edtAllergictoDrugsSpecify.setText(second);
                }
                spLSCSDone.setSelection(getListPosition(yn, jObj.getString("mLscsDone")));             //Yes,No
//                    spComDuringPrgncy.setSelection(getListPosition(acdp,jObj.getString("mAnyComplecationDuringPreganancy")));  //Hypertention, Diabetes, Congenital Heart Disease, Tb, Others
//                spinner1.setSelection(getListPosition(acdp, jObj.getString("mAnyComplecationDuringPreganancy")));
                spinner1.setSelection(getListPosition(acdp,jObj.getString("mAnyComplecationDuringPreganancy")));

                spPrePrgncyG.setSelection(getListPosition(numG, jObj.getString("mPresentPreganancyG")));  //1234567890
                spPrePrgncyP.setSelection(getListPosition(numP, jObj.getString("mPresentPreganancyP")));  //1234567890
                spPrePrgncyA.setSelection(getListPosition(numA, jObj.getString("mPresentPreganancyA")));  //1234567890
                spPrePrgncyL.setSelection(getListPosition(numL, jObj.getString("mPresentPreganancyL")));   //1234567890
                spBloodGroup.setSelection(getListPosition(bg, jObj.getString("mBloodGroup")));   //A+ve, A-, B+, B-, O+, O-,
                spHIV.setSelection(getListPosition(rnr, jObj.getString("mHIV")));      //Reactive, Non Reactive
                spVDRL.setSelection(getListPosition(rnr, jObj.getString("mVDRL")));      //Reactive, Non Reactive
                spHelpatitis.setSelection(getListPosition(rnr, jObj.getString("mHepatitis")));   //Reactive, Non Reactive
                spHusbBloodGroup.setSelection(getListPosition(bg, jObj.getString("hBloodGroup")));  //A+ve, A-, B+, B-, O+, O-,
                spHusbHIV.setSelection(getListPosition(rnr, jObj.getString("hHIV")));         //Reactive, Non Reactive
                spHusbVDRL.setSelection(getListPosition(rnr, jObj.getString("hVDRL")));       //Reactive, Non Reactive
                spHusbHelpatitis.setSelection(getListPosition(rnr, jObj.getString("hHepatitis")));  //Reactive, Non Reactive
//                String anycomplication = jObj.getString("mAnyComplecationDuringPreganancy");


            } else {
                Log.d("message---->", message);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private int getListPosition(String[] occ, String mMotherOccupation) {
        int position = 0;
        List<String> listOcc = Arrays.asList(occ);
        if (listOcc.contains(mMotherOccupation)) {
            for (int i = 0; i < listOcc.size(); i++) {
                if (mMotherOccupation.equalsIgnoreCase(occ[i]))
                    position = i;
            }
        } else {
            String[] sx = mMotherOccupation.split("-");
            if (listOcc.contains(sx[0])) {

                for (int i = 0; i < listOcc.size(); i++) {
                    if (mMotherOccupation.equalsIgnoreCase(occ[i]))
                        position = i;
                }

            }
        }

        return position;
    }

    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {
        Toast.makeText(this.getApplicationContext(), "Values -- >" + strings, Toast.LENGTH_LONG).show();
        strComDuringPrgncy = spinner1.getSelectedItemsAsString();
    }
}

