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
import com.unicef.thaimai.motherapp.Presenter.GetVisitHealthRecordsPresenteer;
import com.unicef.thaimai.motherapp.R;
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

    GetVisitHealthRecordsPresenteer gVHRecordsPresenteer;
    HealthRecordResponseModel mhealthRecordResponseModel;
    ArrayList<HealthRecordResponseModel> mhealthRecordList;
    ViewPagerAdapter adapter;
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

//        pager = view.findViewById(R.id.pager);
//        PagerView(pager);


        preferenceData = new PreferenceData(getActivity());
        editor = getActivity().getSharedPreferences(AppConstants.PREF_NAME, MODE_PRIVATE).edit();

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");

        gVHRecordsPresenteer = new GetVisitHealthRecordsPresenteer(getActivity(), this);
        gVHRecordsPresenteer.getAllVistHeathRecord(preferenceData.getPicmeId(), "1");
        mhealthRecordResponseModel = new HealthRecordResponseModel();
        mhealthRecordList = new ArrayList<>();
        viewPager = view.findViewById(R.id.hre_viewpager);
        setupViewPager(viewPager);
        tabLayout = view.findViewById(R.id.hre_tabs);
//        tabLayoutmain = view.findViewById(R.id.tabLayoutmain);
        tabLayout.setupWithViewPager(viewPager);
        btn_primary_report = (Button) view.findViewById(R.id.btn_primary_report);
        btn_view_report = (Button) view.findViewById(R.id.btn_view_report);

    }


    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(health_records.this,new OneFragment(), "Present Status\nVISIT 5", mhealthRecordList);

        adapter.addFragment(health_records.this,new OneFragment(), "VISIT 4",mhealthRecordList);
        adapter.addFragment(health_records.this,new OneFragment(), "VISIT 3",mhealthRecordList);
        adapter.addFragment(health_records.this,new OneFragment(), "VISIT 2",mhealthRecordList);
        adapter.addFragment(health_records.this,new OneFragment(), "VISIT 1",mhealthRecordList);
        viewPager.setAdapter(adapter);
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
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Log.d(health_records.class.getSimpleName(), "jsonObject" + i + jsonObject);
                mhealthRecordResponseModel.setMasterId(jsonObject.getString("masterId"));
                Log.d("masterId",jsonObject.getString("masterId"));
                mhealthRecordResponseModel.setMasterId(jsonObject.getString("vDate"));
                Log.d("vDate",jsonObject.getString("vDate"));
                mhealthRecordResponseModel.setMasterId(jsonObject.getString("vFacility"));
                Log.d("vFacility",jsonObject.getString("vFacility"));

                mhealthRecordList.add(mhealthRecordResponseModel);
//                Log.e(health_records.class.getSimpleName(), mhealthRecordList.toString());
                adapter.notifyDataSetChanged();
            }
//            Log.e(health_records.class.getSimpleName(), mhealthRecordList.size()+"");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getVisitHealthRecordsFailiur(String errorMsg) {
        Log.d(health_records.class.getSimpleName(), "--->errorMsg" + errorMsg);

    }

    /*private void PagerView(ViewPager PagerView) {

        ViewPagerAdapterMain adapter1 = new ViewPagerAdapterMain(getActivity().getSupportFragmentManager());

        adapter1.addFragmentMain(new PicmeVisit(), "Picme Visits");
        adapter1.addFragmentMain(new PicmeVisit(), "Other Visits");

        PagerView.setAdapter(adapter1);

    }
*/
    /*@Override
    public void onTabSelected(TabLayout.Tab tab) {

        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }*/

}