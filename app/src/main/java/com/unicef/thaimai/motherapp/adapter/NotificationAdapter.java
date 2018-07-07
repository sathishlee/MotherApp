package com.unicef.thaimai.motherapp.adapter;

import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.model.NotificationListResponseModel;

import java.text.ParseException;
import java.util.ArrayList;


/**
 * Created by Suthishan on 20/1/2018.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>  {
    private ArrayList<NotificationListResponseModel.NotificationList> moviesList;
    NotificationListResponseModel.NotificationList movie;;
    public NotificationAdapter(ArrayList<NotificationListResponseModel.NotificationList> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);

        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NotificationListResponseModel.NotificationList movie = moviesList.get(position);
        holder.txt_message.setText(movie.getMPicmeId());
        holder.txt_time.setText(timeago(movie.getMessage()));

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private CharSequence timeago(String noteStartDateTime) {
        SimpleDateFormat sdf = null;
        CharSequence ago = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");      //31-03-2018 14:35:54
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            try {
                long time = sdf.parse(noteStartDateTime).getTime();
                long now = System.currentTimeMillis();
                ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
                Log.e("time --->", String.valueOf(time));
                Log.e("currentt ime --->", String.valueOf(now));
                Log.e("ago time --->", String.valueOf(ago));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return ago;
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_message, txt_time;

        public ViewHolder(View view) {
            super(view);
            txt_message = (TextView) view.findViewById(R.id.txt_message);
            txt_time = (TextView) view.findViewById(R.id.txt_time);
//            year = (TextView) view.findViewById(R.id.year);
        }
    }
}
