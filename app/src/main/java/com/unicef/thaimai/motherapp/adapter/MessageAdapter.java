package com.unicef.thaimai.motherapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.fragment.MessageFragment;
import com.unicef.thaimai.motherapp.model.responsemodel.MessageFragmentModel;

import java.util.ArrayList;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class MessageAdapter extends  RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    ArrayList<MessageFragmentModel.Healthtips> healthtipses;
    MessageFragmentModel.Healthtips healthtips;
    Context context;


    public MessageAdapter(Context context, ArrayList<MessageFragmentModel.Healthtips> healthtipses){
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
    public void onBindViewHolder(MessageAdapter.ViewHolder holder, final int position) {
        healthtips = healthtipses.get(position);

        holder.message_tile.setText(healthtips.getSubject());
        holder.message_details.setText(Html.fromHtml(healthtips.getMessage()));
    }

    @Override
    public int getItemCount() {
        return healthtipses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
            TextView message_tile, message_details;
        public ViewHolder(View itemView) {
            super(itemView);

            message_tile = itemView.findViewById(R.id.message_tile);
            message_details = itemView.findViewById(R.id.message_details);


        }
    }
}
