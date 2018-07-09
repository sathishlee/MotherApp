package com.unicef.thaimai.motherapp.fragment;

import android.app.AlertDialog;
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
import com.unicef.thaimai.motherapp.app.RealmController;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.model.responsemodel.HealthRecordResponseModel;
import com.unicef.thaimai.motherapp.realmDbModelClass.ANMotherVisitRealmModel;
import com.unicef.thaimai.motherapp.realmDbModelClass.ImmunizationRealmModel;
import com.unicef.thaimai.motherapp.utility.CheckNetwork;
import com.unicef.thaimai.motherapp.view.GetVisitHelthRecordsViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
//public class health_records extends Fragment implements TabLayout.OnTabSelectedListener {

import io.realm.Realm;
import io.realm.RealmResults;

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

    Realm realm;
    CheckNetwork checkNetwork;
    boolean isoffline = false;


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
        realm = RealmController.with(getActivity()).getRealm();
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
        checkNetwork = new CheckNetwork(getActivity());
        preferenceData = new PreferenceData(getActivity());
        editor = getActivity().getSharedPreferences(AppConstants.PREF_NAME, MODE_PRIVATE).edit();
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");

        txt_visit_type = view.findViewById(R.id.txt_visit_type);

        gVHRecordsPresenteer = new GetVisitHealthRecordsPresenter(getActivity(), this);
//        gVHRecordsPresenteer.getAllVistHeathRecord(Apiconstants.POST_VIST_HEALTH_RECORD_PICME,preferenceData.getPicmeId(), preferenceData.getMId());
        if (checkNetwork.isNetworkAvailable()) {
            gVHRecordsPresenteer.getAllVistHeathRecord(Apiconstants.POST_VIST_HEALTH_RECORD, preferenceData.getPicmeId(), preferenceData.getMId());
        }else {
            isoffline = true;
        }
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

        if (isoffline) {
            motherOfflineRecords();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Record Not Found");
            builder.create();
        }

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
                JSONArray jsonArray = mJsnobject.getJSONArray("Visit_Records");



                if (jsonArray.length() != 0) {

                    RealmResults<ANMotherVisitRealmModel> anMotherVisitRealmModels = realm.where(ANMotherVisitRealmModel.class).findAll();
                    Log.e("Realm size ---->", anMotherVisitRealmModels.size() + "");
                    if (anMotherVisitRealmModels.size()!=0){
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.delete(ANMotherVisitRealmModel.class);
                            }
                        });
                    }

                    ll_click_visit_view.setVisibility(View.VISIBLE);
                    txt_no_records.setVisibility(View.GONE);
                    realm.beginTransaction();
                    ANMotherVisitRealmModel anMotherVisitRealmModel = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        anMotherVisitRealmModel = realm.createObject(ANMotherVisitRealmModel.class);

                        mhealthRecordResponseModel = new HealthRecordResponseModel.Visit_Records();

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        anMotherVisitRealmModel.setVDate(jsonObject.getString("vDate"));
                        anMotherVisitRealmModel.setVFacility(jsonObject.getString("vFacility"));
