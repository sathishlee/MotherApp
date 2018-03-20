package com.unicef.thaimai.motherapp.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.model.responsemodel.ImmunizationResponseModel;
import java.util.List;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class ImmunizationListAdapter extends RecyclerView.Adapter<ImmunizationListAdapter.ViewHolder> {

    List<ImmunizationResponseModel.Result> resultList;
    Activity applicationContext;


    public ImmunizationListAdapter(List<ImmunizationResponseModel.Result> resultList, Activity applicationContext) {
        this.applicationContext =applicationContext;
        this.resultList =resultList;

    }

    @Override
    public ImmunizationListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_immunization,parent,false);
        return new ImmunizationListAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ImmunizationListAdapter.ViewHolder holder, int position) {
        final ImmunizationResponseModel.Result result = resultList.get(position);
        holder.txt_imu_dose_number.setText(result.getImmDoseNumber());
        holder.txt_due_date.setText(result.getImmDueDate());
        holder.txt_provided_date.setText(result.getImmCarePovidedDate());
        holder.txt_opv.setText(result.getImmOpvStatus());
        holder.txt_pentavalent.setText(result.getImmPentanvalentStatus());
        holder.txt_rota.setText(result.getImmRotaStatus());
        holder.txt_ipv.setText(result.getImmIpvStatus());


    }


    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_imu_dose_number, txt_due_date, txt_provided_date, txt_opv, txt_pentavalent, txt_rota, txt_ipv;


        public ViewHolder(View itemView) {
            super(itemView);

            txt_imu_dose_number = itemView.findViewById(R.id.txt_imu_dose_number);
            txt_due_date = itemView.findViewById(R.id.txt_due_date);
            txt_provided_date = itemView.findViewById(R.id.txt_provided_date);
            txt_opv = itemView.findViewById(R.id.txt_opv);
            txt_pentavalent = itemView.findViewById(R.id.txt_pentavalent);
            txt_rota = itemView.findViewById(R.id.txt_rota);
            txt_ipv = itemView.findViewById(R.id.txt_ipv);

        }
    }



}
