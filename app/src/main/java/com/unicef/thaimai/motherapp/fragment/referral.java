package com.unicef.thaimai.motherapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.activity.AddReferral;
import com.unicef.thaimai.motherapp.activity.ReferralActivity;
import com.unicef.thaimai.motherapp.activity.ReferralList;


public class referral extends Fragment {

    CardView referral_display;

    public static referral newInstance()
    {
        referral fragment = new referral();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = null;
        view =  inflater.inflate(R.layout.layout_referral, container, false);

        layoutView(view);

        return view;
    }

    private void layoutView(View view){

        getActivity().setTitle("Referral Details");

        CardView referral_display = (CardView)view.findViewById(R.id.referral_display);

        referral_display.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent e = new Intent(getActivity(), ReferralActivity.class);
                startActivity(e);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(getActivity(), AddReferral.class);
                startActivity(i);
            }
        });

    }
}