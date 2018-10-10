package com.unicef.thaimai.motherapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.PrimaryRegisterPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.app.RealmController;
import com.unicef.thaimai.motherapp.realmDbModelClass.PrimaryRegisterRealmModel;
import com.unicef.thaimai.motherapp.utility.CheckNetwork;
import com.unicef.thaimai.motherapp.view.PrimaryRegisterViews;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class PrimaryRegisterView extends AppCompatActivity implements PrimaryRegisterViews {

    TextView txt_name, txt_mother_age, txt_lmp_date, txt_edd_date, txt_pry_mobile_no,
            txt_alter_mobile_no,txt_mother_occupation, txt_hus_occupation, txt_age_at_marriage,txt_consanguineous_marraige,
            txt_history_of_illness, txt_history_of_illness_family, txt_any_surgery_done, txt_tobacco, txt_alcohol,
            txt_on_any_medication, txt_allergic_to_any_drug, txt_history_of_previous_pregnancy, txt_lscs_done,
            txt_any_complication, txt_g, txt_p, txt_a, txt_l, txt_registration_week, txt_an_tt_1st, txt_an_tt_2nd,
            txt_ifa_start_date, txt_height, txt_blood_group, txt_hiv, txt_vdrl, txt_Hepatitis, txt_hus_blood_group,
            txt_hus_hiv, txt_hus_vdrl, txt_hus_Hepatitis;
    FloatingActionButton fab_edi_details;
    ProgressDialog pDialog;
    PrimaryRegisterPresenter primaryRegisterPresenter;

    PreferenceData preferenceData;
    TextView txt_no_records_found;
    FrameLayout primary_register;

    boolean isoffline = false;
    Realm realm;
    CheckNetwork checkNetwork;
    PrimaryRegisterRealmModel primaryRegisterRealmModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = RealmController.with(this).getRealm();
        setContentView(R.layout.primary_register_view);
        showActionBar();
        initUI();
        onClickListner();

    }

    private void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Primary Records");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        finish();
