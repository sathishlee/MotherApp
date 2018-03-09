package com.unicef.thaimai.motherapp.fragment;

import android.content.Intent;
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


public class health_records extends Fragment implements TabLayout.OnTabSelectedListener {

    Button btn_primary;
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

//        pager = view.findViewById(R.id.pager);
//        PagerView(pager);

        viewPager = view. findViewById(R.id.hre_viewpager);
        setupViewPager(viewPager);

        tabLayout = view .findViewById(R.id.hre_tabs);

//        tabLayoutmain = view.findViewById(R.id.tabLayoutmain);
//        tabLayout.setupWithViewPager(viewPager);


        btn_primary = (Button) view .findViewById(R.id.btn_primary);

        btn_primary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), PrimaryRegister.class);
                getActivity().finish();
                startActivity(intent);

            }
        });


        return view;
    }



    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "Present Status\nVISIT 5");
        adapter.addFragment(new OneFragment(), "VISIT 4");
        adapter.addFragment(new OneFragment(), "VISIT 3");
        adapter.addFragment(new OneFragment(), "VISIT 2");
        adapter.addFragment(new OneFragment(), "VISIT 1");
        viewPager.setAdapter(adapter);
    }

    private void PagerView(ViewPager PagerView){

        ViewPagerAdapterMain adapter1 = new ViewPagerAdapterMain(getActivity().getSupportFragmentManager());

        adapter1.addFragmentMain(new PicmeVisit(), "Picme Visits");
        adapter1.addFragmentMain(new PicmeVisit(), "Other Visits");

        PagerView.setAdapter(adapter1);

    }

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