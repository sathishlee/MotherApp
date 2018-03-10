package com.unicef.thaimai.motherapp.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.activity.PrimaryRegister;
import com.unicef.thaimai.motherapp.adapter.ViewPagerAdapter;
import com.unicef.thaimai.motherapp.adapter.ViewPagerAdapterMain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.unicef.thaimai.motherapp.fragment.OneFragment.mhealthRecordList;

public class health_records extends Fragment implements TabLayout.OnTabSelectedListener {

    Button btn_primary_report, btn_view_report;
    private TabLayout tabLayout, tabLayoutmain;
    private ViewPager viewPager, pager;

    ViewPagerAdapter viewPagerAdapter;
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
        getActivity().setTitle("Health Records");


        viewPager = view. findViewById(R.id.hre_viewpager);
        setupViewPager(viewPager);
        tabLayout = view.findViewById(R.id.hre_tabs);
//        tabLayoutmain = view.findViewById(R.id.tabLayoutmain);
//        tabLayout.setupWithViewPager(viewPager);
        btn_primary_report = (Button) view.findViewById(R.id.btn_primary_report);
        btn_view_report = (Button) view.findViewById(R.id.btn_view_report);
    }


    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(health_records.this,new OneFragment(), "Present Status\nVISIT 5", mhealthRecordList);

//        adapter.addFragment(new OneFragment(), "VISIT 4");
//        adapter.addFragment(new OneFragment(), "VISIT 3");
//        adapter.addFragment(new OneFragment(), "VISIT 2");
//        adapter.addFragment(new OneFragment(), "VISIT 1");
        viewPager.setAdapter(adapter);
    }

//    private void PagerView(ViewPager PagerView){
//
//        ViewPagerAdapterMain adapter1 = new ViewPagerAdapterMain(getActivity().getSupportFragmentManager());
//
//        adapter1.addFragmentMain(new PicmeVisit(), "Picme Visits");
//        adapter1.addFragmentMain(new OtherVisit(), "Other Visits");
//
//        viewPager.setAdapter(adapter1);
//
//    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}