package com.unicef.thaimai.motherapp.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.Interface.MakeCallInterface;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.model.responsemodel.NearHospitalResponseModel;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class NearByHospitalAdapter extends RecyclerView.Adapter<NearByHospitalAdapter.ViewHolder>{
    DecimalFormat precision = new DecimalFormat("0.00");
    List<NearHospitalResponseModel.Nearby> list_nearByHospitalModel;
    Activity applicationContext;
    MakeCallInterface makeCallInterface;
    public NearByHospitalAdapter(List<NearHospitalResponseModel.Nearby> list_nearByHospitalModel, Activity applicationContext, MakeCallInterface makeCallInterface) {
        this.applicationContext =applicationContext;
        this.list_nearByHospitalModel =list_nearByHospitalModel;
        this.makeCallInterface=  makeCallInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_hospital,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final NearHospitalResponseModel.Nearby nearByHospitalModel = list_nearByHospitalModel.get(position);
        if(nearByHospitalModel.getPhcMobile().equalsIgnoreCase("null")){
            holder.txt_phc_call.setText("-");
        }else{
            holder.txt_phc_call.setText(nearByHospitalModel.getPhcMobile());
        }
        if(nearByHospitalModel.getF_nin_num().equalsIgnoreCase("null")){
            holder.txt_f_nin_num.setText("-");
        }else{
            holder.txt_f_nin_num.setText(nearByHospitalModel.getF_nin_num());
        }
        if(nearByHospitalModel.getF_facility_name().equalsIgnoreCase("null")){
            holder.txt_phc_name.setText("-");
        }else{
            holder.txt_phc_name.setText(nearByHospitalModel.getF_facility_name());
        }
        if(nearByHospitalModel.getVillage().equalsIgnoreCase("null")){
            holder.txt_fac_name.setText("-");
        }else{
            holder.txt_fac_name.setText(nearByHospitalModel.getVillage());
        }
        if(nearByHospitalModel.getDistance().equalsIgnoreCase("null")){
            holder.txt_hosp_distance.setText("-");
        }else{
            holder.txt_hosp_distance.setText(nearByHospitalModel.getDistance());
        }

        if(nearByHospitalModel.getPhcMobile().equalsIgnoreCase("null")){
            holder.txt_phc_call.setText("-");
        }else{
            holder.txt_phc_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Toast.makeText(applicationContext.getApplicationContext(),"make call to"+nearByHospitalModel.getPhcMobile()+"", Toast.LENGTH_LONG).show();
                    makeCallInterface.makeCall(nearByHospitalModel.getPhcMobile());

                }
            });
        }

        if(nearByHospitalModel.getF_latitute().equalsIgnoreCase("null")&&nearByHospitalModel.getF_longititute().equalsIgnoreCase("null")){
            holder.itemView.setVisibility(View.GONE);
        }else{
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Toast.makeText(applicationContext.getApplicationContext(),"to view dir to google map", Toast.LENGTH_LONG).show();
                    Uri gmmIntentUri = Uri.parse("google.navigation:q="+nearByHospitalModel.getF_latitute()+","+nearByHospitalModel.getF_longititute());
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(applicationContext.getPackageManager()) != null) {
                        applicationContext. startActivity(mapIntent);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list_nearByHospitalModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_phc_call, txt_f_nin_num, txt_phc_name, txt_fac_name, txt_hosp_distance;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_f_nin_num = itemView.findViewById(R.id.txt_f_nin_num);
            txt_phc_name = itemView.findViewById(R.id.txt_phc_name);
            txt_fac_name = itemView.findViewById(R.id.txt_fac_name);
            txt_hosp_distance = itemView.findViewById(R.id.txt_hosp_distance);
            txt_phc_call = itemView.findViewById(R.id.txt_phc_call);
        }
    }

}
