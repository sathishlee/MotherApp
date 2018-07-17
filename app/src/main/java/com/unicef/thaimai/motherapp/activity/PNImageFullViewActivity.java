package com.unicef.thaimai.motherapp.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.adapter.PNSlidingImage_Adapter;

import java.util.ArrayList;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class PNImageFullViewActivity extends AppCompatActivity {

    private static ViewPager mPager;
    private ArrayList<String> ImagesArray = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full_view);
        showActionBar();
        init();
    }

    private void showActionBar() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("THAIMAI");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    private void init() {
//        ImagesArray= (ArrayList<ANVisitRecordsSingleResponseModel>) getIntent().getSerializableExtra("mylist");
        String [] imagelist= getIntent().getExtras().getStringArray("mylist");
        for(int i = 0; i< imagelist.length; i++)
        {
            ImagesArray.add(imagelist[i]);
        }
        Log.e(PNImageFullViewActivity.class.getSimpleName(),imagelist.length+"");

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new PNSlidingImage_Adapter(PNImageFullViewActivity.this,ImagesArray));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
