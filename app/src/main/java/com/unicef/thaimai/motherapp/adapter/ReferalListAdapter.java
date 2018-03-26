package com.unicef.thaimai.motherapp.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.Interface.RefrealAutoComplete;
import com.unicef.thaimai.motherapp.Presenter.ReferalPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.activity.AddReferral;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.model.responsemodel.ReferalListResponseModel;

import java.util.ArrayList;

/**
 * Created by sathish on 3/18/2018.
 */

public class ReferalListAdapter extends  RecyclerView.Adapter<ReferalListAdapter.ViewHolder> {
    ArrayList<ReferalListResponseModel.NearestHospitals> mReferalLis;
    ReferalListResponseModel.NearestHospitals      mReferalListmodel;
    Context mcontext;
    RefrealAutoComplete refrealAutoComplete;



    public ReferalListAdapter(Context mcontext,ArrayList<ReferalListResponseModel.NearestHospitals> mReferalLis,RefrealAutoComplete refrealAutoComplete) {
        this.mReferalLis = mReferalLis;
        this.mcontext = mcontext;
        this.refrealAutoComplete = refrealAutoComplete;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.referral_card, parent, false);

        return new ViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        mReferalListmodel = mReferalLis.get(position);
       if (mReferalListmodel.getReferalStatus().equalsIgnoreCase("Created") ){

           holder.ll_referal_card.setBackgroundColor(Color.RED);
       }  /*if (mReferalListmodel.getReferalStatus().equalsIgnoreCase("InProgress") ){

           holder.ll_referal_card.setBackgroundColor(Color.RED);
       } */ if (mReferalListmodel.getReferalStatus().equalsIgnoreCase("InProgress")&& mReferalListmodel.getRUpdateAdmitted().equalsIgnoreCase("Yes") ){

            holder.ll_referal_card.setBackgroundColor(mcontext.getResources().getColor(R.color.card_blue));

        }
        else  if(mReferalListmodel.getReferalStatus().equalsIgnoreCase("Closed") && mReferalListmodel.getRUpdateAdmitted().equalsIgnoreCase("No")){
           holder.ll_referal_card.setBackgroundColor(Color.BLACK);

       }else if(mReferalListmodel.getReferalStatus().equalsIgnoreCase("Closed") && mReferalListmodel.getRUpdateAdmitted().equalsIgnoreCase("Yes")){
           holder.ll_referal_card.setBackgroundColor(mcontext.getResources().getColor(R.color.card_blue));

       }
        holder.txt_referal_date.setText(mReferalListmodel.getRReferalDate());
//        AppConstants.REFERAL_DATE = mReferalListmodel.getRReferalDate();

        holder.txt_referal_time.setText(mReferalListmodel.getRReferalTime());
//        AppConstants.REFERAL_TIME = mReferalListmodel.getRReferalTime();

        holder.txt_referred_by.setText(mReferalListmodel.getRReferredBy());
//        AppConstants.REFERAL_BY = mReferalListmodel.getRReferredBy();

        holder.txt_referred_from.setText(mReferalListmodel.getRFacilityReferring());
//        AppConstants.REFERAL_FACILITY= mReferalListmodel.getRFacilityReferring();

        holder.txt_referred_to.setText(mReferalListmodel.getRFacilityReferredTo());
//        AppConstants.REFERAL_TO = mReferalListmodel.getRFacilityReferredTo();

        holder.txt_diagnosis.setText(mReferalListmodel.getRDiagonosis());
//        AppConstants.REFERAL_DIAGONOSIS = mReferalListmodel.getRDiagonosis();

        holder.txt_referred_why.setText(mReferalListmodel.getRReferalReason());
//        AppConstants.REFERAL_REASON = mReferalListmodel.getRReferalReason();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mReferalLis.get(position).getReferalStatus().equalsIgnoreCase("Created")){
                    AppConstants.CREATE_NEW_REFRAL = false;
                    AppConstants.REFERAL_ID = mReferalLis.get(position).getRid();
                    mcontext.startActivity(new Intent(mcontext, AddReferral.class));


                }
                else if (mReferalLis.get(position).getReferalStatus().equalsIgnoreCase("InProgress")) {

//                    AppConstants.CREATE_NEW_REFRAL = false;
//                    AppConstants.REFERAL_ID = mReferalLis.get(position).getRid();
//                    refrealAutoComplete.isRefrealAutoComplete( AppConstants.REFERAL_ID,"InProgress" );

                    AppConstants.CREATE_NEW_REFRAL = false;
                    AppConstants.REFERAL_ID = mReferalLis.get(position).getRid();
                    mcontext.startActivity(new Intent(mcontext, AddReferral.class));


                }/*else if (mReferalListmodel.getReferalStatus().equalsIgnoreCase("Created")){
                    AppConstants.CREATE_NEW_REFRAL = false;
                    AppConstants.REFERAL_ID = mReferalLis.get(position).getRid();
                    refrealAutoComplete.isRefrealAutoComplete( AppConstants.REFERAL_ID ,"Created");
                }*/else{
                    Toast.makeText(mcontext.getApplicationContext(),"Referal Completed",Toast.LENGTH_SHORT).show();
                }


            }

        });
    }

    @Override
    public int getItemCount() {
        return mReferalLis.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         TextView txt_referal_time,txt_referred_by,txt_referred_from,txt_referred_to,txt_diagnosis,txt_referred_why,txt_referal_date;
        LinearLayout ll_referal_card,ll_referal_card_inner;
        CardView referral_card;
        public ViewHolder(View itemView) {
            super(itemView);
            txt_referal_date =  itemView.findViewById(R.id.txt_referal_date);
            txt_referal_time =itemView.findViewById(R.id.txt_referal_time);
            txt_referred_by = itemView.findViewById(R.id.txt_referred_by);
              txt_referred_from =itemView.findViewById(R.id.txt_referred_from);
              txt_referred_to =itemView.findViewById(R.id.txt_referred_to);
              txt_diagnosis =itemView.findViewById(R.id.txt_diagnosis);
            txt_referred_why =itemView.findViewById(R.id.txt_referred_why);
            ll_referal_card =itemView.findViewById(R.id.ll_referal_card);
            ll_referal_card_inner =itemView.findViewById(R.id.ll_referal_card_inner);
            referral_card =itemView.findViewById(R.id.referral_card);

        }
    }
}
