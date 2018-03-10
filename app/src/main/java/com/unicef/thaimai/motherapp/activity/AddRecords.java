    package com.unicef.thaimai.motherapp.activity;

    import android.app.ProgressDialog;
    import android.content.Intent;
    import android.os.Bundle;
    import android.support.v7.app.ActionBar;
    import android.support.v7.app.AppCompatActivity;
    import android.util.Log;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.RadioButton;
    import android.widget.RadioGroup;
    import android.widget.Spinner;

    import com.unicef.thaimai.motherapp.Preference.PreferenceData;
    import com.unicef.thaimai.motherapp.Presenter.AddVisitRecordsPresenter;
    import com.unicef.thaimai.motherapp.Presenter.PrimaryRegisterPresenter;
    import com.unicef.thaimai.motherapp.R;
    import com.unicef.thaimai.motherapp.model.requestmodel.AddRecordRequestModel;
    import com.unicef.thaimai.motherapp.model.requestmodel.PrimaryDataRequestModel;
    import com.unicef.thaimai.motherapp.view.AddRecordViews;
    import com.unicef.thaimai.motherapp.view.PrimaryRegisterViews;


    /**
     * Created by Suthishan on 20/1/2018.
     */

    public class AddRecords extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, AddRecordViews {

        Spinner sp_type_of_visit, sp_facility, sp_any_complaints, sp_fundal_height;

        EditText edt_facility_other, edt_any_complaints_other, edt_bp_sys, edt_bp_dis, edt_pluse_rate, edt_weight, edt_fhs, edt_temp, edt_hemo,
                edt_rbs, edt_fbs, edt_ppbs, edt_gtt, edt_tsh, edt_urine_sugar, edt_albumin, edt_fetus, edt_gestation_sac, edt_liquor, edt_placenta;
        RadioGroup rg_pep;
        RadioButton rb_yes, rb_no;
        Button btn_submit;

       public String strTypeOfVisit, strFacility, strAnyComplaints, strFundal_Height,
                strFacility_other, strany_complaints_other, strbp_sys, strbp_dis, strpluse_rate, strweight, strfhs, strtemp, strhemo,
                strrbs, strfbs, strppbs, strgtt, strtsh, strurine_sugar, stralbumin, strfetus, strgestation_sac, strliquor, strplacenta;

        //    edt_facility_other,edt_any_complaints_other--->gone

        ProgressDialog pDialog;
        AddVisitRecordsPresenter addVisitRecordsPresenter;
        AddRecordRequestModel addRecordRequestModel;
        PreferenceData preferenceData;

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
            sp_fundal_height.setOnItemSelectedListener(this);
        }

        private void onClickListner() {
            btn_submit.setOnClickListener(this);
        }

        private void initUI() {
            pDialog = new ProgressDialog(this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Please Wait ...");
            preferenceData = new PreferenceData(this);
            addVisitRecordsPresenter = new AddVisitRecordsPresenter(AddRecords.this, this);
            sp_type_of_visit = (Spinner) findViewById(R.id.sp_type_of_visit);
            sp_facility = (Spinner) findViewById(R.id.sp_facility);
            sp_any_complaints = (Spinner) findViewById(R.id.sp_any_complaints);
            sp_fundal_height = (Spinner) findViewById(R.id.sp_fundal_height);
            edt_facility_other = (EditText) findViewById(R.id.edt_facility_other);
            edt_any_complaints_other = (EditText) findViewById(R.id.edt_any_complaints_other);
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
        }

        private void showActionBar() {
            ActionBar actionBar = getSupportActionBar();

            actionBar.setTitle("Add Records");

            actionBar.setHomeButtonEnabled(true);

            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            Intent intent = new Intent(AddRecords.this, MainActivity.class);
            finish();
            startActivity(intent);
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
            addRecordRequestModel = new AddRecordRequestModel();
            addRecordRequestModel.setVid("1");
            addRecordRequestModel.setVDate("24-4-2018");
            addRecordRequestModel.setVisitId("6");
            addRecordRequestModel.setMid("1");
            addRecordRequestModel.setPhcId(preferenceData.getPicmeId());
            addRecordRequestModel.setVtypeOfVisit(strTypeOfVisit);
            addRecordRequestModel.setVFacility(strFacility);
            addRecordRequestModel.setVAnyComplaints(strAnyComplaints);
            addRecordRequestModel.setVClinicalBPSystolic(strbp_sys);
            addRecordRequestModel.setVClinicalBPDiastolic(strbp_dis);
            addRecordRequestModel.setVEnterPulseRate(strpluse_rate);
            addRecordRequestModel.setVEnterWeight(strweight);
            addRecordRequestModel.setVFundalHeight(strFundal_Height);
            addRecordRequestModel.setVFHS(strfhs);
            addRecordRequestModel.setVPedalEdemaPresent("");
            addRecordRequestModel.setVBodyTemp(strtemp);
            addRecordRequestModel.setVHemoglobin(strhemo);
            addRecordRequestModel.setVRBS(strrbs);
            addRecordRequestModel.setVFBS(strfbs);
            addRecordRequestModel.setVPPBS(strppbs);
            addRecordRequestModel.setVGTT(strgtt);
            addRecordRequestModel.setVUrinSugar(strurine_sugar);
            addRecordRequestModel.setUsgFetus(strfetus);
            addRecordRequestModel.setUsgLiquor(strliquor);
            addRecordRequestModel.setUsgPlacenta(strplacenta);
            addRecordRequestModel.setUsgPlacenta(strplacenta);
            addRecordRequestModel.setVTSH(strtsh);

            addVisitRecordsPresenter.insertVistRecords(addRecordRequestModel);
            Log.d("addRecordRequestModel",addRecordRequestModel.getVid());
            Log.d("addRecordRequestModel",addRecordRequestModel.getVDate());
            Log.d("addRecordRequestModel",addRecordRequestModel.getVisitId());
            Log.d("addRecordRequestModel",addRecordRequestModel.getMid());
            Log.d("addRecordRequestModel",addRecordRequestModel.getPhcId());
            Log.d("addRecordRequestModel",addRecordRequestModel.getVtypeOfVisit());
            Log.d("addRecordRequestModel",addRecordRequestModel.getVFacility());
            Log.d("addRecordRequestModel",addRecordRequestModel.getVAnyComplaints());
            Log.d("addRecordRequestModel",addRecordRequestModel.getVClinicalBPSystolic());
            Log.d("addRecordRequestModel",addRecordRequestModel.getVClinicalBPDiastolic());
            Log.d("addRecordRequestModel",addRecordRequestModel.getVEnterPulseRate());
            Log.d("addRecordRequestModel",addRecordRequestModel.getVEnterWeight());
        }

        private void getallEditTextvalues() {
            strFacility_other = edt_facility_other.getText().toString();
            strany_complaints_other = edt_facility_other.getText().toString();
            strbp_sys = edt_facility_other.getText().toString();
            strbp_dis = edt_facility_other.getText().toString();
            strpluse_rate = edt_facility_other.getText().toString();
            strweight = edt_facility_other.getText().toString();
            strfhs = edt_facility_other.getText().toString();
            strtemp = edt_facility_other.getText().toString();
            strhemo = edt_facility_other.getText().toString();
            strrbs = edt_facility_other.getText().toString();
            strfbs = edt_facility_other.getText().toString();
            strppbs = edt_facility_other.getText().toString();
            strgtt = edt_facility_other.getText().toString();
            strtsh = edt_facility_other.getText().toString();
            strurine_sugar = edt_facility_other.getText().toString();
            stralbumin = edt_facility_other.getText().toString();
            strfetus = edt_facility_other.getText().toString();
            strgestation_sac = edt_facility_other.getText().toString();
            strliquor = edt_facility_other.getText().toString();
            strplacenta = edt_facility_other.getText().toString();
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.sp_type_of_visit:
                    strTypeOfVisit = parent.getSelectedItem().toString();
                    break;
                case R.id.sp_facility:
                    strFacility = parent.getSelectedItem().toString();
                    break;
                case R.id.sp_any_complaints:
                    strAnyComplaints = parent.getSelectedItem().toString();
                    break;
                case R.id.sp_fundal_height:
                    strFundal_Height = parent.getSelectedItem().toString();
                    break;
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
        public void insertRecordSuccess(String response) {
            Log.d(AddRecords.class.getSimpleName(), "Response Success--->" + response);

        }

        @Override
        public void insertRecordFailiure(String response) {
            Log.d(AddRecords.class.getSimpleName(), "Response Failiur-->" + response);

        }


    }
