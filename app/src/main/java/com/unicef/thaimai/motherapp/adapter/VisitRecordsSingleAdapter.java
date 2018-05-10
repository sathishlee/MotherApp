package com.unicef.thaimai.motherapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.model.responsemodel.VisitRecordsSingleResponseModel;
import com.unicef.thaimai.motherapp.utility.RoundedTransformation;

import java.util.ArrayList;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class VisitRecordsSingleAdapter extends RecyclerView.Adapter<VisitRecordsSingleAdapter.SingleImageHolder> {

    private ArrayList<VisitRecordsSingleResponseModel> visitRecordsSingleResponseModels;
    private Context context;
    String visitImage;
    Activity applicationContext;
    PreferenceData preferenceData;


    public VisitRecordsSingleAdapter(ArrayList<VisitRecordsSingleResponseModel> visitRecordsSingleResponseModels, Context context){
        this.context = context;
        this.visitRecordsSingleResponseModels = visitRecordsSingleResponseModels;
    }

    @Override
    public SingleImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reports_list_single, parent,false);
//        SingleImageHolder singleImageHolder = new SingleImageHolder(v);
        return new SingleImageHolder(v);
    }

    @Override
    public void onBindViewHolder(SingleImageHolder imageHolder, int position){
        preferenceData = new PreferenceData(context);
       final VisitRecordsSingleResponseModel visitRecordsSingleResponseModel = visitRecordsSingleResponseModels.get(position);

        visitImage = visitRecordsSingleResponseModel.getImage();
        Log.w("Visit Reports", Apiconstants.VISIT_REPORTS_URL+preferenceData.getPicmeId()+visitImage);

        if(!TextUtils.isEmpty(visitRecordsSingleResponseModel.getImage())) {
            Picasso.with(this.context)
                    .load(!TextUtils.isEmpty(visitRecordsSingleResponseModel.getImage())? Apiconstants.VISIT_REPORTS_URL+preferenceData.getPicmeId()+"/"+visitRecordsSingleResponseModel.getImage():"")
                    .placeholder(R.drawable.no_image)
                    .fit()
                    .centerCrop()
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .transform(new RoundedTransformation(90, 4))
                    .error(R.drawable.no_image)
                    .into(imageHolder.itemImage);
        }
        else{
            imageHolder.itemImage.setImageResource(R.drawable.no_image);
        }
//        imageHolder
    }

    @Override
    public int getItemCount() {
//        return (null != visitRecordsSingleResponseModels ? visitRecordsSingleResponseModels.size() : 0);
        return visitRecordsSingleResponseModels.size();
    }

    public class SingleImageHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;

        public SingleImageHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
        }
    }

}