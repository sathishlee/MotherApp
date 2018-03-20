package com.unicef.thaimai.motherapp.view;

/**
 * Created by Suthishan on 20/1/2018.
 */

public interface ImmunizationListViews {

    void showProgress();
    void hideProgress();

    void getImmunizationListSuccess(String response);
    void getImmunizationListFailure(String response);
}
