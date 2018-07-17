package com.unicef.thaimai.motherapp.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.model.responsemodel.ANVisitRecordsFullResponseModel;
import com.unicef.thaimai.motherapp.model.responsemodel.ANVisitRecordsSingleResponseModel;

import java.util.ArrayList;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class ANVisitRecordsAdapter extends RecyclerView.Adapter<ANVisitRecordsAdapter.VisitHeader> {

    private ArrayList<ANVisitRecordsFullResponseModel> visitRecordsFullResponseModels = new ArrayList<>();
    private Activity context;
//    private RecyclerView.RecycledViewPool recycledViewPool;

    public ANVisitRecordsAdapter(ArrayList<ANVisitRecordsFullResponseModel> visitRecordsFullResponseModels, Activity context){
        this.context = context;
        this.visitRecordsFullResponseModels = visitRecordsFullResponseModels;
//        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    public VisitHeader onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mother_reports, viewGroup,false);
//        VisitHeader visitHeader = new VisitHeader(v);
        return new VisitHeader(v);
    }

    public void setList(ArrayList<ANVisitRecordsFullResponseModel> visitRecordsFullResponseModels){
        this.visitRecordsFullResponseModels.addAll(visitRecordsFullResponseModels);
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(VisitHeader visitHeader, int position){
        Log.w("position",position+"");

        final ANVisitRecordsFullResponseModel visitRecordsFullResponseModel = visitRecordsFullResponseModels.get(position);

        final String titleName = visitRecordsFullResponseModel.getTitle();

        final ArrayList<ANVisitRecordsSingleResponseModel> singleResponseModels = visitRecordsFullResponseModel.getVisitRecordsSingleResponseModels();

//        ArrayList arrayList = visitRecordsFullResponseModel.getVisitRecordsSingleResponseModels();

        visitHeader.txt_visit_details.setText(titleName);

        Log.w("title name",titleName);

        ANVisitRecordsSingleAdapter adapter = new ANVisitRecordsSingleAdapter(singleResponseModels, context);

        visitHeader.recyclerView.setHasFixedSize(true);

        visitHeader.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        visitHeader.recyclerView.setAdapter(adapter);

        visitHeader.recyclerView.setNestedScrollingEnabled(false);

        visitHeader.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent= new Intent(context,ANImageFullViewActivity.class);
               *//*for (int i=0;i<singleResponseModels.size();i++){
//                   Apiconstants.VISIT_REPORTS_URL+preferenceData.getPicmeId()+"/"+visitRecordsSingleResponseModel.getImage()
                   intent.putExtra("image"+i,singleResponseModels.get(i)+"");
               }*//*
//                intent.putExtra("mylist", singleResponseModels);
                context.startActivity(intent);*/
            }
        });
    }
    @Override
    public int getItemCount() {
//        return (null != visitRecordsFullResponseModels ? visitRecordsFullResponseModels.size() : 0);
        return visitRecordsFullResponseModels.size();
    }

    public class VisitHeader extends RecyclerView.ViewHolder {
        protected TextView txt_visit_details;
        protected RecyclerView recyclerView;

        public VisitHeader(View itemView) {
            super(itemView);
            txt_visit_details = itemView.findViewById(R.id.txt_visit_details);
            recyclerView = itemView.findViewById(R.id.recycler_view_list);
        }
    }

}
