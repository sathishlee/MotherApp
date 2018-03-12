package com.unicef.thaimai.motherapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unicef.thaimai.motherapp.R;


public class baby extends Fragment  {


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
        view = inflater.inflate(R.layout.layout_baby_delivery_details, container, false);

        getActivity().setTitle("Delivery Details");

        return view;
    }


}