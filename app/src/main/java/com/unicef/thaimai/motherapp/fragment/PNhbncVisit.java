package com.unicef.thaimai.motherapp.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.adapter.ViewPageAdapterPN;
import com.unicef.thaimai.motherapp.adapter.ViewPagerAdapter;
import com.unicef.thaimai.motherapp.model.responsemodel.HealthRecordResponseModel;

import java.util.ArrayList;


public class PNhbncVisit extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPageAdapterPN adapter;

    ArrayList<HealthRecordResponseModel> mhealthRecordList;

    public static PNhbncVisit newInstance()
    {
        PNhbncVisit fragment = new PNhbncVisit();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
       View view =  inflater.inflate(R.layout.layout_pn_hbnc_visit, container, false);
        initUI(view);

        return  view;
    }

    private void initUI(View view) {
        getActivity().setTitle("PN-HBNC Details");

        viewPager = view.findViewById(R.id.pn_viewpager);
        setupViewPager(viewPager);
        tabLayout = view.findViewById(R.id.pn_tabs);
//        tabLayoutmain = view.findViewById(R.id.tabLayoutmain);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPageAdapterPN(getActivity().getSupportFragmentManager());
        adapter.addFragment(new PNoneFragment(), "Present Status\nVISIT 5");
        adapter.addFragment(new PNtwoFragment(), "VISIT 4");
        adapter.addFragment(new PNoneFragment(), "VISIT 3");
        adapter.addFragment(new PNtwoFragment(), "VISIT 2");
        adapter.addFragment(new PNoneFragment(), "VISIT 1");
        viewPager.setAdapter(adapter);
    }
}