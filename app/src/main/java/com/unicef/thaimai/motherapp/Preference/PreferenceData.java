package com.unicef.thaimai.motherapp.Preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.unicef.thaimai.motherapp.constant.AppConstants;

/**
 * Created by sathish on 2/20/2018.
 */

public class PreferenceData {
    SharedPreferences sharedPreferences;
    public PreferenceData(Context context) {
        sharedPreferences = context.getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE);
    }
    public SharedPreferences getPreference() {
        return sharedPreferences;
    }


    public void setLogin(boolean isLogin) {
        sharedPreferences.edit().putBoolean(AppConstants.IS_LOGIN, isLogin).commit();
    }

    public boolean getLogin() {
        return  sharedPreferences.getBoolean(AppConstants.IS_LOGIN, Boolean.parseBoolean(""));
    }

    public void storeUserInfo(String picmeid,String mid,String name, String age, String status, String phcId, String vhnId, String awwId, String dId){
        sharedPreferences.edit().putString(AppConstants.PICME_ID, picmeid).commit();
        sharedPreferences.edit().putString(AppConstants.M_ID, mid).commit();
        sharedPreferences.edit().putString(AppConstants.MOTHER_NAME, name).commit();
        sharedPreferences.edit().putString(AppConstants.MOTHER_AGE, age).commit();
        sharedPreferences.edit().putString(AppConstants.MOTHER_STATUS,status ).commit();
        sharedPreferences.edit().putString(AppConstants.PHC_ID,phcId).commit();
        sharedPreferences.edit().putString(AppConstants.VHN_ID,vhnId).commit();
        sharedPreferences.edit().putString(AppConstants.AWW_ID,awwId).commit();
        sharedPreferences.edit().putString(AppConstants.DELIVERY_ID, dId).commit();
        Log.e("PICME_ID",sharedPreferences.getString(AppConstants.PICME_ID,""));
        Log.e("MOTHER_NAME",sharedPreferences.getString(AppConstants.MOTHER_NAME,""));
        Log.e("MOTHER_AGE",sharedPreferences.getString(AppConstants.MOTHER_AGE,""));
        Log.e("MOTHER_STATUS",sharedPreferences.getString(AppConstants.MOTHER_STATUS,""));

        Log.e("D_ID", sharedPreferences.getString(AppConstants.DELIVERY_ID,""));
    }



    public String getPicmeId(){
        return sharedPreferences.getString(AppConstants.PICME_ID,"");
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
    public String getDid(){
        return sharedPreferences.getString(AppConstants.DELIVERY_ID, "1");
    }
}
