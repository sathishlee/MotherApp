package com.unicef.thaimai.motherapp.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.GetUserInfoPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.activity.NearHospitalActivity;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.activity.profile;
import com.unicef.thaimai.motherapp.model.responsemodel.LoginResponseModel;
import com.unicef.thaimai.motherapp.view.LoginViews;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;


public class home extends Fragment implements LoginViews, View.OnClickListener {

    TextView txt_username, picme_id, txt_age, txt_risk,txt_gst_week,txt_weight,
            txt_next_visit,txt_lmp_date,txt_edd_date,txt_husb_name,
            txt_husb_mobile_number,txt_vhn_name,txt_vhn_mobile_number,txt_aww_name,
            txt_aww_mobile_number,txt_phc_name,txt_phc_mobile_number;

    ImageView img_call_husb,img_call_vhn,img_call_aww,img_call_phc;

     String strname, strpicmeId, strage,str_mobile_number_hsbn,
            str_mobile_number_vhn, str_mobile_number_aww,str_mobile_number_phc;

    CardView profile;

    PreferenceData preferenceData;
    SharedPreferences.Editor editor;
    ProgressDialog pDialog;

    GetUserInfoPresenter getUserInfoPresenter;

    Activity mActivity;
    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;

    public static home newInstance()
    {
        home fragment = new home();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
         View view = inflater.inflate(R.layout.fragment_home, container, false);
         initUI(view);
         onClickListner();

        getActivity().setTitle("Dashboard");





        profile = (CardView) view.findViewById(R.id.user_profile_photo);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), profile.class);
                getActivity().finish();
                startActivity(intent);
             }
        });
        return view;
    }



    private void initUI(View view) {

        preferenceData = new PreferenceData(getActivity());
        editor = getActivity().getSharedPreferences(AppConstants.PREF_NAME, MODE_PRIVATE).edit();

        strname = preferenceData.getMotherName();
        strpicmeId = preferenceData.getPicmeId();
        strage = preferenceData.getMotherAge();

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        getUserInfoPresenter =new GetUserInfoPresenter(getActivity().getApplicationContext(),this);
       Log.e("PICME_ID",preferenceData.getPicmeId());
        getUserInfoPresenter.getUserInfo(strpicmeId);
        getActivity().setTitle("Dashboard");
        txt_username = (TextView) view.findViewById(R.id.txt_username);
        picme_id = (TextView) view.findViewById(R.id.txt_picme_id);
        txt_age = (TextView) view.findViewById(R.id.txt_age);
        txt_gst_week = (TextView) view.findViewById(R.id.txt_gst_week);
        txt_weight = (TextView) view.findViewById(R.id.txt_weight);
        txt_next_visit = (TextView) view.findViewById(R.id.txt_next_visit);
        txt_lmp_date = (TextView) view.findViewById(R.id.txt_lmp_date);
        txt_edd_date = (TextView) view.findViewById(R.id.txt_edd_date);
        txt_risk = (TextView) view.findViewById(R.id.txt_risk);

        txt_husb_name = (TextView) view.findViewById(R.id.txt_husb_name);
//        txt_husb_mobile_number = (TextView) view.findViewById(R.id.txt_husb_mobile_number);
        txt_vhn_name = (TextView) view.findViewById(R.id.txt_vhn_name);
        txt_vhn_mobile_number = (TextView) view.findViewById(R.id.txt_vhn_mobile_number);
        txt_aww_name = (TextView) view.findViewById(R.id.txt_aww_name);
        txt_aww_mobile_number = (TextView) view.findViewById(R.id.txt_aww_mobile_number);
        txt_phc_name = (TextView) view.findViewById(R.id.txt_phc_name);
//        txt_phc_mobile_number = (TextView) view.findViewById(R.id.txt_phc_mobile_number);

        img_call_husb =(ImageView) view.findViewById(R.id.img_call_husb);
        img_call_vhn =(ImageView) view.findViewById(R.id.img_call_vhn);
        img_call_aww =(ImageView) view.findViewById(R.id.img_call_aww);
        img_call_phc =(ImageView) view.findViewById(R.id.img_call_phc);
        picme_id.setText(strpicmeId);
        txt_username .setText(strname);
        txt_age.setText(strage);

    }

    private void onClickListner() {
        img_call_husb.setOnClickListener(this);
        img_call_vhn.setOnClickListener(this);
        img_call_aww.setOnClickListener(this);
        img_call_phc.setOnClickListener(this);
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
    public void showPickmeResult(String response) {
        try {
            JSONObject jObj = new JSONObject(response);
            txt_lmp_date.setText(jObj.getString("mLMP"));
            txt_edd_date.setText(jObj.getString("mLMP"));
            txt_risk.setText(jObj.getString("mRiskStatus"));
            txt_weight.setText(jObj.getString("mWeight"));
            txt_husb_name.setText(jObj.getString("mHusbandName"));
//            str_mobile_number_hsbn =jObj.getString("mHusbandMobile");
//            txt_husb_mobile_number.setText(str_mobile_number_hsbn);
            txt_vhn_name.setText(jObj.getString("vhnName"));
//            str_mobile_number_vhn =jObj.getString("vhnMobile");
//            txt_vhn_mobile_number.setText(str_mobile_number_vhn);
            txt_aww_name.setText(jObj.getString("awwName"));
//            str_mobile_number_aww = jObj.getString("awwMobile");
//            txt_aww_mobile_number.setText(str_mobile_number_aww);
//            txt_aww_mobile_number.setText(jObj.getString("phcName"));
//            str_mobile_number_phc = jObj.getString("phcMobile");
//            txt_phc_mobile_number.setText(str_mobile_number_phc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showErrorMessage(String string) {
        Log.e( "Error", string);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_call_husb:
                makeCall(str_mobile_number_hsbn); break;
            case R.id.img_call_vhn:
                makeCall(str_mobile_number_vhn); break;
            case R.id.img_call_aww:
                makeCall(str_mobile_number_aww); break;
            case R.id.img_call_phc:
                makeCall(str_mobile_number_phc); break;
        }
    }

    public void makeCall(String str_mobile_number) {
        Toast.makeText(getActivity().getApplicationContext(),str_mobile_number,Toast.LENGTH_SHORT).show();

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // Camera permission has not been granted.

            requestCallPermission();

        } else {

            // Camera permissions is already available, show the camera preview.
            Log.i(NearHospitalActivity.class.getSimpleName(),"CALL permission has already been granted. Displaying camera preview.");
//            showCameraPreview();
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:+"+str_mobile_number)));

        }
    }

    private void requestCallPermission() {


        Log.i(NearHospitalActivity.class.getSimpleName(), "CALL permission has NOT been granted. Requesting permission.");

        // BEGIN_INCLUDE(camera_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CALL_PHONE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            Log.i(NearHospitalActivity.class.getSimpleName(),            "Displaying camera permission rationale to provide additional context.");
            Toast.makeText(getActivity(),"Displaying camera permission rationale to provide additional context.",Toast.LENGTH_SHORT).show();

        } else {

            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},
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
                    Toast.makeText(getActivity(), "You can call the number by clicking on the button", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
}
