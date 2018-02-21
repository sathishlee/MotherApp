package com.unicef.thaimai.motherapp.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.activity.Login;
import com.unicef.thaimai.motherapp.constant.AppConstants;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;


public class home extends Fragment {

    Button btn_logout;
    TextView txt_username, picme_id, dob;
    String id, name, picmeId, regDob;


    // Context
    Context _context;
    CardView profile, next_visit;


    private static final String TAG_ID = "id";
    private static final String TAG_USERNAME = "name";
    private static final String PICME_ID = "picmeId";
    private static final String AGE = "Age";


    PreferenceData preferenceData;
    SharedPreferences.Editor editor;


    public static home newInstance()
    {
        home fragment = new home();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
         View view = inflater.inflate(R.layout.fragment_home, container, false);
        preferenceData = new PreferenceData(getActivity());
        editor = getActivity().getSharedPreferences(AppConstants.PREF_NAME, MODE_PRIVATE).edit();


        txt_username = (TextView) view.findViewById(R.id.txt_username);
        picme_id = (TextView) view.findViewById(R.id.picme_id);
        dob = (TextView) view.findViewById(R.id.regDob);
        profile = (CardView) view.findViewById(R.id.android_card_view_example);

        getSharedValues();

//        id = getActivity().getIntent().getStringExtra(TAG_ID);
//        name = getActivity().getIntent().getStringExtra(TAG_USERNAME);
//        picmeId = getActivity().getIntent().getStringExtra(PICME_ID);
//        regDob = getActivity().getIntent().getStringExtra(AGE);
        return view;

    }

    private void getSharedValues() {
            String str_json_user_info = preferenceData.getUserInfo();
        Log.e("str_json_user_info---->",str_json_user_info);
        String str_json_UserMedical = preferenceData.getUserMedical();
        Log.e("UserMedical---->",str_json_UserMedical);
        String str_json_EmergencyContacts= preferenceData.getEmergencyContacts();
        Log.e("EmergencyContacts---->",str_json_EmergencyContacts);

    }


}
