package com.unicef.thaimai.motherapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;

import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.VideoView;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.model.VideoModel;

import java.util.List;




public class VideoAdapter extends ArrayAdapter<VideoModel> {

    private Context mContext;
    private List<VideoModel> mVideos;

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

            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        /***get clicked view and play video url at this position**/
        try {
            VideoModel video = mVideos.get(position);
            //play video using android api, when video view is clicked.
            String url = video.getVideoUrl(); // your URL here
            Uri videoUri = Uri.parse(url);
            holder.videoView.setVideoURI(videoUri);

            holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                    holder.videoView.start();
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