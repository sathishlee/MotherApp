package com.unicef.thaimai.motherapp.Preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.unicef.thaimai.motherapp.activity.MainActivity;
import com.unicef.thaimai.motherapp.constant.AppConstants;

/**
 * Created by sathish on 2/20/2018.
 */

public class PreferenceData {

    SharedPreferences sharedPreferences;
    public PreferenceData(Context context) {
        sharedPreferences = context.getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE);
//        sharedPreferences.edit().clear().commit();
    }
    public SharedPreferences getPreference() {
        return sharedPreferences;
    }


    public void setLogin(boolean isLogin) {
        sharedPreferences.edit().putBoolean(AppConstants.IS_LOGIN, isLogin).commit();
    }

    public void setSharePrefrenceLocale(String locale) {
        sharedPreferences.edit().putString(AppConstants.LANGUAGE,locale).commit();
    }

    public String getSharePrefrenceLocale() {
        return sharedPreferences.getString(AppConstants.LANGUAGE,"");

    }


    public boolean getLogin() {
        return  sharedPreferences.getBoolean(AppConstants.IS_LOGIN, Boolean.parseBoolean(""));
    }

    public void storePicmeInfo(String picmeid, String DOB){
        sharedPreferences.edit().putString(AppConstants.PICME_ID_CHECK,picmeid).commit();
        sharedPreferences.edit().putString(AppConstants.MOTHER_DOB,DOB).commit();
//        sharedPreferences.edit().putString(AppConstants.MOTHER_PHOTO,mPhoto).commit();
    }
    public String getMotherPhoto(){
        return sharedPreferences.getString(AppConstants.MOTHER_PHOTO,"");
    }
    public void setMotherPhoto(String mPhoto){
        sharedPreferences.edit().putString(AppConstants.MOTHER_PHOTO, mPhoto).commit();
    }

    public String getCheckPicmeID(){
        return sharedPreferences.getString(AppConstants.PICME_ID_CHECK,"");
    }
    public String getCheckDob(){
        return sharedPreferences.getString(AppConstants.MOTHER_DOB,"");
    }

    public void storeUserInfo(String picmeid,String mid,String name, String age, String status,
                              String phcId, String vhnId, String awwId,String gstWeek,
                              String vhnMobileNumber){
        sharedPreferences.edit().putString(AppConstants.PICME_ID, picmeid).commit();
        sharedPreferences.edit().putString(AppConstants.M_ID, mid).commit();
        sharedPreferences.edit().putString(AppConstants.MOTHER_NAME, name).commit();
        sharedPreferences.edit().putString(AppConstants.MOTHER_AGE, age).commit();
        sharedPreferences.edit().putString(AppConstants.MOTHER_STATUS,status ).commit();
        sharedPreferences.edit().putString(AppConstants.PHC_ID,phcId).commit();
        sharedPreferences.edit().putString(AppConstants.VHN_ID,vhnId).commit();
        sharedPreferences.edit().putString(AppConstants.AWW_ID,awwId).commit();
        sharedPreferences.edit().putString(AppConstants.GST_WEEK, gstWeek).commit();
        sharedPreferences.edit().putString(AppConstants.VHN_MOBILE_NUMBER, vhnMobileNumber).commit();
//        sharedPreferences.edit().putString(AppConstants.MOTHER_PHOTO, mPhoto).commit();
//        sharedPreferences.edit().putString(AppConstants.DEVICE_ID,tokenId).commit();
        Log.e("PICME_ID",sharedPreferences.getString(AppConstants.PICME_ID,""));
        Log.e("MOTHER_NAME",sharedPreferences.getString(AppConstants.MOTHER_NAME,""));
        Log.e("MOTHER_AGE",sharedPreferences.getString(AppConstants.MOTHER_AGE,""));
        Log.e("MOTHER_STATUS",sharedPreferences.getString(AppConstants.MOTHER_STATUS,""));
        Log.e("MOTHER_STATUS",sharedPreferences.getString(AppConstants.VHN_MOBILE_NUMBER,""));
    }



    public String getPicmeId(){
        return sharedPreferences.getString(AppConstants.PICME_ID,"");
    } public String getVHNMobileNumber(){
        return sharedPreferences.getString(AppConstants.VHN_MOBILE_NUMBER,"");
    } public String getMId(){
        return sharedPreferences.getString(AppConstants.M_ID,"");
    }public String getMotherName(){
        return sharedPreferences.getString(AppConstants.MOTHER_NAME,"");
    }public String getMotherAge(){
        return sharedPreferences.getString(AppConstants.MOTHER_AGE,"");
    }
    public String getPhcId(){
        return sharedPreferences.getString(AppConstants.PHC_ID,"");
    }
    public String getVhnId(){
        return sharedPreferences.getString(AppConstants.VHN_ID,"");
    } public String getAwwId(){
        return sharedPreferences.getString(AppConstants.AWW_ID,"");
    }

    public void storeDid(String strDid) {
        sharedPreferences.edit().putString(AppConstants.DELIVERY_ID,strDid).commit();
    }
    public void storeImmuid(String strImmuID){
        sharedPreferences.edit().putString(AppConstants.IMMUNIZATION_ID, strImmuID).commit();
    }

    public  String getDid() {
        return sharedPreferences.getString(AppConstants.DELIVERY_ID, "");
    }

    public void setDeviceId(String deviceId) {
        sharedPreferences.edit().putString(AppConstants.DEVICE_ID, deviceId).commit();
    }

    public String getDeviceId(){
        return sharedPreferences.getString(AppConstants.DEVICE_ID,"");
    }


   public void setGstWeek(String gstweek) {
        sharedPreferences.edit().putString(AppConstants.GST_WEEK, gstweek).commit();
    }

    public String getGstWeek(){
        return sharedPreferences.getString(AppConstants.GST_WEEK,"");
    }



    public void setMainScreenOpen(int count) {
        sharedPreferences.edit().putString(AppConstants.isMainActivityOpen_Count, String.valueOf(count)).commit();
    }

    public String getMainScreenOpen() {
        return sharedPreferences.getString(AppConstants.isMainActivityOpen_Count,"");

    }


    public void setNotificationCount(String count) {
        sharedPreferences.edit().putString(AppConstants.NOTIFICATION_COUNT, count).commit();
    }


    public String getNotificationCount() {
        return  sharedPreferences.getString(AppConstants.NOTIFICATION_COUNT, "");
    }

    public void setCurentAdress(String strAddress) {
        sharedPreferences.edit().putString(AppConstants.CURENT_ADDRESS, strAddress).commit();
            }

    public void setCurentlatitude(String latitude) {
        sharedPreferences.edit().putString(AppConstants.CURENT_LAT, latitude).commit();
    }

    public void setCurentlongitude(String longitude) {
        sharedPreferences.edit().putString(AppConstants.CURENT_LON, longitude).commit();
    }

    public String gettCurentAdress() {
        return  sharedPreferences.getString(AppConstants.CURENT_ADDRESS, "");
    }
    public String gettCurentlatitude() {
        return  sharedPreferences.getString(AppConstants.CURENT_LAT, "");
    }
    public String gettCurentlongitude() {
        return  sharedPreferences.getString(AppConstants.CURENT_LON, "");
    }
}
