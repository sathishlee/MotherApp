package com.unicef.thaimai.motherapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.model.responsemodel.HealthRecordResponseModel;

import java.util.ArrayList;

/**
 * Created by sathish on 3/12/2018.
 */

public class HealthRecordsAdapter extends PagerAdapter {
    private ArrayList<HealthRecordResponseModel.Visit_Records> mhealthRecordList;
    private LayoutInflater inflater;
    private Context context;
    FragmentActivity activity;
    public HealthRecordsAdapter(ArrayList<HealthRecordResponseModel.Visit_Records> mhealthRecordList, LayoutInflater inflater, Context context) {
        this.inflater = inflater;
        this.context = context;
        this.mhealthRecordList = mhealthRecordList;

    }

    public HealthRecordsAdapter(FragmentActivity activity,  ArrayList<HealthRecordResponseModel.Visit_Records> mhealthRecordList) {
        this.mhealthRecordList = mhealthRecordList;
        this.activity =activity;
        inflater    = LayoutInflater.from(activity);

    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {

        View myImageLayout = inflater.inflate(R.layout.item_visit_screen, view, false);

        view.addView(myImageLayout, position);
//        Log.e(HealthRecordsAdapter.class.getSimpleName(),mhealthRecordList.get(position).getVisitId());

        return myImageLayout;
    }

    @Override
    public int getCount() {
        return mhealthRecordList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Log.d(HealthRecordsAdapter.class.getSimpleName(),mhealthRecordList.get(position).getVisitId());
        return mhealthRecordList.get(position).getVisitId();
    }
}
