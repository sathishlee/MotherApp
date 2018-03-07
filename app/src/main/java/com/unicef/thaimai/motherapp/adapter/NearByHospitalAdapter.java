package com.unicef.thaimai.motherapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.model.NearByHospitalModel;

import java.util.List;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class NearByHospitalAdapter extends RecyclerView.Adapter<NearByHospitalAdapter.ViewHolder>{
    List<NearByHospitalModel> list_nearByHospitalModel;
    Context applicationContext;

    public NearByHospitalAdapter(List<NearByHospitalModel> list_nearByHospitalModel, Context applicationContext) {
        this.applicationContext =applicationContext;
        this.list_nearByHospitalModel =list_nearByHospitalModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_hospital,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NearByHospitalModel nearByHospitalModel = list_nearByHospitalModel.get(position);

        holder.hosp_name.setText(nearByHospitalModel.getF_facility_name());
        holder.hosp_facility.setText(nearByHospitalModel.getF_health_facility());
        holder.hosp_location.setText(nearByHospitalModel.getF_district_name());
        holder.hosp_distance.setText(nearByHospitalModel.getDistance());
        holder.txt_call.setText(nearByHospitalModel.getF_nin_num());
        holder.txt_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(applicationContext.getApplicationContext(),"make call", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_nearByHospitalModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_call, hosp_name, hosp_facility, hosp_location, hosp_distance;


        public ViewHolder(View itemView) {
            super(itemView);

            txt_call = itemView.findViewById(R.id.txt_call);
            hosp_name = itemView.findViewById(R.id.hosp_name);
            hosp_facility = itemView.findViewById(R.id.hosp_facility);
            hosp_location = itemView.findViewById(R.id.hosp_location);
            hosp_distance = itemView.findViewById(R.id.hosp_distance);



        }
    }
}
