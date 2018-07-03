package com.unicef.thaimai.motherapp.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.model.responsemodel.HealthRecordResponseModel;
import com.unicef.thaimai.motherapp.model.responsemodel.PnHbncVisitRecordsModel;

import java.util.ArrayList;

/**
 * Created by sathish on 3/13/2018.
 */

public class PNHBNCVisitRecordsAdapter extends PagerAdapter {
    PnHbncVisitRecordsModel.Visit_Records mPnHbncVisitRecordsModel;

    private ArrayList<PnHbncVisitRecordsModel.Visit_Records> mhealthRecordList;
    private LayoutInflater inflater;
    FragmentActivity activity;

    TextView txt_due_date,txt_provided_date,txt_hbnc_visit_num, txt_place,txt_weight,txt_temp,txt_umbilical_tump,txt_cry,txt_eye,txt_skin,txt_breat_feeding,txt_reasons,txt_out_come,
            txt_mother_any_complient,txt_mother_bp,txt_mother_pluse_rate,txt_mother_temp,txt_mother_epistomy_tear_suture,txt_mother_pv_discharge,txt_mother_breast_feeding,txt_mother_reasons,
            txt_mother_breast_examination,txt_mother_out_come;

    public PNHBNCVisitRecordsAdapter(FragmentActivity activity, ArrayList<PnHbncVisitRecordsModel.Visit_Records> mhealthRecordList) {
        this.activity = activity;
        this.mhealthRecordList = mhealthRecordList;
        inflater    = LayoutInflater.from(activity);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        Log.d("instantiateItem",position+"");
        View healthRecordLayout = inflater.inflate(R.layout.item_pn_hbnc_visit, view, false);
        mPnHbncVisitRecordsModel   =mhealthRecordList.get(position);
        initUI(healthRecordLayout);
        setValuetoUI(mPnHbncVisitRecordsModel);
        view.addView(healthRecordLayout);

        return healthRecordLayout;
    }

    private void setValuetoUI(PnHbncVisitRecordsModel.Visit_Records mhealthRecordResponseModel) {

        if(mhealthRecordResponseModel.getPnDueDate().equalsIgnoreCase("null")){
            txt_due_date.setText("-");
        }else{
            txt_due_date.setText(mhealthRecordResponseModel.getPnDueDate());
        }
        if(mhealthRecordResponseModel.getPnCareProvidedDate().equalsIgnoreCase("null")){
            txt_provided_date.setText("-");
        }else{
            txt_provided_date.setText(mhealthRecordResponseModel.getPnCareProvidedDate());
        }
        if(mhealthRecordResponseModel.getPnVisitNo().equalsIgnoreCase("null")){
            txt_hbnc_visit_num.setText("-");
        }else{
            txt_hbnc_visit_num.setText(mhealthRecordResponseModel.getPnVisitNo());
        }
        if(mhealthRecordResponseModel.getPnPlace().equalsIgnoreCase("null")){
            txt_place.setText("-");
        }else{
            txt_place.setText(mhealthRecordResponseModel.getPnPlace());
        }
        if(mhealthRecordResponseModel.getPnAnyComplaints().equalsIgnoreCase("null")){
            txt_weight.setText("-");
        }else{
            txt_weight.setText(mhealthRecordResponseModel.getPnAnyComplaints());
        }
        if(mhealthRecordResponseModel.getCTemp().equalsIgnoreCase("null")){
            txt_temp.setText("-");
        }else{
            txt_temp.setText(mhealthRecordResponseModel.getCTemp());
        }
        if(mhealthRecordResponseModel.getPnBPDiastolic().equalsIgnoreCase("null")&&mhealthRecordResponseModel.getPnBPSystolic().equalsIgnoreCase("null")){
            txt_mother_bp.setText("-");
        }else{
            txt_mother_bp.setText(mhealthRecordResponseModel.getPnBPDiastolic()+"/"+mhealthRecordResponseModel.getPnBPSystolic());
        }
        if(mhealthRecordResponseModel.getPnPulseRate().equalsIgnoreCase("null")){
            txt_mother_pluse_rate.setText("-");
        }else{
            txt_mother_pluse_rate.setText(mhealthRecordResponseModel.getPnPulseRate());
        }
        if(mhealthRecordResponseModel.getPnEpistomyTear().equalsIgnoreCase("null")){
            txt_mother_epistomy_tear_suture.setText("-");
        }else{
            txt_mother_epistomy_tear_suture.setText(mhealthRecordResponseModel.getPnEpistomyTear());
        }
        if(mhealthRecordResponseModel.getPnPVDischarge().equalsIgnoreCase("null")){
            txt_mother_pv_discharge.setText("-");
        }else{
            txt_mother_pv_discharge.setText(mhealthRecordResponseModel.getPnPVDischarge());
        }
        if(mhealthRecordResponseModel.getPnBreastFeeding().equalsIgnoreCase("null")){
            txt_mother_breast_feeding.setText("-");
        }else {
            txt_mother_breast_feeding.setText(mhealthRecordResponseModel.getPnBreastFeeding());
        }
        if(mhealthRecordResponseModel.getPnBreastFeedingReason().equalsIgnoreCase("null")){
            txt_mother_reasons.setText("-");
        }else {
            txt_mother_reasons.setText(mhealthRecordResponseModel.getPnBreastFeedingReason());
        }
        if(mhealthRecordResponseModel.getPnBreastExamination().equalsIgnoreCase("null")){
            txt_mother_breast_examination.setText("-");
        }else {
            txt_mother_breast_examination.setText(mhealthRecordResponseModel.getPnBreastExamination());
        }
        if(mhealthRecordResponseModel.getPnOutCome().equalsIgnoreCase("null")){
            txt_mother_out_come.setText("-");
        }else {
            txt_mother_out_come.setText(mhealthRecordResponseModel.getPnOutCome());
        }
        if(mhealthRecordResponseModel.getCWeight().equalsIgnoreCase("null")){
            txt_weight.setText("-");
        }else {
            txt_weight.setText(mhealthRecordResponseModel.getCWeight());
        }
        if(mhealthRecordResponseModel.getCUmbilicalStump().equalsIgnoreCase("null")){
            txt_umbilical_tump.setText("-");
        }else {
            txt_umbilical_tump.setText(mhealthRecordResponseModel.getCUmbilicalStump());
        }
        if(mhealthRecordResponseModel.getCCry().equalsIgnoreCase("null")){
            txt_cry.setText("-");
        }else {
            txt_cry.setText(mhealthRecordResponseModel.getCCry());
        }
        if(mhealthRecordResponseModel.getCEyes().equalsIgnoreCase("null")){
            txt_eye.setText("-");
        }else {
            txt_eye.setText(mhealthRecordResponseModel.getCEyes());
        }
        if(mhealthRecordResponseModel.getCSkin().equalsIgnoreCase("null")){
            txt_skin.setText("-");
        }else {
            txt_skin.setText(mhealthRecordResponseModel.getCSkin());
        }
        if(mhealthRecordResponseModel.getCBreastFeeding().equalsIgnoreCase("null")){
            txt_breat_feeding.setText("-");
        }else {
            txt_breat_feeding.setText(mhealthRecordResponseModel.getCBreastFeeding());
        }
        if(mhealthRecordResponseModel.getCBreastFeedingReason().equalsIgnoreCase("null")){
            txt_reasons.setText("-");
        }else {
            txt_reasons.setText(mhealthRecordResponseModel.getCBreastFeedingReason());
        }
        if(mhealthRecordResponseModel.getCOutCome().equalsIgnoreCase("null")){
            txt_out_come.setText("-");
        }else {
            txt_out_come.setText(mhealthRecordResponseModel.getCOutCome());
        }
        if(mhealthRecordResponseModel.getPnAnyComplaints().equalsIgnoreCase("null")){
            txt_mother_any_complient.setText("-");
        }else {
            txt_mother_any_complient.setText(mhealthRecordResponseModel.getPnAnyComplaints());
        }
        if(mhealthRecordResponseModel.getPnTemp().equalsIgnoreCase("null")){
            txt_mother_temp.setText("-");
        }else {
            txt_mother_temp.setText(mhealthRecordResponseModel.getPnTemp());
        }
    }

