package com.unicef.thaimai.motherapp.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

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
    ProgressDialog pDialog;

    PrimaryRegisterPresenter primaryRegisterPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.primary_details);
        initUI();

    }

    private void initUI() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        primaryRegisterPresenter = new PrimaryRegisterPresenter(PrimaryRegisterView.this, this);
        primaryRegisterPresenter.getAllMotherPrimaryRegistration("1000000000001");

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
                txt_name.setText(jObj.getString("mName"));
                txt_mother_age.setText(jObj.getString("mAge"));
                txt_lmp_date.setText(jObj.getString("mLMP"));
                txt_edd_date.setText(jObj.getString("mEDD"));
                txt_pry_mobile_no.setText(jObj.getString("mEDD"));
                txt_alter_mobile_no.setText(jObj.getString("mEDD"));
                txt_mother_occupation.setText(jObj.getString("mMotherOccupation"));
                txt_hus_occupation.setText(jObj.getString("mHusbandOccupation"));
                txt_age_at_marriage.setText(jObj.getString("mAgeatMarriage"));
                txt_consanguineous_marraige.setText(jObj.getString("mConsanguineousMarraige"));
                txt_history_of_illness.setText(jObj.getString("mHistoryIllness"));
                txt_history_of_illness_family.setText(jObj.getString("mHistoryIllnessFamily"));
                txt_any_surgery_done.setText(jObj.getString("mAnySurgeryBefore"));
                txt_tobacco.setText(jObj.getString("mUseTobacco"));
                txt_alcohol.setText(jObj.getString("mUseAlcohol"));
                txt_on_any_medication.setText(jObj.getString("mAnyMeditation"));
                txt_allergic_to_any_drug.setText(jObj.getString("mAllergicToanyDrug"));
                txt_history_of_previous_pregnancy.setText(jObj.getString("mHistroyPreviousPreganancy"));
                txt_lscs_done.setText(jObj.getString("mLscsDone"));
                txt_any_complication.setText(jObj.getString("mAnyComplecationDuringPreganancy"));
                txt_g.setText(jObj.getString("mPresentPreganancyG"));
                txt_p.setText(jObj.getString("mPresentPreganancyP"));
                txt_a.setText(jObj.getString("mPresentPreganancyA"));
                txt_l.setText(jObj.getString("mPresentPreganancyL"));


                txt_registration_week.setText(jObj.getString("mRegistrationWeek"));
                txt_an_tt_1st.setText(jObj.getString("mANTT1"));
                txt_an_tt_2nd.setText(jObj.getString("mANTT2"));

              txt_ifa_start_date.setText(jObj.getString("mIFAStateDate"));
                txt_height.setText(jObj.getString("mHeight"));
                txt_blood_group.setText(jObj.getString("mBloodGroup"));
                txt_hiv.setText(jObj.getString("mHIV"));
                txt_vdrl.setText(jObj.getString("mVDRL"));
                txt_Hepatitis.setText(jObj.getString("mHepatitis"));
                txt_hus_blood_group.setText(jObj.getString("hBloodGroup"));
                txt_hus_hiv.setText(jObj.getString("hVDRL"));
                txt_hus_vdrl.setText(jObj.getString("hHIV"));
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
        Log.d(PrimaryRegister.class.getSimpleName(), "failiur" + response);

    }

    @Override
    public void postDataSuccess(String response) {

    }

    @Override
    public void postDataFailiure(String response) {

    }
}
