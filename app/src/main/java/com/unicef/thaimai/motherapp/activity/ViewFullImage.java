package com.unicef.thaimai.motherapp.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import com.unicef.thaimai.motherapp.R;


/**
 * Created by Suthishan on 20/1/2018.
 */

public class ViewFullImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        Uri uri = Uri.parse("http://demo.satvatinfosol.com/thaimai/Pregnancy_Tamil_Week_21.mp4");

        VideoView simpleVideoView = (VideoView) findViewById(R.id.video_View);

        simpleVideoView.setVideoURI(uri);
        simpleVideoView.start();

        MediaController mediaController = new MediaController(this);

        simpleVideoView.setMediaController(mediaController);
        simpleVideoView.pause();

    }

}


