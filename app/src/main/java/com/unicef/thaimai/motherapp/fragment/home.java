package com.unicef.thaimai.motherapp.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.activity.Login;


public class home extends Fragment {

    Button btn_logout;
    TextView txt_username, picme_id, dob;
    String id, name, picmeId, regDob;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    // Context
    Context _context;
    CardView profile, next_visit;


    private static final String TAG_ID = "id";
    private static final String TAG_USERNAME = "name";
    private static final String PICME_ID = "picmeId";
    private static final String AGE = "Age";



    public static home newInstance()
    {
        home fragment = new home();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        sharedpreferences = this.getActivity().getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
         View view = inflater.inflate(R.layout.fragment_home, container, false);


        txt_username = (TextView) view.findViewById(R.id.txt_username);
        picme_id = (TextView) view.findViewById(R.id.picme_id);
        dob = (TextView) view.findViewById(R.id.regDob);


        profile = (CardView) view.findViewById(R.id.android_card_view_example);






        id = getActivity().getIntent().getStringExtra(TAG_ID);
        name = getActivity().getIntent().getStringExtra(TAG_USERNAME);
        picmeId = getActivity().getIntent().getStringExtra(PICME_ID);
        regDob = getActivity().getIntent().getStringExtra(AGE);


//        txt_username.setText("USERNAME : " + "Mrs.Tamil Selvi");
//        picme_id.setText("PICME ID : " + 101);
//        dob.setText("Dob :" + );

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(Login.session_status, false);
        editor.putString(TAG_ID, id);
        editor.putString(TAG_USERNAME, name);
        editor.putString(PICME_ID, picmeId);
        editor.putString(AGE, null);
        editor.clear();
        editor.commit();



        return view;

    }


}
