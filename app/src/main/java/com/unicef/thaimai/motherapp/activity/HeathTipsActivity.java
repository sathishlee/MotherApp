package com.unicef.thaimai.motherapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.adapter.VideoAdapter;
import com.unicef.thaimai.motherapp.model.VideoModel;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class HeathTipsActivity extends AppCompatActivity{

    ListView video_list;

    private List<VideoModel> mVideosList = new ArrayList<>();
    VideoAdapter mVideoAdapter;

    CardView card_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heath_tips);
        initUI();
        onClickListner();
        showActionBar();

        VideoModel video1 = new VideoModel("http://demo.satvatinfosol.com/thaimai/assets/mother_videos/month2.mp4");
        VideoModel video2 = new VideoModel("http://demo.satvatinfosol.com/thaimai/assets/mother_videos/month2.mp4");
        VideoModel video3 = new VideoModel("http://demo.satvatinfosol.com/thaimai/Pregnancy_Tamil_Week_21.mp4");


        mVideosList.add(video1);
        mVideosList.add(video2);
        mVideosList.add(video3);



        mVideoAdapter = new VideoAdapter(this, mVideosList);
        video_list.setAdapter(mVideoAdapter);

    }

    public  void initUI(){



        video_list = (ListView) findViewById(R.id.video_list);




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

        Intent intent = new Intent(HeathTipsActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }


}
