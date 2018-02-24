package com.unicef.thaimai.motherapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.activity.PNMotherVisitActivity;

import java.util.List;

/**
 * Created by sathish on 2/23/2018.
 */

public class MothervistListAdapter  extends RecyclerView.Adapter<MothervistListAdapter.ViewHolder>  {
    private List<String> visitList;
    Activity  mactivity;
    public MothervistListAdapter(Activity  mactivity, List<String> visitList) {
        this.visitList = visitList;
        this.mactivity =mactivity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vist_list, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
holder.txt_visit.setText(visitList.get(position).toString());
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mactivity.startActivity(new Intent(mactivity.getApplicationContext(), PNMotherVisitActivity.class));
    }
});
    }



    @Override
    public int getItemCount() {
        return visitList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_visit;
        public ViewHolder(View itemView) {
            super(itemView);
            txt_visit =itemView.findViewById(R.id.txt_visit);
        }
    }
}
