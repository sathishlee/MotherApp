package com.unicef.thaimai.motherapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.model.NotificationModel;
import com.unicef.thaimai.motherapp.model.VisitModel;

import java.util.List;

/**
 * Created by sathish on 2/14/2018.
 */

public class VisitAdapter extends RecyclerView.Adapter<VisitAdapter.ViewHolder>{
    List<VisitModel> visitList;
    public VisitAdapter(List<VisitModel> visitList) {
        this.visitList = visitList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_visit_1, parent, false);

        return new VisitAdapter.ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
