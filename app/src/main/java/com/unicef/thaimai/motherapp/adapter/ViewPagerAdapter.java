package com.unicef.thaimai.motherapp.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.unicef.thaimai.motherapp.fragment.OneFragment;
import com.unicef.thaimai.motherapp.fragment.health_records;
import com.unicef.thaimai.motherapp.model.responsemodel.HealthRecordResponseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suthishan on 20/1/2018.
 */

//public class ViewPagerAdapter   extends PagerAdapter {
public class ViewPagerAdapter   extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
   public  ArrayList<HealthRecordResponseModel.Visit_Records> mhealthRecordList;
    health_records context;

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
       return mFragmentList.get(position);
//        Fragment fragment = null;
//        HealthRecordResponseModel.Visit_Records items = mhealthRecordList.get(position);
//        OneFragment dinamisFragment = new OneFragment();
//        dinamisFragment.setDetail(items);
//        fragment = dinamisFragment;
//
//        if (mFragmentList.get(position) == null) {
//            mFragmentList.set(position, fragment);
//        }
//        return mFragmentList.get(position);
//         return OneFragment.newInstance(mhealthRecordList.get(position));
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(health_records context, Fragment fragment, String title, ArrayList<HealthRecordResponseModel.Visit_Records> mhealthRecordList) {
       this.context = context;
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        this.mhealthRecordList =mhealthRecordList;
        }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    public ArrayList<HealthRecordResponseModel.Visit_Records> getMhealthRecordList() {
        return mhealthRecordList;
    }
}