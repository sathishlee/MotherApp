package com.unicef.thaimai.motherapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unicef.thaimai.motherapp.ChildDevelopment.ChildTrackingViewReportActivity;
import com.unicef.thaimai.motherapp.Interface.CaptureImageInterface;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.model.ChildDevelopmentModdel.ChildDevQuestionModel;
import com.unicef.thaimai.motherapp.model.ChildDevelopmentModdel.ChildQuestionReportViewModel;
import com.unicef.thaimai.motherapp.model.ChildDevelopmentModdel.OuterListModel;

import java.util.ArrayList;

public class ChildDevelopmentReportAdapter extends PagerAdapter implements CaptureImageInterface {

    String TAG = ChildDevelopmentReportAdapter.class.getSimpleName();
    Context context;
    private LayoutInflater inflater;
    ArrayList<OuterListModel> nested_childQuestionReportViewModel;
    OuterListModel outerListModel;
    ArrayList<ChildQuestionReportViewModel> childQuestionReportViewModel;

    ChildQuestionReportViewAdapter childQuestionReportViewAdapter;

    public ChildDevelopmentReportAdapter(Context applicationContext, ArrayList<OuterListModel> nested_childQuestionReportViewModel) {
        Log.e(TAG, "arrchildDevList--->" + nested_childQuestionReportViewModel.size());
        this.context = applicationContext;
        this.nested_childQuestionReportViewModel = nested_childQuestionReportViewModel;
        inflater = LayoutInflater.from(applicationContext);

        for (int i = 0; i < nested_childQuestionReportViewModel.size(); i++) {
            childQuestionReportViewModel =nested_childQuestionReportViewModel.get(i).getChildQuestionReportViewModels();
            Log.e(TAG,nested_childQuestionReportViewModel.get(i).getChildQuestionReportViewModels()+"");
            for (int j=0;j<childQuestionReportViewModel.size();j++){
            }
        }
    }



    TextView age_of_month;
    LinearLayout ll_button_block,ll_block12,ll_block13;
    RecyclerView rec_question_list;
    LinearLayoutManager mLayoutManager;


    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View reportView = inflater.inflate(R.layout.activity_child_tracking, view, false);
        initUI(reportView);

      for (int i = 0; i < nested_childQuestionReportViewModel.size(); i++) {
            childQuestionReportViewModel =nested_childQuestionReportViewModel.get(i).getChildQuestionReportViewModels();
            notifyDataSetChanged();
            for (int j=1;j<=childQuestionReportViewModel.size();j++){
                if (AppConstants.CURRENT_MONTH<=j){
                setValue(childQuestionReportViewModel,position);
            }
            }
        }

        view.addView(reportView);

        return reportView;
    }

    private void setValue(ArrayList<ChildQuestionReportViewModel> childQuestionReportViewModel, int position) {

        ArrayList<ChildQuestionReportViewModel> childQuestionReportViewModel_adp=new ArrayList<>();

        for (int i=0;i<childQuestionReportViewModel.size();i++) {

                if (position == 0) {
                    if (childQuestionReportViewModel.get(i).getMonth().equalsIgnoreCase("2-4")) {
                        childQuestionReportViewModel_adp.add(childQuestionReportViewModel.get(i));
                    }
                } else if (position == 1) {
                    if (childQuestionReportViewModel.get(i).getMonth().equalsIgnoreCase("4-6")) {
                        childQuestionReportViewModel_adp.add(childQuestionReportViewModel.get(i));
                    }
                } else if (position == 2) {
                    if (childQuestionReportViewModel.get(i).getMonth().equalsIgnoreCase("6-9")) {
                        childQuestionReportViewModel_adp.add(childQuestionReportViewModel.get(i));
                    }
                } else if (position == 3) {
                    if (childQuestionReportViewModel.get(i).getMonth().equalsIgnoreCase("9-12")) {
                        childQuestionReportViewModel_adp.add(childQuestionReportViewModel.get(i));
                    }
                } else if (position == 4) {
                    if (childQuestionReportViewModel.get(i).getMonth().equalsIgnoreCase("12-15")) {
                        childQuestionReportViewModel_adp.add(childQuestionReportViewModel.get(i));
                    }
                } else if (position == 5) {
                    if (childQuestionReportViewModel.get(i).getMonth().equalsIgnoreCase("15-18")) {
                        childQuestionReportViewModel_adp.add(childQuestionReportViewModel.get(i));
                    }
                } else if (position == 6) {
                    if (childQuestionReportViewModel.get(i).getMonth().equalsIgnoreCase("18-24")) {
                        childQuestionReportViewModel_adp.add(childQuestionReportViewModel.get(i));
                    }
                } else if (position == 7) {
                    if (childQuestionReportViewModel.get(i).getMonth().equalsIgnoreCase("24-30")) {
                        childQuestionReportViewModel_adp.add(childQuestionReportViewModel.get(i));
                    }
                }

            }

            childQuestionReportViewAdapter = new ChildQuestionReportViewAdapter(context, childQuestionReportViewModel_adp);
            rec_question_list.setAdapter(childQuestionReportViewAdapter);
        }



    private void initUI(View reportView) {
        age_of_month = reportView.findViewById(R.id.age_of_month);
        ll_block12 = reportView.findViewById(R.id.ll_block12);
        ll_block13 = reportView.findViewById(R.id.ll_block13);
        ll_block12.setVisibility(View.GONE);
        ll_block13.setVisibility(View.GONE);
        ll_button_block = reportView.findViewById(R.id.ll_button_block);
        ll_button_block.setVisibility(View.GONE);
        rec_question_list = reportView.findViewById(R.id.rec_question_list);
        mLayoutManager = new LinearLayoutManager(context);
        rec_question_list.setLayoutManager(mLayoutManager);

        }


    @Override
    public int getCount() {
        return nested_childQuestionReportViewModel.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String strPosition="";
        if (position==0){
            strPosition="2-4";
        }else if (position==1){
            strPosition="4-6";
        }else if (position==2){
            strPosition="6-9";
        }else if (position==3){
            strPosition="9-12";
        }else if (position==4){
            strPosition="12-15";
        }else if (position==5){
            strPosition="15-18";
        }else if (position==6){
            strPosition="18-24";
        }else if (position==7){
            strPosition="24-30";
        }
        return strPosition;
    }


    @Override
    public void OpenCamera(ChildDevQuestionAdapter.ViewHolder holder, int postion) {

    }

    @Override
    public void NextQuestion(int postion) {

    }

    @Override
    public void SendQuestion(ChildDevQuestionAdapter.ViewHolder holder, int postion, Bitmap bitmap) {

    }


}
