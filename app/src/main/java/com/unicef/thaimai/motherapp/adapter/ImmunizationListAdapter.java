package com.unicef.thaimai.motherapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.activity.ImmunizationEditActivity;
import com.unicef.thaimai.motherapp.constant.AppConstants;
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_immunization,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ImmunizationResponseModel.Result result = resultList.get(position);
        holder.txt_imu_dose_number.setText(result.getImmDoseNumber());
        holder.txt_due_date.setText(result.getImmDueDate());
        holder.txt_provided_date.setText(result.getImmCarePovidedDate());
        holder.txt_opv.setText(result.getImmOpvStatus());
        holder.txt_pentavalent.setText(result.getImmPentanvalentStatus());
        holder.txt_rota.setText(result.getImmRotaStatus());
        holder.txt_ipv.setText(result.getImmIpvStatus());
//        holder.ll_edit_immunization.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                AppConstants. IMMUNIZATION_EDIT=false;
////                AppConstants.ImmuID=Result.getImmId();
////                applicationContext.startActivity(new Intent(applicationContext.getApplicationContext(), ImmunizationEditActivity.class));
//            }
//        });

    }


    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_imu_dose_number, txt_due_date, txt_provided_date, txt_opv, txt_pentavalent, txt_rota, txt_ipv;
//        LinearLayout ll_edit_immunization;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_imu_dose_number = itemView.findViewById(R.id.txt_imu_dose_number);
            txt_due_date = itemView.findViewById(R.id.txt_due_date);
            txt_provided_date = itemView.findViewById(R.id.txt_provided_date);
            txt_opv = itemView.findViewById(R.id.txt_opv);
            txt_pentavalent = itemView.findViewById(R.id.txt_pentavalent);
            txt_rota = itemView.findViewById(R.id.txt_rota);
            txt_ipv = itemView.findViewById(R.id.txt_ipv);
//            ll_edit_immunization =itemView.findViewById(R.id.ll_edit_immunization);

        }
    }



}
