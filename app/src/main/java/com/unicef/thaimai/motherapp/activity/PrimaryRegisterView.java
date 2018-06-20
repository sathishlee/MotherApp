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
        finish();
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
            primaryRecordsOfline();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Record Not Found");
            builder.create();
        }

    }

    private void primaryRecordsOfline() {

        RealmResults<PrimaryRegisterRealmModel> primaryRegisterRealmModels;
        realm.beginTransaction();
        primaryRegisterRealmModels = realm.where(PrimaryRegisterRealmModel.class).findAll();
        Log.e(String.valueOf(PrimaryRegisterView.class),primaryRegisterRealmModels.size()+"");
        Log.e(PrimaryRegisterView.class.getSimpleName(),"primaryRegisterRealmModels  -->"+primaryRegisterRealmModels);
        for(int i=0;i<primaryRegisterRealmModels.size();i++) {
            PrimaryRegisterRealmModel model =primaryRegisterRealmModels.get(i);
            txt_name.setText(model.getMName());
            txt_mother_age.setText(model.getMAge());
            txt_lmp_date.setText(model.getMLMP());
            txt_edd_date.setText(model.getMEDD());
            txt_pry_mobile_no.setText(model.getMMotherMobile());
            txt_alter_mobile_no.setText(model.getMHusbandMobile());
            txt_mother_occupation.setText(model.getMMotherOccupation());
            txt_hus_occupation.setText(model.getMHusbandOccupation());
            txt_age_at_marriage.setText(model.getMAgeatMarriage());
            txt_consanguineous_marraige.setText(model.getMConsanguineousMarraige());
            txt_history_of_illness.setText(model.getMHistoryIllness());
            txt_history_of_illness_family.setText(model.getMHistoryIllnessFamily());
            txt_any_surgery_done.setText(model.getMAnySurgeryBefore());
            txt_tobacco.setText(model.getMUseTobacco());
            txt_alcohol.setText(model.getMUseAlcohol());
            txt_on_any_medication.setText(model.getMAnyMeditation());
            txt_allergic_to_any_drug.setText(model.getMAllergicToanyDrug());
            txt_history_of_previous_pregnancy.setText(model.getMHistroyPreviousPreganancy());
            txt_lscs_done.setText(model.getMLscsDone());
            txt_any_complication.setText(model.getMAnyComplecationDuringPreganancy());
            txt_g.setText(model.getMPresentPreganancyG());
            txt_p.setText(model.getMPresentPreganancyP());
            txt_a.setText(model.getMPresentPreganancyA());
            txt_l.setText(model.getMPresentPreganancyL());
            txt_registration_week.setText(model.getMRegistrationWeek());
            txt_an_tt_1st.setText(model.getMANTT1());
            txt_an_tt_2nd.setText(model.getMANTT2());
            txt_ifa_start_date.setText(model.getMIFAStateDate());
            txt_height.setText(model.getMHeight());
            txt_blood_group.setText(model.getMBloodGroup());
            txt_hiv.setText(model.getMHIV());
            txt_vdrl.setText(model.getMVDRL());
            txt_Hepatitis.setText(model.getMHepatitis());
            txt_hus_blood_group.setText(model.getHBloodGroup());
            txt_hus_hiv.setText(model.getHHIV());
            txt_hus_vdrl.setText(model.getHVDRL());
            txt_hus_Hepatitis.setText(model.getHHepatitis());
        }
        realm.commitTransaction();
        pDialog.dismiss();
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

//        JSONObject jObj = null;
        try {
            JSONObject jObj = new JSONObject(response);
            int status = jObj.getInt("status");
            String message = jObj.getString("message");
            if (status==1){
                primary_register.setVisibility(View.VISIBLE);
                txt_no_records_found.setVisibility(View.GONE);
                Log.d("message---->",message);
                if (jObj.getString("mName")!="")
                    txt_name.setText(jObj.getString("mName"));
                if (jObj.getString("mAge")!="")
                    txt_mother_age.setText(jObj.getString("mAge"));
                if (jObj.getString("mLMP")!="")
                    txt_lmp_date.setText(jObj.getString("mLMP"));
                if (jObj.getString("mEDD")!="")
                    txt_edd_date.setText(jObj.getString("mEDD"));
                if (jObj.getString("mMotherMobile")!="")
                    txt_pry_mobile_no.setText(jObj.getString("mMotherMobile"));
                if (jObj.getString("mHusbandMobile")!="")
                    txt_alter_mobile_no.setText(jObj.getString("mHusbandMobile"));
                if (jObj.getString("mMotherOccupation")!="")
                    txt_mother_occupation.setText(jObj.getString("mMotherOccupation"));
                if (jObj.getString("mHusbandOccupation")!="")
                    txt_hus_occupation.setText(jObj.getString("mHusbandOccupation"));
                if (jObj.getString("mAgeatMarriage")!="")
                    txt_age_at_marriage.setText(jObj.getString("mAgeatMarriage"));
                if (jObj.getString("mConsanguineousMarraige")!="")
                    txt_consanguineous_marraige.setText(jObj.getString("mConsanguineousMarraige"));
                if (jObj.getString("mHistoryIllness")!="")
                    txt_history_of_illness.setText(jObj.getString("mHistoryIllness"));
                if (jObj.getString("mHistoryIllnessFamily")!="")
                    txt_history_of_illness_family.setText(jObj.getString("mHistoryIllnessFamily"));
                if (jObj.getString("mAnySurgeryBefore")!="")
                    txt_any_surgery_done.setText(jObj.getString("mAnySurgeryBefore"));
                if (jObj.getString("mUseTobacco")!="")
                    txt_tobacco.setText(jObj.getString("mUseTobacco"));
                if (jObj.getString("mUseAlcohol")!="")
                    txt_alcohol.setText(jObj.getString("mUseAlcohol"));
                if (jObj.getString("mAnyMeditation")!="")
                    txt_on_any_medication.setText(jObj.getString("mAnyMeditation"));
                if (jObj.getString("mAllergicToanyDrug")!="")
                    txt_allergic_to_any_drug.setText(jObj.getString("mAllergicToanyDrug"));
                if (jObj.getString("mHistroyPreviousPreganancy")!="")
                    txt_history_of_previous_pregnancy.setText(jObj.getString("mHistroyPreviousPreganancy"));
                if (jObj.getString("mLscsDone")!="")
                    txt_lscs_done.setText(jObj.getString("mLscsDone"));
                if (jObj.getString("mAnyComplecationDuringPreganancy")!="")
                    txt_any_complication.setText(jObj.getString("mAnyComplecationDuringPreganancy"));
                if (jObj.getString("mPresentPreganancyG")!="")
                    txt_g.setText(jObj.getString("mPresentPreganancyG"));
                if (jObj.getString("mPresentPreganancyP")!="")
                    txt_p.setText(jObj.getString("mPresentPreganancyP"));
                if (jObj.getString("mPresentPreganancyA")!="")
                    txt_a.setText(jObj.getString("mPresentPreganancyA"));
                if (jObj.getString("mPresentPreganancyL")!="")
                    txt_l.setText(jObj.getString("mPresentPreganancyL"));
                if (jObj.getString("mRegistrationWeek")!="")
                    txt_registration_week.setText(jObj.getString("mRegistrationWeek"));
                if (jObj.getString("mANTT1")!="")
                    txt_an_tt_1st.setText(jObj.getString("mANTT1"));
                if (jObj.getString("mANTT2")!="")
                    txt_an_tt_2nd.setText(jObj.getString("mANTT2"));
                if (jObj.getString("mIFAStateDate")!="")
                    txt_ifa_start_date.setText(jObj.getString("mIFAStateDate"));
                if (jObj.getString("mHeight")!="")
                    txt_height.setText(jObj.getString("mHeight"));
                if (jObj.getString("mBloodGroup")!="")
                    txt_blood_group.setText(jObj.getString("mBloodGroup"));
                if (jObj.getString("mHIV")!="")
                    txt_hiv.setText(jObj.getString("mHIV"));
                if (jObj.getString("mVDRL")!="")
                    txt_vdrl.setText(jObj.getString("mVDRL"));
                if (jObj.getString("mHepatitis")!="")
                    txt_Hepatitis.setText(jObj.getString("mHepatitis"));
                if (jObj.getString("hBloodGroup")!="")
                    txt_hus_blood_group.setText(jObj.getString("hBloodGroup"));
                if (jObj.getString("hVDRL")!="")
                    txt_hus_hiv.setText(jObj.getString("hVDRL"));
                if (jObj.getString("hHIV")!="")
                    txt_hus_vdrl.setText(jObj.getString("hHIV"));
                if (jObj.getString("hHepatitis")!="")
                    txt_hus_Hepatitis.setText(jObj.getString("hHepatitis"));

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
                primaryRegisterRealmModel.setMHusbandMobile(jObj.getString("mHusbandName"));
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
                primary_register.setVisibility(View.GONE);
                txt_no_records_found.setVisibility(View.VISIBLE);

                if(message.equalsIgnoreCase("Your account is Deactivated")){
                    preferenceData.setLogin(false);
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
