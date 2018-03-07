package com.unicef.thaimai.motherapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.unicef.thaimai.motherapp.R;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class PrimaryRegisterView extends AppCompatActivity {

    TextView txt_name, txt_mother_age, txt_lmp_date, txt_edd_date, txt_pry_mobile_no,
            txt_alter_mobile_no,txt_mother_occupation, txt_hus_occupation, txt_age_at_marriage,txt_consanguineous_marraige,
            txt_history_of_illness, txt_history_of_illness_family, txt_any_surgery_done, txt_tobacco, txt_alcohol,
            txt_on_any_medication, txt_allergic_to_any_drug, txt_history_of_previous_pregnancy, txt_lscs_done,
            txt_any_complication, txt_g, txt_p, txt_a, txt_l, txt_registration_week, txt_an_tt_1st, txt_an_tt_2nd,
            txt_ifa_start_date, txt_height, txt_blood_group, txt_hiv, txt_vdrl, txt_Hepatitis, txt_hus_blood_group,
            txt_hus_hiv, txt_hus_vdrl, txt_hus_Hepatitis;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.primary_details);
        initUI();

    }

    private void initUI() {

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



}
