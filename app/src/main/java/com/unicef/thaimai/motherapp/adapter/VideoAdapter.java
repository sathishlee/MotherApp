package com.unicef.thaimai.motherapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.VideoView;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.activity.ViewFullVideo;
import com.unicef.thaimai.motherapp.model.VideoModel;

import java.util.List;




public class VideoAdapter extends ArrayAdapter<VideoModel> {

    private Context mContext;
    private List<VideoModel> mVideos;
    CardView card_view;

    public VideoAdapter(@NonNull Context context, @NonNull List<VideoModel> objects) {
        super(context, R.layout.list_video, objects);

        mContext = context;
        mVideos = objects;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;



        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.list_video, null);
            holder = new ViewHolder();

            holder.videoView = (VideoView) convertView
                    .findViewById(R.id.myVideo);

            card_view = (CardView) convertView.findViewById(R.id.card_view);


            card_view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    mContext.startActivity(new Intent(mContext, ViewFullVideo.class));
                }

            });

            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();

        }


        try {
            VideoModel video = mVideos.get(position);


            String url = video.getVideoUrl();
            Uri videoUri = Uri.parse(url);
            holder.videoView.setVideoURI(videoUri);
            holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {

                    mp.setLooping(true);

                    //holder.videoView.start();
                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }


        return convertView;
    }

    public static class ViewHolder {
        VideoView videoView;



    }
}