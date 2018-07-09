    package com.unicef.thaimai.motherapp.activity;

    import android.app.DatePickerDialog;
    import android.app.ProgressDialog;
    import android.content.Context;
    import android.content.Intent;
    import android.os.Bundle;
    import android.support.v7.app.ActionBar;
    import android.support.v7.app.AppCompatActivity;
    import android.util.Log;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.inputmethod.InputMethodManager;
    import android.widget.AdapterView;
    import android.widget.Button;
    import android.widget.DatePicker;
    import android.widget.EditText;
    import android.widget.RadioButton;
    import android.widget.RadioGroup;
    import android.widget.Spinner;
    import android.widget.Toast;

    import com.unicef.thaimai.motherapp.ImageSelectedActivity;
    import com.unicef.thaimai.motherapp.Preference.PreferenceData;
    import com.unicef.thaimai.motherapp.Presenter.AddVisitRecordsPresenter;
    import com.unicef.thaimai.motherapp.R;
    import com.unicef.thaimai.motherapp.model.requestmodel.AddRecordRequestModel;
    import com.unicef.thaimai.motherapp.utility.CheckNetwork;
    import com.unicef.thaimai.motherapp.view.AddRecordViews;

    import org.json.JSONException;
    import org.json.JSONObject;

    import java.text.SimpleDateFormat;
    import java.util.Calendar;
    import java.util.Locale;


    /**
     * Created by Suthishan on 20/1/2018.
     */

    public class AddRecords extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, AddRecordViews, RadioGroup.OnCheckedChangeListener {

        Spinner sp_type_of_visit, sp_facility, sp_any_complaints, sp_fundal_height,sp_liquor,sp_placenta,sp_gestation_sac,sp_fetus;

        EditText edt_date,edt_facility_other, edt_any_complaints_other, edt_bp_sys, edt_bp_dis, edt_pluse_rate, edt_weight, edt_fhs, edt_temp, edt_hemo,
                edt_rbs, edt_fbs, edt_ppbs, edt_fundal_height, edt_gtt, edt_tsh, edt_urine_sugar, edt_albumin, edt_fetus, edt_gestation_sac, edt_liquor, edt_placenta;
        RadioGroup rg_pep;
        RadioButton rb_yes, rb_no;
        Button btn_submit, upload_reports;

       public String strDate,strTypeOfVisit, strFacility, strAnyComplaints, strFundal_Height,
                strFacility_other, strany_complaints_other, strbp_sys, strbp_dis, strpluse_rate, strweight, strfhs, strtemp, strhemo,
                strrbs, strfbs, strppbs, strgtt, strtsh, strurine_sugar, stralbumin, strfetus, strgestation_sac, strliquor, strplacenta,strPep="Not Selected";

       public static String strTotalVisitCount="0";
       Calendar mCurrentDate;
       int day, month, year, hour, minute, sec;
        //    edt_facility_other,edt_any_complaints_other--->gone

       ProgressDialog pDialog;
       AddVisitRecordsPresenter addVisitRecordsPresenter;
       AddRecordRequestModel addRecordRequestModel;
       PreferenceData preferenceData;
        CheckNetwork checkNetwork;
        private SimpleDateFormat dateFormatter;


        @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.add_report);
           showActionBar();
           initUI();
           onClickListner();
           OnItemSelectedListener();
        }

        private void OnItemSelectedListener() {
            sp_type_of_visit.setOnItemSelectedListener(this);
            sp_facility.setOnItemSelectedListener(this);
            sp_any_complaints.setOnItemSelectedListener(this);
//            sp_fundal_height.setOnItemSelectedListener(this);
            rg_pep.setOnCheckedChangeListener(this);
            sp_liquor.setOnItemSelectedListener(this);;
            sp_placenta.setOnItemSelectedListener(this);;
            sp_gestation_sac.setOnItemSelectedListener(this);
            sp_fetus.setOnItemSelectedListener(this);

        }

        private void onClickListner() {
            btn_submit.setOnClickListener(this);
            upload_reports.setOnClickListener(this);
            edt_date.setOnClickListener(this);
        }

        private void initUI() {
            pDialog = new ProgressDialog(this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Please Wait ...");
            preferenceData = new PreferenceData(this);
            checkNetwork = new CheckNetwork(this);

            addVisitRecordsPresenter = new AddVisitRecordsPresenter(AddRecords.this, this);
            if (checkNetwork.isNetworkAvailable()) {
                addVisitRecordsPresenter.getVisitCount(preferenceData.getPicmeId(), preferenceData.getMId());
            }else{
                Toast.makeText(getApplicationContext(), "Check Internet Connection...Try Agian After Sometimes", Toast.LENGTH_LONG).show();
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
            edt_date =(EditText) findViewById(R.id.edt_date);
            sp_type_of_visit = (Spinner) findViewById(R.id.sp_type_of_visit);
            sp_facility = (Spinner) findViewById(R.id.sp_facility);
            sp_any_complaints = (Spinner) findViewById(R.id.sp_any_complaints);

            sp_liquor = (Spinner) findViewById(R.id.sp_liquor);
            sp_placenta = (Spinner) findViewById(R.id.sp_placenta);
            sp_gestation_sac = (Spinner) findViewById(R.id.sp_gestation_sac);
            sp_fetus = (Spinner) findViewById(R.id.sp_fetus);


            edt_fundal_height = (EditText) findViewById(R.id.edt_fundal_height);

            edt_facility_other = (EditText) findViewById(R.id.edt_facility_other);
            edt_facility_other.setVisibility(View.GONE);
            edt_any_complaints_other = (EditText) findViewById(R.id.edt_any_complaints_other);
            edt_any_complaints_other.setVisibility(View.GONE);
            edt_bp_sys = (EditText) findViewById(R.id.edt_bp_sys);
            edt_bp_dis = (EditText) findViewById(R.id.edt_bp_dis);
            edt_pluse_rate = (EditText) findViewById(R.id.edt_pluse_rate);
            edt_weight = (EditText) findViewById(R.id.edt_weight);
            edt_fhs = (EditText) findViewById(R.id.edt_fhs);
            edt_temp = (EditText) findViewById(R.id.edt_temp);
            edt_hemo = (EditText) findViewById(R.id.edt_hemo);
            edt_rbs = (EditText) findViewById(R.id.edt_rbs);
            edt_fbs = (EditText) findViewById(R.id.edt_fbs);
            edt_ppbs = (EditText) findViewById(R.id.edt_ppbs);
            edt_gtt = (EditText) findViewById(R.id.edt_gtt);
            edt_tsh = (EditText) findViewById(R.id.edt_tsh);
            edt_urine_sugar = (EditText) findViewById(R.id.edt_urine_sugar);
            edt_albumin = (EditText) findViewById(R.id.edt_albumin);
            edt_fetus = (EditText) findViewById(R.id.edt_fetus);
            edt_gestation_sac = (EditText) findViewById(R.id.edt_gestation_sac);
            edt_liquor = (EditText) findViewById(R.id.edt_liquor);
            edt_placenta = (EditText) findViewById(R.id.edt_placenta);
            rg_pep = (RadioGroup) findViewById(R.id.rg_pep);
            rb_yes = (RadioButton) findViewById(R.id.rb_yes);
            rb_no = (RadioButton) findViewById(R.id.rb_no);
            btn_submit = (Button) findViewById(R.id.btn_submit);
            upload_reports = (Button) findViewById(R.id.upload_reports);
            mCurrentDate = Calendar.getInstance();
            day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
            month = mCurrentDate.get(Calendar.MONTH);
            year = mCurrentDate.get(Calendar.YEAR);
            hour = mCurrentDate.get(Calendar.HOUR);
            minute = mCurrentDate.get(Calendar.MINUTE);
            sec = mCurrentDate.get(Calendar.SECOND);
            month = month + 1;
        }

        private void showActionBar() {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("AN Visit Entry");
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
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
                    if (checkNetwork.isNetworkAvailable()) {
                        sendtoServer();
                    }else{
                        Toast.makeText(getApplicationContext(), "Your are offline, Please Try Again After Sometimes." + checkNetwork.isNetworkAvailable(), Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.upload_reports:
                    if (checkNetwork.isNetworkAvailable()) {
                        startActivity(new Intent(getApplicationContext(), ImageSelectedActivity.class));
                    }else{
                        Toast.makeText(getApplicationContext(), "Your are offline, Please Try Again After Sometimes." + checkNetwork.isNetworkAvailable(), Toast.LENGTH_LONG).show();
                    }

                    break;
                case R.id.edt_date:
                    pickDate(edt_date);
                    break;
            }
        }

        private void pickDate(final EditText edt_date) {

            Calendar newCalendar = Calendar.getInstance();

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddRecords.this, R.style.DatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
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
            if (strDate.equalsIgnoreCase("")){
                showAlert("Date is Empty");
            } else if (strTypeOfVisit.equalsIgnoreCase("--Select--")){
                showAlert("type of is Empty");
            }else if (strFacility.equalsIgnoreCase("--Select--")){
                showAlert("Facility is Empty");
            }else if (strAnyComplaints.equalsIgnoreCase("--Select--")){
                showAlert("AnyComplaints is Empty");
            }else if (strbp_sys.equalsIgnoreCase("")){
                showAlert("Systolic is Empty");
            }else if (strbp_dis.equalsIgnoreCase("")){
                showAlert("Diastolic is Empty");
            }else if (strpluse_rate.equalsIgnoreCase("")){
                showAlert("pluse rate is Empty");
            }else if (strweight.equalsIgnoreCase("")){
                showAlert("Weight is Empty");
            }else if (strFundal_Height.equalsIgnoreCase("--Select--")){
                showAlert("Fundal Height is Empty");
            }else if (strfhs.equalsIgnoreCase("")){
                showAlert("FHS Height is Empty");
            }else if (strPep.equalsIgnoreCase("Not Selected")){
                showAlert("Pedal Edema Present Height is Empty");
            }else if (strtemp.equalsIgnoreCase("")){
                showAlert("BodyTemp is Empty");
            }else if (strhemo.equalsIgnoreCase("")){
                showAlert("Hemoglobin is Empty");
            }else if (strrbs.equalsIgnoreCase("")){
                showAlert("RBS is Empty");
            }else if (strfbs.equalsIgnoreCase("")){
                showAlert("FBS is Empty");
            }else if (strppbs.equalsIgnoreCase("")){
                showAlert("PPBS is Empty");
            }else if (strgtt.equalsIgnoreCase("")){
                showAlert("GTT is Empty");
            }else if (strurine_sugar.equalsIgnoreCase("")){
                showAlert("Urine sugar is Empty");
            }else if (stralbumin.equalsIgnoreCase("")){
                showAlert("Albumin is Empty");
            }else if (strfetus.equalsIgnoreCase("--Select--")){
                showAlert("Fetus is Empty");
            }else if (strliquor.equalsIgnoreCase("--Select--")){
                showAlert("Liquor is Empty");
            }else if (strgestation_sac.equalsIgnoreCase("--Select--")){
                showAlert("GestationSac is Empty");
            }else if (strplacenta.equalsIgnoreCase("--Select--")){
                showAlert("Placenta is Empty");
            }else if (strtsh.equalsIgnoreCase("")){
                showAlert("TSH is Empty");
            }
            else {
                addRecordRequestModel = new AddRecordRequestModel();
//                addRecordRequestModel.setVid("1");
                addRecordRequestModel.setVDate(strDate);
                addRecordRequestModel.setVisitId(strTotalVisitCount);
                addRecordRequestModel.setMid(preferenceData.getMId());
                addRecordRequestModel.setPicmeId(preferenceData.getPicmeId());
                addRecordRequestModel.setVtypeOfVisit(strTypeOfVisit);
                addRecordRequestModel.setVFacility(strFacility);
                addRecordRequestModel.setvFacilityOthers(strFacility_other);
                addRecordRequestModel.setvAnyComplaintsOthers(strany_complaints_other);
                addRecordRequestModel.setVAnyComplaints(strAnyComplaints);
                addRecordRequestModel.setVClinicalBPSystolic(strbp_sys);
                addRecordRequestModel.setVClinicalBPDiastolic(strbp_dis);
                addRecordRequestModel.setVEnterPulseRate(strpluse_rate);
                addRecordRequestModel.setVEnterWeight(strweight);
                addRecordRequestModel.setVFundalHeight(strFundal_Height);
                addRecordRequestModel.setVFHS(strfhs);
                addRecordRequestModel.setVPedalEdemaPresent(strPep);
                addRecordRequestModel.setVBodyTemp(strtemp);
                addRecordRequestModel.setVHemoglobin(strhemo);
                addRecordRequestModel.setVRBS(strrbs);
                addRecordRequestModel.setVFBS(strfbs);
                addRecordRequestModel.setVPPBS(strppbs);
                addRecordRequestModel.setVGTT(strgtt);
                addRecordRequestModel.setVUrinSugar(strurine_sugar);
                addRecordRequestModel.setVAlbumin(stralbumin);
                addRecordRequestModel.setUsgFetus(strfetus);
                addRecordRequestModel.setUsgLiquor(strliquor);
                addRecordRequestModel.setUsgGestationSac(strgestation_sac);
                addRecordRequestModel.setUsgPlacenta(strplacenta);
                addRecordRequestModel.setVTSH(strtsh);

                addVisitRecordsPresenter.insertVistRecords(addRecordRequestModel);
            }
       }

        private void showAlert(String msg) {
            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
        }

        private void getallEditTextvalues() {
            strDate = edt_date.getText().toString();
            strFacility_other = edt_facility_other.getText().toString();
            strany_complaints_other = edt_any_complaints_other.getText().toString();
            strbp_sys = edt_bp_sys.getText().toString();
            strbp_dis = edt_bp_dis.getText().toString();
            strpluse_rate = edt_pluse_rate.getText().toString();
            strweight = edt_weight.getText().toString();
            strfhs = edt_fhs.getText().toString();
            strtemp = edt_temp.getText().toString();
            strhemo = edt_hemo.getText().toString();
            strrbs = edt_rbs.getText().toString();
            strfbs = edt_fbs.getText().toString();
            strppbs = edt_ppbs.getText().toString();
            strgtt = edt_gtt.getText().toString();
            strtsh = edt_tsh.getText().toString();
            strurine_sugar = edt_urine_sugar.getText().toString();
            stralbumin = edt_albumin.getText().toString();
//            strfetus = edt_fetus.getText().toString();
//            strgestation_sac = edt_gestation_sac.getText().toString();
//            strliquor = edt_liquor.getText().toString();
//            strplacenta = edt_placenta.getText().toString();
            strFundal_Height = edt_fundal_height.getText().toString();
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.sp_type_of_visit:
                    strTypeOfVisit = parent.getSelectedItem().toString();
                    break;

                    case R.id.sp_liquor:
                    strliquor = parent.getSelectedItem().toString();
                    break;
                    case R.id.sp_placenta:
                    strplacenta = parent.getSelectedItem().toString();
                    break;
                    case R.id.sp_gestation_sac:
                    strgestation_sac = parent.getSelectedItem().toString();
                    break;
                    case R.id.sp_fetus:
                    strfetus = parent.getSelectedItem().toString();
                    break;

                case R.id.sp_facility:
                    strFacility = parent.getSelectedItem().toString();
                    if (strFacility.equalsIgnoreCase("Others")) {
                        edt_facility_other.setVisibility(View.VISIBLE);
                    }
                    else {
                        edt_facility_other.setVisibility(View.GONE);

                    }
                    break;
                case R.id.sp_any_complaints:
                    strAnyComplaints = parent.getSelectedItem().toString();
                    if (strAnyComplaints.equalsIgnoreCase("Others (Specify)")) {
                        edt_any_complaints_other.setVisibility(View.VISIBLE);
                    }
                    else {
                        strany_complaints_other="Not Selected Others";
                        edt_any_complaints_other.setVisibility(View.GONE);

                    }
                    break;
                /*case R.id.sp_fundal_height:
                    strFundal_Height = parent.getSelectedItem().toString();
                    break;*/
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

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
        public void insertRecordSuccess(String response) {
            Log.d(AddRecords.class.getSimpleName(), "Response Success--->" + response);
            try {
                JSONObject jsonObject =new JSONObject(response);
                String status =jsonObject.getString("status");
                String msg = jsonObject.getString("message");
                if (status.equalsIgnoreCase("1")){
                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }else{
                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    if(msg.equalsIgnoreCase("Your account is Deactivated")){
                        preferenceData.setLogin(false);
                        startActivity(new Intent(getApplicationContext(), Login.class));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void insertRecordFailiure(String response) {
            Log.d(AddRecords.class.getSimpleName(), "Response Failure-->" + response);
        }

        @Override
        public void getVisitIDSuccess(String response) {
            Log.d(AddRecords.class.getSimpleName(), "Response Success--->" + response);
            try {
                JSONObject jsonObject =new JSONObject(response);
                String status =jsonObject.getString("status");
                String msg = jsonObject.getString("message");

                if(status.equalsIgnoreCase("1")){
                    strTotalVisitCount= jsonObject.getString("visitId");
                }else {
                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
                if(msg.equalsIgnoreCase("Your account is Deactivated")){
                    preferenceData.setLogin(false);
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void getVisitIDFailiure(String response) {
            Log.d(AddRecords.class.getSimpleName(), "Response Success--->" + response);
        }


        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton rb = (RadioButton) group.findViewById(checkedId);
            if (null != rb && checkedId > -1) {
            }
            strPep =rb.getText().toString();
        }
    }
