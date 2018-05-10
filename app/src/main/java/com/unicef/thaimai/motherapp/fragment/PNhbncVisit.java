package com.unicef.thaimai.motherapp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.GetVisitHealthRecordsPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.activity.DeliveryDetailsView;
import com.unicef.thaimai.motherapp.adapter.HealthRecordsAdapter;
import com.unicef.thaimai.motherapp.adapter.PNHBNCVisitRecordsAdapter;
import com.unicef.thaimai.motherapp.adapter.ViewPageAdapterPN;
import com.unicef.thaimai.motherapp.adapter.ViewPagerAdapter;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.model.responsemodel.HealthRecordResponseModel;
import com.unicef.thaimai.motherapp.model.responsemodel.PnHbncVisitRecordsModel;
import com.unicef.thaimai.motherapp.view.GetVisitHelthRecordsViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PNhbncVisit extends Fragment implements GetVisitHelthRecordsViews, View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPageAdapterPN adapter;

    TextView txt_no_records;

    Button btn_delivery_reports, btn_visit_reports;
    static LinearLayout ll_click_visit_view;
    PreferenceData preferenceData;
    SharedPreferences.Editor editor;
    ProgressDialog pDialog;
    GetVisitHealthRecordsPresenter gVHRecordsPresenteer;
    //    HealthRecordResponseModel.Visit_Records mhealthRecordResponseModel;
    PnHbncVisitRecordsModel.Visit_Records mPnHbncVisitRecordsModel;
    ArrayList<PnHbncVisitRecordsModel.Visit_Records> mPnHbncVisitRecordsList;
    HealthRecordsAdapter hAdapter;

    PNHBNCVisitRecordsAdapter pnhbncVisitRecordsAdapter;

    public static PNhbncVisit newInstance()
    {
        PNhbncVisit fragment = new PNhbncVisit();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view =  inflater.inflate(R.layout.layout_pn_hbnc_visit, container, false);
        initUI(view);
        onClickListner();

        return  view;
    }

    private void initUI(View view) {
        getActivity().setTitle("PN-HBNC Details");
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        preferenceData = new PreferenceData(getActivity());

        gVHRecordsPresenteer = new GetVisitHealthRecordsPresenter(getActivity(), this);
        gVHRecordsPresenteer.getPN_HBNC_VisitRecord(Apiconstants.POST_PN_HBNC_VIST_RECORD,preferenceData.getPicmeId(), preferenceData.getMId());

        mPnHbncVisitRecordsList = new ArrayList<>();

        viewPager = view.findViewById(R.id.pn_viewpager);
        setupViewPager(viewPager);
        tabLayout = view.findViewById(R.id.pn_tabs);
        tabLayout.setupWithViewPager(viewPager);
        txt_no_records = view.findViewById(R.id.txt_no_records);

        ll_click_visit_view = view.findViewById(R.id.ll_click_visit_view);

        btn_delivery_reports = (Button) view.findViewById(R.id.btn_delivery_reports);
        btn_visit_reports = (Button) view.findViewById(R.id.btn_visit_reports);


    }

    private void onClickListner() {

        btn_delivery_reports.setOnClickListener(this);
        btn_visit_reports.setOnClickListener(this);
    }

    private void setupViewPager(ViewPager viewPager) {

        pnhbncVisitRecordsAdapter =new PNHBNCVisitRecordsAdapter(getActivity(),mPnHbncVisitRecordsList);
        viewPager.setOffscreenPageLimit(mPnHbncVisitRecordsList.size());
        viewPager.setAdapter(pnhbncVisitRecordsAdapter);



//        adapter = new ViewPageAdapterPN(getActivity().getSupportFragmentManager());
//        adapter.addFragment(new PNoneFragment(), "Present Status\nVISIT 5");
//        adapter.addFragment(new PNtwoFragment(), "VISIT 4");
//        adapter.addFragment(new PNoneFragment(), "VISIT 3");
//        adapter.addFragment(new PNtwoFragment(), "VISIT 2");
//        adapter.addFragment(new PNoneFragment(), "VISIT 1");
//        viewPager.setAdapter(adapter);
    }

    @Override
    public void showProgress() {
        pDialog.show();
    }

    @Override
    public void hideProgress() {
        pDialog.hide();
    }

    @Override
    public void getVisitHealthRecordsSuccess(String healthRecordResponseModel) {
        Log.d(health_records.class.getSimpleName(), "--->healthRecordResponseModel" + healthRecordResponseModel);

    }

    @Override
    public void getVisitHealthRecordsFailiur(String errorMsg) {
        Log.d(health_records.class.getSimpleName(), "--->errorMsg" + errorMsg);

    }

    @Override
    public void getPNHBNCVisitRecordsSuccess(String response) {
        try {
            JSONObject mJsnobject = new JSONObject(response);
            String status = mJsnobject.getString("status");
            String message = mJsnobject.getString("message");
            Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
            if (status.equalsIgnoreCase("1")){
                txt_no_records.setVisibility(View.GONE);
                JSONArray jsonArray = mJsnobject.getJSONArray("Visit_Records");
                for (int i = 0; i < jsonArray.length(); i++) {
                    mPnHbncVisitRecordsModel = new PnHbncVisitRecordsModel.Visit_Records();

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    mPnHbncVisitRecordsModel.setPnId(jsonObject.getString("pnId"));
                    mPnHbncVisitRecordsModel.setMid(jsonObject.getString("mid"));
                    mPnHbncVisitRecordsModel.setPicmeId(jsonObject.getString("picmeId"));
                    mPnHbncVisitRecordsModel.setPnVisitNo(jsonObject.getString("pnVisitNo"));
                    mPnHbncVisitRecordsModel.setPnDueDate(jsonObject.getString("pnDueDate"));
                    mPnHbncVisitRecordsModel.setPnCareProvidedDate(jsonObject.getString("pnCareProvidedDate"));
                    mPnHbncVisitRecordsModel.setPnPlace(jsonObject.getString("pnPlace"));
                    mPnHbncVisitRecordsModel.setPnAnyComplaints(jsonObject.getString("pnAnyComplaints"));
                    mPnHbncVisitRecordsModel.setPnBPSystolic(jsonObject.getString("pnBPSystolic"));
                    mPnHbncVisitRecordsModel.setPnPulseRate(jsonObject.getString("pnPulseRate"));
                    mPnHbncVisitRecordsModel.setPnTemp(jsonObject.getString("pnTemp"));
                    mPnHbncVisitRecordsModel.setPnEpistomyTear(jsonObject.getString("pnEpistomyTear"));
                    mPnHbncVisitRecordsModel.setPnPVDischarge(jsonObject.getString("pnPVDischarge"));
                    mPnHbncVisitRecordsModel.setPnBreastFeedingReason(jsonObject.getString("pnBreastFeedingReason"));
                    mPnHbncVisitRecordsModel.setPnBreastExamination(jsonObject.getString("pnBreastExamination"));
                    mPnHbncVisitRecordsModel.setPnOutCome(jsonObject.getString("pnOutCome"));
                    mPnHbncVisitRecordsModel.setCWeight(jsonObject.getString("cWeight"));
                    mPnHbncVisitRecordsModel.setCTemp(jsonObject.getString("cTemp"));
                    mPnHbncVisitRecordsModel.setCUmbilicalStump(jsonObject.getString("cUmbilicalStump"));
                    mPnHbncVisitRecordsModel.setCCry(jsonObject.getString("cCry"));
                    mPnHbncVisitRecordsModel.setCEyes(jsonObject.getString("cEyes"));
                    mPnHbncVisitRecordsModel.setCSkin(jsonObject.getString("cSkin"));
                    mPnHbncVisitRecordsModel.setCBreastFeeding(jsonObject.getString("cBreastFeeding"));
                    mPnHbncVisitRecordsModel.setCBreastFeedingReason(jsonObject.getString("cBreastFeedingReason"));
                    mPnHbncVisitRecordsModel.setCOutCome(jsonObject.getString("cOutCome"));

                /*mPnHbncVisitRecordsModel.setVDate(jsonObject.getString("vDate"));
                mPnHbncVisitRecordsModel.setVFacility(jsonObject.getString("vFacility"));
//                mhealthRecordResponseModel.setMLongitude(jsonObject.getString("mLongitude"));
//                mhealthRecordResponseModel.setMLatitude(jsonObject.getString("mLatitude"));
                mPnHbncVisitRecordsModel.setMotherStatus(jsonObject.getString("motherStatus"));
                mhealthRecordResponseModel.setMotherCloseDate(jsonObject.getString("motherCloseDate"));
                mhealthRecordResponseModel.setMRiskStatus(jsonObject.getString("mRiskStatus"));
                mhealthRecordResponseModel.setMEDD(jsonObject.getString("mEDD"));
                mhealthRecordResponseModel.setMLMP(jsonObject.getString("mLMP"));
                mhealthRecordResponseModel.setPhcId(jsonObject.getString("phcId"));
                mhealthRecordResponseModel.setAwwId(jsonObject.getString("awwId"));
                mhealthRecordResponseModel.setVhnId(jsonObject.getString("vhnId"));
                mhealthRecordResponseModel.setMasterId(jsonObject.getString("masterId"));
                mhealthRecordResponseModel.setVTSH(jsonObject.getString("vTSH"));
                mhealthRecordResponseModel.setUsgPlacenta(jsonObject.getString("usgPlacenta"));
                mhealthRecordResponseModel.setUsgLiquor(jsonObject.getString("usgLiquor"));
                mhealthRecordResponseModel.setUsgGestationSac(jsonObject.getString("usgGestationSac"));
                mhealthRecordResponseModel.setUsgFetus(jsonObject.getString("usgFetus"));
                mhealthRecordResponseModel.setVAlbumin(jsonObject.getString("vAlbumin"));
                mhealthRecordResponseModel.setVUrinSugar(jsonObject.getString("vUrinSugar"));
                mhealthRecordResponseModel.setVGTT(jsonObject.getString("vGTT"));
                mhealthRecordResponseModel.setVPPBS(jsonObject.getString("vPPBS"));
                mhealthRecordResponseModel.setVFBS(jsonObject.getString("vFBS"));
                mhealthRecordResponseModel.setVRBS(jsonObject.getString("vRBS"));
                mhealthRecordResponseModel.setVFHS(jsonObject.getString("vFHS"));
                mhealthRecordResponseModel.setVHemoglobin(jsonObject.getString("vHemoglobin"));
                mhealthRecordResponseModel.setVBodyTemp(jsonObject.getString("vBodyTemp"));
                mhealthRecordResponseModel.setVPedalEdemaPresent(jsonObject.getString("vPedalEdemaPresent"));
                mhealthRecordResponseModel.setVFundalHeight(jsonObject.getString("vFundalHeight"));
                mhealthRecordResponseModel.setVEnterWeight(jsonObject.getString("vEnterWeight"));
                mhealthRecordResponseModel.setVEnterPulseRate(jsonObject.getString("vEnterPulseRate"));
                mhealthRecordResponseModel.setVClinicalBPDiastolic(jsonObject.getString("vClinicalBPDiastolic"));
                mhealthRecordResponseModel.setVClinicalBPSystolic(jsonObject.getString("vClinicalBPSystolic"));
//                mhealthRecordResponseModel.setVAnyComplaintsOthers(jsonObject.getString("vAnyComplaintsOthers"));
                mhealthRecordResponseModel.setVAnyComplaints(jsonObject.getString("vAnyComplaints"));
//                mhealthRecordResponseModel.setVFacilityOthers(jsonObject.getString("vFacilityOthers"));
                mhealthRecordResponseModel.setVtypeOfVisit(jsonObject.getString("vtypeOfVisit"));
                mhealthRecordResponseModel.setPicmeId(jsonObject.getString("picmeId"));
                mhealthRecordResponseModel.setMid(jsonObject.getString("mid"));
                mhealthRecordResponseModel.setVisitId(jsonObject.getString("visitId"));
                mhealthRecordResponseModel.setVDate(jsonObject.getString("vDate"));
                mhealthRecordResponseModel.setVid(jsonObject.getString("vid"));
                mhealthRecordList.add(mPnHbncVisitRecordsModel);*/

                    mPnHbncVisitRecordsList.add(mPnHbncVisitRecordsModel);
                    pnhbncVisitRecordsAdapter.notifyDataSetChanged();
                }
            }else{
                txt_no_records.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getPNHBNCVisitRecordsFailiur(String errorMsg) {
        Log.d(health_records.class.getSimpleName(), "--->errorMsg" + errorMsg);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_delivery_reports:
                startActivity(new Intent(getActivity().getApplicationContext(), DeliveryDetailsView.class));
                break;
            case R.id.btn_visit_reports:
                break;
        }
    }
}