package com.unicef.thaimai.motherapp.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.suthishan.multiselectspineer.MultiSelectSpinner;
import com.unicef.thaimai.motherapp.PNImageSelectedActivity;
import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.PNHBNCVisitPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.model.requestmodel.PNHBNCVisitEntryRequestModel;
import com.unicef.thaimai.motherapp.utility.CheckNetwork;
import com.unicef.thaimai.motherapp.view.PNHBNCVisitViews;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PNHBNCVisitEntry extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, PNHBNCVisitViews, MultiSelectSpinner.OnMultipleItemsSelectedListener {

    Spinner sp_facility,sp_infant_umbilical_stump,sp_infant_cry,sp_infant_eyes, sp_infant_skin,
            sp_infant_breast_feeding, sp_infant_reason,sp_infant_outcome,sp_mother_any_complaints,
            sp_mother_epistomy, sp_pv_discharge, sp_mother_breast_feeding, sp_mother_reasons, sp_breast_examination,
            sp_mother_outcome, sp_visit_no;

    TextInputLayout til_due_date, til_care_provided_date, til_infant_weight, til_infant_temp, til_mother_pulse_rate, til_mother_temp;

    EditText edt_due_date, edt_care_provided_date, edt_infant_weight, edt_infant_temp, edt_mother_systolic, edt_mother_diastolic, edt_mother_pluse_rate, edt_mother_temp;

    Button btn_pnhbnc_submit, upload_report;

    String  strDueDate, strCareDate, strInfantWeight, strInfantTemp, strMotherSystolic, strMotherDiastolic,
            strMotherPluseRate, strMotherTemp, strFacility, strInfantUmbilicalStump, strInfantCry, strInfantEyes,
            strInfantSkin, strInfantBreastFeeding, strInfantReason, strInfantOutcome, strInfantMotherComplaints,
            strMotherEpistomy, strPVDischarge, strMotherBreastFeeding, strMotherReason, strBreastExamination,
            strMotherOutcome, strVisitNo;

    public static String strVisitId="-1", strPicmeId;

    ProgressDialog progressDialog;

    PreferenceData preferenceData;
    PNHBNCVisitPresenter pnhbncVisitPresenter;
    PNHBNCVisitEntryRequestModel pnhbncVisitEntryRequestModel;

    Calendar mCurrentDate;
    int day, month, year, hour, minute, sec;
    CheckNetwork checkNetwork;

    MultiSelectSpinner spinner1;
    MultiSelectSpinner spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnhbnc_visit_entry);
        showActionBar();
        initUI();
        onClickListner();
        OnItemSelectedListener();
    }

    public void showActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("PN Visit Edit");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void initUI(){

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        checkNetwork = new CheckNetwork(this);
        preferenceData = new PreferenceData(this);
        pnhbncVisitPresenter = new PNHBNCVisitPresenter(PNHBNCVisitEntry.this, this);
        if (checkNetwork.isNetworkAvailable()) {
            pnhbncVisitPresenter.getPNHBNCVisitCount(preferenceData.getPicmeId(), preferenceData.getMId());
        }else{
            Toast.makeText(getApplicationContext(), "Check Internet Connection..." + checkNetwork.isNetworkAvailable(), Toast.LENGTH_LONG).show();
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);
        hour = mCurrentDate.get(Calendar.HOUR);
        minute = mCurrentDate.get(Calendar.MINUTE);
        sec = mCurrentDate.get(Calendar.SECOND);
        month = month + 1;


        til_due_date = (TextInputLayout) findViewById(R.id.til_due_date);
        til_care_provided_date = (TextInputLayout) findViewById(R.id.til_care_provided_date);
        til_infant_weight = (TextInputLayout) findViewById(R.id.til_infant_weight);
        til_infant_temp = (TextInputLayout) findViewById(R.id.til_infant_temp);
        til_mother_pulse_rate = (TextInputLayout) findViewById(R.id.til_mother_pulse_rate);
        til_mother_temp = (TextInputLayout) findViewById(R.id.til_mother_temp);

        edt_due_date = (EditText) findViewById(R.id.edt_due_date);
        edt_due_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(PNHBNCVisitEntry.this, R.style.DatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                        edt_due_date.setText(dayOfMonth + "-" + monthOfYear + "-" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }

        });

        edt_care_provided_date = (EditText) findViewById(R.id.edt_care_provided_date);
        edt_care_provided_date.setText(getCurrentDate());

        edt_care_provided_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(PNHBNCVisitEntry.this, R.style.DatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                        edt_care_provided_date.setText(dayOfMonth + "-" + monthOfYear + "-" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }

        });

        edt_infant_weight = (EditText) findViewById(R.id.edt_infant_weight);
        edt_infant_temp = (EditText) findViewById(R.id.edt_infant_temp);
        edt_mother_systolic = (EditText) findViewById(R.id.edt_mother_systolic);
        edt_mother_diastolic = (EditText) findViewById(R.id.edt_mother_diastolic);
        edt_mother_pluse_rate = (EditText) findViewById(R.id.edt_mother_pluse_rate);
        edt_mother_temp = (EditText) findViewById(R.id.edt_mother_temp);

        btn_pnhbnc_submit = (Button) findViewById(R.id.btn_pnhbnc_submit);
        upload_report = (Button) findViewById(R.id.upload_reports);

        sp_visit_no = (Spinner) findViewById(R.id.sp_visit_no);
        sp_facility = (Spinner) findViewById(R.id.sp_facility);
        sp_infant_umbilical_stump = (Spinner) findViewById(R.id.sp_infant_umbilical_stump);
        sp_infant_cry = (Spinner) findViewById(R.id.sp_infant_cry);
        sp_infant_eyes = (Spinner) findViewById(R.id.sp_infant_eyes);
        spinner1 = (MultiSelectSpinner) findViewById(R.id.mySpinner1);
        spinner2 = (MultiSelectSpinner) findViewById(R.id.mySpinner2);

        sp_infant_skin = (Spinner) findViewById(R.id.sp_infant_skin);
        sp_infant_breast_feeding = (Spinner) findViewById(R.id.sp_infant_breast_feeding);
        sp_infant_reason = (Spinner) findViewById(R.id.sp_infant_reason);
        sp_infant_outcome = (Spinner) findViewById(R.id.sp_infant_outcome);
        sp_mother_any_complaints = (Spinner) findViewById(R.id.sp_mother_any_complaints);
        sp_mother_epistomy = (Spinner) findViewById(R.id.sp_mother_epistomy);
        sp_pv_discharge = (Spinner) findViewById(R.id.sp_pv_discharge);
        sp_mother_breast_feeding = (Spinner) findViewById(R.id.sp_mother_breast_feeding);
        sp_mother_reasons = (Spinner) findViewById(R.id.sp_mother_reasons);
        sp_breast_examination = (Spinner) findViewById(R.id.sp_breast_examination);
        sp_mother_outcome = (Spinner) findViewById(R.id.sp_mother_outcome);


        String[] acdp = {"--Select--", "Normal", "swollen", "Pus present", "Redness", "Yellowish"};
        spinner1.setItems(acdp);
        spinner1.hasNoneOption(true);
        spinner1.setSelection(new int[]{0});
        spinner1.setListener(this);

        String[] acdp1 = {"--Select--", "Fever","Giddiness or Headache","Vomiting","Pain Abdomen",
        "Bleeding PV","Foul smelling PV","Breathlessness","Pedal edema","Burning Mictutrion",
        "Fits", "No Complaints","Others (Specify)"
                 };

        spinner2.setItems(acdp1);
        spinner2.hasNoneOption(true);
        spinner2.setSelection(new int[]{0});
        spinner2.setListener(new MultiSelectSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {

            }

            @Override
            public void selectedStrings(List<String> strings) {
                strInfantMotherComplaints=spinner2.getSelectedItemsAsString();
            }
        });


    }

    private String getCareDueDate(String strDate,int visitNo) {
        String[] arrDay= new String[]{"0", "3", "7", "14", "21", "28", "42"};
        int iday = 0;
        if (visitNo==1){
            iday=0;
        }  else if (visitNo==2){
            iday=3;
        }else if (visitNo==3){
            iday=7;
        }else if (visitNo==4){
            iday=14;
        }else if (visitNo==5){
            iday=21;
        }else if (visitNo==6){
            iday=28;
        }else if (visitNo==7){
            iday=42;
        }
        String dtStart = strDate;
      /*  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(dtStart);
            final LocalDate dateMinus7Days = date.a(7);
            ;
            System.out.println(date );
        } catch (ParseException e) {
            e.printStackTrace();
        }
*/

        String fromDate = strDate;
        //split year, month and days from the date using StringBuffer.
        StringBuffer sBuffer = new StringBuffer(fromDate);
        String year = sBuffer.substring(0,4);
        String mon = sBuffer.substring(5,7);
        String dd = sBuffer.substring(8,10);

        String modifiedFromDate = year +'-'+mon+'-'+dd;
        int MILLIS_IN_DAY = (1000 * 60 * 60 * 24)*iday;

        SimpleDateFormat dateFormat =
                new java.text.SimpleDateFormat("yyyy-MM-dd");
       Date dateSelectedFrom = null;
        Date dateNextDate = null;
      Date datePreviousDate = null;

        // convert date present in the String to java.util.Date.
        try
        {
            dateSelectedFrom = dateFormat.parse(modifiedFromDate);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        //get the next date in String.
        String nextDate =
                dateFormat.format(dateSelectedFrom.getTime() + MILLIS_IN_DAY);

        return nextDate;
    }

    private String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        // formattedDate have current date/time
        Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();
        return formattedDate;
    }

    public void onClickListner(){
        btn_pnhbnc_submit.setOnClickListener(this);
        upload_report.setOnClickListener(this);
    }

    public void OnItemSelectedListener(){
        sp_visit_no.setOnItemSelectedListener(this);
        sp_facility.setOnItemSelectedListener(this);
        sp_infant_umbilical_stump.setOnItemSelectedListener(this);
        sp_infant_cry.setOnItemSelectedListener(this);
        sp_infant_eyes.setOnItemSelectedListener(this);
        sp_infant_skin.setOnItemSelectedListener(this);
        sp_infant_breast_feeding.setOnItemSelectedListener(this);
        sp_infant_reason.setOnItemSelectedListener(this);
        sp_infant_outcome.setOnItemSelectedListener(this);
        sp_mother_any_complaints.setOnItemSelectedListener(this);
        sp_mother_epistomy.setOnItemSelectedListener(this);
        sp_pv_discharge.setOnItemSelectedListener(this);
        sp_mother_breast_feeding.setOnItemSelectedListener(this);
        sp_mother_reasons.setOnItemSelectedListener(this);
        sp_breast_examination.setOnItemSelectedListener(this);
        sp_mother_outcome.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_pnhbnc_submit:
                if (checkNetwork.isNetworkAvailable()) {
                    dataSendtoServer();
                }else{
                    Toast.makeText(getApplicationContext(), "Check Internet Connection...Try Agian After Sometimes", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;

            case R.id.upload_reports:
                if(checkNetwork.isNetworkAvailable()){
                    checkVisitSelect();
                }else{
                    Toast.makeText(getApplicationContext(), "Check Internet Connection...Try Agian After Sometimes", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

    private void checkVisitSelect() {
        startActivity(new Intent(getApplicationContext(), PNImageSelectedActivity.class));
    }

    public void dataSendtoServer(){
        editTextCheck();

        if(TextUtils.isEmpty(strDueDate)){
            til_due_date.setError("Due Date is Empty");
        }
        else{
            til_due_date.setError(null);
        }
        if(TextUtils.isEmpty(strCareDate)){
            til_care_provided_date.setError("Care Provided Date is Empty");
        }
        else{
            til_care_provided_date.setError(null);
        }
        if(TextUtils.isEmpty(strInfantWeight)){
            til_infant_weight.setError("Infant Weight is Empty");
        }
        else{
            til_infant_weight.setError(null);
        }
        if (TextUtils.isEmpty(strInfantTemp)){
            til_infant_temp.setError("Infant Temp is Empty");
        }
        else {
            til_infant_temp.setError(null);
        }
        if (TextUtils.isEmpty(strMotherPluseRate)){
            til_mother_pulse_rate.setError("Pluse Rate is Empty");
        }
        else{
            til_mother_pulse_rate.setError(null);
        }
        if (TextUtils.isEmpty(strMotherTemp)){
            til_mother_temp.setError("Mother Temp is Empty");
        }
        else{
            til_mother_temp.setError(null);
        }

        if (strFacility.equalsIgnoreCase("--Select--")){
            showAlert("Facility is Empty");
        }
        else if (strInfantUmbilicalStump.equalsIgnoreCase("--Select")){
            showAlert("Infant Umbilical Stump is Empty");
        }
        else if (strInfantCry.equalsIgnoreCase("--Select")){
            showAlert("Infant Cry is Empty");
        }
        else if(strInfantEyes.equalsIgnoreCase("--Select")){
            showAlert("Infant Eyes is Empty");
        }
        else if(strInfantSkin.equalsIgnoreCase("--Select")){
            showAlert("Infant Skin is Empty");
        }
        else if(strInfantBreastFeeding.equalsIgnoreCase("--Select")){
            showAlert("Infant Breast Feeding is Empty");
        }
        else if(strInfantReason.equalsIgnoreCase("--Select")){
            showAlert("Infant Reason is Empty");
        }
        else if (strInfantOutcome.equalsIgnoreCase("--Select")){
            showAlert("Infant Outcome is Empty");
        }
        else if (strInfantMotherComplaints.equalsIgnoreCase("--Select")){
            showAlert("Mother Complaints is Empty");
        }
        else if(strMotherEpistomy.equalsIgnoreCase("--Select")){
            showAlert("Mother Epistomy is Empty");
        }
        else if(strPVDischarge.equalsIgnoreCase("--Select")){
            showAlert("PV Discharge is Empty");
        }
        else if(strMotherBreastFeeding.equalsIgnoreCase("--Select")){
            showAlert("Mother Breast Feeding is Empty");
        }
        else if(strMotherReason.equalsIgnoreCase("--Select")){
            showAlert("Mother Reason is Empty");
        }
        else if (strBreastExamination.equalsIgnoreCase("--Select")){
            showAlert("Breast Examination is Empty");
        }
        else if(strMotherOutcome.equalsIgnoreCase("--Select")){
            showAlert("Mother Outcome is Empty");
        }
        else if(strVisitNo.equalsIgnoreCase("--Select")){
            showAlert("Visit No is Empty");
        }

        else {
            pnhbncVisitEntryRequestModel = new PNHBNCVisitEntryRequestModel();
            pnhbncVisitEntryRequestModel.setPicmeId(preferenceData.getPicmeId());
            pnhbncVisitEntryRequestModel.setMid(preferenceData.getMId());
            pnhbncVisitEntryRequestModel.setPnVisitNo(strVisitNo);
            pnhbncVisitEntryRequestModel.setPnVisitId(strVisitId);
            pnhbncVisitEntryRequestModel.setPnDueDate(strDueDate);
            pnhbncVisitEntryRequestModel.setPnCareProvidedDate(strCareDate);
            pnhbncVisitEntryRequestModel.setPnPlace(strFacility);
            pnhbncVisitEntryRequestModel.setCWeight(strInfantWeight);
            pnhbncVisitEntryRequestModel.setCTemp(strInfantTemp);
            pnhbncVisitEntryRequestModel.setCUmbilicalStump(strInfantUmbilicalStump);
            pnhbncVisitEntryRequestModel.setCCry(strInfantCry);
            pnhbncVisitEntryRequestModel.setCEyes(strInfantEyes);
            pnhbncVisitEntryRequestModel.setCSkin(strInfantSkin);
            pnhbncVisitEntryRequestModel.setCBreastFeeding(strInfantBreastFeeding);
            pnhbncVisitEntryRequestModel.setCBreastFeedingReason(strInfantReason);
            pnhbncVisitEntryRequestModel.setCOutCome(strInfantOutcome);
            pnhbncVisitEntryRequestModel.setPnAnyComplaints(strInfantMotherComplaints);
            pnhbncVisitEntryRequestModel.setPnBPSystolic(strMotherSystolic);
            pnhbncVisitEntryRequestModel.setPnBPDiastolic(strMotherDiastolic);
            pnhbncVisitEntryRequestModel.setPnPulseRate(strMotherPluseRate);
            pnhbncVisitEntryRequestModel.setPnTemp(strMotherTemp);
            pnhbncVisitEntryRequestModel.setPnEpistomyTear(strMotherEpistomy);
            pnhbncVisitEntryRequestModel.setPnPVDischarge(strPVDischarge);
            pnhbncVisitEntryRequestModel.setPnBreastFeeding(strMotherBreastFeeding);
            pnhbncVisitEntryRequestModel.setPnBreastFeedingReason(strMotherReason);
            pnhbncVisitEntryRequestModel.setPnBreastExamination(strBreastExamination);
            pnhbncVisitEntryRequestModel.setPnOutCome(strMotherOutcome);

            pnhbncVisitPresenter.insertPNHBNCVistRecords(pnhbncVisitEntryRequestModel);
        }
    }

    public void editTextCheck(){
        strDueDate = edt_due_date.getText().toString();
        strCareDate = edt_care_provided_date.getText().toString();
        strInfantWeight = edt_infant_weight.getText().toString();
        strInfantTemp = edt_infant_temp.getText().toString();
        strMotherPluseRate = edt_mother_pluse_rate.getText().toString();
        strMotherTemp = edt_mother_temp.getText().toString();
        strMotherSystolic = edt_mother_systolic.getText().toString();
        strMotherDiastolic = edt_mother_diastolic.getText().toString();

    }

    private void showAlert(String msg) {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()){
            case R.id.sp_visit_no:
                strVisitNo = parent.getSelectedItem().toString();
                if(strVisitNo.equalsIgnoreCase("--Select--")){
                    position = -1;
                    strVisitId= String.valueOf(position);
                    pnhbncVisitPresenter.checkPNHBNCVisitId(preferenceData.getPicmeId(),preferenceData.getMId(),strVisitId);
                }
                else{
                    strVisitId= String.valueOf(position);
                    pnhbncVisitPresenter.checkPNHBNCVisitId(preferenceData.getPicmeId(),preferenceData.getMId(),strVisitId);

                }
                /*strVisitId= String.valueOf(position);
                pnhbncVisitPresenter.checkPNHBNCVisitId(preferenceData.getPicmeId(),preferenceData.getMId(),strVisitId);
*/

                break;
            case R.id.sp_facility:
                strFacility = parent.getSelectedItem().toString();
                break;
            case R.id.sp_infant_umbilical_stump:
                strInfantUmbilicalStump = parent.getSelectedItem().toString();
                break;
            case R.id.sp_infant_cry:
                strInfantCry = parent.getSelectedItem().toString();
                break;
            case R.id.sp_infant_eyes:
                strInfantEyes = parent.getSelectedItem().toString();
                break;
            case R.id.sp_infant_skin:
                strInfantSkin = parent.getSelectedItem().toString();
                break;
            case R.id.sp_infant_breast_feeding:
                strInfantBreastFeeding = parent.getSelectedItem().toString();
                break;
            case R.id.sp_infant_reason:
                strInfantReason = parent.getSelectedItem().toString();
                break;
            case R.id.sp_infant_outcome:
                strInfantOutcome = parent.getSelectedItem().toString();
                break;
            case R.id.sp_mother_any_complaints:
                strInfantMotherComplaints = parent.getSelectedItem().toString();
                break;
            case R.id.sp_mother_epistomy:
                strMotherEpistomy = parent.getSelectedItem().toString();
                break;
            case R.id.sp_pv_discharge:
                strPVDischarge = parent.getSelectedItem().toString();
                break;
            case R.id.sp_mother_breast_feeding:
                strMotherBreastFeeding = parent.getSelectedItem().toString();
                break;
            case R.id.sp_mother_reasons:
                strMotherReason = parent.getSelectedItem().toString();
                break;
            case R.id.sp_breast_examination:
                strBreastExamination = parent.getSelectedItem().toString();
                break;
            case R.id.sp_mother_outcome:
                strMotherOutcome = parent.getSelectedItem().toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (progressDialog!=null && progressDialog.isShowing() ){
            progressDialog.cancel();
        }
    }

    @Override
    public void pnhbncVisitSuccess(String response) {

        Log.e(PNHBNCVisitEntry.class.getSimpleName(),"Response Success--->"+response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            String msg = jsonObject.getString("message");
            if (status.equalsIgnoreCase("1")){
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                Log.e(PNHBNCVisitEntry.class.getSimpleName(),"Response Success--->"+response);
            }
        } catch (JSONException e) {
            e.printStackTrace();


        }
    }

    @Override
    public void pnhbncVisitFailiure(String response) {
        Log.d(PNHBNCVisitEntry.class.getSimpleName(),"Response Failiure-->" + response);
    }

    @Override
    public void getpnhbncVisitNumberSuccess(String response) {

        Log.d(PNHBNCVisitEntry.class.getSimpleName(), "Response Success--->" + response);
        try{
            JSONObject jsonObject = new JSONObject(response);
            String status =jsonObject.getString("status");
            String msg = jsonObject.getString("message");
//            strVisitId = jsonObject.getString("pnVisitNo");
//            strPicmeId = jsonObject.getString(preferenceData.getPicmeId());
        }
        catch(JSONException e){
            e.printStackTrace();
        }

    }

    @Override
    public void getpnhbncVisitNumberFailiure(String response) {
        Log.d(PNHBNCVisitEntry.class.getSimpleName(),"Response Failure-->" + response);
    }

    @Override
    public void checkpnhbncVisitIdSuccess(String response) {
//        strVisitId = strVisitNo;
        Log.e(PNHBNCVisitEntry.class.getSimpleName(),response);
        try{
            JSONObject jsonObject = new JSONObject(response);
            String status =jsonObject.getString("status");
            String msg = jsonObject.getString("message");

            if (status.equalsIgnoreCase("1")){
//                edt_due_date.setText(getCareDueDate("2018-12-01",Integer.parseInt(strVisitId)));
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                edt_due_date.setText(getCareDueDate(jsonObject.getString("deliverydate"),Integer.parseInt(strVisitId)));

            }else{
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));

            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void checkpnhbncVisitIdFailiure(String response) {
        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {
        strInfantEyes = spinner1.getSelectedItemsAsString();
    }
}

