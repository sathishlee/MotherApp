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
import com.unicef.thaimai.motherapp.adapter.ViewPagerAdapter;
import com.unicef.thaimai.motherapp.fragment.OneFragment;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class PnHbncVisit extends AppCompatActivity{

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


        ViewPager  viewPager = (ViewPager) findViewById(R.id.hre_viewpager);



        tabLayout = findViewById(R.id.hre_tabs);
        tabLayout.setupWithViewPager(viewPager);


    }


}
