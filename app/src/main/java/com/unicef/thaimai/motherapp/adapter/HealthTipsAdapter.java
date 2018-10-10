package com.unicef.thaimai.motherapp.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.unicef.thaimai.motherapp.Interface.DownloadVideoInterface;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.activity.VideoViewActivity;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.listener.AdapterVideoActionListener;
import com.unicef.thaimai.motherapp.listener.AnimateFirstDisplayListener;
import com.unicef.thaimai.motherapp.model.VideoModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class HealthTipsAdapter extends RecyclerView.Adapter<HealthTipsAdapter.ViewHolder> {

    List<VideoModel.VideoList> videoLists;
    Context applicationContext;
    private DisplayImageOptions options;

    AdapterVideoActionListener listener;
    DownloadVideoInterface downloadVideoInterface;
    String filename;


    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public HealthTipsAdapter(List<VideoModel.VideoList> videoLists, Context applicationContext, DownloadVideoInterface downloadVideoInterface){
        this.applicationContext = applicationContext;
        this.videoLists = videoLists;
        this.downloadVideoInterface =downloadVideoInterface;

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

    public void setAdapterActionListener(AdapterVideoActionListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_video,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        final VideoModel.VideoList list = videoLists.get(position);

        holder.img_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                downloadVideoInterface.download(list.getVideo(),list.getVideo());
            }
        });
        filename = list.getVideo();

        holder.txt_video_month.setText(list.getId());
        holder.txt_video_title.setText(list.getTitle());
        holder.img_video_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onPlayVideoListener(position, list);
                Bundle bundle =new Bundle();
                bundle.putString("title", list.getTitle());
                bundle.putString("videolist", list.getVideo());
                Intent intent =new Intent(applicationContext.getApplicationContext(), VideoViewActivity.class);
                intent.putExtras(bundle);
                applicationContext.startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onItemClickListener(position, list);
            }
        });
//        ImageLoader.getInstance().displayImage(list.getVideo(), holder.imvThumbnail, options, animateFirstListener);
    }



    @Override
    public int getItemCount() {
        return videoLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imvThumbnail, img_video_play,img_download;
        TextView txt_video_title, txt_video_month, txt_download;


        public ViewHolder(View itemView) {
            super(itemView);

            imvThumbnail = itemView.findViewById(R.id.img_video_thumbnail);
            img_video_play = itemView.findViewById(R.id.img_video_play);
            txt_video_title = itemView.findViewById(R.id.txt_video_title);
            txt_video_month = itemView.findViewById(R.id.txt_video_month);
            txt_download = itemView.findViewById(R.id.txt_download);
            img_download = itemView.findViewById(R.id.img_download);
        }
    }




}
