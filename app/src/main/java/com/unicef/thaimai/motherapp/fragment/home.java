package com.unicef.thaimai.motherapp.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.GetUserInfoPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.RealmDBModel.UserInfoRealmModel;
import com.unicef.thaimai.motherapp.utility.CheckNetwork;
import com.unicef.thaimai.motherapp.activity.NearHospitalActivity;
//import com.unicef.thaimai.motherapp.bradcastReceiver.ConnectivityReceiver;
import com.unicef.thaimai.motherapp.activity.ProfileActivity;
import com.unicef.thaimai.motherapp.app.RealmController;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.utility.RoundedTransformation;
import com.unicef.thaimai.motherapp.view.LoginViews;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.content.Context.MODE_PRIVATE;


public class home extends Fragment implements LoginViews, View.OnClickListener {

    TextView txt_username, picme_id, txt_age, txt_risk,txt_gst_week,txt_weight,
            txt_next_visit,txt_lmp_date,txt_edd_date,txt_husb_name,
            txt_husb_mobile_number,txt_vhn_name,txt_vhn_mobile_number,txt_aww_name,
            txt_aww_mobile_number,txt_phc_name,txt_phc_mobile_number;

    TextView txt_no_network;

    ImageView img_call_husb,img_call_vhn,img_call_aww,img_call_phc, cardview_image;

     String strname, strpicmeId, strage,str_mobile_number_hsbn,
            str_mobile_number_vhn, str_mobile_number_aww,str_mobile_number_phc, str_mPhoto;

    CardView profile;
    Context context;

    PreferenceData preferenceData;
    SharedPreferences.Editor editor;
    ProgressDialog pDialog;

    GetUserInfoPresenter getUserInfoPresenter;

    Activity mActivity;
    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;


     CheckNetwork checkNetwork;
    boolean isoffline = false;
    Realm realm;


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


//        realm = Realm.getDefaultInstance(); // opens "myrealm.realm"
        realm = RealmController.with(getActivity()).getRealm(); // opens "myrealm.realm"


        initUI(view);
        checkConnection();
         onClickListner();

        getActivity().setTitle("Dashboard");