//                mhealthRecordResponseModel.setMLongitude(jsonObject.getString("mLongitude"));
//                mhealthRecordResponseModel.setMLatitude(jsonObject.getString("mLatitude"));
                        anMotherVisitRealmModel.setMotherStatus(jsonObject.getString("motherStatus"));
                        anMotherVisitRealmModel.setMotherCloseDate(jsonObject.getString("motherCloseDate"));
                        anMotherVisitRealmModel.setMRiskStatus(jsonObject.getString("mRiskStatus"));
                        anMotherVisitRealmModel.setMEDD(jsonObject.getString("mEDD"));
                        anMotherVisitRealmModel.setMLMP(jsonObject.getString("mLMP"));
                        anMotherVisitRealmModel.setPhcId(jsonObject.getString("phcId"));
                        anMotherVisitRealmModel.setAwwId(jsonObject.getString("awwId"));
                        anMotherVisitRealmModel.setVhnId(jsonObject.getString("vhnId"));
                        anMotherVisitRealmModel.setMasterId(jsonObject.getString("masterId"));
                        anMotherVisitRealmModel.setVTSH(jsonObject.getString("vTSH"));
                        anMotherVisitRealmModel.setUsgPlacenta(jsonObject.getString("usgPlacenta"));
                        anMotherVisitRealmModel.setUsgLiquor(jsonObject.getString("usgLiquor"));
                        anMotherVisitRealmModel.setUsgGestationSac(jsonObject.getString("usgGestationSac"));
                        anMotherVisitRealmModel.setUsgFetus(jsonObject.getString("usgFetus"));
                        anMotherVisitRealmModel.setVAlbumin(jsonObject.getString("vAlbumin"));
                        anMotherVisitRealmModel.setVUrinSugar(jsonObject.getString("vUrinSugar"));
                        anMotherVisitRealmModel.setVGTT(jsonObject.getString("vGTT"));
                        anMotherVisitRealmModel.setVPPBS(jsonObject.getString("vPPBS"));
                        anMotherVisitRealmModel.setVFBS(jsonObject.getString("vFBS"));
                        anMotherVisitRealmModel.setVRBS(jsonObject.getString("vRBS"));
                        anMotherVisitRealmModel.setVFHS(jsonObject.getString("vFHS"));
                        anMotherVisitRealmModel.setVHemoglobin(jsonObject.getString("vHemoglobin"));
                        anMotherVisitRealmModel.setVBodyTemp(jsonObject.getString("vBodyTemp"));
                        anMotherVisitRealmModel.setVPedalEdemaPresent(jsonObject.getString("vPedalEdemaPresent"));
                        anMotherVisitRealmModel.setVFundalHeight(jsonObject.getString("vFundalHeight"));
                        anMotherVisitRealmModel.setVEnterWeight(jsonObject.getString("vEnterWeight"));
                        anMotherVisitRealmModel.setVEnterPulseRate(jsonObject.getString("vEnterPulseRate"));
                        anMotherVisitRealmModel.setVClinicalBPDiastolic(jsonObject.getString("vClinicalBPDiastolic"));
                        anMotherVisitRealmModel.setVClinicalBPSystolic(jsonObject.getString("vClinicalBPSystolic"));
//                mhealthRecordResponseModel.setVAnyComplaintsOthers(jsonObject.getString("vAnyComplaintsOthers"));
                        anMotherVisitRealmModel.setVAnyComplaints(jsonObject.getString("vAnyComplaints"));
