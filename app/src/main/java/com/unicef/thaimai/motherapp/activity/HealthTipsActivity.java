package com.unicef.thaimai.motherapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.HealthTipsPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.adapter.HealthTipsAdapter;
import com.unicef.thaimai.motherapp.model.VideoModel;
import com.unicef.thaimai.motherapp.view.HealthTipsViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class HealthTipsActivity extends AppCompatActivity implements HealthTipsViews{

    ProgressDialog pDialog;
    HealthTipsPresenter healthTipsPresenter;
    PreferenceData preferenceData;
    private List<VideoModel.VideoList> lists;
    VideoModel.VideoList videoList;
    private RecyclerView recyclerView;
    private HealthTipsAdapter healthTipsAdapter;
    private DisplayImageOptions options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_video);
        initUI();
        onClickListner();
        showActionBar();
    }

    public  void initUI(){
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        preferenceData =new PreferenceData(this);
        healthTipsPresenter = new HealthTipsPresenter(HealthTipsActivity.this,this);
        healthTipsPresenter.getHealthTipsVideo(preferenceData.getPicmeId());
        lists = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.video_recycler_view);
        healthTipsAdapter = new HealthTipsAdapter(lists, HealthTipsActivity.this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HealthTipsActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(healthTipsAdapter);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.real1)
                .showImageForEmptyUri(R.drawable.real1)
                .showImageOnFail(R.drawable.real1)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();

    }

    public void  onClickListner(){

    }
    public void showActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Health tips");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(HealthTipsActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showProgress() {
        pDialog.show();
    }

    @Override
    public void hideProgress() {
        pDialog.hide();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (pDialog!=null && pDialog.isShowing() ){
            pDialog.cancel();
        }
    }


    @Override
    public void getHealthTipsVideoSuccess(String response) {
        Log.e(HealthTipsActivity.class.getSimpleName(), "Success Response"+ response);

        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("videoList");
            for (int i = 0; i < jsonArray.length(); i++){
                videoList = new VideoModel.VideoList();
                JSONObject object = jsonArray.getJSONObject(i);
                videoList.setId(object.getString("id"));
                videoList.setTitle(object.getString("title"));
                videoList.setVideo(object.getString("video"));

                lists.add(videoList);
                healthTipsAdapter.notifyDataSetChanged();
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }


    }

    @Override
    public void getHealthTipsVideoFailure(String response) {
    Log.e(HealthTipsActivity.class.getSimpleName(), "Error Message"+ response);
    }
}
