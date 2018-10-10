package com.unicef.thaimai.motherapp.adapter;

import android.content.Context;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.unicef.thaimai.motherapp.Interface.CaptureImageInterface;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.model.ChildDevelopmentModdel.ChildDevQuestionModel;

import java.util.ArrayList;


public class ChildDevQuestionAdapter extends RecyclerView.Adapter<ChildDevQuestionAdapter.ViewHolder>   {
    String TAG = ChildDevQuestionAdapter.class.getSimpleName();
    ArrayList<ChildDevQuestionModel> arrQuestionList;
    ArrayList<ChildDevQuestionModel> arrQuestionListupload;
    Context context;
    CaptureImageInterface captureImageInterface;
    int currentposition;
    ArrayList<String> arrAnsList;



    public ChildDevQuestionAdapter(Context context, ArrayList<ChildDevQuestionModel> arrQuestionList, CaptureImageInterface captureImageInterface) {
        Log.e(TAG,arrQuestionList.size()+"");

        this.arrQuestionList = arrQuestionList;
        this.context = context;
        this.captureImageInterface = captureImageInterface;
        arrAnsList = new ArrayList<>();

        setHasStableIds(true);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_child_dev_question, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ChildDevQuestionAdapter.ViewHolder holder, final int position) {

        Log.e(ChildDevQuestionAdapter.class.getSimpleName(),"position-->"+position+"array size-->"+arrQuestionList.size());
          ChildDevQuestionModel childDevQuestionModel = arrQuestionList.get(position);
        holder.txt_question.setText(childDevQuestionModel.getSetquestion());

        /*if (childDevQuestionModel.getSetphoto()!= null) {
            holder.img_camera.setImageBitmap(childDevQuestionModel.getSetphoto());
        }*/
        if (childDevQuestionModel.getSetanswer().equalsIgnoreCase("Yes")){
            holder.but_submit.setVisibility(View.GONE);
            Picasso.with(context)
                    .load(Apiconstants.CHILD_TRACKING_IMAGES+arrQuestionList.get(position).getSetImageUri())
                    .placeholder(R.drawable.no_image)
                    .resize(125,125)
                    .centerInside()
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .error(R.drawable.no_image)
                    .into(holder.img_camera);
        }else {

            holder.img_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    captureImageInterface.OpenCamera(holder,position);
                }
            });
        }



        holder.but_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "getSetquestion" + arrQuestionList.get(position).getSetquestion());
                Log.e(TAG, "getDomain" + arrQuestionList.get(position).getDomain());
//                Log.e(TAG,arrQuestionList.get(position).getDomain());
                captureImageInterface.SendQuestion(holder, position, holder.bitmap);
            }
        });
       /* holder.img_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });*/

       /* holder.rb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                holder.rb2.setChecked(false);
                holder.rb1.setChecked(true);
       captureImageInterface.OpenCamera(holder,position);
            }
        });*/

        /*holder.rb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                holder.rb1.setChecked(false);
                holder.rb2.setChecked(true);
                captureImageInterface.NextQuestion(position);


            }
        });*/

    }

    @Override
    public int getItemCount() {
        return arrQuestionList.size();
    }



    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_question;
//        public RadioGroup rg_answer;
        public ImageView img_camera, img_view;
        public Button but_submit;
//        public RadioButton rb1, rb2;
        public Bitmap bitmap;
        public ViewHolder(View itemView) {
            super(itemView);
            txt_question = itemView.findViewById(R.id.txt_question);
//            rg_answer = itemView.findViewById(R.id.rg_answer);
            img_camera = itemView.findViewById(R.id.img_view_image);
//            img_view = itemView.findViewById(R.id.img_camera_image);
//            rb1 = itemView.findViewById(R.id.rb_yes);
//            rb2 = itemView.findViewById(R.id.rb_no);
            but_submit = itemView.findViewById(R.id.but_submit);

        }
    }
}
