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
import com.unicef.thaimai.motherapp.activity.profile;
import com.unicef.thaimai.motherapp.adapter.ViewPagerAdapter;


public class health_records extends Fragment  {

    Button btn_primary;
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
        getActivity().setTitle("Health Records");
        viewPager = view. findViewById(R.id.hre_viewpager);
        setupViewPager(viewPager);

        tabLayout = view .findViewById(R.id.hre_tabs);
        tabLayout.setupWithViewPager(viewPager);


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

}