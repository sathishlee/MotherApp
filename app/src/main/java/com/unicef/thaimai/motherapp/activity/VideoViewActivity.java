package com.unicef.thaimai.motherapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.utility.CheckNetwork;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class VideoViewActivity extends AppCompatActivity {
    VideoView video;
    TextView visitNo;
    //    String video_url = "http://file2.video9.in/english/movie/2014/x-men-_days_of_future_past/X-Men-%20Days%20of%20Future%20Past%20Trailer%20-%20[Webmusic.IN].3gp";
    String video_url;
    ProgressDialog pd;

    CheckNetwork checkNetwork;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        showActionBar();
        Bundle bundle = getIntent().getExtras();
        video = (VideoView) findViewById(R.id.video_view);
        visitNo = (TextView) findViewById(R.id.visitNo);
        checkNetwork=new CheckNetwork(this);
        if (bundle.getString("title") != null)
            visitNo.setText(bundle.getString("title"));
        if (bundle.getString("videolist") != null)

            video_url = Apiconstants.HEALTH_TIPS_VIDEO_URL + bundle.getString("videolist");
        pd = new ProgressDialog(VideoViewActivity.this);
        pd.setMessage("Buffering video please wait...");
        pd.show();
        if (!checkNetwork.isNetworkAvailable()) {
            uri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "videolist");
        } else {
            uri = Uri.parse(video_url);
        }
        video.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(video);

        video.setMediaController(mediaController);
//        mediaController.show(5000);
//        if(mediaController.isShowing()){
//            mediaController.hide();
//        }

        video.start();

        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //close the progress dialog when buffering is done
                pd.dismiss();
            }
        });
        video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Toast.makeText(getApplicationContext(), "No Video Found.", Toast.LENGTH_LONG).show();
                return false;
            }
        });


        View.OnClickListener onClickListenerNext = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                video.setVideoURI(Uri.parse(Apiconstants.HEALTH_TIPS_VIDEO_URL + "month3.mp4"));
                video.start();
            }
        };
        View.OnClickListener onClickListenerPrevious = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                video.setVideoURI(Uri.parse(Apiconstants.HEALTH_TIPS_VIDEO_URL + "month5.mp4"));
                video.start();
            }
        };
        mediaController.setPrevNextListeners(onClickListenerNext, onClickListenerPrevious);
    }

    private void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Heath Tips Video");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
