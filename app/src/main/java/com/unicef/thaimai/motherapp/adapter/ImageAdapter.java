package com.unicef.thaimai.motherapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.model.responsemodel.ImageFragmentModel;
import com.unicef.thaimai.motherapp.model.responsemodel.MessageFragmentModel;

import java.util.ArrayList;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class ImageAdapter extends  RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    ArrayList<ImageFragmentModel.HealthtipsImage> healthtipses;
    ImageFragmentModel.HealthtipsImage healthtips;
    Context context;
    String healthImage;


    public ImageAdapter(Context context, ArrayList<ImageFragmentModel.HealthtipsImage> healthtipses){
        this.healthtipses = healthtipses;
        this.context = context;
    }

    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragment_health_images, parent, false);

        return new ImageAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ImageAdapter.ViewHolder holder, final int position) {
        healthtips = healthtipses.get(position);

        holder.message_tile.setText(healthtips.getSubject());
        holder.message_details.setText(Html.fromHtml(healthtips.getMessage()));
        healthImage = healthtips.getHealth_pic();
        Log.w("healthImage URL--->",healthImage );


        if(!TextUtils.isEmpty(healthtips.getHealth_pic())) {
            Picasso.with(this.context)
                    .load(!TextUtils.isEmpty(healthtips.getHealth_pic())? Apiconstants.HEALTH_TIPS_IMAGES+"/"+healthtips.getHealth_pic():"")
                    .placeholder(R.drawable.no_image)
                    .fit()
                    .centerCrop()
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .error(R.drawable.no_image)
                    .into(holder.healthImage);
        }
        else{
            holder.healthImage.setImageResource(R.drawable.no_image);
        }

    }

    @Override
    public int getItemCount() {
        return healthtipses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView message_tile, message_details;
        ImageView healthImage;
        public ViewHolder(View itemView) {
            super(itemView);

            message_tile = itemView.findViewById(R.id.message_tile);
            message_details = itemView.findViewById(R.id.message_details);
            healthImage = itemView.findViewById(R.id.healthImage);


        }
    }
}