        profile = (CardView) view.findViewById(R.id.user_profile_photo);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                getActivity().finish();
                startActivity(intent);
             }
        });
        return view;
    }

    private void checkConnection() {
//        boolean isConnected = ConnectivityReceiver.isConnected();
//        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        if (isConnected){
            txt_no_network.setVisibility(View.GONE);

        }else {
            txt_no_network.setVisibility(View.VISIBLE);
        }
    }


    private void initUI(View view) {

        preferenceData = new PreferenceData(getActivity());
        context = getActivity();
        editor = getActivity().getSharedPreferences(AppConstants.PREF_NAME, MODE_PRIVATE).edit();

        strname = preferenceData.getMotherName();
        strpicmeId = preferenceData.getPicmeId();
        strage = preferenceData.getMotherAge();

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait ...");
        getUserInfoPresenter =new GetUserInfoPresenter(getActivity().getApplicationContext(),this);
       Log.e("PICME_ID",preferenceData.getPicmeId());

       checkNetwork =new CheckNetwork(getActivity());
        if (checkNetwork.isNetworkAvailable()) {
            getUserInfoPresenter.getUserInfo(strpicmeId);
        } else {
            isoffline = true;
        }

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
        cardview_image = (ImageView) view.findViewById(R.id.cardview_image);
        picme_id.setText(strpicmeId);
        txt_username .setText(strname);
        txt_age.setText(strage);


//        txt_no_network = (TextView) view.findViewById(R.id.txt_no_network);
        if (isoffline) {
            showOfflineData();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Record Not Found");
           /* .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // FIRE ZE MISSILES!
                }
            })
            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });*/
            // Create the AlertDialog object and return it
            builder.create();

        }
    }

    private void showOfflineData() {

        // show offline data


        realm.beginTransaction();

        RealmResults<UserInfoRealmModel> userInfoRealmResult = realm.where(UserInfoRealmModel.class).findAll();
        for (int i = 0; i < userInfoRealmResult.size(); i++) {
            UserInfoRealmModel model = userInfoRealmResult.get(i);
            Log.d("OffLine Delevery date" + i, model.getDeleveryDate());
            Log.d("OffLine AN Next visit" + i, model.getANnextVisit() + "");
            txt_lmp_date.setText(model.getmLMP());
            txt_edd_date.setText(model.getmEDD());
            txt_age.setText(model.getmAge() + "");
            txt_risk.setText(model.getmRiskStatus());
            txt_weight.setText(model.getmWeight());
            txt_next_visit.setText(model.getANnextVisit());
            txt_husb_name.setText(model.getmHusbandName());
            txt_gst_week.setText(model.getmGesWeek() + "");
            preferenceData.setGstWeek(String.valueOf(model.getmGesWeek()));

            str_mobile_number_hsbn = model.getmHusbandMobile();
//            txt_husb_mobile_number.setText(str_mobile_number_hsbn);
            txt_vhn_name.setText(model.getVhnName());
            str_mobile_number_vhn = model.getVhnMobile();
//            txt_vhn_mobile_number.setText(str_mobile_number_vhn);


            txt_aww_name.setText(model.getAwwName());
            str_mobile_number_aww = model.getAwwMobile();
//            txt_aww_mobile_number.setText(str_mobile_number_aww);
            txt_phc_name.setText(model.getPhcName());
            str_mobile_number_phc = model.getPhcMobile();
//            txt_phc_mobile_number.setText(str_mobile_number_phc);

            str_mPhoto = model.getmPhoto();
            Log.d("mphoto-->", Apiconstants.PHOTO_URL + str_mPhoto);

            Picasso.with(context)
                    .load(Apiconstants.PHOTO_URL + str_mPhoto)
                    .placeholder(R.drawable.girl_1)
                    .fit()
                    .centerCrop()
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .transform(new RoundedTransformation(90, 4))
                    .error(R.drawable.girl_1)
                    .into(cardview_image);
        }
        realm.commitTransaction();

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
    public void loginSuccess(String response) {

    }

    @Override
    public void loginError(String string) {

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (pDialog!=null && pDialog.isShowing() ){
            pDialog.cancel();
        }
    }

    @Override
    public void showPickmeResult(String response) {

        JSONObject jObj = null;
        RealmResults<UserInfoRealmModel> userInfoRealmResult = realm.where(UserInfoRealmModel.class).findAll();
        Log.e("Realm size ---->", userInfoRealmResult.size() + "");
        if (userInfoRealmResult.size() != 0) {


            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.delete(UserInfoRealmModel.class);
                }
            });
        } else {
            Log.e("Realm size  ---->", userInfoRealmResult.size() + "");
        }
        Log.e("After Realm size  ---->", userInfoRealmResult.size() + "");
        try {
            jObj = new JSONObject(response);

            realm.beginTransaction();
            UserInfoRealmModel userInfoRealmModel = realm.createObject(UserInfoRealmModel.class);  //this will create a UserInfoRealmModel object which will be inserted in database
            userInfoRealmModel.setANnextVisit(jObj.getString("ANnextVisit"));
            userInfoRealmModel.setmAge(Integer.parseInt(jObj.getString("mAge")));
            userInfoRealmModel.setmPhoto(jObj.getString("mPhoto"));
            userInfoRealmModel.setmDOB(jObj.getString("mDOB"));
            userInfoRealmModel.setMotherStatus(jObj.getString("motherStatus"));
            userInfoRealmModel.setmLMP(jObj.getString("mLMP"));
            userInfoRealmModel.setmEDD(jObj.getString("mEDD"));
            userInfoRealmModel.setmRiskStatus(jObj.getString("mRiskStatus"));
            userInfoRealmModel.setmWeight(jObj.getString("mWeight"));
            userInfoRealmModel.setmHusbandName(jObj.getString("mHusbandName"));
            userInfoRealmModel.setmGesWeek(Integer.parseInt(jObj.getString("mGesWeek")));
            userInfoRealmModel.setmHusbandMobile(jObj.getString("mHusbandMobile"));
            userInfoRealmModel.setDeleveryDate(jObj.getString("deleveryDate"));
            userInfoRealmModel.setPicmeId(jObj.getString("picmeId"));
if (jObj.getInt("deleveryStatus")==1){

    JSONObject jsonObjPNnextVisit = jObj.getJSONObject("PNnextVisit");

    userInfoRealmModel.setPnVisit(jsonObjPNnextVisit.getString("pnVisit"));
    userInfoRealmModel.setMeaturityWeeks(jsonObjPNnextVisit.getString("meaturityWeeks"));
    userInfoRealmModel.setDeleveryType(jsonObjPNnextVisit.getString("deleveryType"));
    userInfoRealmModel.setChildWeight(jsonObjPNnextVisit.getString("childWeight"));
}


            realm.commitTransaction();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        setValueToUI();
    }

    private void setValueToUI() {

        realm.beginTransaction();
        RealmResults<UserInfoRealmModel> userInfoRealmResult = realm.where(UserInfoRealmModel.class).findAll();
        for (int i = 0; i < userInfoRealmResult.size(); i++) {
            UserInfoRealmModel model = userInfoRealmResult.get(i);

            txt_lmp_date.setText(model.getmLMP());
            txt_edd_date.setText(model.getmEDD());
            txt_age.setText(model.getmAge() + "");
            txt_risk.setText(model.getmRiskStatus());
            txt_weight.setText(model.getmWeight());
            txt_next_visit.setText(model.getANnextVisit());
            txt_husb_name.setText(model.getmHusbandName());
            txt_gst_week.setText(model.getmGesWeek() + "");
            preferenceData.setGstWeek(String.valueOf(model.getmGesWeek()));

            str_mobile_number_hsbn = model.getmHusbandMobile();
//            txt_husb_mobile_number.setText(str_mobile_number_hsbn);
            txt_vhn_name.setText(model.getVhnName());
            str_mobile_number_vhn = model.getVhnMobile();
//            txt_vhn_mobile_number.setText(str_mobile_number_vhn);


            txt_aww_name.setText(model.getAwwName());
            str_mobile_number_aww = model.getAwwMobile();
//            txt_aww_mobile_number.setText(str_mobile_number_aww);
            txt_phc_name.setText(model.getPhcName());
            str_mobile_number_phc = model.getPhcMobile();
//            txt_phc_mobile_number.setText(str_mobile_number_phc);

            str_mPhoto = model.getmPhoto();
            Log.d("mphoto-->", Apiconstants.PHOTO_URL + str_mPhoto);

            Picasso.with(context)
                    .load(Apiconstants.PHOTO_URL + str_mPhoto)
                    .placeholder(R.drawable.girl_1)
                    .fit()
                    .centerCrop()
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .transform(new RoundedTransformation(90, 4))
                    .error(R.drawable.girl_1)
                    .into(cardview_image);
        }
        realm.commitTransaction();

    }

    @Override
    public void showErrorMessage(String string) {
        Log.e( "Error", string);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_call_husb:
                makeCall(str_mobile_number_hsbn);
                break;

//                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("tel:+"+str_mobile_number_hsbn)));
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
