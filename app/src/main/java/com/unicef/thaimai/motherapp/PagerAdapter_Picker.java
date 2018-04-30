package com.unicef.thaimai.motherapp;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class PagerAdapter_Picker extends FragmentPagerAdapter {

    private String[] tab_titles;

    public PagerAdapter_Picker(Context context, FragmentManager fm) {
        super(fm);
        tab_titles = context.getResources().getStringArray(R.array.tab_titles);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return tab_titles[position];
    }

    @Override
    public int getCount() {
        return tab_titles.length;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                CwacCameraFragment profileInfoFragment = new CwacCameraFragment();
                CwacCameraFragment.setConfig(ImageSelectedActivity.getConfig());
                return profileInfoFragment;
            case 1:
                return new GalleryFragment();
            default:
                return null;
        }

    }
}
