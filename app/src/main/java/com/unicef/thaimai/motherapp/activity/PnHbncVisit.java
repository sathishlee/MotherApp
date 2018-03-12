package com.unicef.thaimai.motherapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.adapter.ViewPageAdapterPN;
import com.unicef.thaimai.motherapp.adapter.ViewPagerAdapter;
import com.unicef.thaimai.motherapp.fragment.OneFragment;
import com.unicef.thaimai.motherapp.fragment.PNoneFragment;
import com.unicef.thaimai.motherapp.fragment.PNtwoFragment;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class PnHbncVisit extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_pn_hbnc_visit);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("PN / HBNC Visits");

        actionBar.setHomeButtonEnabled(true);

        actionBar.setDisplayHomeAsUpEnabled(true);

        init();

    }

    private void init() {

//        tabLayout = (TabLayout) findViewById(R.id.pn_tabs);

//Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Tab1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

//Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.hre_viewpager);
        setupViewPager(viewPager);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new PNoneFragment(), "Present Status\nVISIT 5");
//        adapter.addFragment(new PNtwoFragment(), "VISIT 4");
//        adapter.addFragment(new PNoneFragment(), "VISIT 3");
//        adapter.addFragment(new PNtwoFragment(), "VISIT 2");
//        adapter.addFragment(new PNoneFragment(), "VISIT 1");
        viewPager.setAdapter(adapter);
    }
}
