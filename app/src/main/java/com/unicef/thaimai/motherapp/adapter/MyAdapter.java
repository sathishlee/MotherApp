package com.unicef.thaimai.motherapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.model.NotificationListResponseModel;
import com.unicef.thaimai.motherapp.model.NotificationModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Suthishan on 20/1/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>  {
    private ArrayList<NotificationListResponseModel.NotificationList> moviesList;
    NotificationListResponseModel.NotificationList movie;;
    public MyAdapter(ArrayList<NotificationListResponseModel.NotificationList> moviesList) {
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
        holder.title.setText(movie.getMPicmeId());
        holder.genre.setText(movie.getMessage());
        holder.year.setText(movie.getDateTime());

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title, year, genre;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
//            genre = (TextView) view.findViewById(R.id.genre);
//            year = (TextView) view.findViewById(R.id.year);
        }
    }
}
