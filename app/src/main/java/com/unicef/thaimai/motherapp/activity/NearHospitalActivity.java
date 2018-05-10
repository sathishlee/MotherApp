package com.unicef.thaimai.motherapp.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.Preference;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.Interface.MakeCallInterface;
import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.LocationUpdatePresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.adapter.NearByHospitalAdapter;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.model.responsemodel.NearHospitalResponseModel;
import com.unicef.thaimai.motherapp.utility.LocationMonitoringService;
import com.unicef.thaimai.motherapp.view.LocationUpdateViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NearHospitalActivity extends AppCompatActivity implements LocationUpdateViews, MakeCallInterface {

    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;

    ProgressDialog pDialog;
    LocationUpdatePresenter locationUpdatePresenter;
    private List<NearHospitalResponseModel.Nearby> mNearbyList ;
    NearHospitalResponseModel.Nearby mnearbyModel;
    private RecyclerView recyclerView;
    private NearByHospitalAdapter mAdapter;
    boolean isDataUpdate=true;
    PreferenceData preferenceData;
    TextView txt_no_records_found;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_hospital);
        showActionBar();
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        preferenceData = new PreferenceData(this);

        locationUpdatePresenter = new LocationUpdatePresenter(NearHospitalActivity.this, this);
        locationUpdatePresenter.getNearByHospitalFromServer(AppConstants.NEAR_LATITUDE, AppConstants.NEAR_LONGITUDE);
//        locationUpdatePresenter.getNearByHospitalFromServer(AppConstants.EXTRA_LATITUDE, AppConstants.EXTRA_LONGITUDE);

        /*LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {

                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String latitude = intent.getStringExtra(AppConstants.EXTRA_LATITUDE);
                        String longitude = intent.getStringExtra(AppConstants.EXTRA_LONGITUDE);
                        if (isDataUpdate) {
                            if (latitude != null && longitude != null) {
                                AppConstants.NEAR_LATITUDE = latitude;
                                AppConstants.NEAR_LONGITUDE = longitude;
                                locationUpdatePresenter.getNearByHospitalFromServer(AppConstants.NEAR_LATITUDE, AppConstants.NEAR_LONGITUDE);

                            }
                        }
                    }
                }, new IntentFilter(LocationMonitoringService.ACTION_LOCATION_BROADCAST)
        );*/

        mNearbyList =new ArrayList<>();
        recyclerView =(RecyclerView)findViewById(R.id.rec_nearbyhospital);
        txt_no_records_found = (TextView) findViewById(R.id.txt_no_records_found);
        txt_no_records_found.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(NearHospitalActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if(mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
        else {
            mAdapter = new NearByHospitalAdapter(mNearbyList, NearHospitalActivity.this,this);
            recyclerView.setAdapter(mAdapter);
        }


    }

    // Action bar function back button press & set title
    private void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Near By Hospital");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(NearHospitalActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
        if (!NearHospitalActivity.this.isDataUpdate) {
            pDialog.show();
        }
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
    public void locationUpdateSuccess(String loginResponseModel) {

    }

    @Override
    public void locationUpdateFailiure(String string) {

    }

    @Override
    public void getNearbyHospitalSuccess(String responsne) {
        Log.e(NearHospitalActivity.class.getSimpleName(), "response success" + responsne);
        try {
            JSONObject mJsnobject = new JSONObject(responsne);
            String status = mJsnobject.getString("status");
            if (status.equalsIgnoreCase("1")) {
                txt_no_records_found.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                JSONArray jsonArray = mJsnobject.getJSONArray("nearby");
                for (int i = 0; i < jsonArray.length(); i++) {
                    mnearbyModel = new NearHospitalResponseModel.Nearby();

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    mnearbyModel.setPhcId(jsonObject.getString("phcId"));
                    mnearbyModel.setF_district_name(jsonObject.getString("f_district_name"));
                    mnearbyModel.setF_sub_district_nam(jsonObject.getString("f_sub_district_nam"));
                    mnearbyModel.setPhcName(jsonObject.getString("phcName"));
                    mnearbyModel.setPhcCode(jsonObject.getString("phcCode"));
                    mnearbyModel.setF_nin_num(jsonObject.getString("f_nin_num"));
                    mnearbyModel.setF_facility_name(jsonObject.getString("f_facility_name"));
                    mnearbyModel.setF_location(jsonObject.getString("f_location"));
                    mnearbyModel.setF_latitute(jsonObject.getString("f_latitute"));
                    mnearbyModel.setF_longititute(jsonObject.getString("f_longititute"));
                    mnearbyModel.setF_level(jsonObject.getString("f_level"));
                    mnearbyModel.setPhcMobile(jsonObject.getString("phcMobile"));
                    mnearbyModel.setPhcStatus(jsonObject.getString("phcStatus"));
                    mnearbyModel.setDistance(jsonObject.getString("distance"));
//                mNearbyList.clear();
                    mNearbyList.add(mnearbyModel);
                    mAdapter.notifyDataSetChanged();
                    pDialog.hide();
                }
            }
            else{
                txt_no_records_found.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);

            }
        }catch (JSONException e) {
            e.printStackTrace();
            pDialog.hide();
        }
    }
    @Override
    public void getNearbyHospitalFailiure(String loginResponseModel) {
        Log.e(NearHospitalActivity.class.getSimpleName(),"response error"+loginResponseModel);
    }

    @Override
    public void makeCall(String phcMobile) {
        isDataUpdate=false;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // Camera permission has not been granted.
            requestCallPermission();

        } else {
//            Log.i(NearHospitalActivity.class.getSimpleName(),"CALL permission has already been granted. Displaying camera preview.");
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:+"+phcMobile)));
        }

    }

    private void requestCallPermission() {
        Log.i(NearHospitalActivity.class.getSimpleName(), "CALL permission has NOT been granted. Requesting permission.");

        // BEGIN_INCLUDE(camera_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CALL_PHONE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
//            Log.i(NearHospitalActivity.class.getSimpleName(),            "Displaying camera permission rationale to provide additional context.");
            Toast.makeText(getApplicationContext(),"Displaying Call permission rationale to provide additional context.",Toast.LENGTH_SHORT).show();

        } else {

            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                    MAKE_CALL_PERMISSION_REQUEST_CODE);
        }
// END_INCLUDE(camera_permission_request)
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MAKE_CALL_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                    dial.setEnabled(true);
                    Toast.makeText(this, "You can call the number by clicking on the button", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
}
