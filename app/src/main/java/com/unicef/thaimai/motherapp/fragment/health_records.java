package com.unicef.thaimai.motherapp.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import suthishan.navigationwithbottom.R;
import suthishan.navigationwithbottom.adapter.ViewPagerAdapter;


public class health_records extends Fragment  {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static health_records newInstance()
    {
        health_records fragment = new health_records();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = null;
        view=inflater.inflate(R.layout.fragment_health_records, container, false);
        viewPager = view. findViewById(R.id.hre_viewpager);
        setupViewPager(viewPager);

        tabLayout = view .findViewById(R.id.hre_tabs);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "Present Status\nVISIT 5");
        adapter.addFragment(new TwoFragment(), "VISIT 4");
        adapter.addFragment(new ThreeFragment(), "VISIT 3");
        adapter.addFragment(new OneFragment(), "VISIT 2");
        adapter.addFragment(new TwoFragment(), "VISIT 1");
        viewPager.setAdapter(adapter);
    }

}