//        return super.onOptionsItemSelected(item);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        return super.onOptionsItemSelected(item);
    }

    private void onClickListner() {
        fab_edi_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PrimaryRegister.class));
            }
        });
    }

    private void initUI() {
        checkNetwork =new CheckNetwork(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        preferenceData = new PreferenceData(this);
        primaryRegisterPresenter = new PrimaryRegisterPresenter(PrimaryRegisterView.this, this);
        if (checkNetwork.isNetworkAvailable()) {
            primaryRegisterPresenter.getAllMotherPrimaryRegistration(preferenceData.getPicmeId());
        }else{
            isoffline=true;
        }
        txt_no_records_found = (TextView) findViewById(R.id.txt_no_records_found);
        primary_register = (FrameLayout) findViewById(R.id.primary_register);
        fab_edi_details = (FloatingActionButton) findViewById(R.id.fab_edi_details);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_mother_age = (TextView) findViewById(R.id.txt_mother_age);
        txt_lmp_date = (TextView) findViewById(R.id.txt_lmp_date);
        txt_edd_date  = (TextView) findViewById(R.id.txt_edd_date);
        txt_pry_mobile_no = (TextView) findViewById(R.id.txt_pry_mobile_no);
        txt_alter_mobile_no = (TextView) findViewById(R.id.txt_alter_mobile_no);
        txt_mother_occupation = (TextView) findViewById(R.id.txt_mother_occupation);
        txt_hus_occupation = (TextView) findViewById(R.id.txt_hus_occupation);
        txt_age_at_marriage = (TextView) findViewById(R.id.txt_age_at_marriage);
        txt_consanguineous_marraige = (TextView) findViewById(R.id.txt_consanguineous_marraige);
        txt_history_of_illness = (TextView) findViewById(R.id.txt_history_of_illness);
        txt_history_of_illness_family  = (TextView) findViewById(R.id.txt_history_of_illness_family);
        txt_any_surgery_done = (TextView) findViewById(R.id.txt_any_surgery_done);
        txt_tobacco = (TextView) findViewById(R.id.txt_tobacco);
        txt_alcohol = (TextView) findViewById(R.id.txt_alcohol);
        txt_on_any_medication = (TextView) findViewById(R.id.txt_on_any_medication);
        txt_allergic_to_any_drug = (TextView) findViewById(R.id.txt_allergic_to_any_drug);
        txt_history_of_previous_pregnancy  = (TextView) findViewById(R.id.txt_history_of_previous_pregnancy);
        txt_lscs_done = (TextView) findViewById(R.id.txt_lscs_done);
        txt_any_complication = (TextView) findViewById(R.id.txt_any_complication);
        txt_g = (TextView) findViewById(R.id.txt_g);
        txt_p = (TextView) findViewById(R.id.txt_p);
        txt_a = (TextView) findViewById(R.id.txt_a);
        txt_l = (TextView) findViewById(R.id.txt_l);
        txt_registration_week = (TextView) findViewById(R.id.txt_registration_week);
        txt_an_tt_1st = (TextView) findViewById(R.id.txt_an_tt_1st);
        txt_an_tt_2nd = (TextView) findViewById(R.id.txt_an_tt_2nd);
        txt_ifa_start_date = (TextView) findViewById(R.id.txt_ifa_start_date);
        txt_height = (TextView) findViewById(R.id.txt_height);
        txt_blood_group  = (TextView) findViewById(R.id.txt_blood_group);
        txt_hiv = (TextView) findViewById(R.id.txt_hiv);
        txt_vdrl = (TextView) findViewById(R.id.txt_vdrl);
        txt_Hepatitis = (TextView) findViewById(R.id.txt_Hepatitis);
        txt_hus_blood_group = (TextView) findViewById(R.id.txt_hus_blood_group);
        txt_hus_hiv = (TextView) findViewById(R.id.txt_hus_hiv);
        txt_hus_vdrl = (TextView) findViewById(R.id.txt_hus_vdrl);
        txt_hus_Hepatitis = (TextView) findViewById(R.id.txt_hus_Hepatitis);

        if (isoffline) {
            primaryRecordsOffline();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Record Not Found");
            builder.create();
        }

    }

    private void primaryRecordsOffline() {
        RealmResults<PrimaryRegisterRealmModel> primaryRegisterRealmModels;
        realm.beginTransaction();
        primaryRegisterRealmModels = realm.where(PrimaryRegisterRealmModel.class).equalTo("picmeId", preferenceData.getPicmeId()).findAll();
        Log.e(String.valueOf(PrimaryRegisterView.class),primaryRegisterRealmModels.size()+"");
        Log.e(PrimaryRegisterView.class.getSimpleName(),"primaryRegisterRealmModels  -->"+primaryRegisterRealmModels);
        if(primaryRegisterRealmModels.size()!=0){
            primary_register.setVisibility(View.VISIBLE);
            txt_no_records_found.setVisibility(View.GONE);
            for(int i=0;i<primaryRegisterRealmModels.size();i++) {
                PrimaryRegisterRealmModel model =primaryRegisterRealmModels.get(i);

                if(model.getMName().equalsIgnoreCase("null")){
                    txt_name.setText("-");
                }else {
                    txt_name.setText(model.getMName());
                }
                if(model.getMAge().equalsIgnoreCase("null")){
                    txt_mother_age.setText("-");
                }else {
                    txt_mother_age.setText(model.getMAge());
                }
                if(model.getMLMP().equalsIgnoreCase("null")){
                    txt_lmp_date.setText("-");
                }else {
                    txt_lmp_date.setText(model.getMLMP());
                }
                if(model.getMEDD().equalsIgnoreCase("null")){
                    txt_edd_date.setText("-");
                }else {
                    txt_edd_date.setText(model.getMEDD());
                }
                if(model.getMMotherMobile().equalsIgnoreCase("null")){
                    txt_pry_mobile_no.setText("-");
                }else {
                    txt_pry_mobile_no.setText(model.getMMotherMobile());
                }
                if (model.getMHusbandMobile().equalsIgnoreCase("null")){
                    txt_alter_mobile_no.setText("-");
                }else {
                    txt_alter_mobile_no.setText(model.getMHusbandMobile());
                }
                if(model.getMMotherOccupation().equalsIgnoreCase("null")){
                    txt_mother_occupation.setText("-");
                }else {
                    txt_mother_occupation.setText(model.getMMotherOccupation());
                }
                if (model.getMHusbandOccupation().equalsIgnoreCase("null")){
                    txt_hus_occupation.setText("-");
                }else {
                    txt_hus_occupation.setText(model.getMHusbandOccupation());
                }
                if(model.getMAgeatMarriage().equalsIgnoreCase("null")){
                    txt_age_at_marriage.setText("-");
                }else {
                    txt_age_at_marriage.setText(model.getMAgeatMarriage());
                }
                if(model.getMConsanguineousMarraige().equalsIgnoreCase("null")){
                    txt_consanguineous_marraige.setText("-");
                }else {
                    txt_consanguineous_marraige.setText(model.getMConsanguineousMarraige());
                }
                if(model.getMHistoryIllness().equalsIgnoreCase("null")){
                    txt_history_of_illness.setText("-");
                }else {
                    if(model.getMHistoryIllnessOthers().equalsIgnoreCase("null")){
                        txt_history_of_illness.setText(model.getMHistoryIllness());
                    }else {
                        txt_history_of_illness.setText(model.getMHistoryIllness()+"-"+model.getMHistoryIllnessOthers());
                    }
                }
                if(model.getMHistoryIllnessFamily().equalsIgnoreCase("null")){
                    txt_history_of_illness_family.setText("-");
                }else {
                    if (model.getMHistoryIllnessFamilyOthers().equalsIgnoreCase("null")) {
                        txt_history_of_illness_family.setText(model.getMHistoryIllnessFamily());
                    }
                    else{
                        txt_history_of_illness_family.setText(model.getMHistoryIllnessFamily()+"-"+model.getMHistoryIllnessFamilyOthers());

                    }
                }
                if(model.getMAnySurgeryBefore().equalsIgnoreCase("null")){
                    txt_any_surgery_done.setText("-");
                }else {
                    txt_any_surgery_done.setText(model.getMAnySurgeryBefore());
                }
                if(model.getMUseTobacco().equalsIgnoreCase("null")){
                    txt_tobacco.setText("-");
                }else {
                    txt_tobacco.setText(model.getMUseTobacco());
                }
                if(model.getMUseAlcohol().equalsIgnoreCase("null")){
                    txt_alcohol.setText("-");
                }else {
                    txt_alcohol.setText(model.getMUseAlcohol());
                }
                if(model.getMAnyMeditation().equalsIgnoreCase("null")){
                    txt_on_any_medication.setText("-");
                }else {
                    txt_on_any_medication.setText(model.getMAnyMeditation());
                }
                if(model.getMAllergicToanyDrug().equalsIgnoreCase("null")){
                    txt_allergic_to_any_drug.setText("-");
                }else {
                    txt_allergic_to_any_drug.setText(model.getMAllergicToanyDrug());
                }
                if(model.getMHistroyPreviousPreganancy().equalsIgnoreCase("null")){
                    txt_history_of_previous_pregnancy.setText("-");
                }else {
                    txt_history_of_previous_pregnancy.setText(model.getMHistroyPreviousPreganancy());
                }
                if(model.getMLscsDone().equalsIgnoreCase("null")){
                    txt_lscs_done.setText("-");
                }else {
                    txt_lscs_done.setText(model.getMLscsDone());
                }
                if(model.getMAnyComplecationDuringPreganancy().equalsIgnoreCase("null")){
                    txt_any_complication.setText("-");
                }else {
                    txt_any_complication.setText(model.getMAnyComplecationDuringPreganancy());
                }
                if(model.getMPresentPreganancyG().equalsIgnoreCase("null")){
                    txt_g.setText("-");
                }else {
                    txt_g.setText(model.getMPresentPreganancyG());
                }
                if(model.getMPresentPreganancyP().equalsIgnoreCase("null")){
                    txt_p.setText("-");
                }else{
                    txt_p.setText(model.getMPresentPreganancyP());
                }
                if(model.getMPresentPreganancyA().equalsIgnoreCase("null")){
                    txt_a.setText("-");
                }else {
                    txt_a.setText(model.getMPresentPreganancyA());
                }
                if(model.getMPresentPreganancyL().equalsIgnoreCase("null")){
                    txt_l.setText("-");
                }else {
                    txt_l.setText(model.getMPresentPreganancyL());
                }
                if(model.getMRegistrationWeek().equalsIgnoreCase("null")){
                    txt_registration_week.setText("-");
                }else {
                    txt_registration_week.setText(model.getMRegistrationWeek());
                }
                if(model.getMANTT1().equalsIgnoreCase("null")){
                    txt_an_tt_1st.setText("-");
                }else {
                    txt_an_tt_1st.setText(model.getMANTT1());
                }
                if(model.getMANTT2().equalsIgnoreCase("null")){
                    txt_an_tt_2nd.setText("-");
                }else {
                    txt_an_tt_2nd.setText(model.getMANTT2());
                }
                if(model.getMIFAStateDate().equalsIgnoreCase("null")){
                    txt_ifa_start_date.setText("-");
                }else {
                    txt_ifa_start_date.setText(model.getMIFAStateDate());
                }
                if(model.getMHeight().equalsIgnoreCase("null")){
                    txt_height.setText("-");
                }else {
                    txt_height.setText(model.getMHeight() + "cms");
                }
                if(model.getMBloodGroup().equalsIgnoreCase("null")){
                    txt_blood_group.setText("-");
                }else {
                    txt_blood_group.setText(model.getMBloodGroup());
                }
                if(model.getMHIV().equalsIgnoreCase("null")){
                    txt_hiv.setText("-");
                }else {
                    txt_hiv.setText(model.getMHIV());
                }
                if(model.getMVDRL().equalsIgnoreCase("null")){
                    txt_vdrl.setText("-");
                }else {
                    txt_vdrl.setText(model.getMVDRL());
                }
                if(model.getMHepatitis().equalsIgnoreCase("null")){
                    txt_Hepatitis.setText("-");
                }else {
                    txt_Hepatitis.setText(model.getMHepatitis());
                }
                if(model.getHBloodGroup().equalsIgnoreCase("null")){
                    txt_hus_blood_group.setText("-");
                }else {
                    txt_hus_blood_group.setText(model.getHBloodGroup());
                }
                if(model.getHHIV().equalsIgnoreCase("null")){
                    txt_hus_hiv.setText("-");
                }else {
                    txt_hus_hiv.setText(model.getHHIV());
                }
                if(model.getHVDRL().equalsIgnoreCase("null")){
                    txt_hus_vdrl.setText("-");
                }else {
                    txt_hus_vdrl.setText(model.getHVDRL());
                }
                if(model.getHHepatitis().equalsIgnoreCase("null")){
                    txt_hus_Hepatitis.setText("-");
                }else {
                    txt_hus_Hepatitis.setText(model.getHHepatitis());
                }
            }
            realm.commitTransaction();
            pDialog.dismiss();
        }else{
            primary_register.setVisibility(View.GONE);
            txt_no_records_found.setVisibility(View.VISIBLE);
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

    private void setValuetoUI(String response) {

        JSONObject jObj = null;
        try {
            jObj = new JSONObject(response);
            int status = jObj.getInt("status");
            String message = jObj.getString("message");
            if (status==1){
                primary_register.setVisibility(View.VISIBLE);
                txt_no_records_found.setVisibility(View.GONE);
                /*Log.d("message---->",message);
                if(jObj.getString("mName").equalsIgnoreCase("null")){
                    txt_name.setText("-");
                    }else {
                        txt_name.setText(jObj.getString("mName"));
                    }
                if (jObj.getString("mAge").equalsIgnoreCase("null")){
                    txt_mother_age.setText("-");
                }else {
                    txt_mother_age.setText(jObj.getString("mAge"));
                }
                if (jObj.getString("mLMP").equalsIgnoreCase("null")){
                    txt_lmp_date.setText("-");
                }else {
                    txt_lmp_date.setText(jObj.getString("mLMP"));
                }
                if (jObj.getString("mEDD").equalsIgnoreCase("null")){
                    txt_edd_date.setText("-");
                }else {
                    txt_edd_date.setText(jObj.getString("mEDD"));
                }
                if (jObj.getString("mMotherMobile").equalsIgnoreCase("null")){
                    txt_pry_mobile_no.setText("-");
                }else {
                    txt_pry_mobile_no.setText(jObj.getString("mMotherMobile"));
                }
                if (jObj.getString("mHusbandMobile").equalsIgnoreCase("null")){
                    txt_alter_mobile_no.setText("-");
                }else {
                    txt_alter_mobile_no.setText(jObj.getString("mHusbandMobile"));
                }
                if (jObj.getString("mMotherOccupation").equalsIgnoreCase("null")){
                    txt_mother_occupation.setText("-");
                }else {
                    txt_mother_occupation.setText(jObj.getString("mMotherOccupation"));
                }
                if (jObj.getString("mHusbandOccupation").equalsIgnoreCase("null")){
                    txt_hus_occupation.setText("-");
                }else {
                    txt_hus_occupation.setText(jObj.getString("mHusbandOccupation"));
                }
                if (jObj.getString("mAgeatMarriage").equalsIgnoreCase("null")){
                    txt_age_at_marriage.setText("-");
                }else {
                    txt_age_at_marriage.setText(jObj.getString("mAgeatMarriage"));
                }
                if (jObj.getString("mConsanguineousMarraige").equalsIgnoreCase("null")){
                    txt_consanguineous_marraige.setText("-");
                }else {
                    txt_consanguineous_marraige.setText(jObj.getString("mConsanguineousMarraige"));
                }
                if (jObj.getString("mHistoryIllness").equalsIgnoreCase("null")){
                    txt_history_of_illness.setText("-");
                }else {
                    txt_history_of_illness.setText(jObj.getString("mHistoryIllness"));
                }
                if (jObj.getString("mHistoryIllnessFamily").equalsIgnoreCase("null")){
                    txt_history_of_illness_family.setText("-");
                }else {
                    txt_history_of_illness_family.setText(jObj.getString("mHistoryIllnessFamily"));
                }
                if (jObj.getString("mAnySurgeryBefore").equalsIgnoreCase("null")){
                    txt_any_surgery_done.setText("-");
                }else {
                    txt_any_surgery_done.setText(jObj.getString("mAnySurgeryBefore"));
                }
                if (jObj.getString("mUseTobacco").equalsIgnoreCase("null")){
                    txt_tobacco.setText("-");
                }else {
                    txt_tobacco.setText(jObj.getString("mUseTobacco"));
                }
                if (jObj.getString("mUseAlcohol").equalsIgnoreCase("null")){
                    txt_alcohol.setText("-");
                }else {
                    txt_alcohol.setText(jObj.getString("mUseAlcohol"));
                }
                if (jObj.getString("mAnyMeditation").equalsIgnoreCase("null")){
                    txt_on_any_medication.setText("-");
                }else {
                    txt_on_any_medication.setText(jObj.getString("mAnyMeditation"));
                }
                if (jObj.getString("mAllergicToanyDrug").equalsIgnoreCase("null")){
                    txt_allergic_to_any_drug.setText("-");
                }else {
                    txt_allergic_to_any_drug.setText(jObj.getString("mAllergicToanyDrug"));
                }
                if (jObj.getString("mHistroyPreviousPreganancy").equalsIgnoreCase("null")){
                    txt_history_of_previous_pregnancy.setText("-");
                }else {
                    txt_history_of_previous_pregnancy.setText(jObj.getString("mHistroyPreviousPreganancy"));
                }
                if (jObj.getString("mLscsDone").equalsIgnoreCase("null")){
                    txt_lscs_done.setText("-");
                }else {
                    txt_lscs_done.setText(jObj.getString("mLscsDone"));
                }
                if (jObj.getString("mAnyComplecationDuringPreganancy").equalsIgnoreCase("null")){
                    txt_any_complication.setText("-");
                }else {
                    txt_any_complication.setText(jObj.getString("mAnyComplecationDuringPreganancy"));
                }
                if (jObj.getString("mPresentPreganancyG").equalsIgnoreCase("null")){
                    txt_g.setText("-");
                }else {
                    txt_g.setText(jObj.getString("mPresentPreganancyG"));
                }
                if (jObj.getString("mPresentPreganancyP").equalsIgnoreCase("null")){
                    txt_p.setText("-");
                }else {
                    txt_p.setText(jObj.getString("mPresentPreganancyP"));
                }
                if (jObj.getString("mPresentPreganancyA").equalsIgnoreCase("null")){
                    txt_a.setText("-");
                }else {
                    txt_a.setText(jObj.getString("mPresentPreganancyA"));
                }
                if (jObj.getString("mPresentPreganancyL").equalsIgnoreCase("null")){
                    txt_l.setText("-");
                }else {
                    txt_l.setText(jObj.getString("mPresentPreganancyL"));
                }
                if (jObj.getString("mRegistrationWeek").equalsIgnoreCase("null")){
                    txt_registration_week.setText("-");
                }else {
                    txt_registration_week.setText(jObj.getString("mRegistrationWeek"));
                }
                if (jObj.getString("mANTT1").equalsIgnoreCase("null")){
                    txt_an_tt_1st.setText("-");
                }else{
                    txt_an_tt_1st.setText(jObj.getString("mANTT1"));
                }
                if (jObj.getString("mANTT2").equalsIgnoreCase("null")){
                    txt_an_tt_2nd.setText("-");
                }else {
                    txt_an_tt_2nd.setText(jObj.getString("mANTT2"));
                }
                if (jObj.getString("mIFAStateDate").equalsIgnoreCase("null")){
                    txt_ifa_start_date.setText("-");
                }else {
                    txt_ifa_start_date.setText(jObj.getString("mIFAStateDate"));
                }
                if (jObj.getString("mHeight").equalsIgnoreCase("null")){
                    txt_height.setText("-");
                }else{
                    txt_height.setText(jObj.getString("mHeight"));
                }
                if (jObj.getString("mBloodGroup").equalsIgnoreCase("null")){
                    txt_blood_group.setText("-");
                }else {
                    txt_blood_group.setText(jObj.getString("mBloodGroup"));
                }
                if (jObj.getString("mHIV").equalsIgnoreCase("null")){
                    txt_hiv.setText("-");
                }else {
                    txt_hiv.setText(jObj.getString("mHIV"));
                }
                if (jObj.getString("mVDRL").equalsIgnoreCase("null")){
                    txt_vdrl.setText("-");
                }else {
                    txt_vdrl.setText(jObj.getString("mVDRL"));
                }
                if (jObj.getString("mHepatitis").equalsIgnoreCase("null")){
                    txt_Hepatitis.setText("-");
                }else {
                    txt_Hepatitis.setText(jObj.getString("mHepatitis"));
                }
                if (jObj.getString("hBloodGroup").equalsIgnoreCase("null")){
                    txt_hus_blood_group.setText("-");
                }else {
                    txt_hus_blood_group.setText(jObj.getString("hBloodGroup"));
                }
                if (jObj.getString("hVDRL").equalsIgnoreCase("null")){
                    txt_hus_hiv.setText("-");
                }else {
                    txt_hus_hiv.setText(jObj.getString("hVDRL"));
                }
                if (jObj.getString("hHIV").equalsIgnoreCase("null")){
                    txt_hus_vdrl.setText("-");
                }else {
                    txt_hus_vdrl.setText(jObj.getString("hHIV"));
                }
                if (jObj.getString("hHepatitis").equalsIgnoreCase("null")){
                    txt_hus_Hepatitis.setText("-");
                }else {
                    txt_hus_Hepatitis.setText(jObj.getString("hHepatitis"));
                }*/

                RealmResults<PrimaryRegisterRealmModel> primaryRegisterRealmModels = realm.where(PrimaryRegisterRealmModel.class).findAll();
                if (primaryRegisterRealmModels.size()!=0) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.delete(PrimaryRegisterRealmModel.class);
                        }
                    });
                }
                realm.beginTransaction();
                primaryRegisterRealmModel = realm.createObject(PrimaryRegisterRealmModel.class);

                primaryRegisterRealmModel.setId(jObj.getString("id"));
                primaryRegisterRealmModel.setMName(jObj.getString("mName"));
                primaryRegisterRealmModel.setMAge(jObj.getString("mAge"));
                primaryRegisterRealmModel.setMLMP(jObj.getString("mLMP"));
                primaryRegisterRealmModel.setMEDD(jObj.getString("mEDD"));
                primaryRegisterRealmModel.setMMotherMobile(jObj.getString("mMotherMobile"));
                primaryRegisterRealmModel.setMHusbandMobile(jObj.getString("mHusbandMobile"));
                primaryRegisterRealmModel.setMasterId(jObj.getString("masterId"));
                primaryRegisterRealmModel.setPicmeId(jObj.getString("picmeId"));
                primaryRegisterRealmModel.setVhnId(jObj.getString("vhnId"));
                primaryRegisterRealmModel.setTrasVhnId(jObj.getString("trasVhnId"));
                primaryRegisterRealmModel.setAwwId(jObj.getString("awwId"));
                primaryRegisterRealmModel.setPhcId(jObj.getString("phcId"));
                primaryRegisterRealmModel.setMRiskStatus(jObj.getString("mRiskStatus"));
                primaryRegisterRealmModel.setMMotherOccupation(jObj.getString("mMotherOccupation"));
                primaryRegisterRealmModel.setMHusbandOccupation(jObj.getString("mHusbandOccupation"));
                primaryRegisterRealmModel.setMAgeatMarriage(jObj.getString("mAgeatMarriage"));
                primaryRegisterRealmModel.setMConsanguineousMarraige(jObj.getString("mConsanguineousMarraige"));
                primaryRegisterRealmModel.setMHistoryIllness(jObj.getString("mHistoryIllness"));
                primaryRegisterRealmModel.setMHistoryIllnessFamily(jObj.getString("mHistoryIllnessFamily"));

                primaryRegisterRealmModel.setMHistoryIllnessOthers(jObj.getString("mHistoryIllnessOthers"));
                primaryRegisterRealmModel.setMHistoryIllnessFamilyOthers(jObj.getString("mHistoryIllnessFamilyOthers"));

                primaryRegisterRealmModel.setMAnySurgeryBefore(jObj.getString("mAnySurgeryBefore"));
                primaryRegisterRealmModel.setMUseTobacco(jObj.getString("mUseTobacco"));
                primaryRegisterRealmModel.setMUseAlcohol(jObj.getString("mUseAlcohol"));
                primaryRegisterRealmModel.setMAnyMeditation(jObj.getString("mAnyMeditation"));
                primaryRegisterRealmModel.setMAllergicToanyDrug(jObj.getString("mAllergicToanyDrug"));
                primaryRegisterRealmModel.setMHistroyPreviousPreganancy(jObj.getString("mHistroyPreviousPreganancy"));
                primaryRegisterRealmModel.setMLscsDone(jObj.getString("mLscsDone"));
                primaryRegisterRealmModel.setMAnyComplecationDuringPreganancy(jObj.getString("mAnyComplecationDuringPreganancy"));
                primaryRegisterRealmModel.setMPresentPreganancyG(jObj.getString("mPresentPreganancyG"));
                primaryRegisterRealmModel.setMPresentPreganancyP(jObj.getString("mPresentPreganancyP"));
                primaryRegisterRealmModel.setMPresentPreganancyA(jObj.getString("mPresentPreganancyA"));
                primaryRegisterRealmModel.setMPresentPreganancyL(jObj.getString("mPresentPreganancyL"));
                primaryRegisterRealmModel.setMRegistrationWeek(jObj.getString("mRegistrationWeek"));
                primaryRegisterRealmModel.setMANTT1(jObj.getString("mANTT1"));
                primaryRegisterRealmModel.setMANTT2(jObj.getString("mANTT2"));
                primaryRegisterRealmModel.setMIFAStateDate(jObj.getString("mIFAStateDate"));
                primaryRegisterRealmModel.setMHeight(jObj.getString("mHeight"));
                primaryRegisterRealmModel.setMBloodGroup(jObj.getString("mBloodGroup"));
                primaryRegisterRealmModel.setMHIV(jObj.getString("mHIV"));
                primaryRegisterRealmModel.setMVDRL(jObj.getString("mVDRL"));
                primaryRegisterRealmModel.setMHepatitis(jObj.getString("mHepatitis"));
                primaryRegisterRealmModel.setHBloodGroup(jObj.getString("hBloodGroup"));
                primaryRegisterRealmModel.setHVDRL(jObj.getString("hVDRL"));
                primaryRegisterRealmModel.setHHIV(jObj.getString("hHIV"));
                primaryRegisterRealmModel.setHHepatitis(jObj.getString("hHepatitis"));

                realm.commitTransaction();

            }else{
                Log.d("message---->",message);
//                primary_register.setVisibility(View.GONE);
//                txt_no_records_found.setVisibility(View.VISIBLE);

                /*if(message.equalsIgnoreCase("Your account is Deactivated")){
                    preferenceData.setLogin(false);
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }*/

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        primaryRecordsOffline();
    }

    @Override
    public void getAllMotherPrimaryRegisterFailiur(String response) {
        Log.d(PrimaryRegister.class.getSimpleName(), "failure" + response);

    }

    @Override
    public void postDataSuccess(String response) {

    }

    @Override
    public void postDataFailiure(String response) {

    }
}
