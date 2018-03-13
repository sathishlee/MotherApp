package com.unicef.thaimai.motherapp.fragment;

import android.app.ProgressDialog;
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

import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.GetVisitHealthRecordsPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.adapter.HealthRecordsAdapter;
import com.unicef.thaimai.motherapp.adapter.ViewPagerAdapter;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.model.responsemodel.HealthRecordResponseModel;
import com.unicef.thaimai.motherapp.view.GetVisitHelthRecordsViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
//public class health_records extends Fragment implements TabLayout.OnTabSelectedListener {

import static android.content.Context.MODE_PRIVATE;


public class health_records extends Fragment implements GetVisitHelthRecordsViews  {
    Button btn_primary_report, btn_view_report;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    PreferenceData preferenceData;
    SharedPreferences.Editor editor;
    ProgressDialog pDialog;

    GetVisitHealthRecordsPresenter gVHRecordsPresenteer;
    HealthRecordResponseModel.Visit_Records mhealthRecordResponseModel;
    ArrayList<HealthRecordResponseModel.Visit_Records> mhealthRecordList;

    HealthRecordsAdapter hAdapter;
    Button btn_primary;




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


        return view;
    }



    private void initUI(View view) {
        getActivity().setTitle("Health Records");
        preferenceData = new PreferenceData(getActivity());
        editor = getActivity().getSharedPreferences(AppConstants.PREF_NAME, MODE_PRIVATE).edit();
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        gVHRecordsPresenteer = new GetVisitHealthRecordsPresenter(getActivity(), this);
        gVHRecordsPresenteer.getAllVistHeathRecord(preferenceData.getPicmeId(), "1");
        mhealthRecordList = new ArrayList<>();
        viewPager = view.findViewById(R.id.hre_viewpager);
        setupViewPager(viewPager);
        tabLayout = view.findViewById(R.id.hre_tabs);
        tabLayout.setupWithViewPager(viewPager);
        btn_primary_report = (Button) view.findViewById(R.id.btn_primary_report);
        btn_view_report = (Button) view.findViewById(R.id.btn_view_report);

    }


    private void setupViewPager(ViewPager viewPager) {
        hAdapter =new HealthRecordsAdapter(getActivity(),mhealthRecordList);
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
    public void getVisitHealthRecordsSuccess(String healthRecordResponseModel) {
        Log.d(health_records.class.getSimpleName(), "--->healthRecordResponseModel" + healthRecordResponseModel);

        try {
            JSONObject mJsnobject = new JSONObject(healthRecordResponseModel);
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getVisitHealthRecordsFailiur(String errorMsg) {
        Log.d(health_records.class.getSimpleName(), "--->errorMsg" + errorMsg);

    }




}