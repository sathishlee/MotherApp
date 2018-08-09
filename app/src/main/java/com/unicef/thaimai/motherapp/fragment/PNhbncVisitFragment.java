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

import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.GetVisitHealthRecordsPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.activity.DeliveryDetailsView;
import com.unicef.thaimai.motherapp.activity.PNViewReportsActivity;
import com.unicef.thaimai.motherapp.adapter.HealthRecordsAdapter;
import com.unicef.thaimai.motherapp.adapter.PNHBNCVisitRecordsAdapter;
import com.unicef.thaimai.motherapp.adapter.ViewPageAdapterPN;
import com.unicef.thaimai.motherapp.app.RealmController;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.model.responsemodel.PnHbncVisitRecordsModel;
import com.unicef.thaimai.motherapp.realmDbModelClass.PNMotherRealmModel;
import com.unicef.thaimai.motherapp.utility.CheckNetwork;
import com.unicef.thaimai.motherapp.view.GetVisitHelthRecordsViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


public class PNhbncVisitFragment extends Fragment implements GetVisitHelthRecordsViews, View.OnClickListener {
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

    Realm realm;
    CheckNetwork checkNetwork;
    boolean isoffline = false;

    public static PNhbncVisitFragment newInstance() {
        PNhbncVisitFragment fragment = new PNhbncVisitFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_pn_hbnc_visit, container, false);
        realm = RealmController.with(getActivity()).getRealm();
        initUI(view);
        onClickListner();

