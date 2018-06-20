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
        txt_due_date.setText(mhealthRecordResponseModel.getPnDueDate());
        txt_provided_date.setText(mhealthRecordResponseModel.getPnCareProvidedDate());
        txt_hbnc_visit_num.setText(mhealthRecordResponseModel.getPnVisitNo());
        txt_place.setText(mhealthRecordResponseModel.getPnPlace());
        txt_weight.setText(mhealthRecordResponseModel.getPnAnyComplaints());
        txt_temp.setText(mhealthRecordResponseModel.getCTemp());
        txt_mother_bp.setText(mhealthRecordResponseModel.getPnBPDiastolic()+"/"+mhealthRecordResponseModel.getPnBPSystolic());
        txt_mother_pluse_rate.setText(mhealthRecordResponseModel.getPnPulseRate());
        txt_mother_epistomy_tear_suture.setText(mhealthRecordResponseModel.getPnEpistomyTear());
        txt_mother_pv_discharge.setText(mhealthRecordResponseModel.getPnPVDischarge());
        txt_mother_breast_feeding.setText(mhealthRecordResponseModel.getPnBreastFeeding());
        txt_mother_reasons.setText(mhealthRecordResponseModel.getPnBreastFeedingReason());
        txt_mother_breast_examination.setText(mhealthRecordResponseModel.getPnBreastExamination());
        txt_mother_out_come.setText(mhealthRecordResponseModel.getPnOutCome());

        txt_weight.setText(mhealthRecordResponseModel.getCWeight());
        txt_umbilical_tump.setText(mhealthRecordResponseModel.getCUmbilicalStump());
        txt_cry.setText(mhealthRecordResponseModel.getCCry());
        txt_eye.setText(mhealthRecordResponseModel.getCEyes());
        txt_skin.setText(mhealthRecordResponseModel.getCSkin());
        txt_breat_feeding.setText(mhealthRecordResponseModel.getCBreastFeeding());
        txt_reasons.setText(mhealthRecordResponseModel.getCBreastFeedingReason());
        txt_out_come.setText(mhealthRecordResponseModel.getCOutCome());
        txt_mother_any_complient.setText(mhealthRecordResponseModel.getPnAnyComplaints());
        txt_mother_temp.setText(mhealthRecordResponseModel.getPnTemp());
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
