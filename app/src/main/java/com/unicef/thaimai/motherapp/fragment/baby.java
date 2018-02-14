package com.unicef.thaimai.motherapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.github.aakira.expandablelayout.ExpandableWeightLayout;

import suthishan.navigationwithbottom.R;


public class baby extends Fragment implements View.OnClickListener {
    private Button mExpandButton1,mExpandButton2,mExpandButton3,mExpandButton4;
    private RelativeLayout mlayout1,mlayout2,mlayout3,mlayout4;
    Boolean showview1=false;
    Boolean showview2=false;
    Boolean showview3=false;
    Boolean showview4=false;
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
        view = inflater.inflate(R.layout.fragment_baby, container, false);
        mExpandButton1 =view.findViewById(R.id.expandableButton1);
        mExpandButton2 =view.findViewById(R.id.expandableButton2);
        mExpandButton3 =view.findViewById(R.id.expandableButton3);
        mExpandButton4 =view.findViewById(R.id.expandableButton4);
        mlayout1 =view.findViewById(R.id.mlayout1);
        mlayout2 =view.findViewById(R.id.mlayout2);
        mlayout3 =view.findViewById(R.id.mlayout3);
        mlayout4 =view.findViewById(R.id.mlayout4);

        mExpandButton1.setOnClickListener(this);
        mExpandButton2.setOnClickListener(this);
        mExpandButton3.setOnClickListener(this);
        mExpandButton4.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.expandableButton1:

                if (!showview1){
                    showview1=true;
                mlayout1.setVisibility(View.VISIBLE);}
                else{
            mlayout1.setVisibility(View.GONE);
                    showview1=false;
                }
                break;
            case R.id.expandableButton2:
                if (!showview2){
                showview2=true;
                mlayout2.setVisibility(View.VISIBLE);}
            else{
                mlayout2.setVisibility(View.GONE);
                    showview2=false;
                }
                break;
            case R.id.expandableButton3:
                if (!showview3){
                    showview3=true;
                    mlayout3.setVisibility(View.VISIBLE);}
                else{
                    mlayout3.setVisibility(View.GONE);
                    showview3=false;
                }
                break;
            case R.id.expandableButton4:
                if (!showview4){
                    showview4=true;
                    mlayout4.setVisibility(View.VISIBLE);}
                else{
                    mlayout4.setVisibility(View.GONE);
                    showview4=false;
                }
                break;
        }
    }
}