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

    public void storeUserInfo(String userinfo){
        Gson gson = new Gson();
        String json = gson.toJson(userinfo);
        sharedPreferences.edit().putString(AppConstants.USER_INFO, json).commit();
        Log.e("USER_INFO",sharedPreferences.getString(AppConstants.USER_INFO,""));
    }

    public void storeusermedical(String usermedical) {
        Gson gson = new Gson();
        String json = gson.toJson(usermedical);
        sharedPreferences.edit().putString(AppConstants.USER_MEDICAL, json).commit();
        Log.e("USER_MDICAL",sharedPreferences.getString(AppConstants.USER_MEDICAL,""));
    }

    public void storeEmergencyContacts(String emergencyContacts) {
        Gson gson = new Gson();
        String json = gson.toJson(emergencyContacts);
        sharedPreferences.edit().putString(AppConstants.USER_EMERGENCY_CONTACTS, json).commit();
        Log.e("USER_EMERGENCY_CONTACTS",sharedPreferences.getString(AppConstants.USER_EMERGENCY_CONTACTS,""));
    }

    public String getUserInfo(){
        return sharedPreferences.getString(AppConstants.USER_INFO,"");
    }public String getUserMedical(){
        return sharedPreferences.getString(AppConstants.USER_MEDICAL,"");
    }public String getEmergencyContacts(){
        return sharedPreferences.getString(AppConstants.USER_EMERGENCY_CONTACTS,"");
    }
}
