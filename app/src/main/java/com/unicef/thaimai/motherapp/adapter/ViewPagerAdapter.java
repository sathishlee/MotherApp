package com.unicef.thaimai.motherapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.unicef.thaimai.motherapp.model.responsemodel.HealthRecordResponseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class ViewPagerAdapter   extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
   public  ArrayList<HealthRecordResponseModel> mhealthRecordList;
    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title, ArrayList<HealthRecordResponseModel> mhealthRecordList) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        this.mhealthRecordList =mhealthRecordList;
        Log.e(ViewPagerAdapter.class.getSimpleName(),mhealthRecordList.toString());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}