    private void initUI(View healthRecordLayout) {
        txt_due_date = healthRecordLayout.findViewById(R.id.txt_due_date);
        txt_provided_date = healthRecordLayout.findViewById(R.id.txt_provided_date);
        txt_hbnc_visit_num = healthRecordLayout.findViewById(R.id.txt_hbnc_visit_num);
        txt_place = healthRecordLayout.findViewById(R.id.txt_place);
        txt_weight = healthRecordLayout.findViewById(R.id.txt_weight);
        txt_temp = healthRecordLayout.findViewById(R.id.txt_temp);
        txt_umbilical_tump = healthRecordLayout.findViewById(R.id.txt_umbilical_tump);
        txt_cry = healthRecordLayout.findViewById(R.id.txt_cry);
        txt_eye = healthRecordLayout.findViewById(R.id.txt_eye);
        txt_skin = healthRecordLayout.findViewById(R.id.txt_skin);
        txt_breat_feeding = healthRecordLayout.findViewById(R.id.txt_breat_feeding);
        txt_reasons = healthRecordLayout.findViewById(R.id.txt_reasons);
        txt_out_come = healthRecordLayout.findViewById(R.id.txt_out_come);
        txt_mother_any_complient = healthRecordLayout.findViewById(R.id.txt_mother_any_complient);
        txt_mother_bp = healthRecordLayout.findViewById(R.id.txt_mother_bp);
        txt_mother_pluse_rate = healthRecordLayout.findViewById(R.id.txt_mother_pluse_rate);
        txt_mother_temp = healthRecordLayout.findViewById(R.id.txt_mother_temp);
        txt_mother_epistomy_tear_suture = healthRecordLayout.findViewById(R.id.txt_mother_epistomy_tear_suture);
        txt_mother_pv_discharge = healthRecordLayout.findViewById(R.id.txt_mother_pv_discharge);
        txt_mother_breast_feeding = healthRecordLayout.findViewById(R.id.txt_mother_breast_feeding);
        txt_mother_reasons = healthRecordLayout.findViewById(R.id.txt_mother_reasons);
        txt_mother_breast_examination = healthRecordLayout.findViewById(R.id.txt_mother_breast_examination);
        txt_mother_out_come = healthRecordLayout.findViewById(R.id.txt_mother_out_come);
    }

    @Override
    public int getCount() {
        Log.d("mhealthRecordList --->",""+mhealthRecordList.size());
        return mhealthRecordList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view.equals(object);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d("destroyItem --->",""+position);

        container.removeView((View) object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mhealthRecordList.get(position).getPnVisitNo();
    }
}
