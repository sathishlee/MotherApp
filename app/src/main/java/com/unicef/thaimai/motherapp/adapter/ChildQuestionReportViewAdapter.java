package com.unicef.thaimai.motherapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.unicef.thaimai.motherapp.ChildDevelopment.FullViewImageActivity;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.model.ChildDevelopmentModdel.ChildQuestionReportViewModel;

import java.util.ArrayList;

public class ChildQuestionReportViewAdapter extends RecyclerView.Adapter<ChildQuestionReportViewAdapter.ViewHolder> {
    String TAG = ChildQuestionReportViewAdapter.class.getSimpleName();
    Context context;
    ArrayList<ChildQuestionReportViewModel> childQuestionReportViewModel;

    public ChildQuestionReportViewAdapter(Context context, ArrayList<ChildQuestionReportViewModel> childQuestionReportViewModel) {
        this.context = context;
        this.childQuestionReportViewModel = childQuestionReportViewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_child_dev_view_question, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.e(ChildDevQuestionAdapter.class.getSimpleName(), "position-->" + position + "array size-->" + childQuestionReportViewModel.size());
        final ChildQuestionReportViewModel childDevQuestionModel = childQuestionReportViewModel.get(position);
        holder.txt_question.setText(childDevQuestionModel.getQuestion());
        if (childDevQuestionModel.getAnswer().equalsIgnoreCase("Yes")){
            holder.txt_status.setText("Image Upload");
            holder.txt_status.setVisibility(View.GONE);
            holder.img_camera.setVisibility(View.VISIBLE);

        }else{
            holder.txt_status.setVisibility(View.VISIBLE);
            holder.txt_status.setText("Image Not Upload");
            holder.img_camera.setVisibility(View.GONE);
            }

        if (!TextUtils.isEmpty(childDevQuestionModel.getPhoto())) {
            Picasso.with(this.context)
                    .load(!TextUtils.isEmpty(childDevQuestionModel.getPhoto()) ? Apiconstants.CHILD_TRACKING_IMAGES + childDevQuestionModel.getPhoto() : "")
                    .placeholder(R.drawable.no_image)
                    .resize(400,400)
                    .centerInside()
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .error(R.drawable.no_image)
                    .into(holder.img_camera);
        } else {
            holder.img_camera.setImageResource(R.drawable.no_image);
        }
        holder.img_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(childDevQuestionModel.getPhoto())) {
                    AppConstants.FULL_VIEW_IMAGE_CHILD_REPORT =Apiconstants.CHILD_TRACKING_IMAGES + childDevQuestionModel.getPhoto();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("imageuri", childDevQuestionModel.getPhoto());
                    Intent intent = new Intent(context.getApplicationContext(), FullViewImageActivity.class);
//                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);
                }else{

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return childQuestionReportViewModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_question, txt_status;
        public ImageView img_camera, img_view;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_question = itemView.findViewById(R.id.txt_question);
            txt_status = itemView.findViewById(R.id.txt_ans1);
            img_camera = itemView.findViewById(R.id.img_view_image1);
            img_view = itemView.findViewById(R.id.img_open_camera);
        }
    }
}
