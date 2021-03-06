package com.unicef.thaimai.motherapp.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.unicef.thaimai.motherapp.Interface.MakeCallInterface;
import com.unicef.thaimai.motherapp.Preference.PreferenceData;
import com.unicef.thaimai.motherapp.Presenter.GetUserInfoPresenter;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.activity.Login;
//import com.unicef.thaimai.motherapp.bradcastReceiver.ConnectivityReceiver;
import com.unicef.thaimai.motherapp.activity.ProfileActivity;
import com.unicef.thaimai.motherapp.app.RealmController;
import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.constant.AppConstants;
import com.unicef.thaimai.motherapp.realmDbModelClass.HomeRealmModel;
import com.unicef.thaimai.motherapp.utility.CheckNetwork;
import com.unicef.thaimai.motherapp.utility.RoundedTransformation;
import com.unicef.thaimai.motherapp.view.LoginViews;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment implements LoginViews, View.OnClickListener, MakeCallInterface {

    TextView txt_username, picme_id, txt_age, txt_an_risk,txt_pn_risk,txt_gst_week,txt_weight,
            txt_next_visit,txt_lmp_date,txt_edd_date,txt_husb_name,
            txt_husb_mobile_number,txt_vhn_name,txt_vhn_mobile_number,txt_aww_name,
            txt_aww_mobile_number,txt_phc_name,txt_phc_mobile_number, txt_delivery_date,
            txt_birth_weight, txt_type_delivery, txt_maturity, txt_pn_next_visit;

    TextView txt_no_network;

    ImageView img_call_husb,img_call_vhn,img_call_aww,img_call_phc, cardview_image;

     String strname, strpicmeId, strage,str_mobile_number_hsbn,
            str_mobile_number_vhn, str_mobile_number_aww,str_mobile_number_phc, str_mPhoto,
             strPNvisit, strPNNextVisit, strDeliverydate, strMeaturityWeek, strDeliveryDate;

    CardView profile, card_pn_block;
    Context context;

    PreferenceData preferenceData;
    SharedPreferences.Editor editor;
    ProgressDialog pDialog;

    GetUserInfoPresenter getUserInfoPresenter;
    LinearLayout low_risk, high_risk, pn_low_risk,pn_high_risk;

    Activity mActivity;
    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;

    Realm realm;
    boolean isoffline = false;
    CheckNetwork checkNetwork;

    public static HomeFragment newInstance()
    {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
//        pDialog.hide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
         View view = inflater.inflate(R.layout.fragment_home, container, false);
        realm = RealmController.with(getActivity()).getRealm();
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
        checkNetwork = new CheckNetwork(getActivity());
        preferenceData = new PreferenceData(getActivity());
        editor = getActivity().getSharedPreferences(AppConstants.PREF_NAME, MODE_PRIVATE).edit();

        strname = preferenceData.getMotherName();
        strpicmeId = preferenceData.getPicmeId();
        strage = preferenceData.getMotherAge();

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(true);
        pDialog.setMessage("Please Wait ...");
        getUserInfoPresenter =new GetUserInfoPresenter(getActivity().getApplicationContext(),this);
        context = getActivity();
        Log.e("PICME_ID",preferenceData.getPicmeId());
        PackageInfo packageInfo = null;
        String version_name = "Latest";
        int version_code = 5;
        String appversion = String.valueOf(version_code);

        try {
            packageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            version_name = packageInfo.versionName;
            version_code = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
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
        txt_an_risk = (TextView) view.findViewById(R.id.txt_an_risk);
        txt_pn_risk = (TextView) view.findViewById(R.id.txt_pn_risk);

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

        txt_delivery_date = (TextView) view.findViewById(R.id.txt_delivery_date);
        txt_birth_weight = (TextView) view.findViewById(R.id.txt_birth_weight);
        txt_type_delivery = (TextView) view.findViewById(R.id.txt_type_delivery);
        txt_maturity = (TextView) view.findViewById(R.id.txt_maturity);
        txt_pn_next_visit = (TextView) view.findViewById(R.id.txt_pn_next_visit);
        cardview_image = (ImageView) view.findViewById(R.id.cardview_image);
        card_pn_block = (CardView) view.findViewById(R.id.card_pn_block);

        low_risk = (LinearLayout) view.findViewById(R.id.low_risk);
        high_risk = (LinearLayout) view.findViewById(R.id.high_risk);

        pn_low_risk = (LinearLayout) view.findViewById(R.id.pn_low_risk);
        pn_high_risk = (LinearLayout) view.findViewById(R.id.pn_high_risk);

        card_pn_block.setVisibility(View.GONE);
        picme_id.setText(strpicmeId);
        txt_username .setText(strname);
        txt_age.setText(strage);

        if (checkNetwork.isNetworkAvailable()) {
            getUserInfoPresenter.getUserInfo(strpicmeId, appversion);
        } else {
//            Log.w(HomeFragment.class.getSimpleName(), "Is" + checkNetwork.isNetworkAvailable());
//            startActivity(new Intent(getActivity(), NoInternetConnectionActivity.class));
            isoffline = true;
        }

        if (isoffline) {
            showOffline();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Record Not Found");
            builder.create();
        }

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
        pDialog.dismiss();
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
        pDialog.hide();
        /*if (pDialog!=null && pDialog.isShowing() ){
            pDialog.cancel();
        }*/

    }

    private void showOffline() {
        Log.e(HomeFragment.class.getSimpleName(),"your app is now OFF LINE");
        realm.beginTransaction();
        RealmResults<HomeRealmModel> homeRealmModels = realm.where(HomeRealmModel.class).findAll();
        for(int i=0; i < homeRealmModels.size();i++){
            HomeRealmModel homeRealmModel = homeRealmModels.get(i);
            if(homeRealmModel.getMLMP().equalsIgnoreCase("null")){
                txt_lmp_date.setText("-");
            }else {
                txt_lmp_date.setText(homeRealmModel.getMLMP());
            }
            if(homeRealmModel.getMEDD().equalsIgnoreCase("null")){
                txt_edd_date.setText("-");
            }else {
                txt_edd_date.setText(homeRealmModel.getMEDD());
            }
            if(preferenceData.getMotherAge().equalsIgnoreCase("null")){
                txt_age.setText("-");
            }else {
                txt_age.setText(preferenceData.getMotherAge());
            }
            if(homeRealmModel.getMRiskStatus().equalsIgnoreCase("null")){
                txt_an_risk.setText("-");
            }else {
                if(homeRealmModel.getMRiskStatus().equalsIgnoreCase("HIGH")){
                    low_risk.setVisibility(View.GONE);
                    high_risk.setVisibility(View.VISIBLE);
                    txt_an_risk.setText(homeRealmModel.getMRiskStatus());
                    txt_an_risk.setTextColor(Color.parseColor("#fff44336"));
                }else{
                    low_risk.setVisibility(View.VISIBLE);
                    high_risk.setVisibility(View.GONE);
                    txt_an_risk.setText(homeRealmModel.getMRiskStatus());

                }
            }

            if(homeRealmModel.getMWeight().equalsIgnoreCase("null")){
                txt_weight.setText("-");
            }else {
                txt_weight.setText(homeRealmModel.getMWeight());
            }
            String txtHusbandName = homeRealmModel.getMHusbandName();
            if(txtHusbandName.equalsIgnoreCase("null")){
                txt_husb_name.setText("-");
            }else{
                txt_husb_name.setText(homeRealmModel.getMHusbandName());
            }
            if(preferenceData.getGstWeek().equalsIgnoreCase("null")){
                txt_gst_week.setText("-");
            }else {
                txt_gst_week.setText(preferenceData.getGstWeek()+ "Wks");
            }
            if(homeRealmModel.getANnextVisit().equalsIgnoreCase("null")){
                txt_next_visit.setText("-");
            }else {
                txt_next_visit.setText(homeRealmModel.getANnextVisit());
            }
            str_mobile_number_hsbn = homeRealmModel.getMHusbandMobile();
            if(str_mobile_number_hsbn.equalsIgnoreCase("null")){
                img_call_husb.setVisibility(View.GONE);
            }else {
                img_call_husb.setVisibility(View.VISIBLE);
            }
            String vhnName = homeRealmModel.getVhnName();
            if(vhnName.equalsIgnoreCase("null")){
                txt_vhn_name.setText("-");
            }else{
                txt_vhn_name.setText(homeRealmModel.getVhnName());
            }
            str_mobile_number_vhn = homeRealmModel.getVhnMobile();
            if(str_mobile_number_vhn.equalsIgnoreCase("null")){
                img_call_vhn.setVisibility(View.GONE);
            }else{
                img_call_vhn.setVisibility(View.VISIBLE);
            }
            String awwName = homeRealmModel.getAwwName();
            if(awwName.equalsIgnoreCase("null")){
                txt_aww_name.setText("-");
            }else{
                txt_aww_name.setText(homeRealmModel.getAwwName());
            }
            str_mobile_number_aww = homeRealmModel.getAwwMobile();
            if(str_mobile_number_aww.equalsIgnoreCase("null")){
                img_call_aww.setVisibility(View.GONE);
            }else{
                img_call_aww.setVisibility(View.VISIBLE);
            }
            String phcName = homeRealmModel.getPhcName();
            if(phcName.equalsIgnoreCase("null")){
                txt_phc_name.setText("-");
            }else{
                txt_phc_name.setText(homeRealmModel.getPhcName());
            }
            str_mobile_number_phc = homeRealmModel.getPhcMobile();
            if(str_mobile_number_phc.equalsIgnoreCase("null")){
                img_call_phc.setVisibility(View.GONE);
            }else{
                img_call_phc.setVisibility(View.VISIBLE);
            }
            str_mPhoto = homeRealmModel.getMPhoto();

                if(TextUtils.isEmpty(str_mPhoto)){
                    cardview_image.setImageResource(R.drawable.girl_1);
                }else{
                    Log.d("mphoto-->",Apiconstants.PHOTO_URL+str_mPhoto);

                    Picasso.with(context)
                            .load(Apiconstants.PHOTO_URL + str_mPhoto)
                            .placeholder(R.drawable.girl_1)
                            .fit()
                            .centerCrop()
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .networkPolicy(NetworkPolicy.NO_CACHE)
                            .transform(new RoundedTransformation(90,4))
                            .error(R.drawable.girl_1)
                            .into(cardview_image);
                }

            strDeliveryDate = homeRealmModel.getDeleveryStatus();
            if(strDeliveryDate.equalsIgnoreCase("1")){
                card_pn_block.setVisibility(View.VISIBLE);

                if(homeRealmModel.getMRiskStatus().equalsIgnoreCase("null")){
                    txt_pn_risk.setText("-");
                }else{
                    if(homeRealmModel.getMRiskStatus().equalsIgnoreCase("HIGH")){
                        pn_low_risk.setVisibility(View.GONE);
                        pn_high_risk.setVisibility(View.VISIBLE);
                        txt_pn_risk.setText(homeRealmModel.getMRiskStatus());
                        txt_pn_risk.setTextColor(Color.parseColor("#fff44336"));
                    }else{
                        pn_low_risk.setVisibility(View.VISIBLE);
                        pn_high_risk.setVisibility(View.GONE);
                        txt_pn_risk.setText(homeRealmModel.getMRiskStatus());
                    }
                }

                if(homeRealmModel.getDeleveryDate().equalsIgnoreCase("null")){
                    txt_delivery_date.setText("-");
                }else {
                    txt_delivery_date.setText(homeRealmModel.getDeleveryDate());
                }
                if(homeRealmModel.getdBirthWeight().equalsIgnoreCase("null")){
                    txt_birth_weight.setText("-");
                }else {
                    txt_birth_weight.setText(homeRealmModel.getdBirthWeight());
                }
                if(homeRealmModel.getDeleveryType().equalsIgnoreCase("null")){
                    txt_type_delivery.setText("-");
                }else {
                    txt_type_delivery.setText(homeRealmModel.getDeleveryType());
                }
                if(homeRealmModel.getMeaturityWeeks().equalsIgnoreCase("null")){
                    txt_maturity.setText("-");
                }else {
                    txt_maturity.setText(homeRealmModel.getMeaturityWeeks());
                }
                if(homeRealmModel.getPnVisit().equalsIgnoreCase("null")){
                    txt_pn_next_visit.setText("-");
                }else {
                    txt_pn_next_visit.setText(homeRealmModel.getPnVisit());
                }

            }
            else{
                card_pn_block.setVisibility(View.GONE);
            }

        }
        realm.commitTransaction();
    }

    @Override
    public void showPickmeResult(String response) {
        Log.e(HomeFragment.class.getSimpleName(), "Response success" + response);
        try {
            JSONObject jObj = new JSONObject(response);
            String status = jObj.getString("status");
            String message = jObj.getString("message");
            if(status.equalsIgnoreCase("1")){
                RealmResults<HomeRealmModel> homeRealmModels = realm.where(HomeRealmModel.class).findAll();
                Log.e("Realm size ---->", homeRealmModels.size() + "");
                if(homeRealmModels.size() != 0){
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.delete(HomeRealmModel.class);
                        }
                    });
                }else{
                    Log.e("Realm size ---->", homeRealmModels.size() + "");
                }
                Log.e("After Realm size ---->", homeRealmModels.size() + "");
                realm.beginTransaction();
                HomeRealmModel homeRealmModel = realm.createObject(HomeRealmModel.class);
                homeRealmModel.setPicmeId(jObj.getString("picmeId"));
                homeRealmModel.setMLMP(jObj.getString("mLMP"));
                AppConstants.LMP_DATE = jObj.getString("mLMP");
                homeRealmModel.setMEDD(jObj.getString("mEDD"));
                homeRealmModel.setMAge(Integer.parseInt(jObj.getString("mAge")));
                homeRealmModel.setMRiskStatus(jObj.getString("mRiskStatus"));
                if(jObj.getString("mRiskStatus").equalsIgnoreCase("HIGH")){
                    low_risk.setVisibility(View.GONE);
                    high_risk.setVisibility(View.VISIBLE);
                    txt_an_risk.setText(homeRealmModel.getMRiskStatus());
                    txt_an_risk.setTextColor(Color.parseColor("#fff44336"));
                }else{
                    low_risk.setVisibility(View.VISIBLE);
                    high_risk.setVisibility(View.GONE);
                    txt_an_risk.setText(homeRealmModel.getMRiskStatus());
                }
                homeRealmModel.setMWeight(jObj.getString("mWeight"));
                homeRealmModel.setMHusbandName(jObj.getString("mHusbandName"));
                homeRealmModel.setMGesWeek(jObj.getString("mGesWeek"));
                preferenceData.setGstWeek(jObj.getString("mGesWeek"));
                homeRealmModel.setANnextVisit(jObj.getString("ANnextVisit"));
                homeRealmModel.setMHusbandMobile(jObj.getString("mHusbandMobile"));

                homeRealmModel.setVhnName(jObj.getString("vhnName"));
                homeRealmModel.setVhnMobile(jObj.getString("vhnMobile"));
                homeRealmModel.setAwwName(jObj.getString("awwName"));
                homeRealmModel.setAwwMobile(jObj.getString("awwMobile"));
                homeRealmModel.setPhcName(jObj.getString("phcName"));
                homeRealmModel.setPhcMobile(jObj.getString("phcMobile"));

                if(jObj.getString("mPhoto").equalsIgnoreCase("null")){
                    homeRealmModel.setMPhoto("");
                    preferenceData.setMotherPhoto("");
                }else{
                    homeRealmModel.setMPhoto(jObj.getString("mPhoto"));
                    preferenceData.setMotherPhoto(jObj.getString("mPhoto"));
                }

                homeRealmModel.setDeleveryStatus(jObj.getString("deleveryStatus"));
                if (jObj.getString("deleveryStatus").equalsIgnoreCase("1")) {
                    card_pn_block.setVisibility(View.VISIBLE);
                    JSONObject jsonObject = jObj.getJSONObject("PNnextVisit");
                    homeRealmModel.setMRiskStatus(jObj.getString("mRiskStatus"));

                    if(jObj.getString("mRiskStatus").equalsIgnoreCase("HIGH")){
                        pn_low_risk.setVisibility(View.GONE);
                        pn_high_risk.setVisibility(View.VISIBLE);
                        txt_pn_risk.setText(homeRealmModel.getMRiskStatus());
                        txt_pn_risk.setTextColor(Color.parseColor("#fff44336"));
                    }else{
                        pn_low_risk.setVisibility(View.VISIBLE);
                        pn_high_risk.setVisibility(View.GONE);
                        txt_pn_risk.setText(homeRealmModel.getMRiskStatus());
                    }
                    homeRealmModel.setDeleveryDate(jsonObject.getString("deleveryDate"));
                    homeRealmModel.setdBirthWeight(jsonObject.getString("childWeight"));
                    homeRealmModel.setDeleveryType(jsonObject.getString("deleveryType"));
                    homeRealmModel.setMeaturityWeeks(jsonObject.getString("meaturityWeeks"));
                    homeRealmModel.setPnVisit(jsonObject.getString("pnVisit"));
                }
                else{
                    card_pn_block.setVisibility(View.GONE);
                }
                realm.commitTransaction();
            }else{
                if(status.equalsIgnoreCase("0")){
                    preferenceData.setLogin(false);
                    startActivity(new Intent(getActivity(), Login.class));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setValueToUI();
    }

    private void setValueToUI() {
        Log.e(HomeFragment.class.getSimpleName(),"your app is now ON LINE");

        realm.beginTransaction();
        RealmResults<HomeRealmModel> homeRealmModels = realm.where(HomeRealmModel.class).findAll();
        for(int i=0; i < homeRealmModels.size();i++){
            HomeRealmModel homeRealmModel = homeRealmModels.get(i);

            if(homeRealmModel.getMLMP().equalsIgnoreCase("null")){
                txt_lmp_date.setText("-");
            }else {
                txt_lmp_date.setText(homeRealmModel.getMLMP());
            }
            if(homeRealmModel.getMEDD().equalsIgnoreCase("null")){
                txt_edd_date.setText("-");
            }else {
                txt_edd_date.setText(homeRealmModel.getMEDD());
            }
            if(preferenceData.getMotherAge().equalsIgnoreCase("null")){
                txt_age.setText("-");
            }else {
                txt_age.setText(preferenceData.getMotherAge());
            }
            if(homeRealmModel.getMRiskStatus().equalsIgnoreCase("null")){
                txt_an_risk.setText("-");
            }else {
                if(homeRealmModel.getMRiskStatus().equalsIgnoreCase("HIGH")){
                    low_risk.setVisibility(View.GONE);
                    high_risk.setVisibility(View.VISIBLE);
                    txt_an_risk.setText(homeRealmModel.getMRiskStatus());
                    txt_an_risk.setTextColor(Color.parseColor("#fff44336"));
                }else{
                    low_risk.setVisibility(View.VISIBLE);
                    high_risk.setVisibility(View.GONE);
                    txt_an_risk.setText(homeRealmModel.getMRiskStatus());
                }
            }


            if(homeRealmModel.getMWeight().equalsIgnoreCase("null")){
                txt_weight.setText("-");
            }else {
                txt_weight.setText(homeRealmModel.getMWeight());
            }
            String txtHusbandName = homeRealmModel.getMHusbandName();
            if(txtHusbandName.equalsIgnoreCase("null")){
                txt_husb_name.setText("-");
            }else{
                txt_husb_name.setText(homeRealmModel.getMHusbandName());
            }
            if(preferenceData.getGstWeek().equalsIgnoreCase("null")){
                txt_gst_week.setText("-");
            }else {
                txt_gst_week.setText(preferenceData.getGstWeek());
            }
            if(homeRealmModel.getANnextVisit().equalsIgnoreCase("null")){
                txt_next_visit.setText("-");
            }else {
                txt_next_visit.setText(homeRealmModel.getANnextVisit());
            }
            str_mobile_number_hsbn = homeRealmModel.getMHusbandMobile();
            if(str_mobile_number_hsbn.equalsIgnoreCase("null")){
                img_call_husb.setVisibility(View.GONE);
            }else {
                img_call_husb.setVisibility(View.VISIBLE);
            }
            String vhnName = homeRealmModel.getVhnName();
            if(vhnName.equalsIgnoreCase("null")){
                txt_vhn_name.setText("-");
            }else{
                txt_vhn_name.setText(homeRealmModel.getVhnName());
            }
            str_mobile_number_vhn = homeRealmModel.getVhnMobile();
            if(str_mobile_number_vhn.equalsIgnoreCase("null")){
                img_call_vhn.setVisibility(View.GONE);
            }else{
                img_call_vhn.setVisibility(View.VISIBLE);
            }
            String awwName = homeRealmModel.getAwwName();
            if(awwName.equalsIgnoreCase("null")){
                txt_aww_name.setText("-");
            }else{
                txt_aww_name.setText(homeRealmModel.getAwwName());
            }
            str_mobile_number_aww = homeRealmModel.getAwwMobile();
            if(str_mobile_number_aww.equalsIgnoreCase("null")){
                img_call_aww.setVisibility(View.GONE);
            }else{
                img_call_aww.setVisibility(View.VISIBLE);
            }
            String phcName = homeRealmModel.getPhcName();
            if(phcName.equalsIgnoreCase("null")){
                txt_phc_name.setText("-");
            }else{
                txt_phc_name.setText(homeRealmModel.getPhcName());
            }
            str_mobile_number_phc = homeRealmModel.getPhcMobile();
            if(str_mobile_number_phc.equalsIgnoreCase("null")){
                img_call_phc.setVisibility(View.GONE);
            }else{
                img_call_phc.setVisibility(View.VISIBLE);
            }
            str_mPhoto = homeRealmModel.getMPhoto();

            if(TextUtils.isEmpty(str_mPhoto)){
                cardview_image.setImageResource(R.drawable.girl_1);
            }else{
                Log.d("mphoto-->",Apiconstants.PHOTO_URL+str_mPhoto);

                Picasso.with(context)
                        .load(Apiconstants.PHOTO_URL + str_mPhoto)
                        .placeholder(R.drawable.girl_1)
                        .fit()
                        .centerCrop()
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .transform(new RoundedTransformation(90,4))
                        .error(R.drawable.girl_1)
                        .into(cardview_image);
            }

            strDeliveryDate = homeRealmModel.getDeleveryStatus();
            if(strDeliveryDate.equalsIgnoreCase("1")){
                card_pn_block.setVisibility(View.VISIBLE);

                if(homeRealmModel.getMRiskStatus().equalsIgnoreCase("HIGH")){
                    pn_low_risk.setVisibility(View.GONE);
                    pn_high_risk.setVisibility(View.VISIBLE);
                    txt_pn_risk.setText(homeRealmModel.getMRiskStatus());
                    txt_pn_risk.setTextColor(Color.parseColor("#fff44336"));
                }else{
                        pn_low_risk.setVisibility(View.VISIBLE);
                        pn_high_risk.setVisibility(View.GONE);
                        txt_pn_risk.setText(homeRealmModel.getMRiskStatus());
                }
                if(homeRealmModel.getDeleveryDate().equalsIgnoreCase("null")){
                    txt_delivery_date.setText("-");
                }else {
                    txt_delivery_date.setText(homeRealmModel.getDeleveryDate());
                }
                if(homeRealmModel.getdBirthWeight().equalsIgnoreCase("null")){
                    txt_birth_weight.setText("-");
                }else {
                    txt_birth_weight.setText(homeRealmModel.getdBirthWeight());
                }
                if(homeRealmModel.getDeleveryType().equalsIgnoreCase("null")){
                    txt_type_delivery.setText("-");
                }else {
                    txt_type_delivery.setText(homeRealmModel.getDeleveryType());
                }
                if(homeRealmModel.getMeaturityWeeks().equalsIgnoreCase("null")){
                    txt_maturity.setText("-");
                }else {
                    txt_maturity.setText(homeRealmModel.getMeaturityWeeks());
                }
                if(homeRealmModel.getPnVisit().equalsIgnoreCase("null")){
                    txt_pn_next_visit.setText("-");
                }else {
                    txt_pn_next_visit.setText(homeRealmModel.getPnVisit());
                }


            }
            else{
                card_pn_block.setVisibility(View.GONE);
            }

        }
        realm.commitTransaction();
    }

    @Override
    public void showErrorMessage(String string) {
        Log.e( "Error", string);

    }

    @Override
    public void showForgetResult(String response) {

    }

    @Override
    public void showForgetErrorMessage(String string) {

    }

    @Override
    public void showUninstallSuccess(String response) {

    }

    @Override
    public void showUninstallError(String string) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_call_husb:
                makeCall(str_mobile_number_hsbn);
                break;

//                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("tel:+"+str_mobile_number_hsbn)));
            case R.id.img_call_vhn:
                makeCall(str_mobile_number_vhn);
                break;
            case R.id.img_call_aww:
                makeCall(str_mobile_number_aww);
                break;
            case R.id.img_call_phc:
                makeCall(str_mobile_number_phc);
                break;
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
            Log.i(getActivity().getCallingPackage(),"CALL permission has already been granted. Displaying camera preview.");
//            showCameraPreview();
            pDialog.hide();
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:+91"+str_mobile_number)));



        }
    }

    private void requestCallPermission() {


        Log.i(getActivity().getLocalClassName(), "CALL permission has NOT been granted. Requesting permission.");

        // BEGIN_INCLUDE(camera_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CALL_PHONE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            Log.i(getActivity().getLocalClassName(),            "Displaying camera permission rationale to provide additional context.");
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

    @Override
    public void onResume() {
        super.onResume();
    }
}
