package com.unicef.thaimai.motherapp.view;

/**
 * Created by sathish on 3/7/2018.
 */

public interface PrimaryRegisterViews {
    void showProgress();
    void hideProgress();
    void getAllMotherPrimaryRegisterSuccess(String response);
    void getAllMotherPrimaryRegisterFailiur(String response);

    void postDataSuccess(String response);
    void postDataFailiure(String response);
}
