package com.unicef.thaimai.motherapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.github.aakira.expandablelayout.ExpandableWeightLayout;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.activity.DeliveryDetailsActivity;


public class baby extends Fragment  {


   /* private Button mExpandButton1,mExpandButton2,mExpandButton3,mExpandButton4;
    private RelativeLayout mlayout2,mlayout3,mlayout4;
    ScrollView delivery_details, infant_tracking, immunization_details, postnatal_mother;
    Boolean showview1=false;
    Boolean showview2=false;
    Boolean showview3=false;
    Boolean showview4=false;*/
//    private ExpandableWeightLayout mExpandLayout1,mExpandLayout2,mExpandLayout3,mExpandLayout4;

    public static baby newInstance()
    {
        baby fragment = new baby();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = null;
        view = inflater.inflate(R.layout.fragment_baby_delivery_details, container, false);

        /*mExpandButton1 =view.findViewById(R.id.expandableButton1);
        mExpandButton2 =view.findViewById(R.id.expandableButton2);
        mExpandButton3 =view.findViewById(R.id.expandableButton3);
        mExpandButton4 =view.findViewById(R.id.expandableButton4);

        delivery_details =view.findViewById(R.id.delivery_details);
        infant_tracking =view.findViewById(R.id.infant_tracking);
        immunization_details =view.findViewById(R.id.immunization_details);
        postnatal_mother = view.findViewById(R.id.postnatal_mother);*/


        /*mExpandButton1.setOnClickListener(this);
        mExpandButton2.setOnClickListener(this);
        mExpandButton3.setOnClickListener(this);
        mExpandButton4.setOnClickListener(this);*/
        return view;
    }





    /*@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.expandableButton1:

                if (!showview1){
                    showview1=true;
                    delivery_details.setVisibility(View.VISIBLE);}
                else{
                    delivery_details.setVisibility(View.GONE);
                    showview1=false;
                }
                break;
            case R.id.expandableButton2:
                if (!showview2){
                showview2=true;
                    infant_tracking.setVisibility(View.VISIBLE);}
            else{
                    infant_tracking.setVisibility(View.GONE);
                    showview2=false;
                }
                break;
            case R.id.expandableButton3:
                if (!showview3){
                    showview3=true;
                    immunization_details.setVisibility(View.VISIBLE);}
                else{
                    immunization_details.setVisibility(View.GONE);
                    showview3=false;
                }
                break;

            case R.id.expandableButton4:
                if (!showview4){
                    showview4=true;
                    postnatal_mother.setVisibility(View.VISIBLE);}
                else{
                    postnatal_mother.setVisibility(View.GONE);
                    showview4=false;
                }
                break;

        }
    }*/
}