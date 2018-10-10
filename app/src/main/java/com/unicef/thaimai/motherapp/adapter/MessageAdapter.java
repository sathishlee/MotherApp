package com.unicef.thaimai.motherapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.fragment.MessageFragment;
import com.unicef.thaimai.motherapp.model.responsemodel.MessageFragmentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class MessageAdapter extends  RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    List<MessageFragmentModel.Healthtips> healthtipses;
    MessageFragmentModel.Healthtips healthtips;
    Context context;


    public MessageAdapter(Context context, List<MessageFragmentModel.Healthtips> healthtipses){
        this.healthtipses = healthtipses;
        this.context = context;
    }

    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragment_message_list, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MessageAdapter.ViewHolder holder, final int position) {
        healthtips = healthtipses.get(position);
        holder.ll_notify_block.setBackgroundColor(Color.GRAY);

        holder.message_tile.setText(healthtips.getSubject());
        holder.message_details.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                 if (holder.isclick) {
                     holder.isclick=false;
                     holder.message_details.setVisibility(View.VISIBLE);
                     holder.message_details.setText(Html.fromHtml(healthtips.getMessage()));
                     holder.ll_notify_block.setBackgroundColor(Color.WHITE);
                 }else{
                     holder.isclick=true;
                     holder.message_details.setVisibility(View.GONE);
                     holder.ll_notify_block.setBackgroundColor(Color.GRAY);

                     holder.message_date.setImageResource(R.drawable.quantum_ic_keyboard_arrow_down_white_36);



                 }
            }
        });
    }

    @Override
    public int getItemCount() {
        return healthtipses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        boolean isclick;
            TextView message_tile, message_details;
            ImageView message_date;
            LinearLayout ll_notify_block;
        public ViewHolder(View itemView) {
            super(itemView);
            message_tile = itemView.findViewById(R.id.message_tile);
            message_details = itemView.findViewById(R.id.message_details);
            message_date = itemView.findViewById(R.id.message_date);
            ll_notify_block = itemView.findViewById(R.id.ll_notify_block);
            isclick =true;

        }
    }
}
