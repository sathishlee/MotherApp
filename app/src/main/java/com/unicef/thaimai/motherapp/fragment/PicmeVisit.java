package com.unicef.thaimai.motherapp.fragment;

/**
 * Created by Suthishan on 20/1/2018.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unicef.thaimai.motherapp.R;


public class PicmeVisit extends Fragment {

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.tab_picme, container, false);
    }

    public PicmeVisit() {
        // Required empty public constructor
    }


}