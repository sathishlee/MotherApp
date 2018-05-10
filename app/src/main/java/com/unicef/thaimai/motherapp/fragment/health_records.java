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
import android.widget.TextView;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.Interface.TypeOfHealthRecords;
import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.GetVisitHealthRecordsPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.activity.PrimaryRegisterView;
import com.unicef.thaimai.motherapp.activity.ViewReportsActivity;
import com.unicef.thaimai.motherapp.adapter.HealthRecordsAdapter;
import com.unicef.thaimai.motherapp.adapter.ViewPagerAdapter;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.model.responsemodel.HealthRecordResponseModel;
import com.unicef.thaimai.motherapp.view.GetVisitHelthRecordsViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
//public class health_records extends Fragment implements TabLayout.OnTabSelectedListener {

import static android.content.Context.MODE_PRIVATE;


public class health_records extends Fragment implements GetVisitHelthRecordsViews, View.OnClickListener, TypeOfHealthRecords {
    Button btn_primary_report, btn_view_report;
    static LinearLayout llClickPickMeVisit, llClickOtherVisit, ll_click_visit_view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    PreferenceData preferenceData;
    SharedPreferences.Editor editor;
    ProgressDialog pDialog;
    GetVisitHealthRecordsPresenter gVHRecordsPresenteer;
    HealthRecordResponseModel.Visit_Records mhealthRecordResponseModel;
    ArrayList<HealthRecordResponseModel.Visit_Records> mhealthRecordList;
    HealthRecordsAdapter hAdapter;
    TextView txt_visit_type;

    TextView txt_no_records;


    public static health_records newInstance() {
        health_records fragment = new health_records();
        return fragment;
    }
//change
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_health_records, container, false);
        initUI(view);
        onClickListner();
        return view;
    }

    private void onClickListner() {
        llClickPickMeVisit.setOnClickListener(this);
        llClickOtherVisit.setOnClickListener(this);
        btn_primary_report.setOnClickListener(this);
        btn_view_report.setOnClickListener(this);
    }

    private void initUI(View view) {
        getActivity().setTitle("Health Records");
        preferenceData = new PreferenceData(getActivity());
        editor = getActivity().getSharedPreferences(AppConstants.PREF_NAME, MODE_PRIVATE).edit();
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");

        txt_visit_type = view.findViewById(R.id.txt_visit_type);

        gVHRecordsPresenteer = new GetVisitHealthRecordsPresenter(getActivity(), this);
//        gVHRecordsPresenteer.getAllVistHeathRecord(Apiconstants.POST_VIST_HEALTH_RECORD_PICME,preferenceData.getPicmeId(), preferenceData.getMId());
        gVHRecordsPresenteer.getAllVistHeathRecord(Apiconstants.POST_VIST_HEALTH_RECORD,preferenceData.getPicmeId(), preferenceData.getMId());

        mhealthRecordList = new ArrayList<>();
        viewPager = view.findViewById(R.id.hre_viewpager);
        setupViewPager(viewPager);
        tabLayout = view.findViewById(R.id.hre_tabs);
        tabLayout.setupWithViewPager(viewPager);

        llClickPickMeVisit = view.findViewById(R.id.ll_click_pickme_visit);
        ll_click_visit_view = view.findViewById(R.id.ll_click_visit_view);

        txt_no_records = view.findViewById(R.id.txt_no_records);

        llClickOtherVisit = view.findViewById(R.id.ll_click_other_visit);
        btn_primary_report = (Button) view.findViewById(R.id.btn_primary_report);
        btn_view_report = (Button) view.findViewById(R.id.btn_view_report);

    }


    private void setupViewPager(ViewPager viewPager) {
        hAdapter =new HealthRecordsAdapter(getActivity(),mhealthRecordList,this);
        viewPager.setOffscreenPageLimit(mhealthRecordList.size());
        viewPager.setAdapter(hAdapter);
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
    public void onDestroy(){
        super.onDestroy();
        if (pDialog!=null && pDialog.isShowing() ){
            pDialog.cancel();
        }
    }


    @Override
    public void getVisitHealthRecordsSuccess(String healthRecordResponseModel) {
        Log.d(health_records.class.getSimpleName(), "--->healthRecordResponseModel" + healthRecordResponseModel);

        try {
            JSONObject mJsnobject = new JSONObject(healthRecordResponseModel);
            String status = mJsnobject.getString("status");
            String message = mJsnobject.getString("message");
//            Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
            if(status.equalsIgnoreCase("1")){
                ll_click_visit_view.setVisibility(View.VISIBLE);
                txt_no_records.setVisibility(View.GONE);
                JSONArray jsonArray = mJsnobject.getJSONArray("Visit_Records");
                for (int i = 0; i < jsonArray.length(); i++) {
                    mhealthRecordResponseModel = new HealthRecordResponseModel.Visit_Records();

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    mhealthRecordResponseModel.setVDate(jsonObject.getString("vDate"));
                    mhealthRecordResponseModel.setVFacility(jsonObject.getString("vFacility"));
//                mhealthRecordResponseModel.setMLongitude(jsonObject.getString("mLongitude"));
//                mhealthRecordResponseModel.setMLatitude(jsonObject.getString("mLatitude"));
                    mhealthRecordResponseModel.setMotherStatus(jsonObject.getString("motherStatus"));
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
                    mhealthRecordList.add(mhealthRecordResponseModel);
                    hAdapter.notifyDataSetChanged();
                }
            }else{
                ll_click_visit_view.setVisibility(View.GONE);
                txt_no_records.setVisibility(View.VISIBLE);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getVisitHealthRecordsFailiur(String errorMsg) {
        Log.d(health_records.class.getSimpleName(), "--->errorMsg" + errorMsg);

    }

    @Override
    public void getPNHBNCVisitRecordsSuccess(String healthRecordResponseModel) {

    }

    @Override
    public void getPNHBNCVisitRecordsFailiur(String errorMsg) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.ll_click_pickme_visit:
//                mhealthRecordList.clear();
//                gVHRecordsPresenteer.getAllVistHeathRecord(Apiconstants.POST_VIST_HEALTH_RECORD_PICME,preferenceData.getPicmeId(), "1");
//                break;
//            case R.id.ll_click_other_visit:
//                mhealthRecordList.clear();
//                gVHRecordsPresenteer.getAllVistHeathRecord(Apiconstants.POST_VIST_HEALTH_RECORD,preferenceData.getPicmeId(), "1");
//                break;
            case R.id.btn_primary_report:
                startActivity(new Intent(getActivity().getApplicationContext(), PrimaryRegisterView.class));
                break;
            case R.id.btn_view_report:
                startActivity(new Intent(getActivity().getApplicationContext(), ViewReportsActivity.class));
                break;
        }
    }

    @Override
    public void ispickme(boolean isPickme) {
        if (isPickme){
//            llClickPickMeVisit.setVisibility(View.VISIBLE);
//            llClickOtherVisit.setVisibility(View.GONE);
            txt_visit_type.setText("Picme Visit");
        }else {
//            llClickPickMeVisit.setVisibility(View.GONE);
//            llClickOtherVisit.setVisibility(View.VISIBLE);
            txt_visit_type.setText("Other Visit");
        }
    }
}