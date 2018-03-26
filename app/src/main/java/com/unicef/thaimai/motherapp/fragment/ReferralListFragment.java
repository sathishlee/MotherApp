package com.unicef.thaimai.motherapp.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.Interface.RefrealAutoComplete;
import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.ReferalPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.activity.AddReferral;
import com.unicef.thaimai.motherapp.adapter.ReferalListAdapter;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.model.responsemodel.ReferalListResponseModel;
import com.unicef.thaimai.motherapp.view.ReferalViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class ReferralListFragment extends Fragment implements ReferalViews, RefrealAutoComplete {

    FloatingActionButton fabAddNewReferal;
    RecyclerView rec_referral_list;

    PreferenceData preferenceData;
    SharedPreferences.Editor editor;
    ProgressDialog pDialog;

    ReferalPresenter referalPresenter;
    ReferalListResponseModel.NearestHospitals mReferalListModel;
    ArrayList<ReferalListResponseModel.NearestHospitals> mReferalList;
    ReferalListAdapter hAdapter;

String ref_status;
    public static ReferralListFragment newInstance() {
        ReferralListFragment fragment = new ReferralListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.layout_referral, container, false);
        initUI(view);
        onClickListner();

        return view;
    }

    private void onClickListner() {

    }

    private void initUI(View view) {

        getActivity().setTitle("Referral Details");
        preferenceData = new PreferenceData(getActivity());
        editor = getActivity().getSharedPreferences(AppConstants.PREF_NAME, MODE_PRIVATE).edit();
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        referalPresenter = new ReferalPresenter(this, (Activity) getActivity());
        mReferalList = new ArrayList<>();

        referalPresenter.getReffralNeList(preferenceData.getMId(), preferenceData.getPhcId(), preferenceData.getVhnId(), preferenceData.getPicmeId());
        fabAddNewReferal = (FloatingActionButton) view.findViewById(R.id.fab_add_new_referal);
        rec_referral_list = (RecyclerView) view.findViewById(R.id.rec_referral_list);
        hAdapter = new ReferalListAdapter(getActivity(), mReferalList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rec_referral_list.setLayoutManager(mLayoutManager);
        rec_referral_list.setItemAnimator(new DefaultItemAnimator());
        rec_referral_list.setAdapter(hAdapter);


        fabAddNewReferal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent i = new Intent(getActivity(), AddReferral.class);
                    AppConstants.CREATE_NEW_REFRAL = true;
                    startActivity(i);
//                    Toast.makeText(getActivity(),"Already Referal InProgress, you can't make new Referal",Toast.LENGTH_SHORT).show();
            }
        });

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
    public void successReferalAdd(String response) {

    }

    @Override
    public void errorReferalAdd(String response) {

    }

    @Override
    public void successReferalNearestHospital(String response) {

    }

    @Override
    public void errorReferalNearestHospital(String response) {

    }

    @Override
    public void successReferalList(String response) {
        Log.d(health_records.class.getSimpleName(), "--->ReferalList response success" + response);

        try {
            JSONObject mJsnobject = new JSONObject(response);
            String status = mJsnobject.getString("status");
            String message = mJsnobject.getString("message");
            if (status.equalsIgnoreCase("1")) {
                JSONArray jsonArray = mJsnobject.getJSONArray("nearestHospitals");
                for (int i = 0; i < jsonArray.length(); i++) {
                    mReferalListModel = new ReferalListResponseModel.NearestHospitals();


                /*holder.txt_referal_date.setText(mReferalListmodel.getRReferalDate());
        holder.txt_referal_time.setText(mReferalListmodel.getRReferalTime());
        holder.txt_referred_by.setText(mReferalListmodel.getRReferredBy());
        holder.txt_referred_from.setText(mReferalListmodel.getRFacilityReferring());
        holder.txt_referred_to.setText(mReferalListmodel.getRFacilityReferredTo());
        holder.txt_diagnosis.setText(mReferalListmodel.getRDiagonosis());
        holder.txt_referred_why.setText(mReferalListmodel.getRReferalReason());*/
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    mReferalListModel.setRid(jsonObject.getString("rid"));
                    mReferalListModel.setRReferalDate(jsonObject.getString("rReferalDate"));
                    mReferalListModel.setRReferalTime(jsonObject.getString("rReferalTime"));
                    mReferalListModel.setRReferredBy(jsonObject.getString("rReferredBy"));
                    mReferalListModel.setRFacilityReferring(jsonObject.getString("rFacilityReferring"));
                    mReferalListModel.setRFacilityReferredTo(jsonObject.getString("rFacilityReferredTo"));
                    mReferalListModel.setRDiagonosis(jsonObject.getString("rDiagonosis"));
                    mReferalListModel.setRReferalReason(jsonObject.getString("rReferalReason"));
                    mReferalListModel.setRUpdateAdmitted(jsonObject.getString("rUpdateAdmitted"));
                    mReferalListModel.setReferalStatus(jsonObject.getString("referalStatus"));
                    mReferalList.add(mReferalListModel);
                    hAdapter.notifyDataSetChanged();
                }
            }else{
                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void errorReferalList(String response) {
        Toast.makeText(getActivity(),response,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void successReferalClosed(String response) {
        try {
            JSONObject mJsnobject = new JSONObject(response);
            String status = mJsnobject.getString("status");
            String message = mJsnobject.getString("message");
            if (status.equalsIgnoreCase("1")) {
                if (!message.equalsIgnoreCase("Referral Already Closed..!")){
                    startActivity(new Intent(getActivity(), AddReferral.class));
                }

            }else
            {
                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
//        status
    }

    @Override
    public void errorReferalClosed(String response) {
        Toast.makeText(getActivity(),response,Toast.LENGTH_SHORT).show();

    }


    @Override
    public void isRefrealAutoComplete(String referalId, String refStatus) {
        ref_status = refStatus;
        if(ref_status.equalsIgnoreCase("Created")) {
            startActivity(new Intent(getActivity(), AddReferral.class));

        }else{
            referalPresenter.checkReferalClosed(referalId);
        }

    }
}