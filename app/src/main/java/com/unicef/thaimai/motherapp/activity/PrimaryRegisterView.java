package com.unicef.thaimai.motherapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.PrimaryRegisterPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.view.PrimaryRegisterViews;

import org.json.JSONException;
import org.json.JSONObject;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Intent intent = new Intent(PrimaryRegisterView.this, MainActivity.class);
        finish();
        startActivity(intent);
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
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        preferenceData = new PreferenceData(this);
        primaryRegisterPresenter = new PrimaryRegisterPresenter(PrimaryRegisterView.this, this);
        primaryRegisterPresenter.getAllMotherPrimaryRegistration(preferenceData.getPicmeId());
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

    private void setValuetoUI(String response) {

        JSONObject jObj = null;
        try {
            jObj = new JSONObject(response);
            int status = jObj.getInt("status");
            String message = jObj.getString("message");
            if (status==1){
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
            }else{
                Log.d("message---->",message);

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