        return view;
    }

    private void initUI(View view) {
        getActivity().setTitle("PN-HBNC Details");
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        checkNetwork = new CheckNetwork(getActivity());
        preferenceData = new PreferenceData(getActivity());

        gVHRecordsPresenteer = new GetVisitHealthRecordsPresenter(getActivity(), this);
        if (checkNetwork.isNetworkAvailable()) {
            gVHRecordsPresenteer.getPN_HBNC_VisitRecord(Apiconstants.POST_PN_HBNC_VIST_RECORD, preferenceData.getPicmeId(), preferenceData.getMId());
        } else {
            isoffline = true;
        }
        mPnHbncVisitRecordsList = new ArrayList<>();

        viewPager = view.findViewById(R.id.pn_viewpager);
        setupViewPager(viewPager);
        tabLayout = view.findViewById(R.id.pn_tabs);
        tabLayout.setupWithViewPager(viewPager);
        txt_no_records = view.findViewById(R.id.txt_no_records);

        ll_click_visit_view = view.findViewById(R.id.ll_click_visit_view);

        btn_delivery_reports = (Button) view.findViewById(R.id.btn_delivery_reports);
        btn_visit_reports = (Button) view.findViewById(R.id.btn_visit_reports);

        if (isoffline) {
            motherOfflineRecords();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Record Not Found");
            builder.create();
        }

    }


    private void onClickListner() {

        btn_delivery_reports.setOnClickListener(this);
        btn_visit_reports.setOnClickListener(this);
    }

    private void setupViewPager(ViewPager viewPager) {

        pnhbncVisitRecordsAdapter = new PNHBNCVisitRecordsAdapter(getActivity(), mPnHbncVisitRecordsList);
        viewPager.setOffscreenPageLimit(mPnHbncVisitRecordsList.size());
        viewPager.setAdapter(pnhbncVisitRecordsAdapter);

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
        Log.d(HealthRecordsFragment.class.getSimpleName(), "--->healthRecordResponseModel" + healthRecordResponseModel);

    }

    @Override
    public void getVisitHealthRecordsFailiur(String errorMsg) {
        Log.d(HealthRecordsFragment.class.getSimpleName(), "--->errorMsg" + errorMsg);

    }

    @Override
    public void getPNHBNCVisitRecordsSuccess(String response) {
        try {
            JSONObject mJsnobject = new JSONObject(response);
            String status = mJsnobject.getString("status");
            String message = mJsnobject.getString("message");
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            if (status.equalsIgnoreCase("1")) {
                JSONArray jsonArray = mJsnobject.getJSONArray("Visit_Records");
                RealmResults<PNMotherRealmModel> pnMotherRealmModels = realm.where(PNMotherRealmModel.class).findAll();
                Log.e("Realm size ---->", pnMotherRealmModels.size() + "");
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(PNMotherRealmModel.class);
                    }
                });
                if (jsonArray.length() != 0) {
                    txt_no_records.setVisibility(View.GONE);
                    realm.beginTransaction();
                    PNMotherRealmModel pnMotherRealmModel = null;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        pnMotherRealmModel = realm.createObject(PNMotherRealmModel.class);

                        mPnHbncVisitRecordsModel = new PnHbncVisitRecordsModel.Visit_Records();

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        pnMotherRealmModel.setPnId(jsonObject.getString("pnId"));
                        pnMotherRealmModel.setMid(jsonObject.getString("mid"));
                        pnMotherRealmModel.setPicmeId(jsonObject.getString("picmeId"));
                        pnMotherRealmModel.setPnVisitNo(jsonObject.getString("pnVisitNo"));
                        pnMotherRealmModel.setPnDueDate(jsonObject.getString("pnDueDate"));
                        pnMotherRealmModel.setPnCareProvidedDate(jsonObject.getString("pnCareProvidedDate"));
                        pnMotherRealmModel.setPnPlace(jsonObject.getString("pnPlace"));
                        pnMotherRealmModel.setPnAnyComplaints(jsonObject.getString("pnAnyComplaints"));
                        pnMotherRealmModel.setPnBPSystolic(jsonObject.getString("pnBPSystolic"));
                        pnMotherRealmModel.setPnBPDiastolic(jsonObject.getString("pnBPDiastolic"));
                        pnMotherRealmModel.setPnPulseRate(jsonObject.getString("pnPulseRate"));
                        pnMotherRealmModel.setPnTemp(jsonObject.getString("pnTemp"));
                        pnMotherRealmModel.setPnEpistomyTear(jsonObject.getString("pnEpistomyTear"));
                        pnMotherRealmModel.setPnPVDischarge(jsonObject.getString("pnPVDischarge"));
                        pnMotherRealmModel.setPnBreastFeeding(jsonObject.getString("pnBreastFeeding"));
                        pnMotherRealmModel.setPnBreastFeedingReason(jsonObject.getString("pnBreastFeedingReason"));
                        pnMotherRealmModel.setPnBreastExamination(jsonObject.getString("pnBreastExamination"));
                        pnMotherRealmModel.setPnOutCome(jsonObject.getString("pnOutCome"));
                        pnMotherRealmModel.setCWeight(jsonObject.getString("cWeight"));
                        pnMotherRealmModel.setCTemp(jsonObject.getString("cTemp"));
                        pnMotherRealmModel.setCUmbilicalStump(jsonObject.getString("cUmbilicalStump"));
                        pnMotherRealmModel.setCCry(jsonObject.getString("cCry"));
                        pnMotherRealmModel.setCEyes(jsonObject.getString("cEyes"));
                        pnMotherRealmModel.setCSkin(jsonObject.getString("cSkin"));
                        pnMotherRealmModel.setCBreastFeeding(jsonObject.getString("cBreastFeeding"));
                        pnMotherRealmModel.setCBreastFeedingReason(jsonObject.getString("cBreastFeedingReason"));
                        pnMotherRealmModel.setCOutCome(jsonObject.getString("cOutCome"));

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

                        /*mPnHbncVisitRecordsList.add(mPnHbncVisitRecordsModel);
                        pnhbncVisitRecordsAdapter.notifyDataSetChanged();*/
                    }
                    realm.commitTransaction();
                } else {
                    txt_no_records.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        motherOfflineRecords();
    }

    private void motherOfflineRecords() {

        realm.beginTransaction();
        RealmResults<PNMotherRealmModel> pnMotherRealmModels = realm.where(PNMotherRealmModel.class).findAll();
        Log.e("realm Size ->", pnMotherRealmModels.size() + "");

        for (int i = 0; i < pnMotherRealmModels.size(); i++) {
            mPnHbncVisitRecordsModel = new PnHbncVisitRecordsModel.Visit_Records();

            PNMotherRealmModel pnMotherRealmModel = pnMotherRealmModels.get(i);

            mPnHbncVisitRecordsModel.setPnId(pnMotherRealmModel.getPnId());
            mPnHbncVisitRecordsModel.setMid(pnMotherRealmModel.getMid());
            mPnHbncVisitRecordsModel.setPicmeId(pnMotherRealmModel.getPicmeId());
            mPnHbncVisitRecordsModel.setPnVisitNo(pnMotherRealmModel.getPnVisitNo());
            mPnHbncVisitRecordsModel.setPnDueDate(pnMotherRealmModel.getPnDueDate());
            mPnHbncVisitRecordsModel.setPnCareProvidedDate(pnMotherRealmModel.getPnCareProvidedDate());
            mPnHbncVisitRecordsModel.setPnPlace(pnMotherRealmModel.getPnPlace());
            mPnHbncVisitRecordsModel.setPnAnyComplaints(pnMotherRealmModel.getPnAnyComplaints());
            mPnHbncVisitRecordsModel.setPnBPSystolic(pnMotherRealmModel.getPnBPSystolic());
            mPnHbncVisitRecordsModel.setPnBPDiastolic(pnMotherRealmModel.getPnBPDiastolic());
            mPnHbncVisitRecordsModel.setPnPulseRate(pnMotherRealmModel.getPnPulseRate());
            mPnHbncVisitRecordsModel.setPnTemp(pnMotherRealmModel.getPnTemp());
            mPnHbncVisitRecordsModel.setPnEpistomyTear(pnMotherRealmModel.getPnEpistomyTear());
            mPnHbncVisitRecordsModel.setPnPVDischarge(pnMotherRealmModel.getPnPVDischarge());
            mPnHbncVisitRecordsModel.setPnBreastFeeding(pnMotherRealmModel.getPnBreastFeeding());
            mPnHbncVisitRecordsModel.setPnBreastFeedingReason(pnMotherRealmModel.getPnBreastFeedingReason());
            mPnHbncVisitRecordsModel.setPnBreastExamination(pnMotherRealmModel.getPnBreastExamination());
            mPnHbncVisitRecordsModel.setPnOutCome(pnMotherRealmModel.getPnOutCome());
            mPnHbncVisitRecordsModel.setCWeight(pnMotherRealmModel.getCWeight());
            mPnHbncVisitRecordsModel.setCTemp(pnMotherRealmModel.getCTemp());
            mPnHbncVisitRecordsModel.setCUmbilicalStump(pnMotherRealmModel.getCUmbilicalStump());
            mPnHbncVisitRecordsModel.setCCry(pnMotherRealmModel.getCCry());
            mPnHbncVisitRecordsModel.setCEyes(pnMotherRealmModel.getCEyes());
            mPnHbncVisitRecordsModel.setCSkin(pnMotherRealmModel.getCSkin());
            mPnHbncVisitRecordsModel.setCBreastFeeding(pnMotherRealmModel.getCBreastFeeding());
            mPnHbncVisitRecordsModel.setCBreastFeedingReason(pnMotherRealmModel.getCBreastFeedingReason());
            mPnHbncVisitRecordsModel.setCOutCome(pnMotherRealmModel.getCOutCome());
            mPnHbncVisitRecordsList.add(mPnHbncVisitRecordsModel);
            pnhbncVisitRecordsAdapter.notifyDataSetChanged();
        }
        realm.commitTransaction();

    }

    @Override
    public void getPNHBNCVisitRecordsFailiur(String errorMsg) {
        Log.d(HealthRecordsFragment.class.getSimpleName(), "--->errorMsg" + errorMsg);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delivery_reports:
                startActivity(new Intent(getActivity().getApplicationContext(), DeliveryDetailsView.class));
                break;
            case R.id.btn_visit_reports:
                startActivity(new Intent(getActivity().getApplicationContext(), PNViewReportsActivity.class));
                break;
        }
    }
}