//                mhealthRecordResponseModel.setVFacilityOthers(jsonObject.getString("vFacilityOthers"));
                        anMotherVisitRealmModel.setVtypeOfVisit(jsonObject.getString("vtypeOfVisit"));
                        anMotherVisitRealmModel.setPicmeId(jsonObject.getString("picmeId"));
                        anMotherVisitRealmModel.setMid(jsonObject.getString("mid"));
                        anMotherVisitRealmModel.setVisitId(jsonObject.getString("visitId"));
                        anMotherVisitRealmModel.setVDate(jsonObject.getString("vDate"));
                        anMotherVisitRealmModel.setVid(jsonObject.getString("vid"));
                        /*mhealthRecordList.add(mhealthRecordResponseModel);
                        hAdapter.notifyDataSetChanged();*/
                    }
                    realm.commitTransaction();       //create or open
                }else{
                    Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                    ll_click_visit_view.setVisibility(View.GONE);
                    txt_no_records.setVisibility(View.VISIBLE);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        motherOfflineRecords();
    }
    private void motherOfflineRecords() {
        realm.beginTransaction();
        RealmResults<ANMotherVisitRealmModel> anMotherVisitRealmModels = realm.where(ANMotherVisitRealmModel.class).findAll();
        Log.e("realm Size ->", anMotherVisitRealmModels.size() + "");

        for (int i = 0; i < anMotherVisitRealmModels.size(); i++) {
            mhealthRecordResponseModel = new HealthRecordResponseModel.Visit_Records();

            ANMotherVisitRealmModel anMotherVisitRealmModel = anMotherVisitRealmModels.get(i);

            mhealthRecordResponseModel.setVDate(anMotherVisitRealmModel.getVDate());
            mhealthRecordResponseModel.setVFacility(anMotherVisitRealmModel.getVFacility());
//                mhealthRecordResponseModel.setMLongitude(jsonObject.getString("mLongitude"));
//                mhealthRecordResponseModel.setMLatitude(jsonObject.getString("mLatitude"));
            mhealthRecordResponseModel.setMotherStatus(anMotherVisitRealmModel.getMotherStatus());
            mhealthRecordResponseModel.setMotherCloseDate(anMotherVisitRealmModel.getMotherCloseDate());
            mhealthRecordResponseModel.setMRiskStatus(anMotherVisitRealmModel.getMRiskStatus());
            mhealthRecordResponseModel.setMEDD(anMotherVisitRealmModel.getMEDD());
            mhealthRecordResponseModel.setMLMP(anMotherVisitRealmModel.getMLMP());
            mhealthRecordResponseModel.setPhcId(anMotherVisitRealmModel.getPhcId());
            mhealthRecordResponseModel.setAwwId(anMotherVisitRealmModel.getAwwId());
            mhealthRecordResponseModel.setVhnId(anMotherVisitRealmModel.getVhnId());
            mhealthRecordResponseModel.setMasterId(anMotherVisitRealmModel.getMasterId());
            mhealthRecordResponseModel.setVTSH(anMotherVisitRealmModel.getVTSH());
            mhealthRecordResponseModel.setUsgPlacenta(anMotherVisitRealmModel.getUsgPlacenta());
            mhealthRecordResponseModel.setUsgLiquor(anMotherVisitRealmModel.getUsgLiquor());
            mhealthRecordResponseModel.setUsgGestationSac(anMotherVisitRealmModel.getUsgGestationSac());
            mhealthRecordResponseModel.setUsgFetus(anMotherVisitRealmModel.getUsgFetus());
            mhealthRecordResponseModel.setVAlbumin(anMotherVisitRealmModel.getVAlbumin());
            mhealthRecordResponseModel.setVUrinSugar(anMotherVisitRealmModel.getVUrinSugar());
            mhealthRecordResponseModel.setVGTT(anMotherVisitRealmModel.getVGTT());
            mhealthRecordResponseModel.setVPPBS(anMotherVisitRealmModel.getVPPBS());
            mhealthRecordResponseModel.setVFBS(anMotherVisitRealmModel.getVFBS());
            mhealthRecordResponseModel.setVRBS(anMotherVisitRealmModel.getVRBS());
            mhealthRecordResponseModel.setVFHS(anMotherVisitRealmModel.getVFHS());
            mhealthRecordResponseModel.setVHemoglobin(anMotherVisitRealmModel.getVHemoglobin());
            mhealthRecordResponseModel.setVBodyTemp(anMotherVisitRealmModel.getVBodyTemp());
            mhealthRecordResponseModel.setVPedalEdemaPresent(anMotherVisitRealmModel.getVPedalEdemaPresent());
            mhealthRecordResponseModel.setVFundalHeight(anMotherVisitRealmModel.getVFundalHeight());
            mhealthRecordResponseModel.setVEnterWeight(anMotherVisitRealmModel.getVEnterWeight());
            mhealthRecordResponseModel.setVEnterPulseRate(anMotherVisitRealmModel.getVEnterPulseRate());
            mhealthRecordResponseModel.setVClinicalBPDiastolic(anMotherVisitRealmModel.getVClinicalBPDiastolic());
            mhealthRecordResponseModel.setVClinicalBPSystolic(anMotherVisitRealmModel.getVClinicalBPSystolic());
//                mhealthRecordResponseModel.setVAnyComplaintsOthers(jsonObject.getString("vAnyComplaintsOthers"));
            mhealthRecordResponseModel.setVAnyComplaints(anMotherVisitRealmModel.getVAnyComplaints());
//                mhealthRecordResponseModel.setVFacilityOthers(jsonObject.getString("vFacilityOthers"));
            mhealthRecordResponseModel.setVtypeOfVisit(anMotherVisitRealmModel.getVtypeOfVisit());
            mhealthRecordResponseModel.setPicmeId(anMotherVisitRealmModel.getPicmeId());
            mhealthRecordResponseModel.setMid(anMotherVisitRealmModel.getMid());
            mhealthRecordResponseModel.setVisitId(anMotherVisitRealmModel.getVisitId());
            mhealthRecordResponseModel.setVDate(anMotherVisitRealmModel.getVDate());
            mhealthRecordResponseModel.setVid(anMotherVisitRealmModel.getVid());

            mhealthRecordList.add(mhealthRecordResponseModel);
            hAdapter.notifyDataSetChanged();
        }
        realm.commitTransaction();
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