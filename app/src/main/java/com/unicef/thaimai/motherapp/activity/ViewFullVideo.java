package com.unicef.thaimai.motherapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

import com.unicef.thaimai.motherapp.R;


/**
 * Created by Suthishan on 20/1/2018.
 */

public class ViewFullVideo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Video");

        actionBar.setHomeButtonEnabled(true);

        actionBar.setDisplayHomeAsUpEnabled(true);

        Uri uri = Uri.parse("http://demo.satvatinfosol.com/thaimai/Pregnancy_Tamil_Week_21.mp4");

        VideoView simpleVideoView = (VideoView) findViewById(R.id.video_view);

        simpleVideoView.setVideoURI(uri);
        simpleVideoView.start();

        MediaController mediaController = new MediaController(this);

        simpleVideoView.setMediaController(mediaController);
        simpleVideoView.pause();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(ViewFullVideo.this, HealthTipsActivity.class);
        finish();
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

}


